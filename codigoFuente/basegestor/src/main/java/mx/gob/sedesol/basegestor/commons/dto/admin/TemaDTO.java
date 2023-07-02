package mx.gob.sedesol.basegestor.commons.dto.admin;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import mx.gob.sedesol.basegestor.commons.dto.ComunDTO;
import mx.gob.sedesol.basegestor.commons.utils.MensajesSistemaEnum;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;

public class TemaDTO extends ComunDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer idTema;
	private Boolean activo;
	
	@Length(max=100)
	@NotEmpty(message=MensajesSistemaEnum.Constantes.GESTOR_WEB_GENERAL_DATO_REQUERIDO)
	private String nombre;
	
	private String ruta;
	private Integer tipoTema;
	
	public TemaDTO() {
		setFechaRegistro(new Date());
		this.activo = false;
	}
	
	public TemaDTO(int tipoTema, Long usuarioModifico) {
		setUsuarioModifico(usuarioModifico);
		setFechaRegistro(new Date());
		this.activo = false;
		this.tipoTema = tipoTema;
		this.ruta = UUID.randomUUID().toString();
	}
	
	public Integer getIdTema() {
		return idTema;
	}
	public void setIdTema(Integer idTema) {
		this.idTema = idTema;
	}
	public Boolean getActivo() {
		return activo;
	}
	public void setActivo(Boolean activo) {
		this.activo = activo;
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
	public String getRuta() {
		return ruta;
	}
	public void setRuta(String ruta) {
		this.ruta = ruta;
	}
	public Integer getTipoTema() {
		return tipoTema;
	}
	public void setTipoTema(Integer tipoTema) {
		this.tipoTema = tipoTema;
	}

}
