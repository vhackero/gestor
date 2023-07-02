package mx.gob.sedesol.basegestor.commons.dto.planesyprogramas;

import java.io.Serializable;

public class RelProgActAprendizajePKDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer idPrograma;
	private Integer idActividadAprendizaje;
	
	public Integer getIdPrograma() {
		return idPrograma;
	}
	public void setIdPrograma(Integer idPrograma) {
		this.idPrograma = idPrograma;
	}
	public Integer getIdActividadAprendizaje() {
		return idActividadAprendizaje;
	}
	public void setIdActividadAprendizaje(Integer idActividadAprendizaje) {
		this.idActividadAprendizaje = idActividadAprendizaje;
	}
}
