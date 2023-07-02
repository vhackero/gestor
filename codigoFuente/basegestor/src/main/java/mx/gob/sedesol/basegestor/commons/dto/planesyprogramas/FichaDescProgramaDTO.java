package mx.gob.sedesol.basegestor.commons.dto.planesyprogramas;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;
import mx.gob.sedesol.basegestor.commons.dto.admin.BitacoraDTO;

import org.hibernate.validator.constraints.NotEmpty;

import mx.gob.sedesol.basegestor.commons.dto.admin.CatalogoComunDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.OrgGubernamentalDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.PersonaDTO;
import mx.gob.sedesol.basegestor.commons.utils.MensajesSistemaEnum;



public class FichaDescProgramaDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer idPrograma;
	@NotEmpty(message=MensajesSistemaEnum.Constantes.GESTOR_WEB_GENERAL_DATO_REQUERIDO)
	private String conocimietosPrevios;
	private String cvePrograma;
	@NotEmpty(message=MensajesSistemaEnum.Constantes.GESTOR_WEB_GENERAL_DATO_REQUERIDO)
	private String descripcion;
	
	@NotNull(message=MensajesSistemaEnum.Constantes.GESTOR_WEB_GENERAL_DATO_REQUERIDO)
	private Date fechaArranque;
	
	@NotNull(message=MensajesSistemaEnum.Constantes.GESTOR_WEB_GENERAL_DATO_REQUERIDO)
	private Date fechaProduccion;
	
	@NotNull(message=MensajesSistemaEnum.Constantes.GESTOR_WEB_GENERAL_DATO_REQUERIDO)
	private Date fechaSolicitud;
	
	private Integer idAlcancePrograma;
	private CatalogoComunDTO idInstitucionCertifica;
	private Integer idNivelDominioPrograma;
	private FichaDescProgramaDTO programaAntecedente;
	private String identificadorFinal;
	private String instrumentoAprendizaje;
	@NotEmpty(message=MensajesSistemaEnum.Constantes.GESTOR_WEB_GENERAL_DATO_REQUERIDO)
	private String justificacionAcademica;
	private String metodologia;
	@NotEmpty(message=MensajesSistemaEnum.Constantes.GESTOR_WEB_GENERAL_DATO_REQUERIDO)
	private String nombreTentativo;
	@NotEmpty(message=MensajesSistemaEnum.Constantes.GESTOR_WEB_GENERAL_DATO_REQUERIDO)
	private String objetivosGenerales;
	@NotEmpty(message=MensajesSistemaEnum.Constantes.GESTOR_WEB_GENERAL_DATO_REQUERIDO)
	private String presentacion;
	@NotEmpty(message=MensajesSistemaEnum.Constantes.GESTOR_WEB_GENERAL_DATO_REQUERIDO)
	private String propositos;
	private String requerimientoEquipo;
	private String requisitosIngreso;
	private AmbienteAprendizajeEcDTO catAmbientesAprendizajeEc;
	private CatalogoComunDTO catStatusPrograma;
	
	@NotNull(message=MensajesSistemaEnum.Constantes.GESTOR_WEB_GENERAL_DATO_REQUERIDO)
	private CatalogoComunDTO catNivelEnsenanzaPrograma;
	
	@NotNull(message=MensajesSistemaEnum.Constantes.GESTOR_WEB_GENERAL_DATO_REQUERIDO)
	private CatalogoComunDTO catPuestosSedesolEc;
	
	@NotNull(message=MensajesSistemaEnum.Constantes.GESTOR_WEB_GENERAL_DATO_REQUERIDO)
	private CatalogoComunDTO catTipoEventoEc;
	
	@NotNull(message=MensajesSistemaEnum.Constantes.GESTOR_WEB_GENERAL_DATO_REQUERIDO)
	private CatalogoComunDTO catModalidad;
	
	private CatalogoComunDTO catCompetenciasEspe;
	
