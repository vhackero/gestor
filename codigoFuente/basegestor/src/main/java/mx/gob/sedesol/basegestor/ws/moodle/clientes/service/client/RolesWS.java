package mx.gob.sedesol.basegestor.ws.moodle.clientes.service.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import mx.gob.sedesol.basegestor.commons.dto.admin.ParametroWSMoodleDTO;
import mx.gob.sedesol.basegestor.ws.moodle.clientes.model.entities.Enrol;
import mx.gob.sedesol.basegestor.ws.moodle.clientes.service.controller.WSClientBase;
import mx.gob.sedesol.basegestor.ws.moodle.clientes.service.util.ErrorWS;

public class RolesWS {

	private static final Logger logger = Logger.getLogger(RolesWS.class);
	
	private ParametroWSMoodleDTO parametroWSMoodleDTO;

	
	public RolesWS(ParametroWSMoodleDTO plataforma){
		this.parametroWSMoodleDTO = plataforma;
	}
	
	/**
	 * Servicio para enrolar una lista de usuarios como estudiantes en un curso
	 * @param enrols
	 * @throws ErrorWS 
	 */
	public boolean enrolarEstudiantes(List<Enrol> enrols) throws ErrorWS{
            HashMap<String, Object> paramMap = new HashMap<>();
            int x = 0;
            for (Enrol enrol : enrols) {
                paramMap.put("enrolments[" + x + "][roleid]", 5);
                paramMap.put("enrolments[" + x + "][userid]", enrol.getUserId());
                paramMap.put("enrolments[" + x + "][courseid]", enrol.getCourseId());
                //paramMap.put("enrolments[" + x + "][timestart]", enrol.getTimestart());
                //paramMap.put("enrolments[" + x + "][timeend]", enrol.getTimeend());
                x++;
            }
            WSClientBase ws = new WSClientBase(parametroWSMoodleDTO);
            String respuesta = ws.ejecutarServicioPOST("enrol_manual_enrol_users", paramMap, enrols, String.class, paramMap);
            logger.info(respuesta);
            return true;
	}
	
	/**
	 * Servicio para enrolar una lista de usuarios como profesor sin permisos de edicion en un curso
	 * @param enrols
	 * @throws ErrorWS 
	 */
	public boolean enrolarProfesorSinPermisos(List<Enrol> enrols) throws ErrorWS{
            HashMap<String, Object> paramMap = new HashMap<>();
            int x = 0;
            for (Enrol enrol : enrols) {
                paramMap.put("enrolments[" + x + "][roleid]", 4);
                paramMap.put("enrolments[" + x + "][userid]", enrol.getUserId());
                paramMap.put("enrolments[" + x + "][courseid]", enrol.getCourseId());
                //paramMap.put("enrolments[" + x + "][timestart]", enrol.getTimestart());
                //paramMap.put("enrolments[" + x + "][timeend]", enrol.getTimeend());
                x++;
            }
            WSClientBase ws = new WSClientBase(parametroWSMoodleDTO);
            String respuesta = ws.ejecutarServicioPOST("enrol_manual_enrol_users", paramMap, enrols, String.class, paramMap);
            System.out.println(respuesta);
            return true;
	}
	
	public boolean enrolarProfesor(List<Enrol> enrols) throws ErrorWS{
        HashMap<String, Object> paramMap = new HashMap<>();
        int x = 0;
        for (Enrol enrol : enrols) {
            paramMap.put("enrolments[" + x + "][roleid]", 3);
            paramMap.put("enrolments[" + x + "][userid]", enrol.getUserId());
            paramMap.put("enrolments[" + x + "][courseid]", enrol.getCourseId());
            //paramMap.put("enrolments[" + x + "][timestart]", enrol.getTimestart());
            //paramMap.put("enrolments[" + x + "][timeend]", enrol.getTimeend());
            x++;
        }
        WSClientBase ws = new WSClientBase(parametroWSMoodleDTO);
        String respuesta = ws.ejecutarServicioPOST("enrol_manual_enrol_users", paramMap, enrols, String.class, paramMap);
        System.out.println(respuesta);
        return true;
	}
	
	/**
	 * Servicio para quitar los roles de moodle de un curso en espesifico el rol es opcional
	 * @param enrols
	 * @throws ErrorWS
	 */
	public boolean desEnrolar(List<Enrol> enrols) throws ErrorWS{
        HashMap<String, Object> paramMap = new HashMap<>();
        int x = 0;
        for (Enrol enrol : enrols) {
            //paramMap.put("enrolments[" + x + "][roleid]", enrol.getRoleId());
            paramMap.put("enrolments[" + x + "][userid]", enrol.getUserId());
            paramMap.put("enrolments[" + x + "][courseid]", enrol.getCourseId());
            x++;
        }
        WSClientBase ws = new WSClientBase(parametroWSMoodleDTO);
        String respuesta = ws.ejecutarServicioPOST("enrol_manual_unenrol_users", paramMap, enrols, String.class, paramMap);
        System.out.println(respuesta);
        return true;
	}
	
	
	public boolean esEnrolado(int userid, int courseid) throws ErrorWS{
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("userid", userid);
        paramMap.put("courseid", courseid);
        WSClientBase ws = new WSClientBase(parametroWSMoodleDTO);
        Boolean respuesta = ws.ejecutarServicioPOST("local_role_is_user_enrolled", paramMap, null, Boolean.class, paramMap);
        System.out.println(respuesta);
        return respuesta;
	}
	
	
public static void main(String[] args){
		
		Enrol enrol = new Enrol();
		enrol.setUserId(53);
		enrol.setCourseId(27);
		/*Enrol enrol2 = new Enrol();
		enrol2.setUserId(33);
		enrol2.setCourseId(27);*/
		
		List enrols = new ArrayList<Enrol>();
		enrols.add(enrol);
		//enrols.add(enrol2);
		
		
		
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
			boolean r = new RolesWS(p).esEnrolado(2, 2);
			System.out.println(r);
		} catch (ErrorWS e) {
			// TODO Auto-generated catch block
			//e.getCause().printStackTrace();
			e.printStackTrace();
			System.out.println(e.getJson());
		}
	}
 

}
