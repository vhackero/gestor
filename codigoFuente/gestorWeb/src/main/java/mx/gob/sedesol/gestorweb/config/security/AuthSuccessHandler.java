package mx.gob.sedesol.gestorweb.config.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.function.BiConsumer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.UserAgent;
import mx.gob.sedesol.basegestor.commons.constantes.ConstantesGestor;
import mx.gob.sedesol.basegestor.commons.dto.admin.BitacoraDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.PersonaDTO;
import mx.gob.sedesol.basegestor.commons.utils.TipoServicioEnum;
import mx.gob.sedesol.basegestor.mongo.service.BitacoraEntradaSistemaService;
import mx.gob.sedesol.basegestor.mongo.service.BitacoraService;
import mx.gob.sedesol.basegestor.service.admin.PersonaService;
import mx.gob.sedesol.gestorweb.commons.dto.UsuarioSessionDTO;
import mx.gob.sedesol.gestorweb.commons.utils.BitacoraUtil;
import mx.gob.sedesol.gestorweb.config.listener.SessionCounterListener;



@Component
public class AuthSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

	private final Logger log = Logger.getLogger(AuthSuccessHandler.class);
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
	
	@Autowired
	private BitacoraEntradaSistemaService bitacoraEntradaSistemaService;
	
	@Autowired
	private BitacoraService bitacoraService;
	
	@Autowired
	private PersonaService personaService;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request,
			HttpServletResponse response, Authentication authentication) throws IOException,
			ServletException {
		
		//log.info("autorizando y generando session de usuario: " + authentication.getName());
		 HttpSession session = request.getSession();
		
		CustomUserDetail principal = (CustomUserDetail)authentication.getPrincipal();
		if(principal != null){
			
			UsuarioSessionDTO usuarioSession = new UsuarioSessionDTO();
			
			usuarioSession.setUsuario(authentication.getName());
			usuarioSession.setIdPersona(principal.getIdPersona());
			usuarioSession.setRoles(new ArrayList<GrantedAuthority>());
			usuarioSession.getRoles().addAll(authentication.getAuthorities());
			usuarioSession.setEnSession(Boolean.TRUE);
			usuarioSession.setListaPermisos(new HashMap<>());
			
			for (String permiso : principal.getPermisosRol()) {
				usuarioSession.getListaPermisos().put(permiso, permiso);
			}
			
			//log.info("total permisos: " + principal.getPermisosRol().size());
			session.setAttribute(ConstantesGestor.PARAM_USUARIO_SESSION,usuarioSession);  
			redirectStrategy.sendRedirect(request, response, "/tablero");
			
						
			PersonaDTO persona = personaService.buscarPorId(principal.getIdPersona());
			
			BitacoraDTO bitacora = new BitacoraDTO();
			
			bitacora.setIdUsuario(String.valueOf(persona.getIdPersona()));
			bitacora.setUsuario(persona.getUsuario());
			bitacora.setNombreCompleto(persona.getNombreCompleto());
			bitacora.setClaveModulo("MOD_ADM");
			bitacora.setModulo("Administración");
			bitacora.setClaveComponente("CMP_ING_SIS");
			bitacora.setComponente("Entradas y salidas del sistema");
			bitacora.setClaveFuncionalidad("ACC_SIS");
			bitacora.setFuncionalidad("Acceso al sistema");
			bitacora.setIdElementoAfectado(String.valueOf(persona.getIdPersona()));
			bitacora.setFechaRegistro(new Date());
			bitacora.setIp(BitacoraUtil.obtenerIp(request));
			bitacora.setNavegador(BitacoraUtil.obtenerNombreNavegador(request));
			bitacora.setTipoServicio(TipoServicioEnum.LOCAL.getDescripcion());
			
			bitacoraService.guardarBitacora(bitacora);
			
			
		}
	}
 
	/**
	protected String determineTargetUrl(Authentication authentication) {
        Set<String> authorities = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
        if (authorities.contains("ROLE_ADMIN")) {
        	return "/admin.htm";
        } else if (authorities.contains("ROLE_USER")) {
        	return "/user.htm";
        } else {
            throw new IllegalStateException();
        }
    }
    **/
 
	public RedirectStrategy getRedirectStrategy() {
		return redirectStrategy;
	}
 
	public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
		this.redirectStrategy = redirectStrategy;
	}

}
