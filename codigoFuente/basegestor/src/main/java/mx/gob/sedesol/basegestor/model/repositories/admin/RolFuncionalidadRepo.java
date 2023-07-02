package mx.gob.sedesol.basegestor.model.repositories.admin;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import mx.gob.sedesol.basegestor.model.entities.admin.RelRolFuncionalidad;

@Repository
public interface RolFuncionalidadRepo extends JpaRepository<RelRolFuncionalidad, Long> {
	
	@Query("SELECT rf FROM RelRolFuncionalidad rf "
			+ "JOIN FETCH rf.funcionalidad f "
			+ "WHERE rf.rol.idRol = ?1")
	List<RelRolFuncionalidad> obtenerFuncionalidadesRol(int idRol);

}
