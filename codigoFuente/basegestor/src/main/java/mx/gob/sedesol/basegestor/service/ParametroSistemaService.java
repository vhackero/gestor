package mx.gob.sedesol.basegestor.service;

import java.io.Serializable;
import java.util.List;

import mx.gob.sedesol.basegestor.commons.dto.admin.ParametroSistemaDTO;
import mx.gob.sedesol.basegestor.service.admin.CommonService;

public interface ParametroSistemaService extends CommonService<ParametroSistemaDTO, String>, Serializable {
	
	String obtenerParametro(String clave);

	/** IDs de tipos de baja para NP en actas, historial y constancias. */
	List<Integer> obtenerTiposBajaNp();
	
	String obtenerParametroRuta(String clave);
	
	String obtenerParametroConRutaCompleta(String clave);
	
	ParametroSistemaDTO obtieneParametroPorClave(String clave);
}
