package mx.gob.sedesol.basegestor.service.impl.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.gob.sedesol.basegestor.commons.constantes.ConstantesGestor;
import mx.gob.sedesol.basegestor.commons.dto.admin.AsentamientoDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.CapturaPersonaDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.EntidadFederativaDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.MunicipioDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.PaisDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.PersonaDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.PersonaRolDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.RolDTO;
import mx.gob.sedesol.basegestor.service.ParametroSistemaService;
import mx.gob.sedesol.basegestor.service.admin.AsentamientoService;
import mx.gob.sedesol.basegestor.service.admin.EntidadFederativaService;
import mx.gob.sedesol.basegestor.service.admin.MunicipioService;
import mx.gob.sedesol.basegestor.service.admin.PaisService;
import mx.gob.sedesol.basegestor.service.admin.PersonaCorreoService;
import mx.gob.sedesol.basegestor.service.admin.PersonaRolesService;
import mx.gob.sedesol.basegestor.service.admin.PersonaService;
import mx.gob.sedesol.basegestor.service.admin.PersonaTelefonoService;
import mx.gob.sedesol.basegestor.service.admin.RoleService;
import mx.gob.sedesol.basegestor.service.admin.UsuarioDatosLaboralesService;

@Service("personaServiceFacade")
public class PersonaServiceFacade {

	@Autowired
	private PersonaService personaService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private PersonaRolesService personaRolesService;

	@Autowired
	private PaisService paisService;

	@Autowired
	private EntidadFederativaService entidadFederativaService;

	@Autowired
	private MunicipioService municipioService;

	@Autowired
	private AsentamientoService asentamientoService;

	@Autowired
	private ParametroSistemaService parametroSistemaService;

	@Autowired
	private UsuarioDatosLaboralesService usuarioDatosLaboralesService;

	@Autowired
	private PersonaCorreoService personaCorreoService;
	
	@Autowired
	private PersonaTelefonoService personaTelefonoService;
	
	public List<PaisDTO> obtenerPaises() {
		return paisService.findAll();
	}

	public List<EntidadFederativaDTO> obtenerEntidadesPorPais(String idPais) {
		return entidadFederativaService.obtenerEntidadesPorPais(idPais);
	}

	public List<MunicipioDTO> obtenerMunicipiosPorEntidad(Integer idEntidad) {
		return municipioService.buscarPorEntidadFederativa(idEntidad);
	}

	public List<AsentamientoDTO> obtenerAsentamientosPorMunicipio(String idMunicipio) {
		return asentamientoService.buscarPorMunicipio(idMunicipio);
	}

	public AsentamientoDTO obtenerAsentamientoPorCodigoPostal(String codigoPostal) {
		return asentamientoService.buscarPorCodigoPostal(codigoPostal);
	}

	public List<PersonaDTO> buscarPersonaPorCriterios(PersonaDTO filtros) {
		return personaService.busquedaPorCriterios(filtros);
	}
	

	public PersonaDTO obtenerPersonaPorId(Long idPersona) {
		return personaService.buscarPorId(idPersona);
	}

	public CapturaPersonaDTO obtenerDatosPersona(PersonaDTO persona, Long usuarioModifico) {
		return personaService.obtenerDatosPersona(persona, usuarioModifico);
	}

	public ResultadoDTO<PersonaDTO> guardarPersona(CapturaPersonaDTO datos) {
		return personaService.guardarPersona(datos);
	}

	public ResultadoDTO<PersonaDTO> actualizarPersona(CapturaPersonaDTO datos) {
		return personaService.actualizarPersona(datos);
	}

	public ResultadoDTO<PersonaDTO> actualizarPersona(PersonaDTO persona) {
		return personaService.actualizar(persona);
	}

	public List<RolDTO> obtenerTodosRoles() {
		return roleService.findAll();
	}
	
	public RolDTO obtenerRolAlumno(){
		return roleService.buscarPorId(2);
	}

	public List<PersonaRolDTO> obtenerRolesPorUsuario(String usuario) {
		return personaRolesService.obtieneRelPersonaRolesPorUsuario(usuario);
	}

	public ResultadoDTO<PersonaRolDTO> almacenarRolesUsuario(PersonaDTO persona, List<RolDTO> roles,
			long usuarioModifico) {
		return personaRolesService.almacenarRolesUsuario(persona, roles, usuarioModifico);
	}

