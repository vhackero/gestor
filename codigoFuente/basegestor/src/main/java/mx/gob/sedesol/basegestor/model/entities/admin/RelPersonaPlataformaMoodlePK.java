package mx.gob.sedesol.basegestor.model.entities.admin;

import java.io.Serializable;

public class RelPersonaPlataformaMoodlePK implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long idPersona;
	private Integer idPlataformaMoodle;

	public Long getIdPersona() {
		return this.idPersona;
	}
	public void setIdPersona(Long idPersona) {
		this.idPersona = idPersona;
	}
	public Integer getIdPlataformaMoodle() {
		return this.idPlataformaMoodle;
	}
	public void setIdPlataformaMoodle(Integer idPlataformaMoodle) {
		this.idPlataformaMoodle = idPlataformaMoodle;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof RelPersonaPlataformaMoodlePK)) {
			return false;
		}
		RelPersonaPlataformaMoodlePK castOther = (RelPersonaPlataformaMoodlePK)other;
		return 
			this.idPersona.equals(castOther.idPersona)
			&& (this.idPlataformaMoodle == castOther.idPlataformaMoodle);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.idPersona.hashCode();
		hash = hash * prime + this.idPlataformaMoodle;
		
		return hash;
	}
}