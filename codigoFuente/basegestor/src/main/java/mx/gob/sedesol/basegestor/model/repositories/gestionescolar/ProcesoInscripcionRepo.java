package mx.gob.sedesol.basegestor.model.repositories.gestionescolar;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mx.gob.sedesol.basegestor.model.entities.gestionescolar.TblProcesoInscripcion;

@Repository
public interface ProcesoInscripcionRepo  extends JpaRepository<TblProcesoInscripcion, Integer>, JpaSpecificationExecutor<TblProcesoInscripcion> { 
	
	@Query("SELECT pi FROM TblProcesoInscripcion pi "
			+ " WHERE pi.convocatoria.id = :idConvocatoria")
	public List<TblProcesoInscripcion> obtenerProcesosInscripcionPorConvocatoriaId(@Param("idConvocatoria")Integer idConvocatoria);
	
}
