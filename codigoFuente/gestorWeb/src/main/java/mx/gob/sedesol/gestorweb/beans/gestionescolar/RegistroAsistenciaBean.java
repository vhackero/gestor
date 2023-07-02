package mx.gob.sedesol.gestorweb.beans.gestionescolar;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIOutput;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ValueChangeEvent;
import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.SelectEvent;

import mx.gob.sedesol.basegestor.commons.dto.admin.CatalogoComunDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.AsistenciaAuxDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.CargaAsistenciaDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.CatAsistenciaDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.EventoCapacitacionDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.GrupoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.RelAsistenciaDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.RelDiasEventoCapacitacionDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.RelGrupoParticipanteDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.ReponsableProduccionEcDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.FichaDescProgramaDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.MallaCurricularDTO;
import mx.gob.sedesol.basegestor.commons.utils.DateUtils;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.TipoServicioEnum;
import mx.gob.sedesol.basegestor.service.ServiceException;
import mx.gob.sedesol.basegestor.service.impl.gestionescolar.AsistenciaFacadeService;
import mx.gob.sedesol.basegestor.service.planesyprogramas.MallaCurricularService;
import mx.gob.sedesol.gestorweb.beans.acceso.BaseBean;
import mx.gob.sedesol.gestorweb.beans.administracion.BitacoraBean;
import mx.gob.sedesol.gestorweb.commons.constantes.ConstantesGestorWeb;
import mx.gob.sedesol.gestorweb.commons.dto.NodoeHijosDTO;
import mx.gob.sedesol.gestorweb.commons.dto.ReporteConfig;
import mx.gob.sedesol.gestorweb.commons.utils.ReporteAsistenciaUtil;
import mx.gob.sedesol.gestorweb.commons.utils.ReporteUtil;

@SessionScoped
@ManagedBean(name = "registroAsistenciaBean")
public class RegistroAsistenciaBean extends BaseBean {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger(RegistroAsistenciaBean.class);

	@ManagedProperty(value = "#{mallaCurricularService}")
	private MallaCurricularService mallaCurricularService;

	@ManagedProperty(value = "#{asistenciaFacadeService}")
	private AsistenciaFacadeService asistenciaFacadeService;

	@ManagedProperty("#{bitacoraBean}")
	private BitacoraBean bitacoraBean;

	private EventoCapacitacionDTO evento;

	private List<GrupoDTO> grupos;

	private GrupoDTO grupo;

	private List<RelGrupoParticipanteDTO> grupoParticipantes;

	private List<RelAsistenciaDTO> asistencias;

	private String duracionEvento;

	private Integer idEvento;
	private Integer idGrupo;

	private List<CatalogoComunDTO> catTpoComp;
	private List<CatalogoComunDTO> ejesCapacitacion;

	private NodoeHijosDTO estPlanSedesol;
	private CatalogoComunDTO modalidad;
	private CatalogoComunDTO ejeCapacitacion;
	private CatalogoComunDTO competencia;
	private List<CatAsistenciaDTO> catAsistencia;

	private List<RelDiasEventoCapacitacionDTO> diasEvento;
	private Integer numParticipantes;

	private List<CargaAsistenciaDTO> cargaAsistencias;

	private boolean cmdSalvarDisabled = true;

	private Date diaEvento;
	private String alumnoNombre;

	public void init() {
		idGrupo = 0;
		this.evento = asistenciaFacadeService.getEvento(idEvento);

		this.catAsistencia = asistenciaFacadeService.getCatAsistencia(this.idEvento);

		try {
			this.evento.setResponsableCoordinadorAcademico(asistenciaFacadeService.getRelReponsableProduccionEcService()
					.getResponsableDelEvento(idEvento).get(0));
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
			evento.setResponsableCoordinadorAcademico(new ReponsableProduccionEcDTO());
		}

		this.grupos = asistenciaFacadeService.getGruposByEvento(idEvento);

		this.evento.setFechaInicialFormat(DateUtils.convierteDateAString(this.evento.getFechaInicial(), "dd/MM/yyyy"));
		this.evento.setFechaFinalFormat(DateUtils.convierteDateAString(this.evento.getFechaFinal(), "dd/MM/yyyy"));

		FichaDescProgramaDTO programa = evento.getFichaDescriptivaPrograma();
		this.modalidad = programa.getCatModalidad();

		Integer idEjeCapacitacion = this.evento.getFichaDescriptivaPrograma().getEjeCapacitacion();
		Integer idTipoCompentencia = this.evento.getFichaDescriptivaPrograma().getTipoCompetencia();

		grupo = new GrupoDTO();
		grupo.setIdGrupo(0);

		this.generaEstructuraCatTpoCompetenciaPlan();
		this.generaCatEjesCapacitBusqueda();

		ejeCapacitacion = asistenciaFacadeService.getEjeCapacitacion(idEjeCapacitacion, ejesCapacitacion);

		for (CatalogoComunDTO catalogoComunDTO : catTpoComp) {

			if (catalogoComunDTO.getId() == idTipoCompentencia) {
				competencia = catalogoComunDTO;
			}
		}
		grupoParticipantes = new ArrayList<>();

	}

