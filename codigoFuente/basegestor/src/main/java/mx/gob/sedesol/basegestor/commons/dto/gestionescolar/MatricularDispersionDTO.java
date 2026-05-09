package mx.gob.sedesol.basegestor.commons.dto.gestionescolar;

import java.io.Serializable;

public class MatricularDispersionDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer idDispersion;
	private Integer idConvocatoria;
	private Integer idTipoProceso;
	private Integer idProcesoInscripcion;
	private Long idUsuario;
	private Integer idPrograma;
	private Integer idPlan;
	private Integer gruposGenerales;
	private Integer grupoResto;
	private Boolean matriculacionCompartidos;
	private String nombreProgramaSeleccionado;

	public Integer getIdDispersion() {
		return idDispersion;
	}

	public void setIdDispersion(Integer idDispersion) {
		this.idDispersion = idDispersion;
	}

	public Integer getIdConvocatoria() {
		return idConvocatoria;
	}

	public void setIdConvocatoria(Integer idConvocatoria) {
		this.idConvocatoria = idConvocatoria;
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

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public Integer getIdPrograma() {
		return idPrograma;
	}

	public void setIdPrograma(Integer idPrograma) {
		this.idPrograma = idPrograma;
	}

	public Integer getIdPlan() {
		return idPlan;
	}

	public void setIdPlan(Integer idPlan) {
		this.idPlan = idPlan;
	}

	public Integer getGruposGenerales() {
		return gruposGenerales;
	}

	public void setGruposGenerales(Integer gruposGenerales) {
		this.gruposGenerales = gruposGenerales;
	}

	public Integer getGrupoResto() {
		return grupoResto;
	}

	public void setGrupoResto(Integer grupoResto) {
		this.grupoResto = grupoResto;
	}

	public Boolean getMatriculacionCompartidos() {
		return matriculacionCompartidos;
	}

	public void setMatriculacionCompartidos(Boolean matriculacionCompartidos) {
		this.matriculacionCompartidos = matriculacionCompartidos;
	}

	public String getNombreProgramaSeleccionado() {
		return nombreProgramaSeleccionado;
	}

	public void setNombreProgramaSeleccionado(String nombreProgramaSeleccionado) {
		this.nombreProgramaSeleccionado = nombreProgramaSeleccionado;
	}

}
