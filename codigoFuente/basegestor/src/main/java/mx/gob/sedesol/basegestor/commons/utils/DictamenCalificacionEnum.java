package mx.gob.sedesol.basegestor.commons.utils;

public enum DictamenCalificacionEnum {

	ACREDITADO(1,"Acreditado"),
	NO_ACREDITADO(2,"No Acreditado"),
	EN_PROCESO(3,"En proceso"),
	BAJA(4,"Baja");
	
	
	private Integer id;
	private String nombre;
	
	DictamenCalificacionEnum(Integer id, String nombre){
		this.id = id;
		this.nombre = nombre;
	}
	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}


	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}
}
