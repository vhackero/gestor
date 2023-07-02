package mx.gob.sedesol.basegestor.model.repositories.admin;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mx.gob.sedesol.basegestor.model.entities.admin.RelPersonaRol;

@Repository
public interface PersonaRolesRepo extends JpaRepository<RelPersonaRol, Long> {

	/**
	 * Implementar metodos necesarios
	 */
	
	@Query("SELECT rpr FROM  RelPersonaRol rpr "
			+ "JOIN rpr.persona p "
			+ "WHERE p.usuario = :usuario")
	List<RelPersonaRol> obtieneRelPersonaRoles(@Param("usuario") String usuario);

	@Query("SELECT pr FROM  RelPersonaRol pr " + "WHERE pr.rol.idRol = :idRol ")
	List<RelPersonaRol> obtieneRelPersonaRolesPorRol(@Param("idRol") Integer idRol);
	
	@Modifying
	@Query("DELETE FROM RelPersonaRol rpr WHERE rpr.idPersonaRol = :idPersonaRol")
	void eliminaRelPersonaRolById(@Param("idPersonaRol") Long idPersonaRol);
}

