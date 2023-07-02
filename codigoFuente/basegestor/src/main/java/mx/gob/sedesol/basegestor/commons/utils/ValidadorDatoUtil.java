package mx.gob.sedesol.basegestor.commons.utils;

import java.util.regex.Pattern;

public class ValidadorDatoUtil {
	
	public static boolean esNombre(String valor) {
		final String regex = "[a-zA-ZáéíóúÁÉÍÓÚ ]{1,45}$";
		Pattern patron = Pattern.compile(regex);
		return patron.matcher(valor).matches();
	}
	
	public static boolean esNombreCompleto(String valor) {
		final String regex = "[a-zA-ZáéíóúÁÉÍÓÚ ]{1,130}$";
		Pattern patron = Pattern.compile(regex);
		return patron.matcher(valor).matches();
	}

}
