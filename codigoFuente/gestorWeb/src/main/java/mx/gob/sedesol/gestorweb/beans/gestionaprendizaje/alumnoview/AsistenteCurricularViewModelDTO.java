package mx.gob.sedesol.gestorweb.beans.gestionaprendizaje.alumnoview;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/** Modelo de presentación: no contiene ni ejecuta reglas académicas. */
public class AsistenteCurricularViewModelDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private String situacionAcademica;
    private String escenario;
    private String resumen;
    private String carga;
    private final List<String> alertas = new ArrayList<String>();
    private final List<String> motivos = new ArrayList<String>();
    private final List<String> reglas = new ArrayList<String>();

    public String getSituacionAcademica() { return situacionAcademica; }
    public void setSituacionAcademica(String valor) { situacionAcademica = valor; }
    public String getEscenario() { return escenario; }
    public void setEscenario(String valor) { escenario = valor; }
    public String getResumen() { return resumen; }
    public void setResumen(String valor) { resumen = valor; }
    public String getCarga() { return carga; }
    public void setCarga(String valor) { carga = valor; }
    public List<String> getAlertas() { return alertas; }
    public List<String> getMotivos() { return motivos; }
    public List<String> getReglas() { return reglas; }
}
