package mx.gob.sedesol.basegestor.service.impl.inscripcion;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.gob.sedesol.basegestor.commons.dto.inscripcion.InscripcionPreviaMateriasDTO;
import mx.gob.sedesol.basegestor.model.repositories.inscripcion.IInscripcionPreviaMateriasRepository;
import mx.gob.sedesol.basegestor.service.inscripcion.InscripcionPreviaMateriasService;

@Service("inscripcionPreviaMateriasService")
public class InscripcionPreviaMateriasServiceImpl implements InscripcionPreviaMateriasService{
	
	@Autowired
	private IInscripcionPreviaMateriasRepository inscripcionPreviaMateriasRepository;

	@Override
	public List<InscripcionPreviaMateriasDTO> obtenerInscripcionPrevia(Long idPersona) {
		return inscripcionPreviaMateriasRepository.obtenerInscripcionPrevia(idPersona);
	}

}