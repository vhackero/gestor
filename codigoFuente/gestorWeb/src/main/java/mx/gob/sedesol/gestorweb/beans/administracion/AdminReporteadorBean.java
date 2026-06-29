package mx.gob.sedesol.gestorweb.beans.administracion;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import mx.gob.sedesol.basegestor.commons.dto.analisisdatos.ReporteadorParametroDTO;
import mx.gob.sedesol.basegestor.commons.dto.analisisdatos.ReporteadorReporteDTO;
import mx.gob.sedesol.basegestor.service.analisisdatos.ReporteadorService;
import mx.gob.sedesol.gestorweb.beans.acceso.BaseBean;

@ViewScoped
@ManagedBean
public class AdminReporteadorBean extends BaseBean {

	private static final long serialVersionUID = 1L;

	@ManagedProperty("#{reporteadorService}")
	private ReporteadorService reporteadorService;

	private List<ReporteadorReporteDTO> reportes;
	private List<ReporteadorParametroDTO> parametros;
	private ReporteadorReporteDTO reporteSeleccionado;
	private ReporteadorReporteDTO reporteEdicion;
	private ReporteadorParametroDTO parametroEdicion;

	@PostConstruct
	public void init() {
		reportes = new ArrayList<>();
		parametros = new ArrayList<>();
		nuevoReporte();
		nuevoParametro();
		cargarReportes();
	}

	public void cargarReportes() {
		reportes = reporteadorService.obtenerReportes();
		if (reporteSeleccionado != null && reporteSeleccionado.getIdReporte() != null) {
			for (ReporteadorReporteDTO reporte : reportes) {
				if (reporteSeleccionado.getIdReporte().equals(reporte.getIdReporte())) {
					reporteSeleccionado = reporte;
					cargarParametros();
					return;
				}
			}
		}
		parametros = new ArrayList<>();
	}

	public void seleccionarReporte(ReporteadorReporteDTO reporte) {
		reporteSeleccionado = copiarReporte(reporte);
		reporteEdicion = copiarReporte(reporte);
		cargarParametros();
		nuevoParametro();
	}

	public void nuevoReporte() {
		reporteEdicion = new ReporteadorReporteDTO();
		reporteEdicion.setActivo(Boolean.TRUE);
		reporteSeleccionado = null;
		parametros = new ArrayList<>();
		nuevoParametro();
	}

	public void guardarReporte() {
		try {
			reporteEdicion = reporteadorService.guardarReporte(reporteEdicion, idPersonaEnSesion());
			reporteSeleccionado = copiarReporte(reporteEdicion);
			cargarReportes();
			cargarParametros();
			agregarMensaje(FacesMessage.SEVERITY_INFO, "Reporte guardado correctamente.");
		} catch (Exception e) {
			agregarMensaje(FacesMessage.SEVERITY_ERROR, obtenerMensajeError(e));
		}
	}

	public void eliminarReporte(ReporteadorReporteDTO reporte) {
		try {
			reporteadorService.eliminarReporte(reporte.getIdReporte());
			if (reporteSeleccionado != null && reporte.getIdReporte().equals(reporteSeleccionado.getIdReporte())) {
				reporteSeleccionado = null;
				parametros = new ArrayList<>();
				nuevoReporte();
				nuevoParametro();
			}
			cargarReportes();
			agregarMensaje(FacesMessage.SEVERITY_INFO, "Reporte eliminado correctamente.");
		} catch (Exception e) {
			agregarMensaje(FacesMessage.SEVERITY_ERROR, obtenerMensajeError(e));
		}
	}

	public void cambiarEstatusReporte(ReporteadorReporteDTO reporte) {
		try {
			ReporteadorReporteDTO reporteGuardar = copiarReporte(reporte);
			reporteGuardar.setActivo(!Boolean.TRUE.equals(reporte.getActivo()));
			reporteadorService.guardarReporte(reporteGuardar, idPersonaEnSesion());
			cargarReportes();
			agregarMensaje(FacesMessage.SEVERITY_INFO, "Estatus del reporte actualizado.");
		} catch (Exception e) {
			agregarMensaje(FacesMessage.SEVERITY_ERROR, obtenerMensajeError(e));
		}
	}

	public void cargarParametros() {
		if (reporteSeleccionado == null || reporteSeleccionado.getIdReporte() == null) {
			parametros = new ArrayList<>();
			return;
		}
		parametros = reporteadorService.obtenerParametrosPorReporteAdmin(reporteSeleccionado.getIdReporte());
	}

	public void nuevoParametro() {
		parametroEdicion = new ReporteadorParametroDTO();
		parametroEdicion.setActivo(Boolean.TRUE);
		parametroEdicion.setOrden(1);
		if (reporteSeleccionado != null) {
			parametroEdicion.setIdReporte(reporteSeleccionado.getIdReporte());
		}
	}

