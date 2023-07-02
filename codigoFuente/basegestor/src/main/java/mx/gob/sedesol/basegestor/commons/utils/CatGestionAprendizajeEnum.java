package mx.gob.sedesol.basegestor.commons.utils;
import mx.gob.sedesol.basegestor.model.entities.gestionaprendizaje.*;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.CatTipoResponsabilidadEc;

public enum CatGestionAprendizajeEnum {
	
	
				
	CAT_CLASIFICACION_ARCHIVO_OA    ("CATALOGO CLASIFICACION ARCHIVO OA","catClasificacionArchivoOa",1,CatClasificacionArchivoOa.class,Boolean.FALSE),
	CAT_ESTADO_AVA                  ("CATALOGO ESTADO AVA",              "catEstadoAva",2,CatEstadoAva.class,Boolean.FALSE),	
	CAT_TEMA_RECURSO                ("CATALOGO TEMA RECURSO",            "catTemaRescurso",3,CatTemaRecurso.class,Boolean.FALSE),
	CAT_TIPO_CONTENIDO_OA           ("CATALOGO TIPO CONTENIDO",          "catTipoContenido",4,CatTipoContenidoOa.class,Boolean.TRUE),	
	CAT_NIVEL_GRANULARIDAD_OA       ("CATALOGO NIVEL GRANULARIDAD",      "catNivelGranularidadOa",5,CatNivelGranularidadOa.class,Boolean.TRUE),
	CAT_TIPO_ESTRUCTURA_OA          ("CATALOGO TIPO ESTRUCTURA",         "catTipoEstructuraOa",6,CatTipoEstructuraOa.class,Boolean.TRUE), 
	CAT_COMPOSICION_OBJETO_OA       ("CATALOGO COMPOSICION OBJETO",      "catComposicionObjetoOa",7,CatComposicionObjetoOa.class,Boolean.TRUE),
	CAT_DENSIDAD_OBJETO_OA          ("CATALOGO DENSIDAD OBJETO",         "catDensidadSemanticaOa",8,CatDensidadSemanticaOa.class,Boolean.TRUE),
	CAT_NIVEL_INTERACTIVIDAD_OA     ("CATALOGO NIVEL INTERACTIVIDAD",    "catNivelInteractividadOa",9,CatNivelInteractividadOa.class,Boolean.TRUE),
	CAT_RECURSO_DIDACTICO_OA        ("CATALOGO RECURSO DIDACTICO",       "catRecursoDidacticoOa",10,CatRecursoDidacticoOa.class,Boolean.TRUE),
	CAT_TIPO_INTERACTIVIDAD_OA      ("CATALOGO TIPO INTERACTIVIDAD",     "catTipoInteractividadOa",11,CatTipoInteractividadOa.class,Boolean.TRUE),
	CAT_TIPO_RESPONSABILIDAD_EC     ("CATALOGO TIPO RESPONSABILIDAD",    "catTipoResponsabilidadEc",12,CatTipoResponsabilidadEc.class,Boolean.TRUE),
	CAT_DIFICULTAD_OA               ("CATALOGO DIFICULTAD",              "catDificultadOa",13,CatDificultadOa.class,Boolean.TRUE),
	CAT_ESTATUS_OA                  ("CATALOGO ESTATUS",                 "catEstatusFoa",14,CatEstatusFoa.class,Boolean.FALSE),
	CAT_FORMATO_OA                  ("CATALOGO FORMATO",                 "catFormatoOa", 15,CatFormatoFoa.class,Boolean.TRUE),
	CAT_IDIOMA_OA                   ("CATALOGO IDIOMA",                  "catIdiomaOa",16,CatIdiomaOa.class,Boolean.TRUE),	
	CATALOGO_ACTIVIDAD_REFORZAMIENTO("CATALOGO ACTIVIDAD REFORZAMIENTO", "catActividadReforzamientoFoa",17,CatActividadReforzamientoFoa.class,Boolean.TRUE),
	CATALOGO_AMBITO_APLICACION      ("CATALOGO AMBITO APLICACION",       "catAmbitoAplicacionFoa",18,CatAmbitoAplicacionFoa.class,Boolean.TRUE),
	CATALOGO_ELEMENTOS_MULTIMEDIA   ("CATALOGO ELEMENTOS MULTIMEDIA",    "catElementosMultimedia",19,CatElementosMultimedia.class,Boolean.TRUE),
	CATALOGO_RELACION_OTROS_OBJETO  ("CATALOGO RELACION OTROS OBJETO",   "catRelacionOtrosObjeto",20,CatRelacionOtrosObjeto.class,Boolean.TRUE),
	CATALOGO_RECURSO_PREDOMINANTE   ("CATALOGO RECURSO PREDOMINANTE",    "catRecursoPredominanteFoa",21,CatRecursoPredominanteFoa.class,Boolean.TRUE);

	
	
	    private String nombre;
	    private String id;
	    private Integer orden;
	    private Class<?> classEntidad;
	    private boolean visualizarReg;

	    CatGestionAprendizajeEnum(String nombre, String id, Integer orden, Class<?> classEntidad, boolean visualizarReg) {
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
