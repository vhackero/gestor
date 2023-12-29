package mx.gob.sedesol.basegestor.model.entities.planesyprogramas;

import java.io.Serializable;
import javax.persistence.*;

import mx.gob.sedesol.basegestor.model.entities.admin.TblOrganismoGubernamental;
import mx.gob.sedesol.basegestor.model.entities.admin.TblPersona;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.TblEvento;

import java.util.Date;
import java.util.List;

/**
 * The persistent class for the tbl_ficha_descriptiva_programa database table.
 * 
 */
@Entity
@Table(name = "tbl_ficha_descriptiva_programa")
@NamedQuery(name = "TblFichaDescriptivaPrograma.findAll", query = "SELECT t FROM TblFichaDescriptivaPrograma t")
public class TblFichaDescriptivaPrograma implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_programa")
	private Integer idPrograma;

	@Column(name = "conocimietos_previos", columnDefinition = "TEXT")
	private String conocimietosPrevios;

	@Column(name = "cve_programa")
	private String cvePrograma;

	@Column(name = "descripcion", columnDefinition = "TEXT")
	private String descripcion;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_arranque")
	private Date fechaArranque;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_produccion")
	private Date fechaProduccion;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_solicitud")
	private Date fechaSolicitud;

	@Column(name = "id_alcance_programa")
	private Integer idAlcancePrograma;
	
	// bi-directional one-to-one association to TblPlanes
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_plan")
	private TblPlan plan;

	@Column(name = "id_nivel_dominio_programa")
	private Integer idNivelDominioPrograma;

	@Column(name = "identificador_final")
	private String identificadorFinal;

	@Column(name = "instrumento_aprendizaje")
	private String instrumentoAprendizaje;

	@Column(name = "justificacion_academica", columnDefinition = "TEXT")
	private String justificacionAcademica;

	@Column(name = "metodologia", columnDefinition = "TEXT")
	private String metodologia;

	@Column(name = "nombre_tentativo")
	private String nombreTentativo;

	@Column(name = "objetivos_generales", columnDefinition = "TEXT")
	private String objetivosGenerales;

	@Column(name = "presentacion", columnDefinition = "TEXT")
	private String presentacion;

	@Column(name = "propositos", columnDefinition = "TEXT")
	private String propositos;

	@Column(name = "requerimiento_equipo")
	private String requerimientoEquipo;

	@Column(name = "requisitos_ingreso")
	private String requisitosIngreso;

	@Column(name = "calificacion_min_aprobatoria")
	private String calificacionMinAprobatoria;

	@OneToMany(mappedBy = "fichaDescriptivaPrograma", cascade = CascadeType.ALL)
	private List<RelProgramaCargaHoraria> relProgramaDuracion;

	@OneToMany(mappedBy = "fichaDescriptivaPrograma", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private List<RelProgramaAutore> relProgramaAutores;

	@OneToMany(mappedBy = "fichaDescriptivaPrograma", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private List<RelProgramaResponsable> relProgramaResponsables;

	@OneToMany(mappedBy = "fichaDescriptivaPrograma", cascade = CascadeType.ALL)
	private List<RelProgramaCompEspecifica> relProgramaComEspecificas;

	// bi-directional many-to-one association to TblEvento
	@OneToMany(mappedBy = "fichaDescriptivaPrograma", cascade = CascadeType.ALL)
	private List<TblEvento> tblEventos;

	@OneToMany(mappedBy = "tblFichaDescriptivaPrograma", cascade = CascadeType.ALL)
	private List<RelProgramaPersonalExterno> relProgEstPersonalExterno;

	@ManyToOne
	@JoinColumn(name = "id_institucion_certifica")
	private CatInstitucionesCertificadora idInstitucionCertifica;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_programa_antecedente")
	private TblFichaDescriptivaPrograma programaAntecedente;

	// bi-directional one-to-one association to TblMallaCurricular
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_programa")
	private TblMallaCurricular tblMallaCurricular;

	// bi-directional many-to-one association to CatAmbientesAprendizajeEc
	@OneToOne
	@JoinColumn(name = "id_ambiente_aprendizaje")
	private CatAmbientesAprendizajeEc catAmbientesAprendizajeEc;

	// bi-directional many-to-one association to CatStatusPrograma
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_estatus_programa")
	private CatStatusPrograma catStatusPrograma;

	// bi-directional many-to-one association to CatNivelEnsenanzaPrograma
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_nivel_programa")
	private CatNivelEnsenanzaPrograma catNivelEnsenanzaPrograma;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_nivel_conocimiento")
	private CatNivelConocimiento catNivelConocimiento;

	// bi-directional many-to-one association to CatTipoEventoEc
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_tipo_evento_programa")
	private CatTipoEventoEc catTipoEventoEc;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_actualizacion", insertable = false, updatable = false)
	private Date fechaActualizacion;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_registro", insertable = false, updatable = false)
	private Date fechaRegistro;

	@Column(name = "usuario_modifico")
	private Long usuarioModifico;

	@Column(name = "id_tpo_competencia")
	private Integer tipoCompetencia;

	@Column(name = "id_eje_capacitacion")
	private Integer ejeCapacitacion;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_modalidad_programa")
	private CatModalidadPlanPrograma catModalidad;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_vigencia_inicial")
	private Date fechaVigInicial;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_vigencia_final")
	private Date fechaVigFinal;

	@Column(name = "encuestas_kp")
	private Boolean encuestaKp;

	// bi-directional many-to-one association to TblOrganismoGubernamental
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_org_gubernamental")
	private TblOrganismoGubernamental organismoGubernamental;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_area_resp_org_gub")
	private TblOrganismoGubernamental areaResponsable;

	@Column(name = "perfil_egreso", columnDefinition = "TEXT")
	private String perfilEgreso;

	@Column(name = "perfil_ingreso", columnDefinition = "TEXT")
	private String perfilIngreso;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_coordinador_academico")
	private TblPersona coordinadorAcademico;

	@Column(name = "id_categoria_mdl")
	private Integer idCategoriaMdl;

	@Column(name = "tipo")
	private String tipo;

	@Column(name = "creditos")
	private Integer creditos;

//	@OneToOne(fetch=FetchType.LAZY)
//	@JoinColumn(name="id_estructura_personal_externo")
//	private TblEstPersonalExterno catDirigidoAExterno;

	public TblFichaDescriptivaPrograma() {
	}

	public Integer getCreditos() {
		return creditos;
	}

	public void setCreditos(Integer creditos) {
		this.creditos = creditos;
	}
	
	public TblPlan getPlan() {
		return plan;
	}

	public void setPlan(TblPlan plan) {
		this.plan = plan;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Integer getIdPrograma() {
		return this.idPrograma;
	}

	public void setIdPrograma(Integer idPrograma) {
		this.idPrograma = idPrograma;
	}

	public String getConocimietosPrevios() {
		return this.conocimietosPrevios;
	}

	public void setConocimietosPrevios(String conocimietosPrevios) {
		this.conocimietosPrevios = conocimietosPrevios;
	}

	public String getCvePrograma() {
		return this.cvePrograma;
	}

	public void setCvePrograma(String cvePrograma) {
		this.cvePrograma = cvePrograma;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Date getFechaArranque() {
		return this.fechaArranque;
	}

	public void setFechaArranque(Date fechaArranque) {
		this.fechaArranque = fechaArranque;
	}

	public Date getFechaProduccion() {
		return this.fechaProduccion;
	}

	public void setFechaProduccion(Date fechaProduccion) {
		this.fechaProduccion = fechaProduccion;
	}

	public Date getFechaSolicitud() {
		return this.fechaSolicitud;
	}

	public void setFechaSolicitud(Date fechaSolicitud) {
		this.fechaSolicitud = fechaSolicitud;
	}

	public Integer getIdAlcancePrograma() {
		return this.idAlcancePrograma;
	}

	public void setIdAlcancePrograma(Integer idAlcancePrograma) {
		this.idAlcancePrograma = idAlcancePrograma;
	}

	public CatInstitucionesCertificadora getIdInstitucionCertifica() {
		return idInstitucionCertifica;
	}

	public void setIdInstitucionCertifica(CatInstitucionesCertificadora idInstitucionCertifica) {
		this.idInstitucionCertifica = idInstitucionCertifica;
	}

	public Integer getIdNivelDominioPrograma() {
		return this.idNivelDominioPrograma;
	}

	public void setIdNivelDominioPrograma(Integer idNivelDominioPrograma) {
		this.idNivelDominioPrograma = idNivelDominioPrograma;
	}

	public TblFichaDescriptivaPrograma getProgramaAntecedente() {
		return programaAntecedente;
	}

	public void setProgramaAntecedente(TblFichaDescriptivaPrograma programaAntecedente) {
		this.programaAntecedente = programaAntecedente;
	}

	public String getIdentificadorFinal() {
		return this.identificadorFinal;
	}

	public void setIdentificadorFinal(String identificadorFinal) {
		this.identificadorFinal = identificadorFinal;
	}

	/**
	 * @return the catModalidad
	 */
	public CatModalidadPlanPrograma getCatModalidad() {
		return catModalidad;
	}

	/**
	 * @param catModalidad the catModalidad to set
	 */
	public void setCatModalidad(CatModalidadPlanPrograma catModalidad) {
		this.catModalidad = catModalidad;
	}

	public String getInstrumentoAprendizaje() {
		return this.instrumentoAprendizaje;
	}

	public void setInstrumentoAprendizaje(String instrumentoAprendizaje) {
		this.instrumentoAprendizaje = instrumentoAprendizaje;
	}

	public String getJustificacionAcademica() {
		return this.justificacionAcademica;
	}

	public void setJustificacionAcademica(String justificacionAcademica) {
		this.justificacionAcademica = justificacionAcademica;
	}

	public String getMetodologia() {
		return this.metodologia;
	}

	public void setMetodologia(String metodologia) {
		this.metodologia = metodologia;
	}

	public String getNombreTentativo() {
		return this.nombreTentativo;
	}

	public void setNombreTentativo(String nombreTentativo) {
		this.nombreTentativo = nombreTentativo;
	}

	public String getObjetivosGenerales() {
		return this.objetivosGenerales;
	}

	public void setObjetivosGenerales(String objetivosGenerales) {
		this.objetivosGenerales = objetivosGenerales;
	}

	public String getPresentacion() {
		return this.presentacion;
	}

	public void setPresentacion(String presentacion) {
		this.presentacion = presentacion;
	}

	public String getPropositos() {
		return this.propositos;
	}

	public void setPropositos(String propositos) {
		this.propositos = propositos;
	}

	public String getRequerimientoEquipo() {
		return this.requerimientoEquipo;
	}

	public void setRequerimientoEquipo(String requerimientoEquipo) {
		this.requerimientoEquipo = requerimientoEquipo;
	}

	public String getRequisitosIngreso() {
		return this.requisitosIngreso;
	}

	public void setRequisitosIngreso(String requisitosIngreso) {
		this.requisitosIngreso = requisitosIngreso;
	}

	public List<RelProgramaCargaHoraria> getRelProgramaDuracion() {
		return this.relProgramaDuracion;
	}

	public void setRelProgramaDuracion(List<RelProgramaCargaHoraria> relProgramaDuracion) {
		this.relProgramaDuracion = relProgramaDuracion;
	}

	public List<RelProgramaResponsable> getRelProgramaResponsables() {
		return this.relProgramaResponsables;
	}

	public void setRelProgramaResponsables(List<RelProgramaResponsable> relProgramaResponsables) {
		this.relProgramaResponsables = relProgramaResponsables;
	}

	public TblMallaCurricular getTblMallaCurricular() {
		return this.tblMallaCurricular;
	}

	public void setTblMallaCurricular(TblMallaCurricular tblMallaCurricular) {
		this.tblMallaCurricular = tblMallaCurricular;
	}

	public CatAmbientesAprendizajeEc getCatAmbientesAprendizajeEc() {
		return this.catAmbientesAprendizajeEc;
	}

	public void setCatAmbientesAprendizajeEc(CatAmbientesAprendizajeEc catAmbientesAprendizajeEc) {
		this.catAmbientesAprendizajeEc = catAmbientesAprendizajeEc;
	}

	public CatStatusPrograma getCatStatusPrograma() {
		return this.catStatusPrograma;
	}

	public void setCatStatusPrograma(CatStatusPrograma catStatusPrograma) {
		this.catStatusPrograma = catStatusPrograma;
	}

	public CatNivelEnsenanzaPrograma getCatNivelEnsenanzaPrograma() {
		return this.catNivelEnsenanzaPrograma;
	}

	public void setCatNivelEnsenanzaPrograma(CatNivelEnsenanzaPrograma catNivelEnsenanzaPrograma) {
		this.catNivelEnsenanzaPrograma = catNivelEnsenanzaPrograma;
	}

	public CatTipoEventoEc getCatTipoEventoEc() {
		return this.catTipoEventoEc;
	}

	public void setCatTipoEventoEc(CatTipoEventoEc catTipoEventoEc) {
		this.catTipoEventoEc = catTipoEventoEc;
	}

	public Date getFechaActualizacion() {
		return fechaActualizacion;
	}

	public void setFechaActualizacion(Date fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public Long getUsuarioModifico() {
		return usuarioModifico;
	}

	public void setUsuarioModifico(Long usuarioModifico) {
		this.usuarioModifico = usuarioModifico;
	}

	/**
	 * @return the tipoCompetencia
	 */
	public Integer getTipoCompetencia() {
		return tipoCompetencia;
	}

	/**
	 * @param tipoCompetencia the tipoCompetencia to set
	 */
	public void setTipoCompetencia(Integer tipoCompetencia) {
		this.tipoCompetencia = tipoCompetencia;
	}

	/**
	 * @return the ejeCapacitacion
	 */
	public Integer getEjeCapacitacion() {
		return ejeCapacitacion;
	}

	/**
	 * @param ejeCapacitacion the ejeCapacitacion to set
	 */
	public void setEjeCapacitacion(Integer ejeCapacitacion) {
		this.ejeCapacitacion = ejeCapacitacion;
	}

	/**
	 * @return the fechaVigInicial
	 */
	public Date getFechaVigInicial() {
		return fechaVigInicial;
	}

	/**
	 * @param fechaVigInicial the fechaVigInicial to set
	 */
	public void setFechaVigInicial(Date fechaVigInicial) {
		this.fechaVigInicial = fechaVigInicial;
	}

	/**
	 * @return the fechaVigFinal
	 */
	public Date getFechaVigFinal() {
		return fechaVigFinal;
	}

	/**
	 * @param fechaVigFinal the fechaVigFinal to set
	 */
	public void setFechaVigFinal(Date fechaVigFinal) {
		this.fechaVigFinal = fechaVigFinal;
	}

	/**
	 * @return the catNivelConocimiento
	 */
	public CatNivelConocimiento getCatNivelConocimiento() {
		return catNivelConocimiento;
	}

	/**
	 * @param catNivelConocimiento the catNivelConocimiento to set
	 */
	public void setCatNivelConocimiento(CatNivelConocimiento catNivelConocimiento) {
		this.catNivelConocimiento = catNivelConocimiento;
	}

	/**
	 * @return the encuestaKp
	 */
	public Boolean getEncuestaKp() {
		return encuestaKp;
	}

	/**
	 * @param encuestaKp the encuestaKp to set
	 */
	public void setEncuestaKp(Boolean encuestaKp) {
		this.encuestaKp = encuestaKp;
	}

	/**
	 * @return the organismoGubernamental
	 */
	public TblOrganismoGubernamental getOrganismoGubernamental() {
		return organismoGubernamental;
	}

	/**
	 * @param organismoGubernamental the organismoGubernamental to set
	 */
	public void setOrganismoGubernamental(TblOrganismoGubernamental organismoGubernamental) {
		this.organismoGubernamental = organismoGubernamental;
	}

	/**
	 * @return the perfilEgreso
	 */
	public String getPerfilEgreso() {
		return perfilEgreso;
	}

	/**
	 * @param perfilEgreso the perfilEgreso to set
	 */
	public void setPerfilEgreso(String perfilEgreso) {
		this.perfilEgreso = perfilEgreso;
	}

	/**
	 * @return the perfilIngreso
	 */
	public String getPerfilIngreso() {
		return perfilIngreso;
	}

	/**
	 * @param perfilIngreso the perfilIngreso to set
	 */
	public void setPerfilIngreso(String perfilIngreso) {
		this.perfilIngreso = perfilIngreso;
	}

	public String getCalificacionMinAprobatoria() {
		return this.calificacionMinAprobatoria;
	}

	public void setCalificacionMinAprobatoria(String calificacionMinAprobatoria) {
		this.calificacionMinAprobatoria = calificacionMinAprobatoria;
	}

	public List<RelProgramaAutore> getRelProgramaAutores() {
		return relProgramaAutores;
	}

	public void setRelProgramaAutores(List<RelProgramaAutore> relProgramaAutores) {
		this.relProgramaAutores = relProgramaAutores;
	}

	/**
	 * @return the relProgramaComEspecificas
	 */
	public List<RelProgramaCompEspecifica> getRelProgramaComEspecificas() {
		return relProgramaComEspecificas;
	}

	/**
	 * @param relProgramaComEspecificas the relProgramaComEspecificas to set
	 */
	public void setRelProgramaComEspecificas(List<RelProgramaCompEspecifica> relProgramaComEspecificas) {
		this.relProgramaComEspecificas = relProgramaComEspecificas;
	}

	/**
	 * @return the areaResponsable
	 */
	public TblOrganismoGubernamental getAreaResponsable() {
		return areaResponsable;
	}

	/**
	 * @param areaResponsable the areaResponsable to set
	 */
	public void setAreaResponsable(TblOrganismoGubernamental areaResponsable) {
		this.areaResponsable = areaResponsable;
	}

	public List<TblEvento> getTblEventos() {
		return tblEventos;
	}

	public void setTblEventos(List<TblEvento> tblEventos) {
		this.tblEventos = tblEventos;
	}

	/**
	 * @return the relProgEstPersonalExterno
	 */
	public List<RelProgramaPersonalExterno> getRelProgEstPersonalExterno() {
		return relProgEstPersonalExterno;
	}

	/**
	 * @param relProgEstPersonalExterno the relProgEstPersonalExterno to set
	 */
	public void setRelProgEstPersonalExterno(List<RelProgramaPersonalExterno> relProgEstPersonalExterno) {
		this.relProgEstPersonalExterno = relProgEstPersonalExterno;
	}

	/**
	 * @return the coordinadorAcademico
	 */
	public TblPersona getCoordinadorAcademico() {
		return coordinadorAcademico;
	}

	/**
	 * @param coordinadorAcademico the coordinadorAcademico to set
	 */
	public void setCoordinadorAcademico(TblPersona coordinadorAcademico) {
		this.coordinadorAcademico = coordinadorAcademico;
	}

	/**
	 * @return the idCategoriaMdl
	 */
	public Integer getIdCategoriaMdl() {
		return idCategoriaMdl;
	}

	/**
	 * @param idCategoriaMdl the idCategoriaMdl to set
	 */
	public void setIdCategoriaMdl(Integer idCategoriaMdl) {
		this.idCategoriaMdl = idCategoriaMdl;
	}

}