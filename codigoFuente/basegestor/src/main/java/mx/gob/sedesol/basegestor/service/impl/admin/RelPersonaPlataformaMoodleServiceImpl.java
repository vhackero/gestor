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

		if (!lista.isEmpty()) {
			return lista.get(ConstantesGestor.PRIMER_ELEMENTO).getIdPersonaMoodle();
		}
		ParametroWSMoodleDTO parametroWSMoodleDTO = parametroWSMoodleService.buscarPorId(idPlataformaMoodle);
		Integer idMoodle = resolverIdMoodle(persona, parametroWSMoodleDTO);
		if (ObjectUtils.isNullOrCero(idMoodle)) {
			return null;
		}
		guardarRelacionPersonaPlataforma(lista, persona.getIdPersona(), idPlataformaMoodle, idMoodle, usuarioModifico);
		return idMoodle;
	}
	
	@Override
	public Integer obtenerIdMoodle(PersonaDTO persona, ParametroWSMoodleDTO parametroWSMoodleDTO, Long usuarioModifico) {
		
		System.out.println("::::obtenerIdMoodle:::: persona "+ persona.getIdPersona() +" - " + parametroWSMoodleDTO.getIdParametroWSMoodle()); 
		List<RelPersonaPlataformaMoodle> lista =
		        relPersonaPlataformaMoodleRepo.obtenerPorPersonaPlataforma(
		                persona.getIdPersona(),
		                parametroWSMoodleDTO.getIdParametroWSMoodle()
		        );

		Integer idMoodle = resolverIdMoodle(persona, parametroWSMoodleDTO);

		if (ObjectUtils.isNullOrCero(idMoodle)) {
		    return null;
		}

		guardarRelacionPersonaPlataforma(lista, persona.getIdPersona(), parametroWSMoodleDTO.getIdParametroWSMoodle(),
				idMoodle, usuarioModifico);

		return idMoodle;

	}

	private Integer resolverIdMoodle(PersonaDTO persona, ParametroWSMoodleDTO parametroWSMoodleDTO) {
		UsuarioWSClient wsClient = new UsuarioWSClient(parametroWSMoodleDTO);
		String correoElectronico = personaCorreoService.obtenerCorreoInstitucional(persona.getIdPersona())
				.getCorreoElectronico();
		Integer idMoodle = null;
		try {
			idMoodle = wsClient.existeNombreUsuario(persona.getUsuario());
			if (ObjectUtils.isNullOrCero(idMoodle)) {
				idMoodle = wsClient.existeCorreo(correoElectronico);
			}
			if (ObjectUtils.isNullOrCero(idMoodle)) {
				Usuario usuario = new Usuario();
				usuario.setUsername(persona.getUsuario());
				usuario.setPassword(persona.getContrasenia());
				usuario.setFirstname(persona.getNombre());
				usuario.setLastname(persona.getApellidoPaterno());
				usuario.setIdnumber(persona.getIdPersona().toString());
				usuario.setEmail(correoElectronico);
				try {
					idMoodle = wsClient.crearUsuario(usuario);
				} catch (Exception e) {
					logger.warn("Fallo al crear usuario en Moodle; se intentará recuperar por username/email.", e);
					idMoodle = recuperarIdMoodleExistente(wsClient, persona.getUsuario(), correoElectronico);
					if (ObjectUtils.isNullOrCero(idMoodle)) {
						throw e;
					}
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return ObjectUtils.isNullOrCero(idMoodle) ? null : idMoodle;
	}

	private Integer recuperarIdMoodleExistente(UsuarioWSClient wsClient, String username, String correoElectronico) {
		Integer idMoodle = null;
		try {
			idMoodle = wsClient.existeNombreUsuario(username);
		} catch (Exception e) {
			logger.warn("No fue posible recuperar el usuario de Moodle por username después del alta.", e);
		}
		if (ObjectUtils.isNullOrCero(idMoodle)) {
			try {
				idMoodle = wsClient.existeCorreo(correoElectronico);
			} catch (Exception e) {
				logger.warn("No fue posible recuperar el usuario de Moodle por correo después del alta.", e);
			}
		}
		return ObjectUtils.isNullOrCero(idMoodle) ? null : idMoodle;
	}

	private void guardarRelacionPersonaPlataforma(List<RelPersonaPlataformaMoodle> lista, Long idPersona,
			Integer idPlataformaMoodle, Integer idMoodle, Long usuarioModifico) {
		if (ObjectUtils.isNullOrCero(idMoodle)) {
			return;
		}
		if (lista == null || lista.isEmpty()) {
			RelPersonaPlataformaMoodle entidad = new RelPersonaPlataformaMoodle();
			entidad.setIdPersona(idPersona);
			entidad.setIdPlataformaMoodle(idPlataformaMoodle);
			entidad.setIdPersonaMoodle(idMoodle);
			entidad.setUsuarioModifico(usuarioModifico);
			entidad.setFechaRegistro(new Date());
			relPersonaPlataformaMoodleRepo.save(entidad);
			return;
		}
		RelPersonaPlataformaMoodle entidad = lista.get(ConstantesGestor.PRIMER_ELEMENTO);
		if (ObjectUtils.isNullOrCero(entidad.getIdPersonaMoodle())) {
			entidad.setIdPersonaMoodle(idMoodle);
			entidad.setUsuarioModifico(usuarioModifico);
			entidad.setFechaRegistro(new Date());
			relPersonaPlataformaMoodleRepo.save(entidad);
		}
	}
	
	/**
	 * 
	 * @param idPersona
	 * @param idPlataformaMoodle
	 * @return
	 */
	public RelPersonaPlataformaMoodleDTO obtenerPersonaPlataformaMoodle(Integer idPersona, Integer idPlataformaMoodle) {
		
		List<RelPersonaPlataformaMoodle> lista = relPersonaPlataformaMoodleRepo
				.obtenerPersonaPlataformaByPersonaMoodle( idPersona, idPlataformaMoodle);
		if (!lista.isEmpty()) {
			RelPersonaPlataformaMoodle aux =  lista.get(ConstantesGestor.PRIMER_ELEMENTO);
			if(ObjectUtils.isNotNull(aux))
				return mpPersonaPlat.map(aux, RelPersonaPlataformaMoodleDTO.class);
		}
		return null;
	}

}
