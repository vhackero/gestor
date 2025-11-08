package mx.gob.sedesol.basegestor.service.gestionescolar;

import java.time.LocalDateTime;
import java.util.List;

import mx.gob.sedesol.basegestor.model.entities.gestionescolar.Convocatoria;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.ConvocatoriaNivelEducativo;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.ConvocatoriaParamConsulta;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.ConvocatoriaTableroResumen;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.InscripcionParamNueva;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.InscripcionPlanesProgramas;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.InscripcionesConsultaResumen;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.InscripcionesTableroResumen;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.TipoProceso;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.TblFichaDescriptivaPrograma;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.TblPlan;
import mx.gob.sedesol.basegestor.service.admin.CommonService;

/**
 *  
 * @author ITTIVA
 * 
 */
public interface InscripcionesService extends CommonService<Convocatoria, Integer>{
	
 
	public List<TipoProceso> consultarTipoProceso();
	
	List<TipoProceso> consultarTipoProcesoDisponibles(Integer convocatoriaId);

	public List<TblPlan> consultarPlan(InscripcionParamNueva inscripcionParamNueva);
	
	public List<TblFichaDescriptivaPrograma> consultarPrograma(InscripcionParamNueva inscripcionParamNueva);
	
	public List<InscripcionesTableroResumen> consultarTableroResumen(ConvocatoriaParamConsulta tableroParamConsulta);
	
	public List<InscripcionesConsultaResumen> consultarFiltros(ConvocatoriaParamConsulta tableroParamConsulta);
	
	public void altaConvocatorias();
	
	public void altaInscripciones(InscripcionParamNueva inscripcionParamNueva);
	
	public void altaInscripcionesExtra(InscripcionParamNueva inscripcionParamNueva);
	
	public List<InscripcionPlanesProgramas> consultarPlanPrograma(InscripcionParamNueva inscripcionParamNueva);

	List<TipoProceso> consultarNombre(ConvocatoriaParamConsulta tableroParamConsulta);

	boolean updateProcesoInscripcion(Long procesoInscripcionId, String nombre, LocalDateTime fechaInicio,
			LocalDateTime fechaFin, int estatus, Long idTipoProceso, Long convocatoriaId);

	void deleteProcesoInscripcion(Long procesoInscripcionId, Long convocatoriaId, String tipoProceso);

	String generarClaveProceso(String nombre);

	 

}
