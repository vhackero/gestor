package mx.gob.sedesol.basegestor.model.repositories.admin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.gob.sedesol.basegestor.model.entities.admin.TblPersonaSige;

@Repository
public interface PersonaSigeRepo extends JpaRepository<TblPersonaSige, Long> {
	
}
