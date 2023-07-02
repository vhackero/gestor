package mx.gob.sedesol.basegestor.model.entities.planesyprogramas;

import java.io.Serializable;

public class RelUnidDidacticaTposCompPK implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer idUnidadDidactica;
	private Integer idCompEspecifica;
	
	public RelUnidDidacticaTposCompPK(){
		
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
	 * @return the idCompEspecifica
	 */
	public Integer getIdCompEspecifica() {
		return idCompEspecifica;
	}

	/**
	 * @param idCompEspecifica the idCompEspecifica to set
	 */
	public void setIdCompEspecifica(Integer idCompEspecifica) {
		this.idCompEspecifica = idCompEspecifica;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idCompEspecifica == null) ? 0 : idCompEspecifica.hashCode());
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
		RelUnidDidacticaTposCompPK other = (RelUnidDidacticaTposCompPK) obj;
		if (idCompEspecifica == null) {
			if (other.idCompEspecifica != null)
				return false;
		} else if (!idCompEspecifica.equals(other.idCompEspecifica))
			return false;
		if (idUnidadDidactica == null) {
			if (other.idUnidadDidactica != null)
				return false;
		} else if (!idUnidadDidactica.equals(other.idUnidadDidactica))
			return false;
		return true;
	}
	
}
