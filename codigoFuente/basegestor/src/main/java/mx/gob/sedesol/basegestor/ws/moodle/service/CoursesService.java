package mx.gob.sedesol.basegestor.ws.moodle.service;

import java.io.IOException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.springframework.web.client.RestClientException;

import com.fasterxml.jackson.core.JsonProcessingException;

import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.EstructuraTematicaDTO;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.ws.moodle.dto.Course;
import mx.gob.sedesol.basegestor.ws.moodle.dto.CourseCreationRequest;
import mx.gob.sedesol.basegestor.ws.moodle.dto.MoodleExceptionResponse;
import mx.gob.sedesol.basegestor.ws.moodle.util.MoodleWSRestTemplate;

public class CoursesService {
	
	public static final Integer  SUMMARY_FORMAT = 1;
	public static final String FORMAT = "weeks";
	public static final Integer NEWSITEMS =5; 
	
	private static final String FUNCION_CREA_CURSO_MDL ="core_course_create_courses";
	private static final String REST_FORMAT_WS = "json";
	private MoodleWSRestTemplate template;
	//private MoodleWSRestTemplate template = new MoodleWSRestTemplate("192.168.14.87",80,"/plataformaeducativa/webservice/rest/server.php");
	private EstructuraTematicaDTO estructuraTematica;
	private String dirIpWS;
	private String urlWebService;
	private String token;
	private Integer puertoWS;
	
	/**
	 * 
	 * @param temaSelec
	 * @param dirIpWebService
	 * @param puertoWS
	 * @param urlWebService
	 */
	public CoursesService(String dirIpWebService, Integer puertoWS, String urlWebService, String token){
		
		
		this.dirIpWS = dirIpWebService;
		this.urlWebService = urlWebService;
		this.puertoWS = puertoWS;
		this.token = token;
		
		if(ObjectUtils.isNotNull(dirIpWebService)
				&& ObjectUtils.isNotNull(urlWebService)
						&& ObjectUtils.isNotNull(puertoWS))
			template = new MoodleWSRestTemplate(this.dirIpWS,this.puertoWS,this.urlWebService);
	}
	
	
	/**
	 * Genera el objeto Curso de Moodle a partir de una estructura tematica
	 * @param temaSelec
	 * @return
	 */
	private Course generaObjCursoMoodle(){
		//TODO: CONSULTAR LAS UNIDADES DIDACTICAS
		Course curso = new Course();
		//curso.setId(); 
		curso.setFullName("TEST"); 
		curso.setShortName("TEST"); 
		curso.setCategoryId(null); 
		curso.setCategorySortOrder(null); 
		curso.setIdNombre(null); 
		curso.setVisible(Boolean.TRUE); 
		curso.setSummary("SUMMARY"); 
		curso.setSummaryFormat( SUMMARY_FORMAT); 
		curso.setFormat(FORMAT); 
		curso.setShowGrades(Boolean.TRUE); 
		curso.setNewsItems(NEWSITEMS); 
		
		/* curso.setStartDate(null); 
		curso.setNumSections( null); 
		curso.setMaxBytes( null); 
		curso.setShowReports( null); 
		curso.setHiddenSections( null); 
		curso.setGroupMode( null); 
		curso.setGroupModeForce( null); 
		curso.setDefaultGroupingId( null); 
		curso.setTimeCreated( null); 
		curso.setTimeModified( null); 
		curso.setEnableCompletion( null); 
		curso.setCompletionNotify( null); 
		curso.setLang( null); 
		curso.setForceTheme( null); 
		curso.setCourseFormatOptions(null); */
		
		return curso;
	}
	
	/**
	 * Realiza una peticion via Web Service Rest 
	 * para generar un Curso en un ambiente Moodle
	 */
	public void sendCourseCreationRequest() throws Exception{
		
		try{
			
			if(ObjectUtils.isNull(template))
				throw new Exception("el objeto MoodleWSRestTemplate no se a inicializado");
			
			List<Course> courses = new LinkedList<Course>();
			courses.add(generaObjCursoMoodle());
			
			CourseCreationRequest ccr = new CourseCreationRequest();
			ccr.setWsToken(this.token);
			ccr.setWsFunction(FUNCION_CREA_CURSO_MDL);
			ccr.setMoodleWsRestFormat(REST_FORMAT_WS);
			ccr.setCourses(courses);
			
			template.getForObject(ccr, MoodleExceptionResponse.class);
			
		}catch (Exception e) {
			throw e;
		}
	}
	
	/**
	 * 
	 * @return
	 * @throws RestClientException
	 * @throws JsonProcessingException
	 * @throws IOException
	 */
	public MoodleExceptionResponse requestBadCourseCreation() throws RestClientException, JsonProcessingException, IOException{
		
		Course course = new Course();
		course.setFullName("Pruebas de servicios REST de moodle");
		course.setShortName("moodle rest api test 2");
		course.setStartDate(new Date());
		course.setSummary("<h2>Este es un curso de prueba</h2>");
		course.setSummaryFormat(1);
		course.setVisible(true);
		course.setCategoryId(1);
		List<Course> courses = new LinkedList<Course>();
		courses.add(course);
		
		CourseCreationRequest ccr = new CourseCreationRequest();
		ccr.setWsToken("c358645f739b7797ccb39ee619849bc9");
		ccr.setWsFunction("core_course_create_courses");
		ccr.setMoodleWsRestFormat("json");
		ccr.setCourses(courses);
		MoodleExceptionResponse mer = template.getForObject(ccr, MoodleExceptionResponse.class);
		return mer;
		
	}


	public String getDirIpWS() {
		return dirIpWS;
	}


	public String getUrlWebService() {
		return urlWebService;
	}


	public String getToken() {
		return token;
	}


	public void setDirIpWS(String dirIpWS) {
		this.dirIpWS = dirIpWS;
	}


	public void setUrlWebService(String urlWebService) {
		this.urlWebService = urlWebService;
	}


	public void setToken(String token) {
		this.token = token;
	}


	public Integer getPuertoWS() {
		return puertoWS;
	}


	public void setPuertoWS(Integer puertoWS) {
		this.puertoWS = puertoWS;
	}


	public EstructuraTematicaDTO getEstructuraTematica() {
		return estructuraTematica;
	}


	public void setEstructuraTematica(EstructuraTematicaDTO estructuraTematica) {
		this.estructuraTematica = estructuraTematica;
	}
	
//	public static void main(String args[]) throws RestClientException, JsonProcessingException, IOException{
//		MoodleExceptionResponse mer = new CoursesService().requestBadCourseCreation();
//		System.out.println(mer);
//	}

}
