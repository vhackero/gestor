package mx.gob.sedesol.basegestor.commons.utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import org.apache.log4j.Logger;

public class UtilidadadDescargaArchivoUrl {

	private static final Logger logger = Logger.getLogger(UtilidadadDescargaArchivoUrl.class);
	
	 
	 public static Boolean descargaArchivoApartirDeUrl(String url,String ubicacionArchivo){
		 		
		 	logger.info("Ruta en donde dejara el archivo :"+ubicacionArchivo);
		 	Boolean resultadoOperacionCorrecto=Boolean.TRUE;
		 
	        try {
	        	logger.info("Inicia la descarga url:"+url);
	            URL website = new URL(url);
	            ReadableByteChannel rbc = Channels.newChannel(website.openStream());
	            FileOutputStream fos = new FileOutputStream(ubicacionArchivo);
	            
	            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);

	            fos.close();
	            rbc.close();
	            
	            logger.info("Descargo el archivo correctamente");

	        } catch (IOException e) {
	        	logger.error("Ocurrio un error en la descarga del archivo :"+url+" en  "+ubicacionArchivo);
	            e.printStackTrace();
	            resultadoOperacionCorrecto = Boolean.FALSE;
	        }


	        return resultadoOperacionCorrecto;
	    }
	 
	
	
}
