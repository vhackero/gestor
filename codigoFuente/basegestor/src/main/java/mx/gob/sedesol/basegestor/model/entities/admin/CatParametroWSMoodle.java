package mx.gob.sedesol.basegestor.model.entities.admin;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

/**
 * Clase entity para la tabla cat_parametros_sistema
 * @author eolf
 *
 */
@Entity
@Table(name = "cat_parametros_wsmoodle")
public class CatParametroWSMoodle implements java.io.Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8010602749283903653L;
	
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

	public CatParametroWSMoodle() {
		super();
	}

	@Id
	@Column(name = "id_parametro_wsmoodle", unique = true, nullable = false, length = 11)
	@NotNull
	@Length(max = 20)
	public Integer getIdParametroWSMoodle() {
		return idParametroWSMoodle;
	}

	public void setIdParametroWSMoodle(Integer idParametroWSMoodle) {
		this.idParametroWSMoodle = idParametroWSMoodle;
	}

	@Column(name = "host", nullable = false, length = 200)
	@NotNull
	@Length(max = 200)
	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	@Column(name = "path", nullable = false, length = 200)
	@NotNull
	@Length(max = 200)
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@Column(name = "service", nullable = false, length = 10)
	@NotNull
	@Length(max = 10)
	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	@Column(name = "username", nullable = false, length = 10)
	@NotNull
	@Length(max = 10)
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name = "password", nullable = false, length = 50)
	@NotNull
	@Length(max = 50)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "outh", nullable = false, length = 200)
	@NotNull
	@Length(max = 200)
	public String getOuth() {
		return outh;
	}

	public void setOuth(String outh) {
		this.outh = outh;
	}

	@Column(name = "server", nullable = false, length = 200)
	@NotNull
	@Length(max = 200)
	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	@Column(name = "activo", nullable = false, length = 3)
	@NotNull
	@Length(max = 3)
	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_registro", nullable = false, length = 19)
	@NotNull
	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	/**
	 * @return the fechaActualizacion
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_actualizacion", nullable = false, length = 19)
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
	@Column(name = "nombre", nullable = false, length = 100)
	@Length(max = 100)
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
	@Column(name = "descripcion", nullable = false, length = 255)
	@Length(max = 255)
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
	@Column(name = "usuario_modifico", nullable = false, length = 20)
	@Length(max = 20)
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
	@Column(name = "orden", nullable = false, length = 3)
	@Length(max = 3)
	public Integer getOrden() {
		return orden;
	}

	/**
	 * @param orden the orden to set
	 */
	public void setOrden(Integer orden) {
		this.orden = orden;
	}

}
