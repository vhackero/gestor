package mx.gob.sedesol.gestorweb.beans.acceso;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import org.apache.log4j.Logger;
import org.springframework.security.core.GrantedAuthority;

import mx.gob.sedesol.basegestor.commons.constantes.ConstantesGestor;
import mx.gob.sedesol.basegestor.commons.dto.admin.CatalogoComunDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.PersonaDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.PersonaRolDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.RolDTO;
import mx.gob.sedesol.basegestor.commons.utils.TipoServicioEnum;
import mx.gob.sedesol.basegestor.mongo.service.BitacoraService;
import mx.gob.sedesol.basegestor.service.ParametroSistemaService;
import mx.gob.sedesol.basegestor.service.admin.PersonaRolesService;
import mx.gob.sedesol.basegestor.service.admin.PersonaService;
import mx.gob.sedesol.gestorweb.beans.administracion.AdminPersonaBean;
import mx.gob.sedesol.gestorweb.beans.administracion.BitacoraBean;
import mx.gob.sedesol.gestorweb.beans.administracion.NotificacionesBean;
import mx.gob.sedesol.gestorweb.beans.analisisdatos.ReporteGestionEscolarBean;
import mx.gob.sedesol.gestorweb.beans.gestionaprendizaje.ExpedienteAlumnoBean;
import mx.gob.sedesol.gestorweb.beans.gestionaprendizaje.alumnoview.ConstanciasBean;
import mx.gob.sedesol.gestorweb.beans.gestionescolar.EventoCapacitacionBean;
import mx.gob.sedesol.gestorweb.beans.logisticainfraestructura.AreasBean;
import mx.gob.sedesol.gestorweb.commons.constantes.ConstantesGestorWeb;
import mx.gob.sedesol.gestorweb.commons.dto.UsuarioSessionDTO;
import mx.gob.sedesol.gestorweb.commons.utils.ObjectUtils;

@SessionScoped
@ManagedBean
public class MenuGestorBean extends BaseBean {

	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{personaRolesService}")
	private PersonaRolesService personaRolesService;
	
	@ManagedProperty(value = "#{notificacionesBean}")
	private NotificacionesBean notificacionesBean;

	@ManagedProperty(value = "#{areasBean}")
	private AreasBean areasBean;

	@ManagedProperty(value = "#{personaService}")
	private PersonaService personaService;

	@ManagedProperty(value = "#{parametroSistemaService}")
	private ParametroSistemaService parametroSistemaService;

	@ManagedProperty(value = "#{eventoCapacitacionBean}")
	private EventoCapacitacionBean eventoCapacitacionBean;

	@ManagedProperty(value = "#{adminPersonaBean}")
	private AdminPersonaBean adminPersonaBean;

	@ManagedProperty("#{bitacoraService}")
	private BitacoraService bitacoraService;

	@ManagedProperty("#{bitacoraBean}")
	private BitacoraBean bitacoraBean;
	
	@ManagedProperty(value = "#{reporteGestionEscolarBean}")
	private ReporteGestionEscolarBean reporteGestionEscolarBean;
	
	@ManagedProperty("#{constanciasBean}")
	private ConstanciasBean constanciasBean;
	
	@ManagedProperty("#{expedienteAlumnoBean}")
	private ExpedienteAlumnoBean expedienteAlumnoBean;
	
	private List<RolDTO> roles;
	private Integer idRol;

	private String rutaFotoUsuario;

	private Map<String, String> mapa;
	private boolean showLogros;
	private boolean showPerfil=false;

	public boolean isShowPerfil() {
		return showPerfil;
	}

	private static final Logger logger = Logger.getLogger(MenuGestorBean.class);

