package mx.gob.sedesol.basegestor.model.repositories.admin;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import mx.gob.sedesol.basegestor.model.entities.admin.TblPlantilla;

@Repository
public interface PlantillaRepo extends JpaRepository<TblPlantilla, Long> {
	
	@Query("SELECT p FROM TblPlantilla p "
			+ "WHERE p.tipoDocumento = ?1")
	List<TblPlantilla> obtenerPlantillasPorTipoDocumento(Integer tipoDocumento);
	
	@Query("SELECT p FROM TblPlantilla p "
			+ " WHERE p.tipoDocumento = ?1 "
			+ " AND p.activo = ?2")
	List<TblPlantilla> obtenerPlantillasPorTipoDocumentoEstatus(Integer tipoDocumento, boolean activo);
}
