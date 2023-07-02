package mx.gob.sedesol.basegestor.commons.utils;

public enum DiasDeLaSemanaEnum {

	
	DOMINGO  (1,"Domingo"),
	LUNES    (2,"Lunes"),
	MARTES   (3,"Martes"),
	MIERCOLES(4,"Miercoles"),
	JUEVES   (5,"Jueves"),
	VIERNES  (6,"Viernes"),
	SABADO   (7,"Sabado");
	
	
	
	private Integer id;
	private String nombre;
		
	private DiasDeLaSemanaEnum(Integer id,String nombre){
		this.id = id;
		this.nombre = nombre;					
	}

	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	
	
	public static DiasDeLaSemanaEnum getDiasDeLaSemanaEnum(Integer idDiaDeLaSemana) {
		for (DiasDeLaSemanaEnum elemento : DiasDeLaSemanaEnum.values()) {
			if (elemento.getId().equals(idDiaDeLaSemana)) {
				return elemento;
			}
		}
		return null;
	}
	
	
	
	
	
	
}
