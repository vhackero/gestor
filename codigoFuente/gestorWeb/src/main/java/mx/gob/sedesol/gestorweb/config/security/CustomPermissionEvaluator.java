/**
 * 
 */
package mx.gob.sedesol.gestorweb.config.security;

import java.io.Serializable;

import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

/**
 * @author ormg
 *
 */
@Component
public class CustomPermissionEvaluator implements PermissionEvaluator {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.security.access.PermissionEvaluator#hasPermission(org
	 * .springframework.security.core.Authentication, java.lang.Object,
	 * java.lang.Object)
	 */

	@Override
	public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {

		String permiso = (String) permission;

		// System.out.println( authentication.getPrincipal().getClass() + " " +
		// authentication.getPrincipal());
		try {
			CustomUserDetail detUsuarioLogueado = (CustomUserDetail) authentication.getPrincipal();
			if (detUsuarioLogueado != null && !detUsuarioLogueado.getPermisosRol().isEmpty()) {
				if (detUsuarioLogueado.getPermisosRol().contains(permiso))
					return true;
			}
		} catch (Exception e) {
			System.out.println("OCURRIO UN STRING CANNOT BE CAST TO CUSTOM USER DETAIL");
			return false;
		}

		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.security.access.PermissionEvaluator#hasPermission(org
	 * .springframework.security.core.Authentication, java.io.Serializable,
	 * java.lang.String, java.lang.Object)
	 */
	@Override
	public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType,
			Object permission) {
		throw new RuntimeException("No soportado --> CustomPermissionEvaluator.class ");
	}
}
