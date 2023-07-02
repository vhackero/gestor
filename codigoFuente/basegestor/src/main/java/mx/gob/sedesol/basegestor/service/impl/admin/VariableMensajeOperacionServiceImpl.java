package mx.gob.sedesol.basegestor.service.impl.admin;

import java.lang.reflect.Type;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.gob.sedesol.basegestor.commons.constantes.ConstantesBitacora;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.VariableMensajeOperacionDTO;
import mx.gob.sedesol.basegestor.commons.utils.MensajesSistemaEnum;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.TipoAccion;
import mx.gob.sedesol.basegestor.model.entities.admin.RelVariableMensajeOperacion;
import mx.gob.sedesol.basegestor.model.repositories.admin.VariableMensajeOperacionRepo;
import mx.gob.sedesol.basegestor.service.admin.ComunValidacionService;
import mx.gob.sedesol.basegestor.service.admin.VariableMensajeOperacionService;

@Service("variableMensajeOperacionService")
public class VariableMensajeOperacionServiceImpl extends ComunValidacionService<VariableMensajeOperacionDTO>
        implements VariableMensajeOperacionService {

    @Autowired
    private VariableMensajeOperacionRepo variableMensajeOperacionRepo;

    private ModelMapper modelMapper = new ModelMapper();

    private static Type tipoListaVariableMensaje = new TypeToken<List<VariableMensajeOperacionDTO>>() {
    }.getType();

    @Override
    public List<VariableMensajeOperacionDTO> findAll() {
        return modelMapper.map(variableMensajeOperacionRepo.findAll(), tipoListaVariableMensaje);
    }

    @Override
    public VariableMensajeOperacionDTO buscarPorId(Integer id) {
        VariableMensajeOperacionDTO dto;
        RelVariableMensajeOperacion variable = variableMensajeOperacionRepo.findOne(id);
        if (ObjectUtils.isNull(variable)) {
            dto = null;
        } else {
            dto = modelMapper.map(variable, VariableMensajeOperacionDTO.class);
        }
        return dto;
    }

    @Override
    public ResultadoDTO<VariableMensajeOperacionDTO> guardar(VariableMensajeOperacionDTO dto) {
        ResultadoDTO<VariableMensajeOperacionDTO> resultado = sonDatosRequeridosValidos(TipoAccion.PERSISTENCIA, dto);
        if (resultado.getResultado().getValor()) {

            RelVariableMensajeOperacion entidad = modelMapper.map(dto, RelVariableMensajeOperacion.class);

            variableMensajeOperacionRepo.save(entidad);

    //GUSTAVO --guardarBitacora(dto.getBitacoraDTO(), String.valueOf(entidad.getIdVariableMensajeOperacion()));
            resultado.agregaMensaje(MensajesSistemaEnum.VAR_MSG_GUARDAR_EXITO.getId());
        }
        return resultado;
    }

    @Override
    public ResultadoDTO<VariableMensajeOperacionDTO> actualizar(VariableMensajeOperacionDTO dto) {
        ResultadoDTO<VariableMensajeOperacionDTO> resultado = sonDatosRequeridosValidos(TipoAccion.ACTUALIZACION, dto);
        if (resultado.getResultado().getValor()) {

            RelVariableMensajeOperacion entidad = modelMapper.map(dto, RelVariableMensajeOperacion.class);

            variableMensajeOperacionRepo.save(entidad);

    //GUSTAVO --guardarBitacora(dto.getBitacoraDTO(), String.valueOf(entidad.getIdVariableMensajeOperacion()));

            resultado.agregaMensaje(MensajesSistemaEnum.VAR_MSG_EDITAR_EXITO.getId());
        }
        return resultado;
    }

    @Override
    public ResultadoDTO<VariableMensajeOperacionDTO> eliminar(VariableMensajeOperacionDTO dto) {
        ResultadoDTO<VariableMensajeOperacionDTO> resultado = sonDatosRequeridosValidos(TipoAccion.ELIMINACION, dto);
        if (resultado.getResultado().getValor()) {
            RelVariableMensajeOperacion entidad = modelMapper.map(dto, RelVariableMensajeOperacion.class);

            variableMensajeOperacionRepo.delete(entidad);

    //GUSTAVO --guardarBitacora(dto.getBitacoraDTO(), String.valueOf(entidad.getIdVariableMensajeOperacion()));
            resultado.agregaMensaje(MensajesSistemaEnum.ADMIN_MSG_ELIMINACION_EXITOSA.getId());
        }
        return resultado;
    }

    @Override
    public void validarPersistencia(VariableMensajeOperacionDTO dto,
            ResultadoDTO<VariableMensajeOperacionDTO> resultado) {
        if (ObjectUtils.isNull(dto.getFuncionalidadDTO())
                || ObjectUtils.isNullOrCero(dto.getFuncionalidadDTO().getIdFuncionalidad())) {
            resultado.setMensajeError(MensajesSistemaEnum.VAR_MSG_OPERACION_REQ);
        }
        if (ObjectUtils.isNullOrEmpty(dto.getDescripcion())) {
            resultado.setMensajeError(MensajesSistemaEnum.VAR_MSG_DESCRIPCION_REQ);
        }
        if (ObjectUtils.isNullOrEmpty(dto.getClave())) {
            resultado.setMensajeError(MensajesSistemaEnum.VAR_MSG_CLAVE_REQ);
        }
        if (ObjectUtils.isNull(dto.getFechaRegistro())) {
            resultado.setMensajeError(MensajesSistemaEnum.VAR_MSG_FECHA_REG_REQ);
        }
        if (ObjectUtils.isNullOrEmpty(dto.getUsuarioModifico())) {
            resultado.setMensajeError(MensajesSistemaEnum.VAR_MSG_USUARIO_REQ);
        }
    }

    @Override
    public void validarActualizacion(VariableMensajeOperacionDTO dto,
            ResultadoDTO<VariableMensajeOperacionDTO> resultado) {
        if (ObjectUtils.isNullOrCero(dto.getIdVariableMensajeOperacion())) {
            resultado.setMensajeError(MensajesSistemaEnum.VAR_MSG_ID_REQ);
        }
        if (ObjectUtils.isNull(dto.getFuncionalidadDTO())
                || ObjectUtils.isNullOrCero(dto.getFuncionalidadDTO().getIdFuncionalidad())) {
            resultado.setMensajeError(MensajesSistemaEnum.VAR_MSG_OPERACION_REQ);
        }
        if (ObjectUtils.isNullOrEmpty(dto.getDescripcion())) {
            resultado.setMensajeError(MensajesSistemaEnum.VAR_MSG_DESCRIPCION_REQ);
        }
        if (ObjectUtils.isNullOrEmpty(dto.getClave())) {
            resultado.setMensajeError(MensajesSistemaEnum.VAR_MSG_CLAVE_REQ);
        }
        if (ObjectUtils.isNull(dto.getFechaActualizacion())) {
            resultado.setMensajeError(MensajesSistemaEnum.VAR_MSG_FECHA_EDI_REQ);
        }
        if (ObjectUtils.isNullOrEmpty(dto.getUsuarioModifico())) {
            resultado.setMensajeError(MensajesSistemaEnum.VAR_MSG_USUARIO_REQ);
        }
    }

    @Override
    public void validarEliminacion(VariableMensajeOperacionDTO dto,
            ResultadoDTO<VariableMensajeOperacionDTO> resultado) {
        if (ObjectUtils.isNullOrCero(dto.getIdVariableMensajeOperacion())) {
            resultado.setMensajeError(MensajesSistemaEnum.VAR_MSG_ID_REQ);
        }
        if (ObjectUtils.isNull(dto.getFechaActualizacion())) {
            resultado.setMensajeError(MensajesSistemaEnum.VAR_MSG_FECHA_EDI_REQ);
        }
        if (ObjectUtils.isNullOrEmpty(dto.getUsuarioModifico())) {
            resultado.setMensajeError(MensajesSistemaEnum.VAR_MSG_USUARIO_REQ);
        }
    }

    @Override
    public List<VariableMensajeOperacionDTO> obtenerVariablesPorOperacion(long idFuncionalidad) {
        return modelMapper.map(variableMensajeOperacionRepo.obtenerVariablesPorOperacion(idFuncionalidad),
                tipoListaVariableMensaje);
    }

}
