package mx.gob.sedesol.basegestor.commons.dto.gestionescolar;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PendientesPlanDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer obligatoriasFaltantes;
    private Integer optativasFaltantes;
    private Integer electivasFaltantes;
    private Integer optativasRezagadas;
    private Integer creditosFaltantes;
    private List<String> optativasRezagadasDetalle;
    private List<String> pendientesRelevantes;

    public PendientesPlanDTO() {
        this.optativasRezagadasDetalle = new ArrayList<String>();
        this.pendientesRelevantes = new ArrayList<String>();
    }

    public Integer getObligatoriasFaltantes() {
        return obligatoriasFaltantes;
    }

    public void setObligatoriasFaltantes(Integer obligatoriasFaltantes) {
        this.obligatoriasFaltantes = obligatoriasFaltantes;
    }

    public Integer getOptativasFaltantes() {
        return optativasFaltantes;
    }

    public void setOptativasFaltantes(Integer optativasFaltantes) {
        this.optativasFaltantes = optativasFaltantes;
    }

    public Integer getElectivasFaltantes() {
        return electivasFaltantes;
    }

    public void setElectivasFaltantes(Integer electivasFaltantes) {
        this.electivasFaltantes = electivasFaltantes;
    }

    public Integer getOptativasRezagadas() {
        return optativasRezagadas;
    }

    public void setOptativasRezagadas(Integer optativasRezagadas) {
        this.optativasRezagadas = optativasRezagadas;
    }

    public Integer getCreditosFaltantes() {
        return creditosFaltantes;
    }

    public void setCreditosFaltantes(Integer creditosFaltantes) {
        this.creditosFaltantes = creditosFaltantes;
    }

    public List<String> getOptativasRezagadasDetalle() {
        return optativasRezagadasDetalle;
    }

    public void setOptativasRezagadasDetalle(List<String> optativasRezagadasDetalle) {
        this.optativasRezagadasDetalle = optativasRezagadasDetalle;
    }

    public List<String> getPendientesRelevantes() {
        return pendientesRelevantes;
    }

    public void setPendientesRelevantes(List<String> pendientesRelevantes) {
        this.pendientesRelevantes = pendientesRelevantes;
    }
}
