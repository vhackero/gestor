/**
 * 
 */
package mx.gob.sedesol.basegestor.commons.utils;

import mx.gob.sedesol.basegestor.model.entities.encuestas.TblPreguntasEncuesta;

/**
 * @author jhcortes
 *
 */
public enum TblPreguntasYOpcionesEncuestaEnum {

	TBL_PREGUNTAS_ENCUESTA("TABLA DE PREGUNTAS POR ENCUESTA","tblPreguntasEncuesta",5,TblPreguntasEncuesta.class);
	
	
	private String nombre;
	private String id;
	private Integer orden;
	private Class<?> classEntidad;
	
	TblPreguntasYOpcionesEncuestaEnum(String nombre, String id, Integer orden, Class<?> classEntidad) {
		this.nombre = nombre;
		this.id = id;
		this.orden = orden;
		this.classEntidad = classEntidad;
	}

	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the orden
	 */
	public Integer getOrden() {
		return orden;
	}

	/**
	 * @param orden the orden to set
	 */
	public void setOrden(Integer orden) {
		this.orden = orden;
	}

	/**
	 * @return the classEntidad
	 */
	public Class<?> getClassEntidad() {
		return classEntidad;
	}

	/**
	 * @param classEntidad the classEntidad to set
	 */
	public void setClassEntidad(Class<?> classEntidad) {
		this.classEntidad = classEntidad;
	}
	
	
}
