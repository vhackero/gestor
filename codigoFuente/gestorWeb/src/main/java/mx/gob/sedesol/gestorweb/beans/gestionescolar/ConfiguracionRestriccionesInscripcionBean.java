package mx.gob.sedesol.gestorweb.beans.gestionescolar;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.ReglaInscripcionDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.ConfiguracionCargaNuevoIngresoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.ConfiguracionCargaIrregularDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.ConfiguracionCargaRegularDTO;
import mx.gob.sedesol.basegestor.service.gestionescolar.InscripcionService;
import mx.gob.sedesol.gestorweb.beans.acceso.BaseBean;

@ManagedBean
@ViewScoped
public class ConfiguracionRestriccionesInscripcionBean extends BaseBean {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(ConfiguracionRestriccionesInscripcionBean.class);

	@ManagedProperty(value = "#{inscripcionService}")
	private InscripcionService inscripcionService;

	private List<ReglaInscripcionDTO> reglas;
	private ConfiguracionCargaNuevoIngresoDTO configuracionGeneralCargaNuevoIngreso;
	private List<ConfiguracionCargaNuevoIngresoDTO> configuracionesCargaNuevoIngreso;
	private List<ConfiguracionCargaNuevoIngresoDTO> planesDisponiblesCargaNuevoIngreso;
	private Long idPlanNuevaConfiguracion;
	private String usernameNuevaConfiguracion;
	private ConfiguracionCargaRegularDTO configuracionGeneralCargaRegular;
	private List<ConfiguracionCargaRegularDTO> configuracionesCargaRegular;
	private List<ConfiguracionCargaRegularDTO> planesDisponiblesCargaRegular;
	private Long idPlanNuevaConfiguracionRegular;
	private ConfiguracionCargaRegularDTO configuracionRestriccionesAcademicasGenerales;

	private ConfiguracionCargaIrregularDTO configuracionGeneralCargaIrregular;
	private List<ConfiguracionCargaIrregularDTO> configuracionesCargaIrregular;
	private List<ConfiguracionCargaIrregularDTO> planesDisponiblesCargaIrregular;
	private Long idPlanNuevaConfiguracionIrregular;

	@PostConstruct
	public void init() {
		consultarReglas();
	}

	private void consultarReglas() {
		reglas = new ArrayList<>();
		configuracionGeneralCargaNuevoIngreso = new ConfiguracionCargaNuevoIngresoDTO();
		configuracionesCargaNuevoIngreso = new ArrayList<>();
		planesDisponiblesCargaNuevoIngreso = new ArrayList<>();
		configuracionGeneralCargaRegular = new ConfiguracionCargaRegularDTO();
		configuracionesCargaRegular = new ArrayList<>();
		planesDisponiblesCargaRegular = new ArrayList<>();
		configuracionGeneralCargaIrregular = new ConfiguracionCargaIrregularDTO();
		configuracionesCargaIrregular = new ArrayList<>();
		planesDisponiblesCargaIrregular = new ArrayList<>();
		configuracionRestriccionesAcademicasGenerales = new ConfiguracionCargaRegularDTO();
		try {
			reglas = inscripcionService.obtenerReglasInscripcion();
			configuracionGeneralCargaNuevoIngreso = inscripcionService.obtenerConfiguracionGeneralCargaNuevoIngreso();
			configuracionesCargaNuevoIngreso = inscripcionService.obtenerConfiguracionesCargaNuevoIngreso();
			planesDisponiblesCargaNuevoIngreso = inscripcionService.obtenerPlanesDisponiblesCargaNuevoIngreso();
			configuracionGeneralCargaRegular = inscripcionService.obtenerConfiguracionGeneralCargaRegular();
			configuracionesCargaRegular = inscripcionService.obtenerConfiguracionesCargaRegular();
			planesDisponiblesCargaRegular = inscripcionService.obtenerPlanesDisponiblesCargaRegular();
			configuracionGeneralCargaIrregular = inscripcionService.obtenerConfiguracionGeneralCargaIrregular();
			configuracionesCargaIrregular = inscripcionService.obtenerConfiguracionesCargaIrregular();
			planesDisponiblesCargaIrregular = inscripcionService.obtenerPlanesDisponiblesCargaIrregular();
			configuracionRestriccionesAcademicasGenerales = inscripcionService
					.obtenerConfiguracionRestriccionesAcademicasGenerales();
		} catch (Exception e) {
			logger.error("No fue posible consultar las reglas de inscripción", e);
			mensajeError("No fue posible consultar las reglas de inscripción", e);
		}
	}

