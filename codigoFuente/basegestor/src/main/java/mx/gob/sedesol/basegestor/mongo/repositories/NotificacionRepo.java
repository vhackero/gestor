package mx.gob.sedesol.basegestor.mongo.repositories;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import mx.gob.sedesol.basegestor.mongo.model.Notificacion;
/**
 * Repositorio para el documento Notificacion
 * el cual hace referencia a una coleccion MONGODB
 * 
 * @author Carlos Lopez
 *
 */
@Repository
public interface NotificacionRepo extends MongoRepository<Notificacion, String> {
	
	/**
	 * Obtiene las notificaciones 
	 * con los siguientes filtros:
	 * @param idPersona - id del remitente
	 * @param enviado - si ha sido enviado o no
	 * @return
	 */
	@Query(value="{ $and : [ "
			+ "{ mensajes : { $elemMatch : { remitente : ?0 } } }, "
			+ "{ enviado : ?1 } ] }")
	List<Notificacion> findByRemitente(Long idPersona, boolean enviado);
	
	/**
	 * Obtiene las notificaciones que ya han sido enviadas 
	 * con los siguientes filtros:
	 * @param idPersona - id del destinatario
	 * @param activo - si esta activo o no
	 * @param page - limite de documentos junto con ordenamiento
	 * @return
	 */
	@Query(value="{ $and : [ "
			+ "{ destinatarios : { $elemMatch : { $and : [ { idPersona : ?0 }, { activo : ?1 } ] } } }, "
			+ "{ enviado : true } ] }")
	List<Notificacion> findByDestinatario(Long idPersona, boolean activo, Pageable page);
	
	/**
	 * Obtiene las notificaciones que ya han sido enviadas
	 * con los siguientes filtros
	 * @param idPersona - id del destinatario
	 * @param activo - si esta activo o no
	 * @param sort - ordenamiento
	 * @return
	 */
	@Query(value="{ $and : [ "
			+ "{ destinatarios : { $elemMatch : { $and : [ { idPersona : ?0 }, { activo : ?1 } ] } } }, "
			+ "{ enviado : true } ] }")
	List<Notificacion> findByDestinatario(Long idPersona, boolean activo, Sort sort);
	
	/**
	 * Obtiene las notificaciones enviadas que no han sido vistas
	 * con los siguientes filtros
	 * @param idPersona - id del destinatario
	 * @return
	 */
	@Query(value="{ $and : [ "
			+ "{ destinatarios : { $elemMatch : { $and : [ { idPersona : ?0 }, { nuevo : true } ] } } }, "
			+ "{ enviado : true } ] }")
	List<Notificacion> findByDestinatarioNuevo(Long idPersona);
}
