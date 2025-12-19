/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sedesol.basegestor.ws.moodle.clientes.service.client;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import java.net.URI;

import com.fasterxml.jackson.core.type.TypeReference;

import mx.gob.sedesol.basegestor.commons.dto.admin.ParametroWSMoodleDTO;
import mx.gob.sedesol.basegestor.ws.moodle.clientes.model.entities.Curso;
import mx.gob.sedesol.basegestor.ws.moodle.clientes.model.entities.EnrolMents;
import mx.gob.sedesol.basegestor.ws.moodle.clientes.model.entities.Usuario;
import mx.gob.sedesol.basegestor.ws.moodle.clientes.model.entities.Usuarios;
import mx.gob.sedesol.basegestor.ws.moodle.clientes.service.controller.WSClientBase;
import mx.gob.sedesol.basegestor.ws.moodle.clientes.service.util.ErrorWS;
import mx.gob.sedesol.basegestor.ws.moodle.clientes.service.util.StaticsConstants;
import mx.gob.sedesol.basegestor.ws.moodle.clientes.service.util.TokenController;
import mx.gob.sedesol.basegestor.ws.moodle.clientes.service.util.URIBuilder;

public class UsuarioWSClient implements Serializable {
	
	private static final Logger logger = Logger.getLogger(UsuarioWSClient.class);
	
	private ParametroWSMoodleDTO parametroWSMoodleDTO;
	
	public UsuarioWSClient(ParametroWSMoodleDTO plataforma){
		this.parametroWSMoodleDTO = plataforma;
	}

    /**
     * Cliente de servicio web utilizado para obtener a los alumnos enrolados a
     * un curso especifico
     *
     * @param idCurso numero identificador del curso
     * @return
     */
    public List<Usuario> listarUsuariosPorCurso(String idCurso) throws ErrorWS {

        List<Usuario> list;//= om.readValue(json, new TypeReference<List<Usuario>>(){});

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("courseid", idCurso);

        WSClientBase ws = new WSClientBase(parametroWSMoodleDTO);
        list = ws.ejecutarServicioListPOST("core_enrol_get_enrolled_users", paramMap, idCurso, new TypeReference<List<Usuario>>() {}, idCurso);
        logger.info(list);
        return list;
    }
    

    public void inscribirUsuariosCurso(List<EnrolMents> enrolments) throws ErrorWS {
        HashMap<String, Object> paramMap = new HashMap<>();
        int x = 0;
        for (EnrolMents usuario : enrolments) {
            paramMap.put("enrolments[" + x + "][roleid]", usuario.getRoleid());
            paramMap.put("enrolments[" + x + "][userid]", usuario.getUserid());
            paramMap.put("enrolments[" + x + "][courseid]", usuario.getCourseid());
            paramMap.put("enrolments[" + x + "][timestart]", (int) (new Date().getTime() / 1000));
            
            WSClientBase ws = new WSClientBase(parametroWSMoodleDTO);
            EnrolMents respuesta = ws.ejecutarServicioPOST("enrol_manual_enrol_users", paramMap, enrolments.get(x), EnrolMents.class, enrolments);
            x++;
        }
    }
    
    public void eliminarUsuariosCurso(List<EnrolMents> enrolments) throws ErrorWS {
        HashMap<String, Object> paramMap = new HashMap<>();
        int x = 0;
        for (EnrolMents usuario : enrolments) {
            paramMap.put("enrolments[" + x + "][roleid]", usuario.getRoleid());
            paramMap.put("enrolments[" + x + "][userid]", usuario.getUserid());
            paramMap.put("enrolments[" + x + "][courseid]", usuario.getCourseid());
            WSClientBase ws = new WSClientBase(parametroWSMoodleDTO);
            EnrolMents respuesta = ws.ejecutarServicioPOST("enrol_manual_unenrol_users", paramMap, enrolments.get(x), EnrolMents.class, enrolments);
            x++;
        }
    }
    
