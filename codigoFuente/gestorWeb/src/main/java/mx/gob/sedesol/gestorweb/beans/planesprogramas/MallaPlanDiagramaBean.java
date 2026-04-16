package mx.gob.sedesol.gestorweb.beans.planesprogramas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.FichaDescProgramaDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.MallaCurricularDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.PlanDTO;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.ObjetoCurricularEnum;
import mx.gob.sedesol.basegestor.service.impl.planesyprogramas.FECServiceFacade;
import mx.gob.sedesol.gestorweb.beans.acceso.BaseBean;
import mx.gob.sedesol.gestorweb.commons.constantes.ConstantesGestorWeb;
import mx.gob.sedesol.gestorweb.commons.dto.MallaDiagramaNodoDTO;
import mx.gob.sedesol.gestorweb.commons.dto.MallaDiagramaTipoDTO;

@ManagedBean
@ViewScoped
public class MallaPlanDiagramaBean extends BaseBean {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(MallaPlanDiagramaBean.class);

	private static final int COL_WIDTH = 260;
	private static final int SEMESTRE_Y = 20;
	private static final int SEMESTRE_HEIGHT = 34;
	private static final int SEMESTRE_BLOQUE_GAP = 8;
	private static final int BLOQUE_BASE_HEIGHT = 32;
	private static final int PROGRAMA_BASE_HEIGHT = 60;
	private static final int PROGRAMA_GAP = 8;
	private static final int BLOQUE_GAP = 18;
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

	@ManagedProperty(value = "#{fecServiceFacade}")
	private FECServiceFacade fecServiceFacade;

	private List<MallaDiagramaNodoDTO> nodos;
	private int diagramWidth;
	private int diagramHeight;
	private PlanDTO planSelec;
	private List<MallaDiagramaTipoDTO> tiposPrograma;
	private Map<String, Integer> creditosPorTipo;
	private int creditosPlanTotal;

	@PostConstruct
	public void init() {
		nodos = new ArrayList<>();
		tiposPrograma = new ArrayList<>();
		creditosPorTipo = new LinkedHashMap<>();
		creditosPlanTotal = 0;

		planSelec = (PlanDTO) getSession().getAttribute(ConstantesGestorWeb.OBJ_PLAN_SELEC);
		if (ObjectUtils.isNull(planSelec)) {
			logger.warn("No se encontro plan seleccionado para generar la malla.");
			return;
		}
		getSession().removeAttribute(ConstantesGestorWeb.OBJ_PLAN_SELEC);

		MallaCurricularDTO raiz = fecServiceFacade.getMallaCurricularService()
				.obtenerMallaCurricularPorIdPlan(planSelec.getIdPlan());
		if (ObjectUtils.isNull(raiz)) {
			logger.warn("No se encontro malla curricular para el plan seleccionado.");
			return;
		}

		construirModelo(raiz);
	}

