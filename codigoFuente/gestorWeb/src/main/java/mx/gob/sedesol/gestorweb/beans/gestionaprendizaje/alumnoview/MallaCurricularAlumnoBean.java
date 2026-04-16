package mx.gob.sedesol.gestorweb.beans.gestionaprendizaje.alumnoview;

import java.text.DecimalFormat;
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

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.InscripcionPersonaDTO;
import mx.gob.sedesol.basegestor.commons.dto.inscripcion.InscripcionPreviaMateriasDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.MallaAlumnoProgramaDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.FichaDescProgramaDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.MallaCurricularDTO;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.ObjetoCurricularEnum;
import mx.gob.sedesol.basegestor.service.gestionescolar.InscripcionService;
import mx.gob.sedesol.basegestor.service.inscripcion.InscripcionPreviaMateriasService;
import mx.gob.sedesol.basegestor.service.impl.planesyprogramas.FECServiceFacade;
import mx.gob.sedesol.gestorweb.beans.acceso.BaseBean;
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

	private List<MallaDiagramaNodoDTO> nodos;
	private int diagramWidth;
	private int diagramHeight;
	private String planNombre;
	private Long planId;
	private List<MallaDiagramaTipoDTO> tiposPrograma;
	private List<MallaDiagramaTipoDTO> estatusPrograma;
	private Map<String, Integer> creditosAprobadosPorTipo;
	private int creditosAprobadosTotal;
	private int creditosPlanTotal;
	private double sumaCalificaciones;
	private int totalCalificaciones;
	private Set<String> asignaturasEnCurso;
	private Integer semestreEnCurso;
	private Integer bloqueEnCurso;
	private String estatusEstudiante;
	private List<SemestreTablaDTO> semestresTabla;

	@PostConstruct
	public void init() {
		nodos = new ArrayList<>();
		tiposPrograma = new ArrayList<>();
		estatusPrograma = new ArrayList<>();
		creditosAprobadosPorTipo = new LinkedHashMap<>();
		creditosAprobadosTotal = 0;
		creditosPlanTotal = 0;
		sumaCalificaciones = 0d;
		totalCalificaciones = 0;
		asignaturasEnCurso = new HashSet<>();
		semestreEnCurso = null;
		bloqueEnCurso = null;
		estatusEstudiante = "Regular";
		semestresTabla = new ArrayList<>();

		Long idPersona = idPersonaEnSesion();
		InscripcionPersonaDTO inscripcion = inscripcionService.obtenerInscripcionPorPersona(String.valueOf(idPersona));
		if (inscripcion == null || inscripcion.getIdPlan() == null) {
			logger.warn("No se encontro plan de estudios para el alumno en sesion.");
			return;
		}
		planId = inscripcion.getIdPlan();
		planNombre = inscripcion.getPlan();

		MallaCurricularDTO raiz = fecServiceFacade.getMallaCurricularService()
				.obtenerMallaCurricularPorIdPlan(planId.intValue());
		if (ObjectUtils.isNull(raiz)) {
			logger.warn("No se encontro malla curricular para el plan seleccionado.");
			return;
		}

		cargarInscripcionesEnCurso(idPersona);
		construirModelo(raiz, idPersona);
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
			if (semestre > maxSemestre) {
				maxSemestre = semestre;
			}
			int bloqueMateria = parseNumero(materia.getBloque(), 0);
			if (bloqueEnCurso != null && bloqueEnCurso.intValue() == bloqueMateria
					&& StringUtils.isNotBlank(materia.getAsignatura())) {
				asignaturasEnCurso.add(normalizaTexto(limpiaAsignatura(materia.getAsignatura())));
			}
		}
		if (maxSemestre > 0) {
			semestreEnCurso = maxSemestre;
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
							idPrograma != null ? estatusPorPrograma.get(idPrograma) : null);
					programaNodo.setCreditos(programa.getCreditos());
					programaNodo.setTextoCompacto(resolveTextoCompacto(programa, nombrePrograma));
					if (StringUtils.isNotBlank(nombrePrograma)
							&& asignaturasEnCurso.contains(normalizaTexto(nombrePrograma))) {
						programaNodo.setEnCurso(true);
					}
					acumularCreditos(programa.getTipo(), programa.getCreditos(),
							estatusPorPrograma.get(idPrograma));
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

	private void acumularCreditos(String tipo, Integer creditos, MallaAlumnoProgramaDTO estatusDto) {
		if (creditos == null) {
			return;
		}
		creditosPlanTotal += creditos;

		acumularPromedio(estatusDto);
		if (!esProgramaAprobado(estatusDto)) {
			return;
		}
		String tipoNormalizado = StringUtils.isBlank(tipo) ? "Sin tipo" : tipo.trim();
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

	private void aplicaEstatusPrograma(MallaDiagramaNodoDTO nodo, MallaAlumnoProgramaDTO estatusDto) {
		String estatus = ESTATUS_NO_INSCRITA;
		Double calificacionFinal = null;
		Double calificacionMin = null;
		if (estatusDto != null) {
			calificacionFinal = estatusDto.getCalificacionFinal();
			calificacionMin = estatusDto.getCalificacionMinAprobatoria();
		}
		if (calificacionFinal != null) {
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

	private void aplicarEstatusEnCurso(Map<Integer, MallaDiagramaNodoDTO> programasPorId) {
		for (MallaDiagramaNodoDTO nodo : programasPorId.values()) {
			if (nodo.isEnCurso()) {
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
			if (nodo.isEnCurso() || ESTATUS_APROBADA.equals(nodo.getEstatus())) {
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

	public Integer getSemestreEnCurso() {
		return semestreEnCurso;
	}

	public String getEstatusEstudiante() {
		return estatusEstudiante;
	}

	public List<SemestreTablaDTO> getSemestresTabla() {
		return semestresTabla;
	}

	public String getEstatusColorByCss(String estatusCss) {
		return resolveColorByEstatusCss(estatusCss);
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
