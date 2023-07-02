package mx.gob.sedesol.basegestor.commons.utils;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.POIXMLException;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.csvreader.CsvReader;

import mx.gob.sedesol.basegestor.commons.constantes.ConstantesGestor;
import mx.gob.sedesol.basegestor.commons.dto.admin.PersonaCargaDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.PersonaDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoCargaDTO;
import mx.gob.sedesol.basegestor.model.entities.admin.TblPersona;

public class UtilidadesCargaMasiva {
	
	private static final Logger logger = Logger.getLogger(UtilidadesCargaMasiva.class);
	
	private UtilidadesCargaMasiva() {
		throw new IllegalAccessError(ConstantesGestor.CLASE_UTILIDADES);
	}

	public static List<PersonaCargaDTO> cargarDocumento(String ruta, Map<String, Integer> entidades, 
			Map<String , String> municipios) {

		try(BufferedInputStream bis = new BufferedInputStream(new FileInputStream(ruta)); 
				FileInputStream fis = new FileInputStream(ruta)) {

			if (POIFSFileSystem.hasPOIFSHeader(bis)) {
				logger.info("FS");
				return leerExcelFS(fis, entidades, municipios);
			}
			if (POIXMLDocument.hasOOXMLHeader(bis)) {
				logger.info("XML");
				return leerExcelXML(fis, entidades, municipios);
			}
			logger.info("SCV");
			return leerCVS(ruta, entidades, municipios);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			return new ArrayList<>();
		}
	}

	public static List<PersonaCargaDTO> leerExcelXML(FileInputStream fis, Map<String, Integer> entidades, 
			Map<String , String> municipios) throws IOException {

		List<PersonaCargaDTO> personas = new ArrayList<>();

		try(XSSFWorkbook libro = new XSSFWorkbook(fis)) {
			
			XSSFSheet hoja = libro.getSheetAt(0);

			Iterator<Row> filas = hoja.iterator();
			filas.next(); // iteramos la fila de los encabezados

			while (filas.hasNext()) {
				Row filaXssf = filas.next();
				int indiceFila = 0;

				PersonaCargaDTO dto = new PersonaCargaDTO();
				dto.setTipoUsuario(obtenerValor(filaXssf, indiceFila++));
				dto.setUsuario(obtenerValor(filaXssf, indiceFila++));
				dto.setContrasenia(obtenerValor(filaXssf, indiceFila++));
				dto.setPrograma(obtenerValor(filaXssf, indiceFila++));
				dto.setCurp(obtenerValor(filaXssf, indiceFila++));
				dto.setNombre(obtenerValor(filaXssf, indiceFila++));
				dto.setApellidoPaterno(obtenerValor(filaXssf, indiceFila++));
				dto.setApellidoMaterno(obtenerValor(filaXssf, indiceFila++));
				dto.setFechaNacimiento(obtenerCadena(filaXssf, indiceFila++));
				dto.setInstitucion(obtenerValor(filaXssf, indiceFila++));
				dto.setCorreo(obtenerValor(filaXssf, indiceFila++));
				dto.setTelefono(obtenerValor(filaXssf, indiceFila++));
				dto.setCelular(obtenerValor(filaXssf, indiceFila++));
				dto.setSede(obtenerValor(filaXssf, indiceFila++));
				dto.setMunicipio(obtenerValor(filaXssf, indiceFila++));
				dto.setOrden(obtenerValor(filaXssf, indiceFila++));
				dto.setPuesto(obtenerValor(filaXssf, indiceFila++));
				
				validarPersona(dto, entidades, municipios);

				personas.add(dto);
			}
		} catch (POIXMLException e) {
			logger.error(e.getMessage(), e);
		}

		return personas;
	}
	
	private static String obtenerValor(Row fila, int columna) {
		final int tipoNumerico = 0;
		final int tipoCadena = 1;
		if (ObjectUtils.isNotNull(fila.getCell(columna))) {
			switch (fila.getCell(columna).getCellType()) {
			case tipoCadena:
				return fila.getCell(columna).getStringCellValue();
			case tipoNumerico:
				return String.valueOf(fila.getCell(columna).getNumericCellValue());
			default:
				return "";
			}
		} else {
			return null;
		}
	}
	
	private static String obtenerCadena(Row fila, int columna) {
		if (ObjectUtils.isNotNull(fila.getCell(columna))) {
			return fila.getCell(columna).getStringCellValue();
		} else {
			return null;
		}
	}

