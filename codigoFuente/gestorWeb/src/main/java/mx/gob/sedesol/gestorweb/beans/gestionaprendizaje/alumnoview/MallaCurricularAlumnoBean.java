package mx.gob.sedesol.gestorweb.beans.gestionaprendizaje.alumnoview;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.text.Normalizer;
import java.util.HashSet;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.AsistenteInscripcionContextoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.ContextoAsistenteCurricularV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.FichaIntegralCasoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestion.aprendizaje.EventoConstanciaDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.InscripcionPersonaDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.InscripcionBajasDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.InscripcionMateriasCursadasDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.UnidadDecisionInscripcionDTO;
import mx.gob.sedesol.basegestor.commons.dto.inscripcion.InscripcionPreviaMateriasDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.MallaAlumnoProgramaDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.FichaDescProgramaDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.MallaCurricularDTO;
import mx.gob.sedesol.basegestor.commons.utils.InscripcionException;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.ObjetoCurricularEnum;
import mx.gob.sedesol.basegestor.service.gestionescolar.AsistenteInscripcionService;
import mx.gob.sedesol.basegestor.service.gestionescolar.GrupoParticipanteService;
import mx.gob.sedesol.basegestor.service.gestionescolar.InscripcionService;
import mx.gob.sedesol.basegestor.service.gestionescolar.v2.MallaCurricularV2Facade;
import mx.gob.sedesol.basegestor.service.inscripcion.InscripcionPreviaMateriasService;
import mx.gob.sedesol.basegestor.service.impl.planesyprogramas.FECServiceFacade;
import mx.gob.sedesol.gestorweb.beans.acceso.BaseBean;
import mx.gob.sedesol.gestorweb.beans.gestionaprendizaje.TrayectoriaAcademicaContextoBean;
import mx.gob.sedesol.gestorweb.commons.dto.MallaDiagramaNodoDTO;
import mx.gob.sedesol.gestorweb.commons.dto.MallaDiagramaTipoDTO;

@ManagedBean
@ViewScoped
public class MallaCurricularAlumnoBean extends BaseBean {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(MallaCurricularAlumnoBean.class);

	private static final int COL_WIDTH = 260;
	private static final int SEMESTRE_Y = 20;
	private static final int SEMESTRE_HEIGHT = 34;
	private static final int SEMESTRE_BLOQUE_GAP = 8;
	private static final int BLOQUE_BASE_HEIGHT = 34;
	private static final int PROGRAMA_BASE_HEIGHT = 60;
	private static final int PROGRAMA_GAP = 10;
	private static final int BLOQUE_GAP = 20;
	private static final int SEMESTRE_X_OFFSET = 20;
	private static final int BLOQUE_X_OFFSET = 0;
	private static final int PROGRAMA_X_OFFSET = 0;
	private static final int MAX_CHARS_PER_LINE = 18;
	private static final int LINE_HEIGHT = 16;
	private static final int EXTRA_TEXT_HEIGHT = 6;
	private static final String ID_SEMESTRE_PREFIX = "sem-";
	private static final String ID_BLOQUE_PREFIX = "blo-";
	private static final String ID_PROGRAMA_PREFIX = "pro-";
	private static final int NODE_WIDTH = 210;
	private static final int SEMESTRE_WIDTH = 270;
	private static final int BLOQUE_WIDTH = 210;
	private static final int PROGRAMA_WIDTH = 190;
	private static final int DIAGRAMA_PADDING = 30;
	private static final String[] CONEXION_COLORS = new String[] {
			"#2d2d2d", "#1f6feb", "#d97706", "#059669", "#7c3aed",
			"#dc2626", "#0f766e", "#b45309"
	};
	private static final String[] TIPOS_COLORS = new String[] {
			"#B8DC9F", "#AACAEF", "#EFD339"
	};
	private static final String ESTATUS_APROBADA = "Aprobada";
	private static final String ESTATUS_NO_ACREDITADA = "No acreditada";
	private static final String ESTATUS_NO_INSCRITA = "No inscrita";
	private static final String ESTATUS_EN_CURSO = "En curso";
	private static final String ESTATUS_BAJA = "Baja";
	private static final String ESTATUS_BLOQUEADA = "Bloqueada";
	private static final Map<String, String> ESTATUS_COLORS = new LinkedHashMap<>();
	private static final double CALIFICACION_NO_PRESENTADA = 666.0;
	private static final DecimalFormat CALIF_FORMAT = new DecimalFormat("0.##");
	private static final DecimalFormat PROMEDIO_FORMAT = new DecimalFormat("0.00", DecimalFormatSymbols.getInstance(Locale.US));

	static {
		ESTATUS_COLORS.put(ESTATUS_APROBADA, "#15A449");
		ESTATUS_COLORS.put(ESTATUS_NO_ACREDITADA, "#FF0000");
		ESTATUS_COLORS.put(ESTATUS_NO_INSCRITA, "#506172");
		ESTATUS_COLORS.put(ESTATUS_BAJA, "#ED9A55");
		ESTATUS_COLORS.put(ESTATUS_EN_CURSO, "#C19E00");
		ESTATUS_COLORS.put(ESTATUS_BLOQUEADA, "#000000");
	}

	@ManagedProperty(value = "#{fecServiceFacade}")
	private FECServiceFacade fecServiceFacade;

	@ManagedProperty(value = "#{inscripcionService}")
	private InscripcionService inscripcionService;

	@ManagedProperty(value = "#{inscripcionPreviaMateriasService}")
	private InscripcionPreviaMateriasService inscripcionPreviaMateriasService;

	@ManagedProperty(value = "#{grupoParticipanteService}")
	private GrupoParticipanteService grupoParticipanteService;

	@ManagedProperty(value = "#{asistenteInscripcionServiceImpl}")
	private AsistenteInscripcionService asistenteInscripcionService;

	@ManagedProperty(value = "#{trayectoriaAcademicaContextoBean}")
	private TrayectoriaAcademicaContextoBean trayectoriaAcademicaContextoBean;

	@ManagedProperty(value = "#{mallaCurricularV2Facade}")
	private MallaCurricularV2Facade mallaCurricularV2Facade;

	private List<MallaDiagramaNodoDTO> nodos;
	private int diagramWidth;
	private int diagramHeight;
	private String planNombre;
	private Long planId;
	private List<MallaDiagramaTipoDTO> tiposPrograma;
	private List<MallaDiagramaTipoDTO> estatusPrograma;
	private Map<String, Integer> creditosAprobadosPorTipo;
	private Map<String, Integer> creditosPlanPorTipo;
	private int creditosAprobadosTotal;
	private int creditosPlanTotal;
	private double sumaCalificaciones;
	private int totalCalificaciones;
	private Set<String> asignaturasEnCurso;
	private Map<String, Set<String>> ubicacionesProgramasEnCurso;
	private Map<String, Set<String>> ubicacionesOptativasHistoricas;
	private Map<String, Set<String>> ubicacionesOptativasEnCurso;
	private Set<Integer> programasConBaja;
	private Integer semestreEnCurso;
	private Integer bloqueEnCurso;
	private String estatusEstudiante;
	private List<SemestreTablaDTO> semestresTabla;
	private Long idPersonaObjetivo;
	private boolean vistaGestor;
	private String nombrePersonaObjetivo;
	private String matriculaPersonaObjetivo;
	private AsistenteInscripcionContextoDTO contextoAsistente;
	private FichaIntegralCasoDTO fichaIntegralV2;
	private boolean intentoCargaFichaIntegralV2;
	private Map<Long, UnidadDecisionInscripcionDTO> unidadesAsistentePorPrograma;

	@PostConstruct
	public void init() {
		nodos = new ArrayList<>();
		tiposPrograma = new ArrayList<>();
		estatusPrograma = new ArrayList<>();
		creditosAprobadosPorTipo = new LinkedHashMap<>();
		creditosPlanPorTipo = new LinkedHashMap<>();
		creditosAprobadosTotal = 0;
		creditosPlanTotal = 0;
		sumaCalificaciones = 0d;
		totalCalificaciones = 0;
		asignaturasEnCurso = new HashSet<>();
		ubicacionesProgramasEnCurso = new HashMap<>();
		ubicacionesOptativasHistoricas = new HashMap<>();
		ubicacionesOptativasEnCurso = new HashMap<>();
		programasConBaja = new HashSet<>();
		semestreEnCurso = null;
		bloqueEnCurso = null;
		estatusEstudiante = "Regular";
		semestresTabla = new ArrayList<>();
		unidadesAsistentePorPrograma = new HashMap<>();

		Long idPersona = trayectoriaAcademicaContextoBean.resolverIdPersonaObjetivo(idPersonaEnSesion());
		idPersonaObjetivo = idPersona;
		vistaGestor = trayectoriaAcademicaContextoBean.isVistaGestor();
		nombrePersonaObjetivo = trayectoriaAcademicaContextoBean.resolverNombreObjetivo(getUsuarioEnSession().getUsuario());
		matriculaPersonaObjetivo = trayectoriaAcademicaContextoBean.resolverMatriculaObjetivo(getUsuarioEnSession().getUsuario());
		cargarContextoAsistente(idPersona);
		InscripcionPersonaDTO inscripcion = inscripcionService.obtenerInscripcionPorPersona(String.valueOf(idPersona));
		if (inscripcion == null || inscripcion.getIdPlan() == null) {
			logger.warn("No se encontro plan de estudios para el alumno en sesion.");
			return;
		}
		planId = inscripcion.getIdPlan();
		planNombre = inscripcion.getPlan();
		cargarFichaIntegralV2();

		MallaCurricularDTO raiz = fecServiceFacade.getMallaCurricularService()
				.obtenerMallaCurricularPorIdPlan(planId.intValue());
		if (ObjectUtils.isNull(raiz)) {
			logger.warn("No se encontro malla curricular para el plan seleccionado.");
			return;
		}

		cargarInscripcionesEnCurso(idPersona);
		cargarUbicacionesHistoricasOptativas(idPersona);
		ajustarSemestreReferenciaPorTrayectoria(idPersona);
		construirModelo(raiz, idPersona);
	}

