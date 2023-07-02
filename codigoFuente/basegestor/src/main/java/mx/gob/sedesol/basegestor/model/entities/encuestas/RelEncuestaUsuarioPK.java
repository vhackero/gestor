package mx.gob.sedesol.basegestor.model.entities.encuestas;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the rel_encuesta_usuario database table.
 * 
 */
@Embeddable
public class RelEncuestaUsuarioPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final Long serialVersionUID = 1L;
	
	private Integer idEncuesta;

	private Integer idGrupoParticipante;

	public RelEncuestaUsuarioPK() {
	}
	public int getIdEncuesta() {
		return this.idEncuesta;
	}
	public void setIdEncuesta(int idEncuesta) {
		this.idEncuesta = idEncuesta;
	}
	public Integer getIdGrupoParticipante() {
		return this.idGrupoParticipante;
	}
	public void setIdGrupoParticipante(Integer IdGrupoParticipante) {
		this.idGrupoParticipante = IdGrupoParticipante;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof RelEncuestaUsuarioPK)) {
			return false;
		}
		RelEncuestaUsuarioPK castOther = (RelEncuestaUsuarioPK)other;
		return 
			(this.idEncuesta == castOther.idEncuesta)
			&& this.idGrupoParticipante.equals(castOther.idGrupoParticipante);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.idEncuesta;
		hash = hash * prime + this.idGrupoParticipante.hashCode();
		
		return hash;
	}
}