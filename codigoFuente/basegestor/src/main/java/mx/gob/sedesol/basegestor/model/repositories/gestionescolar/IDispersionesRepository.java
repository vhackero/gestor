package mx.gob.sedesol.basegestor.model.repositories.gestionescolar;

import java.util.List;

import mx.gob.sedesol.basegestor.model.entities.gestionescolar.DispersionesParam;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.DispersionesParamNuevo;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.InscripcionPlanesProgramas;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.ProcesosInscripcion;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.TipoMatriculacion;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.TblDispersionesBusqueda;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.TblFichaDescriptivaPrograma;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.TblPlan;

public interface IDispersionesRepository {
	
	public List<ProcesosInscripcion> consultarProcesoInscripcion(DispersionesParam dispercionParametros);
	
	public List<TblPlan> consultarPlan(DispersionesParam dispercionParametros);
	
	public void altaDisperciones(DispersionesParam dispercionParametros);
	
	public void borrarDispercsion(TblDispersionesBusqueda tblBusqueda);

	public boolean existeRelDispersionGrupo(Integer idDispersion);
	
	public boolean validarDispercionExistente(DispersionesParam dispercionParametros);
	
	public List<TblDispersionesBusqueda> actualizarDispersion(DispersionesParamNuevo dispercionParametros);
	
	public boolean actualizarDispersionExc(DispersionesParamNuevo dispercionParametros);
	
	public boolean validarDispercionExistenteOrdinario(DispersionesParam dispercionParametros);
	
	public List<TblDispersionesBusqueda> consultaDisperciones(DispersionesParam dispercionParametros);
	
	public List<TblFichaDescriptivaPrograma> consultarPrograma(DispersionesParam dispercionParametros);
	
	public List<TipoMatriculacion> consultarTipoMatriculacion();
	
	public List<InscripcionPlanesProgramas> consultarPlanesProgramas(DispersionesParam dispercionParametros);

}
