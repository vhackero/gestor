package mx.gob.sedesol.basegestor.model.entities.logisticainfraestructura;

import java.io.Serializable;
import javax.persistence.*;


import java.util.Date;
import java.util.List;


/**
 * The persistent class for the tbl_configuracion_area database table.
 * 
 */
@Entity
@Table(name="tbl_configuracion_area")
@NamedQuery(name="TblConfiguracionArea.findAll", query="SELECT t FROM TblConfiguracionArea t")
public class TblConfiguracionArea implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_config_area")
	private Integer idConfigArea;

	@Column(name="email_cc")
	private String emailCc;

	@Column(name="email_responsable")
	private String emailResponsable;

	@Column(name="email_solicitante")
	private String emailSolicitante;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_actualizacion")
	private Date fechaActualizacion;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_registro")
	private Date fechaRegistro;

	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_estatus")
	private CatEstatusArea catEstatusArea;

	@Column(name="ruta_img_dos")
	private String rutaImgDos;

	@Column(name="ruta_img_tres")
	private String rutaImgTres;

	@Column(name="ruta_img_uno")
	private String rutaImgUno;

	private String solicitante;

	@Column(name="usuario_modifico")
	private Long usuarioModifico;

	//bi-directional many-to-one association to RelAreaDistribucion
	@OneToMany(mappedBy="tblConfiguracionArea", cascade=CascadeType.ALL)
	private List<RelAreaDistribucion> relAreaDistribucion;

	//bi-directional many-to-one association to RelAreaRecurso
	@OneToMany(mappedBy="tblConfiguracionArea",cascade=CascadeType.ALL)
	private List<RelAreaRecurso> relAreaRecursos;

	//bi-directional many-to-one association to CatAreasSede
	//@ManyToOne(fetch=FetchType.LAZY)
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_area_sede")
	private CatAreasSede catAreasSede;

	public TblConfiguracionArea() {
	}

	public Integer getIdConfigArea() {
		return this.idConfigArea;
	}

	public void setIdConfigArea(Integer idConfigArea) {
		this.idConfigArea = idConfigArea;
	}

	public String getEmailCc() {
		return this.emailCc;
	}

	public void setEmailCc(String emailCc) {
		this.emailCc = emailCc;
	}

	public String getEmailResponsable() {
		return this.emailResponsable;
	}

	public void setEmailResponsable(String emailResponsable) {
		this.emailResponsable = emailResponsable;
	}

	public String getEmailSolicitante() {
		return this.emailSolicitante;
	}

	public void setEmailSolicitante(String emailSolicitante) {
		this.emailSolicitante = emailSolicitante;
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

	public String getRutaImgDos() {
		return this.rutaImgDos;
	}

	public void setRutaImgDos(String rutaImgDos) {
		this.rutaImgDos = rutaImgDos;
	}

	public String getRutaImgTres() {
		return this.rutaImgTres;
	}

	public void setRutaImgTres(String rutaImgTres) {
		this.rutaImgTres = rutaImgTres;
	}

	public String getRutaImgUno() {
		return this.rutaImgUno;
	}

	public void setRutaImgUno(String rutaImgUno) {
		this.rutaImgUno = rutaImgUno;
	}

	public String getSolicitante() {
		return this.solicitante;
	}

	public void setSolicitante(String solicitante) {
		this.solicitante = solicitante;
	}

	public Long getUsuarioModifico() {
		return this.usuarioModifico;
	}

	public void setUsuarioModifico(Long usuarioModifico) {
		this.usuarioModifico = usuarioModifico;
	}

	public List<RelAreaDistribucion> getRelAreaDistribucion() {
		return this.relAreaDistribucion;
	}

	public void setRelAreaDistribucion(List<RelAreaDistribucion> relAreaDistribucion) {
		this.relAreaDistribucion = relAreaDistribucion;
	}

	public List<RelAreaRecurso> getRelAreaRecursos() {
		return this.relAreaRecursos;
	}

	public void setRelAreaRecursos(List<RelAreaRecurso> relAreaRecursos) {
		this.relAreaRecursos = relAreaRecursos;
	}

	public CatAreasSede getCatAreasSede() {
		return this.catAreasSede;
	}

	public void setCatAreasSede(CatAreasSede catAreasSede) {
		this.catAreasSede = catAreasSede;
	}

	public CatEstatusArea getCatEstatusArea() {
		return catEstatusArea;
	}

	public void setCatEstatusArea(CatEstatusArea catEstatusArea) {
		this.catEstatusArea = catEstatusArea;
	}

}