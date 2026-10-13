package mx.gob.sedesol.gestorweb.beans.gestionescolar;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import java.util.Arrays;
import java.util.Collections;
import org.junit.After;
import org.junit.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import mx.gob.sedesol.gestorweb.config.security.CustomUserDetail;

public class PermisosEventosTest {
	private void permisos(String... permisos) {
		CustomUserDetail usuario = mock(CustomUserDetail.class);
		when(usuario.getPermisosRol()).thenReturn(Arrays.asList(permisos));
		SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(
				usuario, null, Collections.emptyList()));
	}
	@After public void limpiar() { SecurityContextHolder.clearContext(); }

	@Test public void permisosDeActasYEstatusSonIndependientesDeCrear() {
		EventoCapacitacionBean bean = mock(EventoCapacitacionBean.class, CALLS_REAL_METHODS);
		permisos("EC_AGR");
		assertFalse(bean.isPuedeGestionarActas()); assertFalse(bean.isPuedeModificarEstatus());
		permisos("EC_MOD_ESTATUS");
		assertFalse(bean.isPuedeGestionarActas()); assertTrue(bean.isPuedeModificarEstatus());
		permisos("EC_CON_ACTAS");
		assertTrue(bean.isPuedeGestionarActas()); assertFalse(bean.isPuedeModificarEstatus());
	}

	@Test(expected = SecurityException.class) public void permisoCrearNoPermiteActualizarEstatus() {
		permisos("EC_AGR");
		mock(EventoCapacitacionBean.class, CALLS_REAL_METHODS).actualizarModificarEstatus();
	}

	@Test(expected = SecurityException.class) public void permisoCrearNoPermiteConsultarActas() {
		permisos("EC_AGR");
		mock(EventoCapacitacionBean.class, CALLS_REAL_METHODS).buscarConsultarActas();
	}

	@Test public void permisoActasNoPermiteEntrarACrear() {
		permisos("EC_CON_ACTAS");
		CapturaEventoCapacitacion bean = mock(CapturaEventoCapacitacion.class, CALLS_REAL_METHODS);
		doNothing().when(bean).agregarMsgError(anyString(), anyString());
		assertNull(bean.nuevoEventoCapacitacion());
		verify(bean).agregarMsgError("No cuenta con permiso para crear eventos académicos.", null);
	}
}
