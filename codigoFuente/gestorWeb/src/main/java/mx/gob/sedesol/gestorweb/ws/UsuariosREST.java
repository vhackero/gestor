package mx.gob.sedesol.gestorweb.ws;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import mx.gob.sedesol.basegestor.commons.constantes.ConstantesGestor;
import mx.gob.sedesol.basegestor.commons.dto.admin.AsentamientoDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.CapturaPersonaDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.DomicilioPersonaDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.PersonaCorreoDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.PersonaDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.PersonaTelefonoDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.RolDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.SsoElementoDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.UsuarioDatosLaboralesDTO;
import mx.gob.sedesol.basegestor.commons.dto.ws.ActualizarUsuarioRequest;
import mx.gob.sedesol.basegestor.commons.dto.ws.ActualizarUsuarioResponse;
import mx.gob.sedesol.basegestor.commons.dto.ws.ArrayOfElemento;
import mx.gob.sedesol.basegestor.commons.dto.ws.ArrayOfPerfil;
import mx.gob.sedesol.basegestor.commons.dto.ws.ArrayOfUsuarios;
import mx.gob.sedesol.basegestor.commons.dto.ws.ConsultarUsuariosRequest;
import mx.gob.sedesol.basegestor.commons.dto.ws.ConsultarUsuariosResponse;
import mx.gob.sedesol.basegestor.commons.dto.ws.Elemento;
import mx.gob.sedesol.basegestor.commons.dto.ws.EliminarUsuarioRequest;
import mx.gob.sedesol.basegestor.commons.dto.ws.EliminarUsuarioResponse;
import mx.gob.sedesol.basegestor.commons.dto.ws.GuardarUsuarioRequest;
import mx.gob.sedesol.basegestor.commons.dto.ws.GuardarUsuarioResponse;
import mx.gob.sedesol.basegestor.commons.dto.ws.Perfil;
import mx.gob.sedesol.basegestor.commons.dto.ws.Usuario;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.ResultadoTransaccionEnum;
import mx.gob.sedesol.basegestor.commons.utils.TipoUsuarioEnum;
import mx.gob.sedesol.basegestor.service.admin.PersonaCorreoService;
import mx.gob.sedesol.basegestor.service.admin.PersonaService;
import mx.gob.sedesol.basegestor.service.admin.UsuarioDatosLaboralesService;
import mx.gob.sedesol.basegestor.service.impl.admin.PersonaServiceFacade;

@RestController
@RequestMapping("/usuarios")
public class UsuariosREST {

	private static final Logger logger = Logger.getLogger(UsuariosREST.class);

	@Autowired
	private PersonaServiceFacade personaServiceFacade;

	@Autowired
	private PersonaService personaService;

	@Autowired
	private PersonaCorreoService personaCorreoService;

	@Autowired
	private UsuarioDatosLaboralesService datosLaboralesService;

	/* CODIGO DE MENSAJES DE ERROR ESTABLECIDOS POR INSSOFT */
	private static final int ERROR_DATOS_VACIOS = 11;
	private static final int ERROR_INSERTAR_DATOS = 12;
	private static final int ERROR_MODIFICACION = 21;
	private static final int ERROR_ELIMINAR_USUARIO = 51;

	/**
	 * Id para los registros que fueron insertados o modificados por servicios
	 * web de aprovisionamiento.
	 */
	private static final long ID_USUARIO_MODIFICO = 99999;

