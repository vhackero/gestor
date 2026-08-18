package mx.gob.sedesol.gestorweb.ws.sigie;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SigieErrorResponse {
    private final String codigo;
    private final String mensaje;
    @JsonProperty("folio_consulta")
    private final String folioConsulta;

    public SigieErrorResponse(String codigo, String mensaje, String folioConsulta) {
        this.codigo = codigo;
        this.mensaje = mensaje;
        this.folioConsulta = folioConsulta;
    }
    public String getCodigo() { return codigo; }
    public String getMensaje() { return mensaje; }
    public String getFolioConsulta() { return folioConsulta; }
}
