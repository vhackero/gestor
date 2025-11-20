package mx.gob.sedesol.basegestor.model.repositories.gestionescolar;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.BajaDetalleDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.CatalogoOpcionDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.NuevaBajaDTO;

@Repository
public class BajaUsuarioRepository implements IBajaUsuarioRepository {

    @Autowired
    private EntityManager entityManager;

    @Override
    public List<CatalogoOpcionDTO> obtenerTiposBaja() {
        String consulta = "SELECT ctb.id_tipo_baja, ctb.nombre FROM cat_tipo_bajas ctb WHERE ctb.activo = 1 ORDER BY ctb.nombre";
        List<Object[]> resultado = entityManager.createNativeQuery(consulta).getResultList();
        List<CatalogoOpcionDTO> lista = new ArrayList<>();
        if (resultado != null) {
            for (Object[] obj : resultado) {
                lista.add(mapearCatalogo(obj));
            }
        }
        return lista;
    }

    @Override
    public Optional<Long> buscarPersonaPorMatricula(String matricula) {
        String consulta = "SELECT tp.id_persona FROM tbl_persona tp WHERE tp.sso_idUsuario = :matricula AND tp.activo = 1";
        Query query = entityManager.createNativeQuery(consulta);
        query.setParameter("matricula", matricula);
        List<?> resultado = query.getResultList();
        if (resultado.isEmpty()) {
            return Optional.empty();
        }
        Number idPersona = (Number) resultado.get(0);
        return Optional.ofNullable(idPersona != null ? idPersona.longValue() : null);
    }

    @Override
    public List<CatalogoOpcionDTO> obtenerPlanes() {
        String consulta = "SELECT tmc.id, tmc.id_plan, tmc.nombre"
                + " FROM tbl_malla_curricular tmc"
                + " WHERE tmc.activo = 1 AND tmc.id_plan IS NOT NULL";
        List<Object[]> resultado = entityManager.createNativeQuery(consulta).getResultList();
        List<CatalogoOpcionDTO> planes = new ArrayList<>();
        if (resultado != null) {
            for (Object[] fila : resultado) {
                CatalogoOpcionDTO dto = new CatalogoOpcionDTO();
                dto.setId(fila[1] != null ? ((Number) fila[1]).longValue() : null);
                dto.setDescripcion(fila[2] != null ? fila[2].toString() : "");
                planes.add(dto);
            }
        }
        return planes;
    }

    @Override
    public List<CatalogoOpcionDTO> obtenerSemestres(Integer idPlan) {
        String consulta = "SELECT tmc.id, tmc.nombre FROM tbl_malla_curricular tmc WHERE tmc.id_plan = :idPlan";
        Query query = entityManager.createNativeQuery(consulta);
        query.setParameter("idPlan", idPlan);
        return mapearCatalogos(query.getResultList());
    }

    @Override
    public List<CatalogoOpcionDTO> obtenerBloques(Integer idSemestre) {
        String consulta = "SELECT tmc.id, tmc.nombre FROM tbl_malla_curricular tmc WHERE tmc.id_padre = :idSemestre";
        Query query = entityManager.createNativeQuery(consulta);
        query.setParameter("idSemestre", idSemestre);
        return mapearCatalogos(query.getResultList());
    }

    @Override
    public List<CatalogoOpcionDTO> obtenerProgramas(Integer idEjeCapacitacion) {
        String consulta = "SELECT * FROM tbl_ficha_descriptiva_programa tfdp WHERE tfdp.id_eje_capacitacion = :idEje";
        Query query = entityManager.createNativeQuery(consulta);
        query.setParameter("idEje", idEjeCapacitacion);
        List<Object[]> resultado = query.getResultList();
        List<CatalogoOpcionDTO> programas = new ArrayList<>();
        if (resultado != null) {
            for (Object[] fila : resultado) {
                CatalogoOpcionDTO dto = new CatalogoOpcionDTO();
                dto.setId(fila[0] != null ? ((Number) fila[0]).longValue() : null);
                dto.setDescripcion(fila[2] != null ? fila[2].toString() : "");
                programas.add(dto);
            }
        }
        return programas;
    }

    @Override
    public List<CatalogoOpcionDTO> obtenerPeriodos() {
        String consulta = "SELECT tpi.nombre_periodo FROM tbl_periodos_inscripcion tpi";
        List<?> resultado = entityManager.createNativeQuery(consulta).getResultList();
        List<CatalogoOpcionDTO> periodos = new ArrayList<>();
        if (resultado != null) {
            long consecutivo = 1L;
            for (Object fila : resultado) {
                CatalogoOpcionDTO dto = new CatalogoOpcionDTO();
                dto.setId(consecutivo++);
                dto.setDescripcion(fila != null ? fila.toString() : "");
                periodos.add(dto);
            }
        }
        return periodos;
    }

    @Override
    public List<CatalogoOpcionDTO> obtenerEventos(String nombrePeriodo, Integer idPrograma) {
        String consulta = "SELECT * FROM tbl_eventos te WHERE te.cve_evento_cap LIKE CONCAT('%', :nombrePeriodo, '%')"
                + " AND te.id_programa = :idPrograma";
        Query query = entityManager.createNativeQuery(consulta);
        query.setParameter("nombrePeriodo", nombrePeriodo);
        query.setParameter("idPrograma", idPrograma);
        List<Object[]> resultado = query.getResultList();
        List<CatalogoOpcionDTO> eventos = new ArrayList<>();
        if (resultado != null) {
            for (Object[] fila : resultado) {
                CatalogoOpcionDTO dto = new CatalogoOpcionDTO();
                dto.setId(fila[0] != null ? ((Number) fila[0]).longValue() : null);
                dto.setDescripcion(fila[3] != null ? fila[3].toString() : (fila[4] != null ? fila[4].toString() : ""));
                eventos.add(dto);
            }
        }
        return eventos;
    }

