package mx.gob.sedesol.basegestor.commons.utils;

public enum TipoDocumentoEnum {
	
	CONSTANCIA_ACREDITACION(1, "Constancia de acreditaci\u00F3n", OrientacionPaginaEnum.HORIZONTAL),
	CONSTANCIA_PARTICIPACION(2, "Constancia de participaci\u00F3n", OrientacionPaginaEnum.HORIZONTAL);
	
	private int valor;
	private String descripcion;
	private OrientacionPaginaEnum orientacion;
	
	private TipoDocumentoEnum(int valor, String descripcion, OrientacionPaginaEnum orientacion) {
		this.valor = valor;
		this.descripcion = descripcion;
		this.orientacion = orientacion;
	}
	
	public int getValor() {
		return this.valor;
	}
	
	public String getDescripcion() {
		return this.descripcion;
	}
	
	public OrientacionPaginaEnum getOrientacion() {
		return orientacion;
	}

	public static TipoDocumentoEnum getTipoDocumentoEnum(int valor) {
		for (TipoDocumentoEnum elemento : TipoDocumentoEnum.values()) {
			if (elemento.getValor() == valor) {
				return elemento;
			}
		}
		return null;
	}
	
	public static TipoDocumentoEnum getTipoDocumentoEnum(String descripcion) {
		for (TipoDocumentoEnum elemento : TipoDocumentoEnum.values()) {
			if (elemento.getDescripcion().equalsIgnoreCase(descripcion)) {
				return elemento;
			}
		}
		return null;
	}
	

}
