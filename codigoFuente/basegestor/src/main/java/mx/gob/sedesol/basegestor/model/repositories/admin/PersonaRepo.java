package mx.gob.sedesol.basegestor.model.repositories.admin;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import mx.gob.sedesol.basegestor.model.entities.admin.TblPersona;

@Repository
public interface PersonaRepo extends JpaRepository<TblPersona,Long>, JpaSpecificationExecutor<TblPersona>{

	@Query("select p from TblPersona p where p.usuario=?1 and p.contrasenia=?2")
	TblPersona autenticacionUsuario(String usuario, String contrasenia);
	
	@Query("select p from TblPersona p "
			+ "JOIN FETCH p.relPersonaRoles rp "
			+ "where p.usuario=?1")
	TblPersona obtienePersonaPorNombreUsuario(String usuario);
	
	@Query("SELECT p FROM TblPersona p WHERE p.usuario = ?1 AND p.idPersona <> ?2")
	List<TblPersona> obtienePersonaPorNombreUsuario(String usuario, Long idPersona);
	
	@Query("SELECT p FROM TblPersona p WHERE p.curp = ?1 AND p.idPersona <> ?2")
	List<TblPersona> obtienePersonaPorCurp(String curp, Long idPersona);
	
	@Query("SELECT p FROM TblPersona p "
			+ "JOIN FETCH p.domiciliosPersona d "
			+ "JOIN FETCH d.asentamiento a "	
			+ "WHERE a.codigoPostal like %?1%")
	List<TblPersona> obtenerPersonaPorCodigoPostal(String codigoPostal);
	
}
