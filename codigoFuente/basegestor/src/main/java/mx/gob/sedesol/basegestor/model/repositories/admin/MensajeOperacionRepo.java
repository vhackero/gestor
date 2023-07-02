package mx.gob.sedesol.basegestor.model.repositories.admin;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import mx.gob.sedesol.basegestor.model.entities.admin.RelMensajeOperacion;

@Repository
public interface MensajeOperacionRepo extends JpaRepository<RelMensajeOperacion, Long> {
	
	@Query("SELECT mo FROM RelMensajeOperacion mo "
			+ "WHERE mo.funcionalidad.idFuncionalidad = ?1")
	List<RelMensajeOperacion> obtenerMensajesPorOperacion(long idFuncionalidad);
	
	@Query("SELECT mo FROM RelMensajeOperacion mo "
			+ "WHERE mo.funcionalidad.clave = ?1 "
			+ "AND mo.activo = ?2")
	List<RelMensajeOperacion> obtenerMensajesPorOperacion(String clave, boolean activo);
	
	@Query("SELECT mo FROM RelMensajeOperacion mo "
			+ "WHERE mo.funcionalidad.clave = ?1 "
			+ "AND mo.activo = 1")
	RelMensajeOperacion obtenerMensajeActivoPorClaveFuncionalidad(String clave);
	

}
