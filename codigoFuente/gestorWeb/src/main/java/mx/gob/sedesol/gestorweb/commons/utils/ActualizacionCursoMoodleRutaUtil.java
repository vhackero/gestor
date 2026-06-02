package mx.gob.sedesol.gestorweb.commons.utils;

import java.io.File;

import org.apache.commons.lang3.StringUtils;

import mx.gob.sedesol.basegestor.commons.constantes.ConstantesGestor;
import mx.gob.sedesol.basegestor.service.ParametroSistemaService;

public final class ActualizacionCursoMoodleRutaUtil {

	private static final String CARPETA_RESPALDOS_MOODLE = "respaldos-moodle";

	private ActualizacionCursoMoodleRutaUtil() {
		throw new IllegalStateException("Clase utilitaria");
	}

	public static File obtenerDirectorioRespaldos(ParametroSistemaService parametroSistemaService) {
		File directorio = construirDirectorioRecursosPublicos(parametroSistemaService);
		if (esUtilizable(directorio)) {
			return directorio;
		}
		return directorio;
	}

	private static File construirDirectorioRecursosPublicos(ParametroSistemaService parametroSistemaService) {
		String rutaRecursosPublicos = parametroSistemaService
				.obtenerParametro(ConstantesGestor.PARAMETRO_RUTA_RECURSOS_PUBLICOS);
		if (StringUtils.isBlank(rutaRecursosPublicos)) {
			return new File(CARPETA_RESPALDOS_MOODLE);
		}
		if (!rutaRecursosPublicos.endsWith(File.separator)) {
			rutaRecursosPublicos = rutaRecursosPublicos + File.separator;
		}
		return new File(rutaRecursosPublicos + CARPETA_RESPALDOS_MOODLE);
	}

	private static boolean esUtilizable(File directorio) {
		if (directorio == null) {
			return false;
		}
		if (directorio.exists()) {
			return directorio.isDirectory() && directorio.canWrite();
		}
		return directorio.mkdirs();
	}
}
