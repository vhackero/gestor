package mx.gob.sedesol.basegestor.commons.dto.ws;

import java.io.Serializable;

public class EliminarUsuarioRequest implements Serializable {

	private static final long serialVersionUID = 3643925975239025024L;

	protected EliminarUsuarioRequestType eliminarUsuarioRequestType;

	public EliminarUsuarioRequest() {
		this.eliminarUsuarioRequestType = new EliminarUsuarioRequestType();
	}

	public EliminarUsuarioRequestType getEliminarUsuarioRequestType() {
		return eliminarUsuarioRequestType;
	}

	public void setEliminarUsuarioRequestType(EliminarUsuarioRequestType value) {
		this.eliminarUsuarioRequestType = value;
	}

}
