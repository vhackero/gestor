package mx.gob.sedesol.basegestor.model.entities.admin;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the rel_persona_correo database table.
 * 
 */
@Entity
@Table(name="rel_persona_correo")
@NamedQuery(name="RelPersonaCorreo.findAll", query="SELECT r FROM RelPersonaCorreo r")
public class RelPersonaCorreo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_persona_correo")
	private Integer idPersonaCorreo;

	@Column(name="sso_correoElectronico")
	private String correoElectronico;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_actualizacion", insertable = false, updatable=false)
	private Date fechaActualizacion;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_registro", insertable = false, updatable=false)
	private Date fechaRegistro;

	@Column(name="usuario_modifico")
	private Long usuarioModifico;

	//bi-directional many-to-one association to TblPersona
	@ManyToOne
	@JoinColumn(name="id_persona")
	private TblPersona persona;

	//bi-directional many-to-one association to CatTiposCorreo
	@ManyToOne
	@JoinColumn(name="id_tipo_correo")
	private CatTiposCorreo tipoCorreo;
	
	@Column(name="activo")
	private Integer activo;
	
	@Column(name="nivel_prioridad")
	private Integer nivelPrioridad;

	public RelPersonaCorreo() {
	}

	public Integer getIdPersonaCorreo() {
		return this.idPersonaCorreo;
	}

	public void setIdPersonaCorreo(Integer idPersonaCorreo) {
		this.idPersonaCorreo = idPersonaCorreo;
	}

	public String getCorreoElectronico() {
		return this.correoElectronico;
	}

	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
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

	public CatTiposCorreo getTipoCorreo() {
		return this.tipoCorreo;
	}

	public void setTipoCorreo(CatTiposCorreo tipoCorreo) {
		this.tipoCorreo = tipoCorreo;
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
	 * @return the nivelPrioridad
	 */
	public Integer getNivelPrioridad() {
		return nivelPrioridad;
	}

	/**
	 * @param nivelPrioridad the nivelPrioridad to set
	 */
	public void setNivelPrioridad(Integer nivelPrioridad) {
		this.nivelPrioridad = nivelPrioridad;
	}

}