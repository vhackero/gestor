package mx.gob.sedesol.basegestor.commons.dto.ws;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ArrayOfElemento implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<Elemento> elemento;
	
	public ArrayOfElemento(){
		this.elemento = new ArrayList<>();
	}

	public List<Elemento> getElemento() {
		return elemento;
	}

	public void setElemento(List<Elemento> elemento) {
		this.elemento = elemento;
	}

}
