package mx.gob.sedesol.basegestor.model.repositories.logisticainfraestructura;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.gob.sedesol.basegestor.model.entities.logisticainfraestructura.TblPersonalizacionArea;

@Repository
public interface PersonalizacionAreaRepo extends JpaRepository<TblPersonalizacionArea,Integer> {

}
