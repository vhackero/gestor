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
import mx.gob.sedesol.basegestor.commons.dto.logisticainfraestructura.RelSolicitudEventoCapacitacionDTO;
import mx.gob.sedesol.basegestor.commons.utils.MensajesSistemaEnum;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.TipoAccion;
import mx.gob.sedesol.basegestor.model.entities.logisticainfraestructura.RelSolicitudEventoCapacitacion;
import mx.gob.sedesol.basegestor.model.especificaciones.SolicitudEventoCapacitacionEspecificacion;
import mx.gob.sedesol.basegestor.model.repositories.logisticainfraestructura.SolicitudEventoCapacitacionRepo;
import mx.gob.sedesol.basegestor.service.admin.ComunValidacionService;
import mx.gob.sedesol.basegestor.service.logisticainfraestructura.AreaSedeService;
import mx.gob.sedesol.basegestor.service.logisticainfraestructura.SolicitudEventoCapacitacionService;

@Service("solicitudEventoCapacitacionService")
public class SolicitudEventoCapacitacionServiceImpl
        extends ComunValidacionService<RelSolicitudEventoCapacitacionDTO>
        implements SolicitudEventoCapacitacionService {

    private static final Logger logger = Logger.getLogger(SolicitudEventoCapacitacionServiceImpl.class);

    private ModelMapper mapper = new ModelMapper();

    @Autowired
    private SolicitudEventoCapacitacionRepo solicitudEventoCapacitacionRepo;

    @Autowired
    private AreaSedeService areaSedeService;

    @Override
    public List<RelSolicitudEventoCapacitacionDTO> findAll() {
        return new ArrayList<>();
    }

    @Override
    public RelSolicitudEventoCapacitacionDTO buscarPorId(Integer id) {
        return new RelSolicitudEventoCapacitacionDTO();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultadoDTO<RelSolicitudEventoCapacitacionDTO> guardar(RelSolicitudEventoCapacitacionDTO dto) {

        ResultadoDTO<RelSolicitudEventoCapacitacionDTO> res = sonDatosRequeridosValidos(TipoAccion.PERSISTENCIA, dto);
        try {
            if (ObjectUtils.isNotNull(res) && res.getResultado().getValor()) {

                RelSolicitudEventoCapacitacion entity = mapper.map(dto, RelSolicitudEventoCapacitacion.class);
                entity = solicitudEventoCapacitacionRepo.save(entity);
                res.setDto(mapper.map(entity, RelSolicitudEventoCapacitacionDTO.class));

        //GUSTAVO --guardarBitacora(dto.getBitacoraDTO(), String.valueOf(entity.getIdReservacionEC()));

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
    public ResultadoDTO<RelSolicitudEventoCapacitacionDTO> actualizar(RelSolicitudEventoCapacitacionDTO dto) {

        ResultadoDTO<RelSolicitudEventoCapacitacionDTO> res = sonDatosRequeridosValidos(TipoAccion.ACTUALIZACION, dto);
        try {
            if (ObjectUtils.isNotNull(res) && res.getResultado().getValor()) {

                RelSolicitudEventoCapacitacion entity = mapper.map(dto, RelSolicitudEventoCapacitacion.class);
                entity = solicitudEventoCapacitacionRepo.save(entity);
                res.setDto(mapper.map(entity, RelSolicitudEventoCapacitacionDTO.class));

        //GUSTAVO --guardarBitacora(dto.getBitacoraDTO(), String.valueOf(entity.getIdReservacionEC()));

                res.agregaMensaje(MensajesSistemaEnum.ADMIN_MSG_ACTUALIZACION_EXITOSA.getId());
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            res.setMensajeError(MensajesSistemaEnum.ADMIN_MSG_ACTUALIZACION_FALLIDA);
        }

        return res;
    }

    @Override
    public ResultadoDTO<RelSolicitudEventoCapacitacionDTO> eliminar(RelSolicitudEventoCapacitacionDTO dto) {
        return new ResultadoDTO<>();
    }

    @Override
    public ResultadoDTO<RelSolicitudEventoCapacitacionDTO> sonDatosRequeridosValidos(TipoAccion accion,
            RelSolicitudEventoCapacitacionDTO dto) {

        ResultadoDTO<RelSolicitudEventoCapacitacionDTO> res = null;
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
    public void validarPersistencia(RelSolicitudEventoCapacitacionDTO dto,
            ResultadoDTO<RelSolicitudEventoCapacitacionDTO> res) {
        if (ObjectUtils.isNullOrEmpty(dto.getIdSolicitud())) {
            res.setMensajeError(
                    MensajesSistemaEnum.LOGISTICA_INFRAESTRUCTURA_SOLICITUD_EVENTO_CAPACITACION_ID_SOLICITUD_REQ);
        }

        if (ObjectUtils.isNullOrEmpty(dto.getIdReservacionEC())) {
            res.setMensajeError(
                    MensajesSistemaEnum.LOGISTICA_INFRAESTRUCTURA_SOLICITUD_EVENTO_CAPACITACION_ID_RESERVACION_EVENTO_CAPACITACION_REQ);
        }

    }

    @Override
    public void validarActualizacion(RelSolicitudEventoCapacitacionDTO dto,
            ResultadoDTO<RelSolicitudEventoCapacitacionDTO> res) {
        validarPersistencia(dto, res);
    }

    @Override
    public List<RelSolicitudEventoCapacitacionDTO> consultaSolicitudesPorIdReservacion(Integer idReservacion) {

        try {
            Type listAux = new TypeToken<List<RelSolicitudEventoCapacitacionDTO>>() {
            }.getType();
            List<RelSolicitudEventoCapacitacion> solicitudes = solicitudEventoCapacitacionRepo
                    .consultaSolicitudesPorIdReservacion(idReservacion);

            return mapper.map(solicitudes, listAux);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return new ArrayList<>();
    }

    @Override
    public List<RelSolicitudEventoCapacitacionDTO> consultaReservacionesPorIdSolicitud(Integer idSolicitud) {

        try {
            Type listAux = new TypeToken<List<RelSolicitudEventoCapacitacionDTO>>() {
            }.getType();
            List<RelSolicitudEventoCapacitacion> reservaciones = solicitudEventoCapacitacionRepo
                    .consultaReservacionesPorIdSolicitud(idSolicitud);

            return mapper.map(reservaciones, listAux);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return new ArrayList<>();

    }

    @Override
    public List<RelSolicitudEventoCapacitacionDTO> consultaPorCriterios(CritBusquedaReporteReservDTO criterios) {
        try {
            if (!ObjectUtils.isNullOrEmpty(criterios)) {
                Type lstSolicitudEventoCap = new TypeToken<List<RelSolicitudEventoCapacitacionDTO>>() {
                }.getType();

                List<Integer> areasSede = new ArrayList<>();
                Specification<RelSolicitudEventoCapacitacion> spec;

                if (!ObjectUtils.isNullOrCero(criterios.getIdArea())) {

                    Integer idAreaSede = areaSedeService
                            .consultaAreaSedePorIdSedeyIdArea(criterios.getIdSede(), criterios.getIdArea()).getIdAreaSede();

                    criterios.setIdAreaSede(idAreaSede);

                } else if (!ObjectUtils.isNullOrCero(criterios.getIdSede())) {
                    List<AreaSedeDTO> areaS = areaSedeService.consultaAreasSedePorIdSede(criterios.getIdSede());
                    for (AreaSedeDTO as : areaS) {
                        areasSede.add(as.getIdAreaSede());
                    }

                }

                spec = new SolicitudEventoCapacitacionEspecificacion(criterios, areasSede);
                List<RelSolicitudEventoCapacitacion> resultadoCriterios = solicitudEventoCapacitacionRepo.findAll(spec);

                return mapper.map(resultadoCriterios, lstSolicitudEventoCap);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

        return new ArrayList<>();
    }

    /**
     * @return the solicitudEventoCapacitacionRepo
     */
    public SolicitudEventoCapacitacionRepo getSolicitudEventoCapacitacionRepo() {
        return solicitudEventoCapacitacionRepo;
    }

    /**
     * @param solicitudEventoCapacitacionRepo the
     * solicitudEventoCapacitacionRepo to set
     */
    public void setSolicitudEventoCapacitacionRepo(SolicitudEventoCapacitacionRepo solicitudEventoCapacitacionRepo) {
        this.solicitudEventoCapacitacionRepo = solicitudEventoCapacitacionRepo;
    }

    public AreaSedeService getAreaSedeService() {
        return areaSedeService;
    }

    public void setAreaSedeService(AreaSedeService areaSedeService) {
        this.areaSedeService = areaSedeService;
    }

    @Override
    public void validarEliminacion(RelSolicitudEventoCapacitacionDTO dto, ResultadoDTO<RelSolicitudEventoCapacitacionDTO> resultado) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
