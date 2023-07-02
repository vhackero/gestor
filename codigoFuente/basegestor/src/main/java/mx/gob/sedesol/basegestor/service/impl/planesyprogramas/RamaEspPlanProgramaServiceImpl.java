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
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.RamaEspPlanProgramaDTO;
import mx.gob.sedesol.basegestor.commons.utils.MensajesErrorEnum;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.TipoAccion;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.CatRamaEspecialidadPlanPrograma;
import mx.gob.sedesol.basegestor.model.repositories.planesyprogramas.RamaEspPlanProgRepo;
import mx.gob.sedesol.basegestor.service.admin.ComunValidacionService;
import mx.gob.sedesol.basegestor.service.planesyprogramas.RamaEspPlanProgramaService;

@Service("ramaEspPlanProgramaService")
public class RamaEspPlanProgramaServiceImpl extends ComunValidacionService<RamaEspPlanProgramaDTO> implements RamaEspPlanProgramaService {

    private static final Logger logger = Logger.getLogger(RamaEspPlanProgramaServiceImpl.class);

    @Autowired
    private RamaEspPlanProgRepo ramaEspPlanProgRepo;

    private ModelMapper ramaEspPlnMapper = new ModelMapper();

    @Override
    public List<RamaEspPlanProgramaDTO> findAll() {

        List<CatRamaEspecialidadPlanPrograma> lstAux = ramaEspPlanProgRepo.findAll();
        Type lstPlanProg = new TypeToken<List<RamaEspPlanProgramaDTO>>() {
        }.getType();
        return ramaEspPlnMapper.map(lstAux, lstPlanProg);
    }

    @Override
    public RamaEspPlanProgramaDTO buscarPorId(Integer id) {
        return ramaEspPlnMapper.map(ramaEspPlanProgRepo.findOne(id), RamaEspPlanProgramaDTO.class);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResultadoDTO<RamaEspPlanProgramaDTO> guardar(RamaEspPlanProgramaDTO dto) {
        ResultadoDTO<RamaEspPlanProgramaDTO> rs = sonDatosRequeridosValidos(TipoAccion.PERSISTENCIA, dto);
        if (ObjectUtils.isNotNull(rs) && rs.getResultado().getValor()) {

            rs = new ResultadoDTO<RamaEspPlanProgramaDTO>();
            try {
                CatRamaEspecialidadPlanPrograma entidad = ramaEspPlnMapper.map(dto, CatRamaEspecialidadPlanPrograma.class);
                entidad = ramaEspPlanProgRepo.save(entidad);
                rs.setDto(ramaEspPlnMapper.map(entidad, RamaEspPlanProgramaDTO.class));

        //GUSTAVO --guardarBitacora(dto.getBitacoraDTO(), String.valueOf(entidad.getId()));

            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                rs.setMensajeError(MensajesErrorEnum.ERROR_PERSISTENCIA_DATOS);
                throw e;
            }
        }
        return rs;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResultadoDTO<RamaEspPlanProgramaDTO> actualizar(RamaEspPlanProgramaDTO dto) {
        ResultadoDTO<RamaEspPlanProgramaDTO> rs = sonDatosRequeridosValidos(TipoAccion.ACTUALIZACION, dto);
        if (ObjectUtils.isNotNull(rs) && rs.getResultado().getValor()) {

            rs = new ResultadoDTO<RamaEspPlanProgramaDTO>();
            try {
                CatRamaEspecialidadPlanPrograma entidad = ramaEspPlnMapper.map(dto, CatRamaEspecialidadPlanPrograma.class);
                entidad = ramaEspPlanProgRepo.saveAndFlush(entidad);
                rs.setDto(ramaEspPlnMapper.map(entidad, RamaEspPlanProgramaDTO.class));

        //GUSTAVO --guardarBitacora(dto.getBitacoraDTO(), String.valueOf(entidad.getId()));

            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                rs.setMensajeError(MensajesErrorEnum.ERROR_PERSISTENCIA_DATOS);
                throw e;
            }
        }
        return rs;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResultadoDTO<RamaEspPlanProgramaDTO> eliminar(RamaEspPlanProgramaDTO dto) {
        ResultadoDTO<RamaEspPlanProgramaDTO> rs = sonDatosRequeridosValidos(TipoAccion.ELIMINACION, dto);
        if (ObjectUtils.isNotNull(rs) && rs.getResultado().getValor()) {
            try {
                CatRamaEspecialidadPlanPrograma entidad = ramaEspPlnMapper.map(dto, CatRamaEspecialidadPlanPrograma.class);
                ramaEspPlanProgRepo.delete(entidad);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                rs.setMensajeError(MensajesErrorEnum.ERROR_ELIMINAR_DATOS);
                throw e;
            }
        }
        return rs;
    }

    @Override
    public ResultadoDTO<RamaEspPlanProgramaDTO> sonDatosRequeridosValidos(TipoAccion accion, RamaEspPlanProgramaDTO dto) {

        ResultadoDTO<RamaEspPlanProgramaDTO> rs = null;
        if (ObjectUtils.isNotNull(dto)) {
            rs = new ResultadoDTO<RamaEspPlanProgramaDTO>();

            switch (accion) {
                case PERSISTENCIA:

                    if (ObjectUtils.isNullOrEmpty(dto.getNombre())) {
                        rs.setMensajeError(MensajesErrorEnum.ERROR_NOMBRE_REQ);
                    }

                    if (ObjectUtils.isNull(dto.getFechaRegistro())) {
                        rs.setMensajeError(MensajesErrorEnum.ERROR_FECHA_REGISTRO_REQ);
                    }

                    break;

                case ACTUALIZACION:

                    if (ObjectUtils.isNullOrEmpty(dto.getFechaActualizacion())) {
                        rs.setMensajeError(MensajesErrorEnum.ERROR_FECHA_UPDATE_REQ);
                    }

                    if (ObjectUtils.isNull(dto.getId())) {
                        rs.setMensajeError(MensajesErrorEnum.ERROR_ID_REQ);
                    }

                    if (ObjectUtils.isNull(dto.getUsuarioModifico())) {
                        rs.setMensajeError(MensajesErrorEnum.ERROR_USUARIO_REQ);
                    }

                    break;

                case ELIMINACION:

                    if (ObjectUtils.isNull(dto.getId())) {
                        rs.setMensajeError(MensajesErrorEnum.ERROR_ID_REQ);
                    }

                    break;
            }
        }

        return rs;
    }

    @Override
    public void validarPersistencia(RamaEspPlanProgramaDTO dto, ResultadoDTO<RamaEspPlanProgramaDTO> resultado) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void validarActualizacion(RamaEspPlanProgramaDTO dto, ResultadoDTO<RamaEspPlanProgramaDTO> resultado) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void validarEliminacion(RamaEspPlanProgramaDTO dto, ResultadoDTO<RamaEspPlanProgramaDTO> resultado) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
