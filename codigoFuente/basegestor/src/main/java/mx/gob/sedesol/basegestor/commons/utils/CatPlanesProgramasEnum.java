package mx.gob.sedesol.basegestor.commons.utils;

import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.*;

public enum CatPlanesProgramasEnum {
	
	CAT_DOCUMENTOS_EXPIDE_PLAN("DOCUMENTOS EXPIDE PLAN","docExpPln",1,CatDocumentosExpidePlan.class,Boolean.TRUE),
	CAT_TIPO_PLAN("TIPO PLAN","tpoPlan",2, CatTipoPlan.class,Boolean.TRUE),
	CAT_ESTADO_PLAN("ESTATUS PLAN","edoPlan",3,CatEstatusPlan.class,Boolean.FALSE),
	CAT_ESTADO_PROGRAMA("ESTATUS PROGRAMAS","edoProg",4,CatStatusPrograma.class,Boolean.FALSE),
	CAT_TECNICAS_DIDACTICAS_PROGRAMA("TECNICAS DIDACTICAS PROGRAMA","tecDidProg",5,CatTecnicaDidacticaPrograma.class,Boolean.TRUE),
	CAT_ACTIVIDADES_APRENDIZAJE_PROGRAMA("ACTIVIDADES APRENDIZAJE PROGRAMA","actAprPrg",6,CatActividadesAprendizajePrograma.class,Boolean.TRUE),
	CAT_NIVEL_ENSENANZA_PROGRAMA("NIVEL ENSEÑANZA PROGRAMA","nvlEnsPrg",7,CatNivelEnsenanzaPrograma.class,Boolean.TRUE),
	CAT_OBJETO_CURRICULAR("OBJETO CURRICULAR","objCurr",11, CatObjetoCurricular.class,Boolean.TRUE); 
	
	private String nombre;
	private String id;
	private Integer orden;
	private Class<?> classEntidad;
	private boolean visualizarReg;
	
	CatPlanesProgramasEnum(String nombre, String id, Integer orden, Class<?> classEntidad, boolean visualizar){
		this.nombre = nombre;
		this.id = id;
		this.orden = orden;
		this.classEntidad = classEntidad;
		this.visualizarReg = visualizar;
		
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
	
	public boolean getVisualizarReg(){
		return visualizarReg;
	}
}
