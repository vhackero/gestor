package mx.gob.sedesol.basegestor.commons.dto.logisticainfraestructura;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import mx.gob.sedesol.basegestor.commons.utils.MensajesSistemaEnum;

public class EspacioDTO {
	

	private Integer idEspacio;
	private Short capacidad;
	@NotEmpty(message=MensajesSistemaEnum.Constantes.LOGISTICA_ESPACIOS_ADMINISTRAR_DESCRIPCION_REQ)
	@Size(min=2,max=250, message=MensajesSistemaEnum.Constantes.LOGISTICA_ESPACIOS_ADMINISTRAR_DESCRIPCION_LONGITUD)
	private String descripcion;
	private Date fechaActualizacion;
	private Date fechaRegistro;
	@NotEmpty(message=MensajesSistemaEnum.Constantes.LOGISTICA_ESPACIOS_ADMINISTRAR_NOMBRE_REQ)
	@Size(min=2,max=100, message=MensajesSistemaEnum.Constantes.LOGISTICA_ESPACIOS_ADMINISTRAR_NOMBRE_LONGITUD)
	private String nombre;
	private String piso;
	private Long usuarioModifico;
	@NotNull(message=MensajesSistemaEnum.Constantes.LOGISTICA_ESPACIOS_ADMINISTRAR_EDIFICIO_REQ)
	private EdificioDTO catEdificio;
	@NotNull(message=MensajesSistemaEnum.Constantes.LOGISTICA_ESPACIOS_ADMINISTRAR_TIPO_ESPACIO_REQ)
	private TipoEspacioDTO catTiposEspacio;
	
	public Integer getIdEspacio() {
		return idEspacio;
	}
	public void setIdEspacio(Integer idEspacio) {
		this.idEspacio = idEspacio;
	}
	public Short getCapacidad() {
		return capacidad;
	}
	public void setCapacidad(Short capacidad) {
		this.capacidad = capacidad;
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
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getPiso() {
		return piso;
	}
	public void setPiso(String piso) {
		this.piso = piso;
	}
	public Long getUsuarioModifico() {
		return usuarioModifico;
	}
	public void setUsuarioModifico(Long usuarioModifico) {
		this.usuarioModifico = usuarioModifico;
	}
	public EdificioDTO getCatEdificio() {
		return catEdificio;
	}
	public void setCatEdificio(EdificioDTO catEdificio) {
		this.catEdificio = catEdificio;
	}
	public TipoEspacioDTO getCatTiposEspacio() {
		return catTiposEspacio;
	}
	public void setCatTiposEspacio(TipoEspacioDTO catTiposEspacio) {
		this.catTiposEspacio = catTiposEspacio;
	}
	
}
