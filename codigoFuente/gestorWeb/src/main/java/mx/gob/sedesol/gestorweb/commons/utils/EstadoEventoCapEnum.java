package mx.gob.sedesol.gestorweb.commons.utils;

public enum EstadoEventoCapEnum {

	CALENDARIZADO(1,"Calendarizado"),
	EN_EJECUCION (2,"En ejecución"),
	CONCLUIDOS   (3,"Concluidos"),
	BLOQUEADO    (4,"Bloqueado"),
	CANCELADO    (5,"Cancelado"),
	BORRADOR    (6,"Borrador");
	
	
	private Integer id;
	private String etiqueta;
	
	
	
	
	
	private EstadoEventoCapEnum(Integer id, String etiqueta) {
		this.id = id;
		this.etiqueta = etiqueta;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getEtiqueta() {
		return etiqueta;
	}
	public void setEtiqueta(String etiqueta) {
		this.etiqueta = etiqueta;
	}
	
	
	
	
	
	
}
