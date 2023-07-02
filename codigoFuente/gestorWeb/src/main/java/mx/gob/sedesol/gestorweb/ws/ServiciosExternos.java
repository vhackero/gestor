package mx.gob.sedesol.gestorweb.ws;

import org.apache.log4j.Logger;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import mx.gob.sedesol.basegestor.commons.dto.admin.PersonaDTO;
import mx.gob.sedesol.basegestor.commons.utils.DateUtils;
import mx.gob.sedesol.gestorweb.commons.dto.ServicioCURP;

public class ServiciosExternos {

	private static final Logger logger = Logger.getLogger(ServiciosExternos.class);

	public static final String RUTA_SERVICIO_CURP_RENAPO = "http://172.25.39.44:7013/SoapServices/resources/renapo/query/";
	public static final String RUTA_SERVICIO_DETALLE_RENAPO = "http://172.25.39.44:7013/SoapServiceDetalle/webresources/renapo/query/";

	public static ServicioCURP obtenerDatosPersona(String curp) {
		ServicioCURP servicio;
		RestTemplate restTemplate = new RestTemplate(getClientHttpRequestFactory());

		try {
			servicio = restTemplate.getForObject(RUTA_SERVICIO_CURP_RENAPO + curp, ServicioCURP.class);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			servicio = null;
		}
		return servicio;
	}

	public static Boolean validarDatosPersona(PersonaDTO persona) {
		Boolean valido;
		RestTemplate restTemplate = new RestTemplate(getClientHttpRequestFactory());

		try {
			ServicioCURP servicio = restTemplate.getForObject(
					RUTA_SERVICIO_DETALLE_RENAPO + persona.getApellidoPaterno() + "/" + persona.getApellidoMaterno()
							+ "/" + persona.getNombre() + "/" + persona.getGenero() + "/"
							+ DateUtils.convierteDateAString(persona.getFechaNacimiento(), "dd-MM-yyyy") + "/29",
					ServicioCURP.class);
			valido = persona.getCurp().equalsIgnoreCase(servicio.getResponse().getCurpRespuesta());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			valido = null;
		}
		return valido;
	}

	private static ClientHttpRequestFactory getClientHttpRequestFactory() {
		int numeroSegundos = 4;
		HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
		factory.setReadTimeout(numeroSegundos * 1000);
		factory.setConnectTimeout(numeroSegundos * 1000);
		return factory;
	}

}
