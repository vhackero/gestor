package mx.gob.sedesol.basegestor.commons.dto.planesyprogramas;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;

import mx.gob.sedesol.basegestor.commons.dto.admin.CatalogoComunDTO;

public class CompetenciaEspecificaDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private Byte activo;
	private String descripcion;
	private Date fechaActualizacion;
	private Date fechaRegistro;
	
	@NotEmpty
	private String nombre;
	
	private Integer orden;
	private Long usuarioModifico;
	private List<CatalogoComunDTO> relEjeCompetencia;
	private Integer ponderacion;
	
	public CompetenciaEspecificaDTO(Long usuarioModifico){
		this.id = 0;
		this.ponderacion = 0;
		this.usuarioModifico = usuarioModifico;
		this.fechaRegistro = new Date();
		this.activo = 1;
		this.orden = 1;
		
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Byte getActivo() {
		return activo;
	}

	public void setActivo(Byte activo) {
		this.activo = activo;
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

	public Integer getOrden() {
		return orden;
	}

	public void setOrden(Integer orden) {
		this.orden = orden;
	}

	public Long getUsuarioModifico() {
		return usuarioModifico;
	}

	public void setUsuarioModifico(Long usuarioModifico) {
		this.usuarioModifico = usuarioModifico;
	}

	public List<CatalogoComunDTO> getRelEjeCompetencia() {
		return relEjeCompetencia;
	}

	public void setRelEjeCompetencia(List<CatalogoComunDTO> relEjeCompetencia) {
		this.relEjeCompetencia = relEjeCompetencia;
	}

	public Integer getPonderacion() {
		return ponderacion;
	}

	public void setPonderacion(Integer ponderacion) {
		this.ponderacion = ponderacion;
	}

	public CompetenciaEspecificaDTO(){
		
	}	
	
}