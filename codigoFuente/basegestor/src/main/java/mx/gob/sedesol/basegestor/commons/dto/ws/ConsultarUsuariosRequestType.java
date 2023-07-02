package mx.gob.sedesol.basegestor.commons.dto.ws;

import java.io.Serializable;

public class ConsultarUsuariosRequestType implements Serializable {

	private static final long serialVersionUID = -7819648286253008696L;
	
	protected Usuario usuario;
    
    public ConsultarUsuariosRequestType() {
		this.usuario = new Usuario();
	}

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario value) {
        this.usuario = value;
    }

}
