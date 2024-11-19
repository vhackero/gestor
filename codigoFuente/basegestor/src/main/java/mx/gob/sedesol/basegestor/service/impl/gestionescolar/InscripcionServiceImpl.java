package mx.gob.sedesol.basegestor.service.impl.gestionescolar;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.HistorialAcademicoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.InscripcionBajasDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.InscripcionDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.InscripcionInsertDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.InscripcionMateriasDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.InscripcionMateriasInsDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.InscripcionMateriasPasadasDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.InscripcionMaxMinDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.IntentosAsignaturasDTO;
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
	public InscripcionDTO consultaInformacionPersona(String id_persona) {
		
		List<InscripcionDTO> listaInformacionPersona = inscripcionesRepository.consultarTipoProceso(id_persona);
		
		if (listaInformacionPersona == null ) {
			return new InscripcionDTO();
		}
		InscripcionDTO regresaPersona = listaInformacionPersona.get(0);
		return regresaPersona;
	}
	
	@Override
	public List<InscripcionMateriasDTO> consultarMaterias(String id_plan) {
		List<InscripcionMateriasDTO> listaMaterias = new ArrayList<>();

		listaMaterias = inscripcionesRepository.consultarMaterias(id_plan);
		
		if (listaMaterias == null ) {
			return listaMaterias;
		}
		return listaMaterias;
	}
	
	@Override
	public List<InscripcionMateriasDTO> consultarMateriasPorConvocatoria(String id_plan, String id_convocatoria, String id_estructura) {
		List<InscripcionMateriasDTO> listaMaterias = new ArrayList<>();

		listaMaterias = inscripcionesRepository.consultarMateriasPorConvocatoria(id_plan, id_convocatoria, id_estructura);
		
		if (listaMaterias == null ) {
			return listaMaterias;
		}
		return listaMaterias;
	}
	
	@Override
	public InscripcionMaxMinDTO consultarMaxMin(String id_plan) {
		
		InscripcionMaxMinDTO consultaMaxMin = inscripcionesRepository.consultarMaxMin(id_plan);
		
		if (consultaMaxMin == null ) {
			return new InscripcionMaxMinDTO();
		}
		
		return consultaMaxMin;
	}
	
	@Override
	public List<InscripcionMateriasPasadasDTO> consultarMateriasCursadas(String id_plan) {
		List<InscripcionMateriasPasadasDTO> listaInscripcionMateriasPasadas = new ArrayList<>();

		listaInscripcionMateriasPasadas = inscripcionesRepository.consultarMateriasCursadas(id_plan);
		
		if (listaInscripcionMateriasPasadas == null ) {
			return listaInscripcionMateriasPasadas;
		}
		return listaInscripcionMateriasPasadas;
	}
	
	@Override
	public List<InscripcionMateriasInsDTO> consultarMateriasInscritas(String idpersona,String plan) {
		List<InscripcionMateriasInsDTO> listaInscripcionMateriasIns = new ArrayList<>();

		listaInscripcionMateriasIns = inscripcionesRepository.consultarMateriasInscritas(idpersona, plan);
		
		if (listaInscripcionMateriasIns == null ) {
			return listaInscripcionMateriasIns;
		}
		return listaInscripcionMateriasIns;
	}
	
	@Override
	public Long consultarNumeroEstructura(String id_plan_infopersona) {
		return inscripcionesRepository.consultarNumeroEstructura(id_plan_infopersona);
	}
	
	@Override
	public List<IntentosAsignaturasDTO> consultarIntentosAsignaturas(String idpersona) {
		List<IntentosAsignaturasDTO> listaIntentosAsignaturas = new ArrayList<>();

		listaIntentosAsignaturas = inscripcionesRepository.consultarIntentosAsignaturas(idpersona);
		
		if (listaIntentosAsignaturas == null ) {
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
		
		if (listaBajasAsignaturas == null ) {
			return listaBajasAsignaturas;
		}
		return listaBajasAsignaturas;
	}
	
	@Override
    public void insertarInscripciones(List<InscripcionInsertDTO> inscripciones) {
        inscripciones.forEach(inscripcionesRepository::insertarRegistro);
    }
	
	
}