	public static List<PersonaCargaDTO> leerExcelFS(FileInputStream fis, Map<String, Integer> entidades, 
			Map<String , String> municipios) throws IOException {
		List<PersonaCargaDTO> personas = new ArrayList<>();

		try(HSSFWorkbook libro = new HSSFWorkbook(fis)) {
			
			HSSFSheet hoja = libro.getSheetAt(0);

			Iterator<Row> filas = hoja.rowIterator();
			filas.next();

			while (filas.hasNext()) {
				HSSFRow filaHssf = (HSSFRow) filas.next();
				int indiceFila = 0;
				
				PersonaCargaDTO dto = new PersonaCargaDTO();
				dto.setTipoUsuario(obtenerValor(filaHssf, indiceFila++));
				dto.setUsuario(obtenerValor(filaHssf, indiceFila++));
				dto.setContrasenia(obtenerValor(filaHssf, indiceFila++));
				dto.setPrograma(obtenerValor(filaHssf, indiceFila++));
				dto.setCurp(obtenerValor(filaHssf, indiceFila++));
				dto.setNombre(obtenerValor(filaHssf, indiceFila++));
				dto.setApellidoPaterno(obtenerValor(filaHssf, indiceFila++));
				dto.setApellidoMaterno(obtenerValor(filaHssf, indiceFila++));
				obtenerFecha(filaHssf, indiceFila++, dto);
				dto.setInstitucion(obtenerValor(filaHssf, indiceFila++));
				dto.setCorreo(obtenerValor(filaHssf, indiceFila++));
				dto.setTelefono(obtenerValor(filaHssf, indiceFila++));
				dto.setCelular(obtenerValor(filaHssf, indiceFila++));
				dto.setSede(obtenerValor(filaHssf, indiceFila++));
				dto.setMunicipio(obtenerValor(filaHssf, indiceFila++));
				dto.setOrden(obtenerValor(filaHssf, indiceFila++));
				dto.setPuesto(obtenerValor(filaHssf, indiceFila++));
				
				validarPersona(dto, entidades, municipios);

				personas.add(dto);
			}
		} catch (IllegalArgumentException e) {
			logger.error(e.getMessage(), e);
		}
		return personas;
	}
	
	private static String obtenerValor(HSSFRow fila, int columna) {
		final int tipoNumerico = 0;
		final int tipoCadena = 1;
		if (ObjectUtils.isNotNull(fila.getCell(columna))) {
			switch (fila.getCell(columna).getCellType()) {
			case tipoCadena:
				return fila.getCell(columna).getStringCellValue();
			case tipoNumerico:
				return String.valueOf(fila.getCell(columna).getNumericCellValue());
			
			default:
				return "";
			}
		} else {
			return null;
		}
	}
	
	private static void obtenerFecha(HSSFRow fila, int columna, PersonaCargaDTO dto) {
		final int tipoNumerico = 0;
		if (ObjectUtils.isNotNull(fila.getCell(columna)) && fila.getCell(columna).getCellType() == tipoNumerico) {
			if (HSSFDateUtil.isCellDateFormatted(fila.getCell(columna))) {
				DataFormatter dataFormatter = new DataFormatter();
				dto.setFechaNacimientoDate(fila.getCell(columna).getDateCellValue());
				dto.setFechaNacimiento(dataFormatter.formatCellValue(fila.getCell(columna)));
			} else {
				dto.setFechaNacimiento(obtenerValor(fila, columna));
			}
		} else {
			dto.setFechaNacimiento(obtenerValor(fila, columna));
		}
	}
	
	private static void validarPersona(PersonaCargaDTO dto, Map<String, Integer> entidades, 
			Map<String , String> municipios) {

		validarTipoUsuario(dto);
		if (ObjectUtils.isNullOrEmpty(dto.getUsuario())) {
			dto.setUsuarioCorrecto(false);
			dto.setCorrecto(false);
		}
		
		if (ObjectUtils.isNullOrEmpty(dto.getContrasenia())) {
			dto.setContraseniaCorrecto(false);
			dto.setCorrecto(false);
		}
		validarPrograma(dto);
		validarCurp(dto);
		if (ObjectUtils.isNullOrEmpty(dto.getNombre())) {
			dto.setNombreCorrecto(false);
			dto.setCorrecto(false);
		}
		if (ObjectUtils.isNullOrEmpty(dto.getApellidoPaterno())) {
			dto.setApellidoPaternoCorrecto(false);
			dto.setCorrecto(false);
		}
		if (ObjectUtils.isNull(dto.getFechaNacimientoDate())) {
			dto.setFechaNacimientoCorrecto(false);
			dto.setCorrecto(false);
		}
		if (ObjectUtils.isNullOrEmpty(dto.getInstitucion())) {
			dto.setInstitucionCorrecto(false);
			dto.setCorrecto(false);
		}
		validarCorreo(dto);
		if (!esSedeValida(dto, entidades)) {
			dto.setCorrecto(false);
		}
		if (!esMunicipioValido(dto, municipios)) {
			dto.setCorrecto(false);
		}
		validarOrdenGobierno(dto);
	}
	
