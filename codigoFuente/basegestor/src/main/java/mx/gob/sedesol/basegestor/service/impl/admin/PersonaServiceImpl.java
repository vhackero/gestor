package mx.gob.sedesol.basegestor.service.impl.admin;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.commons.mail.EmailException;
import org.apache.log4j.Logger;
import org.apache.poi.ss.formula.functions.T;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.gob.sedesol.basegestor.commons.constantes.ConstantesGestor;
import mx.gob.sedesol.basegestor.commons.dto.admin.AsentamientoDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.CapturaPersonaDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.CorreoDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.DatoSociodemograficoDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.DiscapacidadDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.DomicilioPersonaDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.EntidadFederativaDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.LenguajeIndigenaDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.LoteCargaUsuarioDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.MunicipioDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.PaisDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.PersonaCargaDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.PersonaCorreoDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.PersonaDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.PersonaDatosAcademicoDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.PersonaDatosDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.PersonaRolDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.PersonaTelefonoDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoCargaDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.RolDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.SsoElementoDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.UsuarioDatosLaboralesDTO;
import mx.gob.sedesol.basegestor.commons.utils.ContieneProgramaEnum;
import mx.gob.sedesol.basegestor.commons.utils.CorreoUtil;
import mx.gob.sedesol.basegestor.commons.utils.CurpUtils;
import mx.gob.sedesol.basegestor.commons.utils.MensajesSistemaEnum;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.OrdenGobiernoEnum;
import mx.gob.sedesol.basegestor.commons.utils.ResultadoTransaccionEnum;
import mx.gob.sedesol.basegestor.commons.utils.TipoAccion;
import mx.gob.sedesol.basegestor.commons.utils.TipoUsuarioEnum;
import mx.gob.sedesol.basegestor.commons.utils.UtilidadesCargaMasiva;
import mx.gob.sedesol.basegestor.model.entities.admin.CatEntidadFederativa;
import mx.gob.sedesol.basegestor.model.entities.admin.CatMunicipio;
import mx.gob.sedesol.basegestor.model.entities.admin.CatRol;
import mx.gob.sedesol.basegestor.model.entities.admin.CatTipoDiscapacidad;
import mx.gob.sedesol.basegestor.model.entities.admin.RelLoteUsuario;
import mx.gob.sedesol.basegestor.model.entities.admin.RelPersonaCorreo;
import mx.gob.sedesol.basegestor.model.entities.admin.RelPersonaDatosAcademico;
import mx.gob.sedesol.basegestor.model.entities.admin.RelPersonaRol;
import mx.gob.sedesol.basegestor.model.entities.admin.RelPersonaTelefono;
import mx.gob.sedesol.basegestor.model.entities.admin.RelUsuarioDatosLaborales;
import mx.gob.sedesol.basegestor.model.entities.admin.SsoElemento;
import mx.gob.sedesol.basegestor.model.entities.admin.TblDatosSociodemograficosPersona;
import mx.gob.sedesol.basegestor.model.entities.admin.TblDomiciliosPersona;
import mx.gob.sedesol.basegestor.model.entities.admin.TblLoteCargaUsuario;
import mx.gob.sedesol.basegestor.model.entities.admin.TblPersona;
import mx.gob.sedesol.basegestor.model.entities.admin.TblPersonaBasica;
import mx.gob.sedesol.basegestor.model.entities.admin.TblTextoSistema;
import mx.gob.sedesol.basegestor.model.especificaciones.DatosLaboralesEspecificacion;
import mx.gob.sedesol.basegestor.model.especificaciones.PersonaBasicaEspecificacion;
import mx.gob.sedesol.basegestor.model.especificaciones.PersonaEspecificacion;
import mx.gob.sedesol.basegestor.model.repositories.admin.DatoSociodemograficoPersonaRepo;
import mx.gob.sedesol.basegestor.model.repositories.admin.DomicilioPersonaRepo;
import mx.gob.sedesol.basegestor.model.repositories.admin.EntidadFederativaRepo;
import mx.gob.sedesol.basegestor.model.repositories.admin.IUsuariosImportarRepo;
import mx.gob.sedesol.basegestor.model.repositories.admin.LoteCargaUsuarioRepo;
import mx.gob.sedesol.basegestor.model.repositories.admin.LoteUsuarioRepo;
import mx.gob.sedesol.basegestor.model.repositories.admin.MunicipioRepo;
import mx.gob.sedesol.basegestor.model.repositories.admin.PersonaBasicaRepo;
import mx.gob.sedesol.basegestor.model.repositories.admin.PersonaCorreoRepo;
import mx.gob.sedesol.basegestor.model.repositories.admin.PersonaDatosAcademicosRepo;
import mx.gob.sedesol.basegestor.model.repositories.admin.PersonaRepo;
import mx.gob.sedesol.basegestor.model.repositories.admin.PersonaRolesRepo;
import mx.gob.sedesol.basegestor.model.repositories.admin.PersonaTelefonoRepo;
import mx.gob.sedesol.basegestor.model.repositories.admin.SsoElementoRepo;
import mx.gob.sedesol.basegestor.model.repositories.admin.TextoSistemaRepo;
import mx.gob.sedesol.basegestor.model.repositories.admin.UsuarioDatosLaboralesRepo;
import mx.gob.sedesol.basegestor.service.admin.ComunValidacionService;
import mx.gob.sedesol.basegestor.service.admin.EntidadFederativaService;
import mx.gob.sedesol.basegestor.service.admin.PersonaCorreoService;
import mx.gob.sedesol.basegestor.service.admin.PersonaDatosAcademicosService;
import mx.gob.sedesol.basegestor.service.admin.PersonaRolesService;
import mx.gob.sedesol.basegestor.service.admin.PersonaService;
import mx.gob.sedesol.basegestor.service.admin.RoleService;

@Service("personaService")
public class PersonaServiceImpl extends ComunValidacionService<PersonaDTO> implements PersonaService {

	private static final Logger logger = Logger.getLogger(PersonaServiceImpl.class);

	@Autowired
	private PersonaRepo personaRepo;
	
	@Autowired
	private PersonaBasicaRepo personaBasicaRepo;
	
	@Autowired
	private UsuarioDatosLaboralesRepo usuarioDatosLaboralesRepo;
	@Autowired
	private PersonaTelefonoRepo personaTelefonoRepo;
	@Autowired
	private PersonaCorreoRepo personaCorreoRepo;
	@Autowired
	private EntidadFederativaRepo entidadFederativaRepo;
	@Autowired
	private MunicipioRepo municipioRepo;
	@Autowired
	private DomicilioPersonaRepo domicilioPersonaRepo;
	@Autowired
	private PersonaRolesRepo personaRolesRepo;
	@Autowired
	private LoteCargaUsuarioRepo loteCargaUsuarioRepo;
	@Autowired
	private LoteUsuarioRepo loteUsuarioRepo;
	@Autowired
	private RoleService rolService;
	@Autowired
	private PersonaRolesService personaRolesService;
	@Autowired
	private TextoSistemaRepo textoSistemaRepo;
	@Autowired
	private PersonaDatosAcademicosService personaDatosAcademicos;
	@Autowired
	private PersonaDatosAcademicosRepo personaDatosAcademicosRepo;

	@Autowired
	private PersonaCorreoService personaCorreoService;

	@Autowired
	private EntidadFederativaService entidadFederativaService;

	@Autowired
	private SsoElementoRepo ssoElementoRepo;
	
	@Autowired
	private DatoSociodemograficoPersonaRepo datoSociodemograficoPersonaRepo;
	
	@Autowired
	private IUsuariosImportarRepo usuariosImportarRepo;

	private ModelMapper mapper = new ModelMapper();

	Type personasDto = new TypeToken<List<PersonaDTO>>() {
	}.getType();

	@Override
	public List<PersonaDTO> findAll() {
		List<TblPersona> personas = personaRepo.findAll();
		for (TblPersona personaEnt : personas) {
			personaEnt.setPersonaCorreos(null);
			personaEnt.setRelPersonaTelefonos(null);
			personaEnt.setRelPersonaRoles(null);
			personaEnt.setDomiciliosPersonas(null);
			personaEnt.setRelPersonaElementos(null);
		}
		return mapper.map(personas, personasDto);
	}

	public List<PersonaDTO> obtenerTodasLasPersonas() {
		List<TblPersona> personas = personaRepo.findAll();
		return mapper.map(personas, personasDto);
	}

