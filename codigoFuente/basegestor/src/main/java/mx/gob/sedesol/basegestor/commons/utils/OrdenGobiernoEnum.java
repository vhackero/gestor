package mx.gob.sedesol.basegestor.commons.utils;

public enum OrdenGobiernoEnum {

	FEDERAL("1", "Federal"), ESTATAL("2", "Estatal"), MUNICIPAL("3", "Municipal"), OTRO("4", "Otro");

	private String valor;
	private String descripcion;

	private OrdenGobiernoEnum(String valor, String descripcion) {
		this.valor = valor;
		this.descripcion = descripcion;
	}

	public String getValor() {
		return this.valor;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public static OrdenGobiernoEnum getOrdenGobiernoEnum(String valor) {
		for (OrdenGobiernoEnum elemento : OrdenGobiernoEnum.values()) {
			if (elemento.getValor().equals(valor)) {
				return elemento;
			}
		}
		return null;
	}

}
