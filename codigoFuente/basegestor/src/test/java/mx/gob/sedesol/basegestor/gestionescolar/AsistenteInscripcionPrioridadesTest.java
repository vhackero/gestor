package mx.gob.sedesol.basegestor.service.impl.gestionescolar;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.mockito.Mockito;
import org.testng.annotations.Test;

import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.IntentosAsignaturasDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.AsistenteInscripcionContextoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.EscenarioProyeccionDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.EstadoAcademicoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.InscripcionContextoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.InscripcionBajasDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.InscripcionMateriasReprobadasDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.InscripcionPersonaDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.ProyeccionSiguientePeriodoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.UnidadDecisionInscripcionDTO;
import mx.gob.sedesol.basegestor.commons.dto.inscripcion.InscripcionPreviaMateriasDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.ContextoEvaluacionCurricularV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EstadoUnidadCurricularV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.UnidadEvaluacionCurricularV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.ResultadoEvaluacionCurricularV2DTO;
import mx.gob.sedesol.basegestor.service.gestionescolar.v2.ContextoEvaluacionCurricularV2Adapter;
import mx.gob.sedesol.basegestor.service.gestionescolar.v2.EvaluacionCurricularV2Service;
import mx.gob.sedesol.basegestor.service.impl.gestionescolar.v2.ContextoEvaluacionCurricularV2AdapterImpl;
import mx.gob.sedesol.basegestor.service.impl.gestionescolar.v2.EvaluacionCurricularV2ServiceImpl;

public class AsistenteInscripcionPrioridadesTest {

    @Test
    public void cuentaIntentosPorIdentidadDeUdSinDuplicarPersonasNiUnidades() {
        IntentosAsignaturasDTO primera = intento(100L, 2L);
        IntentosAsignaturasDTO segunda = intento(200L, 1L);
        IntentosAsignaturasDTO actualizacionMismaUd = intento(100L, 2L);

        Map<Long, Long> resultado = AsistenteInscripcionServiceImpl
                .mapearIntentosReprobadosPorUd(Arrays.asList(primera, segunda, actualizacionMismaUd));

        assertEquals(resultado.size(), 2);
        assertEquals(resultado.get(100L), Long.valueOf(2L));
        assertEquals(resultado.get(200L), Long.valueOf(1L));
    }

    @Test
    public void noRelacionaRecursamientoSoloPorNombreCoincidente() {
        assertEquals(AsistenteInscripcionServiceImpl.coincideUnidadReinscrita(Long.valueOf(200L), "UD-200",
                Collections.singleton(Long.valueOf(100L)), new HashSet<String>()), false);
        assertEquals(AsistenteInscripcionServiceImpl.coincideUnidadReinscrita(null, "UD-200",
                Collections.<Long>emptySet(), Collections.<String>emptySet()), false);
    }

    @Test
    public void relacionaRecursamientoPorIdOClaveCurricularVerificable() {
        assertEquals(AsistenteInscripcionServiceImpl.coincideUnidadReinscrita(Long.valueOf(100L), "OTRA",
                Collections.singleton(Long.valueOf(100L)), Collections.<String>emptySet()), true);
        assertEquals(AsistenteInscripcionServiceImpl.coincideUnidadReinscrita(Long.valueOf(200L), "UD-100",
                Collections.<Long>emptySet(), Collections.singleton("UD-100")), true);
    }

    @Test
    public void bajaTemporalNoOcultaUnaReprobadaPeroLaParcialDeLaMismaUdSi() {
        InscripcionMateriasReprobadasDTO reprobada = new InscripcionMateriasReprobadasDTO();
        reprobada.setIdPrograma(Long.valueOf(100L));
        reprobada.setClavePrograma("UD-100");

        assertEquals(AsistenteInscripcionServiceImpl.bajaParcialAfectaReprobada(reprobada,
                baja(Long.valueOf(1L), Long.valueOf(100L), "Baja temporal")), false);
        assertEquals(AsistenteInscripcionServiceImpl.bajaParcialAfectaReprobada(reprobada,
                baja(Long.valueOf(2L), Long.valueOf(100L), "Baja parcial")), true);
    }

