package mx.gob.sedesol.basegestor.commons.utils;

public enum ClasificacionArchivoOaEnum
{
	GUION_INSTRUCCIONAL (1,"Guión instruccional"),
	DESARROLLO_OA (2,"Desarrollo OA"),
	SCORM (3,"SCORM");
	
	private Integer id;
	private String nombre;

	private ClasificacionArchivoOaEnum(Integer id,String nombre)
	{
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
	
}
