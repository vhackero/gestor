package mx.gob.sedesol.basegestor.ws.moodle.clientes.service.client;


import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.web.client.ResourceAccessException;

import com.fasterxml.jackson.core.type.TypeReference;

import mx.gob.sedesol.basegestor.commons.dto.admin.ParametroWSMoodleDTO;
import mx.gob.sedesol.basegestor.ws.moodle.clientes.model.entities.Categoria;
import mx.gob.sedesol.basegestor.ws.moodle.clientes.model.entities.Criterio;
import mx.gob.sedesol.basegestor.ws.moodle.clientes.service.controller.WSClientBase;
import mx.gob.sedesol.basegestor.ws.moodle.clientes.service.util.ErrorWS;

public class ObtenerCategorias {
	
	private static final Logger logger = Logger.getLogger(ObtenerCategorias.class);

	private ParametroWSMoodleDTO parametroWSMoodleDTO;
	
	public ObtenerCategorias(ParametroWSMoodleDTO plataforma){
		this.parametroWSMoodleDTO = plataforma;
	}

	public void buscarCategoria(List<Criterio> criterios) throws ErrorWS {
		List<Categoria> respuesta;
			HashMap<String, Object> paramMap = new HashMap<>();
			int x = 0;
			for (Criterio c : criterios) {
				paramMap.put("criteria[" + x + "][key]", c.getKey());
				paramMap.put("criteria[" + x + "][value]", c.getValue());
				x++;
			}

			WSClientBase ws = new WSClientBase(parametroWSMoodleDTO);
			respuesta = ws.ejecutarServicioListPOST("core_course_get_categories", paramMap, criterios.get(0),
					new TypeReference<List<Categoria>>() {
					}, criterios);

			logger.info(respuesta);
		
	}
	
	
	 public void borrarCategoría(Categoria category) throws ErrorWS {
	        String respuesta;
	            HashMap<String, Object> paramMap = new HashMap<>();
	            paramMap.put("categories[0][id]", category.getId());
	            paramMap.put("categories[0][newparent]", category.getNewparent());
	            paramMap.put("categories[0][recursive]", category.getRecursive());
	            WSClientBase ws = new WSClientBase(parametroWSMoodleDTO);
	            respuesta = ws.ejecutarServicioPOST("core_course_delete_categories", paramMap, "", String.class, category);
	            logger.info(respuesta);
	        
	    }
}
