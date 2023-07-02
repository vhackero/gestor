package mx.gob.sedesol.basegestor.service.impl.admin;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.gob.sedesol.basegestor.commons.constantes.ConstantesGestor;
import mx.gob.sedesol.basegestor.commons.dto.admin.ParametroWSMoodleDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.PersonaDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.RelPersonaPlataformaMoodleDTO;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.model.entities.admin.RelPersonaPlataformaMoodle;
import mx.gob.sedesol.basegestor.model.repositories.admin.RelPersonaPlataformaMoodleRepo;
import mx.gob.sedesol.basegestor.service.ParametroWSMoodleService;
import mx.gob.sedesol.basegestor.service.admin.PersonaCorreoService;
import mx.gob.sedesol.basegestor.service.admin.RelPersonaPlataformaMoodleService;
import mx.gob.sedesol.basegestor.ws.moodle.clientes.model.entities.Usuario;
import mx.gob.sedesol.basegestor.ws.moodle.clientes.service.client.UsuarioWSClient;

@Service("relPersonaPlataformaMoodleService")
public class RelPersonaPlataformaMoodleServiceImpl implements RelPersonaPlataformaMoodleService {
	
	private static final Logger logger = Logger.getLogger(RelPersonaPlataformaMoodleServiceImpl.class);
	
	@Autowired
	private RelPersonaPlataformaMoodleRepo relPersonaPlataformaMoodleRepo;
	
	@Autowired
	private ParametroWSMoodleService parametroWSMoodleService;
	
	@Autowired
	private PersonaCorreoService personaCorreoService;
	
	private ModelMapper mpPersonaPlat = new ModelMapper();

	@Override
	public Integer obtenerIdMoodle(PersonaDTO persona, Integer idPlataformaMoodle, Long usuarioModifico) {
		List<RelPersonaPlataformaMoodle> lista = relPersonaPlataformaMoodleRepo
				.obtenerPorPersonaPlataforma(persona.getIdPersona(), idPlataformaMoodle);

		if (lista.isEmpty()) {
			UsuarioWSClient wsClient = new UsuarioWSClient(parametroWSMoodleService.buscarPorId(idPlataformaMoodle));
			String correoElectronico = personaCorreoService.obtenerCorreoInstitucional(persona.getIdPersona())
					.getCorreoElectronico();
			Integer idMoodle = null;

			try {
				idMoodle = wsClient.existeNombreUsuario(persona.getUsuario());

				if (ObjectUtils.isNullOrCero(idMoodle)) {
					idMoodle = wsClient.existeCorreo(correoElectronico);
					if (ObjectUtils.isNullOrCero(idMoodle)) {
						Usuario usuario = new Usuario();
						usuario.setUsername(persona.getUsuario());
						usuario.setPassword(persona.getContrasenia());
						usuario.setFirstname(persona.getNombre());
						usuario.setLastname(persona.getApellidoPaterno());
						usuario.setIdnumber(persona.getIdPersona().toString());
						usuario.setEmail(correoElectronico);
						idMoodle = wsClient.crearUsuario(usuario);
					} else {
						//almacenar inconsistencia
						idMoodle = null;
						logger.info("Error, el correo ya existe en moodle.");
					}
				}else{
					logger.info("Error, el nombre de usuario ya existe en moodle.");
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
			if (ObjectUtils.isNullOrCero(idMoodle)) {
				idMoodle = null;
			}
			if (ObjectUtils.isNull(idMoodle)) {
				RelPersonaPlataformaMoodle entidad = new RelPersonaPlataformaMoodle();
				entidad.setIdPersona(persona.getIdPersona());
				entidad.setIdPlataformaMoodle(idPlataformaMoodle);
				entidad.setIdPersonaMoodle(idMoodle);
				entidad.setUsuarioModifico(usuarioModifico);
				entidad.setFechaRegistro(new Date());
				relPersonaPlataformaMoodleRepo.save(entidad);
			}
			return idMoodle;
		} else {
			return lista.get(ConstantesGestor.PRIMER_ELEMENTO).getIdPersonaMoodle();
		}
	}
	
	@Override
	public Integer obtenerIdMoodle(PersonaDTO persona, ParametroWSMoodleDTO parametroWSMoodleDTO, Long usuarioModifico) {
		List<RelPersonaPlataformaMoodle> lista = relPersonaPlataformaMoodleRepo
				.obtenerPorPersonaPlataforma(persona.getIdPersona(), parametroWSMoodleDTO.getIdParametroWSMoodle());

		if (lista.isEmpty()) {
			UsuarioWSClient wsClient = new UsuarioWSClient(parametroWSMoodleDTO);
			String correoElectronico = personaCorreoService.obtenerCorreoInstitucional(persona.getIdPersona())
					.getCorreoElectronico();
			Integer idMoodle = null;

			try {
				idMoodle = wsClient.existeNombreUsuario(persona.getUsuario());

				if (ObjectUtils.isNullOrCero(idMoodle)) {
					idMoodle = wsClient.existeCorreo(correoElectronico);
					if (ObjectUtils.isNullOrCero(idMoodle)) {
						Usuario usuario = new Usuario();
						usuario.setUsername(persona.getUsuario());
						usuario.setPassword(persona.getContrasenia());
						usuario.setFirstname(persona.getNombre());
						usuario.setLastname(persona.getApellidoPaterno());
						usuario.setIdnumber(persona.getIdPersona().toString());
						usuario.setEmail(correoElectronico);
						idMoodle = wsClient.crearUsuario(usuario);
					} else {
						//almacenar inconsistencia
						idMoodle = null;
					}
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
			if (ObjectUtils.isNullOrCero(idMoodle)) {
				idMoodle = null;
			}
			if (!ObjectUtils.isNullOrCero(idMoodle)) {
				RelPersonaPlataformaMoodle entidad = new RelPersonaPlataformaMoodle();
				entidad.setIdPersona(persona.getIdPersona());
				entidad.setIdPlataformaMoodle(parametroWSMoodleDTO.getIdParametroWSMoodle());
				entidad.setIdPersonaMoodle(idMoodle);
				entidad.setUsuarioModifico(usuarioModifico);
				entidad.setFechaRegistro(new Date());
				relPersonaPlataformaMoodleRepo.save(entidad);
			}
			return idMoodle;
		} else {
			return lista.get(ConstantesGestor.PRIMER_ELEMENTO).getIdPersonaMoodle();
		}
	}
	
	/**
	 * 
	 * @param idPersona
	 * @param idPlataformaMoodle
	 * @return
	 */
	public RelPersonaPlataformaMoodleDTO obtenerPersonaPlataformaMoodle(Integer idPersonaMoodle, Integer idPlataformaMoodle) {
		
		List<RelPersonaPlataformaMoodle> lista = relPersonaPlataformaMoodleRepo
				.obtenerPersonaPlataformaByPersonaMoodle(idPersonaMoodle, idPlataformaMoodle);
		if (lista.isEmpty()) {
			return null;
		} else {
			RelPersonaPlataformaMoodle aux =  lista.get(ConstantesGestor.PRIMER_ELEMENTO);
			if(ObjectUtils.isNotNull(aux))
				return mpPersonaPlat.map(aux, RelPersonaPlataformaMoodleDTO.class);
			else 
				return null;
		}
	}

}
