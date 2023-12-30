package mx.gob.sedesol.basegestor.model.repositories.admin;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import mx.gob.sedesol.basegestor.model.entities.admin.CatLenguajeIndigena;

@Repository
public interface LenguajeIndigenaRepo extends JpaRepository<CatLenguajeIndigena, String> {

	@Override
	@Query("SELECT c FROM CatLenguajeIndigena c")
	List<CatLenguajeIndigena> findAll();

	
}
