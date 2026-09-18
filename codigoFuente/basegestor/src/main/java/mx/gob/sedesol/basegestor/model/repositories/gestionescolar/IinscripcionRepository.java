package mx.gob.sedesol.basegestor.model.repositories.gestionescolar;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.AprobacionAsignaturasPorSemestreDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.CreditosTotalesPlanDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.ConfiguracionElectivaDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.ConfiguracionCargaNuevoIngresoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.ConfiguracionCargaIrregularDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.ConfiguracionCargaRegularDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.ReglaInscripcionDTO;
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

	Boolean esEstudianteRegular(Long idPersona, Long idPlan);

	/** Semestre pendiente de reincorporación por baja temporal contabilizable, o null. */
	Integer obtenerSemestreBajaTemporalPendiente(Long idPersona, Long idPlan);

	Boolean esEstudianteNuevoIngreso(Long idPersona, Long idPlan);

	Boolean tienePrimerSemestrePendiente(Long idPersona, Long idPlan);

	Boolean existeInscripcionPrevia(Long idPersona, Long idPlan, Long idConvocatoria, Date fechaActual);

	/** Serializa la finalización de inscripciones de una persona dentro de la transacción. */
	void bloquearPersonaParaInscripcion(Long idPersona);

	/** Incluye cualquier materia del proceso, incluso electivas de otro plan. */
	Boolean existeInscripcionEnProceso(Long idPersona, Long idProcesoInscripcion);


	List<InscripcionMateriasCursadasDTO> obtenerMateriasCursadas(Long idPersona);

	List<InscripcionMateriasReprobadasDTO> obtenerMateriasCursadasReprobadas(Long idPersona, Long idPlan);

	List<InscripcionBajasDTO> obtenerBajasDeMateriasSolicitadas(Long idPersona);

	Optional<CreditosTotalesPlanDTO> obtenerCreditosTotalesPorPlan(Long idPlan);

	void guardarCreditosTotalesPorPlan(Long idPlan, Long totalCreditos);

	void actualizarCreditosTotalesPorPlan(Long idPlan, Long totalCreditos);

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

	List<ConfiguracionCargaNuevoIngresoDTO> obtenerConfiguracionesCargaNuevoIngreso();

	ConfiguracionCargaNuevoIngresoDTO obtenerConfiguracionGeneralCargaNuevoIngreso();

	List<ConfiguracionCargaNuevoIngresoDTO> obtenerPlanesDisponiblesCargaNuevoIngreso();

	ConfiguracionCargaNuevoIngresoDTO obtenerConfiguracionCargaNuevoIngreso(Long idPlan, Long idPersona);

	void guardarConfiguracionCargaNuevoIngreso(ConfiguracionCargaNuevoIngresoDTO configuracion, Long idUsuario);

	void guardarConfiguracionGeneralCargaNuevoIngreso(ConfiguracionCargaNuevoIngresoDTO configuracion, Long idUsuario);

	void eliminarConfiguracionCargaNuevoIngreso(ConfiguracionCargaNuevoIngresoDTO configuracion);

	ConfiguracionCargaRegularDTO obtenerConfiguracionGeneralCargaRegular();

	List<ConfiguracionCargaRegularDTO> obtenerConfiguracionesCargaRegular();

	List<ConfiguracionCargaRegularDTO> obtenerPlanesDisponiblesCargaRegular();

	ConfiguracionCargaRegularDTO obtenerConfiguracionCargaRegular(Long idPlan);

	void guardarConfiguracionGeneralCargaRegular(ConfiguracionCargaRegularDTO configuracion, Long idUsuario);

	void guardarConfiguracionCargaRegular(ConfiguracionCargaRegularDTO configuracion, Long idUsuario);

	void eliminarConfiguracionCargaRegular(ConfiguracionCargaRegularDTO configuracion);

	ConfiguracionCargaIrregularDTO obtenerConfiguracionGeneralCargaIrregular();

	List<ConfiguracionCargaIrregularDTO> obtenerConfiguracionesCargaIrregular();

	List<ConfiguracionCargaIrregularDTO> obtenerPlanesDisponiblesCargaIrregular();

	ConfiguracionCargaIrregularDTO obtenerConfiguracionCargaIrregular(Long idPlan);

	void guardarConfiguracionGeneralCargaIrregular(ConfiguracionCargaIrregularDTO configuracion, Long idUsuario);

	void guardarConfiguracionCargaIrregular(ConfiguracionCargaIrregularDTO configuracion, Long idUsuario);

	void eliminarConfiguracionCargaIrregular(ConfiguracionCargaIrregularDTO configuracion);

	ConfiguracionCargaRegularDTO obtenerConfiguracionRestriccionesAcademicasGenerales();

	void guardarConfiguracionRestriccionesAcademicasGenerales(ConfiguracionCargaRegularDTO configuracion,
			Long idUsuario);

}
