package mx.gob.sedesol.basegestor.commons.dto;

import java.io.Serializable;

public class NodoDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param id
	 * @param nombre
	 * @param tipoObjCurricular
	 */
	public NodoDTO(Integer id, String nombre, String nombreObjCurricular) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.nombreObjCurricular = nombreObjCurricular;
	}
	
	public NodoDTO(Integer id, String nombre, Integer tipoObjCurricular) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.tipoObjCurricular = tipoObjCurricular;
	}
	
	public NodoDTO(Integer id, String nombre) {
		super();
		this.id = id;
		this.nombre = nombre;
	}
	
	public NodoDTO(Integer id, String nombre, String nombreObjCurricular, Integer idCategoriaMdl) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.nombreObjCurricular = nombreObjCurricular;
		this.idCategoriaMdl = idCategoriaMdl;
	}
	
	public NodoDTO(){
		
	}
	
	private Integer id;
	private String nombre;
	private Integer tipoObjCurricular;
	private String nombreObjCurricular;
	private Integer idCategoriaMdl;
	
	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
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
	 * @return the tipoObjCurricular
	 */
	public Integer getTipoObjCurricular() {
		return tipoObjCurricular;
	}
	/**
	 * @param tipoObjCurricular the tipoObjCurricular to set
	 */
	public void setTipoObjCurricular(Integer tipoObjCurricular) {
		this.tipoObjCurricular = tipoObjCurricular;
	}
	/**
	 * @return the nombreObjCurricular
	 */
	public String getNombreObjCurricular() {
		return nombreObjCurricular;
	}
	/**
	 * @param nombreObjCurricular the nombreObjCurricular to set
	 */
	public void setNombreObjCurricular(String nombreObjCurricular) {
		this.nombreObjCurricular = nombreObjCurricular;
	}

	/**
	 * @return the idCategoriaMdl
	 */
	public Integer getIdCategoriaMdl() {
		return idCategoriaMdl;
	}

	/**
	 * @param idCategoriaMdl the idCategoriaMdl to set
	 */
	public void setIdCategoriaMdl(Integer idCategoriaMdl) {
		this.idCategoriaMdl = idCategoriaMdl;
	}
}
