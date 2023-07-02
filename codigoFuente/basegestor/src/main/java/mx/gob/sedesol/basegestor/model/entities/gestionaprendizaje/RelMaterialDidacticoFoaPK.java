package mx.gob.sedesol.basegestor.model.entities.gestionaprendizaje;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the rel_material_didactico_foa database table.
 * 
 */
@Embeddable
public class RelMaterialDidacticoFoaPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	
	private Integer idFoa;

	private Integer idCatRecursoDidactico;

	public RelMaterialDidacticoFoaPK() {
	}
	public Integer getIdFoa() {
		return this.idFoa;
	}
	public void setIdFoa(Integer idFoa) {
		this.idFoa = idFoa;
	}
	public Integer getIdCatRecursoDidactico() {
		return this.idCatRecursoDidactico;
	}
	public void setIdCatRecursoDidactico(Integer idCatRecursoDidactico) {
		this.idCatRecursoDidactico = idCatRecursoDidactico;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof RelMaterialDidacticoFoaPK)) {
			return false;
		}
		RelMaterialDidacticoFoaPK castOther = (RelMaterialDidacticoFoaPK)other;
		return 
			(this.idFoa == castOther.idFoa)
			&& (this.idCatRecursoDidactico == castOther.idCatRecursoDidactico);
	}

	public int hashCode() {
		final Integer prime = 31;
		Integer hash = 17;
		hash = hash * prime + this.idFoa;
		hash = hash * prime + this.idCatRecursoDidactico;
		
		return hash;
	}
}