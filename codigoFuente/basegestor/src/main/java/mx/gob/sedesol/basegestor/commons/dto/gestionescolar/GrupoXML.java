package mx.gob.sedesol.basegestor.commons.dto.gestionescolar;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement  ( name = "grupo" )
public class GrupoXML {
	

	private int idGrupo;

	private String descripcion;

	private List<ParticipanteXML> participantes = new ArrayList<>(); 

	@XmlElement  
	public int getIdGrupo() {
		return idGrupo;
	}
	public void setIdGrupo(int idGrupo) {
		this.idGrupo = idGrupo;
	}
	@XmlElement  
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	@XmlElementWrapper(name = "participantes")
	@XmlElement(name = "participante")
	public List<ParticipanteXML> getParticipantes() {
		return participantes;
	}
	public void setParticipantes(List<ParticipanteXML> participantes) {
		this.participantes = participantes;
	}
	
	public void addParticipante(ParticipanteXML participante){
		this.participantes.add(participante);
	}
	
	
}
