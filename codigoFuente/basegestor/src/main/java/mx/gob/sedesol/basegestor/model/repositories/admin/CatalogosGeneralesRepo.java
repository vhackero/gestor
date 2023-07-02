package mx.gob.sedesol.basegestor.model.repositories.admin;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.RelCatalogoGeneralValor;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.TblCatalogoGeneral;

@Repository
public interface CatalogosGeneralesRepo extends JpaRepository<TblCatalogoGeneral, Integer>{

	@Query("Select cg from TblCatalogoGeneral cg where cg.claveCatalogo = :cveCatalogo ")
    TblCatalogoGeneral buscarPorClaveCatalogo(@Param("cveCatalogo") String cveCatalogo);
	
	@Query("Select vl from RelCatalogoGeneralValor vl where vl.catalogoGeneral.claveCatalogo = :cveCatalogo ")
    List<RelCatalogoGeneralValor> obtenerValoresPorCveCatalogo(@Param("cveCatalogo") String cveCatalogo);
}
