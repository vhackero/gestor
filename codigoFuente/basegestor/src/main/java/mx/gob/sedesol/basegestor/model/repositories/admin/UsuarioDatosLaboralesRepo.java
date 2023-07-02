package mx.gob.sedesol.basegestor.model.repositories.admin;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import mx.gob.sedesol.basegestor.model.entities.admin.RelUsuarioDatosLaborales;

@Repository
public interface UsuarioDatosLaboralesRepo extends JpaRepository<RelUsuarioDatosLaborales, Long>, JpaSpecificationExecutor<RelUsuarioDatosLaborales> {
	
	@Query("SELECT dl FROM RelUsuarioDatosLaborales dl "
			+ "WHERE dl.persona.idPersona = ?1")
	List<RelUsuarioDatosLaborales> obtenerDatosLaboralesPorPersona(Long idPersona);

}
