package mx.gob.sedesol.basegestor.commons.utils;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import mx.gob.sedesol.basegestor.commons.constantes.ConstantesGestor;

/**
 * Utileria de manejo de fechas
 * @author omartinez
 *
 */
public final class DateUtils {
	
	public final static String FORMATO_DDMMYYYY="dd/MM/yyyy";
	
	public final static String FORMATO_FECHA = "dd-MMM-yyyy";
	
	public final static String FORMATO_FECHA_HORA = "dd-MMM-yyyy HH:mm";
	
	private DateUtils() {
		throw new IllegalAccessError(ConstantesGestor.CLASE_UTILIDADES);
	}

	/**
	 * Convierte String a Date de acuerdo al formato especificado
	 * @param fecha
	 * @param formato
	 * @return
	 */
	public static Date convierteStringADate(String fecha, String formato){
		SimpleDateFormat sdf = new SimpleDateFormat(formato);
		try {
			return sdf.parse(fecha);
		} catch (ParseException e) {
			return null;
		}
	}
	
	/**
	 * Convierte Date a String de acuerdo al formato especificado
	 * @param fecha
	 * @return
	 */
	public static String convierteDateAString(Date fecha, String formato){
		SimpleDateFormat sdf = new SimpleDateFormat(formato);
		return sdf.format(fecha);
	}
	
	/**
	 * 
	 * @param fecha
	 * @return
	 */
	public static Date consultaSiFechaEsSabadoDomingo(Date fecha){
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fecha);
		int diaSemana = calendar.get(Calendar.DAY_OF_WEEK);
		
		if(diaSemana == Calendar.SATURDAY)
			return null;
		
		if(diaSemana == Calendar.SUNDAY)
			return null;
			
