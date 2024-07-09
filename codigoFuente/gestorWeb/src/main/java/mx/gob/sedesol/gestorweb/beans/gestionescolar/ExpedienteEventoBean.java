package mx.gob.sedesol.gestorweb.beans.gestionescolar;

import mx.gob.sedesol.basegestor.commons.constantes.ConstantesGestor;
import mx.gob.sedesol.basegestor.commons.dto.admin.CatalogoComunDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.PlantillaDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.EventoCapacitacionDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.GrupoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.RelGrupoParticipanteDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.FichaDescProgramaDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.MallaCurricularDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.PlanDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.RelProgDuracionDTO;
import mx.gob.sedesol.basegestor.commons.utils.DateUtils;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.TipoDocumentoEnum;
import mx.gob.sedesol.basegestor.commons.utils.TipoServicioEnum;
import mx.gob.sedesol.basegestor.service.ParametroSistemaService;
import mx.gob.sedesol.basegestor.service.admin.PlantillaService;
import mx.gob.sedesol.basegestor.service.gestionescolar.EventoCapacitacionService;
import mx.gob.sedesol.basegestor.service.gestionescolar.GrupoParticipanteService;
import mx.gob.sedesol.basegestor.service.gestionescolar.GrupoService;
import mx.gob.sedesol.basegestor.service.impl.planesyprogramas.FECServiceFacade;
import mx.gob.sedesol.basegestor.service.planesyprogramas.FichaDescProgramaService;
import mx.gob.sedesol.basegestor.service.planesyprogramas.MallaCurricularService;
import mx.gob.sedesol.basegestor.service.planesyprogramas.PlanService;
import mx.gob.sedesol.gestorweb.beans.acceso.BaseBean;
import mx.gob.sedesol.gestorweb.beans.administracion.BitacoraBean;
import mx.gob.sedesol.gestorweb.commons.constantes.ConstantesGestorWeb;
import mx.gob.sedesol.gestorweb.commons.dto.NodoeHijosDTO;
import mx.gob.sedesol.gestorweb.commons.dto.ReporteConfig;
import mx.gob.sedesol.gestorweb.commons.utils.ReporteUtil;
import org.apache.commons.io.Charsets;
import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@SessionScoped
@ManagedBean
public class ExpedienteEventoBean extends BaseBean {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(ExpedienteEventoBean.class);

	@ManagedProperty("#{mallaCurricularService}")
	private MallaCurricularService mallaCurricularService;

	@ManagedProperty("#{fichaDescProgramaService}")
	private FichaDescProgramaService fichaDescProgramaService;

	@ManagedProperty("#{eventoCapacitacionService}")
	private EventoCapacitacionService eventoCapacitacionService;

	@ManagedProperty("#{grupoParticipanteService}")
	private GrupoParticipanteService grupoParticipanteService;

	@ManagedProperty("#{grupoService}")
	private GrupoService grupoService;

	@ManagedProperty("#{parametroSistemaService}")
	private ParametroSistemaService parametroSistemaService;

	@ManagedProperty("#{plantillaService}")
	private PlantillaService plantillaService;

	@ManagedProperty("#{bitacoraBean}")
	private BitacoraBean bitacoraBean;

	private List<CatalogoComunDTO> catalogoTipoComp;
	private List<CatalogoComunDTO> catalogoEjeCap;
	private List<FichaDescProgramaDTO> listaProgramas;
	private List<EventoCapacitacionDTO> listaEventos;
	private List<GrupoDTO> listaGrupos;
	private List<RelGrupoParticipanteDTO> alumnosConConstancia;
	private NodoeHijosDTO estructuraPlanSedesol;

// Nuevo
	private List<NodoeHijosDTO> nodos;
	@ManagedProperty("#{planService}")
	private PlanService planService;
	@ManagedProperty(value = "#{fecServiceFacade}")
	private FECServiceFacade fecServiceFacade;
	private List<CatalogoComunDTO> planes;
	private List<CatalogoComunDTO> listaTiposCompetencias;
	private NodoeHijosDTO estPlanSedesol;
	private Integer idPlan;
	private List<CatalogoComunDTO> catEstructuras;
	private List<CatalogoComunDTO> catSubEstructurasNivel1;
	private List<CatalogoComunDTO> catSubEstructurasNivel2;
	private List<CatalogoComunDTO> catSubEstructurasNivel3;
	private EventoCapacitacionDTO filtros;
	private Integer nivelMaximo = 1;

