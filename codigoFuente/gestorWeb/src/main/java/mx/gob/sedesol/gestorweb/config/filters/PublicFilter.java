package mx.gob.sedesol.gestorweb.config.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.service.ParametroSistemaService;

/**
 * Servlet Filter implementation class PublicFilter
 */
@WebFilter("/views/public/*")
public class PublicFilter implements Filter {

	@Autowired
	private ParametroSistemaService parametroSistemaService;

	private static Logger log = Logger.getLogger(PublicFilter.class);

	/**
	 * Default constructor.
	 */
	public PublicFilter() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here

		String paginaInicio = parametroSistemaService.obtenerParametro("PAGINA_INICIO");

		if (!ObjectUtils.isNullOrEmpty(paginaInicio)) {

			HttpServletResponse httpResponse = (HttpServletResponse) response;
			try {
				httpResponse.sendRedirect(paginaInicio);
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}

		} else {
			// pass the request along the filter chain
			chain.doFilter(request, response);
		}

	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
		try {
			SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, fConfig.getServletContext());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

	}

}
