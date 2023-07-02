package mx.gob.sedesol.gestorweb.beans.gestionescolar;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ValueChangeEvent;

import org.apache.log4j.Logger;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.CheckboxTreeNode;
import org.primefaces.model.TreeNode;

import mx.gob.sedesol.basegestor.commons.dto.NodoDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.CatalogoComunDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.PersonaDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.PersonaResponsabilidadesDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.FichaDescProgramaDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.MallaCurricularDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.RelEjeCompetenciaDTO;
import mx.gob.sedesol.basegestor.commons.utils.CatGestionEscolarEnum;
import mx.gob.sedesol.basegestor.commons.utils.EstatusProgramaEnum;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.CatClasificacionInformacion;
import mx.gob.sedesol.basegestor.service.impl.planesyprogramas.FECServiceFacade;
import mx.gob.sedesol.basegestor.springinit.GestionEscolarServiceAdapter;
import mx.gob.sedesol.gestorweb.beans.acceso.BaseBean;
import mx.gob.sedesol.gestorweb.commons.constantes.ConstantesGestorWeb;
import mx.gob.sedesol.gestorweb.commons.dto.NodoeHijosDTO;

/**
 * Created by jhcortes on 3/02/17.
 */
@SessionScoped
@ManagedBean
public class NuevoEventoCapacitacionBean extends BaseBean {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger(NuevoEventoCapacitacionBean.class);

	@ManagedProperty(value = "#{fecServiceFacade}")
	private FECServiceFacade fecServiceFacade;

	@ManagedProperty(value = "#{gestionEscolarServiceAdapter}")
	private GestionEscolarServiceAdapter gestionEscolarServiceAdapter;

	private CatGestionEscolarEnum[] catalogosGestionEscolar;
	private CatGestionEscolarEnum catalogoSeleccionado;
	private List<CatalogoComunDTO> catClasifInfo;

	private Date fechaInicio;
	private Date fechaFin;
	private String clavePrograma;
	private String nombrePrograma;
	private FichaDescProgramaDTO programaSelect;

	private FichaDescProgramaDTO programa;
	private List<CatalogoComunDTO> catTpoComp;
	private List<CatalogoComunDTO> catEjeCapacit;
	private NodoeHijosDTO estPlanSedesol;
	private List<CatalogoComunDTO> ejesCapacitacion;
	private TreeNode arbolCompEspecificas;
	private Integer tipoCompetencia;
	private Integer ejeCapacitacion;
	private List<FichaDescProgramaDTO> programasBusqueda;

	private List<PersonaResponsabilidadesDTO> coordinadoresAcademicos;
	private List<PersonaDTO> tblCoordinadorAcademico;
	private PersonaDTO coordinadorAcademico;
	
	private List<PersonaResponsabilidadesDTO> facilitadores;
	private List<PersonaDTO> tblFacilitador;
	private PersonaDTO facilitador;

	private List<PersonaResponsabilidadesDTO> responsablesProduccion;
	private List<PersonaDTO> tblResponsableProduccion;
	private PersonaDTO responsableProduccion;
	
	private int tabIndex;
	
	public NuevoEventoCapacitacionBean() {
		catalogosGestionEscolar = CatGestionEscolarEnum.values();
		programa = new FichaDescProgramaDTO();
		programasBusqueda = new ArrayList<>();
		programaSelect = new FichaDescProgramaDTO();
		coordinadorAcademico = new PersonaDTO();
		tblCoordinadorAcademico = new ArrayList<>();
		tblFacilitador = new ArrayList<>();
		facilitador = new PersonaDTO();
		tblResponsableProduccion = new ArrayList<>();
		responsableProduccion = new PersonaDTO();
	}

	@SuppressWarnings("unchecked")
	@PostConstruct
	public void iniciaRecursos() {
		coordinadoresAcademicos = fecServiceFacade.getPersonaResponsabilidadesService()
				.obtienePersonasPorResponsabilidad(2);
		facilitadores = fecServiceFacade.getPersonaResponsabilidadesService()
				.obtienePersonasPorResponsabilidad(1);
		responsablesProduccion = fecServiceFacade.getPersonaResponsabilidadesService()
				.obtienePersonasPorResponsabilidad(3);
		this.generaEstructuraCatTpoCompetenciaPlan();
		this.generaCatEjesCapacitBusqueda();
		recuperarListaPogramas(Boolean.TRUE);
		obtenerClasifInformacion();
	}