//	private List<RelProgActividadesAprendizajeDTO> relProgramaActividadesAprendizajes;
//	private List<RelProgAreaConocimientoDTO> relProgramaAreasConocimientos;
//	private List<RelProgCreadorProgramaDTO> relProgramaCreadorProgramas;
//	private List<RelProgEntidadAcademicaDTO> relProgramaEntidadesAcademicas;
//	private List<RelProgPilotajeDTO> relProgramaPilotajes;
//	private List<RelProgResolucionImagenDTO> relProgramaResolucionImagen;
//	private List<RelProgTecnicaDidacticaDTO> relProgramaTecnicasDidacticas;
	private List<RelProgAutoreDTO> relProgramaAutores;
	private List<RelProgDuracionDTO> relProgramaDuracion;
	private List<RelProgResponsableDTO> relProgramaResponsables;
	private List<RelProgCompEspecificaDTO> relProgramaComEspecificas;
	private EstructuraTematicaDTO tblEstructuraTematica;
	
	private Date fechaActualizacion;
	private Date fechaRegistro;
	private Long usuarioModifico;
	@NotNull(message=MensajesSistemaEnum.Constantes.GESTOR_WEB_GENERAL_DATO_REQUERIDO)
	private Integer tipoCompetencia;
	@NotNull(message=MensajesSistemaEnum.Constantes.GESTOR_WEB_GENERAL_DATO_REQUERIDO)
	private Integer ejeCapacitacion;
	
	@NotNull(message=MensajesSistemaEnum.Constantes.GESTOR_WEB_GENERAL_DATO_REQUERIDO)
	private Date fechaVigInicial;
	@NotNull(message=MensajesSistemaEnum.Constantes.GESTOR_WEB_GENERAL_DATO_REQUERIDO)
	private Date fechaVigFinal;
	
	@NotNull(message=MensajesSistemaEnum.Constantes.GESTOR_WEB_GENERAL_DATO_REQUERIDO)
	private OrgGubernamentalDTO organismoGubernamental;
	
	@NotNull(message=MensajesSistemaEnum.Constantes.GESTOR_WEB_GENERAL_DATO_REQUERIDO)
	private CatalogoComunDTO catNivelConocimiento;
	
	private Boolean encuestakp;
	@NotEmpty(message=MensajesSistemaEnum.Constantes.GESTOR_WEB_GENERAL_DATO_REQUERIDO)
	private String perfilEgreso;
	@NotEmpty(message=MensajesSistemaEnum.Constantes.GESTOR_WEB_GENERAL_DATO_REQUERIDO)
	private String perfilIngreso;
	
	private String calificacionMinAprobatoria;
	
