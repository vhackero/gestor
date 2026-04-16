package mx.gob.sedesol.basegestor.commons.dto.gestionescolar;

import java.io.Serializable;

public class RelacionSeriacionDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long udId;
    private String clave;
    private String nombre;
    private Integer semestre;
    private Integer bloque;

    public RelacionSeriacionDTO() {
    }

    public RelacionSeriacionDTO(Long udId, String clave, String nombre, Integer semestre, Integer bloque) {
        this.udId = udId;
        this.clave = clave;
        this.nombre = nombre;
        this.semestre = semestre;
        this.bloque = bloque;
    }

    public Long getUdId() {
        return udId;
    }

    public void setUdId(Long udId) {
        this.udId = udId;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getSemestre() {
        return semestre;
    }

    public void setSemestre(Integer semestre) {
        this.semestre = semestre;
    }

    public Integer getBloque() {
        return bloque;
    }

    public void setBloque(Integer bloque) {
        this.bloque = bloque;
    }
}
