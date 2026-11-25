package mx.gob.sedesol.basegestor.gestionescolar.v2;

import static org.testng.Assert.*;

import org.testng.annotations.Test;

import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.CausaPendienteUnidadV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.ContextoEvaluacionCurricularV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EstadoReinscripcionExtemporaneaV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EstadoUnidadCurricularV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EvaluacionCargaAcademicaV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.RelacionSeriacionCurricularV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.ResultadoEvaluacionCurricularV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.TipoProcesoAdministrativoV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.TipoRelacionSeriacionV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.TipoSeriacionV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.UnidadEvaluacionCurricularV2DTO;
import mx.gob.sedesol.basegestor.service.impl.gestionescolar.v2.EvaluacionCurricularV2ServiceImpl;

/** RN-9.1: el proceso extemporáneo solo orquesta resultados V2 ya calculados. */
public class EvaluacionCurricularV2Fase9ATest {
    private final EvaluacionCurricularV2ServiceImpl servicio = new EvaluacionCurricularV2ServiceImpl();

    @Test public void f9AU01OrdinarioNoAplicaComoExtemporaneo() {
        EvaluacionCargaAcademicaV2DTO carga = carga(contexto(3, TipoProcesoAdministrativoV2.ORDINARIO));
        assertFalse(Boolean.TRUE.equals(carga.getAplicaRn91()));
        assertEquals(carga.getEstadoReinscripcionExtemporaneaRn91(), EstadoReinscripcionExtemporaneaV2.NO_APLICA);
    }

    @Test public void f9AU02ExtemporaneoVerificableAplicaEnSombra() {
        ContextoEvaluacionCurricularV2DTO c = contexto(3, TipoProcesoAdministrativoV2.EXTEMPORANEO);
        c.setEvidenciaProcesoAdministrativo("VENTANA_EXTEMPORANEA_VERIFICABLE");
        EvaluacionCargaAcademicaV2DTO carga = carga(c);
        assertTrue(Boolean.TRUE.equals(carga.getAplicaRn91()));
        assertEquals(carga.getEstadoReinscripcionExtemporaneaRn91(), EstadoReinscripcionExtemporaneaV2.EXTEMPORANEO_PROCEDE_SEGUN_REGLAS);
    }

    @Test public void f9AU03TipoProcesoNoDeterminadoRequiereRevisionPropia() {
        EvaluacionCargaAcademicaV2DTO carga = carga(contexto(3, TipoProcesoAdministrativoV2.NO_DETERMINADO));
        assertEquals(carga.getEstadoReinscripcionExtemporaneaRn91(), EstadoReinscripcionExtemporaneaV2.REQUIERE_REVISION);
        assertTrue(carga.getDecisionesRn91().contains("TIPO_PROCESO_ADMINISTRATIVO_NO_DETERMINADO"));
    }

    @Test public void f9AU04MetamorficaRegularConservaSituacionYCarga() { assertEquivalenciaAcademica(contexto(3, TipoProcesoAdministrativoV2.ORDINARIO)); }

    @Test public void f9AU05MetamorficaIrregularConservaSituacionYCargaYPrioridad() {
        ContextoEvaluacionCurricularV2DTO c = contexto(3, TipoProcesoAdministrativoV2.ORDINARIO);
        c.getUnidades().add(unidad(1L, 2, EstadoUnidadCurricularV2.REPROBADA));
        assertEquivalenciaAcademica(c);
    }

    @Test public void f9AU06MetamorficaRn64PermaneceActiva() {
        ContextoEvaluacionCurricularV2DTO c = contexto(3, TipoProcesoAdministrativoV2.ORDINARIO);
        for (long id = 1; id <= 4; id++) c.getUnidades().add(unidad(id, 2, EstadoUnidadCurricularV2.REPROBADA));
        ResultadoEvaluacionCurricularV2DTO ordinario = servicio.evaluarSombra(c);
        c.setTipoProcesoAdministrativo(TipoProcesoAdministrativoV2.EXTEMPORANEO);
        ResultadoEvaluacionCurricularV2DTO extemporaneo = servicio.evaluarSombra(c);
        assertTrue(Boolean.TRUE.equals(ordinario.getEvaluacionCargaAcademica().getAplicaRn64()));
        assertEquals(extemporaneo.getEvaluacionCargaAcademica().getAplicaRn64(), ordinario.getEvaluacionCargaAcademica().getAplicaRn64());
        assertEquals(extemporaneo.getEvaluacionCargaAcademica().getEstadoReinscripcionExtemporaneaRn91(), EstadoReinscripcionExtemporaneaV2.EXTEMPORANEO_RESTRINGIDO_POR_REGLAS);
    }

