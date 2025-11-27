package mx.gob.sedesol.basegestor.commons.dto.gestionescolar;

import java.io.Serializable;

public class RelacionBajaDTO implements Serializable {

    private static final long serialVersionUID = 3589383469018357755L;

    private Long idPlan;
    private Long idSemestre;
    private Long idBloque;
    private Long idPrograma;
    private String periodo;
    private Long idEvento;

    public Long getIdPlan() {
        return idPlan;
    }

    public void setIdPlan(Long idPlan) {
        this.idPlan = idPlan;
    }

    public Long getIdSemestre() {
        return idSemestre;
    }

    public void setIdSemestre(Long idSemestre) {
        this.idSemestre = idSemestre;
    }

    public Long getIdBloque() {
        return idBloque;
    }

    public void setIdBloque(Long idBloque) {
        this.idBloque = idBloque;
    }

    public Long getIdPrograma() {
        return idPrograma;
    }

    public void setIdPrograma(Long idPrograma) {
        this.idPrograma = idPrograma;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public Long getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(Long idEvento) {
        this.idEvento = idEvento;
    }

    public boolean esRelacionValida() {
        return idPlan != null && idSemestre != null && idBloque != null && idPrograma != null && idEvento != null;
    }
}
