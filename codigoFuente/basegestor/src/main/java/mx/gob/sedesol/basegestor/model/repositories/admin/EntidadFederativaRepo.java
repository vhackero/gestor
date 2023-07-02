package mx.gob.sedesol.basegestor.model.repositories.admin;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import mx.gob.sedesol.basegestor.model.entities.admin.CatEntidadFederativa;

@Repository
public interface EntidadFederativaRepo extends JpaRepository<CatEntidadFederativa, Integer> {

	@Override
	@Query("SELECT ef FROM CatEntidadFederativa ef ORDER BY ef.nombre")
	List<CatEntidadFederativa> findAll();
	
	@Query("SELECT ef FROM CatEntidadFederativa ef WHERE ef.pais.idPais = ?1 ORDER BY ef.nombre")
	List<CatEntidadFederativa> obtenerEntidadesPorPais(String idPais);

}
