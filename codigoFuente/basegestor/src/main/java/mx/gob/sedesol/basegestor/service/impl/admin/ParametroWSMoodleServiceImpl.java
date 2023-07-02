package mx.gob.sedesol.basegestor.service.impl.admin;

import java.lang.reflect.Type;
import java.util.List;
import mx.gob.sedesol.basegestor.commons.dto.admin.ParametroWSMoodleDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.utils.MensajesSistemaEnum;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.TipoAccion;
import mx.gob.sedesol.basegestor.model.entities.admin.CatParametroWSMoodle;
import mx.gob.sedesol.basegestor.model.repositories.admin.ParametroWSMoodleRepo;
import mx.gob.sedesol.basegestor.service.ParametroWSMoodleService;
import mx.gob.sedesol.basegestor.service.admin.ComunValidacionService;
import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

/**
 * Implementacion de la interface
 *
 * @author eolf
 *
 */
@Service("parametroWSMoodleService")
@EnableAsync
public class ParametroWSMoodleServiceImpl extends ComunValidacionService<ParametroWSMoodleDTO> implements ParametroWSMoodleService {

    @Autowired
    private ParametroWSMoodleRepo parametroWSMoodleRepo;

    private ModelMapper modelMapper = new ModelMapper();

    private static Type tipoListaParametro = new TypeToken<List<ParametroWSMoodleDTO>>() {
    }.getType();

    private static final Logger logger = Logger.getLogger(ParametroWSMoodleServiceImpl.class);

    @Override
    public List<ParametroWSMoodleDTO> findAll() {
        logger.debug("[findAll]");
        return modelMapper.map(parametroWSMoodleRepo.findAll(), tipoListaParametro);
    }

    @Override
    public ParametroWSMoodleDTO buscarPorId(Integer id) {
        logger.debug("[buscarPorId]");
        ParametroWSMoodleDTO dto;
        CatParametroWSMoodle entidad = parametroWSMoodleRepo.findOne(id);
        if (ObjectUtils.isNull(entidad)) {
            dto = null;
        } else {
            dto = modelMapper.map(entidad, ParametroWSMoodleDTO.class);
        }
        return dto;
    }

    @Override
    public ResultadoDTO<ParametroWSMoodleDTO> guardar(ParametroWSMoodleDTO dto) {
        logger.debug("[guardar]");
        ResultadoDTO<ParametroWSMoodleDTO> resultado = sonDatosRequeridosValidos(TipoAccion.PERSISTENCIA, dto);
        if (resultado.getResultado().getValor()) {

            CatParametroWSMoodle entidad = modelMapper.map(dto, CatParametroWSMoodle.class);

            parametroWSMoodleRepo.save(entidad);

            //GUSTAVO --guardarBitacoraParametro(dto, String.valueOf(entidad.getIdParametroWSMoodle()));
            resultado.agregaMensaje(MensajesSistemaEnum.PARAMETROS_WSMOODLE_GUARDAR_EXITO.getId());
        }
        return resultado;
    }

    @Override
    public ResultadoDTO<ParametroWSMoodleDTO> actualizar(ParametroWSMoodleDTO dto) {
        logger.debug("[actualizar]");
        ResultadoDTO<ParametroWSMoodleDTO> resultado = sonDatosRequeridosValidos(TipoAccion.ACTUALIZACION, dto);
        if (resultado.getResultado().getValor()) {
            CatParametroWSMoodle entidad = modelMapper.map(dto, CatParametroWSMoodle.class);

            parametroWSMoodleRepo.save(entidad);

            //GUSTAVO --guardarBitacoraParametro(dto, String.valueOf(entidad.getIdParametroWSMoodle()));

            resultado.agregaMensaje(MensajesSistemaEnum.PARAMETROS_WSMOODLE_EDITAR_EXITO.getId());
        }
        return resultado;
    }

    @Override
    public ResultadoDTO<ParametroWSMoodleDTO> eliminar(ParametroWSMoodleDTO dto) {
        logger.debug("[eliminar]");
        ResultadoDTO<ParametroWSMoodleDTO> resultado = sonDatosRequeridosValidos(TipoAccion.ELIMINACION, dto);
        if (resultado.getResultado().getValor()) {

            CatParametroWSMoodle entidad = modelMapper.map(dto, CatParametroWSMoodle.class);

            parametroWSMoodleRepo.delete(entidad);

            //GUSTAVO --guardarBitacoraParametro(dto, String.valueOf(entidad.getIdParametroWSMoodle()));

            resultado.agregaMensaje(MensajesSistemaEnum.ADMIN_MSG_ELIMINACION_EXITOSA.getId());
        }
        return resultado;
    }

