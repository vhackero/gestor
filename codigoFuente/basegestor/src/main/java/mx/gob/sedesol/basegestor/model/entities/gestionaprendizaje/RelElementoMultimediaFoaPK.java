package mx.gob.sedesol.basegestor.model.entities.gestionaprendizaje;

import java.io.Serializable;


public class RelElementoMultimediaFoaPK implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer idFoa;
	private Integer idElementoMultimediaFoa;

	public RelElementoMultimediaFoaPK() {
	}
	
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof RelElementoMultimediaFoaPK)) {
			return false;
		}
		RelElementoMultimediaFoaPK castOther = (RelElementoMultimediaFoaPK)other;
		return 
			(this.getIdFoa() == castOther.idFoa)
			&& (this.getIdElementoMultimediaFoa() == castOther.getIdElementoMultimediaFoa());
	}
	
	
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.getIdFoa();
		hash = hash * prime + this.getIdElementoMultimediaFoa();
		
		return hash;
	}

	/**
	 * @return the idFoa
	 */
	public Integer getIdFoa() {
		return idFoa;
	}

	/**
	 * @param idFoa the idFoa to set
	 */
	public void setIdFoa(Integer idFoa) {
		this.idFoa = idFoa;
	}

	/**
	 * @return the idElementoMultimediaFoa
	 */
	public Integer getIdElementoMultimediaFoa() {
		return idElementoMultimediaFoa;
	}

	/**
	 * @param idElementoMultimediaFoa the idElementoMultimediaFoa to set
	 */
	public void setIdElementoMultimediaFoa(Integer idElementoMultimediaFoa) {
		this.idElementoMultimediaFoa = idElementoMultimediaFoa;
	}
}
