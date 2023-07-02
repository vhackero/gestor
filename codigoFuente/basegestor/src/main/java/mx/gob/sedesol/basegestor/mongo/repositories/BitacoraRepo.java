package mx.gob.sedesol.basegestor.mongo.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import mx.gob.sedesol.basegestor.mongo.model.Bitacora;

/**
 * Repositorio para el documento Bitacora
 * el cual hace referencia a una coleccion MONGODB
 * 
 * @author Carlos Lopez
 *
 */
@Repository
public interface BitacoraRepo extends MongoRepository<Bitacora, String> {
		
}
