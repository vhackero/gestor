package mx.gob.sedesol.basegestor.model.repositories.gestionescolar;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import mx.gob.sedesol.basegestor.model.entities.gestionescolar.TblConvocatoria;



@Repository
public interface ConvocatoriaRepo extends JpaRepository<TblConvocatoria, Integer>, JpaSpecificationExecutor<TblConvocatoria> { 
	  
}