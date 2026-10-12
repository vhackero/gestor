package mx.gob.sedesol.basegestor.gestionescolar.v2;

import static org.testng.Assert.*;

import org.testng.annotations.Test;

import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.ContextoEvaluacionCurricularV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EstadoRequisitoComponenteFinalV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EstadoRequisitosEgresoAcademicoV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EstadoUnidadCurricularV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EvaluacionCargaAcademicaV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.RelacionSeriacionCurricularV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.TipoComponenteCurricularV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.TipoRelacionSeriacionV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.TipoSeriacionV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.UnidadEvaluacionCurricularV2DTO;
import mx.gob.sedesol.basegestor.service.impl.gestionescolar.v2.EvaluacionCurricularV2ServiceImpl;

/** RN-9.4: requisitos académicos de egreso y componentes finales, sólo en sombra. */
public class EvaluacionCurricularV2Fase9CTest {
    private final EvaluacionCurricularV2ServiceImpl servicio = new EvaluacionCurricularV2ServiceImpl();

    @Test public void f9CU01TodasLasCategoriasCumplenSinAutorizarEgreso() {
        EvaluacionCargaAcademicaV2DTO carga = carga(contextoCompleto());
        assertEquals(carga.getEstadoRequisitosEgresoAcademicoRn94(), EstadoRequisitosEgresoAcademicoV2.REQUISITOS_ACADEMICOS_EGRESO_CUMPLIDOS);
        assertFalse(Boolean.TRUE.equals(servicio.evaluarSombra(contextoCompleto()).getEfectosProductivosAplicados()));
    }

    @Test public void f9CU02ObligatoriaPendienteImpideTotalidadSinReclasificarla() {
        ContextoEvaluacionCurricularV2DTO c = contextoCompleto(); c.getUnidades().get(0).setEstado(EstadoUnidadCurricularV2.NO_CURSADA);
        EvaluacionCargaAcademicaV2DTO carga = carga(c); assertEquals(carga.getEstadoRequisitosEgresoAcademicoRn94(), EstadoRequisitosEgresoAcademicoV2.REQUISITOS_ACADEMICOS_EGRESO_PENDIENTES); assertTrue(carga.getObligatoriasPendientesRn94().contains(1L));
    }

    @Test public void f9CU03FronteraOptativasSieteOchoYNueve() {
        assertEquals(carga(contextoConOptativas(7)).getEstadoRequisitosEgresoAcademicoRn94(), EstadoRequisitosEgresoAcademicoV2.REQUISITOS_ACADEMICOS_EGRESO_PENDIENTES);
        assertEquals(carga(contextoConOptativas(8)).getEstadoRequisitosEgresoAcademicoRn94(), EstadoRequisitosEgresoAcademicoV2.REQUISITOS_ACADEMICOS_EGRESO_CUMPLIDOS);
        assertEquals(carga(contextoConOptativas(9)).getEstadoRequisitosEgresoAcademicoRn94(), EstadoRequisitosEgresoAcademicoV2.REQUISITOS_ACADEMICOS_EGRESO_CUMPLIDOS);
    }

    @Test public void f9CU04ElectivaNoSeConfundeConOptativaYUnaBasta() {
        ContextoEvaluacionCurricularV2DTO c = contextoCompleto(); c.getUnidades().remove(c.getUnidades().size() - 1);
        assertEquals(carga(c).getEstadoRequisitosEgresoAcademicoRn94(), EstadoRequisitosEgresoAcademicoV2.REQUISITOS_ACADEMICOS_EGRESO_PENDIENTES);
        c.getUnidades().add(unidad(99L, "Electiva", 5, EstadoUnidadCurricularV2.ACREDITADA));
        assertEquals(carga(c).getElectivasAcreditadasRn94(), Integer.valueOf(1));
    }

