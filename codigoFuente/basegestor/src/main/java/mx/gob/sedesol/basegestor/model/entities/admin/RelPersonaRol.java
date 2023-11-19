package mx.gob.sedesol.basegestor.model.entities.admin;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the rel_persona_roles database table.
 * 
 */
@Entity
@Table(name="rel_persona_roles")
@NamedQuery(name="RelPersonaRol.findAll", query="SELECT r FROM RelPersonaRol r")
public class RelPersonaRol implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_persona_rol")
	private Long idPersonaRol;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_actualizacion", insertable = false, updatable=false)
	private Date fechaActualizacion;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_registro", insertable = false, updatable=false)
	private Date fechaRegistro;

	@Column(name="usuario_modifico")
	private Long usuarioModifico;

	//bi-directional many-to-one association to TblPersona
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="id_persona")
	private TblPersona persona;

	//bi-directional many-to-one association to CatRole
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="id_rol")
	private CatRol rol;

	@Column(name="activo")
	private Integer activo; 
	
	public RelPersonaRol() {
	}
	
	public RelPersonaRol(CatRol rol, TblPersona persona, long usuarioModifico) {
		this.fechaRegistro = new Date();
		this.usuarioModifico = usuarioModifico;
		this.rol = rol;
		this.persona = persona;
		this.activo = 1;
	}

	public Long getIdPersonaRol() {
		return this.idPersonaRol;
	}

	public void setIdPersonaRol(Long idPersonaRol) {
		this.idPersonaRol = idPersonaRol;
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

	public Long getUsuarioModifico() {
		return this.usuarioModifico;
	}

	public void setUsuarioModifico(Long usuarioModifico) {
		this.usuarioModifico = usuarioModifico;
	}

	public TblPersona getPersona() {
		return this.persona;
	}

	public void setPersona(TblPersona persona) {
		this.persona = persona;
	}

	public CatRol getRol() {
		return this.rol;
	}

	public void setRol(CatRol rol) {
		this.rol = rol;
	}

	public Integer getActivo() {
		return activo;
	}

	public void setActivo(Integer activo) {
		this.activo = activo;
	}

	@Override
	public String toString() {
		return "RelPersonaRol [idPersonaRol=" + idPersonaRol + ", fechaActualizacion=" + fechaActualizacion
				+ ", fechaRegistro=" + fechaRegistro + ", usuarioModifico=" + usuarioModifico + ", persona=" + persona
				+ ", rol=" + rol + ", activo=" + activo + "]";
	}
	

}