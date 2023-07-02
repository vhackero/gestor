package mx.gob.sedesol.gestorweb.commons.dto;

public class CursoPublico {

	private String idCurso;

	private Evento curso;

	private Programa programa;


	public String getIdCurso() {
		return idCurso;
	}

	public void setIdCurso(String idCurso) {
		this.idCurso = idCurso;
	}

	public Evento getCurso() {
		return curso;
	}

	public void setCurso(Evento curso) {
		this.curso = curso;
	}

	public Programa getPrograma() {
		return programa;
	}

	public void setPrograma(Programa programa) {
		this.programa = programa;
	}

	
	
}