	private void cargarContextoAsistente(Long idPersona) {
		if (asistenteInscripcionService == null || idPersona == null) {
			return;
		}
		try {
			contextoAsistente = asistenteInscripcionService.obtenerContextoAsistido(idPersona);
			if (contextoAsistente == null || ObjectUtils.isNullOrEmpty(contextoAsistente.getUnidades())) {
				return;
			}
			for (UnidadDecisionInscripcionDTO unidad : contextoAsistente.getUnidades()) {
				if (unidad != null && unidad.getUdId() != null) {
					unidadesAsistentePorPrograma.put(unidad.getUdId(), unidad);
				}
			}
		} catch (InscripcionException e) {
			logger.warn("No fue posible cargar el contexto asistido para la malla curricular.", e);
		} catch (Exception e) {
			logger.warn("Error inesperado al cargar el contexto asistido para la malla curricular.", e);
		}
	}

	private void cargarFichaIntegralV2() {
		intentoCargaFichaIntegralV2 = true;
		fichaIntegralV2 = null;
		if (mallaCurricularV2Facade == null || idPersonaObjetivo == null) {
			return;
		}
		try {
			fichaIntegralV2 = mallaCurricularV2Facade.obtenerContextoMalla(construirContextoV2());
		} catch (InscripcionException e) {
			logger.warn("No fue posible cargar la ficha integral V2 para malla curricular.", e);
		} catch (Exception e) {
			logger.warn("Error inesperado al cargar la ficha integral V2 para malla curricular.", e);
		}
	}

	private void asegurarFichaIntegralV2() {
		if (fichaIntegralV2 != null || intentoCargaFichaIntegralV2) {
			return;
		}
		cargarFichaIntegralV2();
	}

	private ContextoAsistenteCurricularV2DTO construirContextoV2() {
		ContextoAsistenteCurricularV2DTO contextoV2 = new ContextoAsistenteCurricularV2DTO();
		contextoV2.setIdPersonaObjetivo(idPersonaObjetivo);
		contextoV2.setIdPersonaConsulta(idPersonaEnSesion());
		contextoV2.setPerfilConsulta(vistaGestor ? "GESTOR" : "ESTUDIANTE");
		contextoV2.setVistaGestor(Boolean.valueOf(vistaGestor));
		contextoV2.setPeriodoOperativo(esPeriodoCursamiento() ? "CURSAMIENTO" : "INSCRIPCION");
		contextoV2.setOrigenConsulta("MALLA");
		contextoV2.setIdPlan(planId);
		return contextoV2;
	}

	private void cargarInscripcionesEnCurso(Long idPersona) {
		if (planId == null) {
			return;
		}
		List<InscripcionPreviaMateriasDTO> inscritas = inscripcionPreviaMateriasService
				.obtenerInscripcionPrevia(idPersona);
		if (ObjectUtils.isNullOrEmpty(inscritas)) {
			return;
		}
		bloqueEnCurso = resolverBloqueActual();
		int maxSemestre = 0;
		for (InscripcionPreviaMateriasDTO materia : inscritas) {
			int semestre = parseNumero(materia.getSemestre(), 0);
			int bloque = parseNumero(materia.getBloque(), 0);
			if (semestre > maxSemestre) {
				maxSemestre = semestre;
			}
			if (StringUtils.isNotBlank(materia.getAsignatura())) {
				String nombreNormalizado = normalizaTexto(limpiaAsignatura(materia.getAsignatura()));
				asignaturasEnCurso.add(nombreNormalizado);
				registrarUbicacionProgramaEnCurso(nombreNormalizado, semestre, bloque);
				if (esProgramaOpcional(materia.getTipoPrograma())) {
					registrarUbicacionOptativa(ubicacionesOptativasEnCurso, nombreNormalizado, semestre, bloque);
				}
			}
		}
		if (maxSemestre > 0) {
			semestreEnCurso = maxSemestre;
		}
	}

	private void cargarUbicacionesHistoricasOptativas(Long idPersona) {
		if (grupoParticipanteService == null) {
			return;
		}
		List<EventoConstanciaDTO> historial = grupoParticipanteService.getParticipanteByActaCerradaYconstancia2(idPersona);
		if (ObjectUtils.isNullOrEmpty(historial)) {
			return;
		}
		for (EventoConstanciaDTO materia : historial) {
			if (materia == null || StringUtils.isBlank(materia.getClave()) || StringUtils.isBlank(materia.getnActa())) {
				continue;
			}
			int semestre = extraerSemestreDeActa(materia.getnActa());
			int bloque = extraerBloqueDeActa(materia.getnActa());
			registrarUbicacionOptativa(ubicacionesOptativasHistoricas, normalizaTexto(materia.getClave()), semestre, bloque);
		}
	}

	private void ajustarSemestreReferenciaPorTrayectoria(Long idPersona) {
		List<InscripcionMateriasCursadasDTO> materiasCursadas = inscripcionService.obtenerMateriasCursadas(idPersona);
		if (ObjectUtils.isNullOrEmpty(materiasCursadas)) {
			return;
		}
		int maxSemestreHistorico = 0;
		for (InscripcionMateriasCursadasDTO materia : materiasCursadas) {
			if (materia == null) {
				continue;
			}
			int semestreHistorico = parseNumero(materia.getEstructura(), 0);
			if (semestreHistorico > maxSemestreHistorico) {
				maxSemestreHistorico = semestreHistorico;
			}
		}
		if (maxSemestreHistorico <= 0) {
			return;
		}
		if (semestreEnCurso == null || maxSemestreHistorico > semestreEnCurso.intValue()) {
			semestreEnCurso = maxSemestreHistorico;
		}
	}