	/**
	 * Metodo que extraer los datos para mostrar el tipo competencia
	 */
	private void generaEstructuraCatTpoCompetenciaPlan() {

		List<NodoeHijosDTO> planes = new ArrayList<>();
		List<MallaCurricularDTO> mallas = new ArrayList<>();

		MallaCurricularDTO mallaSedesol = mallaCurricularService.obtenerMallaCurricularPorId(1);
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
	 * Metodo que sirve para extraer los ejes de capacitacion
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
	}

	/**
	 * Complementa al metdo generaEstructuraCatTpoCompetenciaPlan
	 */
	public void generaCatxNivel(List<MallaCurricularDTO> hijos, NodoeHijosDTO nodoGral, int nivel) {

		for (MallaCurricularDTO mint : hijos) {

			NodoeHijosDTO hijo = new NodoeHijosDTO();
			hijo.setIdNodo(mint.getId());
			hijo.setIdPadre(nodoGral.getIdNodo());
			hijo.setIdObjCurr(mint.getObjetoCurricular().getId());
			hijo.setNivel(nivel + 1);
			hijo.setNombre(mint.getNombre());
			nodoGral.getNodosHijos().add(hijo);

			if (!ObjectUtils.isNullOrEmpty(mint.getLstHijosMallaCurr())) {
				this.generaCatxNivel(mint.getLstHijosMallaCurr(), hijo, hijo.getNivel());
			}
			;
		}
	}

	/**
	 * Metodo cambio de grupo
	 */
	public void onChangeGrupo(ValueChangeEvent e) {

		this.duracionEvento = "";
		this.numParticipantes = 0;
		this.asistencias = null;
		this.diasEvento = null;
		this.asistencias = null;

		if (ObjectUtils.isNotNull(e.getNewValue())) {

			if (this.idGrupo == null || this.idGrupo == 0) {

				agregarMsgError2("grupo", "Debe seleccionar un grupo.", null);

				return;
			}
			limpiarDatosGrupo();
			consultarGrupoPartcipante();
		}

	}

	public String navegaRegistrarAsistenciasEventoCap() {
		init();
		return ConstantesGestorWeb.NAVEGA_REGISTRO_ASISTENCIAS;
	}

	public void limpiarDatosGrupo() {

		if (this.grupoParticipantes != null) {
			this.grupoParticipantes.clear();
		}

		if (this.asistencias != null) {
			this.asistencias.clear();
		}

		if (this.diasEvento != null) {
			this.diasEvento.clear();
		}

		this.duracionEvento = "";
		this.numParticipantes = 0;
		this.cmdSalvarDisabled = true;

	}

	public void onChangeGrupoAjax(AjaxBehaviorEvent event) {
		limpiarDatosGrupo();
		this.idGrupo = (Integer) ((UIOutput) event.getSource()).getValue();
		if (this.idGrupo == null || this.idGrupo == 0) {
			idGrupo = 0;
		} else {
			this.grupo = asistenciaFacadeService.getGrupoService().buscarGrupoPorId(this.idGrupo);
			consultarGrupoPartcipante();
		}
		
	}