	////

	private String grupoNombre;
	private String eventoNombre;
	private String grup;

	private FichaDescProgramaDTO filtroPrograma;
	private Integer idPrograma;
	private Integer idEvento;
	private Integer idGrupo;
	private PlantillaDTO plantillaConstanciaAcreditacion;

	private PlantillaDTO plantillaConstancia;

	private StreamedContent constanciaPDF;
	private StreamedContent constanciasZip;

	public ExpedienteEventoBean() {
		filtroPrograma = new FichaDescProgramaDTO();
		filtroPrograma.setTipoCompetencia(0);
		filtroPrograma.setEjeCapacitacion(0);
		listaProgramas = new ArrayList<>();
		listaEventos = new ArrayList<>();
		listaGrupos = new ArrayList<>();
		idPrograma = new Integer(0);
		idEvento = new Integer(0);
		idGrupo = new Integer(0);
		alumnosConConstancia = new ArrayList<>();

		//Nuevo
		filtros = new EventoCapacitacionDTO();
	}

	@PostConstruct
	public void iniciarRecursos() {
		generaEstructuraTipoCompetencia();
		this.generaEstructuraCatTpoCompetenciaPlan();
	}

	public void limpiarFiltro() {
		filtroPrograma = new FichaDescProgramaDTO();
		filtroPrograma.setTipoCompetencia(0);
		filtroPrograma.setEjeCapacitacion(0);
		idPrograma = new Integer(0);
		idEvento = new Integer(0);
		idGrupo = new Integer(0);
		alumnosConConstancia = new ArrayList<>();
	}

	private void generaEstructuraTipoCompetencia() {
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
				generaCatxNivel(m.getLstHijosMallaCurr(), nodog, nodog.getNivel());
			}
			planes.add(nodog);
		}
		catalogoTipoComp = new ArrayList<>();
		// Genera el Catalogo Tipo de Competencia
		estructuraPlanSedesol = planes.get(ConstantesGestorWeb.INDICE_INICIAL);
		for (NodoeHijosDTO nh : estructuraPlanSedesol.getNodosHijos()) {
			CatalogoComunDTO cc = new CatalogoComunDTO();
			cc.setId(nh.getIdNodo());
			cc.setNombre(nh.getNombre());
			getCatalogoTipoComp().add(cc);
		}
	}

	private void generaEstructuraCatTpoCompetenciaPlan() {

		nodos = new ArrayList<>();
		List<MallaCurricularDTO> mallas = getFecServiceFacade().getMallaCurricularService().obtieneMallasCurricularesDisponibles();

		// List<MallaCurricularDTO> mallas =
		// getFecServiceFacade().getMallaCurricularService().obtieneMallasCurricularesDisponibles();
		// RN: Solo se presentara el plan de sedesol por el momento
		/*MallaCurricularDTO mallaSedesol = getFecServiceFacade().getMallaCurricularService()
				.obtenerMallaCurricularPorId(1);
		mallas.add(mallaSedesol);*/
		planes = new ArrayList<>();
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
			nodos.add(nodog);
			CatalogoComunDTO cat = new CatalogoComunDTO();
			cat.setId(nodog.getIdNodo());
			cat.setNombre(nodog.getNombre());
			planes.add(cat);
		}

		listaTiposCompetencias = new ArrayList<>();

		// Genera el Catalogo Tipo de Competencia
		estPlanSedesol = nodos.get(ConstantesGestorWeb.INDICE_INICIAL);
		for (NodoeHijosDTO nh : estPlanSedesol.getNodosHijos()) {
			CatalogoComunDTO cc = new CatalogoComunDTO();
			cc.setId(nh.getIdNodo());
			cc.setNombre(nh.getNombre());
			listaTiposCompetencias.add(cc);
		}
	}

	public void onChangeCatPlan(ValueChangeEvent e) {
		if (ObjectUtils.isNotNull(e.getNewValue())) {
			catEstructuras = this.generarEstructuras(nodos, Integer.parseInt(e.getNewValue().toString()));
			Integer idPlan =mallaCurricularService.buscarPorId(Integer.parseInt(e.getNewValue().toString())).getIdPlan();
			PlanDTO plandto =planService.buscarPorId(idPlan);
			filtros.getFichaDescriptivaPrograma().setPlan(plandto);
			//filtros.getFichaDescriptivaPrograma().setPlan(planService.buscarPorId(Integer.parseInt(e.getNewValue().toString())));
			catSubEstructurasNivel1 = new ArrayList<CatalogoComunDTO>();
			catSubEstructurasNivel2 = new ArrayList<CatalogoComunDTO>();
			catSubEstructurasNivel3 = new ArrayList<CatalogoComunDTO>();
			nivelMaximo = 0;
		}
	}

	public void onChangeCatEstructura(ValueChangeEvent e) {
		if (ObjectUtils.isNotNull(e.getNewValue())) {
			Integer idSubEstructura = Integer.parseInt(e.getNewValue().toString());
			filtros.getFichaDescriptivaPrograma().setEjeCapacitacion(idSubEstructura);
			catSubEstructurasNivel1 = this.generarSubEstructuras1(nodos, idSubEstructura);
			catSubEstructurasNivel2 = new ArrayList<CatalogoComunDTO>();
			catSubEstructurasNivel3 = new ArrayList<CatalogoComunDTO>();
			nivelMaximo = 1;
		}
	}
