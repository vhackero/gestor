package mx.gob.sedesol.basegestor.model.repositories.admin;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import mx.gob.sedesol.basegestor.model.entities.admin.TblDatosSociodemograficosPersona;

@Repository
public interface DatoSociodemograficoPersonaRepo 
		extends JpaRepository<TblDatosSociodemograficosPersona, Long>, JpaSpecificationExecutor<TblDatosSociodemograficosPersona> {		

	@Query("SELECT ds FROM TblDatosSociodemograficosPersona ds "
			+ "JOIN FETCH ds.tipoDiscapacidad td "		
			+ "WHERE ds.persona.idPersona = ?1")
	TblDatosSociodemograficosPersona obtenerDatosSociodemograficosPersona(Long idPersona);
	//List<TblDatosSociodemograficosPersona> obtenerDatosSociodemograficosPersona(Long idPersona);

}


