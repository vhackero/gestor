package mx.gob.sedesol.basegestor.service.impl.admin;

import java.lang.reflect.Type;
import java.util.List;

import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.TipoCorreoDTO;
import mx.gob.sedesol.basegestor.commons.utils.MensajesSistemaEnum;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.TipoAccion;
import mx.gob.sedesol.basegestor.model.entities.admin.CatTiposCorreo;
import mx.gob.sedesol.basegestor.model.repositories.admin.TiposCorreoRepo;
import mx.gob.sedesol.basegestor.service.admin.ComunValidacionService;
import mx.gob.sedesol.basegestor.service.admin.TiposCorreoService;

@Service("tiposCorreoService")
public class TiposCorreoServiceImpl extends ComunValidacionService<TipoCorreoDTO> implements TiposCorreoService {

    private static final Logger logger = Logger.getLogger(TiposCorreoServiceImpl.class);

    @Autowired
    private TiposCorreoRepo tiposCorreoRepo;

    private ModelMapper tpoCorreoMapper = new ModelMapper();

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultadoDTO<TipoCorreoDTO> guardar(TipoCorreoDTO tpoCorreo) {

        ResultadoDTO<TipoCorreoDTO> res = sonDatosRequeridosValidos(TipoAccion.PERSISTENCIA, tpoCorreo);
        if (res.getResultado().getValor()) {
            try {
                res = new ResultadoDTO<>();
                CatTiposCorreo tposCorreoAux = tpoCorreoMapper.map(tpoCorreo, CatTiposCorreo.class);
                tiposCorreoRepo.save(tposCorreoAux);
                res.setDto(tpoCorreoMapper.map(tposCorreoAux, TipoCorreoDTO.class));

        //GUSTAVO --guardarBitacora(tpoCorreo.getBitacoraDTO(), String.valueOf(tposCorreoAux.getIdTipoCorreo()));
                res.agregaMensaje(MensajesSistemaEnum.ADMIN_MSG_GUARDADO_EXITOSO.getId());
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                res.setMensajeError(MensajesSistemaEnum.ADMIN_MSG_GUARDADO_FALLIDO);
            }
        }
        return res;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultadoDTO<TipoCorreoDTO> eliminar(TipoCorreoDTO tpoCorreo) {
        ResultadoDTO<TipoCorreoDTO> res = sonDatosRequeridosValidos(TipoAccion.ELIMINACION, tpoCorreo);
        if (res.getResultado().getValor()) {

            try {
                res = new ResultadoDTO<>();
                CatTiposCorreo tposCorreoAux = tpoCorreoMapper.map(tpoCorreo, CatTiposCorreo.class);
                tiposCorreoRepo.saveAndFlush(tposCorreoAux);
                res.setDto(tpoCorreoMapper.map(tposCorreoAux, TipoCorreoDTO.class));

        //GUSTAVO --guardarBitacora(tpoCorreo.getBitacoraDTO(), String.valueOf(tposCorreoAux.getIdTipoCorreo()));
                res.agregaMensaje(MensajesSistemaEnum.ADMIN_MSG_ELIMINACION_EXITOSA.getId());
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                res.setMensajeError(MensajesSistemaEnum.ADMIN_MSG_ELIMINACION_FALLIDA);
            }
        }

        return res;
    }

    @Override
    public List<TipoCorreoDTO> findAll() {
        List<CatTiposCorreo> tposCorreo = tiposCorreoRepo.findAll();
        Type tposCorreoDto = new TypeToken<List<TipoCorreoDTO>>() {
        }.getType();
        return tpoCorreoMapper.map(tposCorreo, tposCorreoDto);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultadoDTO<TipoCorreoDTO> actualizar(TipoCorreoDTO tpoCorreo) {
        ResultadoDTO<TipoCorreoDTO> res = sonDatosRequeridosValidos(TipoAccion.ACTUALIZACION, tpoCorreo);
        if (res.getResultado().getValor()) {
            try {
                res = new ResultadoDTO<>();
                CatTiposCorreo tpoCorreoAux = tpoCorreoMapper.map(tpoCorreo, CatTiposCorreo.class);
                tiposCorreoRepo.saveAndFlush(tpoCorreoAux);
                res.setDto(tpoCorreoMapper.map(tpoCorreoAux, TipoCorreoDTO.class));

        //GUSTAVO --guardarBitacora(tpoCorreo.getBitacoraDTO(), String.valueOf(tpoCorreoAux.getIdTipoCorreo()));
                res.agregaMensaje(MensajesSistemaEnum.ADMIN_MSG_ACTUALIZACION_EXITOSA.getId());
            } catch (Exception e) {
                res.setMensajeError(MensajesSistemaEnum.ADMIN_MSG_ACTUALIZACION_FALLIDA);
                logger.error(e.getMessage(), e);
            }
        }
        return res;
    }

    @Override
    public TipoCorreoDTO buscarPorId(Integer id) {
        TipoCorreoDTO tipoCorreoDTO;
        CatTiposCorreo catTiposCorreo = tiposCorreoRepo.findOne(id);
        if (ObjectUtils.isNull(catTiposCorreo)) {
            tipoCorreoDTO = null;
        } else {
            tipoCorreoDTO = tpoCorreoMapper.map(catTiposCorreo, TipoCorreoDTO.class);
        }
        return tipoCorreoDTO;
    }

    @Override
    public void validarPersistencia(TipoCorreoDTO tpoCorreo, ResultadoDTO<TipoCorreoDTO> res) {
        if (ObjectUtils.isNullOrEmpty(tpoCorreo.getDescripcion())) {
            res.setMensajeError(MensajesSistemaEnum.TIPO_CORREO_DESCRIPCION_REQ);
        }
        if (ObjectUtils.isNull(tpoCorreo.getFechaRegistro())) {
            res.setMensajeError(MensajesSistemaEnum.MSG_GENERAL_FECHA_REGISTRO_REQ);
        }
        if (ObjectUtils.isNullOrEmpty(tpoCorreo.getUsuarioModifico())) {
            res.setMensajeError(MensajesSistemaEnum.MSG_GENERAL_USUARIO_MODIFICO_REQ);
        }
    }

    @Override
    public void validarActualizacion(TipoCorreoDTO tpoCorreo, ResultadoDTO<TipoCorreoDTO> res) {
        if (ObjectUtils.isNull(tpoCorreo.getIdTipoCorreo())) {
            res.setMensajeError(MensajesSistemaEnum.TIPO_CORREO_ID_REQ);
        }
        if (ObjectUtils.isNullOrEmpty(tpoCorreo.getDescripcion())) {
            res.setMensajeError(MensajesSistemaEnum.TIPO_CORREO_DESCRIPCION_REQ);
        }
        if (ObjectUtils.isNullOrEmpty(tpoCorreo.getFechaActualizacion())) {
            res.setMensajeError(MensajesSistemaEnum.MSG_GENERAL_FECHA_EDICION_REQ);
        }
        if (ObjectUtils.isNullOrEmpty(tpoCorreo.getUsuarioModifico())) {
            res.setMensajeError(MensajesSistemaEnum.MSG_GENERAL_USUARIO_MODIFICO_REQ);
        }
    }

    @Override
    public void validarEliminacion(TipoCorreoDTO tpoCorreo, ResultadoDTO<TipoCorreoDTO> res) {
        if (ObjectUtils.isNull(tpoCorreo.getIdTipoCorreo())) {
            res.setMensajeError(MensajesSistemaEnum.TIPO_CORREO_ID_REQ);
        }
        if (ObjectUtils.isNull(tpoCorreo.getActivo())) {
            res.setMensajeError(MensajesSistemaEnum.MSG_GENERAL_ESTA_ACTIVO_REQ);
        }
    }

}
