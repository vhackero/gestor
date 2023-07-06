package mx.gob.sedesol.gestorweb.beans.planesprogramas;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ValueChangeEvent;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.log4j.Logger;
import org.primefaces.component.dialog.Dialog;
import org.primefaces.component.tabview.TabView;
import org.primefaces.context.RequestContext;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.NodeUnselectEvent;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.model.CheckboxTreeNode;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.TreeNode;

import mx.gob.sedesol.basegestor.commons.constantes.ConstantesBitacora;
import mx.gob.sedesol.basegestor.commons.constantes.ConstantesGestor;
import mx.gob.sedesol.basegestor.commons.dto.NodoDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.CatalogoComunDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.OrgGubernamentalDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.ParametroWSMoodleDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.PersonaDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.VariableMensajeOperacionDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.CmpDinamicoUniDidacticaDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.CompetenciaEspecificaDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.ControlEstTematicaDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.DetEstUniDidacticaDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.DetEtematicaTemaDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.EstPersonalExternoDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.EstructuraTematicaDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.FichaDescProgramaDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.MallaCurricularDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.RelEjeCompetenciaDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.RelEstUnidadDidacticaDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.RelProgAutoreDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.RelProgCompEspecificaDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.RelProgDuracionDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.RelProgPersonalExternoDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.RelProgResponsableDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.RelUDidacticaTposCompetenciaDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.RelUniDidacticaMaterialDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.SubtemasUDidacticaDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.ValoresEstTematicaDTO;
import mx.gob.sedesol.basegestor.commons.utils.DateUtils;
import mx.gob.sedesol.basegestor.commons.utils.EstatusProgramaEnum;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.TipoServicioEnum;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.CatStatusPrograma;
import mx.gob.sedesol.basegestor.service.admin.OrgGubernamentalService;
import mx.gob.sedesol.basegestor.service.impl.planesyprogramas.FECServiceFacade;
import mx.gob.sedesol.basegestor.ws.moodle.clientes.model.entities.Categoria;
import mx.gob.sedesol.basegestor.ws.moodle.clientes.model.entities.RespuestaCrearCategorias;
import mx.gob.sedesol.basegestor.ws.moodle.clientes.service.client.CrearCategoria;
import mx.gob.sedesol.basegestor.ws.moodle.clientes.service.util.ErrorWS;
import mx.gob.sedesol.gestorweb.beans.acceso.BaseBean;
import mx.gob.sedesol.gestorweb.beans.administracion.BitacoraBean;
import mx.gob.sedesol.gestorweb.commons.constantes.ConstantesGestorWeb;
import mx.gob.sedesol.gestorweb.commons.constantes.ConstantesReportesGestorWeb;
import mx.gob.sedesol.gestorweb.commons.dto.NodoeHijosDTO;
import mx.gob.sedesol.gestorweb.commons.dto.ReporteConfig;
import mx.gob.sedesol.gestorweb.commons.utils.ReporteUtil;

@ViewScoped
@ManagedBean
public class ProgramaCapacitacionBean extends BaseBean {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger(ProgramaCapacitacionBean.class);

	private String leyendaBusqueda;

	@ManagedProperty(value = "#{fecServiceFacade}")
	private FECServiceFacade fecServiceFacade;

	@ManagedProperty("#{orgGubernamentalService}")
	private OrgGubernamentalService orgGubernamentalService;

	@ManagedProperty("#{bitacoraBean}")
	private BitacoraBean bitacoraBean;

	private String tpoCompSelec;
	private String ejeCapacitSel;
	private List<CatalogoComunDTO> catStatusPrograma;
	private List<CatalogoComunDTO> catModalidadPrograma;
	private List<CatalogoComunDTO> catNivelEnsenanzaProg;
	private List<CatalogoComunDTO> catDirigidoA;
	private List<CatalogoComunDTO> catEventoCapacit;
	private List<CatalogoComunDTO> catNivelConocim;
	private List<CatalogoComunDTO> catInstitucionesCertificadoras;
	private List<FichaDescProgramaDTO> programasBusqueda;
	private FichaDescProgramaDTO programaSeleccionado;
	private FichaDescProgramaDTO programa;
	private List<CatalogoComunDTO> catTpoComp;
	private List<CatalogoComunDTO> catEjeCapacit;
	private NodoeHijosDTO estPlanSedesol;
	private List<CatalogoComunDTO> ejesCapacitacion;
	private List<OrgGubernamentalDTO> catOrgGubs;
	private boolean hideMsgsGuardar;
	private Dialog cmpDialogNvoProg;
	private boolean btnNavegaSig;
	private Integer indexTabProg;
	private TabView cpmTabViewProg;
	private Integer numEstTematicas;
	private Integer numUniDidacticas;

	private Integer numEstTematicasAux = 0;
	private Integer numUniDidacticasAux = 0;

	private ControlEstTematicaDTO controlEstTematica;
	private List<Integer> temasXUnidad;
	private boolean cmpMultiSelectUd;
	private List<CatalogoComunDTO> catMaterialDidactico;
	private List<CompetenciaEspecificaDTO> catTposCompetenciaXEje;
	private List<CmpDinamicoUniDidacticaDTO> unidadesDidacticasProg;
	private Integer numSubtemas;
	private boolean ultimoTabProg;
	HashMap<Integer, String> temasIndexados = new HashMap<>();
	private HashMap<Integer, String> unidadDidacticaSelect;
	private HashMap<Integer, String> unidadDidacticaMS;
	private static final int TAB_DATOS_GENERALES = 0;
	private static final int TAB_ESTANDAR_COMPETENCIA = 1;
	private static final int TAB_ESTRUCTURA_DIDACTICA = 2;
	private static final int TAB_UNIDADES_DIDACTICAS = 3;
	private static final String COMA = ",";
	private static final String ESPACIO = " ";
	private static final Integer NUMERO_CERO = 0;
	private static final Integer NUMERO_UNO = 1;

	private boolean edicionPrograma;
	private String idProgramaSeriado;
	private int idDirigidoaStdComp;
	private String idInstitucionesCertificadoras;
	private boolean isSeriado;
	private boolean isCertificar;
	private RelProgDuracionDTO relProgDuracionTeoria;
	private RelProgDuracionDTO relProgDuracionPractica;
	private RelProgDuracionDTO relProgDuracionEvaluacion;
	private List<CatalogoComunDTO> catTipoCargaHoraria;
	private List<FichaDescProgramaDTO> programasList;
	private Integer calificacionMinimaAprobatoria = 80;
	private String nombreTipoCompetencia;
	private String nombreEjeCapacitacion;
	private String responsableDatosGenerales;
	private String responsablesDatosGenerales;
	private String autorDatosGenerales;
	private String autoresDatosGenerales;
	private List<RelProgResponsableDTO> relProgResponsables;
	private List<RelProgAutoreDTO> relProgAutores;
	List<DetEstUniDidacticaDTO> uDidacticasXPrograma;
	private StreamedContent programaPDF;
	private boolean finalizaProg;
	private boolean validaFinPrograma;
	private TreeNode arbolCompEspecificas;
	private TreeNode[] competenciasEspecifProg;
	private boolean personalInterno;

	private TreeNode arbolOrgGubernamental;
	private TreeNode arbolOrgGubAreaResp;
	private TreeNode nodoOrgGubernamentalSel;
	private TreeNode arbolEstPersonalExt;
	private TreeNode[] nodoEstPersonalExtSel;
	private TreeNode nodoAreaRespSel;

	private String compEspecificasProg;
	private HashMap<Integer, List<SubtemasUDidacticaDTO>> subTemasUnidadDidSaved;
	private boolean progConUnidDid;
	private boolean clonarConEstYUD;
	private boolean progConEstDid;
	// int indexCompEspecif = 0;
	private String personalExtDirigoA;

	private StreamedContent pdfFECMedia;
	private List<RelProgCompEspecificaDTO> competenciasEspecifPrograma;
	private boolean conCompEspecificas;
	private boolean opcDirigidoA;
	private boolean opcAreaResp;
	private boolean progConEvaluacion;

	private String nombreProgramaClon;

	private String color;
	private String labelCompetencias;

	/**
	 *
	 */
	public ProgramaCapacitacionBean() {
		labelCompetencias = "Tipo de competencia";
		color = "blanco";
		setIndexTabProg(0);
		setCmpMultiSelectUd(Boolean.TRUE);
		// setPersonalInterno(Boolean.TRUE);

		if (ObjectUtils.isNotNull(getSession().getAttribute(ConstantesGestorWeb.OBJ_PROGRAMA))) {

			controlEstTematica = new ControlEstTematicaDTO();
			responsablesDatosGenerales = new String();
			autoresDatosGenerales = new String();
			programa = (FichaDescProgramaDTO) getSession().getAttribute(ConstantesGestorWeb.OBJ_PROGRAMA);
			this.validaDatosProgramaRequeridos(programa);

			getSession().removeAttribute(ConstantesGestorWeb.OBJ_PROGRAMA);

			if (ObjectUtils.isNotNull(getSession().getAttribute(ConstantesGestorWeb.EDICION_PROGRAMA))) {

				edicionPrograma = (Boolean) getSession().getAttribute(ConstantesGestorWeb.EDICION_PROGRAMA);
				getSession().removeAttribute(ConstantesGestorWeb.EDICION_PROGRAMA);
			}

		} else if (ObjectUtils.isNull(programa) || ObjectUtils.isNull(programa.getIdPrograma())) {

			// Nuevo programa
			leyendaBusqueda = "Listado de Programas de capacitación creados";
			controlEstTematica = new ControlEstTematicaDTO();
			programa = new FichaDescProgramaDTO();
			programa.setTipoCompetencia(0);
			programa.setCatTipoEventoEc(new CatalogoComunDTO());
			programa.setOrganismoGubernamental(new OrgGubernamentalDTO());
			programa.setCatNivelConocimiento(new CatalogoComunDTO());
			programa.setCatNivelEnsenanzaPrograma(new CatalogoComunDTO());
			programa.setCatModalidad(new CatalogoComunDTO());

			if (ObjectUtils.isNotNull(programa.getFechaVigInicial())) {
				programa.setFechaSolicitud(DateUtils.agregaDiaHabilAFecha(programa.getFechaVigInicial()));
				programa.setFechaProduccion(DateUtils.agregaDiaHabilAFecha(programa.getFechaSolicitud()));
				programa.setFechaArranque(DateUtils.agregaDiaHabilAFecha(programa.getFechaProduccion()));
			}
			responsablesDatosGenerales = new String();
			autoresDatosGenerales = new String();
		}
	}

	@SuppressWarnings("unchecked")
	@PostConstruct
	public void iniciaRecursos() {

		catNivelEnsenanzaProg = (List<CatalogoComunDTO>) getSession().getServletContext()
				.getAttribute(ConstantesGestorWeb.CAT_NIVEL_ENSE_PLAN_PROG);
		catStatusPrograma = (List<CatalogoComunDTO>) getSession().getServletContext()
				.getAttribute(ConstantesGestorWeb.CAT_ESTATUS_PROG);
		catModalidadPrograma = (List<CatalogoComunDTO>) getSession().getServletContext()
				.getAttribute(ConstantesGestorWeb.CAT_MODALIDAD_PLAN_PROG);
		catDirigidoA = (List<CatalogoComunDTO>) getSession().getServletContext()
				.getAttribute(ConstantesGestorWeb.CAT_DIRIGIDO_A_PROG);
		catOrgGubs = orgGubernamentalService.obtenerOrgGubsPadres();
		catEventoCapacit = (List<CatalogoComunDTO>) getSession().getServletContext()
				.getAttribute(ConstantesGestorWeb.CAT_EVENTO_CAPACITACION);
		catNivelConocim = (List<CatalogoComunDTO>) getSession().getServletContext()
				.getAttribute(ConstantesGestorWeb.CAT_NIVEL_CONOCIMIENTO);
		catInstitucionesCertificadoras = (List<CatalogoComunDTO>) getSession().getServletContext()
				.getAttribute(ConstantesGestorWeb.CAT_INSTITUCIIONES_CERTIFICADORAS);
		catTipoCargaHoraria = (List<CatalogoComunDTO>) getSession().getServletContext()
				.getAttribute(ConstantesGestorWeb.CAT_TIPO_CARGA_HORARIA);
		catMaterialDidactico = (List<CatalogoComunDTO>) getSession().getServletContext()
				.getAttribute(ConstantesGestorWeb.CAT_MATERIAL_DIDACTICO);

		this.consultaProgramasBorrador();
		this.generaEstructuraCatTpoCompetenciaPlan();
		this.generaCatEjesCapacitBusqueda();
		this.generaArbolOrgGubernamental();
		this.generaArbolEstPersonalExterno();

		relProgDuracionTeoria = new RelProgDuracionDTO(
				getTipoCargaHoraria(ConstantesGestorWeb.TIPO_CARGA_HORARIA_TEORIA));
		relProgDuracionPractica = new RelProgDuracionDTO(
				getTipoCargaHoraria(ConstantesGestorWeb.TIPO_CARGA_HORARIA_PRACTICA));
		relProgDuracionEvaluacion = new RelProgDuracionDTO(
				getTipoCargaHoraria(ConstantesGestorWeb.TIPO_CARGA_HORARIA_EVALUACION));

		// Carga las competencias especificas previamente guardadas
		if (!ObjectUtils.isNullOrEmpty(this.programa.getRelProgramaComEspecificas())) {
			catTposCompetenciaXEje = new ArrayList<>();
			for (RelProgCompEspecificaDTO cmpEsp : this.programa.getRelProgramaComEspecificas()) {
				catTposCompetenciaXEje.add(cmpEsp.getCatCompetenciaEspecifica());
			}
			setConCompEspecificas(Boolean.TRUE);
		}

		// Si es Edicion de programa
		if (ObjectUtils.isNotNull(programa) && ObjectUtils.isNotNull(programa.getIdPrograma()) && isEdicionPrograma()) {
			controlEstTematica = new ControlEstTematicaDTO();
			generaEstructuraCatTpoCompetenciaPlan();
			this.cargaDatosEdicionPrograma(programa);
			this.cargaCatalogosInicialesEdicion(programa);
			this.generaVistaEstTematica();
			this.validaDatosProgramaRequeridos(programa);

		} else {

			PersonaDTO coordAcad = fecServiceFacade.getPersonaService()
					.buscarPorId(getUsuarioEnSession().getIdPersona());
			this.programa.setCoordinadorAcademico(coordAcad);
		}

		this.getNombreEjeCapTipoCom();

	}

	public void generaTemaUnidad() {
		if (ObjectUtils.isNull(numEstTematicas))
			numEstTematicas = 0;
		if (ObjectUtils.isNull(numUniDidacticas))
			numUniDidacticas = 0;

		this.numEstTematicas = this.numEstTematicasAux + this.numEstTematicas;
		this.numUniDidacticas = this.numUniDidacticasAux + this.numUniDidacticas;
		this.generaTemasPrograma();
		this.generaUniDidacticasPrograma();
		logger.info("Numero de estructuras tematicas: " + numEstTematicas);
		logger.info("Numero de unidades didacticas: " + numUniDidacticas);

	}

	public void limpiarFiltro() {
		programa = new FichaDescProgramaDTO();
		programa.setTipoCompetencia(0);
		programa.setCatTipoEventoEc(new CatalogoComunDTO());
		programa.setOrganismoGubernamental(new OrgGubernamentalDTO());
		programa.setCatNivelConocimiento(new CatalogoComunDTO());
		programa.setCatNivelEnsenanzaPrograma(new CatalogoComunDTO());
		programa.setCatModalidad(new CatalogoComunDTO());

	}

