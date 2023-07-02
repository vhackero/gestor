package mx.gob.sedesol.basegestor.ws.moodle.clientes.service.client;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.ResourceAccessException;

import mx.gob.sedesol.basegestor.commons.dto.admin.ParametroWSMoodleDTO;
import mx.gob.sedesol.basegestor.service.ParametroWSMoodleService;
import mx.gob.sedesol.basegestor.ws.moodle.clientes.model.entities.Leccion;
import mx.gob.sedesol.basegestor.ws.moodle.clientes.service.controller.WSClientBase;
import mx.gob.sedesol.basegestor.ws.moodle.clientes.service.util.ErrorWS;

public class CrearLeccion {

	
	private ParametroWSMoodleDTO parametroWSMoodleDTO;
	
	public CrearLeccion(ParametroWSMoodleDTO plataforma){
		this.parametroWSMoodleDTO = plataforma;
	}
	
	public String crearLeccion(Leccion leccion) throws ErrorWS {
		String respuesta;
			HashMap<String, Object> paramMap = new HashMap<>();
			paramMap.put("parameters[course]", leccion.getCourse());
			paramMap.put("parameters[section]", leccion.getSection());
			paramMap.put("parameters[name]", leccion.getName());
			paramMap.put("parameters[intro]", leccion.getIntro());
			WSClientBase ws = new WSClientBase(parametroWSMoodleDTO);
			respuesta = ws.ejecutarServicioPOST("servicios_web_lesson_create_lesson", paramMap, leccion,
					String.class, leccion.getCourse());
		
		return respuesta;

	}
}