	private void construirModelo(MallaCurricularDTO raiz) {
		Map<Integer, MallaDiagramaNodoDTO> programasPorId = new HashMap<>();
		Map<Integer, String> programaNombrePorId = new HashMap<>();
		Map<Integer, List<String>> dependenciasEntrantes = new HashMap<>();
		Map<Integer, List<String>> dependenciasSalientes = new HashMap<>();
		List<FichaDescProgramaDTO> programasPlan = new ArrayList<>();
		Map<Integer, List<FichaDescProgramaDTO>> programasPorEje = new HashMap<>();
		Map<String, MallaDiagramaTipoDTO> tiposMap = new LinkedHashMap<>();
		int secuenciaLocal = 0;
		int maxX = 0;
		int maxY = 0;

		List<FichaDescProgramaDTO> programasPlanCompleto = fecServiceFacade.getFichaDescProgramaService()
				.buscarProgramasPorPlan(planSelec.getIdPlan());
		if (!ObjectUtils.isNullOrEmpty(programasPlanCompleto)) {
			programasPorEje = programasPlanCompleto.stream()
					.filter(p -> p.getEjeCapacitacion() != null)
					.collect(Collectors.groupingBy(FichaDescProgramaDTO::getEjeCapacitacion));
		}

		List<MallaCurricularDTO> semestres = filtraHijosPorTipo(raiz, ObjetoCurricularEnum.ESTRUCTURA);
		int col = 0;
		for (MallaCurricularDTO semestre : semestres) {
			int numeroSemestre = resolveNumeroSemestre(semestre.getNombre(), col + 1);
			int x = (col * COL_WIDTH) + SEMESTRE_X_OFFSET;
			int y = SEMESTRE_Y;

			String semestreId = nextElementId(ID_SEMESTRE_PREFIX, semestre.getId(), secuenciaLocal++);
			MallaDiagramaNodoDTO semestreNodo = new MallaDiagramaNodoDTO(
					semestreId, semestre.getNombre(), "SEMESTRE",
					x + ((NODE_WIDTH - SEMESTRE_WIDTH) / 2), y, SEMESTRE_WIDTH, SEMESTRE_HEIGHT);
			semestreNodo.setSemestre(numeroSemestre);
			semestreNodo.setTextoCompacto("S" + numeroSemestre);
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
					aplicaTipoPrograma(programaNodo, programa.getTipo(), tiposMap);
					programaNodo.setCreditos(programa.getCreditos());
					programaNodo.setSemestre(numeroSemestre);
					programaNodo.setBloqueId(bloqueId);
					programaNodo.setTextoCompacto(StringUtils.defaultIfBlank(programa.getCvePrograma(), abrevia(nombrePrograma)));
					nodos.add(programaNodo);

					if (programa.getIdPrograma() != null) {
						programasPorId.put(programa.getIdPrograma(), programaNodo);
						programaNombrePorId.put(programa.getIdPrograma(), nombrePrograma);
					}
					programasPlan.add(programa);
					acumulaCreditos(programa);
					maxX = Math.max(maxX, programaX + NODE_WIDTH);
					maxY = Math.max(maxY, y + programaHeight);
					y += programaHeight + PROGRAMA_GAP;
				}

				y += BLOQUE_GAP;
			}
			col++;
		}

		conectarProgramas(programasPlan, programasPorId, programaNombrePorId, dependenciasEntrantes, dependenciasSalientes);
		tiposPrograma = new ArrayList<>(tiposMap.values());
		diagramWidth = maxX + DIAGRAMA_PADDING;
		diagramHeight = maxY + DIAGRAMA_PADDING;
	}

	private void conectarProgramas(List<FichaDescProgramaDTO> programasPlan, Map<Integer, MallaDiagramaNodoDTO> programasPorId,
			Map<Integer, String> programaNombrePorId,
			Map<Integer, List<String>> dependenciasEntrantes, Map<Integer, List<String>> dependenciasSalientes) {
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
			dependenciasEntrantes.computeIfAbsent(programa.getIdPrograma(), key -> new ArrayList<>())
					.add(programaNombrePorId.get(programa.getProgramaAntecedente().getIdPrograma()));
			dependenciasSalientes.computeIfAbsent(programa.getProgramaAntecedente().getIdPrograma(), key -> new ArrayList<>())
					.add(programaNombrePorId.get(programa.getIdPrograma()));
		}
		for (Map.Entry<Integer, MallaDiagramaNodoDTO> entry : programasPorId.entrySet()) {
			MallaDiagramaNodoDTO nodo = entry.getValue();
			List<String> entrantes = dependenciasEntrantes.get(entry.getKey());
			List<String> salientes = dependenciasSalientes.get(entry.getKey());
			if (ObjectUtils.isNullOrEmpty(entrantes) && ObjectUtils.isNullOrEmpty(salientes)) {
				continue;
			}
			nodo.setTieneDependencias(true);
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
			nodo.setDependenciaTooltip(tooltip.toString());
		}
	}

	private void acumulaCreditos(FichaDescProgramaDTO programa) {
		if (programa == null || programa.getCreditos() == null) {
			return;
		}
		creditosPlanTotal += programa.getCreditos();
		String tipo = StringUtils.isBlank(programa.getTipo()) ? "Sin tipo" : programa.getTipo().trim();
		Integer acumulado = creditosPorTipo.get(tipo);
		creditosPorTipo.put(tipo, (acumulado == null ? 0 : acumulado) + programa.getCreditos());
	}

	private boolean esMismaColumna(MallaDiagramaNodoDTO a, MallaDiagramaNodoDTO b) {
		if (a == null || b == null) {
			return false;
		}
		return Math.abs(a.getX() - b.getX()) < 40;
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

	private String normalizaTipoPrograma(String tipo) {
		if (StringUtils.isBlank(tipo)) {
			return "";
		}
		String normalizado = tipo.trim().toLowerCase().replaceAll("[^a-z0-9]+", "-");
		return " tipo-" + normalizado;
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

	private String abrevia(String texto) {
		if (StringUtils.isBlank(texto)) {
			return "";
		}
		String limpio = texto.trim();
		return limpio.length() <= 6 ? limpio : limpio.substring(0, 6).toUpperCase();
	}

	private int resolveNumeroSemestre(String nombre, int fallback) {
		return parseNumero(nombre, fallback);
	}

	private int resolveNumeroBloque(String nombre, int fallback) {
		return parseNumero(nombre, fallback);
	}

	private int parseNumero(String valor, int fallback) {
		if (StringUtils.isBlank(valor)) {
			return fallback;
		}
		String numeros = valor.replaceAll("[^0-9]", "");
		if (StringUtils.isBlank(numeros)) {
			return fallback;
		}
		try {
			return Integer.parseInt(numeros);
		} catch (NumberFormatException e) {
			return fallback;
		}
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
		nodo.setTipoPrograma(tipoNormalizado);
		nodo.setTipoBackgroundColor(tipoDTO.getBackgroundColor());
		nodo.setTipoBorderColor(tipoDTO.getBorderColor());
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

	public List<MallaDiagramaTipoDTO> getTiposPrograma() {
		return tiposPrograma;
	}

	public Map<String, Integer> getCreditosPorTipo() {
		return creditosPorTipo;
	}

	public List<Map.Entry<String, Integer>> getCreditosPorTipoEntries() {
		return new ArrayList<>(creditosPorTipo.entrySet());
	}

	public int getCreditosPlanTotal() {
		return creditosPlanTotal;
	}

	public PlanDTO getPlanSelec() {
		return planSelec;
	}

	public void setPlanSelec(PlanDTO planSelec) {
		this.planSelec = planSelec;
	}

	public FECServiceFacade getFecServiceFacade() {
		return fecServiceFacade;
	}

	public void setFecServiceFacade(FECServiceFacade fecServiceFacade) {
		this.fecServiceFacade = fecServiceFacade;
	}
}
