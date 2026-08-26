package mx.gob.sedesol.basegestor.commons.dto.gestionescolar;

import java.io.Serializable;

public class ReglaInscripcionDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String clave;
	private String nombre;
	private String descripcion;
	private String categoria;
	private String tipoRegla;
	private Boolean activa;

	public String getClave() { return clave; }
	public void setClave(String clave) { this.clave = clave; }
	public String getNombre() { return nombre; }
	public void setNombre(String nombre) { this.nombre = nombre; }
	public String getDescripcion() { return descripcion; }
	public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
	public Boolean getActiva() { return activa; }
	public void setActiva(Boolean activa) { this.activa = activa; }
	public String getCategoria() { return categoria; }
	public void setCategoria(String categoria) { this.categoria = categoria; }
	public String getTipoRegla() { return tipoRegla; }
	public void setTipoRegla(String tipoRegla) { this.tipoRegla = tipoRegla; }
}
