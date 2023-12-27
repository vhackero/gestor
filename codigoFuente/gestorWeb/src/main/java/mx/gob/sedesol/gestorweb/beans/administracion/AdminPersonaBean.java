package mx.gob.sedesol.gestorweb.beans.administracion;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.validator.ValidatorException;

import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DualListModel;
import org.primefaces.model.UploadedFile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import mx.gob.sedesol.basegestor.commons.constantes.ConstantesGestor;
import mx.gob.sedesol.basegestor.commons.dto.admin.AsentamientoDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.CapturaPersonaDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.DomicilioPersonaDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.EntidadFederativaDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.MunicipioDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.PaisDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.PersonaCorreoDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.PersonaDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.PersonaDatosAcademicoDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.PersonaRolDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.PersonaSigeDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.PersonaTelefonoDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.RolDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.TipoCorreoDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.TipoTelefonoDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.UsuarioDatosLaboralesDTO;
import mx.gob.sedesol.basegestor.commons.dto.logisticainfraestructura.SedeDTO;
import mx.gob.sedesol.basegestor.commons.utils.GeneroEnum;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.OrdenGobiernoEnum;
import mx.gob.sedesol.basegestor.commons.utils.ResultadoTransaccionEnum;
import mx.gob.sedesol.basegestor.commons.utils.TipoServicioEnum;
import mx.gob.sedesol.basegestor.commons.utils.TipoUsuarioEnum;
import mx.gob.sedesol.basegestor.model.especificaciones.DatosLaboralesEspecificacion;
import mx.gob.sedesol.basegestor.mongo.service.BitacoraService;
import mx.gob.sedesol.basegestor.service.admin.EntidadFederativaService;
import mx.gob.sedesol.basegestor.service.admin.MunicipioService;
import mx.gob.sedesol.basegestor.service.admin.PersonaSigeService;
import mx.gob.sedesol.basegestor.service.impl.admin.PersonaServiceFacade;
import mx.gob.sedesol.gestorweb.beans.acceso.BaseBean;
import mx.gob.sedesol.gestorweb.commons.constantes.ConstantesGestorWeb;
import mx.gob.sedesol.gestorweb.commons.dto.ServicioCURP;
import mx.gob.sedesol.gestorweb.commons.utils.GestorArchivos;
import mx.gob.sedesol.gestorweb.sistema.SistemaBean;
import mx.gob.sedesol.gestorweb.ws.ServiciosExternos;

@ManagedBean
@SessionScoped
public class AdminPersonaBean extends BaseBean {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger(AdminPersonaBean.class);

	@ManagedProperty("#{personaServiceFacade}")
	private transient PersonaServiceFacade personaServiceFacade;

	@ManagedProperty("#{bitacoraService}")
	private BitacoraService bitacoraService;
	
	@ManagedProperty("#{entidadFederativaService}")
	private EntidadFederativaService entidadFederativaService;

	@ManagedProperty("#{sistema}")
	private SistemaBean textosSistema;

	@ManagedProperty("#{bitacoraBean}")
	private BitacoraBean bitacoraBean;
	
	@ManagedProperty("#{municipioService}")
	private MunicipioService municipioService;

	private transient ModelMapper mapper = new ModelMapper();

	private transient BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

	private PersonaDTO personaFiltros = new PersonaDTO();

	private DualListModel<RolDTO> listaRoles;
	private String rolesAsignados;

	private List<PersonaDTO> personas = new ArrayList<>();
	private PersonaDTO persona;

	private CapturaPersonaDTO datos;

	private List<PaisDTO> listaPaises;
	private List<EntidadFederativaDTO> listaEntidades;
	private List<MunicipioDTO> listaMunicipiosDomicilio;
	private List<AsentamientoDTO> listaAsentamientos;
	private List<EntidadFederativaDTO> listaSedes;
	private List<MunicipioDTO> listaMunicipiosLaboral;

	private boolean nuevaPersona;
	private boolean mostrarContrasenia;

	private PersonaDTO personaSeleccionada;

	private boolean mostrarDialogoExito = false;

	private String rutaFotografias;
	private String nombreFotoComun;
	private String rutaUndertow;
	
	@ManagedProperty(value = "#{personaSigeService}")
	private transient PersonaSigeService personaSigeService;

