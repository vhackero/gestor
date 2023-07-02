package mx.gob.sedesol.gestorweb.commons.utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.activation.MimetypesFileTypeMap;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import mx.gob.sedesol.basegestor.commons.constantes.ConstantesGestor;
import mx.gob.sedesol.basegestor.commons.dto.admin.ArchivoDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.utils.ResultadoTransaccionEnum;
import mx.gob.sedesol.gestorweb.sistema.SistemaBean;

public class GestorArchivos {
	
	private static final Logger logger = Logger.getLogger(SistemaBean.class);
	
	private GestorArchivos() {
		throw new IllegalAccessError(ConstantesGestor.CLASE_UTILIDADES);
	}

	public static ResultadoDTO<Boolean> crearCarpeta(String ruta) {
		ResultadoDTO<Boolean> resultadoDTO = new ResultadoDTO<>();

		final File strRutaArchivos = new File(ruta);
		final boolean folderCreado = strRutaArchivos.exists() || strRutaArchivos.mkdirs();

		if (folderCreado) {
			resultadoDTO.setResultado(ResultadoTransaccionEnum.EXITOSO);
		} else {
			resultadoDTO.setResultado(ResultadoTransaccionEnum.FALLIDO);
		}

		return resultadoDTO;
	}

	public static ResultadoDTO<Boolean> reemplazarCarpeta(String ruta) {
		ResultadoDTO<Boolean> resultadoDTO = new ResultadoDTO<>();

		final File strRutaArchivos = new File(ruta);
		logger.info("borrar " + strRutaArchivos.getAbsolutePath());
		if (strRutaArchivos.exists()) {
			try {
				eliminar(strRutaArchivos);
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
		}
		final boolean folderCreado = strRutaArchivos.mkdirs();

		if (folderCreado) {
			resultadoDTO.setResultado(ResultadoTransaccionEnum.EXITOSO);
		} else {
			resultadoDTO.setResultado(ResultadoTransaccionEnum.FALLIDO);
		}

		return resultadoDTO;
	}

	public static void eliminarArchivo(String ruta) {
		File archivo = new File(ruta);
		try {
			eliminar(archivo);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}

	public static void eliminar(File file) throws IOException {

		if (file.isDirectory()) {

			// directory is empty, then delete it
			if (file.list().length == 0 && file.delete()) {
				logger.info("Directory is deleted : " + file.getAbsolutePath());
			} else {
				eliminarContenidoDirectorio(file);
			}

		} else {
			// if file, then delete it
			if (file.delete()) {
				logger.info("File is deleted : " + file.getAbsolutePath());
			}
		}
	}
	
	private static void eliminarContenidoDirectorio(File file) throws IOException {
		// list all the directory contents
		String[] files = file.list();

		for (String temp : files) {
			// construct the file structure
			File fileDelete = new File(file, temp);

			// recursive delete
			eliminar(fileDelete);
		}

		// check the directory again, if empty then delete it
		if (file.list().length == 0 && file.delete()) {
			logger.info("Directory is deleted : " + file.getAbsolutePath());
		}
	}

	public static void unzipFunction(String zipFile, String destinationFolder) {

		// if the output directory doesn't exist, create it
		createDirectory(destinationFolder);

		try(ZipInputStream zipInput = new ZipInputStream(new FileInputStream(zipFile))) {

			ZipEntry entry = zipInput.getNextEntry();

			while (ObjectUtils.isNotNull(entry)) {
				String filePath = destinationFolder + File.separator + entry.getName();
				logger.info("Unzip file " + entry.getName() + " to " + filePath);

				// create the directories of the zip directory
				if (entry.isDirectory()) {
					createDirectory(filePath);
				} else {
					 extractFile(zipInput, filePath);
				}
				// close ZipEntry and take the next one
				zipInput.closeEntry();
				entry = zipInput.getNextEntry();
			}

			// close the last ZipEntry
			zipInput.closeEntry();
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}

	private static void createDirectory(String filePath) {
		File newDir = new File(filePath);
		if (!newDir.exists() && !newDir.mkdirs()) {
			logger.info("Problem creating Folder");
		}
	}
	
	private static void extractFile(ZipInputStream zipIn, String filePath) throws IOException {
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath));
        IOUtils.copy(zipIn, bos);
        bos.close();
    }

	public static ResultadoDTO<Boolean> almacenarArchivo(String ruta, byte[] datos) {
		ResultadoDTO<Boolean> resultadoDTO = new ResultadoDTO<>();

		try {
			guardarArchivo(ruta, datos);
			resultadoDTO.setResultado(ResultadoTransaccionEnum.EXITOSO);

		} catch (IOException e) {
			resultadoDTO.setResultado(ResultadoTransaccionEnum.FALLIDO);
			logger.error(e.getMessage(), e);
		}

		return resultadoDTO;
	}
	
	private static void guardarArchivo(String ruta, byte[] datos) throws IOException {
		FileOutputStream fos = new FileOutputStream(new File(ruta));
		fos.write(datos);
		fos.close();
	}

	public static List<ArchivoDTO> obtenerArchivos(String ruta) {
		List<ArchivoDTO> archivos = new ArrayList<>();
		File directorio = new File(ruta);
		
		if (directorio.exists() && directorio.isDirectory()) {
			llenarListaArchivosDirectorio(archivos, directorio);
		}
		
		return archivos;
	}
	
	public static ArchivoDTO obtenerArchivo(String rutaArchivo) {
		File archivo = new File(rutaArchivo);
		
		if (archivo.exists() && archivo.isFile()) {
			try {
				return obtenerArchivo(archivo);
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
		}
		return null;
	}

	private static void llenarListaArchivosDirectorio(List<ArchivoDTO> archivos, File directorio) {
		for (final File archivo : directorio.listFiles()) {
			if (archivo.isFile()) {
				try {
					archivos.add(obtenerArchivo(archivo));
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
			}
		}
	}

	private static ArchivoDTO obtenerArchivo(File archivo) throws IOException {
		FileInputStream fileInputStream = new FileInputStream(archivo);
		ArchivoDTO archivoDTO = new ArchivoDTO();
		MimetypesFileTypeMap mimeTypesMap = new MimetypesFileTypeMap();
		
		archivoDTO.setDatos(IOUtils.toByteArray(fileInputStream));
		archivoDTO.setNombre(archivo.getName());
		archivoDTO.setTipo(mimeTypesMap.getContentType(archivo));

		return archivoDTO;
	}

}
