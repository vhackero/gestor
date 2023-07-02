/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sedesol.basegestor.ws.moodle.clientes.service.client;

import java.io.IOException;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.ResourceAccessException;

import com.fasterxml.jackson.databind.JsonMappingException;

import mx.gob.sedesol.basegestor.commons.dto.admin.ParametroWSMoodleDTO;
import mx.gob.sedesol.basegestor.service.ParametroWSMoodleService;
import mx.gob.sedesol.basegestor.ws.moodle.clientes.model.entities.Tablas;
import mx.gob.sedesol.basegestor.ws.moodle.clientes.service.controller.WSClientBase;
import mx.gob.sedesol.basegestor.ws.moodle.clientes.service.util.ErrorWS;

/**
 *
 * @author robert o_O
 */
public class ObtenerCalificacionesCursoTabla {
	
	private static final Logger logger = Logger.getLogger(ObtenerCalificacionesCursoTabla.class);

	private ParametroWSMoodleDTO parametroWSMoodleDTO;
	
	public ObtenerCalificacionesCursoTabla(ParametroWSMoodleDTO plataforma){
		this.parametroWSMoodleDTO = plataforma;
	}
	
     public Tablas obtenerCalificacionesporGrupoTablas(int courseid) throws ErrorWS {

        Tablas respuesta;
        try {
            HashMap<String, Object> paramMap = new HashMap<>();
            paramMap.put("courseid", courseid);
           
            WSClientBase ws = new WSClientBase(parametroWSMoodleDTO);
            respuesta = ws.ejecutarServicioGET("gradereport_user_get_grades_table", paramMap, Tablas.class);
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
}