	private static final String CURP_PATTERN = "[A-Z]{1}[AEIOU]{1}[A-Z]{2}[0-9]{2}"
			+ "(0[1-9]|1[0-2])(0[1-9]|1[0-9]|2[0-9]|3[0-1])" + "[HM]{1}"
			+ "(AS|BC|BS|CC|CS|CH|CL|CM|DF|DG|GT|GR|HG|JC|MC|MN|MS|NT|NL|OC|PL|QT|QR|SP|SL|SR|TC|TS|TL|VZ|YN|ZS|NE)"
			+ "[B-DF-HJ-NP-TV-Z]{3}" + "[0-9A-Z]{1}[0-9]{1}$";

	@PostConstruct
	public void init() {

		rutaFotografias = personaServiceFacade.obtenerRutaAlmacenamientoFotosUsuario();
		nombreFotoComun = personaServiceFacade.obtenerNombreImagenComun();
		rutaUndertow = personaServiceFacade.obtenerRutaUndertow();

		listaPaises = personaServiceFacade.obtenerPaises();
		listaSedes = personaServiceFacade.obtenerEntidadesPorPais(ConstantesGestor.ID_PAIS_MEXICO);

		listaRoles = new DualListModel<>();
	}
	
	public void obtenerUsuarios() {
		List<PersonaSigeDTO> personasSige = personaSigeService.findAll();
		for(PersonaSigeDTO persona : personasSige) {
			try {
				if(!personaServiceFacade.getPersonaService().existeCurp(persona.getCurp())) {
					agregarPersona();
					
					PersonaDTO personaInsertar = crearPersona(persona);
					datos.setPersona(personaInsertar);
					datos.setDatosLaborales(datoslaborales(personaInsertar));
					datos.setPersonaCorreo(personaCorreo());
					datos.setDomicilioPersona(personaDomicilio());
					datos.setDatosAcademicos(personaDatosAcademicos());
					
					guardarPersona();
				}else {
					logger.error("La persona ya existe: "+persona.getCurp());
					agregarMsgError("La persona ya existe: "+persona.getCurp(), "Regiustro existente");
					continue;
				}
			}catch(Exception e) {
				logger.error(e);
			}
		}
	}
	
	private PersonaDTO crearPersona(PersonaSigeDTO persona) {
		Long id = 2L; 
		PersonaDTO personaInsertar = new PersonaDTO(id, "MX");
		String usuario = persona.getNombre()+persona.getApellidoPaterno().substring(0, 1)+persona.getApellidoMaterno().substring(0, 1);
		personaInsertar.setUsuario(usuario.toLowerCase());
		personaInsertar.setContrasenia(persona.getPassword());
		personaInsertar.setNuevaContrasenia(persona.getPassword());
		personaInsertar.setCurp(persona.getCurp());
		personaInsertar.setNombre(persona.getNombre());
		personaInsertar.setApellidoPaterno(persona.getApellidoPaterno());
		personaInsertar.setApellidoMaterno(persona.getApellidoMaterno());
		personaInsertar.setFechaNacimiento(persona.getFechaNacimiento());
		personaInsertar.setRfc(persona.getCurp().substring(0, 9));
		personaInsertar.setCorreoElectronico(persona.getCorreoInstitucional());
		personaInsertar.setConfirmacionContrasenia(persona.getPassword());
		personaInsertar.setIdEntidadFederativa("01");
		personaInsertar.setEntidadFederativa("Mexico");
		personaInsertar.setIdMunicipio("12");
		personaInsertar.setMunicipio("Patriotismo");
		personaInsertar.setIdDependencia("2-00");
		personaInsertar.setClaveDependencia("11");
		personaInsertar.setDependencia("Dependencia");
		personaInsertar.setIdUnidadAdministrativa("11");
		personaInsertar.setSso_status(String.valueOf(persona.getIdPersonaSige()));
		
		return personaInsertar;
	}
	
	private UsuarioDatosLaboralesDTO datoslaborales(PersonaDTO persona) {
		UsuarioDatosLaboralesDTO usuario = new UsuarioDatosLaboralesDTO(persona);
		EntidadFederativaDTO sede = entidadFederativaService.buscarPorId(11);
		List<MunicipioDTO> municipio = municipioService.buscarPorEntidadFederativa(11);
		usuario.setInstitucion("UNADM");
		usuario.setSede(sede);
		usuario.setMunicipio(municipio.get(0));
		usuario.setFechaIngreso(persona.getFechaActualizacion());
		return usuario;
	}
	
	private PersonaCorreoDTO personaCorreo() {
		Long usuarioModifico = 2L;
		PersonaCorreoDTO usuario = new PersonaCorreoDTO(usuarioModifico, 1);
		TipoCorreoDTO correo = new TipoCorreoDTO();

		correo.setDescripcion("Correo");
		correo.setIdTipoCorreo(1);
		correo.setActivo(1);
		usuario.setCorreoElectronico(datos.getPersona().getCorreoElectronico());
		usuario.setPersona(datos.getPersona());
		usuario.setTipoCorreo(correo);
		return usuario;
	}
	
