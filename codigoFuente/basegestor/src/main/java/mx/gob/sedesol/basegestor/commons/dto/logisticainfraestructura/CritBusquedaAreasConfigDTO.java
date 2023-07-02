package mx.gob.sedesol.basegestor.commons.dto.logisticainfraestructura;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import mx.gob.sedesol.basegestor.commons.utils.MensajesSistemaEnum;

public class CritBusquedaAreasConfigDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2248974897580354303L;

	@NotNull(message = MensajesSistemaEnum.Constantes.GESTOR_WEB_GENERAL_DATO_REQUERIDO)
	private Integer idOrgGubernamental;
	@NotNull(message = MensajesSistemaEnum.Constantes.GESTOR_WEB_GENERAL_DATO_REQUERIDO)
	private Integer idUbicacion;
	@NotNull(message = MensajesSistemaEnum.Constantes.GESTOR_WEB_GENERAL_DATO_REQUERIDO)
	private Integer idSede;
	private Integer idArea;
	private Integer idAreaSede;
	@NotEmpty(message = MensajesSistemaEnum.Constantes.GESTOR_WEB_GENERAL_DATO_REQUERIDO)
	private String orgGubNombre;
	private String nombreUbicacion;
	private String nombreSede;
	private String nombreArea;

	public CritBusquedaAreasConfigDTO() {
		// TODO Auto-generated constructor stub
	}

	public CritBusquedaAreasConfigDTO(Integer idAreaSede) {
		this.idAreaSede = idAreaSede;
	}

	/**
	 * @return the idOrgGubernamental
	 */
	public Integer getIdOrgGubernamental() {
		return idOrgGubernamental;
	}

	/**
	 * @param idOrgGubernamental
	 *            the idOrgGubernamental to set
	 */
	public void setIdOrgGubernamental(Integer idOrgGubernamental) {
		this.idOrgGubernamental = idOrgGubernamental;
	}

	/**
	 * @return the idUbicacion
	 */
	public Integer getIdUbicacion() {
		return idUbicacion;
	}

	/**
	 * @param idUbicacion
	 *            the idUbicacion to set
	 */
	public void setIdUbicacion(Integer idUbicacion) {
		this.idUbicacion = idUbicacion;
	}

	/**
	 * @return the idSede
	 */
	public Integer getIdSede() {
		return idSede;
	}

	/**
	 * @param idSede
	 *            the idSede to set
	 */
	public void setIdSede(Integer idSede) {
		this.idSede = idSede;
	}

	/**
	 * @return the idArea
	 */
	public Integer getIdArea() {
		return idArea;
	}

	/**
	 * @param idArea
	 *            the idArea to set
	 */
	public void setIdArea(Integer idArea) {
		this.idArea = idArea;
	}

	/**
	 * @return the orgGubNombre
	 */
	public String getOrgGubNombre() {
		return orgGubNombre;
	}

	/**
	 * @param orgGubNombre
	 *            the orgGubNombre to set
	 */
	public void setOrgGubNombre(String orgGubNombre) {
		this.orgGubNombre = orgGubNombre;
	}

	/**
	 * @return the nombreUbicacion
	 */
	public String getNombreUbicacion() {
		return nombreUbicacion;
	}

	/**
	 * @param nombreUbicacion
	 *            the nombreUbicacion to set
	 */
	public void setNombreUbicacion(String nombreUbicacion) {
		this.nombreUbicacion = nombreUbicacion;
	}

	/**
	 * @return the nombreSede
	 */
	public String getNombreSede() {
		return nombreSede;
	}

	/**
	 * @param nombreSede
	 *            the nombreSede to set
	 */
	public void setNombreSede(String nombreSede) {
		this.nombreSede = nombreSede;
	}

	/**
	 * @return the nombreArea
	 */
	public String getNombreArea() {
		return nombreArea;
	}

	/**
	 * @param nombreArea
	 *            the nombreArea to set
	 */
	public void setNombreArea(String nombreArea) {
		this.nombreArea = nombreArea;
	}

	/**
	 * @return the idAreaSede
	 */
	public Integer getIdAreaSede() {
		return idAreaSede;
	}

	/**
	 * @param idAreaSede
	 *            the idAreaSede to set
	 */
	public void setIdAreaSede(Integer idAreaSede) {
		this.idAreaSede = idAreaSede;
	}

	@Override
	public String toString() {
		return "CritBusquedaAreasConfigDTO [idOrgGubernamental=" + idOrgGubernamental + ", idUbicacion=" + idUbicacion
				+ ", idSede=" + idSede + ", idArea=" + idArea + ", idAreaSede=" + idAreaSede + ", orgGubNombre="
				+ orgGubNombre + ", nombreUbicacion=" + nombreUbicacion + ", nombreSede=" + nombreSede + ", nombreArea="
				+ nombreArea + "]";
	}

}
