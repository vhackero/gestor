package mx.gob.sedesol.gestorweb.ws.sigie;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import java.io.File;
import java.lang.reflect.Field;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import javax.servlet.DispatcherType;
import javax.xml.parsers.DocumentBuilderFactory;
import org.junit.Test;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.w3c.dom.*;
import mx.gob.sedesol.basegestor.commons.dto.integracion.ClienteApiDTO;
import mx.gob.sedesol.basegestor.service.integracion.ClienteApiService;

public class SigieHostValidatorTest {
    @Test public void admiteIpv4Ipv6YPuertos() {
        for (String host : new String[]{"192.0.2.10", "192.0.2.10:8443", "127.0.0.1",
                "[2001:db8::10]", "[2001:db8::10]:443", "[::1]", "[::ffff:192.0.2.1]:8080"}) {
            assertTrue(host, SigieHostValidator.esAutoridadIp(host));
        }
    }

    @Test public void rechazaDominiosYAutoridadesMalformadas() {
        for (String host : new String[]{null, "", "localhost", "gestor.example.org",
                "gestor.example.org:443", "192.0.2.10.example.org", "192.0.2.10.",
                "2130706433", "127.1", "0x7f000001", "0177.0.0.1", "256.0.0.1",
                "192.0.2.10:", "192.0.2.10:0", "192.0.2.10:65536", "192.0.2.10:-1",
                "usuario@192.0.2.10", "192.0.2.10/servicio", "192.0.2.10?x=1",
                "192.0.2.10#x", "192.0.2.10,192.0.2.11", " 192.0.2.10",
                "192.0.2.10\r\nX-Test: 1", "[2001:db8:::1]", "2001:db8::1",
                "[localhost]", "[fe80::1%25eth0]", "%31%39%32.0.2.10", "https://192.0.2.10"}) {
            assertFalse(String.valueOf(host), SigieHostValidator.esAutoridadIp(host));
        }
    }


    @Test public void dominioSeRechazaAntesDeAutenticarAunqueForwardedIndiqueIp() throws Exception {
        for (String ruta : new String[]{"/SIGIE/v1/estudiantes/ES000000001", "/ws/v1/estudiantes/ES000000001"}) {
            MockHttpServletRequest request = solicitud("gestor.example.org");
            request.setRequestURI(ruta);
            request.setServerName("192.0.2.10");
            request.addHeader("X-Forwarded-Host", "192.0.2.10");
            request.addHeader("Forwarded", "host=192.0.2.10");
            ClienteApiService service = mock(ClienteApiService.class);
            MockHttpServletResponse response = ejecutar(request, service, false);
            assertEquals(403, response.getStatus());
            assertTrue(response.getContentAsString().contains("ACCESO_SOLO_POR_IP"));
            assertTrue(response.getContentAsString().contains("folio_consulta"));
            assertEquals("no-store", response.getHeader("Cache-Control"));
            assertFalse(response.getContentAsString().contains("gestor.example.org"));
            assertNull(response.getHeader("Location"));
            verifyZeroInteractions(service);
        }
    }

    @Test public void autoridadInterpretadaComoDominioSeRechazaAunqueHostSeaIp() throws Exception {
        MockHttpServletRequest request = spy(solicitud("192.0.2.10"));
        doReturn("gestor.example.org").when(request).getServerName();
        assertEquals(403, ejecutar(request, mock(ClienteApiService.class), false).getStatus());
    }

    @Test public void faltaHostODuplicadoSeRechazan() throws Exception {
        assertEquals(403, ejecutar(solicitud(null), mock(ClienteApiService.class), false).getStatus());
        MockHttpServletRequest request = solicitud("192.0.2.10");
        request.addHeader("Host", "gestor.example.org");
        assertEquals(403, ejecutar(request, mock(ClienteApiService.class), false).getStatus());
    }

    @Test public void ipSinCredencialesSigueRequiriendoAutenticacion() throws Exception {
        for (String host : new String[]{"192.0.2.10:8443", "[2001:db8::10]:443"}) {
            assertEquals(401, ejecutar(solicitud(host), mock(ClienteApiService.class), false).getStatus());
        }
    }

    @Test public void ipConCredencialesValidasContinuaLaCadena() throws Exception {
        for (String host : new String[]{"192.0.2.10", "[2001:db8::10]:443"}) {
            MockHttpServletRequest request = solicitud(host);
            request.addHeader("Authorization", "Basic " + Base64.getEncoder().encodeToString(
                    "siss:secreto".getBytes(StandardCharsets.UTF_8)));
            ClienteApiDTO cliente = new ClienteApiDTO();
            cliente.setClientId("siss");
            cliente.setScope("sigie:estudiantes:read");
            ClienteApiService service = mock(ClienteApiService.class);
            when(service.autenticar("siss","secreto")).thenReturn(cliente);
            assertEquals(200, ejecutar(request, service, true).getStatus());
        }
    }

    @Test public void todosLosMetodosYDespachosRechazanDominio() throws Exception {
        for (String metodo : new String[]{"GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD"}) {
            for (DispatcherType tipo : new DispatcherType[]{DispatcherType.REQUEST, DispatcherType.FORWARD, DispatcherType.ASYNC}) {
                MockHttpServletRequest request = solicitud("gestor.example.org");
                request.setMethod(metodo);
                request.setDispatcherType(tipo);
                assertEquals(403, ejecutar(request, mock(ClienteApiService.class), false).getStatus());
            }
        }
    }

    @Test public void descriptoresCubrenSoloSigieYSuAliasAntesDeOtrosFiltros() throws Exception {
        File base = new File("WebContent/WEB-INF/web.xml").isFile() ? new File(".") : new File("codigoFuente/gestorWeb");
        for (String ruta : new String[]{"WebContent/WEB-INF/web.xml", "DeployConfig/Desarrollo/web.xml"}) {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            Document doc = factory.newDocumentBuilder().parse(new File(base, ruta));
            NodeList mappings = doc.getElementsByTagNameNS("*", "filter-mapping");
            Element primero = (Element) mappings.item(0);
            assertEquals("sigieSecurityFilter", primero.getElementsByTagNameNS("*","filter-name").item(0).getTextContent().trim());
            NodeList patrones = primero.getElementsByTagNameNS("*","url-pattern");
            assertEquals(3, patrones.getLength());
            assertEquals("/SIGIE/*", patrones.item(0).getTextContent().trim());
            assertEquals("/ws/v1/estudiantes", patrones.item(1).getTextContent().trim());
            assertEquals("/ws/v1/estudiantes/*", patrones.item(2).getTextContent().trim());
            assertEquals(0, primero.getElementsByTagNameNS("*","servlet-name").getLength());
            for (String tipo : new String[]{"REQUEST","FORWARD","ASYNC"}) assertTrue(primero.getTextContent().contains(tipo));
        }
    }

    private MockHttpServletRequest solicitud(String host) throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/SIGIE/v1/estudiantes/ES000000001");
        if (host != null) {
            request.addHeader("Host", host);
            request.setServerName(new URI("http://" + host).getHost());
        }
        return request;
    }

    private MockHttpServletResponse ejecutar(MockHttpServletRequest request, ClienteApiService service, boolean continuar) throws Exception {
        SigieSecurityFilter filter = new SigieSecurityFilter();
        Field campo = SigieSecurityFilter.class.getDeclaredField("clienteApiService");
        campo.setAccessible(true);
        campo.set(filter, service);
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockFilterChain chain = new MockFilterChain();
        filter.doFilter(request, response, chain);
        assertEquals(continuar, chain.getRequest() != null);
        return response;
    }
}
