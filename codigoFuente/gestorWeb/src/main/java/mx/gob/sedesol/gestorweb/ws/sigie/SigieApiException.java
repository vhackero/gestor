package mx.gob.sedesol.gestorweb.ws.sigie;

import org.springframework.http.HttpStatus;

public class SigieApiException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private final HttpStatus status;
    private final String codigo;

    public SigieApiException(HttpStatus status, String codigo, String mensaje) {
        super(mensaje);
        this.status = status;
        this.codigo = codigo;
    }

    public HttpStatus getStatus() { return status; }
    public String getCodigo() { return codigo; }
}
