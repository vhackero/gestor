package mx.gob.sedesol.basegestor.commons.dto.gestionescolar;

public class GroupByGestionEscolarDTO {

	
	/**
	 * Es la llave en el mapa
	 */
	private String llavePrimaria;
	
	/**
	 * Es la llave secundaria
	 */
	private String llaveSecundaria;	
	
	/**
	 * Es el valor en el mapa
	 */
	private String valor;
	
	public GroupByGestionEscolarDTO(){}
	
	/**
	 * Quitar este constructor
	 * @param llave
	 * @param valor
	 */
	public GroupByGestionEscolarDTO (Long llave,Long valor){
		
		this.llavePrimaria = String.valueOf(llave);
		this.valor         = String.valueOf(valor);
		
	}
	
	public GroupByGestionEscolarDTO (String llave,String valor){
		
		this.llavePrimaria = llave;
		this.valor         = valor;
		
	}
	public GroupByGestionEscolarDTO (Integer llave,Integer valor){
		
		this.llavePrimaria = String.valueOf(llave);
		this.valor         = String.valueOf(valor);
		
	}
	
	public GroupByGestionEscolarDTO (Integer llave,Long valor){
		this.llavePrimaria = String.valueOf(llave);
		this.valor         = String.valueOf(valor);
	}
	
	public GroupByGestionEscolarDTO (Integer llave,String llaveSecundaria,Long valor){
		
		this.llavePrimaria   = String.valueOf(llave);
		this.valor           = String.valueOf(valor);
		this.llaveSecundaria = llaveSecundaria;
		
	}

	public String getLlavePrimaria() {
		return llavePrimaria;
	}

	public void setLlavePrimaria(String llavePrimaria) {
		this.llavePrimaria = llavePrimaria;
	}

	public String getLlaveSecundaria() {
		return llaveSecundaria;
	}

	public void setLlaveSecundaria(String llaveSecundaria) {
		this.llaveSecundaria = llaveSecundaria;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}
	
	
}
