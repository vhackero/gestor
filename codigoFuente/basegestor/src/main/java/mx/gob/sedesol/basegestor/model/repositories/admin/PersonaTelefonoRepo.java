package mx.gob.sedesol.basegestor.model.repositories.admin;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import mx.gob.sedesol.basegestor.model.entities.admin.RelPersonaTelefono;

@Repository
public interface PersonaTelefonoRepo extends JpaRepository<RelPersonaTelefono, Integer> {
	
	@Query("SELECT tp FROM RelPersonaTelefono tp "
			+ "WHERE tp.persona.idPersona = ?1 "
			+ "AND tp.tipoTelefono.idTipoTelefono = ?2")
	List<RelPersonaTelefono> obtenerTelefonosPersonaPorTipo(Long idPersona, Integer idTipoTelefono);

}