	@RequestMapping(value = "/guardarUsuario", method = RequestMethod.POST)
	public GuardarUsuarioResponse guardarUsuario(@RequestBody GuardarUsuarioRequest guardarUsuarioRequest,
			HttpServletResponse response) {

		Usuario usuario = guardarUsuarioRequest.getGuardarUsuarioRequestType().getUsuario();
		PersonaDTO persona = personaService.obtienePersonaPorNombreUsuario(usuario.getIdUsuario());

		ResultadoDTO<PersonaDTO> resultado = new ResultadoDTO<>();

		if (ObjectUtils.isNull(persona)) {
			resultado = personaService.guardarPersona(llenarDatosPersona(usuario));
		} else {
			resultado.agregaMensaje("El usuario ya existe.");
			resultado.setResultado(ResultadoTransaccionEnum.FALLIDO);
		}
		if (resultado.esCorrecto()) {
			response.setStatus(HttpServletResponse.SC_OK);
			return new GuardarUsuarioResponse(usuario);
		} else {
			response.setStatus(obtenerCodigoGuardar(resultado.getMensajes()));
			return new GuardarUsuarioResponse(usuario);
		}
	}

	@RequestMapping(value = "/actualizarUsuario", method = RequestMethod.POST)
	public ActualizarUsuarioResponse actualizarUsuario(@RequestBody ActualizarUsuarioRequest actualizarUsuarioRequest,
			HttpServletResponse response) {

		Usuario usuario = actualizarUsuarioRequest.getActualizarUsuarioRequestType().getUsuario();
		PersonaDTO persona = personaService.obtienePersonaPorNombreUsuario(usuario.getIdUsuario());

		ResultadoDTO<PersonaDTO> resultado = new ResultadoDTO<>();

		if (ObjectUtils.isNotNull(persona)) {

			resultado = personaService.actualizarPersona(actualizarDatos(persona, usuario));

		} else {
			resultado.agregaMensaje("El usuario no existe.");
			response.setStatus(ERROR_MODIFICACION);
		}
		if (resultado.esCorrecto()) {
			response.setStatus(HttpServletResponse.SC_OK);
			return new ActualizarUsuarioResponse(usuario);
		} else {
			response.setStatus(leerMensajesError(resultado.getMensajes(), ERROR_MODIFICACION));
			return new ActualizarUsuarioResponse(usuario);
		}
	}

	@RequestMapping(value = "/eliminarUsuario", method = RequestMethod.POST)
	public EliminarUsuarioResponse eliminarUsuario(@RequestBody EliminarUsuarioRequest eliminarUsuarioRequest,
			HttpServletResponse response) {

		String idUsuarioRecibido = eliminarUsuarioRequest.getEliminarUsuarioRequestType().getIdUsuario();
		String correoRecibido = eliminarUsuarioRequest.getEliminarUsuarioRequestType().getCorreoElectronico();

		ResultadoDTO<PersonaDTO> resultado = new ResultadoDTO<>();

		if (!ObjectUtils.isNullOrEmpty(idUsuarioRecibido) && !ObjectUtils.isNullOrEmpty(correoRecibido)) {

			PersonaDTO persona = personaService.obtienePersonaPorNombreUsuario(idUsuarioRecibido);
			String correoEncontrado = obtenerCorreoPersona(persona);

			if (correoRecibido.equalsIgnoreCase(correoEncontrado)) {
				resultado = personaService.desactivarPersona(persona);
				if (resultado.esCorrecto()) {
					response.setStatus(HttpServletResponse.SC_OK);
					return new EliminarUsuarioResponse(llenarUsuario(persona));
				}
			} else {
				resultado.agregaMensaje("El correo no coincide.");
			}
		} else {
			resultado.agregaMensaje("Falta usuario o correo.");
		}
		response.setStatus(leerMensajesError(resultado.getMensajes(), ERROR_ELIMINAR_USUARIO));
		return new EliminarUsuarioResponse(new Usuario());
	}

	@RequestMapping(value = "/consultarUsuarios", method = RequestMethod.POST)
	public ConsultarUsuariosResponse consultarUsuarios(@RequestBody ConsultarUsuariosRequest consultarUsuariosRequest) {

		Usuario usuario = consultarUsuariosRequest.getConsultarUsuariosRequestType().getUsuario();

		List<UsuarioDatosLaboralesDTO> listaDatosLaborales = datosLaboralesService
				.busquedaDatosLaboralesPorCriterios(llenarDatosLaborales(usuario));

		return new ConsultarUsuariosResponse(llenarUsuarios(listaDatosLaborales));
	}

