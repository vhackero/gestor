/**
 * 
 */
package mx.gob.sedesol.gestorweb.commons.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

import org.springframework.security.core.GrantedAuthority;

import mx.gob.sedesol.gestorweb.config.listener.SessionCounterListener;


/**
 * @author PlanetMedia
 *
 */
public class UsuarioSessionDTO implements Serializable, HttpSessionBindingListener {

	
	private static final long serialVersionUID = 1L;
	
	private Long idPersona;
	private String usuario;
	private List< GrantedAuthority> roles;
	private boolean enSession;
	private Map<String, String> listaPermisos;
	/**
	 * @return  usuario
	 */
	public String getUsuario() {
		return usuario;
	}
	/**
	 * @param usuario set usuario 
	 */
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	/**
	 * @return  roles
	 */
	public List< GrantedAuthority> getRoles() {
		return roles;
	}
	/**
	 * @param roles set roles 
	 */
	public void setRoles(List< GrantedAuthority> roles) {
		this.roles = roles;
	}
	/**
	 * @return the idPersona
	 */
	public Long getIdPersona() {
		return idPersona;
	}
	/**
	 * @param idPersona the idPersona to set
	 */
	public void setIdPersona(Long idPersona) {
		this.idPersona = idPersona;
	}
	/**
	 * @return the enSession
	 */
	public boolean isEnSession() {
		return enSession;
	}
	/**
	 * @param enSession the enSession to set
	 */
	public void setEnSession(boolean enSession) {
		this.enSession = enSession;
	}
	public Map<String, String> getListaPermisos() {
		return listaPermisos;
	}
	public void setListaPermisos(Map<String, String> listaPermisos) {
		this.listaPermisos = listaPermisos;
	}
	@Override
	public String toString() {
		return "UsuarioSessionDTO [idPersona=" + idPersona + ", usuario=" + usuario + ", roles=" + roles
				+ ", enSession=" + enSession + ", listaPermisos=" + listaPermisos + "]";
	}
	/**
	 * 
	 */
	@Override
	public void valueBound(HttpSessionBindingEvent event) {
		// TODO Auto-generated method stub
		SessionCounterListener.sumaSesion();
		
	}
	@Override
	public void valueUnbound(HttpSessionBindingEvent event) {
		// TODO Auto-generated method stub
		SessionCounterListener.restaSesion();
	}
	
}
