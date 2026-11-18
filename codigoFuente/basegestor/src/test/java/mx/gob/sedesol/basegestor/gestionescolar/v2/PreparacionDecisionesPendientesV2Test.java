package mx.gob.sedesol.basegestor.gestionescolar.v2;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.Date;

import org.testng.annotations.Test;

import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.CausaPendienteUnidadV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.ContextoEvaluacionCurricularV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.DecisionUnidadCurricularV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EstadoDecisionUnidadCurricularV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EstadoIntentoAcreditacionV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EstadoUnidadCurricularV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.IntentoAcreditacionUnidadV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.ParametrosCurricularesV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.RelacionSeriacionCurricularV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.ResultadoEvaluacionCurricularV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.TipoRelacionSeriacionV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.TipoSeriacionV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.UnidadEvaluacionCurricularV2DTO;
import mx.gob.sedesol.basegestor.service.impl.gestionescolar.v2.EvaluacionCurricularV2ServiceImpl;

/** Opciones preparadas para respuestas Q4-Q7/Q11; los datos son sintéticos y de sólo lectura. */
public class PreparacionDecisionesPendientesV2Test {
    private final EvaluacionCurricularV2ServiceImpl service = new EvaluacionCurricularV2ServiceImpl();

    @Test public void q6AlternativasCambianSoloElTratamientoConfiguradoDelCeroSinBaja() {
        ContextoEvaluacionCurricularV2DTO actual = contexto(); actual.getUnidades().add(cero(1L));
        assertEquals(service.evaluarSombra(actual).getCantidadReprobadasVigentes(), Integer.valueOf(1));
        ContextoEvaluacionCurricularV2DTO excluir = contexto(); excluir.getParametrosCurriculares().setTratamientoCeroSinBaja("EXCLUIR_PERIODO_COMPLETO_EN_CERO"); excluir.getUnidades().add(cero(1L));
        assertEquals(service.evaluarSombra(excluir).getCantidadReprobadasVigentes(), Integer.valueOf(0));
        ContextoEvaluacionCurricularV2DTO revision = contexto(); revision.getParametrosCurriculares().setTratamientoCeroSinBaja("REVISION"); revision.getUnidades().add(cero(1L));
        assertEquals(service.evaluarSombra(revision).getSituacionAcademicaCalculada(), "NO_DETERMINADA");
    }

    @Test public void q4AlternativasControlanConteoDeB2SeriadaEnMinimoYMaximo() {
        ContextoEvaluacionCurricularV2DTO noCuenta = contexto(); noCuenta.getUnidades().add(b2Seriada(2L));
        assertEquals(service.evaluarSombra(noCuenta).getEvaluacionCargaMinima().getCantidadTotalElegible(), Integer.valueOf(0));
        ContextoEvaluacionCurricularV2DTO ambos = contexto(); ambos.getParametrosCurriculares().setTratamientoUdB2SeriadaCarga("CUENTA_MIN_Y_MAX"); ambos.getUnidades().add(b2Seriada(2L));
        assertEquals(service.evaluarSombra(ambos).getEvaluacionCargaMinima().getCantidadTotalElegible(), Integer.valueOf(1));
        ContextoEvaluacionCurricularV2DTO soloMax = contexto(); soloMax.getParametrosCurriculares().setTratamientoUdB2SeriadaCarga("SOLO_MAX"); soloMax.getUnidades().add(b2Seriada(2L));
        ResultadoEvaluacionCurricularV2DTO resultado = service.evaluarSombra(soloMax);
        assertEquals(resultado.getEvaluacionCargaMinima().getCantidadTotalElegible(), Integer.valueOf(0));
        assertEquals(resultado.getEvaluacionCargaMaxima().getCargaActualEvaluada(), Integer.valueOf(1));
    }

    @Test public void q5RecursamientoDeQuintoPuedePrepararseComoRutaRegularizacion() {
        ContextoEvaluacionCurricularV2DTO actual = contextoQuinto();
        assertFalse(service.evaluarSombra(actual).getEvaluacionCargaAcademica().getImpedimentosAvanceRn615().contains("Q5_RECURSAMIENTO_QUINTO_REGULARIZACION"));
        ContextoEvaluacionCurricularV2DTO alternativa = contextoQuinto(); alternativa.getParametrosCurriculares().setTratamientoRecursamientoQuinto("REGULARIZACION");
        assertTrue(service.evaluarSombra(alternativa).getEvaluacionCargaAcademica().getImpedimentosAvanceRn615().contains("Q5_RECURSAMIENTO_QUINTO_REGULARIZACION"));
    }

