package mx.gob.sedesol.basegestor.commons.utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.log4j.Logger;

public class UtilidadesDescargaArchivoFTP {

	private static final Logger logger = Logger.getLogger(UtilidadesDescargaArchivoFTP.class);
	
	private static final int NUMERO_MENOS_UNO = -1;
	private static final int NUMERO_CERO = 0;
	private static final String COMODIN_DIAGONAL= "/" ;
	private static final int BUFFER_SIZE = 4096;
	
	
	private FTPClient ftpCliente;
	private String host;
	private Integer puerto;
	private String usuario;
	private String pasword;
	
	
	public UtilidadesDescargaArchivoFTP(String host, Integer puerto, String usuario, String pasword) {
		super();
		this.host = host;
		this.puerto = puerto;
		this.usuario = usuario;
		this.pasword = pasword;
		this.ftpCliente = new FTPClient();
	}
	
	
	private void conectarFTP() throws IOException {
		Boolean resultadoOperacion = Boolean.FALSE;
		try {
			ftpCliente.connect(host, puerto);
			resultadoOperacion = ftpCliente.login(usuario, pasword);
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

	private void crearDirectorio(String rutaLocal){		
		new File(rutaLocal.substring(NUMERO_CERO,rutaLocal.lastIndexOf(COMODIN_DIAGONAL))).mkdirs();		
	}
	
	public void descargarArchivo(String rutaLocal, String rutaRemota) throws IOException {
		this.conectarFTP();
		this.crearDirectorio(rutaLocal);		
		Boolean success = Boolean.FALSE;
		OutputStream os = null;
		InputStream inputStream = null;
		String mensajeResultado = "No se pudo descargar el archivo, revisar configuracion, contactar a el administrador";
		try {
			File archivoDescargado = new File(rutaLocal);

			os = new BufferedOutputStream(new FileOutputStream(archivoDescargado));

			inputStream = ftpCliente.retrieveFileStream(rutaRemota);
			if(inputStream == null){
				throw new IOException("Elementos no encontrados en el servidor remoto"
						+ " para la descarga de archivo");
			}
			byte[] bytesArray = new byte[BUFFER_SIZE];
			int bytesRead = NUMERO_MENOS_UNO;
			while ((bytesRead = inputStream.read(bytesArray)) != NUMERO_MENOS_UNO) {
				os.write(bytesArray, NUMERO_CERO, bytesRead);
			}

			success = ftpCliente.completePendingCommand();
			if (success) {
				mensajeResultado = "El archivo se descargo correctamente.";			
			}
			logger.info(mensajeResultado);

		} catch (FileNotFoundException e) {
			logger.error("El archivo o la ruta no se encontro");
			e.printStackTrace();
			throw e;
		} catch (IOException e) {
			logger.error("Error en la coneccion FTP");
			e.printStackTrace();			
			throw e;
		} finally {
			try {
				if (ftpCliente.isConnected()) {
					ftpCliente.logout();
					ftpCliente.disconnect();
					os.close();
					inputStream.close();
				}				
				this.ftpCliente = null;
				this.host = null;
				this.usuario = null;
				this.pasword = null;
			} catch (IOException ex) {
				ex.printStackTrace();
			}

		}

	}
}
