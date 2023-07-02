package mx.gob.sedesol.basegestor.model.repositories.admin;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import mx.gob.sedesol.basegestor.model.entities.admin.TblTextoSistema;

@Repository
public interface TextoSistemaRepo extends JpaRepository<TblTextoSistema, String>, JpaSpecificationExecutor<TblTextoSistema> {
	
	@Query("SELECT ts FROM TblTextoSistema ts WHERE ts.funcionalidad.idFuncionalidad = ?1")
	List<TblTextoSistema> obtenerTextosPorFuncionalidad(Long idFuncionalidad);

}
