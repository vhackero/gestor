package mx.gob.sedesol.basegestor.model.entities.planesyprogramas;

import java.io.Serializable;

public class RelProgramaCompEspecificaPK implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public RelProgramaCompEspecificaPK(){
		
	}
	
	private Integer idPrograma;
	private Integer idCompEspecifica;
	

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
		RelProgramaCompEspecificaPK other = (RelProgramaCompEspecificaPK) obj;
		if (idCompEspecifica == null) {
			if (other.idCompEspecifica != null)
				return false;
		} else if (!idCompEspecifica.equals(other.idCompEspecifica))
			return false;
		if (idPrograma == null) {
			if (other.idPrograma != null)
				return false;
		} else if (!idPrograma.equals(other.idPrograma))
			return false;
		return true;
	}
}
