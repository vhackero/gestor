package mx.gob.sedesol.gestorweb.listeners;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.faces.component.UIViewRoot;

import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import org.apache.log4j.Logger;

/**
 * PhaseListener que registra los tiempos de cada fase JSF para una petición.
 * Ayuda a identificar si el costo está en restaurar vista, validar, renderizar,
 * etc.
 */
public class PerformancePhaseListener implements PhaseListener {

	private static final long serialVersionUID = 1L;

	private static final Logger log = Logger.getLogger(PerformancePhaseListener.class);

	private static final String ATTR_TOTAL_START = "perfPhaseListener.totalStart";
	private static final String ATTR_PHASE_PREFIX = "perfPhaseListener.phaseStart.";
	private static final String ATTR_PHASE_LOGS = "perfPhaseListener.phaseLogs";

	@Override
	public void beforePhase(PhaseEvent event) {
		Map<String, Object> requestMap = event.getFacesContext().getExternalContext().getRequestMap();
		if (!requestMap.containsKey(ATTR_TOTAL_START)) {
			requestMap.put(ATTR_TOTAL_START, System.currentTimeMillis());
		}
		requestMap.put(ATTR_PHASE_PREFIX + event.getPhaseId().getOrdinal(), System.currentTimeMillis());

		@SuppressWarnings("unchecked")
		List<String> logs = (List<String>) requestMap.get(ATTR_PHASE_LOGS);
		if (logs == null) {
			logs = new ArrayList<>();
			requestMap.put(ATTR_PHASE_LOGS, logs);
		}
	}

	@Override
	public void afterPhase(PhaseEvent event) {
		Map<String, Object> requestMap = event.getFacesContext().getExternalContext().getRequestMap();

		Long inicioFase = (Long) requestMap.get(ATTR_PHASE_PREFIX + event.getPhaseId().getOrdinal());
		if (inicioFase == null) {
			return;
		}
		long duracionFase = System.currentTimeMillis() - inicioFase;

		@SuppressWarnings("unchecked")
		List<String> logs = (List<String>) requestMap.get(ATTR_PHASE_LOGS);
		if (logs != null) {
			logs.add(event.getPhaseId().getName() + "=" + duracionFase + "ms");
		}

		if (PhaseId.RENDER_RESPONSE.equals(event.getPhaseId())) {
			Long inicioTotal = (Long) requestMap.get(ATTR_TOTAL_START);
			long total = inicioTotal != null ? (System.currentTimeMillis() - inicioTotal) : duracionFase;
			String viewId = event.getFacesContext().getViewRoot() != null
					? event.getFacesContext().getViewRoot().getViewId()
					: "N/D";
			boolean ajax = event.getFacesContext().getPartialViewContext().isAjaxRequest();
			long estadoBytes = estimateViewStateSize(event);
			log.info(new StringBuilder("JSF_METRICAS -> viewId=").append(viewId).append(", ajax=").append(ajax)
					.append(", totalMs=").append(total).append(", fases=").append(logs)
					.append(", estadoBytes=").append(estadoBytes));
		}
	}

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.ANY_PHASE;
	}

	private long estimateViewStateSize(PhaseEvent event) {
		try {
			UIViewRoot root = event.getFacesContext().getViewRoot();
			if (root == null) {
				return -1;
			}
			// Serializa el estado de vista para estimar tamaño en bytes
			java.io.ByteArrayOutputStream bos = new java.io.ByteArrayOutputStream();
			java.io.ObjectOutputStream oos = new java.io.ObjectOutputStream(bos);
			oos.writeObject(root.saveState(event.getFacesContext()));
			oos.flush();
			return bos.toByteArray().length;
		} catch (Exception e) {
			log.debug("No se pudo estimar tamaño de estado de vista", e);
			return -1;
		}
	}
}
