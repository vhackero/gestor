package mx.gob.sedesol.basegestor.gestionescolar.v2;

import static org.testng.Assert.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.testng.annotations.Test;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.*;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.AsistenteInscripcionContextoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.UnidadDecisionInscripcionDTO;
import mx.gob.sedesol.basegestor.service.impl.gestionescolar.v2.ContextoEvaluacionCurricularV2AdapterImpl;
import mx.gob.sedesol.basegestor.service.impl.gestionescolar.v2.AmbitoReglasCurricularesV2ServiceImpl;
import mx.gob.sedesol.basegestor.service.impl.gestionescolar.v2.ConfiguracionEvaluacionCurricularV2ServiceImpl;
import mx.gob.sedesol.basegestor.service.impl.gestionescolar.v2.EvaluacionCurricularV2ServiceImpl;
import mx.gob.sedesol.basegestor.service.impl.gestionescolar.v2.SemestreReferenciaV2ServiceImpl;

/** Casos dorados GC-04, GC-05 y GC-12 del Bloque 1. */
public class Bloque1FundamentosRn20261105Test {
    @Test public void c01UniversoMapaYcandidatasSeConservanSeparados() {
        AsistenteInscripcionContextoDTO origen = new AsistenteInscripcionContextoDTO(); origen.setPeriodoActivo("2027-2");
        List<UnidadDecisionInscripcionDTO> mapa = new ArrayList<UnidadDecisionInscripcionDTO>();
        mapa.add(unidadOrigen(1L, 3, false)); mapa.add(unidadOrigen(2L, 5, true));
        origen.setUnidadesMapaPlan(mapa);
        List<UnidadDecisionInscripcionDTO> candidatas = new ArrayList<UnidadDecisionInscripcionDTO>(); candidatas.add(unidadOrigen(2L, 5, true)); origen.setUnidadesCandidatasPeriodo(candidatas);
        ContextoEvaluacionCurricularV2DTO contexto = new ContextoEvaluacionCurricularV2AdapterImpl().construirDesdeAsistente(origen);
        assertEquals(contexto.getUnidades().size(), 2);
        assertEquals(contexto.getIdsUnidadesCandidatas().size(), 1);
        assertEquals(contexto.getIdsUnidadesCandidatas().get(0), Long.valueOf(2L));
        assertTrue(contexto.getUniversoMapaCompleto());
    }
    @Test public void gc05ReferenciaExcluyeBajaTemporalYSinInscripcion() {
        List<PeriodoTrayectoriaV2DTO> periodos = new ArrayList<PeriodoTrayectoriaV2DTO>();
        periodos.add(periodo("2025-1", true, false));
        periodos.add(periodo("2025-2", true, false));
        periodos.add(periodo("2026-1", true, true));
        periodos.add(periodo("2026-2", false, false));
        periodos.add(periodo("2027-1", true, false));
        SemestreReferenciaV2DTO resultado = new SemestreReferenciaV2ServiceImpl().calcular("2027-2", Integer.valueOf(8), periodos, "2027-2");
        assertEquals(resultado.getValor(), Integer.valueOf(4));
        assertTrue(resultado.getPeriodosContados().contains("2025-1"));
        assertTrue(resultado.getPeriodosContados().contains("2025-2"));
        assertTrue(resultado.getPeriodosContados().contains("2027-1"));
        assertTrue(resultado.getPeriodosExcluidos().contains("2026-1:BAJA_TEMPORAL"));
        assertTrue(resultado.getPeriodosExcluidos().contains("2026-2:SIN_INSCRIPCION"));
    }

    @Test public void gc05InscribirUnaUdQuintoNoAlteraReferencia() {
        List<PeriodoTrayectoriaV2DTO> periodos = new ArrayList<PeriodoTrayectoriaV2DTO>();
        periodos.add(periodo("2025-1", true, false)); periodos.add(periodo("2025-2", true, false));
        periodos.add(periodo("2027-1", true, false));
        SemestreReferenciaV2DTO resultado = new SemestreReferenciaV2ServiceImpl().calcular("2027-2", Integer.valueOf(8), periodos, null);
        assertEquals(resultado.getValor(), Integer.valueOf(4));
    }

