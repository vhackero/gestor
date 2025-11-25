package mx.gob.sedesol.basegestor.commons.dto.gestionescolar;

public class ConsultaBajaDTO {

    private String matricula;
    private String plan;
    private String programa;
    private String evento;
    private String tipoBaja;
    private String estructura;
    private String periodo;
    private Integer estatus;

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    public String getPrograma() {
        return programa;
    }

    public void setPrograma(String programa) {
        this.programa = programa;
    }

    public String getEvento() {
        return evento;
    }

    public void setEvento(String evento) {
        this.evento = evento;
    }

    public String getTipoBaja() {
        return tipoBaja;
    }

    public void setTipoBaja(String tipoBaja) {
        this.tipoBaja = tipoBaja;
    }

    public String getEstructura() {
        return estructura;
    }

    public void setEstructura(String estructura) {
        this.estructura = estructura;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public Integer getEstatus() {
        return estatus;
    }

    public void setEstatus(Integer estatus) {
        this.estatus = estatus;
    }
}
