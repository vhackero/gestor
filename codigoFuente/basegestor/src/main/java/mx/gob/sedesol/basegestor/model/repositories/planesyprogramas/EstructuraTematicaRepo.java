package mx.gob.sedesol.basegestor.model.repositories.planesyprogramas;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.TblEstructuraTematica;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.TblMallaCurricular;

@Repository
public interface EstructuraTematicaRepo extends JpaRepository<TblEstructuraTematica,Integer> {

	@Query("select et from TblEstructuraTematica et where et.idEstructuraTematica = :id_est_tematica ")
	public List<TblMallaCurricular> buscarMallaCurricularPorId(@Param("id_est_tematica") Integer id_est_tematica);
	
	@Query("SELECT et FROM TblEstructuraTematica et WHERE et.tblFichaDescriptivaPrograma.idPrograma = :idPrograma ")
	public TblEstructuraTematica buscarEstTematicaPorIdPrograma(@Param("idPrograma") Integer idPrograma);
}
