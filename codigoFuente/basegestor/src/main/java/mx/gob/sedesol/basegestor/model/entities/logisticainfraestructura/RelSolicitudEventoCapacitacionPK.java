package mx.gob.sedesol.basegestor.model.entities.logisticainfraestructura;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the rel_solicitud_evento_capacitacion database table.
 * 
 */
@Embeddable
public class RelSolicitudEventoCapacitacionPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private Integer idSolicitud;

	private Integer idReservacionEC;

	public RelSolicitudEventoCapacitacionPK() {
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof RelSolicitudEventoCapacitacionPK)) {
			return false;
		}
		RelSolicitudEventoCapacitacionPK castOther = (RelSolicitudEventoCapacitacionPK)other;
		return 
			(this.getIdSolicitud() == castOther.getIdSolicitud())
			&& (this.getIdReservacionEC() == castOther.getIdReservacionEC());
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.getIdSolicitud();
		hash = hash * prime + this.getIdReservacionEC();
		
		return hash;
	}

	/**
	 * @return the idSolicitud
	 */
	public Integer getIdSolicitud() {
		return idSolicitud;
	}

	/**
	 * @param idSolicitud the idSolicitud to set
	 */
	public void setIdSolicitud(Integer idSolicitud) {
		this.idSolicitud = idSolicitud;
	}

	/**
	 * @return the idReservacionEC
	 */
	public Integer getIdReservacionEC() {
		return idReservacionEC;
	}

	/**
	 * @param idReservacionEC the idReservacionEC to set
	 */
	public void setIdReservacionEC(Integer idReservacionEC) {
		this.idReservacionEC = idReservacionEC;
	}
}