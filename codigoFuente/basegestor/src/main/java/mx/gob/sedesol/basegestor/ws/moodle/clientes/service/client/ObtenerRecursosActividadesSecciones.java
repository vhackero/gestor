/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sedesol.basegestor.ws.moodle.clientes.service.client;

import java.io.IOException;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.ResourceAccessException;

import mx.gob.sedesol.basegestor.commons.dto.admin.ParametroWSMoodleDTO;
import mx.gob.sedesol.basegestor.service.ParametroWSMoodleService;
import mx.gob.sedesol.basegestor.ws.moodle.clientes.model.entities.Curso;
import mx.gob.sedesol.basegestor.ws.moodle.clientes.model.entities.CursoSeccion;
import mx.gob.sedesol.basegestor.ws.moodle.clientes.service.controller.WSClientBase;
import mx.gob.sedesol.basegestor.ws.moodle.clientes.service.util.ErrorWS;

/**
 *
 * @author robert o_O
 */
public class ObtenerRecursosActividadesSecciones {

	private static final Logger logger = Logger.getLogger(ObtenerRecursosActividadesSecciones.class);
	
	private ParametroWSMoodleDTO parametroWSMoodleDTO;
	
	public ObtenerRecursosActividadesSecciones(ParametroWSMoodleDTO plataforma){
		this.parametroWSMoodleDTO = plataforma;
	}
    
    public CursoSeccion ObtenerRecursosActividadesSeccion(int courseid) throws ErrorWS {
        CursoSeccion respuesta ;
        try{
          HashMap<String, Object> paramMap = new HashMap<>();
            paramMap.put("courseid", courseid);
            
            WSClientBase ws = new WSClientBase(parametroWSMoodleDTO);
             respuesta = ws.ejecutarServicioGET("servicios_web_resource_get_section", paramMap, CursoSeccion.class);
            logger.info(respuesta);
        } catch (ResourceAccessException ex) {
        	logger.error(ex);
            throw new ErrorWS(ex.getMessage(), ex.getCause());
        } catch (JsonMappingException ex) {
        	logger.error(ex);
            throw new ErrorWS(ex.getMessage(), ex.getCause());
        } catch (IOException ex) {
        	logger.error(ex);
            throw new ErrorWS(ex.getMessage(), ex.getCause());
        }
        return respuesta;
    
    }
    public Curso obtenerRecursosActividades(int cursoId) throws ErrorWS {
        Curso respuesta;
        try {
            HashMap<String, Object> paramMap = new HashMap<>();
            paramMap.put("course_id", cursoId);
            WSClientBase ws = new WSClientBase(parametroWSMoodleDTO);
            respuesta = ws.ejecutarServicioGET("servicios_web_resource_get_activity", paramMap, Curso.class);
        } catch (ResourceAccessException ex) {
        	logger.error(ex);
            throw new ErrorWS(ex.getMessage(), ex.getCause());
        } catch (JsonMappingException ex) {
        	logger.error(ex);
            throw new ErrorWS(ex.getMessage(), ex.getCause());
        } catch (IOException ex) {
        	logger.error(ex);
            throw new ErrorWS(ex.getMessage(), ex.getCause());
        }
        return respuesta;
    }
}
