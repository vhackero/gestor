package mx.gob.sedesol.basegestor.model.entities.admin;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the cat_roles database table.
 * 
 */
@Entity
@Table(name="cat_roles")
@NamedQuery(name="CatRol.findAll", query="SELECT c FROM CatRol c")
public class CatRol implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_rol")
	private Integer idRol;
	
	@Column(name="clave")
	private String clave;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_actualizacion")
	private Date fechaActualizacion;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_registro")
	private Date fechaRegistro;

	@Column(name="nombre")
	private String nombre;

	@Column(name="usuario_modifica")
	private Long usuarioModifica;
	
	@Column(name="activo")
	private boolean activo;

	//bi-directional many-to-one association to RelPersonaRole
	@OneToMany(mappedBy="rol")
	private List<RelPersonaRol> relPersonaRoles;

	//bi-directional many-to-one association to RelRolFuncionalidad
	@OneToMany(mappedBy="rol")
	private List<RelRolFuncionalidad> relRolFuncionalidads;

	public CatRol() {
	}

	public Integer getIdRol() {
		return this.idRol;
	}

	public void setIdRol(Integer idRol) {
		this.idRol = idRol;
	}

	/**
	 * @return the clave
	 */
	public String getClave() {
		return clave;
	}

	/**
	 * @param clave the clave to set
	 */
	public void setClave(String clave) {
		this.clave = clave;
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

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public boolean getActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	public Long getUsuarioModifica() {
		return this.usuarioModifica;
	}

	public void setUsuarioModifica(Long usuarioModifica) {
		this.usuarioModifica = usuarioModifica;
	}

	public List<RelPersonaRol> getRelPersonaRoles() {
		return this.relPersonaRoles;
	}

	public void setRelPersonaRoles(List<RelPersonaRol> relPersonaRoles) {
		this.relPersonaRoles = relPersonaRoles;
	}

	public RelPersonaRol addRelPersonaRole(RelPersonaRol relPersonaRole) {
		getRelPersonaRoles().add(relPersonaRole);
		relPersonaRole.setRol(this);

		return relPersonaRole;
	}

	public RelPersonaRol removeRelPersonaRole(RelPersonaRol relPersonaRole) {
		getRelPersonaRoles().remove(relPersonaRole);
		relPersonaRole.setRol(null);

		return relPersonaRole;
	}

	public List<RelRolFuncionalidad> getRelRolFuncionalidads() {
		return this.relRolFuncionalidads;
	}

	public void setRelRolFuncionalidads(List<RelRolFuncionalidad> relRolFuncionalidads) {
		this.relRolFuncionalidads = relRolFuncionalidads;
	}

	public RelRolFuncionalidad addRelRolFuncionalidad(RelRolFuncionalidad relRolFuncionalidad) {
		getRelRolFuncionalidads().add(relRolFuncionalidad);
		relRolFuncionalidad.setRol(this);

		return relRolFuncionalidad;
	}

	public RelRolFuncionalidad removeRelRolFuncionalidad(RelRolFuncionalidad relRolFuncionalidad) {
		getRelRolFuncionalidads().remove(relRolFuncionalidad);
		relRolFuncionalidad.setRol(null);

		return relRolFuncionalidad;
	}

}