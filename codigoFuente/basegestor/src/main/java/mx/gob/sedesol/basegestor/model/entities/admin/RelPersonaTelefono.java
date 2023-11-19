package mx.gob.sedesol.basegestor.model.entities.admin;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the rel_persona_telefono database table.
 * 
 */
@Entity
@Table(name="rel_persona_telefono")
@NamedQuery(name="RelPersonaTelefono.findAll", query="SELECT r FROM RelPersonaTelefono r")
public class RelPersonaTelefono implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_persona_telefono")
	private Integer idPersonaTelefono;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_actualizacion", insertable = false, updatable=false)
	private Date fechaActualizacion;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_registro", insertable = false, updatable=false)
	private Date fechaRegistro;

	@Column(name="sso_telefono")
	private String telefono;
	
	@Column(name="sso_extensionDeTelefono")
	private String extension;

	@Column(name="usuario_modifico")
	private Long usuarioModifico;

	//bi-directional many-to-one association to TblPersona
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_persona")
	private TblPersona persona;

	//bi-directional many-to-one association to CatTiposTelefono
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_tipo_telefono")
	private CatTiposTelefono tipoTelefono;
	
	@Column(name="activo")
	private Integer activo;

	public RelPersonaTelefono() {
	}

	public Integer getIdPersonaTelefono() {
		return this.idPersonaTelefono;
	}

	public void setIdPersonaTelefono(Integer idPersonaTelefono) {
		this.idPersonaTelefono = idPersonaTelefono;
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

	public String getTelefono() {
		return this.telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public Long getUsuarioModifico() {
		return this.usuarioModifico;
	}

	public void setUsuarioModifico(Long usuarioModifico) {
		this.usuarioModifico = usuarioModifico;
	}

	public CatTiposTelefono getTipoTelefono() {
		return tipoTelefono;
	}

	public void setTipoTelefono(CatTiposTelefono tipoTelefono) {
		this.tipoTelefono = tipoTelefono;
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
	 * @return the persona
	 */
	public TblPersona getPersona() {
		return persona;
	}

	/**
	 * @param persona the persona to set
	 */
	public void setPersona(TblPersona persona) {
		this.persona = persona;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

}