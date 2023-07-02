package mx.gob.sedesol.basegestor.commons.utils;

public enum RecursosOaEnum {

	SCORM("SCORM"),
	TEMA_DISCUCION("Tema de discusión"),
	FORO("Foro");
	
	private String valor;
	
	private RecursosOaEnum (String valor){
		this.valor = valor;		
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}
	
	
	public static RecursosOaEnum getRecursosOaEnum(String valor) {
		for (RecursosOaEnum elemento : RecursosOaEnum.values()) {
			if (elemento.getValor().equals(valor)) {
				return elemento;
			}
		}
		return null;
	}
	
	
	
	
	
	
}
