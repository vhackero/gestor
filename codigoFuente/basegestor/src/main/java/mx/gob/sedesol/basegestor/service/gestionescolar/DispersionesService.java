package mx.gob.sedesol.basegestor.service.gestionescolar;

import java.util.List;

import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.CrearEventoDispersionDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.DispersionCreacionResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.DispersionMatriculacionResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.MatricularDispersionDTO;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.DispersionPreEvento;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.Dispersiones;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.DispersionesParam;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.DispersionesParamNuevo;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.InscripcionPlanesProgramas;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.ProcesosInscripcion;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.TipoMatriculacion;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.TblDispersionesBusqueda;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.TblFichaDescriptivaPrograma;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.TblPlan;
import mx.gob.sedesol.basegestor.service.admin.CommonService;

public interface DispersionesService extends CommonService<Dispersiones, Integer>{
	
	public List<ProcesosInscripcion> consultarProcesoInscripcion(DispersionesParam dispercionParametros);
	
	public List<TblPlan> consultarPlan(DispersionesParam dispercionParametros);
	
	public List<TblFichaDescriptivaPrograma> consultarPrograma(DispersionesParam dispercionParametros);
	
	public List<InscripcionPlanesProgramas> consultarPlanesProgramas(DispersionesParam dispercionParametros);
	
	public void altaDisperciones(DispersionesParam dispercionParametros);
	
	public List<TblDispersionesBusqueda> consultaDisperciones(DispersionesParam dispercionParametros);
	
	public List<TblDispersionesBusqueda> actualizarDispersion(DispersionesParamNuevo dispercionParametros);
	
	public boolean actualizarDispersionExc(DispersionesParamNuevo dispercionParametros);
	
	public void borrarDispercsion(TblDispersionesBusqueda tblBusqueda);

	public boolean existeRelDispersionGrupo(Integer idDispersion);
	
	public boolean validarDispercionExistente(DispersionesParam dispercionParametros);
	
	public boolean validarDispercionExistenteOrdinario(DispersionesParam dispercionParametros);
	
	public List<TipoMatriculacion> consultarTipoMatriculacion();
	
	public DispersionPreEvento obtenerDatosPreviosEvento(Integer idPrograma);
	
	public ResultadoDTO<DispersionCreacionResultadoDTO> crearEventosDispersion(CrearEventoDispersionDTO solicitud);
	
	public ResultadoDTO<DispersionMatriculacionResultadoDTO> matricularUsuariosDispersion(MatricularDispersionDTO solicitud);

}
