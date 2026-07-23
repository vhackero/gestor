package mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2;

import java.io.Serializable;

public class CampoExpedienteCasoDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String claveCampo;
    private String nombreCampo;
    private Boolean presente;
    private Boolean obligatorio;
    private String observacion;

    public String getClaveCampo() {
        return claveCampo;
    }

    public void setClaveCampo(String claveCampo) {
        this.claveCampo = claveCampo;
    }

    public String getNombreCampo() {
        return nombreCampo;
    }

    public void setNombreCampo(String nombreCampo) {
        this.nombreCampo = nombreCampo;
    }

    public Boolean getPresente() {
        return presente;
    }

    public void setPresente(Boolean presente) {
        this.presente = presente;
    }

    public Boolean getObligatorio() {
        return obligatorio;
    }

    public void setObligatorio(Boolean obligatorio) {
        this.obligatorio = obligatorio;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }
}
