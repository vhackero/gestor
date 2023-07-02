package mx.gob.sedesol.basegestor.model.repositories.admin;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import mx.gob.sedesol.basegestor.model.entities.admin.RelVariableMensajeOperacion;

@Repository
public interface VariableMensajeOperacionRepo extends JpaRepository<RelVariableMensajeOperacion, Integer> {

	@Query("SELECT vmo FROM RelVariableMensajeOperacion vmo "
			+ "WHERE vmo.funcionalidad.idFuncionalidad = ?1 "
			+ "ORDER BY vmo.descripcion")
	List<RelVariableMensajeOperacion> obtenerVariablesPorOperacion(long idFuncionalidad);
}