	@Override
	public PersonaDTO buscarPorId(Long idPersona) {
		TblPersona personaEnt = personaRepo.findOne(idPersona);
		PersonaDTO personaDTO = new PersonaDTO();
		if (ObjectUtils.isNotNull(personaEnt)) {
			personaEnt.setPersonaCorreos(null);
			personaEnt.setRelPersonaTelefonos(null);
			personaEnt.setRelPersonaRoles(null);
			personaEnt.setDomiciliosPersonas(null);
			personaEnt.setRelPersonaElementos(null);
			personaEnt.setDatosLaborales(null);
			mapper.map(personaEnt, personaDTO);
		}
		return personaDTO;
	}

	@Override
	public PersonaDTO obtienePersonaPorNombreUsuario(String usuario) {

		TblPersona persona = this.buscarPorNombreUsuario(usuario);
		if (ObjectUtils.isNotNull(persona)) {
			return mapper.map(persona, PersonaDTO.class);
		}
		return null;

	}

	@Override
	public TblPersona buscarPorNombreUsuario(String usuario) {
		TblPersona persona = personaRepo.obtienePersonaPorNombreUsuario(usuario);
		return persona;

	}

	@Override
	public List<PersonaDTO> busquedaPorCriterios(PersonaDTO personaDto) {

		try {
			if (ObjectUtils.isNotNull(personaDto)) {
				TblPersona personaCriterios = mapper.map(personaDto, TblPersona.class);

				Specification<TblPersona> spec = new PersonaEspecificacion(personaCriterios);

				List<TblPersona> resultadoCriterios = personaRepo.findAll(spec, sortByFechaActualizacionDesc());

				return mapper.map(resultadoCriterios, personasDto);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return new ArrayList<>();
	}
	
	@Override
	public List<PersonaDTO> busquedaPorCriteriosPersonaBasica(PersonaDTO personaDto) {

		try {
			if (ObjectUtils.isNotNull(personaDto)) {
				TblPersonaBasica personaCriterios = mapper.map(personaDto, TblPersonaBasica.class);

				Specification<TblPersonaBasica> spec = new PersonaBasicaEspecificacion(personaCriterios);

				List<TblPersonaBasica> resultadoCriterios = personaBasicaRepo.findAll(spec);

				return mapper.map(resultadoCriterios, personasDto);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return new ArrayList<>();
	}

	private Sort sortByFechaActualizacionDesc() {
		return new Sort(Sort.Direction.DESC, "fechaActualizacion");
	}

	@Override
	public List<PersonaDatosDTO> busquedaDatosLaboralesPorCriterios(PersonaDTO personaDTO) {
		List<PersonaDatosDTO> lista = new ArrayList<>();
		try {
			if (ObjectUtils.isNotNull(personaDTO)) {

				List<RelUsuarioDatosLaborales> resultadoCriterios = usuarioDatosLaboralesRepo
						.findAll(new DatosLaboralesEspecificacion(personaDTO));

				for (RelUsuarioDatosLaborales datos : resultadoCriterios) {
					PersonaDatosDTO datosPersona = new PersonaDatosDTO();
					datosPersona.setInstitucion(datos.getInstitucion());
					datosPersona.setAreaAdscripcion(datos.getAreaAdscripcion());
					datos.getPersona().setDomiciliosPersonas(null);
					datos.getPersona().setRelPersonaTelefonos(null);
					datos.getPersona().setPersonaCorreos(null);
					datos.getPersona().setRelPersonaRoles(null);
					datosPersona.setPersona(mapper.map(datos.getPersona(), PersonaDTO.class));
					lista.add(datosPersona);
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return lista;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResultadoDTO<PersonaDTO> guardar(PersonaDTO personaDto) {
		ResultadoDTO<PersonaDTO> resultado = sonDatosRequeridosValidos(TipoAccion.PERSISTENCIA, personaDto);
		if (resultado.getResultado().getValor()) {
			try {

				TblPersona personaEnt = mapper.map(personaDto, TblPersona.class);
				TblPersona personaRespuesta = personaRepo.save(personaEnt);
				resultado = new ResultadoDTO<>();
				resultado.setDto(mapper.map(personaRespuesta, PersonaDTO.class));
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
	public ResultadoDTO<PersonaDTO> actualizar(PersonaDTO persona) {

		ResultadoDTO<PersonaDTO> resultado = sonDatosRequeridosValidos(TipoAccion.ACTUALIZACION, persona);
		if (resultado.getResultado().getValor()) {
			try {
				resultado = new ResultadoDTO<>();

				TblPersona personaEnt = mapper.map(persona, TblPersona.class);

				TblPersona personaRespuesta = personaRepo.saveAndFlush(personaEnt);
				resultado.setDto(mapper.map(personaRespuesta, PersonaDTO.class));

			} catch (Exception e) {
				resultado.setResultado(ResultadoTransaccionEnum.FALLIDO);
				logger.error(e.getMessage(), e);
			}
		}

		return resultado;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResultadoDTO<PersonaDTO> eliminar(PersonaDTO persona) {

		ResultadoDTO<PersonaDTO> resultado = sonDatosRequeridosValidos(TipoAccion.ELIMINACION, persona);
		if (resultado.getResultado().getValor()) {
			try {
				personaRepo.saveAndFlush(mapper.map(persona, TblPersona.class));

				resultado.setResultado(ResultadoTransaccionEnum.EXITOSO);

				resultado.agregaMensaje(MensajesSistemaEnum.ADMIN_MSG_ELIMINACION_EXITOSA.getId());

			} catch (Exception e) {
				resultado.setResultado(ResultadoTransaccionEnum.FALLIDO);
				logger.error(e.getMessage(), e);
			}
		}
		return resultado;
	}

	public void estableceSexoMedianteCurp(PersonaDTO persona) {
		if (ObjectUtils.isNullOrEmpty(persona.getGenero())) {
			if (!ObjectUtils.isNullOrEmpty(persona.getCurp())) {
				if (CurpUtils.validaCurpConRegExp(persona.getCurp())) {
					char genero = persona.getCurp().charAt(ConstantesGestor.POSICION_GENERO_EN_CURP);
					if (genero == 'H') {
						persona.setGenero("M");
					} else {
						persona.setGenero("F");
					}
				} else {
					persona.setGenero("M");
				}
			}
		}
	}

	private TblPersona almacenarDatosPersonales(PersonaDTO personaDto) {
		personaDto.setContrasenia(personaDto.getContraseniaEncriptada());
		personaDto.setUnidadAdministrativa(personaDto.getUnidadAdministrativa());
		estableceSexoMedianteCurp(personaDto);
		TblPersona persona = mapper.map(personaDto, TblPersona.class);
		return personaRepo.save(persona);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResultadoDTO<PersonaDTO> guardarPersona(CapturaPersonaDTO datos) {
		ResultadoDTO<PersonaDTO> resultado = sonDatosRequeridosValidos(TipoAccion.PERSISTENCIA, datos.getPersona());
		resultado.setDto(datos.getPersona());
		validarCorreo(TipoAccion.PERSISTENCIA, datos.getPersonaCorreo(), datos.getCorreoDePersonaEnBD(), resultado);
		if (!datos.getPersona().getConvocatoria().equals("0.0") && !datos.getPersona().getFuenteExterna().equals("0.0")) {
			validarAspirante(datos.getPersona(),resultado);
		}
		if (resultado.getResultado().getValor()) {
			try {
				resultado = new ResultadoDTO<>();
				TblPersona persona = almacenarDatosPersonales(datos.getPersona());
				//ES AQUI DONDE METEMOS A LA OTRA TABLA 
				if (!datos.getPersona().getConvocatoria().equals("0.0") && !datos.getPersona().getFuenteExterna().equals("0.0")) {
					almacenarAspirante(datos.getPersona(), persona);
				}
				almacenarDatosLaborales(datos.getDatosLaborales(), persona);
				almacenarTelefono(datos.getTelefonoFijo(), persona);
				almacenarTelefono(datos.getCelular(), persona);
				almacenarCorreo(datos.getPersonaCorreo(), persona);
				almacenarRoles(datos.getRoles(), persona);
				almacenarDomicilio(datos.getDomicilioPersona(), persona);
				almacenarElementos(datos.getElementos(), persona);
				almacenarDatosSociodemograficosPersona(datos.getDatosSociodemograficos(), persona);
				resultado.setDto(mapper.map(persona, PersonaDTO.class));
				resultado.agregaMensaje(MensajesSistemaEnum.ADMIN_MSG_GUARDADO_EXITOSO.getId());
			} catch (Exception e) {
				resultado.setResultado(ResultadoTransaccionEnum.FALLIDO);
				resultado.setMensajeError(MensajesSistemaEnum.ADMIN_MSG_GUARDADO_FALLIDO);
				new Exception();
				logger.error(e.getMessage(), e);
			}
		}
		return resultado;
	}

	private void almacenarElementos(List<SsoElementoDTO> elementos, TblPersona persona) {
		for (SsoElementoDTO dto : elementos) {
			SsoElemento entidad = mapper.map(dto, SsoElemento.class);
			entidad.setPersona(persona);
			ssoElementoRepo.saveAndFlush(entidad);
		}
	}

	private void reemplazarElementos(List<SsoElementoDTO> elementosDTOS, TblPersona persona) {
		List<SsoElemento> entidades = ssoElementoRepo.obtieneSsoElementoPorUsuario(persona.getUsuario());

		for (SsoElemento ssoElemento : entidades) {
			ssoElementoRepo.delete(ssoElemento.getId());
		}

		for (SsoElementoDTO dto : elementosDTOS) {
			SsoElemento entidad = mapper.map(dto, SsoElemento.class);
			entidad.setPersona(persona);
			ssoElementoRepo.saveAndFlush(entidad);
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResultadoDTO<PersonaDTO> actualizarPersona(CapturaPersonaDTO datos) {
		ResultadoDTO<PersonaDTO> resultado = sonDatosRequeridosValidos(TipoAccion.ACTUALIZACION, datos.getPersona());
		resultado.setDto(datos.getPersona());
		validarCorreo(TipoAccion.ACTUALIZACION, datos.getPersonaCorreo(), datos.getCorreoDePersonaEnBD(), resultado);

		if (resultado.getResultado().getValor()) {
			try {

				if (!ObjectUtils.isNullOrEmpty(datos.getPersona().getNuevaContrasenia())) {
					datos.getPersona().setContrasenia(datos.getPersona().getContraseniaEncriptada());
				}

				estableceSexoMedianteCurp(datos.getPersona());

				TblPersona persona = mapper.map(datos.getPersona(), TblPersona.class);

				TblPersona resultadoPersona = personaRepo.saveAndFlush(persona);
				resultado = new ResultadoDTO<>();
				resultado.setDto(mapper.map(resultadoPersona, PersonaDTO.class));

				almacenarDatosLaborales(datos.getDatosLaborales(), resultadoPersona);
				almacenarTelefono(datos.getTelefonoFijo(), resultadoPersona);
				almacenarTelefono(datos.getCelular(), resultadoPersona);
				almacenarCorreo(datos.getPersonaCorreo(), resultadoPersona);
				almacenarDomicilio(datos.getDomicilioPersona(), resultadoPersona);

				almacenarDatosAcademicos(datos.getDatosAcademicos(), resultadoPersona);

				reemplazarElementos(datos.getElementos(), resultadoPersona);

				personaRolesService.almacenarRolesUsuario(mapper.map(resultadoPersona, PersonaDTO.class),
						estableceRolAlumnoPorDefecto(datos.getRoles()), resultadoPersona.getUsuarioModifico());

				resultado.agregaMensaje(MensajesSistemaEnum.ADMIN_MSG_ACTUALIZACION_EXITOSA.getId());

			} catch (Exception e) {
				resultado.setResultado(ResultadoTransaccionEnum.FALLIDO);
				resultado.setMensajeError(MensajesSistemaEnum.ADMIN_MSG_GUARDADO_FALLIDO);
				logger.error(e.getMessage(), e);
			}
		}

		return resultado;
	}

	@Override
	public CapturaPersonaDTO obtenerDatosPersona(PersonaDTO persona, Long usuarioModifico) {
		CapturaPersonaDTO datos = new CapturaPersonaDTO();
		persona.setUsuarioModifico(usuarioModifico);
		persona.setFechaActualizacion(new Date());
		persona.setNuevaContrasenia(null);
		persona.setConfirmacionContrasenia(null);
		if (ObjectUtils.isNull(persona.getTipoUsuario())
				|| !(persona.getTipoUsuario() == TipoUsuarioEnum.EXTERNO.getValor()
						|| persona.getTipoUsuario() == TipoUsuarioEnum.INTERNO.getValor())) {
			persona.setTipoUsuario(TipoUsuarioEnum.INTERNO.getValor());
		}
		if (ObjectUtils.isNull(persona.getNacionalidad())) {
			persona.setNacionalidad(new PaisDTO());
		}

		datos.setPersona(persona);

		datos.setDatosLaborales(obtenerDatosLaborales(persona.getIdPersona(), usuarioModifico));
		datos.getDatosLaborales().setPersona(persona);

		datos.setTelefonoFijo(
				obtenerTelefono(persona.getIdPersona(), ConstantesGestor.TIPO_TELEFONO_INSTITUCIONAL, usuarioModifico));
		datos.getTelefonoFijo().setPersona(persona);

		datos.setCelular(
				obtenerTelefono(persona.getIdPersona(), ConstantesGestor.TIPO_TELEFONO_MOVIL, usuarioModifico));
		datos.getCelular().setPersona(persona);

		datos.setPersonaCorreo(
				obtenerCorreo(persona.getIdPersona(), ConstantesGestor.TIPO_CORREO_INSTITUCIONAL, usuarioModifico));
		datos.getPersonaCorreo().setPersona(persona);

		datos.setDomicilioPersona(
				obtenerDomicilio(persona.getIdPersona(), ConstantesGestor.ID_PAIS_MEXICO, usuarioModifico));
		datos.getDomicilioPersona().setPersona(persona);

		
		datos.setDatosSociodemograficos(
				obtenerDatoSociodemograficoPersona(persona.getIdPersona(), usuarioModifico));
		datos.getDatosSociodemograficos().setPersona(persona);
		
		datos.setRoles(obtenerRolesPersona(persona.getUsuario()));
		datos.setElementos(obtenerElementos(persona.getUsuario()));

		datos.setDatosAcademicos(obtenerDatosAcademicosPorIdPersona(usuarioModifico, usuarioModifico));
		datos.getDatosAcademicos().setTblPersona(persona);
		datos.getDatosAcademicos().setIdPersona(persona.getIdPersona().intValue());
		datos.getPersona().setRutaFoto(persona.getRutaFoto());
		return datos;
	}

	private List<SsoElementoDTO> obtenerElementos(String usuario) {
		List<SsoElemento> entidades = ssoElementoRepo.obtieneSsoElementoPorUsuario(usuario);
		List<SsoElementoDTO> dtos = new ArrayList<>();
		for (SsoElemento entidad : entidades) {
			dtos.add(mapper.map(entidad, SsoElementoDTO.class));
		}
		return dtos;
	}

	/**
	 * Guarda la informacion del usuario otorgandole el rol de alumno
	 *
	 * @param personaDto
	 * @param correoDTO
	 * @return
	 * @throws EmailException
	 */
	@Override
	@Transactional(rollbackFor = { Exception.class, EmailException.class })
	public ResultadoDTO<PersonaDTO> guardaPersonaRolAlumno(PersonaDTO personaDto, CorreoDTO correoDTO)
			throws EmailException {

		ResultadoDTO<PersonaDTO> resultado = sonDatosRequeridosValidos(TipoAccion.PERSISTENCIA, personaDto);
		if (resultado.getResultado().getValor()) {

			TblPersona personaEnt = mapper.map(personaDto, TblPersona.class);
			personaEnt = personaRepo.save(personaEnt);
			PersonaDTO nuevaPersona = mapper.map(personaEnt, PersonaDTO.class);

			RolDTO rolAlumno = rolService.buscarPorClave(ConstantesGestor.ROLE_ALUMNO);

			if (ObjectUtils.isNotNull(rolAlumno)) {

				PersonaRolDTO personaRol = new PersonaRolDTO();
				personaRol.setRol(rolAlumno);
				personaRol.setPersona(nuevaPersona);
				personaRol.setActivo(1);
				personaRol.setFechaRegistro(new Date());
				personaRol.setUsuarioModifico(personaDto.getUsuarioModifico());
				mapper.map(personaDto, personaRol);
				personaRolesService.guardar(personaRol);

				enviarCorreoConfirmacion(correoDTO, nuevaPersona);

				resultado = new ResultadoDTO<>();
				resultado.setDto(nuevaPersona);

			}

		}

		return resultado;
	}

	@Override
	public ResultadoDTO<PersonaDTO> activaUsuarioWeb(String usuario, String token) {

		ResultadoDTO<PersonaDTO> res = new ResultadoDTO<>();
		try {

			TblPersona persona = personaRepo.obtienePersonaPorNombreUsuario(usuario);
			// if (ObjectUtils.isNotNull(persona)) {
			// if (ObjectUtils.isNotNull(persona.getToken()) &&
			// persona.getToken().equals(token)) {
			// persona.setActivo(Boolean.TRUE);
			// //persona.setToken(null);
			// personaRepo.saveAndFlush(persona);
			// } else {
			// res.setMensajeError(MensajesSistemaEnum.ERROR_VALIDA_TOKEN);
			// }
			//
			// } else {
			res.setMensajeError(MensajesSistemaEnum.ERROR_USUARIO_NO_EXISTE);
			// }
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			logger.error("No existe el atributo token en la entidad");
		}
		return res;
	}

	@Override
	public void validarPersistencia(PersonaDTO personaDto, ResultadoDTO<PersonaDTO> resultado) {
		validacionComun(personaDto, resultado);

		if (ObjectUtils.isNullOrEmpty(personaDto.getNuevaContrasenia())) {
			resultado.setMensajeError(MensajesSistemaEnum.ADMIN_PERSONAS_CONTRASENIA_REQ);
		}

		if (ObjectUtils.isNullOrEmpty(personaDto.getConfirmacionContrasenia())) {
			resultado.setMensajeError(MensajesSistemaEnum.ADMIN_PERSONAS_RECONTRASENIA_REQ);
		}
		if (ObjectUtils.isNull(personaDto.getFechaRegistro())) {
			resultado.setMensajeError(MensajesSistemaEnum.MSG_GENERAL_FECHA_REGISTRO_REQ);
		}
		if (ObjectUtils.isNull(personaDto.getUsuarioModifico())) {
			resultado.setMensajeError(MensajesSistemaEnum.MSG_GENERAL_USUARIO_MODIFICO_REQ);
		}
	}

	@Override
	public void validarActualizacion(PersonaDTO personaDto, ResultadoDTO<PersonaDTO> resultado) {
		validacionComun(personaDto, resultado);

		if (ObjectUtils.isNull(personaDto.getIdPersona())) {
			resultado.setMensajeError(MensajesSistemaEnum.ADMIN_PERSONAS_ID_REQ);
		}

		if (!ObjectUtils.isNullOrEmpty(personaDto.getNuevaContrasenia())
				&& ObjectUtils.isNullOrEmpty(personaDto.getConfirmacionContrasenia())) {
			resultado.setMensajeError(MensajesSistemaEnum.ADMIN_PERSONAS_RECONTRASENIA_REQ);
		}

		if (ObjectUtils.isNull(personaDto.getFechaActualizacion())) {
			resultado.setMensajeError(MensajesSistemaEnum.MSG_GENERAL_FECHA_EDICION_REQ);
		}
		if (ObjectUtils.isNull(personaDto.getUsuarioModifico())) {
			resultado.setMensajeError(MensajesSistemaEnum.MSG_GENERAL_USUARIO_MODIFICO_REQ);
		}
	}

	@Override
	public void validarEliminacion(PersonaDTO personaDto, ResultadoDTO<PersonaDTO> resultado) {
		if (ObjectUtils.isNull(personaDto.getIdPersona())) {
			resultado.setMensajeError(MensajesSistemaEnum.ADMIN_PERSONAS_ID_REQ);
		}

		if (ObjectUtils.isNull(personaDto.getUsuarioModifico())) {
			resultado.setMensajeError(MensajesSistemaEnum.MSG_GENERAL_USUARIO_MODIFICO_REQ);
		}

		if (ObjectUtils.isNull(personaDto.getFechaActualizacion())) {
			resultado.setMensajeError(MensajesSistemaEnum.MSG_GENERAL_FECHA_EDICION_REQ);
		}
	}

	@Override
	public ResultadoCargaDTO procesarArchivo(String ruta, Long idUsuario) {
		ResultadoCargaDTO resultado = new ResultadoCargaDTO();

		List<PersonaCargaDTO> personas = UtilidadesCargaMasiva.cargarDocumento(ruta, obtenerMapaEntidades(),
				obtenerMapaMunicipios());
		resultado.setRegistros(personas);
		resultado.setTotalRegistros(personas.size());
		calcularResultados(resultado, personas);

		return resultado;
	}

	@Override
	public ResultadoDTO<LoteCargaUsuarioDTO> guardar(LoteCargaUsuarioDTO dto, ResultadoCargaDTO resultadoCarga,
			String ruta) {
		ResultadoDTO<LoteCargaUsuarioDTO> resultado = sonDatosRequeridosValidos(dto);
		if (ObjectUtils.isNotNull(resultado) && resultado.getResultado().getValor()) {
			TblLoteCargaUsuario entidad = mapper.map(dto, TblLoteCargaUsuario.class);
			almacenarUsuarios(resultadoCarga, dto.getUsuarioModifico());
			UtilidadesCargaMasiva.crearDocumento(resultadoCarga, ruta);
			entidad.setRutaArchivo(resultadoCarga.getRutaArchivo());
			entidad = loteCargaUsuarioRepo.save(entidad);
			for (PersonaCargaDTO persona : resultadoCarga.getRegistros()) {
				if (persona.isCorrecto()) {
					RelLoteUsuario relLoteUsuario = new RelLoteUsuario();
					relLoteUsuario.setFechaRegistro(new Date());
					relLoteUsuario.setUsuarioModifico(dto.getUsuarioModifico());
					relLoteUsuario.setIdLote(entidad.getIdLoteCargaUsuarios());
					relLoteUsuario.setIdPersona(persona.getIdPersona());
					loteUsuarioRepo.save(relLoteUsuario);
					resultado.setDto(mapper.map(entidad, LoteCargaUsuarioDTO.class));
				}
			}

			resultado.agregaMensaje(MensajesSistemaEnum.LOTES_CARGA_USUARIOS_ID_REQ.getId());
		}
		return resultado;
	}

	/**
	 * Metodo para envio de correo, para recuperacion de contrasenia
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResultadoDTO<PersonaDTO> enviaCorreoRecuperarContrasenia(PersonaCorreoDTO personaCorreo, CorreoDTO correo) {

		ResultadoDTO<PersonaDTO> res = new ResultadoDTO<>();

		if (ObjectUtils.isNull(personaCorreo) || ObjectUtils.isNull(personaCorreo.getPersona())
				|| ObjectUtils.isNull(correo)) {
			res.setMensajeError(MensajesSistemaEnum.CORREO_PERSONA_CORREO_REQ);
		} else {

			try {
				correo.agregarDestinatario(personaCorreo.getCorreoElectronico());
				this.guardar(personaCorreo.getPersona());
				CorreoUtil.sendMail(correo);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
		return res;

	}

	private void almacenarDatosLaborales(UsuarioDatosLaboralesDTO datosLaborales, TblPersona persona) {
		establecerNombreSedeLaboral(datosLaborales);
		RelUsuarioDatosLaborales datosLaboralesEntidad = mapper.map(datosLaborales, RelUsuarioDatosLaborales.class);
		datosLaboralesEntidad.setOrdenGobierno(estableceNombreOrdenGobierno(datosLaborales.getIdOrdenGobierno()));
		datosLaboralesEntidad.setPersona(persona);
		usuarioDatosLaboralesRepo.save(datosLaboralesEntidad);
	}
	
	
	private void almacenarAspirante(PersonaDTO dto, TblPersona persona) {
		usuariosImportarRepo.insertAspirante(persona.getIdPersona().toString(), dto.getFuenteExterna(), dto.getConvocatoria());

	}
	
	private void validarAspirante(PersonaDTO dto, ResultadoDTO<PersonaDTO> resultado) {
		if (ObjectUtils.isNullOrEmpty(dto.getConvocatoria()) || ObjectUtils.isNullOrEmpty(dto.getFuenteExterna()) ) {
			resultado.setMensajeError(MensajesSistemaEnum.ADMIN_PERSONAS_PLAN_CONVOCATORIA_REQ);
			logger.info("Error plan o convocatoria requerido");
		} else {
			if (!usuariosImportarRepo.verificarRelacionConvocatoria(dto.getFuenteExterna(), dto.getConvocatoria())) {
				resultado.setMensajeError(MensajesSistemaEnum.ADMIN_PERSONAS_PLAN_CONVOCATORIA_NO_EXISTE);
				logger.info("Error no existe plan o convocatoria");
			}
		}

	}

	private String estableceNombreOrdenGobierno(String idOrdenGobierno) {
		if (!ObjectUtils.isNullOrEmpty(idOrdenGobierno)) {
			return OrdenGobiernoEnum.getOrdenGobiernoEnum(idOrdenGobierno).getDescripcion();
		} else {
			return null;
		}
	}

	private void almacenarDatosAcademicos(PersonaDatosAcademicoDTO datosAcademicos, TblPersona persona) {

		RelPersonaDatosAcademico datosAcademicosEntidad = mapper.map(datosAcademicos, RelPersonaDatosAcademico.class);
		datosAcademicosEntidad.setIdPersona(persona.getIdPersona());
		personaDatosAcademicosRepo.save(datosAcademicosEntidad);
	}

	private void establecerNombreSedeLaboral(UsuarioDatosLaboralesDTO datosLaborales) {
		Integer id = datosLaborales.getSede().getIdEntidadFederativa();
		datosLaborales.getSede().setNombre(obtenerNombreEntidad(id));
	}

	public String obtenerNombreEntidad(Integer id) {
		if (ObjectUtils.isNotNull(id)) {

			List<EntidadFederativaDTO> listaSedes = entidadFederativaService
					.obtenerEntidadesPorPais(ConstantesGestor.ID_PAIS_MEXICO);
			for (EntidadFederativaDTO entidad : listaSedes) {
				if (id.equals(entidad.getIdEntidadFederativa())) {
					return entidad.getNombre();
				}
			}
		}
		return null;
	}

	private UsuarioDatosLaboralesDTO obtenerDatosLaborales(Long idPersona, Long usuarioModifico) {
		List<RelUsuarioDatosLaborales> datos = usuarioDatosLaboralesRepo.obtenerDatosLaboralesPorPersona(idPersona);
		UsuarioDatosLaboralesDTO datosLaborales;
		if (datos.isEmpty()) {
			datosLaborales = new UsuarioDatosLaboralesDTO(usuarioModifico);
		} else {
			datosLaborales = mapper.map(datos.get(0), UsuarioDatosLaboralesDTO.class);
			datosLaborales.setUsuarioModifico(usuarioModifico);
			datosLaborales.setFechaActualizacion(new Date());
			if (ObjectUtils.isNull(datosLaborales.getMunicipio())) {
				datosLaborales.setMunicipio(new MunicipioDTO());
			}
			if (ObjectUtils.isNull(datosLaborales.getSede())) {
				datosLaborales.setSede(new EntidadFederativaDTO());
			}
		}
		return datosLaborales;
	}

	private void almacenarTelefono(PersonaTelefonoDTO personaTelefono, TblPersona persona) {
		if (!ObjectUtils.isNullOrEmpty(personaTelefono.getTelefono())) {
			RelPersonaTelefono personaTelefonoEntidad = mapper.map(personaTelefono, RelPersonaTelefono.class);
			personaTelefonoEntidad.setPersona(persona);
			personaTelefonoRepo.save(personaTelefonoEntidad);
		}
	}

	private PersonaTelefonoDTO obtenerTelefono(Long idPersona, Integer idTipoTelefono, Long usuarioModifico) {
		List<RelPersonaTelefono> telefonos = personaTelefonoRepo.obtenerTelefonosPersonaPorTipo(idPersona,
				idTipoTelefono);
		PersonaTelefonoDTO telefono;
		if (telefonos.isEmpty()) {
			telefono = new PersonaTelefonoDTO(usuarioModifico, idTipoTelefono);
		} else {
			telefonos.get(0).setPersona(null);

			telefono = mapper.map(telefonos.get(0), PersonaTelefonoDTO.class);
			telefono.setUsuarioModifico(usuarioModifico);
			telefono.setFechaActualizacion(new Date());
		}
		return telefono;
	}

	private void almacenarCorreo(PersonaCorreoDTO personaCorreo, TblPersona persona) {
		RelPersonaCorreo personaCorreoEntidad = mapper.map(personaCorreo, RelPersonaCorreo.class);
		personaCorreoEntidad.setPersona(persona);
		personaCorreoRepo.save(personaCorreoEntidad);
	}

	private PersonaCorreoDTO obtenerCorreo(Long idPersona, Integer idTipoCorreo, Long usuarioModifico) {
		List<RelPersonaCorreo> correos = personaCorreoRepo.obtenerCorreoPersonaPorTipo(idPersona, idTipoCorreo);
		PersonaCorreoDTO correo;
		if (correos.isEmpty()) {
			correo = new PersonaCorreoDTO(usuarioModifico, idTipoCorreo);
		} else {
			correos.get(0).setPersona(null);

			correo = mapper.map(correos.get(0), PersonaCorreoDTO.class);
			correo.setUsuarioModifico(usuarioModifico);
			correo.setFechaActualizacion(new Date());
		}
		return correo;
	}

	private void almacenarDomicilio(DomicilioPersonaDTO domicilio, TblPersona persona) {
		TblDomiciliosPersona domicilioPersonaEntidad = mapper.map(domicilio, TblDomiciliosPersona.class);
		domicilioPersonaEntidad.setPersona(persona);
		if (ObjectUtils.isNotNull(domicilioPersonaEntidad.getAsentamiento())
				&& ObjectUtils.isNotNull(domicilioPersonaEntidad.getAsentamiento().getIdAsentamiento())) {
			domicilioPersonaRepo.save(domicilioPersonaEntidad);
		}

	}

	private DomicilioPersonaDTO obtenerDomicilio(Long idPersona, String idPais, Long usuarioModifico) {
		List<TblDomiciliosPersona> domicilios = domicilioPersonaRepo.obtenerDomiciliosPersona(idPersona);
		DomicilioPersonaDTO domicilio;
		if (domicilios.isEmpty()) {
			domicilio = new DomicilioPersonaDTO(usuarioModifico, idPais);
		} else {
			domicilio = mapper.map(domicilios.get(0), DomicilioPersonaDTO.class);
			domicilio.setUsuarioModifico(usuarioModifico);
			domicilio.setFechaActualizacion(new Date());
			domicilio
					.setIdPais(domicilio.getAsentamiento().getMunicipio().getEntidadFederativa().getPais().getIdPais());
			domicilio.setIdEntidadFederativa(
					domicilio.getAsentamiento().getMunicipio().getEntidadFederativa().getIdEntidadFederativa());
			domicilio.setIdMunicipio(domicilio.getAsentamiento().getMunicipio().getIdMunicipio());
			if (ObjectUtils.isNull(domicilio.getAsentamiento())) {
				domicilio.setAsentamiento(new AsentamientoDTO());
			}
		}
		return domicilio;
	}

	private void almacenarDatosSociodemograficosPersona(DatoSociodemograficoDTO sociodemografico, TblPersona persona) {
		TblDatosSociodemograficosPersona dato = mapper.map(sociodemografico, TblDatosSociodemograficosPersona.class);
		dato.setPersona(persona);					
		if (ObjectUtils.isNotNull(dato.getLenguajeIndigena())
				|| ObjectUtils.isNotNull(dato.getTipoDiscapacidad())){			
				System.out.println("Guardando datos sociodemograficos...");	
				if(dato.getLenguajeIndigena().getIdLenguaje() == 0) {
					dato.getLenguajeIndigena().setIdLenguaje(27);
				}
				datoSociodemograficoPersonaRepo.save(dato);
		}

	}

	private DatoSociodemograficoDTO obtenerDatoSociodemograficoPersona(Long idPersona, Long usuarioModifico) {
		TblDatosSociodemograficosPersona datosSociodemograficos = datoSociodemograficoPersonaRepo.obtenerDatosSociodemograficosPersona(idPersona);
		// ITTIVA se quita system ya que cuando no encuntra nada en bd, llega null y truena
		//System.out.println("datosSociodemograficos IDDISCAPACIDAD: "+datosSociodemograficos.getTipoDiscapacidad().getCatDiscapacidad().getIdDiscapacidad());
		DatoSociodemograficoDTO datoSociodemograficoDTO;
		if (ObjectUtils.isNull(datosSociodemograficos)) {			
			datoSociodemograficoDTO = new DatoSociodemograficoDTO(usuarioModifico);
		} else {
			datoSociodemograficoDTO = mapper.map(datosSociodemograficos, DatoSociodemograficoDTO.class);
			datoSociodemograficoDTO.setUsuarioModifico(usuarioModifico);
			datoSociodemograficoDTO.setFechaActualizacion(new Date());
			
			datoSociodemograficoDTO.setTieneDiscapacidad(datoSociodemograficoDTO.isTieneDiscapacidad());			
			datoSociodemograficoDTO.setLenguaIndigena(datoSociodemograficoDTO.isLenguaIndigena());
			datoSociodemograficoDTO.setIdLenguaje(datoSociodemograficoDTO.getLenguajeIndigena().getIdLenguajeIndigena());
				
			System.out.println("obteniendo sociodmograficos");
			datoSociodemograficoDTO.getTipoDiscapacidad().setIdDiscapacidad(datosSociodemograficos.getTipoDiscapacidad().getCatDiscapacidad().getIdDiscapacidad());
			//datoSociodemograficoDTO.setIdDiscapacidad(datoSociodemograficoDTO.getTipoDiscapacidad().getIdDiscapacidad());
			datoSociodemograficoDTO.getTipoDiscapacidad().setIdTipoDiscapacidad(datoSociodemograficoDTO.getTipoDiscapacidad().getIdTipoDiscapacidad());
			//datoSociodemograficoDTO.setIdTipoDiscapacidad(datoSociodemograficoDTO.getTipoDiscapacidad().getIdTipoDiscapacidad());
			
			if (ObjectUtils.isNull(datoSociodemograficoDTO.getLenguajeIndigena())) {
				datoSociodemograficoDTO.setLenguajeIndigena(new LenguajeIndigenaDTO());
			}
		}
		return datoSociodemograficoDTO;
	}
	
	private void almacenarRoles(List<RolDTO> roles, TblPersona persona) {
		List<RolDTO> rolesParaGuardar = estableceRolAlumnoPorDefecto(roles);
		for (RolDTO rol : rolesParaGuardar) {
			RelPersonaRol relPersonaRol = new RelPersonaRol(mapper.map(rol, CatRol.class), persona,
					persona.getUsuarioModifico());
			personaRolesRepo.save(relPersonaRol);
		}
	}

	/**
	 * Se realiza la busqueda del rol alumno en los roles enviados. Si no se
	 * encuentra el rol del alumno, se establece.
	 */

	public List<RolDTO> estableceRolAlumnoPorDefecto(List<RolDTO> roles) {
		if (roles.isEmpty()) {
			List<RolDTO> rolAlumno = new ArrayList<>();
			rolAlumno.add(rolService.buscarPorId(2));
			return rolAlumno;
		} else {
			RolDTO rolAlumno = rolService.buscarPorId(2);
			boolean rolAlumnoEncontrado = false;
			for (RolDTO rolDTO : roles) {
				if (rolAlumno.getIdRol().equals(rolDTO.getIdRol())) {
					rolAlumnoEncontrado = true;
				}
			}
			if (rolAlumnoEncontrado == false) {
				roles.add(rolService.buscarPorId(2));
				return roles;
			} else {
				return roles;
			}
		}

	}

	private List<RolDTO> obtenerRolesPersona(String usuario) {
		List<RelPersonaRol> rolesPersona = personaRolesRepo.obtieneRelPersonaRoles(usuario);
		List<RolDTO> roles = new ArrayList<>();
		for (RelPersonaRol rolPersona : rolesPersona) {
			roles.add(mapper.map(rolPersona.getRol(), RolDTO.class));
		}
		return roles;
	}

	private void enviarCorreoConfirmacion(CorreoDTO correoDTO, PersonaDTO nuevaPersona) throws EmailException {
		// Enviando correo de confirmacion
		correoDTO.setNombre(nuevaPersona.getNombre());
		correoDTO.setExtraInfo(nuevaPersona.getApellidoPaterno());
		if (!ObjectUtils.isNullOrEmpty(nuevaPersona.getPersonaCorreos())) {
			for (PersonaCorreoDTO personaCorreo : nuevaPersona.getPersonaCorreos()) {
				correoDTO.agregarDestinatario(personaCorreo.getCorreoElectronico());
			}
		}

		CorreoUtil.sendMail(correoDTO);
	}

	private boolean validarExisteUsuarioPorNombreUsuario(PersonaDTO persona) {
		Long idPersona;
		if (ObjectUtils.isNull(persona.getIdPersona())) {
			idPersona = 0L;
		} else {
			idPersona = persona.getIdPersona();
		}
		List<TblPersona> personas = personaRepo.obtienePersonaPorNombreUsuario(persona.getUsuario(), idPersona);
		return !personas.isEmpty();
	}

	private void validarCorreo(TipoAccion accion, PersonaCorreoDTO personaCorreoDto,
			PersonaCorreoDTO correoDePersonaEnBD, ResultadoDTO<PersonaDTO> resultado) {
		if (ObjectUtils.isNullOrEmpty(personaCorreoDto.getCorreoElectronico())) {
			resultado.setMensajeError(MensajesSistemaEnum.ADMIN_PERSONAS_CORREO_REQUERIDO);
			logger.info("Error correo requerido");
		} else {
			if (!validaCorreoRegExp(personaCorreoDto.getCorreoElectronico())) {
				resultado.setMensajeError(MensajesSistemaEnum.ADMIN_PERSONAS_CORREO_FORMATO);
				logger.info("Error formato correo");
			}
			if (accion.equals(TipoAccion.PERSISTENCIA)) {

				if (correoExisteAlGuardar(personaCorreoDto.getCorreoElectronico())) {
					resultado.setMensajeError(MensajesSistemaEnum.ADMIN_PERSONAS_CORREO_EXISTE);
					logger.info("Error correo ya existe");
				}
			}
			if (accion.equals(TipoAccion.ACTUALIZACION)) {
				if (correoExisteAlActualizar(personaCorreoDto.getCorreoElectronico(),
						correoDePersonaEnBD.getCorreoElectronico(), resultado)) {
					resultado.setMensajeError(MensajesSistemaEnum.ADMIN_PERSONAS_CORREO_EXISTE);
					logger.info("Error correo ya existe");
				}
			}
		}

	}

	private boolean validaCorreoRegExp(String correo) {
		String EMAIL_PATTERN = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@[a-z0-9-]+(.[a-z0-9-]+)*(.[a-z]{2,4})$";
		Pattern pattern = Pattern.compile(EMAIL_PATTERN);
		Matcher matcher = pattern.matcher(correo);
		return matcher.matches();
	}

	private boolean correoExisteAlGuardar(String correo) {
		List<String> listaDeCorreos = obtenerCorreosDePersonas(obtenerTodasLasPersonas());
		for (String personaCorreo : listaDeCorreos) {
			if (personaCorreo.equals(correo)) {
				return true;
			}
		}
		return false;
	}

	private boolean correoExisteAlActualizar(String correoNuevo, String correoDePersonaEnBD,
			ResultadoDTO<PersonaDTO> resultado) {
		if (correoNuevo.equals(correoDePersonaEnBD)) {
			return false;
		} else {
			List<String> listaDeCorreos = obtenerCorreosDePersonas(obtenerTodasLasPersonas());
			for (String correoDeLaLista : listaDeCorreos) {
				if (correoNuevo.equals(correoDeLaLista)) {
					return true;
				}
			}
		}
		return false;

	}

	private List<String> obtenerCorreosDePersonas(List<PersonaDTO> listaPersonas) {
		List<String> listaCorreos = new ArrayList<>();
		for (PersonaDTO personaDTO : listaPersonas) {
			if (ObjectUtils.isNotNull(personaDTO.getPersonaCorreos())) {
				if (!personaDTO.getPersonaCorreos().isEmpty()) {
					listaCorreos.add(personaDTO.getPersonaCorreos().get(0).getCorreoElectronico());
				}
			}

		}
		return listaCorreos;
	}

	private void validacionComun(PersonaDTO personaDto, ResultadoDTO<PersonaDTO> resultado) {

		if (ObjectUtils.isNullOrEmpty(personaDto.getUsuario())) {
			resultado.setMensajeError(MensajesSistemaEnum.ADMIN_PERSONAS_USUARIO_REQ);
			logger.info("Error usuario requerido");
		} else {
			if (validarExisteUsuarioPorNombreUsuario(personaDto)) {
				resultado.setMensajeError(MensajesSistemaEnum.ADMIN_PERSONAS_USUARIO_EXISTE);
				logger.info("Error usuario existe");
			}
		}
		validarCurp(personaDto, resultado);

		if (!ObjectUtils.isNullOrEmpty(personaDto.getNuevaContrasenia())
				&& !ObjectUtils.isNullOrEmpty(personaDto.getConfirmacionContrasenia())
				&& !personaDto.getNuevaContrasenia().equals(personaDto.getConfirmacionContrasenia())) {
			resultado.setMensajeError(MensajesSistemaEnum.ADMIN_PERSONAS_CONTRASENIAS_DIFERENTES);
			logger.info("Error contrasenias diferentes");
		}

		if (ObjectUtils.isNullOrEmpty(personaDto.getNombre())) {
			resultado.setMensajeError(MensajesSistemaEnum.ADMIN_PERSONAS_NOMBRE_REQ);
			logger.info("Error nombre requerido");
		}

		if (ObjectUtils.isNullOrEmpty(personaDto.getApellidoPaterno())) {
			resultado.setMensajeError(MensajesSistemaEnum.ADMIN_PERSONAS_APELLIDO_PATERNO_REQ);
			logger.info("Error apellido paterno requerido");
		}

		if (ObjectUtils.isNull(personaDto.getFechaNacimiento())) {
			resultado.setMensajeError(MensajesSistemaEnum.ADMIN_PERSONAS_FECHA_NACIMIENTO_REQ);
			logger.info("Error fecha nacimiento requerido");
		}
	}

	private void validarCurp(PersonaDTO personaDto, ResultadoDTO<PersonaDTO> resultado) {
		if (ObjectUtils.isNullOrEmpty(personaDto.getCurp())) {
			resultado.setMensajeError(MensajesSistemaEnum.ADMIN_PERSONAS_CURP_REQ);
			logger.info("Error curp requerida");
		} else {
			if (!CurpUtils.validaCurpConRegExp(personaDto.getCurp())) {
				resultado.setMensajeError(MensajesSistemaEnum.ADMIN_PERSONAS_CURP_FORMATO);
				logger.info("Error formato curp");
			} else {
				if (validarExisteUsuarioPorCurp(personaDto)) {
					resultado.setMensajeError(MensajesSistemaEnum.ADMIN_PERSONAS_CURP_EXISTE);
					logger.info("Error curp ya existe");
				}
			}
		}
	}

	private boolean validarExisteUsuarioPorCurp(PersonaDTO persona) {
		Long idPersona;
		if (ObjectUtils.isNull(persona.getIdPersona())) {
			idPersona = 0L;
		} else {
			idPersona = persona.getIdPersona();
		}
		List<TblPersona> personas = personaRepo.obtienePersonaPorCurp(persona.getCurp(), idPersona);
		return !personas.isEmpty();
	}

	private void calcularResultados(ResultadoCargaDTO resultado, List<PersonaCargaDTO> personas) {
		logger.info("NUM_PER: " + personas.size());
		if (!personas.isEmpty()) {
			boolean correcto = true;
			for (PersonaCargaDTO dto : personas) {

				logger.info(dto.getNombre() + " " + dto.isCorrecto());

				if (dto.isCorrecto()) {
					resultado.setRegistrosValidos(1 + resultado.getRegistrosValidos());
					dto.setMensajeResultado(ConstantesGestor.MENSAJE_CORRECTO);
				} else {
					resultado.setRegistrosInvalidos(1 + resultado.getRegistrosInvalidos());
					correcto = false;
				}
			}
			resultado.setCorrecto(correcto);

		}
	}

	private ResultadoDTO<LoteCargaUsuarioDTO> sonDatosRequeridosValidos(LoteCargaUsuarioDTO dto) {
		ResultadoDTO<LoteCargaUsuarioDTO> resultado = new ResultadoDTO<>();

		if (ObjectUtils.isNotNull(dto)) {

			if (ObjectUtils.isNullOrEmpty(dto.getNombre())) {
				resultado.setMensajeError(MensajesSistemaEnum.LOTES_CARGA_USUARIOS_NOMBRE_REQ);
			}
			if (ObjectUtils.isNullOrEmpty(dto.getRutaArchivo())) {
				resultado.setMensajeError(MensajesSistemaEnum.LOTES_CARGA_USUARIOS_RUTA_ARCHIVO_REQ);
			}
			if (ObjectUtils.isNull(dto.getFechaRegistro())) {
				resultado.setMensajeError(MensajesSistemaEnum.LOTES_CARGA_USUARIOS_FECHA_REGISTRO_REQ);
			}
			if (ObjectUtils.isNullOrEmpty(dto.getUsuarioModifico())) {
				resultado.setMensajeError(MensajesSistemaEnum.LOTES_CARGA_USUARIOS_USUARIO_REQ);
			}
		} else {
			resultado.setResultado(ResultadoTransaccionEnum.FALLIDO);
		}
		return resultado;
	}

	private void almacenarUsuarios(ResultadoCargaDTO resultadoCarga, long usuarioModifico) {
		for (PersonaCargaDTO persona : resultadoCarga.getRegistros()) {
			almacenarUsuario(persona, usuarioModifico);
		}
	}

	private void almacenarUsuario(PersonaCargaDTO persona, long usuarioModifico) {
		CapturaPersonaDTO datos = convertirCargaCapturaPersona(persona, usuarioModifico);
		ResultadoDTO<PersonaDTO> resultado = guardarPersona(datos);
		if (resultado.esCorrecto()) {
			persona.setIdPersona(resultado.getDto().getIdPersona());
		} else {
			persona.setCorrecto(false);
			if (!ObjectUtils.isNullOrEmpty(resultado.getMensajes())) {
				logger.info(resultado.getMensajes().get(ConstantesGestor.PRIMER_ELEMENTO));
				TblTextoSistema texto = textoSistemaRepo
						.findOne(resultado.getMensajes().get(ConstantesGestor.PRIMER_ELEMENTO));
				String correo = resultado.getMensajes().get(ConstantesGestor.PRIMER_ELEMENTO);
				if (ObjectUtils.isNotNull(texto)) {
					logger.info(texto.getValor());
					persona.setMensajeResultado(texto.getValor());
				} else if("gw.admin.personas.correo.existe".equals(correo)){
					logger.info("Error correo ya existe");
					persona.setMensajeResultado("Error correo ya existe");
				} else {
					persona.setMensajeResultado(ConstantesGestor.MENSAJE_INCORRECTO);
				}
			} else {
				persona.setMensajeResultado(ConstantesGestor.MENSAJE_INCORRECTO);
			}
		}
	}

	private CapturaPersonaDTO convertirCargaCapturaPersona(PersonaCargaDTO personaCargaDTO, long usuarioModifico) {
		CapturaPersonaDTO datos = new CapturaPersonaDTO();
		datos.setPersona(new PersonaDTO(usuarioModifico, ConstantesGestor.ID_PAIS_MEXICO));
		datos.setDatosLaborales(new UsuarioDatosLaboralesDTO(usuarioModifico));
		datos.setTelefonoFijo(new PersonaTelefonoDTO(usuarioModifico, ConstantesGestor.TIPO_TELEFONO_INSTITUCIONAL));
		datos.setCelular(new PersonaTelefonoDTO(usuarioModifico, ConstantesGestor.TIPO_TELEFONO_MOVIL));
		datos.setPersonaCorreo(new PersonaCorreoDTO(usuarioModifico, ConstantesGestor.TIPO_CORREO_INSTITUCIONAL));
		datos.setDomicilioPersona(new DomicilioPersonaDTO(usuarioModifico, ConstantesGestor.ID_PAIS_MEXICO));

		datos.getPersona().setTipoUsuario(TipoUsuarioEnum.valueOf(personaCargaDTO.getTipoUsuario()).getValor());
		datos.getPersona().setUsuario(personaCargaDTO.getUsuario());
		datos.getPersona().setUnidadAdministrativa(personaCargaDTO.getHash());
		datos.getPersona().setNuevaContrasenia(personaCargaDTO.getContrasenia());
		datos.getPersona().setConfirmacionContrasenia(personaCargaDTO.getContrasenia());
		datos.getPersona().setContraseniaEncriptada(personaCargaDTO.getContraseniaEncriptada());
		datos.getPersona().setCurp(personaCargaDTO.getCurp());
		datos.getPersona().setNombre(personaCargaDTO.getNombre());
		datos.getPersona().setApellidoPaterno(personaCargaDTO.getApellidoPaterno());
		datos.getPersona().setApellidoMaterno(personaCargaDTO.getApellidoMaterno());
		datos.getPersona().setFechaNacimiento(personaCargaDTO.getFechaNacimientoDate());
		datos.getPersona().setFuenteExterna(personaCargaDTO.getPlan());
		datos.getPersona().setConvocatoria(personaCargaDTO.getConvocatoria());

		datos.getDatosLaborales()
				.setProgramaSocial(personaCargaDTO.getPrograma().equals(ContieneProgramaEnum.SI.getValor()));
		datos.getDatosLaborales().setInstitucion(personaCargaDTO.getInstitucion());
		datos.getDatosLaborales().getSede().setIdEntidadFederativa(personaCargaDTO.getIdEntidadFederativa());
		datos.getDatosLaborales().getMunicipio().setIdMunicipio(personaCargaDTO.getIdMunicipio());
		datos.getDatosLaborales().setIdOrdenGobierno(obtenerIdOrden(personaCargaDTO.getOrden()));
		datos.getDatosLaborales().setPuesto(personaCargaDTO.getPuesto());
		datos.getPersonaCorreo().setCorreoElectronico(personaCargaDTO.getCorreo());
		datos.getTelefonoFijo().setTelefono(personaCargaDTO.getTelefono());
		datos.getCelular().setTelefono(personaCargaDTO.getCelular());

		datos.setRoles(new ArrayList<RolDTO>());
		RolDTO rolAlumno = new RolDTO();
		rolAlumno.setIdRol(ConstantesGestor.ROL_ALUMNO);
		datos.getRoles().add(rolAlumno);

		return datos;
	}

	private String obtenerIdOrden(String nombreOrden) {
		for (OrdenGobiernoEnum orden : OrdenGobiernoEnum.values()) {
			if (orden.getDescripcion().equals(nombreOrden)) {
				return orden.getValor();
			}
		}
		return "";
	}

	private Map<String, Integer> obtenerMapaEntidades() {
		Map<String, Integer> mapa = new HashMap<>();
		for (CatEntidadFederativa entidad : entidadFederativaRepo.findAll()) {
			mapa.put(entidad.getNombre().toUpperCase(), entidad.getIdEntidadFederativa());
		}
		return mapa;
	}

	private Map<String, String> obtenerMapaMunicipios() {
		Map<String, String> mapa = new HashMap<>();
		for (CatMunicipio entidad : municipioRepo.findAll()) {
			mapa.put(entidad.getNombre().toUpperCase(), entidad.getIdMunicipio());
		}
		return mapa;
	}

	private PersonaDatosAcademicoDTO obtenerDatosAcademicosPorIdPersona(Long idPersona, Long usuarioModifico) {
		List<RelPersonaDatosAcademico> datosAcademicos = getPersonaDatosAcademicosRepo()
				.obtenerDatosAcademicosPorIdPersona(idPersona);
		PersonaDatosAcademicoDTO dto = new PersonaDatosAcademicoDTO();

		if (datosAcademicos.isEmpty()) {
			dto = new PersonaDatosAcademicoDTO(usuarioModifico);
		} else {
			dto = mapper.map(datosAcademicos.get(0), PersonaDatosAcademicoDTO.class);
			dto.setUsuarioModifico(usuarioModifico);
			dto.setFechaActualizacion(new Date());
		}
		return dto;
	}
	
	@Override
	public Boolean existeCurp(String curpParam) {
		Boolean existe = false;
		try {
			TblPersona consultaCurp = personaRepo.obtieneCurp(curpParam);
			if(consultaCurp != null) {
				existe = true;
			}
		} catch (Exception e) {
			logger.error(e);
		}

		return existe;
	}

	public PersonaDatosAcademicosService getPersonaDatosAcademicos() {
		return personaDatosAcademicos;
	}

	public void setPersonaDatosAcademicos(PersonaDatosAcademicosService personaDatosAcademicos) {
		this.personaDatosAcademicos = personaDatosAcademicos;
	}

	public PersonaDatosAcademicosRepo getPersonaDatosAcademicosRepo() {
		return personaDatosAcademicosRepo;
	}

	public void setPersonaDatosAcademicosRepo(PersonaDatosAcademicosRepo personaDatosAcademicosRepo) {
		this.personaDatosAcademicosRepo = personaDatosAcademicosRepo;
	}

	public PersonaCorreoService getPersonaCorreoService() {
		return personaCorreoService;
	}

	public void setPersonaCorreoService(PersonaCorreoService personaCorreoService) {
		this.personaCorreoService = personaCorreoService;
	}

	public EntidadFederativaService getEntidadFederativaService() {
		return entidadFederativaService;
	}

	public void setEntidadFederativaService(EntidadFederativaService entidadFederativaService) {
		this.entidadFederativaService = entidadFederativaService;
	}

	@Override
	public List<PersonaDTO> obtenerPersonaPorCodigoPostal(String codigoPostal) {

		List<TblPersona> personasEntities = personaRepo.obtenerPersonaPorCodigoPostal(codigoPostal);

		if (!personasEntities.isEmpty()) {
			List<PersonaDTO> personasDTO = mapper.map(personasEntities, personasDto);
			return personasDTO;
		}

		return new ArrayList<>();
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResultadoDTO<PersonaDTO> desactivarPersona(PersonaDTO persona) {

		ResultadoDTO<PersonaDTO> resultado = sonDatosRequeridosValidos(TipoAccion.ACTUALIZACION, persona);
		if (resultado.getResultado().getValor()) {
			try {
				resultado = new ResultadoDTO<>();

				persona.setActivo(false);

				TblPersona personaEnt = mapper.map(persona, TblPersona.class);

				TblPersona personaRespuesta = personaRepo.saveAndFlush(personaEnt);
				resultado.setDto(mapper.map(personaRespuesta, PersonaDTO.class));

			} catch (Exception e) {
				resultado.setResultado(ResultadoTransaccionEnum.FALLIDO);
				logger.error(e.getMessage(), e);
			}
		}

		return resultado;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Boolean guardarPersonas(List<CapturaPersonaDTO> datos, String convocatoria, boolean vincularUsuario) {	
		boolean exito = false;
		List<TblPersona> personaDTOToTbl = datos.stream()
				.map(capturaPersonaDTO -> mapper.map(capturaPersonaDTO.getPersona(), TblPersona.class))
				.collect(Collectors.toList());
		List<TblPersona> personas = personaRepo.save(personaDTOToTbl);
		IntStream.range(0, datos.size()).forEach(i -> {
		    CapturaPersonaDTO capturaPersonaDTO = datos.get(i);
		    TblPersona persona = personas.get(i);
		    capturaPersonaDTO.getPersona().setIdPersona(persona.getIdPersona());
		    if(vincularUsuario){
		    	usuariosImportarRepo.insertAspirante(persona.getIdPersona().toString(), capturaPersonaDTO.getPersona().getFuenteExterna(), convocatoria);
		    }
		});
		List<RelUsuarioDatosLaborales> usuarioDatosLaboralesDtoToTbl = datos.stream()
		        .map(capturaPersonaDTO -> {
		            RelUsuarioDatosLaborales relUsuarioDatosLaborales = mapper.map(capturaPersonaDTO.getDatosLaborales(), RelUsuarioDatosLaborales.class);
		            PersonaDTO persona = capturaPersonaDTO.getPersona();
		            relUsuarioDatosLaborales.getPersona().setIdPersona(persona.getIdPersona());
		            return relUsuarioDatosLaborales;
		        })
		        .collect(Collectors.toList());
		List<RelPersonaCorreo> correoDtoToTbl = datos.stream()
				.map(capturaPersonaDTO -> {
					RelPersonaCorreo personaCorreo = mapper.map(capturaPersonaDTO.getPersonaCorreo(), RelPersonaCorreo.class);
					PersonaDTO persona = capturaPersonaDTO.getPersona();					
					personaCorreo.getPersona().setIdPersona(persona.getIdPersona());
					return personaCorreo;
				})
				.collect(Collectors.toList());
		List<TblDomiciliosPersona> domicilioDtoToTbl = datos.stream()
				.map(capturaPersonaDTO -> {
					TblDomiciliosPersona domicilio = mapper.map(capturaPersonaDTO.getDomicilioPersona(), TblDomiciliosPersona.class);
					PersonaDTO persona = capturaPersonaDTO.getPersona();
					domicilio.getPersona().setIdPersona(persona.getIdPersona());
					return domicilio;
				})
				.collect(Collectors.toList());
		usuarioDatosLaboralesRepo.save(usuarioDatosLaboralesDtoToTbl);
		personaCorreoRepo.save(correoDtoToTbl);
		
		List<RolDTO> listaPersonas = datos.stream()
		        .map(CapturaPersonaDTO::getRoles)
		        .flatMap(List::stream)
		        .collect(Collectors.toList()); 
		for (TblPersona persona : personaDTOToTbl) {
	        List<RolDTO> rolesParaGuardar = estableceRolAlumnoPorDefecto(listaPersonas);
	        for (RolDTO rol : rolesParaGuardar) {
	            RelPersonaRol relPersonaRol = new RelPersonaRol(mapper.map(rol, CatRol.class), persona,
	                    persona.getUsuarioModifico());
	            personaRolesRepo.save(relPersonaRol);
	        }
	    }
		
		domicilioPersonaRepo.save(domicilioDtoToTbl);
		List<SsoElementoDTO> listasso = datos.stream()
		        .map(CapturaPersonaDTO::getElementos)
		        .flatMap(List::stream)
		        .collect(Collectors.toList()); 
		for (TblPersona persona : personaDTOToTbl) {
	        for (SsoElementoDTO dto : listasso) {
	            SsoElemento entidad = mapper.map(dto, SsoElemento.class);
	            entidad.setPersona(persona);
	            ssoElementoRepo.saveAndFlush(entidad);
	        }
	    }
		exito = true;
		return exito;
	}

}
