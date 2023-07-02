package mx.gob.sedesol.basegestor.model.repositories.gestion.aprendizaje;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.gob.sedesol.basegestor.model.entities.gestionaprendizaje.TblRutaRespaldo;


@Repository
public interface RutaRespaldosRepo extends JpaRepository<TblRutaRespaldo,Integer> {

	
	public TblRutaRespaldo findByClave(String clave);
	
}
