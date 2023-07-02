package mx.gob.sedesol.basegestor.commons.utils;

public enum TipoCalificacionECEnum {

	TPO_CAL_PROMEDIO(1,"Por Promedio"),
	TPO_CAL_SUMA(2,"Suma");
	
	TipoCalificacionECEnum(Integer id, String nombre){
		this.id = id;
		this.nombre = nombre;
	}
	
	private Integer id;
	private String nombre;
	/**
	 * @return the id
	 */
	public Integer getId() {
		return this.id;
	}
	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return this.nombre;
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public static TipoCalificacionECEnum getTipoCalificacionById(Integer id){
		for(TipoCalificacionECEnum enm: values()){
			if(enm.getId().equals(id))
				return enm;
		}
		
		return null;
	}
}
