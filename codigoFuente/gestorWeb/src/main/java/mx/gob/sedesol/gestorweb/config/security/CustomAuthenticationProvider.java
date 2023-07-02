package mx.gob.sedesol.gestorweb.config.security;


import java.util.Collection;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
	
	private static Logger logger = Logger.getLogger(CustomAuthenticationProvider.class);
	
	@Autowired
	private CustomUserDetailsService restuserDetailService;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	/**
	 * Metodo que realiza la autenticacion de usuario
	 */
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {

		logger.info("Autenticando usuario.");
		//UserDetails detallesUsuario = null;
		//detallesUsuario = restuserDetailService.loadUserByUsername(authentication.getName());
		if(authentication.getName() != null && !authentication.getName().isEmpty()){
			
			final CustomUserDetail detallesUsuarioCustom = (CustomUserDetail) restuserDetailService.loadUserByUsername(authentication.getName());
			if(detallesUsuarioCustom == null)
				throw new UsernameNotFoundException(String.format("No se encuentra registrado el usuario: ", authentication.getName()));
			
			if(detallesUsuarioCustom.getPassword().equalsIgnoreCase(authentication.getCredentials().toString())){
				 // return new UsernamePasswordAuthenticationToken(detallesUsuario.getUsername(), detallesUsuario.getPassword(), detallesUsuario.getAuthorities());
				return new Authentication() {
					
					/**
					 * 
					 */
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
				} ; 
				
				
			}else{
				throw new UsernameNotFoundException(String.format("La contraseña es incorrecta.", authentication.getName()));
			}
		}
		return null;
	}

	public boolean supports(Class<?> authentication) {
		 return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

	public BCryptPasswordEncoder getPasswordEncoder() {
		return passwordEncoder;
	}

	public void setPasswordEncoder(BCryptPasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

}
