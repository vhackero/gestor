package mx.gob.sedesol.basegestor.model.repositories.admin;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import mx.gob.sedesol.basegestor.model.entities.admin.RelLoteUsuario;
import mx.gob.sedesol.basegestor.model.entities.admin.RelLoteUsuarioPK;

@Repository
public interface LoteUsuarioRepo extends JpaRepository<RelLoteUsuario, RelLoteUsuarioPK> {
	
	@Query("SELECT lu FROM RelLoteUsuario lu "
			+ "WHERE lu.idLote = ?1")
	List<RelLoteUsuario> usuariosPorLote(Integer idLote);

}
