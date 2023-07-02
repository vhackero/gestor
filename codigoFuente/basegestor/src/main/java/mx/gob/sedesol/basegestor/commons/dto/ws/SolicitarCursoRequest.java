package mx.gob.sedesol.basegestor.commons.dto.ws;

public class SolicitarCursoRequest {
	
	private Integer idCurso;
	private String nombre;
	private String correo;
	private String mensaje;
	
	public Integer getIdCurso() {
		return idCurso;
	}
	public void setIdCurso(Integer idCurso) {
		this.idCurso = idCurso;
	}
	public String getCorreo() {
		return correo;
	}
	public void setCorreo(String correo) {
		this.correo = correo;
	}
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombreInterezado(String nombre) {
		this.nombre = nombre;
	}

}
