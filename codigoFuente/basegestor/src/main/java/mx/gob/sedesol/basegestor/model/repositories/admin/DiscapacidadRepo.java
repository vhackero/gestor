package mx.gob.sedesol.basegestor.model.repositories.admin;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import mx.gob.sedesol.basegestor.model.entities.admin.CatDiscapacidad;

@Repository
public interface DiscapacidadRepo  extends JpaRepository<CatDiscapacidad, String> {

	@Override
	@Query("SELECT c FROM CatDiscapacidad c")
	List<CatDiscapacidad> findAll();

}
