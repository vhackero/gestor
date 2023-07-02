/**
 * 
 */
package mx.gob.sedesol.gestorweb.commons.utils;

/**
 * @author PlanetMedia
 *
 */
public enum ResultadoTransaccionEnum {

	FALLIDO(false),
	EXITOSO(true);
	
	private boolean valor;
	
	ResultadoTransaccionEnum(boolean valor){
		this.valor = valor;
	}
	
	public boolean  getValor(){
		return this.valor;
	}
}
