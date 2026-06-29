package mx.gob.sedesol.gestorweb.beans.analisisdatos;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import mx.gob.sedesol.basegestor.commons.dto.analisisdatos.ReporteadorParametroDTO;
import mx.gob.sedesol.basegestor.commons.dto.analisisdatos.ReporteadorReporteDTO;
import mx.gob.sedesol.basegestor.commons.dto.analisisdatos.ReporteadorResultadoDTO;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.service.analisisdatos.ReporteadorService;
import mx.gob.sedesol.gestorweb.beans.acceso.BaseBean;

@ViewScoped
@ManagedBean
public class ReporteadorBean extends BaseBean {

	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE_XLSX = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
	private static final String SIN_INFORMACION = "No existe información para generar el reporte";

	@ManagedProperty("#{reporteadorService}")
	private ReporteadorService reporteadorService;

	private List<ReporteadorReporteDTO> reportes;
	private Long idReporteSeleccionado;
	private ReporteadorReporteDTO reporteSeleccionado;
	private Map<String, String> valoresParametros;
	private ReporteadorResultadoDTO resultado;
	private StreamedContent archivoReporte;

	@PostConstruct
	public void init() {
		valoresParametros = new HashMap<>();
		resultado = new ReporteadorResultadoDTO();
		cargarReportes();
	}

	public void cargarReportes() {
		reportes = reporteadorService.obtenerReportesActivos();
		for (ReporteadorReporteDTO reporte : reportes) {
			cargarOpcionesParametros(reporte);
		}
	}

	public void seleccionarReporte() {
		reporteSeleccionado = null;
		valoresParametros = new HashMap<>();
		resultado = new ReporteadorResultadoDTO();
		archivoReporte = null;
		if (ObjectUtils.isNull(idReporteSeleccionado)) {
			return;
		}
		for (ReporteadorReporteDTO reporte : reportes) {
			if (idReporteSeleccionado.equals(reporte.getIdReporte())) {
				reporteSeleccionado = reporte;
				cargarOpcionesParametros(reporteSeleccionado);
				break;
			}
		}
	}

	public void limpiarPantalla() {
		idReporteSeleccionado = null;
		reporteSeleccionado = null;
		valoresParametros = new HashMap<>();
		resultado = new ReporteadorResultadoDTO();
		archivoReporte = null;
	}

	public void generarVistaPrevia() {
		archivoReporte = null;
		if (!validarReporteSeleccionado()) {
			return;
		}
		try {
			resultado = reporteadorService.ejecutarReporte(reporteSeleccionado, valoresParametros);
			if (resultado.getFilas().isEmpty()) {
				agregarMensaje(FacesMessage.SEVERITY_WARN, SIN_INFORMACION);
			}
		} catch (IllegalArgumentException e) {
			agregarMensaje(FacesMessage.SEVERITY_WARN, e.getMessage());
		} catch (Exception e) {
			agregarMensaje(FacesMessage.SEVERITY_ERROR, e.getMessage());
		}
	}

	public void generarExcel() {
		archivoReporte = null;
		if (!validarReporteSeleccionado()) {
			return;
		}
		try {
			resultado = reporteadorService.ejecutarReporte(reporteSeleccionado, valoresParametros);
			archivoReporte = construirExcel(resultado);
			if (resultado.getFilas().isEmpty()) {
				agregarMensaje(FacesMessage.SEVERITY_WARN, SIN_INFORMACION);
			}
		} catch (IllegalArgumentException e) {
			agregarMensaje(FacesMessage.SEVERITY_WARN, e.getMessage());
		} catch (Exception e) {
			agregarMensaje(FacesMessage.SEVERITY_ERROR, e.getMessage());
		}
	}

	private StreamedContent construirExcel(ReporteadorResultadoDTO resultadoReporte) throws IOException {
		try (XSSFWorkbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
			Sheet sheet = workbook.createSheet("Reporte");

			if (resultadoReporte.getFilas().isEmpty()) {
				Row row = sheet.createRow(0);
				row.createCell(0).setCellValue(SIN_INFORMACION);
				sheet.autoSizeColumn(0);
			} else {
				Row header = sheet.createRow(0);
				for (int i = 0; i < resultadoReporte.getColumnas().size(); i++) {
					Cell cell = header.createCell(i);
					cell.setCellValue(resultadoReporte.getColumnas().get(i));
				}

				int rowIndex = 1;
				for (List<Object> filaResultado : resultadoReporte.getFilas()) {
					Row row = sheet.createRow(rowIndex++);
					for (int i = 0; i < filaResultado.size(); i++) {
						row.createCell(i).setCellValue(valorExcel(filaResultado.get(i)));
					}
				}

				for (int i = 0; i < resultadoReporte.getColumnas().size(); i++) {
					sheet.autoSizeColumn(i);
				}
			}

			workbook.write(out);
			String nombreArchivo = obtenerNombreArchivo();
			return new DefaultStreamedContent(new ByteArrayInputStream(out.toByteArray()), CONTENT_TYPE_XLSX,
					nombreArchivo);
		}
	}

	private String valorExcel(Object valor) {
		return valor != null ? String.valueOf(valor) : "";
	}

	private String obtenerNombreArchivo() {
		String clave = reporteSeleccionado != null ? reporteSeleccionado.getClave() : "reporte";
		return clave + "_" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".xlsx";
	}

	private boolean validarReporteSeleccionado() {
		if (ObjectUtils.isNull(reporteSeleccionado)) {
			agregarMensaje(FacesMessage.SEVERITY_WARN, "Debe seleccionar un reporte.");
			return false;
		}
		return true;
	}

	private void cargarOpcionesParametros(ReporteadorReporteDTO reporte) {
		if (reporte == null || ObjectUtils.isNullOrEmpty(reporte.getParametros())) {
			return;
		}
		for (ReporteadorParametroDTO parametro : reporte.getParametros()) {
			parametro.setOpciones(reporteadorService.obtenerOpcionesParametro(parametro));
		}
	}

	private void agregarMensaje(FacesMessage.Severity severity, String mensaje) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, mensaje, null));
	}

	public boolean isReporteSeleccionadoConParametros() {
		return reporteSeleccionado != null && !ObjectUtils.isNullOrEmpty(reporteSeleccionado.getParametros());
	}

	public boolean isConResultado() {
		return resultado != null && !ObjectUtils.isNullOrEmpty(resultado.getColumnas());
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

	public Long getIdReporteSeleccionado() {
		return idReporteSeleccionado;
	}

	public void setIdReporteSeleccionado(Long idReporteSeleccionado) {
		this.idReporteSeleccionado = idReporteSeleccionado;
	}

	public ReporteadorReporteDTO getReporteSeleccionado() {
		return reporteSeleccionado;
	}

	public void setReporteSeleccionado(ReporteadorReporteDTO reporteSeleccionado) {
		this.reporteSeleccionado = reporteSeleccionado;
	}

	public Map<String, String> getValoresParametros() {
		return valoresParametros;
	}

	public void setValoresParametros(Map<String, String> valoresParametros) {
		this.valoresParametros = valoresParametros;
	}

	public ReporteadorResultadoDTO getResultado() {
		return resultado;
	}

	public void setResultado(ReporteadorResultadoDTO resultado) {
		this.resultado = resultado;
	}

	public StreamedContent getArchivoReporte() {
		return archivoReporte;
	}

	public void setArchivoReporte(StreamedContent archivoReporte) {
		this.archivoReporte = archivoReporte;
	}
}
