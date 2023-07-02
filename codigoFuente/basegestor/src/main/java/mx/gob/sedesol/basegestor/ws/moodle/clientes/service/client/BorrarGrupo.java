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

import mx.gob.sedesol.basegestor.commons.dto.admin.ParametroWSMoodleDTO;
import mx.gob.sedesol.basegestor.service.ParametroWSMoodleService;
import mx.gob.sedesol.basegestor.ws.moodle.clientes.service.controller.WSClientBase;
import mx.gob.sedesol.basegestor.ws.moodle.clientes.service.util.ErrorWS;

/**
 *
 * @author robert o_O
 */
public class BorrarGrupo {
	
	private ParametroWSMoodleDTO parametroWSMoodleDTO;
	
	public BorrarGrupo(ParametroWSMoodleDTO plataforma){
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
            
        return respuesta;
    }
}
