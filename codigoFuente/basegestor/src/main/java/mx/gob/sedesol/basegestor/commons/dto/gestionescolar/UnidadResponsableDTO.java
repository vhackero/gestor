package mx.gob.sedesol.basegestor.commons.dto.gestionescolar;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import mx.gob.sedesol.basegestor.commons.dto.ComunDTO;
import mx.gob.sedesol.basegestor.commons.utils.MensajesSistemaEnum;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;

public class UnidadResponsableDTO extends ComunDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer idUnidadResponsable;
	
	@Length(max=150)
	@NotEmpty(message=MensajesSistemaEnum.Constantes.GESTOR_WEB_GENERAL_DATO_REQUERIDO)
	private String clave;
	
	@Length(max=150)
	@NotEmpty(message=MensajesSistemaEnum.Constantes.GESTOR_WEB_GENERAL_DATO_REQUERIDO)
	private String nombre;
	
	private Boolean activo;
	
	public UnidadResponsableDTO() {
		setFechaRegistro(new Date());
		setActivo(true);
	}
	
	public UnidadResponsableDTO(Long usuarioModifico) {
		setFechaRegistro(new Date());
		setActivo(true);
		setUsuarioModifico(usuarioModifico);
	}
	
	public Integer getIdUnidadResponsable() {
		return idUnidadResponsable;
	}
	public void setIdUnidadResponsable(Integer idUnidadResponsable) {
		this.idUnidadResponsable = idUnidadResponsable;
	}
	public String getClave() {
		return clave;
	}
	public void setClave(String clave) {
		if (ObjectUtils.isNull(clave)) {
			this.clave = null;
		} else {
			this.clave = clave.trim();
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
	public Boolean getActivo() {
		return activo;
	}
	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

}
