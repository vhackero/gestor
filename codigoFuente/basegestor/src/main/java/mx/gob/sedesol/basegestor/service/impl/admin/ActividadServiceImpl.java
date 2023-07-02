package mx.gob.sedesol.basegestor.service.impl.admin;

import java.lang.reflect.Type;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import mx.gob.sedesol.basegestor.commons.dto.admin.ActividadDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.utils.MensajesSistemaEnum;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.TipoAccion;
import mx.gob.sedesol.basegestor.mongo.model.Actividad;
import mx.gob.sedesol.basegestor.mongo.repositories.ActividadRepo;
import mx.gob.sedesol.basegestor.service.admin.ActividadService;
import mx.gob.sedesol.basegestor.service.admin.ComunValidacionService;

@Service("actividadService")
public class ActividadServiceImpl extends ComunValidacionService<ActividadDTO> implements ActividadService {
    
    @Autowired
    private ActividadRepo actividadRepo;
    
    ModelMapper modelMapper = new ModelMapper();
    
    private static Type tipoListaActividad = new TypeToken<List<ActividadDTO>>() {
    }.getType();
    
    @Override
    public List<ActividadDTO> findAll() {
        return modelMapper.map(actividadRepo.findAll(), tipoListaActividad);
    }
    
    @Override
    public ActividadDTO buscarPorId(String id) {
        Actividad actividad = actividadRepo.findOne(id);
        if (ObjectUtils.isNull(actividad)) {
            return null;
        } else {
            return modelMapper.map(actividad, ActividadDTO.class);
        }
    }
    
    @Override
    public ResultadoDTO<ActividadDTO> guardar(ActividadDTO dto) {
        ResultadoDTO<ActividadDTO> resultado = sonDatosRequeridosValidos(TipoAccion.PERSISTENCIA, dto);
        if (resultado.getResultado().getValor()) {
            
            Actividad actividad = modelMapper.map(dto, Actividad.class);
            
            actividadRepo.save(actividad);
            
            //GUSTAVO --guardarBitacora(dto.getBitacoraDTO(), actividad.getId());
            resultado.agregaMensaje(MensajesSistemaEnum.ACTIVIDADES_GUARDAR_EXITO.getId());
        }
        return resultado;
    }
    
    @Override
    public ResultadoDTO<ActividadDTO> actualizar(ActividadDTO dto) {
        ResultadoDTO<ActividadDTO> resultado = sonDatosRequeridosValidos(TipoAccion.ACTUALIZACION, dto);
        if (resultado.getResultado().getValor()) {
            
            Actividad actividad = modelMapper.map(dto, Actividad.class);
            
            actividadRepo.save(actividad);
            
            //GUSTAVO --guardarBitacora(dto.getBitacoraDTO(), actividad.getId());
            
            resultado.agregaMensaje(MensajesSistemaEnum.ACTIVIDADES_EDITAR_EXITO.getId());
        }
        return resultado;
    }
    
    @Override
    public ResultadoDTO<ActividadDTO> eliminar(ActividadDTO dto) {
        ResultadoDTO<ActividadDTO> resultado = sonDatosRequeridosValidos(TipoAccion.ELIMINACION, dto);
        if (resultado.getResultado().getValor()) {
            
            Actividad actividad = modelMapper.map(dto, Actividad.class);
            
            actividadRepo.delete(actividad);
            
            //GUSTAVO --guardarBitacora(dto.getBitacoraDTO(), actividad.getId());
            
            resultado.agregaMensaje(MensajesSistemaEnum.ADMIN_MSG_ELIMINACION_EXITOSA.getId());
        }
        return resultado;
    }
    
    @Override
    public void validarPersistencia(ActividadDTO dto, ResultadoDTO<ActividadDTO> resultado) {
        validacionComun(dto, resultado);
    }
    
    @Override
    public void validarActualizacion(ActividadDTO dto, ResultadoDTO<ActividadDTO> resultado) {
        if (ObjectUtils.isNullOrEmpty(dto.getId())) {
            resultado.setMensajeError(MensajesSistemaEnum.ACTIVIDADES_ID_REQ);
        }
        validacionComun(dto, resultado);
        if (ObjectUtils.isNull(dto.getFechaTermino())) {
            resultado.setMensajeError(MensajesSistemaEnum.ACTIVIDADES_FECHA_TERMINO_REQ);
        }
    }
    
    @Override
    public void validarEliminacion(ActividadDTO dto, ResultadoDTO<ActividadDTO> resultado) {
        if (ObjectUtils.isNullOrEmpty(dto.getId())) {
            resultado.setMensajeError(MensajesSistemaEnum.ACTIVIDADES_ID_REQ);
        }
    }
    
    private void validacionComun(ActividadDTO dto, ResultadoDTO<ActividadDTO> resultado) {
        if (ObjectUtils.isNullOrEmpty(dto.getActividad())) {
            resultado.setMensajeError(MensajesSistemaEnum.ACTIVIDADES_ACTIVIDAD_REQ);
        }
        if (ObjectUtils.isNullOrEmpty(dto.getEstatus())) {
            resultado.setMensajeError(MensajesSistemaEnum.ACTIVIDADES_ESTATUS_REQ);
        }
        if (ObjectUtils.isNull(dto.getFechaInicio())) {
            resultado.setMensajeError(MensajesSistemaEnum.ACTIVIDADES_FECHA_INICIO_REQ);
        }
        if (ObjectUtils.isNull(dto.getFechaFin())) {
            resultado.setMensajeError(MensajesSistemaEnum.ACTIVIDADES_FECHA_FIN_REQ);
        }
        if (ObjectUtils.isNull(dto.getFechaRegistro())) {
            resultado.setMensajeError(MensajesSistemaEnum.ACTIVIDADES_FECHA_REGISTRO_REQ);
        }
        /*if (ObjectUtils.isNullOrCero(dto.getBitacoraDTO().getIdUsuario())) {
            resultado.setMensajeError(MensajesSistemaEnum.ACTIVIDADES_ID_USUARIO_REQ);
        }*/
    }
    
    @Override
    public List<ActividadDTO> buscarPorUsuario(long idUsuario) {
        return modelMapper.map(actividadRepo.findByIdUsuario(idUsuario), tipoListaActividad);
    }
}
