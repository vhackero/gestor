package mx.gob.sedesol.basegestor.commons.dto.planesyprogramas;

import java.io.Serializable;


public class RelProgDuracionPKDTO implements Serializable { 

	
	private static final long serialVersionUID = 1L;

	private int idPrograma;

	private String horas;

	private String minutos;

	public RelProgDuracionPKDTO() {
	}
	
	
	public int getIdPrograma() {
		return this.idPrograma;
	}
	public void setIdPrograma(int idPrograma) {
		this.idPrograma = idPrograma;
	}


	public String getHoras() {
		return horas;
	}


	public void setHoras(String horas) {
		this.horas = horas;
	}


	public String getMinutos() {
		return minutos;
	}


	public void setMinutos(String minutos) {
		this.minutos = minutos;
	}
	

	
	
}
