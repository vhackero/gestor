package mx.gob.sedesol.basegestor.commons.dto.gestionescolar;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ResultadoSimulacionDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Boolean valida;
    private Integer totalMaterias;
    private Integer totalCreditos;

    private String mensajeGeneral;
    private String impactoSiguientePeriodo;

    private List<MotivoDecisionDTO> motivos;
    private List<String> advertencias;
    private List<Long> unidadesSeleccionadas;

    public ResultadoSimulacionDTO() {
        this.motivos = new ArrayList<MotivoDecisionDTO>();
        this.advertencias = new ArrayList<String>();
        this.unidadesSeleccionadas = new ArrayList<Long>();
    }

    public Boolean getValida() {
        return valida;
    }

    public void setValida(Boolean valida) {
        this.valida = valida;
    }

    public Integer getTotalMaterias() {
        return totalMaterias;
    }

    public void setTotalMaterias(Integer totalMaterias) {
        this.totalMaterias = totalMaterias;
    }

    public Integer getTotalCreditos() {
        return totalCreditos;
    }

    public void setTotalCreditos(Integer totalCreditos) {
        this.totalCreditos = totalCreditos;
    }

    public String getMensajeGeneral() {
        return mensajeGeneral;
    }

    public void setMensajeGeneral(String mensajeGeneral) {
        this.mensajeGeneral = mensajeGeneral;
    }

    public String getImpactoSiguientePeriodo() {
        return impactoSiguientePeriodo;
    }

    public void setImpactoSiguientePeriodo(String impactoSiguientePeriodo) {
        this.impactoSiguientePeriodo = impactoSiguientePeriodo;
    }

    public List<MotivoDecisionDTO> getMotivos() {
        return motivos;
    }

    public void setMotivos(List<MotivoDecisionDTO> motivos) {
        this.motivos = motivos;
    }

    public List<String> getAdvertencias() {
        return advertencias;
    }

    public void setAdvertencias(List<String> advertencias) {
        this.advertencias = advertencias;
    }

    public List<Long> getUnidadesSeleccionadas() {
        return unidadesSeleccionadas;
    }

    public void setUnidadesSeleccionadas(List<Long> unidadesSeleccionadas) {
        this.unidadesSeleccionadas = unidadesSeleccionadas;
    }
}
