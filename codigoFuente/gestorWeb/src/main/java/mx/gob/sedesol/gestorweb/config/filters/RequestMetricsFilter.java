package mx.gob.sedesol.gestorweb.config.filters;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

/**
 * Filtro para registrar en el log cada URL consultada junto con los elementos
 * principales de la página (nombres de parámetros recibidos) y el tiempo de
 * atención. Permite identificar vistas lentas o con demasiados componentes.
 */
public class RequestMetricsFilter implements Filter {

	private static final Logger log = Logger.getLogger(RequestMetricsFilter.class);
	private static final Set<String> STATIC_EXTENSIONS = new HashSet<>(
			Arrays.asList(".css", ".js", ".png", ".jpg", ".jpeg", ".gif", ".ico", ".woff", ".woff2", ".ttf", ".svg",
					".eot", ".map"));
	private static final Set<String> JSF_STATE_PARAMS = new HashSet<>(
			Arrays.asList("javax.faces.ViewState", "javax.faces.source", "javax.faces.partial.ajax",
					"javax.faces.partial.execute", "javax.faces.partial.render"));
	private static final int MAX_ELEMENTS = 15;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// No se requiere configuración adicional
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		if (!(request instanceof HttpServletRequest)) {
			chain.doFilter(request, response);
			return;
		}

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		String uri = resolveUri(httpRequest);

		if (isStaticResource(uri)) {
			chain.doFilter(request, response);
			return;
		}

		long inicio = System.currentTimeMillis();
		try {
			chain.doFilter(request, response);
		} finally {
			long tiempo = System.currentTimeMillis() - inicio;
			String vista = resolveViewId(httpRequest);
			List<String> elementos = resolveMainElements(httpRequest);

			log.info(new StringBuilder("METRICAS -> metodo=").append(httpRequest.getMethod()).append(", url=")
					.append(buildFullUrl(httpRequest)).append(", vista=").append(vista)
					.append(", elementosPrincipales=").append(elementos.isEmpty() ? "-" : elementos)
					.append(", tiempoMs=").append(tiempo).toString());
		}
	}

	@Override
	public void destroy() {
		// No se requiere liberar recursos
	}

	private String resolveUri(HttpServletRequest request) {
		String forwardUri = (String) request.getAttribute("javax.servlet.forward.request_uri");
		if (forwardUri != null) {
			return forwardUri;
		}
		String includeUri = (String) request.getAttribute("javax.servlet.include.request_uri");
		if (includeUri != null) {
			return includeUri;
		}
		return request.getRequestURI();
	}

	private String buildFullUrl(HttpServletRequest request) {
		String query = request.getQueryString();
		String uri = resolveUri(request);
		return query == null ? uri : uri + "?" + query;
	}

	private boolean isStaticResource(String uri) {
		int extensionIndex = uri.lastIndexOf('.');
		if (extensionIndex == -1) {
			return false;
		}
		String extension = uri.substring(extensionIndex).toLowerCase();

		// JSF sirve recursos estáticos bajo /javax.faces.resource/
		if (uri.contains("/javax.faces.resource/")) {
			return true;
		}

		return STATIC_EXTENSIONS.contains(extension);
	}

	private String resolveViewId(HttpServletRequest request) {
		String servletPath = (String) request.getAttribute("javax.servlet.forward.servlet_path");
		if (servletPath == null || servletPath.isEmpty()) {
			servletPath = request.getServletPath();
		}

		String pathInfo = request.getPathInfo();
		if (pathInfo != null) {
			return servletPath + pathInfo;
		}
		return servletPath;
	}

	private List<String> resolveMainElements(HttpServletRequest request) {
		Map<String, String[]> parameterMap = request.getParameterMap();
		if (parameterMap == null || parameterMap.isEmpty()) {
			return Collections.emptyList();
		}

		List<String> elementos = new ArrayList<>();
		for (String parametro : parameterMap.keySet()) {
			if (JSF_STATE_PARAMS.contains(parametro)) {
				continue;
			}
			elementos.add(parametro);
			if (elementos.size() >= MAX_ELEMENTS) {
				break;
			}
		}
		return elementos;
	}
}
