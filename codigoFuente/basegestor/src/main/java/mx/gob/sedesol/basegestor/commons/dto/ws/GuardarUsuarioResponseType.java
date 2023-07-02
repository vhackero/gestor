package mx.gob.sedesol.basegestor.commons.dto.ws;

import java.io.Serializable;

public class GuardarUsuarioResponseType implements Serializable {

	private static final long serialVersionUID = -5114477978617961401L;
	
	protected Usuario usuario;
    
	public GuardarUsuarioResponseType(){
		
	}
	
    public GuardarUsuarioResponseType(Usuario usuario) {
		this.usuario = usuario;
	}

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario value) {
        this.usuario = value;
    }

}
