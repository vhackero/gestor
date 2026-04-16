package mx.gob.sedesol.basegestor.commons.dto.gestionescolar;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RiesgoAcademicoDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String nivelRiesgo;
    private String resumen;
    private List<String> riesgosInmediatos;
    private List<String> riesgosMedianoPlazo;

    public RiesgoAcademicoDTO() {
        this.riesgosInmediatos = new ArrayList<String>();
        this.riesgosMedianoPlazo = new ArrayList<String>();
    }

    public String getNivelRiesgo() {
        return nivelRiesgo;
    }

    public void setNivelRiesgo(String nivelRiesgo) {
        this.nivelRiesgo = nivelRiesgo;
    }

    public String getResumen() {
        return resumen;
    }

    public void setResumen(String resumen) {
        this.resumen = resumen;
    }

    public List<String> getRiesgosInmediatos() {
        return riesgosInmediatos;
    }

    public void setRiesgosInmediatos(List<String> riesgosInmediatos) {
        this.riesgosInmediatos = riesgosInmediatos;
    }

    public List<String> getRiesgosMedianoPlazo() {
        return riesgosMedianoPlazo;
    }

    public void setRiesgosMedianoPlazo(List<String> riesgosMedianoPlazo) {
        this.riesgosMedianoPlazo = riesgosMedianoPlazo;
    }
}
