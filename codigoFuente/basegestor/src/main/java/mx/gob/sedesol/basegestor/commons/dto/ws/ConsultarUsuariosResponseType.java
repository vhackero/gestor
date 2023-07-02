package mx.gob.sedesol.basegestor.commons.dto.ws;

import java.io.Serializable;

public class ConsultarUsuariosResponseType implements Serializable {

	private static final long serialVersionUID = 7306553505523222059L;
	
	protected ArrayOfUsuarios arrayOfUsuarios;
    
    public ConsultarUsuariosResponseType() {
		this.arrayOfUsuarios = new ArrayOfUsuarios();
	}
    public ConsultarUsuariosResponseType(ArrayOfUsuarios arrayOfUsuarios) {
		this.arrayOfUsuarios = arrayOfUsuarios;
	}

    public ArrayOfUsuarios getArrayOfUsuarios() {
        return arrayOfUsuarios;
    }

    public void setArrayOfUsuarios(ArrayOfUsuarios value) {
        this.arrayOfUsuarios = value;
    }

}
