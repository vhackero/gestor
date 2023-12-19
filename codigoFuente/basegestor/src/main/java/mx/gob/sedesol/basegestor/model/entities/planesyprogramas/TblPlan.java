package mx.gob.sedesol.basegestor.model.entities.planesyprogramas;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import mx.gob.sedesol.basegestor.model.entities.admin.TblOrganismoGubernamental;

/**
 * The persistent class for the tbl_planes database table.
 * 
 */
@Entity
@Table(name="tbl_planes")
@NamedQuery(name="TblPlan.findAll", query="SELECT t FROM TblPlan t")
public class TblPlan implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_plan")
	private Integer idPlan;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_actualizacion", insertable = false, updatable=false)
	private Date fechaActualizacion;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_inicio")
	private Date fechaInicio;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_registro", insertable = false, updatable=false)
	private Date fechaRegistro;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_termino")
	private Date fechaTermino;

	@Column(columnDefinition="TEXT")
	private String fundamentacion;

	private String identificador;

	private String nombre;

	private Integer horasCredito;

	@Column(columnDefinition="TEXT")
	private String objetivos;

	@Column(name="perfil_egreso",columnDefinition="TEXT")
	private String perfilEgreso;

	@Column(name="perfil_ingreso",columnDefinition="TEXT")
	private String perfilIngreso;

	@Column(name="ponderacion_calif")
	private Integer ponderacionCalif;

	@Column(name="usuario_modifico")
	private Long usuarioModifico;
	
	@Column(name="version")
	private Integer version;

	//bi-directional many-to-one association to RelPlanAptitude
	@OneToMany(mappedBy="tblPlan",cascade={CascadeType.ALL})
	private List<RelPlanAptitud> relPlanAptitudes;

	//bi-directional many-to-one association to RelPlanConocimiento
	@OneToMany(mappedBy="tblPlan",cascade={CascadeType.ALL})
	private List<RelPlanConocimiento> relPlanConocimientos;

	//bi-directional many-to-one association to RelPlanHabilidad
	@OneToMany(mappedBy="tblPlan",cascade={CascadeType.ALL})
	private List<RelPlanHabilidad> relPlanHabilidades;

	//bi-directional many-to-one association to CatCompetenciasPlan
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_competencia")
	private CatCompetenciasPlan catCompetenciasPlan;

	//bi-directional many-to-one association to CatDocumentosExpidePlan
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_documento_expide")
	private CatDocumentosExpidePlan catDocumentosExpidePlan;

	//bi-directional many-to-one association to CatEstatusPlan
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_estatus_plan")
	private CatEstatusPlan catEstatusPlan;

	//bi-directional many-to-one association to CatModalidadPlanPrograma
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_modalidad")
	private CatModalidadPlanPrograma catModalidadPlanPrograma;

	//bi-directional many-to-one association to CatNivelEnsenanzaPrograma
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_nivel_ensenanza")
	private CatNivelEnsenanzaPrograma catNivelEnsenanzaPrograma;

	//bi-directional many-to-one association to TblOrganismoGubernamental
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_org_gub")
	private TblOrganismoGubernamental tblOrganismoGubernamental;

	//bi-directional many-to-one association to CatTipoPlan
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_tpo_plan")
	private CatTipoPlan catTipoPlan;

	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_periodo")
	private CatPeriodo catPeriodo;

	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_tpo_competencia")
	private CatTipoCompetencia catTpoCompetencia;

	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_alcance_plan")
	private CatAlcancePlan catAlcancePlan;
	
	@Column(name="id_categoria_mdl")
	private Integer idCategoriaMdl;
	
	@OneToOne(fetch=FetchType.LAZY, targetEntity = CatCreditosPlan.class)
	@JoinColumn(name="id_creditos_plan")
	private CatCreditosPlan catCreditosPlan;
	
	@OneToOne(fetch=FetchType.LAZY, targetEntity = CatDivisionesPlan.class)
	@JoinColumn(name="id_divisiones_plan")
	private CatDivisionesPlan catDivisionesPlan;

	public TblPlan() {
	}

	public Integer getIdPlan() {
		return this.idPlan;
	}

	public void setIdPlan(Integer idPlan) {
		this.idPlan = idPlan;
	}

	public Date getFechaActualizacion() {
		return this.fechaActualizacion;
	}

	public void setFechaActualizacion(Date fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}

	public Date getFechaInicio() {
		return this.fechaInicio;
	}

	public CatPeriodo getCatPeriodo() {
		return catPeriodo;
	}

	public void setCatPeriodo(CatPeriodo catPeriodo) {
		this.catPeriodo = catPeriodo;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaRegistro() {
		return this.fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public Date getFechaTermino() {
		return this.fechaTermino;
	}

	public void setFechaTermino(Date fechaTermino) {
		this.fechaTermino = fechaTermino;
	}

	public String getFundamentacion() {
		return this.fundamentacion;
	}

	public void setFundamentacion(String fundamentacion) {
		this.fundamentacion = fundamentacion;
	}

	public String getIdentificador() {
		return this.identificador;
	}

	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Integer getHorasCredito() {
		return horasCredito;
	}

	public void setHorasCredito(Integer horasCredito) {
		this.horasCredito = horasCredito;
	}

	public String getObjetivos() {
		return this.objetivos;
	}

	public void setObjetivos(String objetivos) {
		this.objetivos = objetivos;
	}

	public String getPerfilEgreso() {
		return this.perfilEgreso;
	}

	public void setPerfilEgreso(String perfilEgreso) {
		this.perfilEgreso = perfilEgreso;
	}

	public String getPerfilIngreso() {
		return this.perfilIngreso;
	}

	public void setPerfilIngreso(String perfilIngreso) {
		this.perfilIngreso = perfilIngreso;
	}

	public Integer getPonderacionCalif() {
		return this.ponderacionCalif;
	}

	public void setPonderacionCalif(Integer ponderacionCalif) {
		this.ponderacionCalif = ponderacionCalif;
	}

	public Long getUsuarioModifico() {
		return this.usuarioModifico;
	}

	public void setUsuarioModifico(Long usuarioModifico) {
		this.usuarioModifico = usuarioModifico;
	}

	public List<RelPlanAptitud> getRelPlanAptitudes() {
		return this.relPlanAptitudes;
	}

	public void setRelPlanAptitudes(List<RelPlanAptitud> relPlanAptitudes) {
		this.relPlanAptitudes = relPlanAptitudes;
	}

	
	public List<RelPlanConocimiento> getRelPlanConocimientos() {
		return this.relPlanConocimientos;
	}

	public void setRelPlanConocimientos(List<RelPlanConocimiento> relPlanConocimientos) {
		this.relPlanConocimientos = relPlanConocimientos;
	}

	
	public List<RelPlanHabilidad> getRelPlanHabilidades() {
		return this.relPlanHabilidades;
	}

	public void setRelPlanHabilidades(List<RelPlanHabilidad> relPlanHabilidades) {
		this.relPlanHabilidades = relPlanHabilidades;
	}

	public CatCompetenciasPlan getCatCompetenciasPlan() {
		return this.catCompetenciasPlan;
	}

	public void setCatCompetenciasPlan(CatCompetenciasPlan catCompetenciasPlan) {
		this.catCompetenciasPlan = catCompetenciasPlan;
	}

	public CatDocumentosExpidePlan getCatDocumentosExpidePlan() {
		return this.catDocumentosExpidePlan;
	}

	public void setCatDocumentosExpidePlan(CatDocumentosExpidePlan catDocumentosExpidePlan) {
		this.catDocumentosExpidePlan = catDocumentosExpidePlan;
	}

	public CatEstatusPlan getCatEstatusPlan() {
		return this.catEstatusPlan;
	}

	public void setCatEstatusPlan(CatEstatusPlan catEstatusPlan) {
		this.catEstatusPlan = catEstatusPlan;
	}

	public CatModalidadPlanPrograma getCatModalidadPlanPrograma() {
		return this.catModalidadPlanPrograma;
	}

	public void setCatModalidadPlanPrograma(CatModalidadPlanPrograma catModalidadPlanPrograma) {
		this.catModalidadPlanPrograma = catModalidadPlanPrograma;
	}

	public CatNivelEnsenanzaPrograma getCatNivelEnsenanzaPrograma() {
		return this.catNivelEnsenanzaPrograma;
	}

	public void setCatNivelEnsenanzaPrograma(CatNivelEnsenanzaPrograma catNivelEnsenanzaPrograma) {
		this.catNivelEnsenanzaPrograma = catNivelEnsenanzaPrograma;
	}

	public TblOrganismoGubernamental getTblOrganismosGubernamentale() {
		return this.tblOrganismoGubernamental;
	}

	public void setTblOrganismosGubernamentale(TblOrganismoGubernamental tblOrganismoGubernamental) {
		this.tblOrganismoGubernamental = tblOrganismoGubernamental;
	}

	public CatTipoPlan getCatTipoPlan() {
		return this.catTipoPlan;
	}

	public void setCatTipoPlan(CatTipoPlan catTipoPlan) {
		this.catTipoPlan = catTipoPlan;
	}

	/**
	 * @return the tblOrganismoGubernamental
	 */
	public TblOrganismoGubernamental getTblOrganismoGubernamental() {
		return tblOrganismoGubernamental;
	}

	/**
	 * @param tblOrganismoGubernamental the tblOrganismoGubernamental to set
	 */
	public void setTblOrganismoGubernamental(TblOrganismoGubernamental tblOrganismoGubernamental) {
		this.tblOrganismoGubernamental = tblOrganismoGubernamental;
	}

	/**
	 * @return the catAlcancePlan
	 */
	public CatAlcancePlan getCatAlcancePlan() {
		return catAlcancePlan;
	}

	/**
	 * @param catAlcancePlan the catAlcancePlan to set
	 */
	public void setCatAlcancePlan(CatAlcancePlan catAlcancePlan) {
		this.catAlcancePlan = catAlcancePlan;
	}
	
	/**
	 * @return the catCreditosPlan
	 */
	public CatCreditosPlan getCatCreditosPlan() {
		return catCreditosPlan;
	}

	/**
	 * @param catCreditosPlan the catCreditosPlan to set
	 */
	public void setCatCreditosPlan(CatCreditosPlan catCreditosPlan) {
		this.catCreditosPlan = catCreditosPlan;
	}

	/**
	 * @return the catDivisionesPlan
	 */
	public CatDivisionesPlan getCatDivisionesPlan() {
		return catDivisionesPlan;
	}

	/**
	 * @param catDivisionesPlan the catDivisionesPlan to set
	 */
	public void setCatDivisionesPlan(CatDivisionesPlan catDivisionesPlan) {
		this.catDivisionesPlan = catDivisionesPlan;
	}

	/**
	 * @return the version
	 */
	public Integer getVersion() {
		return version;
	}

	/**
	 * @param version the version to set
	 */
	public void setVersion(Integer version) {
		this.version = version;
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

	public CatTipoCompetencia getCatTpoCompetencia() {
		return catTpoCompetencia;
	}

	public void setCatTpoCompetencia(CatTipoCompetencia catTpoCompetencia) {
		this.catTpoCompetencia = catTpoCompetencia;
	}
}