	public String obtenerRutaAlmacenamientoFotosUsuario() {
		StringBuilder rutaAlmacenamiento = new StringBuilder(
				parametroSistemaService.obtenerParametroConRutaCompleta(ConstantesGestor.PARAMETRO_RUTA_RECURSOS));
		rutaAlmacenamiento
				.append(parametroSistemaService.obtenerParametroRuta(ConstantesGestor.PARAMETRO_RUTA_FOTOS_USUARIOS));
		return rutaAlmacenamiento.toString();
	}

	public String obtenerRutaAlmacenamientoBadge() {
		StringBuilder rutaAlmacenamiento = new StringBuilder(
				parametroSistemaService.obtenerParametroConRutaCompleta(ConstantesGestor.PARAMETRO_RUTA_RECURSOS));
		rutaAlmacenamiento
				.append(parametroSistemaService.obtenerParametroRuta(ConstantesGestor.PARAMETRO_RUTA_INSIGNIAS));
		return rutaAlmacenamiento.toString();
	}

	public String obtenerNombreImagenComun() {
		return parametroSistemaService.obtenerParametro(ConstantesGestor.PARAMETRO_NOMBRE_FOTO_COMUN);
	}

	public String obtenerRutaUndertow() {
		StringBuilder rutaAlmacenamiento = new StringBuilder(
				parametroSistemaService.obtenerParametroRuta(ConstantesGestor.PARAMETRO_RUTA_UNDERTOW));
		rutaAlmacenamiento
				.append(parametroSistemaService.obtenerParametroRuta(ConstantesGestor.PARAMETRO_RUTA_FOTOS_USUARIOS));
		return rutaAlmacenamiento.toString();
	}

	public String obtenerRutaUndertowBadges() {
		StringBuilder rutaAlmacenamiento = new StringBuilder(
				parametroSistemaService.obtenerParametroRuta(ConstantesGestor.PARAMETRO_RUTA_UNDERTOW));
		rutaAlmacenamiento
				.append(parametroSistemaService.obtenerParametroRuta(ConstantesGestor.PARAMETRO_RUTA_INSIGNIAS));
		return rutaAlmacenamiento.toString();
	}

	public PersonaService getPersonaService() {
		return personaService;
	}

	public PaisService getPaisService() {
		return paisService;
	}

	public EntidadFederativaService getEntidadFederativaService() {
		return entidadFederativaService;
	}

	public MunicipioService getMunicipioService() {
		return municipioService;
	}

	public AsentamientoService getAsentamientoService() {
		return asentamientoService;
	}

	public ParametroSistemaService getParametroSistemaService() {
		return parametroSistemaService;
	}

	public UsuarioDatosLaboralesService getUsuarioDatosLaboralesService() {
		return usuarioDatosLaboralesService;
	}

	public void setPersonaService(PersonaService personaService) {
		this.personaService = personaService;
	}

	public void setPaisService(PaisService paisService) {
		this.paisService = paisService;
	}

	public void setEntidadFederativaService(EntidadFederativaService entidadFederativaService) {
		this.entidadFederativaService = entidadFederativaService;
	}

	public void setMunicipioService(MunicipioService municipioService) {
		this.municipioService = municipioService;
	}

	public void setAsentamientoService(AsentamientoService asentamientoService) {
		this.asentamientoService = asentamientoService;
	}

	public void setParametroSistemaService(ParametroSistemaService parametroSistemaService) {
		this.parametroSistemaService = parametroSistemaService;
	}

	public void setUsuarioDatosLaboralesService(UsuarioDatosLaboralesService usuarioDatosLaboralesService) {
		this.usuarioDatosLaboralesService = usuarioDatosLaboralesService;
	}

	public RoleService getRoleService() {
		return roleService;
	}

	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	public PersonaRolesService getPersonaRolesService() {
		return personaRolesService;
	}

	public void setPersonaRolesService(PersonaRolesService personaRolesService) {
		this.personaRolesService = personaRolesService;
	}

	public PersonaCorreoService getPersonaCorreoService() {
		return personaCorreoService;
	}

	public void setPersonaCorreoService(PersonaCorreoService personaCorreoService) {
		this.personaCorreoService = personaCorreoService;
	}

	public PersonaTelefonoService getPersonaTelefonoService() {
		return personaTelefonoService;
	}

	public void setPersonaTelefonoService(PersonaTelefonoService personaTelefonoService) {
		this.personaTelefonoService = personaTelefonoService;
	}

}