	private void construirModelo(MallaCurricularDTO raiz, Long idPersona) {
		Map<Integer, MallaDiagramaNodoDTO> programasPorId = new HashMap<>();
		Map<Integer, String> programaNombrePorId = new HashMap<>();
		Map<Integer, Integer> programaAntecedentePorId = new HashMap<>();
		Map<Integer, List<String>> dependenciasEntrantes = new HashMap<>();
		Map<Integer, List<String>> dependenciasSalientes = new HashMap<>();
		List<FichaDescProgramaDTO> programasPlan = new ArrayList<>();
		Map<Integer, List<FichaDescProgramaDTO>> programasPorEje = new HashMap<>();
		Map<String, MallaDiagramaTipoDTO> tiposMap = new LinkedHashMap<>();
		Map<Integer, MallaAlumnoProgramaDTO> estatusPorPrograma = new HashMap<>();
		Map<Integer, ProgramaTablaDTO> filasTablaPorPrograma = new HashMap<>();
		Map<Integer, SemestreTablaDTO> semestreTablaMap = new LinkedHashMap<>();
		int secuenciaLocal = 0;
		int maxX = 0;
		int maxY = 0;

		List<FichaDescProgramaDTO> programasPlanCompleto = fecServiceFacade.getFichaDescProgramaService()
				.buscarProgramasPorPlan(planId.intValue());
		if (!ObjectUtils.isNullOrEmpty(programasPlanCompleto)) {
			programasPorEje = programasPlanCompleto.stream()
					.filter(p -> p.getEjeCapacitacion() != null)
					.collect(Collectors.groupingBy(FichaDescProgramaDTO::getEjeCapacitacion));
		}

		List<MallaAlumnoProgramaDTO> estatusLista = inscripcionService.obtenerProgramasMallaAlumno(idPersona, planId);
		cargarProgramasConBaja(idPersona);
		if (!ObjectUtils.isNullOrEmpty(estatusLista)) {
			for (MallaAlumnoProgramaDTO dto : estatusLista) {
				if (dto.getIdPrograma() != null) {
					estatusPorPrograma.put(dto.getIdPrograma().intValue(), dto);
				}
			}
			actualizarEstatusEstudiante(estatusLista);
		}

		inicializaLeyendaEstatus();

		List<MallaCurricularDTO> semestres = filtraHijosPorTipo(raiz, ObjetoCurricularEnum.ESTRUCTURA);
		int col = 0;
		for (MallaCurricularDTO semestre : semestres) {
			int numeroSemestre = resolveNumeroSemestre(semestre.getNombre(), col + 1);
			SemestreTablaDTO semestreTabla = new SemestreTablaDTO(numeroSemestre, "Semestre " + numeroSemestre);
			semestreTablaMap.put(numeroSemestre, semestreTabla);
			semestresTabla.add(semestreTabla);
			int x = (col * COL_WIDTH) + SEMESTRE_X_OFFSET;
			int y = SEMESTRE_Y;

			String semestreId = nextElementId(ID_SEMESTRE_PREFIX, semestre.getId(), secuenciaLocal++);
			MallaDiagramaNodoDTO semestreNodo = new MallaDiagramaNodoDTO(
					semestreId, semestre.getNombre(), "SEMESTRE",
					x + ((NODE_WIDTH - SEMESTRE_WIDTH) / 2), y, SEMESTRE_WIDTH, SEMESTRE_HEIGHT);
			semestreNodo.setSemestre(numeroSemestre);
			semestreNodo.setTextoCompacto("S" + numeroSemestre);
			if (semestreEnCurso != null && semestreEnCurso.equals(numeroSemestre)) {
				semestreNodo.setEnCurso(true);
			}
			nodos.add(semestreNodo);
			maxX = Math.max(maxX, x + NODE_WIDTH);
			maxY = Math.max(maxY, y + SEMESTRE_HEIGHT);
			y += SEMESTRE_HEIGHT + SEMESTRE_BLOQUE_GAP;

			List<MallaCurricularDTO> bloques = filtraHijosPorTipo(semestre, ObjetoCurricularEnum.SUB_ESTRUCTURA);
			for (MallaCurricularDTO bloque : bloques) {
				int numeroBloque = resolveNumeroBloque(bloque.getNombre(), 0);
				int bloqueX = x + BLOQUE_X_OFFSET + ((NODE_WIDTH - BLOQUE_WIDTH) / 2);
				int bloqueHeight = estimaAlto(bloque.getNombre(), BLOQUE_BASE_HEIGHT);
				String bloqueId = nextElementId(ID_BLOQUE_PREFIX, bloque.getId(), secuenciaLocal++);
				MallaDiagramaNodoDTO bloqueNodo = new MallaDiagramaNodoDTO(
						bloqueId, bloque.getNombre(), "BLOQUE", bloqueX, y, BLOQUE_WIDTH, bloqueHeight);
				bloqueNodo.setBloqueId(bloqueId);
				bloqueNodo.setSemestre(numeroSemestre);
				bloqueNodo.setTextoCompacto(numeroBloque > 0 ? "B" + numeroBloque : "B");
				if (semestreEnCurso != null && bloqueEnCurso != null
						&& semestreEnCurso.equals(numeroSemestre)
						&& bloqueEnCurso.equals(numeroBloque)) {
					bloqueNodo.setEnCurso(true);
				}
				nodos.add(bloqueNodo);
				maxX = Math.max(maxX, bloqueX + NODE_WIDTH);
				maxY = Math.max(maxY, y + bloqueHeight);
				y += bloqueHeight;

				List<MallaCurricularDTO> materiasMalla = filtraHijosPorTipo(bloque, ObjetoCurricularEnum.PROGRAMA);
				int idxMateriaMalla = 0;
				List<FichaDescProgramaDTO> programas = programasPorEje.getOrDefault(bloque.getId(), new ArrayList<>());
				for (FichaDescProgramaDTO programa : programas) {
					String nombrePrograma = resolveNombrePrograma(programa);
					if ("Programa sin nombre".equals(nombrePrograma) && idxMateriaMalla < materiasMalla.size()) {
						MallaCurricularDTO materiaMalla = materiasMalla.get(idxMateriaMalla);
						if (materiaMalla != null && StringUtils.isNotBlank(materiaMalla.getNombre())) {
							nombrePrograma = materiaMalla.getNombre();
						}
						idxMateriaMalla++;
					}
					if (!debeMostrarsePrograma(programa, nombrePrograma, numeroSemestre, numeroBloque)) {
						continue;
					}
					int programaX = x + PROGRAMA_X_OFFSET + ((NODE_WIDTH - PROGRAMA_WIDTH) / 2);
					int programaHeight = estimaAlto(nombrePrograma, PROGRAMA_BASE_HEIGHT);
					String programaId = nextElementId(ID_PROGRAMA_PREFIX, programa.getIdPrograma(), secuenciaLocal++);
					MallaDiagramaNodoDTO programaNodo = new MallaDiagramaNodoDTO(
							programaId, nombrePrograma, "PROGRAMA", programaX, y, PROGRAMA_WIDTH, programaHeight);
					programaNodo.setSemestre(numeroSemestre);
					programaNodo.setBloqueId(bloqueId);
					aplicaTipoPrograma(programaNodo, programa.getTipo(), tiposMap);
					Integer idPrograma = programa.getIdPrograma();
					aplicaEstatusPrograma(programaNodo,
							idPrograma != null ? estatusPorPrograma.get(idPrograma) : null,
							idPrograma);
					programaNodo.setCreditos(programa.getCreditos());
					programaNodo.setTextoCompacto(resolveTextoCompacto(programa, nombrePrograma));
					programaNodo.setProgramaId(idPrograma);
					programaNodo.setClavePrograma(StringUtils.defaultIfBlank(programa.getCvePrograma(), "N/A"));
					if (estaProgramaEnCurso(programa, nombrePrograma, numeroSemestre, numeroBloque)) {
						programaNodo.setEnCurso(true);
					}
					UnidadDecisionInscripcionDTO unidadContextual = idPrograma != null
							? unidadesAsistentePorPrograma.get(idPrograma.longValue()) : null;
					enriquecerDetalleContextual(programaNodo, idPrograma, programa);
					acumularCreditos(programa.getTipo(), programa.getCreditos(),
							estatusPorPrograma.get(idPrograma), unidadContextual);
					nodos.add(programaNodo);

					if (programa.getIdPrograma() != null) {
						programasPorId.put(programa.getIdPrograma(), programaNodo);
						programaNombrePorId.put(programa.getIdPrograma(), nombrePrograma);
						if (programa.getProgramaAntecedente() != null
								&& programa.getProgramaAntecedente().getIdPrograma() != null) {
							programaAntecedentePorId.put(programa.getIdPrograma(),
									programa.getProgramaAntecedente().getIdPrograma());
						}
					}
					programasPlan.add(programa);
					if (programa.getIdPrograma() != null) {
						ProgramaTablaDTO fila = new ProgramaTablaDTO();
						fila.setBloque(numeroBloque);
						fila.setAsignatura(nombrePrograma);
						fila.setClave(StringUtils.defaultIfBlank(programa.getCvePrograma(), "N/A"));
						fila.setCreditos(programa.getCreditos());
						fila.setTipoBorderColor(programaNodo.getTipoBorderColor());
						semestreTabla.getFilas().add(fila);
						filasTablaPorPrograma.put(programa.getIdPrograma(), fila);
					}
					maxX = Math.max(maxX, programaX + NODE_WIDTH);
					maxY = Math.max(maxY, y + programaHeight);
					y += programaHeight + PROGRAMA_GAP;
				}

				y += BLOQUE_GAP;
			}
			col++;
		}

		conectarProgramas(programasPlan, programasPorId);
		resolverDependencias(programaAntecedentePorId, programaNombrePorId, dependenciasEntrantes, dependenciasSalientes);
		aplicarTooltipsDependencias(programasPorId, dependenciasEntrantes, dependenciasSalientes);
		aplicarEstatusEnCurso(programasPorId);
		aplicarBloqueos(programasPorId, programaAntecedentePorId, estatusPorPrograma);
		actualizarFilasTabla(filasTablaPorPrograma, programasPorId);
		tiposPrograma = new ArrayList<>(tiposMap.values());
		diagramWidth = maxX + DIAGRAMA_PADDING;
		diagramHeight = maxY + DIAGRAMA_PADDING;
	}

	private void actualizarFilasTabla(Map<Integer, ProgramaTablaDTO> filasTablaPorPrograma,
			Map<Integer, MallaDiagramaNodoDTO> programasPorId) {
		for (Map.Entry<Integer, ProgramaTablaDTO> entry : filasTablaPorPrograma.entrySet()) {
			MallaDiagramaNodoDTO nodo = programasPorId.get(entry.getKey());
			if (nodo == null) {
				continue;
			}
			ProgramaTablaDTO fila = entry.getValue();
			fila.setEstatus(resolveEstatusTabla(nodo.getEstatus()));
			fila.setEstatusCss(nodo.getEstatusCss());
			fila.setEstatusIconClass(nodo.getEstatusIconClass());
			fila.setEstatusColor(resolveColorByEstatusCss(nodo.getEstatusCss()));
			fila.setSeriada(nodo.isTieneDependencias());
			fila.setDependenciaTooltip(nodo.getDependenciaTooltip());
			if (StringUtils.isNotBlank(nodo.getCalificacion())) {
				fila.setCalificacion(nodo.getCalificacion());
			} else if (ESTATUS_BLOQUEADA.equals(nodo.getEstatus())) {
				fila.setCalificacion("--");
			} else {
				fila.setCalificacion("N/A");
			}
		}
	}

	private String resolveEstatusTabla(String estatus) {
		if (ESTATUS_APROBADA.equals(estatus)) {
			return "Acreditada";
		}
		if (ESTATUS_NO_ACREDITADA.equals(estatus)) {
			return "No acreditada";
		}
		if (ESTATUS_NO_INSCRITA.equals(estatus)) {
			return "No inscrita";
		}
		if (ESTATUS_BLOQUEADA.equals(estatus)) {
			return "Bloqueada";
		}
		if (ESTATUS_BAJA.equals(estatus)) {
			return "Baja";
		}
		if (ESTATUS_EN_CURSO.equals(estatus)) {
			return "En curso";
		}
		return StringUtils.defaultString(estatus, "N/A");
	}

	private void enriquecerDetalleContextual(MallaDiagramaNodoDTO programaNodo, Integer idPrograma,
			FichaDescProgramaDTO programa) {
		if (programaNodo == null) {
			return;
		}
		programaNodo.setDetalleTipoUd(formatearTipoDetalle(programaNodo, programa, null));
		programaNodo.setDetalleEstatusHistorico(resolveEstatusDetalle(programaNodo, null));
		programaNodo.setDetalleEstatusPeriodo(resolveEstatusPeriodoContextual(programaNodo));
		programaNodo.setDetalleMotivoPrincipal("Selecciona una unidad didáctica para consultar su situación académica.");
		programaNodo.setDetalleAccionSugerida("Revisa el asistente de inscripción curricular para interpretar su elegibilidad y oferta.");
		programaNodo.setDetalleRiesgo("Su resultado académico incide en la continuidad de tu trayectoria.");
		if (idPrograma == null) {
			return;
		}
		UnidadDecisionInscripcionDTO unidad = unidadesAsistentePorPrograma.get(idPrograma.longValue());
		if (unidad == null) {
			return;
		}
		programaNodo.setDetalleTipoUd(formatearTipoDetalle(programaNodo, programa, unidad));
		programaNodo.setDetalleEstatusHistorico(resolveEstatusDetalle(programaNodo, unidad));
		if (StringUtils.isNotBlank(unidad.getEstatusPeriodo())) {
			programaNodo.setDetalleEstatusPeriodo(unidad.getEstatusPeriodo());
		}
		programaNodo.setDetalleMotivoPrincipal(construirSituacionDetalle(programaNodo, programa, unidad));
		programaNodo.setDetalleAccionSugerida(construirRecomendacionDetalle(programaNodo, programa, unidad));
		programaNodo.setDetalleRiesgo(construirImpactoDetalle(programaNodo, programa, unidad));
	}

