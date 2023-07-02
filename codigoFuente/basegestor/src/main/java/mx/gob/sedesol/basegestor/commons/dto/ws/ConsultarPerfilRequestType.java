package mx.gob.sedesol.basegestor.commons.dto.ws;

import java.io.Serializable;

public class ConsultarPerfilRequestType implements Serializable {

	private static final long serialVersionUID = -7981893248620422529L;
	
	protected Perfil perfil;
    
    public ConsultarPerfilRequestType() {
		this.perfil = new Perfil();
	}

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil value) {
        this.perfil = value;
    }

}
