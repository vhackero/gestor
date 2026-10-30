package mx.gob.sedesol.basegestor.gestionescolar.v2;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.CausaPendienteUnidadV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.ContextoEvaluacionCurricularV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EstadoElegibilidadElectivaV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EstadoUnidadCurricularV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EvaluacionCargaAcademicaV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.UnidadEvaluacionCurricularV2DTO;
import mx.gob.sedesol.basegestor.service.impl.gestionescolar.v2.EvaluacionCurricularV2ServiceImpl;

/** RN-7.2 definitivo: tratamiento de optativas reprobadas, sin categoría ELECTIVA. */
public class EvaluacionCurricularV2Fase7BTest {
    private final EvaluacionCurricularV2ServiceImpl service = new EvaluacionCurricularV2ServiceImpl();

    @Test public void f7BU01NoAplicaSinOptativasReprobadas() {
        EvaluacionCargaAcademicaV2DTO carga = carga(contexto());
        assertFalse(Boolean.TRUE.equals(carga.getAplicaRn72()));
        assertEquals(carga.getEstadoElegibilidadElectiva(), EstadoElegibilidadElectivaV2.NO_APLICA);
    }

    @Test public void f7BU02OptativaReprobadaRequeridaReutilizaRn71() {
        ContextoEvaluacionCurricularV2DTO contexto = contexto();
        contexto.getUnidades().add(optativa(1L, EstadoUnidadCurricularV2.REPROBADA));
        EvaluacionCargaAcademicaV2DTO carga = carga(contexto);
        assertTrue(Boolean.TRUE.equals(carga.getAplicaRn72()));
        assertTrue(carga.getDecisionesPorUdElectivaRn72().toString().contains("OPTATIVA_REPROBADA_REQUERIDA_RECURSAMIENTO_MISMA_UD"));
    }

    @Test public void f7BU03OchoOptativasAcreditadasConservanTratamientoAdicionalSujetoACatalogo() {
        ContextoEvaluacionCurricularV2DTO contexto = contexto();
        for (long id = 1; id <= 8; id++) {
            UnidadEvaluacionCurricularV2DTO unidad = optativa(id, EstadoUnidadCurricularV2.ACREDITADA);
            unidad.setOrdenAcreditacionOptativa(Integer.valueOf((int) id));
            contexto.getUnidades().add(unidad);
        }
        contexto.getUnidades().add(optativa(9L, EstadoUnidadCurricularV2.REPROBADA));
        assertTrue(carga(contexto).getDecisionesPorUdElectivaRn72().toString()
                .contains("OPTATIVA_REPROBADA_POSIBLE_ADEUDO_ADICIONAL_SUJETO_A_CATALOGO_Y_RN711"));
    }

    @Test public void f7BU04EtiquetaElectivaLegacyNoGeneraReglaNueva() {
        ContextoEvaluacionCurricularV2DTO contexto = contexto();
        UnidadEvaluacionCurricularV2DTO legado = optativa(1L, EstadoUnidadCurricularV2.NO_INSCRITA);
        legado.setTipoUd("Electiva");
        contexto.getUnidades().add(legado);
        EvaluacionCargaAcademicaV2DTO carga = carga(contexto);
        assertFalse(Boolean.TRUE.equals(carga.getAplicaRn72()));
        assertTrue(carga.getUdsElectivasElegiblesRn72().isEmpty());
    }

    @Test public void f7BU05MismoContextoEsDeterministaYSinEfectoProductivo() {
        ContextoEvaluacionCurricularV2DTO contexto = contexto();
        contexto.getUnidades().add(optativa(1L, EstadoUnidadCurricularV2.REPROBADA));
        assertEquals(carga(contexto).getDecisionesPorUdElectivaRn72(), carga(contexto).getDecisionesPorUdElectivaRn72());
        assertFalse(service.evaluarSombra(contexto).getEfectosProductivosAplicados());
    }

    private ContextoEvaluacionCurricularV2DTO contexto() {
        ContextoEvaluacionCurricularV2DTO contexto = new ContextoEvaluacionCurricularV2DTO();
        contexto.setPeriodoNormativo("2027-1");
        contexto.setActividadAcademicaComprobada(Boolean.TRUE);
        contexto.setPermanenciaDentroLimiteComprobada(Boolean.TRUE);
        return contexto;
    }

    private UnidadEvaluacionCurricularV2DTO optativa(Long id, EstadoUnidadCurricularV2 estado) {
        UnidadEvaluacionCurricularV2DTO unidad = new UnidadEvaluacionCurricularV2DTO();
        unidad.setIdPrograma(id); unidad.setClave("OPT" + id); unidad.setTipoUd("Optativa");
        unidad.setSemestre(Integer.valueOf(3)); unidad.setEstado(estado); unidad.setOfertada(Boolean.TRUE);
        unidad.setGrupoActivoConocido(Boolean.TRUE); unidad.setCupoDisponibleConocido(Boolean.TRUE);
        if (estado == EstadoUnidadCurricularV2.REPROBADA) {
            unidad.setCausa(CausaPendienteUnidadV2.REPROBACION);
            unidad.setReprobacionVigenteConocida(Boolean.TRUE);
        }
        return unidad;
    }

    private EvaluacionCargaAcademicaV2DTO carga(ContextoEvaluacionCurricularV2DTO contexto) {
        return service.evaluarSombra(contexto).getEvaluacionCargaAcademica();
    }
}