	public void guardarCargaRegularGeneral() {
		try {
			inscripcionService.guardarConfiguracionGeneralCargaRegular(configuracionGeneralCargaRegular,
					getUsuarioEnSession().getIdPersona());
			consultarReglas();
			mensaje(FacesMessage.SEVERITY_INFO, "Configuración guardada",
					"La carga académica general para estudiantes regulares fue actualizada.");
		} catch (Exception e) {
			logger.error("No fue posible guardar la carga regular general", e);
			mensajeError("No fue posible guardar la carga regular general", e);
		}
	}

	public void agregarCargaRegular() {
		if (idPlanNuevaConfiguracionRegular == null) {
			mensaje(FacesMessage.SEVERITY_WARN, "Plan requerido", "Selecciona el plan al que se aplicará la regla.");
			return;
		}
		try {
			ConfiguracionCargaRegularDTO configuracion = inscripcionService
					.obtenerConfiguracionCargaRegular(idPlanNuevaConfiguracionRegular);
			configuracion.setActiva(Boolean.TRUE);
			inscripcionService.guardarConfiguracionCargaRegular(configuracion, getUsuarioEnSession().getIdPersona());
			idPlanNuevaConfiguracionRegular = null;
			consultarReglas();
			mensaje(FacesMessage.SEVERITY_INFO, "Regla agregada", "La configuración regular fue creada para el plan.");
		} catch (Exception e) {
			logger.error("No fue posible agregar la carga regular", e);
			mensajeError("No fue posible agregar la carga regular", e);
		}
	}

	public void guardarCargaRegular(ConfiguracionCargaRegularDTO configuracion) {
		try {
			inscripcionService.guardarConfiguracionCargaRegular(configuracion, getUsuarioEnSession().getIdPersona());
			consultarReglas();
			mensaje(FacesMessage.SEVERITY_INFO, "Configuración guardada", "La carga regular del plan fue actualizada.");
		} catch (Exception e) {
			logger.error("No fue posible guardar la carga regular", e);
			mensajeError("No fue posible guardar la carga regular", e);
		}
	}

	public void eliminarCargaRegular(ConfiguracionCargaRegularDTO configuracion) {
		try {
			inscripcionService.eliminarConfiguracionCargaRegular(configuracion);
			consultarReglas();
			mensaje(FacesMessage.SEVERITY_INFO, "Configuración eliminada",
					"El plan vuelve a utilizar la regla general para estudiantes regulares.");
		} catch (Exception e) {
			logger.error("No fue posible eliminar la carga regular", e);
			mensajeError("No fue posible eliminar la carga regular", e);
		}
	}

	public void guardarCargaIrregularGeneral() {
		try {
			inscripcionService.guardarConfiguracionGeneralCargaIrregular(configuracionGeneralCargaIrregular,
					getUsuarioEnSession().getIdPersona());
			consultarReglas();
			mensaje(FacesMessage.SEVERITY_INFO, "Configuración guardada",
					"La carga académica general para estudiantes irregulares fue actualizada.");
		} catch (Exception e) {
			logger.error("No fue posible guardar la carga irregular general", e);
			mensajeError("No fue posible guardar la carga irregular general", e);
		}
	}

	public void agregarCargaIrregular() {
		if (idPlanNuevaConfiguracionIrregular == null) {
			mensaje(FacesMessage.SEVERITY_WARN, "Plan requerido", "Selecciona el plan al que se aplicará la regla.");
			return;
		}
		if (configuracionesCargaIrregular.stream()
				.anyMatch(configuracion -> idPlanNuevaConfiguracionIrregular.equals(configuracion.getIdPlan()))) {
			mensaje(FacesMessage.SEVERITY_WARN, "Regla existente", "El plan ya tiene una configuración irregular. Edita esa regla.");
			return;
		}
		try {
			ConfiguracionCargaIrregularDTO configuracion = inscripcionService
					.obtenerConfiguracionCargaIrregular(idPlanNuevaConfiguracionIrregular);
			configuracion.setActiva(Boolean.TRUE);
			inscripcionService.guardarConfiguracionCargaIrregular(configuracion, getUsuarioEnSession().getIdPersona());
			idPlanNuevaConfiguracionIrregular = null;
			consultarReglas();
			mensaje(FacesMessage.SEVERITY_INFO, "Regla agregada", "La configuración irregular fue creada para el plan.");
		} catch (Exception e) {
			logger.error("No fue posible agregar la carga irregular", e);
			mensajeError("No fue posible agregar la carga irregular", e);
		}
	}

