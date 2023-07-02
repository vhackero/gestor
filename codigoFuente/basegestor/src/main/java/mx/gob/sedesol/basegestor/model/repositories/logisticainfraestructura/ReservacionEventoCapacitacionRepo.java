package mx.gob.sedesol.basegestor.model.repositories.logisticainfraestructura;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mx.gob.sedesol.basegestor.model.entities.logisticainfraestructura.TblReservacionEventoCapacitacion;

@Repository
public interface ReservacionEventoCapacitacionRepo extends JpaRepository<TblReservacionEventoCapacitacion,Integer> {

	@Query("SELECT r FROM TblReservacionEventoCapacitacion r "
			+ "join fetch r.catEstatusReservacion c "
			+ "WHERE r.idAreaSede = :id")
	public List<TblReservacionEventoCapacitacion> buscaReservacionesPorIdAreaSede(@Param("id") Integer id);
	
}
