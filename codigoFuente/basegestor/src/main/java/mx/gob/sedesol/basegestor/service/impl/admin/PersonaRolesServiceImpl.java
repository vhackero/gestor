package mx.gob.sedesol.basegestor.service.impl.admin;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.gob.sedesol.basegestor.commons.constantes.ConstantesBitacora;
import mx.gob.sedesol.basegestor.commons.dto.admin.FuncionalidadDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.PersonaDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.PersonaRolDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.RolDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.RolFuncionalidadDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.VariableMensajeOperacionDTO;
import mx.gob.sedesol.basegestor.commons.utils.MensajesSistemaEnum;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.ResultadoTransaccionEnum;
import mx.gob.sedesol.basegestor.commons.utils.TipoAccion;
import mx.gob.sedesol.basegestor.model.entities.admin.CatRol;
import mx.gob.sedesol.basegestor.model.entities.admin.RelPersonaRol;
import mx.gob.sedesol.basegestor.model.entities.admin.TblPersona;
import mx.gob.sedesol.basegestor.model.repositories.admin.PersonaRolesRepo;
import mx.gob.sedesol.basegestor.mongo.service.NotificacionSistemaService;
import mx.gob.sedesol.basegestor.service.admin.ComunValidacionService;
import mx.gob.sedesol.basegestor.service.admin.PersonaRolesService;
import mx.gob.sedesol.basegestor.service.admin.RolFuncionalidadService;
import mx.gob.sedesol.basegestor.service.admin.RoleService;

@Service("personaRolesService")
public class PersonaRolesServiceImpl extends ComunValidacionService<PersonaRolDTO> implements PersonaRolesService {

	private static final Logger logger = Logger.getLogger(PersonaRolesServiceImpl.class);

	@Autowired
	private PersonaRolesRepo personaRolesRepo;
	@Autowired
	private RolFuncionalidadService rolFuncionalidadService;
	@Autowired
	private NotificacionSistemaService notificacionSistemaService;

	private ModelMapper modelMapper = new ModelMapper();
	private Type tipoListaPersonaRol = new TypeToken<List<PersonaRolDTO>>() {
	}.getType();

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResultadoDTO<PersonaRolDTO> guardar(PersonaRolDTO personaRoldto) {

		ResultadoDTO<PersonaRolDTO> res = sonDatosRequeridosValidos(TipoAccion.PERSISTENCIA, personaRoldto);
		if (res.getResultado().getValor()) {
			try {
				res = new ResultadoDTO<>();
				RelPersonaRol personaRolAux = modelMapper.map(personaRoldto, RelPersonaRol.class);
				personaRolAux = personaRolesRepo.save(personaRolAux);
				res.setDto(modelMapper.map(personaRolAux, PersonaRolDTO.class));
				
				res.agregaMensaje(MensajesSistemaEnum.ADMIN_MSG_GUARDADO_EXITOSO.getId());
			} catch (Exception e) {
				res.setMensajeError(MensajesSistemaEnum.ADMIN_MSG_GUARDADO_FALLIDO);
				logger.error(e.getMessage(), e);
			}

		}

		return res;
	}

