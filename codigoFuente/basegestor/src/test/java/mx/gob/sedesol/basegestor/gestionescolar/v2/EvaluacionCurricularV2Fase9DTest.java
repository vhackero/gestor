package mx.gob.sedesol.basegestor.gestionescolar.v2;

import static org.testng.Assert.*;

import java.util.Date;

import org.testng.annotations.Test;

import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.CausaPendienteUnidadV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.CausaIndisponibilidadOfertaV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.CodigoMotivoCurricularV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.CodigoReglaCurricularV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.ContextoEvaluacionCurricularV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.DecisionUnidadCurricularV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EstadoUnidadCurricularV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.RelacionSeriacionCurricularV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.ResultadoEvaluacionCurricularV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.TipoProcesoAdministrativoV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.TipoRelacionSeriacionV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.TipoSeriacionV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.TrazaReglaAplicadaV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.UnidadEvaluacionCurricularV2DTO;
import mx.gob.sedesol.basegestor.service.impl.gestionescolar.v2.EvaluacionCurricularV2ServiceImpl;

/** Cierre F9: RN-9.5 auditable y RN-9.2 fuera del motor curricular V2. */
public class EvaluacionCurricularV2Fase9DTest {
    private final EvaluacionCurricularV2ServiceImpl servicio = new EvaluacionCurricularV2ServiceImpl();

