package mx.gob.sedesol.basegestor.model.repositories.gestionescolar;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import mx.gob.sedesol.basegestor.model.entities.gestionescolar.Acta; 

/**
 *  ACTAS
 * @author ITTIVA
 * 
 */
@Repository
public interface IActaRepository extends JpaRepository<Acta, Integer> {
	
	@Query("SELECT acta FROM Acta acta WHERE id_grupo =:idGrupo AND usuario_modifico =:idUser")
	public List<Acta> getActaByIdGrupoUser(@Param("idGrupo")Integer idGrupo,@Param("idUser") long idUser);
	
	@Query("SELECT acta FROM Acta acta WHERE id_grupo =:idGrupo")
	public List<Acta> getActaByIdGrupo(@Param("idGrupo")Integer idGrupo);
	
	@Modifying
	@Query(value = "DELETE  FROM tbl_actas WHERE id_acta = :idActa", nativeQuery = true)
	public void borraPorIdActa(@Param("idActa")Integer idActa);

}