	/**
	 *
	 * @param programaEditar
	 */
	private void cargaCatalogosInicialesEdicion(FichaDescProgramaDTO programaEditar) {

		catEjeCapacit = new ArrayList<>();
		for (NodoeHijosDTO nh : estPlanSedesol.getNodosHijos()) {
			if (nh.getIdNodo().equals(programaEditar.getTipoCompetencia())) {
				for (NodoeHijosDTO nint : nh.getNodosHijos()) {
					CatalogoComunDTO cc = new CatalogoComunDTO();
					cc.setId(nint.getIdNodo());
					cc.setNombre(nint.getNombre());
					catEjeCapacit.add(cc);
				}
			}
		}
	}

	/**
	 *
	 * @param programa
	 */
	private void validaDatosProgramaRequeridos(FichaDescProgramaDTO programa) {

		if (ObjectUtils.isNull(programa.getCatTipoEventoEc())) {
			programa.setCatTipoEventoEc(new CatalogoComunDTO());
		}

		if (ObjectUtils.isNull(programa.getCatNivelEnsenanzaPrograma())) {
			programa.setCatNivelEnsenanzaPrograma(new CatalogoComunDTO());
		}

		if (ObjectUtils.isNull(programa.getCatModalidad())) {
			programa.setCatModalidad(new CatalogoComunDTO());
		}

		if (ObjectUtils.isNull(programa.getCatNivelConocimiento())) {
			programa.setCatNivelConocimiento(new CatalogoComunDTO());
		}

		if (ObjectUtils.isNull(programa.getAreaResponsable())) {
			programa.setAreaResponsable(new OrgGubernamentalDTO());
		}

		if (ObjectUtils.isNull(programa.getFechaSolicitud())) {
			programa.setFechaSolicitud(DateUtils.agregaDiaHabilAFecha(programa.getFechaVigInicial()));
		}

		if (ObjectUtils.isNull(programa.getFechaProduccion())) {
			programa.setFechaProduccion(DateUtils.agregaDiaHabilAFecha(programa.getFechaSolicitud()));
		}

		if (ObjectUtils.isNull(programa.getFechaArranque())) {
			programa.setFechaArranque(DateUtils.agregaDiaHabilAFecha(programa.getFechaProduccion()));
		}

	}

	/**
	 *
	 * @return
	 */
	private void asignaCargaHorariaAPrograma(FichaDescProgramaDTO programa, boolean conEvaluacion,
			Integer califMinAprob) {
		List<RelProgDuracionDTO> relProgramaDuracionList = new ArrayList<>();

		this.validaProgDuracionDTO(relProgDuracionTeoria);
		relProgramaDuracionList.add(relProgDuracionTeoria);

		this.validaProgDuracionDTO(relProgDuracionPractica);
		relProgramaDuracionList.add(relProgDuracionPractica);

		if (conEvaluacion) {
			this.validaProgDuracionDTO(relProgDuracionEvaluacion);
			relProgramaDuracionList.add(relProgDuracionEvaluacion);
			programa.setCalificacionMinAprobatoria(califMinAprob.toString());
		}
		programa.setRelProgramaDuracion(relProgramaDuracionList);
	}

	/**
	 *
	 * @param dto
	 */
	private void validaProgDuracionDTO(RelProgDuracionDTO dto) {

		if (ObjectUtils.isNotNull(dto)) {
			if (ObjectUtils.isNullOrEmpty(dto.getHoras())) {
				dto.setHoras(ConstantesGestorWeb.NUM_CERO_CADENA);
			}
			if (ObjectUtils.isNullOrEmpty(dto.getMinutos())) {
				dto.setMinutos(ConstantesGestorWeb.NUM_CERO_CADENA);
			}
		}
	}

	/**
	 *
	 * @param arbol
	 * @param idData
	 */
	private void obtieneValorCompEspecifSelec(TreeNode arbol, List<Integer> valoresRegistrados, TreeNode[] nodosSelect,
			Integer indexGlobal) {

		for (TreeNode hijo : arbol.getChildren()) {
			NodoDTO data = (NodoDTO) hijo.getData();
			if (ObjectUtils.isNotNull(data.getNombreObjCurricular())
					&& data.getNombreObjCurricular().equals(ConstantesGestorWeb.TIPO_COMP_ESPECIFICA)
					&& valoresRegistrados.contains(data.getId())) {
				hijo.setSelected(Boolean.TRUE);
				nodosSelect[indexGlobal] = hijo;
				indexGlobal++;
			}
			if (!ObjectUtils.isNullOrEmpty(hijo.getChildren())) {
				obtieneValorCompEspecifSelec(hijo, valoresRegistrados, nodosSelect, indexGlobal);
			}
		}
	}

	/**
	 *
	 * @param arbol
	 * @param valoresRegistrados
	 * @param nodosSelect
	 * @param indexGlobal
	 */
	private void obtieneValorPersonalExterno(TreeNode arbol, List<Integer> valoresRegistrados, TreeNode[] nodosSelect,
			Integer indexGlobal) {

		for (TreeNode hijo : arbol.getChildren()) {
			NodoDTO data = (NodoDTO) hijo.getData();
			if (valoresRegistrados.contains(data.getId())) {
				hijo.setSelected(Boolean.TRUE);
				nodosSelect[indexGlobal] = hijo;
				indexGlobal++;
			}
			if (!ObjectUtils.isNullOrEmpty(hijo.getChildren())) {
				obtieneValorCompEspecifSelec(hijo, valoresRegistrados, nodosSelect, indexGlobal);
			}
		}
	}

	/**
	 *
	 * @param arbol
	 * @param compEspecifSel
	 */
	private void obtieneValorSelecFromTreeNode(TreeNode arbol, Integer valorSeleccionado, TreeNode nodeSelected) {
		for (TreeNode hijo : arbol.getChildren()) {
			NodoDTO data = (NodoDTO) hijo.getData();

			if (data.getId().equals(valorSeleccionado)) {
				hijo.setSelected(Boolean.TRUE);
				nodeSelected = hijo;
				break;
			}
			if (!ObjectUtils.isNullOrEmpty(hijo.getChildren())) {
				obtieneValorSelecFromTreeNode(hijo, valorSeleccionado, nodeSelected);
			}
		}
	}

	/**
	 *
	 */
	public void consultaProgramasBorrador() {
		programasBusqueda = fecServiceFacade.getFichaDescProgramaService().consultaUltimosProgramas();
	}

	public void addResponsableListener() {

		StringBuilder responsablesTemp = new StringBuilder();

		if (responsableDatosGenerales.length() != NUMERO_CERO.intValue()) {
			if (responsablesDatosGenerales.length() > NUMERO_CERO) {
				responsablesTemp.append(responsablesDatosGenerales);
				responsablesTemp.append(COMA).append(ESPACIO);
				responsablesTemp.append(responsableDatosGenerales);
			} else {
				responsablesTemp.append(responsableDatosGenerales);
			}
			responsablesDatosGenerales = responsablesTemp.toString();
			setResponsableAlProgramaListener();
		}
		responsableDatosGenerales = new String();
	}

	public void setResponsableAlProgramaListener() {
		if (ObjectUtils.isNullOrEmpty(responsablesDatosGenerales)) {
			relProgResponsables = null;
		} else {
			relProgResponsables = new ArrayList<RelProgResponsableDTO>();

			RelProgResponsableDTO relProgramaResponsable = new RelProgResponsableDTO();

			relProgramaResponsable.setFechaRegistro(new Date());
			relProgramaResponsable.setUsuarioModifico(getUsuarioEnSession().getIdPersona());
			// relProgramaResponsable.setResponsables(setQuitarComaCadena(responsablesDatosGenerales));
			relProgramaResponsable.setResponsables(responsablesDatosGenerales);
			relProgResponsables.add(relProgramaResponsable);
		}

	}

	public void addAutorListener() {

		StringBuilder AutoresTemp = new StringBuilder();

		if (autorDatosGenerales.length() != NUMERO_CERO.intValue()) {
			if (autoresDatosGenerales.length() > NUMERO_CERO) {
				AutoresTemp.append(autoresDatosGenerales);
				AutoresTemp.append(COMA).append(ESPACIO);
				AutoresTemp.append(autorDatosGenerales);
			} else {
				AutoresTemp.append(autorDatosGenerales);
			}
			autoresDatosGenerales = AutoresTemp.toString();
			setAutoresAlProgramaListener();
		}
		autorDatosGenerales = new String();

	}

	public void setAutoresAlProgramaListener() {
		if (ObjectUtils.isNullOrEmpty(autoresDatosGenerales)) {
			relProgAutores = null;
		} else {
			relProgAutores = new ArrayList<RelProgAutoreDTO>();
			RelProgAutoreDTO programaAutore = new RelProgAutoreDTO();
			programaAutore.setFechaRegistro(new Date());
			programaAutore.setUsuarioModifico(getUsuarioEnSession().getIdPersona());
			// programaAutore.setAutores(setQuitarComaCadena(autoresDatosGenerales));
			programaAutore.setAutores(autoresDatosGenerales);

			relProgAutores.add(programaAutore);
		}

	}

	private String setQuitarComaCadena(String cadena) {
		if (ObjectUtils.isNotNull(cadena) && cadena.length() != NUMERO_CERO.intValue()) {
			if (COMA.equals(String.valueOf(cadena.charAt(cadena.length() - NUMERO_UNO)))) {
				cadena = cadena.substring(NUMERO_CERO, cadena.length() - NUMERO_UNO);

			}
		}
		return cadena;
	}

	/**
	 *
	 * @param nombreTpoCargaHoraria
	 * @return
	 */
	private CatalogoComunDTO getTipoCargaHoraria(String nombreTpoCargaHoraria) {

		CatalogoComunDTO tipoCargaHoraria = null;

		for (CatalogoComunDTO tpoCargaHoraria : catTipoCargaHoraria) {
			if (tpoCargaHoraria.getNombre().equals(nombreTpoCargaHoraria)) {
				tipoCargaHoraria = tpoCargaHoraria;
				break;
			}
		}
		return tipoCargaHoraria;
	}

	/**
	 *
	 */
	private void getNombreEjeCapTipoCom() {

		if (ObjectUtils.isNotNull(programa)) {

			if (ObjectUtils.isNotNull(programa.getEjeCapacitacion())) {
				nombreEjeCapacitacion = this.getNombreElemento(programa.getEjeCapacitacion(), ejesCapacitacion);
			}

			if (ObjectUtils.isNotNull(programa.getTipoCompetencia())) {
				nombreTipoCompetencia = this.getNombreElemento(programa.getTipoCompetencia(), catTpoComp);
			}
		}
	}

	/**
	 * Metodo que carga los datos si existe una estructura tematica asociada al
	 * programa
	 */
	private void generaVistaEstTematica() {

		EstructuraTematicaDTO estTematica = getFecServiceFacade().getEstructuraTematicaService()
				.obtieneEstTematicaTemaPorPrograma(programa.getIdPrograma());

		controlEstTematica = new ControlEstTematicaDTO();
		if (ObjectUtils.isNotNull(estTematica)) {

			uDidacticasXPrograma = new ArrayList<>();
			setNumUniDidacticas(estTematica.getNumUnidadesTematicas());

			List<DetEtematicaTemaDTO> temas = estTematica.getDetEtematicaTemas();

			if (!ObjectUtils.isNullOrEmpty(temas)) {

				setNumEstTematicas(temas.size());
				controlEstTematica.setTemas(new ArrayList<>());
				for (DetEtematicaTemaDTO detEtem : temas) {

					ValoresEstTematicaDTO valorEst = new ValoresEstTematicaDTO();
					valorEst.setNombreTema(detEtem.getNombreTema());
					controlEstTematica.getTemas().add(valorEst);

					setProgConEstDid(Boolean.TRUE);

					this.obtieneUnidadesDidacticasProg(detEtem.getIdDetTema());

				}
			}

			temasXUnidad = new ArrayList<>();
			if (ObjectUtils.isNotNull(numEstTematicas)) {
				for (int i = 1; i <= numEstTematicas; i++) {
					temasXUnidad.add(i);
				}
				this.generaUniDidacticasPrograma();
			}

		}
	}

	/**
	 * Consulta los datos de la pantalla Unidades didacticas
	 */
	private void obtieneUnidadesDidacticasProg(Integer idTema) {

		List<RelEstUnidadDidacticaDTO> lstAux = getFecServiceFacade().getEstructuraTematicaService()
				.obtieneRelacionesPorEstTematica(idTema);
		if (ObjectUtils.isNotNull(lstAux)) {
			setProgConUnidDid(Boolean.TRUE);
			for (RelEstUnidadDidacticaDTO rel : lstAux) {
				if (!this.containsEstUnidDidactica(rel.getDetEstUnidadDidactica(), uDidacticasXPrograma)) {
					uDidacticasXPrograma.add(rel.getDetEstUnidadDidactica());
				}
			}
		}
	}

