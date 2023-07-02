package mx.gob.sedesol.basegestor.service.impl.gestionescolar;

import java.lang.reflect.Type;
import java.util.List;

import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.UnidadResponsableDTO;
import mx.gob.sedesol.basegestor.commons.utils.MensajesSistemaEnum;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.TipoAccion;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.CatUnidadResponsable;
import mx.gob.sedesol.basegestor.model.repositories.gestionescolar.UnidadResponsableRepo;
import mx.gob.sedesol.basegestor.service.admin.ComunValidacionService;
import mx.gob.sedesol.basegestor.service.gestionescolar.UnidadResponsableService;

@Service("unidadResponsableService")
@EnableAsync
public class UnidadResponsableServiceImpl extends ComunValidacionService<UnidadResponsableDTO>
        implements UnidadResponsableService {

    private static final Logger logger = Logger.getLogger(UnidadResponsableServiceImpl.class);

    @Autowired
    private UnidadResponsableRepo unidadResponsableRepo;

    private ModelMapper modelMapper = new ModelMapper();

    private static Type tipoListaUnidad = new TypeToken<List<UnidadResponsableDTO>>() {
    }.getType();

    @Override
    public List<UnidadResponsableDTO> findAll() {
        return modelMapper.map(unidadResponsableRepo.findAll(), tipoListaUnidad);
    }

    @Override
    public UnidadResponsableDTO buscarPorId(Integer id) {
        UnidadResponsableDTO dto;
        CatUnidadResponsable entidad = unidadResponsableRepo.findOne(id);
        if (ObjectUtils.isNull(entidad)) {
            dto = null;
        } else {
            dto = modelMapper.map(entidad, UnidadResponsableDTO.class);
        }
        return dto;
    }

    @Override
    public ResultadoDTO<UnidadResponsableDTO> guardar(UnidadResponsableDTO dto) {
        ResultadoDTO<UnidadResponsableDTO> resultado = sonDatosRequeridosValidos(TipoAccion.PERSISTENCIA, dto);
        if (resultado.getResultado().getValor()) {

            try {
                resultado = new ResultadoDTO<>();
                CatUnidadResponsable entidad = modelMapper.map(dto, CatUnidadResponsable.class);
                unidadResponsableRepo.save(entidad);
                resultado.setDto(modelMapper.map(entidad, UnidadResponsableDTO.class));

        //GUSTAVO --guardarBitacora(dto.getBitacoraDTO(), String.valueOf(entidad.getIdUnidadResponsable()));
                resultado.agregaMensaje(MensajesSistemaEnum.ADMIN_MSG_GUARDADO_EXITOSO.getId());
            } catch (Exception e) {
                resultado.setMensajeError(MensajesSistemaEnum.ADMIN_MSG_GUARDADO_FALLIDO);
                logger.error(e.getMessage(), e);
            }
        }

        return resultado;
    }

    @Override
    public ResultadoDTO<UnidadResponsableDTO> actualizar(UnidadResponsableDTO dto) {
        ResultadoDTO<UnidadResponsableDTO> resultado = sonDatosRequeridosValidos(TipoAccion.ACTUALIZACION, dto);
        if (resultado.getResultado().getValor()) {

            try {
                resultado = new ResultadoDTO<>();
                CatUnidadResponsable entidad = modelMapper.map(dto, CatUnidadResponsable.class);
                unidadResponsableRepo.saveAndFlush(entidad);
                resultado.setDto(modelMapper.map(entidad, UnidadResponsableDTO.class));

        //GUSTAVO --guardarBitacora(dto.getBitacoraDTO(), String.valueOf(entidad.getIdUnidadResponsable()));
                resultado.agregaMensaje(MensajesSistemaEnum.ADMIN_MSG_ACTUALIZACION_EXITOSA.getId());
            } catch (Exception e) {
                resultado.setMensajeError(MensajesSistemaEnum.ADMIN_MSG_ACTUALIZACION_FALLIDA);
                logger.error(e.getMessage(), e);
            }
        }

        return resultado;
    }

    @Override
    public ResultadoDTO<UnidadResponsableDTO> eliminar(UnidadResponsableDTO dto) {
        ResultadoDTO<UnidadResponsableDTO> resultado = sonDatosRequeridosValidos(TipoAccion.ELIMINACION, dto);
        if (resultado.getResultado().getValor()) {

            try {
                resultado = new ResultadoDTO<>();
                CatUnidadResponsable entidad = unidadResponsableRepo.findOne(dto.getIdUnidadResponsable());
                unidadResponsableRepo.delete(entidad);
                resultado.setDto(modelMapper.map(entidad, UnidadResponsableDTO.class));

        //GUSTAVO --guardarBitacora(dto.getBitacoraDTO(), String.valueOf(entidad.getIdUnidadResponsable()));
                resultado.agregaMensaje(MensajesSistemaEnum.ADMIN_MSG_ELIMINACION_EXITOSA.getId());
            } catch (Exception e) {
                resultado.setMensajeError(MensajesSistemaEnum.ADMIN_MSG_ELIMINACION_FALLIDA);
                logger.error(e.getMessage(), e);
            }
        }

        return resultado;
    }

    @Override
    public void validarPersistencia(UnidadResponsableDTO dto, ResultadoDTO<UnidadResponsableDTO> resultado) {
        if (ObjectUtils.isNullOrEmpty(dto.getClave())) {
            resultado.setMensajeError(MensajesSistemaEnum.MSG_GENERAL_CLAVE_REQ);
        }
        if (ObjectUtils.isNullOrEmpty(dto.getNombre())) {
            resultado.setMensajeError(MensajesSistemaEnum.MSG_GENERAL_NOMBRE_REQ);
        }
        if (ObjectUtils.isNull(dto.getActivo())) {
            resultado.setMensajeError(MensajesSistemaEnum.MSG_GENERAL_ESTA_ACTIVO_REQ);
        }
        if (ObjectUtils.isNull(dto.getFechaRegistro())) {
            resultado.setMensajeError(MensajesSistemaEnum.MSG_GENERAL_FECHA_REGISTRO_REQ);
        }
        if (ObjectUtils.isNullOrCero(dto.getUsuarioModifico())) {
            resultado.setMensajeError(MensajesSistemaEnum.MSG_GENERAL_USUARIO_MODIFICO_REQ);
        }
    }

    @Override
    public void validarActualizacion(UnidadResponsableDTO dto, ResultadoDTO<UnidadResponsableDTO> resultado) {
        validarPersistencia(dto, resultado);
        if (ObjectUtils.isNullOrCero(dto.getIdUnidadResponsable())) {
            resultado.setMensajeError(MensajesSistemaEnum.MSG_GENERAL_ID_REQ);
        }
        if (ObjectUtils.isNull(dto.getFechaActualizacion())) {
            resultado.setMensajeError(MensajesSistemaEnum.MSG_GENERAL_FECHA_EDICION_REQ);
        }
    }

    @Override
    public void validarEliminacion(UnidadResponsableDTO dto, ResultadoDTO<UnidadResponsableDTO> resultado) {
        if (ObjectUtils.isNullOrCero(dto.getIdUnidadResponsable())) {
            resultado.setMensajeError(MensajesSistemaEnum.MSG_GENERAL_ID_REQ);
        }
        if (ObjectUtils.isNullOrCero(dto.getUsuarioModifico())) {
            resultado.setMensajeError(MensajesSistemaEnum.MSG_GENERAL_USUARIO_MODIFICO_REQ);
        }
    }

}
