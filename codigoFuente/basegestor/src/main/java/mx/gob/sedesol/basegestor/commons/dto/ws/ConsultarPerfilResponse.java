package mx.gob.sedesol.basegestor.commons.dto.ws;

import java.io.Serializable;

public class ConsultarPerfilResponse implements Serializable {

	private static final long serialVersionUID = 3849204112950437857L;
	
	protected ConsultarPerfilResponseType consultarPerfilResponseType;
    
    public ConsultarPerfilResponse() {
		this.consultarPerfilResponseType = new ConsultarPerfilResponseType();
	}
    
    public ConsultarPerfilResponse(ArrayOfPerfil arrayOfPerfil){
    	this.consultarPerfilResponseType = new ConsultarPerfilResponseType(arrayOfPerfil);
    }

    public ConsultarPerfilResponseType getConsultarPerfilResponseType() {
        return consultarPerfilResponseType;
    }

    public void setConsultarPerfilResponseType(ConsultarPerfilResponseType value) {
        this.consultarPerfilResponseType = value;
    }

}
