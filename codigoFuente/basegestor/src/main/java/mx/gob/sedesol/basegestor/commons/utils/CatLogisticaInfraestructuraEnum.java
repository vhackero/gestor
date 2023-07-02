package mx.gob.sedesol.basegestor.commons.utils;

import mx.gob.sedesol.basegestor.model.entities.logisticainfraestructura.CatAreaInfraestructura;
import mx.gob.sedesol.basegestor.model.entities.logisticainfraestructura.CatDistribucionArea;
import mx.gob.sedesol.basegestor.model.entities.logisticainfraestructura.CatEstatusArea;
import mx.gob.sedesol.basegestor.model.entities.logisticainfraestructura.CatTipoRecurso;
import mx.gob.sedesol.basegestor.model.entities.logisticainfraestructura.CatUbicacionTerritorial;

public enum CatLogisticaInfraestructuraEnum {

	CAT_UBICACION_TERRITORIAL("CATALOGO UBICACION TERRITORIAL", "cat_ubicacion_terr",1,CatUbicacionTerritorial.class,Boolean.FALSE),
	CAT_ESTATUS_AREA("CATALOGO ESTATUS AREA","cat_estatus_area",2,CatEstatusArea.class,Boolean.FALSE),
	CAT_TIPOS_RECURSO("CATALOGO DE TIPOS RECURSO","cat_tipo_recurso",3,CatTipoRecurso.class,Boolean.FALSE),
	CAT_DISTRIBUCION_AREA("CATALOGO DISTRIBUCION AREA","cat_dist_area",4,CatDistribucionArea.class,Boolean.FALSE),
	CAT_AREAS_INFRAESTRUCTURA("CATALOGO DE AREAS","cat_areas_infraestructura",4,CatAreaInfraestructura.class,Boolean.FALSE); 
	
	private String nombre;
	private String id;
	private Integer orden;
	private Class<?> classEntidad;
	private boolean visualizarReg;
	
	CatLogisticaInfraestructuraEnum(String nombre, String id, Integer orden, Class<?> classEntidad, boolean visualizarReg){
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
	 * @return the orden
	 */
	public Integer getOrden() {
		return orden;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return the classEntidad
	 */
	public Class<?> getClassEntidad() {
		return classEntidad;
	}

	public boolean isVisualizarReg() {
		return visualizarReg;
	}

	public void setVisualizarReg(boolean visualizarReg) {
		this.visualizarReg = visualizarReg;
	}
}
