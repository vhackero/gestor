package mx.gob.sedesol.basegestor.service.impl.planesyprogramas;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.RelMallaPlanDTO;
import mx.gob.sedesol.basegestor.commons.utils.MensajesSistemaEnum;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.TipoAccion;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.RelMallaPlan;
import mx.gob.sedesol.basegestor.model.repositories.planesyprogramas.MallaPlanRepo;
import mx.gob.sedesol.basegestor.service.admin.ComunValidacionService;
import mx.gob.sedesol.basegestor.service.planesyprogramas.MallaPlanService;

@Service("mallaPlanService")
public class RelMallaPlanServiceImpl extends ComunValidacionService<RelMallaPlanDTO> implements MallaPlanService {

	private static final Logger logger = Logger.getLogger(RelMallaPlanServiceImpl.class);

	private ModelMapper mallaPlanMapper = new ModelMapper();

	@Autowired
	private MallaPlanRepo mallaPlanRepo;

	@Transactional(rollbackFor = Exception.class)
	public ResultadoDTO<RelMallaPlanDTO> guardar(RelMallaPlanDTO dto) {

		ResultadoDTO<RelMallaPlanDTO> res = null;
		try {

			RelMallaPlan mallaPlan = mallaPlanMapper.map(dto, RelMallaPlan.class);
			mallaPlan = mallaPlanRepo.save(mallaPlan);

			res = new ResultadoDTO<RelMallaPlanDTO>();
			res.setDto(mallaPlanMapper.map(mallaPlan, RelMallaPlanDTO.class));

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			res.setMensajeError(MensajesSistemaEnum.ADMIN_MSG_GUARDADO_FALLIDO);
			throw e;
		}
		return res;
	}
	
	@Transactional(rollbackFor = Exception.class)
    public ResultadoDTO<RelMallaPlanDTO> actualizar(RelMallaPlanDTO dto) {

        ResultadoDTO<RelMallaPlanDTO> res = null;
            try {

                RelMallaPlan mallaPlanActualizar = mallaPlanMapper.map(dto, RelMallaPlan.class);
                mallaPlanActualizar = mallaPlanRepo.saveAndFlush(mallaPlanActualizar);
                
                res = new ResultadoDTO<RelMallaPlanDTO>();
                res.setDto(mallaPlanMapper.map(mallaPlanActualizar, RelMallaPlanDTO.class));

            } catch (Exception e) {
                res.setMensajeError(MensajesSistemaEnum.ADMIN_MSG_ACTUALIZACION_FALLIDA);
                logger.error(e.getMessage(), e);
                throw e;
            }
        return res;
    }
	
	@Override
	public RelMallaPlanDTO findByIdPlan(Integer idPlan) {
		try {
			RelMallaPlan mallaPlan = mallaPlanRepo.findByIdPlan(idPlan);

            return mallaPlanMapper.map(mallaPlan, RelMallaPlanDTO.class);
		}catch(Exception e) {
			logger.error(e);
			return null;
		}
	}

	@Transactional(rollbackFor = Exception.class)
    public ResultadoDTO<RelMallaPlanDTO> eliminar(RelMallaPlanDTO dto) {

        ResultadoDTO<RelMallaPlanDTO> res = sonDatosRequeridosValidos(TipoAccion.ELIMINACION, dto);
        try {
            if (ObjectUtils.isNotNull(res) && res.getResultado().getValor()) {

            	RelMallaPlan mallaPlan = mallaPlanMapper.map(dto, RelMallaPlan.class);
                mallaPlanRepo.delete(mallaPlan);
                res = new ResultadoDTO<RelMallaPlanDTO>();
                res.setDto(mallaPlanMapper.map(mallaPlan, RelMallaPlanDTO.class));

            //GUSTAVO --guardarBitacoraEjeCompetencia(dto, String.valueOf(ejeComp.getIdCompetenciaEspecifica()));
            }

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            res.setMensajeError(MensajesSistemaEnum.ADMIN_MSG_ELIMINACION_FALLIDA);
        }
        return res;
    }

	@Override
	public ResultadoDTO<RelMallaPlanDTO> sonDatosRequeridosValidos(TipoAccion accion, RelMallaPlanDTO dto) {
        ResultadoDTO<RelMallaPlanDTO> resultado = null;

        if (ObjectUtils.isNotNull(dto)) {
            resultado = new ResultadoDTO<RelMallaPlanDTO>();
        }
        
        return resultado;
    }

	@Override
    public List<RelMallaPlanDTO> findAll() {
        return new ArrayList<>();
    }

    @Override
    public RelMallaPlanDTO buscarPorId(Integer id) {
        return new RelMallaPlanDTO();
    }

	@Override
	public void validarPersistencia(RelMallaPlanDTO dto, ResultadoDTO<RelMallaPlanDTO> resultado) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
		
	}

	@Override
	public void validarActualizacion(RelMallaPlanDTO dto, ResultadoDTO<RelMallaPlanDTO> resultado) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
		
	}

	@Override
	public void validarEliminacion(RelMallaPlanDTO dto, ResultadoDTO<RelMallaPlanDTO> resultado) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
		
	}

}
