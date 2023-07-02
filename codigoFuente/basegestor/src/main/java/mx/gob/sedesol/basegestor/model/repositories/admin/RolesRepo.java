package mx.gob.sedesol.basegestor.model.repositories.admin;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mx.gob.sedesol.basegestor.model.entities.admin.CatRol;

@Repository
public interface RolesRepo extends JpaRepository<CatRol, Integer>, JpaSpecificationExecutor<CatRol> {

	@Query("SELECT r FROM CatRol r WHERE r.clave = :clave")
	public CatRol buscarRolPorClave(@Param("clave") String clave);

	@Query("SELECT r FROM CatRol r " + " WHERE r.clave = :clave " + " AND r.idRol <> :idRol")
	public List<CatRol> buscarRolPorClaveId(@Param("clave") String clave, @Param("idRol") Integer idRol);
	
}
