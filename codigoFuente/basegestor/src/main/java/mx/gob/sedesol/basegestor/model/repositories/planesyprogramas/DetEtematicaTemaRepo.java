package mx.gob.sedesol.basegestor.model.repositories.planesyprogramas;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.DetEtematicaTema;

@Repository
public interface DetEtematicaTemaRepo extends JpaRepository<DetEtematicaTema, Integer> {

	@Query("SELECT et FROM DetEtematicaTema et WHERE TRIM(et.nombreTema) = :nombreTema AND et.tblEstructuraTematica.idEstructuraTematica = :idEstTematica ")
	public DetEtematicaTema buscarTemaPorNombreEIdEstTematica(@Param("idEstTematica") Integer idEstTematica, @Param("nombreTema") String nombreTema);
	
}
