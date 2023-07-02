package mx.gob.sedesol.basegestor.model.repositories.gestion.aprendizaje;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mx.gob.sedesol.basegestor.model.entities.gestionaprendizaje.RelUnidadOaAva;

@Repository
public interface RelUnidadOaAvaRepo extends JpaRepository<RelUnidadOaAva,Integer> {

	
	
	@Query("SELECT unidadAva FROM RelUnidadOaAva unidadAva "
			+ "	JOIN FETCH unidadAva.ambienteVirtualAprendizaje ava	"
			+ " WHERE ava.id =:idAva")
	public List<RelUnidadOaAva> findByIdAva(@Param("idAva") Integer idAva);
	
	@Query("SELECT unidadAva FROM RelUnidadOaAva unidadAva "
			+ "	JOIN FETCH unidadAva.ambienteVirtualAprendizaje ava	"
			+ " JOIN FETCH unidadAva.detEstUnidadDidactica "
			+ "	LEFT JOIN FETCH unidadAva.fichaDescriptivaObjetoAprendizaje foa "
			+ " WHERE ava.id =:idAva")
	public List<RelUnidadOaAva> obtenerUnidadesCompletasPorAva(@Param("idAva") Integer idAva);
	
	
}
