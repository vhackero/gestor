package mx.gob.sedesol.gestorweb.commons.utils;

import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.UserAgent;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import mx.gob.sedesol.basegestor.commons.dto.admin.BitacoraDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.PersonaDTO;
import mx.gob.sedesol.basegestor.commons.utils.TipoServicioEnum;
import mx.gob.sedesol.gestorweb.commons.dto.UsuarioSessionDTO;
import org.apache.log4j.Logger;

/**
 * Clase de utilería que sirve para llenar el DTO de bitácora
 * 
 */
public class BitacoraUtil {

	private final static Logger LOG = Logger.getLogger(BitacoraUtil.class);
	public static final String IP_HEADER = "X-FORWARDED-FOR";
	public static final String NAVEGADOR_HEADER = "User-Agent";
	private static final String NOMBRE_SETTER = "setBitacoraDTO";

	public static void llenarBitacora(Object obj, UsuarioSessionDTO usuario, String funcion,
			HttpServletRequest request) {
		BitacoraDTO dto = new BitacoraDTO();
//		dto.setFuncion(funcion);
//		dto.setUsrNomCompleto(usuario.getUsuario());
//		dto.setIdUsuario(usuario.getIdPersona());
		llenarIpNavegador(request, dto);
		setBitacoraObject(dto, obj);
	}

	public static void llenarIpNavegador(HttpServletRequest request, BitacoraDTO dto) {
		String ipAddress = request.getHeader(IP_HEADER);
		UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader(NAVEGADOR_HEADER));
		Browser browser = userAgent.getBrowser();

		String navegador = browser.getName();
		if (ipAddress == null) {
			ipAddress = request.getRemoteAddr();
		}
		dto.setIp(ipAddress);
		dto.setNavegador(navegador);
	}

	public static String obtenerIp(HttpServletRequest request) {
		if (ObjectUtils.isNullOrEmpty(request.getHeader(IP_HEADER))) {
			return request.getRemoteAddr();
		} else {
			return request.getHeader(IP_HEADER);
		}
	}

	public static String obtenerNombreNavegador(HttpServletRequest request) {
		UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader(NAVEGADOR_HEADER));
		Browser browser = userAgent.getBrowser();
		return browser.getName();
	}

	private static void setBitacoraObject(BitacoraDTO dto, Object obj) {
		java.lang.reflect.Method method;
		try {
			method = obj.getClass().getMethod(NOMBRE_SETTER, BitacoraDTO.class);
			method.invoke(obj, dto);
		} catch (SecurityException | NoSuchMethodException | IllegalAccessException | InvocationTargetException ex) {
			LOG.error("Ha ocurrido un error al setear el objecto bitacoraDTO en el objeto: " + obj.getClass(), ex);
		}
	}
	

}