	private DomicilioPersonaDTO personaDomicilio() {
		DomicilioPersonaDTO usuario = new DomicilioPersonaDTO();
		usuario.setNumeroExterior("1");
		usuario.setIdMunicipio("11");
		usuario.setPersona(datos.getPersona());
		usuario.setIdMunicipio("11");
		usuario.setIdEntidadFederativa(11);
		return usuario;
	}
	private PersonaDatosAcademicoDTO personaDatosAcademicos() {
		PersonaDatosAcademicoDTO usuario = new PersonaDatosAcademicoDTO();
		return usuario;
	}

	public void limpiarDatos() {
		personaFiltros = new PersonaDTO();
		personas = new ArrayList<>();
	}

	public void buscarPersonaPorCriterios() {
		personas = personaServiceFacade.getPersonaService().busquedaPorCriteriosPersonaBasica(personaFiltros);
		if (!personas.isEmpty()) {
			bitacoraBean.guardarBitacora(idPersonaEnSesion(), "BUS_USU", "", requestActual(), TipoServicioEnum.LOCAL);
		}
	}

	public String obtenerTipoUsuario(int tipo) {
		TipoUsuarioEnum tipoUsuarioEnum = TipoUsuarioEnum.getTipoUsuarioEnum(tipo);
		if (ObjectUtils.isNull(tipoUsuarioEnum)) {
			return "";
		} else {
			return tipoUsuarioEnum.getDescripcion();
		}
	}

	public boolean validaCurpConRegExp(String curp) {
		Pattern pattern = Pattern.compile(CURP_PATTERN);
		Matcher matcher = pattern.matcher(curp);
		return matcher.matches();
	}

	public String agregarPersona() {
		nuevaPersona = true;
		mostrarContrasenia = true;

		datos = new CapturaPersonaDTO();

		listaRoles = new DualListModel<>(personaServiceFacade.obtenerTodosRoles(), new ArrayList<>());
		rolesAsignados = "";

		datos.setPersona(new PersonaDTO(getUsuarioEnSession().getIdPersona(), ConstantesGestor.ID_PAIS_MEXICO));
		datos.getPersona().setRutaCompletaFoto(rutaUndertow + nombreFotoComun);
		datos.setDatosLaborales(new UsuarioDatosLaboralesDTO(getUsuarioEnSession().getIdPersona()));
		datos.getDatosLaborales().getSede().setIdEntidadFederativa(0);

		datos.setTelefonoFijo(new PersonaTelefonoDTO(getUsuarioEnSession().getIdPersona(),
				ConstantesGestor.TIPO_TELEFONO_INSTITUCIONAL));
		datos.setCelular(
				new PersonaTelefonoDTO(getUsuarioEnSession().getIdPersona(), ConstantesGestor.TIPO_TELEFONO_MOVIL));
		datos.setPersonaCorreo(
				new PersonaCorreoDTO(getUsuarioEnSession().getIdPersona(), ConstantesGestor.TIPO_CORREO_INSTITUCIONAL));
		datos.setDomicilioPersona(
				new DomicilioPersonaDTO(getUsuarioEnSession().getIdPersona(), ConstantesGestor.ID_PAIS_MEXICO));

		listaMunicipiosLaboral = new ArrayList<>();
		listaEntidades = personaServiceFacade.obtenerEntidadesPorPais(ConstantesGestor.ID_PAIS_MEXICO);
		listaMunicipiosDomicilio = new ArrayList<>();

		return ConstantesGestorWeb.NAVEGA_REGISTRO_USUARIO_INTERNO;
	}
	