    @Override
    public Integer obtenerProcesoBajas() {
        String consulta = "SELECT tpi.proceso_inscripcion_id FROM tbl_procesos_inscripcion tpi"
                + " WHERE UPPER(tpi.nombre) LIKE '%BAJA%' AND tpi.activo = 1"
                + " ORDER BY tpi.fecha_inicio DESC LIMIT 1";
        List<?> resultado = entityManager.createNativeQuery(consulta).getResultList();
        if (resultado.isEmpty()) {
            return null;
        }
        return ((Number) resultado.get(0)).intValue();
    }

    @Override
    public BajaDetalleDTO obtenerDetalleBaja(Long idPersona, Integer idPlan, Integer idPrograma, Integer idEvento) {
        String consulta = "SELECT ti.IdpersonaSIGIE, ti.idplan, ti.idprograma, ti.idevento, tg.id"
                + " FROM tbl_inscripciones ti"
                + " LEFT JOIN tbl_grupos tg ON tg.id_evento = ti.idevento AND tg.clave = ti.groupbase"
                + " WHERE ti.IdpersonaSIGIE = :idPersona AND ti.idplan = :idPlan"
                + " AND (:idPrograma IS NULL OR ti.idprograma = :idPrograma)"
                + " AND (:idEvento IS NULL OR ti.idevento = :idEvento)"
                + " ORDER BY ti.fecha_registro DESC LIMIT 1";

        Query query = entityManager.createNativeQuery(consulta);
        query.setParameter("idPersona", idPersona);
        query.setParameter("idPlan", idPlan);
        query.setParameter("idPrograma", idPrograma);
        query.setParameter("idEvento", idEvento);

        List<Object[]> resultado = query.getResultList();
        if (resultado.isEmpty()) {
            return null;
        }

        Object[] obj = resultado.get(0);
        BajaDetalleDTO detalle = new BajaDetalleDTO();
        detalle.setIdPersona(obj[0] != null ? ((Number) obj[0]).longValue() : null);
        detalle.setIdPlan(obj[1] != null ? ((Number) obj[1]).intValue() : null);
        detalle.setIdPrograma(obj[2] != null ? ((Number) obj[2]).intValue() : null);
        detalle.setIdEvento(obj[3] != null ? ((Number) obj[3]).intValue() : null);
        detalle.setIdGrupo(obj[4] != null ? ((Number) obj[4]).intValue() : null);
        return detalle;
    }

    @Override
    @Transactional
    public Integer registrarMotivo(NuevaBajaDTO nuevaBaja) {
        String consulta = "INSERT INTO rel_motivo_baja (tipo_baja_id, descripcion) VALUES (:tipoBajaId, :descripcion)";
        Query query = entityManager.createNativeQuery(consulta);
        query.setParameter("tipoBajaId", nuevaBaja.getIdTipoBaja());
        query.setParameter("descripcion", nuevaBaja.getMotivo());
        query.executeUpdate();

        Number id = (Number) entityManager.createNativeQuery("SELECT LAST_INSERT_ID()").getSingleResult();
        return id != null ? id.intValue() : null;
    }

    @Override
    @Transactional
    public void registrarBaja(NuevaBajaDTO nuevaBaja, Long usuarioSesion) {
        String consulta = "INSERT INTO rel_persona_bajas (id_persona, motivo_baja_id, proceso_id, id_plan, id_programa, id_evento, id_grupo, id_user_enrolments_lms, usuario_modifico)"
                + " VALUES (:idPersona, :motivoId, :procesoId, :idPlan, :idPrograma, :idEvento, :idGrupo, :idUserEnrolments, :usuarioModifico)";
        Query query = entityManager.createNativeQuery(consulta);
        query.setParameter("idPersona", nuevaBaja.getIdPersona());
        query.setParameter("motivoId", nuevaBaja.getIdMotivo());
        query.setParameter("procesoId", nuevaBaja.getProcesoId());
        query.setParameter("idPlan", nuevaBaja.getIdPlan());
        query.setParameter("idPrograma", nuevaBaja.getIdPrograma());
        query.setParameter("idEvento", nuevaBaja.getIdEvento());
        query.setParameter("idGrupo", nuevaBaja.getIdGrupo() != null ? nuevaBaja.getIdGrupo() : 0);
        // Integración Moodle pendiente: los identificadores externos se almacenan en cero.
        query.setParameter("idUserEnrolments", 0);
        query.setParameter("usuarioModifico", usuarioSesion != null ? usuarioSesion : 0L);
        query.executeUpdate();
    }

    private List<CatalogoOpcionDTO> mapearCatalogos(List<?> resultados) {
        List<CatalogoOpcionDTO> lista = new ArrayList<>();
        if (resultados != null) {
            for (Object obj : resultados) {
                lista.add(mapearCatalogo((Object[]) obj));
            }
        }
        return lista;
    }

    private CatalogoOpcionDTO mapearCatalogo(Object[] obj) {
        CatalogoOpcionDTO dto = new CatalogoOpcionDTO();
        dto.setId(obj[0] != null ? Long.valueOf(obj[0].toString()) : null);
        dto.setDescripcion(obj[1] != null ? obj[1].toString() : "");
        return dto;
    }
}
