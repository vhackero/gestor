package mx.gob.sedesol.basegestor.springinit;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import mx.gob.sedesol.basegestor.service.admin.PersonaService;


@Component
public class InicializadorSpringST {

	private static final String CONFIG_PATH = "classpath*:spring/applicationContext.xml";
	private static ApplicationContext context;
	

	public static void main(String[] args) {

		context = new ClassPathXmlApplicationContext(CONFIG_PATH);
		PersonaService personaService = context.getBean(PersonaService.class);
		
		System.out.println("Test: "+ personaService.buscarPorNombreUsuario("usrDesa").toString());
		
		
	}

}
