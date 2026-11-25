package mx.gob.sedesol.gestorweb.beans.gestionaprendizaje.alumnoview;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.CodigoMotivoCurricularV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.DecisionUnidadCurricularV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EstadoDecisionUnidadCurricularV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.TrazaReglaAplicadaV2DTO;
import mx.gob.sedesol.gestorweb.commons.dto.MallaDiagramaNodoDTO;

/** Adaptador sin estado entre decisiones V2 y la representación visual existente de la Malla. */
public class MallaCurricularV2ResultadoAdapter {

	private static final String ESTATUS_APROBADA = "Aprobada";
	private static final String ESTATUS_NO_ACREDITADA = "No acreditada";
	private static final String ESTATUS_NO_INSCRITA = "No inscrita";
	private static final String ESTATUS_BAJA = "Baja";
	private static final String ESTATUS_EN_CURSO = "En curso";
	private static final String ESTATUS_BLOQUEADA = "Bloqueada";
	private static final String COLOR_BLOQUEADA = "#000000";

	public String normalizarEstatusDetalle(String estado, MallaDiagramaNodoDTO programaNodo) {
		String valor = StringUtils.trimToEmpty(estado);
		// El estado histórico prevalece sobre etiquetas operativas de la Malla.
		if (programaNodo != null && ESTATUS_NO_ACREDITADA.equals(programaNodo.getEstatus())) return ESTATUS_NO_ACREDITADA;
		if (programaNodo != null && ESTATUS_APROBADA.equals(programaNodo.getEstatus())) return ESTATUS_APROBADA;
		if (programaNodo != null && ESTATUS_BAJA.equals(programaNodo.getEstatus())) return ESTATUS_BAJA;
		if (programaNodo != null && ESTATUS_EN_CURSO.equals(programaNodo.getEstatus())) return ESTATUS_EN_CURSO;
		if (programaNodo != null && ESTATUS_NO_INSCRITA.equals(programaNodo.getEstatus())) return ESTATUS_NO_INSCRITA;
		if (StringUtils.equalsIgnoreCase(valor, ESTATUS_APROBADA) || StringUtils.equalsIgnoreCase(valor, "Acreditada")) return ESTATUS_APROBADA;
		if (StringUtils.equalsIgnoreCase(valor, ESTATUS_NO_ACREDITADA) || StringUtils.equalsIgnoreCase(valor, "Pendiente de acreditar")) return ESTATUS_NO_ACREDITADA;
		if (StringUtils.equalsIgnoreCase(valor, ESTATUS_NO_INSCRITA)) return ESTATUS_NO_INSCRITA;
		if (StringUtils.equalsIgnoreCase(valor, ESTATUS_BAJA) || StringUtils.containsIgnoreCase(valor, "baja")) return ESTATUS_BAJA;
		if (StringUtils.equalsIgnoreCase(valor, ESTATUS_EN_CURSO)) return ESTATUS_EN_CURSO;
		if (StringUtils.equalsIgnoreCase(valor, ESTATUS_BLOQUEADA)
				|| StringUtils.equalsIgnoreCase(valor, "RECOMENDADA")
				|| StringUtils.equalsIgnoreCase(valor, "Opcional")
				|| StringUtils.equalsIgnoreCase(valor, "Pendiente de cursar")
				|| StringUtils.equalsIgnoreCase(valor, "Pendiente de validación")
				|| StringUtils.equalsIgnoreCase(valor, "Seguimiento de trayectoria")) return "Por cursar";
		if (programaNodo != null && programaNodo.isBloqueada()) return "Por cursar";
		return "Por cursar";
	}

	/**
	 * Conserva el resultado académico vigente y agrega la condición operativa
	 * cuando la UD reprobada se encuentra recursándose en el periodo actual.
	 */
	public String construirEstatusContextual(String estatusHistorico, String estatusPeriodo,
			MallaDiagramaNodoDTO programaNodo) {
		String historico = normalizarEstatusDetalle(estatusHistorico, programaNodo);
		boolean enCurso = StringUtils.equalsIgnoreCase(StringUtils.trimToEmpty(estatusPeriodo), ESTATUS_EN_CURSO)
				|| (programaNodo != null && programaNodo.isEnCurso());
		if (ESTATUS_NO_ACREDITADA.equals(historico) && enCurso) {
			return ESTATUS_NO_ACREDITADA + " (en curso)";
		}
		return normalizarEstatusDetalle(estatusPeriodo, programaNodo);
	}

	public AplicacionResultadoMallaV2 aplicar(Map<Integer, MallaDiagramaNodoDTO> programasPorId,
			Map<Long, DecisionUnidadCurricularV2DTO> decisionesPorPrograma, String situacionAcademica) {
		AplicacionResultadoMallaV2 aplicacion = new AplicacionResultadoMallaV2(situacionAcademica);
		for (Map.Entry<Integer, MallaDiagramaNodoDTO> entry : programasPorId.entrySet()) {
			MallaDiagramaNodoDTO nodo = entry.getValue();
			DecisionUnidadCurricularV2DTO decision = decisionesPorPrograma.get(Long.valueOf(entry.getKey().longValue()));
			if (nodo == null || decision == null) {
				continue;
			}
			contarEstatus(aplicacion, nodo);
			if (Boolean.TRUE.equals(decision.getHabilitada())) {
				aplicacion.habilitadas++;
			} else {
				aplicacion.restringidas++;
				nodo.setBloqueada(true);
				if (!conservaEstatusHistorico(nodo.getEstatus())) {
					aplicarEstatusBloqueado(nodo);
				}
			}
			if (decision.getEstadosSombra().contains(EstadoDecisionUnidadCurricularV2.REQUIERE_REVISION)) {
				aplicacion.requiereRevision++;
			}
			if (!decision.getMotivos().isEmpty()) {
				nodo.setDetalleMotivoPrincipal(construirMotivoVisible(decision, nodo));
			}
			if (Boolean.TRUE.equals(decision.getPrioritaria())) {
				String recomendacion = StringUtils.defaultString(nodo.getDetalleAccionSugerida());
				nodo.setDetalleAccionSugerida((recomendacion + " UD prioritaria para regularización según las reglas aplicables.").trim());
			}
		}
		return aplicacion;
	}

