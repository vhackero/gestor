package mx.gob.sedesol.basegestor.model.repositories.cronscheduler;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import mx.gob.sedesol.basegestor.model.entities.admin.TblCfgCron;



@Repository
public interface ConfiguracionCronRepo 
						extends JpaRepository<TblCfgCron, Integer>,
								JpaSpecificationExecutor<TblCfgCron> {  
	
	
	
	@Query("SELECT c FROM TblCfgCron c WHERE"
			+ " c.clave =:clave")
	public TblCfgCron obtenerCronProClave(@Param("clave")String clave);
	
	
	
	 
	
	

}
