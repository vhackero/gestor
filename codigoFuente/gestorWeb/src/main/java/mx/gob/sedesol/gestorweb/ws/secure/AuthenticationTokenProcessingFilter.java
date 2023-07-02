package mx.gob.sedesol.gestorweb.ws.secure;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.filter.GenericFilterBean;

import mx.gob.sedesol.basegestor.commons.constantes.ConstantesGestor;
import mx.gob.sedesol.basegestor.commons.dto.admin.BitacoraDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.FuncionalidadDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.PersonaDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.PersonaRolDTO;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.TipoServicioEnum;
import mx.gob.sedesol.basegestor.model.entities.admin.TblPersona;
import mx.gob.sedesol.basegestor.mongo.service.BitacoraEntradaSistemaService;
import mx.gob.sedesol.basegestor.mongo.service.BitacoraService;
import mx.gob.sedesol.basegestor.service.ParametroSistemaService;
import mx.gob.sedesol.basegestor.service.admin.PersonaRolesService;
import mx.gob.sedesol.basegestor.service.admin.PersonaService;
import mx.gob.sedesol.gestorweb.commons.dto.UsuarioSessionDTO;
import mx.gob.sedesol.gestorweb.commons.utils.BitacoraUtil;
import mx.gob.sedesol.gestorweb.config.security.CustomUserDetail;
import mx.gob.sedesol.gestorweb.ws.AES;
import mx.gob.sedesol.gestorweb.ws.IdentityServerConectionClientIT;

public class AuthenticationTokenProcessingFilter extends GenericFilterBean {

	private static final Logger LOG = Logger.getLogger(AuthenticationTokenProcessingFilter.class);

	@Autowired
	private PersonaService personaService;

	@Autowired
	private PersonaRolesService personaRolesService;

	@Autowired
	private BitacoraEntradaSistemaService bitacoraEntradaSistemaService;

	@Autowired
	private ParametroSistemaService parametroSistemaService;

	@Autowired
	private IdentityServerConectionClientIT identityServerConectionClientIT;

	@Autowired
	private BitacoraService bitacoraService;

	// public static final String RUTA_PORTAL =
	// "http://35.162.15.208:5050/web/guest/perfil";
	public static final String SECRET_KEY = "1[0x1]";

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		String RUTA_PORTAL = parametroSistemaService.obtenerParametro("SSO_RUTA_PORTAL");
		// LOG.info("RUTA_PORTAL: " + RUTA_PORTAL);
		
		request.setCharacterEncoding("UTF-8");
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		

		Map<String, String[]> parms = request.getParameterMap();

