package mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ExpedienteMinimoCasoDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Boolean completo;
    private String estatusExpediente;
    private List<CampoExpedienteCasoDTO> campos;
    private List<String> faltantes;

    public ExpedienteMinimoCasoDTO() {
        this.campos = new ArrayList<CampoExpedienteCasoDTO>();
        this.faltantes = new ArrayList<String>();
    }

    public Boolean getCompleto() {
        return completo;
    }

    public void setCompleto(Boolean completo) {
        this.completo = completo;
    }

    public String getEstatusExpediente() {
        return estatusExpediente;
    }

    public void setEstatusExpediente(String estatusExpediente) {
        this.estatusExpediente = estatusExpediente;
    }

    public List<CampoExpedienteCasoDTO> getCampos() {
        return campos;
    }

    public void setCampos(List<CampoExpedienteCasoDTO> campos) {
        this.campos = campos;
    }

    public List<String> getFaltantes() {
        return faltantes;
    }

    public void setFaltantes(List<String> faltantes) {
        this.faltantes = faltantes;
    }
}
