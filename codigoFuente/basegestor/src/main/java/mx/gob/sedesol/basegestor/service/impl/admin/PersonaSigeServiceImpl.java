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
import mx.gob.sedesol.basegestor.commons.utils.MensajesSistemaEnum;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.ResultadoTransaccionEnum;
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
		if (ObjectUtils.isNull(id)) {
			return null;
		}
		return personaSigeRepo.findById(id).map(persona -> mapper.map(persona, PersonaSigeDTO.class)).orElse(null);
	}
	@Override
	public ResultadoDTO<PersonaSigeDTO> guardar(PersonaSigeDTO dto) {
		ResultadoDTO<PersonaSigeDTO> resultado = new ResultadoDTO<>();
		try {
			validarPersistencia(dto, resultado);
			if (!resultado.esCorrecto()) {
				return resultado;
			}
			TblPersonaSige entidad = mapper.map(dto, TblPersonaSige.class);
			entidad = personaSigeRepo.save(entidad);
			resultado.setDto(mapper.map(entidad, PersonaSigeDTO.class));
			resultado.setMensaje(MensajesSistemaEnum.ADMIN_MSG_REGISTRO_EXITOSO.getId());
			resultado.setResultado(ResultadoTransaccionEnum.EXITOSO);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			resultado.setMensajeError(MensajesSistemaEnum.MSG_ERROR_INTERNO, " " + ex.getMessage());
		}
		return resultado;
	}
	@Override
	public ResultadoDTO<PersonaSigeDTO> actualizar(PersonaSigeDTO dto) {
		ResultadoDTO<PersonaSigeDTO> resultado = new ResultadoDTO<>();
		try {
			validarActualizacion(dto, resultado);
			if (!resultado.esCorrecto()) {
				return resultado;
			}
			TblPersonaSige entidad = mapper.map(dto, TblPersonaSige.class);
			entidad = personaSigeRepo.save(entidad);
			resultado.setDto(mapper.map(entidad, PersonaSigeDTO.class));
			resultado.setMensaje(MensajesSistemaEnum.ADMIN_MSG_REGISTRO_EXITOSO.getId());
			resultado.setResultado(ResultadoTransaccionEnum.EXITOSO);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			resultado.setMensajeError(MensajesSistemaEnum.MSG_ERROR_INTERNO, " " + ex.getMessage());
		}
		return resultado;
	}
	@Override
	public ResultadoDTO<PersonaSigeDTO> eliminar(PersonaSigeDTO dto) {
		ResultadoDTO<PersonaSigeDTO> resultado = new ResultadoDTO<>();
		try {
			validarEliminacion(dto, resultado);
			if (!resultado.esCorrecto()) {
				return resultado;
			}
			if (!ObjectUtils.isNull(dto.getIdPersonaSige())) {
				personaSigeRepo.deleteById(dto.getIdPersonaSige());
			}
			resultado.setResultado(ResultadoTransaccionEnum.EXITOSO);
			resultado.setMensaje(MensajesSistemaEnum.ADMIN_MSG_REGISTRO_EXITOSO.getId());
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			resultado.setMensajeError(MensajesSistemaEnum.MSG_ERROR_INTERNO, " " + ex.getMessage());
		}
		return resultado;
	}
	@Override
	public void validarPersistencia(PersonaSigeDTO dto, ResultadoDTO<PersonaSigeDTO> resultado) {
		if (ObjectUtils.isNull(dto)) {
			resultado.setMensajeError(MensajesSistemaEnum.MSG_ERROR_INTERNO, " DTO nulo");
		}
	}
	@Override
	public void validarActualizacion(PersonaSigeDTO dto, ResultadoDTO<PersonaSigeDTO> resultado) {
		if (ObjectUtils.isNull(dto) || ObjectUtils.isNull(dto.getIdPersonaSige())) {
			resultado.setMensajeError(MensajesSistemaEnum.MSG_ERROR_INTERNO, " DTO nulo o sin identificador");
		}
	}
	@Override
	public void validarEliminacion(PersonaSigeDTO dto, ResultadoDTO<PersonaSigeDTO> resultado) {
		if (ObjectUtils.isNull(dto) || ObjectUtils.isNull(dto.getIdPersonaSige())) {
			resultado.setMensajeError(MensajesSistemaEnum.MSG_ERROR_INTERNO, " DTO nulo o sin identificador");
		}
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
