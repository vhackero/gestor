package mx.gob.sedesol.basegestor.service.impl.admin;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.gob.sedesol.basegestor.commons.dto.admin.PersonaSigeDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.utils.MensajesErrorEnum;
import mx.gob.sedesol.basegestor.commons.utils.ResultadoTransaccionEnum;
import mx.gob.sedesol.basegestor.model.entities.admin.TblPersonaSige;
import mx.gob.sedesol.basegestor.model.repositories.admin.PersonaSigeRepo;
import mx.gob.sedesol.basegestor.service.admin.ComunValidacionService;
import mx.gob.sedesol.basegestor.service.admin.PersonaSigeService;

@Service("personaSigeService")
public class PersonaSigeServiceImpl extends ComunValidacionService<PersonaSigeDTO> implements PersonaSigeService{
	
	private static final Logger logger = Logger.getLogger(PersonaSigeServiceImpl.class);
	
	@Autowired
	private PersonaSigeRepo personaSigeRepo;
	private ModelMapper mapper = new ModelMapper();
	Type personaSigeDTO = new TypeToken<List<PersonaSigeDTO>>() {
	}.getType();
	
	@PostConstruct
	private void initMapper() {
		mapper.getConfiguration().setAmbiguityIgnored(true);
		TypeMap<PersonaSigeDTO, TblPersonaSige> dtoToEntity = mapper.createTypeMap(PersonaSigeDTO.class, TblPersonaSige.class);
		dtoToEntity.addMappings(m -> {
			m.map(PersonaSigeDTO::getIdPersonaSige, TblPersonaSige::setIdPersonaSige);
			m.map(PersonaSigeDTO::getPersonaIdSige, TblPersonaSige::setPersonaIdSige);
			m.map(PersonaSigeDTO::getPerfilIdSige, TblPersonaSige::setPerfilIdSige);
		});
		TypeMap<TblPersonaSige, PersonaSigeDTO> entityToDto = mapper.createTypeMap(TblPersonaSige.class, PersonaSigeDTO.class);
		entityToDto.addMappings(m -> {
			m.map(TblPersonaSige::getIdPersonaSige, PersonaSigeDTO::setIdPersonaSige);
			m.map(TblPersonaSige::getPersonaIdSige, PersonaSigeDTO::setPersonaIdSige);
			m.map(TblPersonaSige::getPerfilIdSige, PersonaSigeDTO::setPerfilIdSige);
		});
	}
	
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
		TblPersonaSige entidad = personaSigeRepo.findOne(id);
		return entidad != null ? mapper.map(entidad, PersonaSigeDTO.class) : null;
	}
	@Override
	public ResultadoDTO<PersonaSigeDTO> guardar(PersonaSigeDTO dto) {
		ResultadoDTO<PersonaSigeDTO> resultado = new ResultadoDTO<>();
		try {
			if (dto.getPersonaIdSige() <= 0) {
				dto.setPersonaIdSige(0);
			}
			if (dto.getPerfilIdSige() <= 0) {
				dto.setPerfilIdSige(0);
			}
			TblPersonaSige entidad = new TblPersonaSige();
			copiarCamposImportacion(dto, entidad, true);
			boolean passwordPresente = dto.getPassword() != null && !dto.getPassword().isEmpty();
			logger.info(String.format("Insertando persona SIGE matricula=%s passwordPresent=%s personaIdSige=%d perfilIdSige=%d",
					entidad.getMatricula(), passwordPresente, entidad.getPersonaIdSige(), entidad.getPerfilIdSige()));
			TblPersonaSige guardada = personaSigeRepo.save(entidad);
			resultado.setDto(mapper.map(guardada, PersonaSigeDTO.class));
		} catch (Exception e) {
			logger.error("Error al guardar persona sige", e);
			resultado.setResultado(ResultadoTransaccionEnum.FALLIDO);
			resultado.setMensajeError(MensajesErrorEnum.ERROR_PERSISTENCIA_DATOS, e.getMessage());
		}
		return resultado;
	}
	@Override
	public ResultadoDTO<PersonaSigeDTO> actualizar(PersonaSigeDTO dto) {
		ResultadoDTO<PersonaSigeDTO> resultado = new ResultadoDTO<>();
		try {
			if (dto.getIdPersonaSige() == null) {
				logger.info("DTO sin idPersonaSige, se realizará inserción en lugar de actualización.");
				return guardar(dto);
			}
			TblPersonaSige existente = personaSigeRepo.findOne(dto.getIdPersonaSige());
			if (existente == null) {
				logger.info(String.format("No se encontró persona SIGE id=%s, se realizará inserción.", dto.getIdPersonaSige()));
				return guardar(dto);
			}
			copiarCamposImportacion(dto, existente, false);
			boolean passwordPresente = dto.getPassword() != null && !dto.getPassword().isEmpty();
			logger.info(String.format("Actualizando persona SIGE id=%s matricula=%s passwordPresent=%s personaIdSige=%d perfilIdSige=%d",
					existente.getIdPersonaSige(), existente.getMatricula(), passwordPresente, existente.getPersonaIdSige(),
					existente.getPerfilIdSige()));
			TblPersonaSige actualizada = personaSigeRepo.save(existente);
			resultado.setDto(mapper.map(actualizada, PersonaSigeDTO.class));
		} catch (Exception e) {
			logger.error("Error al actualizar persona sige", e);
			resultado.setResultado(ResultadoTransaccionEnum.FALLIDO);
			resultado.setMensajeError(MensajesErrorEnum.ERROR_PERSISTENCIA_DATOS, e.getMessage());
		}
		return resultado;
	}
	@Override
	public ResultadoDTO<PersonaSigeDTO> eliminar(PersonaSigeDTO dto) {
		ResultadoDTO<PersonaSigeDTO> resultado = new ResultadoDTO<>();
		try {
			TblPersonaSige entidad = mapper.map(dto, TblPersonaSige.class);
			personaSigeRepo.delete(entidad);
		} catch (Exception e) {
			logger.error("Error al eliminar persona sige", e);
			resultado.setResultado(ResultadoTransaccionEnum.FALLIDO);
			resultado.setMensajeError(MensajesErrorEnum.ERROR_ELIMINAR_REGISTRO, e.getMessage());
		}
		return resultado;
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

	@Override
	public PersonaSigeDTO buscarPorMatricula(String matricula) {
		TblPersonaSige entidad = personaSigeRepo.findByMatricula(matricula);
		return entidad != null ? mapper.map(entidad, PersonaSigeDTO.class) : null;
	}
	
	private void copiarCamposImportacion(PersonaSigeDTO dto, TblPersonaSige entidad, boolean actualizarIdsSiempre) {
		entidad.setMatricula(dto.getMatricula());
		entidad.setNombre(dto.getNombre());
		entidad.setApellidoPaterno(dto.getApellidoPaterno());
		entidad.setApellidoMaterno(dto.getApellidoMaterno());
		entidad.setProgramaEducativo(dto.getProgramaEducativo());
		entidad.setDivision(dto.getDivision());
		entidad.setCorreoInstitucional(dto.getCorreoInstitucional());
		entidad.setFechaNacimiento(dto.getFechaNacimiento());
		entidad.setCurp(dto.getCurp());
		entidad.setNivelSige(dto.getNivelSige());
		entidad.setPassword(dto.getPassword());
		if (actualizarIdsSiempre || dto.getPersonaIdSige() > 0) {
			entidad.setPersonaIdSige(dto.getPersonaIdSige());
		}
		if (actualizarIdsSiempre || dto.getPerfilIdSige() > 0) {
			entidad.setPerfilIdSige(dto.getPerfilIdSige());
		}
	}
	
}
