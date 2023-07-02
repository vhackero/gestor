package mx.gob.sedesol.basegestor.model.entities.gestionaprendizaje;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the rel_cat_relacion_otros_objetos_foa database table.
 * 
 */
@Embeddable
public class RelCatRelacionOtrosObjetosFoaPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	
	private Integer idFoa;

	private Integer idCatRelacionOtrosObjetos;

	public RelCatRelacionOtrosObjetosFoaPK() {
	}
	public Integer getIdFoa() {
		return this.idFoa;
	}
	public void setIdFoa(Integer idFoa) {
		this.idFoa = idFoa;
	}
	public Integer getIdCatRelacionOtrosObjetos() {
		return this.idCatRelacionOtrosObjetos;
	}
	public void setIdCatRelacionOtrosObjetos(Integer idCatRelacionOtrosObjetos) {
		this.idCatRelacionOtrosObjetos = idCatRelacionOtrosObjetos;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof RelCatRelacionOtrosObjetosFoaPK)) {
			return false;
		}
		RelCatRelacionOtrosObjetosFoaPK castOther = (RelCatRelacionOtrosObjetosFoaPK)other;
		return 
			(this.idFoa == castOther.idFoa)
			&& (this.idCatRelacionOtrosObjetos == castOther.idCatRelacionOtrosObjetos);
	}

	public int hashCode() {
		final Integer prime = 31;
		Integer hash = 17;
		hash = hash * prime + this.idFoa;
		hash = hash * prime + this.idCatRelacionOtrosObjetos;
		
		return hash;
	}
}