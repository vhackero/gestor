package mx.gob.sedesol.gestorweb.config.security;

public class CustomSocialUserDetailService  {
	//implements SocialUserDetailsService
//	@Autowired
//	private CustomUserDetailsService customUserDetailsService;
//	@Override
//	public SocialUserDetails loadUserByUserId(String usuario) throws UsernameNotFoundException, DataAccessException {
//		
//		UserDetails detallesUsuario = customUserDetailsService.loadUserByUsername(usuario);
//		if(ObjectUtils.isNull(detallesUsuario))
//			throw new SocialAuthenticationException("No existe un usuario local mapeado al usuario de red social " + usuario);
//		
////		return new SocialUser(detallesUsuario.getUsername(), detallesUsuario.getPassword(), detallesUsuario.isEnabled()
////				, detallesUsuario.isAccountNonExpired(), detallesUsuario.isCredentialsNonExpired(), detallesUsuario.isAccountNonLocked(), detallesUsuario.getAuthorities());
//		
//		return (SocialUserDetails) detallesUsuario;
//	}

}
