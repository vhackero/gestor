package mx.gob.sedesol.basegestor.commons.dto.ws;

public class SolicitarCursoResponse {
	
	private boolean exito;
	private String mensaje;
	
	public boolean isExito() {
		return exito;
	}
	public void setExito(boolean exito) {
		this.exito = exito;
	}
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

}
