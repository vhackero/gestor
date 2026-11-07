package mx.gob.sedesol.basegestor.gestionescolar.v2;

import static org.testng.Assert.assertTrue;

import java.util.Date;

import org.testng.annotations.Test;

import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.CausaPendienteUnidadV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.CodigoMotivoCurricularV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.CodigoReglaCurricularV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.ContextoEvaluacionCurricularV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EstadoUnidadCurricularV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.ResultadoEvaluacionCurricularV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.TrazaReglaAplicadaV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.UnidadEvaluacionCurricularV2DTO;
import mx.gob.sedesol.basegestor.service.impl.gestionescolar.v2.EvaluacionCurricularV2ServiceImpl;

/** Verifica la numeracion vigente de las trazas del control de avance. */
public class TrazabilidadNumeracionRn61Test {
    @Test public void cadaCondicionEmiteElCodigoVigenteDeRn61() {
        ResultadoEvaluacionCurricularV2DTO base = evaluar(contexto(false));
        assertTrue(tieneTraza(base, CodigoReglaCurricularV2.RN_6_1_1, null));
        assertTrue(tieneTraza(base, CodigoReglaCurricularV2.RN_6_1_2, null));
        assertTrue(tieneTraza(base, CodigoReglaCurricularV2.RN_6_1_3, null));

        ResultadoEvaluacionCurricularV2DTO omitida = evaluar(contexto(true));
        assertTrue(tieneTraza(omitida, CodigoReglaCurricularV2.RN_6_1_2,
                CodigoMotivoCurricularV2.PENDIENTE_OMITIDA_AVANCE_CONDICIONADO));
    }

    private ResultadoEvaluacionCurricularV2DTO evaluar(ContextoEvaluacionCurricularV2DTO contexto) {
        return new EvaluacionCurricularV2ServiceImpl().evaluarSombra(contexto);
    }

    private ContextoEvaluacionCurricularV2DTO contexto(boolean omitirPendiente) {
        ContextoEvaluacionCurricularV2DTO contexto = new ContextoEvaluacionCurricularV2DTO();
        contexto.setIdPlan(1L); contexto.setPeriodoNormativo("2027-1"); contexto.setSemestreReferencia(5);
        contexto.setFechaEvaluacion(new Date(0)); contexto.setActividadAcademicaComprobada(Boolean.TRUE);
        contexto.setPermanenciaDentroLimiteComprobada(Boolean.TRUE); contexto.setSeriacionComprobada(Boolean.TRUE);
        contexto.setEstructuraProgramaEducativoComprobada(Boolean.TRUE); contexto.setSancionImpedimentoConocida(Boolean.TRUE);
        contexto.setSancionImpedimentoRegistrada(Boolean.FALSE); contexto.setAvanceCondicionado(Boolean.TRUE);
        contexto.getUnidades().add(pendiente(1L, 1));
        contexto.getUnidades().add(acreditada(2L, 2)); contexto.getUnidades().add(acreditada(3L, 3));
        contexto.getUnidades().add(acreditada(4L, 4)); contexto.getUnidades().add(noCursada(5L, 5));
        contexto.getIdsUnidadesPretendidas().add(5L);
        if (!omitirPendiente) contexto.getIdsUnidadesPretendidas().add(1L);
        return contexto;
    }

    private UnidadEvaluacionCurricularV2DTO pendiente(Long id, int semestre) {
        UnidadEvaluacionCurricularV2DTO unidad = unidad(id, semestre, EstadoUnidadCurricularV2.REPROBADA);
        unidad.setCausa(CausaPendienteUnidadV2.REPROBACION); unidad.setReprobacionVigenteConocida(Boolean.TRUE);
        unidad.setReprobacionPeriodoPrevioConocida(Boolean.TRUE); return unidad;
    }
    private UnidadEvaluacionCurricularV2DTO acreditada(Long id, int semestre) { return unidad(id, semestre, EstadoUnidadCurricularV2.ACREDITADA); }
    private UnidadEvaluacionCurricularV2DTO noCursada(Long id, int semestre) { UnidadEvaluacionCurricularV2DTO unidad = unidad(id, semestre, EstadoUnidadCurricularV2.NO_CURSADA); unidad.setCausa(CausaPendienteUnidadV2.NO_CURSAMIENTO); return unidad; }
    private UnidadEvaluacionCurricularV2DTO unidad(Long id, int semestre, EstadoUnidadCurricularV2 estado) {
        UnidadEvaluacionCurricularV2DTO unidad = new UnidadEvaluacionCurricularV2DTO();
        unidad.setIdPrograma(id); unidad.setClave("UD" + id); unidad.setTipoUd("Obligatoria"); unidad.setSemestre(semestre);
        unidad.setEstado(estado); unidad.setOfertada(Boolean.TRUE); unidad.setGrupoActivoConocido(Boolean.TRUE);
        unidad.setCupoDisponibleConocido(Boolean.TRUE); return unidad;
    }
    private boolean tieneTraza(ResultadoEvaluacionCurricularV2DTO resultado, CodigoReglaCurricularV2 regla, CodigoMotivoCurricularV2 motivo) {
        for (TrazaReglaAplicadaV2DTO traza : resultado.getTrazas())
            if (traza.getCodigoRegla() == regla && (motivo == null || traza.getCodigoMotivo() == motivo)) return true;
        return false;
    }
}
