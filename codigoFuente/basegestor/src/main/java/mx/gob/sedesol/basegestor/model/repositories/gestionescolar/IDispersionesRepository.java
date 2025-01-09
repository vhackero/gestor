package mx.gob.sedesol.basegestor.model.repositories.gestionescolar;

import java.util.List;

import mx.gob.sedesol.basegestor.model.entities.gestionescolar.Dispersiones;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.DispersionesParam;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.ProcesosInscripcion;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.TipoMatriculacion;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.TblDispersiones;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.TblFichaDescriptivaPrograma;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.TblPlan;

public interface IDispersionesRepository {
	
	public List<ProcesosInscripcion> consultarProcesoInscripcion(DispersionesParam dispercionParametros);
	
	public List<TblPlan> consultarPlan(DispersionesParam dispercionParametros);
	
	public void altaDisperciones(DispersionesParam dispercionParametros);
	
	public List<TblDispersiones> validarDispercionExistente(DispersionesParam dispercionParametros);
	
	public List<TblDispersiones> validarDispercionExistenteOrdinario(DispersionesParam dispercionParametros);
	
	public List<TblFichaDescriptivaPrograma> consultarPrograma(DispersionesParam dispercionParametros);
	
	public List<TipoMatriculacion> consultarTipoMatriculacion();

}
