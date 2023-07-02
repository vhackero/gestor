package mx.gob.sedesol.basegestor.model.entities.gestionaprendizaje;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the rel_foa_actividad_reforzamiento database table.
 * 
 */
@Embeddable
public class RelFoaActividadReforzamientoPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;


	private Integer idActividadReforzamiento;

	private Integer idFoa;

	public RelFoaActividadReforzamientoPK() {
	}
	public Integer getIdActividadReforzamiento() {
		return this.idActividadReforzamiento;
	}
	public void setIdActividadReforzamiento(Integer idActividadReforzamiento) {
		this.idActividadReforzamiento = idActividadReforzamiento;
	}
	public Integer getIdFoa() {
		return this.idFoa;
	}
	public void setIdFoa(Integer idFoa) {
		this.idFoa = idFoa;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof RelFoaActividadReforzamientoPK)) {
			return false;
		}
		RelFoaActividadReforzamientoPK castOther = (RelFoaActividadReforzamientoPK)other;
		return 
			(this.idActividadReforzamiento == castOther.idActividadReforzamiento)
			&& (this.idFoa == castOther.idFoa);
	}

	public int hashCode() {
		final int prime = 31;
		Integer hash = 17;
		hash = hash * prime + this.idActividadReforzamiento;
		hash = hash * prime + this.idFoa;
		
		return hash;
	}
}