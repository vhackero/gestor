package mx.gob.sedesol.basegestor.commons.utils;

public enum TipoUsuarioEnum {
	
	INTERNO(1,"Interno"),
	EXTERNO(2,"Externo");
	
	private int valor;
	private String descripcion;
	
	private TipoUsuarioEnum(int valor, String descripcion) {
		this.valor = valor;
		this.descripcion = descripcion;
	}
	
	public int getValor() {
		return this.valor;
	}
	
	public String getDescripcion() {
		return this.descripcion;
	}
	
	public static TipoUsuarioEnum getTipoUsuarioEnum(int valor) {
		for (TipoUsuarioEnum elemento : TipoUsuarioEnum.values()) {
			if (elemento.getValor() == valor) {
				return elemento;
			}
		}
		return TipoUsuarioEnum.INTERNO;
	}
	
	public static TipoUsuarioEnum getTipoUsuarioEnum(String descripcion) {
		for (TipoUsuarioEnum elemento : TipoUsuarioEnum.values()) {
			if (elemento.getDescripcion().equalsIgnoreCase(descripcion)) {
				return elemento;
			}
		}
		return TipoUsuarioEnum.INTERNO;
	}

}
