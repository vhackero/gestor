package mx.gob.sedesol.gestorweb.ws.sigie;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;

public final class SigieRequestContext {
    public static final String FOLIO = "sigie.folio";
    public static final String REQUEST_ID = "sigie.requestId";
    public static final String CLIENT_ID = "sigie.clientId";
    private static final Pattern REQUEST_ID_VALIDO = Pattern.compile("^[A-Za-z0-9._-]{1,64}$");

    private SigieRequestContext() { }

    public static String preparar(HttpServletRequest request) {
        Object existente = request.getAttribute(FOLIO);
        if (existente != null) { return existente.toString(); }
        String fecha = ZonedDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String folio = "SIGIE-" + fecha + "-" + UUID.randomUUID().toString();
        String recibido = request.getHeader("X-Request-ID");
        String requestId = recibido != null && REQUEST_ID_VALIDO.matcher(recibido).matches()
                ? recibido : UUID.randomUUID().toString();
        request.setAttribute(FOLIO, folio);
        request.setAttribute(REQUEST_ID, requestId);
        return folio;
    }
}
