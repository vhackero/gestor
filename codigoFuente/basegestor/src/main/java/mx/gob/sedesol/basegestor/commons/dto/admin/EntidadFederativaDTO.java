package mx.gob.sedesol.basegestor.commons.dto.admin;

import java.io.Serializable;

import mx.gob.sedesol.basegestor.commons.dto.ComunDTO;

public class EntidadFederativaDTO extends ComunDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer idEntidadFederativa;
	private String abreviatura;
	private String abreviaturaCurp;
	private String nombre;
	private Boolean activo;
	private PaisDTO pais;
	
	public Integer getIdEntidadFederativa() {
		return idEntidadFederativa;
	}
	public void setIdEntidadFederativa(Integer idEntidadFederativa) {
		this.idEntidadFederativa = idEntidadFederativa;
	}
	public String getAbreviatura() {
		return abreviatura;
	}
	public void setAbreviatura(String abreviatura) {
		this.abreviatura = abreviatura;
	}
	public String getAbreviaturaCurp() {
		return abreviaturaCurp;
	}
	public void setAbreviaturaCurp(String abreviaturaCurp) {
		this.abreviaturaCurp = abreviaturaCurp;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Boolean getActivo() {
		return activo;
	}
	public void setActivo(Boolean activo) {
		this.activo = activo;
	}
	public PaisDTO getPais() {
		return pais;
	}
	public void setPais(PaisDTO pais) {
		this.pais = pais;
	}

}
