package mx.gob.sedesol.gestorweb.beans.gestionaprendizaje.alumnoview;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import mx.gob.sedesol.basegestor.commons.dto.admin.RolDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.EventoPeriodoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.MatriculacionMasivaRegistroDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.PeriodoInscripcionDTO;
import mx.gob.sedesol.basegestor.service.admin.RoleService;
import mx.gob.sedesol.basegestor.service.gestionescolar.MatriculacionMasivaService;
import mx.gob.sedesol.gestorweb.beans.acceso.BaseBean;
import mx.gob.sedesol.gestorweb.sistema.SistemaBean;

@ManagedBean
@ViewScoped
public class MatriculacionMasivaUsuariosBean extends BaseBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(MatriculacionMasivaUsuariosBean.class);
	private static final String CONTENT_TYPE_XLSX = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

	@ManagedProperty("#{matriculacionMasivaService}")
	private MatriculacionMasivaService matriculacionMasivaService;

	@ManagedProperty("#{roleService}")
	private RoleService roleService;
	
	@ManagedProperty("#{sistema}")
	private SistemaBean sistema;

	private List<PeriodoInscripcionDTO> periodos;
	private Integer periodoSeleccionado;
	private StreamedContent reporteEventos;
	private transient UploadedFile archivoMatriculacion;
	private StreamedContent resultadoMatriculacion;
	private boolean resultadoDisponible;
	private int totalMatriculados;
	private int totalProcesados;

	@PostConstruct
	public void init() {
		periodos = new ArrayList<>();
		cargarPeriodos();
	}

	public void cargarPeriodos() {
		try {
			periodos = matriculacionMasivaService.obtenerPeriodosInscripcion();
		} catch (Exception ex) {
			logger.error("Error al cargar los periodos de inscripción", ex);
			agregarMsgError("Ocurrió un error al cargar los periodos de inscripción", null, sistema);
		}
	}

	public void generarEventosPorPeriodo() {
		reporteEventos = null;
		if (periodoSeleccionado == null) {
			agregarMsgError("Seleccione un periodo válido", null, sistema);
			return;
		}

		String nombrePeriodo = obtenerNombrePeriodo(periodoSeleccionado);
		if (nombrePeriodo == null) {
			agregarMsgError("Seleccione un periodo válido", null, sistema);
			return;
		}

		try {
			List<EventoPeriodoDTO> eventos = matriculacionMasivaService.obtenerEventosPorPeriodo(nombrePeriodo);
			if (eventos == null || eventos.isEmpty()) {
				agregarMsgInfo("No se encontraron eventos para el periodo seleccionado", null, sistema);
				return;
			}

			byte[] archivo = construirExcelEventos(eventos);
			String nombreArchivo = "eventos_" + nombrePeriodo + "_" + System.currentTimeMillis() + ".xlsx";
			reporteEventos = new DefaultStreamedContent(new ByteArrayInputStream(archivo), CONTENT_TYPE_XLSX,
					nombreArchivo);
		} catch (Exception ex) {
			logger.error("Error al generar el archivo de eventos por periodo", ex);
			agregarMsgError("Ocurrió un error al generar el archivo de eventos", null, sistema);
		}
	}

	public StreamedContent getPlantilla() {
		InputStream stream = FacesContext.getCurrentInstance().getExternalContext()
				.getResourceAsStream("/resources/plantillas/ejemplo_archivo_registro_masivo.xlsx");
		if (stream == null) {
			agregarMsgError("No se encontró la plantilla de ejemplo", null, sistema);
			return null;
		}
		return new DefaultStreamedContent(stream, CONTENT_TYPE_XLSX, "ejemplo_archivo_registro_masivo.xlsx");
	}

	public StreamedContent getRolesDisponibles() {
		try {
			List<RolDTO> rolesConsultados = roleService.findAll();
			List<RolDTO> roles = rolesConsultados != null ? new ArrayList<>(rolesConsultados) : new ArrayList<>();
			roles.sort((rol1, rol2) -> {
				Integer id1 = rol1 != null ? rol1.getIdRol() : null;
				Integer id2 = rol2 != null ? rol2.getIdRol() : null;
				if (id1 == null) {
					return id2 == null ? 0 : 1;
				}
				return id2 == null ? -1 : id1.compareTo(id2);
			});
			return construirExcelRoles(roles);
		} catch (Exception ex) {
			logger.error("Error al generar el archivo de roles disponibles", ex);
			agregarMsgError("Ocurrió un error al generar el archivo de roles disponibles", null, sistema);
			return null;
		}
	}

	private StreamedContent construirExcelRoles(List<RolDTO> roles) throws IOException {
		try (XSSFWorkbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
			Sheet sheet = workbook.createSheet("Roles disponibles");
			Row header = sheet.createRow(0);
			String[] columnas = new String[] { "id_rol", "nombre", "clave" };
			for (int i = 0; i < columnas.length; i++) {
				header.createCell(i).setCellValue(columnas[i]);
			}

			int fila = 1;
			for (RolDTO rol : roles) {
				if (rol == null) {
					continue;
				}
				Row row = sheet.createRow(fila++);
				row.createCell(0).setCellValue(rol.getIdRol() != null ? rol.getIdRol() : 0);
				row.createCell(1).setCellValue(valorSeguro(rol.getNombre()));
				row.createCell(2).setCellValue(valorSeguro(rol.getClave()));
			}

			for (int i = 0; i < columnas.length; i++) {
				sheet.autoSizeColumn(i);
			}

			workbook.write(out);
			return new DefaultStreamedContent(new ByteArrayInputStream(out.toByteArray()), CONTENT_TYPE_XLSX,
					"roles_disponibles.xlsx");
		}
	}
	
	public void procesarMatriculacionMasiva() {
		resultadoMatriculacion = null;
		resultadoDisponible = false;
		totalMatriculados = 0;
		totalProcesados = 0;
		
		if (archivoMatriculacion == null) {
			agregarMsgError("Seleccione un archivo para procesar", null, sistema);
			return;
		}
		try {
			List<MatriculacionMasivaRegistroDTO> registros = leerArchivoMatriculacion(
					archivoMatriculacion.getInputstream());
			if (registros.isEmpty()) {
				agregarMsgInfo("El archivo no contiene registros para procesar", null, sistema);
				return;
			}
			List<MatriculacionMasivaRegistroDTO> resultado = matriculacionMasivaService
					.procesarMatriculacionMasiva(registros, getUsuarioEnSession().getIdPersona());
			totalProcesados = resultado.size();
			totalMatriculados = (int) resultado.stream()
					.filter(r -> "Matriculado".equalsIgnoreCase(r.getEstado()))
					.count();
			resultadoMatriculacion = construirExcelResultado(resultado);
			resultadoDisponible = resultadoMatriculacion != null;
			agregarMsgInfo(resumenMatriculacion(), null, sistema);
		} catch (Exception ex) {
			logger.error("Error al procesar el archivo de matriculación masiva", ex);
			agregarMsgError("Ocurrió un error al procesar el archivo", null, sistema);
		}
	}
	
	private List<MatriculacionMasivaRegistroDTO> leerArchivoMatriculacion(InputStream inputStream) throws IOException {
		List<MatriculacionMasivaRegistroDTO> registros = new ArrayList<>();
		try (XSSFWorkbook workbook = new XSSFWorkbook(inputStream)) {
			Sheet sheet = workbook.getSheetAt(0);
			for (int i = 1; i <= sheet.getLastRowNum(); i++) {
				Row row = sheet.getRow(i);
				if (row == null) {
					continue;
				}
				MatriculacionMasivaRegistroDTO registro = new MatriculacionMasivaRegistroDTO();
				registro.setIdGrupo(leerLong(row.getCell(0)));
				registro.setUsername(leerCadena(row.getCell(1)));
				registro.setRol(leerInteger(row.getCell(2)));
				registros.add(registro);
			}
		}
		return registros;
	}
	
	private StreamedContent construirExcelResultado(List<MatriculacionMasivaRegistroDTO> registros) throws IOException {
		if (registros == null || registros.isEmpty()) {
			return null;
		}
		try (XSSFWorkbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
			Sheet sheet = workbook.createSheet("Resultado");
			Row header = sheet.createRow(0);
			String[] columnas = new String[] { "id_grupo", "username", "rol", "estado" };
			for (int i = 0; i < columnas.length; i++) {
				header.createCell(i).setCellValue(columnas[i]);
			}
			
			int fila = 1;
			for (MatriculacionMasivaRegistroDTO registro : registros) {
				Row row = sheet.createRow(fila++);
				row.createCell(0).setCellValue(registro.getIdGrupo() != null ? registro.getIdGrupo() : 0);
				row.createCell(1).setCellValue(valorSeguro(registro.getUsername()));
				row.createCell(2).setCellValue(registro.getRol() != null ? registro.getRol() : 0);
				row.createCell(3).setCellValue(valorSeguro(registro.getEstado()));
			}
			
			for (int i = 0; i < columnas.length; i++) {
				sheet.autoSizeColumn(i);
			}
			
			workbook.write(out);
			String nombre = "resultado_matriculacion_" + new Date().getTime() + ".xlsx";
			return new DefaultStreamedContent(new ByteArrayInputStream(out.toByteArray()), CONTENT_TYPE_XLSX, nombre);
		}
	}
	
	private Long leerLong(Cell cell) {
		if (cell == null) {
			return null;
		}
		try {
			int tipo = cell.getCellType();
			if (tipo == Cell.CELL_TYPE_NUMERIC) {
				return Long.valueOf((long) cell.getNumericCellValue());
			}
			if (tipo == Cell.CELL_TYPE_STRING) {
				String val = cell.getStringCellValue();
				return val != null && !val.trim().isEmpty() ? Long.valueOf(val.trim()) : null;
			}
		} catch (Exception ex) {
			logger.warn("No se pudo leer id_grupo en la fila " + cell.getRowIndex(), ex);
		}
		return null;
	}
	
	private Integer leerInteger(Cell cell) {
		if (cell == null) {
			return null;
		}
		try {
			int tipo = cell.getCellType();
			if (tipo == Cell.CELL_TYPE_NUMERIC) {
				return Integer.valueOf((int) cell.getNumericCellValue());
			}
			if (tipo == Cell.CELL_TYPE_STRING) {
				String val = cell.getStringCellValue();
				return val != null && !val.trim().isEmpty() ? Integer.valueOf(val.trim()) : null;
			}
		} catch (Exception ex) {
			logger.warn("No se pudo leer rol en la fila " + cell.getRowIndex(), ex);
		}
		return null;
	}
	
	private String leerCadena(Cell cell) {
		if (cell == null) {
			return null;
		}
		cell.setCellType(Cell.CELL_TYPE_STRING);
		String valor = cell.getStringCellValue();
		return valor != null ? valor.trim() : null;
	}

	private byte[] construirExcelEventos(List<EventoPeriodoDTO> eventos) throws IOException {
		try (XSSFWorkbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
			Sheet sheet = workbook.createSheet("Eventos");
			Row header = sheet.createRow(0);
			String[] columnas = new String[] { "Plan", "Programa", "Evento", "Clave evento", "Id evento", "Grupo",
					"Id grupo" };
			for (int i = 0; i < columnas.length; i++) {
				Cell cell = header.createCell(i);
				cell.setCellValue(columnas[i]);
			}

			int rowIndex = 1;
			for (EventoPeriodoDTO evento : eventos) {
				Row row = sheet.createRow(rowIndex++);
				row.createCell(0).setCellValue(valorSeguro(evento.getPlan()));
				row.createCell(1).setCellValue(valorSeguro(evento.getPrograma()));
				row.createCell(2).setCellValue(valorSeguro(evento.getEvento()));
				row.createCell(3).setCellValue(valorSeguro(evento.getClaveEvento()));
				row.createCell(4).setCellValue(evento.getIdEvento() != null ? evento.getIdEvento() : 0);
				row.createCell(5).setCellValue(valorSeguro(evento.getGrupo()));
				row.createCell(6).setCellValue(evento.getIdGrupo() != null ? evento.getIdGrupo() : 0);
			}

			for (int i = 0; i < columnas.length; i++) {
				sheet.autoSizeColumn(i);
			}

			workbook.write(out);
			return out.toByteArray();
		}
	}

	private String valorSeguro(String valor) {
		return valor != null ? valor : "";
	}

	private String obtenerNombrePeriodo(Integer idPeriodo) {
		Optional<PeriodoInscripcionDTO> periodo = periodos.stream()
				.filter(p -> idPeriodo.equals(p.getIdPeriodo()))
				.findFirst();
		return periodo.map(PeriodoInscripcionDTO::getNombrePeriodo).orElse(null);
	}

	public List<PeriodoInscripcionDTO> getPeriodos() {
		return periodos;
	}

	public Integer getPeriodoSeleccionado() {
		return periodoSeleccionado;
	}

	public void setPeriodoSeleccionado(Integer periodoSeleccionado) {
		this.periodoSeleccionado = periodoSeleccionado;
	}

	public StreamedContent getReporteEventos() {
		return reporteEventos;
	}
	
	public UploadedFile getArchivoMatriculacion() {
		return archivoMatriculacion;
	}

	public void setArchivoMatriculacion(UploadedFile archivoMatriculacion) {
		this.archivoMatriculacion = archivoMatriculacion;
	}
	
	public StreamedContent getResultadoMatriculacion() {
		return resultadoMatriculacion;
	}
	
	public boolean isResultadoDisponible() {
		return resultadoDisponible;
	}
	
	public int getTotalMatriculados() {
		return totalMatriculados;
	}
	
	public String getResumenMatriculacion() {
		if (totalProcesados <= 0) {
			return null;
		}
		return resumenMatriculacion();
	}
	
	public int getTotalProcesados() {
		return totalProcesados;
	}

	public void setMatriculacionMasivaService(MatriculacionMasivaService matriculacionMasivaService) {
		this.matriculacionMasivaService = matriculacionMasivaService;
	}

	public RoleService getRoleService() {
		return roleService;
	}

	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	public SistemaBean getSistema() {
		return sistema;
	}

	public void setSistema(SistemaBean sistema) {
		this.sistema = sistema;
	}
	
	private String resumenMatriculacion() {
		return String.format("Procesados: %d, matriculados: %d, con incidencia: %d",
				totalProcesados, totalMatriculados, totalProcesados - totalMatriculados);
	}
}
