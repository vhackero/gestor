package mx.gob.sedesol.basegestor.model.entities.planesyprogramas;

import java.io.Serializable;

public class RelProgramaPersonalExternoPK implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer idPrograma;
	private Integer idEstPersonalExt;
	
	public RelProgramaPersonalExternoPK(){
		
	}
	
	/**
	 * @return the idPrograma
	 */
	public Integer getIdPrograma() {
		return idPrograma;
	}
	/**
	 * @param idPrograma the idPrograma to set
	 */
	public void setIdPrograma(Integer idPrograma) {
		this.idPrograma = idPrograma;
	}
	/**
	 * @return the idEstPersonalExt
	 */
	public Integer getIdEstPersonalExt() {
		return idEstPersonalExt;
	}
	/**
	 * @param idEstPersonalExt the idEstPersonalExt to set
	 */
	public void setIdEstPersonalExt(Integer idEstPersonalExt) {
		this.idEstPersonalExt = idEstPersonalExt;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idEstPersonalExt == null) ? 0 : idEstPersonalExt.hashCode());
		result = prime * result + ((idPrograma == null) ? 0 : idPrograma.hashCode());
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
		RelProgramaPersonalExternoPK other = (RelProgramaPersonalExternoPK) obj;
		if (idEstPersonalExt == null) {
			if (other.idEstPersonalExt != null)
				return false;
		} else if (!idEstPersonalExt.equals(other.idEstPersonalExt))
			return false;
		if (idPrograma == null) {
			if (other.idPrograma != null)
				return false;
		} else if (!idPrograma.equals(other.idPrograma))
			return false;
		return true;
	}

}