		else
			return fecha;
	}
	
	/**
	 * 
	 * @param fecha
	 * @return
	 */
	public static boolean fechaEsDiaViernes(Date fecha){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fecha);
		int diaSemana = calendar.get(Calendar.DAY_OF_WEEK);
		
		if(diaSemana == Calendar.FRIDAY)
			return true;
		
		return false;
	}
	
	/**
	 * 
	 * @param fecha
	 * @return
	 */
	public static Date consultaFechaHabilAnteriorSabadoDomingo(Date fecha){
		
		Calendar dayTime = Calendar.getInstance();
		dayTime.setTime(fecha);
		
		int diaSemana = dayTime.get(Calendar.DAY_OF_WEEK);
		
		if(diaSemana == Calendar.SATURDAY
				|| diaSemana == Calendar.SUNDAY ){
			//busca el dia habil de L a V
			dayTime.add(Calendar.DAY_OF_MONTH, -1);
			return consultaFechaHabilAnteriorSabadoDomingo(dayTime.getTime());
		}
		
		else{
			return fecha;
		}
	}
	
	/**
	 * 
	 * @param fecha
	 * @return
	 */
	public static Date consultaFechaHabilPosteriorSabadoDomingo(Date fecha){
	
		Calendar dayTime = Calendar.getInstance();
		dayTime.setTime(fecha);
		
		int diaSemana = dayTime.get(Calendar.DAY_OF_WEEK);
		
		if(diaSemana == Calendar.SATURDAY
				|| diaSemana == Calendar.SUNDAY ){
			//busca el dia habil de L a V
			dayTime.add(Calendar.DAY_OF_MONTH, 1);
			return consultaFechaHabilPosteriorSabadoDomingo(dayTime.getTime());
		}
		
		else{
			return fecha;
		}
	}
	
	/**
	 * 
	 * @param fecha
	 * @return
	 */
	public static Date agregaDiaHabilAFecha(Date fecha){
		
		Calendar dayTime = Calendar.getInstance();
		dayTime.setTime(fecha);
			
		dayTime.add(Calendar.DAY_OF_MONTH, 1);
		return dayTime.getTime();
	}
	
	/**
	 * 
	 * @param fecha
	 * @param numDias
	 * @return
	 */
	public static Date agregaDiasHabilesAFecha(Date fecha, Integer numDias){
		
		Calendar dayTime = Calendar.getInstance();
		dayTime.setTime(fecha);
		
		for(int i=0; i<=numDias; i++){
			
			int diaSemana = dayTime.get(Calendar.DAY_OF_WEEK);
			
			if(diaSemana == Calendar.SATURDAY
					|| diaSemana == Calendar.SUNDAY ){
				
				Date nuevaFechaHabil = consultaFechaHabilPosteriorSabadoDomingo(dayTime.getTime());
				
				dayTime.setTime(nuevaFechaHabil);
			}else{
				dayTime.add(Calendar.DAY_OF_MONTH, 1);
			}
		}
		
		return dayTime.getTime();
	}
	
	/**
	 * 
	 * @return
	 */
	public static String getFechaHoraCadenaSinFormato(){
		Calendar cal = new GregorianCalendar();
		cal.setTime(new Date());
		Integer diaMes = cal.get(Calendar.DAY_OF_MONTH);
		Integer mes = cal.get(Calendar.MONTH) + 1;
		Integer anio = cal.get(Calendar.YEAR);
		Integer hora = cal.get(Calendar.HOUR_OF_DAY);
		Integer minuto = cal.get(Calendar.MINUTE);
		Integer seg = cal.get(Calendar.SECOND);
		
		return agregaCero(diaMes) + agregaCero(mes) + anio.toString() + agregaCero(hora) + agregaCero(minuto) + agregaCero(seg);
	}
	
	private static String agregaCero(Integer valor){
		if(valor <= 9){
			return "0"+valor.toString();
		}
		return valor.toString();
	}
	
	
	/**
	 * 
	 * @return
	 */
	public static Date getcurrentDate(){
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        
        return cal.getTime();
	}
	
	public static Date obtenerFechaHoraActual(){
		Calendar cal = Calendar.getInstance();
        return cal.getTime();
	}
	
	 /**
     * Establece una fecha a la hora inicial del dia
     * @param fechaInicial
     * @return
     */
    public static Date procesarFechaInicio(Date fechaInicial) {
        Date fechaProcesada = null;

        if (ObjectUtils.isNotNull(fechaInicial)) {
            Calendar fecha = Calendar.getInstance();
            fecha.setTime(fechaInicial);
            fecha.set(Calendar.HOUR_OF_DAY, 0);
            fecha.set(Calendar.MINUTE, 0);
            fecha.set(Calendar.SECOND, 0);
            fecha.set(Calendar.MILLISECOND, 0);
            fechaProcesada = fecha.getTime();
        }
        return fechaProcesada;
    }

    
    /**
     * Establece una fecha a la hora final del dia
     * @param fechaFinal
     * @return
     */
    public static Date procesarFechaFin(Date fechaFinal) {
        Date fechaProcesada = null;

        if (ObjectUtils.isNotNull(fechaFinal)) {
            Calendar fecha = Calendar.getInstance();
            fecha.setTime(fechaFinal);
            fecha.set(Calendar.HOUR_OF_DAY, 23);
            fecha.set(Calendar.MINUTE, 59);
            fecha.set(Calendar.SECOND, 59);
            fecha.set(Calendar.MILLISECOND, 999);
            fechaProcesada = fecha.getTime();
        }
        return fechaProcesada;
    }
    
    
    public static Date getCreateDate(int dia, int mes , int anio){
        Date fechaProcesada = null;

       
            Calendar fecha = Calendar.getInstance();
            fecha.set(anio, mes-1, dia);
            fecha.set(Calendar.HOUR_OF_DAY, 0);
            fecha.set(Calendar.MINUTE, 0);
            fecha.set(Calendar.SECOND, 0);
            fecha.set(Calendar.MILLISECOND, 0);

            fechaProcesada = fecha.getTime();
        
        return fechaProcesada;
    }
    
    /**
     * 
     * @param fecha
     * @param patron
     * @return
     */
    public static String darFormatoFecha(Date fecha, String patron) {
		if (ObjectUtils.isNotNull(fecha)) {
			SimpleDateFormat formato = new SimpleDateFormat(patron);
			return formato.format(fecha);
		} else {
			return "";
		}
	}
    
    /**
     * 
     * @param fechaIni
     * @param fechaFin
     * @return
     */
    public static Integer restaFechasEnDiasMes(Date fechaIni, Date fechaFin){
    	
    	Calendar fechaCalIni = Calendar.getInstance();
    	Calendar fechaCalFin = Calendar.getInstance();
    	
    	fechaCalIni.setTime(fechaIni);
    	fechaCalFin.setTime(fechaFin);
    	
    	int diasDisp = fechaCalFin.get(Calendar.DAY_OF_MONTH) - fechaCalIni.get(Calendar.DAY_OF_MONTH);
    	return diasDisp;
    	
    }
    
    public static String formatoFechaConstancia(Date fecha){
    	
    	Format formatoDia = new SimpleDateFormat("dd" , new Locale("ES"));
    	Format formatoMes = new SimpleDateFormat("MMMMM" , new Locale("ES"));
    	Format formatoAnio = new SimpleDateFormat("YYYY" , new Locale("ES"));
    	
    	StringBuilder fechaString = new StringBuilder();
    	fechaString.append(formatoDia.format(fecha));
    	fechaString.append(" de ");
    	fechaString.append(formatoMes.format(fecha));
    	fechaString.append(" del ");
    	fechaString.append(formatoAnio.format(fecha));
    	
    	return fechaString.toString();
    }

}
