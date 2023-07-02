package mx.gob.sedesol.basegestor.service.impl.logisticainfraestructura;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.logisticainfraestructura.ReservacionEventoCapacitacionDTO;
import mx.gob.sedesol.basegestor.commons.utils.MensajesSistemaEnum;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.TipoAccion;
import mx.gob.sedesol.basegestor.model.entities.logisticainfraestructura.TblReservacionEventoCapacitacion;
import mx.gob.sedesol.basegestor.model.repositories.logisticainfraestructura.ReservacionEventoCapacitacionRepo;
import mx.gob.sedesol.basegestor.service.admin.ComunValidacionService;
import mx.gob.sedesol.basegestor.service.logisticainfraestructura.ReservacionEventoCapacitacionService;

@Service("reservacionEventoCapacitacionService")
public class ReservacionEventoCapacitacionServiceImpl 
        extends ComunValidacionService<ReservacionEventoCapacitacionDTO>
        implements ReservacionEventoCapacitacionService {

    private static final Logger logger = Logger.getLogger(ReservacionEventoCapacitacionServiceImpl.class);

    @Autowired
    private ReservacionEventoCapacitacionRepo reservacionEventoCapacitacionRepo;

    private ModelMapper mapper = new ModelMapper();

    @Override
    public List<ReservacionEventoCapacitacionDTO> findAll() {
        return new ArrayList<>();
    }

    @Override
    public ReservacionEventoCapacitacionDTO buscarPorId(Integer id) {
        return new ReservacionEventoCapacitacionDTO();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultadoDTO<ReservacionEventoCapacitacionDTO> guardar(ReservacionEventoCapacitacionDTO dto) {
        ResultadoDTO<ReservacionEventoCapacitacionDTO> res = sonDatosRequeridosValidos(TipoAccion.PERSISTENCIA, dto);
        try {
            if (ObjectUtils.isNotNull(res) && res.getResultado().getValor()) {

                TblReservacionEventoCapacitacion entity = mapper.map(dto, TblReservacionEventoCapacitacion.class);
                entity = reservacionEventoCapacitacionRepo.save(entity);
                res.setDto(getMapper().map(entity, ReservacionEventoCapacitacionDTO.class));

        //GUSTAVO --guardarBitacora(dto.getBitacoraDTO(), String.valueOf(entity.getIdReservacionEc()));

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
    public ResultadoDTO<ReservacionEventoCapacitacionDTO> actualizar(ReservacionEventoCapacitacionDTO dto) {
        ResultadoDTO<ReservacionEventoCapacitacionDTO> res = sonDatosRequeridosValidos(TipoAccion.ACTUALIZACION, dto);
        try {
            if (ObjectUtils.isNotNull(res) && res.getResultado().getValor()) {

                TblReservacionEventoCapacitacion entity = mapper.map(dto, TblReservacionEventoCapacitacion.class);
                entity = reservacionEventoCapacitacionRepo.save(entity);
                res.setDto(getMapper().map(entity, ReservacionEventoCapacitacionDTO.class));

        //GUSTAVO --guardarBitacora(dto.getBitacoraDTO(), String.valueOf(entity.getIdReservacionEc()));

                res.agregaMensaje(MensajesSistemaEnum.ADMIN_MSG_ACTUALIZACION_EXITOSA.getId());
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            res.setMensajeError(MensajesSistemaEnum.ADMIN_MSG_ACTUALIZACION_FALLIDA);
        }

        return res;
    }

    @Override
    public ResultadoDTO<ReservacionEventoCapacitacionDTO> eliminar(ReservacionEventoCapacitacionDTO dto) {
        return new ResultadoDTO<>();
    }

    @Override
    public ResultadoDTO<ReservacionEventoCapacitacionDTO> sonDatosRequeridosValidos(TipoAccion accion,
            ReservacionEventoCapacitacionDTO dto) {

        ResultadoDTO<ReservacionEventoCapacitacionDTO> res = null;
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

    @Override
    public void validarPersistencia(ReservacionEventoCapacitacionDTO dto,
            ResultadoDTO<ReservacionEventoCapacitacionDTO> res) {
        if (ObjectUtils.isNullOrEmpty(dto.getIdAreaSede())) {
            res.setMensajeError(
                    MensajesSistemaEnum.LOGISTICA_INFRAESTRUCTURA_RESERVACION_EVENTO_CAPACITACION_ID_AREA_REQ);
        }

        if (ObjectUtils.isNullOrEmpty(dto.getTblEvento().getIdEvento())) {
            res.setMensajeError(
                    MensajesSistemaEnum.LOGISTICA_INFRAESTRUCTURA_RESERVACION_EVENTO_CAPACITACION_ID_EVENTO_CAPACITACION_REQ);
        }

        if (ObjectUtils.isNullOrEmpty(dto.getUsuarioRegistro())) {
            res.setMensajeError(
                    MensajesSistemaEnum.LOGISTICA_INFRAESTRUCTURA_RESERVACION_EVENTO_CAPACITACION_USUARIO_REGISTRO_REQ);
        }

        if (ObjectUtils.isNullOrEmpty(dto.getFechaRegistro())) {
            res.setMensajeError(
                    MensajesSistemaEnum.LOGISTICA_INFRAESTRUCTURA_RESERVACION_EVENTO_CAPACITACION_FECHA_REGISTRO_REQ);
        }

    }

    @Override
    public void validarActualizacion(ReservacionEventoCapacitacionDTO dto,
            ResultadoDTO<ReservacionEventoCapacitacionDTO> res) {
        if (ObjectUtils.isNullOrEmpty(dto.getIdReservacionEc())) {
            res.setMensajeError(
                    MensajesSistemaEnum.LOGISTICA_INFRAESTRUCTURA_RESERVACION_EVENTO_CAPACITACION_ID_ERSERVACION_EC_REQ);
        }

        validarPersistencia(dto, res);
    }

    /**
     * @return the reservacionEventoCapacitacionRepo
     */
    public ReservacionEventoCapacitacionRepo getReservacionEventoCapacitacionRepo() {
        return reservacionEventoCapacitacionRepo;
    }

    /**
     * @param reservacionEventoCapacitacionRepo the
     * reservacionEventoCapacitacionRepo to set
     */
    public void setReservacionEventoCapacitacionRepo(
            ReservacionEventoCapacitacionRepo reservacionEventoCapacitacionRepo) {
        this.reservacionEventoCapacitacionRepo = reservacionEventoCapacitacionRepo;
    }

    /**
     * @return the mapper
     */
    public ModelMapper getMapper() {
        return mapper;
    }

    /**
     * @param mapper the mapper to set
     */
    public void setMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public List<ReservacionEventoCapacitacionDTO> buscaReservacionesPorIdAreaSede(Integer id) {

        try {
            Type listAux = new TypeToken<List<ReservacionEventoCapacitacionDTO>>() {
            }.getType();
            List<TblReservacionEventoCapacitacion> reservaciones = reservacionEventoCapacitacionRepo
                    .buscaReservacionesPorIdAreaSede(id);

            return mapper.map(reservaciones, listAux);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return new ArrayList<>();

    }

    @Override
    public void validarEliminacion(ReservacionEventoCapacitacionDTO dto, ResultadoDTO<ReservacionEventoCapacitacionDTO> resultado) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
