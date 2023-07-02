package mx.gob.sedesol.basegestor.commons.dto.logisticainfraestructura;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import mx.gob.sedesol.basegestor.commons.utils.MensajesSistemaEnum;

public class TipoEspacioDTO {

	private Short idTipoEspacio;
	@NotNull(message=MensajesSistemaEnum.Constantes.LOGISTICA_ESPACIOS_TIPO_ESPACIO_DESCRIPCION_REQ)
	@Size(min = 2, max = 20, message=MensajesSistemaEnum.Constantes.LOGISTICA_ESPACIOS_TIPO_ESPACIO_DESCRIPCION_LONGITUD)
	private String descripcion;
	private Date fechaActualizacion;
	private Date fechaRegistro;
	private Long usuarioModifico;
	private List<EspacioDTO> espacios;
        
	public Short getIdTipoEspacio() {
		return idTipoEspacio;
	}
	public void setIdTipoEspacio(Short idTipoEspacio) {
		this.idTipoEspacio = idTipoEspacio;
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
	public List<EspacioDTO> getEspacios() {
		return espacios;
	}
	public void setEspacios(List<EspacioDTO> espacios) {
		this.espacios = espacios;
	}
	
}