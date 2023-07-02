package mx.gob.sedesol.basegestor.model.entities.encuestas;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the rel_encuesta_evento_capacitacion database table.
 * 
 */
@Embeddable
public class RelEncuestaEventoCapacitacionPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="id_evento_capacitacion", insertable=false, updatable=false)
	private int idEventoCapacitacion;

	@Column(name="id_encuesta", insertable=false, updatable=false)
	private int idEncuesta;

	public RelEncuestaEventoCapacitacionPK() {
	}
	public int getIdEventoCapacitacion() {
		return this.idEventoCapacitacion;
	}
	public void setIdEventoCapacitacion(int idEventoCapacitacion) {
		this.idEventoCapacitacion = idEventoCapacitacion;
	}
	public int getIdEncuesta() {
		return this.idEncuesta;
	}
	public void setIdEncuesta(int idEncuesta) {
		this.idEncuesta = idEncuesta;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof RelEncuestaEventoCapacitacionPK)) {
			return false;
		}
		RelEncuestaEventoCapacitacionPK castOther = (RelEncuestaEventoCapacitacionPK)other;
		return 
			(this.idEventoCapacitacion == castOther.idEventoCapacitacion)
			&& (this.idEncuesta == castOther.idEncuesta);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.idEventoCapacitacion;
		hash = hash * prime + this.idEncuesta;
		
		return hash;
	}
}