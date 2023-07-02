package mx.gob.sedesol.basegestor.model.repositories.logisticainfraestructura;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.gob.sedesol.basegestor.model.entities.logisticainfraestructura.TblSolicitudReservacion;

@Repository
public interface SolicitudReservacionRepo extends JpaRepository<TblSolicitudReservacion,Integer> {

}
