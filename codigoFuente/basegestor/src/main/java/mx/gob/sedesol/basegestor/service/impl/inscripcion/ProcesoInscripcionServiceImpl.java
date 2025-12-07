package mx.gob.sedesol.basegestor.service.impl.inscripcion;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.gob.sedesol.basegestor.commons.dto.inscripcion.ProcesoInscripcionDTO;
import mx.gob.sedesol.basegestor.model.repositories.inscripcion.IProcesoInscripcionRepository;
import mx.gob.sedesol.basegestor.service.inscripcion.ProcesoInscripcionService;

@Service("procesoInscripcionService")
public class ProcesoInscripcionServiceImpl implements ProcesoInscripcionService {

	@Autowired
	private IProcesoInscripcionRepository procesoInscripcionRepository;
	
	@Transactional(readOnly = true)
	@Override
	public List<ProcesoInscripcionDTO> obtenerPorConvocatoriaYTipoProceso(Long idConvocatoria, Long idTipoProceso) {
		return procesoInscripcionRepository.obtenerPorConvocatoriaYTipoProceso(idConvocatoria, idTipoProceso);
	}



}
