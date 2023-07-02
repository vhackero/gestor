package mx.gob.sedesol.basegestor.commons.utils;

public enum EstatusActividadEnum {
	
	PENDIENTE("Pendiente"),
	FINALIZADO("Finalizado");
	
	private String valor;
	
	private EstatusActividadEnum(String valor) {
		this.valor = valor;
	}
	
	public String getValor() {
		return this.valor;
	}

}
