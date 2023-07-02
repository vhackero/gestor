package mx.gob.sedesol.gestorscheduler.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class AplicationContextProvider implements ApplicationContextAware {

	
	private static ApplicationContext context;
	
	@Override
	public void setApplicationContext(ApplicationContext ac) throws BeansException {
		 context = ac;
		
	}

	
	public ApplicationContext getApplicationContext() {
        return context;
    }
     

	
	
	
	
	
}
