/**
 * 
 */
package mx.gob.sedesol.basegestor.model.repositories.encuestas;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mx.gob.sedesol.basegestor.model.entities.encuestas.CatEncuestaTipo;
import mx.gob.sedesol.basegestor.model.entities.encuestas.TblEncuesta;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.TblEvento;

/**
 * @author jhcortes
 *
 */
@Repository
public interface CatalogoEncuestaRepo
		extends JpaRepository<TblEncuesta, Integer>, JpaSpecificationExecutor<TblEncuesta> {

	@Query("SELECT e FROM TblEncuesta e " + "WHERE " + "e.id = :idEncuesta")
	public TblEncuesta obtenerEncuestaPorId(@Param("idEncuesta") Integer idEncuesta);

	@Query("SELECT t FROM TblEncuesta t WHERE t.nombre = :nombre and t.clave = :clave ORDER BY t.encuestaEstatus asc , t.fechaActualizacion desc")
	TblEncuesta buscarPorParametros(@Param("nombre") String nombre, @Param("clave") String clave);

	/**
	 * Realiza un count a la tabla de encuestas, donde la combinacion de tipo de
	 * encuesta, dirigido a, contexto y estatus encuesta, dan paso a generar un
	 * numero consecutivo para la clave de la encuesta.
	 * 
	 * @param idTipoEnc
	 * @param idObjEnc
	 * @param idContextEnc
	 * @param idEstatusEnc
	 * @return
	 */
	@Query("SELECT COUNT(tblEnc) FROM TblEncuesta tblEnc , CatEncuestaTipo catNivel WHERE tblEnc.encuestaTipo.id = :idTipoEnc AND tblEnc.encuestaObjetivo.id = :idObjEnc AND catNivel.contexto.id = :idContextEnc AND tblEnc.encuestaTipo.id  = catNivel.id AND tblEnc.encuestaEstatus.id = :idEstatusEnc ORDER BY tblEnc.encuestaEstatus asc , tblEnc.fechaActualizacion desc ")
	Integer countCombinacion(@Param("idTipoEnc") Integer idTipoEnc, @Param("idObjEnc") Integer idObjEnc,
			@Param("idContextEnc") Integer idContextEnc, @Param("idEstatusEnc") Integer idEstatusEnc);

	@Query("SELECT t FROM TblEncuesta t ORDER BY t.encuestaEstatus asc , t.fechaActualizacion desc")
	List<TblEncuesta> buscarTodosOrdenEstatus();

	@Query("SELECT t FROM TblEncuesta t where t.encuestaTipo.id =:clTipoEncuesta ORDER BY t.encuestaEstatus asc , t.fechaActualizacion desc")
	List<TblEncuesta> buscarTipoEncuesta(@Param("clTipoEncuesta") Integer clTipoEncuesta);

	@Query("SELECT encuesta from TblEncuesta encuesta join encuesta.encuestaTipo encuestaTipo join encuestaTipo.contexto encuestaContexto where encuesta.encuestaEstatus.id=4 AND  encuestaContexto.id =:clContexto ORDER BY encuesta.encuestaEstatus asc , encuesta.fechaActualizacion desc ")
	List<TblEncuesta> buscarEncuestaContexto(@Param("clContexto") Integer clContexto);

	@Query("SELECT encuesta FROM TblEncuesta encuesta " + "JOIN encuesta.encuestaTipo tipo "
			+ "JOIN tipo.contexto contexto " + "JOIN encuesta.encuestaEstatus estatus "
			+ "JOIN encuesta.encuestaObjetivo dirigido " + "WHERE tipo.id = 1 AND " + "contexto.id = 1 AND "
			+ "estatus.id = 4 AND " + "dirigido.id = 1")
	List<TblEncuesta> buscarEncPorKirkReaccionAlumnoPublicar();
}
