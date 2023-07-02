package mx.gob.sedesol.basegestor.model.entities.gestionaprendizaje;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the rel_recurso_predominante_foa database table.
 * 
 */
@Embeddable
public class RelRecursoPredominanteFoaPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	
	private Integer idFoa;

	private Integer idCatRecursoPredominante;

	public RelRecursoPredominanteFoaPK() {
	}
	public Integer getIdFoa() {
		return this.idFoa;
	}
	public void setIdFoa(Integer idFoa) {
		this.idFoa = idFoa;
	}
	public Integer getIdCatRecursoPredominante() {
		return this.idCatRecursoPredominante;
	}
	public void setIdCatRecursoPredominante(Integer idCatRecursoPredominante) {
		this.idCatRecursoPredominante = idCatRecursoPredominante;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof RelRecursoPredominanteFoaPK)) {
			return false;
		}
		RelRecursoPredominanteFoaPK castOther = (RelRecursoPredominanteFoaPK)other;
		return 
			(this.idFoa == castOther.idFoa)
			&& (this.idCatRecursoPredominante == castOther.idCatRecursoPredominante);
	}

	public int hashCode() {
		final Integer prime = 31;
		Integer hash = 17;
		hash = hash * prime + this.idFoa;
		hash = hash * prime + this.idCatRecursoPredominante;
		
		return hash;
	}
}