    @Test public void f9CU05AcreditadaDuplicadaCuentaUnaVezYEstadoVigentePrevalece() {
        ContextoEvaluacionCurricularV2DTO c = contextoCompleto(); UnidadEvaluacionCurricularV2DTO duplicada = unidad(2L, "Optativa", 2, EstadoUnidadCurricularV2.ACREDITADA); duplicada.getReferenciasHistoricas().add("REPROBADA_HISTORICA"); c.getUnidades().add(duplicada);
        EvaluacionCargaAcademicaV2DTO carga = carga(c); assertEquals(carga.getCantidadOptativasAcreditadasRn711(), Integer.valueOf(8)); assertEquals(carga.getEstadoRequisitosEgresoAcademicoRn94(), EstadoRequisitosEgresoAcademicoV2.REQUISITOS_ACADEMICOS_EGRESO_CUMPLIDOS);
    }

    @Test public void f9CU06CatalogoIncompletoYTipoAmbiguoRequierenRevision() {
        ContextoEvaluacionCurricularV2DTO c = contextoCompleto(); c.setEstructuraProgramaEducativoComprobada(Boolean.FALSE); assertTrue(Boolean.TRUE.equals(carga(c).getRequiereRevisionRn94()));
        ContextoEvaluacionCurricularV2DTO ambiguo = contextoCompleto(); ambiguo.getUnidades().get(1).setTipoUd("Optativa Electiva"); assertTrue(Boolean.TRUE.equals(carga(ambiguo).getRequiereRevisionRn94()));
    }

    @Test public void f9CU07ProyectoTerminalRequiereSoloSeriadasS1AS6() {
        ContextoEvaluacionCurricularV2DTO c = contextoCompleto(); UnidadEvaluacionCurricularV2DTO seriada = unidad(50L, "Obligatoria", 6, EstadoUnidadCurricularV2.NO_CURSADA); seriada.setSeriacionConocida(Boolean.TRUE); c.getUnidades().add(seriada); c.getIdsSeriadasPrimerosSeisSemestres().add(50L); UnidadEvaluacionCurricularV2DTO proyecto = unidad(60L, "Obligatoria", 8, EstadoUnidadCurricularV2.NO_INSCRITA); proyecto.setTipoComponenteCurricular(TipoComponenteCurricularV2.PROYECTO_TERMINAL); c.getUnidades().add(proyecto);
        EvaluacionCargaAcademicaV2DTO carga = carga(c); assertEquals(carga.getEstadoComponentesFinalesRn94(), EstadoRequisitoComponenteFinalV2.REQUISITOS_SERIADAS_S1_S6_PENDIENTES); assertTrue(carga.getComponentesFinalesPendientesRn94().contains(60L));
    }

    @Test public void f9CU08NoSeriadaS1AS6YNorSeriadaS7NoBloqueanComponente() {
        ContextoEvaluacionCurricularV2DTO c = contextoCompleto(); UnidadEvaluacionCurricularV2DTO proyecto = unidad(60L, "Obligatoria", 8, EstadoUnidadCurricularV2.NO_INSCRITA); proyecto.setTipoComponenteCurricular(TipoComponenteCurricularV2.MODULO_INTEGRADOR); c.getUnidades().add(proyecto); c.getUnidades().add(unidad(51L, "Obligatoria", 3, EstadoUnidadCurricularV2.NO_CURSADA)); UnidadEvaluacionCurricularV2DTO s7 = unidad(52L, "Obligatoria", 7, EstadoUnidadCurricularV2.NO_CURSADA); s7.setSeriacionConocida(Boolean.TRUE); c.getUnidades().add(s7);
        assertEquals(carga(c).getEstadoComponentesFinalesRn94(), EstadoRequisitoComponenteFinalV2.REQUISITOS_SERIADAS_S1_S6_CUMPLIDOS);
    }

