package mx.gob.sedesol.basegestor.gestionescolar.v2;

import static org.testng.Assert.*;
import org.testng.annotations.Test;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.*;
import mx.gob.sedesol.basegestor.service.impl.gestionescolar.v2.EvaluacionCurricularV2ServiceImpl;

/** Cobertura de cierre F6A: RN-6.2 descriptiva y sin efectos productivos. */
public class EvaluacionCurricularV2Fase6ACierreTest {
    private final EvaluacionCurricularV2ServiceImpl servicio = new EvaluacionCurricularV2ServiceImpl();

    @Test public void f6AC01DatosDeAdmisionOInscripcionDesconocidosRequierenRevision() {
        ContextoEvaluacionCurricularV2DTO admitido = base(); admitido.setAdmitido(null);
        assertEquals(carga(admitido).getEstadoIncorporacionDiferida(), EstadoIncorporacionDiferidaV2.REQUIERE_REVISION);
        ContextoEvaluacionCurricularV2DTO inscripcion = base(); inscripcion.setInscripcionInicialRealizada(null);
        assertEquals(carga(inscripcion).getEstadoIncorporacionDiferida(), EstadoIncorporacionDiferidaV2.REQUIERE_REVISION);
    }

    @Test public void f6AC02SeriacionIndirectaYNoDeterminableNoHabilitanSemestreDos() {
        ContextoEvaluacionCurricularV2DTO indirecta = base(); indirecta.getUnidades().get(0).setSeriacionIndirectaConocida(Boolean.TRUE);
        assertEquals(carga(indirecta).getEstadoIncorporacionDiferida(), EstadoIncorporacionDiferidaV2.INCORPORACION_SEMESTRE_2_NO_PROCEDE_POR_SERIACION);
        ContextoEvaluacionCurricularV2DTO indeterminada = base(); indeterminada.getUnidades().get(0).setSeriacionConocida(Boolean.TRUE);
        assertEquals(carga(indeterminada).getEstadoIncorporacionDiferida(), EstadoIncorporacionDiferidaV2.REQUIERE_REVISION);
    }

    @Test public void f6AC03SinSemestreDosExisteAlternativaSemestreUnoPosterior() {
        ContextoEvaluacionCurricularV2DTO contexto = base(); contexto.getUnidades().clear();
        EvaluacionCargaAcademicaV2DTO carga = carga(contexto);
        assertEquals(carga.getEstadoIncorporacionDiferida(), EstadoIncorporacionDiferidaV2.INCORPORACION_DIFERIDA_SEMESTRE_1_PROCEDE_EN_SOMBRA);
        assertEquals(carga.getCondicionTrayectoriaRn62(), "INSCRIPCION_INICIAL_DIFERIDA");
        assertEquals(carga.getPeriodoAdmisionRn62(), "2026-2");
        assertEquals(carga.getPeriodoIncorporacionRn62(), "2027-1");
        assertEquals(carga.getSemestreObjetivoRn62(), Integer.valueOf(2));
        assertFalse(carga.getEvidencias().isEmpty());
    }

    @Test public void f6AC04InactividadNoComprobadaRequiereRevision() {
        ContextoEvaluacionCurricularV2DTO contexto = base(); contexto.setActividadAcademicaComprobada(Boolean.FALSE);
        assertEquals(carga(contexto).getEstadoIncorporacionDiferida(), EstadoIncorporacionDiferidaV2.REQUIERE_REVISION);
    }

    @Test public void f6AC05NoInscripcionNoCreaReprobacionIrregularidadNiRn64() {
        ContextoEvaluacionCurricularV2DTO contexto = base();
        UnidadEvaluacionCurricularV2DTO s1 = unidad(10L, 1); contexto.getUnidades().add(s1);
        ResultadoEvaluacionCurricularV2DTO resultado = servicio.evaluarSombra(contexto);
        assertEquals(resultado.getCantidadReprobadasVigentes(), Integer.valueOf(0));
        assertFalse(Boolean.TRUE.equals(resultado.getEvaluacionCargaAcademica().getAplicaRn64()));
        assertFalse(resultado.getSituacionAcademica().contains("IRREGULAR"));
        assertEquals(s1.getEstado(), EstadoUnidadCurricularV2.NO_CURSADA);
    }

    @Test public void f6AC06Rn61NoAplicaEnUnoDosYSigueMotorExistenteEnQuinto() {
        ContextoEvaluacionCurricularV2DTO uno = base(); uno.setSemestreObjetivo(1); uno.setPeriodoNormativo("2027-1");
        assertFalse(Boolean.TRUE.equals(carga(uno).getAplicaCtr01()));
        ContextoEvaluacionCurricularV2DTO dos = base(); dos.setSemestreObjetivo(2); dos.setPeriodoNormativo("2027-1");
        assertFalse(Boolean.TRUE.equals(carga(dos).getAplicaCtr01()));
        ContextoEvaluacionCurricularV2DTO quinto = base(); quinto.setSemestreObjetivo(5); quinto.setPeriodoNormativo("2027-1"); quinto.setIdPlan(1L);
        assertTrue(Boolean.TRUE.equals(carga(quinto).getAplicaCtr01()));
    }

    @Test public void f6AC07DuplicadosSonUnicosYContextoEsDeterministaSinEfectoProductivo() {
        ContextoEvaluacionCurricularV2DTO contexto = base(); contexto.getUnidades().add(unidad(20L, 2));
        EvaluacionCargaAcademicaV2DTO primera = carga(contexto); EvaluacionCargaAcademicaV2DTO segunda = carga(contexto);
        assertEquals(primera.getUdsSemestre2CompatiblesRn62().size(), 1);
        assertEquals(primera.getEstadoIncorporacionDiferida(), segunda.getEstadoIncorporacionDiferida());
        assertFalse(servicio.evaluarSombra(contexto).getEfectosProductivosAplicados());
    }

    private EvaluacionCargaAcademicaV2DTO carga(ContextoEvaluacionCurricularV2DTO contexto) { return servicio.evaluarSombra(contexto).getEvaluacionCargaAcademica(); }
    private ContextoEvaluacionCurricularV2DTO base() {
        ContextoEvaluacionCurricularV2DTO contexto = new ContextoEvaluacionCurricularV2DTO();
        contexto.setAdmitido(Boolean.TRUE); contexto.setInscripcionInicialRealizada(Boolean.FALSE);
        contexto.setActividadAcademicaComprobada(Boolean.TRUE); contexto.setPeriodoAdmision("2026-2"); contexto.setPeriodoIncorporacion("2027-1"); contexto.setSemestreObjetivo(2);
        contexto.getUnidades().add(unidad(20L, 2)); return contexto;
    }
    private UnidadEvaluacionCurricularV2DTO unidad(Long id, int semestre) {
        UnidadEvaluacionCurricularV2DTO unidad = new UnidadEvaluacionCurricularV2DTO();
        unidad.setIdPrograma(id); unidad.setClave("UD" + id); unidad.setTipoUd("Obligatoria"); unidad.setSemestre(semestre);
        unidad.setEstado(EstadoUnidadCurricularV2.NO_CURSADA); unidad.setCausa(CausaPendienteUnidadV2.NO_CURSAMIENTO); unidad.setOfertada(Boolean.TRUE);
        return unidad;
    }
}
