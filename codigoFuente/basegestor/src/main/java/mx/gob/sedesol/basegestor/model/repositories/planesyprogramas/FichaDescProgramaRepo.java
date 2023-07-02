package mx.gob.sedesol.basegestor.model.repositories.planesyprogramas;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.TblFichaDescriptivaPrograma;

@Repository
public interface FichaDescProgramaRepo extends JpaRepository<TblFichaDescriptivaPrograma, Integer>,JpaSpecificationExecutor<TblFichaDescriptivaPrograma> {

	@Query("SELECT prg FROM TblFichaDescriptivaPrograma prg WHERE prg.catStatusPrograma.id = 1 ORDER BY prg.fechaActualizacion desc")
	public List<TblFichaDescriptivaPrograma> consultaUltimosProgramas( Pageable paginado);
	
	@Query("SELECT prg FROM TblFichaDescriptivaPrograma prg WHERE prg.catStatusPrograma.id = :idEstatus ORDER BY prg.fechaActualizacion desc")
	public List<TblFichaDescriptivaPrograma> consultaProgramasPorEstatus(@Param("idEstatus")Integer idEstatus);

	@Query("SELECT prg FROM TblFichaDescriptivaPrograma prg "
			//+ " JOIN FETCH prg.catStatusPrograma csp"
			+ " WHERE"
			+ "     prg.tipoCompetencia = :tipoCompetencia "
			+ " AND prg.ejeCapacitacion = :ejeCapacitacion "
			+ " AND prg.catStatusPrograma.id              = :idEstatusPrograma ")
	public List<TblFichaDescriptivaPrograma> consultarProgramasPorTCompYEjeCap(
			@Param("tipoCompetencia") Integer tipoCompetencia, 
			@Param("ejeCapacitacion") Integer ejeCapacitacion,
			@Param("idEstatusPrograma") Integer idEstatusPrograma);
	
	
	@Modifying
	@Query("DELETE FROM RelProgramaPersonalExterno rpe WHERE rpe.idPrograma = :idPrograma")
	public void eliminaRelProgPersonalExtPorIdPrograma(@Param("idPrograma") Integer idPrograma);
	
	@Modifying
	@Query("DELETE FROM RelProgramaResponsable rpr WHERE rpr.idPrograma = :idPrograma")
	public void eliminaRelProgResponsablesIdPrograma(@Param("idPrograma") Integer idPrograma);
	
	@Modifying
	@Query("DELETE FROM RelProgramaAutore rpa WHERE rpa.idPrograma = :idPrograma")
	public void eliminaRelProgAutorIdPrograma(@Param("idPrograma") Integer idPrograma);
	
	@Modifying
	@Query("DELETE FROM RelProgramaCompEspecifica rpc WHERE rpc.idPrograma = :idPrograma")
	public void eliminaRelProgCompEspecificasIdPrograma(@Param("idPrograma") Integer idPrograma);
	
	@Modifying
	@Query("DELETE FROM RelProgramaCargaHoraria rpc WHERE rpc.idPrograma = :idPrograma")
	public void eliminaRelProgDuracionIdPrograma(@Param("idPrograma") Integer idPrograma);
	
	@Query("SELECT COUNT(prg.idPrograma) FROM TblFichaDescriptivaPrograma prg WHERE prg.catStatusPrograma.id = :idEstatus ")
	public Integer totalProgramasActivosByEstatus(@Param("idEstatus")Integer idEstatus);
	
	@Query("SELECT COUNT(prg.idPrograma) FROM TblFichaDescriptivaPrograma prg")
	public Integer totalProgramas();
	
	@Query("SELECT COUNT(prg.idPrograma) FROM TblFichaDescriptivaPrograma prg WHERE prg.tipoCompetencia = :tipo")
	public Integer totalProgramasByTipo(@Param("tipo")Integer tipo);
	
	@Query("SELECT COUNT(prg.idPrograma) FROM TblFichaDescriptivaPrograma prg WHERE prg.catModalidad.id = :idModalidad")
	public Integer totalModalidadPrograma(@Param("idModalidad")Integer idModalidad);
	
	@Query("SELECT COUNT(prg.idPrograma) FROM TblFichaDescriptivaPrograma prg WHERE prg.catNivelEnsenanzaPrograma.id = :idNivelEnsenanza")
	public Integer totalNivelEnsenanzaById(@Param("idNivelEnsenanza")Integer idNivelEnsenanza);
	
	@Query("SELECT COUNT(prg.idPrograma) FROM TblFichaDescriptivaPrograma prg WHERE prg.catNivelConocimiento.id = :idNivelConocimiento")
	public Integer totalNivelConocimientoById(@Param("idNivelConocimiento")Integer idNivelConocimiento);
	
	@Query("SELECT COUNT(prg.idPrograma) FROM TblFichaDescriptivaPrograma prg WHERE prg.catTipoEventoEc.id = :idEvento")
	public Integer totalTipoEventoById(@Param("idEvento")Integer idEvento);
	
}