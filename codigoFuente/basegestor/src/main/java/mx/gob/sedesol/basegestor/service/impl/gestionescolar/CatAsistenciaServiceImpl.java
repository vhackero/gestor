package mx.gob.sedesol.basegestor.service.impl.gestionescolar;

import java.lang.reflect.Type;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.CatAsistenciaDTO;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.CatAsistencia;
import mx.gob.sedesol.basegestor.model.repositories.gestionescolar.CatalogoAsistenciaRepo;
import mx.gob.sedesol.basegestor.service.gestionescolar.CatAsistenciaService;

@Service("catAsistenciaService")
public class CatAsistenciaServiceImpl implements CatAsistenciaService{
	
	@Autowired
	private CatalogoAsistenciaRepo catAsistenciaRepo;
	
	private ModelMapper modelMapper = new ModelMapper();
	
    @Override
    public List<CatAsistenciaDTO> getCatAsistencia(){
    	
    	List<CatAsistenciaDTO> catAsistencia;
	    	
	    	List<CatAsistencia> lista = catAsistenciaRepo.findAll();
	    	
			Type objetoDTO = new TypeToken<List<CatAsistenciaDTO>>() {
			}.getType();
	
	    	
			catAsistencia = modelMapper.map(lista, objetoDTO);
    	
        
    	return catAsistencia;
    		 
    }

}
