package mx.gob.sedesol.basegestor.commons.utils;

public enum EstatusPersonaEnum {

	ACTIVO(Boolean.TRUE,"Activo"),
	INACTIVO(Boolean.FALSE,"Inactivo");
	
	private boolean valor;
	private String descripcion;
	
	private EstatusPersonaEnum(Boolean valor, String descripcion) {
		this.valor = valor;
		this.descripcion = descripcion;
	}
	
	public boolean getValor() {
		return this.valor;
	}
	
	public String getDescripcion() {
		return this.descripcion;
	}
	
	public static EstatusPersonaEnum getEstatusPersonaEnum(Boolean valor) {
		for (EstatusPersonaEnum elemento : EstatusPersonaEnum.values()) {
			if (elemento.getValor() == valor) {
				return elemento;
			}
		}
		return null;
	}
	
	public static EstatusPersonaEnum getEstatusPersonaEnum(String descripcion) {
		for (EstatusPersonaEnum elemento : EstatusPersonaEnum.values()) {
			if (elemento.getDescripcion().equalsIgnoreCase(descripcion)) {
				return elemento;
			}
		}
		return null;
	}
	
}
