package mx.gob.sedesol.basegestor.model.repositories.admin;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import mx.gob.sedesol.basegestor.model.entities.admin.CatTipoDiscapacidad;

@Repository
public interface TipoDiscapacidadRepo extends JpaRepository<CatTipoDiscapacidad, String> {

	@Query("SELECT c FROM CatTipoDiscapacidad c "
			+ "JOIN FETCH c.catDiscapacidad d "
			+ "WHERE d.idDiscapacidad = ?1 ORDER BY d.discapacidad")
	List<CatTipoDiscapacidad> buscarPorDiscapacidad(int idDiscapacidad);

}
