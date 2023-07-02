package mx.gob.sedesol.basegestor.ws.moodle.clientes.service.client;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.ResourceAccessException;

import mx.gob.sedesol.basegestor.commons.dto.admin.ParametroWSMoodleDTO;
import mx.gob.sedesol.basegestor.service.ParametroWSMoodleService;
import mx.gob.sedesol.basegestor.ws.moodle.clientes.model.entities.Pagina;
import mx.gob.sedesol.basegestor.ws.moodle.clientes.service.controller.WSClientBase;
import mx.gob.sedesol.basegestor.ws.moodle.clientes.service.util.ErrorWS;

public class CrearPagina {

	
	private ParametroWSMoodleDTO parametroWSMoodleDTO;
	
	public CrearPagina(ParametroWSMoodleDTO plataforma){
		this.parametroWSMoodleDTO = plataforma;
	}
	
	public String crearPagina(Pagina pagina) throws ErrorWS {
		String respuesta;
			HashMap<String, Object> paramMap = new HashMap<>();
			paramMap.put("parameters[course]", pagina.getCourse());
			paramMap.put("parameters[section]", pagina.getSection());
			paramMap.put("parameters[name]", pagina.getName());
			paramMap.put("parameters[intro]", pagina.getIntro());
			paramMap.put("parameters[content]", pagina.getContent());
			
			WSClientBase ws = new WSClientBase(parametroWSMoodleDTO);
			respuesta = ws.ejecutarServicioPOST("servicios_web_page_create_page", paramMap, pagina,
					String.class, pagina.getName());
		return respuesta;

	}
}
