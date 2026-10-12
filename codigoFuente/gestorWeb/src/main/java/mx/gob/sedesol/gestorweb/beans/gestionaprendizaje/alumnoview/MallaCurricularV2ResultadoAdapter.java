package mx.gob.sedesol.gestorweb.beans.gestionaprendizaje.alumnoview;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.DecisionUnidadCurricularV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EstadoDecisionUnidadCurricularV2;
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
				nodo.setDetalleMotivoPrincipal("Restricción curricular V2: " + decision.getMotivos());
			}
			if (Boolean.TRUE.equals(decision.getPrioritaria())) {
				String recomendacion = StringUtils.defaultString(nodo.getDetalleAccionSugerida());
				nodo.setDetalleAccionSugerida((recomendacion + " UD prioritaria para regularización según V2.").trim());
			}
		}
		return aplicacion;
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