	public void consultarGrupoPartcipante() {
		List<Integer> lisIdGrupoParticipante = new ArrayList<>();
		this.duracionEvento = "";
		this.numParticipantes = 0;
		this.asistencias = null;
		this.diasEvento = null;
		this.asistencias = null;

		this.grupoParticipantes = asistenciaFacadeService.getParticipantesByGrupo(idGrupo);

		if (!ObjectUtils.isNullOrEmpty(grupoParticipantes)) {

			this.numParticipantes = grupoParticipantes.size();

			if (this.numParticipantes > 0) {
				cmdSalvarDisabled = false;
			} else {
				cmdSalvarDisabled = true;
			}

			/**
			 * se genera una lista del ID_GRUPO_PARTICPANTE para generar un
			 * SELECT IN(lista) de la tabla de asistencias
			 */
			for (RelGrupoParticipanteDTO relGrupoParticipanteDTO : grupoParticipantes) {
				lisIdGrupoParticipante.add(relGrupoParticipanteDTO.getId());
			}

			/**
			 * Obtienen las asistencias de cada participante
			 */
			if (!ObjectUtils.isNullOrEmpty(lisIdGrupoParticipante)) {
				this.asistencias = asistenciaFacadeService.getAsistenciaByIdGrupoParticipante(lisIdGrupoParticipante);
			}

			this.diasEvento = asistenciaFacadeService.getDiasEventoByGrupo(idGrupo);

			/**
			 * Carga el arreglo de asistencias de cada participante
			 */
			asistenciaFacadeService.crearArregloAsistenciasXparticipante(this.diasEvento, grupoParticipantes,
					this.asistencias);

		} // fin grupoParticipante

		/**
		 * Obtiene los dias de capacitancion del grupo
		 */
		if (!ObjectUtils.isNullOrEmpty(diasEvento)) {
			this.duracionEvento = diasEvento.size() + diasEvento.size() != 1 ? " días." : " día.";
		}

		logger.info("----duracionEvento " + duracionEvento);
	}

	public void guardar() {
		ResultadoDTO res = null;

		imprimirLista();

		try {

			res = asistenciaFacadeService.guardarRelAsistencia(this.getUsuarioEnSession().getIdPersona(),
					grupoParticipantes);

			if (ObjectUtils.isNotNull(res) && res.getResultado().getValor()) {
				bitacoraBean.guardarBitacora(idPersonaEnSesion(), "REG_ASI_PAR", "", requestActual(),
						TipoServicioEnum.LOCAL);
				agregarMsgInfo2(ConstantesGestorWeb.TAG_VALIDACION_REGASISTENCIA, res.getMensaje(), null);

			} else {

				agregarMsgError2(ConstantesGestorWeb.TAG_VALIDACION_REGASISTENCIA, res.getMensaje(), null);
			}
		} catch (ServiceException e) {

			agregarMsgError2(ConstantesGestorWeb.TAG_VALIDACION_REGASISTENCIA, res.getMensaje(), null);
		}

		consultarGrupoPartcipante();

	}

	private void imprimirLista() {

		logger.info("*********imprimir Lista OUT******************************");
		logger.info("******************************************************");

		for (RelGrupoParticipanteDTO participante : this.grupoParticipantes) {
			logger.info("partipante ID " + participante.getPersona().getIdPersona());
			logger.info("partipante    " + participante.getPersona().getNombre() + " "
					+ participante.getPersona().getApellidoPaterno() + " "
					+ participante.getPersona().getApellidoMaterno());

			logger.info("*********LISTA DE ASISTENCIAS OUT *********");
			logger.info("  lista size  " + participante.getAsistencias());

			for (AsistenciaAuxDTO asistencia : participante.getAsistencias()) {
				logger.info("   ID         " + asistencia.getId());
				logger.info("   IdGpoPart  " + asistencia.getIdGrupoParticipante());
				logger.info("   ID dia     " + asistencia.getIdDiaCapacitacion());
				logger.info("   fecha      " + asistencia.getFechaEventoCapacitacion());
				logger.info("   idTipoAsis " + asistencia.getIdtipoAsistencia());
				logger.info("---------------------------------");
			}
		}
	}

