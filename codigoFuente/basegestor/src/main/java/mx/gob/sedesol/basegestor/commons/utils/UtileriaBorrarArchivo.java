package mx.gob.sedesol.basegestor.commons.utils;

import java.io.File;

import org.apache.log4j.Logger;

public class UtileriaBorrarArchivo {

	private static final Logger logger = Logger.getLogger(UtileriaBorrarArchivo.class);
	
	public  static Boolean borraArchivo (String archivoABorrar){
		
		Boolean borroArchivoCorrectamente = Boolean.FALSE;

		try{

			File file = new File(archivoABorrar);
			

    		if(file.delete()){    			
    			logger.info("El archivo : "+file.getName()+" se borro correctamente" );
    			borroArchivoCorrectamente =Boolean.TRUE;
    		}else{
    			logger.info("El archivo : "+file.getName()+" No se borro correctamente, valide la existencia del archivo o los permisos "
    					+ "para manipularlo" );
    		}

    	}catch(Exception e){
    		logger.error("Ocurrio un error al borrar el archivo ");
    		e.printStackTrace();

    	}	
		
		
		return borroArchivoCorrectamente;
	}
	
	
	
}
