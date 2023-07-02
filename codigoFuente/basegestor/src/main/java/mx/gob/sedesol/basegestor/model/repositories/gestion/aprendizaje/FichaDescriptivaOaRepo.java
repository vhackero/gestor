package mx.gob.sedesol.basegestor.model.repositories.gestion.aprendizaje;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import mx.gob.sedesol.basegestor.model.entities.gestionaprendizaje.TblFichaDescriptivaObjetoAprendizaje;

@Repository
public interface FichaDescriptivaOaRepo extends 
							JpaRepository<TblFichaDescriptivaObjetoAprendizaje, Integer>, 
							JpaSpecificationExecutor<TblFichaDescriptivaObjetoAprendizaje> {

}
