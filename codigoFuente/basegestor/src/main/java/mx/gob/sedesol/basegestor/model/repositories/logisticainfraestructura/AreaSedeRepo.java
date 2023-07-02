package mx.gob.sedesol.basegestor.model.repositories.logisticainfraestructura;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mx.gob.sedesol.basegestor.model.entities.logisticainfraestructura.CatAreasSede;

@Repository
public interface AreaSedeRepo extends JpaRepository<CatAreasSede, Integer> {

	@Query("SELECT asede FROM CatAreasSede asede WHERE asede.catSede.idSede = :idSede AND asede.catEstatusArea.id = 1")
	public List<CatAreasSede> consultaAreasSedePorIdSede(@Param("idSede") Integer idSede);
	
	@Modifying(clearAutomatically = true)
	@Query("DELETE FROM CatAreasSede asede WHERE asede.catSede.idSede = :idSede")
	public void eliminaAreasSedePorIdSede(@Param("idSede") Integer idSede);
	
	@Query("SELECT asede FROM CatAreasSede asede WHERE asede.catArea.id = :idArea AND asede.catEstatusArea.id = 1 ")
	public List<CatAreasSede> consultaAreasSedePorIdArea(@Param("idArea") Integer idArea);
	
	@Query("SELECT asede FROM CatAreasSede asede WHERE asede.catArea.id = :idArea AND asede.catSede.idSede = :idSede ")
	public CatAreasSede consultaAreaSedePorIdSedeyIdArea(@Param("idSede") Integer idSede,@Param("idArea") Integer idArea);
	
}