    @Test
    public void copiaExpectativaNoMutaContextoRegistradoNiComparteUnidades() {
        ContextoEvaluacionCurricularV2DTO original = new ContextoEvaluacionCurricularV2DTO();
        UnidadEvaluacionCurricularV2DTO unidad = new UnidadEvaluacionCurricularV2DTO();
        unidad.setIdPrograma(Long.valueOf(10L));
        unidad.setEstado(EstadoUnidadCurricularV2.EN_CURSO);
        original.getUnidades().add(unidad);

        ContextoEvaluacionCurricularV2DTO primero = AsistenteInscripcionServiceImpl.copiarContextoExpectativa(original);
        ContextoEvaluacionCurricularV2DTO segundo = AsistenteInscripcionServiceImpl.copiarContextoExpectativa(original);
        primero.getUnidades().get(0).setEstado(EstadoUnidadCurricularV2.ACREDITADA);
        segundo.getUnidades().get(0).setEstado(EstadoUnidadCurricularV2.REPROBADA);

        assertEquals(original.getUnidades().get(0).getEstado(), EstadoUnidadCurricularV2.EN_CURSO);
        assertEquals(primero.getUnidades().get(0).getEstado(), EstadoUnidadCurricularV2.ACREDITADA);
        assertEquals(segundo.getUnidades().get(0).getEstado(), EstadoUnidadCurricularV2.REPROBADA);
    }

    @Test
    public void expectativaSinUdEnCursoConservaReferenciaYNoConstruyeHipotesis() throws Exception {
        ResultadoEvaluacionCurricularV2DTO resultado = resultado("REGULAR");
        ProyeccionSiguientePeriodoDTO proyeccion = invocarExpectativa(base(), resultado, false);
        assertEquals(proyeccion.getEscenarios().size(), 1);
        assertTrue(proyeccion.getResumen().contains("No hay UD en curso verificables"));
        assertEquals(proyeccion.getEscenarios().get(0).getResultadoHipotetico(), "REGULAR");
    }

    @Test
    public void expectativaAnteErrorV2NoUsaConclusionesLegacy() throws Exception {
        ProyeccionSiguientePeriodoDTO proyeccion = invocarExpectativa(base(), null, true);
        EscenarioProyeccionDTO escenario = proyeccion.getEscenarios().get(0);
        assertEquals(escenario.getResultadoHipotetico(), "NO_DETERMINADO");
        assertTrue(escenario.getRecomendaciones().get(0).contains("no pudo completarse"));
    }

    @Test
    public void expectativaRepetidaEsDeterministaYNoMutaBase() throws Exception {
        ContextoEvaluacionCurricularV2DTO base = base();
        ResultadoEvaluacionCurricularV2DTO resultado = resultado("IRREGULAR");
        ProyeccionSiguientePeriodoDTO primera = invocarExpectativa(base, resultado, false);
        ProyeccionSiguientePeriodoDTO segunda = invocarExpectativa(base, resultado, false);
        assertEquals(primera.getEscenarios().get(0).getResultadoHipotetico(), segunda.getEscenarios().get(0).getResultadoHipotetico());
        assertEquals(base.getUnidades().get(0).getEstado(), EstadoUnidadCurricularV2.EN_CURSO);
    }

    @Test
    public void expectativaConMotorYAdaptadorRealesActivaRn64AlPasarDeTresACuatroReprobadas() throws Exception {
        AsistenteInscripcionContextoDTO contexto = contextoExpectativaReal(3, false);
        ProyeccionSiguientePeriodoDTO proyeccion = invocarExpectativaReal(contexto, materiaEnCurso(104L, "UD-104"));

        assertEquals(proyeccion.getEscenarios().size(), 3);
        assertEquals(proyeccion.getEscenarios().get(0).getReprobadasVigentes(), Integer.valueOf(3));
        assertEquals(proyeccion.getEscenarios().get(1).getReprobadasVigentes(), Integer.valueOf(3));
        assertEquals(proyeccion.getEscenarios().get(2).getReprobadasVigentes(), Integer.valueOf(4));
        assertEquals(contexto.getUnidades().get(3).getEstatusHistorico(), "En curso");
    }

    @Test
    public void expectativaConMotorYAdaptadorRealesDesactivaRn64SinRecuperarRegularidadPorAdeudo() throws Exception {
        AsistenteInscripcionContextoDTO contexto = contextoExpectativaReal(4, true);
        ProyeccionSiguientePeriodoDTO proyeccion = invocarExpectativaReal(contexto, materiaEnCurso(104L, "UD-104"));

        assertEquals(proyeccion.getEscenarios().get(0).getReprobadasVigentes(), Integer.valueOf(4));
        assertEquals(proyeccion.getEscenarios().get(1).getReprobadasVigentes(), Integer.valueOf(3));
        assertEquals(proyeccion.getEscenarios().get(1).getResultadoHipotetico(), "IRREGULAR");
        assertEquals(contexto.getUnidades().get(3).getEstatusHistorico(), "Reprobada");
    }

