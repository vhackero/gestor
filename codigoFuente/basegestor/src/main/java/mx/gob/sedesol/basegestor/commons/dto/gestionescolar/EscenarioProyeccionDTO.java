package mx.gob.sedesol.basegestor.commons.dto.gestionescolar;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class EscenarioProyeccionDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String nombre;
    private String descripcion;
    private Integer materiasOfertadas;
    private Integer materiasHabilitadas;
    private Integer materiasBloqueadas;
    /** Reprobadas vigentes calculadas para el escenario hipotético V2. */
    private Integer reprobadasVigentes;
    private String situacionBase;
    private String resultadoHipotetico;
    private String periodoSimulado;
    private String periodoElegibilidad;
    private String versionReglas;
    private String corteDatos;
    private List<String> supuestos;
    private List<String> recomendaciones;

    public EscenarioProyeccionDTO() {
        this.recomendaciones = new ArrayList<String>();
        this.supuestos = new ArrayList<String>();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getMateriasHabilitadas() {
        return materiasHabilitadas;
    }

    public void setMateriasHabilitadas(Integer materiasHabilitadas) {
        this.materiasHabilitadas = materiasHabilitadas;
    }

    public Integer getMateriasOfertadas() {
        return materiasOfertadas;
    }

    public void setMateriasOfertadas(Integer materiasOfertadas) {
        this.materiasOfertadas = materiasOfertadas;
    }

    public Integer getMateriasBloqueadas() {
        return materiasBloqueadas;
    }

    public void setMateriasBloqueadas(Integer materiasBloqueadas) {
        this.materiasBloqueadas = materiasBloqueadas;
    }

    public Integer getReprobadasVigentes() {
        return reprobadasVigentes;
    }

    public void setReprobadasVigentes(Integer reprobadasVigentes) {
        this.reprobadasVigentes = reprobadasVigentes;
    }

    public List<String> getRecomendaciones() {
        return recomendaciones;
    }

    public void setRecomendaciones(List<String> recomendaciones) {
        this.recomendaciones = recomendaciones;
    }
    public String getSituacionBase() { return situacionBase; }
    public void setSituacionBase(String valor) { situacionBase = valor; }
    public String getResultadoHipotetico() { return resultadoHipotetico; }
    public void setResultadoHipotetico(String valor) { resultadoHipotetico = valor; }
    public String getPeriodoSimulado() { return periodoSimulado; }
    public void setPeriodoSimulado(String valor) { periodoSimulado = valor; }
    public String getPeriodoElegibilidad() { return periodoElegibilidad; }
    public void setPeriodoElegibilidad(String valor) { periodoElegibilidad = valor; }
    public String getVersionReglas() { return versionReglas; }
    public void setVersionReglas(String valor) { versionReglas = valor; }
    public String getCorteDatos() { return corteDatos; }
    public void setCorteDatos(String valor) { corteDatos = valor; }
    public List<String> getSupuestos() { return supuestos; }
    public void setSupuestos(List<String> valor) { supuestos = valor == null ? new ArrayList<String>() : valor; }
}
