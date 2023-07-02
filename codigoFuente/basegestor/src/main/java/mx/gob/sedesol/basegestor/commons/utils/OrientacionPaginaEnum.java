package mx.gob.sedesol.basegestor.commons.utils;

public enum OrientacionPaginaEnum {
	
	VERTICAL("Vertical"),
	HORIZONTAL("Horizontal");
	
	private String descripcion;
	
	private OrientacionPaginaEnum(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public String getDescripcion() {
		return this.descripcion;
	}
	
	public static OrientacionPaginaEnum getOrientacionPaginaEnum(String descripcion) {
		for (OrientacionPaginaEnum elemento : OrientacionPaginaEnum.values()) {
			if (elemento.getDescripcion() == descripcion) {
				return elemento;
			}
		}
		return null;
	}

}
