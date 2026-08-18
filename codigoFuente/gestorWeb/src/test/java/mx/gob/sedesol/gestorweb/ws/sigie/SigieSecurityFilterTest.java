package mx.gob.sedesol.gestorweb.ws.sigie;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import mx.gob.sedesol.basegestor.commons.dto.integracion.ClienteApiDTO;
import mx.gob.sedesol.basegestor.service.integracion.ClienteApiService;

public class SigieSecurityFilterTest {
    private SigieSecurityFilter filter;
    private ClienteApiService service;

    @Before public void preparar() throws Exception {
        filter = new SigieSecurityFilter();
        service = mock(ClienteApiService.class);
        Field field = SigieSecurityFilter.class.getDeclaredField("clienteApiService");
        field.setAccessible(true);
        field.set(filter, service);
    }

    @Test public void credencialAusenteEs401YSinDatosSensibles() throws Exception {
        MockHttpServletResponse response = ejecutar(null, "127.0.0.1");
        assertEquals(401, response.getStatus());
        assertFalse(response.getContentAsString().contains("SQL"));
        assertFalse(response.getContentAsString().contains("stack"));
    }

    @Test public void credencialInvalidaEs401() throws Exception {
        when(service.autenticar("siss", "incorrecto")).thenReturn(null);
        assertEquals(401, ejecutar(basic("siss", "incorrecto"), "127.0.0.1").getStatus());
    }

    @Test public void scopeInvalidoEs403() throws Exception {
        when(service.autenticar("siss", "secreto")).thenReturn(cliente("otro:scope", null));
        assertEquals(403, ejecutar(basic("siss", "secreto"), "127.0.0.1").getStatus());
    }

    @Test public void ipNoPermitidaEs403() throws Exception {
        when(service.autenticar("siss", "secreto")).thenReturn(cliente("sigie:estudiantes:read", "10.0.0.5"));
        assertEquals(403, ejecutar(basic("siss", "secreto"), "127.0.0.1").getStatus());
    }

    @Test public void solicitudSesentaYUnoEs429() throws Exception {
        when(service.autenticar("siss", "secreto")).thenReturn(cliente("sigie:estudiantes:read", null));
        for (int i = 0; i < 60; i++) {
            assertEquals(200, ejecutar(basic("siss", "secreto"), "127.0.0.1").getStatus());
        }
        MockHttpServletResponse response = ejecutar(basic("siss", "secreto"), "127.0.0.1");
        assertEquals(429, response.getStatus());
        assertEquals("60", response.getHeader("Retry-After"));
    }

    private MockHttpServletResponse ejecutar(String authorization, String remoteAddress) throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/SIGIE/v1/estudiantes/ES241100064");
        request.setRemoteAddr(remoteAddress);
        if (authorization != null) { request.addHeader("Authorization", authorization); }
        MockHttpServletResponse response = new MockHttpServletResponse();
        filter.doFilter(request, response, new MockFilterChain());
        return response;
    }

    private ClienteApiDTO cliente(String scope, String ips) {
        ClienteApiDTO dto = new ClienteApiDTO();
        dto.setClientId("siss"); dto.setScope(scope); dto.setIpsPermitidas(ips);
        return dto;
    }

    private String basic(String clientId, String secret) {
        return "Basic " + Base64.getEncoder().encodeToString(
                (clientId + ":" + secret).getBytes(StandardCharsets.UTF_8));
    }
}