	private String formatearTipoDetalle(MallaDiagramaNodoDTO programaNodo, FichaDescProgramaDTO programa,
			UnidadDecisionInscripcionDTO unidad) {
		String tipo = unidad != null && StringUtils.isNotBlank(unidad.getTipoUd())
				? unidad.getTipoUd() : (programa != null ? programa.getTipo() : null);
		String normalizado = normalizaTexto(tipo);
		if (esUnidadOpcionalLibre(unidad) || normalizado.contains("optativa opcional")) {
			return "Optativa opcional";
		}
		if (normalizado.contains("electiv")) {
			return "Electiva";
		}
		if (normalizado.contains("optativ")) {
			return "Optativa";
		}
		if (normalizado.contains("oblig")) {
			return programaNodo != null && (programaNodo.isBloqueada() || programaNodo.isTieneDependencias())
					? "Obligatoria seriada" : "Obligatoria";
		}
		return StringUtils.defaultIfBlank(tipo, "Sin información");
	}

	private String resolveEstatusDetalle(MallaDiagramaNodoDTO programaNodo, UnidadDecisionInscripcionDTO unidad) {
		if (esUnidadOpcionalLibre(unidad)) {
			return "Opcional";
		}
		if (programaNodo != null && ESTATUS_APROBADA.equals(programaNodo.getEstatus())) {
			return "Acreditada";
		}
		if (programaNodo != null && ESTATUS_NO_ACREDITADA.equals(programaNodo.getEstatus())) {
			return "Pendiente de acreditar";
		}
		if (programaNodo != null && programaNodo.isBloqueada()) {
			return "Pendiente de cursar";
		}
		if (programaNodo != null && ESTATUS_BAJA.equals(programaNodo.getEstatus())) {
			return "Pendiente por " + resolverTipoBaja(unidad);
		}
		return "Por cursar";
	}

	private String construirSituacionDetalle(MallaDiagramaNodoDTO programaNodo, FichaDescProgramaDTO programa,
			UnidadDecisionInscripcionDTO unidad) {
		if (esUnidadOpcionalLibre(unidad)) {
			return "Corresponde a la unidad didáctica optativa adicional del bloque. Dado que ya cubriste las 2 unidades optativas opcionales requeridas en tu Programa educativo, esta unidad didáctica queda como una opción libre a elegir, si deseas aumentar tus créditos, puedes integrarla en cuanto se oferte.";
		}
		if (programaNodo != null && ESTATUS_APROBADA.equals(programaNodo.getEstatus())) {
			return "Unidad didáctica concluida y acreditada en tu historial académico.";
		}
		if (programaNodo != null && ESTATUS_BAJA.equals(programaNodo.getEstatus())) {
			String tipoBaja = resolverTipoBaja(unidad);
			return "Registras una " + tipoBaja + " previa en esta unidad didáctica, debes cursarla en cuanto se oferte para continuar con tu trayectoria académica.";
		}
		if (programaNodo != null && programaNodo.isBloqueada()) {
			return "Esta unidad didáctica no está disponible para selección porque requiere haber acreditado previamente " + resolverNombreAntecedente(programa, programaNodo) + ".";
		}
		if (programaNodo != null && ESTATUS_NO_ACREDITADA.equals(programaNodo.getEstatus())) {
			if ("Obligatoria seriada".equalsIgnoreCase(formatearTipoDetalle(programaNodo, programa, unidad))) {
				return "Esta unidad didáctica seriada no está acreditada y debes inscribirla y cursarla en cuanto se oferte para continuar con tu trayectoria académica regular y poder registrar la siguiente unidad didáctica.";
			}
			return "Esta unidad didáctica no está acreditada y debe inscribirse y cursarse en cuanto se oferte para continuar con tu trayectoria académica regular.";
		}
		return "Unidad didáctica por cursar y acreditar en próximos periodos.";
	}

	private String construirRecomendacionDetalle(MallaDiagramaNodoDTO programaNodo, FichaDescProgramaDTO programa,
			UnidadDecisionInscripcionDTO unidad) {
		if (esUnidadOpcionalLibre(unidad)) {
			return "No es obligatorio registrarla. Inscríbela únicamente si deseas reforzar tu formación y cuentas con espacio en tu carga máxima permitida o si es especialmente útil si alguna unidad didáctica obligatoria no llega a ofertarse.";
		}
		if (programaNodo != null && ESTATUS_APROBADA.equals(programaNodo.getEstatus())) {
			return "Tus créditos ya forman parte de tu avance. Puedes consultar tu calificación registrada en el Historial Académico.";
		}
		if (programaNodo != null && ESTATUS_BAJA.equals(programaNodo.getEstatus())) {
			return "Inscribe y cursa la unidad didáctica. Revisa elegibilidad, oferta y validación de registro de la UD en el asistente de inscripción.";
		}
		if (programaNodo != null && programaNodo.isBloqueada()) {
			String antecedente = resolverNombreAntecedente(programa, programaNodo);
			return "Acredita la unidad didáctica pendiente " + antecedente + " para poder registrar esta unidad didáctica. Revisa elegibilidad, oferta y validación de registro de la UD en el asistente de inscripción.";
		}
		if (programaNodo != null && ESTATUS_NO_ACREDITADA.equals(programaNodo.getEstatus())) {
			return "Revisa elegibilidad, oferta y validación de registro de la UD en el asistente de inscripción.";
		}
		return "Continúa revisando a detalle cada unidad didáctica para que tengas conocimiento del impacto que tiene en tu trayectoria académica.";
	}

	private String construirImpactoDetalle(MallaDiagramaNodoDTO programaNodo, FichaDescProgramaDTO programa,
			UnidadDecisionInscripcionDTO unidad) {
		if (esUnidadOpcionalLibre(unidad)) {
			return "Aporta créditos adicionales a tu historial académico. Cursarla o no cursarla no afecta tu situación académica.";
		}
		if (programaNodo != null && ESTATUS_APROBADA.equals(programaNodo.getEstatus())) {
			return "El cumplimiento de acreditación con esta unidad didáctica en tu Programa educativo permite avanzar a los siguientes semestres.";
		}
		if (programaNodo != null && ESTATUS_BAJA.equals(programaNodo.getEstatus())) {
			return "Mantener unidades didácticas obligatorias pendientes puede limitar la selección de unidades didácticas en semestres posteriores.";
		}
		if (programaNodo != null && (programaNodo.isBloqueada() || ESTATUS_NO_ACREDITADA.equals(programaNodo.getEstatus()))) {
			return "Mantener unidades didácticas pendientes puede limitar la selección de unidades didácticas en semestres posteriores.";
		}
		return "Acreditar esta unidad didáctica en su momento te permitirá continuar avanzando en la secuencia prevista para tu programa educativo.";
	}

	private String resolverNombreAntecedente(FichaDescProgramaDTO programa, MallaDiagramaNodoDTO programaNodo) {
		if (programa != null && programa.getProgramaAntecedente() != null) {
			String nombre = resolveNombrePrograma(programa.getProgramaAntecedente());
			if (StringUtils.isNotBlank(nombre) && !"Programa sin nombre".equalsIgnoreCase(nombre)) {
				return nombre;
			}
		}
		if (programaNodo != null && StringUtils.isNotBlank(programaNodo.getDependenciaTooltip())) {
			String tooltip = programaNodo.getDependenciaTooltip().trim();
			int idx = tooltip.lastIndexOf(":");
			if (idx >= 0 && idx + 1 < tooltip.length()) {
				return tooltip.substring(idx + 1).trim();
			}
			return tooltip;
		}
		return "la unidad didáctica antecedente";
	}

	private String resolverTipoBaja(UnidadDecisionInscripcionDTO unidad) {
		if (unidad == null || StringUtils.isBlank(unidad.getEstatusHistorico())) {
			return "baja previa";
		}
		String estatus = unidad.getEstatusHistorico().trim();
		if (estatus.toUpperCase().contains("TEMP")) {
			return "baja temporal";
		}
		if (estatus.toUpperCase().contains("PARC")) {
			return "baja parcial";
		}
		return estatus.toLowerCase(Locale.ROOT);
	}

	private String resolveEstatusPeriodoContextual(MallaDiagramaNodoDTO programaNodo) {
		if (programaNodo == null) {
			return "Sin información";
		}
		if (programaNodo.isBloqueada()) {
			return "Bloqueada";
		}
		if (programaNodo.isEnCurso()) {
			return "En curso";
		}
		if (ESTATUS_BAJA.equals(programaNodo.getEstatus())) {
			return "Baja";
		}
		if (ESTATUS_APROBADA.equals(programaNodo.getEstatus())) {
			return "Acreditada";
		}
		if (ESTATUS_NO_ACREDITADA.equals(programaNodo.getEstatus())) {
			return "No acreditada";
		}
		return esPeriodoCursamiento() ? "Seguimiento de trayectoria" : "Pendiente de validación";
	}

	private void acumularCreditos(String tipo, Integer creditos, MallaAlumnoProgramaDTO estatusDto,
			UnidadDecisionInscripcionDTO unidadContextual) {
		acumularPromedio(estatusDto);
		if (creditos == null || esUnidadOpcionalLibre(tipo, unidadContextual)) {
			return;
		}
		creditosPlanTotal += creditos;
		String tipoNormalizado = StringUtils.isBlank(tipo) ? "Sin tipo" : tipo.trim();
		Integer totalActual = creditosPlanPorTipo.get(tipoNormalizado);
		creditosPlanPorTipo.put(tipoNormalizado, totalActual == null ? creditos : totalActual + creditos);

		if (!esProgramaAprobado(estatusDto)) {
			return;
		}
		Integer actual = creditosAprobadosPorTipo.get(tipoNormalizado);
		if (actual == null) {
			creditosAprobadosPorTipo.put(tipoNormalizado, creditos);
		} else {
			creditosAprobadosPorTipo.put(tipoNormalizado, actual + creditos);
		}
		creditosAprobadosTotal += creditos;
	}

	private boolean esProgramaAprobado(MallaAlumnoProgramaDTO estatusDto) {
		if (estatusDto == null || estatusDto.getCalificacionFinal() == null) {
			return false;
		}
		Double calificacionFinal = estatusDto.getCalificacionFinal();
		if (Double.compare(calificacionFinal, CALIFICACION_NO_PRESENTADA) == 0) {
			return false;
		}
		double min = estatusDto.getCalificacionMinAprobatoria() != null
				? estatusDto.getCalificacionMinAprobatoria() : 0d;
		return calificacionFinal >= min;
	}

