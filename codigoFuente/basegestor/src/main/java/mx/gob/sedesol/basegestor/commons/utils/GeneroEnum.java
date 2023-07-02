package mx.gob.sedesol.basegestor.commons.utils;

public enum GeneroEnum {
	
	MASCULINO("M", "Masculino"),
	FEMENINO("F", "Femenino");
	
	private String valor;
	private String descripcion;
	
	private GeneroEnum(String valor, String descripcion) {
		this.valor = valor;
		this.descripcion = descripcion;
	}
	
	public String getValor() {
		return this.valor;
	}
	
	public String getDescripcion() {
		return this.descripcion;
	}
	
	public static GeneroEnum getGeneroEnum(String valor) {
		for (GeneroEnum elemento : GeneroEnum.values()) {
			if (elemento.getValor() == valor) {
				return elemento;
			}
		}
		return null;
	}

}
