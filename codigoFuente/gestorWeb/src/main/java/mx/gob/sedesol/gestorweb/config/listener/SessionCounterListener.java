package mx.gob.sedesol.gestorweb.config.listener;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener
public class SessionCounterListener implements HttpSessionListener {

	private static int totalActiveSessions = 0;

	public static int getTotalActiveSession() {
		return totalActiveSessions;
	}

	
	public static void sumaSesion(){
		totalActiveSessions++;
	}
	
	public static void restaSesion(){
		totalActiveSessions--;
	}
	

	@Override
	public void sessionCreated(HttpSessionEvent arg0) {
		
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent arg0) {
//		totalActiveSessions--;
		//System.out.println("SE CERRO UNA SESION, CANTIDAD DE SESIONES ACTIVAS: "
			//	+ totalActiveSessions );
	}
}