    @Test
    public void expectativaSeleccionadaReevaluaConjuntoDeResultadosSinMutarLaBase() throws Exception {
        AsistenteInscripcionContextoDTO contexto = contextoExpectativaReal(3, false);
        ProyeccionSiguientePeriodoDTO proyeccion = invocarExpectativaSeleccionadaReal(contexto,
                materiaEnCurso(104L, "UD-104"), Collections.singleton(Long.valueOf(104L)));

        assertEquals(proyeccion.getEscenarios().size(), 3);
        assertEquals(proyeccion.getEscenarios().get(1).getReprobadasVigentes(), Integer.valueOf(3));
        assertEquals(proyeccion.getEscenarios().get(2).getReprobadasVigentes(), Integer.valueOf(4));
        assertEquals(contexto.getUnidades().get(3).getEstatusHistorico(), "En curso");
    }

    private ProyeccionSiguientePeriodoDTO invocarExpectativa(ContextoEvaluacionCurricularV2DTO base,
            ResultadoEvaluacionCurricularV2DTO resultado, boolean falla) throws Exception {
        AsistenteInscripcionServiceImpl servicio = new AsistenteInscripcionServiceImpl();
        ContextoEvaluacionCurricularV2Adapter adaptador = Mockito.mock(ContextoEvaluacionCurricularV2Adapter.class);
        EvaluacionCurricularV2Service evaluador = Mockito.mock(EvaluacionCurricularV2Service.class);
        Mockito.when(adaptador.construirDesdeAsistente(Mockito.any(AsistenteInscripcionContextoDTO.class))).thenReturn(base);
        if (falla) Mockito.when(evaluador.evaluarSombra(Mockito.any(ContextoEvaluacionCurricularV2DTO.class))).thenThrow(new IllegalStateException("error"));
        else Mockito.when(evaluador.evaluarSombra(Mockito.any(ContextoEvaluacionCurricularV2DTO.class))).thenReturn(resultado);
        inyectar(servicio, "contextoEvaluacionCurricularV2Adapter", adaptador);
        inyectar(servicio, "evaluacionCurricularV2Service", evaluador);
        Method metodo = AsistenteInscripcionServiceImpl.class.getDeclaredMethod("construirProyeccionSiguientePeriodoV2", AsistenteInscripcionContextoDTO.class, List.class);
        metodo.setAccessible(true);
        return (ProyeccionSiguientePeriodoDTO) metodo.invoke(servicio, new AsistenteInscripcionContextoDTO(), Collections.emptyList());
    }

    private ProyeccionSiguientePeriodoDTO invocarExpectativaReal(AsistenteInscripcionContextoDTO contexto,
            InscripcionPreviaMateriasDTO materiaEnCurso) throws Exception {
        AsistenteInscripcionServiceImpl servicio = new AsistenteInscripcionServiceImpl();
        inyectar(servicio, "contextoEvaluacionCurricularV2Adapter", new ContextoEvaluacionCurricularV2AdapterImpl());
        inyectar(servicio, "evaluacionCurricularV2Service", new EvaluacionCurricularV2ServiceImpl());
        Method metodo = AsistenteInscripcionServiceImpl.class.getDeclaredMethod("construirProyeccionSiguientePeriodoV2", AsistenteInscripcionContextoDTO.class, List.class);
        metodo.setAccessible(true);
        return (ProyeccionSiguientePeriodoDTO) metodo.invoke(servicio, contexto, Collections.singletonList(materiaEnCurso));
    }

    private ProyeccionSiguientePeriodoDTO invocarExpectativaSeleccionadaReal(AsistenteInscripcionContextoDTO contexto,
            InscripcionPreviaMateriasDTO materiaEnCurso, java.util.Set<Long> noAcreditadas) throws Exception {
        AsistenteInscripcionServiceImpl servicio = new AsistenteInscripcionServiceImpl();
        inyectar(servicio, "contextoEvaluacionCurricularV2Adapter", new ContextoEvaluacionCurricularV2AdapterImpl());
        inyectar(servicio, "evaluacionCurricularV2Service", new EvaluacionCurricularV2ServiceImpl());
        Method metodo = AsistenteInscripcionServiceImpl.class.getDeclaredMethod("construirExpectativaSeleccionadaV2",
                AsistenteInscripcionContextoDTO.class, List.class, java.util.Set.class);
        metodo.setAccessible(true);
        return (ProyeccionSiguientePeriodoDTO) metodo.invoke(servicio, contexto,
                Collections.singletonList(materiaEnCurso), noAcreditadas);
    }

