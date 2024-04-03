package mx.gob.sedesol.basegestor.commons.dto.gestionescolar;

import java.io.Serializable;

/**
 * 
 */

public class CalificacionRecordDTO implements Serializable{
	
	/**
	 *
	 */
	private static final long serialVersionUID = 2488627809362056351L;

	private String no;
	
	private String matricula;
	
	private String estudiante;
	
	private String calificacion;

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getEstudiante() {
		return estudiante;
	}

	public void setEstudiante(String estudiante) {
		this.estudiante = estudiante;
	}

	public String getCalificacion() {
		return calificacion;
	}

	public void setCalificacion(String calificacion) {
		this.calificacion = calificacion;
	}
	
}
