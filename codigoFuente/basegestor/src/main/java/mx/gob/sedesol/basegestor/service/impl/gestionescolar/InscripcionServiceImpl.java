package mx.gob.sedesol.basegestor.service.impl.gestionescolar;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.HistorialAcademicoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.InscripcionDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.InscripcionMateriasDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.InscripcionMateriasInsDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.InscripcionMateriasPasadasDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.InscripcionMaxMinDTO;
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
		
		List<InscripcionDTO> listadatosPersona = inscripcionesRepository.consultarTipoProceso(id_persona);
		
		if (listadatosPersona == null ) {
			return new InscripcionDTO();
		}
		InscripcionDTO regresaPersona = listadatosPersona.get(0);
		return regresaPersona;
	}
	
	@Override
	public List<InscripcionMateriasDTO> consultarMaterias(String id_plan) {
		List<InscripcionMateriasDTO> listadatosMaterias = new ArrayList<>();

		listadatosMaterias = inscripcionesRepository.consultarMaterias(id_plan);
		
		if (listadatosMaterias == null ) {
			return listadatosMaterias;
		}
		return listadatosMaterias;
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
		List<InscripcionMateriasPasadasDTO> listadatosMaterias = new ArrayList<>();

		listadatosMaterias = inscripcionesRepository.consultarMateriasCursadas(id_plan);
		
		if (listadatosMaterias == null ) {
			return listadatosMaterias;
		}
		return listadatosMaterias;
	}
	
	@Override
	public List<InscripcionMateriasInsDTO> consultarMateriasInscritas(String idpersona,String plan) {
		List<InscripcionMateriasInsDTO> listadatosMaterias = new ArrayList<>();

		listadatosMaterias = inscripcionesRepository.consultarMateriasInscritas(idpersona, plan);
		
		if (listadatosMaterias == null ) {
			return listadatosMaterias;
		}
		return listadatosMaterias;
	}
 

}