	public void onCellEdit(CellEditEvent event) {
		logger.info("********************onCellEdit***************************");

		Object oldValue = event.getOldValue();
		logger.info("	oldValue " + oldValue);

		Object newValue = event.getNewValue();
		logger.info("	newValue " + newValue);

		int row = event.getRowIndex();
		logger.info("	row " + row);

		int idTipoAsistencia = (Integer) newValue;

		String columnHead = event.getColumn().getHeaderText().trim();

		logger.info(" column key " + event.getColumn().getHeaderText());

		RelGrupoParticipanteDTO participante = this.grupoParticipantes.get(row);

		for (AsistenciaAuxDTO asistencia : participante.getAsistencias()) {

			if (asistencia.getFechaEventoFormat().equals(columnHead)) {

				asistencia.setIdtipoAsistencia(idTipoAsistencia);
				asistencia.setTipoAsistencia(asistenciaFacadeService.getTipoAsistencia(idTipoAsistencia));

				logger.info("   ID dia      " + asistencia.getIdDiaCapacitacion());
				logger.info("   fecha       " + asistencia.getFechaEventoCapacitacion());
				logger.info("   fechaFormat " + asistencia.getFechaEventoFormat());
				logger.info("   idTipoAsis  " + asistencia.getIdtipoAsistencia());

				logger.info("---------------------------------");
			}
		}

		FacesContext context = FacesContext.getCurrentInstance();
		RelGrupoParticipanteDTO participanteDTO = context.getApplication().evaluateExpressionGet(context,
				"#{participante}", RelGrupoParticipanteDTO.class);

		logger.info("   participante " + participanteDTO.getId());
		logger.info("   participante " + participanteDTO.getPersona().getNombre());
		logger.info("   participante " + participanteDTO.getPersona().getApellidoPaterno());
		logger.info("   participante " + participanteDTO.getPersona().getApellidoMaterno());

	}

	public void buscarAlumno() {
		List<Integer> lisIdGrupoParticipante = new ArrayList<Integer>();

		logger.info("***************INICIO buscarAlumno*********************");
		logger.info("	idGrupo      " + this.idGrupo);
		logger.info("	alumnoNombre " + this.alumnoNombre);

		if (this.idGrupo == null || this.idGrupo == 0) {
			agregarMsgError2(ConstantesGestorWeb.TAG_VALIDACION_ALUMNONOMBRE, "Debe seleccionar un grupo", null);
			return;
		}

		this.grupoParticipantes = asistenciaFacadeService.buscarAlumno(this.idGrupo, this.alumnoNombre);

		if (!ObjectUtils.isNullOrEmpty(grupoParticipantes)) {

			if (grupoParticipantes.size() > 0) {
				cmdSalvarDisabled = false;
			} else {
				cmdSalvarDisabled = true;
			}

			/**
			 * se genera una lista del ID_GRUPO_PARTICPANTE para generar un
			 * SELECT IN(lista) de la tabla de asistencias
			 */
			for (RelGrupoParticipanteDTO relGrupoParticipanteDTO : grupoParticipantes) {
				lisIdGrupoParticipante.add(relGrupoParticipanteDTO.getId());
			}

			/**
			 * Se carga un consecutivo a la lista de participantes
			 *
			 */
			for (int x = 0; x < grupoParticipantes.size(); x++) {
				RelGrupoParticipanteDTO grupoParticiDTO = grupoParticipantes.get(x);
				grupoParticiDTO.setSecuencia(x + 1);
			}

			/**
			 * Obtienen las asistencias de cada participante
			 */
			this.asistencias = asistenciaFacadeService.getAsistenciaByIdGrupoParticipante(lisIdGrupoParticipante);

			/**
			 * Carga el arreglo de asistencias de cada participante
			 */
			asistenciaFacadeService.crearArregloAsistenciasXparticipante(this.diasEvento, grupoParticipantes,
					this.asistencias);

		} // fin grupoParticipante

		this.duracionEvento = diasEvento.size() + " Dias";

		this.alumnoNombre = "";

		logger.info("***************FIN buscarAlumno*********************");

	}

	public void agregarDiaEvento(SelectEvent event) {
		logger.info("*************agregarDiaEvento*****************");

		RelDiasEventoCapacitacionDTO diaEventoDTO = new RelDiasEventoCapacitacionDTO();

		diaEventoDTO.setGrupo(new GrupoDTO());

		Date date = (Date) event.getObject();
		logger.info("    date " + date);

		if (this.idGrupo == null || this.idGrupo == 0) {
			agregarMsgError2(ConstantesGestorWeb.TAG_VALIDACION_DIAEVENTO, "Debe seleccionar primero un grupo", null);
			return;

		}

		diaEventoDTO.setFechaEventoCapacitacion(date);
		diaEventoDTO.getGrupo().setIdGrupo(this.idGrupo);
		diaEventoDTO.setUsuarioModifico(super.getUsuarioEnSession().getIdPersona());
		diaEventoDTO.setFechaRegistro(new Date());
		diaEventoDTO.setFechaActualizacion(new Date());

		ResultadoDTO res = asistenciaFacadeService.agregarDiaEvento(diaEventoDTO);

		consultarGrupoPartcipante();

		if (ObjectUtils.isNotNull(res) && res.getResultado().getValor()) {
			bitacoraBean.guardarBitacora(idPersonaEnSesion(), "REG_DIA_EVT", "", requestActual(),
					TipoServicioEnum.LOCAL);
			agregarMsgInfo2(ConstantesGestorWeb.TAG_VALIDACION_DIAEVENTO, res.getMensaje(), null);

		} else {

			agregarMsgError2(ConstantesGestorWeb.TAG_VALIDACION_DIAEVENTO, "" + res.getMensaje(), null);
		}

	}

