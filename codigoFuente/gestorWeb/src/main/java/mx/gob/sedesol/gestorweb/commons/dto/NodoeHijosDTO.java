package mx.gob.sedesol.gestorweb.commons.dto;

import java.util.ArrayList;
import java.util.List;

import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;

public class NodoeHijosDTO {
	
	private Integer idPadre;
	private Integer idNodo;
	private Integer idObjCurr;
	private Integer nivel;
	private String nombre;
	private List<NodoeHijosDTO> nodosHijos;
	
	/**
	 * @return the idPadre
	 */
	public Integer getIdPadre() {
		return idPadre;
	}
	/**
	 * @param idPadre the idPadre to set
	 */
	public void setIdPadre(Integer idPadre) {
		this.idPadre = idPadre;
	}
	/**
	 * @return the idNodo
	 */
	public Integer getIdNodo() {
		return idNodo;
	}
	/**
	 * @param idNodo the idNodo to set
	 */
	public void setIdNodo(Integer idNodo) {
		this.idNodo = idNodo;
	}
	/**
	 * @return the nodosHijos
	 */
	public List<NodoeHijosDTO> getNodosHijos() {
		if(ObjectUtils.isNullOrEmpty(nodosHijos))
			this.nodosHijos = new ArrayList<>();
		
		return nodosHijos;
	}
	/**
	 * @param nodosHijos the nodosHijos to set
	 */
	public void setNodosHijos(List<NodoeHijosDTO> nodosHijos) {
		this.nodosHijos = nodosHijos;
	}
	/**
	 * @return the idObjCurr
	 */
	public Integer getIdObjCurr() {
		return idObjCurr;
	}
	/**
	 * @param idObjCurr the idObjCurr to set
	 */
	public void setIdObjCurr(Integer idObjCurr) {
		this.idObjCurr = idObjCurr;
	}
	/**
	 * @return the nivel
	 */
	public Integer getNivel() {
		return nivel;
	}
	/**
	 * @param nivel the nivel to set
	 */
	public void setNivel(Integer nivel) {
		this.nivel = nivel;
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
	
}
