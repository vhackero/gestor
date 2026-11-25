package mx.gob.sedesol.basegestor.gestionescolar.v2;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.CausaPendienteUnidadV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.CodigoReglaCurricularV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.CondicionCurricularV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.ContextoEvaluacionCurricularV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EstadoActividadAcademicaV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EstadoUnidadCurricularV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.ResultadoEvaluacionCurricularV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.UnidadEvaluacionCurricularV2DTO;
import mx.gob.sedesol.basegestor.service.impl.gestionescolar.v2.EvaluacionCurricularV2ServiceImpl;

/** F2A-U-01 a F2A-U-14: RN-1.1 en modo sombra, sin efectos productivos. */
public class EvaluacionCurricularV2Fase2ATest {
    private final EvaluacionCurricularV2ServiceImpl service = new EvaluacionCurricularV2ServiceImpl();

    @Test public void f2AU01EstudianteActivo() { ContextoEvaluacionCurricularV2DTO c=contexto(); c.setActividadAcademicaComprobada(Boolean.TRUE); assertEstado(c, EstadoActividadAcademicaV2.ACTIVO); }
    @Test public void f2AU02BajaTemporalVigenteUnPeriodo() { ContextoEvaluacionCurricularV2DTO c=bajaTemporal(2,1); assertEstado(c, EstadoActividadAcademicaV2.BAJA_TEMPORAL_VIGENTE); }
    @Test public void f2AU03BajaTemporalRenovada() { ContextoEvaluacionCurricularV2DTO c=bajaTemporal(3,2); assertEstado(c, EstadoActividadAcademicaV2.BAJA_TEMPORAL_VIGENTE); }
    @Test public void f2AU04MaximoPeriodosBajaTemporal() { ContextoEvaluacionCurricularV2DTO c=bajaTemporal(3,3); c.setLimiteInactividadAlcanzado(Boolean.TRUE); assertEstado(c, EstadoActividadAcademicaV2.LIMITE_INACTIVIDAD_ALCANZADO); }
    @Test public void f2AU05BajaTemporalConcluidaConReincorporacion() { ContextoEvaluacionCurricularV2DTO c=bajaTemporal(1,1); c.setReincorporacionRegistrada(Boolean.TRUE); assertEstado(c, EstadoActividadAcademicaV2.ACTIVO); }
    @Test public void f2AU06BajaTemporalConcluidaSinReincorporacion() { assertEstado(bajaTemporal(1,1), EstadoActividadAcademicaV2.REINCORPORACION_PENDIENTE); }
    @Test public void f2AU07SinInscripcionSinEvidenciaSuficiente() { assertEstado(contexto(), EstadoActividadAcademicaV2.NO_DETERMINADO); }
    @Test public void f2AU08RegularConBajaTemporal() { ContextoEvaluacionCurricularV2DTO c=bajaTemporal(2,1); ResultadoEvaluacionCurricularV2DTO r=service.evaluarSombra(c); assertEquals(r.getSituacionAcademica(),"REGULAR"); assertEquals(r.getEstadoActividadAcademica(),EstadoActividadAcademicaV2.BAJA_TEMPORAL_VIGENTE); }
    @Test public void f2AU09IrregularConBajaTemporal() { ContextoEvaluacionCurricularV2DTO c=bajaTemporal(2,1); c.getUnidades().add(unidad(1L,EstadoUnidadCurricularV2.REPROBADA)); ResultadoEvaluacionCurricularV2DTO r=service.evaluarSombra(c); assertEquals(r.getSituacionAcademica(),"IRREGULAR"); assertEquals(r.getEstadoActividadAcademica(),EstadoActividadAcademicaV2.BAJA_TEMPORAL_VIGENTE); }
    @Test public void f2AU10BajaParcialNoEsInactividadTotal() { ContextoEvaluacionCurricularV2DTO c=contexto(); c.setBajaParcialRegistrada(Boolean.TRUE); assertEstado(c, EstadoActividadAcademicaV2.NO_DETERMINADO); }
    @Test public void f2AU11NoCursamientoUdNoEsInactividad() { ContextoEvaluacionCurricularV2DTO c=contexto(); c.getUnidades().add(unidad(2L,EstadoUnidadCurricularV2.NO_CURSADA)); ResultadoEvaluacionCurricularV2DTO r=service.evaluarSombra(c); assertEquals(r.getEstadoActividadAcademica(),EstadoActividadAcademicaV2.NO_DETERMINADO); assertFalse(r.getCondicionesDetectadas().contains(CondicionCurricularV2.INACTIVIDAD_ACADEMICA)); }
    @Test public void f2AU12DatosContradictoriosNoSeInfieren() { ContextoEvaluacionCurricularV2DTO c=bajaTemporal(2,1); c.setDatosActividadContradictorios(Boolean.TRUE); assertEstado(c, EstadoActividadAcademicaV2.NO_DETERMINADO); }
    @Test public void f2AU13MismoContextoMismoResultado() { ContextoEvaluacionCurricularV2DTO c=bajaTemporal(2,1); ResultadoEvaluacionCurricularV2DTO a=service.evaluarSombra(c), b=service.evaluarSombra(c); assertEquals(a.getEstadoActividadAcademica(),b.getEstadoActividadAcademica()); assertEquals(a.getTrazas().get(0).getResultado(),b.getTrazas().get(0).getResultado()); }
    @Test public void f2AU14NoHayCambioProductivo() { ResultadoEvaluacionCurricularV2DTO r=service.evaluarSombra(bajaTemporal(2,1)); assertFalse(r.getEfectosProductivosAplicados()); assertTrue(r.getReglasEvaluadas().contains(CodigoReglaCurricularV2.RN_1_1)); }

    private void assertEstado(ContextoEvaluacionCurricularV2DTO contexto, EstadoActividadAcademicaV2 esperado) { assertEquals(service.evaluarSombra(contexto).getEstadoActividadAcademica(), esperado); }
    private ContextoEvaluacionCurricularV2DTO bajaTemporal(int autorizados, int transcurridos) { ContextoEvaluacionCurricularV2DTO c=contexto(); c.setBajaTemporalTotalRegistrada(Boolean.TRUE); c.setPeriodosBajaTemporalAutorizados(Integer.valueOf(autorizados)); c.setPeriodosBajaTemporalTranscurridos(Integer.valueOf(transcurridos)); c.getEvidenciasActividad().add("BAJA_TEMPORAL_TOTAL_AUTORIZADA"); c.getPeriodosActividadConsiderados().add("P-01"); return c; }
    private ContextoEvaluacionCurricularV2DTO contexto() { ContextoEvaluacionCurricularV2DTO c=new ContextoEvaluacionCurricularV2DTO(); c.setVersionReglas("PE2027-V1"); c.setFechaEvaluacion(new java.util.Date(0L)); return c; }
    private UnidadEvaluacionCurricularV2DTO unidad(Long id, EstadoUnidadCurricularV2 estado) { UnidadEvaluacionCurricularV2DTO u=new UnidadEvaluacionCurricularV2DTO(); u.setIdPrograma(id); u.setClave("UD"+id); u.setEstado(estado); u.setCausa(estado==EstadoUnidadCurricularV2.REPROBADA?CausaPendienteUnidadV2.REPROBACION:CausaPendienteUnidadV2.NO_CURSAMIENTO); u.setOfertada(Boolean.FALSE); return u; }
}
