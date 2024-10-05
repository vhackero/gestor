package mx.gob.sedesol.basegestor.service.impl.admin;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.gob.sedesol.basegestor.commons.dto.admin.DiscapacidadDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.MunicipioDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.PersonaSigeDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.TipoDiscapacidadDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.SelectImportarDTO;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.model.entities.admin.CatDiscapacidad;
import mx.gob.sedesol.basegestor.model.entities.admin.CatTipoDiscapacidad;
import mx.gob.sedesol.basegestor.model.repositories.admin.DiscapacidadRepo;
import mx.gob.sedesol.basegestor.model.repositories.admin.IUsuariosImportarRepo;
import mx.gob.sedesol.basegestor.model.repositories.admin.TipoDiscapacidadRepo;
import mx.gob.sedesol.basegestor.service.admin.DiscapacidadService;
import mx.gob.sedesol.basegestor.service.gestionescolar.UsuariosImportarService;


@Service("usuariosImportarService")
public class UsuariosImportarServiceImpl implements UsuariosImportarService{

	@Autowired
	private IUsuariosImportarRepo usuariosImportarRepo;
	


	
	@Override
	public List<SelectImportarDTO> consultaConvocatorias() {
		return usuariosImportarRepo.consultaConvocatorias();
	}

	
	@Override
	public List<SelectImportarDTO> consultaFuenteExterna() {
		return usuariosImportarRepo.consultaFuenteExterna();
	}


	@Override
	public List<PersonaSigeDTO> consultaPersonasImportar(String fuenteExterna, String convocatoria) {
		return usuariosImportarRepo.consultaPersonasImportar(fuenteExterna, convocatoria);
	}


}
