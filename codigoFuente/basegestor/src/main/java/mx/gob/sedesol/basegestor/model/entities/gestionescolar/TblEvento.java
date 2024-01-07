package mx.gob.sedesol.basegestor.model.entities.gestionescolar;

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

import mx.gob.sedesol.basegestor.model.entities.encuestas.RelEncuestaEventoCapacitacion;
import mx.gob.sedesol.basegestor.model.entities.gestionaprendizaje.TblAmbienteVirtualAprendizaje;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.CatModalidadPlanPrograma;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.TblFichaDescriptivaPrograma;

@Entity
@Table(name = "tbl_eventos")
@NamedQuery(name = "TblEvento.findAll", query = "SELECT t FROM TblEvento t")
public class TblEvento implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_evento")
	private Integer idEvento;

	@Column(name = "id_programa")
	private Integer idPrograma;

	@Column(name = "alcance_ec")
	private String alcanceEc;

	private Boolean constancia;

	@Column(name = "porcentaje_min_asist")
	private Integer procentajeMinAsistencia;

	@Column(name = "cve_evento_cap")
	private String cveEventoCap;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_actualizacion", insertable = false, updatable=false)
	private Date fechaActualizacion;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_registro", insertable = false, updatable=false)
	private Date fechaRegistro;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_final")
	private Date fechaFinal;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_inicial")
	private Date fechaInicial;

	@Column(name = "no_registro_ec")
	private String noRegistroEc;

	@Column(name = "nombre_ec", columnDefinition = "TEXT")
	private String nombreEc;

	@Column(name = "objetivo_general_ec", columnDefinition = "TEXT")
	private String objetivoGeneralEc;

	@Column(name = "perfil_ec", columnDefinition = "TEXT")
	private String perfilEc;

	@Column(name = "requisitos_ec", columnDefinition = "TEXT")
	private String requisitosEc;

	@Column(name = "usuario_modifico")
	private Long usuarioModifico;

	@Column(name = "calificacion_min_aprobatoria")
	private String calificacionMinAprobatoria;

	@ManyToOne
	@JoinColumn(name = "id_categoria_ec")
	private CatCategoriaEventoCapacitacion categoriaEC;

	@Column(name = "id_dirigido")
	private Integer idDirigido;

	@Column(name = "id_clasificacion_ava")
	private Integer idClasificacionAva;

	@Column(name = "id_evento_capacitacion_anterior")
	private Integer idEventoCapacitacionAnterior;

	@Column(name = "id_curso_lms_borrador")
	private Integer idCursoLmsBorrador;

	@Column(name = "id_entidad_federativa")
	private Integer idEntidadFederativa;

	@Column(name = "id_municipio")
	private String idMunicipio;

	@Column(name = "id_programa_social")
	private Integer idProgramaSocial;

	@Column(name = "oportunidades_evaluacion")
	private Integer oportunidadesEvaluacion;

	@Column(name = "id_plataforma_lms_borrador")
	private Integer idPlataformaLmsBorrador;

	// bi-directional many-to-one association to CatEstadoEventoCapacitacion
	@ManyToOne
	@JoinColumn(name = "id_estatus_ec")
	private CatEstadoEventoCapacitacion catEstadoEventoCapacitacion;

	// bi-directional many-to-one association to CatDestinatariosEC
	@ManyToOne
	@JoinColumn(name = "id_destinatario")
	private CatDestinatariosEc idDestinatario;

	// bi-directional many-to-one association to TblFichaDescriptivaPrograma
	@ManyToOne
	@JoinColumn(name = "id_programa", insertable = false, updatable = false)
	private TblFichaDescriptivaPrograma fichaDescriptivaPrograma;

	// bi-directional many-to-one association to TblAmbienteVirtualAprendizaje
	@OneToMany(mappedBy = "eventoCapacitacion")
	private List<TblAmbienteVirtualAprendizaje> ambienteVirtualAprendizajes;

	// bi-directional many-to-one association to RelReponsableProduccionEc
	@OneToMany(mappedBy = "eventoCapacitacion")
	private List<RelReponsableProduccionEc> reponsableProduccionEcs;

	// bi-directional many-to-one association to CatModalidadPlanPrograma
	@ManyToOne
	@JoinColumn(name = "modalidad")
	private CatModalidadPlanPrograma catModalidadPlanPrograma;

	@OneToMany(mappedBy = "tblEvento")
	private List<RelEncuestaEventoCapacitacion> relEncuestaEvento;

	@Column(name = "url_imagen")
	private String urlImagen;

	@Column(name = "aplica_encuesta")
	private Boolean aplicaEncuesta;

	@Column(name = "version_encuesta")
	private String versionEncuesta;

	@Column(name = "valor_asistencia")
	private Integer valorAsistencia;

	@Column(name = "valor_falta")
	private Integer valorFalta;

	@Column(name = "valor_falta_justificada")
	private Integer valorFaltaJustificada;

	@Column(name = "valor_inconcluso")
	private Integer valorInconcluso;

	@Column(name = "valor_retardo")
	private Integer valorRetardo;

	@Column(name = "tipo_calificacion")
	private Integer tpoCalificacion;

	@Column(name = "tipo_dictamen")
	private Integer tpoDictamen;

	@Column(name = "valor_asistencia_dictamen")
	private Integer valorAsistenciaDictamen;

	@Column(name = "valor_calificacion_dictamen")
	private Integer valorCalificacionDictamen;

	@Column(name = "privado", columnDefinition = "TINYINT(1)")
	private Boolean privado;
	
	// bi-directional many-to-one association to TblInscripcion
	@OneToMany(mappedBy = "eventoCapacitacion")
	private List<TblInscripcion> inscripciones;
	
	public TblEvento() {
	}

	public String getAlcanceEc() {
		return this.alcanceEc;
	}

	public void setAlcanceEc(String alcanceEc) {
		this.alcanceEc = alcanceEc;
	}

	public Boolean getConstancia() {
		return constancia;
	}

	public void setConstancia(Boolean constancia) {
		this.constancia = constancia;
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

	public CatEstadoEventoCapacitacion getCatEstadoEventoCapacitacion() {
		return catEstadoEventoCapacitacion;
	}

	public void setCatEstadoEventoCapacitacion(CatEstadoEventoCapacitacion catEstadoEventoCapacitacion) {
		this.catEstadoEventoCapacitacion = catEstadoEventoCapacitacion;
	}

	public CatModalidadPlanPrograma getCatModalidadPlanPrograma() {
		return catModalidadPlanPrograma;
	}

	public void setCatModalidadPlanPrograma(CatModalidadPlanPrograma catModalidadPlanPrograma) {
		this.catModalidadPlanPrograma = catModalidadPlanPrograma;
	}

	public String getNoRegistroEc() {
		return this.noRegistroEc;
	}

	public void setNoRegistroEc(String noRegistroEc) {
		this.noRegistroEc = noRegistroEc;
	}

	public String getNombreEc() {
		return this.nombreEc;
	}

	public void setNombreEc(String nombreEc) {
		this.nombreEc = nombreEc;
	}

	public String getObjetivoGeneralEc() {
		return this.objetivoGeneralEc;
	}

	public void setObjetivoGeneralEc(String objetivoGeneralEc) {
		this.objetivoGeneralEc = objetivoGeneralEc;
	}

	public String getPerfilEc() {
		return this.perfilEc;
	}

	public void setPerfilEc(String perfilEc) {
		this.perfilEc = perfilEc;
	}

	public String getRequisitosEc() {
		return this.requisitosEc;
	}

	public void setRequisitosEc(String requisitosEc) {
		this.requisitosEc = requisitosEc;
	}

	public Long getUsuarioModifico() {
		return this.usuarioModifico;
	}

	public void setUsuarioModifico(Long usuarioModifico) {
		this.usuarioModifico = usuarioModifico;
	}

	// public Integer getId() {
	// return this.id;
	// }
	// public void setId(Integer id) {
	// this.id = id;
	// }

	public Integer getIdPrograma() {
		return this.idPrograma;
	}

	public void setIdPrograma(Integer idPrograma) {
		this.idPrograma = idPrograma;
	}

	public TblFichaDescriptivaPrograma getFichaDescriptivaPrograma() {
		return fichaDescriptivaPrograma;
	}

	public void setFichaDescriptivaPrograma(TblFichaDescriptivaPrograma fichaDescriptivaPrograma) {
		this.fichaDescriptivaPrograma = fichaDescriptivaPrograma;
	}

	public Date getFechaFinal() {
		return fechaFinal;
	}

	public void setFechaFinal(Date fechaFinal) {
		this.fechaFinal = fechaFinal;
	}

	public Date getFechaInicial() {
		return fechaInicial;
	}

	public void setFechaInicial(Date fechaInicial) {
		this.fechaInicial = fechaInicial;
	}

	public String getCveEventoCap() {
		return cveEventoCap;
	}

	public void setCveEventoCap(String cveEventoCap) {
		this.cveEventoCap = cveEventoCap;
	}

	public List<TblAmbienteVirtualAprendizaje> getAmbienteVirtualAprendizajes() {
		return ambienteVirtualAprendizajes;
	}

	public void setAmbienteVirtualAprendizajes(List<TblAmbienteVirtualAprendizaje> ambienteVirtualAprendizajes) {
		this.ambienteVirtualAprendizajes = ambienteVirtualAprendizajes;
	}

	public List<RelReponsableProduccionEc> getReponsableProduccionEcs() {
		return reponsableProduccionEcs;
	}

	public void setReponsableProduccionEcs(List<RelReponsableProduccionEc> reponsableProduccionEcs) {
		this.reponsableProduccionEcs = reponsableProduccionEcs;
	}

	public String getCalificacionMinAprobatoria() {
		return calificacionMinAprobatoria;
	}

	public void setCalificacionMinAprobatoria(String calificacionMinAprobatoria) {
		this.calificacionMinAprobatoria = calificacionMinAprobatoria;
	}

	public CatCategoriaEventoCapacitacion getCategoriaEC() {
		return categoriaEC;
	}

	public void setCategoriaEC(CatCategoriaEventoCapacitacion categoriaEC) {
		this.categoriaEC = categoriaEC;
	}

	public Integer getIdDirigido() {
		return idDirigido;
	}

	public void setIdDirigido(Integer idDirigido) {
		this.idDirigido = idDirigido;
	}

	public Integer getIdEntidadFederativa() {
		return idEntidadFederativa;
	}

	public void setIdEntidadFederativa(Integer idEntidadFederativa) {
		this.idEntidadFederativa = idEntidadFederativa;
	}

	public String getIdMunicipio() {
		return idMunicipio;
	}

	public void setIdMunicipio(String idMunicipio) {
		this.idMunicipio = idMunicipio;
	}

	public Integer getIdProgramaSocial() {
		return idProgramaSocial;
	}

	public void setIdProgramaSocial(Integer idProgramaSocial) {
		this.idProgramaSocial = idProgramaSocial;
	}

	public Integer getOportunidadesEvaluacion() {
		return oportunidadesEvaluacion;
	}

	public void setOportunidadesEvaluacion(Integer oportunidadesEvaluacion) {
		this.oportunidadesEvaluacion = oportunidadesEvaluacion;
	}

	public Integer getIdClasificacionAva() {
		return idClasificacionAva;
	}

	public Integer getIdEventoCapacitacionAnterior() {
		return idEventoCapacitacionAnterior;
	}

	public Integer getIdCursoLmsBorrador() {
		return idCursoLmsBorrador;
	}

	public void setIdClasificacionAva(Integer idClasificacionAva) {
		this.idClasificacionAva = idClasificacionAva;
	}

	public void setIdEventoCapacitacionAnterior(Integer idEventoCapacitacionAnterior) {
		this.idEventoCapacitacionAnterior = idEventoCapacitacionAnterior;
	}

	public void setIdCursoLmsBorrador(Integer idCursoLmsBorrador) {
		this.idCursoLmsBorrador = idCursoLmsBorrador;
	}

	public Integer getIdPlataformaLmsBorrador() {
		return idPlataformaLmsBorrador;
	}

	public void setIdPlataformaLmsBorrador(Integer IdPlataformaLmsBorrador) {
		this.idPlataformaLmsBorrador = IdPlataformaLmsBorrador;
	}

	public String getUrlImagen() {
		return urlImagen;
	}

	public Boolean getAplicaEncuesta() {
		return aplicaEncuesta;
	}

	public Integer getValorAsistencia() {
		return valorAsistencia;
	}

	public Integer getValorFalta() {
		return valorFalta;
	}

	public Integer getValorFaltaJustificada() {
		return valorFaltaJustificada;
	}

	public Integer getValorInconcluso() {
		return valorInconcluso;
	}

	public Integer getValorRetardo() {
		return valorRetardo;
	}

	public Integer getValorAsistenciaDictamen() {
		return valorAsistenciaDictamen;
	}

	public Integer getValorCalificacionDictamen() {
		return valorCalificacionDictamen;
	}

	public void setUrlImagen(String urlImagen) {
		this.urlImagen = urlImagen;
	}

	public void setAplicaEncuesta(Boolean aplicaEncuesta) {
		this.aplicaEncuesta = aplicaEncuesta;
	}

	public void setValorAsistencia(Integer valorAsistencia) {
		this.valorAsistencia = valorAsistencia;
	}

	public void setValorFalta(Integer valorFalta) {
		this.valorFalta = valorFalta;
	}

	public void setValorFaltaJustificada(Integer valorFaltaJustificada) {
		this.valorFaltaJustificada = valorFaltaJustificada;
	}

	public void setValorInconcluso(Integer valorInconcluso) {
		this.valorInconcluso = valorInconcluso;
	}

	public void setValorRetardo(Integer valorRetardo) {
		this.valorRetardo = valorRetardo;
	}

	public void setValorAsistenciaDictamen(Integer valorAsistenciaDictamen) {
		this.valorAsistenciaDictamen = valorAsistenciaDictamen;
	}

	public void setValorCalificacionDictamen(Integer valorCalificacionDictamen) {
		this.valorCalificacionDictamen = valorCalificacionDictamen;
	}

	public String getVersionEncuesta() {
		return versionEncuesta;
	}

	public Boolean getPrivado() {
		return privado;
	}

	public void setVersionEncuesta(String versionEncuesta) {
		this.versionEncuesta = versionEncuesta;
	}

	public void setPrivado(Boolean privado) {
		this.privado = privado;
	}

	/**
	 * @return the tpoCalificacion
	 */
	public Integer getTpoCalificacion() {
		return tpoCalificacion;
	}

	/**
	 * @param tpoCalificacion
	 *            the tpoCalificacion to set
	 */
	public void setTpoCalificacion(Integer tpoCalificacion) {
		this.tpoCalificacion = tpoCalificacion;
	}

	/**
	 * @return the tpoDictamen
	 */
	public Integer getTpoDictamen() {
		return tpoDictamen;
	}

	/**
	 * @param tpoDictamen
	 *            the tpoDictamen to set
	 */
	public void setTpoDictamen(Integer tpoDictamen) {
		this.tpoDictamen = tpoDictamen;
	}

	/**
	 * @return the idDestinatario
	 */
	public CatDestinatariosEc getIdDestinatario() {
		return idDestinatario;
	}

	/**
	 * @param idDestinatario
	 *            the idDestinatario to set
	 */
	public void setIdDestinatario(CatDestinatariosEc idDestinatario) {
		this.idDestinatario = idDestinatario;
	}

	/**
	 * @return the idEvento
	 */
	public Integer getIdEvento() {
		return idEvento;
	}

	/**
	 * @param idEvento
	 *            the idEvento to set
	 */
	public void setIdEvento(Integer idEvento) {
		this.idEvento = idEvento;
	}

	public Integer getProcentajeMinAsistencia() {
		return procentajeMinAsistencia;
	}

	public void setProcentajeMinAsistencia(Integer procentajeMinAsistencia) {
		this.procentajeMinAsistencia = procentajeMinAsistencia;
	}

	public List<RelEncuestaEventoCapacitacion> getRelEncuestaEvento() {
		return relEncuestaEvento;
	}

	public void setRelEncuestaEvento(List<RelEncuestaEventoCapacitacion> relEncuestaEvento) {
		this.relEncuestaEvento = relEncuestaEvento;
	}

	public List<TblInscripcion> getInscripciones() {
		return inscripciones;
	}

	public void setInscripciones(List<TblInscripcion> inscripciones) {
		this.inscripciones = inscripciones;
	}

}