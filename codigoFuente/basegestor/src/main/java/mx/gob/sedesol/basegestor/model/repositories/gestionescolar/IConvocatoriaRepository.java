package mx.gob.sedesol.basegestor.model.repositories.gestionescolar;

import java.util.List;

import mx.gob.sedesol.basegestor.model.entities.gestionescolar.Convocatoria;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.ConvocatoriaNivelEducativo;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.ConvocatoriaTableroResumen;

public interface IConvocatoriaRepository {
	
	List<Convocatoria> consultarConvocatorias();
	
	List<Convocatoria> consultarConvocatoriasFiltros();
	
	List<ConvocatoriaTableroResumen> consultarTableroResumen(Integer convocatoriaId);
	
	List<ConvocatoriaNivelEducativo> consultarNivelEducativo();
	
	public void altaConvocatorias();

}