	private static void validarTipoUsuario(PersonaCargaDTO dto) {
		if (ObjectUtils.isNullOrEmpty(dto.getTipoUsuario()) 
				|| !esTipoUsuarioValido(dto.getTipoUsuario())) {
			dto.setTipoUsuarioCorrecto(false);
			dto.setCorrecto(false);
		}
	}
	
	private static boolean esTipoUsuarioValido(String tipo) {
		boolean resultado = false;
		for (TipoUsuarioEnum tipoUsuario : TipoUsuarioEnum.values()) {
			if (tipoUsuario.getDescripcion().equalsIgnoreCase(tipo)) {
				resultado = true;
				break;
			}
		}
		return resultado;
	}
	
	private static void validarPrograma(PersonaCargaDTO dto) {
		if (ObjectUtils.isNullOrEmpty(dto.getPrograma()) || !esProgramaValido(dto.getPrograma())) {
			dto.setProgramaCorrecto(false);
			dto.setCorrecto(false);
		}
	}
	
	private static boolean esProgramaValido(String programa) {
		boolean resultado = false;
		for (ContieneProgramaEnum contienePrograma : ContieneProgramaEnum.values()) {
			if (contienePrograma.getValor().equals(programa)) {
				resultado = true;
			}
		}
		return resultado;
	}
	
	private static void validarCurp(PersonaCargaDTO dto) {
		if (ObjectUtils.isNullOrEmpty(dto.getCurp()) || 
				!CurpUtils.validaCurpConRegExp(dto.getCurp())) {
			dto.setCurpCorrecto(false);
			dto.setCorrecto(false);
		}
	}
	
	
	
	private static void validarCorreo(PersonaCargaDTO dto) {
		if (ObjectUtils.isNullOrEmpty(dto.getCorreo()) || 
				!CorreoElectronicoUtils.esCorreoElectronicoValido(dto.getCorreo())) {
			dto.setCorreoCorrecto(false);
			dto.setCorrecto(false);
		}
	}
	
	private static boolean esSedeValida(PersonaCargaDTO dto, Map<String, Integer> entidades) {
		if (ObjectUtils.isNullOrEmpty(dto.getSede()) || !entidades.containsKey(dto.getSede())) {
			dto.setSedeCorrecto(false);
		} else {
			dto.setIdEntidadFederativa(entidades.get(dto.getSede()));
			dto.setSedeCorrecto(true);
		} 
		return dto.isSedeCorrecto();
	}
	
	private static boolean esMunicipioValido(PersonaCargaDTO dto, Map<String, String> municipios) {
		if (ObjectUtils.isNullOrEmpty(dto.getMunicipio()) || !municipios.containsKey(dto.getMunicipio())) {
			dto.setMunicipioCorrecto(false);
		} else {
			dto.setIdMunicipio(municipios.get(dto.getMunicipio()));
			dto.setMunicipioCorrecto(true);
		} 
		return dto.isMunicipioCorrecto();
	}
	
	private static void validarOrdenGobierno(PersonaCargaDTO dto) {
		if (ObjectUtils.isNullOrEmpty(dto.getOrden())) {
			dto.setOrdenCorrecto(true);
		} else {
			if (esOrdenGobiernoValido(dto.getOrden())) {
				dto.setOrdenCorrecto(true);
			} else {
				dto.setOrdenCorrecto(false);
				dto.setCorrecto(false);
			}
		}
	}
	
	private static boolean esOrdenGobiernoValido(String orden) {
		boolean resultado = false;
		for (OrdenGobiernoEnum ordenGobierno : OrdenGobiernoEnum.values()) {
			if(orden.equalsIgnoreCase(ordenGobierno.getDescripcion())) {
				resultado = true;
			}
		}	
		return resultado;
	}

