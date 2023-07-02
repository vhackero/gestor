package mx.gob.sedesol.basegestor.service.gestion.aprendizaje;

import java.util.List;

import mx.gob.sedesol.basegestor.commons.dto.admin.CatalogoComunDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestion.aprendizaje.CargaArchivosOaDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestion.aprendizaje.UnidadOaAvaDTO;
import mx.gob.sedesol.basegestor.service.admin.CommonService;

public interface CargaArchivosOaService extends CommonService<CargaArchivosOaDTO, Integer>{

	List<CargaArchivosOaDTO> obtenerArchivosPorUnidadOA(Integer idUnidadOA);
		
	
	public List<CargaArchivosOaDTO> buscarPorIdUnidadOa(UnidadOaAvaDTO unidadOaAvaDTO,CatalogoComunDTO catalogoComunDTO);
}
