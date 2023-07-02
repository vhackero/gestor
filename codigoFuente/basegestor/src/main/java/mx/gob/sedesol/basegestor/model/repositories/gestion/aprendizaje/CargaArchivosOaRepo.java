/**
 * 
 */
package mx.gob.sedesol.basegestor.model.repositories.gestion.aprendizaje;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import mx.gob.sedesol.basegestor.model.entities.gestionaprendizaje.CatClasificacionArchivoOa;
import mx.gob.sedesol.basegestor.model.entities.gestionaprendizaje.RelUnidadOaAva;
import mx.gob.sedesol.basegestor.model.entities.gestionaprendizaje.TblCargaArchivosOa;


public interface CargaArchivosOaRepo
		extends JpaRepository<TblCargaArchivosOa, Integer>, JpaSpecificationExecutor<TblCargaArchivosOa> {

	@Query("SELECT r FROM TblCargaArchivosOa r " + "WHERE r.unidadOaAva.id= :idUnidadOa")
	List<TblCargaArchivosOa> obtenerArchivosPorUnidadOA(@Param("idUnidadOa") Integer idUnidadOa);

	
	@Query("SELECT tblCargaArchivosOa FROM TblCargaArchivosOa tblCargaArchivosOa "
			+ " WHERE tblCargaArchivosOa.unidadOaAva =:unidadOaAva "
			+ " AND tblCargaArchivosOa.catClasificacionArchivoOa =:catClasificacionArchivoOa"
			+ " ORDER BY tblCargaArchivosOa.versionArchivo DESC")
	List<TblCargaArchivosOa> findByUnidadOaAvaAndCatClasificacionArchivo(
		@Param("unidadOaAva")RelUnidadOaAva unidadOaAva
		,@Param("catClasificacionArchivoOa")CatClasificacionArchivoOa catClasificacionArchivoOa	);
														           
	
	
	
}
