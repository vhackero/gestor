package mx.gob.sedesol.basegestor.model.entities.admin;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the rel_rol_funcionalidad database table.
 * 
 */
@Entity
@Table(name="rel_rol_funcionalidad")
@NamedQuery(name="RelRolFuncionalidad.findAll", query="SELECT r FROM RelRolFuncionalidad r")
public class RelRolFuncionalidad implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_rol_funcionalidad")
	private Long idRolFuncionalidad;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_actualizacion")
	private Date fechaActualizacion;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_registro")
	private Date fechaRegistro;

	@Column(name="usuario_modifico")
	private Long usuarioModifico;

	//bi-directional many-to-one association to TblFuncionalidade
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_funcionalidad")
	private TblFuncionalidad funcionalidad;

	//bi-directional many-to-one association to CatRole
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_rol")
	private CatRol rol;
	
	@Column(name="activo")
	private Integer activo;

	public RelRolFuncionalidad() {
	}

	public Long getIdRolFuncionalidad() {
		return this.idRolFuncionalidad;
	}

	public void setIdRolFuncionalidad(Long idRolFuncionalidad) {
		this.idRolFuncionalidad = idRolFuncionalidad;
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

	public Integer getActivo() {
		return activo;
	}

	public void setActivo(Integer activo) {
		this.activo = activo;
	}

	/**
	 * @return the funcionalidad
	 */
	public TblFuncionalidad getFuncionalidad() {
		return funcionalidad;
	}

	/**
	 * @param funcionalidad the funcionalidad to set
	 */
	public void setFuncionalidad(TblFuncionalidad funcionalidad) {
		this.funcionalidad = funcionalidad;
	}

	/**
	 * @return the rol
	 */
	public CatRol getRol() {
		return rol;
	}

	/**
	 * @param rol the rol to set
	 */
	public void setRol(CatRol rol) {
		this.rol = rol;
	}

}