	public static List<PersonaCargaDTO> leerCVS(String ruta, Map<String, Integer> entidades, 
			Map<String , String> municipios) throws IOException {
		List<PersonaCargaDTO> personas = new ArrayList<>();

		CsvReader usuariosImport = new CsvReader(ruta);
		usuariosImport.readHeaders();

		while (usuariosImport.readRecord()) {
			PersonaCargaDTO dto = new PersonaCargaDTO();
			
			dto.setTipoUsuario(usuariosImport.get(ConstantesGestor.COLUMNA_TIPO_USUARIO));
			dto.setUsuario(usuariosImport.get(ConstantesGestor.COLUMNA_USUARIO));
			dto.setContrasenia(usuariosImport.get(ConstantesGestor.COLUMNA_CONTRASENIA));
			dto.setPrograma(usuariosImport.get(ConstantesGestor.COLUMNA_PROGRAMA));
			dto.setCurp(usuariosImport.get(ConstantesGestor.COLUMNA_CURP));
			dto.setNombre(usuariosImport.get(ConstantesGestor.COLUMNA_NOMBRE));
			dto.setApellidoPaterno(usuariosImport.get(ConstantesGestor.COLUMNA_APELLIDO_PATERNO));
			dto.setApellidoMaterno(usuariosImport.get(ConstantesGestor.COLUMNA_APELLIDO_MATERNO));
			dto.setFechaNacimiento(usuariosImport.get(ConstantesGestor.COLUMNA_FECHA_NACIMIENTO));
			//TODO dto.setFechaNacimientoDate(usuariosImport.get(ConstantesGestor.COLUMNA_FECHA_NACIMIENTO));
			dto.setInstitucion(usuariosImport.get(ConstantesGestor.COLUMNA_INSTITUCION));
			dto.setCorreo(usuariosImport.get(ConstantesGestor.COLUMNA_CORREO));
			dto.setTelefono(usuariosImport.get(ConstantesGestor.COLUMNA_TELEFONO));
			dto.setCelular(usuariosImport.get(ConstantesGestor.COLUMNA_CELULAR));
			dto.setSede(usuariosImport.get(ConstantesGestor.COLUMNA_SEDE));
			dto.setMunicipio(usuariosImport.get(ConstantesGestor.COLUMNA_MUNICIPIO));
			dto.setOrden(usuariosImport.get(ConstantesGestor.COLUMNA_ORDEN));
			dto.setPuesto(usuariosImport.get(ConstantesGestor.COLUMNA_PUESTO));
			validarPersona(dto, entidades, municipios);
			personas.add(dto);
		}
		usuariosImport.close();
		return personas;
	}
	
