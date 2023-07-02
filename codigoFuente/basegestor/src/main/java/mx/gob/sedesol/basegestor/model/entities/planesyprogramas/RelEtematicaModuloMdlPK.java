package mx.gob.sedesol.basegestor.model.entities.planesyprogramas;

import java.io.Serializable;

/**
 * The primary key class for the rel_etematica_modulo_mdl database table.
 * 
 */
public class RelEtematicaModuloMdlPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private Integer idEstructuraTematica;

	private Integer idModuloMdl;

	public RelEtematicaModuloMdlPK() {
	}
	public Integer getIdEstructuraTematica() {
		return this.idEstructuraTematica;
	}
	public void setIdEstructuraTematica(Integer idEstructuraTematica) {
		this.idEstructuraTematica = idEstructuraTematica;
	}
	public Integer getIdModuloMdl() {
		return this.idModuloMdl;
	}
	public void setIdModuloMdl(Integer idModuloMdl) {
		this.idModuloMdl = idModuloMdl;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idEstructuraTematica == null) ? 0 : idEstructuraTematica.hashCode());
		result = prime * result + ((idModuloMdl == null) ? 0 : idModuloMdl.hashCode());
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
		RelEtematicaModuloMdlPK other = (RelEtematicaModuloMdlPK) obj;
		if (idEstructuraTematica == null) {
			if (other.idEstructuraTematica != null)
				return false;
		} else if (!idEstructuraTematica.equals(other.idEstructuraTematica))
			return false;
		if (idModuloMdl == null) {
			if (other.idModuloMdl != null)
				return false;
		} else if (!idModuloMdl.equals(other.idModuloMdl))
			return false;
		return true;
	}

}