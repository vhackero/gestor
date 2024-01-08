/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sedesol.basegestor.ws.moodle.clientes.service.client;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.web.client.ResourceAccessException;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import mx.gob.sedesol.basegestor.commons.dto.admin.ParametroWSMoodleDTO;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.TblEvento;
import mx.gob.sedesol.basegestor.ws.moodle.clientes.model.entities.Alertas;
import mx.gob.sedesol.basegestor.ws.moodle.clientes.model.entities.Curso;
import mx.gob.sedesol.basegestor.ws.moodle.clientes.model.entities.Cursos;
import mx.gob.sedesol.basegestor.ws.moodle.clientes.model.entities.Seccion;
import mx.gob.sedesol.basegestor.ws.moodle.clientes.model.entities.Usuarios;
import mx.gob.sedesol.basegestor.ws.moodle.clientes.service.controller.WSClientBase;
import mx.gob.sedesol.basegestor.ws.moodle.clientes.service.util.ErrorWS;

/**
 *
 * @author robert o_O
 */
public class CursoWS {
	
	private static final Logger logger = Logger.getLogger(CursoWS.class);
	
	private ParametroWSMoodleDTO parametroWSMoodleDTO;
	
	public CursoWS(ParametroWSMoodleDTO plataforma){
		this.parametroWSMoodleDTO = plataforma;
	}
	
    public Cursos verificarExisteCurso(String criterianame, String criteriavalue) throws ErrorWS {

        Cursos respuesta = null;
        try{
            HashMap<String, Object> paramMap = new HashMap<>();
            paramMap.put("criterianame", criterianame);
            paramMap.put("criteriavalue", criteriavalue);
            WSClientBase ws = new WSClientBase(parametroWSMoodleDTO);
            respuesta = ws.ejecutarServicioGET("core_course_search_courses", paramMap, Cursos.class);
            logger.info(respuesta);
        } catch (JsonMappingException ex) {
        	logger.error(ex);
            throw new ErrorWS(ex.getMessage(), ex.getCause());
        } catch (IOException ex) {
        	logger.error(ex);
            throw new ErrorWS(ex.getMessage(), ex.getCause());
        }
        return respuesta;
    }
    
    
    public void crearCurso(List<Curso> cursos) throws ErrorWS{
        
        HashMap<String, Object> paramMap = new HashMap<>();
        int x = 0;
        
        for (Curso curso : cursos) {
            paramMap.put("courses[" + x + "][fullname]", curso.getFullname());
            paramMap.put("courses[" + x + "][shortname]", curso.getShortname());
            paramMap.put("courses[" + x + "][categoryid]", curso.getCategoryid());
            paramMap.put("courses[" + x + "][idnumber]", curso.getIdnumber());
            paramMap.put("courses[" + x + "][startdate]", (int) (new Date().getTime() / 1000));
            paramMap.put("courses[" + x + "][summary]", curso.getSummary());
            paramMap.put("courses[" + x + "][visible]", curso.getVisible());
            WSClientBase ws = new WSClientBase(parametroWSMoodleDTO);
            String respuesta = ws.ejecutarServicioPOST("core_course_create_courses", paramMap, cursos.get(x), String.class, cursos);
            logger.info(respuesta);
            x++;
        }
    }
    
    
    public List<Curso> listarCursos(int cursoId) throws ErrorWS{
        List<Curso> respuesta;
            HashMap<String, Object> paramMap = new HashMap<>();
            paramMap.put("options[ids][0]", cursoId);
            WSClientBase ws = new WSClientBase(parametroWSMoodleDTO);
            respuesta = ws.ejecutarServicioListPOST("core_course_get_courses", paramMap, "", new TypeReference<List<Curso>>() {
            }, paramMap);
            logger.info(respuesta);
        return respuesta;
    }
    
