package mx.gob.sedesol.basegestor.commons.dto.ws;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ArrayOfUsuarios implements Serializable {

	private static final long serialVersionUID = 828677011001765382L;
	
	protected List<Usuario> usuario;

    public List<Usuario> getUsuario() {
        if (usuario == null) {
            usuario = new ArrayList<Usuario>();
        }
        return this.usuario;
    }

}
