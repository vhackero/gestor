package mx.gob.sedesol.basegestor.model.repositories.gestion.aprendizaje;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import mx.gob.sedesol.basegestor.model.entities.gestionaprendizaje.RelFoaActividadReforzamiento;

@Repository
public interface RelActividadesReforzamientoFoaRepo extends JpaRepository<RelFoaActividadReforzamiento, Integer>
{
	@Query("SELECT t FROM RelFoaActividadReforzamiento t WHERE t.idFoa=:clFoa ORDER BY t.idActividadReforzamiento")
	List<RelFoaActividadReforzamiento> buscarActividadReforzamientoFoa(@Param("clFoa") Integer clFoa);
}
