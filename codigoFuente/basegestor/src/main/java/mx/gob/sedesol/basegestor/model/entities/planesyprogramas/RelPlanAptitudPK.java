package mx.gob.sedesol.basegestor.model.entities.planesyprogramas;

import java.io.Serializable;


public class RelPlanAptitudPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private Integer idPlan;
	private Integer idAptitud;
	
	public RelPlanAptitudPK() {
	}
	/**
	 * @return the idPlan
	 */
	public Integer getIdPlan() {
		return idPlan;
	}
	/**
	 * @param idPlan the idPlan to set
	 */
	public void setIdPlan(Integer idPlan) {
		this.idPlan = idPlan;
	}
	/**
	 * @return the idAptitud
	 */
	public Integer getIdAptitud() {
		return idAptitud;
	}
	/**
	 * @param idAptitud the idAptitud to set
	 */
	public void setIdAptitud(Integer idAptitud) {
		this.idAptitud = idAptitud;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idAptitud == null) ? 0 : idAptitud.hashCode());
		result = prime * result + ((idPlan == null) ? 0 : idPlan.hashCode());
		return result;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RelPlanAptitudPK other = (RelPlanAptitudPK) obj;
		if (idAptitud == null) {
			if (other.idAptitud != null)
				return false;
		} else if (!idAptitud.equals(other.idAptitud))
			return false;
		if (idPlan == null) {
			if (other.idPlan != null)
				return false;
		} else if (!idPlan.equals(other.idPlan))
			return false;
		return true;
	}
	

}