	public void agregarMsgInfo2(String id, String mensaje, String detalle) {
		FacesContext.getCurrentInstance().addMessage(id,
				new FacesMessage(FacesMessage.SEVERITY_INFO, mensaje, detalle));
	}

	public void agregarMsgError2(String id, String mensaje, String detalle) {
		FacesContext.getCurrentInstance().addMessage(id,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, mensaje, detalle));
	}

	public void getReporte(int opcion) {

		byte[] bytes = null;

		try {

			String XML = asistenciaFacadeService.getXML(this.idEvento);

			ReporteConfig reporteConfig = new ReporteConfig();
			reporteConfig.setDatos(null);
			reporteConfig.setNombreReporte("Plantilla_SISI");
			reporteConfig.setPathJasper("/resources/jasperReport/gestionEscolar/GrupoParticipantes.jasper");

			if (opcion == 1) {
				reporteConfig.setTipoReporte(ReporteUtil.REPORTE_PDF);
			} else {
				reporteConfig.setTipoReporte(ReporteUtil.REPORTE_EXCEL);
			}
			Map<String, Object> params = new HashMap<>();

			FacesContext fc = FacesContext.getCurrentInstance();
			ExternalContext ec = fc.getExternalContext();
			ServletContext servletContext = (ServletContext) ec.getContext();

			String path = servletContext.getRealPath("/");
			logger.info("*******************+path " + path);

			String rutaRecurso = reporteConfig.getPathJasper();
			String subReportDir = path + rutaRecurso.substring(0, rutaRecurso.lastIndexOf("/")) + "/";
			logger.info("   subReportDir " + subReportDir);
			params.put("P_SUBREPORT_DIR", subReportDir);
			reporteConfig.setParametros(params);

			ec.responseReset();

			if (opcion == 1) {
				ec.setResponseContentType("application/pdf");
				ec.setResponseHeader("Content-Disposition", "attachment; filename=\"AsistenciasGrupo.pdf\"");
			} else {
				ec.setResponseContentType("application/vnd.ms-excel");
				ec.setResponseHeader("Content-Disposition", "attachment; filename=\"AsistenciasGrupo.xls\"");
			}

			OutputStream output = ec.getResponseOutputStream();
			bytes = ReporteUtil.generarPorXML(reporteConfig, XML);

			output.write(bytes);
			output.flush();
			output.close();
			fc.responseComplete();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	public void getReporteExcel() {

		byte[] bytes = null;

		try {

			FacesContext fc = FacesContext.getCurrentInstance();
			ExternalContext ec = fc.getExternalContext();

			ec.responseReset();

			ec.setResponseContentType("application/vnd.ms-excel");
			ec.setResponseHeader("Content-Disposition", "attachment; filename=\"AsistenciasGrupo.xls\"");

			OutputStream output = ec.getResponseOutputStream();

			bytes = ReporteAsistenciaUtil.generaReporte(grupoParticipantes, diasEvento);

			output.write(bytes);
			output.flush();
			output.close();
			fc.responseComplete();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	public String getAlumnoNombre() {
		return alumnoNombre;
	}

	public void setAlumnoNombre(String alumnoNombre) {
		this.alumnoNombre = alumnoNombre;
	}

	public String regresar() {
		return "/views/private/gestionEscolar/busquedaEventoCapacitacion.xhtml?faces-redirect=true&&idEvento=";
	}

	public List<CatAsistenciaDTO> getCatAsistencia() {
		return catAsistencia;
	}

	public void setCatAsistencia(List<CatAsistenciaDTO> catAsistencia) {
		this.catAsistencia = catAsistencia;
	}

	public EventoCapacitacionDTO getEvento() {
		return evento;
	}

	public void setEvento(EventoCapacitacionDTO evento) {
		this.evento = evento;
	}

	public Integer getIdEvento() {
		return idEvento;
	}

	public void setIdEvento(Integer idEvento) {
		this.idEvento = idEvento;
	}

	public MallaCurricularService getMallaCurricularService() {
		return mallaCurricularService;
	}

	public void setMallaCurricularService(MallaCurricularService mallaCurricularService) {
		this.mallaCurricularService = mallaCurricularService;
	}

	public CatalogoComunDTO getModalidad() {
		return modalidad;
	}

	public void setModalidad(CatalogoComunDTO modalidad) {
		this.modalidad = modalidad;
	}

	public CatalogoComunDTO getEjeCapacitacion() {
		return ejeCapacitacion;
	}

	public void setEjeCapacitacion(CatalogoComunDTO ejeCapacitacion) {
		this.ejeCapacitacion = ejeCapacitacion;
	}

	public CatalogoComunDTO getCompetencia() {
		return competencia;
	}

	public void setCompetencia(CatalogoComunDTO competencia) {
		this.competencia = competencia;
	}

	public GrupoDTO getGrupo() {
		return grupo;
	}

	public void setGrupo(GrupoDTO grupo) {
		this.grupo = grupo;
	}

	public List<GrupoDTO> getGrupos() {
		return grupos;
	}

	public void setGrupos(List<GrupoDTO> grupos) {
		this.grupos = grupos;
	}

	public String getDuracionEvento() {
		return duracionEvento;
	}

	public void setDuracionEvento(String duracionEvento) {
		this.duracionEvento = duracionEvento;
	}

	public List<RelDiasEventoCapacitacionDTO> getDiasEvento() {
		return diasEvento;
	}

	public void setDiasEvento(List<RelDiasEventoCapacitacionDTO> diasEvento) {
		this.diasEvento = diasEvento;
	}

	public Integer getNumParticipantes() {
		return numParticipantes;
	}

	public void setNumParticipantes(Integer numParticipantes) {
		this.numParticipantes = numParticipantes;
	}

	public List<RelGrupoParticipanteDTO> getGrupoParticipantes() {
		return grupoParticipantes;
	}

	public void setGrupoParticipantes(List<RelGrupoParticipanteDTO> grupoParticipantes) {
		this.grupoParticipantes = grupoParticipantes;
	}

	public List<CargaAsistenciaDTO> getCargaAsistencias() {
		return cargaAsistencias;
	}

	public void setCargaAsistencias(List<CargaAsistenciaDTO> cargaAsistencias) {
		this.cargaAsistencias = cargaAsistencias;
	}

	public Date getDiaEvento() {
		return diaEvento;
	}

	public void setDiaEvento(Date diaEvento) {
		this.diaEvento = diaEvento;
	}

	public boolean getCmdSalvarDisabled() {
		return cmdSalvarDisabled;
	}

	public void setCmdSalvarDisabled(boolean cmdSalvarDisabled) {
		this.cmdSalvarDisabled = cmdSalvarDisabled;
	}

	public AsistenciaFacadeService getAsistenciaFacadeService() {
		return asistenciaFacadeService;
	}

	public void setAsistenciaFacadeService(AsistenciaFacadeService asistenciaFacadeService) {
		this.asistenciaFacadeService = asistenciaFacadeService;
	}

	/**
	 * @return the asistencias
	 */
	public List<RelAsistenciaDTO> getAsistencias() {
		return asistencias;
	}

	/**
	 * @param asistencias
	 *            the asistencias to set
	 */
	public void setAsistencias(List<RelAsistenciaDTO> asistencias) {
		this.asistencias = asistencias;
	}

	/**
	 * @return the idGrupo
	 */
	public Integer getIdGrupo() {
		return idGrupo;
	}

	/**
	 * @param idGrupo
	 *            the idGrupo to set
	 */
	public void setIdGrupo(Integer idGrupo) {
		this.idGrupo = idGrupo;
	}

	public BitacoraBean getBitacoraBean() {
		return bitacoraBean;
	}

	public void setBitacoraBean(BitacoraBean bitacoraBean) {
		this.bitacoraBean = bitacoraBean;
	}

}