	public void guardarCargaIrregular(ConfiguracionCargaIrregularDTO configuracion) {
		try {
			inscripcionService.guardarConfiguracionCargaIrregular(configuracion, getUsuarioEnSession().getIdPersona());
			consultarReglas();
			mensaje(FacesMessage.SEVERITY_INFO, "Configuración guardada", "La carga irregular del plan fue actualizada.");
		} catch (Exception e) {
			logger.error("No fue posible guardar la carga irregular", e);
			mensajeError("No fue posible guardar la carga irregular", e);
		}
	}

	public void eliminarCargaIrregular(ConfiguracionCargaIrregularDTO configuracion) {
		try {
			inscripcionService.eliminarConfiguracionCargaIrregular(configuracion);
			consultarReglas();
			mensaje(FacesMessage.SEVERITY_INFO, "Configuración eliminada",
					"El plan vuelve a utilizar la regla general para estudiantes irregulares.");
		} catch (Exception e) {
			logger.error("No fue posible eliminar la carga irregular", e);
			mensajeError("No fue posible eliminar la carga irregular", e);
		}
	}

	public void guardarRestriccionesAcademicasGenerales() {
		try {
			inscripcionService.guardarConfiguracionRestriccionesAcademicasGenerales(
					configuracionRestriccionesAcademicasGenerales, getUsuarioEnSession().getIdPersona());
			consultarReglas();
			mensaje(FacesMessage.SEVERITY_INFO, "Configuración guardada",
					"Las restricciones académicas generales fueron actualizadas.");
		} catch (Exception e) {
			logger.error("No fue posible guardar las restricciones académicas generales", e);
			mensajeError("No fue posible guardar las restricciones académicas generales", e);
		}
	}

	public void guardarCargaNuevoIngresoGeneral() {
		try {
			inscripcionService.guardarConfiguracionGeneralCargaNuevoIngreso(configuracionGeneralCargaNuevoIngreso,
					getUsuarioEnSession().getIdPersona());
			consultarReglas();
			mensaje(FacesMessage.SEVERITY_INFO, "Configuración guardada",
					"La regla general de nuevo ingreso fue actualizada correctamente.");
		} catch (Exception e) {
			logger.error("No fue posible guardar la regla general de nuevo ingreso", e);
			mensajeError("No fue posible guardar la regla general de nuevo ingreso", e);
		}
	}

	public void agregarCargaNuevoIngreso() {
		if (idPlanNuevaConfiguracion == null) {
			mensaje(FacesMessage.SEVERITY_WARN, "Plan requerido",
					"Selecciona el plan al que se aplicará la regla.");
			return;
		}
		String username = usernameNuevaConfiguracion == null ? null : usernameNuevaConfiguracion.trim();
		if (username != null && username.isEmpty()) {
			username = null;
		}
		if (existeConfiguracionEspecifica(idPlanNuevaConfiguracion, username)) {
			mensaje(FacesMessage.SEVERITY_WARN, "Regla existente",
					username == null
							? "El plan seleccionado ya tiene una regla específica para todos sus usuarios. Puedes modificarla en la tabla."
							: "El username indicado ya tiene una regla específica para el plan seleccionado. Puedes modificarla en la tabla.");
			return;
		}
		try {
			ConfiguracionCargaNuevoIngresoDTO configuracion = inscripcionService
					.obtenerConfiguracionCargaNuevoIngreso(idPlanNuevaConfiguracion, null);
			configuracion.setActiva(Boolean.TRUE);
			configuracion.setUsername(username);
			inscripcionService.guardarConfiguracionCargaNuevoIngreso(configuracion,
					getUsuarioEnSession().getIdPersona());
			idPlanNuevaConfiguracion = null;
			usernameNuevaConfiguracion = null;
			consultarReglas();
			mensaje(FacesMessage.SEVERITY_INFO, "Regla agregada",
					configuracion.getUsername() == null || configuracion.getUsername().isEmpty()
							? "La configuración fue creada para todos los usuarios del plan seleccionado."
							: "La configuración fue creada para el username indicado dentro del plan.");
		} catch (Exception e) {
			logger.error("No fue posible agregar la regla de nuevo ingreso para el plan " + idPlanNuevaConfiguracion, e);
			mensajeError("No fue posible agregar la regla para el plan", e);
		}
	}

