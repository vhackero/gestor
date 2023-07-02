package mx.gob.sedesol.basegestor.commons.dto.ws;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ArrayOfEventos implements Serializable {

	private static final long serialVersionUID = 4012788240549412419L;

	private List<Evento> eventos;

	public void setEventos(List<Evento> eventos) {
		this.eventos = eventos;
	}

	public List<Evento> getEventos() {
		if (eventos == null) {
			eventos = new ArrayList<>();
		}
		return this.eventos;
	}
}
