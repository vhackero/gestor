package mx.gob.sedesol.basegestor.commons.utils;

public enum TipoNotificacionEnum {
	
	ALERTA(1),
	AVISO(2),
	MENSAJE(3);
	
	private int valor;
	
	private TipoNotificacionEnum(int valor) {
		this.valor = valor;
	}

	public int getValor() {
		return this.valor;
	}
	
	public static TipoNotificacionEnum geTipoNotificacionEnum(int valor) {
		for (TipoNotificacionEnum tipo : values()) {
			if (tipo.getValor() == valor) {
				return tipo;
			}
		}
		return null;
	}
}
