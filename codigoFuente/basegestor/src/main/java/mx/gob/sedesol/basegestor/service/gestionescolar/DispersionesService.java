package mx.gob.sedesol.basegestor.service.gestionescolar;

import java.util.List;

import mx.gob.sedesol.basegestor.model.entities.gestionescolar.Dispersiones;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.ProcesosInscripcion;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.TipoMatriculacion;
import mx.gob.sedesol.basegestor.service.admin.CommonService;

public interface DispersionesService extends CommonService<Dispersiones, Integer>{
	
	public List<ProcesosInscripcion> consultarProcesoInscripcion();
	
	public List<TipoMatriculacion> consultarTipoMatriculacion();

}
