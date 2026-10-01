package mx.gob.sedesol.basegestor.gestionescolar;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.lang.reflect.Field;
import java.util.*;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.junit.Test;
import org.mockito.ArgumentCaptor;

import mx.gob.sedesol.basegestor.commons.dto.admin.*;
import mx.gob.sedesol.basegestor.commons.dto.gestion.aprendizaje.EventoConstanciaDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.*;
import mx.gob.sedesol.basegestor.commons.utils.*;
import mx.gob.sedesol.basegestor.model.entities.admin.CatParametroSistema;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.RelGrupoParticipante;
import mx.gob.sedesol.basegestor.model.repositories.admin.ParametroSistemaRepo;
import mx.gob.sedesol.basegestor.model.repositories.gestionescolar.*;
import mx.gob.sedesol.basegestor.service.ParametroSistemaService;
import mx.gob.sedesol.basegestor.service.impl.admin.ParametroSistemaServiceImpl;
import mx.gob.sedesol.basegestor.service.impl.gestionescolar.*;

public class BajasNpConfigurablesTest {
    private static final String CLAVE = "TIPOS_BAJA_NP_ACTA";

    private static void inyectar(Object destino, String nombre, Object valor) throws Exception {
        Field campo = destino.getClass().getDeclaredField(nombre);
        campo.setAccessible(true);
        campo.set(destino, valor);
    }

    @Test
    public void admiteDerivacionesEspaciosYDuplicados() {
        assertEquals(Arrays.asList(1, 2, 7, 8), TiposBajaNp.parsear(" 1, 2,7,8,7 "));
        assertEquals(Arrays.asList(8), TiposBajaNp.parsear("8"));
    }

    @Test
    public void rechazaListasInvalidasCompletas() {
        for (String valor : Arrays.asList(null, "", " ", "1,", ",2", "1,,7", "0", "-1",
                "1;2", "1,abc", "1,2147483648", "1,2 OR 1=1", "1.0", "+1")) {
            try {
                TiposBajaNp.parsear(valor);
                fail("Se acepto un valor invalido: " + valor);
            } catch (IllegalArgumentException esperado) {
                assertEquals(TiposBajaNp.MENSAJE_FORMATO, esperado.getMessage());
            }
        }
    }

    @Test
    public void consultaConfiguracionEnCadaLecturaYUsaCompatibilidadSoloSiFalta() throws Exception {
        ParametroSistemaRepo repo = mock(ParametroSistemaRepo.class);
        ParametroSistemaServiceImpl servicio = new ParametroSistemaServiceImpl();
        inyectar(servicio, "parametroSistemaRepo", repo);
        CatParametroSistema primero = new CatParametroSistema();
        primero.setValor("1,2,7,8");
        CatParametroSistema segundo = new CatParametroSistema();
        segundo.setValor("7");
        when(repo.findOne(CLAVE)).thenReturn(null, primero, segundo);
        assertEquals(Arrays.asList(1, 2), servicio.obtenerTiposBajaNp());
        assertEquals(Arrays.asList(1, 2, 7, 8), servicio.obtenerTiposBajaNp());
        assertEquals(Arrays.asList(7), servicio.obtenerTiposBajaNp());
    }

    @Test
    public void noPersisteConfiguracionInvalidaNiRestringeOtrosParametros() throws Exception {
        ParametroSistemaRepo repo = mock(ParametroSistemaRepo.class);
        ParametroSistemaServiceImpl servicio = new ParametroSistemaServiceImpl();
        inyectar(servicio, "parametroSistemaRepo", repo);
        ParametroSistemaDTO dto = new ParametroSistemaDTO(1L);
        dto.setFechaActualizacion(new Date());
        dto.setClave(CLAVE);
        dto.setValor("1,incorrecto");
        assertFalse(servicio.guardar(dto).esCorrecto());
        assertFalse(servicio.actualizar(dto).esCorrecto());
        verifyZeroInteractions(repo);
        dto.setClave("OTRO_PARAMETRO");
        ResultadoDTO<ParametroSistemaDTO> resultado = new ResultadoDTO<>();
        servicio.validarActualizacion(dto, resultado);
        assertTrue(resultado.esCorrecto());
    }

    @Test
    public void actaUsaListaConfiguradaYConservaNotaDeQuienNoTieneBaja() throws Exception {
        EventoCapacitacionRepo repo = mock(EventoCapacitacionRepo.class);
        ParametroSistemaService parametros = mock(ParametroSistemaService.class);
        EventoCapacitacionServiceImpl servicio = new EventoCapacitacionServiceImpl();
        inyectar(servicio, "eventoCapacitacionRepo", repo);
        inyectar(servicio, "parametroSistemaService", parametros);
        when(parametros.obtenerTiposBajaNp()).thenReturn(Arrays.asList(7, 8));
        BajasDTO baja = mock(BajasDTO.class);
        when(baja.getIdpersonamoodle()).thenReturn(11);
        when(repo.obtenerBajas(10, 20, Arrays.asList(11, 12), Arrays.asList(7, 8)))
                .thenReturn(Arrays.asList(baja));
        TablaCalificacionesDTO conBaja = calificacion(11, 50.0);
        TablaCalificacionesDTO sinBaja = calificacion(12, 80.0);
        List<CalificacionRecordDTO> detalle = servicio.obtieneDetalleActa(10, 20, Arrays.asList(conBaja, sinBaja));
        assertEquals("NP", detalle.get(0).getCalificacion());
        assertEquals("80", detalle.get(1).getCalificacion());
        assertEquals(Double.valueOf(50), conBaja.getCalifFinal());
        verify(repo).obtenerBajas(10, 20, Arrays.asList(11, 12), Arrays.asList(7, 8));
    }