	public String cargarDatosPersona() {
		
		nuevaPersona = false;
		mostrarContrasenia = false;

		PersonaDTO personaModificar = new PersonaDTO();
		personaSeleccionada = new PersonaDTO();
		mapper.map(persona, personaModificar);
		mapper.map(persona, personaSeleccionada);
		datos = personaServiceFacade.obtenerDatosPersona(personaModificar, getUsuarioEnSession().getIdPersona());

		listaMunicipiosLaboral = personaServiceFacade
				.obtenerMunicipiosPorEntidad(datos.getDatosLaborales().getSede().getIdEntidadFederativa());
		listaEntidades = personaServiceFacade.obtenerEntidadesPorPais(datos.getDomicilioPersona().getIdPais());
		listaMunicipiosDomicilio = personaServiceFacade
				.obtenerMunicipiosPorEntidad(datos.getDomicilioPersona().getIdEntidadFederativa());
		listaAsentamientos = personaServiceFacade
				.obtenerAsentamientosPorMunicipio(datos.getDomicilioPersona().getIdMunicipio());

		String rutaAbsolutaFoto = rutaFotografias + datos.getPersona().getRutaFoto();

		File archivo = new File(rutaAbsolutaFoto);

		if (archivo.exists()) {
			datos.getPersona().setRutaCompletaFoto(rutaUndertow + datos.getPersona().getRutaFoto());
		} else {
			datos.getPersona().setRutaCompletaFoto(rutaUndertow + nombreFotoComun);
		}
		cargarRolesUsuario();
		actualizarRolesUsuario();

		bitacoraBean.guardarBitacora(idPersonaEnSesion(), "VER_USU", String.valueOf(datos.getPersona().getIdPersona()),
				requestActual(), TipoServicioEnum.LOCAL);

		if (datos.getPersona().getTipoUsuario().equals(TipoUsuarioEnum.INTERNO.getValor())) {
			return ConstantesGestorWeb.NAVEGA_REGISTRO_USUARIO_INTERNO;
		} else {
			return ConstantesGestorWeb.NAVEGA_REGISTRO_USUARIO_EXTERNO;
		}
	}

	public void mostrarContrasenias() {
		mostrarContrasenia = true;
	}

	public void cambiarTipoUsuario() {
		try {
			switch (TipoUsuarioEnum.getTipoUsuarioEnum(datos.getPersona().getTipoUsuario())) {
			case INTERNO:
				getFacesContext().getExternalContext().redirect(ConstantesGestorWeb.RUTA_REGISTRO_USUARIO_INTERNO);
				break;
			case EXTERNO:
				getFacesContext().getExternalContext().redirect(ConstantesGestorWeb.RUTA_REGISTRO_USUARIO_EXTERNO);
				break;
			default:
				break;
			}
		} catch (IOException e) {
			logger.info(e.getMessage(), e);
		}
	}

	public void actualizarRolesUsuario() {
		StringBuilder roles = new StringBuilder();
		for (RolDTO rol : listaRoles.getTarget()) {
			roles.append(rol.getNombre() + ", ");
		}
		if (roles.length() == 0) {
			rolesAsignados = "";
		} else {
			rolesAsignados = roles.substring(0, roles.length() - 2);
		}
	}

	public void onChangeSede(ValueChangeEvent e) {
		Integer valor = (Integer) e.getNewValue();
		if (!valor.equals(0)) {
			listaMunicipiosLaboral = personaServiceFacade.obtenerMunicipiosPorEntidad(valor);
			// obtenerNombreEntidad
			datos.getDatosLaborales().getSede().setNombre(obtenerNombreEntidad(valor));
		} else {
			datos.getDatosLaborales().getSede().setIdEntidadFederativa(null);
			datos.getDatosLaborales().getSede().setNombre(null);
			listaMunicipiosLaboral = new ArrayList<>();
		}

	}

	public void onChangeMunicipioLaboral(ValueChangeEvent e) {
		Integer valor = (Integer) e.getNewValue();
		if (!valor.equals(0)) {
			datos.getDatosLaborales().getMunicipio().setNombre(obtenerNombreMunicipio(valor));
		} else {
			datos.getDatosLaborales().getMunicipio().setIdMunicipio(null);
			datos.getDatosLaborales().getMunicipio().setNombre(null);
			listaMunicipiosLaboral = new ArrayList<>();
		}
	}

	public String obtenerNombreEntidad(Integer id) {

		for (EntidadFederativaDTO entidad : listaEntidades) {
			if (id.equals(entidad.getIdEntidadFederativa())) {
				return entidad.getNombre();
			}
		}
		return null;
	}

	public String obtenerNombreMunicipio(Integer id) {

		for (MunicipioDTO municipio : listaMunicipiosLaboral) {
			if (id.equals(municipio.getIdMunicipio())) {
				return municipio.getNombre();
			}
		}
		return null;
	}

