package mx.gob.sedesol.basegestor.model.entities.gestionaprendizaje;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
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
import mx.gob.sedesol.basegestor.model.entities.admin.CatParametroWSMoodle;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.TblEvento;


/**
 * The persistent class for the tbl_ambiente_virtual_aprendizaje database table.
 * 
 */
@Entity
@Table(name="tbl_ambiente_virtual_aprendizaje")
@NamedQuery(name="TblAmbienteVirtualAprendizaje.findAll", query="SELECT t FROM TblAmbienteVirtualAprendizaje t")
public class TblAmbienteVirtualAprendizaje implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private Boolean activo;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_actualizacion")
	private Date fechaActualizacion;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_registro")
	private Date fechaRegistro;

	@Column(name="porcentaje_avance_ava")
	private Byte porcentajeAvance;

	@Column(name="usuario_modifico")
	private Long usuarioModifico;

	@Column(name="validacion_ava")
	private Boolean validacionAva;

	//bi-directional many-to-one association to RelUnidadOaAva
	@OneToMany(mappedBy="ambienteVirtualAprendizaje")
	private List<RelUnidadOaAva> relUnidadOaAvas;

	//bi-directional many-to-one association to CatEstadoAva
	@ManyToOne
	@JoinColumn(name="id_cat_estado_ava")
	private CatEstadoAva catEstadoAva;

	//bi-directional many-to-one association to TblEvento
	@ManyToOne
	@JoinColumn(name="id_evento_capacitacion")
	private TblEvento eventoCapacitacion;
	
	@ManyToOne
	@JoinColumn(name="id_parametro_wsmoodle")
	private CatParametroWSMoodle plataformaMoodle;
	
	@Column(name="id_curso_lms")
	private Integer idCursoLms;
	
	@Column(name="url_lms")
	private String urlLms;
	
	@Column(name="ava_archivado")
	private Boolean esAvaArchivado;
	
	@Column(name="id_ava_clon_lms")
	private Integer idAvaClonLms;
	
	
	public TblAmbienteVirtualAprendizaje() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	

	public Boolean getActivo() {
		return activo;
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

	
	


	public Byte getPorcentajeAvance() {
		return porcentajeAvance;
	}

	public void setPorcentajeAvance(Byte porcentajeAvance) {
		this.porcentajeAvance = porcentajeAvance;
	}

	public Long getUsuarioModifico() {
		return this.usuarioModifico;
	}

	public void setUsuarioModifico(Long usuarioModifico) {
		this.usuarioModifico = usuarioModifico;
	}

	
	
	public Boolean getValidacionAva() {
		return validacionAva;
	}

	public void setValidacionAva(Boolean validacionAva) {
		this.validacionAva = validacionAva;
	}

	public List<RelUnidadOaAva> getRelUnidadOaAvas() {
		return this.relUnidadOaAvas;
	}

	public void setRelUnidadOaAvas(List<RelUnidadOaAva> relUnidadOaAvas) {
		this.relUnidadOaAvas = relUnidadOaAvas;
	}


	public CatEstadoAva getCatEstadoAva() {
		return this.catEstadoAva;
	}

	public void setCatEstadoAva(CatEstadoAva catEstadoAva) {
		this.catEstadoAva = catEstadoAva;
	}


	public TblEvento getEventoCapacitacion() {
		return eventoCapacitacion;
	}

	public void setEventoCapacitacion(TblEvento eventoCapacitacion) {
		this.eventoCapacitacion = eventoCapacitacion;
	}

	public CatParametroWSMoodle getPlataformaMoodle() {
		return plataformaMoodle;
	}

	public void setPlataformaMoodle(CatParametroWSMoodle plataformaMoodle) {
		this.plataformaMoodle = plataformaMoodle;
	}

	public Integer getIdCursoLms() {
		return idCursoLms;
	}

	public void setIdCursoLms(Integer idCursoLms) {
		this.idCursoLms = idCursoLms;
	}

	public String getUrlLms() {
		return urlLms;
	}

	public void setUrlLms(String urlLms) {
		this.urlLms = urlLms;
	}

	public Boolean getEsAvaArchivado() {
		return esAvaArchivado;
	}

	public void setEsAvaArchivado(Boolean esAvaArchivado) {
		this.esAvaArchivado = esAvaArchivado;
	}

	public Integer getIdAvaClonLms() {
		return idAvaClonLms;
	}

	public void setIdAvaClonLms(Integer idAvaClonLms) {
		this.idAvaClonLms = idAvaClonLms;
	}

	
	
	
}