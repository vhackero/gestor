package mx.gob.sedesol.basegestor.commons.dto.logisticainfraestructura;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import mx.gob.sedesol.basegestor.commons.utils.MensajesSistemaEnum;

public class TipoEdificioDTO {
	
	private Short idTipoEdificio;
	@NotNull(message=MensajesSistemaEnum.Constantes.LOGISTICA_EDIFICIOS_TIPO_EDIFICIO_DESCRIPCION_REQ)
	@Size(min = 2, max = 20, message=MensajesSistemaEnum.Constantes.LOGISTICA_EDIFICIOS_TIPO_EDIFICIO_DESCRIPCION_LONGITUD)
	private String descripcion;
	private Date fechaActualizacion;
	private Date fechaRegistro;
	private Long usuarioModifico;
	private List<EdificioDTO> edificios;
	
	public Short getIdTipoEdificio() {
		return idTipoEdificio;
	}
	public void setIdTipoEdificio(Short idTipoEdificio) {
		this.idTipoEdificio = idTipoEdificio;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public Date getFechaActualizacion() {
		return fechaActualizacion;
	}
	public void setFechaActualizacion(Date fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}
	public Date getFechaRegistro() {
		return fechaRegistro;
	}
	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}
	public Long getUsuarioModifico() {
		return usuarioModifico;
	}
	public void setUsuarioModifico(Long usuarioModifico) {
		this.usuarioModifico = usuarioModifico;
	}
	public List<EdificioDTO> getEdificios() {
		return edificios;
	}
	public void setEdificios(List<EdificioDTO> edificios) {
		this.edificios = edificios;
	}

}
