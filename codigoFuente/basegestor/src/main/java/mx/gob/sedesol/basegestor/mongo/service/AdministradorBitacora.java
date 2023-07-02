package mx.gob.sedesol.basegestor.mongo.service;

import java.io.Serializable;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.mongodb.MongoException;

import mx.gob.sedesol.basegestor.mongo.model.Bitacora;
import mx.gob.sedesol.basegestor.mongo.repositories.BitacoraRepo;

/**
 * Servicio para el almacenamiento de la bitacora
 * 
 * @author Carlos Lopez
 *
 */
@Service("administradorBitacora")
public class AdministradorBitacora implements Serializable {
	
	private static final long serialVersionUID = -7381485056851294088L;

	private static Logger logger = Logger.getLogger(AdministradorBitacora.class);
	
	@Autowired
	private BitacoraRepo bitacoraRepo;

	/**
	 * Almacena la bitacora
	 * con los siguientes datos
	 * @param idUsuario - id del usuario que realizo la operacion
	 * @param funcionalidad - clave de la operacion
	 * @param registro - id del registro de la operacion
	 */
	//@Async
	/*public void almacenarBitacora(Long idUsuario, String funcionalidad, String registro) {
		try {

			logger.info("Creando instancia de MongoDB Client.");

			Bitacora bitacora = new Bitacora(idUsuario, funcionalidad, registro);
			bitacoraRepo.save(bitacora);
			
			logger.info("Se genero registro en bitacora --> usuario: " + idUsuario + " funcionalidad: " + funcionalidad +" registro: " + registro);
		} catch (MongoException e) {
			logger.error(e.getMessage(),e);
		}
	}*/

}
