package mx.gob.sedesol.basegestor.commons.dto.admin;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import mx.gob.sedesol.basegestor.commons.dto.ComunDTO;
import mx.gob.sedesol.basegestor.commons.utils.MensajesSistemaEnum;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;

public class MunicipioDTO extends ComunDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Length(max=5)
	@NotEmpty(message=MensajesSistemaEnum.Constantes.GESTOR_WEB_GENERAL_DATO_REQUERIDO)
	private String idMunicipio;
	
	@Length(max=50)
	@NotEmpty(message=MensajesSistemaEnum.Constantes.GESTOR_WEB_GENERAL_DATO_REQUERIDO)
	private String nombre;
	
	private Boolean activo;
	private EntidadFederativaDTO entidadFederativa;
	
	public MunicipioDTO() {
		setFechaRegistro(new Date());
		this.activo = true;
	}
	
	public MunicipioDTO(Long usuarioModifico) {
		setUsuarioModifico(usuarioModifico);
		setFechaRegistro(new Date());
		this.activo = true;
	}
	
	public String getIdMunicipio() {
		return idMunicipio;
	}
	public void setIdMunicipio(String idMunicipio) {
		if (ObjectUtils.isNull(idMunicipio)) {
			this.idMunicipio = null;
		} else {
			this.idMunicipio = idMunicipio.trim();
		}
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		if (ObjectUtils.isNull(nombre)) {
			this.nombre = null;
		} else {
			this.nombre = nombre.trim();
		}
	}
	public EntidadFederativaDTO getEntidadFederativa() {
		return entidadFederativa;
	}
	public void setEntidadFederativa(EntidadFederativaDTO entidadFederativa) {
		this.entidadFederativa = entidadFederativa;
	}
	public Boolean getActivo() {
		return activo;
	}
	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

}
