package mx.gob.sedesol.basegestor.commons.dto.admin;

import java.io.Serializable;
import java.util.Date;

import mx.gob.sedesol.basegestor.commons.dto.ComunDTO;

/**
 * Clase DTO para CatPArametroWSMoodle
 * @author eolf
 *
 */
public class ParametroWSMoodleDTO extends ComunDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 258117299587846138L;
	
	private Integer idParametroWSMoodle;
	private String host;
	private String path;
	private String service;
	private String username;
	private String password;
	private String outh;
	private String server;
	private Boolean activo;
	private Date fechaRegistro;
	private Date fechaActualizacion;
	private String nombre;
	private String descripcion;
	private Long usuarioModifico;
	private Integer orden;
	
	public ParametroWSMoodleDTO() {
		super();
	}

	public Integer getIdParametroWSMoodle() {
		return idParametroWSMoodle;
	}

	public void setIdParametroWSMoodle(Integer idParametroWSMoodle) {
		this.idParametroWSMoodle = idParametroWSMoodle;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getOuth() {
		return outh;
	}

	public void setOuth(String outh) {
		this.outh = outh;
	}

	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
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
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param descripcion the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
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

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ParametroWSMoodleDTO [idParametroWSMoodle=" + idParametroWSMoodle + ", host=" + host + ", path=" + path
				+ ", service=" + service + ", username=" + username + ", password=" + password + ", outh=" + outh
				+ ", server=" + server + ", activo=" + activo + ", fechaRegistro=" + fechaRegistro
				+ ", fechaActualizacion=" + fechaActualizacion + ", nombre=" + nombre + ", descripcion=" + descripcion + ", usuarioModifico="
				+ usuarioModifico + ", orden=" + orden + "]";
	}

}