    @Test public void f9DU01TrazaRegistraSituacionSemestreFechaVersionYResultado() {
        ResultadoEvaluacionCurricularV2DTO r = evaluar(contexto(5)); assertEquals(r.getSituacionAcademica(), "REGULAR"); TrazaReglaAplicadaV2DTO t = traza(r, CodigoReglaCurricularV2.RN_9_1); assertNotNull(t); assertNotNull(t.getFechaEvaluacion()); assertEquals(t.getVersionReglas(), "PE2027-V1"); assertNotNull(t.getResultado());
    }
    @Test public void f9DU02PendienteCausaYTotalReprobadasSonReconstruibles() {
        ContextoEvaluacionCurricularV2DTO c = contexto(8); c.getUnidades().add(unidad(1L, 3, EstadoUnidadCurricularV2.REPROBADA)); ResultadoEvaluacionCurricularV2DTO r = evaluar(c); assertEquals(r.getTotalReprobadas(), Integer.valueOf(1)); assertEquals(r.getDecisiones().get(0).getMotivos().contains(CodigoMotivoCurricularV2.REPROBACION_ACUMULADA), true);
    }
    @Test public void f9DU03SeriacionDirectaEIndirectaYMotivosSeConservan() {
        ContextoEvaluacionCurricularV2DTO c = contexto(7); UnidadEvaluacionCurricularV2DTO a = unidad(1L, 3, EstadoUnidadCurricularV2.NO_CURSADA); UnidadEvaluacionCurricularV2DTO b = unidad(2L, 5, EstadoUnidadCurricularV2.ACREDITADA); UnidadEvaluacionCurricularV2DTO d = unidad(3L, 7, EstadoUnidadCurricularV2.NO_INSCRITA); RelacionSeriacionCurricularV2DTO ab = relacion(1L, 2L); ab.setSemestreAntecedente(Integer.valueOf(3)); ab.setSemestreConsecuente(Integer.valueOf(5)); RelacionSeriacionCurricularV2DTO bd = relacion(2L, 3L); bd.setSemestreAntecedente(Integer.valueOf(5)); bd.setSemestreConsecuente(Integer.valueOf(7)); b.getRelacionesSeriacion().add(ab); d.getRelacionesSeriacion().add(bd); c.getUnidades().add(a); c.getUnidades().add(b); c.getUnidades().add(d); ResultadoEvaluacionCurricularV2DTO r = evaluar(c); assertTrue(r.getEvaluacionCargaAcademica().getUdsInteranualesNoCumplidasRn83().contains(3L)); assertTrue(r.getEvaluacionCargaAcademica().getAntecedentesNoAcreditadosRn83().contains(1L)); assertNotNull(traza(r, CodigoReglaCurricularV2.RN_8_3));
    }
    @Test public void f9DU04OfertaSinOfertaYSinCupoNoSeColapsan() {
        ContextoEvaluacionCurricularV2DTO c = contexto(8); UnidadEvaluacionCurricularV2DTO sinOferta = unidad(1L, 3, EstadoUnidadCurricularV2.NO_INSCRITA); sinOferta.setOfertada(Boolean.FALSE); UnidadEvaluacionCurricularV2DTO sinCupo = unidad(2L, 3, EstadoUnidadCurricularV2.NO_INSCRITA); sinCupo.setOfertada(Boolean.FALSE); sinCupo.setCupoDisponibleConocido(Boolean.FALSE); sinCupo.setCausaIndisponibilidadOferta(CausaIndisponibilidadOfertaV2.SIN_CUPO); c.getUnidades().add(sinOferta); c.getUnidades().add(sinCupo); ResultadoEvaluacionCurricularV2DTO r = evaluar(c); assertTrue(existeDetalle(r, "disponibilidad=SIN_OFERTA")); assertTrue(existeDetalle(r, "disponibilidad=SIN_CUPO"));
    }
    @Test public void f9DU05Rn61AplicaSoloS5YNoAplicaS7S8() { assertTrue(tieneRegla(evaluar(contexto(5)), CodigoReglaCurricularV2.RN_6_1)); assertFalse(Boolean.TRUE.equals(evaluar(contexto(7)).getEvaluacionCargaAcademica().getAplicaRn61())); assertFalse(Boolean.TRUE.equals(evaluar(contexto(8)).getEvaluacionCargaAcademica().getAplicaRn61())); }
    @Test public void f9DU06ExcepcionTipificadaSeConservaEnCtr02() { ContextoEvaluacionCurricularV2DTO c = contexto(5); c.setAvanceCondicionado(Boolean.TRUE); ResultadoEvaluacionCurricularV2DTO r = evaluar(c); assertNotNull(traza(r, CodigoReglaCurricularV2.CTR_02)); }
    @Test public void f9DU07ExtraordinarioDocumentadoRegistraMetadatosSinPii() { ContextoEvaluacionCurricularV2DTO c = extraordinario(); c.setInstanciaResolutoraExtraordinaria("COORDINACION_ACADEMICA"); c.setFundamentacionExtraordinaria("DICTAMEN_NORMATIVO"); c.setFolioInstitucionalExtraordinario("FOLIO-9D-1"); assertTrue(traza(evaluar(c), CodigoReglaCurricularV2.RN_9_1).getDetalle().contains("DOCUMENTADA")); }
    @Test public void f9DU08ExtraordinarioSinResolucionNoInventaDatos() { assertTrue(traza(evaluar(extraordinario()), CodigoReglaCurricularV2.RN_9_1).getDetalle().contains("PENDIENTE_DE_RESOLUCION")); }
    @Test public void f9DU09ExtemporaneoConservaDecisionesAcademicas() { ContextoEvaluacionCurricularV2DTO o = contexto(7); o.getUnidades().add(unidad(1L, 3, EstadoUnidadCurricularV2.REPROBADA)); ContextoEvaluacionCurricularV2DTO e = contexto(7); e.getUnidades().add(unidad(1L, 3, EstadoUnidadCurricularV2.REPROBADA)); e.setTipoProcesoAdministrativo(TipoProcesoAdministrativoV2.EXTEMPORANEO); assertEquals(evaluar(o).getSituacionAcademica(), evaluar(e).getSituacionAcademica()); assertEquals(evaluar(o).getTotalReprobadas(), evaluar(e).getTotalReprobadas()); }
    @Test public void f9DU10Rn92NoCreaGrupoReservaCupoNiAlteraMatricula() { ResultadoEvaluacionCurricularV2DTO r = evaluar(contexto(8)); assertFalse(r.getReglasEvaluadas().contains(CodigoReglaCurricularV2.RN_9_2)); assertFalse(Boolean.TRUE.equals(r.getEfectosProductivosAplicados())); }
    @Test public void f9DU11Rn93YRN94SeMantienenAuditables() { ResultadoEvaluacionCurricularV2DTO r = evaluar(contexto(8)); assertNotNull(traza(r, CodigoReglaCurricularV2.RN_9_3)); assertNotNull(traza(r, CodigoReglaCurricularV2.RN_9_4)); }
    @Test public void f9DU12DeterminismoEIdempotenciaNoDuplicanReglas() { ContextoEvaluacionCurricularV2DTO c = contexto(8); ResultadoEvaluacionCurricularV2DTO a = evaluar(c), b = evaluar(c); assertEquals(a.getReglasEvaluadas(), b.getReglasEvaluadas()); assertEquals(a.getTrazas().size(), b.getTrazas().size()); }

