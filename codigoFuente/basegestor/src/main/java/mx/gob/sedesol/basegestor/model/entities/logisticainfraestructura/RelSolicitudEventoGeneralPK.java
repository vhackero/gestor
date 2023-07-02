package mx.gob.sedesol.basegestor.model.entities.logisticainfraestructura;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the rel_solicitud_evento_general database table.
 * 
 */
@Embeddable
public class RelSolicitudEventoGeneralPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private Integer idReservacionEG;

	private Integer idSolicitud;

	public RelSolicitudEventoGeneralPK() {
	}
	public Integer getIdSolicitud() {
		return this.idSolicitud;
	}
	public void setIdSolicitud(Integer idSolicitud) {
		this.idSolicitud = idSolicitud;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof RelSolicitudEventoGeneralPK)) {
			return false;
		}
		RelSolicitudEventoGeneralPK castOther = (RelSolicitudEventoGeneralPK)other;
		return 
			(this.getIdReservacionEG() == castOther.getIdReservacionEG())
			&& (this.idSolicitud == castOther.idSolicitud);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.getIdReservacionEG();
		hash = hash * prime + this.idSolicitud;
		
		return hash;
	}
	/**
	 * @return the idReservacionEG
	 */
	public Integer getIdReservacionEG() {
		return idReservacionEG;
	}
	/**
	 * @param idReservacionEG the idReservacionEG to set
	 */
	public void setIdReservacionEG(Integer idReservacionEG) {
		this.idReservacionEG = idReservacionEG;
	}
}