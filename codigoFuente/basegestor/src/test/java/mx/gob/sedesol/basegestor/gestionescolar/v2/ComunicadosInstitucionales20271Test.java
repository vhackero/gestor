package mx.gob.sedesol.basegestor.gestionescolar.v2;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import java.util.Date;
import org.testng.annotations.Test;

import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.CodigoAvisoEvaluacionCurricularV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.ContextoEvaluacionCurricularV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.DecisionUnidadCurricularV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EstadoIntentoAcreditacionV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EstadoIncorporacionDiferidaV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EstadoLimiteIntentosV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EstadoUnidadCurricularV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EvaluacionIntentosUnidadV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.IntentoAcreditacionUnidadV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.ParametrosCurricularesV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.ResultadoEvaluacionCurricularV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.UnidadEvaluacionCurricularV2DTO;
import mx.gob.sedesol.basegestor.service.impl.gestionescolar.v2.EvaluacionCurricularV2ServiceImpl;
import mx.gob.sedesol.basegestor.service.impl.gestionescolar.v2.ConfiguracionEvaluacionCurricularV2ServiceImpl;

/** GC-19: comunicados 2027-1 con datos exclusivamente sintéticos. */
public class ComunicadosInstitucionales20271Test {

    @Test public void gc19aTresFallidosConservaBloqueoYAgregaMarcadorEn20271() {
        ResultadoEvaluacionCurricularV2DTO resultado = evaluar("2027-1", ud(1L, 3));
        assertEquals(intentos(resultado, 1L).getEstadoLimite(), EstadoLimiteIntentosV2.AGOTADA);
        assertFalse(decision(resultado, 1L).getHabilitada());
        assertTrue(intentos(resultado, 1L).getAviso().contains("AUTORIZACION_INSTITUCIONAL_POSIBLE_2027_1"));
        assertAvisoUnico(resultado);
    }

    @Test public void gc19bCuatroFallidosMantieneLimiteGeneralYMarcador() {
        ResultadoEvaluacionCurricularV2DTO resultado = evaluar("2027-1", ud(2L, 4));
        assertEquals(intentos(resultado, 2L).getEstadoLimite(), EstadoLimiteIntentosV2.AGOTADA);
        assertTrue(intentos(resultado, 2L).getAviso().contains("LIMITE_SUPERADO_HISTORIAL_PREVIO"));
        assertFalse(decision(resultado, 2L).getHabilitada());
        assertAvisoUnico(resultado);
    }

    @Test public void gc19CuartoIntentoEnCursoNoInfiereAutorizacion() {
        UnidadEvaluacionCurricularV2DTO unidad = ud(21L, 3);
        IntentoAcreditacionUnidadV2DTO enCurso = new IntentoAcreditacionUnidadV2DTO();
        enCurso.setIdEjecucionTecnico("I21-4"); enCurso.setPeriodo("2027-1");
        enCurso.setEstado(EstadoIntentoAcreditacionV2.EN_CURSO);
        unidad.setEstado(EstadoUnidadCurricularV2.EN_CURSO);
        unidad.getIntentosAcreditacion().add(enCurso);
        ResultadoEvaluacionCurricularV2DTO resultado = evaluar("2027-1", unidad);
        assertEquals(intentos(resultado, 21L).getEstadoLimite(), EstadoLimiteIntentosV2.AGOTADA);
        assertFalse(decision(resultado, 21L).getHabilitada());
        assertAvisoUnico(resultado);
    }

    @Test public void configuracionExponePeriodoTextoReferenciaYVigenciaDelComunicado() {
        ParametrosCurricularesV2DTO parametros = new ConfiguracionEvaluacionCurricularV2ServiceImpl()
                .obtenerConfiguracion().getParametrosCurriculares();
        assertEquals(parametros.getPeriodoComunicadoInstitucional(), "2027-1");
        assertTrue(parametros.getTextoComunicadoInstitucional().startsWith("Esta simulación aplica las reglas generales."));
        assertNotNull(parametros.getReferenciaComunicadoInstitucional());
        assertNotNull(parametros.getVigenciaComunicadoInstitucional());
    }