    public List<Curso> listarCursosSinIdNumber() throws ErrorWS {
    	List<Curso> respuesta;
            HashMap<String, Object> paramMap = new HashMap<>();
            //paramMap.put("options[ids][0]", cursoId);
            WSClientBase ws = new WSClientBase(parametroWSMoodleDTO);
            respuesta = ws.ejecutarServicioListPOST("local_courses_without_idnumber", paramMap, "", new TypeReference<List<Curso>>() {
            }, paramMap);
            logger.info(respuesta);
        
        return respuesta;
    }
    
    
    
    
    public void actualizarCurso(List<Curso> cursos) throws ErrorWS{
        
        HashMap<String, Object> paramMap = new HashMap<>();
        int x = 0;
        
        for (Curso curso : cursos) {
            paramMap.put("courses[" + x + "][id]", curso.getId());
            paramMap.put("courses[" + x + "][fullname]", curso.getFullname());
            paramMap.put("courses[" + x + "][shortname]", curso.getShortname());
            paramMap.put("courses[" + x + "][categoryid]", curso.getCategoryid());
            paramMap.put("courses[" + x + "][idnumber]", curso.getIdnumber());
            paramMap.put("courses[" + x + "][summary]", curso.getSummary());
            paramMap.put("courses[" + x + "][visible]", curso.getVisible());
            WSClientBase ws = new WSClientBase(parametroWSMoodleDTO);
            String respuesta = ws.ejecutarServicioPOST("core_course_update_courses", paramMap, cursos.get(x), String.class, cursos);
            logger.info(respuesta);
            x++;
        }
    }
    
    public  Cursos borrarCurso(int cursoId) throws ErrorWS {
    	Cursos respuesta;
            HashMap<String, Object> paramMap = new HashMap<>();
            paramMap.put("courseids[0]", cursoId);
            WSClientBase ws = new WSClientBase(parametroWSMoodleDTO);
            //List<Alertas> respuesta = ws.ejecutarServicioListPOST("core_course_delete_courses", paramMap, "", new TypeReference<List<Alertas>>() {}, paramMap);
            respuesta = ws.ejecutarServicioPOST("core_course_delete_courses", paramMap, "",Cursos.class, cursoId);
            logger.info(respuesta);
        return respuesta;
    }
    
    public List<Seccion> verDetallesCurso(int cursoId) throws ErrorWS {
        List<Seccion> seccion;
            HashMap<String, Object> paramMap = new HashMap<>();
            paramMap.put("courseid", cursoId);
            WSClientBase ws = new WSClientBase(parametroWSMoodleDTO);
            seccion = ws.ejecutarServicioListPOST("core_course_get_contents", paramMap, "", new TypeReference<List<Seccion>>() {
            }, paramMap);
        
        return seccion;
    }
    
    
    public int crearCurso(Curso curso, TblEvento evento) throws ErrorWS{
        
        HashMap<String, Object> paramMap = new HashMap<>();
        //int x = 0;
        
        //for (Curso curso : cursos) {
        long val2 = new Timestamp(evento.getFechaInicial().getTime() / 1000).getTime();
        int x = 0;
        paramMap.put("course[" + x + "][fullname]", curso.getFullname());
        paramMap.put("course[" + x + "][shortname]", curso.getShortname());
        paramMap.put("course[" + x + "][categoryid]", curso.getCategoryid());
        paramMap.put("course[" + x + "][idnumber]", curso.getIdnumber());
        paramMap.put("course[" + x + "][startdate]", val2 );
        paramMap.put("course[" + x + "][summary]", curso.getSummary());
        paramMap.put("course[" + x + "][visible]", curso.getVisible());
        paramMap.put("course[" + x + "][numsections]", curso.getNumsections());
        String s = 
        		"course[" + x + "][fullname]="+ curso.getFullname();
        		
        
            WSClientBase ws = new WSClientBase(parametroWSMoodleDTO);
            Integer respuesta = ws.ejecutarServicioPOST("local_courses_create_notvisible", paramMap, curso, Integer.class, curso);
            logger.info(respuesta);
          //  x++;
        //}
            //System.out.println(respuesta);
            return respuesta;
    }
    
    
	public void ocultarCurso(int idCurso) throws ErrorWS {

		HashMap<String, Object> paramMap = new HashMap<>();
		int x = 0;

		paramMap.put("courses[" + x + "][id]", idCurso);
		paramMap.put("courses[" + x + "][visible]", 0);
		WSClientBase ws = new WSClientBase(parametroWSMoodleDTO);
		String respuesta = ws.ejecutarServicioPOST("core_course_update_courses", paramMap, null, String.class);
		logger.info(respuesta);

	}
	
