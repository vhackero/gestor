package mx.gob.sedesol.basegestor.service.impl.admin;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.RolDTO;
import mx.gob.sedesol.basegestor.commons.utils.MensajesSistemaEnum;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.TipoAccion;
import mx.gob.sedesol.basegestor.model.entities.admin.CatRol;
import mx.gob.sedesol.basegestor.model.especificaciones.RolEspecificacion;
import mx.gob.sedesol.basegestor.model.repositories.admin.RolesRepo;
import mx.gob.sedesol.basegestor.service.admin.ComunValidacionService;
import mx.gob.sedesol.basegestor.service.admin.RoleService;

@Service("roleService")
public class RoleServiceImpl extends ComunValidacionService<RolDTO> implements RoleService {

	private static final Logger logger = Logger.getLogger(RoleServiceImpl.class);

	@Autowired
	private RolesRepo roleRepository;

	private ModelMapper rolMapper = new ModelMapper();

	Type rolesDto = new TypeToken<List<RolDTO>>() {
	}.getType();

	@Override
	public List<RolDTO> busquedaPorCriterios(RolDTO criterios) {

		try {
			if (ObjectUtils.isNotNull(criterios)) {

				Specification<CatRol> spec = new RolEspecificacion(criterios);

				List<CatRol> resultadoCriterios = roleRepository.findAll(spec);

				return rolMapper.map(resultadoCriterios, rolesDto);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return new ArrayList<>();
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResultadoDTO<RolDTO> guardar(RolDTO rol) {

		ResultadoDTO<RolDTO> resultado = sonDatosRequeridosValidos(TipoAccion.PERSISTENCIA, rol);
		if (resultado.esCorrecto()) {
			try {

				resultado = new ResultadoDTO<>();
				CatRol entidadRol = rolMapper.map(rol, CatRol.class);
				CatRol entidadRespuesta = roleRepository.save(entidadRol);
				resultado.setDto(rolMapper.map(entidadRespuesta, RolDTO.class));

				resultado.agregaMensaje(MensajesSistemaEnum.ADMIN_MSG_GUARDADO_EXITOSO.getId());
			} catch (Exception e) {
				resultado.setMensajeError(MensajesSistemaEnum.ADMIN_MSG_GUARDADO_FALLIDO);
				logger.error(e.getMessage(), e);
			}
		}

		return resultado;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResultadoDTO<RolDTO> eliminar(RolDTO rol) {
		ResultadoDTO<RolDTO> res = sonDatosRequeridosValidos(TipoAccion.ELIMINACION, rol);
		if (res.getResultado().getValor()) {
			try {

				res = new ResultadoDTO<>();
				CatRol rolAux = rolMapper.map(rol, CatRol.class);
				roleRepository.saveAndFlush(rolAux);
				res.setDto(rolMapper.map(rolAux, RolDTO.class));
				res.agregaMensaje(MensajesSistemaEnum.ADMIN_MSG_ELIMINACION_EXITOSA.getId());
			} catch (Exception e) {
				res.setMensajeError(MensajesSistemaEnum.ADMIN_MSG_ELIMINACION_EXITOSA);
				logger.error(e.getMessage(), e);
			}

		}

		return res;
	}

	@Override
	public List<RolDTO> findAll() {
		List<CatRol> catRoles = roleRepository.findAll();

		return rolMapper.map(catRoles, rolesDto);

	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResultadoDTO<RolDTO> actualizar(RolDTO rol) {
		ResultadoDTO<RolDTO> res = sonDatosRequeridosValidos(TipoAccion.ACTUALIZACION, rol);

		if (res.getResultado().getValor()) {
			try {
				res = new ResultadoDTO<>();
				CatRol rolAux = rolMapper.map(rol, CatRol.class);
				CatRol entidadRespuesta = roleRepository.saveAndFlush(rolAux);
				res.setDto(rolMapper.map(entidadRespuesta, RolDTO.class));
				res.agregaMensaje(MensajesSistemaEnum.ADMIN_MSG_ACTUALIZACION_EXITOSA.getId());
			} catch (Exception e) {
				res.setMensajeError(MensajesSistemaEnum.ADMIN_MSG_ACTUALIZACION_FALLIDA);
				logger.error(e.getMessage(), e);
			}
		}
		return res;
	}

	@Override
	public RolDTO buscarPorId(Integer id) {
		RolDTO rolDTO;
		CatRol rolAux = roleRepository.findOne(id);
		if (ObjectUtils.isNull(rolAux)) {
			rolDTO = null;
		} else {
			rolDTO = rolMapper.map(rolAux, RolDTO.class);
		}
		return rolDTO;
	}

	@Override
	public RolDTO buscarPorClave(String claveRol) {
		RolDTO rolDTO;
		CatRol rolClave = roleRepository.buscarRolPorClave(claveRol);
		if (ObjectUtils.isNull(rolClave)) {
			rolDTO = null;
		} else {
			rolDTO = rolMapper.map(rolClave, RolDTO.class);
		}
		return rolDTO;
	}

	@Override
	public void validarPersistencia(RolDTO rol, ResultadoDTO<RolDTO> res) {
		validacionComun(rol, res);
	}

	@Override
	public void validarActualizacion(RolDTO rol, ResultadoDTO<RolDTO> res) {
		validacionComun(rol, res);
		if (ObjectUtils.isNull(rol.getFechaActualizacion())) {
			res.setMensajeError(MensajesSistemaEnum.ROLES_FECHA_EDI_REQ);
		}

	}

	public void validacionComun(RolDTO rol, ResultadoDTO<RolDTO> res) {
		if (ObjectUtils.isNullOrEmpty(rol.getNombre())) {
			res.setMensajeError(MensajesSistemaEnum.ROLES_NOMBRE_REQ);
		}
		if (ObjectUtils.isNullOrEmpty(rol.getClave())) {
			res.setMensajeError(MensajesSistemaEnum.ROLES_CLAVE_REQ);
		}
		if (ObjectUtils.isNullOrEmpty(rol.getUsuarioModifica())) {
			res.setMensajeError(MensajesSistemaEnum.ROLES_USUARIO_REQ);
		}
		if (ObjectUtils.isNull(rol.getFechaRegistro())) {
			res.setMensajeError(MensajesSistemaEnum.ROLES_FECHA_REG_REQ);
		}
		int idRol;
		if (ObjectUtils.isNull(rol.getIdRol())) {
			idRol = 0;
		} else {
			idRol = rol.getIdRol();
		}
		List<CatRol> lista = roleRepository.buscarRolPorClaveId(rol.getClave(), idRol);
	
		if (!lista.isEmpty()) {
			res.setMensajeError(MensajesSistemaEnum.ROLES_EXISTE_ROL);
		}
		
	}

	@Override
	public void validarEliminacion(RolDTO rol, ResultadoDTO<RolDTO> res) {
		if (ObjectUtils.isNull(rol.getFechaActualizacion())) {
			res.setMensajeError(MensajesSistemaEnum.ROLES_FECHA_EDI_REQ);
		}
		if (ObjectUtils.isNullOrEmpty(rol.getUsuarioModifica())) {
			res.setMensajeError(MensajesSistemaEnum.ROLES_USUARIO_REQ);
		}
	}

}