    @Test public void gc19cFueraDe20271ConservaRestriccionSinComunicado() {
        ResultadoEvaluacionCurricularV2DTO resultado = evaluar("2027-2", ud(3L, 3));
        assertEquals(intentos(resultado, 3L).getEstadoLimite(), EstadoLimiteIntentosV2.AGOTADA);
        assertFalse(decision(resultado, 3L).getHabilitada());
        assertTrue(resultado.getAvisos().isEmpty());
        assertFalse(intentos(resultado, 3L).getAviso().contains("AUTORIZACION_INSTITUCIONAL_POSIBLE_2027_1"));
    }

    @Test public void gc19dIncorporacionDiferidaSinReferenciaConservaSemestreUnoSinInactividad() {
        ContextoEvaluacionCurricularV2DTO contexto = contexto("2027-1");
        contexto.setAdmitido(Boolean.TRUE);
        contexto.setInscripcionInicialRealizada(Boolean.FALSE);
        contexto.setPeriodoAdmision("2024-1");
        contexto.setSemestreReferencia(Integer.valueOf(1));
        contexto.setLimiteInactividadAlcanzado(Boolean.FALSE);
        ResultadoEvaluacionCurricularV2DTO resultado = new EvaluacionCurricularV2ServiceImpl().evaluarSombra(contexto);
        assertEquals(resultado.getEvaluacionCargaAcademica().getEstadoIncorporacionDiferida(), EstadoIncorporacionDiferidaV2.REQUIERE_REVISION);
        assertEquals(resultado.getEvaluacionCargaAcademica().getSemestreObjetivoRn62(), Integer.valueOf(1));
        assertFalse(contieneTraza(resultado, "RN62_IDENTIFICA_INACTIVIDAD"));
        assertAvisoUnico(resultado);
    }

    @Test public void gc19eSupuestosConcurrentesProducenUnSoloAviso() {
        ContextoEvaluacionCurricularV2DTO contexto = contexto("2027-1");
        contexto.getUnidades().add(ud(4L, 3));
        contexto.setAdmitido(Boolean.TRUE);
        contexto.setInscripcionInicialRealizada(Boolean.FALSE);
        ResultadoEvaluacionCurricularV2DTO resultado = new EvaluacionCurricularV2ServiceImpl().evaluarSombra(contexto);
        assertAvisoUnico(resultado);
    }

    @Test public void gc19fSinRestriccionNoMuestraAviso() {
        ResultadoEvaluacionCurricularV2DTO resultado = evaluar("2027-1", ud(5L, 1));
        assertTrue(resultado.getAvisos().isEmpty());
    }

    @Test public void gc19gComunicadoNoModificaDecisionMotivosNiCarga() {
        ResultadoEvaluacionCurricularV2DTO conComunicado = evaluar("2027-1", ud(6L, 3));
        ResultadoEvaluacionCurricularV2DTO sinComunicado = evaluar("2027-2", ud(6L, 3));
        DecisionUnidadCurricularV2DTO con = decision(conComunicado, 6L);
        DecisionUnidadCurricularV2DTO sin = decision(sinComunicado, 6L);
        assertEquals(con.getHabilitada(), sin.getHabilitada());
        assertEquals(con.getMotivos(), sin.getMotivos());
        assertEquals(conComunicado.getEvaluacionCargaMaxima().getCargaMaximaNormativa(),
                sinComunicado.getEvaluacionCargaMaxima().getCargaMaximaNormativa());
        assertAvisoUnico(conComunicado);
        assertTrue(sinComunicado.getAvisos().isEmpty());
    }

    private ResultadoEvaluacionCurricularV2DTO evaluar(String periodo, UnidadEvaluacionCurricularV2DTO unidad) {
        ContextoEvaluacionCurricularV2DTO contexto = contexto(periodo);
        contexto.getUnidades().add(unidad);
        return new EvaluacionCurricularV2ServiceImpl().evaluarSombra(contexto);
    }

