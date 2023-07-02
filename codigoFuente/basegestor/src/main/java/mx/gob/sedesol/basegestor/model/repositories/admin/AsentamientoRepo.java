package mx.gob.sedesol.basegestor.model.repositories.admin;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import mx.gob.sedesol.basegestor.model.entities.admin.CatAsentamiento;

@Repository
public interface AsentamientoRepo extends JpaRepository<CatAsentamiento, String> {
	
	@Query("SELECT a FROM CatAsentamiento a "
			+ "JOIN FETCH a.municipio m "
			+ "JOIN FETCH a.tipoAsentamiento ta "
			+ "JOIN FETCH m.entidadFederativa ef "
			+ "WHERE m.idMunicipio = ?1 ORDER BY a.nombre")
	List<CatAsentamiento> buscarPorMunicipio(String idMunicipio);
	
	@Query("SELECT a FROM CatAsentamiento a WHERE a.idAsentamiento = ?1")
	CatAsentamiento buscarPorIdCadena(String idAsentamieto);
	
	@Query("SELECT a FROM CatAsentamiento a "
			+ "JOIN FETCH a.municipio m "
			+ "JOIN FETCH a.tipoAsentamiento ta "
			+ "JOIN FETCH m.entidadFederativa ef "
			+ "JOIN FETCH ef.pais p "
			+ "WHERE a.codigoPostal = ?1 ORDER BY a.nombre")
	List<CatAsentamiento> buscarPorCodigoPostal(String codigoPostal);

}