	@PostConstruct
	public void init() {
		showPerfil=false;
		roles = new ArrayList<>();
		List<PersonaRolDTO> rolesPersona = personaRolesService
				.obtieneRelPersonaRolesPorUsuario(getUsuarioEnSession().getUsuario());
		
		for (PersonaRolDTO personaRol : rolesPersona) {
			roles.add(personaRol.getRol());
			if(personaRol.getRol().getIdRol() == 1) {
				showPerfil = true;
			}
		}
		
		if (ObjectUtils.isNullOrEmpty(roles)) {
			idRol = null;
			showLogros=false;
		} else {
			idRol = roles.get(0).getIdRol();
			mapa = personaRolesService.obtenerFuncionalidadesRol(idRol);
			showLogros = mapa.containsKey("MIS_LOGROS");
		}
		actualizarMenu();

		PersonaDTO persona = personaService.buscarPorId(getUsuarioEnSession().getIdPersona());

		StringBuilder rutaAbsoluta = new StringBuilder(
				parametroSistemaService.obtenerParametroConRutaCompleta(ConstantesGestor.PARAMETRO_RUTA_RECURSOS));
		rutaAbsoluta
				.append(parametroSistemaService.obtenerParametroRuta(ConstantesGestor.PARAMETRO_RUTA_FOTOS_USUARIOS));

		StringBuilder rutaUndertow = new StringBuilder(
				parametroSistemaService.obtenerParametroRuta(ConstantesGestor.PARAMETRO_RUTA_UNDERTOW));
		rutaUndertow
				.append(parametroSistemaService.obtenerParametroRuta(ConstantesGestor.PARAMETRO_RUTA_FOTOS_USUARIOS));

		parametroSistemaService.obtenerParametro(ConstantesGestor.PARAMETRO_NOMBRE_FOTO_COMUN);

		String rutaAbsolutaFoto = rutaAbsoluta.toString() + persona.getRutaFoto();

		File archivo = new File(rutaAbsolutaFoto);

		if (archivo.exists()) {
			rutaFotoUsuario = rutaUndertow.toString() + persona.getRutaFoto();
		} else {
			rutaFotoUsuario = rutaUndertow
					+ parametroSistemaService.obtenerParametro(ConstantesGestor.PARAMETRO_NOMBRE_FOTO_COMUN);
		}
	}

	public void actualizarMenu() {
		if (ObjectUtils.isNull(idRol)) {
			mapa = new HashMap<>();
		} else {
			mapa = personaRolesService.obtenerFuncionalidadesRol(idRol);
		}
		try {
			getFacesContext().getExternalContext().redirect(
					getFacesContext().getExternalContext().getRequestContextPath() + ConstantesGestorWeb.RUTA_TABLERO);
		} catch (IOException e) {
			logger.info(e.getMessage(), e);
		}
	}

	public boolean rolTienePermiso(String claveFuncionalidad) {
		return mapa.containsKey(claveFuncionalidad);
	}

	public String navegaPantallaInicio() {
		logger.info("Navegando a pantalla de administracion de consulta bitacora");
		return ConstantesGestorWeb.NAVEGA_INICIO;
	}

	public String navegaAnalisisUsuarios() {
		logger.info("Navegando a pantalla de análisis de datos de usuario");
		return ConstantesGestorWeb.NAVEGA_ANALISIS_USUARIO;
	}

	public String navegaAnalisisGestionEscolar() {
		logger.info("Navegando a pantalla de análisis de datos de gestion escolar");
		reporteGestionEscolarBean.inicializarMalla();
		return ConstantesGestorWeb.NAVEGA_ANALISIS_GESTION_ESC;
	}

	public String navegaAnalisisEventos() {
		reporteGestionEscolarBean.inicializarMalla();
		logger.info("Navegando a pantalla de análisis de datos de eventos de capacitacion");
		return ConstantesGestorWeb.NAVEGA_ANALISIS_EVENTOS;
	}

	public String navegaEventosPublico() {
		logger.info("Navegando a pantalla de eventos en portal publico");
		return ConstantesGestorWeb.NAVEGA_EVENTOS_PUBLICOS;
	}

	public String navegaRecursos() {
		logger.info("Navegando a pantalla de administracion de recursos");
		return ConstantesGestorWeb.NAVEGA_RECURSOS;
	}

	/**
	 * 
	 * @return
	 */
	public String navegaAdministracionUsuarios() {
		logger.info("Navegando a modulo de administracion de usuarios");
		inicializaAdminUsuarios();
		return ConstantesGestorWeb.NAVEGA_ADMIN_USUARIOS;
	}

	public String navegaResponsablesEventoCapacitacion() {
		logger.info("Navegando a modulo de responsables de eventos de capacitación");
		return ConstantesGestorWeb.NAVEGA_RESPONSABLES_EVE_CAP;
	}

