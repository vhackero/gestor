package mx.gob.sedesol.basegestor.model.repositories.gestion.aprendizaje;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import mx.gob.sedesol.basegestor.model.entities.gestionaprendizaje.TblAmbienteVirtualAprendizaje;


@Repository
public interface AmbienteVirtualApRepo extends 
	JpaRepository<TblAmbienteVirtualAprendizaje, Integer>, 
	JpaSpecificationExecutor<TblAmbienteVirtualAprendizaje> {

	@Query("SELECT ava FROM TblAmbienteVirtualAprendizaje ava "
			 + " JOIN FETCH  ava.eventoCapacitacion ev " 
			 + " JOIN FETCH  ev.fichaDescriptivaPrograma pro " 
			 + " JOIN FETCH  ava.catEstadoAva est  WHERE ava.catEstadoAva.id = :idCatEstatusAva ")
	public List<TblAmbienteVirtualAprendizaje> consultaAVAPorEstatus(@Param("idCatEstatusAva")Integer idCatEstatusAva);
	
	
	/*@Query("SELECT ava FROM TblAmbienteVirtualAprendizaje ava "
			 + "  JOIN FETCH  ava.personaResponsabilidades resp "
			 + "  JOIN FETCH  resp.tblPersona per " 
			 + "  JOIN FETCH  resp.catTipoResponsabilidadEc res   WHERE ava.id = :idAva ")
	public List<TblAmbienteVirtualAprendizaje> consultaAVAResponsable(@Param("idAva")Integer idAva);
	*/
	@Query("SELECT ava FROM TblAmbienteVirtualAprendizaje ava WHERE ava.id =:idAva ")	
	public TblAmbienteVirtualAprendizaje findById(@Param("idAva")Integer idAva);
	
	@Query("SELECT ava FROM TblAmbienteVirtualAprendizaje ava "		
			+ " WHERE ava.eventoCapacitacion.id = :idEvento ")
	public TblAmbienteVirtualAprendizaje obtenerAVAPorEvento(@Param("idEvento")Integer idEvento);
	
	//metodo matriculacion
	@Query("SELECT ava FROM TblAmbienteVirtualAprendizaje ava "
			+ "WHERE ava.eventoCapacitacion.id = :idEvento")
	public TblAmbienteVirtualAprendizaje obtenerAVAPorIdEvento(@Param("idEvento")Integer idEvento);
	
	
	/**************************************************************************************************/
	
	@Query("SELECT ava FROM TblAmbienteVirtualAprendizaje        ava "
			+ " JOIN FETCH ava.eventoCapacitacion                eventoCap "
			+ " JOIN FETCH ava.catEstadoAva                      edoAva"
			+ " JOIN FETCH eventoCap.catModalidadPlanPrograma    modEvtCap"
			+ " JOIN FETCH eventoCap.catEstadoEventoCapacitacion edoEvtCap"			
			+ " WHERE edoAva.id    =:estatusAva"
			+ " AND   modEvtCap.id IN(:modalidadEvtCapList) "
			+ " AND   edoEvtCap.id =:estatusEvtCap "
			+ " ORDER BY ava.fechaActualizacion DESC")
	public List<TblAmbienteVirtualAprendizaje> consultarAvaPorEstatusYEventoCapacitacion(
				@Param("estatusAva")      Integer estatusAva ,
				@Param("modalidadEvtCapList") List<Integer> modalidadEvtCapList,
				@Param("estatusEvtCap")   Integer estatusEvtCap );
	
	
	@Query("SELECT ava FROM TblAmbienteVirtualAprendizaje        ava"
			+ "	JOIN FETCH ava.eventoCapacitacion                eventoCap "
			+ " JOIN FETCH eventoCap.fichaDescriptivaPrograma    programa"
			+ " JOIN FETCH eventoCap.catModalidadPlanPrograma    modEvtCap "
			+ " JOIN FETCH eventoCap.catEstadoEventoCapacitacion edoEvtCap "
			+ " WHERE   modEvtCap.id             IN(:modalidadEvtCapList) "
			+ " AND     edoEvtCap.id             IN(:estatusEvtCap) "
			+ " AND     programa.tipoCompetencia =:idTipoCom"
			+ " AND     programa.ejeCapacitacion =:idEjeCapacitacion"
			
			)	
	public List<TblAmbienteVirtualAprendizaje> consultarAvasPorTipoCompetenciaEjeCapacitacion(
			@Param("modalidadEvtCapList") List<Integer> modalidadEvtCapList,
			@Param("estatusEvtCap")       List<Integer> estatusEvtCap,
			@Param("idTipoCom")           Integer idTipoCom,
			@Param("idEjeCapacitacion")   Integer idEjeCapacitacion);
	
	@Query("SELECT ava FROM TblAmbienteVirtualAprendizaje        ava"
			+ "	JOIN FETCH ava.eventoCapacitacion                eventoCap "
			+ " JOIN FETCH eventoCap.fichaDescriptivaPrograma    programa"
			+ " JOIN FETCH eventoCap.catModalidadPlanPrograma    modEvtCap "
			+ " JOIN FETCH eventoCap.catEstadoEventoCapacitacion edoEvtCap "
			+ " WHERE   modEvtCap.id             IN(:modalidadEvtCapList) "
			+ " AND     edoEvtCap.id             IN(:estatusEvtCap) "
			+ " AND     programa.ejeCapacitacion =:idEjeCapacitacion"
			
			)	
	public List<TblAmbienteVirtualAprendizaje> consultarAvasPorEjeCapacitacion(
			@Param("modalidadEvtCapList") List<Integer> modalidadEvtCapList,
			@Param("estatusEvtCap")       List<Integer> estatusEvtCap,
			@Param("idEjeCapacitacion")   Integer idEjeCapacitacion);
	
	@Query("SELECT ava FROM TblAmbienteVirtualAprendizaje ava"
			+ " JOIN ava.eventoCapacitacion  evt "
			+ " WHERE evt.id IN(:idEventos) ")	
	public List<TblAmbienteVirtualAprendizaje> obtenerAVAsPorIdEventos(@Param("idEventos") List<Integer> idEventos);
	
	
	
	@Query("SELECT ava FROM TblAmbienteVirtualAprendizaje ava "
			+" JOIN ava.eventoCapacitacion evt "
			+" WHERE ava.activo =:estatusActual "
			+" AND  evt.catEstadoEventoCapacitacion.id =:idEdoEvtCapacitacion "
			+" AND  ava.catEstadoAva.id =:idCatEdoAva "
			+" AND  evt.fechaInicial >:fechaVencimiento "
			+" AND  evt.fechaFinal >:fechaVencimiento ")	
	public List<TblAmbienteVirtualAprendizaje> obtenerAvasPorActivar
		(@Param("fechaVencimiento") Date fechaVencimiento, 
					@Param("estatusActual")Boolean estatusActual,
						@Param("idEdoEvtCapacitacion") Integer idEdoEvtCapacitacion,
							@Param("idCatEdoAva")Integer idCatEdoAva);
	
	
	
	
	
}
