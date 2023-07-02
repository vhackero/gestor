package mx.gob.sedesol.basegestor.model.entities.planesyprogramas;

import java.io.Serializable;

public class RelEstructuraUnidadDidacticaPK  implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer idDetTema;
	private Integer idUnidadDidactica;
	
	public RelEstructuraUnidadDidacticaPK(){
		
	}
	/**
	 * @return the idDetTema
	 */
	public Integer getIdDetTema() {
		return idDetTema;
	}
	/**
	 * @param idDetTema the idDetTema to set
	 */
	public void setIdDetTema(Integer idDetTema) {
		this.idDetTema = idDetTema;
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
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idDetTema == null) ? 0 : idDetTema.hashCode());
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
		RelEstructuraUnidadDidacticaPK other = (RelEstructuraUnidadDidacticaPK) obj;
		if (idDetTema == null) {
			if (other.idDetTema != null)
				return false;
		} else if (!idDetTema.equals(other.idDetTema))
			return false;
		if (idUnidadDidactica == null) {
			if (other.idUnidadDidactica != null)
				return false;
		} else if (!idUnidadDidactica.equals(other.idUnidadDidactica))
			return false;
		return true;
	}

}
