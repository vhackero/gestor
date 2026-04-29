package mx.gob.sedesol.basegestor.commons.dto.gestionescolar;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class EscenarioProyeccionDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String nombre;
    private String descripcion;
    private Integer materiasHabilitadas;
    private Integer materiasBloqueadas;
    private List<String> recomendaciones;

    public EscenarioProyeccionDTO() {
        this.recomendaciones = new ArrayList<String>();
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

    public Integer getMateriasBloqueadas() {
        return materiasBloqueadas;
    }

    public void setMateriasBloqueadas(Integer materiasBloqueadas) {
        this.materiasBloqueadas = materiasBloqueadas;
    }

    public List<String> getRecomendaciones() {
        return recomendaciones;
    }

    public void setRecomendaciones(List<String> recomendaciones) {
        this.recomendaciones = recomendaciones;
    }
}
