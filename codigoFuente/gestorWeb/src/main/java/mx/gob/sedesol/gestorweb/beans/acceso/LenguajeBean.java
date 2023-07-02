package mx.gob.sedesol.gestorweb.beans.acceso;

import java.util.Locale;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@SessionScoped
@ManagedBean
public class LenguajeBean extends BaseBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Locale locale = getFacesContext().getViewRoot().getLocale();
	
	/**
	 * 
	 * @param lenguaje
	 */
	public void cambiarLenguaje(String lenguaje) {
        locale = new Locale(lenguaje);
        getFacesContext().getViewRoot().setLocale(new Locale(lenguaje));
    }
	
	 public Locale getLocale() {
	        return locale;
	    }

	    public String getLenguaje() {
	        return locale.getLanguage();
	    }

}