	private boolean containsEstUnidDidactica(DetEstUniDidacticaDTO unidDid,
			List<DetEstUniDidacticaDTO> unidadesPrograma) {
		if (!ObjectUtils.isNullOrEmpty(unidadesPrograma)) {
			for (DetEstUniDidacticaDTO ud : unidadesPrograma) {
				if (ud.getIdUnidadDidactica().equals(unidDid.getIdUnidadDidactica())) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 *
	 */
	private void generaObjsUnidadesDidacticas() {
		if (!ObjectUtils.isNullOrEmpty(uDidacticasXPrograma)) {

			subTemasUnidadDidSaved = new HashMap<>();

			unidadesDidacticasProg = new ArrayList<>();
			for (DetEstUniDidacticaDTO ud : uDidacticasXPrograma) {

				CmpDinamicoUniDidacticaDTO cmpUd = new CmpDinamicoUniDidacticaDTO();
				cmpUd.setNombreUnidad(ud.getTituloUa());
				cmpUd.setDetUnidadDidactica(ud);
				cmpUd.setMaterialDidApoyoSel(this.obtenerMaterialDidacticoFromUD(ud.getRelUniDidacticaMaterial()));
				cmpUd.setTiposCompetenciaXEje(this.obtenerTposCompFromUD(ud.getRelUDidacticaTposCompetencia()));

				unidadesDidacticasProg.add(cmpUd);

				subTemasUnidadDidSaved.put(ud.getIdUnidadDidactica(), ud.getSubtemasUdidactica());
			}
		}
	}

	/**
	 *
	 * @param relMatDid
	 * @return
	 */
	private List<String> obtenerMaterialDidacticoFromUD(List<RelUniDidacticaMaterialDTO> relMatDid) {
		List<String> md = new ArrayList<>();
		for (RelUniDidacticaMaterialDTO relud : relMatDid) {
			md.add(relud.getCatMaterialDidactico().getId().toString());
		}

		return md;
	}

	/**
	 *
	 * @param relTposComp
	 * @return
	 */
	private List<String> obtenerTposCompFromUD(List<RelUDidacticaTposCompetenciaDTO> relTposComp) {
		List<String> tposComp = new ArrayList<>();
		for (RelUDidacticaTposCompetenciaDTO relud : relTposComp) {
			tposComp.add(relud.getCatCompetenciaEspecifica().getId().toString());
		}

		return tposComp;
	}

	/**
	 *
	 */
	private void generaEstructuraCatTpoCompetenciaPlan() {

		List<NodoeHijosDTO> planes = new ArrayList<>();
		List<MallaCurricularDTO> mallas = new ArrayList<>();

		// List<MallaCurricularDTO> mallas =
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

	/**
	 *
	 */
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

	/**
	 *
	 * @param e
	 */
	public void onChangeBusquedaEstatus(ValueChangeEvent e) {
		if (ObjectUtils.isNotNull(e.getNewValue())) {
			CatalogoComunDTO estatus = (CatalogoComunDTO) e.getNewValue();
			programasBusqueda = fecServiceFacade.getFichaDescProgramaService()
					.consultaProgramasPorEstatus(estatus.getId());
		}
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
	 * Obtiene los tipos de competencia por eje de capacitacion
	 *
	 * @param e
	 */
	public void onChangeEjeCapacitacion(ValueChangeEvent e) {
		if (ObjectUtils.isNotNull(e.getNewValue())) {
			Integer idEjeCapacitacion = (Integer) e.getNewValue();
			programa.setEjeCapacitacion(idEjeCapacitacion);
		}
	}

	public void onChangeOrgGubPlan(ValueChangeEvent e) {
		if (ObjectUtils.isNotNull(e.getNewValue())) {
			for (OrgGubernamentalDTO orgGub : catOrgGubs) {
				for (OrgGubernamentalDTO orgGubHijo : orgGub.getLstHijosOrgGub()) {
					if (orgGubHijo.getId().equals((Integer) e.getNewValue())) {
						programa.setOrganismoGubernamental(orgGubHijo);
					}
				}
			}
		}
	}

	/**
	 * Event TabChange
	 *
	 * @param event
	 */
	public void onTabProgChange(TabChangeEvent event) {

		TabView tabView = (TabView) event.getComponent();

		indexTabProg = tabView.getChildren().indexOf(event.getTab());

		if (indexTabProg.equals(3)) {

			// Genera los objetos de unidades didacticas guardadas
			if (!ObjectUtils.isNullOrEmpty(uDidacticasXPrograma)) {
				this.generaObjsUnidadesDidacticas();
				cpmTabViewProg.setActiveIndex(indexTabProg);
				setUltimoTabProg(Boolean.TRUE);
				RequestContext.getCurrentInstance().update("formNvoProg:tabViewFec");

			} else if (this.validaUnidDidacticasXEstTem()) {
				this.generaEstructuraUnidadesDidacticas();
				cpmTabViewProg.setActiveIndex(indexTabProg);
				setUltimoTabProg(Boolean.TRUE);
				RequestContext.getCurrentInstance().update("formNvoProg:tabViewFec");
			} else {
				// Mensaje
				indexTabProg = indexTabProg - 1;
				agregarMsgError("Se deben asociar todas las unidades didacticas", null);

				// Se cargan competencias especificas si existen
				if (ObjectUtils.isNotNull(programa.getRelProgramaComEspecificas())) {
					compEspecificasProg = programa.getRelProgramaComEspecificas().stream()
							.map(ce -> ce.getCatCompetenciaEspecifica().getNombre()).collect(Collectors.joining(","));
				}
			}
		} else {// Navegacion
			cpmTabViewProg.setActiveIndex(indexTabProg);
			setUltimoTabProg(Boolean.FALSE);
		}

		RequestContext.getCurrentInstance().update("formNvoProg:tabViewFec");
	}

	/**
	 *
	 * @param idTpoComp
	 * @return
	 */
	public String obtieneNombreTpoCompetencia(Integer idTpoComp) {

		if (ObjectUtils.isNotNull(idTpoComp)) {
			for (CatalogoComunDTO tpoCom : catTpoComp) {
				if (tpoCom.getId().equals(idTpoComp)) {
					return tpoCom.getNombre();
				}
			}
		}
		return null;
	}

	/**
	 *
	 * @param idEjeCap
	 * @return
	 */
	public String obtieneNombreEjeCapacit(Integer idEjeCap) {
		if (ObjectUtils.isNotNull(idEjeCap)) {
			for (CatalogoComunDTO ejeCap : ejesCapacitacion) {
				if (ejeCap.getId().compareTo(idEjeCap) == 0) {
					return ejeCap.getNombre();
				}
			}
		}
		return null;
	}

	/**
	 *
	 * @return
	 */
	public String crearProgramaCapacitacion() {

		programa = new FichaDescProgramaDTO();
		return ConstantesGestorWeb.NAVEGA_NUEVO_PROGRAMA_CAPACITACION;
	}

	/**
	 *
	 */
	public void buscarProgramasCapacitacion() {
		if (ObjectUtils.isNotNull(programa)) {
			leyendaBusqueda = "Resultado de la busqueda";
			programasBusqueda = fecServiceFacade.getFichaDescProgramaService().buscaProgramasPorCriterios(programa);
			if (!programasBusqueda.isEmpty()) {
				bitacoraBean.guardarBitacora(idPersonaEnSesion(), "CON_PRO", "", requestActual(),
						TipoServicioEnum.LOCAL);
			}
		}

	}

	/**
	 *
	 * @return
	 */
	public String navegaDatosFECPrograma() {

		if (datosEspecifProgramaValidos()) {
			if (isEdicionPrograma()) {
				getSession().setAttribute(ConstantesGestorWeb.EDICION_PROGRAMA, Boolean.TRUE);
			}

			competenciasEspecifPrograma = generaRelProgramaCompEspecificas(this.programa);
			this.programa.setRelProgramaComEspecificas(competenciasEspecifPrograma);
			this.programa.setRelProgEstPersonalExterno(
					this.generaRelProgEstPersonalExterno(this.programa, nodoEstPersonalExtSel));
			this.programa.setCatStatusPrograma(fecServiceFacade.getCatStatusProgramaService()
					.buscarRegistroPorNombre(EstatusProgramaEnum.BORRADOR.getEtiqueta(), CatStatusPrograma.class));
			this.programa.setUsuarioModifico(getUsuarioEnSession().getIdPersona());

			ResultadoDTO<FichaDescProgramaDTO> resTx = fecServiceFacade.guardarDatosPrograma(this.programa);
			if (ObjectUtils.isNotNull(resTx) && resTx.getResultado().getValor()) {
				getSession().setAttribute(ConstantesGestorWeb.OBJ_PROGRAMA, resTx.getDto());
				return ConstantesGestorWeb.NAVEGA_FEC_PROGRAMA;
			} else {
				return ConstantesGestorWeb.NAVEGA_NUEVO_PROGRAMA_CAPACITACION;
			}

		}
		return null;
	}

	public String navegaBusquedaProgramas() {
		programa = new FichaDescProgramaDTO();
		return ConstantesGestorWeb.NAVEGA_BUSQUEDA_PROGRAMAS_CAPACIT;
	}

	/**
	 *
	 * @return
	 */
	private boolean datosEspecifProgramaValidos() {
		if (ObjectUtils.isNullOrEmpty(competenciasEspecifProg)
				|| competenciasEspecifProg.length == ConstantesGestorWeb.NUMERO_CERO) {
			agregarMsgError("Seleccione almenos una competencia especifica", null);
			this.generaArbolCompEspecificasXEje();
			return false;

		}
		return true;
	}

	/**
	 *
	 * @return
	 */
	private List<RelProgCompEspecificaDTO> generaRelProgramaCompEspecificas(FichaDescProgramaDTO programa) {
		List<RelProgCompEspecificaDTO> aux = null;
		if (!ObjectUtils.isNullOrEmpty(competenciasEspecifProg)) {
			aux = new ArrayList<>();
			catTposCompetenciaXEje = new ArrayList<>();
			for (TreeNode nodoCmpEsp : competenciasEspecifProg) {

				if (ObjectUtils.isNotNull(nodoCmpEsp) && ObjectUtils.isNotNull(nodoCmpEsp.getData())) {

					NodoDTO data = (NodoDTO) nodoCmpEsp.getData();
					if (ObjectUtils.isNotNull(data.getNombreObjCurricular()) && data.getNombreObjCurricular()
							.equalsIgnoreCase(ConstantesGestorWeb.TIPO_COMP_ESPECIFICA)) {
						RelProgCompEspecificaDTO compEsp = new RelProgCompEspecificaDTO();
						compEsp.setCatCompetenciaEspecifica(
								getFecServiceFacade().getCompetenciaEspecificaService().buscarPorId(data.getId()));

						compEsp.setUsuarioModifico(getUsuarioEnSession().getIdPersona());
						compEsp.setFechaRegistro(programa.getFechaRegistro());
						compEsp.setFichaDescProgramaDTO(programa);
						if (!this.containsCompEspecifica(compEsp.getIdCompEspecifica(), aux)) {
							aux.add(compEsp);
							// Competencias espeficias sin persistir
							catTposCompetenciaXEje.add(compEsp.getCatCompetenciaEspecifica());
						}
					}

				}
			}
		}
		return aux;
	}

	/**
	 *
	 * @param programa
	 * @return
	 */
	private List<RelProgPersonalExternoDTO> generaRelProgEstPersonalExterno(FichaDescProgramaDTO programa,
			TreeNode[] estPersonalExtSel) {
		List<RelProgPersonalExternoDTO> aux = null;
		if (ObjectUtils.isNotNull(estPersonalExtSel)) {
			aux = new ArrayList<>();
			for (TreeNode nodoCmpEsp : estPersonalExtSel) {

				if (ObjectUtils.isNotNull(nodoCmpEsp)) {
					NodoDTO data = (NodoDTO) nodoCmpEsp.getData();
					RelProgPersonalExternoDTO personalExt = new RelProgPersonalExternoDTO();

					personalExt.setTblFichaDescriptivaPrograma(programa);
					personalExt.setEstPersonalExterno(
							fecServiceFacade.getEstPersonalExternoService().buscarPorId(data.getId()));
					personalExt.setUsuarioModifico(getUsuarioEnSession().getIdPersona());
					personalExt.setFechaRegistro(programa.getFechaRegistro());

					aux.add(personalExt);
				}
			}
		}

		return aux;
	}

	/**
	 *
	 * @param idCompEsp
	 * @param compEspecif
	 * @return
	 */
	private boolean containsCompEspecifica(Integer idCompEsp, List<RelProgCompEspecificaDTO> compEspecif) {
		if (!ObjectUtils.isNullOrEmpty(compEspecif)) {
			for (RelProgCompEspecificaDTO a : compEspecif) {
				if (idCompEsp.equals(a.getIdCompEspecifica())) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Cancelar Programa
	 */
	public String cancelarPrograma() {
		programa = new FichaDescProgramaDTO();
		return ConstantesGestorWeb.NAVEGA_BUSQUEDA_PROGRAMAS_CAPACIT;

	}

	/**
	 * Next Tab
	 */
	public void nextTabProg() {

		indexTabProg = indexTabProg + 1;
		setHideMsgsGuardar(Boolean.FALSE);

		if (indexTabProg.equals(3)) {

			// Genera los objetos de unidades didacticas guardadas
			if (!ObjectUtils.isNullOrEmpty(uDidacticasXPrograma)) {
				this.generaObjsUnidadesDidacticas();
				cpmTabViewProg.setActiveIndex(indexTabProg);
				setUltimoTabProg(Boolean.TRUE);
				RequestContext.getCurrentInstance().update("formNvoProg:tabViewFec");

			} else if (this.validaUnidDidacticasXEstTem()) {

				this.generaEstructuraUnidadesDidacticas();
				cpmTabViewProg.setActiveIndex(indexTabProg);
				setUltimoTabProg(Boolean.TRUE);
				RequestContext.getCurrentInstance().update("formNvoProg:tabViewFec");
			} else {
				// Mensaje
				indexTabProg = indexTabProg - 1;
				agregarMsgError("Se deben asociar todas las unidades didacticas", null);
			}
		} else {// Navegacion
			cpmTabViewProg.setActiveIndex(indexTabProg);
			setUltimoTabProg(Boolean.FALSE);

			// Se cargan competencias especificas si existen
			if (ObjectUtils.isNotNull(programa.getRelProgramaComEspecificas())) {
				compEspecificasProg = programa.getRelProgramaComEspecificas().stream()
						.map(ce -> ce.getCatCompetenciaEspecifica().getNombre()).collect(Collectors.joining(","));
			}

		}
		validaDatosProgramaRequeridos(this.programa);
		RequestContext.getCurrentInstance().update("formNvoProg:tabViewFec");
	}

	/**
	 *
	 */
	private void generaEstructuraUnidadesDidacticas() {

		if (ObjectUtils.isNotNull(controlEstTematica) && ObjectUtils.isNotNull(controlEstTematica.getTemas())) {

			// TEMAS
			int indexTemas = 1;
			for (ValoresEstTematicaDTO tema : controlEstTematica.getTemas()) {
				temasIndexados.put(indexTemas, tema.getNombreTema());
				indexTemas++;
			}
			// fin temas

			// COMPONENTE MULTISELECT
			if (isCmpMultiSelectUd() && !ObjectUtils.isNullOrEmpty(controlEstTematica.getUnidDidSelect())) {
				unidadDidacticaMS = new HashMap<>();
				int indexUniDidacticaMS = 1;
				for (ValoresEstTematicaDTO unidadesMS : controlEstTematica.getUnidDidSelect()) {
					unidadDidacticaMS.put(indexUniDidacticaMS,
							nombreTemarioByMultiSelect(temasIndexados, unidadesMS.getUnidadesTematicas()));
					indexUniDidacticaMS++;
				}

				// Se genera la estructura de componentes dinamicos
				if (ObjectUtils.isNotNull(unidadDidacticaMS)) {

					if (ObjectUtils.isNullOrEmpty(unidadesDidacticasProg)) {
						unidadesDidacticasProg = new ArrayList<CmpDinamicoUniDidacticaDTO>();
					}

					for (String ud : unidadDidacticaMS.values()) {
						if (!this.containNombreUnidadDidactica(unidadesDidacticasProg, ud)) {
							CmpDinamicoUniDidacticaDTO udDto = new CmpDinamicoUniDidacticaDTO();
							udDto.setNombreUnidad(ud);
							udDto.setDetUnidadDidactica(new DetEstUniDidacticaDTO(ud, isProgConUnidDid()));
							udDto.setMaterialDidApoyoSel(new ArrayList<String>());
							unidadesDidacticasProg.add(udDto);
						}
					}
				}
			}

			// Componente Select
			if (!isCmpMultiSelectUd() && !ObjectUtils.isNullOrEmpty(controlEstTematica.getUnidadesDidacticas())) {
				unidadDidacticaSelect = new HashMap<>();
				int indexUniDidacticaSel = 1;
				for (ValoresEstTematicaDTO unidadesSel : controlEstTematica.getUnidadesDidacticas()) {
					// unidadesSel.getUnidadTematica()
					unidadDidacticaSelect.put(indexUniDidacticaSel,
							temasIndexados.get(unidadesSel.getUnidadTematica()));
					indexUniDidacticaSel++;
				}

				if (ObjectUtils.isNotNull(unidadDidacticaSelect)) {

					if (ObjectUtils.isNullOrEmpty(unidadesDidacticasProg)) {
						unidadesDidacticasProg = new ArrayList<CmpDinamicoUniDidacticaDTO>();
					}

					// Integer numTemasnuevos = unidadDidacticaSelect.size() -
					// unidadesDidacticasProg.size();
					for (String ntema : unidadDidacticaSelect.values()) {
						if (!this.containNombreUnidadDidactica(unidadesDidacticasProg, ntema)) {
							CmpDinamicoUniDidacticaDTO udDTO = new CmpDinamicoUniDidacticaDTO();
							udDTO.setNombreUnidad(ntema);
							udDTO.setDetUnidadDidactica(new DetEstUniDidacticaDTO(ntema, isProgConUnidDid()));
							udDTO.setMaterialDidApoyoSel(new ArrayList<String>());
							unidadesDidacticasProg.add(udDTO);
						}
					}

				}
			}

		} // Objeto General
	}

	private boolean containNombreUnidadDidactica(List<CmpDinamicoUniDidacticaDTO> unidadesDidacticas,
			String nombreUnidad) {
		if (!ObjectUtils.isNullOrEmpty(unidadesDidacticas)) {
			for (CmpDinamicoUniDidacticaDTO udb : unidadesDidacticas) {
				if (udb.getNombreUnidad().equalsIgnoreCase(nombreUnidad)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 *
	 * @param temas
	 * @param temasSelect
	 * @return
	 */
	private String nombreTemarioByMultiSelect(HashMap<Integer, String> temas, List<String> temasSelect) {
		String nombre = "";
		for (String id : temasSelect) {
			if (nombre.isEmpty()) {
				nombre = temas.get(new Integer(id));
			} else {
				nombre = nombre.concat(", ").concat(temas.get(new Integer(id)));
			}
		}
		return nombre;
	}

	/**
	 * Previous Tab
	 */
	public void prevTabProg() {
		indexTabProg = indexTabProg - 1;
		cpmTabViewProg.setActiveIndex(indexTabProg);
		setUltimoTabProg(Boolean.FALSE);

		try {
			if (indexTabProg.equals(-1)) {
				getSession().setAttribute(ConstantesGestorWeb.OBJ_PROGRAMA, this.programa);
				getSession().setAttribute(ConstantesGestorWeb.EDICION_PROGRAMA, Boolean.TRUE);
				getFacesContext().getExternalContext().redirect("nuevoProgramaCapacitacion.jsf?edicion=true");
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

	}

	/**
	 * Show Dialog
	 */
	public void muestraDialog() {
		cmpDialogNvoProg.setVisible(Boolean.TRUE);
	}

	/**
	 * Metodo para generar las estructuras Tematicas
	 */
	public void generaTemasPrograma() {
		if (ObjectUtils.isNotNull(numEstTematicas)) {

			temasXUnidad = new ArrayList<>();
			if (ObjectUtils.isNullOrEmpty(controlEstTematica.getTemas())) {
				controlEstTematica.setTemas(new ArrayList<ValoresEstTematicaDTO>());
			}

			// Genera numero de temas
			for (int i = 1; i <= numEstTematicas; i++) {
				temasXUnidad.add(i);
			}

			if (numEstTematicas > controlEstTematica.getTemas().size()) {
				int indexEstTemas = numEstTematicas - controlEstTematica.getTemas().size();
				for (int i = 1; i <= indexEstTemas; i++) {
					controlEstTematica.getTemas().add(new ValoresEstTematicaDTO());
				}
			} else {
				while (controlEstTematica.getTemas().size() > numEstTematicas) {
					controlEstTematica.getTemas().remove(controlEstTematica.getTemas().size() - 1);
				}
			}
		}
		generaUniDidacticasPrograma();
	}

	/**
	 *
	 */
	public void generaUniDidacticasPrograma() {

		if (ObjectUtils.isNotNull(numUniDidacticas) && ObjectUtils.isNotNull(numEstTematicas)) {

			if (numEstTematicas.compareTo(numUniDidacticas) > 0) {

				setCmpMultiSelectUd(Boolean.TRUE);
				controlEstTematica.setUnidDidSelect(new ArrayList<ValoresEstTematicaDTO>());

				for (int j = 1; j <= numUniDidacticas; j++) {
					controlEstTematica.getUnidDidSelect().add(new ValoresEstTematicaDTO());
				}

			} else {

				setCmpMultiSelectUd(Boolean.FALSE);
				List<ValoresEstTematicaDTO> unDidCmp = new ArrayList<>();
				for (int i = 1; i <= numUniDidacticas; i++) {
					unDidCmp.add(new ValoresEstTematicaDTO());
				}

				controlEstTematica.setUnidadesDidacticas(unDidCmp);
			}
		}

	}

	/**
	 * Valida la asociacion de unidades didacticas por temas
	 *
	 * @return
	 */
	private boolean validaUnidDidacticasXEstTem() {

		if (ObjectUtils.isNotNull(controlEstTematica)) {

			if (!ObjectUtils.isNullOrEmpty(controlEstTematica.getTemas())) {
				for (ValoresEstTematicaDTO tema : controlEstTematica.getTemas()) {
					if (ObjectUtils.isNullOrEmpty(tema.getNombreTema())) {
						agregarMsgWarn("Escriba el nombre de los temas", null);
						return false;
					}
				}
			}

			// Valida que el numero de temas asociados a las unidades didacticas
			// corresponda
			if (isCmpMultiSelectUd() && !ObjectUtils.isNullOrEmpty(controlEstTematica.getUnidDidSelect())) {
				int contSel = 0;
				for (ValoresEstTematicaDTO sel : controlEstTematica.getUnidDidSelect()) {
					if (!ObjectUtils.isNullOrEmpty(sel.getUnidadesTematicas())) {
						contSel += sel.getUnidadesTematicas().size();
					}
				}
				if (contSel != numEstTematicas) {
					return false;
				}
			}

			// Componente Select
			if (!isCmpMultiSelectUd() && !ObjectUtils.isNullOrEmpty(controlEstTematica.getUnidadesDidacticas())) {
				for (ValoresEstTematicaDTO sel : controlEstTematica.getUnidadesDidacticas()) {
					if (ObjectUtils.isNull(sel.getUnidadTematica())
							| sel.getUnidadTematica().equals(ConstantesGestorWeb.NUMERO_CERO)) {
						return false;
					}
				}
			}

		} else {
			return false;
		}

		return true;
	}

	/**
	 *
	 * @param e
	 */
	public void onChangeProgramasSeriados(ValueChangeEvent e) {
		if (ObjectUtils.isNotNull(e.getNewValue())) {
			idProgramaSeriado = (String) e.getNewValue();
			for (FichaDescProgramaDTO fichaDescProgramaDTO : programasList) {
				if (fichaDescProgramaDTO.getIdPrograma().equals(new Integer(idProgramaSeriado))) {
					programa.setProgramaAntecedente(fichaDescProgramaDTO);
					break;
				}
			}
		}
	}

	public void onChangeGetProgramaSeriado() {
		programasList = fecServiceFacade.getFichaDescProgramaService().consultarProgramasPorTCompYEjeCap(
				programa.getTipoCompetencia(), programa.getEjeCapacitacion(), getIdEstatusPrograma());
	}

	private Integer getIdEstatusPrograma() {
		Integer idEstatusPrograma = null;
		for (CatalogoComunDTO statusPrograma : catStatusPrograma) {
			if (EstatusProgramaEnum.FINAL.getEtiqueta().equals(statusPrograma.getNombre())) {
				idEstatusPrograma = statusPrograma.getId();
				break;
			}
		}
		return idEstatusPrograma;
	}

	public void onChangeInstitucionesCertificadoras(ValueChangeEvent e) {
		if (ObjectUtils.isNotNull(e.getNewValue())) {
			idInstitucionesCertificadoras = (String) e.getNewValue();
			for (CatalogoComunDTO catInstitucionCertificadora : catInstitucionesCertificadoras) {
				if (catInstitucionCertificadora.getId().equals(new Integer(idInstitucionesCertificadoras))) {
					programa.setIdInstitucionCertifica(catInstitucionCertificadora);
					break;
				}
			}
		}
	}

	/**
	 *
	 */
	public void guardaBorradorPrograma() {
		if (ObjectUtils.isNotNull(programa)) {

			switch (indexTabProg) {
			case TAB_DATOS_GENERALES:
				this.guardaBorradorDatosGenerales(programa);
				break;
			case TAB_ESTANDAR_COMPETENCIA:
				this.guardarBorradorEstCompetencia(programa);
				break;

			case TAB_ESTRUCTURA_DIDACTICA:
				if (!ObjectUtils.isNullOrCero(numUniDidacticas)) {
					this.generaVistaEstTematica();
					this.guardaBorradorEstTematica(programa, controlEstTematica, numUniDidacticas);

				}
				break;
			case TAB_UNIDADES_DIDACTICAS:

				FichaDescProgramaDTO resTranx = this.guardaBorradorUnidDidacticas(programa, controlEstTematica,
						numUniDidacticas, unidadesDidacticasProg);
				if (ObjectUtils.isNotNull(resTranx)) {
					this.programa = resTranx;
					setBtnNavegaSig(Boolean.TRUE);
					setHideMsgsGuardar(Boolean.TRUE);
					agregarMsgInfo("Guardado correcto.", null);
					this.generaVistaEstTematica();
				} else {
					agregarMsgError("Ocurrio un error al guardar el registro.", null);
				}

				break;

			default:
				break;
			}

			this.validaDatosProgramaRequeridos(programa);
		}
		RequestContext.getCurrentInstance().update("formNvoProg:tabViewFec");
		RequestContext.getCurrentInstance().execute("PF('dlgAlertProg').show();");
	}

	/**
	 *
	 * @param event
	 */
	public void onNodeSelectAreaResp(NodeSelectEvent event) {
		NodoDTO n = (NodoDTO) event.getTreeNode().getData();
		programa.setAreaResponsable(
				getFecServiceFacade().getOrgGubernamentalService().obtenerOrgGubernamentalPorId(n.getId()));
		setOpcAreaResp(Boolean.TRUE);

		if (ObjectUtils.isNullOrEmpty(nodoAreaRespSel)) {
			setOpcAreaResp(Boolean.FALSE);
		}
	}

	/**
	 *
	 * @param event
	 */
	public void onNodeSelectCompEspecif(NodeSelectEvent event) {

		TreeNode treeCmpEsp = event.getTreeNode();
		setConCompEspecificas(Boolean.TRUE);
		List<TreeNode> aux = Arrays.asList(competenciasEspecifProg);

		if (!containsObject(aux, treeCmpEsp)) {
			int index = competenciasEspecifProg.length;
			competenciasEspecifProg[index + 1] = treeCmpEsp;
		}
	}

	/**
	 *
	 * @param event
	 */
	public void onNodeUnSelectCompEspecif(NodeUnselectEvent event) {

		if (competenciasEspecifProg.length == ConstantesGestorWeb.NUMERO_CERO) {
			setConCompEspecificas(Boolean.FALSE);
		}

		TreeNode treeCmpEsp = event.getTreeNode();

		if (competenciasEspecifProg.length > ConstantesGestorWeb.NUMERO_CERO) {
			List<TreeNode> aux = Arrays.asList(competenciasEspecifProg);
			aux.remove(treeCmpEsp);
			competenciasEspecifProg = (TreeNode[]) aux.toArray();
		}

	}

	/**
	 *
	 * @param compEspecificas
	 * @param nodo
	 * @return
	 */
	private boolean containsObject(List<TreeNode> listArbol, TreeNode nodo) {
		if (ObjectUtils.isNotNull(listArbol)) {
			if (listArbol.contains(nodo)) {
				return true;
			}
		}
		return false;
	}

	/**
	 *
	 */
	public void onNodeSelectDirigidoA(NodeSelectEvent event) {

		NodoDTO n = (NodoDTO) event.getTreeNode().getData();
		if (isPersonalInterno()) {
			programa.setOrganismoGubernamental(
					getFecServiceFacade().getOrgGubernamentalService().obtenerOrgGubernamentalPorId(n.getId()));
		}

	}

	/**
	 * Guarda borrador datos generales
	 */
	private void guardaBorradorDatosGenerales(FichaDescProgramaDTO programa) {
		setHideMsgsGuardar(Boolean.TRUE);
		programa.setFechaRegistro(new Date());
		programa.setUsuarioModifico(getUsuarioEnSession().getIdPersona());

		programa.setRelProgramaResponsables(relProgResponsables);
		programa.setRelProgramaAutores(relProgAutores);
		ResultadoDTO<FichaDescProgramaDTO> preGuardado = fecServiceFacade.guardarDatosPrograma(programa);

		if (ObjectUtils.isNotNull(preGuardado) && preGuardado.getResultado().getValor()) {
			this.programa = preGuardado.getDto();
			bitacoraBean.guardarBitacora(idPersonaEnSesion(), "GUA_BOR_PRO", String.valueOf(programa.getIdPrograma()),
					requestActual(), TipoServicioEnum.LOCAL);
			setBtnNavegaSig(Boolean.TRUE);
			agregarMsgInfo("El Programa se guardo exitosamente.", null);
		} else {
			agregarMsgError("Ocurrio un error al guardar el registro.", null);
		}
	}

	public void validarEstructuraTematica() {
		if (ObjectUtils.isNullOrCero(numEstTematicas) && ObjectUtils.isNullOrCero(numUniDidacticas)) {
			this.guardaBorradorPrograma();

		} else {
			if (validaUnidDidacticasXEstTem()) {
				RequestContext.getCurrentInstance().execute("PF('mConfirmacionEstructura').show();");
			} else {
				agregarMsgError("Se deben asociar todas las unidades didacticas", null);
			}
		}
	}

	/**
	 * Guarda borrador de el estandar de competencia
	 */
	private void guardarBorradorEstCompetencia(FichaDescProgramaDTO programa) {
		try {

			programa.setUsuarioModifico(getUsuarioEnSession().getIdPersona());
			programa.setRelProgramaResponsables(relProgResponsables);
			programa.setRelProgramaAutores(relProgAutores);
			asignaCargaHorariaAPrograma(programa, isProgConEvaluacion(), calificacionMinimaAprobatoria);

			ResultadoDTO<FichaDescProgramaDTO> preGuardado = fecServiceFacade
					.guardarDatosProgramaYEstCompetencia(programa);

			if (ObjectUtils.isNotNull(preGuardado) && preGuardado.getResultado().getValor()) {
				this.programa = preGuardado.getDto();
				bitacoraBean.guardarBitacora(idPersonaEnSesion(), "GUA_BOR_PRO",
						String.valueOf(programa.getIdPrograma()), requestActual(), TipoServicioEnum.LOCAL);
				setBtnNavegaSig(Boolean.TRUE);
				setHideMsgsGuardar(Boolean.TRUE);
				agregarMsgInfo("El Programa se guardo exitosamente.", null);
			} else {
				agregarMsgError("Ocurrio un error al guardar el registro.", null);
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		}
	}

	/**
	 *
	 * @param programa
	 */
	private void asignaRelacionesPrograma(FichaDescProgramaDTO programa) {

		if (isEdicionPrograma()) {
			if (!ObjectUtils.isNullOrEmpty(relProgResponsables)) {
				relProgResponsables.stream().forEach(r -> {
					r.setFichaDescriptivaPrograma(programa);
				});
			}
			if (!ObjectUtils.isNullOrEmpty(relProgAutores)) {
				relProgAutores.stream().forEach(a -> {
					a.setFichaDescriptivaPrograma(programa);
				});
			}

		}

		if (ObjectUtils.isNullOrEmpty(programa.getRelProgramaResponsables())) {
			programa.setRelProgramaResponsables(relProgResponsables);
		}
		if (ObjectUtils.isNullOrEmpty(programa.getRelProgramaAutores())) {
			programa.setRelProgramaAutores(relProgAutores);
		}
		if (ObjectUtils.isNullOrEmpty(programa.getRelProgramaDuracion())) {
			asignaCargaHorariaAPrograma(programa, isProgConEvaluacion(), calificacionMinimaAprobatoria);
		}
	}

	/**
	 * Guarda Borrador en Tab Estructura Tematica
	 *
	 * @param programa
	 * @param controlEstTematica
	 * @param numUniDidacticas
	 */
	private void guardaBorradorEstTematica(FichaDescProgramaDTO programa, ControlEstTematicaDTO controlEstTematica,
			Integer numUniDidacticas) {

		try {
			programa.setUsuarioModifico(getUsuarioEnSession().getIdPersona());
			this.asignaRelacionesPrograma(programa);

			ResultadoDTO<FichaDescProgramaDTO> resultado = getFecServiceFacade()
					.guardaDatosProgramaYEstTematica(programa, controlEstTematica, numUniDidacticas);
			if (ObjectUtils.isNotNull(resultado) && resultado.getResultado().getValor()) {
				bitacoraBean.guardarBitacora(idPersonaEnSesion(), "GUA_BOR_PRO",
						String.valueOf(programa.getIdPrograma()), requestActual(), TipoServicioEnum.LOCAL);
				this.programa = resultado.getDto();
				setBtnNavegaSig(Boolean.TRUE);
				setHideMsgsGuardar(Boolean.TRUE);
				agregarMsgInfo("El Programa se guardo exitosamente.", null);

			} else {
				agregarMsgError("Ocurrio un error al guardar el registro.", null);
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

	}

	/**
	 *
	 * @param programa
	 * @param unidadesDidacticas
	 */
	private FichaDescProgramaDTO guardaBorradorUnidDidacticas(FichaDescProgramaDTO programa,
			ControlEstTematicaDTO controlEstTematica, Integer numUniDidacticas,
			List<CmpDinamicoUniDidacticaDTO> unidadesDidacticas) {

		programa.setUsuarioModifico(getUsuarioEnSession().getIdPersona());
		this.asignaRelacionesPrograma(programa);
		try {
			return this.guardaUnidDidacticasPrograma(programa, unidadesDidacticas, controlEstTematica,
					numUniDidacticas);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return null;
	}

	/**
	 *
	 * @param programa
	 * @param unidadesDidacticas
	 */
	private FichaDescProgramaDTO guardaUnidDidacticasPrograma(FichaDescProgramaDTO programa,
			List<CmpDinamicoUniDidacticaDTO> unidadesDidacticas, ControlEstTematicaDTO controlEstTematica,
			Integer numUniDidacticas) {

		try {
			if (ObjectUtils.isNotNull(unidadesDidacticas)) {

				ResultadoDTO<DetEstUniDidacticaDTO> resDelete = getFecServiceFacade()
						.eliminaRelacionesUnidadDidactica(programa);
				if (ObjectUtils.isNotNull(resDelete) && resDelete.getResultado().getValor()) {

					ResultadoDTO<FichaDescProgramaDTO> resultado = getFecServiceFacade()
							.guardaDatosProgramaYUnidadesDidacticas(programa, unidadesDidacticas, controlEstTematica,
									numUniDidacticas);
					if (ObjectUtils.isNotNull(resultado) && resultado.getResultado().getValor()) {
						bitacoraBean.guardarBitacora(idPersonaEnSesion(), "GUA_BOR_PRO",
								String.valueOf(programa.getIdPrograma()), requestActual(), TipoServicioEnum.LOCAL);
						setBtnNavegaSig(Boolean.TRUE);
						agregarMsgInfo("Guardado correcto de unidades didacticas.", null);
						return resultado.getDto();

					} else {
						agregarMsgError("Ocurrio un error al guardar unidades didacticas.", null);
					}
				}

			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	/**
	 *
	 */
	public void finalizarPrograma() {

		FichaDescProgramaDTO programaFinal = this.guardaBorradorUnidDidacticas(programa, controlEstTematica,
				numUniDidacticas, unidadesDidacticasProg);

		if (ObjectUtils.isNotNull(programaFinal)) {
			programaFinal.setCatStatusPrograma((getFecServiceFacade().getCatStatusProgramaService()
					.buscarPorId(EstatusProgramaEnum.FINAL.getId(), CatStatusPrograma.class)));
			this.asignaRelacionesPrograma(programaFinal);
			programaFinal.setIdentificadorFinal(
					getFecServiceFacade().generaClaveProgramaPlanSedesol(programaFinal.getIdPrograma()));

			// TODO: Consumir WS
			try {
				List<ParametroWSMoodleDTO> plataformas = fecServiceFacade.getParametroWSMoodleService().findAll();
				if (!ObjectUtils.isNullOrEmpty(plataformas)) {

					for (ParametroWSMoodleDTO ptf : plataformas) {

						CrearCategoria categoriaWS = new CrearCategoria(ptf);
						Categoria planCategoria = new Categoria();
						planCategoria.setIdnumber(programaFinal.getIdPrograma().toString());
						planCategoria.setName(programaFinal.getNombreTentativo());

						MallaCurricularDTO ejeCapProg = fecServiceFacade.getMallaCurricularService()
								.obtenerMallaCurricularPorId(programaFinal.getEjeCapacitacion());
						if (ObjectUtils.isNotNull(ejeCapProg)) {
							planCategoria.setParent(ejeCapProg.getIdCategoriaMdl());

						}

						ArrayList<Categoria> categorias = new ArrayList<Categoria>();
						categorias.add(planCategoria);

						List<RespuestaCrearCategorias> respuestasWS = categoriaWS.crearCategoria(categorias);
						if (ObjectUtils.isNotNull(respuestasWS)) {

							int idCategoriaMdl = respuestasWS.get(ConstantesGestor.PRIMER_ELEMENTO).getId();
							programaFinal.setIdCategoriaMdl(idCategoriaMdl);

							ResultadoDTO<FichaDescProgramaDTO> rxFinal = getFecServiceFacade()
									.getFichaDescProgramaService().actualizar(programaFinal);
							if (ObjectUtils.isNotNull(rxFinal) && rxFinal.getResultado().getValor()) {

								bitacoraBean.guardarBitacora(idPersonaEnSesion(), "FIN_PRO",
										String.valueOf(rxFinal.getDto().getIdPrograma()), requestActual(),
										TipoServicioEnum.LOCAL);

								RequestContext.getCurrentInstance().execute("PF('progFinalizado').show()");

								this.programa = rxFinal.getDto();
								// Envio de Notificaciones
								List<VariableMensajeOperacionDTO> variables = new ArrayList<>();

								variables.add(new VariableMensajeOperacionDTO("${nombre_programa}",
										this.programa.getNombreTentativo()));
								variables.add(new VariableMensajeOperacionDTO("${nombre_usuario}",
										this.programa.getCoordinadorAcademico().getNombreCompleto()));
								variables.add(new VariableMensajeOperacionDTO("${fecha_produccion}",
										DateUtils.convierteDateAString(this.programa.getFechaProduccion(),
												DateUtils.FORMATO_DDMMYYYY)));
								variables.add(new VariableMensajeOperacionDTO("${fecha_arranque}",
										DateUtils.convierteDateAString(this.programa.getFechaArranque(),
												DateUtils.FORMATO_DDMMYYYY)));

								fecServiceFacade.getNotificacionSistemaService().enviarNotificacion(
										ConstantesBitacora.NOTIFICACION_PROGAMA_FINALIZADO,
										this.programa.getCoordinadorAcademico().getIdPersona(), variables);

							} else {
								agregarMsgError("Ocurrio un error al guardar los datos", null);
							}

						}
					}
				}

			} catch (ErrorWS e) {
				logger.error(e.getMessage(), e);
			}
		}

	}

	/**
	 * NAVEGA A LA EDICION DEL PROGRAMA
	 *
	 * @return
	 */

	public String editaProgramaCapacitacion() {
		if (ObjectUtils.isNotNull(programaSeleccionado)) {

			// ViewScoped
			getSession().setAttribute(ConstantesGestorWeb.OBJ_PROGRAMA, programaSeleccionado);
			getSession().setAttribute(ConstantesGestorWeb.EDICION_PROGRAMA, Boolean.TRUE);

			// SessionScoped
			// this.programa = programaSeleccionado;
			// setEdicionPrograma(Boolean.TRUE);
			// this.iniciaRecursos();
			bitacoraBean.guardarBitacora(idPersonaEnSesion(), "VER_PRO",
					String.valueOf(programaSeleccionado.getIdPrograma()), requestActual(), TipoServicioEnum.LOCAL);
			return ConstantesGestorWeb.NAVEGA_FEC_EDITAR_PROGRAMA;
		}
		return null;
	}

	/**
	 * Accion de clonar un programa
	 */
	public void clonarProgramaCapacitacion() {
		if (datosValidosReqClonarPrograma()) {

			FichaDescProgramaDTO programaClon = SerializationUtils.clone(programaSeleccionado);
			programaClon.setNombreTentativo(nombreProgramaClon);
			programaClon.setUsuarioModifico(getUsuarioEnSession().getIdPersona());

			ResultadoDTO<FichaDescProgramaDTO> res = getFecServiceFacade().clonaProgramaCapacitacion(programaClon,
					clonarConEstYUD);

			if (ObjectUtils.isNotNull(res) && res.getResultado().getValor()) {
				bitacoraBean.guardarBitacora(idPersonaEnSesion(), "CLO_PRO", "", requestActual(),
						TipoServicioEnum.LOCAL);
				agregarMsgInfo("Se clono el programa exitosamente.", null);
				nombreProgramaClon = StringUtils.EMPTY;
			} else {
				agregarMsgError("Ocurrio un error al clonar el programa.", null);
			}
		}
	}

	/**
	 *
	 * @return
	 */
	private boolean datosValidosReqClonarPrograma() {

		if (ObjectUtils.isNull(programaSeleccionado)) {
			return false;
		}

		if (ObjectUtils.isNotNull(programaSeleccionado)
				&& !programaSeleccionado.getCatStatusPrograma().getId().equals(EstatusProgramaEnum.FINAL.getId())) {
			return false;
		}

		if (ObjectUtils.isNullOrEmpty(nombreProgramaClon)) {
			return false;
		}

		return true;
	}

	/**
	 * Accion para Descargar un Programa.
	 */
	public void descargarPrograma(FichaDescProgramaDTO programaSeleccionado) {
		if (!ObjectUtils.isNullOrEmpty(programaSeleccionado) || !ObjectUtils.isNullOrEmpty(programaSeleccionado)) {
			programaPDF = new DefaultStreamedContent();

			List<FichaDescProgramaDTO> FichaDescProgramaDTOs = new ArrayList<FichaDescProgramaDTO>();
			FichaDescProgramaDTOs.add(programaSeleccionado);

			ReporteConfig reporteConfig = new ReporteConfig();
			reporteConfig.setNombreReporte(programaSeleccionado.getNombreTentativo());
			reporteConfig.setTipoReporte(ReporteUtil.REPORTE_PDF);
			reporteConfig.setPathJasper(ConstantesReportesGestorWeb.PATH_JASPER_PLANES_PROGRAMAS);
			reporteConfig.setDatos(FichaDescProgramaDTOs);
			HashMap<String, Object> parametros = new HashMap<String, Object>();

			List<RelEstUnidadDidacticaDTO> estTemUnidad = fecServiceFacade.getEstructuraTematicaService()
					.obtieneRelEstUnidadDidPorPrograma(programaSeleccionado.getIdPrograma());

			if (!ObjectUtils.isNullOrEmpty(estTemUnidad)) {
				List<DetEstUniDidacticaDTO> unidades = new ArrayList<>();

				for (RelEstUnidadDidacticaDTO rud : estTemUnidad) {
					unidades.add(rud.getDetEstUnidadDidactica());
				}
				parametros.put("DS_UNIDADES_DIDACTICAS", unidades);
			}

			reporteConfig.setParametros(parametros);
			programaPDF = ReporteUtil.getStreamedContentOfBytes(ReporteUtil.generar(reporteConfig),
					ConstantesGestorWeb.HTTP_HEADER_CONTENTTYPE_PDF, programaSeleccionado.getNombreTentativo());
			bitacoraBean.guardarBitacora(idPersonaEnSesion(), "DES_PRO",
					String.valueOf(programaSeleccionado.getIdPrograma()), requestActual(), TipoServicioEnum.LOCAL);
		} else {
			return;
		}
	}

	/**
	 *
	 */
	public void cancelarProgramaCapacitacion() {
		if (ObjectUtils.isNotNull(programaSeleccionado)) {
			Integer idPrograma = programaSeleccionado.getIdPrograma();
			programaSeleccionado.setCatStatusPrograma(getFecServiceFacade().getCatStatusProgramaService()
					.buscarPorId(EstatusProgramaEnum.CANCELADO.getId(), CatStatusPrograma.class));
			getFecServiceFacade().getFichaDescProgramaService().actualizar(programaSeleccionado);
			bitacoraBean.guardarBitacora(idPersonaEnSesion(), "CAN_PRO", String.valueOf(idPrograma), requestActual(),
					TipoServicioEnum.LOCAL);

			this.recargaBusquedaProgramas();
		}
	}

	/**
	 *
	 */
	public void eliminaProgCapacitacion() {
		if (ObjectUtils.isNotNull(programaSeleccionado)
				&& programaSeleccionado.getCatStatusPrograma().getId().equals(EstatusProgramaEnum.CANCELADO.getId())) {
			logger.info("Eliminando el programa");
			programaSeleccionado.setCatStatusPrograma(getFecServiceFacade().getCatStatusProgramaService()
					.buscarPorId(EstatusProgramaEnum.ELIMINADO.getId(), CatStatusPrograma.class));
			getFecServiceFacade().getFichaDescProgramaService().actualizar(programaSeleccionado);
			this.recargaBusquedaProgramas();
		}
	}

	/**
	 *
	 */
	public void bloqueaProgCapacitacion() {
		if (ObjectUtils.isNotNull(programaSeleccionado)) {
			programaSeleccionado.setCatStatusPrograma(getFecServiceFacade().getCatStatusProgramaService()
					.buscarPorId(EstatusProgramaEnum.BLOQUEADO.getId(), CatStatusPrograma.class));
			getFecServiceFacade().getFichaDescProgramaService().actualizar(programaSeleccionado);
			bitacoraBean.guardarBitacora(idPersonaEnSesion(), "BLO_PRO",
					String.valueOf(programaSeleccionado.getIdPrograma()), requestActual(), TipoServicioEnum.LOCAL);
			this.recargaBusquedaProgramas();
		}
	}

	/**
	 *
	 */
	public void desbloqueaProgCapacitacion() {
		if (ObjectUtils.isNotNull(programaSeleccionado)
				&& programaSeleccionado.getCatStatusPrograma().getId().equals(EstatusProgramaEnum.BLOQUEADO.getId())) {

			programaSeleccionado.setCatStatusPrograma(getFecServiceFacade().getCatStatusProgramaService()
					.buscarPorId(EstatusProgramaEnum.BORRADOR.getId(), CatStatusPrograma.class));
			getFecServiceFacade().getFichaDescProgramaService().actualizar(programaSeleccionado);

			bitacoraBean.guardarBitacora(idPersonaEnSesion(), "DES_BLOQ_PRO",
					String.valueOf(programaSeleccionado.getIdPrograma()), requestActual(), TipoServicioEnum.LOCAL);

			this.recargaBusquedaProgramas();
		}
	}

	/**
	 *
	 */
	private void recargaBusquedaProgramas() {

		if (ObjectUtils.isNotNull(programa.getCatStatusPrograma())) {
			programasBusqueda = fecServiceFacade.getFichaDescProgramaService()
					.consultaProgramasPorEstatus(programa.getCatStatusPrograma().getId());
		} else {
			programasBusqueda = fecServiceFacade.getFichaDescProgramaService()
					.consultaProgramasPorEstatus(EstatusProgramaEnum.BORRADOR.getId());
		}
	}

	/**
	 *
	 * @param e
	 */
	public void onChangeEventoCap(ValueChangeEvent e) {
		if (ObjectUtils.isNotNull(e.getNewValue())) {
			programa.setCatTipoEventoEc(this.getValorDeCatalogo(catEventoCapacit, (Integer) e.getNewValue()));
		} else {
			programa.setCatTipoEventoEc(null);
		}
	}

	/**
	 *
	 * @param e
	 */
	public void onChangeNivelConoc(ValueChangeEvent e) {
		if (ObjectUtils.isNotNull(e.getNewValue())) {
			programa.setCatNivelConocimiento(this.getValorDeCatalogo(catNivelConocim, (Integer) e.getNewValue()));
		} else {
			programa.setCatNivelConocimiento(null);
		}
	}

	/**
	 *
	 */
	public void validaFinalizacionPrograma() {

		this.asignaRelacionesPrograma(programa);

		HashMap<Boolean, List<String>> validacionProg = this.validaProgramaFinal(programa);

		boolean progValido = true;
		List<String> msgs = null;

		for (java.util.Map.Entry<Boolean, List<String>> entry : validacionProg.entrySet()) {
			progValido = entry.getKey();
			msgs = entry.getValue();
		}

		if (progValido) {
			setFinalizaProg(Boolean.TRUE);
			setValidaFinPrograma(Boolean.TRUE);
		} else {

			if (ObjectUtils.isNotNull(msgs)) {
				for (String error : msgs) {
					agregarMsgError(error, null);
				}
			}
			setValidaFinPrograma(Boolean.FALSE);
			RequestContext.getCurrentInstance().execute("PF('dlgValidadorProg').show();");
		}
	}

	/**
	 * Valida los campos requeridos para finalizar el programa
	 *
	 * @param prog
	 * @return
	 */
	private HashMap<Boolean, List<String>> validaProgramaFinal(FichaDescProgramaDTO prog) {

		List<String> mensajes = new ArrayList<>();
		boolean isValido = Boolean.TRUE;
		if (ObjectUtils.isNull(prog)) {
			isValido = Boolean.FALSE;
		}

		if (ObjectUtils.isNull(prog.getTipoCompetencia())) {
			isValido = Boolean.FALSE;
			mensajes.add("Tipo de competencia es requerido");
		}
		if (ObjectUtils.isNull(prog.getEjeCapacitacion())) {
			isValido = Boolean.FALSE;
			mensajes.add("Eje de capacitación es requerido");
		}
		if (ObjectUtils.isNullOrEmpty(prog.getRelProgramaComEspecificas())) {
			isValido = Boolean.FALSE;
			mensajes.add("Competencias especificas requeridas");
		}
		if (ObjectUtils.isNull(prog.getNombreTentativo())) {
			isValido = Boolean.FALSE;
			mensajes.add("Nombre del programa es requerido");
		}
		if (ObjectUtils.isNull(prog.getCatNivelEnsenanzaPrograma())
				|| ObjectUtils.isNull(prog.getCatNivelEnsenanzaPrograma().getId())) {
			isValido = Boolean.FALSE;
			mensajes.add("Nivel de enseñanza es requerido");
		}
		if (ObjectUtils.isNull(prog.getCatModalidad()) || ObjectUtils.isNull(prog.getCatModalidad().getId())) {
			isValido = Boolean.FALSE;
			mensajes.add("Modalidad es requerido");
		}
		if (ObjectUtils.isNull(prog.getFechaVigInicial())) {
			isValido = Boolean.FALSE;
			mensajes.add("Fecha Vigencia inicial es requerido");
		}
		if (ObjectUtils.isNull(prog.getFechaVigFinal())) {
			isValido = Boolean.FALSE;
			mensajes.add("Fecha Vigencia final es requerido");
		}

		if (ObjectUtils.isNull(prog.getFechaSolicitud())) {
			isValido = Boolean.FALSE;
			mensajes.add("Fecha de solicitud es requerido");
		}
		if (ObjectUtils.isNull(prog.getFechaArranque())) {
			isValido = Boolean.FALSE;
			mensajes.add("Fecha de arranque es requerido");
		}
		if (ObjectUtils.isNull(prog.getFechaProduccion())) {
			isValido = Boolean.FALSE;
			mensajes.add("Fecha de produccion es requerido");
		}

		if (ObjectUtils.isNull(prog.getCatTipoEventoEc()) || ObjectUtils.isNull(prog.getCatTipoEventoEc().getId())) {
			isValido = Boolean.FALSE;
			mensajes.add("Evento capacitación es requerido");
		}
		if (ObjectUtils.isNull(prog.getCatNivelConocimiento())
				|| ObjectUtils.isNull(prog.getCatNivelConocimiento().getId())) {
			isValido = Boolean.FALSE;
			mensajes.add("Nivel conocimiento es requerido");
		}
		if (ObjectUtils.isNullOrEmpty(prog.getJustificacionAcademica())) {
			isValido = Boolean.FALSE;
			mensajes.add("Justificación académica es requerido");
		}
		if (ObjectUtils.isNull(prog.getAreaResponsable()) || ObjectUtils.isNull(prog.getAreaResponsable().getId())) {
			isValido = Boolean.FALSE;
			mensajes.add("Area responsable es requerido");
		}

		if (ObjectUtils.isNullOrEmpty(prog.getRelProgramaAutores())) {
			isValido = Boolean.FALSE;
			mensajes.add("Autores son requeridos");
		}

		if (ObjectUtils.isNull(prog.getRelProgramaResponsables())) {
			isValido = Boolean.FALSE;
			mensajes.add("Responsables son requeridos");
		}

		if (ObjectUtils.isNullOrEmpty(prog.getDescripcion())) {
			isValido = Boolean.FALSE;
			mensajes.add("Descripcion es requerido");
		}
		if (ObjectUtils.isNullOrEmpty(prog.getObjetivosGenerales())) {
			isValido = Boolean.FALSE;
			mensajes.add("Objetivos generales es requerido");
		}
		if (ObjectUtils.isNullOrEmpty(prog.getConocimietosPrevios())) {
			isValido = Boolean.FALSE;
			mensajes.add("Conocimientos previos es requerido");
		}
		if (ObjectUtils.isNullOrEmpty(prog.getPerfilIngreso())) {
			isValido = Boolean.FALSE;
			mensajes.add("Perfil Ingreso es requerido");
		}
		if (ObjectUtils.isNullOrEmpty(prog.getPerfilEgreso())) {
			isValido = Boolean.FALSE;
			mensajes.add("Perfil egreso es requerido");
		}
		if (!ObjectUtils.isNullOrEmpty(prog.getRelProgramaDuracion())) {
			for (RelProgDuracionDTO dur : prog.getRelProgramaDuracion()) {
				if (ObjectUtils.isNotNull(dur.getHoras()) && ObjectUtils.isNotNull(dur.getMinutos())
						&& Integer.parseInt(dur.getHoras()) == 0 && Integer.parseInt(dur.getMinutos()) == 0) {

					isValido = Boolean.FALSE;
					mensajes.add("Carga horaria es requerido. Establecer tiempos para teoría y práctica.");
					break;
				}
			}
		}
		if (ObjectUtils.isNull(prog.getPresentacion())) {
			isValido = Boolean.FALSE;
			mensajes.add("Presentacion es requerido");
		}

		if (ObjectUtils.isNull(prog.getPropositos())) {
			isValido = Boolean.FALSE;
			mensajes.add("Propositos es requerido");
		}
		if (ObjectUtils.isNull(prog.getObjetivosGenerales())) {
			isValido = Boolean.FALSE;
			mensajes.add("Objetivos generales es requerido");
		}
		if (ObjectUtils.isNull(prog.getMetodologia())) {
			isValido = Boolean.FALSE;
			mensajes.add("Metodología es requerido");
		}
		if (ObjectUtils.isNotNull(programa.getIdPrograma())) {

			EstructuraTematicaDTO estPrograma = getFecServiceFacade().getEstructuraTematicaService()
					.obtieneEstTematicaTemaPorPrograma(programa.getIdPrograma());
			if (ObjectUtils.isNotNull(estPrograma)) {

				// Valida Estructura tematica
				if (ObjectUtils.isNull(estPrograma.getDetEtematicaTemas().isEmpty())) {
					isValido = Boolean.FALSE;
					mensajes.add("Temas son requeridos");
				}
			}
		}

		// Valida estructura Tematica
		if (ObjectUtils.isNull(numEstTematicas) || numEstTematicas == 0) {
			isValido = Boolean.FALSE;
			mensajes.add("Estrucutura tematica es requerido");
		}

		if (ObjectUtils.isNull(numUniDidacticas) || numUniDidacticas == 0) {
			isValido = Boolean.FALSE;
			mensajes.add("Unidades didacticas es requerido");
		}

		if (ObjectUtils.isNotNull(controlEstTematica.getTemas())) {
			for (ValoresEstTematicaDTO valTema : controlEstTematica.getTemas()) {
				if (ObjectUtils.isNullOrEmpty(valTema.getNombreTema())) {
					isValido = Boolean.FALSE;
					mensajes.add("Nombre de temas es requerido");
					break;
				}
			}
		} else {
			isValido = Boolean.FALSE;
			mensajes.add("Nombre de temas es requerido");
		}

		// Valida Datos unidades didacticas
		if (!ObjectUtils.isNullOrEmpty(unidadesDidacticasProg)) {
			for (CmpDinamicoUniDidacticaDTO ud : unidadesDidacticasProg) {

				if (ObjectUtils.isNullOrEmpty(ud.getMaterialDidApoyoSel())) {
					isValido = Boolean.FALSE;
					mensajes.add(ud.getNombreUnidad().concat(" : Material didactico es requerido"));
				}

				if (ObjectUtils.isNullOrEmpty(ud.getTiposCompetenciaXEje())) {
					isValido = Boolean.FALSE;
					mensajes.add(ud.getNombreUnidad().concat(" : Tipos de competencia es requerido"));
				}

				if (ObjectUtils.isNotNull(ud.getDetUnidadDidactica())) {

					if (ObjectUtils.isNullOrEmpty(ud.getDetUnidadDidactica().getTituloUa())) {
						isValido = Boolean.FALSE;
						mensajes.add(ud.getNombreUnidad().concat(" : titulo es requerido"));
					}

					if (ObjectUtils.isNullOrEmpty(ud.getDetUnidadDidactica().getObjetivosEspecificos())) {
						isValido = Boolean.FALSE;
						mensajes.add(ud.getNombreUnidad().concat(" : objetivos especificos es requerido"));
					}

					if (ObjectUtils.isNullOrEmpty(ud.getDetUnidadDidactica().getSubtemasUdidactica())) {
						isValido = Boolean.FALSE;
						mensajes.add(ud.getNombreUnidad().concat(" : subtemas son requeridos"));
					}

					if (ObjectUtils.isNullOrEmpty(ud.getDetUnidadDidactica().getActividadesAprendizaje())) {
						isValido = Boolean.FALSE;
						mensajes.add(ud.getNombreUnidad().concat(" : Actividades aprendizaje es requerido"));
					}

					if (ObjectUtils.isNullOrEmpty(ud.getDetUnidadDidactica().getMediosRecursos())) {
						isValido = Boolean.FALSE;
						mensajes.add(ud.getNombreUnidad().concat(" : Medios y recursos es requerido"));
					}

					if (ObjectUtils.isNullOrEmpty(ud.getDetUnidadDidactica().getFuentesInformacion())) {
						isValido = Boolean.FALSE;
						mensajes.add(ud.getNombreUnidad().concat(" : Fuentes de informacion es requerido"));
					}

					if (ObjectUtils.isNullOrEmpty(ud.getDetUnidadDidactica().getEvaluacion())) {
						isValido = Boolean.FALSE;
						mensajes.add(ud.getNombreUnidad().concat(" : Evaluacion es requerido"));
					}

				} else {
					isValido = Boolean.FALSE;
					mensajes.add(ud.getNombreUnidad().concat("Faltan datos requeridos (*)"));
				}

			}
		}

		HashMap<Boolean, List<String>> res = new HashMap<>();
		res.put(isValido, mensajes);

		return res;
	}

	/**
	 *
	 */
	public void generaArbolEstPersonalExterno() {
		List<EstPersonalExternoDTO> estPersExt = fecServiceFacade.getEstPersonalExternoService()
				.obtenerEstPersonalExtPadres();
		arbolEstPersonalExt = new CheckboxTreeNode(new NodoDTO(1, "Personal Externo"), null);
		for (EstPersonalExternoDTO pe : estPersExt) {
			TreeNode peNodo = new CheckboxTreeNode(new NodoDTO(pe.getId(), pe.getNombre()), arbolEstPersonalExt);
			this.generaHijosEstExterna(peNodo, pe.getEstPersExternoHijos());
		}

	}

	private void generaHijosEstExterna(TreeNode nodoPadre, List<EstPersonalExternoDTO> estExtHijos) {
		if (!ObjectUtils.isNullOrEmpty(estExtHijos)) {
			for (EstPersonalExternoDTO ext : estExtHijos) {
				TreeNode ogHijo = new CheckboxTreeNode(new NodoDTO(ext.getId(), ext.getNombre()), nodoPadre);
				ogHijo.setSelectable(Boolean.TRUE);
				this.generaHijosEstExterna(ogHijo, ext.getEstPersExternoHijos());
			}
		}
	}

	/**
	 *
	 */
	private void generaArbolCompEspecificasXEje() {

		arbolCompEspecificas = new CheckboxTreeNode(new NodoDTO(1, "Competencias Especificas"), null);
		arbolCompEspecificas.setSelectable(Boolean.FALSE);
		for (CatalogoComunDTO ejeCap : ejesCapacitacion) {
			TreeNode ejeCapNodo = new CheckboxTreeNode(new NodoDTO(ejeCap.getId(), ejeCap.getNombre()),
					arbolCompEspecificas);
			ejeCapNodo.setSelectable(Boolean.FALSE);
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
					ejeComp.getCatCompetenciaEspecifica().getNombre(), ConstantesGestorWeb.TIPO_COMP_ESPECIFICA),
					nodoPadre);
			compEsp.setExpanded(Boolean.FALSE);
		}
	}

	public void generaArbolOrgGubernamental() {
		List<OrgGubernamentalDTO> orgGubs = orgGubernamentalService.obtenerOrgGubsPadres();
		arbolOrgGubernamental = new DefaultTreeNode();
		this.generaHijosOrgGubAreas(arbolOrgGubernamental, orgGubs);
		arbolOrgGubAreaResp = new DefaultTreeNode();
		this.generaHijosOrgGubAreas(arbolOrgGubAreaResp, orgGubs);
	}

	public void changeColorLabel(CmpDinamicoUniDidacticaDTO cmp) {

		if (cmp.getTiposCompetenciaXEje().size() > 0) {
			labelCompetencias = "Competencias seleccionadas";
			color = "verde";
			logger.info(color);
		} else {
			labelCompetencias = "Tipo de competencia";
			color = "blanco";

		}

	}

	/**
	 * Carga los datos previos del programa
	 *
	 * @param programa
	 */
	private void cargaDatosEdicionPrograma(FichaDescProgramaDTO programa) {

		if (!ObjectUtils.isNullOrEmpty(programa.getRelProgEstPersonalExterno())) {

			personalExtDirigoA = programa.getRelProgEstPersonalExterno().stream()
					.map(ce -> ce.getEstPersonalExterno().getNombre()).collect(Collectors.joining(","));

			int indexPersExt = 0;
			nodoEstPersonalExtSel = new TreeNode[programa.getRelProgEstPersonalExterno().size()];
			List<Integer> personalExt = programa.getRelProgEstPersonalExterno().stream()
					.map(c -> c.getIdEstPersonalExt()).collect(Collectors.toList());
			this.obtieneValorPersonalExterno(arbolEstPersonalExt, personalExt, nodoEstPersonalExtSel, indexPersExt);
			setOpcDirigidoA(Boolean.TRUE);
		}

		if (!ObjectUtils.isNullOrEmpty(programa.getRelProgramaComEspecificas())) {

			compEspecificasProg = programa.getRelProgramaComEspecificas().stream()
					.map(ce -> ce.getCatCompetenciaEspecifica().getNombre()).collect(Collectors.joining(","));

			int indexCmpsEsp = 0;
			competenciasEspecifProg = new TreeNode[programa.getRelProgramaComEspecificas().size()];
			List<Integer> comEspecifIds = programa.getRelProgramaComEspecificas().stream()
					.map(c -> c.getIdCompEspecifica()).collect(Collectors.toList());
			this.obtieneValorCompEspecifSelec(arbolCompEspecificas, comEspecifIds, competenciasEspecifProg,
					indexCmpsEsp);
			setConCompEspecificas(Boolean.TRUE);

			catTposCompetenciaXEje = new ArrayList<>();
			for (RelProgCompEspecificaDTO cmpEsp : programa.getRelProgramaComEspecificas()) {
				catTposCompetenciaXEje.add(cmpEsp.getCatCompetenciaEspecifica());
			}

		}

		if (ObjectUtils.isNotNull(programa.getAreaResponsable())
				&& ObjectUtils.isNotNull(programa.getAreaResponsable().getId())) {
			this.obtieneValorSelecFromTreeNode(arbolOrgGubAreaResp, programa.getAreaResponsable().getId(),
					nodoAreaRespSel);
			setOpcAreaResp(Boolean.TRUE);
		}

		if (ObjectUtils.isNotNull(programa.getRelProgramaAutores())) {
			autoresDatosGenerales = programa.getRelProgramaAutores().stream().map(e -> e.getAutores())
					.collect(Collectors.joining(","));
			relProgAutores = programa.getRelProgramaAutores();
		}

		if (ObjectUtils.isNotNull(programa.getRelProgramaResponsables())) {
			responsablesDatosGenerales = programa.getRelProgramaResponsables().stream().map(e -> e.getResponsables())
					.collect(Collectors.joining(","));
			relProgResponsables = programa.getRelProgramaResponsables();
		}

		if (ObjectUtils.isNotNull(programa.getIdInstitucionCertifica())) {
			idInstitucionesCertificadoras = programa.getIdInstitucionCertifica().getId().toString();
			setCertificar(Boolean.TRUE);
		}

		if (ObjectUtils.isNotNull(programa.getProgramaAntecedente())) {

			programasList = fecServiceFacade.getFichaDescProgramaService().consultarProgramasPorTCompYEjeCap(
					programa.getTipoCompetencia(), programa.getEjeCapacitacion(), getIdEstatusPrograma());

			idProgramaSeriado = programa.getProgramaAntecedente().getIdPrograma().toString();
			setSeriado(Boolean.TRUE);
		}

		if (ObjectUtils.isNotNull(programa.getCalificacionMinAprobatoria())) {
			calificacionMinimaAprobatoria = Integer.parseInt(programa.getCalificacionMinAprobatoria());
			setProgConEvaluacion(Boolean.TRUE);
		}

		if (ObjectUtils.isNotNull(programa.getRelProgramaDuracion())) {
			for (RelProgDuracionDTO pd : programa.getRelProgramaDuracion()) {
				switch (pd.getIdTpoCargaHoraria()) {
				case ConstantesGestorWeb.TPO_CARGA_HORARIA_TEORICA:
					relProgDuracionTeoria = pd;
					break;
				case ConstantesGestorWeb.TPO_CARGA_HORARIA_PRACTICA:
					relProgDuracionPractica = pd;
					break;
				case ConstantesGestorWeb.TPO_CARGA_HORARIA_EVALUACION:
					relProgDuracionEvaluacion = pd;
					setProgConEvaluacion(Boolean.TRUE);
					break;

				}
			}
		}

	}

	/**
	 * listener para cambio de personal
	 */
	public void changeTipoPersonal() {
		if (isPersonalInterno()) {
			setPersonalInterno(Boolean.FALSE);
		} else {
			setPersonalInterno(Boolean.TRUE);
		}
	}

	public void onChangeSwchEvaluacion() {
		if (isProgConEvaluacion()) {
			setProgConEvaluacion(Boolean.FALSE);
		} else {
			setProgConEvaluacion(Boolean.TRUE);
		}
	}

	public void limpiarNombreProgClon() {
		nombreProgramaClon = StringUtils.EMPTY;
	}
	// SETTERS & GETTERS

	/**
	 * @return the leyendaBusqueda
	 */
	public String getLeyendaBusqueda() {
		return leyendaBusqueda;
	}

	/**
	 * @param leyendaBusqueda
	 *            the leyendaBusqueda to set
	 */
	public void setLeyendaBusqueda(String leyendaBusqueda) {
		this.leyendaBusqueda = leyendaBusqueda;
	}

	/**
	 * @return the numEstTematicas
	 */
	public Integer getNumEstTematicas() {
		return numEstTematicas;
	}

	/**
	 * @param numEstTematicas
	 *            the numEstTematicas to set
	 */
	public void setNumEstTematicas(Integer numEstTematicas) {
		this.numEstTematicas = numEstTematicas;
	}

	/**
	 * @return the numUniDidacticas
	 */
	public Integer getNumUniDidacticas() {
		return numUniDidacticas;
	}

	/**
	 * @param numUniDidacticas
	 *            the numUniDidacticas to set
	 */
	public void setNumUniDidacticas(Integer numUniDidacticas) {
		this.numUniDidacticas = numUniDidacticas;
	}

	/**
	 * @return the catDirigidoA
	 */
	public List<CatalogoComunDTO> getCatDirigidoA() {
		return catDirigidoA;
	}

	/**
	 * @param catDirigidoA
	 *            the catDirigidoA to set
	 */
	public void setCatDirigidoA(List<CatalogoComunDTO> catDirigidoA) {
		this.catDirigidoA = catDirigidoA;
	}

	/**
	 * @return the tpoCompSelec
	 */
	public String getTpoCompSelec() {
		return tpoCompSelec;
	}

	/**
	 * @param tpoCompSelec
	 *            the tpoCompSelec to set
	 */
	public void setTpoCompSelec(String tpoCompSelec) {
		this.tpoCompSelec = tpoCompSelec;
	}

	/**
	 * @return the controlEstTematica
	 */
	public ControlEstTematicaDTO getControlEstTematica() {
		return controlEstTematica;
	}

	/**
	 * @param controlEstTematica
	 *            the controlEstTematica to set
	 */
	public void setControlEstTematica(ControlEstTematicaDTO controlEstTematica) {
		this.controlEstTematica = controlEstTematica;
	}

	/**
	 * @return the cmpMultiSelectUd
	 */
	public boolean isCmpMultiSelectUd() {
		return cmpMultiSelectUd;
	}

	/**
	 * @param cmpMultiSelectUd
	 *            the cmpMultiSelectUd to set
	 */
	public void setCmpMultiSelectUd(boolean cmpMultiSelectUd) {
		this.cmpMultiSelectUd = cmpMultiSelectUd;
	}

	/**
	 * @return the ejeCapacitSel
	 */
	public String getEjeCapacitSel() {
		return ejeCapacitSel;
	}

	/**
	 * @param ejeCapacitSel
	 *            the ejeCapacitSel to set
	 */
	public void setEjeCapacitSel(String ejeCapacitSel) {
		this.ejeCapacitSel = ejeCapacitSel;
	}

	/**
	 * @return the competenciasEspecifProg
	 */
	public TreeNode[] getCompetenciasEspecifProg() {
		return competenciasEspecifProg;
	}

	/**
	 * @param competenciasEspecifProg
	 *            the competenciasEspecifProg to set
	 */
	public void setCompetenciasEspecifProg(TreeNode[] competenciasEspecifProg) {
		this.competenciasEspecifProg = competenciasEspecifProg;
	}

	/**
	 * @return the fecServiceFacade
	 */
	public FECServiceFacade getFecServiceFacade() {
		return fecServiceFacade;
	}

	/**
	 * @param fecServiceFacade
	 *            the fecServiceFacade to set
	 */
	public void setFecServiceFacade(FECServiceFacade fecServiceFacade) {
		this.fecServiceFacade = fecServiceFacade;
	}

	/**
	 * @return the personalInterno
	 */
	public boolean isPersonalInterno() {
		return personalInterno;
	}

	/**
	 * @param personalInterno
	 *            the personalInterno to set
	 */
	public void setPersonalInterno(boolean personalInterno) {
		this.personalInterno = personalInterno;
	}

	/**
	 * @return the catStatusPrograma
	 */
	public List<CatalogoComunDTO> getCatStatusPrograma() {
		return catStatusPrograma;
	}

	/**
	 * @param catStatusPrograma
	 *            the catStatusPrograma to set
	 */
	public void setCatStatusPrograma(List<CatalogoComunDTO> catStatusPrograma) {
		this.catStatusPrograma = catStatusPrograma;
	}

	/**
	 * @return the programaSeleccionado
	 */
	public FichaDescProgramaDTO getProgramaSeleccionado() {
		return programaSeleccionado;
	}

	/**
	 * @param programaSeleccionado
	 *            the programaSeleccionado to set
	 */
	public void setProgramaSeleccionado(FichaDescProgramaDTO programaSeleccionado) {
		this.programaSeleccionado = programaSeleccionado;
	}

	/**
	 * @return the catModalidadPrograma
	 */
	public List<CatalogoComunDTO> getCatModalidadPrograma() {
		return catModalidadPrograma;
	}

	/**
	 * @return the arbolCompEspecificas
	 */
	public TreeNode getArbolCompEspecificas() {
		return arbolCompEspecificas;
	}

	/**
	 * @param arbolCompEspecificas
	 *            the arbolCompEspecificas to set
	 */
	public void setArbolCompEspecificas(TreeNode arbolCompEspecificas) {
		this.arbolCompEspecificas = arbolCompEspecificas;
	}

	/**
	 * @param catModalidadPrograma
	 *            the catModalidadPrograma to set
	 */
	public void setCatModalidadPrograma(List<CatalogoComunDTO> catModalidadPrograma) {
		this.catModalidadPrograma = catModalidadPrograma;
	}

	/**
	 * @return the catNivelEnsenanzaProg
	 */
	public List<CatalogoComunDTO> getCatNivelEnsenanzaProg() {
		return catNivelEnsenanzaProg;
	}

	/**
	 * @param catNivelEnsenanzaProg
	 *            the catNivelEnsenanzaProg to set
	 */
	public void setCatNivelEnsenanzaProg(List<CatalogoComunDTO> catNivelEnsenanzaProg) {
		this.catNivelEnsenanzaProg = catNivelEnsenanzaProg;
	}

	/**
	 * @return the programasBusqueda
	 */
	public List<FichaDescProgramaDTO> getProgramasBusqueda() {
		return programasBusqueda;
	}

	/**
	 * @param programasBusqueda
	 *            the programasBusqueda to set
	 */
	public void setProgramasBusqueda(List<FichaDescProgramaDTO> programasBusqueda) {
		this.programasBusqueda = programasBusqueda;
	}

	/**
	 * @return the programa
	 */
	public FichaDescProgramaDTO getPrograma() {
		return programa;
	}

	/**
	 * @param programa
	 *            the programa to set
	 */
	public void setPrograma(FichaDescProgramaDTO programa) {
		this.programa = programa;
	}

	/**
	 * @return the compEspecificasProg
	 */
	public String getCompEspecificasProg() {
		return compEspecificasProg;
	}

	/**
	 * @param compEspecificasProg
	 *            the compEspecificasProg to set
	 */
	public void setCompEspecificasProg(String compEspecificasProg) {
		this.compEspecificasProg = compEspecificasProg;
	}

	/**
	 * @return the catTpoComp
	 */
	public List<CatalogoComunDTO> getCatTpoComp() {
		return catTpoComp;
	}

	/**
	 * @param catTpoComp
	 *            the catTpoComp to set
	 */
	public void setCatTpoComp(List<CatalogoComunDTO> catTpoComp) {
		this.catTpoComp = catTpoComp;
	}

	/**
	 * @return the catEjeCapacit
	 */
	public List<CatalogoComunDTO> getCatEjeCapacit() {
		return catEjeCapacit;
	}

	/**
	 * @param catEjeCapacit
	 *            the catEjeCapacit to set
	 */
	public void setCatEjeCapacit(List<CatalogoComunDTO> catEjeCapacit) {
		this.catEjeCapacit = catEjeCapacit;
	}

	/**
	 * @return the nodoOrgGubernamentalSel
	 */
	public TreeNode getNodoOrgGubernamentalSel() {
		return nodoOrgGubernamentalSel;
	}

	/**
	 * @param nodoOrgGubernamentalSel
	 *            the nodoOrgGubernamentalSel to set
	 */
	public void setNodoOrgGubernamentalSel(TreeNode nodoOrgGubernamentalSel) {
		this.nodoOrgGubernamentalSel = nodoOrgGubernamentalSel;
	}

	/**
	 * @return the ejesCapacitacion
	 */
	public List<CatalogoComunDTO> getEjesCapacitacion() {
		return ejesCapacitacion;
	}

	/**
	 * @param ejesCapacitacion
	 *            the ejesCapacitacion to set
	 */
	public void setEjesCapacitacion(List<CatalogoComunDTO> ejesCapacitacion) {
		this.ejesCapacitacion = ejesCapacitacion;
	}

	/**
	 * @return the finalizaProg
	 */
	public boolean isFinalizaProg() {
		return finalizaProg;
	}

	/**
	 * @param finalizaProg
	 *            the finalizaProg to set
	 */
	public void setFinalizaProg(boolean finalizaProg) {
		this.finalizaProg = finalizaProg;
	}

	/**
	 * @return the catOrgGubs
	 */
	public List<OrgGubernamentalDTO> getCatOrgGubs() {
		return catOrgGubs;
	}

	/**
	 * @param catOrgGubs
	 *            the catOrgGubs to set
	 */
	public void setCatOrgGubs(List<OrgGubernamentalDTO> catOrgGubs) {
		this.catOrgGubs = catOrgGubs;
	}

	/**
	 * @return the catEventoCapacit
	 */
	public List<CatalogoComunDTO> getCatEventoCapacit() {
		return catEventoCapacit;
	}

	/**
	 * @param catEventoCapacit
	 *            the catEventoCapacit to set
	 */
	public void setCatEventoCapacit(List<CatalogoComunDTO> catEventoCapacit) {
		this.catEventoCapacit = catEventoCapacit;
	}

	/**
	 * @return the catNivelConocim
	 */
	public List<CatalogoComunDTO> getCatNivelConocim() {
		return catNivelConocim;
	}

	/**
	 * @param catNivelConocim
	 *            the catNivelConocim to set
	 */
	public void setCatNivelConocim(List<CatalogoComunDTO> catNivelConocim) {
		this.catNivelConocim = catNivelConocim;
	}

	/**
	 * @return the cmpDialogNvoProg
	 */
	public Dialog getCmpDialogNvoProg() {
		return cmpDialogNvoProg;
	}

	/**
	 * @param cmpDialogNvoProg
	 *            the cmpDialogNvoProg to set
	 */
	public void setCmpDialogNvoProg(Dialog cmpDialogNvoProg) {
		this.cmpDialogNvoProg = cmpDialogNvoProg;
	}

	/**
	 * @return the hideMsgsGuardar
	 */
	public boolean isHideMsgsGuardar() {
		return hideMsgsGuardar;
	}

	/**
	 * @param hideMsgsGuardar
	 *            the hideMsgsGuardar to set
	 */
	public void setHideMsgsGuardar(boolean hideMsgsGuardar) {
		this.hideMsgsGuardar = hideMsgsGuardar;
	}

	/**
	 * @return the indexTabProg
	 */
	public Integer getIndexTabProg() {
		return indexTabProg;
	}

	/**
	 * @param indexTabProg
	 *            the indexTabProg to set
	 */
	public void setIndexTabProg(Integer indexTabProg) {
		this.indexTabProg = indexTabProg;
	}

	/**
	 * @return the cpmTabViewProg
	 */
	public TabView getCpmTabViewProg() {
		return cpmTabViewProg;
	}

	/**
	 * @param cpmTabViewProg
	 *            the cpmTabViewProg to set
	 */
	public void setCpmTabViewProg(TabView cpmTabViewProg) {
		this.cpmTabViewProg = cpmTabViewProg;
	}

	/**
	 * @return the btnNavegaSig
	 */
	public boolean isBtnNavegaSig() {
		return btnNavegaSig;
	}

	/**
	 * @param btnNavegaSig
	 *            the btnNavegaSig to set
	 */
	public void setBtnNavegaSig(boolean btnNavegaSig) {
		this.btnNavegaSig = btnNavegaSig;
	}

	/**
	 * @return the arbolEstPersonalExt
	 */
	public TreeNode getArbolEstPersonalExt() {
		return arbolEstPersonalExt;
	}

	/**
	 * @return the arbolOrgGubAreaResp
	 */
	public TreeNode getArbolOrgGubAreaResp() {
		return arbolOrgGubAreaResp;
	}

	/**
	 * @param arbolOrgGubAreaResp
	 *            the arbolOrgGubAreaResp to set
	 */
	public void setArbolOrgGubAreaResp(TreeNode arbolOrgGubAreaResp) {
		this.arbolOrgGubAreaResp = arbolOrgGubAreaResp;
	}

	/**
	 * @param arbolEstPersonalExt
	 *            the arbolEstPersonalExt to set
	 */
	public void setArbolEstPersonalExt(TreeNode arbolEstPersonalExt) {
		this.arbolEstPersonalExt = arbolEstPersonalExt;
	}

	/**
	 * @return the pdfFECMedia
	 */
	public StreamedContent getPdfFECMedia() {
		return pdfFECMedia;
	}

	/**
	 * @param pdfFECMedia
	 *            the pdfFECMedia to set
	 */
	public void setPdfFECMedia(StreamedContent pdfFECMedia) {
		this.pdfFECMedia = pdfFECMedia;
	}

	/**
	 * @return the opcDirigidoA
	 */
	public boolean isOpcDirigidoA() {
		return opcDirigidoA;
	}

	/**
	 * @param opcDirigidoA
	 *            the opcDirigidoA to set
	 */
	public void setOpcDirigidoA(boolean opcDirigidoA) {
		this.opcDirigidoA = opcDirigidoA;
	}

	/**
	 * @return the nodoEstPersonalExtSel
	 */
	public TreeNode[] getNodoEstPersonalExtSel() {
		return nodoEstPersonalExtSel;
	}

	/**
	 * @param nodoEstPersonalExtSel
	 *            the nodoEstPersonalExtSel to set
	 */
	public void setNodoEstPersonalExtSel(TreeNode[] nodoEstPersonalExtSel) {
		this.nodoEstPersonalExtSel = nodoEstPersonalExtSel;
	}

	/**
	 * @return the catMaterialDidactico
	 */
	public List<CatalogoComunDTO> getCatMaterialDidactico() {
		return catMaterialDidactico;
	}

	/**
	 * @param catMaterialDidactico
	 *            the catMaterialDidactico to set
	 */
	public void setCatMaterialDidactico(List<CatalogoComunDTO> catMaterialDidactico) {
		this.catMaterialDidactico = catMaterialDidactico;
	}

	/**
	 * @return the nodoAreaRespSel
	 */
	public TreeNode getNodoAreaRespSel() {
		return nodoAreaRespSel;
	}

	/**
	 * @param nodoAreaRespSel
	 *            the nodoAreaRespSel to set
	 */
	public void setNodoAreaRespSel(TreeNode nodoAreaRespSel) {
		this.nodoAreaRespSel = nodoAreaRespSel;
	}

	/**
	 * @return the unidadesDidacticasProg
	 */
	public List<CmpDinamicoUniDidacticaDTO> getUnidadesDidacticasProg() {
		return unidadesDidacticasProg;
	}

	/**
	 * @param unidadesDidacticasProg
	 *            the unidadesDidacticasProg to set
	 */
	public void setUnidadesDidacticasProg(List<CmpDinamicoUniDidacticaDTO> unidadesDidacticasProg) {
		this.unidadesDidacticasProg = unidadesDidacticasProg;
	}

	/**
	 * @return the progConEvaluacion
	 */
	public boolean isProgConEvaluacion() {
		return progConEvaluacion;
	}

	/**
	 * @param progConEvaluacion
	 *            the progConEvaluacion to set
	 */
	public void setProgConEvaluacion(boolean progConEvaluacion) {
		this.progConEvaluacion = progConEvaluacion;
	}

	/**
	 * @return the temasXUnidad
	 */
	public List<Integer> getTemasXUnidad() {
		return temasXUnidad;
	}

	/**
	 * @param temasXUnidad
	 *            the temasXUnidad to set
	 */
	public void setTemasXUnidad(List<Integer> temasXUnidad) {
		this.temasXUnidad = temasXUnidad;
	}

	public boolean isSeriado() {
		return isSeriado;
	}

	public void setSeriado(boolean isSeriado) {
		this.isSeriado = isSeriado;
	}

	public boolean isCertificar() {
		return isCertificar;
	}

	public void setCertificar(boolean isCertificar) {
		this.isCertificar = isCertificar;
	}

	public int getIdDirigidoaStdComp() {
		return idDirigidoaStdComp;
	}

	public void setIdDirigidoaStdComp(int idDirigidoaStdComp) {
		this.idDirigidoaStdComp = idDirigidoaStdComp;
	}

	public List<CatalogoComunDTO> getCatInstitucionesCertificadoras() {
		return catInstitucionesCertificadoras;
	}

	public void setCatInstitucionesCertificadoras(List<CatalogoComunDTO> catInstitucionesCertificadoras) {
		this.catInstitucionesCertificadoras = catInstitucionesCertificadoras;
	}

	public String getIdInstitucionesCertificadoras() {
		return idInstitucionesCertificadoras;
	}

	public void setIdInstitucionesCertificadoras(String idInstitucionesCertificadoras) {
		this.idInstitucionesCertificadoras = idInstitucionesCertificadoras;
	}

	public List<FichaDescProgramaDTO> getProgramasList() {
		return programasList;
	}

	public void setProgramasList(List<FichaDescProgramaDTO> programasList) {
		this.programasList = programasList;
	}

	public Integer getCalificacionMinimaAprobatoria() {
		return calificacionMinimaAprobatoria;
	}

	public void setCalificacionMinimaAprobatoria(Integer calificacionMinimaAprobatoria) {
		this.calificacionMinimaAprobatoria = calificacionMinimaAprobatoria;
	}

	/**
	 * @return the numSubtemas
	 */
	public Integer getNumSubtemas() {
		if (ObjectUtils.isNull(numSubtemas)) {
			numSubtemas = new Integer(0);
		}
		return numSubtemas;
	}

	/**
	 * @param numSubtemas
	 *            the numSubtemas to set
	 */
	public void setNumSubtemas(Integer numSubtemas) {
		this.numSubtemas = numSubtemas;
	}

	/**
	 * @return the ultimoTabProg
	 */
	public boolean isUltimoTabProg() {
		return ultimoTabProg;
	}

	/**
	 * @param ultimoTabProg
	 *            the ultimoTabProg to set
	 */
	public void setUltimoTabProg(boolean ultimoTabProg) {
		this.ultimoTabProg = ultimoTabProg;
	}

	/**
	 * @return the opcAreaResp
	 */
	public boolean isOpcAreaResp() {
		return opcAreaResp;
	}

	/**
	 * @param opcAreaResp
	 *            the opcAreaResp to set
	 */
	public void setOpcAreaResp(boolean opcAreaResp) {
		this.opcAreaResp = opcAreaResp;
	}

	/**
	 * @return the catTposCompetenciaXEje
	 */
	public List<CompetenciaEspecificaDTO> getCatTposCompetenciaXEje() {
		return catTposCompetenciaXEje;
	}

	/**
	 * @param catTposCompetenciaXEje
	 *            the catTposCompetenciaXEje to set
	 */
	public void setCatTposCompetenciaXEje(List<CompetenciaEspecificaDTO> catTposCompetenciaXEje) {
		this.catTposCompetenciaXEje = catTposCompetenciaXEje;
	}

	/**
	 * @return the edicionPrograma
	 */
	public boolean isEdicionPrograma() {
		return edicionPrograma;
	}

	/**
	 * @param edicionPrograma
	 *            the edicionPrograma to set
	 */
	public void setEdicionPrograma(boolean edicionPrograma) {
		this.edicionPrograma = edicionPrograma;
	}

	/**
	 * @return the nombreProgramaClon
	 */
	public String getNombreProgramaClon() {
		return nombreProgramaClon;
	}

	/**
	 * @param nombreProgramaClon
	 *            the nombreProgramaClon to set
	 */
	public void setNombreProgramaClon(String nombreProgramaClon) {
		this.nombreProgramaClon = nombreProgramaClon;
	}

	/**
	 * @return the idProgramaSeriado
	 */
	public String getIdProgramaSeriado() {
		return idProgramaSeriado;
	}

	/**
	 * @param idProgramaSeriado
	 *            the idProgramaSeriado to set
	 */
	public void setIdProgramaSeriado(String idProgramaSeriado) {
		this.idProgramaSeriado = idProgramaSeriado;
	}

	public RelProgDuracionDTO getRelProgDuracionTeoria() {
		return relProgDuracionTeoria;
	}

	public void setRelProgDuracionTeoria(RelProgDuracionDTO relProgDuracionTeoria) {
		this.relProgDuracionTeoria = relProgDuracionTeoria;
	}

	public RelProgDuracionDTO getRelProgDuracionPractica() {
		return relProgDuracionPractica;
	}

	public void setRelProgDuracionPractica(RelProgDuracionDTO relProgDuracionPractica) {
		this.relProgDuracionPractica = relProgDuracionPractica;
	}

	public RelProgDuracionDTO getRelProgDuracionEvaluacion() {
		return relProgDuracionEvaluacion;
	}

	public void setRelProgDuracionEvaluacion(RelProgDuracionDTO relProgDuracionEvaluacion) {
		this.relProgDuracionEvaluacion = relProgDuracionEvaluacion;
	}

	public String getNombreTipoCompetencia() {
		return nombreTipoCompetencia;
	}

	public void setNombreTipoCompetencia(String nombreTipoCompetencia) {
		this.nombreTipoCompetencia = nombreTipoCompetencia;
	}

	/**
	 * @return the validaFinPrograma
	 */
	public boolean isValidaFinPrograma() {
		return validaFinPrograma;
	}

	/**
	 * @param validaFinPrograma
	 *            the validaFinPrograma to set
	 */
	public void setValidaFinPrograma(boolean validaFinPrograma) {
		this.validaFinPrograma = validaFinPrograma;
	}

	public String getNombreEjeCapacitacion() {
		return nombreEjeCapacitacion;
	}

	public void setNombreEjeCapacitacion(String nombreEjeCapacitacion) {
		this.nombreEjeCapacitacion = nombreEjeCapacitacion;
	}

	public String getResponsableDatosGenerales() {
		return responsableDatosGenerales;
	}

	public void setResponsableDatosGenerales(String responsableDatosGenerales) {
		this.responsableDatosGenerales = responsableDatosGenerales;
	}

	public String getResponsablesDatosGenerales() {
		return responsablesDatosGenerales;
	}

	public void setResponsablesDatosGenerales(String responsablesDatosGenerales) {
		this.responsablesDatosGenerales = responsablesDatosGenerales;
	}

	public String getAutorDatosGenerales() {
		return autorDatosGenerales;
	}

	public void setAutorDatosGenerales(String autorDatosGenerales) {
		this.autorDatosGenerales = autorDatosGenerales;
	}

	public String getAutoresDatosGenerales() {
		return autoresDatosGenerales;
	}

	public void setAutoresDatosGenerales(String autoresDatosGenerales) {
		this.autoresDatosGenerales = autoresDatosGenerales;
	}

	/**
	 * @return the arbolOrgGubernamental
	 */
	public TreeNode getArbolOrgGubernamental() {
		return arbolOrgGubernamental;
	}

	/**
	 * @param arbolOrgGubernamental
	 *            the arbolOrgGubernamental to set
	 */
	public void setArbolOrgGubernamental(TreeNode arbolOrgGubernamental) {
		this.arbolOrgGubernamental = arbolOrgGubernamental;
	}

	/**
	 * @return the clonarConEstYUD
	 */
	public boolean isClonarConEstYUD() {
		return clonarConEstYUD;
	}

	/**
	 * @param clonarConEstYUD
	 *            the clonarConEstYUD to set
	 */
	public void setClonarConEstYUD(boolean clonarConEstYUD) {
		this.clonarConEstYUD = clonarConEstYUD;
	}

	public StreamedContent getProgramaPDF() {
		return programaPDF;
	}

	public void setProgramaPDF(StreamedContent programaPDF) {
		this.programaPDF = programaPDF;
	}

	/**
	 * @return the conCompEspecificas
	 */
	public boolean isConCompEspecificas() {
		return conCompEspecificas;
	}

	/**
	 * @param conCompEspecificas
	 *            the conCompEspecificas to set
	 */
	public void setConCompEspecificas(boolean conCompEspecificas) {
		this.conCompEspecificas = conCompEspecificas;
	}

	/**
	 * @return the progConUnidDid
	 */
	public boolean isProgConUnidDid() {
		return progConUnidDid;
	}

	/**
	 * @param progConUnidDid
	 *            the progConUnidDid to set
	 */
	public void setProgConUnidDid(boolean progConUnidDid) {
		this.progConUnidDid = progConUnidDid;
	}

	/**
	 * @return the progConEstDid
	 */
	public boolean isProgConEstDid() {
		return progConEstDid;
	}

	/**
	 * @param progConEstDid
	 *            the progConEstDid to set
	 */
	public void setProgConEstDid(boolean progConEstDid) {
		this.progConEstDid = progConEstDid;
	}

	/**
	 * @return the personalExtDirigoA
	 */
	public String getPersonalExtDirigoA() {
		return personalExtDirigoA;
	}

	/**
	 * @param personalExtDirigoA
	 *            the personalExtDirigoA to set
	 */
	public void setPersonalExtDirigoA(String personalExtDirigoA) {
		this.personalExtDirigoA = personalExtDirigoA;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getLabelCompetencias() {
		return labelCompetencias;
	}

	public void setLabelCompetencias(String labelCompetencias) {
		this.labelCompetencias = labelCompetencias;
	}

	public OrgGubernamentalService getOrgGubernamentalService() {
		return orgGubernamentalService;
	}

	public void setOrgGubernamentalService(OrgGubernamentalService orgGubernamentalService) {
		this.orgGubernamentalService = orgGubernamentalService;
	}

	public BitacoraBean getBitacoraBean() {
		return bitacoraBean;
	}

	public void setBitacoraBean(BitacoraBean bitacoraBean) {
		this.bitacoraBean = bitacoraBean;
	}

	public Integer getNumEstTematicasAux() {
		return numEstTematicasAux;
	}

	public void setNumEstTematicasAux(Integer numEstTematicasAux) {
		this.numEstTematicasAux = numEstTematicasAux;
	}

	public Integer getNumUniDidacticasAux() {
		return numUniDidacticasAux;
	}

	public void setNumUniDidacticasAux(Integer numUniDidacticasAux) {
		this.numUniDidacticasAux = numUniDidacticasAux;
	}

}
