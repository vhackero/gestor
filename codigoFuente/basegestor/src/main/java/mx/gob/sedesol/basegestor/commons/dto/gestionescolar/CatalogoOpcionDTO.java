package mx.gob.sedesol.basegestor.commons.dto.gestionescolar;

import java.io.Serializable;

/**
 * DTO sencillo para exponer opciones de catálogo en componentes genéricos.
 */
public class CatalogoOpcionDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String descripcion;

    public CatalogoOpcionDTO() {
    }

    public CatalogoOpcionDTO(Long id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
