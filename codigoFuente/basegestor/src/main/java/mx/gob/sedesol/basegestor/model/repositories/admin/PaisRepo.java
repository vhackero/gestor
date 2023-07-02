package mx.gob.sedesol.basegestor.model.repositories.admin;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import mx.gob.sedesol.basegestor.model.entities.admin.CatPais;

@Repository
public interface PaisRepo extends JpaRepository<CatPais, String> {
	
	@Override
	@Query("SELECT p FROM CatPais p ORDER BY p.nombre")
	List<CatPais> findAll();

}