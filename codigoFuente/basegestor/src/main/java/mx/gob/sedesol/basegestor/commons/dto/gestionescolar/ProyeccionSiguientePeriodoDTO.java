package mx.gob.sedesol.basegestor.commons.dto.gestionescolar;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ProyeccionSiguientePeriodoDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String resumen;
    private List<EscenarioProyeccionDTO> escenarios;

    public ProyeccionSiguientePeriodoDTO() {
        this.escenarios = new ArrayList<EscenarioProyeccionDTO>();
    }

    public String getResumen() {
        return resumen;
    }

    public void setResumen(String resumen) {
        this.resumen = resumen;
    }

    public List<EscenarioProyeccionDTO> getEscenarios() {
        return escenarios;
    }

    public void setEscenarios(List<EscenarioProyeccionDTO> escenarios) {
        this.escenarios = escenarios;
    }
}
