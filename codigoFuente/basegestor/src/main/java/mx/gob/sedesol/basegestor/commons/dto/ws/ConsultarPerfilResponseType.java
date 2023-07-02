package mx.gob.sedesol.basegestor.commons.dto.ws;

import java.io.Serializable;

public class ConsultarPerfilResponseType implements Serializable {

	private static final long serialVersionUID = -6522567991532053627L;

	protected ArrayOfPerfil arrayOfPerfil;

	public ConsultarPerfilResponseType(ArrayOfPerfil arrayOfPerfil) {
		this.arrayOfPerfil = arrayOfPerfil;
	}

	public ConsultarPerfilResponseType() {
		this.arrayOfPerfil = new ArrayOfPerfil();
	}

	public ArrayOfPerfil getArrayOfPerfil() {
		return arrayOfPerfil;
	}

	public void setArrayOfPerfil(ArrayOfPerfil value) {
		this.arrayOfPerfil = value;
	}

}
