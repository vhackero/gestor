package mx.gob.sedesol.basegestor.model.repositories.logisticainfraestructura;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mx.gob.sedesol.basegestor.model.entities.admin.TblDomiciliosPersona;
import mx.gob.sedesol.basegestor.model.entities.logisticainfraestructura.RelSolicitudEventoCapacitacion;

@Repository
public interface SolicitudEventoCapacitacionRepo extends JpaRepository<RelSolicitudEventoCapacitacion, Integer>,
		JpaSpecificationExecutor<RelSolicitudEventoCapacitacion> {

	@Query("SELECT s FROM RelSolicitudEventoCapacitacion s " + "join fetch s.tblSolicitudReservacion "
			+ "WHERE s.idReservacionEC = :idReservacion")
	public List<RelSolicitudEventoCapacitacion> consultaSolicitudesPorIdReservacion(
			@Param("idReservacion") Integer idReservacion);

	@Query("SELECT s FROM RelSolicitudEventoCapacitacion s " + "join fetch s.tblReservacionEventoCapacitacion "
			+ "WHERE s.idSolicitud = :idSolicitud")
	public List<RelSolicitudEventoCapacitacion> consultaReservacionesPorIdSolicitud(
			@Param("idSolicitud") Integer idSolicitud);
}
