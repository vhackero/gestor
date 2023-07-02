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
import mx.gob.sedesol.basegestor.commons.dto.logisticainfraestructura.ReservacionEventoGeneralDTO;
import mx.gob.sedesol.basegestor.commons.utils.MensajesSistemaEnum;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.TipoAccion;
import mx.gob.sedesol.basegestor.model.entities.logisticainfraestructura.TblReservacionEventoGeneral;
import mx.gob.sedesol.basegestor.model.repositories.logisticainfraestructura.ReservacionEventoGeneralRepo;
import mx.gob.sedesol.basegestor.service.admin.ComunValidacionService;
import mx.gob.sedesol.basegestor.service.logisticainfraestructura.ReservacionEventoGeneralService;

@Service("reservacionEventoGeneralService")
public class ReservacionEventoGeneralServiceImpl 
        extends ComunValidacionService<ReservacionEventoGeneralDTO>
        implements ReservacionEventoGeneralService {

    private static final Logger logger = Logger.getLogger(ReservacionEventoGeneralServiceImpl.class);

    @Autowired
    private ReservacionEventoGeneralRepo reservacionEventoGeneralRepo;

    private ModelMapper mapper = new ModelMapper();

    @Override
    public List<ReservacionEventoGeneralDTO> findAll() {
        return new ArrayList<>();
    }

    @Override
    public ReservacionEventoGeneralDTO buscarPorId(Integer id) {
        return new ReservacionEventoGeneralDTO();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultadoDTO<ReservacionEventoGeneralDTO> guardar(ReservacionEventoGeneralDTO dto) {
        ResultadoDTO<ReservacionEventoGeneralDTO> res = sonDatosRequeridosValidos(TipoAccion.PERSISTENCIA, dto);
        try {
            if (ObjectUtils.isNotNull(res) && res.getResultado().getValor()) {

                TblReservacionEventoGeneral entity = mapper.map(dto, TblReservacionEventoGeneral.class);
                entity = reservacionEventoGeneralRepo.save(entity);
                res.setDto(mapper.map(entity, ReservacionEventoGeneralDTO.class));

        //GUSTAVO --guardarBitacora(dto.getBitacoraDTO(), String.valueOf(entity.getIdReservacionEg()));

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
    public ResultadoDTO<ReservacionEventoGeneralDTO> actualizar(ReservacionEventoGeneralDTO dto) {
        ResultadoDTO<ReservacionEventoGeneralDTO> res = sonDatosRequeridosValidos(TipoAccion.ACTUALIZACION, dto);
        try {
            if (ObjectUtils.isNotNull(res) && res.getResultado().getValor()) {

                TblReservacionEventoGeneral entity = mapper.map(dto, TblReservacionEventoGeneral.class);
                entity = reservacionEventoGeneralRepo.save(entity);
                res.setDto(mapper.map(entity, ReservacionEventoGeneralDTO.class));

        //GUSTAVO --guardarBitacora(dto.getBitacoraDTO(), String.valueOf(entity.getIdReservacionEg()));

                res.agregaMensaje(MensajesSistemaEnum.ADMIN_MSG_ACTUALIZACION_EXITOSA.getId());
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            res.setMensajeError(MensajesSistemaEnum.ADMIN_MSG_ACTUALIZACION_FALLIDA);
        }

        return res;
    }

    @Override
    public ResultadoDTO<ReservacionEventoGeneralDTO> eliminar(ReservacionEventoGeneralDTO dto) {
        return new ResultadoDTO<>();
    }

    @Override
    public ResultadoDTO<ReservacionEventoGeneralDTO> sonDatosRequeridosValidos(TipoAccion accion,
            ReservacionEventoGeneralDTO dto) {

        ResultadoDTO<ReservacionEventoGeneralDTO> res = null;
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
    public void validarPersistencia(ReservacionEventoGeneralDTO dto, ResultadoDTO<ReservacionEventoGeneralDTO> res) {
        if (ObjectUtils.isNullOrEmpty(dto.getIdAreaSede())) {
            res.setMensajeError(
                    MensajesSistemaEnum.LOGISTICA_INFRAESTRUCTURA_RESERVACION_EVENTO_GENERAL_ID_AREA_REQ);
        }

        if (ObjectUtils.isNullOrEmpty(dto.getUsuarioRegistro())) {
            res.setMensajeError(
                    MensajesSistemaEnum.LOGISTICA_INFRAESTRUCTURA_RESERVACION_EVENTO_GENERAL_USUARIO_REGISTRO_REQ);
        }

        if (ObjectUtils.isNullOrEmpty(dto.getFechaRegistro())) {
            res.setMensajeError(
                    MensajesSistemaEnum.LOGISTICA_INFRAESTRUCTURA_RESERVACION_EVENTO_GENERAL_FECHA_REGISTRO_REQ);
        }

    }

    @Override
    public void validarActualizacion(ReservacionEventoGeneralDTO dto, ResultadoDTO<ReservacionEventoGeneralDTO> res) {
        if (ObjectUtils.isNullOrEmpty(dto.getIdReservacionEg())) {
            res.setMensajeError(
                    MensajesSistemaEnum.LOGISTICA_INFRAESTRUCTURA_RESERVACION_EVENTO_GENERAL_ID_ERSERVACION_EG_REQ);
        }

        validarPersistencia(dto, res);
    }

    /**
     * @return the reservacionEventoGeneralRepo
     */
    public ReservacionEventoGeneralRepo getReservacionEventoGeneralRepo() {
        return reservacionEventoGeneralRepo;
    }

    /**
     * @param reservacionEventoGeneralRepo the reservacionEventoGeneralRepo to
     * set
     */
    public void setReservacionEventoGeneralRepo(ReservacionEventoGeneralRepo reservacionEventoGeneralRepo) {
        this.reservacionEventoGeneralRepo = reservacionEventoGeneralRepo;
    }

    @Override
    public List<ReservacionEventoGeneralDTO> buscaReservacionesPorIdAreaSede(Integer id) {

        try {
            Type listAux = new TypeToken<List<ReservacionEventoGeneralDTO>>() {
            }.getType();
            List<TblReservacionEventoGeneral> reservaciones = reservacionEventoGeneralRepo
                    .buscaReservacionesPorIdAreaSede(id);

            return mapper.map(reservaciones, listAux);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return new ArrayList<>();

    }

    @Override
    public void validarEliminacion(ReservacionEventoGeneralDTO dto, ResultadoDTO<ReservacionEventoGeneralDTO> resultado) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