    /**
     * Metodo que encapsula los campos comunes
     *
     * @author nnm_eolf
     * @param dto
     * @param resultado
     */
    private void validarComunes(ParametroWSMoodleDTO dto, ResultadoDTO<ParametroWSMoodleDTO> resultado) {
        logger.debug("[validarComunes]");
        if (ObjectUtils.isNull(dto.getIdParametroWSMoodle())) {
            resultado.setMensajeError(MensajesSistemaEnum.PARAMETROS_WSMOODLE_ID_REQ);
        }
        if (ObjectUtils.isNull(dto.getHost())) {
            resultado.setMensajeError(MensajesSistemaEnum.PARAMETROS_WSMOODLE_HOST_REQ);
        }
        if (ObjectUtils.isNull(dto.getPath())) {
            resultado.setMensajeError(MensajesSistemaEnum.PARAMETROS_WSMOODLE_PATH_REQ);
        }
        if (ObjectUtils.isNull(dto.getService())) {
            resultado.setMensajeError(MensajesSistemaEnum.PARAMETROS_WSMOODLE_SERVICE_REQ);
        }
        if (ObjectUtils.isNull(dto.getUsername())) {
            resultado.setMensajeError(MensajesSistemaEnum.PARAMETROS_WSMOODLE_USERNAME_REQ);
        }
        if (ObjectUtils.isNull(dto.getPassword())) {
            resultado.setMensajeError(MensajesSistemaEnum.PARAMETROS_WSMOODLE_PASSWORD_REQ);
        }
        if (ObjectUtils.isNull(dto.getOuth())) {
            resultado.setMensajeError(MensajesSistemaEnum.PARAMETROS_WSMOODLE_OUTH_REQ);
        }
        if (ObjectUtils.isNull(dto.getServer())) {
            resultado.setMensajeError(MensajesSistemaEnum.PARAMETROS_WSMOODLE_SERVER_REQ);
        }
        if (ObjectUtils.isNull(dto.getActivo())) {
            resultado.setMensajeError(MensajesSistemaEnum.PARAMETROS_WSMOODLE_ACTIVO_REQ);
        }
        if (ObjectUtils.isNull(dto.getNombre())) {
            resultado.setMensajeError(MensajesSistemaEnum.PARAMETROS_WSMOODLE_NOMBRE_REQ);
        }
        if (ObjectUtils.isNull(dto.getDescripcion())) {
            resultado.setMensajeError(MensajesSistemaEnum.PARAMETROS_WSMOODLE_DESCRIPCION_REQ);
        }
        if (ObjectUtils.isNullOrEmpty(dto.getUsuarioModifico())) {
            resultado.setMensajeError(MensajesSistemaEnum.MSG_GENERAL_USUARIO_MODIFICO_REQ);
        }
    }

    @Override
    public void validarPersistencia(ParametroWSMoodleDTO dto, ResultadoDTO<ParametroWSMoodleDTO> resultado) {
        logger.debug("[validarPersistencia]");
        validarComunes(dto, resultado);
        if (ObjectUtils.isNull(dto.getFechaRegistro())) {
            resultado.setMensajeError(MensajesSistemaEnum.MSG_GENERAL_FECHA_REGISTRO_REQ);
        }
    }

    @Override
    public void validarActualizacion(ParametroWSMoodleDTO dto, ResultadoDTO<ParametroWSMoodleDTO> resultado) {
        logger.debug("[validarActualizacion]");
        validarComunes(dto, resultado);
        if (ObjectUtils.isNull(dto.getIdParametroWSMoodle())) {
            resultado.setMensajeError(MensajesSistemaEnum.PARAMETROS_WSMOODLE_ID_REQ);
        }
        if (ObjectUtils.isNull(dto.getFechaActualizacion())) {
            resultado.setMensajeError(MensajesSistemaEnum.MSG_GENERAL_FECHA_EDICION_REQ);
        }
    }

    @Override
    public void validarEliminacion(ParametroWSMoodleDTO dto, ResultadoDTO<ParametroWSMoodleDTO> resultado) {
        logger.debug("[validarEliminacion]");
        if (ObjectUtils.isNull(dto.getIdParametroWSMoodle())) {
            resultado.setMensajeError(MensajesSistemaEnum.PARAMETROS_WSMOODLE_ID_REQ);
        }
        if (ObjectUtils.isNull(dto.getNombre())) {
            resultado.setMensajeError(MensajesSistemaEnum.PARAMETROS_WSMOODLE_NOMBRE_REQ);
        }
        if (ObjectUtils.isNull(dto.getFechaActualizacion())) {
            resultado.setMensajeError(MensajesSistemaEnum.MSG_GENERAL_FECHA_EDICION_REQ);
        }
        if (ObjectUtils.isNullOrEmpty(dto.getUsuarioModifico())) {
            resultado.setMensajeError(MensajesSistemaEnum.MSG_GENERAL_USUARIO_MODIFICO_REQ);
        }
    }

    @Override
    public ParametroWSMoodleDTO obtenerMoodleActivo() {
        logger.debug("[obtenerMoodleActivo]");
        ParametroWSMoodleDTO dto = new ParametroWSMoodleDTO();
        List<ParametroWSMoodleDTO> listParametros = findAll();
        if (listParametros != null) {
            for (ParametroWSMoodleDTO parametroX : listParametros) {
                if (parametroX != null && parametroX.getActivo()) {
                    dto = parametroX;
                    break;
                }
            }
        }
        return dto;
    }

}