    @Test public void f9CU09InterbloqueIntersemestralEInteranualCuentanSiEstanEnUniverso() {
        for (TipoSeriacionV2 tipo : new TipoSeriacionV2[] {TipoSeriacionV2.INTERBLOQUE, TipoSeriacionV2.INTERSEMESTRAL, TipoSeriacionV2.INTERANUAL}) {
            ContextoEvaluacionCurricularV2DTO c = contextoCompleto(); UnidadEvaluacionCurricularV2DTO u = unidad(70L, "Obligatoria", 4, EstadoUnidadCurricularV2.NO_CURSADA); RelacionSeriacionCurricularV2DTO r = new RelacionSeriacionCurricularV2DTO(); r.setIdAntecedente(70L); r.setIdConsecuente(71L); r.setTipoSeriacion(tipo); r.setTipoRelacion(TipoRelacionSeriacionV2.DIRECTA); u.getRelacionesSeriacion().add(r); c.getUnidades().add(u); c.getIdsSeriadasPrimerosSeisSemestres().add(70L); UnidadEvaluacionCurricularV2DTO p = unidad(60L, "Obligatoria", 8, EstadoUnidadCurricularV2.NO_INSCRITA); p.setTipoComponenteCurricular(TipoComponenteCurricularV2.PROYECTO_TERMINAL); c.getUnidades().add(p); assertTrue(carga(c).getSeriadasS1S6PendientesRn94().contains(70L));
        }
    }

    @Test public void f9CU10ComponenteNoClasificadoRequiereRevision() { ContextoEvaluacionCurricularV2DTO c = contextoCompleto(); c.getComponentesFinalesSolicitados().add(999L); assertEquals(carga(c).getEstadoComponentesFinalesRn94(), EstadoRequisitoComponenteFinalV2.REQUIERE_REVISION); }
    @Test public void f9CU11Rn93YPromedioNoSustituyenRequisitosEgreso() { ContextoEvaluacionCurricularV2DTO c = contextoCompleto(); c.getUnidades().get(0).setEstado(EstadoUnidadCurricularV2.NO_CURSADA); c.setOportunidadesMejoraUtilizadas(Integer.valueOf(0)); assertEquals(carga(c).getEstadoRequisitosEgresoAcademicoRn94(), EstadoRequisitosEgresoAcademicoV2.REQUISITOS_ACADEMICOS_EGRESO_PENDIENTES); }
    @Test public void f9CU12DeterminismoEIdempotencia() { ContextoEvaluacionCurricularV2DTO c = contextoCompleto(); assertEquals(carga(c).getEstadoRequisitosEgresoAcademicoRn94(), carga(c).getEstadoRequisitosEgresoAcademicoRn94()); }

    private ContextoEvaluacionCurricularV2DTO contextoCompleto() { return contextoConOptativas(8); }
    private ContextoEvaluacionCurricularV2DTO contextoConOptativas(int cantidad) { ContextoEvaluacionCurricularV2DTO c = new ContextoEvaluacionCurricularV2DTO(); c.setPeriodoNormativo("2027-1"); c.setSemestreObjetivo(Integer.valueOf(8)); c.setSeriacionComprobada(Boolean.TRUE); c.setEstructuraProgramaEducativoComprobada(Boolean.TRUE); c.getUnidades().add(unidad(1L, "Obligatoria", 1, EstadoUnidadCurricularV2.ACREDITADA)); c.getIdsObligatoriasRequeridasEgreso().add(1L); for (int i = 0; i < cantidad; i++) { UnidadEvaluacionCurricularV2DTO optativa = unidad(Long.valueOf(i + 2L), "Optativa", 2, EstadoUnidadCurricularV2.ACREDITADA); optativa.setOrdenAcreditacionOptativa(Integer.valueOf(i + 1)); c.getUnidades().add(optativa); } c.getUnidades().add(unidad(90L, "Electiva", 5, EstadoUnidadCurricularV2.ACREDITADA)); return c; }
    private UnidadEvaluacionCurricularV2DTO unidad(Long id, String tipo, int semestre, EstadoUnidadCurricularV2 estado) { UnidadEvaluacionCurricularV2DTO u = new UnidadEvaluacionCurricularV2DTO(); u.setIdPrograma(id); u.setClave("UD" + id); u.setTipoUd(tipo); u.setSemestre(Integer.valueOf(semestre)); u.setEstado(estado); u.setOfertada(Boolean.TRUE); u.setCupoDisponibleConocido(Boolean.TRUE); return u; }
    private EvaluacionCargaAcademicaV2DTO carga(ContextoEvaluacionCurricularV2DTO c) { return servicio.evaluarSombra(c).getEvaluacionCargaAcademica(); }
}
