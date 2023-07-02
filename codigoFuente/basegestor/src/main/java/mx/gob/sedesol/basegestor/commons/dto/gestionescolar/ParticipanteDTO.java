package mx.gob.sedesol.basegestor.commons.dto.gestionescolar;

import java.util.ArrayList;
import java.util.List;

public class ParticipanteDTO {
	private String nombre;
	private String apPaterno;
	private String apMaterno;
	private List<AsistenciaParticipanteDTO> asistencias = new ArrayList<AsistenciaParticipanteDTO>(); 
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApPaterno() {
		return apPaterno;
	}
	public void setApPaterno(String apPaterno) {
		this.apPaterno = apPaterno;
	}
	public String getApMaterno() {
		return apMaterno;
	}
	public void setApMaterno(String apMaterno) {
		this.apMaterno = apMaterno;
	}
	public List<AsistenciaParticipanteDTO> getAsistencias() {
		return asistencias;
	}
	public void setAsistencias(List<AsistenciaParticipanteDTO> asistencias) {
		this.asistencias = asistencias;
	}

	
	
	
	
}
