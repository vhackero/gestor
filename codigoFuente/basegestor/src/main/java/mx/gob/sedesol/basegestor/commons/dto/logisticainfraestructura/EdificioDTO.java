package mx.gob.sedesol.basegestor.commons.dto.logisticainfraestructura;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import mx.gob.sedesol.basegestor.commons.utils.MensajesSistemaEnum;

public class EdificioDTO {
	
	private Integer idEdificio;
	@NotEmpty(message=MensajesSistemaEnum.Constantes.LOGISTICA_EDIFICIOS_ADMINISTRAR_NOMBRE_REQ)
	@Size(min=2, max=100, message=MensajesSistemaEnum.Constantes.LOGISTICA_EDIFICIOS_ADMINISTRAR_NOMBRE_LONGITUD)
	private String nombre;
	@NotEmpty(message=MensajesSistemaEnum.Constantes.LOGISTICA_EDIFICIOS_ADMINISTRAR_DESCRIPCION_REQ)
	@Size(min=2, max=100, message=MensajesSistemaEnum.Constantes.LOGISTICA_EDIFICIOS_ADMINISTRAR_DESCRIPCION_LONGITUD)
	private String descripcion;
	private Date fechaActualizacion;
	private Date fechaRegistro;
	@NotNull(message=MensajesSistemaEnum.Constantes.LOGISTICA_EDIFICIOS_ADMINISTRAR_ID_INSTITUCION_REQ)
	private Integer idInstitucion;
	private Long usuarioModifico;
	@NotNull(message=MensajesSistemaEnum.Constantes.LOGISTICA_EDIFICIOS_ADMINISTRAR_TIPO_EDIFICIO_REQ)
	private TipoEdificioDTO catTiposEdificio;
	private List<EspacioDTO> espacios;
	
	public Integer getIdEdificio() {
		return idEdificio;
	}
	public void setIdEdificio(Integer idEdificio) {
		this.idEdificio = idEdificio;
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
	public Integer getIdInstitucion() {
		return idInstitucion;
	}
	public void setIdInstitucion(Integer idInstitucion) {
		this.idInstitucion = idInstitucion;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Long getUsuarioModifico() {
		return usuarioModifico;
	}
	public void setUsuarioModifico(Long usuarioModifico) {
		this.usuarioModifico = usuarioModifico;
	}
	public TipoEdificioDTO getCatTiposEdificio() {
		return catTiposEdificio;
	}
	public void setCatTiposEdificio(TipoEdificioDTO catTiposEdificio) {
		this.catTiposEdificio = catTiposEdificio;
	}
	public List<EspacioDTO> getEspacios() {
		return espacios;
	}
	public void setEspacios(List<EspacioDTO> espacios) {
		this.espacios = espacios;
	}

}
