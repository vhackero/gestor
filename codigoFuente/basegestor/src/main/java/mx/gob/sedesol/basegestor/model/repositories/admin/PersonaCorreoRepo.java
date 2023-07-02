package mx.gob.sedesol.basegestor.model.repositories.admin;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mx.gob.sedesol.basegestor.model.entities.admin.RelPersonaCorreo;

@Repository
public interface PersonaCorreoRepo extends JpaRepository<RelPersonaCorreo, Integer> {

	@Query("SELECT pc FROM RelPersonaCorreo pc WHERE pc.correoElectronico = :correo ")
	RelPersonaCorreo obtienePersonaCorreoElectronico(@Param("correo") String correo);
	
	@Query("SELECT cp FROM RelPersonaCorreo cp "
			+ "JOIN FETCH cp.persona p "
			+ "JOIN FETCH cp.tipoCorreo tc "
			+ "WHERE p.idPersona = ?1 "
			+ "AND tc.idTipoCorreo = ?2")
	List<RelPersonaCorreo> obtenerCorreoPersonaPorTipo(Long idPersona, Integer idTipoCorreo);
}
