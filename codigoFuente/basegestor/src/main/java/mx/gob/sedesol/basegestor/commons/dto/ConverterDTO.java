package mx.gob.sedesol.basegestor.commons.dto;

import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;

/**
 * 
 * @author ormg
 *
 * @param <D>
 */
public class ConverterDTO <D> {

	private Object objetoACastear;
	private Class<?> objetoResultadoClass;
	
	public ConverterDTO (Object object, Class<?> objetoDto){
		this.objetoACastear = object;
		this.objetoResultadoClass = objetoDto;
	}
	
	/**
	 * Castea el Objeto al Tipo de DTO
	 * @param objOrigen
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public D getObjetoDTOFromObject(){
		
		if(ObjectUtils.isNull(objetoACastear))
			return null;
		
		if(objetoACastear.getClass().equals(objetoResultadoClass)){
			return(D) objetoACastear;
		}
		
		return null;
	}
}
