package mx.gob.sedesol.basegestor.gestionescolar.v2;

import static org.testng.Assert.*;
import org.testng.annotations.Test;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.*;
import mx.gob.sedesol.basegestor.service.impl.gestionescolar.v2.EvaluacionCurricularV2ServiceImpl;
import mx.gob.sedesol.basegestor.service.impl.gestionescolar.v2.SemestreReferenciaV2ServiceImpl;

/** Casos dorados C-14/C-15: evaluación sombra sin movimientos escolares. */
public class Bloque4TrayectoriaMedidaRn20261106Test {
    private final EvaluacionCurricularV2ServiceImpl servicio = new EvaluacionCurricularV2ServiceImpl();

    @Test public void gc05TresBajasNoConsecutivasSeExcluyenUnaVezYNoReinscriben() {
        ContextoEvaluacionCurricularV2DTO c = bajaTemporal(3);
        ResultadoEvaluacionCurricularV2DTO r = servicio.evaluarSombra(c);
        assertEquals(r.getEvaluacionCargaAcademica().getEstadoReincorporacionRn66(), EstadoReincorporacionBajaTemporalV2.REINCORPORACION_PROCEDE_CON_RUTA_REGULARIZACION);
        assertFalse(Boolean.TRUE.equals(c.getReincorporacionRegistrada()));
        SemestreReferenciaV2DTO referencia = new SemestreReferenciaV2ServiceImpl().calcular("2028-1", 8, periodos(), "2028-01-01");
        int bajasTemporales = 0;
        for (String excluido : referencia.getPeriodosExcluidos()) if (excluido.endsWith(":BAJA_TEMPORAL")) bajasTemporales++;
        assertEquals(bajasTemporales, 3);
        assertTrue(referencia.getPeriodosExcluidos().contains("2025-2:BAJA_TEMPORAL"));
    }

    @Test public void gc05CuartaBajaTemporalAcumuladaEsExcedente() {
        ResultadoEvaluacionCurricularV2DTO r = servicio.evaluarSombra(bajaTemporal(4));
        assertEquals(r.getEvaluacionCargaAcademica().getEstadoReincorporacionRn66(), EstadoReincorporacionBajaTemporalV2.NO_PROCEDE_POR_MAXIMO_BAJAS_TEMPORALES);
        assertTrue(r.getRestricciones().contains(CodigoMotivoCurricularV2.INACTIVIDAD));
    }

    @Test public void gc13MedidaAdministrativaSinVigenciaRequiereRevision() {
        ContextoEvaluacionCurricularV2DTO c = avanceQuinto();
        c.setMedidaAdministrativaRegistrada(Boolean.TRUE); c.setAlcanceMedidaAdministrativa("INSCRIPCION");
        ResultadoEvaluacionCurricularV2DTO r = servicio.evaluarSombra(c);
        assertEquals(r.getEvaluacionCargaAcademica().getEstadoResolucionAvance(), EstadoResolucionAvanceV2.REQUIERE_REVISION);
        assertTrue(r.getRestricciones().contains(CodigoMotivoCurricularV2.MEDIDA_ADMINISTRATIVA_SIN_VIGENCIA));
    }

    @Test public void gc13MedidaConAlcanceYVigenciaNoSeBloqueaPorInferencia() {
        ContextoEvaluacionCurricularV2DTO c = avanceQuinto();
        c.setMedidaAdministrativaRegistrada(Boolean.TRUE); c.setAlcanceMedidaAdministrativa("CONSULTA");
        c.setInicioVigenciaMedidaAdministrativa("2026-01-01"); c.setFinVigenciaMedidaAdministrativa("2026-12-31");
        assertFalse(servicio.evaluarSombra(c).getRestricciones().contains(CodigoMotivoCurricularV2.MEDIDA_ADMINISTRATIVA_SIN_VIGENCIA));
    }

