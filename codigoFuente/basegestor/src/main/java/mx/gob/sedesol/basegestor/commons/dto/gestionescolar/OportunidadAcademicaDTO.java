package mx.gob.sedesol.basegestor.commons.dto.gestionescolar;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class OportunidadAcademicaDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String resumen;
    private List<String> materiasEstrategicas;
    private List<String> oportunidades;

    public OportunidadAcademicaDTO() {
        this.materiasEstrategicas = new ArrayList<String>();
        this.oportunidades = new ArrayList<String>();
    }

    public String getResumen() {
        return resumen;
    }

    public void setResumen(String resumen) {
        this.resumen = resumen;
    }

    public List<String> getMateriasEstrategicas() {
        return materiasEstrategicas;
    }

    public void setMateriasEstrategicas(List<String> materiasEstrategicas) {
        this.materiasEstrategicas = materiasEstrategicas;
    }

    public List<String> getOportunidades() {
        return oportunidades;
    }

    public void setOportunidades(List<String> oportunidades) {
        this.oportunidades = oportunidades;
    }
}