	private boolean existeConfiguracionEspecifica(Long idPlan, String username) {
		if (configuracionesCargaNuevoIngreso == null) {
			return false;
		}
		for (ConfiguracionCargaNuevoIngresoDTO configuracion : configuracionesCargaNuevoIngreso) {
			if (!idPlan.equals(configuracion.getIdPlan())) {
				continue;
			}
			String usernameConfigurado = configuracion.getUsername() == null
					? null : configuracion.getUsername().trim();
			if (username == null) {
				if (usernameConfigurado == null || usernameConfigurado.isEmpty()) {
					return true;
				}
			} else if (usernameConfigurado != null && username.equalsIgnoreCase(usernameConfigurado)) {
				return true;
			}
		}
		return false;
	}

	public void guardarCargaNuevoIngreso(ConfiguracionCargaNuevoIngresoDTO configuracion) {
		try {
			inscripcionService.guardarConfiguracionCargaNuevoIngreso(configuracion,
					getUsuarioEnSession().getIdPersona());
			consultarReglas();
			mensaje(FacesMessage.SEVERITY_INFO, "Configuración guardada",
					"La carga de nuevo ingreso fue actualizada para el plan seleccionado.");
		} catch (Exception e) {
			logger.error("No fue posible guardar la carga de nuevo ingreso del plan " + configuracion.getIdPlan(), e);
			mensajeError("No fue posible guardar la configuración de nuevo ingreso", e);
		}
	}

	public void eliminarCargaNuevoIngreso(ConfiguracionCargaNuevoIngresoDTO configuracion) {
		try {
			inscripcionService.eliminarConfiguracionCargaNuevoIngreso(configuracion);
			consultarReglas();
			mensaje(FacesMessage.SEVERITY_INFO, "Configuración eliminada",
					configuracion.getIdPersona() == null
							? "El plan vuelve a utilizar la regla general de nuevo ingreso."
							: "El usuario vuelve a utilizar la configuración del plan o, si no existe, la regla general.");
		} catch (Exception e) {
			logger.error("No fue posible eliminar la carga de nuevo ingreso del plan " + configuracion.getIdPlan(), e);
			mensajeError("No fue posible eliminar la configuración específica del plan", e);
		}
	}

	public void guardar(ReglaInscripcionDTO regla) {
		try {
			inscripcionService.guardarReglaInscripcion(regla, getUsuarioEnSession().getIdPersona());
			consultarReglas();
			mensaje(FacesMessage.SEVERITY_INFO, "Configuración guardada",
					"La regla de inscripción fue actualizada correctamente.");
		} catch (Exception e) {
			logger.error("No fue posible guardar la regla de inscripción " + regla.getClave(), e);
			mensajeError("No fue posible guardar la regla de inscripción", e);
		}
	}

