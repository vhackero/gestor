package mx.gob.sedesol.basegestor.service.impl.admin;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import mx.gob.sedesol.basegestor.commons.dto.admin.LoteCargaUsuarioDTO;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.model.entities.admin.TblLoteCargaUsuario;
import mx.gob.sedesol.basegestor.model.especificaciones.LoteCargaUsuarioEspecificacion;
import mx.gob.sedesol.basegestor.model.repositories.admin.LoteCargaUsuarioRepo;
import mx.gob.sedesol.basegestor.service.admin.LoteCargaUsuarioService;

@Service("loteCargaUsuarioService")
public class LoteCargaUsuarioServiceImpl implements LoteCargaUsuarioService {

	@Autowired
	private LoteCargaUsuarioRepo loteCargaUsuarioRepo;

	private ModelMapper modelMapper = new ModelMapper();

	private static Type tipoListaLote = new TypeToken<List<LoteCargaUsuarioDTO>>() {
	}.getType();

	@Override
	public List<LoteCargaUsuarioDTO> buscarPorCriterios(LoteCargaUsuarioDTO criterios) {
		List<LoteCargaUsuarioDTO> lotes;
		if (ObjectUtils.isNotNull(criterios)) {
			Specification<TblLoteCargaUsuario> especificacion = new LoteCargaUsuarioEspecificacion(criterios);
			List<TblLoteCargaUsuario> textos = loteCargaUsuarioRepo.findAll(especificacion, sortByFechaRegistroDesc());
			lotes = modelMapper.map(textos, tipoListaLote);
		} else {
			lotes = new ArrayList<>();
		}
		return lotes;
	}
	
	private Sort sortByFechaRegistroDesc() {
        return new Sort(Sort.Direction.DESC, "fechaRegistro");
    }

}
