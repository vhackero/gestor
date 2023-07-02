package mx.gob.sedesol.gestorweb.config.security;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.stereotype.Component;

import mx.gob.sedesol.basegestor.commons.dto.admin.BitacoraDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.PersonaDTO;
import mx.gob.sedesol.basegestor.commons.utils.TipoServicioEnum;
import mx.gob.sedesol.basegestor.mongo.service.BitacoraService;
import mx.gob.sedesol.basegestor.service.ParametroSistemaService;
import mx.gob.sedesol.basegestor.service.admin.PersonaService;
import mx.gob.sedesol.gestorweb.commons.utils.BitacoraUtil;
import mx.gob.sedesol.gestorweb.commons.utils.ObjectUtils;

@Component
public class HttpLogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler {

	@Autowired
	private PersonaService personaService;

	@Autowired
	private BitacoraService bitacoraService;

	@Autowired
	private ParametroSistemaService parametroSistemaService;

	private static final Logger log = Logger.getLogger(HttpLogoutSuccessHandler.class);
	// private String DEFAULT_LOGOUT_PAGE ="/views/public/login/login.jsf";
	private String DEFAULT_LOGOUT_PAGE = "/login";

	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		String paginaInicio = parametroSistemaService.obtenerParametro("PAGINA_INICIO");
		if (ObjectUtils.isNullOrEmpty(paginaInicio)) {
			paginaInicio = DEFAULT_LOGOUT_PAGE;
		}
		CustomUserDetail principal = (CustomUserDetail) authentication.getPrincipal();
		if (principal != null) {

			PersonaDTO persona = personaService.buscarPorId(principal.getIdPersona());

			BitacoraDTO bitacora = new BitacoraDTO();

			bitacora.setIdUsuario(String.valueOf(persona.getIdPersona()));
			bitacora.setUsuario(persona.getUsuario());
			bitacora.setNombreCompleto(persona.getNombreCompleto());
			bitacora.setClaveModulo("MOD_ADM");
			bitacora.setModulo("Administración");
			bitacora.setClaveComponente("CMP_ING_SIS");
			bitacora.setComponente("Entradas y salidas del sistema");
			bitacora.setClaveFuncionalidad("SAL_SIS");
			bitacora.setFuncionalidad("Salida del sistema");
			bitacora.setIdElementoAfectado(String.valueOf(persona.getIdPersona()));
			bitacora.setFechaRegistro(new Date());
			bitacora.setIp(BitacoraUtil.obtenerIp(request));
			bitacora.setNavegador(BitacoraUtil.obtenerNombreNavegador(request));
			bitacora.setTipoServicio(TipoServicioEnum.LOCAL.getDescripcion());

			bitacoraService.guardarBitacora(bitacora);

		}
		log.info("Se invalida session por logout.");
		super.setDefaultTargetUrl(paginaInicio);
		request.getSession().invalidate();

		super.onLogoutSuccess(request, response, authentication);

	}

}
