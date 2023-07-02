/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sedesol.basegestor.ws.moodle.clientes.service.client;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.web.client.ResourceAccessException;

import mx.gob.sedesol.basegestor.commons.dto.admin.ParametroWSMoodleDTO;
import mx.gob.sedesol.basegestor.ws.moodle.clientes.model.entities.AlumnoGrupo;
import mx.gob.sedesol.basegestor.ws.moodle.clientes.service.controller.WSClientBase;
import mx.gob.sedesol.basegestor.ws.moodle.clientes.service.util.ErrorWS;

/**
 *
 * @author robert o_O
 */
public class UsuariosGrupoWS implements Serializable {
	
	private static final Logger logger = Logger.getLogger(UsuariosGrupoWS.class);

	private ParametroWSMoodleDTO parametroWSMoodleDTO;
	
	public UsuariosGrupoWS(ParametroWSMoodleDTO plataforma){
		this.parametroWSMoodleDTO = plataforma;
	}
	
    /**
     * Cliente de servicio web utilizado para matricular un alumno(s) a un grupo
     * de un curso especifico
     *
     * @param members lista de elemntos a agrearb al grupo
     * @throws com.clientes.service.util.ErrorWS
     */
    public void agregarUsuariosGrupo(List<AlumnoGrupo> members) throws ErrorWS {
            HashMap<String, Object> paramMap = new HashMap<>();
            int x = 0;
            for (AlumnoGrupo alumno : members) {
                paramMap.put("members[" + x + "][groupid]", alumno.getGroupid());
                paramMap.put("members[" + x + "][userid]", alumno.getUserid());
                x++;
            }
            WSClientBase ws = new WSClientBase(parametroWSMoodleDTO);
            String respuesta = ws.ejecutarServicioPOST("core_group_add_group_members", paramMap, members, String.class, paramMap);
            logger.info(respuesta);
        
    }

    /**
     * Cliente de servicio web utilizado para desmatricular un alumno(s) a un
     * grupo de un curso especifico
     *
     * @param members lista de elemntos a agregar al grupo
     * @throws com.clientes.service.util.ErrorWS
     */
    public void quitarUsuariosGrupo(List<AlumnoGrupo> members) throws ErrorWS {
            HashMap<String, Object> paramMap = new HashMap<>();
            int x = 0;
            for (AlumnoGrupo alumno : members) {
                paramMap.put("members[" + x + "][groupid]", alumno.getGroupid());
                paramMap.put("members[" + x + "][userid]", alumno.getUserid());
                x++;
            }
            WSClientBase ws = new WSClientBase(parametroWSMoodleDTO);
            String respuesta = ws.ejecutarServicioPOST("core_group_delete_group_members", paramMap, members, String.class, paramMap);
            logger.info(respuesta);
        
    }
}
