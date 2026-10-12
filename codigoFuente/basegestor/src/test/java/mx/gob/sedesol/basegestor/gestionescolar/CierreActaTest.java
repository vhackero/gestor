package mx.gob.sedesol.basegestor.gestionescolar;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import java.util.Collections;
import org.junit.Before;
import org.junit.Test;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.annotation.AnnotationTransactionAttributeSource;
import org.springframework.transaction.interceptor.TransactionInterceptor;
import org.springframework.transaction.support.AbstractPlatformTransactionManager;
import org.springframework.transaction.support.DefaultTransactionStatus;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.ActaDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.ConsultaActaEventoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.GrupoDTO;
import mx.gob.sedesol.basegestor.commons.utils.ResultadoTransaccionEnum;
import mx.gob.sedesol.basegestor.service.gestionescolar.ActaService;
import mx.gob.sedesol.basegestor.service.gestionescolar.GrupoService;
import mx.gob.sedesol.basegestor.service.gestionescolar.GrupoParticipanteService;
import mx.gob.sedesol.basegestor.service.gestionescolar.RelGpoEvaluacionService;
import mx.gob.sedesol.basegestor.service.impl.gestionescolar.EventoCapacitacionServiceFacade;

public class CierreActaTest {
	private ActaService actas;
	private GrupoService grupos;
	private GrupoParticipanteService participantes;
	private RelGpoEvaluacionService evaluaciones;
	private EventoCapacitacionServiceFacade facade;
	private ConsultaActaEventoDTO estado;
	private Transacciones transacciones;

	@Before public void preparar() throws Exception {
		actas = mock(ActaService.class);
		grupos = mock(GrupoService.class);
		participantes = mock(GrupoParticipanteService.class);
		evaluaciones = mock(RelGpoEvaluacionService.class);
		EventoCapacitacionServiceFacade destino = new EventoCapacitacionServiceFacade();
		ReflectionTestUtils.setField(destino, "actaService", actas);
		ReflectionTestUtils.setField(destino, "grupoService", grupos);
		ReflectionTestUtils.setField(destino, "grupoParticipanteService", participantes);
		ReflectionTestUtils.setField(destino, "relGpoEvaluacionService", evaluaciones);
		transacciones = new Transacciones();
		ProxyFactory proxy = new ProxyFactory(destino);
		proxy.setProxyTargetClass(true);
		proxy.addAdvice(new TransactionInterceptor(transacciones, new AnnotationTransactionAttributeSource()));
		facade = (EventoCapacitacionServiceFacade) proxy.getProxy();
		estado = ConsultaActaTest.estado(true, 1, 2, 2, 0);
		when(actas.consultarEstadoGrupo(10)).thenReturn(estado);
		ActaDTO documento = new ActaDTO(); documento.setIdActa(20); documento.setGrupo(10);
		when(actas.getActasByIdGrupo(10)).thenReturn(Collections.singletonList(documento));
		documento.setBlob(ConsultaActaTest.pdf(false));
		when(actas.buscarPorId(20)).thenReturn(documento);
		GrupoDTO grupo = new GrupoDTO(); grupo.setIdGrupo(10);
		when(grupos.buscarGrupoPorId(10)).thenReturn(grupo);
		when(grupos.actualizarGrupo(any(GrupoDTO.class), eq(100L))).thenReturn(new ResultadoDTO<GrupoDTO>());
	}

	@Test public void eliminarYReabrirConfirmaUnaSolaTransaccion() throws Exception {
		facade.eliminarActaYReabrir(10, 20, 100L);
		verify(actas).bloquearGrupo(10);
		verify(actas).retirarDocumento(20, 10);
		verify(evaluaciones).eliminaEvaluacionesByIdGrupo(10);
		verify(evaluaciones).eliminaGrupoEvaluacionByIdGrupo(10);
		verify(participantes).actualizaRelGrupoParticipantebyGrupo(10);
		assertEquals(1, transacciones.confirmadas);
		assertEquals(0, transacciones.revertidas);
	}

	@Test public void fallaDeReaperturaRevierteLaTransaccion() throws Exception {
		ResultadoDTO<GrupoDTO> error = new ResultadoDTO<GrupoDTO>();
		error.setResultado(ResultadoTransaccionEnum.FALLIDO);
		when(grupos.actualizarGrupo(any(GrupoDTO.class), eq(100L))).thenReturn(error);
		try { facade.eliminarActaYReabrir(10, 20, 100L); fail("Debe informar la falla"); }
		catch (Exception esperado) { assertEquals(1, transacciones.revertidas); }
		assertEquals(0, transacciones.confirmadas);
	}

	@Test public void duplicadosImpidenBorrarCalificaciones() throws Exception {
		estado.setNumeroActas(2);
		try { facade.eliminarActaYReabrir(10, 20, 100L); fail("Debe elegir el documento desde el listado"); }
		catch (IllegalArgumentException esperado) { }
		verify(actas, never()).retirarDocumento(anyInt(), anyInt());
		verifyZeroInteractions(evaluaciones, participantes, grupos);
	}

	@Test public void grupoCerradoSinDocumentoSePuedeReabrir() throws Exception {
		estado.setNumeroActas(0);
		when(actas.getActasByIdGrupo(10)).thenReturn(Collections.<ActaDTO>emptyList());
		facade.eliminarActaYReabrir(10, null, 100L);
		verify(actas, never()).retirarDocumento(anyInt(), anyInt());
		verify(participantes).actualizaRelGrupoParticipantebyGrupo(10);
		assertEquals(1, transacciones.confirmadas);
	}

	@Test public void completarCierreConservaDocumentosYCalificaciones() throws Exception {
		estado.setActaCerrada(false);
		facade.completarCierreActa(10, 100L);
		verify(grupos).actualizarGrupo(any(GrupoDTO.class), eq(100L));
		verifyZeroInteractions(evaluaciones, participantes);
		verify(actas, never()).retirarDocumento(anyInt(), anyInt());
		assertEquals(1, transacciones.confirmadas);
	}

	@Test public void completarCierreRevalidaCalificacionesYDocumento() throws Exception {
		estado.setActaCerrada(false);
		ConsultaActaEventoDTO[] invalidos = {
			ConsultaActaTest.estado(false, 0, 2, 2, 0),
			ConsultaActaTest.estado(false, 2, 2, 2, 0),
			ConsultaActaTest.estado(false, 1, 0, 0, 0),
			ConsultaActaTest.estado(false, 1, 2, 1, 0),
			ConsultaActaTest.estado(false, 1, 2, 2, 1)
		};
		for (ConsultaActaEventoDTO invalido : invalidos) {
			when(actas.consultarEstadoGrupo(10)).thenReturn(invalido);
			try { facade.completarCierreActa(10, 100L); fail("No debe cerrar un grupo inconsistente"); }
			catch (IllegalArgumentException esperado) { }
		}
		verifyZeroInteractions(grupos, evaluaciones, participantes);
		assertEquals(5, transacciones.revertidas);
	}

	/** Verifica el interceptor real de Spring; la persistencia se simula en estas pruebas. */
	private static class Transacciones extends AbstractPlatformTransactionManager {
		private static final long serialVersionUID = 1L;
		int confirmadas, revertidas;
		protected Object doGetTransaction() { return new Object(); }
		protected void doBegin(Object transaccion, TransactionDefinition definicion) { }
		protected void doCommit(DefaultTransactionStatus estado) { confirmadas++; }
		protected void doRollback(DefaultTransactionStatus estado) { revertidas++; }
	}
}
