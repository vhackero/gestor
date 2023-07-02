package mx.gob.sedesol.basegestor.commons.dto.gestionescolar;

import java.math.BigDecimal;
import java.util.List;


public class ParticipanteXML {
	

    private int idGrupo;
	
	private int secuencia;
	
	private String nombre;
	  
	private String apellidoPaterno;
	   
	private String apellidoMaterno;
	   
	private List<AsistenciaXML> asistencia;
	   
	private BigDecimal porcentaje;
	

	public int getIdGrupo() {
		return idGrupo;
	}
	public void setIdGrupo(int idGrupo) {
		this.idGrupo = idGrupo;
	}
	public int getSecuencia() {
		return secuencia;
	}
	public void setSecuencia(int secuencia) {
		this.secuencia = secuencia;
	}

	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidoPaterno() {
		return apellidoPaterno;
	}
	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}

	public String getApellidoMaterno() {
		return apellidoMaterno;
	}
	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
	}


	public BigDecimal getPorcentaje() {
		return porcentaje;
	}
	public void setPorcentaje(BigDecimal porcentaje) {
		this.porcentaje = porcentaje;
	}
	public List<AsistenciaXML> getAsistencia() {
		return asistencia;
	}
	public void setAsistencia(List<AsistenciaXML> asistencia) {
		this.asistencia = asistencia;
	}
	
	
}
