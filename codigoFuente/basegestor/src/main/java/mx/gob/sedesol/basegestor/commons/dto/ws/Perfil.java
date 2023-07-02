package mx.gob.sedesol.basegestor.commons.dto.ws;

import java.io.Serializable;

public class Perfil implements Serializable {

	private static final long serialVersionUID = -6844494807487357897L;
	
	private String idPerfil;
	private String descripcionPerfil;
	private String clavePerfil;
	
	public String getIdPerfil() {
		return idPerfil;
	}
	public String getDescripcionPerfil() {
		return descripcionPerfil;
	}
	public String getClavePerfil() {
		return clavePerfil;
	}
	public void setIdPerfil(String idPerfil) {
		this.idPerfil = idPerfil;
	}
	public void setDescripcionPerfil(String descripcionPerfil) {
		this.descripcionPerfil = descripcionPerfil;
	}
	public void setClavePerfil(String clavePerfil) {
		this.clavePerfil = clavePerfil;
	}

}
