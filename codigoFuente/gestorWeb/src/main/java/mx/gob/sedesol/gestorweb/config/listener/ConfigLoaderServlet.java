package mx.gob.sedesol.gestorweb.config.listener;

import java.io.File;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.PropertyConfigurator;

@WebServlet(loadOnStartup = 1, value = "/ConfigLoaderServlet", name = "ConfigLoaderServlet")
public class ConfigLoaderServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void init(ServletConfig config) throws ServletException {
		System.out.println("Log4JInitServlet is initializing log4j");

		String log4jProp = config.getServletContext().getRealPath("/WEB-INF/log4j.properties");
		File file = new File(log4jProp);
		if (file.exists()) {
			System.out.println("Initializing log4j with: " + log4jProp);
			PropertyConfigurator.configure(log4jProp);
		} else {
			System.err.println("*** " + log4jProp + " No se encontro el archivo");
			BasicConfigurator.configure();
		}

		super.init(config);
	}

}
