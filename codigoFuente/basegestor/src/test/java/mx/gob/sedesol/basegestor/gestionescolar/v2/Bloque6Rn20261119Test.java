package mx.gob.sedesol.basegestor.gestionescolar.v2;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.Date;

import org.testng.annotations.Test;

import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.CausaPendienteUnidadV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.ContextoEvaluacionCurricularV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EstadoIntentoAcreditacionV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EstadoUnidadCurricularV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.IntentoAcreditacionUnidadV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.ParametrosCurricularesV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.ResultadoEvaluacionCurricularV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.UnidadEvaluacionCurricularV2DTO;
import mx.gob.sedesol.basegestor.service.impl.gestionescolar.v2.EvaluacionCurricularV2ServiceImpl;

/** Casos sintéticos de RN 20261119; no dependen de expedientes reales. */
public class Bloque6Rn20261119Test {
    @Test public void rn14SeparaRecursamientosDeOportunidades() {
        UnidadEvaluacionCurricularV2DTO u = unidad(1L);
        u.getIntentosAcreditacion().add(intento("I1", EstadoIntentoAcreditacionV2.REPROBADO));
        u.getIntentosAcreditacion().add(intento("I2", EstadoIntentoAcreditacionV2.EN_CURSO));
        ResultadoEvaluacionCurricularV2DTO r = evaluar(u);
        assertEquals(r.getEvaluacionesIntentos().get(0).getOportunidadesConsumidas(), Integer.valueOf(2));
        assertEquals(r.getEvaluacionesIntentos().get(0).getRecursamientos(), Integer.valueOf(1));
    }

    @Test public void rn22CuatroAdeudosConfirmadosNoSeAnulanPorUnResultadoEnRevision() {
        ContextoEvaluacionCurricularV2DTO c = contexto();
        for (long id = 1; id <= 4; id++) {
            UnidadEvaluacionCurricularV2DTO u = unidad(id);
            u.getIntentosAcreditacion().add(intento("R" + id, EstadoIntentoAcreditacionV2.REPROBADO));
            c.getUnidades().add(u);
        }
        UnidadEvaluacionCurricularV2DTO incierta = unidad(9L);
        incierta.getIntentosAcreditacion().add(intento("REV", EstadoIntentoAcreditacionV2.NO_DETERMINADO));
        c.getUnidades().add(incierta);
        assertTrue(new EvaluacionCurricularV2ServiceImpl().evaluarSombra(c).getEvaluacionCargaAcademica().getAplicaRn64());
    }

    @Test public void rn711ExponeSustitucionesSoloCuandoLaFuenteLasAcredita() {
        UnidadEvaluacionCurricularV2DTO u = unidad(10L);
        u.setIdAdeudoOptativaOriginal(Long.valueOf(8L));
        u.setSustitucionesUtilizadas(Integer.valueOf(1));
        u.getIntentosAcreditacion().add(intento("S1", EstadoIntentoAcreditacionV2.REPROBADO));
        ResultadoEvaluacionCurricularV2DTO r = evaluar(u);
        assertEquals(r.getEvaluacionesIntentos().get(0).getSustitucionesUtilizadas(), Integer.valueOf(1));
    }

    @Test public void rn711ConservaSustitucionFormalAnteBajaPosteriorYRecursamiento() {
        UnidadEvaluacionCurricularV2DTO sustitutaConBaja = unidad(11L);
        sustitutaConBaja.setIdAdeudoOptativaOriginal(Long.valueOf(8L));
        sustitutaConBaja.setSustitucionesUtilizadas(Integer.valueOf(1));
        sustitutaConBaja.setEstado(EstadoUnidadCurricularV2.BAJA_PARCIAL);
        sustitutaConBaja.setCausa(CausaPendienteUnidadV2.BAJA_PARCIAL);
        sustitutaConBaja.getIntentosAcreditacion().add(intento("S-BAJA", EstadoIntentoAcreditacionV2.BAJA_PARCIAL));
        assertEquals(evaluar(sustitutaConBaja).getEvaluacionesIntentos().get(0).getSustitucionesUtilizadas(), Integer.valueOf(1));

        UnidadEvaluacionCurricularV2DTO sustitutaRecursada = unidad(12L);
        sustitutaRecursada.setIdAdeudoOptativaOriginal(Long.valueOf(8L));
        sustitutaRecursada.setSustitucionesUtilizadas(Integer.valueOf(1));
        sustitutaRecursada.getIntentosAcreditacion().add(intento("S-1", EstadoIntentoAcreditacionV2.REPROBADO));
        sustitutaRecursada.getIntentosAcreditacion().add(intento("S-2", EstadoIntentoAcreditacionV2.EN_CURSO));
        ResultadoEvaluacionCurricularV2DTO resultado = evaluar(sustitutaRecursada);
        assertEquals(resultado.getEvaluacionesIntentos().get(0).getSustitucionesUtilizadas(), Integer.valueOf(1));
        assertEquals(resultado.getEvaluacionesIntentos().get(0).getRecursamientos(), Integer.valueOf(1));
    }

    private ResultadoEvaluacionCurricularV2DTO evaluar(UnidadEvaluacionCurricularV2DTO u) { ContextoEvaluacionCurricularV2DTO c = contexto(); c.getUnidades().add(u); return new EvaluacionCurricularV2ServiceImpl().evaluarSombra(c); }
    private ContextoEvaluacionCurricularV2DTO contexto() { ContextoEvaluacionCurricularV2DTO c = new ContextoEvaluacionCurricularV2DTO(); c.setAmbitoRnPe2024_2027("APLICA"); c.setPeriodoNormativo("2027-1"); c.setSemestreReferencia(Integer.valueOf(4)); c.setFechaEvaluacion(new Date(0L)); c.setActividadAcademicaComprobada(Boolean.TRUE); c.setPermanenciaDentroLimiteComprobada(Boolean.TRUE); c.setEstructuraProgramaEducativoComprobada(Boolean.TRUE); c.setSancionImpedimentoConocida(Boolean.TRUE); c.setSancionImpedimentoRegistrada(Boolean.FALSE); c.setRestriccionPeNormativaConocida(Boolean.TRUE); c.setRestriccionPeNormativaImpeditiva(Boolean.FALSE); ParametrosCurricularesV2DTO p = new ParametrosCurricularesV2DTO(); p.setUmbralRezagoSevero(Integer.valueOf(4)); c.setParametrosCurriculares(p); return c; }
    private UnidadEvaluacionCurricularV2DTO unidad(Long id) { UnidadEvaluacionCurricularV2DTO u = new UnidadEvaluacionCurricularV2DTO(); u.setIdPrograma(id); u.setClave("UD" + id); u.setTipoUd("Obligatoria"); u.setSemestre(Integer.valueOf(2)); u.setBloque("B1"); u.setEstado(EstadoUnidadCurricularV2.REPROBADA); u.setCausa(CausaPendienteUnidadV2.REPROBACION); u.setOfertada(Boolean.TRUE); u.setSeleccionableOrigen(Boolean.TRUE); return u; }
    private IntentoAcreditacionUnidadV2DTO intento(String id, EstadoIntentoAcreditacionV2 estado) { IntentoAcreditacionUnidadV2DTO i = new IntentoAcreditacionUnidadV2DTO(); i.setIdEjecucionTecnico(id); i.setPeriodo("2026-1"); i.setEstado(estado); return i; }
}
