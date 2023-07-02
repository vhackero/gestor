package mx.gob.sedesol.basegestor.model.entities.admin;

import java.io.Serializable;

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


/**
 * The persistent class for the tbl_domicilios_persona database table.
 * 
 */
@Entity
@Table(name="tbl_domicilios_persona")
@NamedQuery(name="TblDomiciliosPersonaReporteUsuario.findAll", query="SELECT t FROM TblDomiciliosPersonaReporteUsuario t")
public class TblDomiciliosPersonaReporteUsuario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_domicilio_persona")
	private Long idDomicilioPersona;

	//bi-directional many-to-one association to TblPersona
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="id_persona")
	private TblPersonaReporteUsuario persona;

	//bi-directional many-to-one association to CatAsentamiento
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="id_asentamiento")
	private CatAsentamientoReporteUsuario asentamiento;

	public TblDomiciliosPersonaReporteUsuario() {
	}

	public Long getIdDomicilioPersona() {
		return this.idDomicilioPersona;
	}

	public void setIdDomicilioPersona(Long idDomicilioPersona) {
		this.idDomicilioPersona = idDomicilioPersona;
	}

	/**
	 * @return the persona
	 */
	public TblPersonaReporteUsuario getPersona() {
		return persona;
	}

	/**
	 * @param persona the persona to set
	 */
	public void setPersona(TblPersonaReporteUsuario persona) {
		this.persona = persona;
	}

	/**
	 * @return the asentamiento
	 */
	public CatAsentamientoReporteUsuario getAsentamiento() {
		return asentamiento;
	}

	/**
	 * @param asentamiento the asentamiento to set
	 */
	public void setAsentamiento(CatAsentamientoReporteUsuario asentamiento) {
		this.asentamiento = asentamiento;
	}



}