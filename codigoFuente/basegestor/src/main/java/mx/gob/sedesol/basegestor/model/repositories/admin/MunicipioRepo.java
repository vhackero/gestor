package mx.gob.sedesol.basegestor.model.repositories.admin;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import mx.gob.sedesol.basegestor.model.entities.admin.CatMunicipio;

@Repository
public interface MunicipioRepo extends JpaRepository<CatMunicipio, String> {

	@Query("SELECT m FROM CatMunicipio m "
			+ "JOIN FETCH m.entidadFederativa ef "
			+ "WHERE ef.idEntidadFederativa = ?1 ORDER BY m.nombre")
	List<CatMunicipio> buscarPorEntidadFederativa(int idEntidadFederativa);

}