	private void acumularPromedio(MallaAlumnoProgramaDTO estatusDto) {
		if (estatusDto == null || estatusDto.getCalificacionFinal() == null) {
			return;
		}
		Double calificacionFinal = estatusDto.getCalificacionFinal();
		if (Double.compare(calificacionFinal, CALIFICACION_NO_PRESENTADA) == 0) {
			return;
		}
		sumaCalificaciones += calificacionFinal;
		totalCalificaciones++;
	}

	private void actualizarEstatusEstudiante(List<MallaAlumnoProgramaDTO> estatusLista) {
		estatusEstudiante = "Regular";
		for (MallaAlumnoProgramaDTO estatusDto : estatusLista) {
			if (estatusDto == null || estatusDto.getCalificacionFinal() == null) {
				continue;
			}
			if (estatusDto.getIdPrograma() != null
					&& esUnidadOpcionalLibre(unidadesAsistentePorPrograma.get(estatusDto.getIdPrograma().longValue()))) {
				continue;
			}
			if (estatusDto.getIdPrograma() != null
					&& programasConBaja.contains(estatusDto.getIdPrograma().intValue())) {
				continue;
			}
			if (!esProgramaAprobado(estatusDto)) {
				estatusEstudiante = "Irregular";
				return;
			}
		}
	}

	private void conectarProgramas(List<FichaDescProgramaDTO> programasPlan, Map<Integer, MallaDiagramaNodoDTO> programasPorId) {
		int colorIndex = 0;
		for (FichaDescProgramaDTO programa : programasPlan) {
			if (programa.getProgramaAntecedente() == null
					|| programa.getProgramaAntecedente().getIdPrograma() == null) {
				continue;
			}
			MallaDiagramaNodoDTO antecedente = programasPorId.get(programa.getProgramaAntecedente().getIdPrograma());
			MallaDiagramaNodoDTO actual = programasPorId.get(programa.getIdPrograma());
			if (antecedente == null || actual == null) {
				continue;
			}
			String color = CONEXION_COLORS[colorIndex % CONEXION_COLORS.length];
			colorIndex++;
			antecedente.getOutgoingColors().add(color);
			actual.getIncomingColors().add(color);
		}
	}

	private List<MallaCurricularDTO> filtraHijosPorTipo(MallaCurricularDTO padre, ObjetoCurricularEnum tipo) {
		List<MallaCurricularDTO> hijos = new ArrayList<>();
		if (ObjectUtils.isNull(padre) || ObjectUtils.isNullOrEmpty(padre.getLstHijosMallaCurr())) {
			return hijos;
		}
		for (MallaCurricularDTO hijo : padre.getLstHijosMallaCurr()) {
			if (ObjectUtils.isNotNull(hijo.getObjetoCurricular())
					&& tipo.getid().equals(hijo.getObjetoCurricular().getId())) {
				hijos.add(hijo);
			}
		}
		return hijos;
	}

	private String resolveNombrePrograma(FichaDescProgramaDTO programa) {
		if (ObjectUtils.isNull(programa)) {
			return "Programa";
		}
		if (StringUtils.isNotBlank(programa.getNombreTentativo())) {
			return programa.getNombreTentativo();
		}
		if (StringUtils.isNotBlank(programa.getIdentificadorFinal())) {
			return programa.getIdentificadorFinal();
		}
		if (StringUtils.isNotBlank(programa.getCvePrograma())) {
			return programa.getCvePrograma();
		}
		if (programa.getIdPrograma() != null) {
			return "Programa " + programa.getIdPrograma();
		}
		return "Programa sin nombre";
	}

	private String resolveTextoCompacto(FichaDescProgramaDTO programa, String nombrePrograma) {
		if (programa != null && StringUtils.isNotBlank(programa.getCvePrograma())) {
			return programa.getCvePrograma();
		}
		if (StringUtils.isNotBlank(nombrePrograma)) {
			String limpio = nombrePrograma.trim();
			return limpio.length() > 12 ? limpio.substring(0, 12) + "…" : limpio;
		}
		return "ND";
	}

	private int estimaAlto(String texto, int baseHeight) {
		if (StringUtils.isBlank(texto)) {
			return baseHeight;
		}
		int length = texto.trim().length();
		int lines = (int) Math.ceil((double) length / MAX_CHARS_PER_LINE);
		if (lines <= 1) {
			return baseHeight;
		}
		return baseHeight + ((lines - 1) * LINE_HEIGHT) + EXTRA_TEXT_HEIGHT;
	}

	private int resolveNumeroSemestre(String nombre, int fallback) {
		return parseNumero(nombre, fallback);
	}

	private int resolveNumeroBloque(String nombre, int fallback) {
		return parseNumero(nombre, fallback);
	}

	private int parseNumero(String texto, int fallback) {
		if (StringUtils.isBlank(texto)) {
			return fallback;
		}
		String digits = texto.replaceAll("[^0-9]", "");
		if (digits.isEmpty()) {
			return fallback;
		}
		try {
			return Integer.parseInt(digits);
		} catch (NumberFormatException ex) {
			return fallback;
		}
	}

	private int extraerSemestreDeActa(String textoActa) {
		if (StringUtils.isBlank(textoActa)) {
			return 0;
		}
		String compacta = textoActa.toUpperCase().replaceAll("\\s+", "");
		java.util.regex.Matcher matcher = java.util.regex.Pattern.compile("B(\\d+)S(\\d+)").matcher(compacta);
		if (matcher.find()) {
			return parseNumero(matcher.group(2), 0);
		}
		return 0;
	}

	private int extraerBloqueDeActa(String textoActa) {
		if (StringUtils.isBlank(textoActa)) {
			return 0;
		}
		String compacta = textoActa.toUpperCase().replaceAll("\\s+", "");
		java.util.regex.Matcher matcher = java.util.regex.Pattern.compile("B(\\d+)S(\\d+)").matcher(compacta);
		if (matcher.find()) {
			return parseNumero(matcher.group(1), 0);
		}
		return 0;
	}

	private boolean debeMostrarsePrograma(FichaDescProgramaDTO programa, String nombrePrograma,
			int numeroSemestre, int numeroBloque) {
		if (!esProgramaOpcional(programa != null ? programa.getTipo() : null)) {
			return true;
		}
		String ubicacionActual = construirClaveUbicacion(numeroSemestre, numeroBloque);
		Set<String> ubicacionesHistoricas = obtenerUbicacionesOptativas(ubicacionesOptativasHistoricas, programa, nombrePrograma);
		if (!ubicacionesHistoricas.isEmpty()) {
			return ubicacionesHistoricas.contains(ubicacionActual);
		}
		Set<String> ubicacionesEnCurso = obtenerUbicacionesOptativas(ubicacionesOptativasEnCurso, programa, nombrePrograma);
		if (!ubicacionesEnCurso.isEmpty()) {
			return ubicacionesEnCurso.contains(ubicacionActual);
		}
		return true;
	}

	private boolean estaProgramaEnCurso(FichaDescProgramaDTO programa, String nombrePrograma,
			int numeroSemestre, int numeroBloque) {
		String ubicacionActual = construirClaveUbicacion(numeroSemestre, numeroBloque);
		if (!esProgramaOpcional(programa != null ? programa.getTipo() : null)) {
			return estaProgramaEnCursoPorUbicacion(programa, nombrePrograma, ubicacionActual);
		}
		Set<String> ubicacionesEnCurso = obtenerUbicacionesOptativas(ubicacionesOptativasEnCurso, programa, nombrePrograma);
		return ubicacionesEnCurso.contains(ubicacionActual);
	}

	private boolean estaProgramaEnCursoPorUbicacion(FichaDescProgramaDTO programa, String nombrePrograma,
			String ubicacionActual) {
		for (String clave : construirClavesPrograma(programa, nombrePrograma)) {
			Set<String> ubicaciones = ubicacionesProgramasEnCurso.get(clave);
			if (!ObjectUtils.isNullOrEmpty(ubicaciones) && ubicaciones.contains(ubicacionActual)) {
				return true;
			}
		}
		return false;
	}

	private boolean esProgramaOpcional(String tipoPrograma) {
		if (StringUtils.isBlank(tipoPrograma)) {
			return false;
		}
		String tipo = normalizaTexto(tipoPrograma);
		return tipo.contains("optativa") || tipo.contains("opcional");
	}

	private boolean esUnidadOpcionalLibre(UnidadDecisionInscripcionDTO unidadContextual) {
		if (unidadContextual == null) {
			return false;
		}
		if ("OPCIONAL".equalsIgnoreCase(StringUtils.trimToEmpty(unidadContextual.getEstatusPeriodo()))
				|| "ALTERNATIVA".equalsIgnoreCase(StringUtils.trimToEmpty(unidadContextual.getEstatusPeriodo()))) {
			return true;
		}
		String tipoUd = normalizaTexto(unidadContextual.getTipoUd());
		return tipoUd.contains("optativa") && tipoUd.contains("opcional");
	}

	private boolean esUnidadOpcionalLibre(String tipoPrograma, UnidadDecisionInscripcionDTO unidadContextual) {
		if (esUnidadOpcionalLibre(unidadContextual)) {
			return true;
		}
		String tipoNormalizado = normalizaTexto(tipoPrograma);
		return tipoNormalizado.contains("optativa opcional");
	}

	private void registrarUbicacionOptativa(Map<String, Set<String>> ubicacionesPorClave, String clave,
			int semestre, int bloque) {
		if (ubicacionesPorClave == null || StringUtils.isBlank(clave) || semestre <= 0 || bloque <= 0) {
			return;
		}
		ubicacionesPorClave.computeIfAbsent(clave, key -> new HashSet<>())
				.add(construirClaveUbicacion(semestre, bloque));
	}

	private void registrarUbicacionProgramaEnCurso(String clave, int semestre, int bloque) {
		if (StringUtils.isBlank(clave) || semestre <= 0 || bloque <= 0) {
			return;
		}
		ubicacionesProgramasEnCurso.computeIfAbsent(clave, key -> new HashSet<>())
				.add(construirClaveUbicacion(semestre, bloque));
	}

