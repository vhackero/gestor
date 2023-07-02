package mx.gob.sedesol.basegestor.commons.utils;

public enum TipoServicioEnum {
	LOCAL(1,"Local"),
	SERVICIOWEB(2,"Servicio Web");
	
	private int valor;
	private String descripcion;
	
	private TipoServicioEnum(int valor, String descripcion) {
		this.valor = valor;
		this.descripcion = descripcion;
	}
	
	public int getValor() {
		return this.valor;
	}
	
	public String getDescripcion() {
		return this.descripcion;
	}
	
	public static TipoServicioEnum getTipoServicioEnum(int valor) {
		for (TipoServicioEnum elemento : TipoServicioEnum.values()) {
			if (elemento.getValor() == valor) {
				return elemento;
			}
		}
		return TipoServicioEnum.LOCAL;
	}
	
	public static TipoServicioEnum getTipoServicioEnum(String descripcion) {
		for (TipoServicioEnum elemento : TipoServicioEnum.values()) {
			if (elemento.getDescripcion().equalsIgnoreCase(descripcion)) {
				return elemento;
			}
		}
		return TipoServicioEnum.LOCAL;
	}
}
