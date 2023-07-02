package mx.gob.sedesol.basegestor.model.entities.planesyprogramas;

import java.io.Serializable;

public class RelUniDidacticaMaterialPK implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer idUnidadDidactica;
	private Integer idMaterialDidactico;
	
	public RelUniDidacticaMaterialPK(){
		
	}
	
	/**
	 * @return the idUnidadDidactica
	 */
	public Integer getIdUnidadDidactica() {
		return idUnidadDidactica;
	}
	/**
	 * @param idUnidadDidactica the idUnidadDidactica to set
	 */
	public void setIdUnidadDidactica(Integer idUnidadDidactica) {
		this.idUnidadDidactica = idUnidadDidactica;
	}
	/**
	 * @return the idMaterialDidactico
	 */
	public Integer getIdMaterialDidactico() {
		return idMaterialDidactico;
	}
	/**
	 * @param idMaterialDidactico the idMaterialDidactico to set
	 */
	public void setIdMaterialDidactico(Integer idMaterialDidactico) {
		this.idMaterialDidactico = idMaterialDidactico;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idMaterialDidactico == null) ? 0 : idMaterialDidactico.hashCode());
		result = prime * result + ((idUnidadDidactica == null) ? 0 : idUnidadDidactica.hashCode());
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
		RelUniDidacticaMaterialPK other = (RelUniDidacticaMaterialPK) obj;
		if (idMaterialDidactico == null) {
			if (other.idMaterialDidactico != null)
				return false;
		} else if (!idMaterialDidactico.equals(other.idMaterialDidactico))
			return false;
		if (idUnidadDidactica == null) {
			if (other.idUnidadDidactica != null)
				return false;
		} else if (!idUnidadDidactica.equals(other.idUnidadDidactica))
			return false;
		return true;
	}
	


}
