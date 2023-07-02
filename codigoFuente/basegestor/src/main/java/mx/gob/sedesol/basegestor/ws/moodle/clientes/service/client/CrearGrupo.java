/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sedesol.basegestor.ws.moodle.clientes.service.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.ResourceAccessException;

import com.fasterxml.jackson.core.type.TypeReference;

import mx.gob.sedesol.basegestor.commons.dto.admin.ParametroWSMoodleDTO;
import mx.gob.sedesol.basegestor.service.ParametroWSMoodleService;
import mx.gob.sedesol.basegestor.ws.moodle.clientes.model.entities.Grupo;
import mx.gob.sedesol.basegestor.ws.moodle.clientes.service.controller.WSClientBase;
import mx.gob.sedesol.basegestor.ws.moodle.clientes.service.util.ErrorWS;

/**
 *
 * @author robert o_O
 */
public class CrearGrupo {

	private ParametroWSMoodleDTO parametroWSMoodleDTO;
	
	public CrearGrupo(ParametroWSMoodleDTO plataforma){
		this.parametroWSMoodleDTO = plataforma;
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
        
return respuesta;
    }

}