    @Test public void c02DistingueAusenciaDeInscripcionYRegistroSinEvidencia() {
        SemestreReferenciaV2ServiceImpl servicio = new SemestreReferenciaV2ServiceImpl();
        SemestreReferenciaV2DTO sinInscripcion = servicio.calcular("2027-2", Integer.valueOf(8),
                new ArrayList<PeriodoTrayectoriaV2DTO>(), null);
        assertTrue(sinInscripcion.getPeriodosExcluidos().contains("SIN_INSCRIPCION"));
        List<PeriodoTrayectoriaV2DTO> incompleto = new ArrayList<PeriodoTrayectoriaV2DTO>();
        PeriodoTrayectoriaV2DTO registroSinEvidencia=periodo("2027-1", true, false); registroSinEvidencia.setParticipacionGrupo(Boolean.FALSE); incompleto.add(registroSinEvidencia);
        SemestreReferenciaV2DTO sinEvidencia = servicio.calcular("2027-2", Integer.valueOf(8), incompleto, null);
        assertTrue(sinEvidencia.getPeriodosExcluidos().contains("2027-1:INSCRIPCION_SIN_EVIDENCIA_DE_CURSAMIENTO"));
    }

    @Test public void c02NingunaReglaUsaSemestreHeredadoNiMaximoCandidatas() throws Exception {
        java.io.InputStream entrada = EvaluacionCurricularV2ServiceImpl.class
                .getResourceAsStream("EvaluacionCurricularV2ServiceImpl.class");
        assertNotNull(entrada, "El evaluador debe estar disponible para la comprobacion arquitectonica");
        entrada.close();
        String fuente = leerFuenteEvaluador();
        assertFalse(fuente.contains("getSemestreObjetivo()"));
        assertFalse(fuente.contains("getSemestreMaximoCandidatas()"));
    }

    @Test public void gc04NuncaCursadaNoOfertadaPermaneceEnUniversoYNoCuentaComoReprobada() {
        ContextoEvaluacionCurricularV2DTO contexto = base();
        UnidadEvaluacionCurricularV2DTO nuncaCursada = unidad(33L, 4, EstadoUnidadCurricularV2.NO_CURSADA, false);
        contexto.getUnidades().add(nuncaCursada);
        UnidadEvaluacionCurricularV2DTO quintoNoDependiente = unidad(51L, 5, EstadoUnidadCurricularV2.NO_INSCRITA, true);
        UnidadEvaluacionCurricularV2DTO quintoDependiente = unidad(53L, 5, EstadoUnidadCurricularV2.NO_INSCRITA, true);
        RelacionSeriacionCurricularV2DTO relacion = new RelacionSeriacionCurricularV2DTO();
        relacion.setIdAntecedente(33L); relacion.setIdConsecuente(53L); relacion.setTipoRelacion(TipoRelacionSeriacionV2.DIRECTA); relacion.setTipoSeriacion(TipoSeriacionV2.INTERSEMESTRAL); relacion.setSemestreAntecedente(Integer.valueOf(4)); relacion.setSemestreConsecuente(Integer.valueOf(5));
        quintoDependiente.getRelacionesSeriacion().add(relacion);
        contexto.getUnidades().add(quintoNoDependiente); contexto.getUnidades().add(quintoDependiente);
        contexto.getIdsUnidadesCandidatas().add(51L); contexto.getIdsUnidadesCandidatas().add(53L);
        ResultadoEvaluacionCurricularV2DTO resultado = new EvaluacionCurricularV2ServiceImpl().evaluarSombra(contexto);
        assertEquals(resultado.getTotalReprobadas(), Integer.valueOf(0));
        DecisionUnidadCurricularV2DTO ob33 = decision(resultado, 33L);
        DecisionUnidadCurricularV2DTO ob51 = decision(resultado, 51L);
        DecisionUnidadCurricularV2DTO ob53 = decision(resultado, 53L);
        assertTrue(ob33.getEstadosSombra().contains(EstadoDecisionUnidadCurricularV2.PRIORITARIA));
        assertFalse(ob33.getEstadosSombra().contains(EstadoDecisionUnidadCurricularV2.HABILITADA));
        assertTrue(ob33.getMotivos().contains(CodigoMotivoCurricularV2.SIN_OFERTA));
        assertTrue(ob51.getEstadosSombra().contains(EstadoDecisionUnidadCurricularV2.HABILITADA));
        assertTrue(ob53.getEstadosSombra().contains(EstadoDecisionUnidadCurricularV2.BLOQUEADA));
        assertTrue(ob53.getMotivos().contains(CodigoMotivoCurricularV2.SERIACION_DIRECTA));
        assertTrue(resultado.getEvaluacionCargaAcademica().getCtr01Rn61Procedente());
    }

    @Test public void gc12PlanFueraDeAmbitoNoEvaluaReglasPe2024_2027() {
        assertEquals(new AmbitoReglasCurricularesV2ServiceImpl().resolver("612301", Boolean.FALSE), "NO_APLICA_RN_PE2024_2027");
        ContextoEvaluacionCurricularV2DTO contexto = base(); contexto.setAmbitoRnPe2024_2027("NO_APLICA_RN_PE2024_2027");
        ResultadoEvaluacionCurricularV2DTO resultado = new EvaluacionCurricularV2ServiceImpl().evaluarSombra(contexto);
        assertEquals(resultado.getSituacionAcademicaCalculada(), "REQUIERE_REVISION");
        assertFalse(resultado.getReglasEvaluadas().contains(CodigoReglaCurricularV2.RN_6_1));
    }

