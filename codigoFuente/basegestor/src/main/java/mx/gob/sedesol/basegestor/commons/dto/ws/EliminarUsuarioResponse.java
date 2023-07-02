package mx.gob.sedesol.basegestor.commons.dto.ws;

import java.io.Serializable;

public class EliminarUsuarioResponse implements Serializable {

	private static final long serialVersionUID = 5964890428124842805L;
	
	protected EliminarUsuarioResponseType eliminarUsuarioResponseType;
    
    public EliminarUsuarioResponse() {
		this.eliminarUsuarioResponseType = new EliminarUsuarioResponseType();
	}
    
    public EliminarUsuarioResponse(Usuario usuario){
    	this.eliminarUsuarioResponseType = new EliminarUsuarioResponseType(usuario);
    }

    public EliminarUsuarioResponseType getEliminarUsuarioResponseType() {
        return eliminarUsuarioResponseType;
    }

    public void setEliminarUsuarioResponseType(EliminarUsuarioResponseType value) {
        this.eliminarUsuarioResponseType = value;
    }

}
