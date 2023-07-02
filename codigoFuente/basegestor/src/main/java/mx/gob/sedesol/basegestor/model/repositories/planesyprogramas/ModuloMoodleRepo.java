package mx.gob.sedesol.basegestor.model.repositories.planesyprogramas;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.MdlModulo;

@Repository
public interface ModuloMoodleRepo extends JpaRepository<MdlModulo, Integer>{

	/**
	 * 
	 * @param tipoModulo
	 * @return
	 */
	@Query("SELECT m FROM MdlModulo m WHERE m.tipoModulo= :tipoModulo")
	public List<MdlModulo> obtieneModulosPorTipo(@Param("tipoModulo")Integer tipoModulo);
}