	public static void crearDocumento(ResultadoCargaDTO resultado, String ruta) {
		UUID uuid = UUID.randomUUID();
		
		resultado.setRutaArchivo(uuid.toString());
		resultado.setRutaCompleta(ruta + uuid.toString());
		
		try (HSSFWorkbook libro = new HSSFWorkbook();
				FileOutputStream archivo = new FileOutputStream(new File(resultado.getRutaCompleta()))) {
		
			HSSFSheet hoja = libro.createSheet("Usuarios");
			
			Row filaEncabezados = hoja.createRow(0);
			llenarEncabezados(filaEncabezados);
			
			HSSFCellStyle estilo = libro.createCellStyle();
			estilo.setFillForegroundColor(HSSFColor.RED.index);
			estilo.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			
			int numeroFila = 1;
			for (PersonaCargaDTO dto : resultado.getRegistros()) {
				Row fila = hoja.createRow(numeroFila);
				int numeroColumna = 0;
				
				llenarCelda(fila, estilo, dto.getTipoUsuario(), dto.isTipoUsuarioCorrecto(), numeroColumna++);
				llenarCelda(fila, estilo, dto.getUsuario(), dto.isUsuarioCorrecto(), numeroColumna++);
				llenarCelda(fila, estilo, dto.getContrasenia(), dto.isContraseniaCorrecto(), numeroColumna++);
				llenarCelda(fila, estilo, dto.getPrograma(), dto.isProgramaCorrecto(), numeroColumna++);
				llenarCelda(fila, estilo, dto.getCurp(), dto.isCurpCorrecto(), numeroColumna++);
				llenarCelda(fila, estilo, dto.getNombre(), dto.isNombreCorrecto(), numeroColumna++);
				llenarCelda(fila, estilo, dto.getApellidoPaterno(), dto.isApellidoPaternoCorrecto(), numeroColumna++);
				llenarCelda(fila, estilo, dto.getApellidoMaterno(), dto.isApellidoMaternoCorrecto(), numeroColumna++);
				llenarCelda(fila, estilo, dto.getFechaNacimiento(), dto.isFechaNacimientoCorrecto(), numeroColumna++);
				llenarCelda(fila, estilo, dto.getInstitucion(), dto.isInstitucionCorrecto(), numeroColumna++);
				llenarCelda(fila, estilo, dto.getCorreo(), dto.isCorreoCorrecto(), numeroColumna++);
				llenarCelda(fila, estilo, dto.getTelefono(), dto.isTelefonoCorrecto(), numeroColumna++);
				llenarCelda(fila, estilo, dto.getCelular(), dto.isCelularCorrecto(), numeroColumna++);
				llenarCelda(fila, estilo, dto.getSede(), dto.isSedeCorrecto(), numeroColumna++);
				llenarCelda(fila, estilo, dto.getMunicipio(), dto.isMunicipioCorrecto(), numeroColumna++);
				llenarCelda(fila, estilo, dto.getOrden(), dto.isOrdenCorrecto(), numeroColumna++);
				llenarCelda(fila, estilo, dto.getPuesto(), dto.isPuestoCorrecto(), numeroColumna++);
				llenarCelda(fila, estilo, dto.getMensajeResultado(), true, numeroColumna);
				numeroFila++;
			}
			
			libro.write(archivo);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	public static byte[] crearPlantilla() throws IOException {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try(HSSFWorkbook libro = new HSSFWorkbook()) {
		
			HSSFSheet hoja = libro.createSheet("Usuarios");
			
			Row filaEncabezados = hoja.createRow(0);
			llenarEncabezados(filaEncabezados);

		    libro.write(bos);
		    
		} finally {
		    bos.close();
		}
		
		return bos.toByteArray();
	}
	
	private static void llenarCelda(Row fila, HSSFCellStyle estilo, String campo, boolean correcto, int numeroColumna) {
		Cell celda = fila.createCell(numeroColumna);
		celda.setCellValue(campo);
		
		if (!correcto) {
			celda.setCellStyle(estilo);
		}
	}
	
	private static void llenarEncabezados(Row fila) {
		int indiceFila = 0;
		Cell celda = fila.createCell(indiceFila++);
		celda.setCellValue(ConstantesGestor.COLUMNA_TIPO_USUARIO);
		celda = fila.createCell(indiceFila++);
		celda.setCellValue(ConstantesGestor.COLUMNA_USUARIO);
		celda = fila.createCell(indiceFila++);
		celda.setCellValue(ConstantesGestor.COLUMNA_CONTRASENIA);
		celda = fila.createCell(indiceFila++);
		celda.setCellValue(ConstantesGestor.COLUMNA_PROGRAMA);
		celda = fila.createCell(indiceFila++);
		celda.setCellValue(ConstantesGestor.COLUMNA_CURP);
		celda = fila.createCell(indiceFila++);
		celda.setCellValue(ConstantesGestor.COLUMNA_NOMBRE);
		celda = fila.createCell(indiceFila++);
		celda.setCellValue(ConstantesGestor.COLUMNA_APELLIDO_PATERNO);
		celda = fila.createCell(indiceFila++);
		celda.setCellValue(ConstantesGestor.COLUMNA_APELLIDO_MATERNO);
		celda = fila.createCell(indiceFila++);
		celda.setCellValue(ConstantesGestor.COLUMNA_FECHA_NACIMIENTO);
		celda = fila.createCell(indiceFila++);
		celda.setCellValue(ConstantesGestor.COLUMNA_INSTITUCION);
		celda = fila.createCell(indiceFila++);
		celda.setCellValue(ConstantesGestor.COLUMNA_CORREO);
		celda = fila.createCell(indiceFila++);
		celda.setCellValue(ConstantesGestor.COLUMNA_TELEFONO);
		celda = fila.createCell(indiceFila++);
		celda.setCellValue(ConstantesGestor.COLUMNA_CELULAR);
		celda = fila.createCell(indiceFila++);
		celda.setCellValue(ConstantesGestor.COLUMNA_SEDE);
		celda = fila.createCell(indiceFila++);
		celda.setCellValue(ConstantesGestor.COLUMNA_MUNICIPIO);
		celda = fila.createCell(indiceFila++);
		celda.setCellValue(ConstantesGestor.COLUMNA_ORDEN);
		celda = fila.createCell(indiceFila);
		celda.setCellValue(ConstantesGestor.COLUMNA_PUESTO);
	}
}
