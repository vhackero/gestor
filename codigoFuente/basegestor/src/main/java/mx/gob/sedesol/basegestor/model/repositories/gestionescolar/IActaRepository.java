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
	/** Exactamente una asignación de rol, y debe ser estudiante (2). Alias participante: rgp. */
	String ALUMNO_EXCLUSIVO_SQL = "EXISTS (SELECT 1 FROM rel_persona_roles rpr "
			+ "WHERE rpr.id_persona = rgp.id_persona_participante AND rpr.id_rol = 2) "
			+ "AND (SELECT COUNT(*) FROM rel_persona_roles roles "
			+ "WHERE roles.id_persona = rgp.id_persona_participante) = 1 ";

	/** Comprobación preliminar en SQL. El PDF completo se valida sólo al seleccionarlo. */
	String DOCUMENTO_INVALIDO_SQL = "(ta.acta IS NULL OR OCTET_LENGTH(ta.acta) = 0 "
			+ "OR OCTET_LENGTH(ta.acta) > 10485760 "
			+ "OR LOCATE('%PDF-', LEFT(ta.acta, 1024)) = 0 "
			+ "OR LOCATE('%%EOF', RIGHT(ta.acta, 1024)) = 0)";
	String METADATOS_SQL = "SELECT ta.id_acta, ta.id_grupo, ta.fecha_cierre, ta.usuario_modifico, "
			+ "COALESCE(OCTET_LENGTH(ta.acta), 0), CASE WHEN " + DOCUMENTO_INVALIDO_SQL
			+ " THEN 0 ELSE 1 END FROM tbl_actas ta ";

	@Query("SELECT COUNT(a.idActa) FROM Acta a WHERE a.grupo = :idGrupo")
	long contarPorGrupo(@Param("idGrupo") Integer idGrupo);
	@Query(value = METADATOS_SQL + "WHERE ta.id_grupo = :idGrupo ORDER BY ta.id_acta", nativeQuery = true)
	List<Object[]> consultarMetadatos(@Param("idGrupo") Integer idGrupo);
	@Query(value = METADATOS_SQL + "WHERE ta.id_acta = :idActa", nativeQuery = true)
	List<Object[]> consultarMetadatosPorId(@Param("idActa") Integer idActa);
	@Query("SELECT a.blob FROM Acta a WHERE a.idActa = :idActa AND LENGTH(a.blob) <= 10485760")
	byte[] consultarArchivoAcotado(@Param("idActa") Integer idActa);
	@Modifying
	@Query(value = "UPDATE tbl_actas SET acta = :archivo, fecha_cierre = :fecha, usuario_modifico = :usuario "
			+ "WHERE id_acta = :idActa AND id_grupo = :idGrupo", nativeQuery = true)
	int reemplazarDocumento(@Param("idActa") Integer idActa, @Param("idGrupo") Integer idGrupo,
			@Param("archivo") byte[] archivo, @Param("fecha") java.util.Date fecha, @Param("usuario") Long usuario);
	
	@Query("SELECT acta FROM Acta acta WHERE id_grupo =:idGrupo AND usuario_modifico =:idUser")
	public List<Acta> getActaByIdGrupoUser(@Param("idGrupo")Integer idGrupo,@Param("idUser") long idUser);
	
	@Query("SELECT acta FROM Acta acta WHERE id_grupo =:idGrupo ORDER BY acta.idActa")
	public List<Acta> getActaByIdGrupo(@Param("idGrupo")Integer idGrupo);

	/** Serializa las correcciones de documentos/cierre de un mismo grupo. */
	@Query(value = "SELECT id FROM tbl_grupos WHERE id = :idGrupo FOR UPDATE", nativeQuery = true)
	Integer bloquearGrupo(@Param("idGrupo") Integer idGrupo);

	@Query(value = "SELECT tg.id, tg.acta_cerrada, "
			+ "COUNT(rgp.id), COUNT(rgp.calificacion_final), "
			+ "(SELECT COUNT(*) FROM tbl_actas ta WHERE ta.id_grupo = tg.id), "
			+ "(SELECT COUNT(*) FROM tbl_actas ta WHERE ta.id_grupo = tg.id AND " + DOCUMENTO_INVALIDO_SQL + ") "
			+ "FROM tbl_grupos tg LEFT JOIN rel_grupo_participante rgp ON rgp.id_grupo = tg.id "
			+ "AND " + ALUMNO_EXCLUSIVO_SQL
			+ "WHERE tg.id = :idGrupo GROUP BY tg.id, tg.acta_cerrada", nativeQuery = true)
	List<Object[]> consultarEstadoGrupo(@Param("idGrupo") Integer idGrupo);

	@Query(value = "SELECT DISTINCT rgp.id_persona_participante FROM rel_grupo_participante rgp "
			+ "WHERE rgp.id_grupo = :idGrupo AND " + ALUMNO_EXCLUSIVO_SQL, nativeQuery = true)
	List<Number> consultarIdsAlumnos(@Param("idGrupo") Integer idGrupo);
	
	@Modifying
	@Query(value = "DELETE  FROM tbl_actas WHERE id_acta = :idActa", nativeQuery = true)
	public void borraPorIdActa(@Param("idActa")Integer idActa);

}