	/**
	 * Se maneja eliminacion logica utilzar metodo actualizar
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResultadoDTO<PersonaRolDTO> eliminar(PersonaRolDTO personaRoldto) {
		ResultadoDTO<PersonaRolDTO> resultado = sonDatosRequeridosValidos(TipoAccion.ELIMINACION, personaRoldto);
		if (resultado.getResultado().getValor()) {

			try {
				RelPersonaRol entidad = modelMapper.map(personaRoldto, RelPersonaRol.class);
				personaRolesRepo.delete(entidad);
				resultado.agregaMensaje("Se elimino correctamente el registro.");

				resultado.agregaMensaje(MensajesSistemaEnum.ADMIN_MSG_ELIMINACION_EXITOSA.getId());
			} catch (Exception e) {
				resultado.setMensajeError(MensajesSistemaEnum.ADMIN_MSG_ELIMINACION_FALLIDA);
				logger.error(e.getMessage(), e);
			}
		}
		return resultado;
	}

	@Override
	public List<PersonaRolDTO> findAll() {
		List<RelPersonaRol> lista = personaRolesRepo.findAll();

		for (RelPersonaRol entidad : lista) {
			entidad.getPersona().setPersonaCorreos(null);
			entidad.getPersona().setRelPersonaTelefonos(null);
			entidad.getPersona().setRelPersonaRoles(null);
			entidad.getPersona().setDomiciliosPersonas(null);
		}

		return modelMapper.map(lista, tipoListaPersonaRol);
	}

	@Override
	public List<PersonaDTO> obtenerPersonasPorRol(Integer idRol) {
		List<PersonaDTO> personas = new ArrayList<>();
		List<RelPersonaRol> personasRol = personaRolesRepo.obtieneRelPersonaRolesPorRol(idRol);
		for (RelPersonaRol personaRol : personasRol) {
			personaRol.getPersona().setPersonaCorreos(null);
			personaRol.getPersona().setRelPersonaTelefonos(null);
			personaRol.getPersona().setRelPersonaRoles(null);
			personaRol.getPersona().setDomiciliosPersonas(null);
			personas.add(modelMapper.map(personaRol.getPersona(), PersonaDTO.class));
		}
		return personas;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResultadoDTO<PersonaRolDTO> actualizar(PersonaRolDTO personaRoldto) {

		ResultadoDTO<PersonaRolDTO> res = sonDatosRequeridosValidos(TipoAccion.ACTUALIZACION, personaRoldto);
		if (res.getResultado().getValor()) {
			try {
				res = new ResultadoDTO<>();
				RelPersonaRol personaRolAux = modelMapper.map(personaRoldto, RelPersonaRol.class);
				personaRolAux = personaRolesRepo.saveAndFlush(personaRolAux);
				res.setDto(modelMapper.map(personaRolAux, PersonaRolDTO.class));


				res.agregaMensaje(MensajesSistemaEnum.ADMIN_MSG_ACTUALIZACION_EXITOSA.getId());
			} catch (Exception e) {
				res.setMensajeError(MensajesSistemaEnum.ADMIN_MSG_ACTUALIZACION_FALLIDA);
				logger.error(e.getMessage(), e);
			}
		}
		return res;
	}

	@Override
	public PersonaRolDTO buscarPorId(Long id) {
		RelPersonaRol persRolAux = personaRolesRepo.findOne(id);
		PersonaRolDTO dto;
		if (ObjectUtils.isNull(persRolAux)) {
			dto = null;
		} else {
			persRolAux.getPersona().setPersonaCorreos(null);
			persRolAux.getPersona().setRelPersonaTelefonos(null);
			persRolAux.getPersona().setRelPersonaRoles(null);
			persRolAux.getPersona().setDomiciliosPersonas(null);
			dto = modelMapper.map(persRolAux, PersonaRolDTO.class);
		}
		return dto;
	}

	@Override
	public void validarPersistencia(PersonaRolDTO personaRoldto, ResultadoDTO<PersonaRolDTO> res) {
		if (ObjectUtils.isNull(personaRoldto.getPersona())
				|| ObjectUtils.isNull(personaRoldto.getPersona().getIdPersona())) {
			res.setMensajeError(MensajesSistemaEnum.ROL_PERSONA_ID_PERSONA_REQ);
		}
		if (ObjectUtils.isNull(personaRoldto.getRol()) || ObjectUtils.isNull(personaRoldto.getRol().getIdRol())) {
			res.setMensajeError(MensajesSistemaEnum.ROL_PERSONA_ID_ROL_REQ);
		}
		if (ObjectUtils.isNull(personaRoldto.getFechaRegistro())) {
			res.setMensajeError(MensajesSistemaEnum.MSG_GENERAL_FECHA_REGISTRO_REQ);
		}
		if (ObjectUtils.isNull(personaRoldto.getUsuarioModifico())) {
			res.setMensajeError(MensajesSistemaEnum.MSG_GENERAL_USUARIO_MODIFICO_REQ);
		}
	}

	@Override
	public void validarActualizacion(PersonaRolDTO personaRoldto, ResultadoDTO<PersonaRolDTO> res) {
		if (ObjectUtils.isNull(personaRoldto.getPersona())
				|| ObjectUtils.isNull(personaRoldto.getPersona().getIdPersona())) {
			res.setMensajeError(MensajesSistemaEnum.ROL_PERSONA_ID_PERSONA_REQ);
		}
		if (ObjectUtils.isNull(personaRoldto.getRol()) || ObjectUtils.isNull(personaRoldto.getRol().getIdRol())) {
			res.setMensajeError(MensajesSistemaEnum.ROL_PERSONA_ID_ROL_REQ);
		}
		if (ObjectUtils.isNull(personaRoldto.getUsuarioModifico())) {
			res.setMensajeError(MensajesSistemaEnum.MSG_GENERAL_USUARIO_MODIFICO_REQ);
		}
		if (ObjectUtils.isNull(personaRoldto.getFechaActualizacion())) {
			res.setMensajeError(MensajesSistemaEnum.MSG_GENERAL_FECHA_EDICION_REQ);
		}
		if (ObjectUtils.isNull(personaRoldto.getActivo())) {
			res.setMensajeError(MensajesSistemaEnum.MSG_GENERAL_ESTA_ACTIVO_REQ);
		}
	}

	@Override
	public void validarEliminacion(PersonaRolDTO personaRoldto, ResultadoDTO<PersonaRolDTO> res) {
		if (ObjectUtils.isNull(personaRoldto.getFechaActualizacion())) {
			res.setMensajeError(MensajesSistemaEnum.MSG_GENERAL_FECHA_EDICION_REQ);
		}
		if (ObjectUtils.isNull(personaRoldto.getActivo())) {
			res.setMensajeError(MensajesSistemaEnum.MSG_GENERAL_ESTA_ACTIVO_REQ);
		}
		if (ObjectUtils.isNull(personaRoldto.getUsuarioModifico())) {
			res.setMensajeError(MensajesSistemaEnum.MSG_GENERAL_USUARIO_MODIFICO_REQ);
		}
	}

	@Override
	public List<PersonaRolDTO> obtieneRelPersonaRolesPorUsuario(String usuario) {

		List<RelPersonaRol> listaPersonaRoles;
		List<PersonaRolDTO> lista = new ArrayList<>();
		if (ObjectUtils.isNullOrEmpty(usuario)) {
			lista = new ArrayList<>();
		} else {
			listaPersonaRoles = personaRolesRepo.obtieneRelPersonaRoles(usuario);
			for (RelPersonaRol entidad : listaPersonaRoles) {
				entidad.setPersona(null);
				try {
					lista.add(modelMapper.map(entidad, PersonaRolDTO.class));
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
			}

		}
		return lista;

	}

	@Override
	public List<FuncionalidadDTO> obtenerFuncionalidadesUsuario(String usuario) {

		Map<Long, FuncionalidadDTO> mapaFuncionalidades = new HashMap<>();
		List<RelPersonaRol> roles = personaRolesRepo.obtieneRelPersonaRoles(usuario);

		for (RelPersonaRol rol : roles) {
			procesarListaRolFuncionalidades(mapaFuncionalidades,
					rolFuncionalidadService.obtenerArbolFuncionalidadesRol(rol.getRol().getIdRol()));
		}
		return new ArrayList<>(mapaFuncionalidades.values());
	}

	@Override
	public Map<String, String> obtenerFuncionalidadesRol(Integer idRol) {

		Map<Long, FuncionalidadDTO> mapaFuncionalidades = new HashMap<>();

		procesarListaRolFuncionalidades(mapaFuncionalidades,
				rolFuncionalidadService.obtenerArbolFuncionalidadesRol(idRol));

		Map<String, String> mapa = new HashMap<>();

		for (FuncionalidadDTO funcionalidad : mapaFuncionalidades.values()) {
			mapa.put(funcionalidad.getClave(), funcionalidad.getClave());
		}

		return mapa;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResultadoDTO<PersonaRolDTO> almacenarRolesUsuario(PersonaDTO persona, List<RolDTO> roles,
			long usuarioModifico) {
		ResultadoDTO<PersonaRolDTO> resultado = new ResultadoDTO<>();
		try {
			PersonaRolDTO prdto = new PersonaRolDTO();
			prdto.setPersona(persona);
			resultado.setDto(prdto);
			
			List<RelPersonaRol> lstPersRoles = personaRolesRepo.obtieneRelPersonaRoles(persona.getUsuario());
			List<RelPersonaRol> rolesEliminar = new ArrayList<>();
			List<RolDTO> rolesAgregar = new ArrayList<>();

			identificarRolesEliminar(roles, lstPersRoles, rolesEliminar);
			identificarRolesAgregar(roles, lstPersRoles, rolesAgregar);

			for (RelPersonaRol personaRol : rolesEliminar) {
				personaRolesRepo.eliminaRelPersonaRolById(personaRol.getIdPersonaRol());
				//personaRolesRepo.delete(personaRol.getIdPersonaRol());
			}

			for (RolDTO rol : rolesAgregar) {
				RelPersonaRol personaRol = new RelPersonaRol(modelMapper.map(rol, CatRol.class),
						modelMapper.map(persona, TblPersona.class), usuarioModifico);
				personaRolesRepo.saveAndFlush(personaRol);
			}	
		} catch (Exception e) {
			resultado.setResultado(ResultadoTransaccionEnum.FALLIDO);
			logger.info("Error al actualizar rol: " + e);
		}
		return resultado;
	}
	
	
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void reemplazarRoles(List<RolDTO> rolesDTO, TblPersona persona) {
		logger.info("Usuario: " + persona.getUsuario());
		logger.info("IdPersona: " + persona.getIdPersona());
		List<RelPersonaRol> personaRolesEntities = personaRolesRepo.obtieneRelPersonaRoles(persona.getUsuario());

		for (RelPersonaRol personaRolEntity : personaRolesEntities) {
			logger.info("id personaRol a eliminar: " + personaRolEntity.getIdPersonaRol());
			personaRolesRepo.delete(personaRolEntity.getIdPersonaRol());

		}
		// // Si la lista de nuevos roles no incluye al rol de alumno, se
		// agrega.
		// estableceRolAlumnoPorDefecto(rolesDTO);
		//
		// for (RolDTO rol : rolesDTO) {
		// logger.info("id rol a guardar: " + rol.getIdRol());
		// RelPersonaRol personaRol = new RelPersonaRol(mapper.map(rol,
		// CatRol.class),
		// mapper.map(persona, TblPersona.class), persona.getUsuarioModifico());
		//
		// personaRolesRepo.saveAndFlush(personaRol);
		// }
	}
	

	private void identificarRolesAgregar(List<RolDTO> roles, List<RelPersonaRol> lstPersRoles,
			List<RolDTO> rolesAgregar) {
		for (RolDTO rol : roles) {
			boolean encontrado = false;
			for (RelPersonaRol personaRol : lstPersRoles) {
				if (personaRol.getRol().getIdRol().equals(rol.getIdRol())) {
					encontrado = true;
					break;
				}
			}
			if (!encontrado) {
				rolesAgregar.add(rol);
			}
		}
	}

	private void identificarRolesEliminar(List<RolDTO> roles, List<RelPersonaRol> lstPersRoles,
			List<RelPersonaRol> rolesEliminar) {
		for (RelPersonaRol personaRol : lstPersRoles) {
			boolean encontrado = false;
			for (RolDTO rol : roles) {
				if (personaRol.getRol().getIdRol().equals(rol.getIdRol())) {
					encontrado = true;
					break;
				}
			}
			if (!encontrado) {
				rolesEliminar.add(personaRol);
			}
		}
	}

	private void procesarListaRolFuncionalidades(Map<Long, FuncionalidadDTO> mapa, List<RolFuncionalidadDTO> lista) {
		validarFuncionalidadesSeleccionadas(lista);
		for (RolFuncionalidadDTO funcionalidad : lista) {
			if (funcionalidad.isSeleccionado()) {
				mapa.put(funcionalidad.getFuncionalidadDTO().getIdFuncionalidad(), funcionalidad.getFuncionalidadDTO());
			}
			if (!ObjectUtils.isNullOrEmpty(funcionalidad.getFuncionalidades())) {
				procesarListaRolFuncionalidades(mapa, funcionalidad.getFuncionalidades());
			}
		}
	}

	private void validarFuncionalidadesSeleccionadas(List<RolFuncionalidadDTO> lista) {
		for (RolFuncionalidadDTO dto : lista) {
			dto.setSeleccionado(validarHijoSeleccionado(dto));
		}
	}

	private boolean validarHijoSeleccionado(RolFuncionalidadDTO funcionalidadDTO) {
		boolean seleccionado = funcionalidadDTO.isSeleccionado();
		if (!ObjectUtils.isNullOrEmpty(funcionalidadDTO.getFuncionalidades())) {
			for (RolFuncionalidadDTO dto : funcionalidadDTO.getFuncionalidades()) {
				dto.setSeleccionado(validarHijoSeleccionado(dto));
				if (dto.isSeleccionado()) {
					seleccionado = true;
				}
			}
		}
		return seleccionado;
	}

}
