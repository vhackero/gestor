package mx.gob.sedesol.basegestor.commons.dto.admin;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import mx.gob.sedesol.basegestor.commons.dto.ComunDTO;
import mx.gob.sedesol.basegestor.commons.utils.MensajesSistemaEnum;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;

public class AsentamientoDTO extends ComunDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Length(max=9)
	@NotEmpty(message=MensajesSistemaEnum.Constantes.GESTOR_WEB_GENERAL_DATO_REQUERIDO)
	private String idAsentamiento;
	
	@Length(max=80)
	@NotEmpty(message=MensajesSistemaEnum.Constantes.GESTOR_WEB_GENERAL_DATO_REQUERIDO)
	private String nombre;
	
	@Length(max=5)
	@NotEmpty(message=MensajesSistemaEnum.Constantes.GESTOR_WEB_GENERAL_DATO_REQUERIDO)
	private String codigoPostal;
	private Boolean activo;
	private TipoAsentamientoDTO tipoAsentamiento;
	private MunicipioDTO municipio;
	
	public AsentamientoDTO() {
		setFechaRegistro(new Date());
		this.activo = true;
	}
	
	public AsentamientoDTO(Long usuarioModifico) {
		setUsuarioModifico(usuarioModifico);
		setFechaRegistro(new Date());
		this.activo = true;
		this.tipoAsentamiento = new TipoAsentamientoDTO();
	}
	
	public String getIdAsentamiento() {
		return idAsentamiento;
	}
	public void setIdAsentamiento(String idAsentamiento) {
		if (ObjectUtils.isNull(idAsentamiento)) {
			this.idAsentamiento = null;
		} else {
			this.idAsentamiento = idAsentamiento.trim();
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
	public String getCodigoPostal() {
		return codigoPostal;
	}
	public void setCodigoPostal(String codigoPostal) {
		if (ObjectUtils.isNull(codigoPostal)) {
			this.codigoPostal = null;
		} else {
			this.codigoPostal = codigoPostal.trim();
		}
	}
	public TipoAsentamientoDTO getTipoAsentamiento() {
		return tipoAsentamiento;
	}
	public void setTipoAsentamiento(TipoAsentamientoDTO tipoAsentamiento) {
		this.tipoAsentamiento = tipoAsentamiento;
	}
	public MunicipioDTO getMunicipio() {
		return municipio;
	}
	public void setMunicipio(MunicipioDTO municipio) {
		this.municipio = municipio;
	}
	public Boolean getActivo() {
		return activo;
	}
	public void setActivo(Boolean activo) {
		this.activo = activo;
	}
}
