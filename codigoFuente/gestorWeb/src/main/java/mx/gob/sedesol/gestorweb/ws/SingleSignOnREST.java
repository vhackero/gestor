package mx.gob.sedesol.gestorweb.ws;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import mx.gob.sedesol.basegestor.commons.dto.admin.PersonaDTO;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.service.admin.PersonaService;
import mx.gob.sedesol.gestorweb.commons.constantes.ConstantesGestorWeb;

@Component
@RestController
@RequestMapping("/SSO")
public class SingleSignOnREST {

	@Autowired
	private PersonaService personaService;

	@Value("#{servletContext.contextPath}")
	private String servletContextPath;

	private static final Logger logger = Logger.getLogger(SingleSignOnREST.class);

	public static final String RUTA_PORTAL = "http://35.162.15.208:5050/web/guest/perfil";

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public void login(@RequestParam(name = "token") String token, @RequestParam(name = "user") String user,
			HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		logger.info(token + " " + user);
		logger.info(servletContextPath);

		IdentityServerConectionClientIT identity = new IdentityServerConectionClientIT();
		boolean correcto = false;
		String secretKey = "1[0x1]";

		try {
			correcto = true;// TODO identity.validar_token(token);
			if (correcto) {
				logger.info("CORRECTO");
				String decryptedString = AES.decrypt(user, secretKey);
				logger.info(decryptedString);

				PersonaDTO persona = personaService.obtienePersonaPorNombreUsuario(decryptedString);
				if (ObjectUtils.isNotNull(persona)) {
					logger.info(persona.getUsuario());
					response.sendRedirect(servletContextPath + ConstantesGestorWeb.RUTA_TABLERO);
				} else {
					response.sendRedirect(RUTA_PORTAL);
				}

			} else {
				response.sendRedirect(RUTA_PORTAL);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
}
