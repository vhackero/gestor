package mx.gob.sedesol.basegestor.commons.dto.ws;

import java.io.Serializable;

public class ActualizarUsuarioResponse implements Serializable {

	private static final long serialVersionUID = 1991329946247211419L;
	
	protected ActualizarUsuarioResponseType actualizarUsuarioResponseType;
    
    public ActualizarUsuarioResponse() {
		this.actualizarUsuarioResponseType = new ActualizarUsuarioResponseType();
	}
    
    public ActualizarUsuarioResponse(Usuario usuario){
		this.actualizarUsuarioResponseType = new ActualizarUsuarioResponseType(usuario);
	}

    public ActualizarUsuarioResponseType getActualizarUsuarioResponseType() {
        return actualizarUsuarioResponseType;
    }

    public void setActualizarUsuarioResponseType(ActualizarUsuarioResponseType value) {
        this.actualizarUsuarioResponseType = value;
    }

}
