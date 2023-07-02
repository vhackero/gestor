package mx.gob.sedesol.basegestor.commons.dto.ws;

import java.io.Serializable;

public class GuardarUsuarioRequest implements Serializable {

	private static final long serialVersionUID = 4664671018804075265L;
	
	protected GuardarUsuarioRequestType guardarUsuarioRequestType;
    
    public GuardarUsuarioRequest() {
		this.guardarUsuarioRequestType = new GuardarUsuarioRequestType();
	}

    public GuardarUsuarioRequestType getGuardarUsuarioRequestType() {
        return guardarUsuarioRequestType;
    }

    public void setGuardarUsuarioRequestType(GuardarUsuarioRequestType value) {
        this.guardarUsuarioRequestType = value;
    }

}
