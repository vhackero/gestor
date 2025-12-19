package mx.gob.sedesol.basegestor.model.entities.planesyprogramas;

import java.io.Serializable;

public class TblDispersionesBusqueda implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer idDispersion;
	private Integer idPlan;
	private Integer idPrograma;
	private String plan;
	private String programa;
	private String clave;
	private String semestre;
	private String bloque;
	private Integer noEstudiantes;
	private Integer gruposGenerales;
	private Integer cupoGeneral;
	private Integer grupoResto;
	private Integer cupoResto;
	private String acciones;
	private Integer tipoMatriculacion;
	private Integer gruposCreados;
	private Integer usuariosMatriculados;
	private boolean crearGruposHabilitado;
	private boolean matricularHabilitado;

	public Integer getIdDispersion() {
		return idDispersion;
	}

	public void setIdDispersion(Integer idDispersion) {
		this.idDispersion = idDispersion;
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

	public String getPlan() {
		return plan;
	}

	public void setPlan(String plan) {
		this.plan = plan;
	}

	public String getPrograma() {
		return programa;
	}

	public void setPrograma(String programa) {
		this.programa = programa;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public String getSemestre() {
		return semestre;
	}

	public void setSemestre(String semestre) {
		this.semestre = semestre;
	}

	public String getBloque() {
		return bloque;
	}

	public void setBloque(String bloque) {
		this.bloque = bloque;
	}

	public Integer getNoEstudiantes() {
		return noEstudiantes;
	}

	public void setNoEstudiantes(Integer noEstudiantes) {
		this.noEstudiantes = noEstudiantes;
	}

	public Integer getGruposGenerales() {
		return gruposGenerales;
	}

	public void setGruposGenerales(Integer gruposGenerales) {
		this.gruposGenerales = gruposGenerales;
	}

	public Integer getCupoGeneral() {
		return cupoGeneral;
	}

	public void setCupoGeneral(Integer cupoGeneral) {
		this.cupoGeneral = cupoGeneral;
	}

	public Integer getGrupoResto() {
		return grupoResto;
	}

	public void setGrupoResto(Integer grupoResto) {
		this.grupoResto = grupoResto;
	}

	public Integer getCupoResto() {
		return cupoResto;
	}

	public void setCupoResto(Integer cupoResto) {
		this.cupoResto = cupoResto;
	}

	public String getAcciones() {
		return acciones;
	}

	public void setAcciones(String acciones) {
		this.acciones = acciones;
	}
	
	public Integer getTipoMatriculacion() {
		return tipoMatriculacion;
	}
	
	public void setTipoMatriculacion(Integer tipoMatriculacion) {
		this.tipoMatriculacion = tipoMatriculacion;
	}
	
	public Integer getGruposCreados() {
		return gruposCreados;
	}
	
	public void setGruposCreados(Integer gruposCreados) {
		this.gruposCreados = gruposCreados;
	}
	
	public Integer getUsuariosMatriculados() {
		return usuariosMatriculados;
	}
	
	public void setUsuariosMatriculados(Integer usuariosMatriculados) {
		this.usuariosMatriculados = usuariosMatriculados;
	}
	
	public boolean isCrearGruposHabilitado() {
		return crearGruposHabilitado;
	}
	
	public void setCrearGruposHabilitado(boolean crearGruposHabilitado) {
		this.crearGruposHabilitado = crearGruposHabilitado;
	}
	
	public boolean isMatricularHabilitado() {
		return matricularHabilitado;
	}
	
	public void setMatricularHabilitado(boolean matricularHabilitado) {
		this.matricularHabilitado = matricularHabilitado;
	}
	
	
}
