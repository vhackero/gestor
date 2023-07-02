package mx.gob.sedesol.basegestor.commons.dto.ws;

import java.io.Serializable;

public class GuardarUsuarioRequestType implements Serializable {

	private static final long serialVersionUID = -8664304748847065585L;
	
	protected Usuario usuario;
    
    public GuardarUsuarioRequestType() {
		this.usuario = new Usuario();
	}

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario value) {
        this.usuario = value;
    }

}
