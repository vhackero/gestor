package mx.gob.sedesol.gestorweb.beans.gestionaprendizaje.alumnoview;

import java.io.Serializable;
import java.util.List;

import javax.servlet.http.HttpSession;

import mx.gob.sedesol.basegestor.commons.dto.admin.CatalogoComunDTO;
import mx.gob.sedesol.basegestor.commons.dto.encuestas.RelEncuestaUsuarioDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestion.aprendizaje.AmbienteVirtualAprendizajeDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.EventoCapacitacionDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.HistorialAcademicoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.RelGrupoParticipanteDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.TiraMateriaBajaDTO;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;

/**
 * Maneja el guardado y recuperación temporal de los datos de MisCursosBean
 * dentro de la sesión, así como la coordinación para evitar inicializaciones
 * simultáneas.
 */
public final class MisCursosSnapshotContext {

	private static final String SESSION_CACHE_KEY = "MIS_CURSOS_BEAN_CACHE";
	private static final String SESSION_INIT_FLAG = "MIS_CURSOS_INIT_EN_PROCESO";
	private static final long CACHE_TTL_MS = 4000L;
	private static final long ESPERA_MAXIMA_INICIALIZACION_MS = 5000L;
	private static final long ESPERA_INTERVALO_MS = 100L;

	private MisCursosSnapshotContext() {
	}

	public static MisCursosSnapshot obtenerSnapshot(HttpSession session) {
		return (MisCursosSnapshot) session.getAttribute(SESSION_CACHE_KEY);
	}

	public static void guardarSnapshot(HttpSession session, MisCursosSnapshot snapshot) {
		session.setAttribute(SESSION_CACHE_KEY, snapshot);
	}

	public static boolean esperarInicializacionEnProceso(HttpSession session) {
		boolean seEspero = false;
		long inicio = System.currentTimeMillis();
		while (Boolean.TRUE.equals(session.getAttribute(SESSION_INIT_FLAG))
				&& (System.currentTimeMillis() - inicio) < ESPERA_MAXIMA_INICIALIZACION_MS) {
			seEspero = true;
			try {
				Thread.sleep(ESPERA_INTERVALO_MS);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				break;
			}
		}
		return seEspero;
	}

	public static void marcarInicializacionEnProceso(HttpSession session) {
		session.setAttribute(SESSION_INIT_FLAG, Boolean.TRUE);
	}

	public static void limpiarBanderaInicializacion(HttpSession session) {
		session.removeAttribute(SESSION_INIT_FLAG);
	}

	public static long obtenerCacheTtlMs() {
		return CACHE_TTL_MS;
	}

	public static final class MisCursosSnapshot implements Serializable {
		private static final long serialVersionUID = 1L;
		private final Long idPersona;
		private final List<CatalogoComunDTO> catEstadoEventoCapacitacionList;
		private final List<RelGrupoParticipanteDTO> participanteEventosCapacitacionEnEjecucion;
		private final List<RelGrupoParticipanteDTO> participanteEventosCapacitacionEnEjecucion2;
		private final List<RelGrupoParticipanteDTO> tiraMateriasConsolidada;
		private final List<String> nombresEstructurasCurriculares;
		private final List<EventoCapacitacionDTO> eventoCapacitacionList;
		private final CatalogoComunDTO estatusEcSeleccionado;
		private final List<RelEncuestaUsuarioDTO> relEncuestaUsuarioEvtConcList;
		private final List<RelEncuestaUsuarioDTO> relEncuestaUsuarioEvtEnEjecList;
		private final List<CatalogoComunDTO> encuestaTipoList;
		private final CatalogoComunDTO tipoEncuestaSeleccionado;
		private final Integer idEstatusSeleccionado;
		private final Boolean esColumnaCompetenciasVisible;
		private final List<AmbienteVirtualAprendizajeDTO> avaList;
		private final HistorialAcademicoDTO tiraMaterias;
		private final List<TiraMateriaBajaDTO> tiraMateriasBaja;
		private final long timestamp;

