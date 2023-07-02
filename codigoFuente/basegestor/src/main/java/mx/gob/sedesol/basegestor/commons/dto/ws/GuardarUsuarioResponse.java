package mx.gob.sedesol.basegestor.commons.dto.ws;

import java.io.Serializable;

public class GuardarUsuarioResponse implements Serializable {

	private static final long serialVersionUID = -6357574320156578338L;
	
	protected GuardarUsuarioResponseType guardarUsuarioResponseType;
    
	public GuardarUsuarioResponse(){
		
	}
	
    public GuardarUsuarioResponse(Usuario usuario) {
		this.guardarUsuarioResponseType = new GuardarUsuarioResponseType(usuario);
	}

    public GuardarUsuarioResponseType getGuardarUsuarioResponseType() {
        return guardarUsuarioResponseType;
    }

    public void setGuardarUsuarioResponseType(GuardarUsuarioResponseType value) {
        this.guardarUsuarioResponseType = value;
    }

}