	private Set<String> obtenerUbicacionesOptativas(Map<String, Set<String>> ubicacionesPorClave,
			FichaDescProgramaDTO programa, String nombrePrograma) {
		Set<String> ubicaciones = new HashSet<>();
		for (String clave : construirClavesPrograma(programa, nombrePrograma)) {
			Set<String> registradas = ubicacionesPorClave.get(clave);
			if (!ObjectUtils.isNullOrEmpty(registradas)) {
				ubicaciones.addAll(registradas);
			}
		}
		return ubicaciones;
	}

	private Set<String> construirClavesPrograma(FichaDescProgramaDTO programa, String nombrePrograma) {
		Set<String> claves = new HashSet<>();
		if (programa != null) {
			if (StringUtils.isNotBlank(programa.getIdentificadorFinal())) {
				claves.add(normalizaTexto(programa.getIdentificadorFinal()));
			}
			if (StringUtils.isNotBlank(programa.getCvePrograma())) {
				claves.add(normalizaTexto(programa.getCvePrograma()));
			}
		}
		if (StringUtils.isNotBlank(nombrePrograma)) {
			claves.add(normalizaTexto(nombrePrograma));
		}
		return claves;
	}

	private String construirClaveUbicacion(int semestre, int bloque) {
		return "S" + semestre + "B" + bloque;
	}

