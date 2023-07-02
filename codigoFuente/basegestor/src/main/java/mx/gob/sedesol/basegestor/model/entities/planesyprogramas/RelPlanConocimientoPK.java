package mx.gob.sedesol.basegestor.model.entities.planesyprogramas;

import java.io.Serializable;

public class RelPlanConocimientoPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private Integer idPlan;

	private Integer idAreaConocimiento;

	public RelPlanConocimientoPK() {
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
	 * @return the idAreaConocimiento
	 */
	public Integer getIdAreaConocimiento() {
		return idAreaConocimiento;
	}

	/**
	 * @param idAreaConocimiento the idAreaConocimiento to set
	 */
	public void setIdAreaConocimiento(Integer idAreaConocimiento) {
		this.idAreaConocimiento = idAreaConocimiento;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idAreaConocimiento == null) ? 0 : idAreaConocimiento.hashCode());
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
		RelPlanConocimientoPK other = (RelPlanConocimientoPK) obj;
		if (idAreaConocimiento == null) {
			if (other.idAreaConocimiento != null)
				return false;
		} else if (!idAreaConocimiento.equals(other.idAreaConocimiento))
			return false;
		if (idPlan == null) {
			if (other.idPlan != null)
				return false;
		} else if (!idPlan.equals(other.idPlan))
			return false;
		return true;
	}

	

}