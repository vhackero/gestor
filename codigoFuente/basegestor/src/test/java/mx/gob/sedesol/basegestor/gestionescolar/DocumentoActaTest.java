package mx.gob.sedesol.basegestor.gestionescolar;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import java.util.Collections;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.Acta;
import mx.gob.sedesol.basegestor.model.repositories.gestionescolar.IActaRepository;
import mx.gob.sedesol.basegestor.service.impl.gestionescolar.ActaServiceImpl;
import mx.gob.sedesol.basegestor.service.impl.gestionescolar.EventoCapacitacionServiceImpl;
import mx.gob.sedesol.basegestor.model.repositories.gestionescolar.EventoCapacitacionRepo;

public class DocumentoActaTest {
	private IActaRepository repo;
	private ActaServiceImpl servicio;
	private Acta documento;

	@Before public void preparar() {
		repo = mock(IActaRepository.class);
		servicio = new ActaServiceImpl();
		ReflectionTestUtils.setField(servicio, "iCargaActaRepository", repo);
		when(repo.bloquearGrupo(10)).thenReturn(10);
		documento = new Acta();
		documento.setIdActa(20);
		documento.setGrupo(10);
		when(repo.consultarMetadatosPorId(20)).thenReturn(Collections.singletonList(new Object[] { 20, 10, null, 100L, 1000L, 1 }));
	}

	@Test public void retiroEliminaSoloElDocumentoElegido() {
		servicio.retirarDocumento(20, 10);
		verify(repo).bloquearGrupo(10);
		verify(repo).consultarMetadatosPorId(20);
		verify(repo).borraPorIdActa(20);
		verify(repo).flush();
		verifyNoMoreInteractions(repo);
	}

	@Test(expected = IllegalArgumentException.class) public void rechazoDocumentoDeOtroGrupo() {
		when(repo.consultarMetadatosPorId(20)).thenReturn(Collections.singletonList(new Object[] { 20, 11, null, 100L, 1000L, 1 }));
		try { servicio.retirarDocumento(20, 10); }
		finally { verify(repo, never()).borraPorIdActa(anyInt()); }
	}

	@Test public void reemplazoConservaIdentidadDelDocumento() throws Exception {
		byte[] pdf = ConsultaActaTest.pdf(false);
		when(repo.reemplazarDocumento(eq(20), eq(10), eq(pdf), any(java.util.Date.class), eq(100L))).thenReturn(1);
		servicio.guardarDocumento(20, 10, pdf, 100L);
		verify(repo).reemplazarDocumento(eq(20), eq(10), eq(pdf), any(java.util.Date.class), eq(100L));
		verify(repo, never()).findOne(anyInt());
		verify(repo, never()).consultarArchivoAcotado(anyInt());
		verify(repo, never()).saveAndFlush(any(Acta.class));
	}

	@Test(expected = IllegalArgumentException.class) public void noCargaDuplicadosAunquePantallaEsteDesactualizada() throws Exception {
		when(repo.contarPorGrupo(10)).thenReturn(1L);
		try { servicio.guardarDocumento(null, 10, ConsultaActaTest.pdf(false), 100L); }
		finally { verify(repo, never()).saveAndFlush(any(Acta.class)); }
	}

	@Test(expected = IllegalArgumentException.class) public void archivoInvalidoNoReemplazaElExistente() {
		try { servicio.guardarDocumento(20, 10, new byte[0], 100L); }
		finally { verify(repo, never()).saveAndFlush(any(Acta.class)); }
	}

	@Test public void estadoNuloOVariableNoSeConvierteEnAbierto() {
		when(repo.getActaByIdGrupo(10)).thenReturn(Collections.<Acta>emptyList());
		when(repo.consultarEstadoGrupo(10)).thenReturn(Collections.singletonList(new Object[] { 10, null, 0, 0, 0, 0 }));
		assertNull(servicio.consultarEstadoGrupo(10).getActaCerrada());
		when(repo.consultarEstadoGrupo(10)).thenReturn(Collections.singletonList(new Object[] { 10, 2, 0, 0, 0, 0 }));
		assertNull(servicio.consultarEstadoGrupo(10).getActaCerrada());
	}

	@Test public void consultaEstadoNoCargaNingunArchivo() {
		when(repo.consultarEstadoGrupo(10)).thenReturn(Collections.singletonList(new Object[] { 10, 1, 100, 99, 50, 2 }));
		assertEquals(50, servicio.consultarEstadoGrupo(10).getNumeroActas());
		verify(repo).consultarEstadoGrupo(10);
		verifyNoMoreInteractions(repo);
	}

	@Test public void busquedaClasificaGruposConUnaSolaConsultaEscalar() {
		EventoCapacitacionRepo eventos = mock(EventoCapacitacionRepo.class);
		EventoCapacitacionServiceImpl busqueda = new EventoCapacitacionServiceImpl();
		ReflectionTestUtils.setField(busqueda, "eventoCapacitacionRepo", eventos);
		when(eventos.consultarActasPorFiltros("2026", "1", Collections.singletonList(5L), ""))
				.thenReturn(Collections.singletonList(new Object[] {
						1L, "Plan", "Semestre", "Bloque", 5L, "Programa", 7L, "Evento", 10L, "Grupo",
						10L, 1, 100, 99, 50, 2 }));
		assertEquals(50, busqueda.consultarActasPorFiltros("2026", "1", Collections.singletonList(5L), 2)
				.get(0).getNumeroActas());
		verify(eventos).consultarActasPorFiltros("2026", "1", Collections.singletonList(5L), "");
		verifyNoMoreInteractions(eventos);
		verifyZeroInteractions(repo);
	}

	@Test public void listadoContieneSoloMetadatosInclusoConDocumentoGrande() {
		when(repo.consultarMetadatos(10)).thenReturn(Collections.singletonList(new Object[] { 20, 10, null, 100L, 100000000L, 0 }));
		assertNull(servicio.getActasByIdGrupo(10).get(0).getBlob());
		verify(repo).consultarMetadatos(10);
		verifyNoMoreInteractions(repo);
	}

	@Test(expected = IllegalArgumentException.class) public void archivoGrandeSeRechazaAntesDeCargarBlob() {
		when(repo.consultarMetadatosPorId(20)).thenReturn(Collections.singletonList(new Object[] { 20, 10, null, 100L, 100000000L, 0 }));
		try { servicio.buscarPorId(20); }
		finally { verify(repo, never()).consultarArchivoAcotado(anyInt()); }
	}

	@Test public void filtroTextoEsOpcionalYSeRecortanEspacios() {
		EventoCapacitacionRepo eventos = mock(EventoCapacitacionRepo.class);
		EventoCapacitacionServiceImpl busqueda = new EventoCapacitacionServiceImpl();
		ReflectionTestUtils.setField(busqueda, "eventoCapacitacionRepo", eventos);
		busqueda.consultarActasPorFiltros("2026", "1", Collections.singletonList(5L), -1, "  S1  ");
		verify(eventos).consultarActasPorFiltros("2026", "1", Collections.singletonList(5L), "S1");
		busqueda.consultarActasPorFiltros("2026", "1", Collections.singletonList(5L), -1, "   ");
		verify(eventos).consultarActasPorFiltros("2026", "1", Collections.singletonList(5L), "");
	}
}