    @Test public void q7PermiteOptativaFaltanteEnReferenciaAltaSoloCuandoSeConfigure() {
        ContextoEvaluacionCurricularV2DTO bloquear = contextoOptativaAlta();
        assertTrue(bloqueada(service.evaluarSombra(bloquear), 31L));
        ContextoEvaluacionCurricularV2DTO permitir = contextoOptativaAlta(); permitir.getParametrosCurriculares().setTratamientoOptativasFaltantesQuintoAOcho("PERMITIR_TRAS_PENDIENTES");
        assertFalse(bloqueada(service.evaluarSombra(permitir), 31L));
    }

    @Test public void q11ConservaAmbosBloquesActualYPuedeExigirBloqueOriginalVerificable() {
        ContextoEvaluacionCurricularV2DTO ambos = contexto(); UnidadEvaluacionCurricularV2DTO optativa = optativa(41L, "USTAPREN", 1, "B2"); optativa.setEstado(EstadoUnidadCurricularV2.BAJA_PARCIAL); optativa.setCausa(CausaPendienteUnidadV2.BAJA_PARCIAL); optativa.getReferenciasHistoricas().add("BLOQUE_ORIGINAL:B1"); ambos.getUnidades().add(optativa); ambos.getIdsUnidadesPretendidas().add(41L);
        assertFalse(bloqueada(service.evaluarSombra(ambos), 41L));
        ContextoEvaluacionCurricularV2DTO original = contexto(); original.getParametrosCurriculares().setTratamientoBloqueOptativaBajaOSustituta("BLOQUE_ORIGINAL"); original.getUnidades().add(optativa(41L, "USTAPREN", 1, "B2")); original.getUnidades().get(0).setEstado(EstadoUnidadCurricularV2.BAJA_PARCIAL); original.getUnidades().get(0).setCausa(CausaPendienteUnidadV2.BAJA_PARCIAL); original.getUnidades().get(0).getReferenciasHistoricas().add("BLOQUE_ORIGINAL:B1"); original.getIdsUnidadesPretendidas().add(41L);
        assertTrue(bloqueada(service.evaluarSombra(original), 41L));
    }

    @Test public void q2MapaPe2027SinteticoNoDependeDeIdsPe2024() {
        for (long plan = 202701L; plan < 202718L; plan++) {
            ContextoEvaluacionCurricularV2DTO contexto = contexto(); contexto.setIdPlan(Long.valueOf(plan)); contexto.setClavePlan("PE2027-" + plan); contexto.setSemestreReferencia(Integer.valueOf(5));
            for (int semestre = 1; semestre <= 4; semestre++) {
                for (int i = 0; i < 4; i++) contexto.getUnidades().add(obligatoria(plan * 100 + semestre * 10 + i, semestre, "B1", EstadoUnidadCurricularV2.ACREDITADA));
                contexto.getUnidades().add(optativa(plan * 100 + semestre * 10 + 5, semestreCatalogo(semestre, 0), semestre, "B1"));
                contexto.getUnidades().add(optativa(plan * 100 + semestre * 10 + 6, semestreCatalogo(semestre, 1), semestre, "B2"));
            }
            UnidadEvaluacionCurricularV2DTO quinta = obligatoria(plan * 100 + 51, 5, "B1", EstadoUnidadCurricularV2.NO_CURSADA);
            quinta.getRelacionesSeriacion().add(relacion(contexto.getUnidades().get(0).getIdPrograma(), quinta.getIdPrograma(), 1, 5, "B1", "B1"));
            contexto.getUnidades().add(quinta); contexto.getIdsUnidadesPretendidas().add(quinta.getIdPrograma());
            ResultadoEvaluacionCurricularV2DTO resultado = service.evaluarSombra(contexto);
            assertTrue(resultado.getReglasEvaluadas().toString().contains("RN_6_1"));
            assertTrue(resultado.getReglasEvaluadas().toString().contains("RN_8"));
        }
    }