	public void agregarCoordinadorAcademico() {
		tblCoordinadorAcademico.add(coordinadorAcademico);
		coordinadorAcademico = new PersonaDTO();
		tabIndex = 3;
	}

	public void eliminarCoordinadorAcademico() {
		tblCoordinadorAcademico.remove(coordinadorAcademico);
		coordinadorAcademico = new PersonaDTO();
		tabIndex = 3;
	}

	public void agregarFacilitador() {
		tblFacilitador.add(facilitador);
		facilitador = new PersonaDTO();
		tabIndex = 3;
	}

	public void eliminarFacilitador() {
		tblFacilitador.remove(facilitador);
		facilitador = new PersonaDTO();
		tabIndex = 3;
	}

	public void agregarResponsableProduccion() {
		tblResponsableProduccion.add(responsableProduccion);
		responsableProduccion = new PersonaDTO();
		tabIndex = 3;
	}

	public void eliminarResponsableProduccion() {
		tblResponsableProduccion.remove(responsableProduccion);
		responsableProduccion = new PersonaDTO();
		tabIndex = 3;
	}
	
	/**
	 *
	 */
	private List<FichaDescProgramaDTO> recuperaTodosLosProgramas() {
		return fecServiceFacade.getFichaDescProgramaService().findAll();
	}

	/**
	 *
	 * @param criterios
	 * @return
	 */
	private List<FichaDescProgramaDTO> recuperaProgramasPorCriterio(FichaDescProgramaDTO criterios) {
		return fecServiceFacade.getFichaDescProgramaService().buscaProgramasPorCriterios(criterios);
	}

	/**
	 *
	 */
	public void buscarProgramas() {
		recuperarListaPogramas(Boolean.FALSE);
	}

	/**
	 *
	 * @param isCriterios
	 */
	private void recuperarListaPogramas(Boolean isCriterios) {
		List<FichaDescProgramaDTO> lstProgramasAux = null;
		programasBusqueda = new ArrayList<>();

		if (isCriterios) {

			lstProgramasAux = recuperaTodosLosProgramas();

		} else {

			FichaDescProgramaDTO criterios = new FichaDescProgramaDTO();
			criterios.setTipoCompetencia(programa.getTipoCompetencia());
			criterios.setEjeCapacitacion(programa.getEjeCapacitacion());
			criterios.setNombreTentativo(nombrePrograma);
			criterios.setCvePrograma(programa.getCvePrograma());

			logger.info("tipo competencia>>>" + programa.getTipoCompetencia());
			logger.info("eje capacitacion>>>" + programa.getEjeCapacitacion());
			logger.info("nombre>>>>>>>>>>>>>" + nombrePrograma);
			logger.info("clave>>>>>>>>>>>>>>" + programa.getCvePrograma());

			lstProgramasAux = recuperaProgramasPorCriterio(criterios);

		}

		if (!ObjectUtils.isNullOrEmpty(lstProgramasAux)) {

			for (FichaDescProgramaDTO programa : lstProgramasAux) {
				if (programa.getCatStatusPrograma().getId() == EstatusProgramaEnum.FINAL.getId())
					programasBusqueda.add(programa);
			}
		}

	}

	private void generaCatEjesCapacitBusqueda() {
		ejesCapacitacion = new ArrayList<>();

		for (NodoeHijosDTO nh : estPlanSedesol.getNodosHijos()) {
			for (NodoeHijosDTO nint : nh.getNodosHijos()) {
				CatalogoComunDTO cc = new CatalogoComunDTO();
				cc.setId(nint.getIdNodo());
				cc.setNombre(nint.getNombre());
				ejesCapacitacion.add(cc);
			}
		}

		// Genera el arbol de Competencias especificas
		this.generaArbolCompEspecificasXEje();
	}