	@RequestMapping(value = "/obtenerListaUsuarios")
	public ConsultarUsuariosResponse obtenerListaUsuarios() {

		List<UsuarioDatosLaboralesDTO> listaDatosLaborales = datosLaboralesService.findAll();

		return new ConsultarUsuariosResponse(llenarUsuarios(listaDatosLaborales));
	}

	private int leerMensajesError(List<String> mensajes, int codigoError) {
		for (String mensaje : mensajes) {
			logger.info(mensaje);
		}
		return codigoError;
	}

	private String obtenerCorreoPersona(PersonaDTO persona) {
		PersonaCorreoDTO correoPersona = personaCorreoService.obtenerCorreoInstitucional(persona.getIdPersona());
		return correoPersona.getCorreoElectronico();
	}

	private ArrayOfPerfil llenarPerfiles(List<RolDTO> roles) {
		ArrayOfPerfil perfiles = new ArrayOfPerfil();
		for (RolDTO rol : roles) {
			Perfil perfil = new Perfil();
			perfil.setIdPerfil(rol.getIdRol().toString());
			perfil.setClavePerfil(rol.getClave());
			perfil.setDescripcionPerfil(rol.getNombre());
			perfiles.getPerfil().add(perfil);
		}
		return perfiles;
	}

	private ArrayOfElemento llenarElementos(List<SsoElementoDTO> elementosDTOS) {
		ArrayOfElemento elementos = new ArrayOfElemento();
		for (SsoElementoDTO elementoDTO : elementosDTOS) {
			Elemento elemento = new Elemento();
			elemento.setIdPadreElemento(elementoDTO.getIdPadreElemento());
			elemento.setIdElemento(elementoDTO.getIdElemento());
			elemento.setNombreElemento(elementoDTO.getNombreElemento());
			elemento.setTipoInformacion(elementoDTO.getTipoInformacion());
			elemento.setiDFuente(elementoDTO.getiDFuente());
			elemento.setFuente(elementoDTO.getFuente());
			elemento.setTipoFuente(elementoDTO.getTipoFuente());
			elementos.getElemento().add(elemento);
		}
		return elementos;
	}

	private CapturaPersonaDTO llenarDatosPersona(Usuario usuario) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

		CapturaPersonaDTO datos = new CapturaPersonaDTO();

		datos.setPersona(new PersonaDTO(ID_USUARIO_MODIFICO, ConstantesGestor.ID_PAIS_MEXICO));
		datos.setDatosLaborales(new UsuarioDatosLaboralesDTO(ID_USUARIO_MODIFICO));
		datos.setTelefonoFijo(
				new PersonaTelefonoDTO(ID_USUARIO_MODIFICO, ConstantesGestor.TIPO_TELEFONO_INSTITUCIONAL));
		datos.setCelular(new PersonaTelefonoDTO(ID_USUARIO_MODIFICO, ConstantesGestor.TIPO_TELEFONO_MOVIL));
		datos.setPersonaCorreo(new PersonaCorreoDTO(ID_USUARIO_MODIFICO, ConstantesGestor.TIPO_CORREO_INSTITUCIONAL));
		datos.setDomicilioPersona(new DomicilioPersonaDTO(ID_USUARIO_MODIFICO, ConstantesGestor.ID_PAIS_MEXICO));

