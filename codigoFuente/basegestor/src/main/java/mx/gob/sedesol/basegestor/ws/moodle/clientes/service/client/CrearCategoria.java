package mx.gob.sedesol.basegestor.ws.moodle.clientes.service.client;

import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.web.client.ResourceAccessException;

import com.fasterxml.jackson.core.type.TypeReference;

import mx.gob.sedesol.basegestor.commons.dto.admin.ParametroWSMoodleDTO;
import mx.gob.sedesol.basegestor.ws.moodle.clientes.model.entities.Categoria;
import mx.gob.sedesol.basegestor.ws.moodle.clientes.model.entities.RespuestaCrearCategorias;
import mx.gob.sedesol.basegestor.ws.moodle.clientes.service.controller.WSClientBase;
import mx.gob.sedesol.basegestor.ws.moodle.clientes.service.util.ErrorWS;

public class CrearCategoria {
	
	
	
	private ParametroWSMoodleDTO parametroWSMoodleDTO;
	
	public CrearCategoria(ParametroWSMoodleDTO plataforma){
		this.parametroWSMoodleDTO = plataforma;
	}

	public List<RespuestaCrearCategorias> crearCategoria(List<Categoria> categorias) throws ErrorWS {
		List<RespuestaCrearCategorias> respuesta;
			HashMap<String, Object> paramMap = new HashMap<>();
			int x = 0;
			for (Categoria cat : categorias) {
				paramMap.put("categories[" + x + "][name]", cat.getName());
				 paramMap.put("categories[" + x + "][parent]",
				 cat.getParent());
				paramMap.put("categories[" + x + "][idnumber]", cat.getIdnumber());
				paramMap.put("categories[" + x + "][description]", cat.getDescription());
				paramMap.put("categories[" + x + "][descriptionformat]", cat.getDescriptionformat());
				paramMap.put("categories[" + x + "][theme]", cat.getTheme());
				x++;
			}
			WSClientBase ws = new WSClientBase(parametroWSMoodleDTO);
			respuesta = ws.ejecutarServicioListPOST("core_course_create_categories", paramMap,
					categorias.get(0), new TypeReference<List<RespuestaCrearCategorias>>() {}, categorias);
			
		
		return respuesta;
	}
	
}
