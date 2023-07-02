package mx.gob.sedesol.basegestor.mongo.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import mx.gob.sedesol.basegestor.mongo.model.BitacoraEntradaSistema;

/**
 * Repositorio para el documento BitacoraEntradaSistema
 * el cual hace referencia a una coleccion MONGODB
 * 
 * @author Carlos Lopez
 *
 */
@Repository
public interface BitacoraEntradaSistemaRepo extends MongoRepository<BitacoraEntradaSistema, Long> {

}