	private void generaArbolCompEspecificasXEje() {

		arbolCompEspecificas = new CheckboxTreeNode(new NodoDTO(1, "Competencias Especificas"), null);
		for (CatalogoComunDTO ejeCap : ejesCapacitacion) {
			TreeNode ejeCapNodo = new CheckboxTreeNode(new NodoDTO(ejeCap.getId(), ejeCap.getNombre()),
					arbolCompEspecificas);
			this.generaArbolComEspecifXEje(ejeCapNodo, ejeCap.getId());
		}
	}

	/**
	 *
	 * @param nodoPadre
	 * @param idEjeCap
	 */
	private void generaArbolComEspecifXEje(TreeNode nodoPadre, Integer idEjeCap) {
		List<RelEjeCompetenciaDTO> relCompEsp = getFecServiceFacade().getEjeCompetenciaService()
				.obtenerCompetenciasEspecificasPorEje(idEjeCap);
		for (RelEjeCompetenciaDTO ejeComp : relCompEsp) {
			TreeNode compEsp = new CheckboxTreeNode(new NodoDTO(ejeComp.getCatCompetenciaEspecifica().getId(),
					ejeComp.getCatCompetenciaEspecifica().getNombre()), nodoPadre);
			compEsp.setExpanded(Boolean.FALSE);
		}
	}

	/**
	 *
	 */
	private void generaEstructuraCatTpoCompetenciaPlan() {

		List<NodoeHijosDTO> planes = new ArrayList<>();
		List<MallaCurricularDTO> mallas = new ArrayList<>();

		// getFecServiceFacade().getMallaCurricularService().obtieneMallasCurricularesDisponibles();
		// RN: Solo se presentara el plan de sedesol por el momento
		MallaCurricularDTO mallaSedesol = getFecServiceFacade().getMallaCurricularService()
				.obtenerMallaCurricularPorId(1);
		mallas.add(mallaSedesol);

		for (MallaCurricularDTO m : mallas) {
			NodoeHijosDTO nodog = new NodoeHijosDTO();
			nodog.setNombre(m.getNombre());
			nodog.setIdNodo(m.getId());
			nodog.setIdPadre(m.getMallaCurricularPadre() != null ? m.getMallaCurricularPadre().getId() : null);
			nodog.setIdObjCurr(m.getObjetoCurricular().getId());
			nodog.setNivel(0);

			if (!m.getLstHijosMallaCurr().isEmpty()) {
				this.generaCatxNivel(m.getLstHijosMallaCurr(), nodog, nodog.getNivel());
			}

			planes.add(nodog);
		}

		catTpoComp = new ArrayList<>();

		// Genera el Catalogo Tipo de Competencia
		estPlanSedesol = planes.get(ConstantesGestorWeb.INDICE_INICIAL);
		for (NodoeHijosDTO nh : estPlanSedesol.getNodosHijos()) {
			CatalogoComunDTO cc = new CatalogoComunDTO();
			cc.setId(nh.getIdNodo());
			cc.setNombre(nh.getNombre());
			catTpoComp.add(cc);
		}
	}

	@SuppressWarnings("unchecked")
	public void obtenerClasifInformacion() {

		catClasifInfo = getGestionEscolarServiceAdapter()
				.getCatalogoServiceByGestionEscolarEnum(CatGestionEscolarEnum.CAT_CLASIFICACION_INFORMACION)
				.findAll(CatClasificacionInformacion.class);

	}

	/**
	 * Genera el catalogo de eje de capacitacion
	 *
	 * @param e
	 */
	public void onChangeTpoCompetencia(ValueChangeEvent e) {

		if (ObjectUtils.isNotNull(e.getNewValue())) {

			Integer idTpoCompSel = (Integer) e.getNewValue();
			programa.setTipoCompetencia(idTpoCompSel);
			catEjeCapacit = new ArrayList<>();

			for (NodoeHijosDTO nh : estPlanSedesol.getNodosHijos()) {
				if (nh.getIdNodo().equals(idTpoCompSel)) {
					for (NodoeHijosDTO nint : nh.getNodosHijos()) {

						CatalogoComunDTO cc = new CatalogoComunDTO();
						cc.setId(nint.getIdNodo());
						cc.setNombre(nint.getNombre());
						catEjeCapacit.add(cc);
					}
				}
			}
		}
	}

