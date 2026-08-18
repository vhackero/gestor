package mx.gob.sedesol.gestorweb.ws.sigie;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.filter.OncePerRequestFilter;
import mx.gob.sedesol.basegestor.commons.dto.integracion.ClienteApiDTO;
import mx.gob.sedesol.basegestor.service.integracion.ClienteApiService;

public class SigieSecurityFilter extends OncePerRequestFilter {
    private static final Logger LOG = Logger.getLogger(SigieSecurityFilter.class);
    private static final String SCOPE_LECTURA = "sigie:estudiantes:read";
    private final Map<String, Ventana> ventanas = new ConcurrentHashMap<String, Ventana>();

    @Autowired
    private ClienteApiService clienteApiService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        long inicio = System.currentTimeMillis();
        String folio = SigieRequestContext.preparar(request);
        response.setHeader("X-Request-ID", request.getAttribute(SigieRequestContext.REQUEST_ID).toString());
        try {
            Credencial credencial = leerCredencial(request.getHeader("Authorization"));
            if (credencial == null) {
                error(response, 401, "NO_AUTORIZADO", "La credencial de acceso está ausente o no es válida.", folio);
                return;
            }
            ClienteApiDTO cliente = clienteApiService.autenticar(credencial.clientId, credencial.secret);
            if (cliente == null) {
                error(response, 401, "NO_AUTORIZADO", "La credencial de acceso está ausente o no es válida.", folio);
                return;
            }
            if (!contieneScope(cliente.getScope(), SCOPE_LECTURA) || !ipPermitida(cliente, request.getRemoteAddr())) {
                error(response, 403, "NO_AUTORIZADO", "El consumidor no tiene autorización para consultar estudiantes.", folio);
                return;
            }
            if (!permitir(cliente.getClientId())) {
                response.setHeader("Retry-After", String.valueOf(obtenerVentanaSegundos()));
                error(response, 429, "LIMITE_EXCEDIDO", "Se ha excedido temporalmente el límite de solicitudes.", folio);
                return;
            }
            request.setAttribute(SigieRequestContext.CLIENT_ID, cliente.getClientId());
            chain.doFilter(request, response);
        } catch (RuntimeException e) {
            LOG.error("folio=" + folio + " resultado=SERVICIO_NO_DISPONIBLE", e);
            error(response, 503, "SERVICIO_NO_DISPONIBLE", "El servicio no está disponible temporalmente.", folio);
        } finally {
            LOG.info("folio=" + folio + " metodo=" + request.getMethod()
                    + " endpoint=/SIGIE/v1/estudiantes/{matricula} http=" + response.getStatus()
                    + " resultado=" + (response.getStatus() < 400 ? "OK" : "ERROR")
                    + " duracion_ms=" + (System.currentTimeMillis() - inicio));
        }
    }

    private Credencial leerCredencial(String authorization) {
        if (authorization == null || !authorization.startsWith("Basic ")) { return null; }
        try {
            String decoded = new String(Base64.getDecoder().decode(authorization.substring(6)), StandardCharsets.UTF_8);
            int separator = decoded.indexOf(':');
            if (separator <= 0 || separator == decoded.length() - 1) { return null; }
            return new Credencial(decoded.substring(0, separator), decoded.substring(separator + 1));
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    private boolean contieneScope(String scopes, String required) {
        if (scopes == null) { return false; }
        for (String scope : scopes.trim().split("\\s+")) {
            if (required.equals(scope)) { return true; }
        }
        return false;
    }

    private boolean ipPermitida(ClienteApiDTO cliente, String remoteAddress) {
        String configured = cliente.getIpsPermitidas();
        if (configured == null || configured.trim().isEmpty()) { return true; }
        for (String ip : configured.split(",")) {
            if (ip.trim().equals(remoteAddress)) { return true; }
        }
        return false;
    }

    private boolean permitir(String clientId) {
        long now = System.currentTimeMillis();
        long windowMillis = obtenerVentanaSegundos() * 1000L;
        Ventana current = ventanas.get(clientId);
        if (current == null || now - current.inicio >= windowMillis) {
            current = new Ventana(now);
            ventanas.put(clientId, current);
        }
        return current.contador.incrementAndGet() <= obtenerLimite();
    }

    private int obtenerLimite() { return enteroEntorno("SIGIE_API_RATE_LIMIT", 60); }
    private int obtenerVentanaSegundos() { return enteroEntorno("SIGIE_API_RATE_WINDOW_SECONDS", 60); }
    private int enteroEntorno(String name, int defaultValue) {
        String value = System.getenv(name);
        if (value == null) { return defaultValue; }
        try { int result = Integer.parseInt(value); return result > 0 ? result : defaultValue; }
        catch (NumberFormatException e) { return defaultValue; }
    }

    private void error(HttpServletResponse response, int status, String codigo, String mensaje, String folio)
            throws IOException {
        response.setStatus(status);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.getWriter().write("{\"codigo\":\"" + codigo + "\",\"mensaje\":\"" + mensaje
                + "\",\"folio_consulta\":\"" + folio + "\"}");
    }

    private static class Credencial {
        private final String clientId;
        private final String secret;
        private Credencial(String clientId, String secret) { this.clientId = clientId; this.secret = secret; }
    }
    private static class Ventana {
        private final long inicio;
        private final AtomicInteger contador = new AtomicInteger();
        private Ventana(long inicio) { this.inicio = inicio; }
    }
}