    private AsistenteInscripcionContextoDTO contextoExpectativaReal(int reprobadas, boolean ultimaEnCursoEsReprobada) {
        AsistenteInscripcionContextoDTO contexto = new AsistenteInscripcionContextoDTO();
        contexto.setPeriodoActivo("2027-1");
        InscripcionPersonaDTO persona = new InscripcionPersonaDTO();
        persona.setIdPersona(Long.valueOf(91L));
        persona.setIdPlan(Long.valueOf(12L));
        EstadoAcademicoDTO estado = new EstadoAcademicoDTO();
        java.util.ArrayList<InscripcionMateriasReprobadasDTO> historico = new java.util.ArrayList<InscripcionMateriasReprobadasDTO>();
        java.util.ArrayList<UnidadDecisionInscripcionDTO> unidades = new java.util.ArrayList<UnidadDecisionInscripcionDTO>();
        for (int indice = 0; indice < reprobadas; indice++) {
            Long id = Long.valueOf(101L + indice);
            UnidadDecisionInscripcionDTO unidad = unidadReal(id, "UD-" + id, "Reprobada", 4);
            if (indice == reprobadas - 1 && ultimaEnCursoEsReprobada) unidad.setEstatusPeriodo("En curso");
            unidades.add(unidad);
            InscripcionMateriasReprobadasDTO reprobada = new InscripcionMateriasReprobadasDTO();
            reprobada.setIdPrograma(id);
            reprobada.setIntentosReprobados(Long.valueOf(1L));
            historico.add(reprobada);
        }
        if (!ultimaEnCursoEsReprobada) unidades.add(unidadReal(Long.valueOf(104L), "UD-104", "En curso", 4));
        estado.setMateriasReprobadas(historico);
        InscripcionContextoDTO base = new InscripcionContextoDTO();
        base.setInscripcionPersona(persona);
        base.setEstadoAcademico(estado);
        contexto.setContextoBase(base);
        contexto.setUnidades(unidades);
        return contexto;
    }

    private UnidadDecisionInscripcionDTO unidadReal(Long id, String clave, String estado, int semestre) {
        UnidadDecisionInscripcionDTO unidad = new UnidadDecisionInscripcionDTO();
        unidad.setUdId(id);
        unidad.setClave(clave);
        unidad.setNombre("Unidad " + clave);
        unidad.setTipoUd("OBLIGATORIA");
        unidad.setSemestre(Integer.valueOf(semestre));
        unidad.setBloque(Integer.valueOf(1));
        unidad.setEstatusHistorico(estado);
        unidad.setOfertada(Boolean.TRUE);
        return unidad;
    }

    private InscripcionPreviaMateriasDTO materiaEnCurso(Long id, String clave) {
        InscripcionPreviaMateriasDTO materia = new InscripcionPreviaMateriasDTO();
        materia.setIdPrograma(id);
        materia.setClavePrograma(clave);
        return materia;
    }

    private void inyectar(Object destino, String nombre, Object valor) throws Exception { Field campo = destino.getClass().getDeclaredField(nombre); campo.setAccessible(true); campo.set(destino, valor); }
    private ContextoEvaluacionCurricularV2DTO base() { ContextoEvaluacionCurricularV2DTO c=new ContextoEvaluacionCurricularV2DTO(); UnidadEvaluacionCurricularV2DTO u=new UnidadEvaluacionCurricularV2DTO();u.setIdPrograma(1L);u.setEstado(EstadoUnidadCurricularV2.EN_CURSO);c.getUnidades().add(u);return c; }
    private ResultadoEvaluacionCurricularV2DTO resultado(String situacion) { ResultadoEvaluacionCurricularV2DTO r=new ResultadoEvaluacionCurricularV2DTO();r.setSituacionAcademicaCalculada(situacion);return r; }

    private IntentosAsignaturasDTO intento(Long idPrograma, Long cantidad) {
        IntentosAsignaturasDTO dto = new IntentosAsignaturasDTO();
        dto.setIdPrograma(idPrograma);
        dto.setIntentosReprobados(cantidad);
        return dto;
    }

    private InscripcionBajasDTO baja(Long idBaja, Long idPrograma, String tipo) {
        InscripcionBajasDTO baja = new InscripcionBajasDTO();
        baja.setIdBaja(idBaja);
        baja.setIdPrograma(idPrograma);
        baja.setTipoBaja(tipo);
        return baja;
    }
}
