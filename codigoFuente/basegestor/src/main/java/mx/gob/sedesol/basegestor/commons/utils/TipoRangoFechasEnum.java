package mx.gob.sedesol.basegestor.commons.utils;

public enum TipoRangoFechasEnum {

	INICIAN_Y_TERMINAN_ENTRE(1,"Inician y terminan entre"),
	INICIAN_ENTRE(2,"Inician entre"),
	TERMINANA_ENTRE(3,"Terminan entre");
	
	private int valor;
	private String descripcion;
	
	private TipoRangoFechasEnum(int valor, String descripcion) {
		this.valor = valor;
		this.descripcion = descripcion;
	}
	
	public int getValor() {
		return this.valor;
	}
	
	public String getDescripcion() {
		return this.descripcion;
	}
	
	public static TipoRangoFechasEnum getTipoRangoFechasEnum(int valor) {
		for (TipoRangoFechasEnum elemento : TipoRangoFechasEnum.values()) {
			if (elemento.getValor() == valor) {
				return elemento;
			}
		}
		return null;
	}
	
	public static TipoRangoFechasEnum getTipoRangoFechasEnum(String descripcion) {
		for (TipoRangoFechasEnum elemento : TipoRangoFechasEnum.values()) {
			if (elemento.getDescripcion().equalsIgnoreCase(descripcion)) {
				return elemento;
			}
		}
		return null;
	}

	
	
	
}
