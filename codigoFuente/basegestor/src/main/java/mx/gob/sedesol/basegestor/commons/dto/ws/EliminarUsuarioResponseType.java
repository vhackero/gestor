package mx.gob.sedesol.basegestor.commons.dto.ws;

import java.io.Serializable;

public class EliminarUsuarioResponseType implements Serializable {

	private static final long serialVersionUID = 4078121495237279403L;
	
	protected Usuario usuario;
    
    public EliminarUsuarioResponseType() {
		this.usuario = new Usuario();
	}
    
    public EliminarUsuarioResponseType(Usuario usuario){
    	this.usuario = usuario;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario value) {
        this.usuario = value;
    }

}
