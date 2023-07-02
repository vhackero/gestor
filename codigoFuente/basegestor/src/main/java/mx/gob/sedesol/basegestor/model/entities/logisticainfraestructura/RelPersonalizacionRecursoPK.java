package mx.gob.sedesol.basegestor.model.entities.logisticainfraestructura;

import java.io.Serializable;

/**
 * The primary key class for the rel_personalizacion_recursos database table.
 * 
 */
public class RelPersonalizacionRecursoPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private Integer idPersonalizacionArea;

	private Integer idRecurso;

	public RelPersonalizacionRecursoPK() {
	}
	public Integer getIdPersonalizacionArea() {
		return this.idPersonalizacionArea;
	}
	public void setIdPersonalizacionArea(Integer idPersonalizacionArea) {
		this.idPersonalizacionArea = idPersonalizacionArea;
	}
	public Integer getIdRecurso() {
		return this.idRecurso;
	}
	public void setIdRecurso(Integer idRecurso) {
		this.idRecurso = idRecurso;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof RelPersonalizacionRecursoPK)) {
			return false;
		}
		RelPersonalizacionRecursoPK castOther = (RelPersonalizacionRecursoPK)other;
		return 
			(this.idPersonalizacionArea == castOther.idPersonalizacionArea)
			&& (this.idRecurso == castOther.idRecurso);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.idPersonalizacionArea;
		hash = hash * prime + this.idRecurso;
		
		return hash;
	}
}