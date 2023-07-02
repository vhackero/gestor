package mx.gob.sedesol.basegestor.service.impl.planesyprogramas;

import java.lang.reflect.Type;
import java.util.List;

import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.gob.sedesol.basegestor.commons.constantes.ConstantesBitacora;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.AreaConocimientoDTO;
import mx.gob.sedesol.basegestor.commons.utils.MensajesErrorEnum;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.ResultadoTransaccionEnum;
import mx.gob.sedesol.basegestor.commons.utils.TipoAccion;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.CatAreasConocimiento;
import mx.gob.sedesol.basegestor.model.repositories.planesyprogramas.AreaConocimientoRepo;
import mx.gob.sedesol.basegestor.mongo.service.AdministradorBitacora;
import mx.gob.sedesol.basegestor.service.admin.ComunValidacionService;
import mx.gob.sedesol.basegestor.service.planesyprogramas.AreaConocimientoService;

@Service("areaConocimientoService")
public class AreaConocimientoServiceImpl extends ComunValidacionService<AreaConocimientoDTO> implements AreaConocimientoService {

    private static final Logger logger = Logger.getLogger(AreaConocimientoServiceImpl.class);

    @Autowired
    private AreaConocimientoRepo areaConocimientoRepo;

    private ModelMapper areaConocMapper = new ModelMapper();

    @Override
    public List<AreaConocimientoDTO> findAll() {

        List<CatAreasConocimiento> areas = areaConocimientoRepo.findAll();
        Type lstAreasDto = new TypeToken<List<AreaConocimientoDTO>>() {
        }.getType();
        return areaConocMapper.map(areas, lstAreasDto);
    }

    @Override
    public AreaConocimientoDTO buscarPorId(Integer id) {
        return areaConocMapper.map(areaConocimientoRepo.findOne(id), AreaConocimientoDTO.class);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResultadoDTO<AreaConocimientoDTO> guardar(AreaConocimientoDTO dto) {
        ResultadoDTO<AreaConocimientoDTO> res = sonDatosRequeridosValidos(TipoAccion.PERSISTENCIA, dto);
        if (ObjectUtils.isNotNull(res) && res.getResultado().getValor()) {
            try {
                res = new ResultadoDTO<AreaConocimientoDTO>();
                CatAreasConocimiento areaC = areaConocMapper.map(dto, CatAreasConocimiento.class);
                areaC = areaConocimientoRepo.save(areaC);
                res.setDto(areaConocMapper.map(areaC, AreaConocimientoDTO.class));

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
    public ResultadoDTO<AreaConocimientoDTO> actualizar(AreaConocimientoDTO dto) {

        ResultadoDTO<AreaConocimientoDTO> res = sonDatosRequeridosValidos(TipoAccion.ACTUALIZACION, dto);
        if (ObjectUtils.isNotNull(res) && res.getResultado().getValor()) {
            try {
                res = new ResultadoDTO<AreaConocimientoDTO>();
                CatAreasConocimiento areaC = areaConocMapper.map(dto, CatAreasConocimiento.class);
                areaC = areaConocimientoRepo.saveAndFlush(areaC);
                res.setDto(areaConocMapper.map(areaC, AreaConocimientoDTO.class));

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
    public ResultadoDTO<AreaConocimientoDTO> eliminar(AreaConocimientoDTO dto) {
        ResultadoDTO<AreaConocimientoDTO> res = sonDatosRequeridosValidos(TipoAccion.ELIMINACION, dto);
        if (ObjectUtils.isNotNull(res) && res.getResultado().getValor()) {
            try {

                res = new ResultadoDTO<AreaConocimientoDTO>();
                areaConocimientoRepo.delete(dto.getIdAreaConocimiento());
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
    public ResultadoDTO<AreaConocimientoDTO> sonDatosRequeridosValidos(TipoAccion accion, AreaConocimientoDTO dto) {

        ResultadoDTO<AreaConocimientoDTO> res = null;
        if (ObjectUtils.isNotNull(dto)) {
            res = new ResultadoDTO<AreaConocimientoDTO>();

            switch (accion) {
                case PERSISTENCIA:

                    if (ObjectUtils.isNullOrEmpty(dto.getNombre())) {
                        res.setMensajeError(MensajesErrorEnum.ERROR_NOMBRE_REQ);
                    }

                    if (ObjectUtils.isNull(dto.getFechaRegistro())) {
                        res.setMensajeError(MensajesErrorEnum.ERROR_FECHA_REGISTRO_REQ);
                    }

                    break;

                case ACTUALIZACION:

                    if (ObjectUtils.isNullOrEmpty(dto.getIdAreaConocimiento())) {
                        res.setMensajeError(MensajesErrorEnum.ERROR_ID_REQ);
                    }

                    if (ObjectUtils.isNull(dto.getFechaActualizacion())) {
                        res.setMensajeError(MensajesErrorEnum.ERROR_FECHA_UPDATE_REQ);
                    }

                    if (ObjectUtils.isNull(dto.getFechaActualizacion())) {
                        res.setMensajeError(MensajesErrorEnum.ERROR_FECHA_UPDATE_REQ);
                    }

                    break;

                case ELIMINACION:

                    if (ObjectUtils.isNullOrEmpty(dto.getIdAreaConocimiento())) {
                        res.setMensajeError(MensajesErrorEnum.ERROR_ID_REQ);
                    }

                    break;
            }
        }

        return res;
    }


    @Override
    public void validarPersistencia(AreaConocimientoDTO dto, ResultadoDTO<AreaConocimientoDTO> resultado) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void validarActualizacion(AreaConocimientoDTO dto, ResultadoDTO<AreaConocimientoDTO> resultado) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void validarEliminacion(AreaConocimientoDTO dto, ResultadoDTO<AreaConocimientoDTO> resultado) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