	private String construirMotivoVisible(DecisionUnidadCurricularV2DTO decision, MallaDiagramaNodoDTO nodo) {
		List<String> mensajes = new ArrayList<String>();
		for (CodigoMotivoCurricularV2 motivo : decision.getMotivos()) {
			String mensaje = describirMotivo(motivo, nodo);
			if (StringUtils.isNotBlank(mensaje) && !mensajes.contains(mensaje)) {
				mensajes.add(mensaje);
			}
		}
		String reglas = obtenerReglasAplicadas(decision);
		String detalle = StringUtils.join(mensajes, " ");
		if (StringUtils.isBlank(detalle)) {
			detalle = "La unidad didáctica tiene una restricción curricular que debe revisarse antes de seleccionarla.";
		}
		return StringUtils.isBlank(reglas) ? detalle : detalle + " Regla aplicable: " + reglas + ".";
	}

	private String describirMotivo(CodigoMotivoCurricularV2 motivo, MallaDiagramaNodoDTO nodo) {
		if (motivo == null) {
			return null;
		}
		switch (motivo) {
		case REPROBACION_ACUMULADA:
			return ESTATUS_NO_ACREDITADA.equals(nodo.getEstatus())
					? "Esta unidad didáctica está reprobada y debe priorizarse su regularización."
					: "Existen unidades didácticas reprobadas pendientes de regularización.";
		case DATOS_INSUFICIENTES:
		case NO_DETERMINADO:
		case ESTRUCTURA_PE_NO_DETERMINADA:
			return "La información curricular disponible requiere revisión para completar esta evaluación.";
		case SERIACION_DIRECTA:
			return "Requiere acreditar previamente una unidad didáctica antecedente.";
		case SERIACION_INDIRECTA:
			return "Requiere acreditar los antecedentes curriculares de su cadena de seriación.";
		case SIN_OFERTA:
			return "La unidad didáctica no tiene oferta disponible en el periodo consultado.";
		case SIN_CUPO:
			return "La unidad didáctica no tiene cupo disponible en el periodo consultado.";
		default:
			return "Esta unidad didáctica tiene una condición curricular que debe revisarse antes de seleccionarla.";
		}
	}

	private String obtenerReglasAplicadas(DecisionUnidadCurricularV2DTO decision) {
		List<String> reglas = new ArrayList<String>();
		for (TrazaReglaAplicadaV2DTO traza : decision.getTrazas()) {
			if (traza == null || traza.getCodigoRegla() == null) {
				continue;
			}
			String regla = formatearCodigoRegla(traza.getCodigoRegla().name());
			if (!reglas.contains(regla)) {
				reglas.add(regla);
			}
		}
		return StringUtils.join(reglas, ", ");
	}

	private String formatearCodigoRegla(String codigo) {
		String[] partes = StringUtils.split(codigo, '_');
		if (partes == null || partes.length < 2) {
			return codigo;
		}
		StringBuilder regla = new StringBuilder(partes[0]).append('-').append(partes[1]);
		for (int indice = 2; indice < partes.length; indice++) {
			regla.append('.').append(partes[indice]);
		}
		return regla.toString();
	}

	private void contarEstatus(AplicacionResultadoMallaV2 aplicacion, MallaDiagramaNodoDTO nodo) {
		if (ESTATUS_APROBADA.equals(nodo.getEstatus())) {
			aplicacion.acreditadas++;
		}
		if (ESTATUS_NO_ACREDITADA.equals(nodo.getEstatus())) {
			aplicacion.reprobadas++;
		}
	}

	private boolean conservaEstatusHistorico(String estatus) {
		return ESTATUS_APROBADA.equals(estatus) || ESTATUS_NO_ACREDITADA.equals(estatus)
				|| ESTATUS_NO_INSCRITA.equals(estatus) || ESTATUS_BAJA.equals(estatus) || ESTATUS_EN_CURSO.equals(estatus);
	}

	private void aplicarEstatusBloqueado(MallaDiagramaNodoDTO nodo) {
		nodo.setEstatus(ESTATUS_BLOQUEADA);
		nodo.setEstatusBackgroundColor(COLOR_BLOQUEADA);
		nodo.setEstatusBorderColor(COLOR_BLOQUEADA);
		nodo.setEstatusIconClass("fa fa-lock");
	}

	public static class AplicacionResultadoMallaV2 {
		private final String situacionAcademica;
		private int acreditadas;
		private int reprobadas;
		private int restringidas;
		private int habilitadas;
		private int requiereRevision;

		private AplicacionResultadoMallaV2(String situacionAcademica) {
			this.situacionAcademica = situacionAcademica;
		}

		public String getSituacionAcademica() { return situacionAcademica; }
		public int getAcreditadas() { return acreditadas; }
		public int getReprobadas() { return reprobadas; }
		public int getRestringidas() { return restringidas; }
		public int getHabilitadas() { return habilitadas; }
		public int getRequiereRevision() { return requiereRevision; }
	}
}