	public String navegaCompetenciasPorEje() {
		logger.info("Navegando a modulo de competencias por eje");
		return ConstantesGestorWeb.REL_EJE_COMPETENCIA;
	}

	public String navegaAgregarUsuario() {
		logger.info("Navegando a formulario para agregar ususrio");
		return ConstantesGestorWeb.NAVEGA_REGISTRO_USUARIO_INTERNO;
	}

	public String navegaFuncionalidades() {
		logger.info("Navegando a modulo de administracion de funcionalidades");
		inicializaFuncionalidades();
		return ConstantesGestorWeb.NAVEGA_FUNCIONALIDADES;
	}

	public String navegaBusquedaTextosSistema() {
		logger.info("Navegando a busqueda de textos del sistema");
		return ConstantesGestorWeb.NAVEGA_BUSQUEDA_TEXTOS_SISTEMA;
	}

	public String navegaActividadesUsuario() {
		logger.info("Navegando a actividades del usuario");
		return ConstantesGestorWeb.NAVEGA_ACTIVIDADES_USUARIO;
	}

	public String navegaPlantillasDocumentos() {
		logger.info("Navegando a plantillas de documentos");
		inicializaPlantillasDocumentos();
		return ConstantesGestorWeb.NAVEGA_PLANTILLAS_DOCS;
	}

	public String navegaRoles() {
		logger.info("Navegando a modulo de administracion de roles");
		return ConstantesGestorWeb.NAVEGA_ROLES;
	}

	public String navegaMunicipios() {
		logger.info("Navegando a modulo de administracion de municipios");
		return ConstantesGestorWeb.NAVEGA_MUNICIPIOS;
	}

	public String navegaCargaUsuarios() {
		logger.info("Navegando a modulo de administracion de carga de usuarios");
		return ConstantesGestorWeb.NAVEGA_CARGA_USUARIOS;
	}

	public String navegaConsultaBitacora() {
		logger.info("Navegando a modulo de administracion de consulta bitacora");
		return ConstantesGestorWeb.NAVEGA_CONSULTA_BITACORA;
	}

	public String navegaAsignarRolesUsuario() {
		logger.info("Navegando a modulo  de administracion de asignacion de roles");
		return ConstantesGestorWeb.NAVEGA_ASIGNAR_ROLES;
	}

	public String navegaNotificaciones() {
		notificacionesBean.init();
		logger.info("Navegando a modulo de notificaciones");
		return ConstantesGestorWeb.NAVEGA_NOTIFICACIONES;
	}

	public String navegaNotificacionDetalle() {
		logger.info("Navegando a modulo de notificacion detalle");
		return ConstantesGestorWeb.NAVEGA_NOTIFICACION_DETALLE;
	}

	public String navegaNotificacionEdicion() {
		logger.info("Navegando a modulo de notificacion edicion");
		return ConstantesGestorWeb.NAVEGA_NOTIFICACION_EDICION;
	}

	public String navegaParametrosSistema() {
		logger.info("Navegando a modulo de notificacion edicion");
		inicializaParametrosSistema();
		return ConstantesGestorWeb.NAVEGA_PARAMETROS_SISTEMA;
	}

	public String navegaCatPlanesProgramas() {
		return ConstantesGestorWeb.NAVEGA_CAT_PLANES_PROGRAMAS;
	}

	public String navegaCatGestionEscolar() {
		return ConstantesGestorWeb.NAVEGA_CAT_GESTION_ESCOLAR;
	}

	public String navegaCatGestionAprendizaje() {
		return ConstantesGestorWeb.NAVEGA_CAT_GESTION_APRENDIZAJE;
	}

	public String navegaPlantillaMensajes() {
		logger.info("Navegando a modulo de plantillas de mensajes");
		return ConstantesGestorWeb.NAVEGA_PLANTILLAS_MENSAJES;
	}

	public String navegaImagenesDocumentos() {
		inicializaImagenesDocumentos();
		return ConstantesGestorWeb.NAVEGA_IMAGENES_DOCUMENTOS;
	}

	public String navegaAdminCatalogosPlanesProgramas() {
		logger.info("Navegando a modulo de administracion catalogos planes programas");
		return ConstantesGestorWeb.NAVEGA_PLANES_PROGRAMAS_CATALOGOS;
	}

