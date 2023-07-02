/**
 * 
 */
package mx.gob.sedesol.basegestor.model.repositories.gestion.aprendizaje;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import mx.gob.sedesol.basegestor.model.entities.gestionaprendizaje.RelUnidadOaAva;
import mx.gob.sedesol.basegestor.model.entities.gestionaprendizaje.TblRecursosOa;
@Repository
public interface RecursosOaRepo extends JpaRepository<TblRecursosOa, Integer>, JpaSpecificationExecutor<TblRecursosOa> {

	
	public List<TblRecursosOa> findTblRecursosOasByUnidadOaAva(RelUnidadOaAva relUnidadOaAva);
	
	
}