		public MisCursosSnapshot(Long idPersona, List<CatalogoComunDTO> catEstadoEventoCapacitacionList,
				List<RelGrupoParticipanteDTO> participanteEventosCapacitacionEnEjecucion,
				List<RelGrupoParticipanteDTO> participanteEventosCapacitacionEnEjecucion2,
				List<RelGrupoParticipanteDTO> tiraMateriasConsolidada,
				List<String> nombresEstructurasCurriculares,
				List<EventoCapacitacionDTO> eventoCapacitacionList, CatalogoComunDTO estatusEcSeleccionado,
				List<RelEncuestaUsuarioDTO> relEncuestaUsuarioEvtConcList,
				List<RelEncuestaUsuarioDTO> relEncuestaUsuarioEvtEnEjecList,
				List<CatalogoComunDTO> encuestaTipoList, CatalogoComunDTO tipoEncuestaSeleccionado,
				Integer idEstatusSeleccionado, Boolean esColumnaCompetenciasVisible,
				List<AmbienteVirtualAprendizajeDTO> avaList, HistorialAcademicoDTO tiraMaterias,
				List<TiraMateriaBajaDTO> tiraMateriasBaja, long timestamp) {
			this.idPersona = idPersona;
			this.catEstadoEventoCapacitacionList = catEstadoEventoCapacitacionList;
			this.participanteEventosCapacitacionEnEjecucion = participanteEventosCapacitacionEnEjecucion;
			this.participanteEventosCapacitacionEnEjecucion2 = participanteEventosCapacitacionEnEjecucion2;
			this.tiraMateriasConsolidada = tiraMateriasConsolidada;
			this.nombresEstructurasCurriculares = nombresEstructurasCurriculares;
			this.eventoCapacitacionList = eventoCapacitacionList;
			this.estatusEcSeleccionado = estatusEcSeleccionado;
			this.relEncuestaUsuarioEvtConcList = relEncuestaUsuarioEvtConcList;
			this.relEncuestaUsuarioEvtEnEjecList = relEncuestaUsuarioEvtEnEjecList;
			this.encuestaTipoList = encuestaTipoList;
			this.tipoEncuestaSeleccionado = tipoEncuestaSeleccionado;
			this.idEstatusSeleccionado = idEstatusSeleccionado;
			this.esColumnaCompetenciasVisible = esColumnaCompetenciasVisible;
			this.avaList = avaList;
			this.tiraMaterias = tiraMaterias;
			this.tiraMateriasBaja = tiraMateriasBaja;
			this.timestamp = timestamp;
		}

		public boolean esValidoPara(Long idPersonaActual, long ttlMs) {
			return ObjectUtils.isNotNull(idPersonaActual) && idPersonaActual.equals(this.idPersona)
					&& (System.currentTimeMillis() - this.timestamp) <= ttlMs;
		}

		public List<CatalogoComunDTO> getCatEstadoEventoCapacitacionList() {
			return catEstadoEventoCapacitacionList;
		}

		public List<RelGrupoParticipanteDTO> getParticipanteEventosCapacitacionEnEjecucion() {
			return participanteEventosCapacitacionEnEjecucion;
		}

		public List<RelGrupoParticipanteDTO> getParticipanteEventosCapacitacionEnEjecucion2() {
			return participanteEventosCapacitacionEnEjecucion2;
		}

		public List<RelGrupoParticipanteDTO> getTiraMateriasConsolidada() {
			return tiraMateriasConsolidada;
		}

		public List<String> getNombresEstructurasCurriculares() {
			return nombresEstructurasCurriculares;
		}

		public List<EventoCapacitacionDTO> getEventoCapacitacionList() {
			return eventoCapacitacionList;
		}

		public CatalogoComunDTO getEstatusEcSeleccionado() {
			return estatusEcSeleccionado;
		}

		public List<RelEncuestaUsuarioDTO> getRelEncuestaUsuarioEvtConcList() {
			return relEncuestaUsuarioEvtConcList;
		}

		public List<RelEncuestaUsuarioDTO> getRelEncuestaUsuarioEvtEnEjecList() {
			return relEncuestaUsuarioEvtEnEjecList;
		}

		public List<CatalogoComunDTO> getEncuestaTipoList() {
			return encuestaTipoList;
		}

		public CatalogoComunDTO getTipoEncuestaSeleccionado() {
			return tipoEncuestaSeleccionado;
		}

		public Integer getIdEstatusSeleccionado() {
			return idEstatusSeleccionado;
		}

		public Boolean getEsColumnaCompetenciasVisible() {
			return esColumnaCompetenciasVisible;
		}

		public List<AmbienteVirtualAprendizajeDTO> getAvaList() {
			return avaList;
		}

		public HistorialAcademicoDTO getTiraMaterias() {
			return tiraMaterias;
		}

		public List<TiraMateriaBajaDTO> getTiraMateriasBaja() {
			return tiraMateriasBaja;
		}
	}
}
