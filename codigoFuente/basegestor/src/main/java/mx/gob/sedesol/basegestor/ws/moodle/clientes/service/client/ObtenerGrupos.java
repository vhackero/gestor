/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sedesol.basegestor.ws.moodle.clientes.service.client;

import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
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
public class ObtenerGrupos {

	private static final Logger logger = Logger.getLogger(ObtenerGrupos.class);
	
	private ParametroWSMoodleDTO parametroWSMoodleDTO;
	
	public ObtenerGrupos(ParametroWSMoodleDTO plataforma){
		this.parametroWSMoodleDTO = plataforma;
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
