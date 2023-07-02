package mx.gob.sedesol.basegestor.service.impl.logisticainfraestructura;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.analisisdatos.CritBusquedaReporteReservDTO;
import mx.gob.sedesol.basegestor.commons.dto.logisticainfraestructura.AreaSedeDTO;
import mx.gob.sedesol.basegestor.commons.dto.logisticainfraestructura.RelSolicitudEventoGeneralDTO;
import mx.gob.sedesol.basegestor.commons.utils.MensajesSistemaEnum;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.TipoAccion;
import mx.gob.sedesol.basegestor.model.entities.logisticainfraestructura.RelSolicitudEventoGeneral;
import mx.gob.sedesol.basegestor.model.especificaciones.SolicitudEventoGeneralEspecificacion;
import mx.gob.sedesol.basegestor.model.repositories.logisticainfraestructura.SolicitudEventoGeneralRepo;
import mx.gob.sedesol.basegestor.service.admin.ComunValidacionService;
import mx.gob.sedesol.basegestor.service.logisticainfraestructura.AreaSedeService;
import mx.gob.sedesol.basegestor.service.logisticainfraestructura.SolicitudEventoGeneralService;

@Service("solicitudEventoGeneralService")
public class SolicitudEventoGeneralServiceImpl
        extends ComunValidacionService<RelSolicitudEventoGeneralDTO>
        implements SolicitudEventoGeneralService {

    private static final Logger logger = Logger.getLogger(SolicitudEventoGeneralServiceImpl.class);

    @Autowired
    private SolicitudEventoGeneralRepo solicitudEventoGeneralRepo;

    @Autowired
    private AreaSedeService areaSedeService;

    private ModelMapper mapper = new ModelMapper();

    @Override
    public List<RelSolicitudEventoGeneralDTO> findAll() {
        return new ArrayList<>();
    }

    @Override
    public RelSolicitudEventoGeneralDTO buscarPorId(Integer id) {
        return new RelSolicitudEventoGeneralDTO();
    }

    @Override
    public List<RelSolicitudEventoGeneralDTO> consultaPorCriterios(CritBusquedaReporteReservDTO criterios) {
        try {
            if (!ObjectUtils.isNullOrEmpty(criterios)) {
                Type lstSolicitudEventoGen = new TypeToken<List<RelSolicitudEventoGeneralDTO>>() {
                }.getType();

                List<Integer> areasSede = new ArrayList<>();
                Specification<RelSolicitudEventoGeneral> spec;

                if (!ObjectUtils.isNullOrCero(criterios.getIdArea())) {

                    Integer idAreaSede = getAreaSedeService()
                            .consultaAreaSedePorIdSedeyIdArea(criterios.getIdSede(), criterios.getIdArea()).getIdAreaSede();

                    criterios.setIdAreaSede(idAreaSede);

                } else if (!ObjectUtils.isNullOrCero(criterios.getIdSede())) {
                    List<AreaSedeDTO> areaS = getAreaSedeService().consultaAreasSedePorIdSede(criterios.getIdSede());
                    for (AreaSedeDTO as : areaS) {
                        areasSede.add(as.getIdAreaSede());
                    }

                }

                spec = new SolicitudEventoGeneralEspecificacion(criterios, areasSede);
                List<RelSolicitudEventoGeneral> resultadoCriterios = solicitudEventoGeneralRepo.findAll(spec);

                return mapper.map(resultadoCriterios, lstSolicitudEventoGen);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

        return new ArrayList<>();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultadoDTO<RelSolicitudEventoGeneralDTO> guardar(RelSolicitudEventoGeneralDTO dto) {

        ResultadoDTO<RelSolicitudEventoGeneralDTO> res = sonDatosRequeridosValidos(TipoAccion.PERSISTENCIA, dto);
        try {
            if (ObjectUtils.isNotNull(res) && res.getResultado().getValor()) {

                RelSolicitudEventoGeneral entity = mapper.map(dto, RelSolicitudEventoGeneral.class);
                entity = solicitudEventoGeneralRepo.save(entity);
                res.setDto(mapper.map(entity, RelSolicitudEventoGeneralDTO.class));

        //GUSTAVO --guardarBitacora(dto.getBitacoraDTO(), new StringBuilder(entity.getIdSolicitud()).append(", ").append(entity.getIdReservacionEG()).toString());

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
    public ResultadoDTO<RelSolicitudEventoGeneralDTO> actualizar(RelSolicitudEventoGeneralDTO dto) {

        ResultadoDTO<RelSolicitudEventoGeneralDTO> res = sonDatosRequeridosValidos(TipoAccion.ACTUALIZACION, dto);
        try {
            if (ObjectUtils.isNotNull(res) && res.getResultado().getValor()) {

                RelSolicitudEventoGeneral entity = mapper.map(dto, RelSolicitudEventoGeneral.class);
                entity = solicitudEventoGeneralRepo.save(entity);
                res.setDto(mapper.map(entity, RelSolicitudEventoGeneralDTO.class));

        //GUSTAVO --guardarBitacora(dto.getBitacoraDTO(), new StringBuilder(entity.getIdSolicitud()).append(", ").append(entity.getIdReservacionEG()).toString());

                res.agregaMensaje(MensajesSistemaEnum.ADMIN_MSG_ACTUALIZACION_EXITOSA.getId());
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            res.setMensajeError(MensajesSistemaEnum.ADMIN_MSG_ACTUALIZACION_FALLIDA);
        }

        return res;
    }

    @Override
    public ResultadoDTO<RelSolicitudEventoGeneralDTO> eliminar(RelSolicitudEventoGeneralDTO dto) {
        return new ResultadoDTO<>();
    }

    @Override
    public ResultadoDTO<RelSolicitudEventoGeneralDTO> sonDatosRequeridosValidos(TipoAccion accion,
            RelSolicitudEventoGeneralDTO dto) {

        ResultadoDTO<RelSolicitudEventoGeneralDTO> res = null;
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
    public void validarPersistencia(RelSolicitudEventoGeneralDTO dto, ResultadoDTO<RelSolicitudEventoGeneralDTO> res) {
        if (ObjectUtils.isNullOrEmpty(dto.getIdSolicitud())) {
            res.setMensajeError(
                    MensajesSistemaEnum.LOGISTICA_INFRAESTRUCTURA_SOLICITUD_EVENTO_GENERAL_ID_SOLICITUD_REQ);
        }

        if (ObjectUtils.isNullOrEmpty(dto.getIdReservacionEG())) {
            res.setMensajeError(
                    MensajesSistemaEnum.LOGISTICA_INFRAESTRUCTURA_SOLICITUD_EVENTO_GENERAL_ID_RESERVACION_EVENTO_GENERAL_REQ);
        }

    }

    @Override
    public void validarActualizacion(RelSolicitudEventoGeneralDTO dto, ResultadoDTO<RelSolicitudEventoGeneralDTO> res) {
        validarPersistencia(dto, res);
    }

    /**
     * @return the solicitudEventoGeneralRepo
     */
    public SolicitudEventoGeneralRepo getSolicitudEventoGeneralRepo() {
        return solicitudEventoGeneralRepo;
    }

    /**
     * @param solicitudEventoGeneralRepo the solicitudEventoGeneralRepo to set
     */
    public void setSolicitudEventoGeneralRepo(SolicitudEventoGeneralRepo solicitudEventoGeneralRepo) {
        this.solicitudEventoGeneralRepo = solicitudEventoGeneralRepo;
    }

    @Override
    public List<RelSolicitudEventoGeneralDTO> consultaSolicitudesPorIdReservacion(Integer idReservacion) {

        try {
            Type listAux = new TypeToken<List<RelSolicitudEventoGeneralDTO>>() {
            }.getType();
            List<RelSolicitudEventoGeneral> solicitudes = solicitudEventoGeneralRepo.consultaSolicitudesPorIdReservacion(idReservacion);

            return mapper.map(solicitudes, listAux);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return new ArrayList<>();
    }

    @Override
    public List<RelSolicitudEventoGeneralDTO> consultaReservacionesPorIdSolicitud(Integer idSolicitud) {

        try {
            Type listAux = new TypeToken<List<RelSolicitudEventoGeneralDTO>>() {
            }.getType();
            List<RelSolicitudEventoGeneral> reservaciones = solicitudEventoGeneralRepo.consultaReservacionesPorIdSolicitud(idSolicitud);

            return mapper.map(reservaciones, listAux);

        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
        return new ArrayList<>();
    }

    public AreaSedeService getAreaSedeService() {
        return areaSedeService;
    }

    public void setAreaSedeService(AreaSedeService areaSedeService) {
        this.areaSedeService = areaSedeService;
    }

    @Override
    public void validarEliminacion(RelSolicitudEventoGeneralDTO dto, ResultadoDTO<RelSolicitudEventoGeneralDTO> resultado) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
