package mx.gob.sedesol.basegestor.service.gestionescolar;

import java.util.List;

import mx.gob.sedesol.basegestor.model.entities.gestionescolar.Dispersiones;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.DispersionesParam;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.DispersionesParamNuevo;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.ProcesosInscripcion;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.TipoMatriculacion;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.TblDispersiones;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.TblDispersionesBusqueda;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.TblFichaDescriptivaPrograma;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.TblPlan;
import mx.gob.sedesol.basegestor.service.admin.CommonService;

public interface DispersionesService extends CommonService<Dispersiones, Integer>{
	
	public List<ProcesosInscripcion> consultarProcesoInscripcion(DispersionesParam dispercionParametros);
	
	public List<TblPlan> consultarPlan(DispersionesParam dispercionParametros);
	
	public List<TblFichaDescriptivaPrograma> consultarPrograma(DispersionesParam dispercionParametros);
	
	public void altaDisperciones(DispersionesParam dispercionParametros);
	
	public List<TblDispersionesBusqueda> consultaDisperciones(DispersionesParam dispercionParametros);
	
	public List<TblDispersionesBusqueda> actualizarDispersion(DispersionesParamNuevo dispercionParametros);
	
	public void actualizarDispersionExc(DispersionesParamNuevo dispercionParametros);
	
	public void borrarDispercsion(TblDispersionesBusqueda tblBusqueda);
	
	public List<TblDispersiones> validarDispercionExistente(DispersionesParam dispercionParametros);
	
	public List<TblDispersiones> validarDispercionExistenteOrdinario(DispersionesParam dispercionParametros);
	
	public List<TipoMatriculacion> consultarTipoMatriculacion();

}