	public void editarParametro(ReporteadorParametroDTO parametro) {
		parametroEdicion = copiarParametro(parametro);
	}

	public void guardarParametro() {
		try {
			if (reporteSeleccionado == null || reporteSeleccionado.getIdReporte() == null) {
				agregarMensaje(FacesMessage.SEVERITY_WARN, "Debe seleccionar o guardar un reporte antes de agregar parametros.");
				return;
			}
			parametroEdicion.setIdReporte(reporteSeleccionado.getIdReporte());
			reporteadorService.guardarParametro(parametroEdicion, idPersonaEnSesion());
			cargarParametros();
			nuevoParametro();
			agregarMensaje(FacesMessage.SEVERITY_INFO, "Parametro guardado correctamente.");
		} catch (Exception e) {
			agregarMensaje(FacesMessage.SEVERITY_ERROR, obtenerMensajeError(e));
		}
	}

	public void eliminarParametro(ReporteadorParametroDTO parametro) {
		try {
			reporteadorService.eliminarParametro(parametro.getIdParametro());
			cargarParametros();
			nuevoParametro();
			agregarMensaje(FacesMessage.SEVERITY_INFO, "Parametro eliminado correctamente.");
		} catch (Exception e) {
			agregarMensaje(FacesMessage.SEVERITY_ERROR, obtenerMensajeError(e));
		}
	}

	public void cambiarEstatusParametro(ReporteadorParametroDTO parametro) {
		try {
			ReporteadorParametroDTO parametroGuardar = copiarParametro(parametro);
			parametroGuardar.setActivo(!Boolean.TRUE.equals(parametro.getActivo()));
			reporteadorService.guardarParametro(parametroGuardar, idPersonaEnSesion());
			cargarParametros();
			agregarMensaje(FacesMessage.SEVERITY_INFO, "Estatus del parametro actualizado.");
		} catch (Exception e) {
			agregarMensaje(FacesMessage.SEVERITY_ERROR, obtenerMensajeError(e));
		}
	}

	private ReporteadorReporteDTO copiarReporte(ReporteadorReporteDTO reporte) {
		ReporteadorReporteDTO copia = new ReporteadorReporteDTO();
		if (reporte != null) {
			copia.setIdReporte(reporte.getIdReporte());
			copia.setClave(reporte.getClave());
			copia.setNombre(reporte.getNombre());
			copia.setConsultaSql(reporte.getConsultaSql());
			copia.setActivo(reporte.getActivo());
		}
		return copia;
	}

	private ReporteadorParametroDTO copiarParametro(ReporteadorParametroDTO parametro) {
		ReporteadorParametroDTO copia = new ReporteadorParametroDTO();
		if (parametro != null) {
			copia.setIdParametro(parametro.getIdParametro());
			copia.setIdReporte(parametro.getIdReporte());
			copia.setClave(parametro.getClave());
			copia.setEtiqueta(parametro.getEtiqueta());
			copia.setConsultaSql(parametro.getConsultaSql());
			copia.setOrden(parametro.getOrden());
			copia.setActivo(parametro.getActivo());
		}
		return copia;
	}

	private void agregarMensaje(FacesMessage.Severity severity, String mensaje) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, mensaje, null));
	}

	private String obtenerMensajeError(Exception e) {
		return e.getMessage() != null ? e.getMessage() : "Ocurrio un error al procesar la solicitud.";
	}

	public boolean isReporteGuardado() {
		return reporteSeleccionado != null && reporteSeleccionado.getIdReporte() != null;
	}

	public ReporteadorService getReporteadorService() {
		return reporteadorService;
	}

	public void setReporteadorService(ReporteadorService reporteadorService) {
		this.reporteadorService = reporteadorService;
	}

	public List<ReporteadorReporteDTO> getReportes() {
		return reportes;
	}

	public void setReportes(List<ReporteadorReporteDTO> reportes) {
		this.reportes = reportes;
	}

	public List<ReporteadorParametroDTO> getParametros() {
		return parametros;
	}

	public void setParametros(List<ReporteadorParametroDTO> parametros) {
		this.parametros = parametros;
	}

	public ReporteadorReporteDTO getReporteSeleccionado() {
		return reporteSeleccionado;
	}

	public void setReporteSeleccionado(ReporteadorReporteDTO reporteSeleccionado) {
		this.reporteSeleccionado = reporteSeleccionado;
	}

	public ReporteadorReporteDTO getReporteEdicion() {
		return reporteEdicion;
	}

	public void setReporteEdicion(ReporteadorReporteDTO reporteEdicion) {
		this.reporteEdicion = reporteEdicion;
	}

	public ReporteadorParametroDTO getParametroEdicion() {
		return parametroEdicion;
	}

	public void setParametroEdicion(ReporteadorParametroDTO parametroEdicion) {
		this.parametroEdicion = parametroEdicion;
	}
}
