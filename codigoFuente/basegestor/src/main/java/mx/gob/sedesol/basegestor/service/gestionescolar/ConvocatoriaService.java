package mx.gob.sedesol.basegestor.service.gestionescolar;

import java.util.List;

import mx.gob.sedesol.basegestor.model.entities.gestionescolar.Convocatoria;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.ConvocatoriaNivelEducativo;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.ConvocatoriaParamConsulta;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.ConvocatoriaParamNueva;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.ConvocatoriaTableroResumen;
import mx.gob.sedesol.basegestor.service.admin.CommonService;

/**
 *  
 * @author ITTIVA
 * 
 */
public interface ConvocatoriaService extends CommonService<Convocatoria, Integer>{
	
	public List<Convocatoria> consultarConvocatorias();
	
	public List<Convocatoria> consultarConvocatoriasFiltros(ConvocatoriaParamConsulta convocatoriaParamConsulta);
	
	public List<ConvocatoriaTableroResumen> consultarTableroResumen(Integer convocatoriaId);
	
	public List<ConvocatoriaNivelEducativo> consultarNivelEducativo();
	
	public void altaConvocatorias(ConvocatoriaParamNueva convocatoriaParamNueva);
	
	public void eliminarConvocatorias(Convocatoria elminarConvo);

}
