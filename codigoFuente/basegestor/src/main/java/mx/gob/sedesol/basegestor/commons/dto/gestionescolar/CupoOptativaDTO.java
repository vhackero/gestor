package mx.gob.sedesol.basegestor.commons.dto.gestionescolar;

import java.io.Serializable;

public class CupoOptativaDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String clave;
    private final String nombre;
    private final long ocupados;
    private Integer maximo;

    public CupoOptativaDTO(String clave, String nombre, Integer maximo, long ocupados) {
        this.clave = clave;
        this.nombre = nombre;
        this.maximo = maximo;
        this.ocupados = ocupados;
    }

    public String getClave() { return clave; }
    public String getNombre() { return nombre; }
    public long getOcupados() { return ocupados; }
    public Integer getMaximo() { return maximo; }
    public void setMaximo(Integer maximo) { this.maximo = maximo; }
}
