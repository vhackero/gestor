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
    public List<CatalogoOpcionDTO> obtenerPlanesPorPersona(Long idPersona) {
        String consulta = "SELECT DISTINCT ti.idplan, tp.nombre FROM tbl_inscripciones ti "
                + " INNER JOIN tbl_planes tp ON tp.id_plan = ti.idplan"
                + " WHERE ti.IdpersonaSIGIE = :idPersona ORDER BY tp.nombre";
        Query query = entityManager.createNativeQuery(consulta);
        query.setParameter("idPersona", idPersona);
        return mapearCatalogos(query.getResultList());
    }

    @Override
    public List<CatalogoOpcionDTO> obtenerSemestres(Long idPersona, Integer idPlan) {
        String consulta = "SELECT DISTINCT ti.semestre, CONCAT('Semestre ', ti.semestre)"
                + " FROM tbl_inscripciones ti WHERE ti.IdpersonaSIGIE = :idPersona AND ti.idplan = :idPlan ORDER BY ti.semestre";
        Query query = entityManager.createNativeQuery(consulta);
        query.setParameter("idPersona", idPersona);
        query.setParameter("idPlan", idPlan);
        return mapearCatalogos(query.getResultList());
    }

    @Override
    public List<CatalogoOpcionDTO> obtenerBloques(Long idPersona, Integer idPlan, Integer semestre) {
        String consulta = "SELECT DISTINCT ti.bloque, ti.bloque FROM tbl_inscripciones ti"
                + " WHERE ti.IdpersonaSIGIE = :idPersona AND ti.idplan = :idPlan"
                + " AND (:semestre IS NULL OR ti.semestre = :semestre) ORDER BY ti.bloque";
        Query query = entityManager.createNativeQuery(consulta);
        query.setParameter("idPersona", idPersona);
        query.setParameter("idPlan", idPlan);
        query.setParameter("semestre", semestre);
        return mapearCatalogos(query.getResultList());
    }

    @Override
    public List<CatalogoOpcionDTO> obtenerProgramas(Long idPersona, Integer idPlan, Integer semestre, String bloque) {
        String consulta = "SELECT DISTINCT ti.idprograma, ti.programa FROM tbl_inscripciones ti"
                + " WHERE ti.IdpersonaSIGIE = :idPersona AND ti.idplan = :idPlan"
                + " AND (:semestre IS NULL OR ti.semestre = :semestre)"
                + " AND (:bloque IS NULL OR ti.bloque = :bloque) ORDER BY ti.programa";
        Query query = entityManager.createNativeQuery(consulta);
        query.setParameter("idPersona", idPersona);
        query.setParameter("idPlan", idPlan);
        query.setParameter("semestre", semestre);
        query.setParameter("bloque", bloque);
        return mapearCatalogos(query.getResultList());
    }

    @Override
    public List<CatalogoOpcionDTO> obtenerPeriodos(Integer idPlan) {
        String consulta = "SELECT cp.id_periodo, cp.nombre FROM tbl_planes tp"
                + " INNER JOIN cat_periodos cp ON cp.id_periodo = tp.id_periodo"
                + " WHERE tp.id_plan = :idPlan AND cp.activo = 1";
        Query query = entityManager.createNativeQuery(consulta);
        query.setParameter("idPlan", idPlan);
        return mapearCatalogos(query.getResultList());
    }

    @Override
    public List<CatalogoOpcionDTO> obtenerEventos(Integer idPlan, Integer idPrograma) {
        String consulta = "SELECT DISTINCT ti.idevento, COALESCE(te.nombre_ec, CONCAT('Evento ', ti.idevento))"
                + " FROM tbl_inscripciones ti"
                + " LEFT JOIN tbl_eventos te ON te.id_evento = ti.idevento"
                + " WHERE ti.idplan = :idPlan AND (:idPrograma IS NULL OR ti.idprograma = :idPrograma)"
                + " ORDER BY 2";
        Query query = entityManager.createNativeQuery(consulta);
        query.setParameter("idPlan", idPlan);
        query.setParameter("idPrograma", idPrograma);
        return mapearCatalogos(query.getResultList());
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