    @Test public void f9AU07MetamorficaSeriacionNoCumplidaSeConserva() {
        ContextoEvaluacionCurricularV2DTO c = contextoConSeriacion(EstadoUnidadCurricularV2.NO_CURSADA, TipoProcesoAdministrativoV2.ORDINARIO);
        ResultadoEvaluacionCurricularV2DTO ordinario = servicio.evaluarSombra(c);
        c.setTipoProcesoAdministrativo(TipoProcesoAdministrativoV2.EXTEMPORANEO);
        ResultadoEvaluacionCurricularV2DTO extemporaneo = servicio.evaluarSombra(c);
        assertEquals(ordinario.getEvaluacionCargaAcademica().getUdsInteranualesNoCumplidasRn83(), extemporaneo.getEvaluacionCargaAcademica().getUdsInteranualesNoCumplidasRn83());
        assertTrue(extemporaneo.getEvaluacionCargaAcademica().getUdsInteranualesNoCumplidasRn83().contains(2L));
    }

    @Test public void f9AU08ExtemporaneoS5ConsumeRn61() {
        EvaluacionCargaAcademicaV2DTO carga = carga(contexto(5, TipoProcesoAdministrativoV2.EXTEMPORANEO));
        assertTrue(carga.getReglasRelacionadas().contains(mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.CodigoReglaCurricularV2.RN_6_1));
    }

    @Test public void f9AU09ExtemporaneoS3YS7NoActivaRn61() {
        assertFalse(Boolean.TRUE.equals(carga(contexto(3, TipoProcesoAdministrativoV2.EXTEMPORANEO)).getAplicaCtr01()));
        assertFalse(Boolean.TRUE.equals(carga(contexto(7, TipoProcesoAdministrativoV2.EXTEMPORANEO)).getAplicaCtr01()));
    }

    @Test public void f9AU10ExcepcionTipificadaSeReutilizaSinValoracionManual() {
        ContextoEvaluacionCurricularV2DTO c = contexto(5, TipoProcesoAdministrativoV2.EXTEMPORANEO);
        c.getUnidades().add(unidad(1L, 1, EstadoUnidadCurricularV2.NO_CURSADA));
        EvaluacionCargaAcademicaV2DTO carga = carga(c);
        assertTrue(Boolean.TRUE.equals(carga.getExcepcionTipificadaRn91()));
        assertFalse(Boolean.TRUE.equals(carga.getRequiereValoracionIndividualRn91()));
        assertEquals(carga.getEstadoReinscripcionExtemporaneaRn91(), EstadoReinscripcionExtemporaneaV2.EXTEMPORANEO_CON_EXCEPCION_TIPIFICADA);
    }

    @Test public void f9AU11ExtraordinarioNoTipificadoExigeResolucionDocumentada() {
        ContextoEvaluacionCurricularV2DTO c = contexto(3, TipoProcesoAdministrativoV2.EXTEMPORANEO);
        UnidadEvaluacionCurricularV2DTO unidad = unidad(1L, 2, EstadoUnidadCurricularV2.NO_CURSADA);
        unidad.setCausa(CausaPendienteUnidadV2.OTRA_INSTITUCIONAL); c.getUnidades().add(unidad);
        EvaluacionCargaAcademicaV2DTO carga = carga(c);
        assertEquals(carga.getEstadoReinscripcionExtemporaneaRn91(), EstadoReinscripcionExtemporaneaV2.REQUIERE_VALORACION_INDIVIDUAL);
        assertTrue(Boolean.TRUE.equals(carga.getRequiereResolucionDocumentadaRn91()));
    }

    @Test public void f9AU12ExtemporaneoNoInventaExcepcionNiFlexibilizaDisponibilidad() {
        ContextoEvaluacionCurricularV2DTO c = contexto(3, TipoProcesoAdministrativoV2.EXTEMPORANEO);
        UnidadEvaluacionCurricularV2DTO u = unidad(1L, 3, EstadoUnidadCurricularV2.NO_INSCRITA); u.setOfertada(Boolean.FALSE); u.setCupoDisponibleConocido(Boolean.FALSE); c.getUnidades().add(u);
        c.setTipoProcesoAdministrativo(TipoProcesoAdministrativoV2.ORDINARIO); ResultadoEvaluacionCurricularV2DTO ordinario = servicio.evaluarSombra(c);
        c.setTipoProcesoAdministrativo(TipoProcesoAdministrativoV2.EXTEMPORANEO); ResultadoEvaluacionCurricularV2DTO extemporaneo = servicio.evaluarSombra(c);
        assertEquals(extemporaneo.getExcepcionesEvaluadas().size(), ordinario.getExcepcionesEvaluadas().size());
        assertFalse(extemporaneo.getDecisiones().get(0).getOfertaEvaluadaV2());
    }

