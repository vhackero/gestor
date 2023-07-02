package mx.gob.sedesol.basegestor.commons.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.log4j.Logger;

public class UtilidadesCargaArchivoFTP {

	private static final Logger logger = Logger.getLogger(UtilidadesCargaArchivoFTP.class);
	
	private static final int BUFFER_SIZE = 4096;
	private static final int LECTURA_INICIAL = 0;
	private static final int NUMERO_MENOS_UNO = -1;
	private static final int NUMERO_CERO = 0;
	private static final String COMODIN_DIAGONAL= "/" ;
	
	
	private FTPClient ftpCliente;
	private String host;
	private Integer puerto;
	private String usuario;
	private String pasword;

	public UtilidadesCargaArchivoFTP(String host, Integer puerto, String usuario, String pasword){
		super();
		this.host = host;
		this.puerto = puerto;
		this.usuario = usuario;
		this.pasword = pasword;
		this.ftpCliente = new FTPClient();
		
	}
	
	private void crearDirectorio(String rutaRemota,FTPClient ftpCliente) throws IOException{
		try {
		boolean completed;		
			completed = makeDirectories(ftpCliente, rutaRemota.substring(NUMERO_CERO,rutaRemota.lastIndexOf(COMODIN_DIAGONAL)));			
			logger.info("La creacion de la carpeta se ejecuto correctamente: "+completed);		
		} catch (IOException e) {
			logger.error("Ocurrio un error al leer y/o crear la estructura de archivos");		
			e.printStackTrace();
			throw e;			
		}
		           
		
	}
	
	private void borrarArchivo(String rutaLocal){
		String mensaje = "Ocurrio un problema al borrar el archivo"+rutaLocal ;		
		if(new File(rutaLocal).delete()){
			mensaje = "Borro el archivo correctamente "+rutaLocal;			
		}		
		logger.info(mensaje);							
	}
	
	private void conectarFTP() throws IOException {
	boolean resultadoOperacion = Boolean.FALSE;
		try {
			ftpCliente.connect(host, puerto);
			resultadoOperacion = 
						ftpCliente.login(usuario, pasword);
			if(!resultadoOperacion){
				logger.error("Usuario o contrasenia invalidos para FTP>"+host+":"+puerto);
				throw new IOException("Usuario o contrasenia invalidos para FTP>"+host+":"+puerto);
			}
			ftpCliente.enterLocalPassiveMode();
			ftpCliente.setFileType(FTP.BINARY_FILE_TYPE);
			logger.info("Se conecto correctamente");
		} catch (IOException e) {
			logger.error("Ocurrio una excepcion al conectarse");
			e.printStackTrace();
			throw e;

		}
	}
	
	public void cargarArchivo(String rutaLocal, String rutaRemota) throws IOException{
		InputStream inputStream = null;
        OutputStream outputStream = null;
        Boolean completed = Boolean.FALSE;
        String mensaje = "El archivo no se ha podido Cargar, revisar configuracion, contactar a el administrador";
		try {
			this.conectarFTP();
			this.crearDirectorio(rutaRemota,ftpCliente);
			logger.info("Inicia carga de archivo ");
			inputStream = new FileInputStream(rutaLocal);
			outputStream = ftpCliente.storeFileStream(rutaRemota);
			if(outputStream == null){
				logger.error("No se encontraron los elementos en el servidor para subir el archivo");
            	throw new IOException("No se encontraron los elementos en el servidor para subir el archivo");
            }
            byte[] bytesIn = new byte[BUFFER_SIZE];
            int read = LECTURA_INICIAL;
 
            while ((read = inputStream.read(bytesIn)) != NUMERO_MENOS_UNO) {
                outputStream.write(bytesIn, LECTURA_INICIAL, read);
            }
            inputStream.close();
            outputStream.close();                        
            completed = ftpCliente.completePendingCommand();
            if (completed) {
            	mensaje="El archivo se cargo correctamente.";            	
            }
            logger.info(mensaje);
            
            borrarArchivo(rutaLocal);

		} catch (IOException e) {		
			logger.error("Error en la coneccion FTP");
			e.printStackTrace();
			throw e;
			
		}  finally {
			
          try {
              if (ftpCliente.isConnected()) {
            	  ftpCliente.logout();
            	  ftpCliente.disconnect();
            	  inputStream.close();
                  outputStream.close();                 
              }              
              this.ftpCliente = null;
              this.host = null;
              this.puerto = null;
              this.usuario = null;
              this.pasword = null;
          } catch (Exception ex) {
              ex.printStackTrace();
          }
      }
	}
	
    private static boolean makeDirectories(FTPClient ftpClient, String dirPath)
            throws IOException {
        String[] pathElements = dirPath.split(COMODIN_DIAGONAL);
        
        if (pathElements != null && pathElements.length > NUMERO_CERO) {
            for (String singleDir : pathElements) {
            	if(singleDir != null && !singleDir.equals("")){            	
                boolean existed = ftpClient.changeWorkingDirectory(singleDir);
                if (!existed) {
                    boolean created = ftpClient.makeDirectory(singleDir);
                    if (created) {
                        logger.info("Directorio creado: " + singleDir);
                        ftpClient.changeWorkingDirectory(singleDir);
                    } else {
                        logger.info("El directorio no se puede crear: " + singleDir);
                        return false;
                    }
                }}
            }
        }
        return true;
    }

	
	
	
	
	
}
