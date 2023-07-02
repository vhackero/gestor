package mx.gob.sedesol.basegestor.model.repositories.gestion.aprendizaje;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mx.gob.sedesol.basegestor.model.entities.gestionaprendizaje.RelMaterialDidacticoFoa;

@Repository
public interface RelMaterialDidacticoFoaRepo extends JpaRepository<RelMaterialDidacticoFoa, Integer>
{
	@Query("SELECT t FROM RelMaterialDidacticoFoa t WHERE t.idFoa=:clFoa ORDER BY t.idCatRecursoDidactico")
	List<RelMaterialDidacticoFoa> buscarMaterialDidacticoFoa(@Param("clFoa") Integer clFoa);
}
