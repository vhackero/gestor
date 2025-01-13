package mx.gob.sedesol.basegestor.model.entities.planesyprogramas;

import java.io.Serializable;

public class TblDispersionesBusqueda implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	Integer idDispersion;

	String plan;
	
	String programa;
	
	String clave;
	
	String semestre;
	
	String bloque;
	
	Integer noEstudiantes;
	
	Integer gruposGenerales;
	
	Integer cupoGeneral;
	
	Integer grupoResto;
	
	Integer cupoResto;
	
	String acciones;

	
	
	public Integer getIdDispersion() {
		return idDispersion;
	}

	public void setIdDispersion(Integer idDispersion) {
		this.idDispersion = idDispersion;
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
	
	
}
