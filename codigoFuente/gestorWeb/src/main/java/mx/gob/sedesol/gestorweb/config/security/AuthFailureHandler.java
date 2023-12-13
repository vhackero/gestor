package mx.gob.sedesol.gestorweb.config.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import mx.gob.sedesol.gestorweb.commons.constantes.ConstantesGestorWeb;

@Component
public class AuthFailureHandler extends SimpleUrlAuthenticationFailureHandler {

	private static final Logger log = Logger.getLogger(AuthFailureHandler.class);
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
	
	@Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {
        
		log.error(exception.getMessage(), exception);
		if(exception.getMessage().equalsIgnoreCase("User is disabled")){
			request.getSession().setAttribute(ConstantesGestorWeb.ERRORES_LOGIN, "El usuario se encuentra inactivo.");
		}else if(exception.getMessage().equalsIgnoreCase("Bad credentials")){
			request.getSession().setAttribute(ConstantesGestorWeb.ERRORES_LOGIN, "Credenciales incorrectas, vuelve a intentarlo.");
		}else{
			request.getSession().setAttribute(ConstantesGestorWeb.ERRORES_LOGIN, exception.getMessage());
		}
		System.out.println(request.getSession());
		
		redirectStrategy.sendRedirect(request, response, "/login?errorLogin=true");
        
    }
	
}