	public void mostrarCurso(int idCurso) throws ErrorWS {

		HashMap<String, Object> paramMap = new HashMap<>();
		int x = 0;

		paramMap.put("courses[" + x + "][id]", idCurso);
		paramMap.put("courses[" + x + "][visible]", 1);
		WSClientBase ws = new WSClientBase(parametroWSMoodleDTO);
		String respuesta = ws.ejecutarServicioPOST("core_course_update_courses", paramMap, null, String.class);
		logger.info(respuesta);

	}
	
	
	/**
	 * Clona un curso sin datos de usuarios, es necesario cambiar el nombre del curso
	 * @param cursoId
	 * @return
	 * @throws ErrorWS
	 */
	public  Integer clonarCursosSinDatos(int cursoId) throws ErrorWS {
        Integer respuesta;
            HashMap<String, Object> paramMap = new HashMap<>();
            paramMap.put("courseid", cursoId);
            WSClientBase ws = new WSClientBase(parametroWSMoodleDTO);
            respuesta = ws.ejecutarServicioPOST("local_courses_duplicate_course", paramMap, null, Integer.class);
        
        return respuesta;
    }
	
	/**
	 * Clona un curso con los datos de los usuarios y regrea el nuevo ID del curso creado
	 * @param cursoId
	 * @return Id moodle creado
	 * @throws ErrorWS
	 */
	public  Integer clonarCursosConDatos(int cursoId) throws ErrorWS {
		Integer respuesta;
            HashMap<String, Object> paramMap = new HashMap<>();
            paramMap.put("courseid", cursoId);
            paramMap.put("data", 1);
            WSClientBase ws = new WSClientBase(parametroWSMoodleDTO);
            respuesta = ws.ejecutarServicioPOST("local_courses_duplicate_course", paramMap, null, Integer.class);
        return respuesta;
    }
	
	
	
	/**
	 * Clona un curso sin datos de usuarios, es necesario cambiar el nombre del curso
	 * @param cursoId
	 * @return
	 * @throws ErrorWS
	 */
	public  String respaldoSinDatos(int cursoId) throws ErrorWS {
        String respuesta;
        URL url;
            HashMap<String, Object> paramMap = new HashMap<>();
            paramMap.put("courseid", cursoId);
            WSClientBase ws = new WSClientBase(parametroWSMoodleDTO);
            respuesta = ws.ejecutarServicioPOST("local_courses_backup_course", paramMap, null, String.class);
            try {
				url = new URL(respuesta.replace("\\", "").replace("\"", ""));
			} catch (MalformedURLException e) {
				e.printStackTrace();
				try {
					ObjectMapper om = new ObjectMapper();
					String json = respuesta;
					ErrorWS ee = om.readValue(json, ErrorWS.class);
					ee.setJson(json);
					throw ee;
				} catch (IOException ex1) {
					logger.error(e);
					throw new ErrorWS("No fue posible leer la estructura regresada: " + respuesta, ex1);
				}
			}
            
        return url.toString();
    }
	
