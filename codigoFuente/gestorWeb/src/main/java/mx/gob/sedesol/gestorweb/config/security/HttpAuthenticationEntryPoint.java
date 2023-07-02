package mx.gob.sedesol.gestorweb.config.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class HttpAuthenticationEntryPoint implements AuthenticationEntryPoint {

	private static final Logger log = Logger.getLogger(HttpAuthenticationEntryPoint.class);

	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authExc)
			throws IOException, ServletException {
		
		request.getRequestURI();
		
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authExc.getMessage());
		log.info("from HttpAuthenticationEntryPoint:  " + HttpServletResponse.SC_UNAUTHORIZED + " " + authExc.getMessage());
	}

}
