package mx.gob.sedesol.basegestor.model.repositories.admin;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import mx.gob.sedesol.basegestor.model.entities.admin.TblPersonaSige;

@Repository
public interface PersonaSigeRepo extends JpaRepository<TblPersonaSige, Long> {
	
	//@Query("SELECT personas FROM TblPersonaSige personas WHERE personas.curp NOT IN (SELECT persona.curp FROM TblPersona persona) AND (personas.correoInstitucional != '' OR personas.correoInstitucional IS NULL)")
	@Query(value = "SELECT t.* FROM tbl_persona_sige t JOIN (SELECT MAX(id_persona_sige) AS id_persona_sige FROM tbl_persona_sige WHERE curp_sige NOT IN (SELECT sso_curp FROM tbl_persona) AND (correo_institucional_sige != \"\" OR correo_institucional_sige IS NULL) GROUP BY correo_institucional_sige) AS subquery ON t.id_persona_sige = subquery.id_persona_sige", nativeQuery = true)
    List<TblPersonaSige> obtenetPersonasNoRegistradas();

	
}
