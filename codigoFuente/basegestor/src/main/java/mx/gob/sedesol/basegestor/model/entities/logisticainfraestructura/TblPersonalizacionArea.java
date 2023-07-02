package mx.gob.sedesol.basegestor.model.entities.logisticainfraestructura;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the tbl_reservacion_area database table.
 * 
 */
@Entity
@Table(name="tbl_personalizacion_area")
@NamedQuery(name="TblPersonalizacionArea.findAll", query="SELECT t FROM TblPersonalizacionArea t")
public class TblPersonalizacionArea implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_personalizacion_area")
	private Integer idPersonalizacion;

	@Column(name="email_responsable")
	private String emailResponsable;

	@Column(name="evento_privado")
	private Integer eventoPrivado;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_actualizacion")
	private Date fechaActualizacion;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_registro")
	private Date fechaRegistro;


	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_area_sede")
	private CatAreasSede catAreasSede;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_distribucion")
	private CatDistribucionArea distribucionArea;

	@Column(name="observaciones_academicas", columnDefinition="TEXT")
	private String observacionesAcademicas;

	@Column(name="servicio_cafeteria")
	private boolean servicioCafeteria;

	@Column(name="email_cc")
	private String emailCc;

	@Column(name="usuario_modifico")
	private Long usuarioModifico;
	
	@OneToMany(mappedBy="tblPersonalizacionArea")
	private List<RelPersonalizacionRecurso> relPersonalizacionRecursos;

	//bi-directional many-to-one association to TblEvento
//	@OneToOne(fetch=FetchType.LAZY)
//	@JoinColumn(name="id_evento_capacitacion")
//	private TblEvento tblEvento;
	

	public TblPersonalizacionArea() {
	}

	public Integer getEventoPrivado() {
		return this.eventoPrivado;
	}

	public void setEventoPrivado(Integer eventoPrivado) {
		this.eventoPrivado = eventoPrivado;
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

	public String getObservacionesAcademicas() {
		return this.observacionesAcademicas;
	}

	public void setObservacionesAcademicas(String observacionesAcademicas) {
		this.observacionesAcademicas = observacionesAcademicas;
	}

	public boolean getServicioCafeteria() {
		return this.servicioCafeteria;
	}

	public void setServicioCafeteria(boolean servicioCafeteria) {
		this.servicioCafeteria = servicioCafeteria;
	}

	/**
	 * @return the emailResponsable
	 */
	public String getEmailResponsable() {
		return emailResponsable;
	}

	/**
	 * @param emailResponsable the emailResponsable to set
	 */
	public void setEmailResponsable(String emailResponsable) {
		this.emailResponsable = emailResponsable;
	}

	/**
	 * @return the emailCc
	 */
	public String getEmailCc() {
		return emailCc;
	}

	/**
	 * @param emailCc the emailCc to set
	 */
	public void setEmailCc(String emailCc) {
		this.emailCc = emailCc;
	}

	public Long getUsuarioModifico() {
		return this.usuarioModifico;
	}

	public void setUsuarioModifico(Long usuarioModifico) {
		this.usuarioModifico = usuarioModifico;
	}

	/**
	 * @return the idPersonalizacion
	 */
	public Integer getIdPersonalizacion() {
		return idPersonalizacion;
	}

	/**
	 * @param idPersonalizacion the idPersonalizacion to set
	 */
	public void setIdPersonalizacion(Integer idPersonalizacion) {
		this.idPersonalizacion = idPersonalizacion;
	}

	/**
	 * @return the catAreasSede
	 */
	public CatAreasSede getCatAreasSede() {
		return catAreasSede;
	}

	/**
	 * @param catAreasSede the catAreasSede to set
	 */
	public void setCatAreasSede(CatAreasSede catAreasSede) {
		this.catAreasSede = catAreasSede;
	}

	/**
	 * @return the relPersonalizacionRecursos
	 */
	public List<RelPersonalizacionRecurso> getRelPersonalizacionRecursos() {
		return relPersonalizacionRecursos;
	}

	/**
	 * @param relPersonalizacionRecursos the relPersonalizacionRecursos to set
	 */
	public void setRelPersonalizacionRecursos(List<RelPersonalizacionRecurso> relPersonalizacionRecursos) {
		this.relPersonalizacionRecursos = relPersonalizacionRecursos;
	}

	public CatDistribucionArea getDistribucionArea() {
		return distribucionArea;
	}

	public void setDistribucionArea(CatDistribucionArea distribucionArea) {
		this.distribucionArea = distribucionArea;
	}

}