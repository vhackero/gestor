package mx.gob.sedesol.basegestor.commons.utils;

import java.math.BigDecimal;

/**
 * Utileria para validar nulidad de Objetos
 *@author omartinez
 */
public final class ObjectUtils {

	/**
	 * valida si el objeto es nulo
	 * @param object
	 * @return
	 */
	public static boolean isNull(Object object) {
		return object == null;
	}

	/**
	 * Valida si el objeto no es nulo
	 * @param object
	 * @return
	 */
	public static boolean isNotNull(Object object) {
		return !isNull(object);
	}
	
	/**
	 * Valida si el objeto es nulo o vacio en caso de que el objeto sea:
	 * String
	 * List
	 * ArrayList
	 * @param objeto
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static boolean isNullOrEmpty(Object objeto) {

		if (objeto == null)
			return true;
		
		if (objeto.getClass().equals(java.lang.String.class)) {
			if (objeto.toString().isEmpty())
				return true;
		}

		if (objeto instanceof java.util.List)
			if (((java.util.List) objeto).isEmpty())
				return true;

		if (objeto instanceof java.util.ArrayList)
			if (((java.util.ArrayList) objeto).isEmpty())
				return true;
		
		return false;

	}
	
	
	/**
	 * Valida si un objeto es nulo o si es igual a cero
	 * @param objeto
	 * @return
	 */
	public static boolean isNullOrCero(Object objeto) {
		if (objeto == null) {
			return true;
		}
		
		if (objeto.getClass().equals(java.lang.Long.class)) {
			if (objeto.equals(0L))
				return true;
		}
		
		if (objeto.getClass().equals(java.lang.Integer.class)) {
			if (objeto.equals(0))
				return true;
		}
		
		if (objeto.getClass().equals(java.lang.Double.class)) {
			if (objeto.equals(0.0))
				return true;
		}
		
		if (objeto.getClass().equals(java.lang.String.class)) {
			if (objeto.equals("0"))
				return true;
		}
		
		if (objeto instanceof BigDecimal ) {
			
			BigDecimal bg = (BigDecimal)objeto;
			
			if (bg.compareTo(new BigDecimal("0")) == 0)
				return true;
		}
		
		return false;
	}
	
}
