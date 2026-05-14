package mx.gob.sedesol.basegestor.model.repositories.gestionescolar;

import java.util.List;

import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.DispersionGrupoEventoDTO;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.DispersionPreEvento;
import mx.gob.sedesol.basegestor.model.entities.gestionaprendizaje.TblAmbienteVirtualAprendizaje;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.DispersionesParam;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.DispersionesParamNuevo;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.InscripcionPlanesProgramas;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.ProcesosInscripcion;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.TblEvento;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.TblGrupo;
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
	
	public DispersionPreEvento obtenerDatosPreviosEvento(Integer idPrograma);
	
		public TblEvento guardarEventoDispersion(TblEvento evento);
		
		public void actualizarEvento(TblEvento evento);
		
		public TblAmbienteVirtualAprendizaje guardarAmbienteVirtual(TblAmbienteVirtualAprendizaje ambienteVirtual);
		
		public TblGrupo guardarGrupoDispersion(TblGrupo grupo);
		
		public void actualizarGrupoDispersion(TblGrupo grupo);
		
		public List<DispersionGrupoEventoDTO> obtenerRelacionesDispersion(Integer idDispersion);
		
		public List<DispersionGrupoEventoDTO> obtenerGruposOrdinariosPorPrograma(Integer idPrograma, Integer idProcesoInscripcion);
		
		public List<Long> obtenerPersonasMatriculacion(Integer idDispersion, Integer idConvocatoria, Integer idTipoProceso, Integer idProcesoInscripcion);

		public List<Long> obtenerPersonasMatriculacionCompartidos(Integer idDispersion, Integer idProcesoInscripcion,
				Integer idPrograma, String nombreProgramaSeleccionado, String bloque);
		
		public void guardarRelacionDispersionGrupo(Integer idDispersion, Integer idEvento, Integer idGrupo, Long idPersona);
		
		public List<ProcesosInscripcion> consultarProcesosConDispersion();
		
		public List<ProcesosInscripcion> consultarProcesosSinDispersion();

		public List<String> consultarProgramasCompartidos(Integer idProcesoInscripcion);

		public List<TblDispersionesBusqueda> consultarDispersionesGruposCompartidos(Integer idProcesoInscripcion,
				String nombreProgramaSeleccionado);
		
		public List<mx.gob.sedesol.basegestor.commons.dto.gestionescolar.DispersionMatriculaExistenteDTO> consultarDispersionesExistentes(Integer idProcesoConDispersion, Integer idProcesoMatricular);
		
		public Integer crearDispersionBasica(Integer idProcesoInscripcion, Integer idPrograma, Integer totalEstudiantes, Integer tipoMatriculacion, Long usuarioModifico);
		
		public List<Long> obtenerPersonasMatriculaExistente(Integer idProcesoInscripcionMatricular, Integer idPrograma);

	}