    @Test
    public void actaSinParticipantesNoEjecutaUnInVacio() throws Exception {
        EventoCapacitacionRepo repo = mock(EventoCapacitacionRepo.class);
        EventoCapacitacionServiceImpl servicio = new EventoCapacitacionServiceImpl();
        inyectar(servicio, "eventoCapacitacionRepo", repo);
        assertTrue(servicio.obtieneDetalleActa(10, 20, Collections.<TablaCalificacionesDTO>emptyList()).isEmpty());
        verifyZeroInteractions(repo);
    }

    @Test
    public void historialYDescargaDeCalificacionesVinculanLaMismaLista() throws Exception {
        HistorialAcademicoRepo repo = new HistorialAcademicoRepo();
        EntityManager em = mock(EntityManager.class);
        Query query = mock(Query.class);
        ParametroSistemaService parametros = mock(ParametroSistemaService.class);
        inyectar(repo, "parametroSistemaService", parametros);
        repo.entityManager = em;
        when(parametros.obtenerTiposBajaNp()).thenReturn(Arrays.asList(7, 8));
        when(em.createNativeQuery(anyString())).thenReturn(query);
        when(query.getResultList()).thenReturn(Collections.emptyList());
        repo.consultaDatosHistorialAcademico("100");
        repo.consultaTiraMateriasBaja2(100L);
        repo.consultaCuerpoPdf(10);
        verify(query, times(3)).setParameter("tiposBajaNp", Arrays.asList(7, 8));
        ArgumentCaptor<String> sql = ArgumentCaptor.forClass(String.class);
        verify(em, times(3)).createNativeQuery(sql.capture());
        for (String consulta : sql.getAllValues()) {
            assertTrue(consulta.contains("IN (:tiposBajaNp)"));
            assertTrue(consulta.contains("contabilizar = 1"));
        }
        assertTrue(sql.getAllValues().get(2).contains("THEN 'NP'"));
    }

    @Test
    public void bajaEnUnGrupoNoDejaNulaLaNotaDeOtroGrupo() throws Exception {
        GrupoParticipanteRepo repo = mock(GrupoParticipanteRepo.class);
        IHistorialAcademicoRepo historial = mock(IHistorialAcademicoRepo.class);
        GrupoParticipanteServiceImpl servicio = new GrupoParticipanteServiceImpl();
        inyectar(servicio, "grupoParticipanteRepo", repo);
        inyectar(servicio, "iHistorialAcademicoRepo", historial);
        inyectar(servicio, "parametroSistemaService", mock(ParametroSistemaService.class));
        RelGrupoParticipante primero = participante(10, 60.0);
        RelGrupoParticipante segundo = participante(20, 90.0);
        RelGrupoParticipante tercero = participante(30, null);
        when(repo.getParticipanteByActaCerradaYconstancia(100L)).thenReturn(Arrays.asList(primero, segundo, tercero));
        when(historial.getParticipanteByActaCerradaYconstancia2("100")).thenReturn(Collections.<HistorialAcademicoListaDTO>emptyList());
        TiraMateriaBaja2DTO baja = new TiraMateriaBaja2DTO();
        baja.setIdGrupo(10);
        when(historial.consultaTiraMateriasBaja2(100L)).thenReturn(Arrays.asList(baja));
        List<EventoConstanciaDTO> eventos = servicio.getParticipanteByActaCerradaYconstancia2(100L);
        assertEquals(Double.valueOf(666), eventos.get(0).getCalificacionFinal());
        assertEquals("NP", eventos.get(0).getCalificacion());
        assertEquals(Double.valueOf(90), eventos.get(1).getCalificacionFinal());
        assertNull(eventos.get(2).getCalificacionFinal());
        assertEquals("", eventos.get(2).getCalificacionFinalTexto());
        verify(primero, never()).setCalifFinal(anyDouble());
        verify(segundo, never()).setCalifFinal(anyDouble());
    }

    private TablaCalificacionesDTO calificacion(int idMoodle, double nota) {
        PersonaDTO persona = new PersonaDTO();
        persona.setUsuario("prueba" + idMoodle);
        persona.setNombre("Prueba");
        persona.setApellidoPaterno("Uno");
        persona.setApellidoMaterno("Dos");
        TablaCalificacionesDTO dto = new TablaCalificacionesDTO();
        dto.setParticipante(persona);
        dto.setIdPersonaMoodle(idMoodle);
        dto.setCalifFinal(nota);
        return dto;
    }

    private RelGrupoParticipante participante(int idGrupo, Double nota) {
        RelGrupoParticipante gp = mock(RelGrupoParticipante.class, RETURNS_DEEP_STUBS);
        when(gp.getGrupo().getIdGrupo()).thenReturn(idGrupo);
        when(gp.getCalifFinal()).thenReturn(nota);
        when(gp.getGrupo().getEvento().getCalificacionMinAprobatoria()).thenReturn("60");
        when(gp.getGrupo().getEvento().getAplicaEncuesta()).thenReturn(false);
        when(gp.getGrupo().getEvento().getFichaDescriptivaPrograma().getRelProgramaDuracion())
                .thenReturn(Collections.emptyList());
        return gp;
    }
}
