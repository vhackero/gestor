package mx.gob.sedesol.gestorscheduler.util;

public enum CronValuesEnum {

	
	CRON_AVA("278f81b7-980e-449a-ab5e-d49a10059d6d","Activar Avas");
	
	
	
	private String clave;
	private String nombre;
	
	private CronValuesEnum(String clave,String nombre){
		this.clave = clave;
		this.nombre = nombre;
		
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	
	
	
	
}
