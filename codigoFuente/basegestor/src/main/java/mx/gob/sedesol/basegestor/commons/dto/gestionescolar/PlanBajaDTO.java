package mx.gob.sedesol.basegestor.commons.dto.gestionescolar;

import java.io.Serializable;

public class PlanBajaDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private Integer idPlan;
    private String nombre;

    public PlanBajaDTO() {
    }

    public PlanBajaDTO(Integer id, Integer idPlan, String nombre) {
        this.id = id;
        this.idPlan = idPlan;
        this.nombre = nombre;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdPlan() {
        return idPlan;
    }

    public void setIdPlan(Integer idPlan) {
        this.idPlan = idPlan;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
