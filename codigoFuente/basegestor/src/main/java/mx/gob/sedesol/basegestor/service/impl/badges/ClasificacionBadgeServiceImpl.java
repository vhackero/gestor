package mx.gob.sedesol.basegestor.service.impl.badges;

import java.lang.reflect.Type;
import java.util.List;

import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import mx.gob.sedesol.basegestor.commons.constantes.ConstantesBitacora;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.badges.ClasificacionBadgeDTO;
import mx.gob.sedesol.basegestor.commons.utils.MensajesSistemaEnum;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.TipoAccion;
import mx.gob.sedesol.basegestor.model.entities.badges.CatClasificacionBadge;
import mx.gob.sedesol.basegestor.model.repositories.badges.ClasificacionBadgeRepo;
import mx.gob.sedesol.basegestor.service.admin.ComunValidacionService;
import mx.gob.sedesol.basegestor.service.badges.ClasificacionBadgeService;

/**
 * Implementacion de la interface
 *
 * @author nnm_eolf
 *
 */
@Service("clasificacionBadgeService")
@EnableAsync
public class ClasificacionBadgeServiceImpl extends ComunValidacionService<ClasificacionBadgeDTO> implements ClasificacionBadgeService {

    @Autowired
    private ClasificacionBadgeRepo clasificacionBadgeRepo;

    private ModelMapper modelMapper = new ModelMapper();

    private static Type tipoListaParametro = new TypeToken<List<ClasificacionBadgeDTO>>() {
    }.getType();

    private static final Logger LOG = Logger.getLogger(ClasificacionBadgeServiceImpl.class);

    @Override
    public List<ClasificacionBadgeDTO> findAll() {
        LOG.debug("[findAll]");
        return modelMapper.map(clasificacionBadgeRepo.findAll(), tipoListaParametro);
    }

    @Override
    public ClasificacionBadgeDTO buscarPorId(Integer id) {
        LOG.debug("[buscarPorId]");
        ClasificacionBadgeDTO dto;
        CatClasificacionBadge entidad = clasificacionBadgeRepo.findOne(id);
        if (ObjectUtils.isNull(entidad)) {
            dto = null;
        } else {
            dto = modelMapper.map(entidad, ClasificacionBadgeDTO.class);
        }
        return dto;
    }

    @Override
    public ResultadoDTO<ClasificacionBadgeDTO> guardar(ClasificacionBadgeDTO dto) {
        LOG.debug("[guardar]");
        ResultadoDTO<ClasificacionBadgeDTO> resultado = sonDatosRequeridosValidos(TipoAccion.PERSISTENCIA, dto);
        if (resultado.getResultado().getValor()) {

            CatClasificacionBadge entidad = modelMapper.map(dto, CatClasificacionBadge.class);
            clasificacionBadgeRepo.save(entidad);
            resultado.setDto(modelMapper.map(entidad, ClasificacionBadgeDTO.class));
        //GUSTAVO --guardarBitacoraClasificacion(dto, String.valueOf(entidad.getIdClasificacionBadge()));
            resultado.agregaMensaje(MensajesSistemaEnum.CLASIFICACION_BADGE_GUARDAR_EXITO.getId());
        }
        return resultado;
    }

    @Override
    public ResultadoDTO<ClasificacionBadgeDTO> actualizar(ClasificacionBadgeDTO dto) {
        LOG.debug("[actualizar]");
        ResultadoDTO<ClasificacionBadgeDTO> resultado = sonDatosRequeridosValidos(TipoAccion.ACTUALIZACION, dto);
        if (resultado.getResultado().getValor()) {
            CatClasificacionBadge entidad = modelMapper.map(dto, CatClasificacionBadge.class);

            clasificacionBadgeRepo.save(entidad);
            resultado.setDto(modelMapper.map(entidad, ClasificacionBadgeDTO.class));
        //GUSTAVO --guardarBitacoraClasificacion(dto, String.valueOf(entidad.getIdClasificacionBadge()));

            resultado.agregaMensaje(MensajesSistemaEnum.CLASIFICACION_BADGE_EDITAR_EXITO.getId());
        }
        return resultado;
    }

    @Override
    public ResultadoDTO<ClasificacionBadgeDTO> eliminar(ClasificacionBadgeDTO dto) {
        LOG.debug("[eliminar]");
        ResultadoDTO<ClasificacionBadgeDTO> resultado = sonDatosRequeridosValidos(TipoAccion.ELIMINACION, dto);
        if (resultado.getResultado().getValor()) {

            CatClasificacionBadge entidad = modelMapper.map(dto, CatClasificacionBadge.class);

            clasificacionBadgeRepo.delete(entidad);

        //GUSTAVO --guardarBitacoraClasificacion(dto, String.valueOf(entidad.getIdClasificacionBadge()));

            resultado.agregaMensaje(MensajesSistemaEnum.CLASIFICACION_BADGE_ELIMINAR_EXITO.getId());
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
    private void validarComunes(ClasificacionBadgeDTO dto, ResultadoDTO<ClasificacionBadgeDTO> resultado) {
        LOG.debug("[validarComunes]");
        if (ObjectUtils.isNullOrEmpty(dto.getNombre())) {
            resultado.setMensajeError(MensajesSistemaEnum.CLASIFICACION_BADGE_NOMBRE_REQ);
        }
        if (ObjectUtils.isNull(dto.getUsuarioModifico())) {
            resultado.setMensajeError(MensajesSistemaEnum.MSG_GENERAL_USUARIO_MODIFICO_REQ);
        }
        if (ObjectUtils.isNull(dto.getIdEstatus())) {
            resultado.setMensajeError(MensajesSistemaEnum.CLASIFICACION_BADGE_ESTATUS_REQ);
        }
    }

    @Override
    public void validarPersistencia(ClasificacionBadgeDTO dto, ResultadoDTO<ClasificacionBadgeDTO> resultado) {
        LOG.debug("[validarPersistencia]");
        validarComunes(dto, resultado);
        if (ObjectUtils.isNull(dto.getFechaRegistro())) {
            resultado.setMensajeError(MensajesSistemaEnum.MSG_GENERAL_FECHA_REGISTRO_REQ);
        }
    }

    @Override
    public void validarActualizacion(ClasificacionBadgeDTO dto, ResultadoDTO<ClasificacionBadgeDTO> resultado) {
        LOG.debug("[validarActualizacion]");
        validarComunes(dto, resultado);
        if (ObjectUtils.isNull(dto.getIdClasificacionBadge())) {
            resultado.setMensajeError(MensajesSistemaEnum.CLASIFICACION_BADGE_ID_REQ);
        }
        if (ObjectUtils.isNull(dto.getFechaActualizacion())) {
            resultado.setMensajeError(MensajesSistemaEnum.MSG_GENERAL_FECHA_EDICION_REQ);
        }
    }

    @Override
    public void validarEliminacion(ClasificacionBadgeDTO dto, ResultadoDTO<ClasificacionBadgeDTO> resultado) {
        LOG.debug("[validarEliminacion]");
        if (ObjectUtils.isNull(dto.getIdClasificacionBadge())) {
            resultado.setMensajeError(MensajesSistemaEnum.CLASIFICACION_BADGE_ID_REQ);
        }
        if (ObjectUtils.isNullOrEmpty(dto.getNombre())) {
            resultado.setMensajeError(MensajesSistemaEnum.CLASIFICACION_BADGE_NOMBRE_REQ);
        }
        if (ObjectUtils.isNull(dto.getUsuarioModifico())) {
            resultado.setMensajeError(MensajesSistemaEnum.MSG_GENERAL_USUARIO_MODIFICO_REQ);
        }
    }
}
