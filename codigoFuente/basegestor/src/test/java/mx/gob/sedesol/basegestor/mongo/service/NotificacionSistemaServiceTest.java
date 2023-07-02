package mx.gob.sedesol.basegestor.mongo.service;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import mx.gob.sedesol.basegestor.commons.dto.admin.NotificacionSistemaDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.VariableMensajeOperacionDTO;
import mx.gob.sedesol.basegestor.commons.utils.ResultadoTransaccionEnum;
import mx.gob.sedesol.basegestor.commons.utils.TipoNotificacionEnum;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath*:spring/applicationContext.xml"})
public class NotificacionSistemaServiceTest {

	@Autowired
	private NotificacionSistemaService notificacionSistemaService;
	
	@Test
	public void enviaNotificacionSistemaTest() {
		List<VariableMensajeOperacionDTO> variables = new ArrayList<VariableMensajeOperacionDTO>();
		variables.add(new VariableMensajeOperacionDTO("{rol}", "Adminitrador del sistema"));
		variables.add(new VariableMensajeOperacionDTO("%nueva%", "4578"));
		variables.add(new VariableMensajeOperacionDTO("{elemento", "RJ237823"));
		ResultadoDTO resultado = notificacionSistemaService.enviarNotificacion("ROL_AGR", 1L, variables);
		
		assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.EXITOSO));
	}
	
	@Test
	public void actualizarFechaTest() {
		ResultadoDTO resultado = notificacionSistemaService.actualizarFechaVisualizacion(1L, "57bdd632d7d4a11b940d1126");
		
		assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.EXITOSO));
	}
	
	@Test
	public void actualizarEstatusTest() {
		ResultadoDTO resultado = notificacionSistemaService.actualizarEstatus(1L, "57bdd632d7d4a11b940d1126");
		
		assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.EXITOSO));
	}
	
	//@Test
	public void contarMensajesTest() {
		long numeroMensajes = notificacionSistemaService.obtenerNumeroNotificacionesNuevas(1L, null, null);

		assertThat(numeroMensajes, is(2L));
	}
	
	//@Test
	public void contarAlertasTest() {
		long numeroMensajes = notificacionSistemaService.obtenerNumeroNotificacionesNuevas(1L, TipoNotificacionEnum.ALERTA, null);

		assertThat(numeroMensajes, is(2L));
	}
	
	//@Test
	public void contarAvisosTest() {
		long numeroMensajes = notificacionSistemaService.obtenerNumeroNotificacionesNuevas(1L, TipoNotificacionEnum.AVISO, null);

		assertThat(numeroMensajes, is(1L));
	}
	
	@Test
	public void obtenerNotificacionesSistemaTest() {
		List<NotificacionSistemaDTO> notificaciones = notificacionSistemaService.obtenerNotificaciones(1L, null, null);
		assertThat(notificaciones, is(not(empty())));
	}
	
	//@Test
	public void obtenerAlertasSistemaTest() {
		List<NotificacionSistemaDTO> notificaciones = notificacionSistemaService.obtenerNotificaciones(1L, TipoNotificacionEnum.ALERTA, true);
		assertThat(notificaciones, is(not(empty())));
	}
	
	@Test
	public void obtenerAvisosSistemaTest() {
		List<NotificacionSistemaDTO> notificaciones = notificacionSistemaService.obtenerNotificaciones(1L, TipoNotificacionEnum.AVISO, true);
		assertThat(notificaciones, is(not(empty())));
	}

}
