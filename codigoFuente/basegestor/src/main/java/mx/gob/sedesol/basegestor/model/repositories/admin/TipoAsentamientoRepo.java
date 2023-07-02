package mx.gob.sedesol.basegestor.model.repositories.admin;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import mx.gob.sedesol.basegestor.model.entities.admin.CatTipoAsentamiento;

@Repository
public interface TipoAsentamientoRepo extends JpaRepository<CatTipoAsentamiento, Integer> {
	
	@Override
	@Query("SELECT ta FROM CatTipoAsentamiento ta ORDER BY ta.descripcion")
	List<CatTipoAsentamiento> findAll();

}