	private void mensaje(FacesMessage.Severity severity, String titulo, String detalle) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, titulo, detalle));
	}

	private void mensajeError(String contexto, Throwable error) {
		mensaje(FacesMessage.SEVERITY_ERROR, "Error", contexto + ": " + obtenerCausaError(error));
	}

	private String obtenerCausaError(Throwable error) {
		Throwable causa = error;
		while (causa.getCause() != null && causa.getCause() != causa) {
			causa = causa.getCause();
		}
		String detalle = error.getMessage();
		String causaRaiz = causa.getMessage();
		if (detalle == null || detalle.trim().isEmpty()) {
			detalle = error.getClass().getSimpleName();
		}
		return causa != error && causaRaiz != null && !causaRaiz.trim().isEmpty()
				&& !causaRaiz.equals(detalle) ? detalle + " (causa: " + causaRaiz + ")" : detalle;
	}

	public InscripcionService getInscripcionService() { return inscripcionService; }
	public void setInscripcionService(InscripcionService inscripcionService) { this.inscripcionService = inscripcionService; }
	public List<ReglaInscripcionDTO> getReglas() { return reglas; }
	public void setReglas(List<ReglaInscripcionDTO> reglas) { this.reglas = reglas; }
	public List<ConfiguracionCargaNuevoIngresoDTO> getConfiguracionesCargaNuevoIngreso() { return configuracionesCargaNuevoIngreso; }
	public void setConfiguracionesCargaNuevoIngreso(List<ConfiguracionCargaNuevoIngresoDTO> configuraciones) { this.configuracionesCargaNuevoIngreso = configuraciones; }
	public String getNombreReglaCargaNuevoIngreso() {
		ConfiguracionCargaNuevoIngresoDTO referencia = obtenerReferenciaCargaNuevoIngreso();
		return referencia == null ? "Nuevo ingreso" : referencia.getNombreRegla();
	}
	public String getDescripcionReglaCargaNuevoIngreso() {
		ConfiguracionCargaNuevoIngresoDTO referencia = obtenerReferenciaCargaNuevoIngreso();
		return referencia == null ? "" : referencia.getDescripcionRegla();
	}
	private ConfiguracionCargaNuevoIngresoDTO obtenerReferenciaCargaNuevoIngreso() {
		if (configuracionGeneralCargaNuevoIngreso != null) {
			return configuracionGeneralCargaNuevoIngreso;
		}
		if (configuracionesCargaNuevoIngreso != null && !configuracionesCargaNuevoIngreso.isEmpty()) {
			return configuracionesCargaNuevoIngreso.get(0);
		}
		return planesDisponiblesCargaNuevoIngreso == null || planesDisponiblesCargaNuevoIngreso.isEmpty()
				? null : planesDisponiblesCargaNuevoIngreso.get(0);
	}
	public List<ConfiguracionCargaNuevoIngresoDTO> getPlanesDisponiblesCargaNuevoIngreso() { return planesDisponiblesCargaNuevoIngreso; }
	public void setPlanesDisponiblesCargaNuevoIngreso(List<ConfiguracionCargaNuevoIngresoDTO> planes) { this.planesDisponiblesCargaNuevoIngreso = planes; }
	public Long getIdPlanNuevaConfiguracion() { return idPlanNuevaConfiguracion; }
	public void setIdPlanNuevaConfiguracion(Long idPlanNuevaConfiguracion) { this.idPlanNuevaConfiguracion = idPlanNuevaConfiguracion; }
	public String getUsernameNuevaConfiguracion() { return usernameNuevaConfiguracion; }
	public void setUsernameNuevaConfiguracion(String username) { this.usernameNuevaConfiguracion = username; }
	public ConfiguracionCargaNuevoIngresoDTO getConfiguracionGeneralCargaNuevoIngreso() { return configuracionGeneralCargaNuevoIngreso; }
	public void setConfiguracionGeneralCargaNuevoIngreso(ConfiguracionCargaNuevoIngresoDTO configuracion) { this.configuracionGeneralCargaNuevoIngreso = configuracion; }
	public ConfiguracionCargaRegularDTO getConfiguracionGeneralCargaRegular() { return configuracionGeneralCargaRegular; }
	public void setConfiguracionGeneralCargaRegular(ConfiguracionCargaRegularDTO valor) { this.configuracionGeneralCargaRegular = valor; }
	public List<ConfiguracionCargaRegularDTO> getConfiguracionesCargaRegular() { return configuracionesCargaRegular; }
	public void setConfiguracionesCargaRegular(List<ConfiguracionCargaRegularDTO> valor) { this.configuracionesCargaRegular = valor; }
	public List<ConfiguracionCargaRegularDTO> getPlanesDisponiblesCargaRegular() { return planesDisponiblesCargaRegular; }
	public void setPlanesDisponiblesCargaRegular(List<ConfiguracionCargaRegularDTO> valor) { this.planesDisponiblesCargaRegular = valor; }
	public Long getIdPlanNuevaConfiguracionRegular() { return idPlanNuevaConfiguracionRegular; }
	public void setIdPlanNuevaConfiguracionRegular(Long valor) { this.idPlanNuevaConfiguracionRegular = valor; }
	public ConfiguracionCargaIrregularDTO getConfiguracionGeneralCargaIrregular() { return configuracionGeneralCargaIrregular; }
	public void setConfiguracionGeneralCargaIrregular(ConfiguracionCargaIrregularDTO valor) { this.configuracionGeneralCargaIrregular = valor; }
	public List<ConfiguracionCargaIrregularDTO> getConfiguracionesCargaIrregular() { return configuracionesCargaIrregular; }
	public void setConfiguracionesCargaIrregular(List<ConfiguracionCargaIrregularDTO> valor) { this.configuracionesCargaIrregular = valor; }
	public List<ConfiguracionCargaIrregularDTO> getPlanesDisponiblesCargaIrregular() { return planesDisponiblesCargaIrregular; }
	public void setPlanesDisponiblesCargaIrregular(List<ConfiguracionCargaIrregularDTO> valor) { this.planesDisponiblesCargaIrregular = valor; }
	public Long getIdPlanNuevaConfiguracionIrregular() { return idPlanNuevaConfiguracionIrregular; }
	public void setIdPlanNuevaConfiguracionIrregular(Long valor) { this.idPlanNuevaConfiguracionIrregular = valor; }
	public ConfiguracionCargaRegularDTO getConfiguracionRestriccionesAcademicasGenerales() { return configuracionRestriccionesAcademicasGenerales; }
	public void setConfiguracionRestriccionesAcademicasGenerales(ConfiguracionCargaRegularDTO valor) { this.configuracionRestriccionesAcademicasGenerales = valor; }
}