    private ContextoEvaluacionCurricularV2DTO bajaTemporal(int acumuladas) {
        ContextoEvaluacionCurricularV2DTO c = new ContextoEvaluacionCurricularV2DTO();
        c.setBajaTemporalFormalComprobada(Boolean.TRUE); c.setBajaTemporalConcluida(Boolean.TRUE); c.setHaCursadoUnoOMasPeriodos(Boolean.TRUE);
        c.setUltimoPeriodoCursado("2027-2"); c.setPeriodoInicioBajaTemporal("2025-2"); c.setPeriodoFinBajaTemporal("2027-2");
        c.setSemestreEfectivoPrevioBaja(3); c.setSemestreReferencia(4); c.setPeriodoNormativo("2028-1");
        c.setLimitePermanenciaComprobado(Boolean.TRUE); c.setLimitePermanenciaExcedido(Boolean.TRUE);
        c.setCalendarioReincorporacionComprobado(Boolean.TRUE); c.setDentroCalendarioReincorporacion(Boolean.TRUE); c.setPeriodosBajaTemporalTranscurridos(acumuladas);
        ParametrosCurricularesV2DTO p = new ParametrosCurricularesV2DTO(); p.setMaxSemestresBajaTemporal(3); c.setParametrosCurriculares(p);
        c.getUnidades().add(unidad(1L, 3, EstadoUnidadCurricularV2.NO_CURSADA)); return c;
    }

    private ContextoEvaluacionCurricularV2DTO avanceQuinto() {
        ContextoEvaluacionCurricularV2DTO c = new ContextoEvaluacionCurricularV2DTO(); c.setIdPlan(1L); c.setPeriodoNormativo("2027-1"); c.setSemestreReferencia(5); c.setActividadAcademicaComprobada(Boolean.TRUE);
        c.setSancionImpedimentoConocida(Boolean.TRUE); c.setSancionImpedimentoRegistrada(Boolean.FALSE); c.setRestriccionPeNormativaConocida(Boolean.TRUE); c.setRestriccionPeNormativaImpeditiva(Boolean.FALSE); c.setEstructuraProgramaEducativoComprobada(Boolean.TRUE);
        c.getUnidades().add(unidad(1L, 1, EstadoUnidadCurricularV2.REPROBADA)); c.getUnidades().add(unidad(5L, 5, EstadoUnidadCurricularV2.NO_CURSADA)); c.getIdsUnidadesPretendidas().add(5L); return c;
    }

    private UnidadEvaluacionCurricularV2DTO unidad(Long id, int semestre, EstadoUnidadCurricularV2 estado) {
        UnidadEvaluacionCurricularV2DTO u = new UnidadEvaluacionCurricularV2DTO(); u.setIdPrograma(id); u.setClave("UD" + id); u.setTipoUd("Obligatoria"); u.setSemestre(semestre); u.setEstado(estado); u.setOfertada(Boolean.TRUE); u.setGrupoActivoConocido(Boolean.TRUE); u.setCupoDisponibleConocido(Boolean.TRUE);
        if (estado == EstadoUnidadCurricularV2.REPROBADA) { u.setCausa(CausaPendienteUnidadV2.REPROBACION); u.setReprobacionVigenteConocida(Boolean.TRUE); u.setReprobacionPeriodoPrevioConocida(Boolean.TRUE); } else u.setCausa(CausaPendienteUnidadV2.NO_CURSAMIENTO); return u;
    }

    private java.util.List<PeriodoTrayectoriaV2DTO> periodos() { java.util.List<PeriodoTrayectoriaV2DTO> r = new java.util.ArrayList<PeriodoTrayectoriaV2DTO>(); r.add(periodo("2025-1", false)); r.add(periodo("2025-2", true)); r.add(periodo("2026-1", false)); r.add(periodo("2026-2", true)); r.add(periodo("2027-1", false)); r.add(periodo("2027-2", true)); return r; }
    private PeriodoTrayectoriaV2DTO periodo(String valor, boolean temporal) { PeriodoTrayectoriaV2DTO p = new PeriodoTrayectoriaV2DTO(); p.setPeriodo(valor); p.setInscripcionFormal(Boolean.valueOf(!temporal)); p.setBajaTemporalCompleta(Boolean.valueOf(temporal)); return p; }
}
