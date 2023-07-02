package mx.gob.sedesol.basegestor.model.repositories.gestion.aprendizaje;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import mx.gob.sedesol.basegestor.model.entities.gestionaprendizaje.RelElementosMultimediaFoa;

@Repository
public interface RelElementosMultimediaFoaRepo extends JpaRepository<RelElementosMultimediaFoa, Integer>
{
	@Query("SELECT t FROM RelElementosMultimediaFoa t WHERE t.idFoa=:clFoa ORDER BY t.idElementoMultimediaFoa")
	List<RelElementosMultimediaFoa> buscarElementoMultimediaFoa(@Param("clFoa") Integer clFoa);	
}
