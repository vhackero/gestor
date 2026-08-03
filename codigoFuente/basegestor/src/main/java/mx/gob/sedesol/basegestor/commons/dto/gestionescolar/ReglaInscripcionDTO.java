package mx.gob.sedesol.basegestor.commons.dto.gestionescolar;

import java.io.Serializable;

public class ReglaInscripcionDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String clave;
	private String descripcion;
	private Boolean activa;

	public String getClave() { return clave; }
	public void setClave(String clave) { this.clave = clave; }
	public String getDescripcion() { return descripcion; }
	public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
	public Boolean getActiva() { return activa; }
	public void setActiva(Boolean activa) { this.activa = activa; }
}
