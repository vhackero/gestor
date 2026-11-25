package mx.gob.sedesol.basegestor.commons.dto.gestionescolar;

import java.io.Serializable;

public class ConfiguracionOmisionReinscripcionDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Boolean activa;
    private Boolean permitirInscripcion;
    private Integer periodosConsecutivos;
    private String mensaje;
    public Boolean getActiva() { return activa; }
    public void setActiva(Boolean valor) { activa = valor; }
    public Boolean getPermitirInscripcion() { return permitirInscripcion; }
    public void setPermitirInscripcion(Boolean valor) { permitirInscripcion = valor; }
    public Integer getPeriodosConsecutivos() { return periodosConsecutivos; }
    public void setPeriodosConsecutivos(Integer valor) { periodosConsecutivos = valor; }
    public String getMensaje() { return mensaje; }
    public void setMensaje(String valor) { mensaje = valor; }
}
