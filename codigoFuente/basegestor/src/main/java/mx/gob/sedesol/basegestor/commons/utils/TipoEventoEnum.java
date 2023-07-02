package mx.gob.sedesol.basegestor.commons.utils;

public enum TipoEventoEnum {
	CAPACITACION(1, "Capacitación"),
	GENERAL(2, "General");
	
	private Integer valor;
	private String descripcion;
	
	private TipoEventoEnum(Integer valor, String descripcion){
		this.valor = valor;
		this.descripcion = descripcion;
	}
	
	public static TipoEventoEnum getTipoEventoEnum(int valor) {
		for (TipoEventoEnum elemento : TipoEventoEnum.values()) {
			if (elemento.getValor() == valor) {
				return elemento;
			}
		}
		return null;
	}
	
	public static TipoEventoEnum getTipoEventoEnum(String descripcion) {
		for (TipoEventoEnum elemento : TipoEventoEnum.values()) {
			if (elemento.getDescripcion().equalsIgnoreCase(descripcion)) {
				return elemento;
			}
		}
		return null;
	}

	public Integer getValor() {
		return valor;
	}

	public String getDescripcion() {
		return descripcion;
	}

}
