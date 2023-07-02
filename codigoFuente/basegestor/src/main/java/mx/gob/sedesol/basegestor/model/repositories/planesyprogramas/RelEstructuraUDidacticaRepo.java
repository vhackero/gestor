package mx.gob.sedesol.basegestor.model.repositories.planesyprogramas;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.RelEstructuraUnidadDidactica;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.RelEstructuraUnidadDidacticaPK;

@Repository
public interface RelEstructuraUDidacticaRepo extends JpaRepository<RelEstructuraUnidadDidactica, RelEstructuraUnidadDidacticaPK> {

	@Query("SELECT  eud FROM RelEstructuraUnidadDidactica eud WHERE eud.idDetTema = :idTema ")
	public List<RelEstructuraUnidadDidactica> obtieneRelacionesPorEstTematica(@Param("idTema") Integer idTema);
	
	@Query("SELECT  eud FROM RelEstructuraUnidadDidactica eud WHERE eud.detEtematicaTema.tblEstructuraTematica.tblFichaDescriptivaPrograma.idPrograma = :idPrograma ")
	public List<RelEstructuraUnidadDidactica> obtieneRelEstUnidadDidPorPrograma(@Param("idPrograma") Integer idPrograma);
	
	@Query("SELECT  eud FROM RelEstructuraUnidadDidactica eud"
			+ "		JOIN FETCH eud.detEtematicaTema detETem"
			+ "		JOIN FETCH detETem.tblEstructuraTematica tblEstTem "
			+ "		JOIN FETCH tblEstTem.tblFichaDescriptivaPrograma tblFichaPrograma"
			+ " WHERE tblFichaPrograma.idPrograma = :idPrograma ")
	public List<RelEstructuraUnidadDidactica> obtieneRelEstUnidadDidPorProgramaJoin(@Param("idPrograma") Integer idPrograma);
	

//	@Query("SELECT  DISTINCT eud.idUnidadDidactica  FROM RelEstructuraUnidadDidactica eud"
//			+ "		JOIN FETCH eud.detEtematicaTema detETem"
//			+ "		JOIN FETCH detETem.tblEstructuraTematica tblEstTem "
//			+ "		JOIN FETCH tblEstTem.tblFichaDescriptivaPrograma tblFichaPrograma"
//			+ " WHERE tblFichaPrograma.idPrograma = :idPrograma ")
//	public List<RelEstructuraUnidadDidactica> obtieneRelEstUnidadDidPorProgramaDistinc(@Param("idPrograma") Integer idPrograma);
//	
	
	
}
