package mx.gob.sedesol.basegestor.commons.dto.ws;

import java.io.Serializable;

public class ActualizarUsuarioRequest implements Serializable {

	private static final long serialVersionUID = 405184671241577155L;
	
	protected ActualizarUsuarioRequestType actualizarUsuarioRequestType;

    public ActualizarUsuarioRequest() {
		this.actualizarUsuarioRequestType = new ActualizarUsuarioRequestType();
	}
    
    public ActualizarUsuarioRequestType getActualizarUsuarioRequestType() {
        return actualizarUsuarioRequestType;
    }

    public void setActualizarUsuarioRequestType(ActualizarUsuarioRequestType value) {
        this.actualizarUsuarioRequestType = value;
    }

}