    private ContextoEvaluacionCurricularV2DTO contexto(String periodo) {
        ContextoEvaluacionCurricularV2DTO contexto = new ContextoEvaluacionCurricularV2DTO();
        contexto.setAmbitoRnPe2024_2027("APLICA");
        contexto.setPeriodoNormativo(periodo);
        contexto.setFechaEvaluacion(new Date(0L));
        contexto.setActividadAcademicaComprobada(Boolean.TRUE);
        contexto.setPermanenciaDentroLimiteComprobada(Boolean.TRUE);
        contexto.setSeriacionComprobada(Boolean.TRUE);
        contexto.setEstructuraProgramaEducativoComprobada(Boolean.TRUE);
        contexto.setSancionImpedimentoConocida(Boolean.TRUE);
        contexto.setSancionImpedimentoRegistrada(Boolean.FALSE);
        ParametrosCurricularesV2DTO parametros = new ParametrosCurricularesV2DTO();
        parametros.setPeriodoComunicadoInstitucional("2027-1");
        parametros.setTextoComunicadoInstitucional("Esta simulación aplica las reglas generales. Para el proceso de Inscripción puede aplicar autorizaciones institucionales específicas. La inscripción efectiva puede diferir del resultado que se muestra aquí.");
        parametros.setReferenciaComunicadoInstitucional("Referencia sintética GC-19");
        parametros.setVigenciaComunicadoInstitucional("2027-1");
        contexto.setParametrosCurriculares(parametros);
        return contexto;
    }

    private UnidadEvaluacionCurricularV2DTO ud(Long id, int fallidos) {
        UnidadEvaluacionCurricularV2DTO unidad = new UnidadEvaluacionCurricularV2DTO();
        unidad.setIdPrograma(id); unidad.setClave("UD-" + id); unidad.setTipoUd("Obligatoria");
        unidad.setSemestre(Integer.valueOf(2)); unidad.setEstado(EstadoUnidadCurricularV2.REPROBADA);
        unidad.setOfertada(Boolean.TRUE); unidad.setCupoDisponibleConocido(Boolean.TRUE);
        for (int indice = 1; indice <= fallidos; indice++) {
            IntentoAcreditacionUnidadV2DTO intento = new IntentoAcreditacionUnidadV2DTO();
            intento.setIdEjecucionTecnico("I" + id + "-" + indice); intento.setPeriodo("2026-" + indice);
            intento.setEstado(EstadoIntentoAcreditacionV2.REPROBADO);
            unidad.getIntentosAcreditacion().add(intento);
        }
        return unidad;
    }

    private EvaluacionIntentosUnidadV2DTO intentos(ResultadoEvaluacionCurricularV2DTO resultado, Long id) {
        for (EvaluacionIntentosUnidadV2DTO evaluacion : resultado.getEvaluacionesIntentos())
            if (id.equals(evaluacion.getIdPrograma())) return evaluacion;
        throw new AssertionError("No existe evaluación de intentos para " + id);
    }

    private DecisionUnidadCurricularV2DTO decision(ResultadoEvaluacionCurricularV2DTO resultado, Long id) {
        for (DecisionUnidadCurricularV2DTO decision : resultado.getDecisiones())
            if (id.equals(decision.getIdPrograma())) return decision;
        throw new AssertionError("No existe decisión para " + id);
    }

    private boolean contieneTraza(ResultadoEvaluacionCurricularV2DTO resultado, String resultadoTraza) {
        for (mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.TrazaReglaAplicadaV2DTO traza : resultado.getTrazas())
            if (resultadoTraza.equals(traza.getResultado())) return true;
        return false;
    }

    private void assertAvisoUnico(ResultadoEvaluacionCurricularV2DTO resultado) {
        assertEquals(resultado.getAvisos().size(), 1);
        assertEquals(resultado.getAvisos().get(0).getCodigo(), CodigoAvisoEvaluacionCurricularV2.AVISO_COMUNICADO_2027_1);
        assertNotNull(resultado.getAvisos().get(0).getTexto());
    }
}
