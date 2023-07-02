package mx.gob.sedesol.basegestor.commons.dto.ws;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ArrayOfPerfil implements Serializable {

	private static final long serialVersionUID = 5841739714852708400L;
	
	protected List<Perfil> perfil;
    
    public ArrayOfPerfil() {
		this.perfil = new ArrayList<>();
	}

    public List<Perfil> getPerfil() {
        return this.perfil;
    }

}
