package mx.gob.sedesol.basegestor.model.repositories.encuestas;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mx.gob.sedesol.basegestor.model.entities.admin.RelUsuarioDatosLaborales;
import mx.gob.sedesol.basegestor.model.entities.encuestas.RelEncuestaEventoCapacitacion;

@Repository
public interface RelEncuestaEventoCapacitacionRepo extends JpaRepository<RelEncuestaEventoCapacitacion, Integer>,
		JpaSpecificationExecutor<RelEncuestaEventoCapacitacion> {
	@Query("SELECT reec from RelEncuestaEventoCapacitacion reec where reec.idEventoCapacitacion =:clEventoCapacitacion")
	List<RelEncuestaEventoCapacitacion> consultarEncuestasAsignadas(
			@Param("clEventoCapacitacion") Integer clEventoCapacitacion);
}
