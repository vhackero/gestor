package mx.gob.sedesol.basegestor.service.impl.admin;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.gob.sedesol.basegestor.commons.dto.admin.PersonaSigeDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.model.entities.admin.TblPersonaSige;
import mx.gob.sedesol.basegestor.model.repositories.admin.PersonaSigeRepo;
import mx.gob.sedesol.basegestor.service.admin.ComunValidacionService;
import mx.gob.sedesol.basegestor.service.admin.PersonaSigeService;

@Service("personaSigeService")
public class PersonaSigeServiceImpl extends ComunValidacionService<PersonaSigeDTO> implements PersonaSigeService{
	
	private static final Logger logger = Logger.getLogger(PersonaServiceImpl.class);
	
	@Autowired
	private PersonaSigeRepo personaSigeRepo;
	private ModelMapper mapper = new ModelMapper();
	Type personaSigeDTO = new TypeToken<List<PersonaSigeDTO>>() {
	}.getType();
	
	@Override
	public List<PersonaSigeDTO> findAll() {
		List<PersonaSigeDTO> listaPersonas = new ArrayList<PersonaSigeDTO>();
		ArrayList<TblPersonaSige> personas = (ArrayList<TblPersonaSige>) personaSigeRepo.findAll();
		for(TblPersonaSige persona : personas) {
			PersonaSigeDTO personaObj = new PersonaSigeDTO();
			personaObj.setIdPersonaSige(persona.getIdPersonaSige());
			personaObj.setMatricula(persona.getMatricula());
			personaObj.setNombre(persona.getNombre());
			personaObj.setApellidoPaterno(persona.getApellidoPaterno());
			personaObj.setApellidoMaterno(persona.getApellidoMaterno());
			personaObj.setProgramaEducativo(persona.getProgramaEducativo());
			personaObj.setDivision(persona.getDivision());
			personaObj.setCorreoInstitucional(persona.getCorreoInstitucional());
			personaObj.setFechaNacimiento(persona.getFechaNacimiento());
			personaObj.setCurp(persona.getCurp());
			personaObj.setNivelSige(persona.getNivelSige());
			personaObj.setPersonaIdSige(persona.getPersonaIdSige());
			personaObj.setPerfilIdSige(persona.getPerfilIdSige());
			personaObj.setPassword(persona.getPassword());
			listaPersonas.add(personaObj);
		}
		
		return listaPersonas;
	}
	@Override
	public PersonaSigeDTO buscarPorId(Long id) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public ResultadoDTO<PersonaSigeDTO> guardar(PersonaSigeDTO dto) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public ResultadoDTO<PersonaSigeDTO> actualizar(PersonaSigeDTO dto) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public ResultadoDTO<PersonaSigeDTO> eliminar(PersonaSigeDTO dto) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void validarPersistencia(PersonaSigeDTO dto, ResultadoDTO<PersonaSigeDTO> resultado) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void validarActualizacion(PersonaSigeDTO dto, ResultadoDTO<PersonaSigeDTO> resultado) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void validarEliminacion(PersonaSigeDTO dto, ResultadoDTO<PersonaSigeDTO> resultado) {
		// TODO Auto-generated method stub
		
	}
	public PersonaSigeRepo getPersonaSigeRepo() {
		return personaSigeRepo;
	}
	public void setPersonaSigeRepo(PersonaSigeRepo personaSigeRepo) {
		this.personaSigeRepo = personaSigeRepo;
	}

	@Override
	public List<PersonaSigeDTO> buscarNoRegistrados() {
		List<PersonaSigeDTO> listaPersonas = new ArrayList<PersonaSigeDTO>();
		List<TblPersonaSige> personas = personaSigeRepo.obtenetPersonasNoRegistradas();
		for(TblPersonaSige persona : personas) {
			if(!persona.getCorreoInstitucional().isEmpty()) {
				PersonaSigeDTO personaObj = new PersonaSigeDTO();
				personaObj.setIdPersonaSige(persona.getIdPersonaSige());
				personaObj.setMatricula(persona.getMatricula());
				personaObj.setNombre(persona.getNombre());
				personaObj.setApellidoPaterno(persona.getApellidoPaterno());
				personaObj.setApellidoMaterno(persona.getApellidoMaterno());
				personaObj.setProgramaEducativo(persona.getProgramaEducativo());
				personaObj.setDivision(persona.getDivision());
				personaObj.setCorreoInstitucional(persona.getCorreoInstitucional());
				personaObj.setFechaNacimiento(persona.getFechaNacimiento());
				personaObj.setCurp(persona.getCurp());
				personaObj.setNivelSige(persona.getNivelSige());
				personaObj.setPersonaIdSige(persona.getPersonaIdSige());
				personaObj.setPerfilIdSige(persona.getPerfilIdSige());
				personaObj.setPassword(persona.getPassword());
				listaPersonas.add(personaObj);				
			}
		}
		
		return listaPersonas;
	}
	
}
