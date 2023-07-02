package mx.gob.sedesol.basegestor.model.entities.admin;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the tbl_domicilios_persona database table.
 * 
 */
@Entity
@Table(name="tbl_domicilios_persona")
@NamedQuery(name="TblDomiciliosPersona.findAll", query="SELECT t FROM TblDomiciliosPersona t")
public class TblDomiciliosPersona implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_domicilio_persona")
	private Long idDomicilioPersona;

	private String calle;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_actualizacion")
	private Date fechaActualizacion;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_registro")
	private Date fechaRegistro;

	@Column(name="numero_exterior")
	private String numeroExterior;
	
	@Column(name="numero_interior")
	private String numeroInterior;

	@Column(name="usuario_modifico")
	private Long usuarioModifico;

	//bi-directional many-to-one association to TblPersona
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_persona")
	private TblPersona persona;

	//bi-directional many-to-one association to CatAsentamiento
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_asentamiento")
	private CatAsentamiento asentamiento;

	public TblDomiciliosPersona() {
	}

	public Long getIdDomicilioPersona() {
		return this.idDomicilioPersona;
	}

	public void setIdDomicilioPersona(Long idDomicilioPersona) {
		this.idDomicilioPersona = idDomicilioPersona;
	}

	public String getCalle() {
		return this.calle;
	}

	public void setCalle(String calle) {
		this.calle = calle;
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

	/**
	 * @return the asentamiento
	 */
	public CatAsentamiento getAsentamiento() {
		return asentamiento;
	}

	/**
	 * @param asentamiento the asentamiento to set
	 */
	public void setAsentamiento(CatAsentamiento asentamiento) {
		this.asentamiento = asentamiento;
	}

	public String getNumeroExterior() {
		return numeroExterior;
	}

	public void setNumeroExterior(String numeroExterior) {
		this.numeroExterior = numeroExterior;
	}

	public String getNumeroInterior() {
		return numeroInterior;
	}

	public void setNumeroInterior(String numeroInterior) {
		this.numeroInterior = numeroInterior;
	}


}