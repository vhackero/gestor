package mx.gob.sedesol.basegestor.model.repositories.gestionescolar;

import java.time.LocalDateTime;
import java.util.List;

import mx.gob.sedesol.basegestor.commons.dto.inscripcion.ModificacionInscripcionDTO;
import mx.gob.sedesol.basegestor.commons.dto.inscripcion.ReenvioCorreoInscripcionDTO;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.Convocatoria;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.ConvocatoriaNivelEducativo;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.ConvocatoriaParamConsulta;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.ConvocatoriaTableroResumen;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.InscripcionParamNueva;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.InscripcionPlanesProgramas;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.InscripcionesConsultaResumen;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.InscripcionesTableroResumen;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.TipoProceso;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.TblFichaDescriptivaPrograma;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.TblPlan;

public interface IinscripcionesRepository {

	List<TipoProceso> consultarTipoProceso();

	List<TipoProceso> consultarTipoProcesoDisponibles(Integer convocatoriaId, LocalDateTime fechaActual);

	int contarProcesosPorTipo(Integer convocatoriaId, int tipoProcesoId);

	LocalDateTime obtenerFechaFinProcesoOrdinario(Integer convocatoriaId);

	Integer obtenerSiguienteConsecutivoProceso();

	boolean existePlanProgramaExtraordinario(Integer convocatoriaId, int idPlan, int idPrograma,
			LocalDateTime fechaActual);

	List<TblPlan> consultarPlan(InscripcionParamNueva inscripcionParamNueva);

	List<InscripcionPlanesProgramas> consultarPlanPrograma(InscripcionParamNueva inscripcionParamNueva);

	// List<TblFichaDescriptivaPrograma> consultarPrograma();

	List<InscripcionesTableroResumen> consultarTableroResumen(ConvocatoriaParamConsulta tableroParamConsulta);

	List<InscripcionesConsultaResumen> consultarFiltros(ConvocatoriaParamConsulta tableroParamConsulta);

	List<InscripcionesTableroResumen> altaInscripciones(InscripcionParamNueva inscripcionParamNueva);

	void altaInscripcion(InscripcionParamNueva inscripcionParamNueva);

	void altaInscripcionExtra(InscripcionParamNueva inscripcionParamNueva);

	List<TipoProceso> consultarNombre(ConvocatoriaParamConsulta tableroParamConsulta);

	Object getProcesoInscripcionById(Long procesoInscripcionId);

	void updateProcesoInscripcion(Long procesoInscripcionId, String nombre, LocalDateTime fechaInicio,
			LocalDateTime fechaFin, int estatus, Long idTipoProceso, Long convocatoriaId);

	void deleteProcesoInscripcion(Long procesoInscripcionId, Long convocatoriaId, String tipoProceso);

	List<ReenvioCorreoInscripcionDTO> obtenerInscripcionesParaReenvioConPaginacion(
			Long idProcesoInscripcionSeleccionado, Long estatusEnvio, Long idPlanSeleccionado,
			Long idProgramaSeleccionado, String matricula, int first, int pageSize);

	Long contarInscripcionesParaReenvio(Long idProcesoInscripcionSeleccionado, Long estatusEnvio,
			Long idPlanSeleccionado, Long idProgramaSeleccionado, String matricula);

	List<ModificacionInscripcionDTO> obtenerMateriasParaModificarInscripcion(Long idPersona, Long idProcesoInscripcion);
	
	void eliminarInscripcionesPorIds(List<Long> ids);

}
