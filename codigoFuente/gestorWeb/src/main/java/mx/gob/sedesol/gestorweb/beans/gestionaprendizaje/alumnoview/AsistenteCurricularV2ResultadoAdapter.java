package mx.gob.sedesol.gestorweb.beans.gestionaprendizaje.alumnoview;

import java.util.List;

import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.CodigoMotivoCurricularV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.DecisionUnidadCurricularV2DTO;
import mx.gob.sedesol.gestorweb.beans.gestionaprendizaje.alumnoview.TablaCurricularAsistidaBean.FilaAsistidaDTO;
import mx.gob.sedesol.gestorweb.beans.gestionaprendizaje.alumnoview.TablaCurricularAsistidaBean.MotivoVisualDTO;

/** Traduce decisiones V2 en recomendaciones del Asistente, sin recalcular reglas. */
public class AsistenteCurricularV2ResultadoAdapter {
	public void aplicar(FilaAsistidaDTO fila, DecisionUnidadCurricularV2DTO decision) {
		if (fila == null || decision == null) return;
		boolean habilitada = Boolean.TRUE.equals(decision.getHabilitada());
		boolean ofertaDisponible = decision.getOfertaEvaluadaV2() == null ? habilitada : Boolean.TRUE.equals(decision.getOfertaEvaluadaV2());
		boolean restriccionAcademica = !habilitada && tieneRestriccionAcademica(decision.getMotivos());
		fila.setBloqueada(Boolean.valueOf(restriccionAcademica));
		fila.setSeleccionable(Boolean.valueOf(habilitada && ofertaDisponible));
		if (restriccionAcademica || !ofertaDisponible) fila.setRecomendada(Boolean.FALSE);
		if (Boolean.TRUE.equals(decision.getPrioritaria())) {
			fila.setPrioritaria(Boolean.TRUE);
			fila.setPrioridad("PRIORITARIA");
		}
		if (!decision.getMotivos().isEmpty()) {
			String motivo = describirMotivos(decision.getMotivos());
			fila.setMotivoPrincipal(motivo);
			MotivoVisualDTO visual = new MotivoVisualDTO();
			visual.setCodigo("V2");
			visual.setSeveridad(restriccionAcademica ? "RESTRICCION" : "INFORMACION");
			visual.setMensajeCorto(motivo);
			visual.setMensajeDetallado(motivo);
			visual.setBloqueante(Boolean.valueOf(restriccionAcademica));
			fila.getMotivos().add(0, visual);
		}
	}

	private boolean tieneRestriccionAcademica(List<CodigoMotivoCurricularV2> motivos) {
		if (motivos.isEmpty()) return true;
		for (CodigoMotivoCurricularV2 motivo : motivos) {
			if (motivo != CodigoMotivoCurricularV2.SIN_OFERTA && motivo != CodigoMotivoCurricularV2.SIN_CUPO) return true;
		}
		return false;
	}

	private String describirMotivos(List<CodigoMotivoCurricularV2> motivos) {
		if (motivos.contains(CodigoMotivoCurricularV2.SERIACION_DIRECTA) || motivos.contains(CodigoMotivoCurricularV2.SERIACION_INDIRECTA)) return "No se recomienda porque requiere acreditar previamente sus prerrequisitos.";
		if (motivos.contains(CodigoMotivoCurricularV2.SIN_OFERTA)) return "La unidad didáctica puede cursarse académicamente, pero no tiene oferta disponible.";
		if (motivos.contains(CodigoMotivoCurricularV2.REPROBACION_ACUMULADA)) return "La prioridad actual es regularizar las unidades didácticas no acreditadas.";
		if (motivos.contains(CodigoMotivoCurricularV2.DATOS_INSUFICIENTES)) return "La información curricular disponible requiere revisión antes de recomendar esta unidad didáctica.";
		return "Esta unidad didáctica tiene condiciones curriculares que deben revisarse antes de recomendarla.";
	}
}
