package mx.gob.sedesol.basegestor.commons.utils;

public enum TipoBusquedaUsuariosEnum {
	
	INDIVUDUAL(1,"Individual");
//	MASIVA(2,"Por lote de carga masiva");
	
	private int valor;
	private String descripcion;
	
	private TipoBusquedaUsuariosEnum(int valor, String descripcion) {
		this.valor = valor;
		this.descripcion = descripcion;
	}
	
	public int getValor() {
		return this.valor;
	}
	
	public String getDescripcion() {
		return this.descripcion;
	}
	
	public static TipoBusquedaUsuariosEnum getTipoBusquedaUsuariosEnum(int valor) {
		for (TipoBusquedaUsuariosEnum elemento : TipoBusquedaUsuariosEnum.values()) {
			if (elemento.getValor() == valor) {
				return elemento;
			}
		}
		return TipoBusquedaUsuariosEnum.INDIVUDUAL;
	}
	
	public static TipoBusquedaUsuariosEnum getTipoBusquedaUsuariosEnum(String descripcion) {
		for (TipoBusquedaUsuariosEnum elemento : TipoBusquedaUsuariosEnum.values()) {
			if (elemento.getDescripcion().equalsIgnoreCase(descripcion)) {
				return elemento;
			}
		}
		return TipoBusquedaUsuariosEnum.INDIVUDUAL;
	}

}