		datos.getPersona().setNuevaContrasenia("Plataforma.@13579&");
		datos.getPersona().setConfirmacionContrasenia("Plataforma.@13579&");
		datos.getPersona().setContraseniaEncriptada(encoder.encode(datos.getPersona().getNuevaContrasenia()));
		datos.getPersona().setTipoUsuario(TipoUsuarioEnum.INTERNO.getValor());
		datos.getPersona().setUsuario(usuario.getIdUsuario());
		datos.getPersona().setNombre(usuario.getNombre());
		datos.getPersona().setApellidoPaterno(usuario.getApellidoPaterno());
		datos.getPersona().setApellidoMaterno(usuario.getApellidoMaterno());
		datos.getPersona().setCurp(usuario.getCurp());
		datos.getPersona().setActivo(convierteEstatusBoolean(usuario.getStatus()));
		datos.getTelefonoFijo().setTelefono(usuario.getTelefono());
		datos.getTelefonoFijo().setExtension(usuario.getExtensionDeTelefono());
		datos.getPersonaCorreo().setCorreoElectronico(usuario.getCorreoElectronico());
		datos.getDatosLaborales().setPuesto(usuario.getCargoPuesto());
		datos.getDatosLaborales().setIdOrdenGobierno(usuario.getIdOrdenDeGobierno());
		datos.getDatosLaborales().setOrdenGobierno(usuario.getOrdenDeGobierno());

		// Campos que se agregaron a tbl_persona para historico
		datos.getPersona().setIdEntidadFederativa(usuario.getIdEntidadFederativa());
		datos.getPersona().setEntidadFederativa(usuario.getEntidadFederativa());
		datos.getPersona().setIdMunicipio(usuario.getIdMunicipio());
		datos.getPersona().setMunicipio(usuario.getMunicipio());
		datos.getPersona().setIdDependencia(usuario.getIdDependencia());
		datos.getPersona().setClaveDependencia(usuario.getClaveDependencia());
		datos.getPersona().setDependencia(usuario.getDependencia());
		datos.getPersona().setIdUnidadAdministrativa(usuario.getIdUnidadAdministrativa());
		datos.getPersona().setClaveUnidadAdministrativa(usuario.getClaveUnidadAdministrativa());
		datos.getPersona().setUnidadAdministrativa(usuario.getUnidadAdministrativa());
		datos.getPersona().setRegistradoPor(usuario.getRegistradoPor());

		datos.getDomicilioPersona().setIdEntidadFederativa(1);
		datos.getDomicilioPersona().setIdMunicipio("1001");
		datos.getDatosLaborales().getSede().setIdEntidadFederativa(1);
		datos.getDatosLaborales().getMunicipio().setIdMunicipio("1001");
		datos.getDatosLaborales().setInstitucion(usuario.getDependencia());
		datos.getDomicilioPersona().setAsentamiento(new AsentamientoDTO());
		datos.getDomicilioPersona().setActivo(1);
		datos.getDomicilioPersona().setUsuarioModifico(ID_USUARIO_MODIFICO);
		datos.getDomicilioPersona().setCalle("N/A");
		datos.getDomicilioPersona().setNumeroExterior("N/A");
		datos.getDomicilioPersona().getAsentamiento().setIdAsentamiento("90161745");

		llenarListaRoles(usuario, datos);
		llenarListaElementos(usuario, datos);

