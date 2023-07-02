package mx.gob.sedesol.basegestor.commons.dto.planesyprogramas;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;

public class DetEstUniDidacticaDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer idUnidadDidactica;
	private Integer activo;
	private String evaluacion;
	private Date fechaActualizacion;
	private Date fechaRegistro;
	private String fuentesInformacion;
	private Integer numSubtemas;
	private String objetivosEspecificos;
	private String tituloUa;
	private String nombreUnidad;
	private Long usuarioModifico;
	private List<RelEstUnidadDidacticaDTO> relEstructuraUnidadDidacticas;
	private List<SubtemasUDidacticaDTO> subtemasUdidactica;
	private String actividadesAprendizaje;
	private String mediosRecursos;
	private List<RelUniDidacticaMaterialDTO> relUniDidacticaMaterial;
	private List<RelUDidacticaTposCompetenciaDTO> relUDidacticaTposCompetencia;

	private boolean edicionUD;

	public DetEstUniDidacticaDTO() {
		numSubtemas = new Integer(0);
	}

	public DetEstUniDidacticaDTO(String tituloUnidad, boolean isEdicion) {
		this.tituloUa = tituloUnidad;
		this.nombreUnidad = tituloUnidad;
		numSubtemas = new Integer(0);
		this.edicionUD = isEdicion;
	}

	/**
	 * @return the idUnidadDidactica
	 */
	public Integer getIdUnidadDidactica() {
		return idUnidadDidactica;
	}

	/**
	 * @param idUnidadDidactica
	 *            the idUnidadDidactica to set
	 */
	public void setIdUnidadDidactica(Integer idUnidadDidactica) {
		this.idUnidadDidactica = idUnidadDidactica;
	}

	/**
	 * @return the activo
	 */
	public Integer getActivo() {
		return activo;
	}

	/**
	 * @param activo
	 *            the activo to set
	 */
	public void setActivo(Integer activo) {
		this.activo = activo;
	}

	/**
	 * @return the evaluacion
	 */
	public String getEvaluacion() {
		return evaluacion;
	}

	/**
	 * @param evaluacion
	 *            the evaluacion to set
	 */
	public void setEvaluacion(String evaluacion) {
		this.evaluacion = evaluacion;
	}

	/**
	 * @return the fechaActualizacion
	 */
	public Date getFechaActualizacion() {
		return fechaActualizacion;
	}

	/**
	 * @param fechaActualizacion
	 *            the fechaActualizacion to set
	 */
	public void setFechaActualizacion(Date fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}

	/**
	 * @return the fechaRegistro
	 */
	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	/**
	 * @param fechaRegistro
	 *            the fechaRegistro to set
	 */
	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	/**
	 * @return the fuentesInformacion
	 */
	public String getFuentesInformacion() {
		return fuentesInformacion;
	}

	/**
	 * @param fuentesInformacion
	 *            the fuentesInformacion to set
	 */
	public void setFuentesInformacion(String fuentesInformacion) {
		this.fuentesInformacion = fuentesInformacion;
	}

	/**
	 * @return the numSubtemas
	 */
	public Integer getNumSubtemas() {
		return numSubtemas;
	}

	/**
	 * @param numSubtemas
	 *            the numSubtemas to set
	 */
	public void setNumSubtemas(Integer numSubtemas) {

		this.numSubtemas = numSubtemas;
		// Valida el numero de subtemas

		// Eliminacion de subtemas
		if (!ObjectUtils.isNullOrEmpty(this.subtemasUdidactica) && this.numSubtemas < this.subtemasUdidactica.size()) {
			this.subtemasUdidactica.remove(this.subtemasUdidactica.size() - 1);
		}

		if (!ObjectUtils.isNullOrEmpty(this.subtemasUdidactica) && (!edicionUD && this.numSubtemas > 0)) {

			for (int i = 1; i <= (this.numSubtemas - this.subtemasUdidactica.size()); i++) {
				subtemasUdidactica.add(new SubtemasUDidacticaDTO());
			}

		} else if (!edicionUD && ObjectUtils.isNotNull(this.numSubtemas) && this.numSubtemas > 0) {
			subtemasUdidactica = new ArrayList<SubtemasUDidacticaDTO>();
			for (int i = 1; i <= this.numSubtemas; i++) {
				subtemasUdidactica.add(new SubtemasUDidacticaDTO());
			}
		}
	}

	/**
	 * @return the objetivosEspecificos
	 */
	public String getObjetivosEspecificos() {
		return objetivosEspecificos;
	}

	/**
	 * @param objetivosEspecificos
	 *            the objetivosEspecificos to set
	 */
	public void setObjetivosEspecificos(String objetivosEspecificos) {
		this.objetivosEspecificos = objetivosEspecificos;
	}

	/**
	 * @return the tituloUa
	 */
	public String getTituloUa() {
		return tituloUa;
	}

	/**
	 * @param tituloUa
	 *            the tituloUa to set
	 */
	public void setTituloUa(String tituloUa) {
		this.tituloUa = tituloUa;
	}

	/**
	 * @return the usuarioModifico
	 */
	public Long getUsuarioModifico() {
		return usuarioModifico;
	}

	/**
	 * @param usuarioModifico
	 *            the usuarioModifico to set
	 */
	public void setUsuarioModifico(Long usuarioModifico) {
		this.usuarioModifico = usuarioModifico;
	}

	/**
	 * @return the subtemasUdidactica
	 */
	public List<SubtemasUDidacticaDTO> getSubtemasUdidactica() {
		return subtemasUdidactica;
	}

	/**
	 * @param subtemasUdidactica
	 *            the subtemasUdidactica to set
	 */
	public void setSubtemasUdidactica(List<SubtemasUDidacticaDTO> subtemasUdidactica) {
		this.subtemasUdidactica = subtemasUdidactica;
	}

	/**
	 * @return the actividadesAprendizaje
	 */
	public String getActividadesAprendizaje() {
		return actividadesAprendizaje;
	}

	/**
	 * @param actividadesAprendizaje
	 *            the actividadesAprendizaje to set
	 */
	public void setActividadesAprendizaje(String actividadesAprendizaje) {
		this.actividadesAprendizaje = actividadesAprendizaje;
	}

	/**
	 * @return the mediosRecursos
	 */
	public String getMediosRecursos() {
		return mediosRecursos;
	}

	/**
	 * @param mediosRecursos
	 *            the mediosRecursos to set
	 */
	public void setMediosRecursos(String mediosRecursos) {
		this.mediosRecursos = mediosRecursos;
	}

	/**
	 * @return the relUniDidacticaMaterial
	 */
	public List<RelUniDidacticaMaterialDTO> getRelUniDidacticaMaterial() {

		return this.relUniDidacticaMaterial;
	}

	/**
	 * @param relUniDidacticaMaterial
	 *            the relUniDidacticaMaterial to set
	 */
	public void setRelUniDidacticaMaterial(List<RelUniDidacticaMaterialDTO> relUniDidacticaMaterial) {
		this.relUniDidacticaMaterial = relUniDidacticaMaterial;
	}

	/**
	 * @return the relEstructuraUnidadDidacticas
	 */
	public List<RelEstUnidadDidacticaDTO> getRelEstructuraUnidadDidacticas() {
		if (this.relEstructuraUnidadDidacticas == null)
			this.relEstructuraUnidadDidacticas = new ArrayList<>();

		return this.relEstructuraUnidadDidacticas;
	}

	/**
	 * @param relEstructuraUnidadDidacticas
	 *            the relEstructuraUnidadDidacticas to set
	 */
	public void setRelEstructuraUnidadDidacticas(List<RelEstUnidadDidacticaDTO> relEstructuraUnidadDidacticas) {
		this.relEstructuraUnidadDidacticas = relEstructuraUnidadDidacticas;
	}

	/**
	 * @return the relUDidacticaTposCompetencia
	 */
	public List<RelUDidacticaTposCompetenciaDTO> getRelUDidacticaTposCompetencia() {

		return this.relUDidacticaTposCompetencia;
	}

	/**
	 * @param relUDidacticaTposCompetencia
	 *            the relUDidacticaTposCompetencia to set
	 */
	public void setRelUDidacticaTposCompetencia(List<RelUDidacticaTposCompetenciaDTO> relUDidacticaTposCompetencia) {
		this.relUDidacticaTposCompetencia = relUDidacticaTposCompetencia;
	}

	/**
	 * @return the nombreUnidad
	 */
	public String getNombreUnidad() {
		return nombreUnidad;
	}

	/**
	 * @param nombreUnidad
	 *            the nombreUnidad to set
	 */
	public void setNombreUnidad(String nombreUnidad) {
		this.nombreUnidad = nombreUnidad;
	}

}
