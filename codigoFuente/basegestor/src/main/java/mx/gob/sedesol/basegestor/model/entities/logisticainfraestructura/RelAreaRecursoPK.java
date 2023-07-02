package mx.gob.sedesol.basegestor.model.entities.logisticainfraestructura;

import java.io.Serializable;

/**
 * The primary key class for the rel_area_recursos database table.
 * 
 */
public class RelAreaRecursoPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private Integer idAreaConfig;

	private Integer idRecurso;

	public RelAreaRecursoPK() {
	}
	public Integer getIdAreaConfig() {
		return this.idAreaConfig;
	}
	public void setIdAreaConfig(Integer idAreaConfig) {
		this.idAreaConfig = idAreaConfig;
	}
	public Integer getIdRecurso() {
		return this.idRecurso;
	}
	public void setIdRecurso(Integer idRecurso) {
		this.idRecurso = idRecurso;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idAreaConfig == null) ? 0 : idAreaConfig.hashCode());
		result = prime * result + ((idRecurso == null) ? 0 : idRecurso.hashCode());
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
		RelAreaRecursoPK other = (RelAreaRecursoPK) obj;
		if (idAreaConfig == null) {
			if (other.idAreaConfig != null)
				return false;
		} else if (!idAreaConfig.equals(other.idAreaConfig))
			return false;
		if (idRecurso == null) {
			if (other.idRecurso != null)
				return false;
		} else if (!idRecurso.equals(other.idRecurso))
			return false;
		return true;
	}

}