package mx.gob.sedesol.basegestor.commons.utils;

public enum TipoAsistenciaEnum {

	ASISTENCIA("A","ASISTENCIA","valorAsistencia"),
	RETARDO("R","RETARDO","valorRetardo"),
	FALTA_JUSTIFICADA("FJ","FALTA JUSTIFICADA","valorFaltaJustificada"),
	FALTA("F","FALTA","valorFalta"),
	INCONCLUSO("I","INCONCLUSO","valorInconcluso");
	
	
	private String valor;
	private String descripcion;
	private String nombreColumna;
	
	private TipoAsistenciaEnum(String valor, String descripcion, String nombreColumna) {
		this.valor = valor;
		this.descripcion = descripcion;
		this.nombreColumna = nombreColumna;
	}
	
	public String getValor() {
		return this.valor;
	}
	
	public String getDescripcion() {
		return this.descripcion;
	}
	
	public static TipoAsistenciaEnum getTipoAsistenciaEnum(String valor) {
		for (TipoAsistenciaEnum elemento : TipoAsistenciaEnum.values()) {
			if (elemento.getValor().equalsIgnoreCase(valor)) {
				return elemento;
			}
		}
		return null;
	}
	
	/**
	 * @return the nombreColumna
	 */
	public String getNombreColumna() {
		return nombreColumna;
	}
	
}
