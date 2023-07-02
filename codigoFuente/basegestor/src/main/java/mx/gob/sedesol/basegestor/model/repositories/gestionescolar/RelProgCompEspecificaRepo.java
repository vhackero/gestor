package mx.gob.sedesol.basegestor.model.repositories.gestionescolar;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.RelProgramaCompEspecifica;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.RelProgramaCompEspecificaPK;

public interface RelProgCompEspecificaRepo extends JpaRepository<RelProgramaCompEspecifica, RelProgramaCompEspecificaPK>{

	@Query("SELECT pce FROM RelProgramaCompEspecifica pce "
			+ " JOIN FETCH pce.catCompetenciaEspecifica ce "
			+ " WHERE pce.idPrograma = :idPrograma")
	public List<RelProgramaCompEspecifica> obtenerCompEspPorIdPrograma(@Param("idPrograma") Integer idPrograma);
}
