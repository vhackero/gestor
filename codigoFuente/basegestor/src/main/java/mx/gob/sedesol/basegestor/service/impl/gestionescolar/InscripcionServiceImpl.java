package mx.gob.sedesol.basegestor.service.impl.gestionescolar;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.AprobacionAsignaturasPorSemestreDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.CreditosTotalesPlanDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.ConfiguracionElectivaDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.EstadoInscripcionEstudianteDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.InscripcionBajasDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.InscripcionPersonaDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.InscripcionInsertDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.InscripcionMateriasCursadasDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.InscripcionMateriasDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.InscripcionMateriasInsDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.InscripcionMateriasReprobadasDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.LimitesCargaAcademicaDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.IntentosAsignaturasDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.MallaAlumnoProgramaDTO;
import mx.gob.sedesol.basegestor.model.repositories.gestionescolar.IinscripcionRepository;
import mx.gob.sedesol.basegestor.service.gestionescolar.InscripcionService;

/**
 * 
 * 
 * @author ITTIVA
 * 
 */
@Service("inscripcionService")
public class InscripcionServiceImpl implements InscripcionService {

	private static final Logger logger = Logger.getLogger(InscripcionServiceImpl.class);

	@Autowired
	private IinscripcionRepository inscripcionesRepository;

	@Override
	public List<ConfiguracionElectivaDTO> obtenerProcesosActivosConfiguracionElectivas() {
		return inscripcionesRepository.obtenerProcesosActivosConfiguracionElectivas();
	}

	@Override
	public List<ConfiguracionElectivaDTO> obtenerAsignaturasConfiguracionElectivas(Long idProcesoInscripcion) {
		return inscripcionesRepository.obtenerAsignaturasConfiguracionElectivas(idProcesoInscripcion);
	}

	@Override
	@Transactional
	public void guardarConfiguracionElectiva(ConfiguracionElectivaDTO configuracion) {
		inscripcionesRepository.guardarConfiguracionElectiva(configuracion);
	}

	@Override
	public boolean tieneCupoElectiva(Long idProcesoInscripcion, Long idPrograma) {
		return inscripcionesRepository.tieneCupoElectiva(idProcesoInscripcion, idPrograma);
	}

	@Override
	public InscripcionPersonaDTO obtenerInscripcionPorPersona(String idPersona) {

		List<InscripcionPersonaDTO> listaInformacionPersona = inscripcionesRepository
				.obtenerInscripcionPorPersona(idPersona);

		if (listaInformacionPersona == null || listaInformacionPersona.isEmpty()) {
			return new InscripcionPersonaDTO();
		}
		InscripcionPersonaDTO regresaPersona = listaInformacionPersona.get(0);
		return regresaPersona;
	}

	@Override
	public List<InscripcionMateriasDTO> consultarMaterias(String id_plan) {
		List<InscripcionMateriasDTO> listaMaterias = new ArrayList<>();

		listaMaterias = inscripcionesRepository.consultarMaterias(id_plan);

		if (listaMaterias == null) {
			return listaMaterias;
		}
		return listaMaterias;
	}

	@Override
	public List<InscripcionMateriasDTO> consultarMateriasPorConvocatoria(String id_plan, String id_convocatoria,
			String id_estructura) {
		List<InscripcionMateriasDTO> listaMaterias = new ArrayList<>();

		listaMaterias = inscripcionesRepository.consultarMateriasPorConvocatoria(id_plan, id_convocatoria,
				id_estructura);

		if (listaMaterias == null) {
			return listaMaterias;
		}
		return listaMaterias;
	}

	@Override
	public Optional<LimitesCargaAcademicaDTO> obtenerLimitesCargaAcademicaPorPlan(Long idPlan) {
		return inscripcionesRepository.obtenerLimitesCargaAcademicaPorPlan(idPlan);
	}

	@Override
	public List<InscripcionMateriasCursadasDTO> consultarMateriasCursadas(String id_plan) {
		List<InscripcionMateriasCursadasDTO> listaInscripcionMateriasPasadas = new ArrayList<>();

		listaInscripcionMateriasPasadas = inscripcionesRepository.consultarMateriasCursadas(id_plan);

		if (listaInscripcionMateriasPasadas == null) {
			return listaInscripcionMateriasPasadas;
		}
		return listaInscripcionMateriasPasadas;
	}

	@Override
	public List<InscripcionMateriasInsDTO> consultarMateriasInscritas(String idpersona, String plan) {
		List<InscripcionMateriasInsDTO> listaInscripcionMateriasIns = new ArrayList<>();

		listaInscripcionMateriasIns = inscripcionesRepository.consultarMateriasInscritas(idpersona, plan);

		if (listaInscripcionMateriasIns == null) {
			return listaInscripcionMateriasIns;
		}
		return listaInscripcionMateriasIns;
	}

