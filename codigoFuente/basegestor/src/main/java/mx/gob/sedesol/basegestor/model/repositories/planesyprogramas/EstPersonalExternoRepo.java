package mx.gob.sedesol.basegestor.model.repositories.planesyprogramas;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.TblEstPersonalExterno;

@Repository
public interface EstPersonalExternoRepo extends JpaRepository<TblEstPersonalExterno, Integer> {

	@Query("select pe from TblEstPersonalExterno pe where pe.id = :id ")
	public List<TblEstPersonalExterno> obtenerOrgGubernamentalPorId(@Param("id") Integer id);
	
	@Query("SELECT pe FROM TblEstPersonalExterno pe WHERE pe.estPersonalExternoPadre.id is NULL")
	public List<TblEstPersonalExterno> obtenerEstPersonalExtPadres();
}
