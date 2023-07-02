package mx.gob.sedesol.basegestor.commons.dto.ws;

import java.io.Serializable;

public class ActualizarUsuarioRequestType implements Serializable {

	private static final long serialVersionUID = -8553818334047810777L;
	
	protected Usuario usuario;

    public ActualizarUsuarioRequestType() {
		this.usuario = new Usuario();
	}
    
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario value) {
        this.usuario = value;
    }

}
