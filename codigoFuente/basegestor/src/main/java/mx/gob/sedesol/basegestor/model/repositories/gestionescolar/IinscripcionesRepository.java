package mx.gob.sedesol.basegestor.model.repositories.gestionescolar;

import java.util.List;

import mx.gob.sedesol.basegestor.model.entities.gestionescolar.Convocatoria;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.ConvocatoriaNivelEducativo;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.ConvocatoriaParamConsulta;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.ConvocatoriaTableroResumen;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.InscripcionParamNueva;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.InscripcionesConsultaResumen;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.InscripcionesTableroResumen;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.TipoProceso;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.TblFichaDescriptivaPrograma;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.TblPlan;

public interface IinscripcionesRepository {
	
	List<TipoProceso> consultarTipoProceso();
	
	List<TblPlan> consultarPlan(InscripcionParamNueva inscripcionParamNueva);
	
	List<TblFichaDescriptivaPrograma> consultarPrograma(InscripcionParamNueva inscripcionParamNueva);
	
	List<TblPlan> consultarPlanConvocatoria(InscripcionParamNueva inscripcionParamNueva);
	
	List<InscripcionesTableroResumen> consultarTableroResumen(ConvocatoriaParamConsulta tableroParamConsulta);
	
	List<InscripcionesConsultaResumen> consultarFiltros(ConvocatoriaParamConsulta tableroParamConsulta);

	List<TipoProceso> consultarNombre(ConvocatoriaParamConsulta tableroParamConsulta);
	
	public void altaInscripcion(InscripcionParamNueva inscripcionParamNueva);
	
	public void altaInscripcionExtra(InscripcionParamNueva inscripcionParamNueva);
 
}
