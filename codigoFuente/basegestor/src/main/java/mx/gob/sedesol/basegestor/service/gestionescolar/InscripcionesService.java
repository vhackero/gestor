package mx.gob.sedesol.basegestor.service.gestionescolar;

import java.util.List;

import mx.gob.sedesol.basegestor.model.entities.gestionescolar.Convocatoria;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.ConvocatoriaNivelEducativo;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.ConvocatoriaParamConsulta;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.ConvocatoriaTableroResumen;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.TipoProceso;
import mx.gob.sedesol.basegestor.service.admin.CommonService;

/**
 *  
 * @author ITTIVA
 * 
 */
public interface InscripcionesService extends CommonService<Convocatoria, Integer>{
	
 
	public List<TipoProceso> consultarTipoProceso();
	
	public void altaConvocatorias();
	 

}
