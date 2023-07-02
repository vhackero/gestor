package mx.gob.sedesol.basegestor.commons.dto.cronscheduler;

import java.io.Serializable;

public class ResultadoRespuestaCronDTO implements Serializable {

    private static final long serialVersionUID = -7810835371329053375L;

    private String descripcionError = "Ocurrio un error";
    private String nombreCron;
    private Integer estatus = -1;

    public String getDescripcionError() {
        return descripcionError;
    }

    public void setDescripcionError(String descripcionError) {
        this.descripcionError = descripcionError;
    }

    public String getNombreCron() {
        return nombreCron;
    }

    public void setNombreCron(String nombreCron) {
        this.nombreCron = nombreCron;
    }

    public Integer getEstatus() {
        return estatus;
    }

    public void setEstatus(Integer estatus) {
        this.estatus = estatus;
    }

}
