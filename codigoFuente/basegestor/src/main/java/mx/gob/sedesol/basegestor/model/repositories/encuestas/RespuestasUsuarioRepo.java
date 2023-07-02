package mx.gob.sedesol.basegestor.model.repositories.encuestas;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import mx.gob.sedesol.basegestor.model.entities.encuestas.RelRespuestaUsuario;

/**
 * Created by jhcortes on 15/12/16.
 */
@Repository
public interface RespuestasUsuarioRepo  extends JpaRepository<RelRespuestaUsuario, Integer>, JpaSpecificationExecutor<RelRespuestaUsuario> {

    @Query("SELECT a FROM RelRespuestaUsuario a WHERE a.idUsuario = ?1 AND a.idEncuesta = ?2")
    List<RelRespuestaUsuario> buscarRespuestasEncuestaUsuario(Integer idUsuario, Integer idEncuesta);
}
