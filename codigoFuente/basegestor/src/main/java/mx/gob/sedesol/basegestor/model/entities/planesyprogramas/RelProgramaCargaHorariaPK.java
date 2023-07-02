package mx.gob.sedesol.basegestor.model.entities.planesyprogramas;

import java.io.Serializable;


public class RelProgramaCargaHorariaPK implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer idPrograma;

	private Integer idTpoCargaHoraria;

	public RelProgramaCargaHorariaPK() {
	}
	public Integer getIdPrograma() {
		return this.idPrograma;
	}
	public void setIdPrograma(Integer idPrograma) {
		this.idPrograma = idPrograma;
	}
	
	public Integer getIdTpoCargaHoraria() {
		return this.idTpoCargaHoraria;
	}
	public void setIdTpoCargaHoraria(Integer idTpoCargaHoraria) {
		this.idTpoCargaHoraria = idTpoCargaHoraria;
	}
	
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof RelProgramaCargaHorariaPK)) {
			return false;
		}
		RelProgramaCargaHorariaPK castOther = (RelProgramaCargaHorariaPK)other;
		return 
			(this.idPrograma == castOther.idPrograma)
			&& (this.idTpoCargaHoraria == castOther.idTpoCargaHoraria);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.idPrograma;
		hash = hash * prime + this.idTpoCargaHoraria;
		
		return hash;
	}
	
	
}