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
import mx.gob.sedesol.basegestor.ws.moodle.clientes.model.entities.MiembrosGrupo;
import mx.gob.sedesol.basegestor.ws.moodle.clientes.service.controller.WSClientBase;
import mx.gob.sedesol.basegestor.ws.moodle.clientes.service.util.ErrorWS;

/**
 *
 * @author robert o_O
 */
public class ObtenerUsuariosGrupo {
	
	private static final Logger logger = Logger.getLogger(ObtenerUsuariosGrupo.class);
	
	private ParametroWSMoodleDTO parametroWSMoodleDTO;
	
	public ObtenerUsuariosGrupo(ParametroWSMoodleDTO plataforma){
		this.parametroWSMoodleDTO = plataforma;
	}
	
	public  List<MiembrosGrupo>  ObtenerUsuariosdeGrupo(List<Integer> groupids) throws ErrorWS {
     
    List<MiembrosGrupo> respuesta;
            HashMap<String, Object> paramMap = new HashMap<>();
            int x = 0;
            for (int id : groupids) {
                paramMap.put("groupids[" + x + "]", id);
                x++;
            }

            WSClientBase ws = new WSClientBase(parametroWSMoodleDTO);
            respuesta = ws.ejecutarServicioListGET("core_group_get_group_members", paramMap, new TypeReference<  List<MiembrosGrupo>>() {
            }, groupids);
            logger.info(respuesta);
        
        return respuesta;
	}
}
