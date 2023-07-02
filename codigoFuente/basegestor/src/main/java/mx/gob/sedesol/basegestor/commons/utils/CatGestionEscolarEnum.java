package mx.gob.sedesol.basegestor.commons.utils;

import mx.gob.sedesol.basegestor.model.entities.gestionescolar.CatAlcanceArea;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.CatAsistencia;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.CatCategoriaEventoCapacitacion;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.CatClasificacionAva;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.CatClasificacionInformacion;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.CatDestinatariosEc;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.CatDictamen;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.CatEstadoEventoCapacitacion;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.CatTipoCalificacionEc;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.CatTipoResponsabilidadEc;

/**
 * Created by jhcortes on 1/02/17.
 */
public enum CatGestionEscolarEnum {

    CAT_CATEGORIA_EVENTO_CAPACITACION("CATEGORÍA EVENTO DE CAPACITACI\u00D3N","eventoCapacitacion",1,CatCategoriaEventoCapacitacion.class,Boolean.TRUE),
    CAT_DICTAMEN("DICTAMEN","catalogoDictamen",2,CatDictamen.class,Boolean.FALSE),
    CAT_ESTADO_EVENTO_CAPACITACION("ESTADO EVENTO CAPACITACIÓN","catalogoEstadoEventoCapacitacion",4,CatEstadoEventoCapacitacion.class,Boolean.FALSE),
    CAT_TIPO_CALIFICACION_EC("TIPO CALIFICACI\u00D3N EC","catalogoTipoCalificacionEC",6,CatTipoCalificacionEc.class,Boolean.FALSE),
    CAT_CLASIFICACION_INFORMACION("CALIFICACI\u00D3N INFORMACI\u00D3N","catalogoClasificacionInformacion",7,CatClasificacionInformacion.class,Boolean.TRUE),
	CAT_CLASIFICACION_AVA("CLASIFICACI\u00D3N AVA","catalogoClasificacionAva",8,CatClasificacionAva.class,Boolean.FALSE),
	CAT_ALCANCE_AREA("ALCANCE \u00C1REA","catAlcanceArea",9,CatAlcanceArea.class,Boolean.FALSE),
	CAT_TIPO_RESPONSABILIDAD_EC("TIPO RESPONSABILIDAD","catTipoResponsabilidadEc",10,CatTipoResponsabilidadEc.class,Boolean.FALSE),
	CAT_DESTINATARIOS_EC("DESTINATARIOS EC", "catDestinatariosEc", 11, CatDestinatariosEc.class,Boolean.FALSE),
	CAT_ASISTENCIA("CAT ASISTENCIA","cat_asistencia",12,CatAsistencia.class,Boolean.FALSE);

	private String nombre;
	private String id;
	private Integer orden;
	private Class<?> classEntidad;
	private boolean visualizarReg;

	CatGestionEscolarEnum(String nombre, String id, Integer orden, Class<?> classEntidad, boolean visualizarReg) {
		this.nombre = nombre;
		this.id = id;
		this.orden = orden;
		this.classEntidad = classEntidad;
		this.visualizarReg = visualizarReg;
	}

	
	public boolean isVisualizarReg() {
		return visualizarReg;
	}



	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre
	 *            the nombre to set
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
	 * @param id
	 *            the id to set
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
	 * @param orden
	 *            the orden to set
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
	 * @param classEntidad
	 *            the classEntidad to set
	 */
	public void setClassEntidad(Class<?> classEntidad) {
		this.classEntidad = classEntidad;
	}
}
