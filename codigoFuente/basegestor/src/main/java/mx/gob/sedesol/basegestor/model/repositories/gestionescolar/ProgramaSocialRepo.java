package mx.gob.sedesol.basegestor.model.repositories.gestionescolar;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import mx.gob.sedesol.basegestor.model.entities.gestionescolar.CatProgramaSocial;

@Repository
public interface ProgramaSocialRepo extends JpaRepository<CatProgramaSocial, Integer> {
	
	@Query("SELECT ps FROM CatProgramaSocial ps "
			+ "WHERE ps.unidadResponsable.idUnidadResponsable = ?1")
	public List<CatProgramaSocial> obtenerProgramasPorUnidad(Integer idUnidadResponsable);

}
