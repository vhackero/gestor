package mx.gob.sedesol.basegestor.commons.dto.gestionescolar;

import java.io.Serializable;

public class DispersionCreacionResultadoDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private int eventosCreados;
	private int gruposCreados;

	public int getEventosCreados() {
		return eventosCreados;
	}

	public void setEventosCreados(int eventosCreados) {
		this.eventosCreados = eventosCreados;
	}

	public int getGruposCreados() {
		return gruposCreados;
	}

	public void setGruposCreados(int gruposCreados) {
		this.gruposCreados = gruposCreados;
	}

}
