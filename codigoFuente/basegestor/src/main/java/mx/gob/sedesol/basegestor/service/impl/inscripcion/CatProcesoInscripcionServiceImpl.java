package mx.gob.sedesol.basegestor.service.impl.inscripcion;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.gob.sedesol.basegestor.commons.dto.inscripcion.CatProcesoInscripcionDTO;
import mx.gob.sedesol.basegestor.model.repositories.inscripcion.ICatProcesoInscripcionRepository;
import mx.gob.sedesol.basegestor.service.inscripcion.CatProcesoInscripcionService;

@Service("catProcesoInscripcionService")
public class CatProcesoInscripcionServiceImpl implements CatProcesoInscripcionService{
	
	@Autowired
	private ICatProcesoInscripcionRepository catProcesoInscripcionRepository;

	@Override
	public List<CatProcesoInscripcionDTO> obtenerTiposProcesoInscripcion() {
		return catProcesoInscripcionRepository.obtenerTiposProcesoInscripcion();
	}

}
