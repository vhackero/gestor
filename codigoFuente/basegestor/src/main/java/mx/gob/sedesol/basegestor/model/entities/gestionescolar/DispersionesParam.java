package mx.gob.sedesol.basegestor.model.entities.gestionescolar;

import java.io.Serializable;
import java.util.List;

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

	private Long idUsuarioAccion;

	private List<Integer> listaIdPlan;

	private List<Integer> listaIdPrograma;
	
	private Integer idProcesoInscripcionConDispersion;
	
	private Integer idProcesoInscripcionMatricular;

	private String nombreProgramaCompartidoSeleccionado;

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

	public List<Integer> getListaIdPlan() {
		return listaIdPlan;
	}

	public void setListaIdPlan(List<Integer> listaIdPlan) {
		this.listaIdPlan = listaIdPlan;
	}

	public List<Integer> getListaIdPrograma() {
		return listaIdPrograma;
	}

	public void setListaIdPrograma(List<Integer> listaIdPrograma) {
		this.listaIdPrograma = listaIdPrograma;
	}
	
	public Integer getIdProcesoInscripcionConDispersion() {
		return idProcesoInscripcionConDispersion;
	}
	
	public void setIdProcesoInscripcionConDispersion(Integer idProcesoInscripcionConDispersion) {
		this.idProcesoInscripcionConDispersion = idProcesoInscripcionConDispersion;
	}
	
	public Integer getIdProcesoInscripcionMatricular() {
		return idProcesoInscripcionMatricular;
	}
	
	public void setIdProcesoInscripcionMatricular(Integer idProcesoInscripcionMatricular) {
		this.idProcesoInscripcionMatricular = idProcesoInscripcionMatricular;
	}

	public String getNombreProgramaCompartidoSeleccionado() {
		return nombreProgramaCompartidoSeleccionado;
	}

	public void setNombreProgramaCompartidoSeleccionado(String nombreProgramaCompartidoSeleccionado) {
		this.nombreProgramaCompartidoSeleccionado = nombreProgramaCompartidoSeleccionado;
	}

	public Long getIdUsuarioAccion() {
		return idUsuarioAccion;
	}

	public void setIdUsuarioAccion(Long idUsuarioAccion) {
		this.idUsuarioAccion = idUsuarioAccion;
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
