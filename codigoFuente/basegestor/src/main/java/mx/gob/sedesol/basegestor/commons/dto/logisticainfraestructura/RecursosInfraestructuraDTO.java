package mx.gob.sedesol.basegestor.commons.dto.logisticainfraestructura;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;

import mx.gob.sedesol.basegestor.commons.dto.admin.CatalogoComunDTO;

public class RecursosInfraestructuraDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer idRecurso;
	private Integer activo;
	private Date fechaActualizacion;
	private Date fechaRegistro;
	@NotEmpty(message="gw.global.msg.dato.req")
	private String nombre;
	private Integer orden;
	private Long usuarioModifico;
	private CatalogoComunDTO catTipoRecurso;
	private List<RelAreaRecursoDTO> relAreaRecursos;
	
	/**
	 * @return the idRecurso
	 */
	public Integer getIdRecurso() {
		return idRecurso;
	}
	/**
	 * @param idRecurso the idRecurso to set
	 */
	public void setIdRecurso(Integer idRecurso) {
		this.idRecurso = idRecurso;
	}
	/**
	 * @return the activo
	 */
	public Integer getActivo() {
		return activo;
	}
	/**
	 * @param activo the activo to set
	 */
	public void setActivo(Integer activo) {
		this.activo = activo;
	}
	/**
	 * @return the fechaActualizacion
	 */
	public Date getFechaActualizacion() {
		return fechaActualizacion;
	}
	/**
	 * @param fechaActualizacion the fechaActualizacion to set
	 */
	public void setFechaActualizacion(Date fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}
	/**
	 * @return the fechaRegistro
	 */
	public Date getFechaRegistro() {
		return fechaRegistro;
	}
	/**
	 * @param fechaRegistro the fechaRegistro to set
	 */
	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}
	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}
	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	/**
	 * @return the orden
	 */
	public Integer getOrden() {
		return orden;
	}
	/**
	 * @param orden the orden to set
	 */
	public void setOrden(Integer orden) {
		this.orden = orden;
	}
	/**
	 * @return the usuarioModifico
	 */
	public Long getUsuarioModifico() {
		return usuarioModifico;
	}
	/**
	 * @param usuarioModifico the usuarioModifico to set
	 */
	public void setUsuarioModifico(Long usuarioModifico) {
		this.usuarioModifico = usuarioModifico;
	}
	/**
	 * @return the catTipoRecurso
	 */
	public CatalogoComunDTO getCatTipoRecurso() {
		return catTipoRecurso;
	}
	/**
	 * @param catTipoRecurso the catTipoRecurso to set
	 */
	public void setCatTipoRecurso(CatalogoComunDTO catTipoRecurso) {
		this.catTipoRecurso = catTipoRecurso;
	}
	/**
	 * @return the relAreaRecursos
	 */
	public List<RelAreaRecursoDTO> getRelAreaRecursos() {
		return relAreaRecursos;
	}
	/**
	 * @param relAreaRecursos the relAreaRecursos to set
	 */
	public void setRelAreaRecursos(List<RelAreaRecursoDTO> relAreaRecursos) {
		this.relAreaRecursos = relAreaRecursos;
	}


}
