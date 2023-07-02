package mx.gob.sedesol.basegestor.service.admin;

import java.util.List;

import mx.gob.sedesol.basegestor.commons.dto.admin.CatalogoGeneralDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.DatoValorCatGeneralDTO;

public interface CatalogosGeneralesService {

	public CatalogoGeneralDTO obtenerCatalogoPorCveCat(String cveCat);

	public List<DatoValorCatGeneralDTO> obtenerValoresPorCveCatalogo(String cveCat);

	List<CatalogoGeneralDTO> findAll();

	CatalogoGeneralDTO buscarPorId(Integer id);
}
