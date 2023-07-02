package mx.gob.sedesol.basegestor.service.impl.logisticainfraestructura;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.gob.sedesol.basegestor.commons.constantes.ConstantesBitacora;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.logisticainfraestructura.EventoGeneralDTO;
import mx.gob.sedesol.basegestor.commons.utils.MensajesSistemaEnum;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.TipoAccion;
import mx.gob.sedesol.basegestor.model.entities.logisticainfraestructura.TblEventoGeneral;
import mx.gob.sedesol.basegestor.model.repositories.logisticainfraestructura.EventoGeneralRepo;
import mx.gob.sedesol.basegestor.mongo.service.AdministradorBitacora;
import mx.gob.sedesol.basegestor.service.admin.ComunValidacionService;
import mx.gob.sedesol.basegestor.service.logisticainfraestructura.EventoGeneralService;

@Service("eventoGeneralService")
public class EventoGeneralServiceImpl extends ComunValidacionService<EventoGeneralDTO> implements EventoGeneralService {

    private static final Logger logger = Logger.getLogger(EventoGeneralServiceImpl.class);

    private ModelMapper mapper = new ModelMapper();

    @Autowired
    private EventoGeneralRepo eventoGeneralRepo;

    @Override
    public List<EventoGeneralDTO> findAll() {
        return new ArrayList<>();
    }

    @Override
    public EventoGeneralDTO buscarPorId(Integer id) {
        return new EventoGeneralDTO();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultadoDTO<EventoGeneralDTO> guardar(EventoGeneralDTO dto) {

        ResultadoDTO<EventoGeneralDTO> res = sonDatosRequeridosValidos(TipoAccion.PERSISTENCIA, dto);
        try {
            if (ObjectUtils.isNotNull(res) && res.getResultado().getValor()) {

                TblEventoGeneral entity = mapper.map(dto, TblEventoGeneral.class);
                entity = eventoGeneralRepo.save(entity);
                res.setDto(mapper.map(entity, EventoGeneralDTO.class));

            //GUSTAVO --guardarBitacoraEvento(dto, String.valueOf(entity.getIdEventoGeneral()));

                res.agregaMensaje(MensajesSistemaEnum.ADMIN_MSG_GUARDADO_EXITOSO.getId());
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            res.setMensajeError(MensajesSistemaEnum.ADMIN_MSG_GUARDADO_FALLIDO);
        }

        return res;
    }

    @Override
    public ResultadoDTO<EventoGeneralDTO> actualizar(EventoGeneralDTO dto) {
        return new ResultadoDTO<>();
    }

    @Override
    public ResultadoDTO<EventoGeneralDTO> eliminar(EventoGeneralDTO dto) {
        return new ResultadoDTO<>();
    }

    @Override
    public ResultadoDTO<EventoGeneralDTO> sonDatosRequeridosValidos(TipoAccion accion, EventoGeneralDTO dto) {
        ResultadoDTO<EventoGeneralDTO> res = null;
        if (ObjectUtils.isNotNull(dto)) {
            res = new ResultadoDTO<>();

            switch (accion) {
                case PERSISTENCIA:
                    validarPersistencia(dto, res);
                    break;

                default:

                    res.setMensajeError(MensajesSistemaEnum.PLNT_MSG_OPERACION_REQ);

                    break;
            }
        }

        return res;
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

    /**
     * @return the eventoGeneralRepo
     */
    public EventoGeneralRepo getEventoGeneralRepo() {
        return eventoGeneralRepo;
    }

    /**
     * @param eventoGeneralRepo the eventoGeneralRepo to set
     */
    public void setEventoGeneralRepo(EventoGeneralRepo eventoGeneralRepo) {
        this.eventoGeneralRepo = eventoGeneralRepo;
    }

    @Override
    public void validarPersistencia(EventoGeneralDTO dto, ResultadoDTO<EventoGeneralDTO> resultado) {

        if (ObjectUtils.isNullOrEmpty(dto.getNombre())) {
            resultado.setMensajeError(
                    MensajesSistemaEnum.MSG_GENERAL_NOMBRE_REQ);
        }

    }

    @Override
    public void validarActualizacion(EventoGeneralDTO dto, ResultadoDTO<EventoGeneralDTO> resultado) {
        // TODO Auto-generated method stub

    }

    @Override
    public void validarEliminacion(EventoGeneralDTO dto, ResultadoDTO<EventoGeneralDTO> resultado) {
        // TODO Auto-generated method stub

    }

}
