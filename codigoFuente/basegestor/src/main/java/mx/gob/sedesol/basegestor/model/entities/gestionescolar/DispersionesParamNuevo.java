package mx.gob.sedesol.basegestor.model.entities.gestionescolar;

import java.io.Serializable;

public class DispersionesParamNuevo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	Integer idDispersion;
	
	Integer noGrupos;
	
	Integer estudiantesGrupo;
	
	Integer grupoResto;
	
	Integer cupoResto;

	public Integer getIdDispersion() {
		return idDispersion;
	}

	public void setIdDispersion(Integer idDispersion) {
		this.idDispersion = idDispersion;
	}

	public Integer getNoGrupos() {
		return noGrupos;
	}

	public void setNoGrupos(Integer noGrupos) {
		this.noGrupos = noGrupos;
	}

	public Integer getEstudiantesGrupo() {
		return estudiantesGrupo;
	}

	public void setEstudiantesGrupo(Integer estudiantesGrupo) {
		this.estudiantesGrupo = estudiantesGrupo;
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

}
