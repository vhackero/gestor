package mx.gob.sedesol.gestorweb.ws;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import mx.gob.sedesol.basegestor.service.ParametroSistemaService;
import mx.gob.sedesol.gestorweb.commons.utils.ActualizacionCursoMoodleRutaUtil;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/public/respaldos-moodle")
public class ActualizacionCursoMoodleREST {

	@Autowired
	private transient ParametroSistemaService parametroSistemaService;

	@RequestMapping("/{nombreArchivo:.+}")
	public ResponseEntity<Resource> descargar(@PathVariable("nombreArchivo") String nombreArchivo) {
		if (StringUtils.isBlank(nombreArchivo) || nombreArchivo.contains("..")
				|| nombreArchivo.contains("/") || nombreArchivo.contains("\\")) {
			return new ResponseEntity<Resource>(HttpStatus.BAD_REQUEST);
		}

		File archivo = new File(obtenerRutaBase(), nombreArchivo);
		if (!archivo.exists() || !archivo.isFile() || !archivo.canRead()) {
			return new ResponseEntity<Resource>(HttpStatus.NOT_FOUND);
		}

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(obtenerMediaType(archivo));
		headers.setContentLength(archivo.length());
		headers.set(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + archivo.getName() + "\"");

		return new ResponseEntity<Resource>(new FileSystemResource(archivo), headers, HttpStatus.OK);
	}

	private File obtenerRutaBase() {
		return ActualizacionCursoMoodleRutaUtil.obtenerDirectorioRespaldos(parametroSistemaService);
	}

	private MediaType obtenerMediaType(File archivo) {
		try {
			String mime = Files.probeContentType(archivo.toPath());
			if (StringUtils.isNotBlank(mime)) {
				return MediaType.parseMediaType(mime);
			}
		} catch (IOException e) {
			// fallback below
		}
		return MediaType.APPLICATION_OCTET_STREAM;
	}
}
