package mx.gob.sedesol.basegestor.service;

import java.io.Serializable;

import mx.gob.sedesol.basegestor.commons.dto.admin.ParametroSistemaDTO;
import mx.gob.sedesol.basegestor.service.admin.CommonService;

public interface ParametroSistemaService extends CommonService<ParametroSistemaDTO, String>, Serializable {
	
	String obtenerParametro(String clave);
	
	String obtenerParametroRuta(String clave);
	
	String obtenerParametroConRutaCompleta(String clave);
	
	ParametroSistemaDTO obtieneParametroPorClave(String clave);
}
