package mx.gob.sedesol.basegestor.ws.moodle.clientes.model.entities;

public class Leccion {
	private int course; // Este es el curso de la leccion
	private int section; // Este es la seccion de la leccion
	private String name; // Este es el nombre de la leccio
	private String intro; // Este es el intro de la leccio

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
		return "parameters[course]=" + course + "& parameters[section]=" + section + "& parameters[name]=" + name
				+ "& parameters[intro]=" + intro;
	}
}
