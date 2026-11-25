package mx.gob.sedesol.gestorweb.beans.gestionescolar;

import mx.gob.sedesol.basegestor.service.admin.TextoSistemaService;
import java.util.ArrayList;
import java.util.List;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.ConfiguracionOmisionReinscripcionDTO;

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
    @javax.faces.bean.ManagedProperty(value = "#{textoSistemaService}")
    private transient TextoSistemaService textosInscripcion;

    public void setTextosInscripcion(TextoSistemaService textosInscripcion) {
        this.textosInscripcion = textosInscripcion;
    }


	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(ConfiguracionRestriccionesInscripcionBean.class);

	@ManagedProperty(value = "#{inscripcionService}")
	private InscripcionService inscripcionService;

	    private ConfiguracionOmisionReinscripcionDTO configuracionOmisionReinscripcion;
    public ConfiguracionOmisionReinscripcionDTO getConfiguracionOmisionReinscripcion() { return configuracionOmisionReinscripcion; }
    public void guardarOmisionReinscripcion() {
        try {
            inscripcionService.guardarConfiguracionOmisionReinscripcion(configuracionOmisionReinscripcion, getUsuarioEnSession().getIdPersona());
            consultarReglas();
            mensaje(FacesMessage.SEVERITY_INFO, textosInscripcion.obtenerTexto("gw.inscripcion.mensaje.configuracion.guardada"), textosInscripcion.obtenerTexto("gw.inscripcion.mensaje.la.regla.de.periodos.sin.reinscripcion.fue.actualizada"));
        } catch (Exception e) {
            logger.error("No fue posible guardar la regla de omisiones", e);
            mensajeError(textosInscripcion.obtenerTexto("gw.inscripcion.mensaje.no.fue.posible.guardar.la.regla.de.omisiones"), e);
        }
    }
    private String[] configuracionBajasInscripcion;
    public String[] getConfiguracionBajasInscripcion() { return configuracionBajasInscripcion; }
    public List<String> getTiposBajaConUd() { return tiposBajaSeleccionados(1); }
    public void setTiposBajaConUd(List<String> tipos) { configuracionBajasInscripcion[1]=tipos==null?"":String.join(",",tipos); }
    public List<String> getTiposBajaSinUd() { return tiposBajaSeleccionados(2); }
    public void setTiposBajaSinUd(List<String> tipos) { configuracionBajasInscripcion[2]=tipos==null?"":String.join(",",tipos); }
    private List<String> tiposBajaSeleccionados(int indice) {
        if(configuracionBajasInscripcion==null || configuracionBajasInscripcion[indice]==null || configuracionBajasInscripcion[indice].isEmpty())return new ArrayList<>();
        return new ArrayList<>(java.util.Arrays.asList(configuracionBajasInscripcion[indice].split(",")));
    }
    public void guardarBajasInscripcion() {
        try {
            inscripcionService.guardarConfiguracionBajasInscripcion(configuracionBajasInscripcion,getUsuarioEnSession().getIdPersona());
            consultarReglas();
            mensaje(FacesMessage.SEVERITY_INFO,textosInscripcion.obtenerTexto("gw.inscripcion.mensaje.configuracion.guardada"),textosInscripcion.obtenerTexto("gw.inscripcion.mensaje.el.tratamiento.de.bajas.en.inscripcion.fue.actualizado"));
        } catch(Exception e) {
            logger.error("No fue posible guardar el tratamiento de bajas",e);
            mensajeError(textosInscripcion.obtenerTexto("gw.inscripcion.mensaje.no.fue.posible.guardar.el.tratamiento.de.bajas"),e);
        }
    }
    private List<ReglaInscripcionDTO> reglas;
    private mx.gob.sedesol.basegestor.commons.dto.gestionescolar.ConfiguracionOportunidadesDTO configuracionOportunidades;
    public mx.gob.sedesol.basegestor.commons.dto.gestionescolar.ConfiguracionOportunidadesDTO getConfiguracionOportunidades() { return configuracionOportunidades; }
    public void guardarOportunidades() {
        try {
            inscripcionService.guardarConfiguracionOportunidades(configuracionOportunidades,getUsuarioEnSession().getIdPersona());
            consultarReglas();mensaje(FacesMessage.SEVERITY_INFO,textosInscripcion.obtenerTexto("gw.inscripcion.mensaje.configuracion.guardada"),textosInscripcion.obtenerTexto("gw.inscripcion.mensaje.limite.y.mensajes.de.oportunidades.actualizados"));
        } catch(Exception e) { logger.error("No fue posible guardar oportunidades",e);mensajeError(textosInscripcion.obtenerTexto("gw.inscripcion.mensaje.no.fue.posible.guardar.oportunidades"),e); }
    }
    private List<ReglaInscripcionDTO> configuracionesConclusion = new ArrayList<>();
    public List<ReglaInscripcionDTO> getConfiguracionesConclusion() { return configuracionesConclusion; }
    public void guardarConclusion(ReglaInscripcionDTO configuracion) {
        try {
            inscripcionService.guardarConfiguracionConclusion(configuracion,getUsuarioEnSession().getIdPersona());
            consultarReglas();
            mensaje(FacesMessage.SEVERITY_INFO,textosInscripcion.obtenerTexto("gw.inscripcion.mensaje.configuracion.guardada"),textosInscripcion.obtenerTexto("gw.inscripcion.mensaje.la.validacion.de.conclusion.del.plan.fue.actualizada"));
        } catch(Exception e) {
            logger.error("No fue posible guardar la validación de conclusión",e);
            mensajeError(textosInscripcion.obtenerTexto("gw.inscripcion.mensaje.no.fue.posible.guardar.la.validacion.de.conclusion"),e);
        }
    }
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
        configuracionesConclusion = new ArrayList<>();
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
            reglas.removeIf(r->"PERMITE_INSCRIPCION_LIMITE_REPROBADAS".equals(r.getClave()));
            configuracionOportunidades=inscripcionService.obtenerConfiguracionOportunidades();
            configuracionesConclusion = inscripcionService.obtenerConfiguracionesConclusion();
            configuracionBajasInscripcion = inscripcionService.obtenerConfiguracionBajasInscripcion();
            configuracionOmisionReinscripcion = inscripcionService.obtenerConfiguracionOmisionReinscripcion();
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
			mensajeError(textosInscripcion.obtenerTexto("gw.inscripcion.mensaje.no.fue.posible.consultar.las.reglas.de.inscripcion"), e);
		}
	}

	public void guardarCargaRegularGeneral() {
		try {
			inscripcionService.guardarConfiguracionGeneralCargaRegular(configuracionGeneralCargaRegular,
					getUsuarioEnSession().getIdPersona());
			consultarReglas();
			mensaje(FacesMessage.SEVERITY_INFO, textosInscripcion.obtenerTexto("gw.inscripcion.mensaje.configuracion.guardada"),
					textosInscripcion.obtenerTexto("gw.inscripcion.mensaje.la.carga.academica.general.para.estudiantes.regulares.fue"));
		} catch (Exception e) {
			logger.error("No fue posible guardar la carga regular general", e);
			mensajeError(textosInscripcion.obtenerTexto("gw.inscripcion.mensaje.no.fue.posible.guardar.la.carga.regular.general"), e);
		}
	}

	public void agregarCargaRegular() {
		if (idPlanNuevaConfiguracionRegular == null) {
			mensaje(FacesMessage.SEVERITY_WARN, textosInscripcion.obtenerTexto("gw.inscripcion.mensaje.plan.requerido"), textosInscripcion.obtenerTexto("gw.inscripcion.mensaje.selecciona.el.plan.al.que.se.aplicara.la"));
			return;
		}
		try {
			ConfiguracionCargaRegularDTO configuracion = inscripcionService
					.obtenerConfiguracionCargaRegular(idPlanNuevaConfiguracionRegular);
			configuracion.setActiva(Boolean.TRUE);
			inscripcionService.guardarConfiguracionCargaRegular(configuracion, getUsuarioEnSession().getIdPersona());
			idPlanNuevaConfiguracionRegular = null;
			consultarReglas();
			mensaje(FacesMessage.SEVERITY_INFO, textosInscripcion.obtenerTexto("gw.inscripcion.mensaje.regla.agregada"), textosInscripcion.obtenerTexto("gw.inscripcion.mensaje.la.configuracion.regular.fue.creada.para.el.plan"));
		} catch (Exception e) {
			logger.error("No fue posible agregar la carga regular", e);
			mensajeError(textosInscripcion.obtenerTexto("gw.inscripcion.mensaje.no.fue.posible.agregar.la.carga.regular"), e);
		}
	}

	public void guardarCargaRegular(ConfiguracionCargaRegularDTO configuracion) {
		try {
			inscripcionService.guardarConfiguracionCargaRegular(configuracion, getUsuarioEnSession().getIdPersona());
			consultarReglas();
			mensaje(FacesMessage.SEVERITY_INFO, textosInscripcion.obtenerTexto("gw.inscripcion.mensaje.configuracion.guardada"), textosInscripcion.obtenerTexto("gw.inscripcion.mensaje.la.carga.regular.del.plan.fue.actualizada"));
		} catch (Exception e) {
			logger.error("No fue posible guardar la carga regular", e);
			mensajeError(textosInscripcion.obtenerTexto("gw.inscripcion.mensaje.no.fue.posible.guardar.la.carga.regular"), e);
		}
	}

	public void eliminarCargaRegular(ConfiguracionCargaRegularDTO configuracion) {
		try {
			inscripcionService.eliminarConfiguracionCargaRegular(configuracion);
			consultarReglas();
			mensaje(FacesMessage.SEVERITY_INFO, textosInscripcion.obtenerTexto("gw.inscripcion.mensaje.configuracion.eliminada"),
					textosInscripcion.obtenerTexto("gw.inscripcion.mensaje.el.plan.vuelve.a.utilizar.la.regla.general"));
		} catch (Exception e) {
			logger.error("No fue posible eliminar la carga regular", e);
			mensajeError(textosInscripcion.obtenerTexto("gw.inscripcion.mensaje.no.fue.posible.eliminar.la.carga.regular"), e);
		}
	}

	public void guardarCargaIrregularGeneral() {
		try {
			inscripcionService.guardarConfiguracionGeneralCargaIrregular(configuracionGeneralCargaIrregular,
					getUsuarioEnSession().getIdPersona());
			consultarReglas();
			mensaje(FacesMessage.SEVERITY_INFO, textosInscripcion.obtenerTexto("gw.inscripcion.mensaje.configuracion.guardada"),
					textosInscripcion.obtenerTexto("gw.inscripcion.mensaje.la.carga.academica.general.para.estudiantes.irregulares.fue"));
		} catch (Exception e) {
			logger.error("No fue posible guardar la carga irregular general", e);
			mensajeError(textosInscripcion.obtenerTexto("gw.inscripcion.mensaje.no.fue.posible.guardar.la.carga.irregular.general"), e);
		}
	}

	public void agregarCargaIrregular() {
		if (idPlanNuevaConfiguracionIrregular == null) {
			mensaje(FacesMessage.SEVERITY_WARN, textosInscripcion.obtenerTexto("gw.inscripcion.mensaje.plan.requerido"), textosInscripcion.obtenerTexto("gw.inscripcion.mensaje.selecciona.el.plan.al.que.se.aplicara.la"));
			return;
		}
		if (configuracionesCargaIrregular.stream()
				.anyMatch(configuracion -> idPlanNuevaConfiguracionIrregular.equals(configuracion.getIdPlan()))) {
			mensaje(FacesMessage.SEVERITY_WARN, textosInscripcion.obtenerTexto("gw.inscripcion.mensaje.regla.existente"), textosInscripcion.obtenerTexto("gw.inscripcion.mensaje.el.plan.ya.tiene.una.configuracion.irregular.edita"));
			return;
		}
		try {
			ConfiguracionCargaIrregularDTO configuracion = inscripcionService
					.obtenerConfiguracionCargaIrregular(idPlanNuevaConfiguracionIrregular);
			configuracion.setActiva(Boolean.TRUE);
			inscripcionService.guardarConfiguracionCargaIrregular(configuracion, getUsuarioEnSession().getIdPersona());
			idPlanNuevaConfiguracionIrregular = null;
			consultarReglas();
			mensaje(FacesMessage.SEVERITY_INFO, textosInscripcion.obtenerTexto("gw.inscripcion.mensaje.regla.agregada"), textosInscripcion.obtenerTexto("gw.inscripcion.mensaje.la.configuracion.irregular.fue.creada.para.el.plan"));
		} catch (Exception e) {
			logger.error("No fue posible agregar la carga irregular", e);
			mensajeError(textosInscripcion.obtenerTexto("gw.inscripcion.mensaje.no.fue.posible.agregar.la.carga.irregular"), e);
		}
	}

	public void guardarCargaIrregular(ConfiguracionCargaIrregularDTO configuracion) {
		try {
			inscripcionService.guardarConfiguracionCargaIrregular(configuracion, getUsuarioEnSession().getIdPersona());
			consultarReglas();
			mensaje(FacesMessage.SEVERITY_INFO, textosInscripcion.obtenerTexto("gw.inscripcion.mensaje.configuracion.guardada"), textosInscripcion.obtenerTexto("gw.inscripcion.mensaje.la.carga.irregular.del.plan.fue.actualizada"));
		} catch (Exception e) {
			logger.error("No fue posible guardar la carga irregular", e);
			mensajeError(textosInscripcion.obtenerTexto("gw.inscripcion.mensaje.no.fue.posible.guardar.la.carga.irregular"), e);
		}
	}

	public void eliminarCargaIrregular(ConfiguracionCargaIrregularDTO configuracion) {
		try {
			inscripcionService.eliminarConfiguracionCargaIrregular(configuracion);
			consultarReglas();
			mensaje(FacesMessage.SEVERITY_INFO, textosInscripcion.obtenerTexto("gw.inscripcion.mensaje.configuracion.eliminada"),
					textosInscripcion.obtenerTexto("gw.inscripcion.mensaje.el.plan.vuelve.a.utilizar.la.regla.general.2"));
		} catch (Exception e) {
			logger.error("No fue posible eliminar la carga irregular", e);
			mensajeError(textosInscripcion.obtenerTexto("gw.inscripcion.mensaje.no.fue.posible.eliminar.la.carga.irregular"), e);
		}
	}

	public void guardarRestriccionesAcademicasGenerales() {
		try {
			inscripcionService.guardarConfiguracionRestriccionesAcademicasGenerales(
					configuracionRestriccionesAcademicasGenerales, getUsuarioEnSession().getIdPersona());
			consultarReglas();
			mensaje(FacesMessage.SEVERITY_INFO, textosInscripcion.obtenerTexto("gw.inscripcion.mensaje.configuracion.guardada"),
					textosInscripcion.obtenerTexto("gw.inscripcion.mensaje.las.restricciones.academicas.generales.fueron.actualizadas"));
		} catch (Exception e) {
			logger.error("No fue posible guardar las restricciones académicas generales", e);
			mensajeError(textosInscripcion.obtenerTexto("gw.inscripcion.mensaje.no.fue.posible.guardar.las.restricciones.academicas.generales"), e);
		}
	}

	public void guardarCargaNuevoIngresoGeneral() {
		try {
			inscripcionService.guardarConfiguracionGeneralCargaNuevoIngreso(configuracionGeneralCargaNuevoIngreso,
					getUsuarioEnSession().getIdPersona());
			consultarReglas();
			mensaje(FacesMessage.SEVERITY_INFO, textosInscripcion.obtenerTexto("gw.inscripcion.mensaje.configuracion.guardada"),
					textosInscripcion.obtenerTexto("gw.inscripcion.mensaje.la.regla.general.de.nuevo.ingreso.fue.actualizada"));
		} catch (Exception e) {
			logger.error("No fue posible guardar la regla general de nuevo ingreso", e);
			mensajeError(textosInscripcion.obtenerTexto("gw.inscripcion.mensaje.no.fue.posible.guardar.la.regla.general.de"), e);
		}
	}

	public void agregarCargaNuevoIngreso() {
		if (idPlanNuevaConfiguracion == null) {
			mensaje(FacesMessage.SEVERITY_WARN, textosInscripcion.obtenerTexto("gw.inscripcion.mensaje.plan.requerido"),
					textosInscripcion.obtenerTexto("gw.inscripcion.mensaje.selecciona.el.plan.al.que.se.aplicara.la"));
			return;
		}
		String username = usernameNuevaConfiguracion == null ? null : usernameNuevaConfiguracion.trim();
		if (username != null && username.isEmpty()) {
			username = null;
		}
		if (existeConfiguracionEspecifica(idPlanNuevaConfiguracion, username)) {
			mensaje(FacesMessage.SEVERITY_WARN, textosInscripcion.obtenerTexto("gw.inscripcion.mensaje.regla.existente"),
					username == null
							? textosInscripcion.obtenerTexto("gw.inscripcion.mensaje.el.plan.seleccionado.ya.tiene.una.regla.especifica")
							: textosInscripcion.obtenerTexto("gw.inscripcion.mensaje.el.username.indicado.ya.tiene.una.regla.especifica"));
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
			mensaje(FacesMessage.SEVERITY_INFO, textosInscripcion.obtenerTexto("gw.inscripcion.mensaje.regla.agregada"),
					configuracion.getUsername() == null || configuracion.getUsername().isEmpty()
							? textosInscripcion.obtenerTexto("gw.inscripcion.mensaje.la.configuracion.fue.creada.para.todos.los.usuarios")
							: textosInscripcion.obtenerTexto("gw.inscripcion.mensaje.la.configuracion.fue.creada.para.el.username.indicado"));
		} catch (Exception e) {
			logger.error("No fue posible agregar la regla de nuevo ingreso para el plan " + idPlanNuevaConfiguracion, e);
			mensajeError(textosInscripcion.obtenerTexto("gw.inscripcion.mensaje.no.fue.posible.agregar.la.regla.para.el"), e);
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
			mensaje(FacesMessage.SEVERITY_INFO, textosInscripcion.obtenerTexto("gw.inscripcion.mensaje.configuracion.guardada"),
					textosInscripcion.obtenerTexto("gw.inscripcion.mensaje.la.carga.de.nuevo.ingreso.fue.actualizada.para"));
		} catch (Exception e) {
			logger.error("No fue posible guardar la carga de nuevo ingreso del plan " + configuracion.getIdPlan(), e);
			mensajeError(textosInscripcion.obtenerTexto("gw.inscripcion.mensaje.no.fue.posible.guardar.la.configuracion.de.nuevo"), e);
		}
	}

	public void eliminarCargaNuevoIngreso(ConfiguracionCargaNuevoIngresoDTO configuracion) {
		try {
			inscripcionService.eliminarConfiguracionCargaNuevoIngreso(configuracion);
			consultarReglas();
			mensaje(FacesMessage.SEVERITY_INFO, textosInscripcion.obtenerTexto("gw.inscripcion.mensaje.configuracion.eliminada"),
					configuracion.getIdPersona() == null
							? "El plan vuelve a utilizar la regla general de nuevo ingreso."
							: "El usuario vuelve a utilizar la configuración del plan o, si no existe, la regla general.");
		} catch (Exception e) {
			logger.error("No fue posible eliminar la carga de nuevo ingreso del plan " + configuracion.getIdPlan(), e);
			mensajeError(textosInscripcion.obtenerTexto("gw.inscripcion.mensaje.no.fue.posible.eliminar.la.configuracion.especifica.del"), e);
		}
	}

	public void guardar(ReglaInscripcionDTO regla) {
		try {
			inscripcionService.guardarReglaInscripcion(regla, getUsuarioEnSession().getIdPersona());
			consultarReglas();
			mensaje(FacesMessage.SEVERITY_INFO, textosInscripcion.obtenerTexto("gw.inscripcion.mensaje.configuracion.guardada"),
					textosInscripcion.obtenerTexto("gw.inscripcion.mensaje.la.regla.de.inscripcion.fue.actualizada.correctamente"));
		} catch (Exception e) {
			logger.error("No fue posible guardar la regla de inscripción " + regla.getClave(), e);
			mensajeError(textosInscripcion.obtenerTexto("gw.inscripcion.mensaje.no.fue.posible.guardar.la.regla.de.inscripcion"), e);
		}
	}

	private void mensaje(FacesMessage.Severity severity, String titulo, String detalle) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, titulo, detalle));
	}

	private void mensajeError(String contexto, Throwable error) {
		mensaje(FacesMessage.SEVERITY_ERROR, textosInscripcion.obtenerTexto("gw.inscripcion.mensaje.error"), contexto + ": " + obtenerCausaError(error));
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
