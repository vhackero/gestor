package mx.gob.sedesol.basegestor.model.entities.gestionaprendizaje;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the rel_reponsable_produccion_oa database table.
 * 
 */
@Embeddable
public class RelReponsableProduccionOaPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	
	private Integer idResponsableProduccion;

	
	private Integer idUnidadOa;

	public RelReponsableProduccionOaPK() {
	}
	public Integer getIdResponsableProduccion() {
		return this.idResponsableProduccion;
	}
	public void setIdResponsableProduccion(Integer idResponsableProduccion) {
		this.idResponsableProduccion = idResponsableProduccion;
	}
	public Integer getIdUnidadOa() {
		return this.idUnidadOa;
	}
	public void setIdUnidadOa(Integer idUnidadOa) {
		this.idUnidadOa = idUnidadOa;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof RelReponsableProduccionOaPK)) {
			return false;
		}
		RelReponsableProduccionOaPK castOther = (RelReponsableProduccionOaPK)other;
		return 
			(this.idResponsableProduccion == castOther.idResponsableProduccion)
			&& (this.idUnidadOa == castOther.idUnidadOa);
	}

	public int hashCode() {
		final Integer prime = 31;
		Integer hash = 17;
		hash = hash * prime + this.idResponsableProduccion;
		hash = hash * prime + this.idUnidadOa;
		
		return hash;
	}
}