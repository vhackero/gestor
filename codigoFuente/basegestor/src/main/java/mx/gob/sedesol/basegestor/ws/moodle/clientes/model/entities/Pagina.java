package mx.gob.sedesol.basegestor.ws.moodle.clientes.model.entities;

public class Pagina {
	private int course; // Este es el curso de la pagina
	private int section; // Este es la seccion de la pagina
	private String name; // Este es el nombre de la pagina
	private String intro; // Este es el intro de la pagina
	private String content; // este es el contenido de la pagina

	public int getCourse() {
		return course;
	}

	public void setCourse(int course) {
		this.course = course;
	}

	public int getSection() {
		return section;
	}

	public void setSection(int section) {
		this.section = section;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	@Override
	public String toString() {
		return "Pagina [course=" + course + ", section=" + section + ", name=" + name + ", intro=" + intro
				+ ", content=" + content + "]";
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
