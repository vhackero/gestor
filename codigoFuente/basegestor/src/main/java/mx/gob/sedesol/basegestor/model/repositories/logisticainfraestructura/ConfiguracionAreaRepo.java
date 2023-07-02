package mx.gob.sedesol.basegestor.model.repositories.logisticainfraestructura;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import mx.gob.sedesol.basegestor.model.entities.logisticainfraestructura.TblConfiguracionArea;

@Repository
public interface ConfiguracionAreaRepo extends JpaRepository<TblConfiguracionArea,Integer>,JpaSpecificationExecutor<TblConfiguracionArea> {

}
