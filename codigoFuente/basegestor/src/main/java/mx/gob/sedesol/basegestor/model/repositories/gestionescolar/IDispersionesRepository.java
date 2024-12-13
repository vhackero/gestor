package mx.gob.sedesol.basegestor.model.repositories.gestionescolar;

import java.util.List;

import mx.gob.sedesol.basegestor.model.entities.gestionescolar.Dispersiones;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.ProcesosInscripcion;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.TipoMatriculacion;

public interface IDispersionesRepository {
	
	public List<ProcesosInscripcion> consultarProcesoInscripcion();
	
	public List<TipoMatriculacion> consultarTipoMatriculacion();

}
