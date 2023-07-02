package mx.gob.sedesol.basegestor.commons.utils;

public enum CicloEjecucionEnum {

		MENSUAL(1,"Mensual"," La ejecucion de la tarea se realizara de forma mensual."),
		SEMANAL(2,"Semanal"," La ejecucion de la tarea se realizara de forma semanal."),
		DIARIO (3,"Diario", " La ejecucion de la tarea se realizara de forma diaria.");
	
	
	private Integer id;
	private String  nombre;
	private String  descripcion;
	
	private CicloEjecucionEnum(Integer id,String nombre, String descripcion){
		
		this.id = id;
		this.nombre = nombre;
		this.descripcion = descripcion;
		
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

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public static CicloEjecucionEnum getCicloEjecucionEnum(String valor) {
		for (CicloEjecucionEnum elemento : CicloEjecucionEnum.values()) {
			if (elemento.getId().equals(elemento.getId())) {
				return elemento;
			}
		}
		return null;
	}
	
	
}
