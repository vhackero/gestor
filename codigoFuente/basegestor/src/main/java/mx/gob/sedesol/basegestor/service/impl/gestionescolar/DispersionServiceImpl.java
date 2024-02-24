package mx.gob.sedesol.basegestor.service.impl.gestionescolar;

import java.lang.reflect.Type;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.EventoCapacitacionDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.TblInscripcionDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.TblInscripcionResumenDTO;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.TblEvento;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.TblInscripcion;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.TblInscripcionResumen;
import mx.gob.sedesol.basegestor.model.repositories.gestionescolar.InscripcionRepo;
import mx.gob.sedesol.basegestor.model.repositories.gestionescolar.InscripcionResumenRepo;
import mx.gob.sedesol.basegestor.service.gestionescolar.DispersionService;

@Service("dispersionServiceImpl")
public class DispersionServiceImpl 
//extends ComunValidacionService<RelGrupoParticipanteDTO>
		implements DispersionService {

	private static final Logger logger = Logger.getLogger(DispersionServiceImpl.class);

	@Autowired
	private InscripcionResumenRepo inscripcionResumenRepo;
	
	@Autowired
	private InscripcionRepo inscripcionRepo;

	private ModelMapper modelMapper = new ModelMapper();

	@PostConstruct
	public void init() {

		PropertyMap<EventoCapacitacionDTO, TblEvento> tblEventoMap = new PropertyMap<EventoCapacitacionDTO, TblEvento>() {
			protected void configure() {
				skip().setCategoriaEC(null);
				skip().setCatEstadoEventoCapacitacion(null);
				skip().setFichaDescriptivaPrograma(null);
				skip().setAmbienteVirtualAprendizajes(null);
				skip().setReponsableProduccionEcs(null);
				skip().setCatModalidadPlanPrograma(null);
			}
		};
		modelMapper.addMappings(tblEventoMap);
	}


	@Override
	public List<TblInscripcionResumenDTO> getInscripcionResumenByProgramaEducativo(List<String> programasEducativos) {
		List<TblInscripcionResumenDTO> listDTO;

		List<TblInscripcionResumen> lista = inscripcionResumenRepo.getInscripcionResumenByProgramaEducativo(programasEducativos);

		Type objetoDTO = new TypeToken<List<TblInscripcionResumenDTO>>() {
		}.getType();

		listDTO = modelMapper.map(lista, objetoDTO);

		return listDTO;
	}
	
	@Override
	public List<TblInscripcionDTO> getInscripcionesByIdPlan(Integer idPlan) {
		List<TblInscripcionDTO> listDTO;

		List<TblInscripcion> lista = inscripcionRepo.getInscripcionesByIdPlan(idPlan);

		Type objetoDTO = new TypeToken<List<TblInscripcionDTO>>() {
		}.getType();

		listDTO = modelMapper.map(lista, objetoDTO);

		return listDTO;
	}
	
	@Override
	public List<TblInscripcionDTO> getInscripcionesByProgramasEducativos(List<String> programas) {
		List<TblInscripcionDTO> listDTO;

		List<TblInscripcion> lista = inscripcionRepo.getInscripcionesByProgramasEducativos(programas);

		Type objetoDTO = new TypeToken<List<TblInscripcionDTO>>() {
		}.getType();

		listDTO = modelMapper.map(lista, objetoDTO);

		return listDTO;
	}
	
	@Override
	public List<TblInscripcionDTO> getInscripcionesByIdPlanes(List<Integer> idPlanes) {
		List<TblInscripcionDTO> listDTO;

		List<TblInscripcion> lista = inscripcionRepo.getInscripcionesByIdPlanes(idPlanes);

		Type objetoDTO = new TypeToken<List<TblInscripcionDTO>>() {
		}.getType();

		listDTO = modelMapper.map(lista, objetoDTO);

		return listDTO;
	}
	
	@Override
	public List<TblInscripcionDTO> getInscripcionesByIdPlanPrograma(Integer idPlan, String programa) {
		List<TblInscripcionDTO> listDTO;
		
		List<TblInscripcion> lista = inscripcionRepo.getInscripcionesByIdPlanPrograma(idPlan, programa);
		
		Type objetoDTO = new TypeToken<List<TblInscripcionDTO>>() {
		}.getType();

		listDTO = modelMapper.map(lista, objetoDTO);

		return listDTO;
	}
	
}
