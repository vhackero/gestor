package mx.gob.sedesol.basegestor.commons.utils;

public enum EncuestaTipoEnum {

	REACCION(1,"Reacción"),
	COMPORTAMIENTO(2,"Comportamiento"),
	RESULTADOS(3,"Resultados"),
	SISTEMA(4,"Sistema");
	
	
	private Integer id;
	private String nombreTipoEncuesta;
	
	
	private EncuestaTipoEnum (Integer id,String nombreTipoEncuesta){
		this.id = id;
		this.nombreTipoEncuesta = nombreTipoEncuesta;				
	}


	public String getNombreTipoEncuesta() {
		return nombreTipoEncuesta;
	}


	public void setNombreTipoEncuesta(String nombreTipoEncuesta) {
		this.nombreTipoEncuesta = nombreTipoEncuesta;
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}
	

	
	
	
	
}
