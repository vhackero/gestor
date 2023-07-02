package mx.gob.sedesol.basegestor.commons.utils;

public enum EstatusReservacionEnum {
	APROBADO(3, "Aprobado"),
	NO_APROBADO(2, "No aprobado");
	
	private Integer valor;
	private String descripcion;
	
	private EstatusReservacionEnum(Integer valor, String descripcion){
		this.setValor(valor);
		this.setDescripcion(descripcion);
	}
	
	public static EstatusReservacionEnum getEstatusReservacionEnum(Integer valor) {
		for (EstatusReservacionEnum elemento : EstatusReservacionEnum.values()) {
			if (elemento.getValor().equals(valor)) {
				return elemento;
			}
		}
		return null;
	}
	
	public static EstatusReservacionEnum getEstatusReservacionEnum(String descripcion) {
		for (EstatusReservacionEnum elemento : EstatusReservacionEnum.values()) {
			if (elemento.getDescripcion().equalsIgnoreCase(descripcion)) {
				return elemento;
			}
		}
		return null;
	}

	
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Integer getValor() {
		return valor;
	}

	public void setValor(Integer valor) {
		this.valor = valor;
	}
}
