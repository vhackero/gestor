package mx.gob.sedesol.basegestor.commons.dto.ws;

import java.io.Serializable;

public class EliminarUsuarioRequestType implements Serializable {

	private static final long serialVersionUID = -7728661399024813631L;
	
	protected String idUsuario;
    protected String correoElectronico;

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String value) {
        this.idUsuario = value;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String value) {
        this.correoElectronico = value;
    }

}
