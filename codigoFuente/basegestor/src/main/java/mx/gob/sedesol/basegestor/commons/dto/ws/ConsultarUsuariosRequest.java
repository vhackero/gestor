package mx.gob.sedesol.basegestor.commons.dto.ws;

import java.io.Serializable;

public class ConsultarUsuariosRequest implements Serializable {

	private static final long serialVersionUID = 6296792471502853568L;
	
	protected ConsultarUsuariosRequestType consultarUsuariosRequestType;

    public ConsultarUsuariosRequest() {
		this.consultarUsuariosRequestType = new ConsultarUsuariosRequestType();
	}
    
    public ConsultarUsuariosRequestType getConsultarUsuariosRequestType() {
        return consultarUsuariosRequestType;
    }

    public void setConsultarUsuariosRequestType(ConsultarUsuariosRequestType value) {
        this.consultarUsuariosRequestType = value;
    }

}