	/**
	 * Clona un curso con los datos de los usuarios y regrea el nuevo ID del curso creado
	 * @param cursoId
	 * @return Id moodle creado
	 * @throws ErrorWS
	 */
	public  String respaldoConConDatos(int cursoId) throws ErrorWS {
		String respuesta;
		URL url;
            HashMap<String, Object> paramMap = new HashMap<>();
            paramMap.put("courseid", cursoId);
            paramMap.put("data", 1);
            WSClientBase ws = new WSClientBase(parametroWSMoodleDTO);
            respuesta = ws.ejecutarServicioPOST("local_courses_backup_course", paramMap, null, String.class);
            try {
				url = new URL(respuesta.replace("\\", "").replace("\"", ""));
			} catch (MalformedURLException e) {
				e.printStackTrace();
				try {
					ObjectMapper om = new ObjectMapper();
					String json = respuesta;
					ErrorWS ee = om.readValue(json, ErrorWS.class);
					ee.setJson(json);
					throw ee;
				} catch (IOException ex1) {
					logger.error(e);
					throw new ErrorWS("No fue posible leer la estructura regresada: " + respuesta, ex1);
				}
			}
            
        return url.toString();
    }
	
	
	/**
	 * Registra nuevas competencias en un curso, eliminando las existentes
	 * @param courseId
	 * @param competencias
	 * @throws ErrorWS
	 */
	public boolean registrarCompetencias(int courseId, List<String> competencias) throws ErrorWS{
        
        HashMap<String, Object> paramMap = new HashMap<>();
        int x = 0;
        paramMap.put("courseid", courseId);
        for (String competencia : competencias) {
            paramMap.put("tag[" + x + "]", competencia );
            x++;
        }
        WSClientBase ws = new WSClientBase(parametroWSMoodleDTO);
        String respuesta = ws.ejecutarServicioPOST("local_courses_create_tags", paramMap, null, String.class);
        
        System.out.println(respuesta);
        return true;
    }
	
	/**
	 * Verificar si un curso esta visible para los estudiantes
	 * @param courseId
	 * @return true visible, false invisible
	 * @throws ErrorWS
	 */
	public boolean esVisible(int courseId) throws ErrorWS{
		
		List<Curso> cursos;
		HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("options[ids][0]", courseId );
        
        WSClientBase ws = new WSClientBase(parametroWSMoodleDTO);
        cursos = ws.ejecutarServicioListPOST("core_course_get_courses", paramMap, "", new TypeReference<List<Curso>>() {
        }, paramMap);
        
        if(cursos!=null && cursos.size()>0){
        	return cursos.get(0).getVisible()==1?true:false;
        }else{
        	return false;
        }
    }
	
	public Curso getCurso(int courseId) throws ErrorWS{
		
		List<Curso> cursos;
		HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("options[ids][0]", courseId );
        
        WSClientBase ws = new WSClientBase(parametroWSMoodleDTO);
        cursos = ws.ejecutarServicioListPOST("core_course_get_courses", paramMap, "", new TypeReference<List<Curso>>() {
        }, paramMap);
        
        if(cursos!=null && cursos.size()>0){
        	return cursos.get(0);
        }else{
        	throw new ErrorWS("El curso no existe");
        }
    }
	
	public  Integer obtenerAvanceOAS(int cursoId, int userId) throws ErrorWS {
		Integer respuesta;
            HashMap<String, Object> paramMap = new HashMap<>();
            paramMap.put("courseid", cursoId);
            paramMap.put("userid", userId);
            WSClientBase ws = new WSClientBase(parametroWSMoodleDTO);
            respuesta = ws.ejecutarServicioPOST("local_courses_avance_oas", paramMap, null, Integer.class);
            logger.info(respuesta);
        return respuesta;
    }
    
    
    
    public static void main(String[] args){
		
		Curso c = new Curso();
		
		c.setFullname("Curso JMP");
		 //paramMap.put("fullname", curso.getFullname());
		c.setShortname("JMP7");
		c.setCategoryid(1);
		c.setStartdate((int) (new Date().getTime() / 1000));
		c.setSummary("Resumen de curso");
		c.setNumsections(-5);
		
		
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
			/*ArrayList<String> competencias = new ArrayList<String>();
			competencias.add("Competencia 1");
			competencias.add("Competencia 2");*/
			
			
			String respuesta = new CursoWS(p).respaldoConConDatos(12);
			System.out.println(respuesta);
		} catch (ErrorWS e) {
			// TODO Auto-generated catch block
			//e.getCause().printStackTrace();
			e.printStackTrace();
			System.out.println(e.getJson());
		}
	}
}
