package mx.gob.sedesol.basegestor.service.gestionescolar;

import java.util.List;

import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.TblConvocatoriaDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.TblInscripcionDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.TblInscripcionResumenDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.TblProcesoInscripcionDTO;

public interface DispersionService {
	
    List<TblInscripcionResumenDTO> getInscripcionResumenByProgramaEducativo(List<String> programaEducativo);
    
    List<TblInscripcionResumenDTO> getInscripcionResumenByIdPlanesSemestreBloque(List<Integer> programasEducativos, Integer semestre, String bloque);
    
    List<TblInscripcionDTO> getInscripcionesByIdPlan(Integer idPlan);
    
    List<TblInscripcionDTO> getInscripcionesByProgramasEducativos(List<String> programas);
	
    List<TblInscripcionDTO> getInscripcionesByIdPlanes(List<Integer> idPlanes);
    
    List<TblInscripcionDTO> getInscripcionesByIdPlanPrograma(Integer idPlan, String programa);
    
    List<TblConvocatoriaDTO> obtenerConvocatorias();
    
    List<TblProcesoInscripcionDTO> obtenerProcesosInscripcionByConvocatoriaId(Integer convocatoriaId) ;
}



