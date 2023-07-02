package mx.gob.sedesol.basegestor.ws.moodle.clientes.service.client;

import java.util.HashMap;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.ResourceAccessException;

import mx.gob.sedesol.basegestor.commons.dto.admin.ParametroWSMoodleDTO;
import mx.gob.sedesol.basegestor.service.ParametroWSMoodleService;
import mx.gob.sedesol.basegestor.ws.moodle.clientes.model.entities.Glosario;
import mx.gob.sedesol.basegestor.ws.moodle.clientes.service.controller.WSClientBase;
import mx.gob.sedesol.basegestor.ws.moodle.clientes.service.util.ErrorWS;

public class CrearGlosario {
	
	private static final Logger logger = Logger.getLogger(CrearGlosario.class);
	
	private ParametroWSMoodleDTO parametroWSMoodleDTO;
	
	public CrearGlosario(ParametroWSMoodleDTO plataforma){
		this.parametroWSMoodleDTO = plataforma;
	}

	public String crearGlosario(Glosario g) throws ErrorWS {
		String respuesta;
			HashMap<String, Object> paramMap = new HashMap<>();
			paramMap.put("parameters[course]", g.getCourse());
			paramMap.put("parameters[section]", g.getSection());
			paramMap.put("parameters[name]", g.getName());
			paramMap.put("parameters[intro]", g.getIntro());
			WSClientBase ws = new WSClientBase(parametroWSMoodleDTO);
			respuesta = ws.ejecutarServicioPOST("servicios_web_glosarry_create_glosarry", paramMap, g,
					String.class, g.getCourse());
			logger.info(respuesta);
		return respuesta;
	}
}