	public String navegaPlanProgMallaCurricular() {
		logger.info("Navegando a modulo de planes busqueda");
		return ConstantesGestorWeb.NAVEGA_BUSQUEDA_PLANES;
	}

	public String navegaBusquedaProgramas() {
		logger.info("Navegando a pantalla de busqueda de programas");
		return ConstantesGestorWeb.NAVEGA_BUSQUEDA_PROGRAMAS_CAPACIT;
	}

	public String navegaCatEncuestas() {
		logger.info("Navegando a modulo de catalogos de encuestas");
		return ConstantesGestorWeb.NAVEGA_CATALOGOS_ENCUESTA;
	}

	public String navegaAdminEncuesta() {
		logger.info("Navegando a modulo de encuestas");
		return ConstantesGestorWeb.NAVEGA_ENCUESTAS;
	}

	public String navegaSeguimientoEncuesta() {
		logger.info("Navegando a modulo de seguimiento encuestas");
		return ConstantesGestorWeb.NAVEGA_SEGUIMIENTO_ENCUESTAS;
	}

	public String navegaEncuestasPendientes() {
		logger.info("Navegando a modulo de encuestas pendientes");
		return ConstantesGestorWeb.NAVEGA_ENCUESTAS_PENDIENTES;
	}

	public String navegaTemas() {
		logger.info("Navegando a modulo de temas");
		inicializaTemas();
		return ConstantesGestorWeb.NAVEGA_TEMAS;
	}

	public String navegaCatalogosGestionEscolar() {
		logger.info("Navegando a catalogos de Gestión Escolar");
		return ConstantesGestorWeb.CATALOGOS_GESTION_ESCOLAR;
	}

	public String navegaCatalogoAsistenciaEscolar() {
		logger.info("Navegando a catalogo de Asistencia Escolar");
		return ConstantesGestorWeb.CATALOGO_ASISTENCIA_ESCOLAR;
	}

	public String navegaNuevoEventoCapacitacion() {
		logger.info("Navegando a nuevo evento de capacitación");
		return ConstantesGestorWeb.NUEVO_EVENTO_CAPACITACION;
	}

	public String navegaDatosGeneralesEC() {
		logger.info("Navegando a datos generales del evento de capacitación");
		return ConstantesGestorWeb.DATOS_GENERALES_EC;
	}

	public String navegaBusquedaEventosCapacitacion() {
		logger.info("Navegando a pantalla de busqueda de eventos capacitacion");
		inicializaBusquedaEventos();
		return ConstantesGestorWeb.NAVEGA_BUSQUEDA_EVENTO_CAP;

	}

	public String navegaAmbientesVirtualesAprendizaje() {
		logger.info("Navegando a pantalla de ambientes virtuales de aprendizaje");
		return ConstantesGestorWeb.NAVEGA_AMBIENTES_VIRTUALES_APRENDIZAJE;
	}

	public String navegaCatsLogisticaInfraestructura() {
		logger.info("Navegando a pantalla de logistica e Infraestructura");
		return ConstantesGestorWeb.NAVEGA_CATS_LOGISTICA_INFRA;
	}

	public String navegaConfiguracionAreas() {
		logger.info("Navegando a configuracion de áreas");
		areasBean.cancelaImagenes();
		areasBean.generaArbolOrgGubernamental();
		return ConstantesGestorWeb.NAVEGA_BUSQ_AREAS_LOGISTICA_INFRA;
	}

	public String navegaAdminSedeInfra() {
		logger.info("Navegando a Administración de Sedes");
		return ConstantesGestorWeb.NAVEGA_CONFIG_SEDES;
	}

	public String navegaReservacionAreas() {
		logger.info("Navegando a reservacion de áreas");
		return ConstantesGestorWeb.NAVEGA_RESERVACION_AREA;
	}

	public String navegaOrganismosGubernamentales() {
		logger.info("Navegando a organismos gubernamentales");
		inicializaOrgGub();
		return ConstantesGestorWeb.NAVEGA_ORG_GUB;
	}

	public String navegaMisCursos() {
		logger.info("Navegando a mis cursos");
		return ConstantesGestorWeb.NAVEGA_MIS_CURSOS;
	}

