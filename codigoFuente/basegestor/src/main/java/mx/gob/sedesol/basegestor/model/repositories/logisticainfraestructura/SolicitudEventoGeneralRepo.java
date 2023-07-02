package mx.gob.sedesol.basegestor.model.repositories.logisticainfraestructura;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mx.gob.sedesol.basegestor.model.entities.logisticainfraestructura.RelSolicitudEventoGeneral;

@Repository
public interface SolicitudEventoGeneralRepo extends JpaRepository<RelSolicitudEventoGeneral,Integer>, JpaSpecificationExecutor<RelSolicitudEventoGeneral> {
	
	@Query("SELECT s FROM RelSolicitudEventoGeneral s "
			+ "join fetch s.tblSolicitudReservacion "
			+ "WHERE s.idReservacionEG = :idReservacion")
	public List<RelSolicitudEventoGeneral> consultaSolicitudesPorIdReservacion(@Param("idReservacion") Integer idReservacion);
	
	@Query("SELECT s FROM RelSolicitudEventoGeneral s "
			+ "join fetch s.tblReservacionEventoGeneral "
			+ "WHERE s.idSolicitud = :idSolicitud")
	public List<RelSolicitudEventoGeneral> consultaReservacionesPorIdSolicitud(@Param("idSolicitud") Integer idSolicitud);
	
}
