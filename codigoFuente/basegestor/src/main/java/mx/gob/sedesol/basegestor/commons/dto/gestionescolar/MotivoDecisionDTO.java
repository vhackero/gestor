package mx.gob.sedesol.basegestor.commons.dto.gestionescolar;

import java.io.Serializable;

public class MotivoDecisionDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String codigo;
    private String severidad;
    private String mensajeCorto;
    private String mensajeDetallado;
    private String accionSugerida;
    private Boolean bloqueante;

    public MotivoDecisionDTO() {
    }

    public MotivoDecisionDTO(String codigo, String severidad, String mensajeCorto,
            String mensajeDetallado, String accionSugerida, Boolean bloqueante) {
        this.codigo = codigo;
        this.severidad = severidad;
        this.mensajeCorto = mensajeCorto;
        this.mensajeDetallado = mensajeDetallado;
        this.accionSugerida = accionSugerida;
        this.bloqueante = bloqueante;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getSeveridad() {
        return severidad;
    }

    public void setSeveridad(String severidad) {
        this.severidad = severidad;
    }

    public String getMensajeCorto() {
        return mensajeCorto;
    }

    public void setMensajeCorto(String mensajeCorto) {
        this.mensajeCorto = mensajeCorto;
    }

    public String getMensajeDetallado() {
        return mensajeDetallado;
    }

    public void setMensajeDetallado(String mensajeDetallado) {
        this.mensajeDetallado = mensajeDetallado;
    }

    public String getAccionSugerida() {
        return accionSugerida;
    }

    public void setAccionSugerida(String accionSugerida) {
        this.accionSugerida = accionSugerida;
    }

    public Boolean getBloqueante() {
        return bloqueante;
    }

    public void setBloqueante(Boolean bloqueante) {
        this.bloqueante = bloqueante;
    }
}
