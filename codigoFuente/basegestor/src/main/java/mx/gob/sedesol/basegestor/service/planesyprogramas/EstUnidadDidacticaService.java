package mx.gob.sedesol.basegestor.service.planesyprogramas;

import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.DetEstUniDidacticaDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.RelEstUnidadDidacticaDTO;
import mx.gob.sedesol.basegestor.service.admin.CommonService;

public interface EstUnidadDidacticaService extends CommonService<DetEstUniDidacticaDTO,Integer> {

	public void eliminaRelUnidDidMaterialByIdUnidDid(RelEstUnidadDidacticaDTO unidadDidactica);
	
	public void eliminaRelUnidDidTpoCompByIdUnidDid(RelEstUnidadDidacticaDTO unidadDidactica);
	
}