	private String normalizaTexto(String texto) {
		if (texto == null) {
			return "";
		}
		String normalized = Normalizer.normalize(texto, Normalizer.Form.NFD)
				.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "")
				.toLowerCase();
		return normalized.replaceAll("\\s+", " ").trim();
	}

	private String limpiaAsignatura(String texto) {
		if (texto == null) {
			return "";
		}
		return texto.replaceAll("\\s*\\(.*?\\)\\s*", " ").trim();
	}

	private int resolverBloqueActual() {
		Calendar calendar = Calendar.getInstance();
		int month = calendar.get(Calendar.MONTH) + 1;
		if (month == 1 || month == 2 || month == 3 || month == 7 || month == 8 || month == 9) {
			return 1;
		}
		return 2;
	}
	private String nextElementId(String prefix, Integer idBase, int secuencia) {
		String base = idBase != null ? String.valueOf(idBase) : "tmp";
		return prefix + base + "-" + secuencia;
	}

	private void aplicaTipoPrograma(MallaDiagramaNodoDTO nodo, String tipo, Map<String, MallaDiagramaTipoDTO> tiposMap) {
		String tipoNormalizado = StringUtils.isBlank(tipo) ? "Sin tipo" : tipo.trim();
		MallaDiagramaTipoDTO tipoDTO = tiposMap.get(tipoNormalizado);
		if (tipoDTO == null) {
			int index = tiposMap.size() % TIPOS_COLORS.length;
			String baseColor = TIPOS_COLORS[index];
			String background = colorToRgba(baseColor, 0.15);
			String border = baseColor;
			tipoDTO = new MallaDiagramaTipoDTO(tipoNormalizado, baseColor, background, border);
			tiposMap.put(tipoNormalizado, tipoDTO);
		}
		if (!creditosAprobadosPorTipo.containsKey(tipoNormalizado)) {
			creditosAprobadosPorTipo.put(tipoNormalizado, 0);
		}
		nodo.setTipoPrograma(tipoNormalizado);
		nodo.setTipoBackgroundColor(tipoDTO.getBackgroundColor());
		nodo.setTipoBorderColor(tipoDTO.getBorderColor());
	}

	private void inicializaLeyendaEstatus() {
		estatusPrograma.clear();
		estatusPrograma.add(new MallaDiagramaTipoDTO(ESTATUS_APROBADA, "#1b7a3f", "#1b7a3f", "#1b7a3f", "fa fa-check"));
		estatusPrograma.add(new MallaDiagramaTipoDTO(ESTATUS_NO_ACREDITADA, "#b91c1c", "#b91c1c", "#b91c1c", "fa fa-times"));
		estatusPrograma.add(new MallaDiagramaTipoDTO(ESTATUS_NO_INSCRITA, "#506172", "#506172", "#506172", "fa fa-exclamation-triangle"));
		estatusPrograma.add(new MallaDiagramaTipoDTO(ESTATUS_BAJA, "#fb923c", "#fb923c", "#fb923c", "fa fa-arrow-down"));
		estatusPrograma.add(new MallaDiagramaTipoDTO(ESTATUS_EN_CURSO, "#facc15", "#facc15", "#facc15", "fa fa-flag"));
		estatusPrograma.add(new MallaDiagramaTipoDTO(ESTATUS_BLOQUEADA, "#111827", "#111827", "#111827", "fa fa-lock"));
	}

	private void aplicaEstatusPrograma(MallaDiagramaNodoDTO nodo, MallaAlumnoProgramaDTO estatusDto, Integer idPrograma) {
		String estatus = ESTATUS_NO_INSCRITA;
		Double calificacionFinal = null;
		Double calificacionMin = null;
		if (estatusDto != null) {
			calificacionFinal = estatusDto.getCalificacionFinal();
			calificacionMin = estatusDto.getCalificacionMinAprobatoria();
		}
		if (idPrograma != null && programasConBaja.contains(idPrograma)) {
			estatus = ESTATUS_BAJA;
		} else if (calificacionFinal != null) {
			if (Double.compare(calificacionFinal, CALIFICACION_NO_PRESENTADA) == 0) {
				estatus = ESTATUS_NO_ACREDITADA;
			} else {
				double min = calificacionMin != null ? calificacionMin : 0d;
				estatus = calificacionFinal >= min ? ESTATUS_APROBADA : ESTATUS_NO_ACREDITADA;
			}
		}
		String color = ESTATUS_COLORS.getOrDefault(estatus, "#6b7280");
		nodo.setEstatus(estatus);
		nodo.setEstatusBackgroundColor(color);
		nodo.setEstatusBorderColor(color);
		nodo.setEstatusIconClass(resolveIconClass(estatus));
		if (calificacionFinal == null) {
			nodo.setCalificacion("");
		} else if (Double.compare(calificacionFinal, CALIFICACION_NO_PRESENTADA) == 0) {
			nodo.setCalificacion("NP");
		} else {
			nodo.setCalificacion(CALIF_FORMAT.format(calificacionFinal));
		}
	}

	private void cargarProgramasConBaja(Long idPersona) {
		programasConBaja.clear();
		List<InscripcionBajasDTO> bajas = inscripcionService.obtenerBajasDeMateriasSolicitadas(idPersona);
		if (ObjectUtils.isNullOrEmpty(bajas)) {
			return;
		}
		for (InscripcionBajasDTO baja : bajas) {
			if (baja != null && baja.getIdPrograma() != null && esBajaTemporalOParcial(baja)) {
				programasConBaja.add(baja.getIdPrograma().intValue());
			}
		}
	}

	private boolean esBajaTemporalOParcial(InscripcionBajasDTO baja) {
		if (baja == null || StringUtils.isBlank(baja.getTipoBaja())) {
			return false;
		}
		String tipo = baja.getTipoBaja().trim().toUpperCase();
		return tipo.contains("TEMPORAL") || tipo.contains("PARCIAL");
	}

	private void aplicarEstatusEnCurso(Map<Integer, MallaDiagramaNodoDTO> programasPorId) {
		for (MallaDiagramaNodoDTO nodo : programasPorId.values()) {
			if (nodo.isEnCurso()
					&& !ESTATUS_APROBADA.equals(nodo.getEstatus())
					&& !ESTATUS_NO_ACREDITADA.equals(nodo.getEstatus())
					&& !ESTATUS_BAJA.equals(nodo.getEstatus())) {
				String color = ESTATUS_COLORS.getOrDefault(ESTATUS_EN_CURSO, "#facc15");
				nodo.setEstatus(ESTATUS_EN_CURSO);
				nodo.setEstatusBackgroundColor(color);
				nodo.setEstatusBorderColor(color);
				nodo.setEstatusIconClass("fa fa-flag");
			}
		}
	}

	private void aplicarBloqueos(Map<Integer, MallaDiagramaNodoDTO> programasPorId,
			Map<Integer, Integer> programaAntecedentePorId,
			Map<Integer, MallaAlumnoProgramaDTO> estatusPorPrograma) {
		for (Map.Entry<Integer, MallaDiagramaNodoDTO> entry : programasPorId.entrySet()) {
			MallaDiagramaNodoDTO nodo = entry.getValue();
			if (nodo.isEnCurso()
					|| ESTATUS_APROBADA.equals(nodo.getEstatus())
					|| ESTATUS_NO_ACREDITADA.equals(nodo.getEstatus())
					|| ESTATUS_BAJA.equals(nodo.getEstatus())) {
				continue;
			}
			boolean bloqueada = false;
			Integer antecedenteId = programaAntecedentePorId.get(entry.getKey());
			if (antecedenteId != null) {
				MallaAlumnoProgramaDTO antecedenteEstado = estatusPorPrograma.get(antecedenteId);
				if (!esProgramaAprobado(antecedenteEstado)) {
					bloqueada = true;
				}
			}
			if (semestreEnCurso != null && nodo.getSemestre() != null
					&& nodo.getSemestre() > semestreEnCurso) {
				MallaAlumnoProgramaDTO estado = estatusPorPrograma.get(entry.getKey());
				if (!esProgramaAprobado(estado)) {
					bloqueada = true;
				}
			}
			if (bloqueada) {
				nodo.setBloqueada(true);
				String color = ESTATUS_COLORS.getOrDefault(ESTATUS_BLOQUEADA, "#111827");
				nodo.setEstatus(ESTATUS_BLOQUEADA);
				nodo.setEstatusBackgroundColor(color);
				nodo.setEstatusBorderColor(color);
				nodo.setEstatusIconClass("fa fa-lock");
			}
		}
	}

	private String resolveIconClass(String estatus) {
		if (ESTATUS_APROBADA.equals(estatus)) {
			return "fa fa-check";
		}
		if (ESTATUS_NO_ACREDITADA.equals(estatus)) {
			return "fa fa-times";
		}
		if (ESTATUS_NO_INSCRITA.equals(estatus)) {
			return "fa fa-exclamation-triangle";
		}
		if (ESTATUS_BAJA.equals(estatus)) {
			return "fa fa-arrow-down";
		}
		if (ESTATUS_EN_CURSO.equals(estatus)) {
			return "fa fa-flag";
		}
		if (ESTATUS_BLOQUEADA.equals(estatus)) {
			return "fa fa-lock";
		}
		return "fa fa-circle";
	}

	private void resolverDependencias(Map<Integer, Integer> programaAntecedentePorId,
			Map<Integer, String> programaNombrePorId,
			Map<Integer, List<String>> dependenciasEntrantes,
			Map<Integer, List<String>> dependenciasSalientes) {
		for (Map.Entry<Integer, Integer> entry : programaAntecedentePorId.entrySet()) {
			Integer programaId = entry.getKey();
			Integer antecedenteId = entry.getValue();
			if (antecedenteId == null) {
				continue;
			}
			String nombreAntecedente = programaNombrePorId.get(antecedenteId);
			String nombrePrograma = programaNombrePorId.get(programaId);
			if (StringUtils.isNotBlank(nombreAntecedente)) {
				dependenciasEntrantes.computeIfAbsent(programaId, k -> new ArrayList<>()).add(nombreAntecedente);
			}
			if (StringUtils.isNotBlank(nombrePrograma)) {
				dependenciasSalientes.computeIfAbsent(antecedenteId, k -> new ArrayList<>()).add(nombrePrograma);
			}
		}
	}

	private void aplicarTooltipsDependencias(Map<Integer, MallaDiagramaNodoDTO> programasPorId,
			Map<Integer, List<String>> dependenciasEntrantes,
			Map<Integer, List<String>> dependenciasSalientes) {
		for (Map.Entry<Integer, MallaDiagramaNodoDTO> entry : programasPorId.entrySet()) {
			Integer idPrograma = entry.getKey();
			List<String> entrantes = dependenciasEntrantes.get(idPrograma);
			List<String> salientes = dependenciasSalientes.get(idPrograma);
			if (ObjectUtils.isNullOrEmpty(entrantes) && ObjectUtils.isNullOrEmpty(salientes)) {
				continue;
			}
			StringBuilder tooltip = new StringBuilder();
			if (!ObjectUtils.isNullOrEmpty(entrantes)) {
				tooltip.append("Depende de: ").append(String.join(", ", entrantes));
			}
			if (!ObjectUtils.isNullOrEmpty(salientes)) {
				if (tooltip.length() > 0) {
					tooltip.append("\n");
				}
				tooltip.append("Libera: ").append(String.join(", ", salientes));
			}
			MallaDiagramaNodoDTO nodo = entry.getValue();
			nodo.setTieneDependencias(true);
			nodo.setDependenciaTooltip(tooltip.toString());
		}
	}

	private String colorToRgba(String hex, double alpha) {
		String value = hex.replace("#", "");
		if (value.length() != 6) {
			return hex;
		}
		int r = Integer.parseInt(value.substring(0, 2), 16);
		int g = Integer.parseInt(value.substring(2, 4), 16);
		int b = Integer.parseInt(value.substring(4, 6), 16);
		return "rgba(" + r + "," + g + "," + b + "," + alpha + ")";
	}

	public List<MallaDiagramaNodoDTO> getNodos() {
		return nodos;
	}

	public int getDiagramWidth() {
		return diagramWidth;
	}

	public int getDiagramHeight() {
		return diagramHeight;
	}

	public String getPlanNombre() {
		return planNombre;
	}

	public List<MallaDiagramaTipoDTO> getTiposPrograma() {
		return tiposPrograma;
	}

	public List<MallaDiagramaTipoDTO> getEstatusPrograma() {
		return estatusPrograma;
	}

	public Map<String, Integer> getCreditosAprobadosPorTipo() {
		return creditosAprobadosPorTipo;
	}

	public List<Map.Entry<String, Integer>> getCreditosAprobadosPorTipoEntries() {
		if (creditosAprobadosPorTipo == null) {
			return new ArrayList<>();
		}
		return new ArrayList<>(creditosAprobadosPorTipo.entrySet());
	}

	public List<CreditoTipoResumenDTO> getResumenCreditosPorTipo() {
		List<CreditoTipoResumenDTO> resumen = new ArrayList<>();
		if (creditosPlanPorTipo == null || creditosPlanPorTipo.isEmpty()) {
			return resumen;
		}
		for (Map.Entry<String, Integer> entry : creditosPlanPorTipo.entrySet()) {
			String tipo = entry.getKey();
			int total = entry.getValue() != null ? entry.getValue().intValue() : 0;
			int aprobados = 0;
			if (creditosAprobadosPorTipo != null && creditosAprobadosPorTipo.containsKey(tipo)
					&& creditosAprobadosPorTipo.get(tipo) != null) {
				aprobados = creditosAprobadosPorTipo.get(tipo).intValue();
			}
			resumen.add(new CreditoTipoResumenDTO(formatearEtiquetaTipoPlural(tipo), aprobados, total));
		}
		return resumen;
	}

	public int getCreditosAprobadosTotal() {
		return creditosAprobadosTotal;
	}

	public int getCreditosPlanTotal() {
		return creditosPlanTotal;
	}

	public double getPromedioActual() {
		if (totalCalificaciones == 0) {
			return 0d;
		}
		return sumaCalificaciones / totalCalificaciones;
	}

	public String getPromedioActualFormateado() {
		return PROMEDIO_FORMAT.format(getPromedioActual());
	}

	public Integer getSemestreEnCurso() {
		return semestreEnCurso;
	}

	public String getEstatusEstudiante() {
		return estatusEstudiante;
	}

	public String getDescripcionSituacionAcademica() {
		return "Irregular".equalsIgnoreCase(StringUtils.trimToEmpty(estatusEstudiante))
				? "Situación académica con irregularidad (existencia de UD no acreditadas)"
				: "Situación académica con regularidad";
	}

	public List<SemestreTablaDTO> getSemestresTabla() {
		return semestresTabla;
	}

	public String getEstatusColorByCss(String estatusCss) {
		return resolveColorByEstatusCss(estatusCss);
	}

	public boolean isVistaGestor() {
		return vistaGestor;
	}

	public String getNombrePersonaObjetivo() {
		return nombrePersonaObjetivo;
	}

	public String getMatriculaPersonaObjetivo() {
		return matriculaPersonaObjetivo;
	}

	public boolean esPeriodoCursamiento() {
		return contextoAsistente != null && Boolean.TRUE.equals(contextoAsistente.getInscripcionVigente());
	}

	public String getEtiquetaPeriodoContextual() {
		return esPeriodoCursamiento() ? "Periodo de cursamiento activo" : "Periodo de inscripción/reinscripción";
	}

	public String getEtiquetaModoContextual() {
		if (vistaGestor) {
			return esPeriodoCursamiento() ? "Seguimiento" : "Gestión";
		}
		return esPeriodoCursamiento() ? "Consulta" : "Operativo";
	}

	public String getEtiquetaEstadoPeriodo() {
		return esPeriodoCursamiento() ? "En curso" : "Activo";
	}

	public String getEtiquetaPeriodoBadge() {
		return esPeriodoCursamiento() ? "Cursamiento activo" : "Inscripción activa";
	}

	public String getTituloVistaMalla() {
		return vistaGestor ? "Malla curricular estudiante | Vista gestor" : "Malla curricular estudiante";
	}

	public String getSubtituloContextual() {
		if (vistaGestor) {
			return "Consulta académica contextual para " + StringUtils.defaultIfBlank(nombrePersonaObjetivo, matriculaPersonaObjetivo);
		}
		return "Consulta tu trayectoria curricular y revisa cómo cada unidad didáctica influye en tu avance académico.";
	}

	public FichaIntegralCasoDTO getFichaIntegralV2() {
		asegurarFichaIntegralV2();
		return fichaIntegralV2;
	}

	public boolean isTieneFichaIntegralV2() {
		asegurarFichaIntegralV2();
		return fichaIntegralV2 != null;
	}

	public boolean isMostrarPrevencionEstudianteV2() {
		asegurarFichaIntegralV2();
		return !vistaGestor && fichaIntegralV2 != null;
	}

	public boolean isMostrarSeguimientoCasoEstudianteV2() {
		asegurarFichaIntegralV2();
		return !vistaGestor && fichaIntegralV2 != null;
	}

	public String getEstatusCasoEstudianteV2() {
		asegurarFichaIntegralV2();
		if (fichaIntegralV2 == null || fichaIntegralV2.getCasoAcademico() == null
				|| StringUtils.isBlank(fichaIntegralV2.getCasoAcademico().getEstatusCaso())) {
			return "Orientación general";
		}
		return fichaIntegralV2.getCasoAcademico().getEstatusCaso().trim();
	}

	public String getMensajeCasoEstudianteV2() {
		asegurarFichaIntegralV2();
		if (fichaIntegralV2 == null || StringUtils.isBlank(fichaIntegralV2.getMensajeEstudiante())) {
			return "Tu trayectoria no muestra un riesgo académico inmediato en este momento.";
		}
		return fichaIntegralV2.getMensajeEstudiante().trim();
	}

	public String getResolucionCasoEstudianteV2() {
		asegurarFichaIntegralV2();
		if (fichaIntegralV2 == null || StringUtils.isBlank(fichaIntegralV2.getAccionSugerida())) {
			return "Mantén tu avance actual y revisa el siguiente periodo conforme a tu trayectoria.";
		}
		return fichaIntegralV2.getAccionSugerida().trim();
	}

	public String getResumenPrevencionEstudianteV2() {
		asegurarFichaIntegralV2();
		if (fichaIntegralV2 == null) {
			return getMensajePanelContextual();
		}
		StringBuilder texto = new StringBuilder();
		if (StringUtils.isNotBlank(fichaIntegralV2.getMensajeEstudiante())) {
			texto.append(fichaIntegralV2.getMensajeEstudiante().trim());
		}
		if (StringUtils.isNotBlank(fichaIntegralV2.getAccionSugerida())) {
			if (texto.length() > 0) {
				texto.append(" ");
			}
			texto.append("Siguiente paso: ").append(fichaIntegralV2.getAccionSugerida()).append(".");
		}
		if (texto.length() == 0) {
			return getMensajePanelContextual();
		}
		return texto.toString().trim();
	}

	public String getPrevencionTicketEstudianteV2() {
		asegurarFichaIntegralV2();
		if (vistaGestor) {
			return null;
		}
		StringBuilder texto = new StringBuilder();
		texto.append("Antes de pedir apoyo adicional revisa el detalle contextual de la UD, confirma la regla aplicada");
		if (fichaIntegralV2 != null && StringUtils.isNotBlank(fichaIntegralV2.getReglaAplicada())) {
			texto.append(" (").append(fichaIntegralV2.getReglaAplicada()).append(")");
		}
		texto.append(" y usa el asistente curricular para validar tu siguiente paso.");
		return texto.toString();
	}

	private String formatearEtiquetaTipoPlural(String tipo) {
		String normalizado = StringUtils.defaultString(tipo).trim();
		if (StringUtils.isBlank(normalizado)) {
			return "Sin tipo";
		}
		String clave = normalizaTexto(normalizado);
		if (clave.contains("obligat")) {
			return "Obligatorias";
		}
		if (clave.contains("optativ") || clave.contains("opcional")) {
			return "Optativas";
		}
		if (clave.contains("electiv")) {
			return "Electivas";
		}
		return normalizado;
	}

	public String getLlamadoAsistente() {
		return esPeriodoCursamiento() ? "Consultar orientación" : "Abrir asistente curricular";
	}

	public String getMensajePanelContextual() {
		return "Consulta el detalle contextual de cada unidad didáctica para identificar su situación académica, la recomendación asociada y su impacto en tu trayectoria.";
	}

	public String getEtiquetaCreditosResumen() {
		return "Créditos requeridos del programa";
	}

	public String irAsistenteCurricular() {
		if (trayectoriaAcademicaContextoBean != null) {
			trayectoriaAcademicaContextoBean.configurarContextoAsistenteDesdeMalla(
					StringUtils.trimToNull(asistenteClaveSeleccionada),
					StringUtils.trimToNull(asistenteNombreSeleccionado),
					StringUtils.trimToNull(asistenteEstadoSeleccionado),
					StringUtils.trimToNull(asistenteTipoSeleccionado),
					StringUtils.trimToNull(asistenteMotivoSeleccionado));
		}
		return "TABLA_CURRICULAR_ASISTIDA";
	}

	private String asistenteClaveSeleccionada;
	private String asistenteNombreSeleccionado;
	private String asistenteEstadoSeleccionado;
	private String asistenteTipoSeleccionado;
	private String asistenteMotivoSeleccionado;

	public String getAsistenteClaveSeleccionada() {
		return asistenteClaveSeleccionada;
	}

	public void setAsistenteClaveSeleccionada(String asistenteClaveSeleccionada) {
		this.asistenteClaveSeleccionada = asistenteClaveSeleccionada;
	}

	public String getAsistenteNombreSeleccionado() {
		return asistenteNombreSeleccionado;
	}

	public void setAsistenteNombreSeleccionado(String asistenteNombreSeleccionado) {
		this.asistenteNombreSeleccionado = asistenteNombreSeleccionado;
	}

	public String getAsistenteEstadoSeleccionado() {
		return asistenteEstadoSeleccionado;
	}

	public void setAsistenteEstadoSeleccionado(String asistenteEstadoSeleccionado) {
		this.asistenteEstadoSeleccionado = asistenteEstadoSeleccionado;
	}

	public String getAsistenteTipoSeleccionado() {
		return asistenteTipoSeleccionado;
	}

	public void setAsistenteTipoSeleccionado(String asistenteTipoSeleccionado) {
		this.asistenteTipoSeleccionado = asistenteTipoSeleccionado;
	}

	public String getAsistenteMotivoSeleccionado() {
		return asistenteMotivoSeleccionado;
	}

	public void setAsistenteMotivoSeleccionado(String asistenteMotivoSeleccionado) {
		this.asistenteMotivoSeleccionado = asistenteMotivoSeleccionado;
	}

	public static class CreditoTipoResumenDTO implements java.io.Serializable {
		private static final long serialVersionUID = 1L;
		private String etiqueta;
		private int aprobados;
		private int total;

		public CreditoTipoResumenDTO(String etiqueta, int aprobados, int total) {
			this.etiqueta = etiqueta;
			this.aprobados = aprobados;
			this.total = total;
		}

		public String getEtiqueta() {
			return etiqueta;
		}

		public int getAprobados() {
			return aprobados;
		}

		public int getTotal() {
			return total;
		}
	}

	private String resolveColorByEstatusCss(String estatusCss) {
		if (StringUtils.isBlank(estatusCss)) {
			return "#6b7280";
		}
		if ("estatus-aprobada".equals(estatusCss) || "estatus-acreditada".equals(estatusCss)) {
			return "#15A449";
		}
		if ("estatus-no-acreditada".equals(estatusCss)) {
			return "#FF0000";
		}
		if ("estatus-no-inscrita".equals(estatusCss)) {
			return "#506172";
		}
		if ("estatus-baja".equals(estatusCss)) {
			return "#ED9A55";
		}
		if ("estatus-en-curso".equals(estatusCss)) {
			return "#C19E00";
		}
		if ("estatus-bloqueada".equals(estatusCss)) {
			return "#000000";
		}
		return "#6b7280";
	}

	public FECServiceFacade getFecServiceFacade() {
		return fecServiceFacade;
	}

	public void setFecServiceFacade(FECServiceFacade fecServiceFacade) {
		this.fecServiceFacade = fecServiceFacade;
	}

	public InscripcionService getInscripcionService() {
		return inscripcionService;
	}

	public void setInscripcionService(InscripcionService inscripcionService) {
		this.inscripcionService = inscripcionService;
	}

	public InscripcionPreviaMateriasService getInscripcionPreviaMateriasService() {
		return inscripcionPreviaMateriasService;
	}

	public void setInscripcionPreviaMateriasService(InscripcionPreviaMateriasService inscripcionPreviaMateriasService) {
		this.inscripcionPreviaMateriasService = inscripcionPreviaMateriasService;
	}

	public GrupoParticipanteService getGrupoParticipanteService() {
		return grupoParticipanteService;
	}

	public void setGrupoParticipanteService(GrupoParticipanteService grupoParticipanteService) {
		this.grupoParticipanteService = grupoParticipanteService;
	}

	public AsistenteInscripcionService getAsistenteInscripcionService() {
		return asistenteInscripcionService;
	}

	public void setAsistenteInscripcionService(AsistenteInscripcionService asistenteInscripcionService) {
		this.asistenteInscripcionService = asistenteInscripcionService;
	}

	public TrayectoriaAcademicaContextoBean getTrayectoriaAcademicaContextoBean() {
		return trayectoriaAcademicaContextoBean;
	}

	public void setTrayectoriaAcademicaContextoBean(TrayectoriaAcademicaContextoBean trayectoriaAcademicaContextoBean) {
		this.trayectoriaAcademicaContextoBean = trayectoriaAcademicaContextoBean;
	}

	public MallaCurricularV2Facade getMallaCurricularV2Facade() {
		return mallaCurricularV2Facade;
	}

	public void setMallaCurricularV2Facade(MallaCurricularV2Facade mallaCurricularV2Facade) {
		this.mallaCurricularV2Facade = mallaCurricularV2Facade;
	}

	public static class SemestreTablaDTO {
		private int numero;
		private String titulo;
		private List<ProgramaTablaDTO> filas;

		public SemestreTablaDTO(int numero, String titulo) {
			this.numero = numero;
			this.titulo = titulo;
			this.filas = new ArrayList<>();
		}

		public int getNumero() {
			return numero;
		}

		public String getTitulo() {
			return titulo;
		}

		public List<ProgramaTablaDTO> getFilas() {
			return filas;
		}
	}

	public static class ProgramaTablaDTO {
		private int bloque;
		private String asignatura;
		private String clave;
		private Integer creditos;
		private String estatus;
		private String calificacion;
		private String estatusCss;
		private String estatusIconClass;
		private String estatusColor;
		private boolean seriada;
		private String dependenciaTooltip;
		private String tipoBorderColor;

		public int getBloque() {
			return bloque;
		}

		public void setBloque(int bloque) {
			this.bloque = bloque;
		}

		public String getAsignatura() {
			return asignatura;
		}

		public void setAsignatura(String asignatura) {
			this.asignatura = asignatura;
		}

		public String getClave() {
			return clave;
		}

		public void setClave(String clave) {
			this.clave = clave;
		}

		public Integer getCreditos() {
			return creditos;
		}

		public void setCreditos(Integer creditos) {
			this.creditos = creditos;
		}

		public String getEstatus() {
			return estatus;
		}

		public void setEstatus(String estatus) {
			this.estatus = estatus;
		}

		public String getCalificacion() {
			return calificacion;
		}

		public void setCalificacion(String calificacion) {
			this.calificacion = calificacion;
		}

		public String getEstatusCss() {
			return estatusCss;
		}

		public void setEstatusCss(String estatusCss) {
			this.estatusCss = estatusCss;
		}

		public String getEstatusIconClass() {
			return estatusIconClass;
		}

		public void setEstatusIconClass(String estatusIconClass) {
			this.estatusIconClass = estatusIconClass;
		}

		public boolean isSeriada() {
			return seriada;
		}

		public void setSeriada(boolean seriada) {
			this.seriada = seriada;
		}

		public String getDependenciaTooltip() {
			return dependenciaTooltip;
		}

		public void setDependenciaTooltip(String dependenciaTooltip) {
			this.dependenciaTooltip = dependenciaTooltip;
		}

		public String getTipoBorderColor() {
			return tipoBorderColor;
		}

		public void setTipoBorderColor(String tipoBorderColor) {
			this.tipoBorderColor = tipoBorderColor;
		}

		public String getEstatusColor() {
			return estatusColor;
		}

		public void setEstatusColor(String estatusColor) {
			this.estatusColor = estatusColor;
		}
	}
}