	@Override
	public List<MallaAlumnoProgramaDTO> obtenerProgramasMallaAlumno(Long idPersona, Long idPlan) {
		List<MallaAlumnoProgramaDTO> lista = new ArrayList<>();

		lista = inscripcionesRepository.obtenerProgramasMallaAlumno(idPersona, idPlan);

		if (lista == null) {
			return lista;
		}
		return lista;
	}

	@Override
	public Long consultarNumeroEstructura(String id_plan_infopersona) {
		return inscripcionesRepository.consultarNumeroEstructura(id_plan_infopersona);
	}

	@Override
	public List<IntentosAsignaturasDTO> consultarIntentosAsignaturas(String idpersona) {
		List<IntentosAsignaturasDTO> listaIntentosAsignaturas = new ArrayList<>();

		listaIntentosAsignaturas = inscripcionesRepository.consultarIntentosAsignaturas(idpersona);

		if (listaIntentosAsignaturas == null) {
			return listaIntentosAsignaturas;
		}
		return listaIntentosAsignaturas;
	}

	@Override
	public Boolean consultarNuevoIngreso(String id_persona) {
		return inscripcionesRepository.consultarNuevoIngreso(id_persona);

	}

	@Override
	public List<InscripcionBajasDTO> consultarBajas(String idpersona) {
		List<InscripcionBajasDTO> listaBajasAsignaturas = new ArrayList<>();

		listaBajasAsignaturas = inscripcionesRepository.consultarBajas(idpersona);

		if (listaBajasAsignaturas == null) {
			return listaBajasAsignaturas;
		}
		return listaBajasAsignaturas;
	}

	@Override
	public void insertarInscripciones(List<InscripcionInsertDTO> inscripciones) {
		inscripciones.forEach(inscripcionesRepository::insertarRegistro);
	}

	@Override
	public List<InscripcionMateriasDTO> obtenerMateriasPorPeriodoInscripcion(Long idPlan, Date fechaActual,
			Long idConvocatoria) {
		return inscripcionesRepository.obtenerMateriasPorPeriodoInscripcion(idPlan, fechaActual, idConvocatoria);
	}

	@Override
	public Boolean esEstudianteRegular(Long idPersona) {
		return inscripcionesRepository.esEstudianteRegular(idPersona);
	}

	@Override
	public Boolean esEstudianteNuevoIngreso(Long idPersona) {
		return inscripcionesRepository.esEstudianteNuevoIngreso(idPersona);
	}

	@Override
	public Boolean existeInscripcionPrevia(InscripcionPersonaDTO infoPersona, Date fechaActual) {
		return inscripcionesRepository.existeInscripcionPrevia(infoPersona.getIdPersona(), infoPersona.getIdPlan(),
				infoPersona.getIdConvocatoria(), fechaActual);
	}

	@Override
	public List<InscripcionMateriasCursadasDTO> obtenerMateriasCursadas(Long idPersona) {
		return inscripcionesRepository.obtenerMateriasCursadas(idPersona);
	}

	@Override
	public List<InscripcionMateriasReprobadasDTO> obtenerMateriasCursadasReprobadas(Long idPersona) {
		return inscripcionesRepository.obtenerMateriasCursadasReprobadas(idPersona);
	}

	@Override
	public List<InscripcionBajasDTO> obtenerBajasDeMateriasSolicitadas(Long idPersona) {
		return inscripcionesRepository.obtenerBajasDeMateriasSolicitadas(idPersona);
	}

	@Override
	public Optional<CreditosTotalesPlanDTO> obtenerCreditosTotalesPorPlan(Long idPlan) {
		return inscripcionesRepository.obtenerCreditosTotalesPorPlan(idPlan);
	}

	@Override
	public List<InscripcionMateriasDTO> obtenerMateriasElectivasDeOtrosPlanes(Long idPlanPersona, Date fechaActual,
			Long idConvocatoria, String semestreCinco, String semestreSeis) {
		return inscripcionesRepository.obtenerMateriasElectivasDeOtrosPlanes(idPlanPersona, fechaActual, idConvocatoria,
				semestreCinco, semestreSeis);
	}

	@Override
	public Optional<EstadoInscripcionEstudianteDTO> obtenerEstadoInscripcionEstudiante(Long idPersona,
			Long idProcesoInscripcion) {
		return inscripcionesRepository.obtenerEstadoInscripcionEstudiante(idPersona, idProcesoInscripcion);
	}

	@Override
	public List<AprobacionAsignaturasPorSemestreDTO> obtenerAprobacionAsignaturasPorSemestre(Long idPlan,
			Long idPersona) {
		return inscripcionesRepository.obtenerAprobacionAsignaturasPorSemestre(idPlan, idPersona);
	}

}
