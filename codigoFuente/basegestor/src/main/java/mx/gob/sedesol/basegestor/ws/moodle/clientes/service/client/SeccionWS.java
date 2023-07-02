package mx.gob.sedesol.basegestor.ws.moodle.clientes.service.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.core.type.TypeReference;

import mx.gob.sedesol.basegestor.commons.dto.admin.ParametroWSMoodleDTO;
import mx.gob.sedesol.basegestor.ws.moodle.clientes.model.entities.Enrol;
import mx.gob.sedesol.basegestor.ws.moodle.clientes.model.entities.Seccion;
import mx.gob.sedesol.basegestor.ws.moodle.clientes.service.controller.WSClientBase;
import mx.gob.sedesol.basegestor.ws.moodle.clientes.service.util.ErrorWS;

public class SeccionWS {
	
	private static final Logger logger = Logger.getLogger(SeccionWS.class);
	
	private ParametroWSMoodleDTO parametroWSMoodleDTO;
	
	public SeccionWS(ParametroWSMoodleDTO plataforma){
		this.parametroWSMoodleDTO = plataforma;
	}
	
	public String registrarSeccionCurso(int opcion, int idCurso) throws ErrorWS{
        String resp;
        
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("courseid", idCurso);
        paramMap.put("increase", opcion);
        WSClientBase ws = new WSClientBase(parametroWSMoodleDTO);
        resp = ws.ejecutarServicioPOST("local_change_numsections", paramMap, "", String.class, opcion, idCurso);
        return resp;
    }
	
	
	public List<Seccion> obtenerSeccion(int idCurso, int idSeccion) throws ErrorWS{
        List<Seccion> list;
        
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("courseid", idCurso);
        paramMap.put("sectionid", idSeccion);
        WSClientBase ws = new WSClientBase(parametroWSMoodleDTO);
        list = ws.ejecutarServicioListPOST("local_get_section", paramMap, "", new TypeReference<List<Seccion>>() {}, idCurso, idSeccion);
        return list;
    }
	
	
	  public void actualizarSeccion(List<Seccion> secciones) throws ErrorWS{
	        
	        HashMap<String, Object> paramMap = new HashMap<>();
	        int x = 0;
	        
	        for (Seccion seccion : secciones) {
	            paramMap.put("sections[" + x + "][id]", seccion.getId());
	            paramMap.put("sections[" + x + "][course]", seccion.getCourse());
	            paramMap.put("sections[" + x + "][name]", seccion.getName());
	            paramMap.put("sections[" + x + "][summary]", seccion.getSummary());
	            //paramMap.put("sections[" + x + "][summaryformat]", seccion.getSummaryformat());
	            //paramMap.put("sections[" + x + "][usedefaultname]", seccion.getUsedefaultname());
	            //paramMap.put("sections[" + x + "][visible]", seccion.getVisible());
	            //paramMap.put("sections[" + x + "][sequence]", seccion.getSequence());
	            
	            WSClientBase ws = new WSClientBase(parametroWSMoodleDTO);
	            
	            String respuesta = ws.ejecutarServicioPOST("local_update_sections", paramMap, secciones.get(x), String.class, secciones);
	            logger.info(respuesta);
	            x++;
	        }
	    }
	  
	  public boolean actualizarSeccionSecuencia(List<Seccion> secciones) throws ErrorWS{
	        
	        HashMap<String, Object> paramMap = new HashMap<>();
	        int x = 0;
	        Boolean respuesta = false;
	        for (Seccion seccion : secciones) {
	            paramMap.put("sections[" + x + "][id]", seccion.getId());
	            paramMap.put("sections[" + x + "][course]", seccion.getCourse());
	            paramMap.put("sections[" + x + "][name]", seccion.getName());
	            paramMap.put("sections[" + x + "][summary]", seccion.getSummary());
	            //paramMap.put("sections[" + x + "][summaryformat]", seccion.getSummaryformat());
	            //paramMap.put("sections[" + x + "][availability]", seccion.getAvailability());
	            //paramMap.put("sections[" + x + "][usedefaultname]", seccion.getUsedefaultname());
	            paramMap.put("sections[" + x + "][visible]", 1);
	            //paramMap.put("sections[" + x + "][sequence]", seccion.getSequence());
	            
	            WSClientBase ws = new WSClientBase(parametroWSMoodleDTO);
	            
	            respuesta = ws.ejecutarServicioPOST("local_update_sections_sequence", paramMap, secciones.get(x), Boolean.class, secciones);
	           logger.info(respuesta);
	            x++;
	        }
	        return respuesta;
	    }
	  
	  
	  public static void main(String[] args){
			
			
			
			
			
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
			
			
			ArrayList<Seccion> secciones = new ArrayList<Seccion>();
			
			Seccion sec = new Seccion();
			sec.setId(4);
			sec.setName("Nombre de la seccion JMP 3");
			sec.setCourse(2);
			sec.setSummary("Esta es la descripcion de la seccion JMP 3");
			secciones.add(sec);
			
			try {
				new SeccionWS(p).actualizarSeccionSecuencia(secciones);
				System.out.println("TERMINO");
				
			} catch (ErrorWS e) {
				// TODO Auto-generated catch block
				//e.getCause().printStackTrace();
				e.printStackTrace();
				System.out.println(e.getJson());
			}
		}

}