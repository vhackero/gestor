package mx.gob.sedesol.basegestor.commons.utils;

import java.util.Random;

public class UtilidadesToken {
	
	public static String getToken(int longitud) {
		String token = "";
		long milis = new java.util.GregorianCalendar().getTimeInMillis();
		Random r = new Random(milis);
		int i = 0;
		while (i < longitud) {
			char c = (char) r.nextInt(255);
			if ((c >= '0' && c <= '9') || (c >= 'A' && c <= 'Z')) {
				token += c;
				i++;
			}
		}
		return token;
	}

}
