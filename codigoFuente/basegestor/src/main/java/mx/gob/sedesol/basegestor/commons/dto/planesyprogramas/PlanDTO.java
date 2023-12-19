package mx.gob.sedesol.basegestor.commons.dto.planesyprogramas;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;
import mx.gob.sedesol.basegestor.commons.dto.admin.BitacoraDTO;

import org.hibernate.validator.constraints.NotEmpty;

import mx.gob.sedesol.basegestor.commons.dto.admin.CatalogoComunDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.OrgGubernamentalDTO;
import mx.gob.sedesol.basegestor.commons.utils.MensajesSistemaEnum;

public class PlanDTO implements Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer idPlan;
	private Date fechaActualizacion;
	@NotNull(message=MensajesSistemaEnum.Constantes.GESTOR_WEB_GENERAL_DATO_REQUERIDO)
	private Date fechaInicio;
	private Date fechaRegistro;
	@NotNull(message=MensajesSistemaEnum.Constantes.GESTOR_WEB_GENERAL_DATO_REQUERIDO)
	private Date fechaTermino;
	private String fundamentacion;
	@NotEmpty(message=MensajesSistemaEnum.Constantes.GESTOR_WEB_GENERAL_DATO_REQUERIDO)
	private String identificador;
	@NotEmpty(message=MensajesSistemaEnum.Constantes.GESTOR_WEB_GENERAL_DATO_REQUERIDO)
	private String nombre;
	private String objetivos;
	private String perfilEgreso;
	private String perfilIngreso;
	private Integer ponderacionCalif;
	private Long usuarioModifico;
	private List<RelPlanAptitudDTO> relPlanAptitudes;
	private List<RelPlanConocimientoDTO> relPlanConocimientos;
	private List<RelPlanHabilidadDTO> relPlanHabilidades;
	@NotNull(message=MensajesSistemaEnum.Constantes.GESTOR_WEB_GENERAL_DATO_REQUERIDO)
	private CatalogoComunDTO catCompetenciasPlan;
	private CatalogoComunDTO catDocumentosExpidePlan;
	private CatalogoComunDTO catEstatusPlan;
	@NotNull(message=MensajesSistemaEnum.Constantes.GESTOR_WEB_GENERAL_DATO_REQUERIDO)
	private CatalogoComunDTO catModalidadPlanPrograma;
	@NotNull(message=MensajesSistemaEnum.Constantes.GESTOR_WEB_GENERAL_DATO_REQUERIDO)
	private CatalogoComunDTO catNivelEnsenanzaPrograma;
	@NotNull(message=MensajesSistemaEnum.Constantes.GESTOR_WEB_GENERAL_DATO_REQUERIDO)
	private OrgGubernamentalDTO tblOrganismoGubernamental;
	@NotNull(message=MensajesSistemaEnum.Constantes.GESTOR_WEB_GENERAL_DATO_REQUERIDO)
	private CatalogoComunDTO catTipoPlan;
	@NotNull(message=MensajesSistemaEnum.Constantes.GESTOR_WEB_GENERAL_DATO_REQUERIDO)
	private CatalogoComunDTO catPeriodo;
	private CatalogoComunDTO catAlcancePlan;
	private boolean ponderacion;
	private Integer version;
	private Integer idCategoriaMdl;
	private BitacoraDTO bitacoraDTO;
	private CatalogoComunDTO catTipoCompetencia;
	private CatalogoComunDTO catCreditosPlan;
	private CatalogoComunDTO catDivisionesPlan;
	private Integer horasCredito;
	
	/**
	 * @return the idPlan
	 */
	public Integer getIdPlan() {
		return idPlan;
	}
	/**
	 * @param idPlan the idPlan to set
	 */
	public void setIdPlan(Integer idPlan) {
		this.idPlan = idPlan;
	}

	public CatalogoComunDTO getCatPeriodo() {
		return catPeriodo;
	}

	public void setCatPeriodo(CatalogoComunDTO catPeriodo) {
		this.catPeriodo = catPeriodo;
	}

	/**
	 * @return the fechaActualizacion
	 */
	public Date getFechaActualizacion() {
		return fechaActualizacion;
	}
	/**
	 * @param fechaActualizacion the fechaActualizacion to set
	 */
	public void setFechaActualizacion(Date fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}
	/**
	 * @return the fechaInicio
	 */
	public Date getFechaInicio() {
		return fechaInicio;
	}
	/**
	 * @param fechaInicio the fechaInicio to set
	 */
	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	/**
	 * @return the fechaRegistro
	 */
	public Date getFechaRegistro() {
		return fechaRegistro;
	}
	/**
	 * @param fechaRegistro the fechaRegistro to set
	 */
	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}
	/**
	 * @return the fechaTermino
	 */
	public Date getFechaTermino() {
		return fechaTermino;
	}
	/**
	 * @param fechaTermino the fechaTermino to set
	 */
	public void setFechaTermino(Date fechaTermino) {
		this.fechaTermino = fechaTermino;
	}
	/**
	 * @return the fundamentacion
	 */
	public String getFundamentacion() {
		return fundamentacion;
	}
	/**
	 * @param fundamentacion the fundamentacion to set
	 */
	public void setFundamentacion(String fundamentacion) {
		this.fundamentacion = fundamentacion;
	}
	/**
	 * @return the identificador
	 */
	public String getIdentificador() {
		return identificador;
	}
	/**
	 * @param identificador the identificador to set
	 */
	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}
	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}
	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Integer getHorasCredito() {
		return horasCredito;
	}

	public void setHorasCredito(Integer horasCredito) {
		this.horasCredito = horasCredito;
	}

	/**
	 * @return the objetivos
	 */
	public String getObjetivos() {
		return objetivos;
	}
	/**
	 * @param objetivos the objetivos to set
	 */
	public void setObjetivos(String objetivos) {
		this.objetivos = objetivos;
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
	/**
	 * @return the ponderacionCalif
	 */
	public Integer getPonderacionCalif() {
		this.ponderacionCalif = 0;
		
		if(isPonderacion())
			this.ponderacionCalif = 1;
		
		return this.ponderacionCalif;
	}
	/**
	 * @param ponderacionCalif the ponderacionCalif to set
	 */
	public void setPonderacionCalif(Integer ponderacionCalif) {
		this.ponderacionCalif = ponderacionCalif;
	}
	/**
	 * @return the usuarioRegistro
	 */
	public Long getUsuarioModifico() {
		return usuarioModifico;
	}
	/**
	 * @param usuarioRegistro the usuarioRegistro to set
	 */
	public void setUsuarioModifico(Long usuarioModifico) {
		this.usuarioModifico = usuarioModifico;
	}
	/**
	 * @return the relPlanAptitudes
	 */
	public List<RelPlanAptitudDTO> getRelPlanAptitudes() {
		return relPlanAptitudes;
	}
	/**
	 * @param relPlanAptitudes the relPlanAptitudes to set
	 */
	public void setRelPlanAptitudes(List<RelPlanAptitudDTO> relPlanAptitudes) {
		this.relPlanAptitudes = relPlanAptitudes;
	}
	/**
	 * @return the relPlanConocimientos
	 */
	public List<RelPlanConocimientoDTO> getRelPlanConocimientos() {
		return relPlanConocimientos;
	}
	/**
	 * @param relPlanConocimientos the relPlanConocimientos to set
	 */
	public void setRelPlanConocimientos(List<RelPlanConocimientoDTO> relPlanConocimientos) {
		this.relPlanConocimientos = relPlanConocimientos;
	}
	/**
	 * @return the relPlanHabilidades
	 */
	public List<RelPlanHabilidadDTO> getRelPlanHabilidades() {
		return relPlanHabilidades;
	}
	/**
	 * @param relPlanHabilidades the relPlanHabilidades to set
	 */
	public void setRelPlanHabilidades(List<RelPlanHabilidadDTO> relPlanHabilidades) {
		this.relPlanHabilidades = relPlanHabilidades;
	}
	/**
	 * @return the catCompetenciasPlan
	 */
	public CatalogoComunDTO getCatCompetenciasPlan() {
		return catCompetenciasPlan;
	}
	/**
	 * @param catCompetenciasPlan the catCompetenciasPlan to set
	 */
	public void setCatCompetenciasPlan(CatalogoComunDTO catCompetenciasPlan) {
		this.catCompetenciasPlan = catCompetenciasPlan;
	}
	/**
	 * @return the catDocumentosExpidePlan
	 */
	public CatalogoComunDTO getCatDocumentosExpidePlan() {
		return catDocumentosExpidePlan;
	}
	/**
	 * @param catDocumentosExpidePlan the catDocumentosExpidePlan to set
	 */
	public void setCatDocumentosExpidePlan(CatalogoComunDTO catDocumentosExpidePlan) {
		this.catDocumentosExpidePlan = catDocumentosExpidePlan;
	}
	/**
	 * @return the catEstatusPlan
	 */
	public CatalogoComunDTO getCatEstatusPlan() {
		return catEstatusPlan;
	}
	/**
	 * @param catEstatusPlan the catEstatusPlan to set
	 */
	public void setCatEstatusPlan(CatalogoComunDTO catEstatusPlan) {
		this.catEstatusPlan = catEstatusPlan;
	}
	/**
	 * @return the catModalidadPlanPrograma
	 */
	public CatalogoComunDTO getCatModalidadPlanPrograma() {
		return catModalidadPlanPrograma;
	}
	/**
	 * @param catModalidadPlanPrograma the catModalidadPlanPrograma to set
	 */
	public void setCatModalidadPlanPrograma(CatalogoComunDTO catModalidadPlanPrograma) {
		this.catModalidadPlanPrograma = catModalidadPlanPrograma;
	}
	/**
	 * @return the catNivelEnsenanzaPrograma
	 */
	public CatalogoComunDTO getCatNivelEnsenanzaPrograma() {
		return catNivelEnsenanzaPrograma;
	}
	/**
	 * @param catNivelEnsenanzaPrograma the catNivelEnsenanzaPrograma to set
	 */
	public void setCatNivelEnsenanzaPrograma(CatalogoComunDTO catNivelEnsenanzaPrograma) {
		this.catNivelEnsenanzaPrograma = catNivelEnsenanzaPrograma;
	}
	/**
	 * @return the catTipoPlan
	 */
	public CatalogoComunDTO getCatTipoPlan() {
		return catTipoPlan;
	}
	/**
	 * @param catTipoPlan the catTipoPlan to set
	 */
	public void setCatTipoPlan(CatalogoComunDTO catTipoPlan) {
		this.catTipoPlan = catTipoPlan;
	}
	/**
	 * @return the tblOrganismoGubernamental
	 */
	public OrgGubernamentalDTO getTblOrganismoGubernamental() {
		return tblOrganismoGubernamental;
	}
	/**
	 * @param tblOrganismoGubernamental the tblOrganismoGubernamental to set
	 */
	public void setTblOrganismoGubernamental(OrgGubernamentalDTO tblOrganismoGubernamental) {
		this.tblOrganismoGubernamental = tblOrganismoGubernamental;
	}
	/**
	 * @return the catAlcancePlan
	 */
	public CatalogoComunDTO getCatAlcancePlan() {
		return catAlcancePlan;
	}
	/**
	 * @param catAlcancePlan the catAlcancePlan to set
	 */
	public void setCatAlcancePlan(CatalogoComunDTO catAlcancePlan) {
		this.catAlcancePlan = catAlcancePlan;
	}
	/**
	 * @return the ponderacion
	 */
	public boolean isPonderacion() {
		return ponderacion;
	}
	/**
	 * @param ponderacion the ponderacion to set
	 */
	public void setPonderacion(boolean ponderacion) {
		this.ponderacion = ponderacion;
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

    /**
     * @return the bitacoraDTO
     */
    public BitacoraDTO getBitacoraDTO() {
        return bitacoraDTO;
    }

    /**
     * @param bitacoraDTO the bitacoraDTO to set
     */
    public void setBitacoraDTO(BitacoraDTO bitacoraDTO) {
        this.bitacoraDTO = bitacoraDTO;
    }
	
    
    /**
	 * @return the catCreditosPlan
	 */
	public CatalogoComunDTO getCatCreditosPlan() {
		return catCreditosPlan;
	}
	/**
	 * @param catCreditosPlan the catCreditosPlan to set
	 */
	public void setCatCreditosPlan(CatalogoComunDTO catCreditosPlan) {
		this.catCreditosPlan = catCreditosPlan;
	}

	public CatalogoComunDTO getCatTipoCompetencia() {
		return catTipoCompetencia;
	}

	public void setCatTipoCompetencia(CatalogoComunDTO catTipoCompetencia) {
		this.catTipoCompetencia = catTipoCompetencia;
	}
	/**
	 * @return the catDivisionesPlan
	 */
	public CatalogoComunDTO getCatDivisionesPlan() {
		return catDivisionesPlan;
	}
	/**
	 * @param catDivisionesPlan the catDivisionesPlan to set
	 */
	public void setCatDivisionesPlan(CatalogoComunDTO catDivisionesPlan) {
		this.catDivisionesPlan = catDivisionesPlan;
	}
}
