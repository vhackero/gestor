package mx.gob.sedesol.basegestor.service.gestionescolar;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.AprobacionAsignaturasPorSemestreDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.CreditosTotalesPlanDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.ConfiguracionElectivaDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.ReglaInscripcionDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.EstadoInscripcionEstudianteDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.InscripcionBajasDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.InscripcionPersonaDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.InscripcionInsertDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.InscripcionMateriasDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.InscripcionMateriasInsDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.InscripcionMateriasReprobadasDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.InscripcionMateriasCursadasDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.LimitesCargaAcademicaDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.MallaAlumnoProgramaDTO;
import mx.gob.sedesol.basegestor.commons.dto.inscripcion.ReenvioCorreoInscripcionDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.IntentosAsignaturasDTO;

/**
 * 
 * @author ITTIVA
 * 
 */
public interface InscripcionService {

	InscripcionPersonaDTO obtenerInscripcionPorPersona(String idPersona);

	List<InscripcionMateriasDTO> consultarMaterias(String id_plan);

	Optional<LimitesCargaAcademicaDTO> obtenerLimitesCargaAcademicaPorPlan(Long idPlan);

	List<InscripcionMateriasCursadasDTO> consultarMateriasCursadas(String id_plan);

	List<InscripcionMateriasInsDTO> consultarMateriasInscritas(String idpersona, String plan);

	Long consultarNumeroEstructura(String id_plan_infopersona);

	List<IntentosAsignaturasDTO> consultarIntentosAsignaturas(String idpersona);

	Boolean consultarNuevoIngreso(String id_persona);

	List<InscripcionBajasDTO> consultarBajas(String idpersona);

	List<InscripcionMateriasDTO> consultarMateriasPorConvocatoria(String id_plan, String id_convocatoria,
			String id_estructura);

	void insertarInscripciones(List<InscripcionInsertDTO> inscripciones);

	List<InscripcionMateriasDTO> obtenerMateriasPorPeriodoInscripcion(Long idPlan, Date fechaActual,
			Long idConvocatoria);

	Boolean esEstudianteRegular(Long idPersona);

	Boolean esEstudianteNuevoIngreso(Long idPersona);

	Boolean existeInscripcionPrevia(InscripcionPersonaDTO infoPersona, Date fechaActual);

	List<InscripcionMateriasCursadasDTO> obtenerMateriasCursadas(Long idPersona);

	List<InscripcionMateriasReprobadasDTO> obtenerMateriasCursadasReprobadas(Long idPersona);

	List<InscripcionMateriasDTO> obtenerMateriasElectivasDeOtrosPlanes(Long idPlanPersona, Date fechaActual,
			Long idConvocatoria, String semestreCinco, String semestreSeis);

	List<InscripcionBajasDTO> obtenerBajasDeMateriasSolicitadas(Long idPersona);

	Optional<CreditosTotalesPlanDTO> obtenerCreditosTotalesPorPlan(Long idPlan);

	Optional<EstadoInscripcionEstudianteDTO> obtenerEstadoInscripcionEstudiante(Long idPersona,
			Long idProcesoInscripcion);

	List<AprobacionAsignaturasPorSemestreDTO> obtenerAprobacionAsignaturasPorSemestre(Long idPlan, Long idPersona);

	List<MallaAlumnoProgramaDTO> obtenerProgramasMallaAlumno(Long idPersona, Long idPlan);

	List<ConfiguracionElectivaDTO> obtenerProcesosActivosConfiguracionElectivas();

	List<ConfiguracionElectivaDTO> obtenerAsignaturasConfiguracionElectivas(Long idProcesoInscripcion);

	void guardarConfiguracionElectiva(ConfiguracionElectivaDTO configuracion);

	boolean tieneCupoElectiva(Long idProcesoInscripcion, Long idPrograma);

	List<ReglaInscripcionDTO> obtenerReglasInscripcion();

	boolean reglaInscripcionActiva(String clave);

	void guardarReglaInscripcion(ReglaInscripcionDTO regla, Long idUsuario);

}
