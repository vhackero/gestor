package mx.gob.sedesol.basegestor.model.repositories.gestion.aprendizaje;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.gob.sedesol.basegestor.model.entities.gestionaprendizaje.TblUsuariosFtp;

@Repository 
public interface UsuariosFtpRepo extends JpaRepository<TblUsuariosFtp, Integer> {

	
	public TblUsuariosFtp findByClave(String clave);
	
	
}
