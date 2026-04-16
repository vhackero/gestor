package mx.gob.sedesol.basegestor.model.repositories.gestionescolar;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.AprobacionAsignaturasPorSemestreDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.CreditosTotalesPlanDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.EstadoInscripcionEstudianteDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.InscripcionBajasDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.InscripcionPersonaDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.InscripcionInsertDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.InscripcionMateriasDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.InscripcionMateriasInsDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.InscripcionMateriasReprobadasDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.InscripcionMateriasCursadasDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.MallaAlumnoProgramaDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.LimitesCargaAcademicaDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.IntentosAsignaturasDTO;

public interface IinscripcionRepository {

	List<InscripcionPersonaDTO> obtenerInscripcionPorPersona(String idPersona);

	List<InscripcionMateriasDTO> consultarMaterias(String id_plan);

	Optional<LimitesCargaAcademicaDTO> obtenerLimitesCargaAcademicaPorPlan(Long idPlan);

	List<InscripcionMateriasCursadasDTO> consultarMateriasCursadas(String id_persona);

	List<InscripcionMateriasInsDTO> consultarMateriasInscritas(String idpersona, String plan);

	Long consultarNumeroEstructura(String id_plan_infopersona);

	List<IntentosAsignaturasDTO> consultarIntentosAsignaturas(String id_persona);

	Boolean consultarNuevoIngreso(String id_persona);

	List<InscripcionBajasDTO> consultarBajas(String id_persona);

	List<InscripcionMateriasDTO> consultarMateriasPorConvocatoria(String id_plan, String id_convocatoria,
			String id_convocatoria2);

	void insertarRegistro(InscripcionInsertDTO dto);

	List<InscripcionMateriasDTO> obtenerMateriasPorPeriodoInscripcion(Long idPlan, Date fechaActual,
			Long idConvocatoria);

	List<InscripcionMateriasDTO> obtenerMateriasElectivasDeOtrosPlanes(Long idPlanActual, Date fechaActual,
			Long idConvocatoria, String semestreCinco, String semestreSeis);

	Boolean esEstudianteRegular(Long idPersona);

	Boolean esEstudianteNuevoIngreso(Long idPersona);

	Boolean existeInscripcionPrevia(Long idPersona, Long idPlan, Long idConvocatoria, Date fechaActual);

	List<InscripcionMateriasCursadasDTO> obtenerMateriasCursadas(Long idPersona);

	List<InscripcionMateriasReprobadasDTO> obtenerMateriasCursadasReprobadas(Long idPersona);

	List<InscripcionBajasDTO> obtenerBajasDeMateriasSolicitadas(Long idPersona);

	Optional<CreditosTotalesPlanDTO> obtenerCreditosTotalesPorPlan(Long idPlan);

	Optional<EstadoInscripcionEstudianteDTO> obtenerEstadoInscripcionEstudiante(Long idPersona,
			Long idProcesoInscripcion);
	
	List<AprobacionAsignaturasPorSemestreDTO> obtenerAprobacionAsignaturasPorSemestre(Long idPlan, Long idPersona);

	List<MallaAlumnoProgramaDTO> obtenerProgramasMallaAlumno(Long idPersona, Long idPlan);

}
