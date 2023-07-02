package mx.gob.sedesol.basegestor.commons.dto.ws;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Resultado implements Serializable {

	private static final long serialVersionUID = 1225230200281707904L;
	
	private boolean correcto;
	private List<String> mensajes;
	
	public Resultado() {
		this.mensajes = new ArrayList<>();
	}
	
	public boolean isCorrecto() {
		return correcto;
	}
	public List<String> getMensajes() {
		return mensajes;
	}
	public void setCorrecto(boolean correcto) {
		this.correcto = correcto;
	}
	public void setMensajes(List<String> mensajes) {
		this.mensajes = mensajes;
	}

}
