package mx.gob.sedesol.gestorweb.beans.administracion.publico;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import mx.gob.sedesol.gestorweb.commons.dto.CasoExito;

@ManagedBean
@ApplicationScoped
public class CasosExitoBean {
	
	private List<CasoExito> carrusel;

	public List<CasoExito> getCarrusel() {
		return carrusel;
	}

	public void setCarrusel(List<CasoExito> carrusel) {
		this.carrusel = carrusel;
	}
	
	@PostConstruct
	public void init(){
		String context = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath(); 
		carrusel = new LinkedList<CasoExito>();
		carrusel.add(new CasoExito(1, "/images/anuncios/anuncio_01.jpg"));
		carrusel.add(new CasoExito(2, "/images/anuncios/anuncio_02.jpg"));
		carrusel.add(new CasoExito(3, "/images/anuncios/anuncio_03.jpg"));
	}
}
