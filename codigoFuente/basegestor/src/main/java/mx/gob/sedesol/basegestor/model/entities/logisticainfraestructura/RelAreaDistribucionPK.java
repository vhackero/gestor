package mx.gob.sedesol.basegestor.model.entities.logisticainfraestructura;

import java.io.Serializable;

/**
 * The primary key class for the rel_area_distribucion database table.
 * 
 */
public class RelAreaDistribucionPK implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private Integer idAreaConfig;

	private Integer idDistribucion;

	public RelAreaDistribucionPK() {
	}

	public Integer getIdAreaConfig() {
		return this.idAreaConfig;
	}

	public void setIdAreaConfig(Integer idAreaConfig) {
		this.idAreaConfig = idAreaConfig;
	}

	public Integer getIdDistribucion() {
		return this.idDistribucion;
	}

	public void setIdDistribucion(Integer idDistribucion) {
		this.idDistribucion = idDistribucion;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idAreaConfig == null) ? 0 : idAreaConfig.hashCode());
		result = prime * result + ((idDistribucion == null) ? 0 : idDistribucion.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
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
		RelAreaDistribucionPK other = (RelAreaDistribucionPK) obj;
		if (idAreaConfig == null) {
			if (other.idAreaConfig != null)
				return false;
		} else if (!idAreaConfig.equals(other.idAreaConfig))
			return false;
		if (idDistribucion == null) {
			if (other.idDistribucion != null)
				return false;
		} else if (!idDistribucion.equals(other.idDistribucion))
			return false;
		return true;
	}

}