package mx.gob.sedesol.basegestor.service.impl.planesyprogramas;

import java.lang.reflect.Type;
import java.util.List;

import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.ConocimientosPlanDTO;
import mx.gob.sedesol.basegestor.commons.utils.MensajesErrorEnum;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.ResultadoTransaccionEnum;
import mx.gob.sedesol.basegestor.commons.utils.TipoAccion;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.CatConocimientosPlan;
import mx.gob.sedesol.basegestor.model.repositories.planesyprogramas.ConocimientosPlanRepo;
import mx.gob.sedesol.basegestor.service.admin.ComunValidacionService;
import mx.gob.sedesol.basegestor.service.planesyprogramas.ConocimientosPlanService;



@Service("conocimientosPlanService")
public class ConocimientosPlanServiceImpl extends ComunValidacionService<ConocimientosPlanDTO> implements ConocimientosPlanService {
	private static final Logger logger = Logger.getLogger(ConocimientosPlanServiceImpl.class);

    @Autowired
    private ConocimientosPlanRepo conocimientosPlanRepo;

    private ModelMapper conocMapper = new ModelMapper();

    @Override
    public List<ConocimientosPlanDTO> findAll() {

        List<CatConocimientosPlan> conocimientos = conocimientosPlanRepo.findAll();
        Type lstConocimientosDto = new TypeToken<List<CatConocimientosPlan>>() {
        }.getType();
        return conocMapper.map(conocimientos, lstConocimientosDto);
    }
    
    @Override
    public ConocimientosPlanDTO buscarPorId(Integer id) {
        return conocMapper.map(conocimientosPlanRepo.findOne(id), ConocimientosPlanDTO.class);
    }

    
    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResultadoDTO<ConocimientosPlanDTO> guardar(ConocimientosPlanDTO dto) {
        ResultadoDTO<ConocimientosPlanDTO> res = sonDatosRequeridosValidos(TipoAccion.PERSISTENCIA, dto);
        if (ObjectUtils.isNotNull(res) && res.getResultado().getValor()) {
            try {
                res = new ResultadoDTO<ConocimientosPlanDTO>();
                CatConocimientosPlan conocimientos = conocMapper.map(dto, CatConocimientosPlan.class);
                conocimientos = conocimientosPlanRepo.save(conocimientos);
                res.setDto(conocMapper.map(conocimientos, ConocimientosPlanDTO.class));

            //GUSTAVO --guardarBitacoraAreaConocimiento(dto, String.valueOf(areaC.getId()));
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                res.setMensajeError(MensajesErrorEnum.ERROR_PERSISTENCIA_DATOS);
            }
        }
        return res;
    }
    
    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResultadoDTO<ConocimientosPlanDTO> actualizar(ConocimientosPlanDTO dto) {

        ResultadoDTO<ConocimientosPlanDTO> res = sonDatosRequeridosValidos(TipoAccion.ACTUALIZACION, dto);
        if (ObjectUtils.isNotNull(res) && res.getResultado().getValor()) {
            try {
                res = new ResultadoDTO<ConocimientosPlanDTO>();
                CatConocimientosPlan conocimientos = conocMapper.map(dto, CatConocimientosPlan.class);
                conocimientos = conocimientosPlanRepo.saveAndFlush(conocimientos);
                res.setDto(conocMapper.map(conocimientos, ConocimientosPlanDTO.class));

            //GUSTAVO --guardarBitacoraAreaConocimiento(dto, String.valueOf(areaC.getId()));
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                res.setMensajeError(MensajesErrorEnum.ERROR_ACTUALIZACION_DATOS);
                throw e;
            }

        }
        return res;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResultadoDTO<ConocimientosPlanDTO> eliminar(ConocimientosPlanDTO dto) {
        ResultadoDTO<ConocimientosPlanDTO> res = sonDatosRequeridosValidos(TipoAccion.ELIMINACION, dto);
        if (ObjectUtils.isNotNull(res) && res.getResultado().getValor()) {
            try {

                res = new ResultadoDTO<ConocimientosPlanDTO>();
                conocimientosPlanRepo.delete(dto.getIdConocimientoPlan());
                res.setResultado(ResultadoTransaccionEnum.EXITOSO);

            //GUSTAVO --guardarBitacoraAreaConocimiento(dto, String.valueOf(dto.getIdAreaConocimiento()));

            } catch (Exception e) {
                res.setResultado(ResultadoTransaccionEnum.FALLIDO);
                logger.error(e.getMessage(), e);
                throw e;
            }
        }
        return res;
    }

    @Override
    public void validarPersistencia(ConocimientosPlanDTO dto, ResultadoDTO<ConocimientosPlanDTO> resultado) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void validarActualizacion(ConocimientosPlanDTO dto, ResultadoDTO<ConocimientosPlanDTO> resultado) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void validarEliminacion(ConocimientosPlanDTO dto, ResultadoDTO<ConocimientosPlanDTO> resultado) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
