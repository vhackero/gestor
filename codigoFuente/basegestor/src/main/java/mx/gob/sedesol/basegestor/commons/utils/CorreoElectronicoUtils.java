package mx.gob.sedesol.basegestor.commons.utils;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.apache.log4j.Logger;

import mx.gob.sedesol.basegestor.commons.constantes.ConstantesGestor;

public class CorreoElectronicoUtils {
	
	private static final Logger logger = Logger.getLogger(CorreoElectronicoUtils.class);
	
	private CorreoElectronicoUtils() {
		throw new IllegalAccessError(ConstantesGestor.CLASE_UTILIDADES);
	}
	
	public static boolean esCorreoElectronicoValido(String correoElectronico) {
		boolean resultado = true;
		try {
			InternetAddress direccionCorreo = new InternetAddress(correoElectronico);
			direccionCorreo.validate();
		} catch (AddressException ex) {
			resultado = false;
			logger.error(ex.getMessage(), ex);
		}
		return resultado;
	}

}