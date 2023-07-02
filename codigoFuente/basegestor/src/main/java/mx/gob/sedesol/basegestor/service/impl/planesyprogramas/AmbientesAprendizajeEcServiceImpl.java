package mx.gob.sedesol.basegestor.service.impl.planesyprogramas;

import java.lang.reflect.Type;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.AmbienteAprendizajeEcDTO;
import mx.gob.sedesol.basegestor.commons.utils.TipoAccion;
import mx.gob.sedesol.basegestor.model.repositories.planesyprogramas.AmbientesAprendizajeRepo;
import mx.gob.sedesol.basegestor.service.planesyprogramas.AmbientesAprendizajeEcService;

@Service("ambientesAprendizajeService")
public class AmbientesAprendizajeEcServiceImpl implements AmbientesAprendizajeEcService{
	
	@Autowired
	private AmbientesAprendizajeRepo ambientesAprendizajeRepo;
	
	private ModelMapper ambienteAprendMapper = new ModelMapper();
	
	
	
	@Override
	public List<AmbienteAprendizajeEcDTO> findAll() {
		Type lstType = new TypeToken<List<AmbienteAprendizajeEcDTO>>(){}.getType();
		return ambienteAprendMapper.map(ambientesAprendizajeRepo.findAll(), lstType);
	}

	@Override
	public AmbienteAprendizajeEcDTO buscarPorId(Integer id) {
		return ambienteAprendMapper.map(ambientesAprendizajeRepo.findOne(id), AmbienteAprendizajeEcDTO.class);
	}

	@Override
	public ResultadoDTO<AmbienteAprendizajeEcDTO> guardar(AmbienteAprendizajeEcDTO dto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultadoDTO<AmbienteAprendizajeEcDTO> actualizar(AmbienteAprendizajeEcDTO dto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultadoDTO<AmbienteAprendizajeEcDTO> eliminar(AmbienteAprendizajeEcDTO dto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultadoDTO<AmbienteAprendizajeEcDTO> sonDatosRequeridosValidos(TipoAccion accion, AmbienteAprendizajeEcDTO dto) {
		// TODO Auto-generated method stub
		return null;
	}

}
