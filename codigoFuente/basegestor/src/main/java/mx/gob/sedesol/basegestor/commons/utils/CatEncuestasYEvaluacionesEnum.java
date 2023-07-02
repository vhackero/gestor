/**
 * 
 */
package mx.gob.sedesol.basegestor.commons.utils;

import mx.gob.sedesol.basegestor.model.entities.encuestas.CatEncuestaContexto;
import mx.gob.sedesol.basegestor.model.entities.encuestas.CatEncuestaEstatus;
import mx.gob.sedesol.basegestor.model.entities.encuestas.CatEncuestaObjetivo;
import mx.gob.sedesol.basegestor.model.entities.encuestas.CatEncuestaTipo;

/**
 * @author jhcortes
 *
 */
public enum CatEncuestasYEvaluacionesEnum {
	
	CAT_ENCUESTAS_CONTEXTO("CATÁLOGO CONTEXTO","catContexto",1,CatEncuestaContexto.class,Boolean.FALSE),
	CAT_ENCUESTAS_ESTATUS("CATÁLOGO ESTATUS","catEstatus",2,CatEncuestaEstatus.class,Boolean.FALSE),
	CAT_ENCUESTAS_OBJETIVO("CATÁLOGO DIRIGIDO A","catObjetivo",3,CatEncuestaObjetivo.class,Boolean.FALSE),
	CAT_ENCUESTAS_TIPO("CATÁLOGO TIPO ENCUESTA","catTipo",4,CatEncuestaTipo.class,Boolean.FALSE);
	
	private String nombre;
	private String id;
	private Integer orden;
	private Class<?> classEntidad;
	private boolean visualizarReg;
	
	CatEncuestasYEvaluacionesEnum(String nombre, String id, Integer orden, Class<?> classEntidad, boolean visualizarReg) {
		this.nombre = nombre;
		this.id = id;
		this.orden = orden;
		this.classEntidad = classEntidad;
		this.visualizarReg = visualizarReg;
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

	public boolean isVisualizarReg() {
		return visualizarReg;
	}

	public void setVisualizarReg(boolean visualizarReg) {
		this.visualizarReg = visualizarReg;
	}
	
	
}