    @Test public void c03VersionYParametrosSonConfigurables() {
        String anterior = System.getProperty("CTR01_PERIODO_INICIO");
        try {
            System.setProperty("CTR01_PERIODO_INICIO", "2028-1");
            ConfiguracionEvaluacionCurricularV2DTO configuracion = new ConfiguracionEvaluacionCurricularV2ServiceImpl().obtenerConfiguracion();
            assertEquals(configuracion.getVersionReglas(), "RN-PE2024-2027-act-20261105");
            assertEquals(configuracion.getParametrosCurriculares().getCtr01PeriodoInicio(), "2028-1");
            assertEquals(configuracion.getParametrosCurriculares().getCargaMaxIrregular(), Integer.valueOf(6));
        } finally { if (anterior == null) System.clearProperty("CTR01_PERIODO_INICIO"); else System.setProperty("CTR01_PERIODO_INICIO", anterior); }
    }

    private PeriodoTrayectoriaV2DTO periodo(String valor, boolean formal, boolean bajaTemporal) { PeriodoTrayectoriaV2DTO p = new PeriodoTrayectoriaV2DTO(); p.setPeriodo(valor); p.setInscripcionFormal(Boolean.valueOf(formal)); p.setParticipacionGrupo(Boolean.valueOf(formal)); p.setBajaTemporalCompleta(Boolean.valueOf(bajaTemporal)); return p; }
    private ContextoEvaluacionCurricularV2DTO base() { ContextoEvaluacionCurricularV2DTO c = new ContextoEvaluacionCurricularV2DTO(); c.setPeriodoNormativo("2027-2"); c.setSemestreReferencia(Integer.valueOf(5)); c.setAmbitoRnPe2024_2027("APLICA"); c.setFechaEvaluacion(new Date(0)); c.setActividadAcademicaComprobada(Boolean.TRUE); c.setPermanenciaDentroLimiteComprobada(Boolean.TRUE); c.setSeriacionComprobada(Boolean.TRUE); c.setEstructuraProgramaEducativoComprobada(Boolean.TRUE); c.setSancionImpedimentoConocida(Boolean.TRUE); c.setSancionImpedimentoRegistrada(Boolean.FALSE); return c; }
    private UnidadEvaluacionCurricularV2DTO unidad(Long id, int semestre, EstadoUnidadCurricularV2 estado, boolean oferta) { UnidadEvaluacionCurricularV2DTO u = new UnidadEvaluacionCurricularV2DTO(); u.setIdPrograma(id); u.setClave("OB" + id); u.setTipoUd("Obligatoria"); u.setSemestre(Integer.valueOf(semestre)); u.setEstado(estado); u.setOfertada(Boolean.valueOf(oferta)); u.setCupoDisponibleConocido(Boolean.TRUE); u.setSeleccionableOrigen(Boolean.valueOf(oferta)); return u; }
    private UnidadDecisionInscripcionDTO unidadOrigen(Long id, int semestre, boolean oferta) { UnidadDecisionInscripcionDTO u = new UnidadDecisionInscripcionDTO(); u.setUdId(id); u.setClave("OB" + id); u.setNombre("Unidad " + id); u.setTipoUd("Obligatoria"); u.setSemestre(Integer.valueOf(semestre)); u.setOfertada(Boolean.valueOf(oferta)); u.setSeleccionable(Boolean.valueOf(oferta)); u.setEstatusHistorico("NO INSCRITA"); return u; }
    private DecisionUnidadCurricularV2DTO decision(ResultadoEvaluacionCurricularV2DTO resultado, Long id) { for (DecisionUnidadCurricularV2DTO actual : resultado.getDecisiones()) if (id.equals(actual.getIdPrograma())) return actual; fail("No se obtuvo decisión para " + id); return null; }
    private String leerFuenteEvaluador() throws Exception { java.io.File archivo = new java.io.File("src/main/java/mx/gob/sedesol/basegestor/service/impl/gestionescolar/v2/EvaluacionCurricularV2ServiceImpl.java"); java.io.BufferedReader lector = new java.io.BufferedReader(new java.io.FileReader(archivo)); StringBuilder texto = new StringBuilder(); String linea; while ((linea = lector.readLine()) != null) texto.append(linea).append('\n'); lector.close(); return texto.toString(); }
}
