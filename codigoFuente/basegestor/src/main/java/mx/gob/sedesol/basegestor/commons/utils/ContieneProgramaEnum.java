package mx.gob.sedesol.basegestor.commons.utils;

public enum ContieneProgramaEnum {
	
	SI("SI", "Si"),
	NO("NO", "No");
	
	private String valor;
	private String descripcion;
	
	private ContieneProgramaEnum(String valor, String descripcion) {
		this.valor = valor;
		this.descripcion = descripcion;
	}
	
	public String getValor() {
		return this.valor;
	}
	
	public String getDescripcion() {
		return this.descripcion;
	}
	
	public static ContieneProgramaEnum getContieneProgramaEnum(String valor) {
		for (ContieneProgramaEnum elemento : ContieneProgramaEnum.values()) {
			if (elemento.getValor() == valor) {
				return elemento;
			}
		}
		return null;
	}

}
