package mx.gob.sedesol.basegestor.ws.moodle.clientes.model.entities;

import java.util.List;

/**
 *
 * @author robert o_O
 */
public class CursoSeccion {
	private String fullname; // full name de curso
	private String shortname; // short name de curso
	private Datos category;
	private List<Seccion> sections;
	private Recursos resources;

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getShortname() {
		return shortname;
	}

	public void setShortname(String shortname) {
		this.shortname = shortname;
	}

	public Datos getCategory() {
		return category;
	}

	public void setCategory(Datos category) {
		this.category = category;
	}

	public List<Seccion> getSections() {
		return sections;
	}

	public void setSections(List<Seccion> sections) {
		this.sections = sections;
	}

	public Recursos getResources() {
		return resources;
	}

	public void setResources(Recursos resources) {
		this.resources = resources;
	}

	@Override
	public String toString() {
		return "CursoSeccion [fullname=" + fullname + ", shortname=" + shortname + ", category=" + category
				+ ", sections=" + sections + ", resources=" + resources + "]";
	}

	

}
