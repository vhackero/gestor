package mx.gob.sedesol.basegestor.commons.dto.gestionescolar;

//import org.apache.commons.lang.StringUtils;

public class CalificacionECDTO {

	private String nombreEvaluacion;
	private Double calificacion;
	private Integer ponderacion;
	private Double califPonderacion;
	
	
	public CalificacionECDTO(){
		nombreEvaluacion = "";
		calificacion = new Double(0);
		califPonderacion = new Double(0);
		ponderacion = new Integer(0);
	}
	
	
	/**
	 * @return the nombreEvaluacion
	 */
	public String getNombreEvaluacion() {
		return nombreEvaluacion;
	}
	/**
	 * @param nombreEvaluacion the nombreEvaluacion to set
	 */
	public void setNombreEvaluacion(String nombreEvaluacion) {
		this.nombreEvaluacion = nombreEvaluacion;
	}
	/**
	 * @return the calificacion
	 */
	public Double getCalificacion() {
		return calificacion;
	}
	/**
	 * @param calificacion the calificacion to set
	 */
	public void setCalificacion(Double calificacion) {
		this.calificacion = calificacion;
	}


	/**
	 * @return the ponderacion
	 */
	public Integer getPonderacion() {
		return ponderacion;
	}


	/**
	 * @param ponderacion the ponderacion to set
	 */
	public void setPonderacion(Integer ponderacion) {
		this.ponderacion = ponderacion;
	}


	/**
	 * @return the califPonderacion
	 */
	public Double getCalifPonderacion() {
		return califPonderacion;
	}


	/**
	 * @param califPonderacion the califPonderacion to set
	 */
	public void setCalifPonderacion(Double califPonderacion) {
		this.califPonderacion = califPonderacion;
	}
}
