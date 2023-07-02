package mx.gob.sedesol.basegestor.commons.utils;

public enum ObjetoCurricularEnum {

	PLAN(2,"Plan"),
	ESTRUCTURA(3,"Estructura"),
	SUB_ESTRUCTURA(4,"SubEstructura"),
	PROGRAMA(5,"Programa"),
	TEMA(6,"Tema");
	
	private Integer id; 
	private String nombre;

	/**
	 * @param id
	 * @param nombre
	 */
	private ObjetoCurricularEnum(Integer id ,String nombre) {
		this.id = id;
		this.nombre = nombre;
	}
	
	
	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}
	
	public Integer getid() {
		return id;
	}
	
	
	public static ObjetoCurricularEnum getEnumPorNombreObjeto(String nombre){
		 for(ObjetoCurricularEnum obj : values()){
			 if(nombre.equalsIgnoreCase(obj.getNombre()))
				 return obj;
		 }
		 return null;
	}
	
	public static ObjetoCurricularEnum getEnumPorIdObjeto(Integer id){
		 for(ObjetoCurricularEnum obj : values()){
			 if(id.equals(obj.getid()))
				 return obj;
		 }
		 return null;
	}
	
	
}