	/**
	 *
	 * @param hijos
	 * @param nodoGral
	 */
//	private void generaCatxNivel(List<MallaCurricularDTO> hijos, NodoeHijosDTO nodoGral, int nivel) {
//
//		for (MallaCurricularDTO mint : hijos) {
//
//			NodoeHijosDTO hijo = new NodoeHijosDTO();
//			hijo.setIdNodo(mint.getId());
//			hijo.setIdPadre(nodoGral.getIdNodo());
//			hijo.setIdObjCurr(mint.getObjetoCurricular().getId());
//			hijo.setNivel(nivel + 1);
//			hijo.setNombre(mint.getNombre());
//			nodoGral.getNodosHijos().add(hijo);
//
//			if (!ObjectUtils.isNullOrEmpty(mint.getLstHijosMallaCurr()))
//				this.generaCatxNivel(mint.getLstHijosMallaCurr(), hijo, hijo.getNivel());
//			;
//		}
//	}

	/**
	 *
	 */
	public void onRowSelect(SelectEvent event) {

		logger.info("**************" + programaSelect.getNombreTentativo());
		getSession().setAttribute("programaSelect", programaSelect);
	}

	/* seccion de getter y setter */

	public FECServiceFacade getFecServiceFacade() {
		return fecServiceFacade;
	}

	public void setFecServiceFacade(FECServiceFacade fecServiceFacade) {
		this.fecServiceFacade = fecServiceFacade;
	}

	public GestionEscolarServiceAdapter getGestionEscolarServiceAdapter() {
		if (ObjectUtils.isNull(gestionEscolarServiceAdapter))
			gestionEscolarServiceAdapter = new GestionEscolarServiceAdapter();
		return gestionEscolarServiceAdapter;
	}

	public void setGestionEscolarServiceAdapter(GestionEscolarServiceAdapter gestionEscolarServiceAdapter) {
		this.gestionEscolarServiceAdapter = gestionEscolarServiceAdapter;
	}

	public CatGestionEscolarEnum[] getCatalogosGestionEscolar() {
		return catalogosGestionEscolar;
	}

	public void setCatalogosGestionEscolar(CatGestionEscolarEnum[] catalogosGestionEscolar) {
		this.catalogosGestionEscolar = catalogosGestionEscolar;
	}

	public CatGestionEscolarEnum getCatalogoSeleccionado() {
		return catalogoSeleccionado;
	}

	public void setCatalogoSeleccionado(CatGestionEscolarEnum catalogoSeleccionado) {
		this.catalogoSeleccionado = catalogoSeleccionado;
	}

	public List<CatalogoComunDTO> getCatClasifInfo() {
		return catClasifInfo;
	}

	public void setCatClasifInfo(List<CatalogoComunDTO> catClasifInfo) {
		this.catClasifInfo = catClasifInfo;
	}

	public List<CatalogoComunDTO> getEjesCapacitacion() {
		return ejesCapacitacion;
	}

	public void setEjesCapacitacion(List<CatalogoComunDTO> ejesCapacitacion) {
		this.ejesCapacitacion = ejesCapacitacion;
	}

	public TreeNode getArbolCompEspecificas() {
		return arbolCompEspecificas;
	}

	public void setArbolCompEspecificas(TreeNode arbolCompEspecificas) {
		this.arbolCompEspecificas = arbolCompEspecificas;
	}

	public FichaDescProgramaDTO getPrograma() {
		return programa;
	}

	public void setPrograma(FichaDescProgramaDTO programa) {
		this.programa = programa;
	}

	public List<CatalogoComunDTO> getCatTpoComp() {
		return catTpoComp;
	}

	public void setCatTpoComp(List<CatalogoComunDTO> catTpoComp) {
		this.catTpoComp = catTpoComp;
	}

	public List<CatalogoComunDTO> getCatEjeCapacit() {
		return catEjeCapacit;
	}