    public int crearUsuario(Usuario usuario) throws ErrorWS{
    	HashMap<String, Object> paramMap = new HashMap<>();
        int x = 0;
        //for (EnrolMents usuario : enrolments) {
            paramMap.put("users[" + x + "][username]", usuario.getUsername().toLowerCase());
            paramMap.put("users[" + x + "][password]", usuario.getPassword());
            paramMap.put("users[" + x + "][firstname]", usuario.getFirstname());
            paramMap.put("users[" + x + "][lastname]", usuario.getLastname());
            paramMap.put("users[" + x + "][email]", usuario.getEmail());
            paramMap.put("users[" + x + "][idnumber]", usuario.getIdnumber());
            WSClientBase ws = new WSClientBase(parametroWSMoodleDTO);
            List<Usuario> usuarios  = ws.ejecutarServicioListPOST("core_user_create_users", paramMap, usuario, new TypeReference<List<Usuario>>() {}, usuario);
            x++;
            if(usuarios!=null && usuarios.size()>0){
            	return usuarios.get(0).getId();
            }
            throw new ErrorWS("La respuesta del servicio fue inesperada");
        //}
        
    }
    
    
    public boolean actualizarUsuario(Usuario usuario) throws ErrorWS{
        HashMap<String, Object> paramMap = new HashMap<>();
        int x = 0;
        //for (EnrolMents usuario : enrolments) {
                paramMap.put("users[" + x + "][id]", usuario.getId());
        	if(usuario.getUsername()!=null){
        		paramMap.put("users[" + x + "][username]", usuario.getUsername().toLowerCase());
        	}
        	if(usuario.getPassword()!=null){
        		paramMap.put("users[" + x + "][password]", usuario.getPassword());
        	}
        	if(usuario.getFirstname()!=null){
        		paramMap.put("users[" + x + "][firstname]", usuario.getFirstname());
        	}
        	if(usuario.getLastname()!=null){
        		paramMap.put("users[" + x + "][lastname]", usuario.getLastname());
        	}
                if(usuario.getEmail()!=null){
                        paramMap.put("users[" + x + "][email]", usuario.getEmail());
                }
                if(usuario.getIdnumber()!=null){
                        paramMap.put("users[" + x + "][idnumber]", usuario.getIdnumber());
                }
            WSClientBase ws = new WSClientBase(parametroWSMoodleDTO);
            Integer salida  = ws.ejecutarServicioPOST("core_user_update_users", paramMap, null, Integer.class);
            System.out.println(salida);;
            return true;
        //}
        
    }

    public boolean actualizarUsuarioSuspender(Usuario usuario) throws ErrorWS{
        HashMap<String, Object> paramMap = new HashMap<>();
        int x = 0;
        //for (EnrolMents usuario : enrolments) {
                paramMap.put("users[" + x + "][id]", usuario.getId());

                if(usuario.getSuspended()!=null){
                        paramMap.put("users[" + x + "][suspended]", usuario.getSuspended());
                }
            WSClientBase ws = new WSClientBase(parametroWSMoodleDTO);
            Integer salida  = ws.ejecutarServicioPOST("core_user_update_users", paramMap, null, Integer.class);
            System.out.println(salida);;
            return true;
        //}

    }

