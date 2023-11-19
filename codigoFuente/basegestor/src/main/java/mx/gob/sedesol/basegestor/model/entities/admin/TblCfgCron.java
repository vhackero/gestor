package mx.gob.sedesol.basegestor.model.entities.admin;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;



/**
 * The persistent class for the tbl_cfg_cron database table.
 * 
 */
@Entity
@Table(name="tbl_cfg_cron")
@NamedQuery(name="TblCfgCron.findAll", query="SELECT t FROM TblCfgCron t")
public class TblCfgCron implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_cfg_cron")
	private Integer idCfgCron;

	private Boolean activo;
	
	private String descripcion;
	
	@Column(name="clave")
	private String clave;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_actualizacion", insertable = false, updatable=false)
	private Date fechaActualizacion;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_registro", insertable = false, updatable=false)
	private Date fechaRegistro;

	@Column(name="nombre_tarea")
	private String nombreTarea;

	

	@Column(name="patron_ejecucion")
	private String patronEjecucion;

	@Column(name="usuario_modifico")
	private Long usuarioModifico;

	public TblCfgCron() {
	}

	public Integer getIdCfgCron() {
		return this.idCfgCron;
	}

	public void setIdCfgCron(Integer idCfgCron) {
		this.idCfgCron = idCfgCron;
	}

	public Boolean getActivo() {
		return this.activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	public Date getFechaActualizacion() {
		return this.fechaActualizacion;
	}

	public void setFechaActualizacion(Date fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}

	public Date getFechaRegistro() {
		return this.fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public String getNombreTarea() {
		return this.nombreTarea;
	}

	public void setNombreTarea(String nombreTarea) {
		this.nombreTarea = nombreTarea;
	}



	public String getPatronEjecucion() {
		return this.patronEjecucion;
	}

	public void setPatronEjecucion(String patronEjecucion) {
		this.patronEjecucion = patronEjecucion;
	}

	public Long getUsuarioModifico() {
		return this.usuarioModifico;
	}

	public void setUsuarioModifico(Long usuarioModifico) {
		this.usuarioModifico = usuarioModifico;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}
	
	

}