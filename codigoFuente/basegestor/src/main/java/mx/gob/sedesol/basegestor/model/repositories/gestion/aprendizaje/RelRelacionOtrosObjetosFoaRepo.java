package mx.gob.sedesol.basegestor.model.repositories.gestion.aprendizaje;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mx.gob.sedesol.basegestor.model.entities.gestionaprendizaje.RelCatRelacionOtrosObjetosFoa;

@Repository
public interface RelRelacionOtrosObjetosFoaRepo extends JpaRepository<RelCatRelacionOtrosObjetosFoa, Integer>
{
	@Query("SELECT t FROM RelCatRelacionOtrosObjetosFoa t WHERE t.idFoa=:clFoa ORDER BY t.idCatRelacionOtrosObjetos")
	List<RelCatRelacionOtrosObjetosFoa> buscarRelacionOtrosObjetosFoa(@Param("clFoa") Integer clFoa);
}