		return datos;
	}

	private void llenarListaElementos(Usuario usuario, CapturaPersonaDTO datos) {
		for (Elemento elemento : usuario.getElementos().getElemento()) {
			SsoElementoDTO dto = new SsoElementoDTO();
			dto.setIdPadreElemento(elemento.getIdPadreElemento());
			dto.setIdElemento(elemento.getIdElemento());
			dto.setNombreElemento(elemento.getNombreElemento());
			dto.setTipoInformacion(elemento.getTipoInformacion());
			dto.setiDFuente(elemento.getiDFuente());
			dto.setFuente(elemento.getFuente());
			dto.setTipoFuente(elemento.getTipoFuente());
			dto.setFechaRegistro(new Date());
			dto.setFechaActualizacion(new Date());
			dto.setUsuarioModifico(ID_USUARIO_MODIFICO);
			datos.getElementos().add(dto);
		}
	}

	private void reemplazarListaElementos(Usuario usuario, CapturaPersonaDTO datos) {
		datos.setElementos(new ArrayList<>());
		for (Elemento elemento : usuario.getElementos().getElemento()) {
			SsoElementoDTO dto = new SsoElementoDTO();
			dto.setIdPadreElemento(elemento.getIdPadreElemento());
			dto.setIdElemento(elemento.getIdElemento());
			dto.setNombreElemento(elemento.getNombreElemento());
			dto.setTipoInformacion(elemento.getTipoInformacion());
			dto.setiDFuente(elemento.getiDFuente());
			dto.setFuente(elemento.getFuente());
			dto.setTipoFuente(elemento.getTipoFuente());
			dto.setFechaRegistro(new Date());
			dto.setFechaActualizacion(new Date());
			dto.setUsuarioModifico(ID_USUARIO_MODIFICO);
			datos.getElementos().add(dto);
		}
	}

	private void llenarListaRoles(Usuario usuario, CapturaPersonaDTO datos) {
		for (Perfil perfil : usuario.getPerfiles().getPerfil()) {
			int idRol = 0;
			try {
				idRol = Integer.parseInt(perfil.getIdPerfil());
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}

			RolDTO rolDTO = personaServiceFacade.getRoleService().buscarPorId(idRol);
			if (ObjectUtils.isNotNull(rolDTO)) {
				datos.getRoles().add(rolDTO);
			}
		}
	}

	private CapturaPersonaDTO actualizarDatos(PersonaDTO personaDTO, Usuario usuario) {
		CapturaPersonaDTO datos = personaServiceFacade.obtenerDatosPersona(personaDTO, ID_USUARIO_MODIFICO);

		if (ObjectUtils.isNullOrEmpty(usuario.getNombre())) {
			datos.getPersona().setNombre(null);
		} else {
			datos.getPersona().setNombre(usuario.getNombre());
		}

		if (ObjectUtils.isNullOrEmpty(usuario.getApellidoPaterno())) {
			datos.getPersona().setApellidoPaterno(null);
		} else {
			datos.getPersona().setApellidoPaterno(usuario.getApellidoPaterno());
		}

		if (ObjectUtils.isNullOrEmpty(usuario.getApellidoMaterno())) {
			datos.getPersona().setApellidoMaterno(null);
		} else {
			datos.getPersona().setApellidoMaterno(usuario.getApellidoMaterno());
		}

		if (ObjectUtils.isNullOrEmpty(usuario.getCurp())) {
			datos.getPersona().setCurp(null);
		} else {
			datos.getPersona().setCurp(usuario.getCurp());
		}

		if (ObjectUtils.isNullOrEmpty(usuario.getStatus())) {
			datos.getPersona().setActivo(null);
		} else {
			datos.getPersona().setActivo(convierteEstatusBoolean(usuario.getStatus()));
		}

		if (ObjectUtils.isNullOrEmpty(usuario.getTelefono())) {
			datos.getTelefonoFijo().setTelefono(null);
		} else {
			datos.getTelefonoFijo().setTelefono(usuario.getTelefono());
		}

		if (ObjectUtils.isNullOrEmpty(usuario.getExtensionDeTelefono())) {
			datos.getTelefonoFijo().setExtension(null);
		} else {
			datos.getTelefonoFijo().setExtension(usuario.getExtensionDeTelefono());
		}

		if (ObjectUtils.isNullOrEmpty(usuario.getCorreoElectronico())) {
			datos.getCorreoDePersonaEnBD().setCorreoElectronico(null);
			datos.getPersonaCorreo().setCorreoElectronico(null);
		} else {
			/* Creamos un respaldo del correo que tiene la persona en la BD. */
			datos.getCorreoDePersonaEnBD().setCorreoElectronico(datos.getPersonaCorreo().getCorreoElectronico());
			/* Guardamos el nuevo correo */
			datos.getPersonaCorreo().setCorreoElectronico(usuario.getCorreoElectronico());
		}

		if (ObjectUtils.isNullOrEmpty(usuario.getCargoPuesto())) {
			datos.getDatosLaborales().setPuesto(null);
		} else {
			datos.getDatosLaborales().setPuesto(usuario.getCargoPuesto());
		}

		if (ObjectUtils.isNullOrEmpty(usuario.getIdOrdenDeGobierno())) {
			datos.getDatosLaborales().setIdOrdenGobierno(null);
		} else {
			datos.getDatosLaborales().setIdOrdenGobierno(usuario.getIdOrdenDeGobierno());
		}

		if (ObjectUtils.isNullOrEmpty(usuario.getOrdenDeGobierno())) {
			datos.getDatosLaborales().setOrdenGobierno(null);
		} else {
			datos.getDatosLaborales().setOrdenGobierno(usuario.getOrdenDeGobierno());
		}

		if (ObjectUtils.isNullOrEmpty(usuario.getIdEntidadFederativa())) {
			datos.getPersona().setIdEntidadFederativa(null);
		} else {
			datos.getPersona().setIdEntidadFederativa(usuario.getIdEntidadFederativa());
		}

		if (ObjectUtils.isNullOrEmpty(usuario.getEntidadFederativa())) {
			datos.getPersona().setEntidadFederativa(null);
		} else {
			datos.getPersona().setEntidadFederativa(usuario.getEntidadFederativa());
		}

		if (ObjectUtils.isNullOrEmpty(usuario.getIdMunicipio())) {
			datos.getPersona().setIdMunicipio(null);
		} else {
			datos.getPersona().setIdMunicipio(usuario.getIdMunicipio());
		}

		if (ObjectUtils.isNullOrEmpty(usuario.getMunicipio())) {
			datos.getPersona().setMunicipio(null);
		} else {
			datos.getPersona().setMunicipio(usuario.getMunicipio());
		}

		if (ObjectUtils.isNullOrEmpty(usuario.getIdDependencia())) {
			datos.getPersona().setIdDependencia(null);
		} else {
			datos.getPersona().setIdDependencia(usuario.getIdDependencia());
		}

		if (ObjectUtils.isNullOrEmpty(usuario.getClaveDependencia())) {
			datos.getPersona().setClaveDependencia(null);
		} else {
			datos.getPersona().setClaveDependencia(usuario.getClaveDependencia());
		}

		if (ObjectUtils.isNullOrEmpty(usuario.getDependencia())) {
			datos.getPersona().setDependencia(null);
		} else {
			datos.getPersona().setDependencia(usuario.getDependencia());
		}

		if (ObjectUtils.isNullOrEmpty(usuario.getIdUnidadAdministrativa())) {
			datos.getPersona().setIdUnidadAdministrativa(null);
		} else {
			datos.getPersona().setIdUnidadAdministrativa(usuario.getIdUnidadAdministrativa());
		}

		if (ObjectUtils.isNullOrEmpty(usuario.getClaveUnidadAdministrativa())) {
			datos.getPersona().setClaveUnidadAdministrativa(null);
		} else {
			datos.getPersona().setClaveUnidadAdministrativa(usuario.getClaveUnidadAdministrativa());
		}

		if (ObjectUtils.isNullOrEmpty(usuario.getUnidadAdministrativa())) {
			datos.getPersona().setUnidadAdministrativa(null);
		} else {
			datos.getPersona().setUnidadAdministrativa(usuario.getUnidadAdministrativa());
		}

		if (ObjectUtils.isNullOrEmpty(usuario.getRegistradoPor())) {
			datos.getPersona().setRegistradoPor(null);
		} else {
			datos.getPersona().setRegistradoPor(usuario.getRegistradoPor());
		}

		if (ObjectUtils.isNullOrEmpty(usuario.getDependencia())) {
			datos.getDatosLaborales().setInstitucion(null);
		} else {
			datos.getDatosLaborales().setInstitucion(usuario.getDependencia());
		}

		datos.setRoles(new ArrayList<>());
		llenarListaRoles(usuario, datos);
		reemplazarListaElementos(usuario, datos);

		datos.getPersona().setFechaActualizacion(new Date());
		datos.getTelefonoFijo().setFechaActualizacion(new Date());
		datos.getPersonaCorreo().setFechaActualizacion(new Date());
		datos.getDatosLaborales().setFechaActualizacion(new Date());
		return datos;
	}

	private int obtenerCodigoGuardar(List<String> mensajes) {
		int codigo = ERROR_INSERTAR_DATOS;
		if (ObjectUtils.isNotNull(mensajes)) {
			for (String mensaje : mensajes) {
				if (mensaje.indexOf(".req") >= 0) {
					codigo = ERROR_DATOS_VACIOS;
					logger.info(mensaje);
					break;
				}
			}
		}
		return codigo;
	}

	private ArrayOfUsuarios llenarUsuarios(List<UsuarioDatosLaboralesDTO> listaDatosLaboralesDTO) {
		ArrayOfUsuarios usuarios = new ArrayOfUsuarios();
		for (UsuarioDatosLaboralesDTO datosLaboralesDTO : listaDatosLaboralesDTO) {
			usuarios.getUsuario().add(llenarUsuario(datosLaboralesDTO.getPersona()));
		}
		return usuarios;
	}

	private Usuario llenarUsuario(PersonaDTO persona) {
		CapturaPersonaDTO datosPersona = personaServiceFacade.obtenerDatosPersona(persona, ID_USUARIO_MODIFICO);
		Usuario usuario = new Usuario();
		usuario.setIdUsuario(datosPersona.getPersona().getUsuario());
		usuario.setNombre(datosPersona.getPersona().getNombre());
		usuario.setApellidoPaterno(datosPersona.getPersona().getApellidoPaterno());
		usuario.setApellidoMaterno(datosPersona.getPersona().getApellidoMaterno());
		usuario.setTelefono(datosPersona.getTelefonoFijo().getTelefono());
		usuario.setExtensionDeTelefono(datosPersona.getTelefonoFijo().getExtension());
		usuario.setCurp(datosPersona.getPersona().getCurp());
		usuario.setCorreoElectronico(datosPersona.getPersonaCorreo().getCorreoElectronico());
		usuario.setCargoPuesto(datosPersona.getDatosLaborales().getPuesto());
		usuario.setIdOrdenDeGobierno(datosPersona.getDatosLaborales().getIdOrdenGobierno());
		usuario.setOrdenDeGobierno(datosPersona.getDatosLaborales().getOrdenGobierno());
		usuario.setIdEntidadFederativa(datosPersona.getPersona().getIdEntidadFederativa());
		usuario.setEntidadFederativa(datosPersona.getPersona().getEntidadFederativa());
		usuario.setIdMunicipio(datosPersona.getPersona().getIdMunicipio());
		usuario.setMunicipio(datosPersona.getPersona().getMunicipio());
		usuario.setIdDependencia(datosPersona.getPersona().getIdDependencia());
		usuario.setClaveDependencia(datosPersona.getPersona().getClaveDependencia());
		usuario.setDependencia(datosPersona.getPersona().getDependencia());
		usuario.setIdUnidadAdministrativa(datosPersona.getPersona().getIdUnidadAdministrativa());
		usuario.setClaveUnidadAdministrativa(datosPersona.getPersona().getClaveUnidadAdministrativa());
		usuario.setUnidadAdministrativa(datosPersona.getPersona().getUnidadAdministrativa());
		usuario.setRegistradoPor(datosPersona.getPersona().getRegistradoPor());
		usuario.setStatus(convierteEstatusString(datosPersona.getPersona().getActivo()));
		usuario.setPerfiles(llenarPerfiles(datosPersona.getRoles()));
		usuario.setElementos(llenarElementos(datosPersona.getElementos()));
		return usuario;
	}

	private UsuarioDatosLaboralesDTO llenarDatosLaborales(Usuario usuario) {
		UsuarioDatosLaboralesDTO dto = new UsuarioDatosLaboralesDTO(new PersonaDTO());
		dto.getPersona().setUsuario(usuario.getIdUsuario());
		dto.getPersona().setNombre(usuario.getNombre());
		dto.getPersona().setApellidoPaterno(usuario.getApellidoPaterno());
		dto.getPersona().setApellidoMaterno(usuario.getApellidoMaterno());
		dto.getPersona().setRelPersonaTelefonos(obtenerTelefono(usuario));
		dto.getPersona().setCurp(usuario.getCurp());
		dto.getPersona().setPersonaCorreos(obtenerCorreoElectronico(usuario));
		dto.getPersona().setActivo(convierteEstatusBooleanValidando(usuario.getStatus()));
		dto.setPuesto(usuario.getCargoPuesto());
		dto.setIdOrdenGobierno(usuario.getIdOrdenDeGobierno());
		dto.setOrdenGobierno(usuario.getOrdenDeGobierno());
		dto.getPersona().setIdEntidadFederativa(usuario.getIdEntidadFederativa());
		dto.getPersona().setEntidadFederativa(usuario.getEntidadFederativa());
		dto.getPersona().setIdMunicipio(usuario.getIdMunicipio());
		dto.getPersona().setMunicipio(usuario.getMunicipio());
		dto.getPersona().setIdDependencia(usuario.getIdDependencia());
		dto.getPersona().setClaveDependencia(usuario.getClaveDependencia());
		dto.getPersona().setDependencia(usuario.getDependencia());
		dto.getPersona().setIdUnidadAdministrativa(usuario.getIdUnidadAdministrativa());
		dto.getPersona().setClaveUnidadAdministrativa(usuario.getClaveUnidadAdministrativa());
		dto.getPersona().setUnidadAdministrativa(usuario.getUnidadAdministrativa());
		dto.getPersona().setRegistradoPor(usuario.getRegistradoPor());
		return dto;
	}

	private boolean convierteEstatusBoolean(String estatus) {
		if (!ObjectUtils.isNullOrEmpty(estatus)) {
			if (estatus.equalsIgnoreCase("activo")) {
				return true;
			} else if (estatus.equalsIgnoreCase("inactivo")) {
				return false;
			} else {
				return true;
			}
		} else {
			return true;
		}
	}

	private Boolean convierteEstatusBooleanValidando(String estatus) {
		if (!ObjectUtils.isNullOrEmpty(estatus)) {
			if (estatus.equalsIgnoreCase("activo")) {
				return true;
			} else if (estatus.equalsIgnoreCase("inactivo")) {
				return false;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	private String convierteEstatusString(boolean estatus) {
		if (estatus == true) {
			return "activo";
		} else {
			return "inactivo";
		}

	}

	private List<PersonaTelefonoDTO> obtenerTelefono(Usuario usuario) {
		if (!ObjectUtils.isNullOrEmpty(usuario.getTelefono())) {
			PersonaTelefonoDTO telefono = new PersonaTelefonoDTO();
			telefono.setTelefono(usuario.getTelefono());
			telefono.setExtension(usuario.getExtensionDeTelefono());
			List<PersonaTelefonoDTO> telefonos = new ArrayList<>();
			telefonos.add(telefono);
			return telefonos;
		}
		return null;

	}

	private List<PersonaCorreoDTO> obtenerCorreoElectronico(Usuario usuario) {
		if (!ObjectUtils.isNullOrEmpty(usuario.getCorreoElectronico())) {
			PersonaCorreoDTO correoElectronico = new PersonaCorreoDTO();
			correoElectronico.setCorreoElectronico(usuario.getCorreoElectronico());
			List<PersonaCorreoDTO> correos = new ArrayList<>();
			correos.add(correoElectronico);
			return correos;

		}
		return null;

	}

}