    public boolean suspenderUsuarioDefinitivo(Integer idUsuarioMoodle) throws ErrorWS {
        try {
            HashMap<String, Object> paramMap = new HashMap<>();
            int x = 0;
            paramMap.put("users[" + x + "][id]", idUsuarioMoodle);
            paramMap.put("users[" + x + "][suspended]", 1);

            TokenController token = new TokenController(parametroWSMoodleDTO);
            HashMap<String, Object> paramsLog = new HashMap<>();
            paramsLog.put("wsfunction", "core_user_update_users");
            paramsLog.put("moodlewsrestformat", "json");
            paramsLog.putAll(paramMap);
            paramsLog.put(StaticsConstants.ACCESS_TOKEN, token.getAccessToken());

            URIBuilder uriBuilder = new URIBuilder(parametroWSMoodleDTO);
            URI uriTotal = uriBuilder.buildWSUri(parametroWSMoodleDTO.getServer(), paramsLog);
            logger.info("URL suspensión usuario Moodle: " + uriTotal.toString());

            WSClientBase ws = new WSClientBase(parametroWSMoodleDTO);

            // En lugar de esperar Integer, manejemos la respuesta como String primero
            String respuesta = ws.ejecutarServicioPOST("core_user_update_users", paramMap, null, String.class);

            // Verificar si es la respuesta de éxito de Moodle
            if (respuesta != null && respuesta.trim().equals("{\"warnings\":[]}")) {
                logger.info("Usuario suspendido exitosamente en Moodle");
                return true;
            }

            // Intentar parsear como Integer si no es warnings[]
            try {
                Integer salida = Integer.parseInt(respuesta);
                return salida != null;
            } catch (NumberFormatException e) {
                logger.warn("Respuesta no numérica de Moodle: " + respuesta);
                return false;
            }

        } catch (ErrorWS e) {
            // Si el error es por warnings[], considerarlo éxito
            if (e.getMessage() != null && e.getMessage().contains("warnings")) {
                logger.info("Moodle devolvió warnings vacíos - suspensión exitosa");
                return true;
            }
            throw e;
        }
    }
    
     
    
    public int existeNombreUsuario(String username) throws ErrorWS{
    	
            List<Usuario> usuarios  = this.existeUsuario("username", username).getUsers();
            if(usuarios!=null && usuarios.size()>0){
            	return usuarios.get(0).getId();
            }
            return 0;
        
    }
    
    public int existeCorreo(String email) throws ErrorWS{
    	
        List<Usuario> usuarios  = this.existeUsuario("email", email).getUsers();
        if(usuarios!=null && usuarios.size()>0){
        	return usuarios.get(0).getId();
        }
        return 0;
    
}
    
    private Usuarios existeUsuario(String key, String value) throws ErrorWS{
    	HashMap<String, Object> paramMap = new HashMap<>();
        int x = 0;
        //for (EnrolMents usuario : enrolments) {
            paramMap.put("criteria[" + x + "][key]", key);
            paramMap.put("criteria[" + x + "][value]", value);
            WSClientBase ws = new WSClientBase(parametroWSMoodleDTO);
            Usuarios usuarios  = ws.ejecutarServicioPOST("core_user_get_users", paramMap, null, Usuarios.class);
            return usuarios;
        //}
        
    }
    
    public static void main(String[] args){
		
		Usuario u = new Usuario();
		u.setId(3);
		//u.setUsername("elearning");
		//u.setPassword("M@c88uco");
		u.setEmail("no@no3.com");
		//u.setFirstname("Juan 2");
		//u.setLastname("Muñoz 2");
		//u.setIdnumber("X");
		
		ParametroWSMoodleDTO p = new ParametroWSMoodleDTO();
		//p.setHost("http://189.206.122.67/sedesol/plataformaEducativa");
		p.setHost("http://localhost/plataformaEducativa");
		p.setPath("/oauth/token");
		//p.setService("todos");
		p.setService("prueba");
		p.setOuth("/login/token.php");
		p.setUsername("admin");
		//p.setPassword("elearning");
		
		p.setPassword("M@cuco88");
		p.setServer("/webservice/rest/server.php");
		
		try {
			boolean b = new UsuarioWSClient(p).actualizarUsuario(u);
			System.out.println(b);
		} catch (ErrorWS e) {
			// TODO Auto-generated catch block
			//e.getCause().printStackTrace();
			e.printStackTrace();
			System.out.println(e.getJson());
		}
	}
}