public void onChangeCatSubestructura1(ValueChangeEvent e) {
		if (ObjectUtils.isNotNull(e.getNewValue())) {
			Integer idSubEstructura = Integer.parseInt(e.getNewValue().toString());
			filtros.getFichaDescriptivaPrograma().setEjeCapacitacion(idSubEstructura);
			catSubEstructurasNivel2 = this.generarSubEstructuras2(nodos, idSubEstructura);
			catSubEstructurasNivel3 = new ArrayList<CatalogoComunDTO>();

			listaProgramas = fichaDescProgramaService.buscaProgramasPorCriterios(filtros.getFichaDescriptivaPrograma());


			nivelMaximo = 2;
		}
	}





	/**
	 * Metodo que genera el catalogo de Mallas curriculares por nivel de
	 * profudidad
	 *
	 * @param hijos
	 * @param nodoGral
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
		}
	}

	public void onChangeTipoCompetencia(ValueChangeEvent e) {
		if (ObjectUtils.isNotNull(e.getNewValue())) {
			Integer idTipoCompSel = (Integer) e.getNewValue();
			filtroPrograma.setTipoCompetencia(idTipoCompSel);
			catalogoEjeCap = new ArrayList<CatalogoComunDTO>();
			for (NodoeHijosDTO nh : estructuraPlanSedesol.getNodosHijos()) {
				if (nh.getIdNodo().equals(idTipoCompSel)) {
					for (NodoeHijosDTO nint : nh.getNodosHijos()) {
						CatalogoComunDTO cc = new CatalogoComunDTO();
						cc.setId(nint.getIdNodo());
						cc.setNombre(nint.getNombre());
						catalogoEjeCap.add(cc);
					}
				}
			}
			/* Para resetear valores en combos */
			filtroPrograma.setEjeCapacitacion(0);
			idPrograma = new Integer(0);
			idEvento = new Integer(0);
			idGrupo = new Integer(0);
			alumnosConConstancia = new ArrayList<>();
		}
	}

	public void onChangeEjeCapacitacion(ValueChangeEvent e) {
		if (ObjectUtils.isNotNull(e.getNewValue())) {
			Integer idEjeCapacitacion = (Integer) e.getNewValue();
			filtroPrograma.setEjeCapacitacion(idEjeCapacitacion);
			listaProgramas = fichaDescProgramaService.buscaProgramasPorCriterios(filtroPrograma);

			/* Para resetear valores en combos */
			idPrograma = new Integer(0);
			idEvento = new Integer(0);
			idGrupo = new Integer(0);
			alumnosConConstancia = new ArrayList<>();
		}
	}

	public void onProgramaChange() {
		listaEventos = getEventoCapacitacionService().obtenerEventosPorPrograma(idPrograma);

		/* Para resetear valores en combos */
		idEvento = new Integer(0);
		idGrupo = new Integer(0);
		alumnosConConstancia = new ArrayList<>();
	}

	public void onEventoChange() {
		listaGrupos = getGrupoService().getGruposByEvento(idEvento);
		/* Para resetear valores en combos */
		idGrupo = new Integer(0);
		alumnosConConstancia = new ArrayList<>();
	}

	public void buscarAlumnosAprobados() {
		try {
			
			List<RelGrupoParticipanteDTO> alumnosConConstancia2 = new ArrayList<>();			
			
			alumnosConConstancia2 = getGrupoParticipanteService().getAlumnosQueRecibieronConstPorIdGrupo(idGrupo);

			if (!alumnosConConstancia2.isEmpty()) {

					alumnosConConstancia = alumnosConConstancia2.stream().filter(RelGrupoParticipanteDTO -> RelGrupoParticipanteDTO.getCalifFinal() != null).collect(Collectors.toList());
					grupoNombre = alumnosConConstancia.get(0).getGrupo().getNombre() + " " + alumnosConConstancia.get(0).getGrupo().getEvento().getNombreEc();

					eventoNombre = grupoNombre.replace(' ', '_');
					grup = alumnosConConstancia.get(0).getGrupo().getNombre();
			}
			
			if (!alumnosConConstancia.isEmpty()) {
				bitacoraBean.guardarBitacora(idPersonaEnSesion(), "CON_EXP_ALM_GPO", String.valueOf(idGrupo),
						requestActual(), TipoServicioEnum.LOCAL);
			}
		} catch (Exception e) {
			agregarMsgError("Existe un valor nulo en la base de datos.", null);
		}
	}

	public String getGrup() {
		return grup;
	}

	public void setGrup(String grup) {
		this.grup = grup;
	}

	public String getEventoNombre() {
		return eventoNombre;
	}

	public void setEventoNombre(String eventoNombre) {
		this.eventoNombre = eventoNombre;
	}

	public String getGrupoNombre() {
		return grupoNombre;
	}

	public void setGrupoNombre(String grupoNombre) {
		this.grupoNombre = grupoNombre;
	}

	/***
	 * Verifica si hay una constancia seleccionada en plantillas de documentos
	 */
	private Boolean verificarPlantillaSeleccionada(RelGrupoParticipanteDTO alumnoSeleccionado) {
		obtenerTipoDeConstancia(alumnoSeleccionado);
		if (ObjectUtils.isNotNull(plantillaConstancia)) {
			return Boolean.TRUE;
		} else {
			return Boolean.FALSE;
		}
	}

	private PlantillaDTO obtenerTipoDeConstancia(RelGrupoParticipanteDTO alumnoSeleccionado) {
		double eventoCalifMinAprob = Double
				.valueOf(alumnoSeleccionado.getGrupo().getEvento().getCalificacionMinAprobatoria());
		double alumnoCalifFinal = alumnoSeleccionado.getCalifFinal() * 10;

		if (alumnoCalifFinal >= eventoCalifMinAprob) {
			plantillaConstancia = plantillaService
					.obtenerPlantillaPorTipoDocumento(TipoDocumentoEnum.CONSTANCIA_ACREDITACION);
		} else {
			plantillaConstancia = plantillaService
					.obtenerPlantillaPorTipoDocumento(TipoDocumentoEnum.CONSTANCIA_PARTICIPACION);
		}
		return plantillaConstancia;

	}

	public void verConstancia(RelGrupoParticipanteDTO alumnoSeleccionado) {
		if (verificarPlantillaSeleccionada(alumnoSeleccionado)) {
			try {
				ReporteConfig reporteConfig = obtenerParametrosJasperConstancia(alumnoSeleccionado,
						obtenerTipoDeConstancia(alumnoSeleccionado));
				constanciaPDF = ReporteUtil.getStreamedContentOfBytes(ReporteUtil.generar(reporteConfig),
						"application/pdf", "Constancia");
				// TODO Es necesario ejecutar el modal y actualizarlo desde el
				// ManagedBean, si se hace en la vista hay conflictos.
				RequestContext.getCurrentInstance().execute("PF('modalConstancia').show()");
				RequestContext.getCurrentInstance().update("constancia");
				bitacoraBean.guardarBitacora(idPersonaEnSesion(), "VER_CON_ALM",
						String.valueOf(alumnoSeleccionado.getPersona().getIdPersona()), requestActual(),
						TipoServicioEnum.LOCAL);
			} catch (Exception e) {
				agregarMsgError("Error al generar constancia.", null);
			}
		} else {
			agregarMsgInfo("No hay una plantilla predefinida para la constancia.", null);
		}

	}

	public void descargarConstancia(RelGrupoParticipanteDTO alumnoSeleccionado) {
		if (verificarPlantillaSeleccionada(alumnoSeleccionado)) {
			try {
				ReporteConfig reporteConfig = obtenerParametrosJasperConstancia(alumnoSeleccionado,
						obtenerTipoDeConstancia(alumnoSeleccionado));
				constanciaPDF = ReporteUtil.getStreamedContentOfBytes(ReporteUtil.generar(reporteConfig),
						"application/pdf", "Constancia");
				bitacoraBean.guardarBitacora(idPersonaEnSesion(), "DES_CON_ALM",
						String.valueOf(alumnoSeleccionado.getPersona().getIdPersona()), requestActual(),
						TipoServicioEnum.LOCAL);
			} catch (Exception e) {
				agregarMsgError("Error al generar constancia.", null);
			}
		} else {
			agregarMsgInfo("No hay una plantilla predefinida para la constancia.", null);
		}
	}

	@SuppressWarnings("deprecation")
	public void descargarTodasLasConstancias() {

		try {
			ByteArrayInputStream bis = new ByteArrayInputStream(zipBytes(alumnosConConstancia));
			bis.mark(0);
			constanciasZip = new DefaultStreamedContent(bis, "application/zip", "constancias.zip",
					Charsets.UTF_8.name());
			bis.reset();
			bis.close();
			bitacoraBean.guardarBitacora(idPersonaEnSesion(), "DES_TOD_CON_GPO", String.valueOf(idGrupo),
					requestActual(), TipoServicioEnum.LOCAL);
		} catch (Exception e) {
			agregarMsgError("Error al generar constancias.", null);
		}

	}

	private String obtenerNombrePDF(PlantillaDTO plantilla, RelGrupoParticipanteDTO alumno) {
		if (plantilla.getIdPlantilla() == TipoDocumentoEnum.CONSTANCIA_ACREDITACION.getValor()) {
			return "acre_" + alumno.getPersona().getNombreCompleto().replace(" ", "_");
		} else {
			return "part_" + alumno.getPersona().getNombreCompleto().replace(" ", "_");
		}
	}

	/**
	 * 
	 * @param alumnos
	 * @return
	 */
	private byte[] zipBytes(List<RelGrupoParticipanteDTO> alumnos) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ZipOutputStream zos = new ZipOutputStream(baos);
		try {
			for (RelGrupoParticipanteDTO alumno : alumnos) {
				PlantillaDTO plantilla = obtenerTipoDeConstancia(alumno);
				ReporteConfig reporteConfig = obtenerParametrosJasperConstancia(alumno,
						plantilla);
				String nombrePDF = obtenerNombrePDF(plantilla,alumno);
				ZipEntry entry = new ZipEntry(nombrePDF + ".pdf");
				zos.putNextEntry(entry);
				zos.write(ReporteUtil.generar(reporteConfig));
				zos.closeEntry();
			}
			zos.close();
			baos.close();
		} catch (Exception e) {
			logger.info("Error al generar zip" + e);
		}
		return baos.toByteArray();
	}

	private ReporteConfig obtenerParametrosJasperConstancia(RelGrupoParticipanteDTO rgp, PlantillaDTO plantilla) {

		String rutaFondoConstancia = FacesContext.getCurrentInstance().getExternalContext().getRequestScheme() + "://"
				+ FacesContext.getCurrentInstance().getExternalContext().getRequestServerName() + ":"
				+ FacesContext.getCurrentInstance().getExternalContext().getRequestServerPort()
				+ parametroSistemaService.obtenerParametro(ConstantesGestor.PARAMETRO_RUTA_UNDERTOW)
				+ parametroSistemaService.obtenerParametro(ConstantesGestor.PARAMETRO_RUTA_IMAGENES_DOCUMENTOS);
		String nombreAcreditado = rgp.getPersona().getNombreCompleto();
		String nombreCurso = rgp.getGrupo().getEvento().getNombreEc();
		String parrafo1 = insertaVariablesEnParrafo(rgp, plantilla.getParrafo1());
		String parrafo2 = insertaVariablesEnParrafo(rgp, plantilla.getParrafo2());
		String parrafo3 = insertaVariablesEnParrafo(rgp, plantilla.getParrafo3());
		String directorGeneral = parametroSistemaService.obtenerParametro(ConstantesGestor.NOMBRE_DIRECTOR_GENERAL);
		String imagen = new StringBuilder().append(rutaFondoConstancia).append(plantilla.getImagenFondo()).toString();

		HashMap<String, Object> params = new HashMap<>();

		params.put("P_NOMBRE_ACREDITADO", nombreAcreditado);
		params.put("P_NOMBRE_PROGRAMA", nombreCurso);
		params.put("P_PARRAFO1", parrafo1);
		params.put("P_PARRAFO2", parrafo2);
		params.put("P_PARRAFO3", parrafo3);
		params.put("P_DIRECTOR_GRAL", directorGeneral);
		params.put("P_IMAGEN", imagen);

		ReporteConfig reporteConfig = new ReporteConfig();
		reporteConfig.setDatos(null);
		reporteConfig.setNombreReporte("Constancia");
		if (plantilla.getTipoDocumento().intValue() == TipoDocumentoEnum.CONSTANCIA_ACREDITACION.getValor()) {
			reporteConfig.setPathJasper("/resources/jasperReport/gestionAprendizaje/constanciaAcreditacion.jasper");
		} else {
			reporteConfig.setPathJasper("/resources/jasperReport/gestionAprendizaje/constanciaParticipacion.jasper");
		}
		reporteConfig.setTipoReporte(ReporteUtil.REPORTE_PDF);
		reporteConfig.setParametros(params);

		return reporteConfig;
	}

	private String insertaVariablesEnParrafo(RelGrupoParticipanteDTO rgp, String parrafo) {

		String calificacion = String.valueOf(rgp.getCalifFinal() * 10);
		String modalidad = rgp.getGrupo().getEvento().getCatModalidadPlanPrograma().getNombre();
		String lugar = parametroSistemaService.obtenerParametro(ConstantesGestor.CIUDAD_CONSTANCIA);
		String fecha = DateUtils.formatoFechaConstancia(rgp.getGrupo().getEvento().getFechaFinal());
		String numHoras = calcularDuracion(
				rgp.getGrupo().getEvento().getFichaDescriptivaPrograma().getRelProgramaDuracion());

		String parrafoSalida = parrafo;
		parrafoSalida = parrafoSalida.replace("$_calificacion_", calificacion);
		parrafoSalida = parrafoSalida.replace("$_modalidad_", modalidad);
		parrafoSalida = parrafoSalida.replace("$_lugar_", lugar);
		parrafoSalida = parrafoSalida.replace("$_fecha_", fecha);
		parrafoSalida = parrafoSalida.replace("$_num_horas_", numHoras);

		/*
		 * Si el numero de horas es igual a 1, se ajusta el texto de horas a
		 * hora.
		 */
		if (numHoras.equals("1")) {
			parrafoSalida = parrafoSalida.replace("horas", "hora");
		}

		return parrafoSalida;
	}

	private String calcularDuracion(List<RelProgDuracionDTO> rel) {
		int numHoras = 0;
		int numMinutos = 0;
		for (RelProgDuracionDTO duracion : rel) {
			if (!ObjectUtils.isNullOrEmpty(duracion.getHoras())) {
				numHoras += Integer.parseInt(duracion.getHoras());
			}
			if (!ObjectUtils.isNullOrEmpty(duracion.getMinutos())) {
				numMinutos += Integer.parseInt(duracion.getMinutos());
			}
		}
		int minutosReales = numMinutos % 60;
		int horasRestantes = (int) numMinutos / 60;
		int horasReales = numHoras + horasRestantes;
		return String.valueOf(horasReales);
	}

	public MallaCurricularService getMallaCurricularService() {
		return mallaCurricularService;
	}

	public void setMallaCurricularService(MallaCurricularService mallaCurricularService) {
		this.mallaCurricularService = mallaCurricularService;
	}

	public NodoeHijosDTO getEstructuraPlanSedesol() {
		return estructuraPlanSedesol;
	}

	public void setEstructuraPlanSedesol(NodoeHijosDTO estructuraPlanSedesol) {
		this.estructuraPlanSedesol = estructuraPlanSedesol;
	}

	public List<CatalogoComunDTO> getCatalogoEjeCap() {
		return catalogoEjeCap;
	}

	public void setCatalogoEjeCap(List<CatalogoComunDTO> catalogoEjeCap) {
		this.catalogoEjeCap = catalogoEjeCap;
	}

	public List<CatalogoComunDTO> getCatalogoTipoComp() {
		return catalogoTipoComp;
	}

	public void setCatalogoTipoComp(List<CatalogoComunDTO> catalogoTipoComp) {
		this.catalogoTipoComp = catalogoTipoComp;
	}

	public FichaDescProgramaDTO getFiltroPrograma() {
		return filtroPrograma;
	}

	public void setFiltroPrograma(FichaDescProgramaDTO filtroPrograma) {
		this.filtroPrograma = filtroPrograma;
	}

	public Integer getIdPrograma() {
		return idPrograma;
	}

	public void setIdPrograma(Integer idPrograma) {
		this.idPrograma = idPrograma;
	}

	public Integer getIdEvento() {
		return idEvento;
	}

	public void setIdEvento(Integer idEvento) {
		this.idEvento = idEvento;
	}

	public List<FichaDescProgramaDTO> getListaProgramas() {
		return listaProgramas;
	}

	public void setListaProgramas(List<FichaDescProgramaDTO> listaProgramas) {
		this.listaProgramas = listaProgramas;
	}

	public List<EventoCapacitacionDTO> getListaEventos() {
		return listaEventos;
	}

	public void setListaEventos(List<EventoCapacitacionDTO> listaEventos) {
		this.listaEventos = listaEventos;
	}

	public FichaDescProgramaService getFichaDescProgramaService() {
		return fichaDescProgramaService;
	}

	public void setFichaDescProgramaService(FichaDescProgramaService fichaDescProgramaService) {
		this.fichaDescProgramaService = fichaDescProgramaService;
	}

	public Integer getIdGrupo() {
		return idGrupo;
	}

	public void setIdGrupo(Integer idGrupo) {
		this.idGrupo = idGrupo;
	}

	public List<GrupoDTO> getListaGrupos() {
		return listaGrupos;
	}

	public void setListaGrupos(List<GrupoDTO> listaGrupos) {
		this.listaGrupos = listaGrupos;
	}

	public List<RelGrupoParticipanteDTO> getAlumnosConConstancia() {
		return alumnosConConstancia;
	}

	public void setAlumnosConConstancia(List<RelGrupoParticipanteDTO> alumnosConConstancia) {
		this.alumnosConConstancia = alumnosConConstancia;
	}

	public GrupoService getGrupoService() {
		return grupoService;
	}

	public void setGrupoService(GrupoService grupoService) {
		this.grupoService = grupoService;
	}

	public GrupoParticipanteService getGrupoParticipanteService() {
		return grupoParticipanteService;
	}

	public void setGrupoParticipanteService(GrupoParticipanteService grupoParticipanteService) {
		this.grupoParticipanteService = grupoParticipanteService;
	}

	public EventoCapacitacionService getEventoCapacitacionService() {
		return eventoCapacitacionService;
	}

	public void setEventoCapacitacionService(EventoCapacitacionService eventoCapacitacionService) {
		this.eventoCapacitacionService = eventoCapacitacionService;
	}

	public ParametroSistemaService getParametroSistemaService() {
		return parametroSistemaService;
	}

	public void setParametroSistemaService(ParametroSistemaService parametroSistemaService) {
		this.parametroSistemaService = parametroSistemaService;
	}

	public StreamedContent getConstanciaPDF() {
		return constanciaPDF;
	}

	public void setConstanciaPDF(StreamedContent constanciaPDF) {
		this.constanciaPDF = constanciaPDF;
	}

	public PlantillaService getPlantillaService() {
		return plantillaService;
	}

	public void setPlantillaService(PlantillaService plantillaService) {
		this.plantillaService = plantillaService;
	}

	public PlantillaDTO getPlantillaConstanciaAcreditacion() {
		return plantillaConstanciaAcreditacion;
	}

	public void setPlantillaConstanciaAcreditacion(PlantillaDTO plantillaConstanciaAcreditacion) {
		this.plantillaConstanciaAcreditacion = plantillaConstanciaAcreditacion;
	}

	public StreamedContent getConstanciasZip() {
		return constanciasZip;
	}

	public void setConstanciasZip(StreamedContent constanciasZip) {
		this.constanciasZip = constanciasZip;
	}

	public BitacoraBean getBitacoraBean() {
		return bitacoraBean;
	}

	public void setBitacoraBean(BitacoraBean bitacoraBean) {
		this.bitacoraBean = bitacoraBean;
	}

	public PlantillaDTO getPlantillaConstancia() {
		return plantillaConstancia;
	}

	public void setPlantillaConstancia(PlantillaDTO plantillaConstancia) {
		this.plantillaConstancia = plantillaConstancia;
	}




	/////Nuevo
	public Integer getIdPlan() {
		return idPlan;
	}
	public void setIdPlan(Integer idPlan) {
		this.idPlan = idPlan;
	}

	public List<CatalogoComunDTO> getCatEstructuras() {
		return catEstructuras;
	}

	public void setCatEstructuras(List<CatalogoComunDTO> catEstructuras) {
		this.catEstructuras = catEstructuras;
	}

	public List<CatalogoComunDTO> getCatSubEstructurasNivel1() {
		return catSubEstructurasNivel1;
	}

	public void setCatSubEstructurasNivel1(List<CatalogoComunDTO> catSubEstructurasNivel1) {
		this.catSubEstructurasNivel1 = catSubEstructurasNivel1;
	}

	public List<CatalogoComunDTO> getCatSubEstructurasNivel2() {
		return catSubEstructurasNivel2;
	}

	public void setCatSubEstructurasNivel2(List<CatalogoComunDTO> catSubEstructurasNivel2) {
		this.catSubEstructurasNivel2 = catSubEstructurasNivel2;
	}

	public List<CatalogoComunDTO> getCatSubEstructurasNivel3() {
		return catSubEstructurasNivel3;
	}

	public void setCatSubEstructurasNivel3(List<CatalogoComunDTO> catSubEstructurasNivel3) {
		this.catSubEstructurasNivel3 = catSubEstructurasNivel3;
	}
	public List<NodoeHijosDTO> getNodos() {
		return nodos;
	}

	public void setNodos(List<NodoeHijosDTO> nodos) {
		this.nodos = nodos;
	}

	public PlanService getPlanService() {
		return planService;
	}

	public void setPlanService(PlanService planService) {
		this.planService = planService;
	}

	public EventoCapacitacionDTO getFiltros() {
		return filtros;
	}

	public void setFiltros(EventoCapacitacionDTO filtros) {
		this.filtros = filtros;
	}

}
