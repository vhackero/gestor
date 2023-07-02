package mx.gob.sedesol.basegestor.model.repositories.logisticainfraestructura;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mx.gob.sedesol.basegestor.model.entities.logisticainfraestructura.CatSede;

@Repository
public interface SedeRepo extends JpaRepository<CatSede, Integer> {

	@Query("SELECT s FROM CatSede s WHERE s.catUbicacionTerritorial.id = :idUbicacion AND s.organismoGubernamental.id = :idOrgGub ")
	public List<CatSede> consultaSedesPorUbicacionOrgGub(@Param("idUbicacion") Integer idUbicacion, @Param("idOrgGub") Integer idOrgGub);
	
	@Query("SELECT s FROM CatSede s WHERE s.organismoGubernamental.id = :idOrgGub ")
	public List<CatSede> consultaSedesPorOrgGubDepedencia(@Param("idOrgGub") Integer idOrgGub);
	
}