	public void setCatEjeCapacit(List<CatalogoComunDTO> catEjeCapacit) {
		this.catEjeCapacit = catEjeCapacit;
	}

	public NodoeHijosDTO getEstPlanSedesol() {
		return estPlanSedesol;
	}

	public void setEstPlanSedesol(NodoeHijosDTO estPlanSedesol) {
		this.estPlanSedesol = estPlanSedesol;
	}

	public Integer getTipoCompetencia() {
		return tipoCompetencia;
	}

	public void setTipoCompetencia(Integer tipoCompetencia) {
		this.tipoCompetencia = tipoCompetencia;
	}

	public Integer getEjeCapacitacion() {
		return ejeCapacitacion;
	}

	public void setEjeCapacitacion(Integer ejeCapacitacion) {
		this.ejeCapacitacion = ejeCapacitacion;
	}

	public List<FichaDescProgramaDTO> getProgramasBusqueda() {
		return programasBusqueda;
	}

	public void setProgramasBusqueda(List<FichaDescProgramaDTO> programasBusqueda) {
		this.programasBusqueda = programasBusqueda;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public String getClavePrograma() {
		return clavePrograma;
	}

	public void setClavePrograma(String clavePrograma) {
		this.clavePrograma = clavePrograma;
	}

	public String getNombrePrograma() {
		return nombrePrograma;
	}

	public void setNombrePrograma(String nombrePrograma) {
		this.nombrePrograma = nombrePrograma;
	}

	public FichaDescProgramaDTO getProgramaSelect() {
		return programaSelect;
	}

	public void setProgramaSelect(FichaDescProgramaDTO programaSelect) {
		this.programaSelect = programaSelect;
	}

	public List<PersonaResponsabilidadesDTO> getCoordinadoresAcademicos() {
		return coordinadoresAcademicos;
	}

	public void setCoordinadoresAcademicos(List<PersonaResponsabilidadesDTO> coordinadoresAcademicos) {
		this.coordinadoresAcademicos = coordinadoresAcademicos;
	}

	public PersonaDTO getCoordinadorAcademico() {
		return coordinadorAcademico;
	}

	public void setCoordinadorAcademico(PersonaDTO coordinadorAcademico) {
		this.coordinadorAcademico = coordinadorAcademico;
	}

	public List<PersonaDTO> getTblCoordinadorAcademico() {
		return tblCoordinadorAcademico;
	}

	public void setTblCoordinadorAcademico(List<PersonaDTO> tblCoordinadorAcademico) {
		this.tblCoordinadorAcademico = tblCoordinadorAcademico;
	}

	public int getTabIndex() {
		return tabIndex;
	}

	public void setTabIndex(int tabIndex) {
		this.tabIndex = tabIndex;
	}

	public List<PersonaResponsabilidadesDTO> getFacilitadores() {
		return facilitadores;
	}

	public void setFacilitadores(List<PersonaResponsabilidadesDTO> facilitadores) {
		this.facilitadores = facilitadores;
	}

	public List<PersonaDTO> getTblFacilitador() {
		return tblFacilitador;
	}

	public void setTblFacilitador(List<PersonaDTO> tblFacilitadores) {
		this.tblFacilitador = tblFacilitadores;
	}

	public PersonaDTO getFacilitador() {
		return facilitador;
	}

	public void setFacilitador(PersonaDTO facilitador) {
		this.facilitador = facilitador;
	}

	public List<PersonaResponsabilidadesDTO> getResponsablesProduccion() {
		return responsablesProduccion;
	}

	public void setResponsablesProduccion(List<PersonaResponsabilidadesDTO> responsablesProduccion) {
		this.responsablesProduccion = responsablesProduccion;
	}

	public List<PersonaDTO> getTblResponsableProduccion() {
		return tblResponsableProduccion;
	}

	public void setTblResponsableProduccion(List<PersonaDTO> tblResponsableProduccion) {
		this.tblResponsableProduccion = tblResponsableProduccion;
	}

	public PersonaDTO getResponsableProduccion() {
		return responsableProduccion;
	}

	public void setResponsableProduccion(PersonaDTO responsableProduccion) {
		this.responsableProduccion = responsableProduccion;
	}

}
