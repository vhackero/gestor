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
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.ProgramaSocialDTO;
import mx.gob.sedesol.basegestor.commons.utils.MensajesSistemaEnum;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.TipoAccion;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.CatProgramaSocial;
import mx.gob.sedesol.basegestor.model.repositories.gestionescolar.ProgramaSocialRepo;
import mx.gob.sedesol.basegestor.service.admin.ComunValidacionService;
import mx.gob.sedesol.basegestor.service.gestionescolar.ProgramaSocialService;

@Service("programaSocialService")
@EnableAsync
public class ProgramaSocialServiceImpl extends ComunValidacionService<ProgramaSocialDTO>
        implements ProgramaSocialService {

    private static final Logger logger = Logger.getLogger(ProgramaSocialServiceImpl.class);

    @Autowired
    private ProgramaSocialRepo programaSocialRepo;

    private ModelMapper modelMapper = new ModelMapper();

    private static Type tipoListaPrograma = new TypeToken<List<ProgramaSocialDTO>>() {
    }.getType();

    @Override
    public List<ProgramaSocialDTO> findAll() {
        return modelMapper.map(programaSocialRepo.findAll(), tipoListaPrograma);
    }

    @Override
    public ProgramaSocialDTO buscarPorId(Integer id) {
        ProgramaSocialDTO dto;
        CatProgramaSocial entidad = programaSocialRepo.findOne(id);
        if (ObjectUtils.isNull(entidad)) {
            dto = null;
        } else {
            dto = modelMapper.map(entidad, ProgramaSocialDTO.class);
        }
        return dto;
    }

    @Override
    public ResultadoDTO<ProgramaSocialDTO> guardar(ProgramaSocialDTO dto) {
        ResultadoDTO<ProgramaSocialDTO> resultado = sonDatosRequeridosValidos(TipoAccion.PERSISTENCIA, dto);
        if (resultado.getResultado().getValor()) {

            try {
                resultado = new ResultadoDTO<>();
                CatProgramaSocial entidad = modelMapper.map(dto, CatProgramaSocial.class);
                programaSocialRepo.save(entidad);
                resultado.setDto(modelMapper.map(entidad, ProgramaSocialDTO.class));

        //GUSTAVO --guardarBitacora(dto.getBitacoraDTO(), String.valueOf(entidad.getIdProgramaSocial()));
                resultado.agregaMensaje(MensajesSistemaEnum.ADMIN_MSG_GUARDADO_EXITOSO.getId());
            } catch (Exception e) {
                resultado.setMensajeError(MensajesSistemaEnum.ADMIN_MSG_GUARDADO_FALLIDO);
                logger.error(e.getMessage(), e);
            }
        }

        return resultado;
    }

    @Override
    public ResultadoDTO<ProgramaSocialDTO> actualizar(ProgramaSocialDTO dto) {
        ResultadoDTO<ProgramaSocialDTO> resultado = sonDatosRequeridosValidos(TipoAccion.ACTUALIZACION, dto);
        if (resultado.getResultado().getValor()) {

            try {
                resultado = new ResultadoDTO<>();
                CatProgramaSocial entidad = modelMapper.map(dto, CatProgramaSocial.class);
                programaSocialRepo.saveAndFlush(entidad);
                resultado.setDto(modelMapper.map(entidad, ProgramaSocialDTO.class));

        //GUSTAVO --guardarBitacora(dto.getBitacoraDTO(), String.valueOf(entidad.getIdProgramaSocial()));
                resultado.agregaMensaje(MensajesSistemaEnum.ADMIN_MSG_ACTUALIZACION_EXITOSA.getId());
            } catch (Exception e) {
                resultado.setMensajeError(MensajesSistemaEnum.ADMIN_MSG_ACTUALIZACION_FALLIDA);
                logger.error(e.getMessage(), e);
            }
        }

        return resultado;
    }

    @Override
    public ResultadoDTO<ProgramaSocialDTO> eliminar(ProgramaSocialDTO dto) {
        ResultadoDTO<ProgramaSocialDTO> resultado = sonDatosRequeridosValidos(TipoAccion.ELIMINACION, dto);
        if (resultado.getResultado().getValor()) {

            try {
                resultado = new ResultadoDTO<>();
                CatProgramaSocial entidad = programaSocialRepo.findOne(dto.getIdProgramaSocial());
                programaSocialRepo.delete(entidad);
                resultado.setDto(modelMapper.map(entidad, ProgramaSocialDTO.class));

        //GUSTAVO --guardarBitacora(dto.getBitacoraDTO(), String.valueOf(entidad.getIdProgramaSocial()));
                resultado.agregaMensaje(MensajesSistemaEnum.ADMIN_MSG_ELIMINACION_EXITOSA.getId());
            } catch (Exception e) {
                resultado.setMensajeError(MensajesSistemaEnum.ADMIN_MSG_ELIMINACION_FALLIDA);
                logger.error(e.getMessage(), e);
            }
        }
        return resultado;
    }

    @Override
    public List<ProgramaSocialDTO> obtenerProgramasPorUnidad(Integer idUnidadResponsable) {
        return modelMapper.map(programaSocialRepo.obtenerProgramasPorUnidad(idUnidadResponsable), tipoListaPrograma);
    }

    @Override
    public boolean findDuplicateClave(ProgramaSocialDTO programaSocialDTO) {
        return programaSocialRepo.findAll().stream().anyMatch(o -> o.getClave().equals(programaSocialDTO.getClave()))
                && !programaSocialRepo.findAll().stream().anyMatch(o -> o.getNombre().equals(programaSocialDTO.getNombre()));
    }

    @Override
    public void validarPersistencia(ProgramaSocialDTO dto, ResultadoDTO<ProgramaSocialDTO> resultado) {
        if (ObjectUtils.isNullOrEmpty(dto.getClave())) {
            resultado.setMensajeError(MensajesSistemaEnum.MSG_GENERAL_CLAVE_REQ);
        }
        if (ObjectUtils.isNullOrEmpty(dto.getNombre())) {
            resultado.setMensajeError(MensajesSistemaEnum.MSG_GENERAL_NOMBRE_REQ);
        }
        if (ObjectUtils.isNull(dto.getUnidadResponsable())
                || ObjectUtils.isNullOrCero(dto.getUnidadResponsable().getIdUnidadResponsable())) {
            resultado.setMensajeError(MensajesSistemaEnum.PROG_SOC_UNIDAD_RESPONSABLE_REQ);
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
    public void validarActualizacion(ProgramaSocialDTO dto, ResultadoDTO<ProgramaSocialDTO> resultado) {
        validarPersistencia(dto, resultado);
        if (ObjectUtils.isNullOrCero(dto.getIdProgramaSocial())) {
            resultado.setMensajeError(MensajesSistemaEnum.MSG_GENERAL_ID_REQ);
        }
        if (ObjectUtils.isNull(dto.getFechaActualizacion())) {
            resultado.setMensajeError(MensajesSistemaEnum.MSG_GENERAL_FECHA_EDICION_REQ);
        }
    }

    @Override
    public void validarEliminacion(ProgramaSocialDTO dto, ResultadoDTO<ProgramaSocialDTO> resultado) {
        if (ObjectUtils.isNullOrCero(dto.getIdProgramaSocial())) {
            resultado.setMensajeError(MensajesSistemaEnum.MSG_GENERAL_ID_REQ);
        }
        if (ObjectUtils.isNullOrCero(dto.getUsuarioModifico())) {
            resultado.setMensajeError(MensajesSistemaEnum.MSG_GENERAL_USUARIO_MODIFICO_REQ);
        }
    }

}
