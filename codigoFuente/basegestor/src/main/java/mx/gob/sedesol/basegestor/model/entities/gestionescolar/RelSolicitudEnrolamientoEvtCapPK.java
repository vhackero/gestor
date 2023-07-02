package mx.gob.sedesol.basegestor.model.entities.gestionescolar;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the rel_solicitud_enrolamiento_evt_cap database table.
 * 
 */
@Embeddable
public class RelSolicitudEnrolamientoEvtCapPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private Long idPersona;

	private Integer idEventoCapacitacion;

	public RelSolicitudEnrolamientoEvtCapPK() {
	}
	public Long getIdPersona() {
		return this.idPersona;
	}
	public void setIdPersona(Long idPersona) {
		this.idPersona = idPersona;
	}
	public Integer getIdEventoCapacitacion() {
		return this.idEventoCapacitacion;
	}
	public void setIdEventoCapacitacion(Integer idEventoCapacitacion) {
		this.idEventoCapacitacion = idEventoCapacitacion;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof RelSolicitudEnrolamientoEvtCapPK)) {
			return false;
		}
		RelSolicitudEnrolamientoEvtCapPK castOther = (RelSolicitudEnrolamientoEvtCapPK)other;
		return 
			this.idPersona.equals(castOther.idPersona)
			&& (this.idEventoCapacitacion == castOther.idEventoCapacitacion);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.idPersona.hashCode();
		hash = hash * prime + this.idEventoCapacitacion;
		
		return hash;
	}
}