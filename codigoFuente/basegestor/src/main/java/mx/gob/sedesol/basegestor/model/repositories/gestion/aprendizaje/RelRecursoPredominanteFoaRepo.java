package mx.gob.sedesol.basegestor.model.repositories.gestion.aprendizaje;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mx.gob.sedesol.basegestor.model.entities.gestionaprendizaje.RelRecursoPredominanteFoa;

@Repository
public interface RelRecursoPredominanteFoaRepo extends JpaRepository<RelRecursoPredominanteFoa, Integer>
{
	@Query("SELECT t FROM RelRecursoPredominanteFoa t WHERE t.idFoa=:clFoa ORDER BY t.idCatRecursoPredominante")
	List<RelRecursoPredominanteFoa> buscarRecursoPredominanteFoa(@Param("clFoa") Integer clFoa);
}
