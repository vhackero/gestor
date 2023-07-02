package mx.gob.sedesol.basegestor.commons.utils;

public enum SemaforoEnum {

	SEMAFORO_ROJO("SemaforoRojo"),	
	SEMAFORO_VERDE("SemaforoVerde"),	
	SEMAFORO_AMARILLO("SemaforoAmarillo"),	
	SEMAFORO_GRIS("SemaforoGris");
	
	
	private String colorSemaforo;
	
	private SemaforoEnum(String colorSemaforo){
		this.colorSemaforo = colorSemaforo;
		
	}
	
	
	public String getColorSemaforo() {
		return this.colorSemaforo;
	}
	
	
	
	public static SemaforoEnum getSemaforoEnum(String colorSemaforo) {
		for (SemaforoEnum elemento : SemaforoEnum.values()) {
			if (elemento.getColorSemaforo() == colorSemaforo) {
				return elemento;
			}
		}
		return null;
	}

	
	
	
}
