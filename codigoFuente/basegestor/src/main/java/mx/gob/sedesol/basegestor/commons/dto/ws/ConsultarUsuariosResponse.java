package mx.gob.sedesol.basegestor.commons.dto.ws;

import java.io.Serializable;

public class ConsultarUsuariosResponse implements Serializable {

	private static final long serialVersionUID = -1659217180724172230L;
	
	protected ConsultarUsuariosResponseType consultarUsuariosResponseType;
    
    public ConsultarUsuariosResponse() {
		this.consultarUsuariosResponseType = new ConsultarUsuariosResponseType();
	}
    
    public ConsultarUsuariosResponse(ArrayOfUsuarios arrayOfUsuarios){
    	this.consultarUsuariosResponseType = new ConsultarUsuariosResponseType(arrayOfUsuarios);
    }

    public ConsultarUsuariosResponseType getConsultarUsuariosResponseType() {
        return consultarUsuariosResponseType;
    }

    public void setConsultarUsuariosResponseType(ConsultarUsuariosResponseType value) {
        this.consultarUsuariosResponseType = value;
    }

}
