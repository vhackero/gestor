package mx.gob.sedesol.basegestor.model.repositories.admin;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mx.gob.sedesol.basegestor.model.entities.admin.SsoElemento;

@Repository
public interface SsoElementoRepo extends JpaRepository<SsoElemento, Integer> {
	@Query("SELECT e FROM  SsoElemento e WHERE e.persona.usuario = :usuario ")
	List<SsoElemento> obtieneSsoElementoPorUsuario(@Param("usuario") String usuario);

//	@Modifying
//	@Query("DELETE FROM SsoElemento s s.id = :id")
//	void eliminaSsoElementoPorId(@Param("id") Integer id);
	
}