    @Test public void f9AU13DeterminismoIdempotenciaYSinEfectoProductivo() {
        ContextoEvaluacionCurricularV2DTO c = contextoConSeriacion(EstadoUnidadCurricularV2.ACREDITADA, TipoProcesoAdministrativoV2.EXTEMPORANEO);
        EvaluacionCargaAcademicaV2DTO primera = carga(c); EvaluacionCargaAcademicaV2DTO segunda = carga(c);
        assertEquals(primera.getEstadoReinscripcionExtemporaneaRn91(), segunda.getEstadoReinscripcionExtemporaneaRn91());
        assertEquals(primera.getDecisionesRn91(), segunda.getDecisionesRn91());
        assertFalse(Boolean.TRUE.equals(servicio.evaluarSombra(c).getEfectosProductivosAplicados()));
    }

    private void assertEquivalenciaAcademica(ContextoEvaluacionCurricularV2DTO ordinario) {
        ResultadoEvaluacionCurricularV2DTO primero = servicio.evaluarSombra(ordinario);
        ordinario.setTipoProcesoAdministrativo(TipoProcesoAdministrativoV2.EXTEMPORANEO);
        ResultadoEvaluacionCurricularV2DTO segundo = servicio.evaluarSombra(ordinario);
        assertEquals(segundo.getSituacionAcademicaCalculada(), primero.getSituacionAcademicaCalculada());
        assertEquals(segundo.getCantidadReprobadasVigentes(), primero.getCantidadReprobadasVigentes());
        assertEquals(segundo.getEvaluacionCargaAcademica().getCargaMaximaNormativa(), primero.getEvaluacionCargaAcademica().getCargaMaximaNormativa());
        assertEquals(segundo.getEvaluacionCargaAcademica().getUdsPrioritarias(), primero.getEvaluacionCargaAcademica().getUdsPrioritarias());
    }
    private ContextoEvaluacionCurricularV2DTO contexto(int semestre, TipoProcesoAdministrativoV2 proceso) { ContextoEvaluacionCurricularV2DTO c = new ContextoEvaluacionCurricularV2DTO(); c.setSemestreObjetivo(Integer.valueOf(semestre)); c.setPeriodoNormativo("2027-1"); c.setTipoProcesoAdministrativo(proceso); c.setSeriacionComprobada(Boolean.TRUE); return c; }
    private ContextoEvaluacionCurricularV2DTO contextoConSeriacion(EstadoUnidadCurricularV2 estado, TipoProcesoAdministrativoV2 proceso) { ContextoEvaluacionCurricularV2DTO c = contexto(3, proceso); UnidadEvaluacionCurricularV2DTO a = unidad(1L, 2, estado); UnidadEvaluacionCurricularV2DTO b = unidad(2L, 3, EstadoUnidadCurricularV2.NO_INSCRITA); RelacionSeriacionCurricularV2DTO r = new RelacionSeriacionCurricularV2DTO(); r.setIdAntecedente(1L); r.setIdConsecuente(2L); r.setSemestreAntecedente(Integer.valueOf(2)); r.setSemestreConsecuente(Integer.valueOf(3)); r.setTipoSeriacion(TipoSeriacionV2.INTERANUAL); r.setTipoRelacion(TipoRelacionSeriacionV2.DIRECTA); r.setEvidencia("PE_F9A:1->2"); b.getRelacionesSeriacion().add(r); c.getUnidades().add(a); c.getUnidades().add(b); return c; }
    private UnidadEvaluacionCurricularV2DTO unidad(Long id, int semestre, EstadoUnidadCurricularV2 estado) { UnidadEvaluacionCurricularV2DTO u = new UnidadEvaluacionCurricularV2DTO(); u.setIdPrograma(id); u.setClave("UD" + id); u.setTipoUd("Obligatoria"); u.setSemestre(Integer.valueOf(semestre)); u.setEstado(estado); u.setOfertada(Boolean.TRUE); u.setGrupoActivoConocido(Boolean.TRUE); u.setCupoDisponibleConocido(Boolean.TRUE); if (estado == EstadoUnidadCurricularV2.REPROBADA) { u.setCausa(CausaPendienteUnidadV2.REPROBACION); u.setReprobacionVigenteConocida(Boolean.TRUE); } return u; }
    private EvaluacionCargaAcademicaV2DTO carga(ContextoEvaluacionCurricularV2DTO c) { return servicio.evaluarSombra(c).getEvaluacionCargaAcademica(); }
}
