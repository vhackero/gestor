package mx.gob.sedesol.basegestor.commons.dto.admin;

import java.io.Serializable;

import mx.gob.sedesol.basegestor.commons.dto.ComunDTO;

public class PaisDTO extends ComunDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String idPais;
	private String abreviatura;
	private String nombre;
	private String nacionalidad;
	private Boolean activo;
	
	public String getIdPais() {
		return idPais;
	}
	public void setIdPais(String idPais) {
		this.idPais = idPais;
	}
	public String getAbreviatura() {
		return abreviatura;
	}
	public void setAbreviatura(String abreviatura) {
		this.abreviatura = abreviatura;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getNacionalidad() {
		return nacionalidad;
	}
	public void setNacionalidad(String nacionalidad) {
		this.nacionalidad = nacionalidad;
	}
	public Boolean getActivo() {
		return activo;
	}
	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

}