		if (parms.containsKey("token") && parms.containsKey("user")) {
			boolean correcto = false;
			String strToken = parms.get("token")[0];
			String strUser = parms.get("user")[0];
			LOG.info("Token: " + strToken + " user" + strUser);

			try {
				correcto = identityServerConectionClientIT.validar_token(strToken);

			} catch (IOException | URISyntaxException | ParseException e) {
				LOG.error(e.getMessage(), e);
			}

			if (correcto) {
				LOG.info("valid token found");

				String decryptedString = AES.decrypt(strUser, SECRET_KEY);
				
				
				LOG.info(decryptedString);

				CustomUserDetail detallesUsuarioCustom = (CustomUserDetail) loadUserByUsername(decryptedString);
				if (detallesUsuarioCustom != null) {
					Authentication authentication = new Authentication() {

						private static final long serialVersionUID = 1L;

						@Override
						public String getName() {
							return detallesUsuarioCustom.getUsername();
						}

						@Override
						public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
						}

						@Override
						public boolean isAuthenticated() {
							return true;
						}

						@Override
						public Object getPrincipal() {
							return detallesUsuarioCustom;
						}

						@Override
						public Object getDetails() {
							return null;
						}

						@Override
						public Object getCredentials() {
							return detallesUsuarioCustom.getPassword();
						}

						@Override
						public Collection<? extends GrantedAuthority> getAuthorities() {
							return detallesUsuarioCustom.getAuthorities();
						}
					};
					LOG.info("##### establecer auth");
					LOG.info(((CustomUserDetail) authentication.getPrincipal()).getUsername());
					LOG.info(((CustomUserDetail) authentication.getPrincipal()).getAuthorities().size());

					SecurityContext securityContext = SecurityContextHolder.getContext();

					securityContext.setAuthentication(authentication);

					// Create a new session and add the security context.
					HttpSession session = httpRequest.getSession();

					CustomUserDetail principal = detallesUsuarioCustom;
					if (principal != null) {

						UsuarioSessionDTO usuarioSession = new UsuarioSessionDTO();

						usuarioSession.setUsuario(authentication.getName());
						usuarioSession.setIdPersona(principal.getIdPersona());
						usuarioSession.setRoles(new ArrayList<>());
						usuarioSession.getRoles().addAll(authentication.getAuthorities());
						usuarioSession.setEnSession(Boolean.TRUE);
						usuarioSession.setListaPermisos(new HashMap<>());

						for (String permiso : principal.getPermisosRol()) {
							usuarioSession.getListaPermisos().put(permiso, permiso);
						}

						LOG.info("total permisos: " + principal.getPermisosRol().size());
						session.setAttribute(ConstantesGestor.PARAM_USUARIO_SESSION, usuarioSession);

						String registro = String.valueOf(principal.getIdPersona());
						LOG.info(registro);

						PersonaDTO persona = personaService.buscarPorId(principal.getIdPersona());

						BitacoraDTO bitacora = new BitacoraDTO();

						bitacora.setIdUsuario(String.valueOf(persona.getIdPersona()));
						bitacora.setUsuario(persona.getUsuario());
						bitacora.setNombreCompleto(persona.getNombreCompleto());
						bitacora.setModulo("Administración");
						bitacora.setFuncionalidad("Acceso al sistema");
						bitacora.setIdElementoAfectado(String.valueOf(persona.getIdPersona()));
						bitacora.setFechaRegistro(new Date());
						bitacora.setIp(BitacoraUtil.obtenerIp((HttpServletRequest) request));
						bitacora.setNavegador(BitacoraUtil.obtenerNombreNavegador((HttpServletRequest) request));
						bitacora.setTipoServicio(TipoServicioEnum.SERVICIOWEB.getDescripcion());

						bitacoraService.guardarBitacora(bitacora);
						
					}

				} else {
					LOG.info("invalid user");
					httpResponse.sendRedirect(RUTA_PORTAL);
				}
			} else {
				LOG.info("invalid token");
				httpResponse.sendRedirect(RUTA_PORTAL);
			}
		} else {
			// LOG.info("no token found");
		}
		// continue thru the filter chain
		

		chain.doFilter(request, response);
	}

	public UserDetails loadUserByUsername(String usuario) throws UsernameNotFoundException {

		LOG.info("##########################################################autenticando RestUserDetailsService ....");

		TblPersona persona = personaService.buscarPorNombreUsuario(usuario);
		if (persona == null) {
			return null;
		}

		List<GrantedAuthority> rolesAutenticados = new ArrayList<>();
		List<String> permisosPorRol = new ArrayList<>();

		if (!ObjectUtils.isNullOrEmpty(persona.getRelPersonaRoles())) {

			try {

				List<PersonaRolDTO> lstRoles = personaRolesService
						.obtieneRelPersonaRolesPorUsuario(persona.getUsuario());
				if (!ObjectUtils.isNullOrEmpty(lstRoles)) {
					for (PersonaRolDTO pr : lstRoles) {
						LOG.info("_____" + pr.getRol().getClave());
						if (!rolesAutenticados.contains(pr.getRol().getClave())) {
							rolesAutenticados.add(new SimpleGrantedAuthority(pr.getRol().getClave()));
						}
					}
				}

				List<FuncionalidadDTO> funcsPersona = personaRolesService
						.obtenerFuncionalidadesUsuario(persona.getUsuario());
				if (!ObjectUtils.isNullOrEmpty(funcsPersona)) {
					for (FuncionalidadDTO func : funcsPersona) {
						LOG.info(func.getClave());
						if (!permisosPorRol.contains(func.getClave())) {
							permisosPorRol.add(func.getClave());
						}
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
				LOG.error(e.getMessage(), e);
			}

		}

		return new CustomUserDetail(new User(persona.getUsuario(), persona.getContrasenia(), rolesAutenticados),
				persona.getIdPersona(), permisosPorRol, persona.getActivo());
	}
}