//	@NotNull(message=MensajesSistemaEnum.Constantes.GESTOR_WEB_GENERAL_DATO_REQUERIDO)
//	private EstPersonalExternoDTO catDirigidoAExterno;
	
	private List<RelProgPersonalExternoDTO> relProgEstPersonalExterno;
	
	@NotNull(message=MensajesSistemaEnum.Constantes.GESTOR_WEB_GENERAL_DATO_REQUERIDO)
	private OrgGubernamentalDTO areaResponsable;
	
	private PersonaDTO coordinadorAcademico;
	private Integer idCategoriaMdl;
        private BitacoraDTO bitacoraDTO;
	
	public FichaDescProgramaDTO(){
		
	}
	

	public Integer getIdPrograma() {
		return idPrograma;
	}
	public void setIdPrograma(Integer idPrograma) {
		this.idPrograma = idPrograma;
	}
	public String getConocimietosPrevios() {
		return conocimietosPrevios;
	}
	public void setConocimietosPrevios(String conocimietosPrevios) {
		this.conocimietosPrevios = conocimietosPrevios;
	}
	public String getCvePrograma() {
		return cvePrograma;
	}
	public void setCvePrograma(String cvePrograma) {
		this.cvePrograma = cvePrograma;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public Date getFechaArranque() {
		return fechaArranque;
	}
	public void setFechaArranque(Date fechaArranque) {
		this.fechaArranque = fechaArranque;
	}
	public Date getFechaProduccion() {
		return fechaProduccion;
	}
	public void setFechaProduccion(Date fechaProduccion) {
		this.fechaProduccion = fechaProduccion;
	}
	public Date getFechaSolicitud() {
		return fechaSolicitud;
	}
	public void setFechaSolicitud(Date fechaSolicitud) {
		this.fechaSolicitud = fechaSolicitud;
	}
	public Integer getIdAlcancePrograma() {
		return idAlcancePrograma;
	}
	public void setIdAlcancePrograma(Integer idAlcancePrograma) {
		this.idAlcancePrograma = idAlcancePrograma;
	}
	
	
	public Integer getIdNivelDominioPrograma() {
		return idNivelDominioPrograma;
	}
	public void setIdNivelDominioPrograma(Integer idNivelDominioPrograma) {
		this.idNivelDominioPrograma = idNivelDominioPrograma;
	}
	
	
	public String getIdentificadorFinal() {
		return identificadorFinal;
	}
	public void setIdentificadorFinal(String identificadorFinal) {
		this.identificadorFinal = identificadorFinal;
	}
	public String getInstrumentoAprendizaje() {
		return instrumentoAprendizaje;
	}
	public void setInstrumentoAprendizaje(String instrumentoAprendizaje) {
		this.instrumentoAprendizaje = instrumentoAprendizaje;
	}
	public String getJustificacionAcademica() {
		return justificacionAcademica;
	}
	public void setJustificacionAcademica(String justificacionAcademica) {
		this.justificacionAcademica = justificacionAcademica;
	}
	public String getMetodologia() {
		return metodologia;
	}
	public void setMetodologia(String metodologia) {
		this.metodologia = metodologia;
	}
	public String getNombreTentativo() {
		return nombreTentativo;
	}
	public void setNombreTentativo(String nombreTentativo) {
		this.nombreTentativo = nombreTentativo;
	}
	public String getObjetivosGenerales() {
		return objetivosGenerales;
	}
	public void setObjetivosGenerales(String objetivosGenerales) {
		this.objetivosGenerales = objetivosGenerales;
	}
	public String getPresentacion() {
		return presentacion;
	}
	public void setPresentacion(String presentacion) {
		this.presentacion = presentacion;
	}
	public String getPropositos() {
		return propositos;
	}
	public void setPropositos(String propositos) {
		this.propositos = propositos;
	}
	public String getRequerimientoEquipo() {
		return requerimientoEquipo;
	}
	public void setRequerimientoEquipo(String requerimientoEquipo) {
		this.requerimientoEquipo = requerimientoEquipo;
	}
	public String getRequisitosIngreso() {
		return requisitosIngreso;
	}
	public void setRequisitosIngreso(String requisitosIngreso) {
		this.requisitosIngreso = requisitosIngreso;
	}
	/**
	 * @return the coordinadorAcademico
	 */
	public PersonaDTO getCoordinadorAcademico() {
		return coordinadorAcademico;
	}


	/**
	 * @param coordinadorAcademico the coordinadorAcademico to set
	 */
	public void setCoordinadorAcademico(PersonaDTO coordinadorAcademico) {
		this.coordinadorAcademico = coordinadorAcademico;
	}


	public AmbienteAprendizajeEcDTO getCatAmbientesAprendizajeEc() {
		return catAmbientesAprendizajeEc;
	}
	public void setCatAmbientesAprendizajeEc(AmbienteAprendizajeEcDTO catAmbientesAprendizajeEc) {
		this.catAmbientesAprendizajeEc = catAmbientesAprendizajeEc;
	}
	public CatalogoComunDTO getCatStatusPrograma() {
		return catStatusPrograma;
	}
	public void setCatStatusPrograma(CatalogoComunDTO catStatusPrograma) {
		this.catStatusPrograma = catStatusPrograma;
	}
	public CatalogoComunDTO getCatNivelEnsenanzaPrograma() {
		return catNivelEnsenanzaPrograma;
	}
	public void setCatNivelEnsenanzaPrograma(CatalogoComunDTO catNivelEnsenanzaPrograma) {
		this.catNivelEnsenanzaPrograma = catNivelEnsenanzaPrograma;
	}
	public CatalogoComunDTO getCatPuestosSedesolEc() {
		return catPuestosSedesolEc;
	}
	public void setCatPuestosSedesolEc(CatalogoComunDTO catPuestosSedesolEc) {
		this.catPuestosSedesolEc = catPuestosSedesolEc;
	}
	public CatalogoComunDTO getCatTipoEventoEc() {
		return catTipoEventoEc;
	}
	public void setCatTipoEventoEc(CatalogoComunDTO catTipoEventoEc) {
		this.catTipoEventoEc = catTipoEventoEc;
	}
	
	public List<RelProgDuracionDTO> getRelProgramaDuracion() {
		return relProgramaDuracion;
	}
	public void setRelProgramaDuracion(List<RelProgDuracionDTO> relProgramaDuracion) {
		this.relProgramaDuracion = relProgramaDuracion;
	}
	public List<RelProgResponsableDTO> getRelProgramaResponsables() {
		return relProgramaResponsables;
	}
	public void setRelProgramaResponsables(List<RelProgResponsableDTO> relProgramaResponsables) {
		this.relProgramaResponsables = relProgramaResponsables;
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
	public EstructuraTematicaDTO getTblEstructuraTematica() {
		return tblEstructuraTematica;
	}
	public void setTblEstructuraTematica(EstructuraTematicaDTO tblEstructuraTematica) {
		this.tblEstructuraTematica = tblEstructuraTematica;
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
	 * @return the areaResponsable
	 */
	public OrgGubernamentalDTO getAreaResponsable() {
		return areaResponsable;
	}
	/**
	 * @param areaResponsable the areaResponsable to set
	 */
	public void setAreaResponsable(OrgGubernamentalDTO areaResponsable) {
		this.areaResponsable = areaResponsable;
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
	 * @return the catModalidad
	 */
	public CatalogoComunDTO getCatModalidad() {
		return catModalidad;
	}
	/**
	 * @param catModalidad the catModalidad to set
	 */
	public void setCatModalidad(CatalogoComunDTO catModalidad) {
		this.catModalidad = catModalidad;
	}
	/**
	 * @return the catCompetenciasEspe
	 */
	public CatalogoComunDTO getCatCompetenciasEspe() {
		return catCompetenciasEspe;
	}
	/**
	 * @param catCompetenciasEspe the catCompetenciasEspe to set
	 */
	public void setCatCompetenciasEspe(CatalogoComunDTO catCompetenciasEspe) {
		this.catCompetenciasEspe = catCompetenciasEspe;
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
	public CatalogoComunDTO getCatNivelConocimiento() {
		return catNivelConocimiento;
	}
	/**
	 * @param catNivelConocimiento the catNivelConocimiento to set
	 */
	public void setCatNivelConocimiento(CatalogoComunDTO catNivelConocimiento) {
		this.catNivelConocimiento = catNivelConocimiento;
	}
	/**
	 * @return the encuestakp
	 */
	public Boolean getEncuestakp() {
		return encuestakp;
	}
	/**
	 * @param encuestakp the encuestakp to set
	 */
	public void setEncuestakp(Boolean encuestakp) {
		this.encuestakp = encuestakp;
	}
	/**
	 * @return the organismoGubernamental
	 */
	public OrgGubernamentalDTO getOrganismoGubernamental() {
//		if(ObjectUtils.isNull(this.organismoGubernamental))
//			this.organismoGubernamental = new OrgGubernamentalDTO();
		return this.organismoGubernamental;
	}
	/**
	 * @param organismoGubernamental the organismoGubernamental to set
	 */
	public void setOrganismoGubernamental(OrgGubernamentalDTO organismoGubernamental) {
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
	public CatalogoComunDTO getIdInstitucionCertifica() {
		return idInstitucionCertifica;
	}
	public void setIdInstitucionCertifica(CatalogoComunDTO idInstitucionCertifica) {
		this.idInstitucionCertifica = idInstitucionCertifica;
	}
	public String getCalificacionMinAprobatoria() {
		return this.calificacionMinAprobatoria;
	}

	public void setCalificacionMinAprobatoria(String calificacionMinAprobatoria) {
		this.calificacionMinAprobatoria = calificacionMinAprobatoria;
	}
	/**
	 * @return the programaAntecedente
	 */
	public FichaDescProgramaDTO getProgramaAntecedente() {
		return programaAntecedente;
	}
	/**
	 * @param programaAntecedente the programaAntecedente to set
	 */
	public void setProgramaAntecedente(FichaDescProgramaDTO programaAntecedente) {
		this.programaAntecedente = programaAntecedente;
	}
	public List<RelProgAutoreDTO> getRelProgramaAutores() {
		return relProgramaAutores;
	}
	public void setRelProgramaAutores(List<RelProgAutoreDTO> relProgramaAutores) {
		this.relProgramaAutores = relProgramaAutores;
	}
	/**
	 * @return the relProgramaComEspecificas
	 */
	public List<RelProgCompEspecificaDTO> getRelProgramaComEspecificas() {
		return relProgramaComEspecificas;
	}
	/**
	 * @param relProgramaComEspecificas the relProgramaComEspecificas to set
	 */
	public void setRelProgramaComEspecificas(List<RelProgCompEspecificaDTO> relProgramaComEspecificas) {
		this.relProgramaComEspecificas = relProgramaComEspecificas;
	}


	/**
	 * @return the relProgEstPersonalExterno
	 */
	public List<RelProgPersonalExternoDTO> getRelProgEstPersonalExterno() {
		return relProgEstPersonalExterno;
	}


	/**
	 * @param relProgEstPersonalExterno the relProgEstPersonalExterno to set
	 */
	public void setRelProgEstPersonalExterno(List<RelProgPersonalExternoDTO> relProgEstPersonalExterno) {
		this.relProgEstPersonalExterno = relProgEstPersonalExterno;
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
}
