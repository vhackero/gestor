package mx.gob.sedesol.basegestor.commons.utils;



public enum SchedulerCronPropiedadesEnum {

	IP("cron.scheduler.ip"),
	PUERTO("cron.scheduler.puerto"),
	CONTEXTO_WEB("cron.scheduler.web.contexto"),
	CONTEXTO_WS("cron.scheduler.web.ws.contexto"),
	METODO_ACTUALIZA_PATRON_EJECUCION("cron.scheduler.web.ws.metodo.actualizaPatronEjecucion");
	
	
	String propiedad;
		
	
	
	private SchedulerCronPropiedadesEnum(String propiedad){
		this.propiedad = propiedad;	
		
	}


	public String getPropiedad() {
		return propiedad;
	}


	public void setPropiedad(String propiedad) {
		this.propiedad = propiedad;
	}

	
}
