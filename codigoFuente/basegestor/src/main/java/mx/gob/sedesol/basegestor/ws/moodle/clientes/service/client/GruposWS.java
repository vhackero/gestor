package mx.gob.sedesol.basegestor.ws.moodle.clientes.service.client;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.web.client.ResourceAccessException;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;

import mx.gob.sedesol.basegestor.commons.dto.admin.ParametroWSMoodleDTO;
import mx.gob.sedesol.basegestor.ws.moodle.clientes.model.entities.Calificaciones;
import mx.gob.sedesol.basegestor.ws.moodle.clientes.model.entities.Grupo;
import mx.gob.sedesol.basegestor.ws.moodle.clientes.service.controller.WSClientBase;
import mx.gob.sedesol.basegestor.ws.moodle.clientes.service.util.ErrorWS;

public class GruposWS {
	
	private static final Logger logger = Logger.getLogger(GruposWS.class);
	
private ParametroWSMoodleDTO parametroWSMoodleDTO;
	
	public GruposWS(ParametroWSMoodleDTO plataforma){
		this.parametroWSMoodleDTO = plataforma;
	}

	
    public String borrarGrupo(List<Integer> ids) throws ErrorWS {
        String respuesta;
            HashMap<String, Object> paramMap = new HashMap<>();
            int x = 0;
            for (int id : ids) {
                paramMap.put("groupids[" + x + "]", id);
                x++;
            }
            WSClientBase ws = new WSClientBase(parametroWSMoodleDTO);
            respuesta = ws.ejecutarServicioPOST("core_group_delete_groups", paramMap, null, String.class);
            logger.info(respuesta);
        return respuesta;
    }
    
    public List<Grupo> crearGrupos(List<Grupo> grupos) throws ErrorWS {
    	 List<Grupo> respuesta;
    	            HashMap<String, Object> paramMap = new HashMap<>();
    	            int x = 0;
    	            for (Grupo g : grupos) {
    	                paramMap.put("groups[" + x + "][courseid]", g.getCourseid());
    	                paramMap.put("groups[" + x + "][name]", g.getName());
    	                paramMap.put("groups[" + x + "][description]", g.getDescription());
    	                paramMap.put("groups[" + x + "][descriptionformat]", g.getDescriptionformat());
    	                paramMap.put("groups[" + x + "][enrolmentkey]", g.getEnrolmentkey());
    	                paramMap.put("groups[" + x + "][idnumber]", g.getIdnumber());
    	                             x++;
    	            }
    	            WSClientBase ws = new WSClientBase(parametroWSMoodleDTO);
    	           respuesta = ws.ejecutarServicioListPOST("core_group_create_groups", paramMap, grupos, new TypeReference<List<Grupo>>() {
    	            }, paramMap);
    	           logger.info(respuesta);
    	       
    	return respuesta;
    	    }
    
    
    
    /**
     * Metodo para consumir el WS ObtenerCalificacionesGrupo:local_grades_get_grades_group
     * @param courseid  ID del curso a consultar
     * @param groupid   ID del grupo a consultar
     * @return Calificaciones  Objeto
     * @throws ErrorWS 
     */
    
    public Calificaciones obtenerCalificacionesporGrupo(int courseid, int groupid) throws ErrorWS {

        Calificaciones respuesta = null;
        try {
            HashMap<String, Object> paramMap = new HashMap<>();
            paramMap.put("courseid", courseid);
            paramMap.put("groupid", groupid);
            WSClientBase ws = new WSClientBase(parametroWSMoodleDTO);
            respuesta = ws.ejecutarServicioGET("local_grades_get_grades_group", paramMap, Calificaciones.class);
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
    
    
    /**
    *
    * @param ids lista de id a consultar
    * @return
    * @throws ErrorWS
    */
   public List<Grupo> obtenerGrupos(List<Integer> ids) throws ErrorWS {
       List<Grupo> respuesta= null;
           HashMap<String, Object> paramMap = new HashMap<>();
           int x = 0;
           for (int id : ids) {
               paramMap.put("groupids[" + x + "]", id);
               x++;
           }
           WSClientBase ws = new WSClientBase(parametroWSMoodleDTO);
           respuesta = ws.ejecutarServicioListGET("core_group_get_groups", paramMap, new TypeReference<List<Grupo>>() {
           }, ids);
           logger.info(respuesta);
       
       return respuesta;
   }

}
