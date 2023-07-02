package mx.gob.sedesol.basegestor.commons.utils;

public enum TipoTemaEnum {
	
	PUBLICO(1,"P\u00FAblico"),
	PRIVADO(2,"Privado");
	
	private int valor;
	private String descripcion;
	
	private TipoTemaEnum(int valor, String descripcion) {
		this.valor = valor;
		this.descripcion = descripcion;
	}
	
	public int getValor() {
		return this.valor;
	}
	
	public String getDescripcion() {
		return this.descripcion;
	}
	
	public static TipoTemaEnum getTipoTemaEnum(int valor) {
		for (TipoTemaEnum elemento : TipoTemaEnum.values()) {
			if (elemento.getValor() == valor) {
				return elemento;
			}
		}
		return null;
	}

}