	public void validarSedeLaboral(FacesContext context, UIComponent component, Object value)
			throws ValidatorException {
		context = FacesContext.getCurrentInstance();
		Integer id = (Integer) value;
		if (ObjectUtils.isNotNull(id)) {
			if (id.equals(0)) {
				throw new ValidatorException(
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "El dato es requerido.", null));
			}
		}
	}

	public void validarMunicipioLaboral(FacesContext context, UIComponent component, Object value)
			throws ValidatorException {
		context = FacesContext.getCurrentInstance();
		String id = (String) value;
		if (!ObjectUtils.isNullOrEmpty(id)) {
			if (id.equals(0)) {
				throw new ValidatorException(
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "El dato es requerido.", null));
			}
		}
	}

	public void validarCorreo(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		context = FacesContext.getCurrentInstance();
		String correo = (String) value;
		if (ObjectUtils.isNullOrEmpty(correo)) {
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "El dato es requerido.", null));
		} else if (correoExiste(correo)) {
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "El correo ya existe.", null));
		}
	}

	public boolean correoExiste(String correo) {
		List<PersonaCorreoDTO> listaDeCorreos = personaServiceFacade.getPersonaCorreoService().findAll();
		if (nuevaPersona) {
			for (PersonaCorreoDTO personaCorreo : listaDeCorreos) {
				if (personaCorreo.getCorreoElectronico().equals(correo)) {
					return true;
				}
			}
		} else {
			Long idPersona = datos.getPersona().getIdPersona();
			PersonaCorreoDTO personaCorreo = personaServiceFacade.getPersonaCorreoService()
					.obtenerCorreoInstitucional(idPersona);
			if (ObjectUtils.isNotNull(personaCorreo)) {
				String correoActual = personaCorreo.getCorreoElectronico();
				String correoNuevo = correo;
				if (!correoActual.equals(correoNuevo)) {
					for (PersonaCorreoDTO objetoCorreoPersona : listaDeCorreos) {
						if (objetoCorreoPersona.getCorreoElectronico().equals(correoNuevo)) {
							return true;
						}
					}
				}
			}

		}

		return false;
	}

	public void validarFormatoCurp(FacesContext context, UIComponent component, Object value)
			throws ValidatorException {

		context = FacesContext.getCurrentInstance();
		String curp = (String) value;
		if (ObjectUtils.isNullOrEmpty(curp)) {
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "El dato es requerido.", null));
		} else if (!validaCurpConRegExp(curp)) {
			throw new ValidatorException(
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Formato de CURP incorrecto.", null));

		}

	}

	public void onChangePais(ValueChangeEvent e) {
		if (ObjectUtils.isNotNull(e.getNewValue())) {
			String valor = (String) e.getNewValue();
			listaEntidades = personaServiceFacade.obtenerEntidadesPorPais(valor);
		}
	}

	public void onChangeEntidad(ValueChangeEvent e) {
		if (ObjectUtils.isNotNull(e.getNewValue())) {

			Integer valor = (Integer) e.getNewValue();
			listaMunicipiosDomicilio = personaServiceFacade.obtenerMunicipiosPorEntidad(valor);
		}
	}

	public void onChangeMunicipio(ValueChangeEvent e) {
		if (ObjectUtils.isNotNull(e.getNewValue())) {

			String valor = (String) e.getNewValue();
			listaAsentamientos = personaServiceFacade.obtenerAsentamientosPorMunicipio(valor);
		}
	}

	public void validarCURP() {
		logger.info(datos.getPersona().getCurp());
		ServicioCURP servicioCURP = ServiciosExternos.obtenerDatosPersona(datos.getPersona().getCurp());
		if (ObjectUtils.isNull(servicioCURP)) {
			agregarMsgInfo("El servicio no se encuentra disponible", null);
		} else {
			datos.getPersona().setNombre(servicioCURP.getResponse().getNombres());
			datos.getPersona().setApellidoPaterno(servicioCURP.getResponse().getApellidoPaterno());
			datos.getPersona().setApellidoMaterno(servicioCURP.getResponse().getApellidoMaterno());
		}
	}

	public void validarCodigoPostal() {
		AsentamientoDTO asentamiento = personaServiceFacade
				.obtenerAsentamientoPorCodigoPostal(datos.getDomicilioPersona().getCodigoPostal());

		if (ObjectUtils.isNotNull(asentamiento)) {
			datos.getDomicilioPersona().setAsentamiento(new AsentamientoDTO());
			datos.getDomicilioPersona().getAsentamiento().setIdAsentamiento(asentamiento.getIdAsentamiento());
			datos.getDomicilioPersona().setIdMunicipio(asentamiento.getMunicipio().getIdMunicipio());
			datos.getDomicilioPersona().setIdEntidadFederativa(
					asentamiento.getMunicipio().getEntidadFederativa().getIdEntidadFederativa());
			datos.getDomicilioPersona()
					.setIdPais(asentamiento.getMunicipio().getEntidadFederativa().getPais().getIdPais());

			listaEntidades = personaServiceFacade.obtenerEntidadesPorPais(datos.getDomicilioPersona().getIdPais());
			listaMunicipiosDomicilio = personaServiceFacade
					.obtenerMunicipiosPorEntidad(datos.getDomicilioPersona().getIdEntidadFederativa());
			listaAsentamientos = personaServiceFacade
					.obtenerAsentamientosPorMunicipio(datos.getDomicilioPersona().getIdMunicipio());
		}
	}

	public TipoUsuarioEnum[] getTiposUsuarios() {
		return TipoUsuarioEnum.values();
	}

	public OrdenGobiernoEnum[] getOrdenGobierno() {
		return OrdenGobiernoEnum.values();
	}

	public GeneroEnum[] getGeneros() {
		return GeneroEnum.values();
	}

	public void fileUploadListener(FileUploadEvent e) {
		UploadedFile archivoCargar = e.getFile();
		if (ObjectUtils.isNotNull(archivoCargar) && !ObjectUtils.isNullOrEmpty(archivoCargar.getFileName())) {
			datos.getPersona().setRutaFoto(UUID.randomUUID().toString());
			String rutaArchivo = rutaFotografias + datos.getPersona().getRutaFoto();
			if (GestorArchivos.crearCarpeta(rutaFotografias).getResultado().getValor() && GestorArchivos
					.almacenarArchivo(rutaArchivo, archivoCargar.getContents()).getResultado().getValor()) {
				datos.getPersona().setRutaCompletaFoto(rutaUndertow + datos.getPersona().getRutaFoto());
			}
		}
	}

	public String guardarPersona() {
		datos.setRoles(listaRoles.getTarget());
		datos.getPersona().setUnidadAdministrativa(datos.getPersona().getNuevaContrasenia());
		if (!ObjectUtils.isNullOrEmpty(datos.getPersona().getNuevaContrasenia())) {
			datos.getPersona().setContraseniaEncriptada(encoder.encode(datos.getPersona().getNuevaContrasenia()));
		}

		if (nuevaPersona) {
			return almacenarPersona();
		} else {
			return actualizarPersona();
		}
	}

	public List<RolDTO> estableceRolPorDefecto() {
		List<RolDTO> rolesPorDefecto = new ArrayList<>();
		rolesPorDefecto.add(personaServiceFacade.obtenerRolAlumno());
		return rolesPorDefecto;
	}

	private String almacenarPersona() {
		String ruta = null;

		ResultadoDTO<PersonaDTO> resultado = personaServiceFacade.guardarPersona(datos);
		if (resultado.getResultado() == ResultadoTransaccionEnum.EXITOSO) {
			personas.add(0, resultado.getDto());

			if (ObjectUtils.isNotNull(resultado.getMensajes())) {
				agregarFlashMessage(textosSistema.obtenerTexto(resultado.getMensajes().get(0)), null,
						FacesMessage.SEVERITY_INFO);
			}

			bitacoraBean.guardarBitacora(idPersonaEnSesion(), "REG_USU",
					String.valueOf(datos.getPersona().getIdPersona()), requestActual(), TipoServicioEnum.LOCAL);

			personas = new ArrayList<>();
			ruta = ConstantesGestorWeb.NAVEGA_ADMIN_USUARIOS;
		} else {
			if (ObjectUtils.isNotNull(resultado.getMensajes())) {
				agregarMsgError(resultado.getMensajes(), null, textosSistema);
			}
		}
		return ruta;
	}

	private String actualizarPersona() {
		String ruta = null;
		
		ResultadoDTO<PersonaDTO> resultado = personaServiceFacade.actualizarPersona(datos);
		if (resultado.getResultado() == ResultadoTransaccionEnum.EXITOSO) {

			personas.remove(persona);
			personas.add(0, resultado.getDto());
			if (ObjectUtils.isNotNull(resultado.getMensajes())) {
				agregarFlashMessage(textosSistema.obtenerTexto(resultado.getMensajes().get(0)), null,
						FacesMessage.SEVERITY_INFO);
			}

			bitacoraBean.guardarBitacora(idPersonaEnSesion(), "EDI_USU",
					String.valueOf(datos.getPersona().getIdPersona()), requestActual(), TipoServicioEnum.LOCAL);

			personas = new ArrayList<>();
			ruta = ConstantesGestorWeb.NAVEGA_ADMIN_USUARIOS;
		} else {
			if (ObjectUtils.isNotNull(resultado.getMensajes())) {
				agregarMsgError(resultado.getMensajes(), null, textosSistema);
			}
		}
		return ruta;
	}

	public void ocultarDialogo() {
		mostrarDialogoExito = false;
	}

	public void cargarRolesUsuario() {
		List<RolDTO> asignados = new ArrayList<>();
		List<RolDTO> rolesDisponibles = personaServiceFacade.obtenerTodosRoles();

		if (ObjectUtils.isNotNull(personaSeleccionada)) {
			List<PersonaRolDTO> rolesPersona = personaServiceFacade
					.obtenerRolesPorUsuario(personaSeleccionada.getUsuario());
			for (PersonaRolDTO personaRol : rolesPersona) {
				RolDTO rolAsignado = obtenerRol(personaRol.getRol(), rolesDisponibles);
				if (ObjectUtils.isNotNull(rolAsignado)) {
					asignados.add(rolAsignado);
				}
			}
		}
		listaRoles = new DualListModel<>(rolesDisponibles, asignados);
	}

	private RolDTO obtenerRol(RolDTO rol, List<RolDTO> roles) {
		RolDTO rolAsignado = null;
		for (RolDTO rolDisponible : roles) {
			if (rolDisponible.getIdRol().equals(rol.getIdRol())) {
				rolAsignado = rolDisponible;
				roles.remove(rolDisponible);
				break;
			}
		}
		return rolAsignado;
	}

	public void guardarRolesUsuario() {

		ResultadoDTO<PersonaRolDTO> resultado = personaServiceFacade.almacenarRolesUsuario(personaSeleccionada,
				personaServiceFacade.getPersonaService().estableceRolAlumnoPorDefecto(listaRoles.getTarget()),
				getUsuarioEnSession().getIdPersona());

		if (resultado.getResultado() == ResultadoTransaccionEnum.EXITOSO) {

			bitacoraBean.guardarBitacora(idPersonaEnSesion(), "EDI_ROL_USU",
					String.valueOf(resultado.getDto().getPersona().getIdPersona()), requestActual(),
					TipoServicioEnum.LOCAL);

			agregarMsgInfo("Actualización exitosa.", null);
		} else {
			agregarMsgInfo("Ocurrió un error al actualizar.", null);
		}

	}

	public void cambioEstatusPersona() {

		String idPersona = getFacesContext().getExternalContext().getRequestParameterMap()
				.get(ConstantesGestorWeb.PARAM_ID_PERSONA);
		if (ObjectUtils.isNotNull(idPersona)) {
			personaSeleccionada = personaServiceFacade.obtenerPersonaPorId(Long.parseLong(idPersona));
		}

		try {
			if (ObjectUtils.isNotNull(personaSeleccionada) && ObjectUtils.isNotNull(personaSeleccionada.getActivo())) {

				if (personaSeleccionada.getActivo()) {
					personaSeleccionada.setActivo(false);
				} else {
					personaSeleccionada.setActivo(true);
				}

				personaSeleccionada.setUsuarioModifico(getUsuarioEnSession().getIdPersona());
				personaSeleccionada.setFechaActualizacion(new Date());

				ResultadoDTO<PersonaDTO> resultado = personaServiceFacade.actualizarPersona(personaSeleccionada);

				if (resultado.getResultado() == ResultadoTransaccionEnum.EXITOSO) {

					String funcionalidad = personaSeleccionada.getActivo().booleanValue() == true ? "EDI_USU"
							: "DES_USU";

					bitacoraBean.guardarBitacora(idPersonaEnSesion(), funcionalidad,
							String.valueOf(resultado.getDto().getIdPersona()), requestActual(), TipoServicioEnum.LOCAL);

					agregarMsgInfo("Actualización exitosa", null);

				} else {
					agregarMsgInfo("Ocurrió un error al actualizar.", null);
				}

			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	public PersonaDTO getPersona() {
		return persona;
	}

	public void setPersona(PersonaDTO persona) {
		this.persona = persona;
	}

	/**
	 * @return the personaSeleccionada
	 */
	public PersonaDTO getPersonaSeleccionada() {
		return personaSeleccionada;
	}

	/**
	 * @param personaSeleccionada
	 *            the personaSeleccionada to set
	 */
	public void setPersonaSeleccionada(PersonaDTO personaSeleccionada) {
		this.personaSeleccionada = personaSeleccionada;
	}

	public SistemaBean getTextosSistema() {
		return textosSistema;
	}

	public void setTextosSistema(SistemaBean textosSistema) {
		this.textosSistema = textosSistema;
	}

	public List<PersonaDTO> getPersonas() {
		return personas;
	}

	public void setPersonas(List<PersonaDTO> personas) {
		this.personas = personas;
	}

	public PersonaDTO getPersonaFiltros() {
		return personaFiltros;
	}

	public void setPersonaFiltros(PersonaDTO personaFiltros) {
		this.personaFiltros = personaFiltros;
	}

	public DualListModel<RolDTO> getListaRoles() {
		return listaRoles;
	}

	public void setListaRoles(DualListModel<RolDTO> listaRoles) {
		this.listaRoles = listaRoles;
	}

	public List<PaisDTO> getListaPaises() {
		return listaPaises;
	}

	public void setListaPaises(List<PaisDTO> listaPaises) {
		this.listaPaises = listaPaises;
	}

	public List<EntidadFederativaDTO> getListaEntidades() {
		return listaEntidades;
	}

	public void setListaEntidades(List<EntidadFederativaDTO> listaEntidades) {
		this.listaEntidades = listaEntidades;
	}

	public List<MunicipioDTO> getListaMunicipiosDomicilio() {
		return listaMunicipiosDomicilio;
	}

	public void setListaMunicipiosDomicilio(List<MunicipioDTO> listaMunicipiosDomicilio) {
		this.listaMunicipiosDomicilio = listaMunicipiosDomicilio;
	}

	public List<AsentamientoDTO> getListaAsentamientos() {
		return listaAsentamientos;
	}

	public void setListaAsentamientos(List<AsentamientoDTO> listaAsentamientos) {
		this.listaAsentamientos = listaAsentamientos;
	}

	public List<MunicipioDTO> getListaMunicipiosLaboral() {
		return listaMunicipiosLaboral;
	}

	public void setListaMunicipiosLaboral(List<MunicipioDTO> listaMunicipiosLaboral) {
		this.listaMunicipiosLaboral = listaMunicipiosLaboral;
	}

	public List<EntidadFederativaDTO> getListaSedes() {
		return listaSedes;
	}

	public void setListaSedes(List<EntidadFederativaDTO> listaSedes) {
		this.listaSedes = listaSedes;
	}

	public PersonaServiceFacade getPersonaServiceFacade() {
		return personaServiceFacade;
	}

	public void setPersonaServiceFacade(PersonaServiceFacade personaServiceFacade) {
		this.personaServiceFacade = personaServiceFacade;
	}

	public String getRolesAsignados() {
		return rolesAsignados;
	}

	public void setRolesAsignados(String rolesAsignados) {
		this.rolesAsignados = rolesAsignados;
	}

	public CapturaPersonaDTO getDatos() {
		return datos;
	}

	public void setDatos(CapturaPersonaDTO datos) {
		this.datos = datos;
	}

	public boolean isMostrarDialogoExito() {
		return mostrarDialogoExito;
	}

	public void setMostrarDialogoExito(boolean mostrarDialogoExito) {
		this.mostrarDialogoExito = mostrarDialogoExito;
	}

	public boolean isNuevaPersona() {
		return nuevaPersona;
	}

	public void setNuevaPersona(boolean nuevaPersona) {
		this.nuevaPersona = nuevaPersona;
	}

	public boolean isMostrarContrasenia() {
		return mostrarContrasenia;
	}

	public void setMostrarContrasenia(boolean mostrarContrasenia) {
		this.mostrarContrasenia = mostrarContrasenia;
	}

	public BitacoraService getBitacoraService() {
		return bitacoraService;
	}

	public void setBitacoraService(BitacoraService bitacoraService) {
		this.bitacoraService = bitacoraService;
	}

	public BitacoraBean getBitacoraBean() {
		return bitacoraBean;
	}

	public void setBitacoraBean(BitacoraBean bitacoraBean) {
		this.bitacoraBean = bitacoraBean;
	}

	public PersonaSigeService getPersonaSigeService() {
		return personaSigeService;
	}

	public void setPersonaSigeService(PersonaSigeService personaSigeService) {
		this.personaSigeService = personaSigeService;
	}

	public EntidadFederativaService getEntidadFederativaService() {
		return entidadFederativaService;
	}

	public void setEntidadFederativaService(EntidadFederativaService entidadFederativaService) {
		this.entidadFederativaService = entidadFederativaService;
	}

	public MunicipioService getMunicipioService() {
		return municipioService;
	}

	public void setMunicipioService(MunicipioService municipioService) {
		this.municipioService = municipioService;
	}
	
}
