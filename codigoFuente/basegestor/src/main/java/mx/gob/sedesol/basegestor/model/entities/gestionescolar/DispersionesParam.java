package mx.gob.sedesol.basegestor.model.entities.gestionescolar;

import java.io.Serializable;

public class DispersionesParam implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer idConvocatoriaSeleccionada;
	
	private Integer idTipoProceso;
	
	private Integer idProcesoInscripcion;
	
	private Integer idTipoMatriculacion;
	
	private Integer idPlan;

	private Integer idPrograma;
	
	private Integer existente;

	public Integer getExistente() {
		return existente;
	}

	public void setExistente(Integer existente) {
		this.existente = existente;
	}

	public Integer getIdPlan() {
		return idPlan;
	}

	public void setIdPlan(Integer idPlan) {
		this.idPlan = idPlan;
	}

	public Integer getIdPrograma() {
		return idPrograma;
	}

	public void setIdPrograma(Integer idPrograma) {
		this.idPrograma = idPrograma;
	}
	
	public Integer getIdConvocatoriaSeleccionada() {
		return idConvocatoriaSeleccionada;
	}

	public void setIdConvocatoriaSeleccionada(Integer idConvocatoriaSeleccionada) {
		this.idConvocatoriaSeleccionada = idConvocatoriaSeleccionada;
	}

	public Integer getIdTipoProceso() {
		return idTipoProceso;
	}

	public void setIdTipoProceso(Integer idTipoProceso) {
		this.idTipoProceso = idTipoProceso;
	}

	public Integer getIdProcesoInscripcion() {
		return idProcesoInscripcion;
	}

	public void setIdProcesoInscripcion(Integer idProcesoInscripcion) {
		this.idProcesoInscripcion = idProcesoInscripcion;
	}

	public Integer getIdTipoMatriculacion() {
		return idTipoMatriculacion;
	}

	public void setIdTipoMatriculacion(Integer idTipoMatriculacion) {
		this.idTipoMatriculacion = idTipoMatriculacion;
	}

}
