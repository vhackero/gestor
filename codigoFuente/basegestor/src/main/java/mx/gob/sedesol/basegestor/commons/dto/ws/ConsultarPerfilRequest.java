package mx.gob.sedesol.basegestor.commons.dto.ws;

import java.io.Serializable;

public class ConsultarPerfilRequest implements Serializable {

	private static final long serialVersionUID = 3612477684379620064L;
	
	protected ConsultarPerfilRequestType consultarPerfilRequestType;
    
    public ConsultarPerfilRequest() {
		this.consultarPerfilRequestType = new ConsultarPerfilRequestType();
	}

    public ConsultarPerfilRequestType getConsultarPerfilRequestType() {
        return consultarPerfilRequestType;
    }

    public void setConsultarPerfilRequestType(ConsultarPerfilRequestType value) {
        this.consultarPerfilRequestType = value;
    }

}
