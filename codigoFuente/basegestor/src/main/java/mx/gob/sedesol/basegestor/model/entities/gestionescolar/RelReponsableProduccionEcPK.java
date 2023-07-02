package mx.gob.sedesol.basegestor.model.entities.gestionescolar;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the rel_reponsable_produccion_ec database table.
 * 
 */
@Embeddable
public class RelReponsableProduccionEcPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private Integer idEvtCapacitacion;

	
	private Integer idReponsableProduccion;

	public RelReponsableProduccionEcPK() {
	}
	
	

	public Integer getIdEvtCapacitacion() {
		return idEvtCapacitacion;
	}



	public void setIdEvtCapacitacion(Integer idEvtCapacitacion) {
		this.idEvtCapacitacion = idEvtCapacitacion;
	}



	public Integer getIdReponsableProduccion() {
		return idReponsableProduccion;
	}
	public void setIdReponsableProduccion(Integer idReponsableProduccion) {
		this.idReponsableProduccion = idReponsableProduccion;
	}
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof RelReponsableProduccionEcPK)) {
			return false;
		}
		RelReponsableProduccionEcPK castOther = (RelReponsableProduccionEcPK)other;
		return 
			(this.idEvtCapacitacion == castOther.idEvtCapacitacion)
			&& (this.idReponsableProduccion == castOther.idReponsableProduccion);
	}

	public int hashCode() {
		final Integer prime = 31;
		Integer hash = 17;
		hash = hash * prime + this.idEvtCapacitacion;
		hash = hash * prime + this.idReponsableProduccion;
		
		return hash;
	}
}