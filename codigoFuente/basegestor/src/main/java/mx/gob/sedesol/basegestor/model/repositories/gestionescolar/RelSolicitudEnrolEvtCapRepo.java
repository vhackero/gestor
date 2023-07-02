package mx.gob.sedesol.basegestor.model.repositories.gestionescolar;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.RelSolicitudEnrolamientoEvtCap;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.RelSolicitudEnrolamientoEvtCapPK;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.TblEvento;
import mx.gob.sedesol.basegestor.model.entities.logisticainfraestructura.RelSolicitudEventoCapacitacionPK;

@Repository
public interface RelSolicitudEnrolEvtCapRepo extends JpaRepository<RelSolicitudEnrolamientoEvtCap, RelSolicitudEnrolamientoEvtCapPK>,
		JpaSpecificationExecutor<RelSolicitudEnrolamientoEvtCap> {

	@Query("SELECT eventoCap FROM RelSolicitudEnrolamientoEvtCap solicitud " + " JOIN  solicitud.tblPersona persona"
			+ " JOIN  solicitud.tblEvento eventoCap" + " WHERE " + "	solicitud.activo =:esActivo  "
			+ "	AND persona.idPersona =:idPersona ")
	public List<TblEvento> obtenerSolicitudesEnrolamientoPorIdPersona(@Param("idPersona") Long idPersona,
			@Param("esActivo") Boolean esActivo);

	@Query("SELECT eventoCap FROM RelSolicitudEnrolamientoEvtCap solicitud " + " JOIN  solicitud.tblPersona persona"
			+ " JOIN  solicitud.tblEvento eventoCap" + " WHERE " + "	solicitud.activo =:esActivo  "
			+ "	AND persona.idPersona =:idPersona "
			+ " AND eventoCap.fichaDescriptivaPrograma.idPrograma = :idPrograma ")
	public List<TblEvento> obtenerSolicitudesPorIdPersonaPrograma(@Param("idPersona") Long idPersona,
			@Param("esActivo") Boolean esActivo, @Param("idPrograma") Integer idPrograma);

}
