package mx.gob.sedesol.basegestor.model.repositories.gestionescolar;

import java.util.List;

import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.HistorialAcademicoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.HistorialAcademicoListaDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.TiraMateriaBajaDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.TiraMateriaDTO;

public interface IHistorialAcademicoRepo { 

	public HistorialAcademicoDTO consultaDatosHistorialAcademico(String id_persona);

	HistorialAcademicoDTO consultaTiraMaterias(String id_persona);

	List<TiraMateriaDTO> consultaTiraMaterias2(Long id_persona, Integer idEstatusEc);

	List<HistorialAcademicoListaDTO> getParticipanteByActaCerradaYconstancia2(String id_persona);
	
	List<TiraMateriaBajaDTO> consultaTiraMateriasBaja(Long id_persona);

	
}
