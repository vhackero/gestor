package mx.gob.sedesol.basegestor.commons.dto.gestionescolar;

import java.util.ArrayList;
import java.util.List;
 
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;  

  
@XmlRootElement  ( name = "RelAsistencias" )
public class GrupoParticipanteXML {
	

	private List<GrupoXML> grupos = new ArrayList<GrupoXML>();
	
	@XmlElementWrapper(name = "grupos")
	@XmlElement(name = "grupo")
	public List<GrupoXML> getGrupos() {
		return grupos;
	}

	public void setGrupos(List<GrupoXML> grupos) {
		this.grupos = grupos;
	}
	
	public void addGrupo(GrupoXML grupo) {
		this.grupos.add(grupo);
	}
	
	
	
	
	
}
