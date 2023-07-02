package mx.gob.sedesol.basegestor.commons.dto.gestionescolar;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import mx.gob.sedesol.basegestor.commons.dto.admin.CatalogoComunDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.PersonaDTO;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;

public class TablaCalificacionesDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private PersonaDTO participante;
	private Double califFinal;
	private Double califTotal;
	private String asistencia;
	private String nombreEvaluacion;
	private Integer consecutivo;
	private CatalogoComunDTO dictamen;
	private List<CalificacionECDTO> calificacionEC;
	private Integer idGpoEvaluacion;
	private Integer tpoEvaluacion;
	private Integer idGpoParticipante;
	private Integer idPersonaMoodle;
	private boolean conAsistencia;

	public TablaCalificacionesDTO() {

	}

	public TablaCalificacionesDTO(int numEvaluaciones) {

		dictamen = new CatalogoComunDTO();
		this.califFinal = new Double(0);
		this.califTotal = new Double(0);
		this.calificacionEC = new ArrayList<CalificacionECDTO>();
		for (int x = 0; x < numEvaluaciones; x++) {
			CalificacionECDTO evalCal = new CalificacionECDTO();
			this.calificacionEC.add(evalCal);
		}
	}

	/**
	 * @return the participante
	 */
	public PersonaDTO getParticipante() {
		return participante;
	}

	/**
	 * @param participante
	 *            the participante to set
	 */
	public void setParticipante(PersonaDTO participante) {
		this.participante = participante;
	}

	/**
	 * @return the califFinal
	 */
	public Double getCalifFinal() {
		if (ObjectUtils.isNull(this.califFinal))
			this.califFinal = new Double(0);

		return califFinal;
	}

	/**
	 * @param califFinal
	 *            the califFinal to set
	 */
	public void setCalifFinal(Double califFinal) {

		this.califFinal = califFinal;
	}

	/**
	 * @return the asistencia
	 */
	public String getAsistencia() {
		return asistencia;
	}

	/**
	 * @param asistencia
	 *            the asistencia to set
	 */
	public void setAsistencia(String asistencia) {
		this.asistencia = asistencia;
	}

	/**
	 * @return the nombreEvaluacion
	 */
	public String getNombreEvaluacion() {
		return nombreEvaluacion;
	}

	/**
	 * @param nombreEvaluacion
	 *            the nombreEvaluacion to set
	 */
	public void setNombreEvaluacion(String nombreEvaluacion) {
		this.nombreEvaluacion = nombreEvaluacion;
	}

	/**
	 * @return the consecutivo
	 */
	public Integer getConsecutivo() {
		return consecutivo;
	}

	/**
	 * @param consecutivo
	 *            the consecutivo to set
	 */
	public void setConsecutivo(Integer consecutivo) {
		this.consecutivo = consecutivo;
	}

	/**
	 * @return the dictamen
	 */
	public CatalogoComunDTO getDictamen() {
		return dictamen;
	}

	/**
	 * @param dictamen
	 *            the dictamen to set
	 */
	public void setDictamen(CatalogoComunDTO dictamen) {
		this.dictamen = dictamen;
	}

	/**
	 * @return the calificacionEC
	 */
	public List<CalificacionECDTO> getCalificacionEC() {
		return calificacionEC;
	}

	/**
	 * @param calificacionEC
	 *            the calificacionEC to set
	 */
	public void setCalificacionEC(List<CalificacionECDTO> calificacionEC) {
		this.calificacionEC = calificacionEC;
	}

	/**
	 * 
	 * @param numEvaluaciones
	 */
	public void actualizaArrayCalificaciones(Integer numEvaluaciones) {
		for (int x = this.calificacionEC.size(); x < numEvaluaciones; x++) {
			CalificacionECDTO evalCal = new CalificacionECDTO();
			evalCal.setCalificacion(new Double(0));
			this.calificacionEC.add(evalCal);
		}
	}

	/**
	 * 
	 * @param calificacion
	 */
	public void agregaCalificacion(Double calificacion, String nombreEval) {

		if (ObjectUtils.isNull(this.calificacionEC))
			this.calificacionEC = new ArrayList<>();

		CalificacionECDTO calEc = new CalificacionECDTO();
		calEc.setCalificacion(calificacion);
		calEc.setCalifPonderacion(calificacion);
		calEc.setNombreEvaluacion(nombreEval);

		this.calificacionEC.add(calEc);

	}

	/**
	 * @return the idGpoEvaluacion
	 */
	public Integer getIdGpoEvaluacion() {
		return idGpoEvaluacion;
	}

	/**
	 * @param idGpoEvaluacion
	 *            the idGpoEvaluacion to set
	 */
	public void setIdGpoEvaluacion(Integer idGpoEvaluacion) {
		this.idGpoEvaluacion = idGpoEvaluacion;
	}

	/**
	 * @return the tpoEvaluacion
	 */
	public Integer getTpoEvaluacion() {
		return tpoEvaluacion;
	}

	/**
	 * @param tpoEvaluacion
	 *            the tpoEvaluacion to set
	 */
	public void setTpoEvaluacion(Integer tpoEvaluacion) {
		this.tpoEvaluacion = tpoEvaluacion;
	}

	/**
	 * @return the idGpoParticipante
	 */
	public Integer getIdGpoParticipante() {
		return idGpoParticipante;
	}

	/**
	 * @param idGpoParticipante
	 *            the idGpoParticipante to set
	 */
	public void setIdGpoParticipante(Integer idGpoParticipante) {
		this.idGpoParticipante = idGpoParticipante;
	}

	/**
	 * @return the idPersonaMoodle
	 */
	public Integer getIdPersonaMoodle() {
		return idPersonaMoodle;
	}

	/**
	 * @param idPersonaMoodle
	 *            the idPersonaMoodle to set
	 */
	public void setIdPersonaMoodle(Integer idPersonaMoodle) {
		this.idPersonaMoodle = idPersonaMoodle;
	}

	/**
	 * @return the conAsistencia
	 */
	public boolean isConAsistencia() {
		return conAsistencia;
	}

	/**
	 * @param conAsistencia
	 *            the conAsistencia to set
	 */
	public void setConAsistencia(boolean conAsistencia) {
		this.conAsistencia = conAsistencia;
	}

	/**
	 * @return the califTotal
	 */
	public Double getCalifTotal() {
		return califTotal;
	}

	/**
	 * @param califTotal
	 *            the califTotal to set
	 */
	public void setCalifTotal(Double califTotal) {

		if (ObjectUtils.isNull(this.califTotal))
			this.califTotal = new Double(0);
		this.califTotal = califTotal;
	}

}
