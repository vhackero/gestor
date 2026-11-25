package mx.gob.sedesol.gestorweb.ws.sigie;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Enumeration;
import javax.servlet.http.HttpServletRequest;

/** Valida la IP literal de Host sin resolver DNS ni confiar en cabeceras reenviadas. */
final class SigieHostValidator {
    private SigieHostValidator() { }

    static boolean esSolicitudPorIp(HttpServletRequest request) {
        Enumeration<String> hosts = request.getHeaders("Host");
        if (hosts == null || !hosts.hasMoreElements()) {
            return false;
        }
        String host = hosts.nextElement();
        if (hosts.hasMoreElements() || !esAutoridadIp(host)) {
            return false;
        }
        // Verifica tambien la autoridad interpretada por el contenedor (p. ej. URI absoluta).
        String nombre = request.getServerName();
        if (nombre == null) {
            return false;
        }
        String autoridad = nombre.indexOf(':') >= 0 && !nombre.startsWith("[")
                ? "[" + nombre + "]" : nombre;
        return esAutoridadIp(autoridad);
    }

    static boolean esAutoridadIp(String autoridad) {
        if (autoridad == null || autoridad.isEmpty() || autoridad.length() > 255
                || !autoridad.matches("[\\x21-\\x7E]+") || autoridad.contains("%")
                || autoridad.endsWith(":")) {
            return false;
        }
        try {
            // URI valida la sintaxis de IPv6 y puerto sin realizar conexiones ni consultas DNS.
            URI uri = new URI("http://" + autoridad).parseServerAuthority();
            if (!autoridad.equals(uri.getRawAuthority()) || uri.getRawUserInfo() != null
                    || !uri.getRawPath().isEmpty() || uri.getRawQuery() != null
                    || uri.getRawFragment() != null || uri.getPort() == 0 || uri.getPort() > 65535) {
                return false;
            }
            String host = uri.getHost();
            if (host == null) {
                return false;
            }
            if (host.startsWith("[") && host.endsWith("]")) {
                return host.indexOf(':') >= 0;
            }
            String[] octetos = host.split("\\.", -1);
            if (octetos.length != 4) {
                return false;
            }
            for (String octeto : octetos) {
                if (!octeto.matches("0|[1-9][0-9]{0,2}") || Integer.parseInt(octeto) > 255) {
                    return false;
                }
            }
            return true;
        } catch (URISyntaxException | NumberFormatException e) {
            return false;
        }
    }

}
