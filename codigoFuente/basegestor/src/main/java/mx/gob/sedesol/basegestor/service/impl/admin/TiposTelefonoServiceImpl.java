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
import mx.gob.sedesol.basegestor.commons.dto.admin.TipoTelefonoDTO;
import mx.gob.sedesol.basegestor.commons.utils.MensajesSistemaEnum;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.TipoAccion;
import mx.gob.sedesol.basegestor.model.entities.admin.CatTiposTelefono;
import mx.gob.sedesol.basegestor.model.repositories.admin.TiposTelefonoRepo;
import mx.gob.sedesol.basegestor.service.admin.ComunValidacionService;
import mx.gob.sedesol.basegestor.service.admin.TiposTelefonoService;

@Service("tiposTelefonoService")
public class TiposTelefonoServiceImpl extends ComunValidacionService<TipoTelefonoDTO> implements TiposTelefonoService {

    private static final Logger logger = Logger.getLogger(TiposTelefonoServiceImpl.class);

    @Autowired
    private TiposTelefonoRepo tiposTelefonoRepo;

    private ModelMapper tpoTelefonoMapper = new ModelMapper();

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultadoDTO<TipoTelefonoDTO> guardar(TipoTelefonoDTO tipoTel) {

        ResultadoDTO<TipoTelefonoDTO> res = sonDatosRequeridosValidos(TipoAccion.PERSISTENCIA, tipoTel);
        if (res.getResultado().getValor()) {
            try {

                res = new ResultadoDTO<>();
                CatTiposTelefono tposTelAux = tpoTelefonoMapper.map(tipoTel, CatTiposTelefono.class);
                tiposTelefonoRepo.save(tposTelAux);
                res.setDto(tpoTelefonoMapper.map(tposTelAux, TipoTelefonoDTO.class));

        //GUSTAVO --guardarBitacora(tipoTel.getBitacoraDTO(), String.valueOf(tposTelAux.getIdTipoTelefono()));
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
    public ResultadoDTO<TipoTelefonoDTO> eliminar(TipoTelefonoDTO tipoTel) {

        ResultadoDTO<TipoTelefonoDTO> res = sonDatosRequeridosValidos(TipoAccion.ELIMINACION, tipoTel);
        if (res.getResultado().getValor()) {
            try {
                res = new ResultadoDTO<>();
                CatTiposTelefono tposTelAux = tpoTelefonoMapper.map(tipoTel, CatTiposTelefono.class);
                tiposTelefonoRepo.saveAndFlush(tposTelAux);
                res.setDto(tpoTelefonoMapper.map(tposTelAux, TipoTelefonoDTO.class));

        //GUSTAVO --guardarBitacora(tipoTel.getBitacoraDTO(), String.valueOf(tposTelAux.getIdTipoTelefono()));
                res.agregaMensaje(MensajesSistemaEnum.ADMIN_MSG_ELIMINACION_EXITOSA.getId());
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                res.setMensajeError(MensajesSistemaEnum.ADMIN_MSG_ELIMINACION_FALLIDA);
            }
        }
        return res;
    }

    @Override
    public List<TipoTelefonoDTO> findAll() {

        List<CatTiposTelefono> lstTposTel = tiposTelefonoRepo.findAll();
        Type tposTelDto = new TypeToken<List<TipoTelefonoDTO>>() {
        }.getType();

        return tpoTelefonoMapper.map(lstTposTel, tposTelDto);

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultadoDTO<TipoTelefonoDTO> actualizar(TipoTelefonoDTO tipoTel) {
        ResultadoDTO<TipoTelefonoDTO> res = sonDatosRequeridosValidos(TipoAccion.ACTUALIZACION, tipoTel);
        if (res.getResultado().getValor()) {
            try {
                res = new ResultadoDTO<>();
                CatTiposTelefono tpoTelAux = tpoTelefonoMapper.map(tipoTel, CatTiposTelefono.class);
                tiposTelefonoRepo.saveAndFlush(tpoTelAux);
                res.setDto(tpoTelefonoMapper.map(tpoTelAux, TipoTelefonoDTO.class));

        //GUSTAVO --guardarBitacora(tipoTel.getBitacoraDTO(), String.valueOf(tpoTelAux.getIdTipoTelefono()));
                res.agregaMensaje(MensajesSistemaEnum.ADMIN_MSG_ACTUALIZACION_EXITOSA.getId());
            } catch (Exception e) {
                res.setMensajeError(MensajesSistemaEnum.ADMIN_MSG_ACTUALIZACION_FALLIDA);
                logger.error(e.getMessage(), e);
            }
        }
        return res;
    }

    @Override
    public TipoTelefonoDTO buscarPorId(Integer id) {
        TipoTelefonoDTO tipoTelefonoDTO;
        CatTiposTelefono tipoTelefono = tiposTelefonoRepo.findOne(id);
        if (ObjectUtils.isNull(tipoTelefono)) {
            tipoTelefonoDTO = null;
        } else {
            tipoTelefonoDTO = tpoTelefonoMapper.map(tipoTelefono, TipoTelefonoDTO.class);
        }
        return tipoTelefonoDTO;
    }

    @Override
    public void validarPersistencia(TipoTelefonoDTO tipoTel, ResultadoDTO<TipoTelefonoDTO> res) {
        if (ObjectUtils.isNullOrEmpty(tipoTel.getDescripcion())) {
            res.setMensajeError(MensajesSistemaEnum.TIPO_TEL_DESCRIPCION_REQ);
        }
        if (ObjectUtils.isNull(tipoTel.getFechaRegistro())) {
            res.setMensajeError(MensajesSistemaEnum.MSG_GENERAL_FECHA_REGISTRO_REQ);
        }
        if (ObjectUtils.isNullOrEmpty(tipoTel.getUsuarioModifico())) {
            res.setMensajeError(MensajesSistemaEnum.MSG_GENERAL_USUARIO_MODIFICO_REQ);
        }
    }

    @Override
    public void validarActualizacion(TipoTelefonoDTO tipoTel, ResultadoDTO<TipoTelefonoDTO> res) {
        if (ObjectUtils.isNull(tipoTel.getIdTipoTelefono())) {
            res.setMensajeError(MensajesSistemaEnum.TIPO_TEL_ID_REQ);
        }

        if (ObjectUtils.isNullOrEmpty(tipoTel.getDescripcion())) {
            res.setMensajeError(MensajesSistemaEnum.TIPO_TEL_DESCRIPCION_REQ);
        }

        if (ObjectUtils.isNullOrEmpty(tipoTel.getFechaActualizacion())) {
            res.setMensajeError(MensajesSistemaEnum.MSG_GENERAL_FECHA_EDICION_REQ);
        }

        if (ObjectUtils.isNullOrEmpty(tipoTel.getUsuarioModifico())) {
            res.setMensajeError(MensajesSistemaEnum.MSG_GENERAL_USUARIO_MODIFICO_REQ);
        }
    }

    @Override
    public void validarEliminacion(TipoTelefonoDTO tipoTel, ResultadoDTO<TipoTelefonoDTO> res) {
        if (ObjectUtils.isNull(tipoTel.getIdTipoTelefono())) {
            res.setMensajeError(MensajesSistemaEnum.TIPO_TEL_ID_REQ);
        }

        if (ObjectUtils.isNull(tipoTel.getActivo())) {
            res.setMensajeError(MensajesSistemaEnum.MSG_GENERAL_ESTA_ACTIVO_REQ);
        }
    }

}
