package mx.gob.sedesol.basegestor.model.repositories.gestionescolar;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mx.gob.sedesol.basegestor.model.entities.gestionescolar.TblInscripcion;



@Repository
public interface InscripcionRepo extends JpaRepository<TblInscripcion, Integer>, JpaSpecificationExecutor<TblInscripcion> { 
	  
	@Query("SELECT ins FROM TblInscripcion ins "
			+ "JOIN ins.plan p "
			+ "WHERE p.idPlan =:idPlan")
	public List<TblInscripcion> getInscripcionesByIdPlan(@Param("idPlan")Integer idPlan);
	
	@Query(" SELECT ins FROM TblInscripcion ins "
		 	+ "JOIN ins.fichaDescriptivaPrograma p "
			+ " WHERE ins.programa in (:programas) "
			+ " ORDER BY p.idPrograma, ins.groupbase ")
	public List<TblInscripcion> getInscripcionesByProgramasEducativos(@Param("programas")List<String> programas);
	
}