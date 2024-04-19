package mx.gob.sedesol.basegestor.model.repositories.gestionescolar;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mx.gob.sedesol.basegestor.model.entities.gestionescolar.TblInscripcionResumen;



@Repository
public interface InscripcionResumenRepo extends JpaRepository<TblInscripcionResumen, Integer>, JpaSpecificationExecutor<TblInscripcionResumen> { 
	

	@Query("SELECT ins FROM TblInscripcionResumen ins "
			+ " WHERE ins.programaEducativo in (:programas)"
//			+ " AND ins.noEstudiantes > 0 "
			+ " AND ins.bloque = 1")
	public List<TblInscripcionResumen> getInscripcionResumenByProgramaEducativo(@Param("programas")List<String> programas);
	
	@Query("SELECT ins FROM TblInscripcionResumen ins "
			+ " WHERE ins.plan.idPlan in (:planes)"
//			+ " AND ins.noEstudiantes > 0 "
			+ " AND ins.semestre = :semestre "
			+ " AND ins.bloque = :bloque")
	public List<TblInscripcionResumen> getInscripcionResumenByIdPlanesSemestreBloque(@Param("planes")List<Integer> planes, @Param("semestre") Integer semestre, @Param("bloque") String bloque);
	
}
