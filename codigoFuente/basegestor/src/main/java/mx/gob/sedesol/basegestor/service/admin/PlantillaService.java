package mx.gob.sedesol.basegestor.service.admin;

import java.util.List;

import mx.gob.sedesol.basegestor.commons.dto.admin.PlantillaDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.CuerpoPdfDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.EncabezadoPdfDTO;
import mx.gob.sedesol.basegestor.commons.utils.TipoDocumentoEnum;

public interface PlantillaService extends CommonService<PlantillaDTO,Long> {

	List<PlantillaDTO> obtenerPlantillasPorTipoDocumento(Integer tipoDocumento);

	String obtenerFondoDocumentoPorTipoDocumento(TipoDocumentoEnum tipoDocumento);

	PlantillaDTO obtenerPlantillaPorTipoDocumento(TipoDocumentoEnum tipoDocumento);

	EncabezadoPdfDTO consultaEncabezadoPdf(int idGrupo);

	List<CuerpoPdfDTO> consultaCuerpoPdf(int idGrupo);

}
