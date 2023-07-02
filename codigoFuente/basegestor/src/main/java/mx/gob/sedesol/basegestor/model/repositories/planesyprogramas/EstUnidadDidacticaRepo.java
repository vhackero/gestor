package mx.gob.sedesol.basegestor.model.repositories.planesyprogramas;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.DetEstUnidadDidactica;

@Repository
public interface EstUnidadDidacticaRepo extends JpaRepository<DetEstUnidadDidactica,Integer>{

	@Modifying(clearAutomatically = true)
	@Query("DELETE FROM RelUniDidacticaMaterial rud WHERE rud.idUnidadDidactica = :idUnidadDidactica")
	public void eliminaRelUnidDidMaterialByIdUnidDid(@Param("idUnidadDidactica") Integer idUnidadDidactica);
	
	@Modifying(clearAutomatically = true)
	@Query("DELETE FROM RelUnidDidacticaTposComp rtc WHERE rtc.idUnidadDidactica = :idUnidadDidactica")
	public void eliminaRelUnidDidTpoCompByIdUnidDid(@Param("idUnidadDidactica") Integer idUnidadDidactica);
	
}
