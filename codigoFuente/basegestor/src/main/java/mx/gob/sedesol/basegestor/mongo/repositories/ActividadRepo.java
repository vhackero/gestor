package mx.gob.sedesol.basegestor.mongo.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import mx.gob.sedesol.basegestor.mongo.model.Actividad;

@Repository
public interface ActividadRepo  extends MongoRepository<Actividad, String> {
	
	@Query(value="{ idUsuario : ?0 }")
	List<Actividad> findByIdUsuario(long idUsuario);

}