    @Test public void cierreDeActaSinCalificacionMantieneInterbloquePendienteDeConfirmacion() {
        ContextoEvaluacionCurricularV2DTO contexto = contexto();
        UnidadEvaluacionCurricularV2DTO b1 = obligatoria(61L, 1, "B1", EstadoUnidadCurricularV2.NO_DETERMINADO);
        b1.getReferenciasHistoricas().add("HISTORIAL_CANONICO:PENDIENTE_CALIFICACION");
        UnidadEvaluacionCurricularV2DTO b2 = obligatoria(62L, 1, "B2", EstadoUnidadCurricularV2.NO_CURSADA);
        b2.getRelacionesSeriacion().add(relacion(61L, 62L, 1, 1, "1", "2")); contexto.getUnidades().add(b1); contexto.getUnidades().add(b2);
        assertEquals(decision(service.evaluarSombra(contexto), 62L).getEstadoOperativoInterbloque(), "PENDIENTE_DE_CONFIRMACION");
    }

    private ContextoEvaluacionCurricularV2DTO contexto() { ContextoEvaluacionCurricularV2DTO c = new ContextoEvaluacionCurricularV2DTO(); c.setAmbitoRnPe2024_2027("APLICA"); c.setIdPlan(202701L); c.setPeriodoNormativo("2027-1"); c.setSemestreReferencia(Integer.valueOf(6)); c.setFechaEvaluacion(new Date(0L)); c.setActividadAcademicaComprobada(Boolean.TRUE); c.setPermanenciaDentroLimiteComprobada(Boolean.TRUE); c.setEstructuraProgramaEducativoComprobada(Boolean.TRUE); c.setSancionImpedimentoConocida(Boolean.TRUE); c.setSancionImpedimentoRegistrada(Boolean.FALSE); c.setRestriccionPeNormativaConocida(Boolean.TRUE); c.setRestriccionPeNormativaImpeditiva(Boolean.FALSE); c.setParametrosCurriculares(parametros()); return c; }
    private ParametrosCurricularesV2DTO parametros() { ParametrosCurricularesV2DTO p = new ParametrosCurricularesV2DTO(); p.setCatalogoOptativas("USTAPREN:1,RECVIRTU:1,PRINVEST:1,PGHRATRS:2,INAVIRTU:2,ANDHUMAN:2,BAIDOCUM:3,BAICAMPO:3,REACADEM:3,INARTIFI:3,PECRITIC:4,HIMEXICO:4,MECMUNDI:4"); p.setCtr01PeriodoInicio("2027-1"); p.setTratamientoCeroSinBaja("REPROBACION"); p.setTratamientoRecursamientoQuinto("AVANCE_CONDICIONADO"); p.setTratamientoOptativasFaltantesQuintoAOcho("BLOQUEAR"); p.setTratamientoBloqueOptativaBajaOSustituta("AMBOS_BLOQUES"); p.setTratamientoUdB2SeriadaCarga("NO_CUENTA"); return p; }
    private ContextoEvaluacionCurricularV2DTO contextoQuinto() { ContextoEvaluacionCurricularV2DTO c = contexto(); c.setSemestreReferencia(Integer.valueOf(4)); UnidadEvaluacionCurricularV2DTO pendiente = obligatoria(21L, 1, "B1", EstadoUnidadCurricularV2.REPROBADA); pendiente.setCausa(CausaPendienteUnidadV2.REPROBACION); pendiente.getIntentosAcreditacion().add(intento("P1", "2026-1", 50D)); UnidadEvaluacionCurricularV2DTO quinta = obligatoria(22L, 5, "B1", EstadoUnidadCurricularV2.REPROBADA); quinta.setCausa(CausaPendienteUnidadV2.REPROBACION); quinta.getIntentosAcreditacion().add(intento("Q1", "2026-2", 50D)); c.getUnidades().add(pendiente); c.getUnidades().add(quinta); c.getIdsUnidadesPretendidas().add(22L); return c; }
    private ContextoEvaluacionCurricularV2DTO contextoOptativaAlta() { ContextoEvaluacionCurricularV2DTO c = contexto(); UnidadEvaluacionCurricularV2DTO deuda = obligatoria(30L, 2, "B1", EstadoUnidadCurricularV2.REPROBADA); deuda.setCausa(CausaPendienteUnidadV2.REPROBACION); deuda.getIntentosAcreditacion().add(intento("D1", "2026-1", 50D)); c.getUnidades().add(deuda); c.getUnidades().add(optativa(31L, "USTAPREN", 1, "B1")); c.getIdsUnidadesPretendidas().add(31L); return c; }
    private UnidadEvaluacionCurricularV2DTO cero(Long id) { UnidadEvaluacionCurricularV2DTO u = obligatoria(id.longValue(), 2, "B1", EstadoUnidadCurricularV2.REPROBADA); u.setCausa(CausaPendienteUnidadV2.REPROBACION); u.getIntentosAcreditacion().add(intento("C" + id, "2026-1", 0D)); return u; }
    private UnidadEvaluacionCurricularV2DTO b2Seriada(Long id) { UnidadEvaluacionCurricularV2DTO u = obligatoria(id.longValue(), 2, "B2", EstadoUnidadCurricularV2.NO_CURSADA); u.setCausa(CausaPendienteUnidadV2.NO_CURSAMIENTO); u.setSeriacionDirectaConocida(Boolean.TRUE); return u; }
    private UnidadEvaluacionCurricularV2DTO obligatoria(long id, int semestre, String bloque, EstadoUnidadCurricularV2 estado) { UnidadEvaluacionCurricularV2DTO u = new UnidadEvaluacionCurricularV2DTO(); u.setIdPrograma(Long.valueOf(id)); u.setClave("OB" + id); u.setTipoUd("Obligatoria"); u.setSemestre(Integer.valueOf(semestre)); u.setBloque(bloque); u.setEstado(estado); u.setOfertada(Boolean.TRUE); u.setGrupoActivoConocido(Boolean.TRUE); u.setCupoDisponibleConocido(Boolean.TRUE); u.setSeleccionableOrigen(Boolean.TRUE); return u; }
    private UnidadEvaluacionCurricularV2DTO optativa(long id, String clave, int semestre, String bloque) { UnidadEvaluacionCurricularV2DTO u = obligatoria(id, semestre, bloque, EstadoUnidadCurricularV2.NO_CURSADA); u.setClave(clave); u.setTipoUd("Optativa"); u.setCausa(CausaPendienteUnidadV2.NO_CURSAMIENTO); return u; }
    private String semestreCatalogo(int semestre, int indice) { String[][] claves = {{"USTAPREN","RECVIRTU"},{"PGHRATRS","INAVIRTU"},{"BAIDOCUM","BAICAMPO"},{"PECRITIC","HIMEXICO"}}; return claves[semestre - 1][indice]; }
    private IntentoAcreditacionUnidadV2DTO intento(String id, String periodo, Double calificacion) { IntentoAcreditacionUnidadV2DTO i = new IntentoAcreditacionUnidadV2DTO(); i.setIdEjecucionTecnico(id); i.setPeriodo(periodo); i.setCalificacionFinal(calificacion); i.setEstado(calificacion.doubleValue() >= 60D ? EstadoIntentoAcreditacionV2.ACREDITADO : EstadoIntentoAcreditacionV2.REPROBADO); return i; }
    private RelacionSeriacionCurricularV2DTO relacion(Long a, Long b, int sa, int sb, String ba, String bb) { RelacionSeriacionCurricularV2DTO r = new RelacionSeriacionCurricularV2DTO(); r.setIdAntecedente(a); r.setIdConsecuente(b); r.setSemestreAntecedente(Integer.valueOf(sa)); r.setSemestreConsecuente(Integer.valueOf(sb)); r.setBloqueAntecedente(ba); r.setBloqueConsecuente(bb); r.setTipoRelacion(TipoRelacionSeriacionV2.DIRECTA); r.setTipoSeriacion(sa == sb ? TipoSeriacionV2.INTERBLOQUE : TipoSeriacionV2.INTERSEMESTRAL); return r; }
    private boolean bloqueada(ResultadoEvaluacionCurricularV2DTO r, Long id) { return decision(r, id).getEstadosSombra().contains(EstadoDecisionUnidadCurricularV2.BLOQUEADA); }
    private DecisionUnidadCurricularV2DTO decision(ResultadoEvaluacionCurricularV2DTO r, Long id) { for (DecisionUnidadCurricularV2DTO d : r.getDecisiones()) if (id.equals(d.getIdPrograma())) return d; throw new AssertionError("Sin decisión " + id); }
}
