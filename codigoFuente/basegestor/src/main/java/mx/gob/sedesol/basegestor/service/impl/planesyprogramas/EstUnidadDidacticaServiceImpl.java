package mx.gob.sedesol.basegestor.service.impl.planesyprogramas;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.List;

import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.DetEstUniDidacticaDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.RelEstUnidadDidacticaDTO;
import mx.gob.sedesol.basegestor.commons.utils.MensajesSistemaEnum;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.TipoAccion;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.DetEstUnidadDidactica;
import mx.gob.sedesol.basegestor.model.repositories.planesyprogramas.EstUnidadDidacticaRepo;
import mx.gob.sedesol.basegestor.service.admin.ComunValidacionService;
import mx.gob.sedesol.basegestor.service.planesyprogramas.EstUnidadDidacticaService;

@Service("estUnidadDidacticaService")
public class EstUnidadDidacticaServiceImpl extends ComunValidacionService<DetEstUniDidacticaDTO> implements EstUnidadDidacticaService {

    private static final Logger logger = Logger.getLogger(EstructuraTematicaServiceImpl.class);

    @Autowired
    EstUnidadDidacticaRepo estUnidadDidacticaRepo;
    private ModelMapper unidDidacticaMapper = new ModelMapper();

    @Override
    public List<DetEstUniDidacticaDTO> findAll() {

        Type listAux = new TypeToken<List<DetEstUniDidacticaDTO>>() {
        }.getType();
        return unidDidacticaMapper.map(estUnidadDidacticaRepo.findAll(), listAux);
    }

    @Override
    public DetEstUniDidacticaDTO buscarPorId(Integer id) {

        DetEstUnidadDidactica entity = estUnidadDidacticaRepo.findOne(id);
        try {
            Method[] metodos = entity.getClass().getDeclaredMethods();
            for (Method m : metodos) {
                Class<?> typeClass = m.getReturnType();
                if (typeClass.equals(java.util.List.class)) {
                    List<?> dataList = (List<?>) m.invoke(entity);
                    if (dataList.isEmpty()) {
                        dataList = null;
                    }
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return unidDidacticaMapper.map(entity, DetEstUniDidacticaDTO.class);
    }

    /**
     *
     * @param dto
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResultadoDTO<DetEstUniDidacticaDTO> guardar(DetEstUniDidacticaDTO dto) {

        ResultadoDTO<DetEstUniDidacticaDTO> res = new ResultadoDTO<>();

        try {
            if (ObjectUtils.isNotNull(dto)) {

                DetEstUnidadDidactica unidDidactica = unidDidacticaMapper.map(dto, DetEstUnidadDidactica.class);
                unidDidactica = estUnidadDidacticaRepo.save(unidDidactica);
                res.setDto(unidDidacticaMapper.map(unidDidactica, DetEstUniDidacticaDTO.class));

        //GUSTAVO --guardarBitacora(dto.getBitacoraDTO(), String.valueOf(unidDidactica.getIdUnidadDidactica()));
            }

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            res.setMensajeError(MensajesSistemaEnum.ADMIN_MSG_GUARDADO_FALLIDO);
            throw e;
        }

        return res;
    }

    /**
     *
     * @param dto
     * @return 
     */
    @Override
    public ResultadoDTO<DetEstUniDidacticaDTO> actualizar(DetEstUniDidacticaDTO dto) {

        ResultadoDTO<DetEstUniDidacticaDTO> res = new ResultadoDTO<>();
        try {
            if (ObjectUtils.isNotNull(dto)) {
                DetEstUnidadDidactica unidDidactica = new DetEstUnidadDidactica();

                unidDidacticaMapper.map(dto, unidDidactica);

                unidDidactica = estUnidadDidacticaRepo.saveAndFlush(unidDidactica);
                res.setDto(unidDidacticaMapper.map(unidDidactica, DetEstUniDidacticaDTO.class));
        //GUSTAVO --guardarBitacora(dto.getBitacoraDTO(), String.valueOf(unidDidactica.getIdUnidadDidactica()));
            }

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            res.setMensajeError(MensajesSistemaEnum.ADMIN_MSG_GUARDADO_FALLIDO);
            throw e;
        }
        return res;
    }

    @Override
    public ResultadoDTO<DetEstUniDidacticaDTO> eliminar(DetEstUniDidacticaDTO dto) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ResultadoDTO<DetEstUniDidacticaDTO> sonDatosRequeridosValidos(TipoAccion accion, DetEstUniDidacticaDTO dto) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     *
     * @param dto
     */
    @Override
    public void eliminaRelUnidDidMaterialByIdUnidDid(RelEstUnidadDidacticaDTO dto) {
//GUSTAVO --guardarBitacora(dto.getBitacoraDTO(), String.valueOf(dto.getDetEstUnidadDidactica().getIdUnidadDidactica()));
        estUnidadDidacticaRepo.eliminaRelUnidDidMaterialByIdUnidDid(dto.getDetEstUnidadDidactica().getIdUnidadDidactica());
    }

    /**
     *
     * @param dto
     */
    @Override
    public void eliminaRelUnidDidTpoCompByIdUnidDid(RelEstUnidadDidacticaDTO dto) {
        estUnidadDidacticaRepo.eliminaRelUnidDidTpoCompByIdUnidDid(dto.getDetEstUnidadDidactica().getIdUnidadDidactica());
    }

    @Override
    public void validarPersistencia(DetEstUniDidacticaDTO dto, ResultadoDTO<DetEstUniDidacticaDTO> resultado) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void validarActualizacion(DetEstUniDidacticaDTO dto, ResultadoDTO<DetEstUniDidacticaDTO> resultado) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void validarEliminacion(DetEstUniDidacticaDTO dto, ResultadoDTO<DetEstUniDidacticaDTO> resultado) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
