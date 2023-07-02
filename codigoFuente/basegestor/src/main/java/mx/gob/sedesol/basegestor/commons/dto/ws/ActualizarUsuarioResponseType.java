package mx.gob.sedesol.basegestor.commons.dto.ws;

import java.io.Serializable;

public class ActualizarUsuarioResponseType implements Serializable {

	private static final long serialVersionUID = -5557336962673904070L;

	protected Usuario usuario;


	public ActualizarUsuarioResponseType() {
		this.usuario = new Usuario();
	}
	
	public ActualizarUsuarioResponseType(Usuario usuario) {
		this.usuario = usuario;
	}


	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario value) {
		this.usuario = value;
	}

}
