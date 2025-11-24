package mx.gob.sedesol.basegestor.model.repositories.gestionescolar;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import mx.gob.sedesol.basegestor.commons.dto.NodoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.PlanBajaDTO;

@Repository
public class NuevaBajaRepository implements INuevaBajaRepository {

    @PersistenceContext(unitName = "entityManagerFactory")
    private EntityManager entityManager;

    @SuppressWarnings("unchecked")
    @Override
    public List<NodoDTO> consultarTiposBajaActivos() {
        String consulta = "SELECT ctb.id_tipo_baja, ctb.nombre FROM cat_tipo_bajas ctb WHERE ctb.activo = 1";
        Query query = entityManager.createNativeQuery(consulta);
        List<Object[]> resultados = query.getResultList();
        List<NodoDTO> tiposBaja = new ArrayList<>();

        for (Object[] fila : resultados) {
            tiposBaja.add(new NodoDTO(obtenerEntero(fila[0]), obtenerCadena(fila[1])));
        }
        return tiposBaja;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<PlanBajaDTO> consultarPlanesActivos() {
        String consulta = "SELECT tmc.id, tmc.id_plan, tmc.nombre FROM tbl_malla_curricular tmc WHERE tmc.activo = 1 AND tmc.id_plan IS NOT NULL";
        Query query = entityManager.createNativeQuery(consulta);
        List<Object[]> resultados = query.getResultList();
        List<PlanBajaDTO> planes = new ArrayList<>();

        for (Object[] fila : resultados) {
            planes.add(new PlanBajaDTO(obtenerEntero(fila[0]), obtenerEntero(fila[1]), obtenerCadena(fila[2])));
        }
        return planes;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<NodoDTO> consultarSemestresPorPlan(Long idPlan) {
        String consulta = "SELECT tmc.id, tmc.nombre FROM tbl_malla_curricular tmc WHERE tmc.id_plan = :idPlan";
        Query query = entityManager.createNativeQuery(consulta);
        query.setParameter("idPlan", idPlan);
        List<Object[]> resultados = query.getResultList();
        List<NodoDTO> semestres = new ArrayList<>();

        for (Object[] fila : resultados) {
            semestres.add(new NodoDTO(obtenerEntero(fila[0]), obtenerCadena(fila[1])));
        }
        return semestres;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<NodoDTO> consultarBloquesPorSemestre(Long idSemestre) {
        String consulta = "SELECT tmc.id, tmc.nombre FROM tbl_malla_curricular tmc WHERE tmc.id_padre = :idSemestre";
        Query query = entityManager.createNativeQuery(consulta);
        query.setParameter("idSemestre", idSemestre);
        List<Object[]> resultados = query.getResultList();
        List<NodoDTO> bloques = new ArrayList<>();

        for (Object[] fila : resultados) {
            bloques.add(new NodoDTO(obtenerEntero(fila[0]), obtenerCadena(fila[1])));
        }
        return bloques;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<NodoDTO> consultarProgramasPorEje(Long idEjeCapacitacion) {
        String consulta = "SELECT tfdp.id_programa, tfdp.nombre_tentativo FROM tbl_ficha_descriptiva_programa tfdp WHERE tfdp.id_eje_capacitacion = :idEjeCapacitacion";
        Query query = entityManager.createNativeQuery(consulta);
        query.setParameter("idEjeCapacitacion", idEjeCapacitacion);
        List<Object[]> resultados = query.getResultList();
        List<NodoDTO> programas = new ArrayList<>();

        for (Object[] fila : resultados) {
            programas.add(new NodoDTO(obtenerEntero(fila[0]), obtenerCadena(fila[1])));
        }
        return programas;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<String> consultarPeriodosInscripcion() {
        String consulta = "SELECT tpi.nombre_periodo FROM tbl_periodos_inscripcion tpi";
        Query query = entityManager.createNativeQuery(consulta);
        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<NodoDTO> consultarEventosPorPeriodoYPrograma(String nombrePeriodo, Long idPrograma) {
        String consulta = "SELECT te.id_evento, te.cve_evento_cap FROM tbl_eventos te WHERE te.cve_evento_cap LIKE CONCAT('%',:nombrePeriodo,'%') AND te.id_programa = :idPrograma";
        Query query = entityManager.createNativeQuery(consulta);
        query.setParameter("nombrePeriodo", nombrePeriodo);
        query.setParameter("idPrograma", idPrograma);
        List<Object[]> resultados = query.getResultList();
        List<NodoDTO> eventos = new ArrayList<>();

        for (Object[] fila : resultados) {
            eventos.add(new NodoDTO(obtenerEntero(fila[0]), obtenerCadena(fila[1])));
        }
        return eventos;
    }

    private Integer obtenerEntero(Object valor) {
        return valor != null ? Integer.valueOf(valor.toString()) : null;
    }

    private String obtenerCadena(Object valor) {
        return valor != null ? valor.toString() : null;
    }
}
