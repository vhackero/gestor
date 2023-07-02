package mx.gob.sedesol.gestorweb.commons.utils;


public enum ECTipoCalificacionEnum {

	PROMEDIO(1,"Por Promedio"),
	SUMA(2,"Suma"),
	CUANTITATIVA(3,"Cuantitativa");
	
	ECTipoCalificacionEnum(Integer id, String nombre){
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
	public static ECTipoCalificacionEnum getTipoCalificacionById(Integer id){
		for(ECTipoCalificacionEnum enm: values()){
			if(enm.getId().equals(id))
				return enm;
		}
		
		return null;
	}
}