	//ITTIVA 666
	public String navegaMisConvocatorias() {
		logger.info("Navegando a mis convocatorias");
		return ConstantesGestorWeb.NAVEGA_MIS_CONVOCATORIAS;
	}
	
	//ITTIVA 666
		public String navegaMisInscripciones() {
			logger.info("Navegando a mis dispersiones");
			return ConstantesGestorWeb.NAVEGA_MIS_INSCRIPCIONES;
		}

	public String navegaTareasProgramadas() {
		logger.info("Navegando a tareas programadas");
		inicializaTareasProgramadas();
		return ConstantesGestorWeb.NAVEGA_TAREAS_PROGRAMADAS;
	}

	public String navegaMiPerfil() {
		logger.info("Navegando a mi perfil");
		return ConstantesGestorWeb.NAVEGA_MI_PERFIL_GE;
	}

	public String navegaMisCursosDisponibles() {
		logger.info("Navegando a cursos disponibles");
		return ConstantesGestorWeb.NAVEGA_CURSOS_DISPONIBLES;
	}

	public String navegaMisCursosPrivados() {
		logger.info("Navegando a mis cursos privados");
		inicializaMisCursos();
		return ConstantesGestorWeb.NAVEGA_MIS_CURSOS_PRIVADOS;
	}

	public String navegaMisLogros() {
		logger.info("Navegando a mis logros");
		inicializaMisLogros();
		return ConstantesGestorWeb.NAVEGA_MIS_LOGROS_GE;
	}

	public String navegaConstancias() {
		logger.info("Navegando a constacias");
		inicializaExpedienteAcademico();
		return ConstantesGestorWeb.NAVEGA_CONSTANCIAS;
	}

	public String navegaExpedienteAlumoBuscar() {
		logger.info("Navegando a Expedientes Academicos Busqueda o expediente alumno");
		
//		roles = new ArrayList<>();
//		List<PersonaRolDTO> rolesPersona = personaRolesService
//				.obtieneRelPersonaRolesPorUsuario(getUsuarioEnSession().getUsuario());
//
//		for (PersonaRolDTO personaRol : rolesPersona) {
//			roles.add(personaRol.getRol());
//		}
//		
//		
//		if("Estudiante".equals(roles.get(0).getNombre())) {
//			
//			PersonaDTO persona = personaService.buscarPorId(getUsuarioEnSession().getIdPersona());
//	
//			return expedienteAlumnoBean.navegaExpedienteAlumno2(persona);			
//			
//		}else {
			
			return ConstantesGestorWeb.NAVEGA_BUSCAR_EXPEDIENTE_ALUMNO;
			
		//}

	}

	public String navegaExpedienteGrupo() {
		logger.info("Navegando a expediente de grupo");
		return ConstantesGestorWeb.NAVEGA_EXPEDIENTE_GRUPO;
	}

	public void inicializaMisCursos(){
		bitacoraBean.guardarBitacora(idPersonaEnSesion(), "VER_MIS_CUR", "", requestActual(), TipoServicioEnum.LOCAL);
	}
	
	public void inicializaFuncionalidades() {
		bitacoraBean.guardarBitacora(idPersonaEnSesion(), "VER_ARB_FUN", "", requestActual(), TipoServicioEnum.LOCAL);
	}

	public void inicializaImagenesDocumentos() {
		bitacoraBean.guardarBitacora(idPersonaEnSesion(), "CON_IMG_DOC", "", requestActual(), TipoServicioEnum.LOCAL);
	}

	public void inicializaMisLogros() {
		bitacoraBean.guardarBitacora(idPersonaEnSesion(), "VER_MIS_LOG", "", requestActual(), TipoServicioEnum.LOCAL);
	}

	public void inicializaBusquedaEventos() {
		eventoCapacitacionBean.getFiltros().setCatEstadoEventoCapacitacion(new CatalogoComunDTO());
		eventoCapacitacionBean.setEventosCapacitacion(new ArrayList<>());
	}

	public void inicializaAdminUsuarios() {
		adminPersonaBean.setPersonas(new ArrayList<>());
	}

	public void inicializaTemas() {
		bitacoraBean.guardarBitacora(idPersonaEnSesion(), "CON_TEM", "", requestActual(), TipoServicioEnum.LOCAL);
	}

