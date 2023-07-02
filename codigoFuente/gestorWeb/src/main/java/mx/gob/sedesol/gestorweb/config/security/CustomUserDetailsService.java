package mx.gob.sedesol.gestorweb.config.security;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.gob.sedesol.basegestor.commons.dto.admin.FuncionalidadDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.PersonaRolDTO;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.model.entities.admin.TblPersona;
import mx.gob.sedesol.basegestor.service.admin.PersonaRolesService;
import mx.gob.sedesol.basegestor.service.admin.PersonaService;

@Service("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

	private static final Logger log = Logger.getLogger(CustomUserDetailsService.class);

	@Autowired
	private PersonaService personaService;

	@Autowired
	private PersonaRolesService personaRolesService;

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String usuario) throws UsernameNotFoundException {
		TblPersona persona = personaService.buscarPorNombreUsuario(usuario);
		if (persona == null)
			throw new UsernameNotFoundException(usuario);

		List<GrantedAuthority> rolesAutenticados = new ArrayList<>();
		List<String> permisosPorRol = new ArrayList<>();

		if (!ObjectUtils.isNullOrEmpty(persona.getRelPersonaRoles())) {

			try {

				List<PersonaRolDTO> lstRoles = personaRolesService
						.obtieneRelPersonaRolesPorUsuario(persona.getUsuario());
				if (!ObjectUtils.isNullOrEmpty(lstRoles)) {
					for (PersonaRolDTO pr : lstRoles) {
						if (!rolesAutenticados.contains(pr.getRol().getClave()))
							rolesAutenticados.add(new SimpleGrantedAuthority(pr.getRol().getClave()));
					}
				}

				List<FuncionalidadDTO> funcsPersona = personaRolesService
						.obtenerFuncionalidadesUsuario(persona.getUsuario());
				if (!ObjectUtils.isNullOrEmpty(funcsPersona)) {
					for (FuncionalidadDTO func : funcsPersona) {
						//log.info(func.getClave());
						if (!permisosPorRol.contains(func.getClave()))
							permisosPorRol.add(func.getClave());
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
				log.error(e.getMessage(), e);
			}

		}

		return new CustomUserDetail(new User(persona.getUsuario(), persona.getContrasenia(), rolesAutenticados),
				persona.getIdPersona(), permisosPorRol, persona.getActivo());
	}

}