    private ContextoEvaluacionCurricularV2DTO extraordinario() { ContextoEvaluacionCurricularV2DTO c = contexto(8); c.setTipoProcesoAdministrativo(TipoProcesoAdministrativoV2.EXTEMPORANEO); UnidadEvaluacionCurricularV2DTO u = unidad(1L, 3, EstadoUnidadCurricularV2.NO_CURSADA); u.setCausa(CausaPendienteUnidadV2.OTRA_INSTITUCIONAL); c.getUnidades().add(u); return c; }
    private ContextoEvaluacionCurricularV2DTO contexto(int semestre) { ContextoEvaluacionCurricularV2DTO c = new ContextoEvaluacionCurricularV2DTO(); c.setSemestreObjetivo(Integer.valueOf(semestre)); c.setPeriodoNormativo("2027-1"); c.setVersionReglas("PE2027-V1"); c.setFechaEvaluacion(new Date(0L)); c.setSeriacionComprobada(Boolean.TRUE); c.setTipoProcesoAdministrativo(TipoProcesoAdministrativoV2.ORDINARIO); return c; }
    private UnidadEvaluacionCurricularV2DTO unidad(Long id, int semestre, EstadoUnidadCurricularV2 estado) { UnidadEvaluacionCurricularV2DTO u = new UnidadEvaluacionCurricularV2DTO(); u.setIdPrograma(id); u.setClave("UD" + id); u.setTipoUd("Obligatoria"); u.setSemestre(Integer.valueOf(semestre)); u.setEstado(estado); u.setOfertada(Boolean.TRUE); u.setGrupoActivoConocido(Boolean.TRUE); u.setCupoDisponibleConocido(Boolean.TRUE); return u; }
    private RelacionSeriacionCurricularV2DTO relacion(Long a, Long b) { RelacionSeriacionCurricularV2DTO r = new RelacionSeriacionCurricularV2DTO(); r.setIdAntecedente(a); r.setIdConsecuente(b); r.setTipoSeriacion(TipoSeriacionV2.INTERANUAL); r.setTipoRelacion(TipoRelacionSeriacionV2.DIRECTA); r.setSemestreAntecedente(Integer.valueOf(3)); r.setSemestreConsecuente(Integer.valueOf(7)); r.setEvidencia("PE"); return r; }
    private ResultadoEvaluacionCurricularV2DTO evaluar(ContextoEvaluacionCurricularV2DTO c) { return servicio.evaluarSombra(c); }
    private TrazaReglaAplicadaV2DTO traza(ResultadoEvaluacionCurricularV2DTO r, CodigoReglaCurricularV2 regla) { for (TrazaReglaAplicadaV2DTO t : r.getTrazas()) if (t.getCodigoRegla() == regla) return t; return null; }
    private boolean tieneRegla(ResultadoEvaluacionCurricularV2DTO r, CodigoReglaCurricularV2 regla) { return r.getReglasEvaluadas().contains(regla); }
    private DecisionUnidadCurricularV2DTO decision(ResultadoEvaluacionCurricularV2DTO r, Long id) { for (DecisionUnidadCurricularV2DTO d : r.getDecisiones()) if (id.equals(d.getIdPrograma())) return d; return null; }
    private boolean existeDetalle(ResultadoEvaluacionCurricularV2DTO r, String texto) { for (TrazaReglaAplicadaV2DTO t : r.getTrazas()) if (t.getDetalle() != null && t.getDetalle().contains(texto)) return true; return false; }
}