	public void inicializaParametrosSistema() {
		bitacoraBean.guardarBitacora(idPersonaEnSesion(), "CON_PAR_SIS", "", requestActual(), TipoServicioEnum.LOCAL);
	}

	public void inicializaPlantillasDocumentos() {
		bitacoraBean.guardarBitacora(idPersonaEnSesion(), "BUS_PLA_DOC", "", requestActual(), TipoServicioEnum.LOCAL);
	}

	public void inicializaTareasProgramadas() {
		bitacoraBean.guardarBitacora(idPersonaEnSesion(), "CON_TAR_PRO", "", requestActual(), TipoServicioEnum.LOCAL);
	}

	public void inicializaOrgGub() {
		bitacoraBean.guardarBitacora(idPersonaEnSesion(), "VER_CAT_ORG_GUB", "", requestActual(),
				TipoServicioEnum.LOCAL);
	}

	public void inicializaExpedienteAcademico() {
		bitacoraBean.guardarBitacora(idPersonaEnSesion(), "VER_EXP_ACA", "", requestActual(), TipoServicioEnum.LOCAL);
		
	}

	/* INICIO DE GETS Y SETS */
	public PersonaRolesService getPersonaRolesService() {
		return personaRolesService;
	}

	public void setPersonaRolesService(PersonaRolesService personaRolesService) {
		this.personaRolesService = personaRolesService;
	}

	public List<RolDTO> getRoles() {
		return roles;
	}

	public void setRoles(List<RolDTO> roles) {
		this.roles = roles;
	}

	public Integer getIdRol() {
		return idRol;
	}

	public void setIdRol(Integer idRol) {
		this.idRol = idRol;
	}

	public PersonaService getPersonaService() {
		return personaService;
	}

	public void setPersonaService(PersonaService personaService) {
		this.personaService = personaService;
	}

	public ParametroSistemaService getParametroSistemaService() {
		return parametroSistemaService;
	}

	public void setParametroSistemaService(ParametroSistemaService parametroSistemaService) {
		this.parametroSistemaService = parametroSistemaService;
	}

	public String getRutaFotoUsuario() {
		return rutaFotoUsuario;
	}

	public void setRutaFotoUsuario(String rutaFotoUsuario) {
		this.rutaFotoUsuario = rutaFotoUsuario;
	}

	public ReporteGestionEscolarBean getReporteGestionEscolarBean() {
		return reporteGestionEscolarBean;
	}

	public void setReporteGestionEscolarBean(ReporteGestionEscolarBean reporteGestionEscolarBean) {
		this.reporteGestionEscolarBean = reporteGestionEscolarBean;
	}

	public AreasBean getAreasBean() {
		return areasBean;
	}

	public void setAreasBean(AreasBean areasBean) {
		this.areasBean = areasBean;
	}

	public EventoCapacitacionBean getEventoCapacitacionBean() {
		return eventoCapacitacionBean;
	}

	public void setEventoCapacitacionBean(EventoCapacitacionBean eventoCapacitacionBean) {
		this.eventoCapacitacionBean = eventoCapacitacionBean;
	}

	public AdminPersonaBean getAdminPersonaBean() {
		return adminPersonaBean;
	}

	public void setAdminPersonaBean(AdminPersonaBean adminPersonaBean) {
		this.adminPersonaBean = adminPersonaBean;
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

	public NotificacionesBean getNotificacionesBean() {
		return notificacionesBean;
	}

	public void setNotificacionesBean(NotificacionesBean notificacionesBean) {
		this.notificacionesBean = notificacionesBean;
	}

	public ConstanciasBean getConstanciasBean() {
		return constanciasBean;
	}

	public void setConstanciasBean(ConstanciasBean constanciasBean) {
		this.constanciasBean = constanciasBean;
	}
	
	public boolean isShowLogros() {
		return showLogros;
	}

	public void setShowLogros(boolean showLogros) {
		this.showLogros = showLogros;
	}

	public ExpedienteAlumnoBean getExpedienteAlumnoBean() {
		return expedienteAlumnoBean;
	}

	public void setExpedienteAlumnoBean(ExpedienteAlumnoBean expedienteAlumnoBean) {
		this.expedienteAlumnoBean = expedienteAlumnoBean;
	}

}
