package mx.gob.sedesol.basegestor.gestionescolar.v2;

import static org.testng.Assert.*;
import java.util.Date;
import java.lang.reflect.Method;
import org.testng.annotations.Test;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.*;
import mx.gob.sedesol.basegestor.service.impl.gestionescolar.v2.EvaluacionCurricularV2ServiceImpl;
import mx.gob.sedesol.basegestor.model.repositories.gestionescolar.InscripcionRepository;

/** Casos dorados GC-08, GC-09, GC-11 y GC-15 del Bloque 2. */
public class Bloque2HistorialOportunidadesRn20261106Test {
    @Test public void gc08DistingueUltimaDisponibleUltimaEnCursoAgotadaYBajaAutorizada() {
        assertEstado(ud(1L, intento("a", EstadoIntentoAcreditacionV2.REPROBADO), intento("b", EstadoIntentoAcreditacionV2.REPROBADO)), EstadoLimiteIntentosV2.ULTIMA_DISPONIBLE, 2, 1);
        assertEstado(ud(2L, intento("a", EstadoIntentoAcreditacionV2.REPROBADO), intento("b", EstadoIntentoAcreditacionV2.REPROBADO), intento("c", EstadoIntentoAcreditacionV2.EN_CURSO)), EstadoLimiteIntentosV2.ULTIMA_EN_CURSO, 3, 0);
        assertEstado(ud(3L, intento("a", EstadoIntentoAcreditacionV2.REPROBADO), intento("b", EstadoIntentoAcreditacionV2.REPROBADO), intento("c", EstadoIntentoAcreditacionV2.REPROBADO)), EstadoLimiteIntentosV2.AGOTADA, 3, 0);
        assertEstado(ud(4L, intento("a", EstadoIntentoAcreditacionV2.REPROBADO), intento("b", EstadoIntentoAcreditacionV2.BAJA_PARCIAL)), EstadoLimiteIntentosV2.DISPONIBLE, 1, 2);
    }
    @Test public void gc09NpSinBajaRequiereRevisionYNoConsumeOportunidad() {
        ResultadoEvaluacionCurricularV2DTO r = evaluar(ud(9L, intento("np", EstadoIntentoAcreditacionV2.NO_DETERMINADO)));
        EvaluacionIntentosUnidadV2DTO e = r.getEvaluacionesIntentos().get(0);
        assertEquals(e.getEstadoLimite(), EstadoLimiteIntentosV2.REQUIERE_REVISION);
        assertEquals(e.getOportunidadesConsumidas(), Integer.valueOf(0));
        assertTrue(decision(r, 9L).getMotivos().contains(CodigoMotivoCurricularV2.DATOS_INSUFICIENTES));
    }
    @Test public void gc11AcreditadaNoExponeMejoraNiSeOfrece() {
        ResultadoEvaluacionCurricularV2DTO r = evaluar(ud(11L, intento("ac", EstadoIntentoAcreditacionV2.ACREDITADO)));
        assertEquals(r.getEvaluacionesIntentos().get(0).getEstadoLimite(), EstadoLimiteIntentosV2.ACREDITADA_SIN_OPORTUNIDAD_REQUERIDA);
        assertEquals(r.getOportunidadesMejoraUtilizadas(), null);
        assertTrue(r.getEvaluacionesMejoraPromedio().isEmpty());
    }
    @Test public void gc15NoClasificaElectivaComoCategoriaVigente() {
        UnidadEvaluacionCurricularV2DTO u = ud(15L, intento("r", EstadoIntentoAcreditacionV2.REPROBADO)); u.setTipoUd("ELECTIVA");
        ResultadoEvaluacionCurricularV2DTO r = evaluar(u);
        assertFalse(r.getReglasEvaluadas().contains(CodigoReglaCurricularV2.RN_7_3));
        assertFalse(tieneTraza(r, CodigoReglaCurricularV2.RN_7_3));
    }
    @Test public void c05BajaTemporalNoEsReprobacionNiOportunidad() {
        UnidadEvaluacionCurricularV2DTO u=ud(20L, intento("bt", EstadoIntentoAcreditacionV2.BAJA_TEMPORAL)); u.setEstado(EstadoUnidadCurricularV2.BAJA_TEMPORAL);
        ResultadoEvaluacionCurricularV2DTO r = evaluar(u);
        assertEquals(r.getCantidadReprobadasVigentes(), Integer.valueOf(0));
        assertEquals(r.getEvaluacionesIntentos().get(0).getOportunidadesConsumidas(), Integer.valueOf(0));
    }
    @Test public void bajasMapeadasPorTipoYMotivoNoUsanCategoriaResidual() throws Exception {
        assertEquals(mapearBaja(1, "solicitud").getEstado(), EstadoIntentoAcreditacionV2.BAJA_PARCIAL);
        assertEquals(mapearBaja(1, "SERiACION").getEstado(), EstadoIntentoAcreditacionV2.BAJA_PARCIAL);
        assertEquals(mapearBaja(2, "").getEstado(), EstadoIntentoAcreditacionV2.BAJA_TEMPORAL);
        assertEquals(mapearBaja(7, "").getEstado(), EstadoIntentoAcreditacionV2.BAJA_TEMPORAL);
        assertEquals(mapearBaja(8, "").getEstado(), EstadoIntentoAcreditacionV2.BAJA_TEMPORAL);
        assertTrue(mapearBaja(4, "").getEvidenciaFuente().contains("BAJA_ADMINISTRATIVA_SEGUN_RESOLUCION"));
        assertTrue(mapearBaja(5, "").getEvidenciaFuente().contains("BAJA_ART40_SUBTIPO_NO_DETERMINADO"));
        assertTrue(mapearBaja(3, "").getEvidenciaFuente().contains("BAJA_DEFINITIVA"));
        assertTrue(mapearBaja(6, "").getEvidenciaFuente().contains("BAJA_DEFINITIVA"));
        assertTrue(mapearBaja(9, "").getEvidenciaFuente().contains("NO_MAPEADO_REQUIERE_REVISION"));
    }
    @Test public void gc08cLimiteAgotadoBloqueaUnidadConMotivoExplicito() {
        ResultadoEvaluacionCurricularV2DTO r = evaluar(ud(27L, intento("a", EstadoIntentoAcreditacionV2.REPROBADO),
                intento("b", EstadoIntentoAcreditacionV2.REPROBADO), intento("c", EstadoIntentoAcreditacionV2.REPROBADO)));
        DecisionUnidadCurricularV2DTO d = decision(r, 27L);
        assertTrue(d.getEstadosSombra().contains(EstadoDecisionUnidadCurricularV2.BLOQUEADA));
        assertTrue(d.getMotivos().contains(CodigoMotivoCurricularV2.LIMITE_INTENTOS));
        assertFalse(d.getHabilitada());
    }
    @Test public void extraordinarioHistoricoNoSumaReprobacionNiOportunidad() {
        ResultadoEvaluacionCurricularV2DTO r=evaluar(ud(25L, intento("ordinaria", EstadoIntentoAcreditacionV2.REPROBADO), intento("extra", EstadoIntentoAcreditacionV2.EXTRAORDINARIO_HISTORICO)));
        assertEquals(r.getCantidadReprobadasVigentes(), Integer.valueOf(1));
        assertEquals(r.getEvaluacionesIntentos().get(0).getOportunidadesConsumidas(), Integer.valueOf(1));
        assertEquals(r.getEvaluacionesIntentos().get(0).getEstadoLimite(), EstadoLimiteIntentosV2.REQUIERE_REVISION);
    }
    @Test public void calificacionFueraDeRangoSeReportaComoNpSinBaja() throws Exception {
        IntentoAcreditacionUnidadV2DTO intento=mapearFila(null, 101D);
        assertEquals(intento.getEstado(), EstadoIntentoAcreditacionV2.NO_DETERMINADO);
        assertTrue(intento.getEvidenciaFuente().contains("NP_SIN_BAJA_REQUIERE_REVISION"));
    }
    @Test public void historialV2RelacionaBajaPorIdentidadCurricularSinColumnaNoDisponible() throws Exception {
        Method m=InscripcionRepository.class.getDeclaredMethod("consultaIntentosCurricularesV2"); m.setAccessible(true);
        String sql=(String)m.invoke(new InscripcionRepository());
        assertFalse(sql.contains("baja.id_inscripcion"));
        assertTrue(sql.contains("FROM rel_grupo_participante rgp"));
        assertTrue(sql.contains("baja.id_persona=rgp.id_persona_participante"));
        assertTrue(sql.contains("baja.id_grupo=rgp.id_grupo"));
        assertFalse(sql.contains("groupbase"));
        assertFalse(sql.contains("baja.solicitud"));
        assertTrue(sql.contains("SUBSTRING_INDEX(e.cve_evento_cap, '-', -2)=pi.nombre_periodo"));
        assertFalse(sql.contains("LIKE CONCAT('%',pi.nombre_periodo,'%')"));
        assertTrue(sql.contains("MIN(rgp.id) AS id_participacion_v2"));
        assertTrue(sql.contains("calificacion_conflictiva_v2"));
        assertTrue(sql.contains("baja_sin_confirmar_v2"));
    }
    @Test public void rn72EsPrincipioGeneralDeOptativasReprobadas() {
        UnidadEvaluacionCurricularV2DTO u=ud(26L, intento("opt", EstadoIntentoAcreditacionV2.REPROBADO)); u.setTipoUd("Optativa");
        ResultadoEvaluacionCurricularV2DTO r=evaluar(u);
        assertTrue(r.getReglasEvaluadas().contains(CodigoReglaCurricularV2.RN_7_2));
        assertFalse(r.getReglasEvaluadas().contains(CodigoReglaCurricularV2.RN_7_3));
        assertFalse(tieneTraza(r, CodigoReglaCurricularV2.RN_7_3));
    }
    @Test public void ceroSinBajaEsReprobadaYCeroConBajaEsNpPorBaja() throws Exception {
        assertEquals(mapearFila(null, 0D, null, new Date(System.currentTimeMillis() + 86400000L)).getEstado(), EstadoIntentoAcreditacionV2.REPROBADO);
        assertEquals(mapearFila(Integer.valueOf(1), 0D, "", new Date(System.currentTimeMillis() + 86400000L)).getEstado(), EstadoIntentoAcreditacionV2.BAJA_PARCIAL);
    }
    @Test public void nuloCerradoRequiereRevisionYNuloVigenteEstaEnCurso() throws Exception {
        assertEquals(mapearFila(null, null, null, new Date(0)).getEstado(), EstadoIntentoAcreditacionV2.NO_DETERMINADO);
        assertEquals(mapearFila(null, null, null, new Date(System.currentTimeMillis() + 86400000L)).getEstado(), EstadoIntentoAcreditacionV2.EN_CURSO);
    }
    @Test public void calificacionesConflictoMismoPeriodoRequierenRevision() throws Exception {
        assertEquals(mapearFila(null, 80D, null, new Date(System.currentTimeMillis() + 86400000L), 1).getEstado(), EstadoIntentoAcreditacionV2.NO_DETERMINADO);
    }
    @Test public void bajaSinContabilizarQuedaEnRevision() throws Exception {
        Method m=InscripcionRepository.class.getDeclaredMethod("mapearIntentoCurricularV2", Object[].class); m.setAccessible(true);
        Object[] fila=new Object[]{1L, 1L, "2026-2", 1L, null, null, null, new Date(0), Integer.valueOf(0), Integer.valueOf(1)};
        IntentoAcreditacionUnidadV2DTO intento=(IntentoAcreditacionUnidadV2DTO)m.invoke(new InscripcionRepository(), new Object[]{fila});
        assertEquals(intento.getEstado(), EstadoIntentoAcreditacionV2.NO_DETERMINADO);
        assertTrue(intento.getEvidenciaFuente().contains("BAJA_SIN_CONTABILIZAR"));
    }
    @Test public void eventoSinPeriodoQuedaEnRevision() throws Exception {
        Method m=InscripcionRepository.class.getDeclaredMethod("mapearIntentoCurricularV2", Object[].class); m.setAccessible(true);
        Object[] fila=new Object[]{1L, 1L, null, 1L, 50D, null, null, new Date(0), Integer.valueOf(0), Integer.valueOf(0)};
        IntentoAcreditacionUnidadV2DTO intento=(IntentoAcreditacionUnidadV2DTO)m.invoke(new InscripcionRepository(), new Object[]{fila});
        assertEquals(intento.getEstado(), EstadoIntentoAcreditacionV2.NO_DETERMINADO);
        assertTrue(intento.getEvidenciaFuente().contains("SIN_IDENTIDAD_UD_O_PERIODO"));
    }
    private IntentoAcreditacionUnidadV2DTO mapearBaja(int tipo, String motivo) throws Exception { return mapearFila(Integer.valueOf(tipo), null, motivo, new Date(System.currentTimeMillis() + 86400000L)); }
    private IntentoAcreditacionUnidadV2DTO mapearFila(Integer tipo, Double calificacion) throws Exception { return mapearFila(tipo, calificacion, null, new Date(System.currentTimeMillis() + 86400000L)); }
    private IntentoAcreditacionUnidadV2DTO mapearFila(Integer tipo, Double calificacion, String motivo, Date fechaFin) throws Exception { return mapearFila(tipo, calificacion, motivo, fechaFin, 0); }
    private IntentoAcreditacionUnidadV2DTO mapearFila(Integer tipo, Double calificacion, String motivo, Date fechaFin, int conflicto) throws Exception {
        Method m=InscripcionRepository.class.getDeclaredMethod("mapearIntentoCurricularV2", Object[].class); m.setAccessible(true);
        Object[] fila=new Object[]{1L, 1L, "2026-2", 1L, calificacion, tipo, motivo, fechaFin, Integer.valueOf(conflicto)};
        return (IntentoAcreditacionUnidadV2DTO)m.invoke(new InscripcionRepository(), new Object[]{fila});
    }
    private void assertEstado(UnidadEvaluacionCurricularV2DTO u, EstadoLimiteIntentosV2 esperado, int consumidas, int disponibles) {
        EvaluacionIntentosUnidadV2DTO e=evaluar(u).getEvaluacionesIntentos().get(0);
        assertEquals(e.getEstadoLimite(), esperado); assertEquals(e.getOportunidadesConsumidas(), Integer.valueOf(consumidas)); assertEquals(e.getOportunidadesDisponibles(), Integer.valueOf(disponibles));
    }
    private ResultadoEvaluacionCurricularV2DTO evaluar(UnidadEvaluacionCurricularV2DTO u) { ContextoEvaluacionCurricularV2DTO c=new ContextoEvaluacionCurricularV2DTO(); c.setPeriodoNormativo("2027-2"); c.setAmbitoRnPe2024_2027("APLICA"); c.setFechaEvaluacion(new Date(0)); c.setActividadAcademicaComprobada(Boolean.TRUE); c.setPermanenciaDentroLimiteComprobada(Boolean.TRUE); c.setSeriacionComprobada(Boolean.TRUE); c.setEstructuraProgramaEducativoComprobada(Boolean.TRUE); c.setSancionImpedimentoConocida(Boolean.TRUE); c.setSancionImpedimentoRegistrada(Boolean.FALSE); c.getUnidades().add(u); return new EvaluacionCurricularV2ServiceImpl().evaluarSombra(c); }
    private UnidadEvaluacionCurricularV2DTO ud(Long id, IntentoAcreditacionUnidadV2DTO... intentos) { UnidadEvaluacionCurricularV2DTO u=new UnidadEvaluacionCurricularV2DTO(); u.setIdPrograma(id); u.setClave("UD"+id); u.setTipoUd("Obligatoria"); u.setSemestre(Integer.valueOf(2)); u.setEstado(EstadoUnidadCurricularV2.REPROBADA); u.setOfertada(Boolean.TRUE); u.setCupoDisponibleConocido(Boolean.TRUE); for(IntentoAcreditacionUnidadV2DTO i:intentos)u.getIntentosAcreditacion().add(i); return u; }
    private IntentoAcreditacionUnidadV2DTO intento(String id, EstadoIntentoAcreditacionV2 estado) { IntentoAcreditacionUnidadV2DTO i=new IntentoAcreditacionUnidadV2DTO(); i.setIdEjecucionTecnico(id); i.setEstado(estado); return i; }
    private DecisionUnidadCurricularV2DTO decision(ResultadoEvaluacionCurricularV2DTO r, Long id) { for(DecisionUnidadCurricularV2DTO d:r.getDecisiones()) if(id.equals(d.getIdPrograma()))return d; return null; }
    private boolean tieneTraza(ResultadoEvaluacionCurricularV2DTO r, CodigoReglaCurricularV2 regla) { for(TrazaReglaAplicadaV2DTO t:r.getTrazas()) if(regla == t.getCodigoRegla()) return true; return false; }
}
