package mx.gob.sedesol.basegestor.model.repositories.gestionescolar;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mx.gob.sedesol.basegestor.model.entities.gestionescolar.TblGrupo;

@Repository
public interface GrupoRepo extends JpaRepository<TblGrupo, Integer>, JpaSpecificationExecutor<TblGrupo> { 
	
	@Query("SELECT grupo FROM TblGrupo grupo "
			+ "JOIN grupo.evento evt "
			+ "WHERE evt.idEvento =:idEvento")
	public List<TblGrupo> getGruposByEvento(@Param("idEvento")Integer idVento);
	
	@Query("SELECT grupo FROM TblGrupo grupo WHERE grupo.evento.idEvento = :idEvento "
			+ "AND grupo.nombre = :nombreGrupo")
	public List<TblGrupo> getGruposByEventoAndNombre(@Param("idEvento") Integer idVento, 
			@Param("nombreGrupo") String nombreGrupo);
	
	
	@Query("SELECT grupo FROM TblGrupo grupo "
			+ " JOIN grupo.evento evt "
			+ " WHERE evt.idEvento IN (:idEventosCapacitacion) "
			+ " AND ((grupo.actaCerrada = true AND evt.fechaFinal <:fechaActual) "
			+ " 	OR (grupo.actaCerrada = false AND evt.fechaFinal >:fechaActual)) ")
	public List<TblGrupo> getGruposByEventoIdEventos(@Param("idEventosCapacitacion")List<Integer> idEventos,			
			@Param("fechaActual")Date fechaActual);

	@Query("SELECT grupo FROM TblGrupo grupo "
			+ "JOIN grupo.evento evt "
			+ "WHERE evt.idEvento IN (:idEvento)")
	public List<TblGrupo> getGruposByEventos(@Param("idEvento")List<Integer> idVento);
	


}




