package mx.gob.sedesol.basegestor.service.impl.logisticainfraestructura;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.logisticainfraestructura.SolicitudReservacionDTO;
import mx.gob.sedesol.basegestor.commons.utils.MensajesSistemaEnum;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.TipoAccion;
import mx.gob.sedesol.basegestor.model.entities.logisticainfraestructura.TblSolicitudReservacion;
import mx.gob.sedesol.basegestor.model.repositories.logisticainfraestructura.SolicitudReservacionRepo;
import mx.gob.sedesol.basegestor.service.admin.ComunValidacionService;
import mx.gob.sedesol.basegestor.service.logisticainfraestructura.SolicitudReservacionService;

@Service("solicitudReservacionService")
public class SolicitudReservacionServiceImpl extends ComunValidacionService<SolicitudReservacionDTO> implements SolicitudReservacionService {

    private static final Logger logger = Logger.getLogger(SolicitudReservacionServiceImpl.class);

    @Autowired
    private SolicitudReservacionRepo solicitudReservacionRepo;

    private ModelMapper mapper = new ModelMapper();

    @Override
    public List<SolicitudReservacionDTO> findAll() {
        return new ArrayList<>();
    }

    @Override
    public SolicitudReservacionDTO buscarPorId(Integer id) {
        return new SolicitudReservacionDTO();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultadoDTO<SolicitudReservacionDTO> guardar(SolicitudReservacionDTO dto) {

        ResultadoDTO<SolicitudReservacionDTO> res = sonDatosRequeridosValidos(TipoAccion.PERSISTENCIA, dto);
        try {
            if (ObjectUtils.isNotNull(res) && res.getResultado().getValor()) {

                TblSolicitudReservacion entity = mapper.map(dto, TblSolicitudReservacion.class);
                entity = solicitudReservacionRepo.save(entity);
                res.setDto(mapper.map(entity, SolicitudReservacionDTO.class));

        //GUSTAVO --guardarBitacora(dto.getBitacoraDTO(), String.valueOf(entity.getIdSolicitud()));

                res.agregaMensaje(MensajesSistemaEnum.ADMIN_MSG_GUARDADO_EXITOSO.getId());
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            res.setMensajeError(MensajesSistemaEnum.ADMIN_MSG_GUARDADO_FALLIDO);
        }

        return res;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultadoDTO<SolicitudReservacionDTO> actualizar(SolicitudReservacionDTO dto) {
        ResultadoDTO<SolicitudReservacionDTO> res = sonDatosRequeridosValidos(TipoAccion.ACTUALIZACION, dto);
        try {
            if (ObjectUtils.isNotNull(res) && res.getResultado().getValor()) {

                TblSolicitudReservacion entity = mapper.map(dto, TblSolicitudReservacion.class);
                entity = solicitudReservacionRepo.save(entity);
                res.setDto(mapper.map(entity, SolicitudReservacionDTO.class));

        //GUSTAVO --guardarBitacora(dto.getBitacoraDTO(), String.valueOf(entity.getIdSolicitud()));

                res.agregaMensaje(MensajesSistemaEnum.ADMIN_MSG_ACTUALIZACION_EXITOSA.getId());
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            res.setMensajeError(MensajesSistemaEnum.ADMIN_MSG_ACTUALIZACION_FALLIDA);
        }

        return res;
    }

    @Override
    public ResultadoDTO<SolicitudReservacionDTO> eliminar(SolicitudReservacionDTO dto) {
        return new ResultadoDTO<>();
    }

    @Override
    public ResultadoDTO<SolicitudReservacionDTO> sonDatosRequeridosValidos(TipoAccion accion,
            SolicitudReservacionDTO dto) {

        ResultadoDTO<SolicitudReservacionDTO> res = null;
        if (ObjectUtils.isNotNull(dto)) {
            res = new ResultadoDTO<>();

            switch (accion) {
                case PERSISTENCIA:

                    validarPersistencia(dto, res);

                    break;

                case ACTUALIZACION:

                    validarActualizacion(dto, res);

                    break;

                default:

                    res.setMensajeError(MensajesSistemaEnum.PLNT_MSG_OPERACION_REQ);

                    break;
            }
        }
        return res;
    }

    /**
     * @return the solicitudReservacionRepo
     */
    public SolicitudReservacionRepo getSolicitudReservacionRepo() {
        return solicitudReservacionRepo;
    }

    /**
     * @param solicitudReservacionRepo the solicitudReservacionRepo to set
     */
    public void setSolicitudReservacionRepo(SolicitudReservacionRepo solicitudReservacionRepo) {
        this.solicitudReservacionRepo = solicitudReservacionRepo;
    }

    @Override
    public void validarPersistencia(SolicitudReservacionDTO dto, ResultadoDTO<SolicitudReservacionDTO> resultado) {
        if (ObjectUtils.isNullOrEmpty(dto.getFechaRegistro())) {
            resultado.setMensajeError(
                    MensajesSistemaEnum.LOGISTICA_INFRAESTRUCTURA_SOLICITUD_RESERVACION_FECHA_REGISTRO_REQ);
        }

        if (ObjectUtils.isNullOrEmpty(dto.getUsuarioModifico())) {
            resultado.setMensajeError(
                    MensajesSistemaEnum.LOGISTICA_INFRAESTRUCTURA_SOLICITUD_RESERVACION_USUARIO_MODIFICO_REQ);
        }

    }

    @Override
    public void validarActualizacion(SolicitudReservacionDTO dto, ResultadoDTO<SolicitudReservacionDTO> resultado) {
        if (ObjectUtils.isNullOrEmpty(dto.getIdSolicitud())) {
            resultado.setMensajeError(
                    MensajesSistemaEnum.LOGISTICA_INFRAESTRUCTURA_SOLICITUD_RESERVACION_ID_SOLICITUD_REQ);
        }

        validarPersistencia(dto, resultado);
    }

    @Override
    public void validarEliminacion(SolicitudReservacionDTO dto, ResultadoDTO<SolicitudReservacionDTO> resultado) {

    }

}
