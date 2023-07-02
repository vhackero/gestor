package mx.gob.sedesol.basegestor.service.impl.logisticainfraestructura;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.logisticainfraestructura.PersonalizacionAreaDTO;
import mx.gob.sedesol.basegestor.commons.utils.MensajesSistemaEnum;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.TipoAccion;
import mx.gob.sedesol.basegestor.model.entities.logisticainfraestructura.TblPersonalizacionArea;
import mx.gob.sedesol.basegestor.model.repositories.logisticainfraestructura.PersonalizacionAreaRepo;
import mx.gob.sedesol.basegestor.service.admin.ComunValidacionService;
import mx.gob.sedesol.basegestor.service.logisticainfraestructura.PersonalizacionAreaService;

@Service("personalizacionAreaService")
public class PersonalizacionAreaServiceImpl extends ComunValidacionService<PersonalizacionAreaDTO> implements PersonalizacionAreaService {

    private static final long serialVersionUID = -2816391007615684736L;

    private static final Logger logger = Logger.getLogger(PersonalizacionAreaServiceImpl.class);

    @Autowired
    private PersonalizacionAreaRepo personalizacionAreaRepo;

    private transient ModelMapper mapper = new ModelMapper();

    @Override
    public List<PersonalizacionAreaDTO> findAll() {
        return new ArrayList<>();
    }

    @Override
    public PersonalizacionAreaDTO buscarPorId(Integer id) {
        TblPersonalizacionArea entidad = personalizacionAreaRepo.findOne(id);
        return mapper.map(entidad, PersonalizacionAreaDTO.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultadoDTO<PersonalizacionAreaDTO> guardar(PersonalizacionAreaDTO dto) {
        ResultadoDTO<PersonalizacionAreaDTO> res = sonDatosRequeridosValidos(TipoAccion.PERSISTENCIA, dto);
        try {
            if (ObjectUtils.isNotNull(res) && res.getResultado().getValor()) {

                TblPersonalizacionArea entity = mapper.map(dto, TblPersonalizacionArea.class);
                entity = personalizacionAreaRepo.save(entity);
                res.setDto(mapper.map(entity, PersonalizacionAreaDTO.class));

            //GUSTAVO --guardarBitacoraPersonalizacion(dto, String.valueOf(entity.getIdPersonalizacion()));

                res.agregaMensaje(MensajesSistemaEnum.ADMIN_MSG_GUARDADO_EXITOSO.getId());
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            res.setMensajeError(MensajesSistemaEnum.ADMIN_MSG_GUARDADO_FALLIDO);
        }

        return res;
    }

    @Override
    public ResultadoDTO<PersonalizacionAreaDTO> actualizar(PersonalizacionAreaDTO dto) {
        ResultadoDTO<PersonalizacionAreaDTO> res = sonDatosRequeridosValidos(TipoAccion.PERSISTENCIA, dto);
        try {
            if (ObjectUtils.isNotNull(res) && res.getResultado().getValor()) {

                TblPersonalizacionArea entity = mapper.map(dto, TblPersonalizacionArea.class);
                entity = personalizacionAreaRepo.save(entity);
                res.setDto(mapper.map(entity, PersonalizacionAreaDTO.class));

            //GUSTAVO --guardarBitacoraPersonalizacion(dto, String.valueOf(entity.getIdPersonalizacion()));

                res.agregaMensaje(MensajesSistemaEnum.ADMIN_MSG_ACTUALIZACION_EXITOSA.getId());
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            res.setMensajeError(MensajesSistemaEnum.ADMIN_MSG_ACTUALIZACION_FALLIDA);
        }

        return res;
    }

    @Override
    public ResultadoDTO<PersonalizacionAreaDTO> eliminar(PersonalizacionAreaDTO dto) {
        return new ResultadoDTO<>();
    }

    @Override
    public ResultadoDTO<PersonalizacionAreaDTO> sonDatosRequeridosValidos(TipoAccion accion,
            PersonalizacionAreaDTO dto) {
        ResultadoDTO<PersonalizacionAreaDTO> res = null;
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
    public void validarActualizacion(PersonalizacionAreaDTO dto, ResultadoDTO<PersonalizacionAreaDTO> res) {
        if (ObjectUtils.isNullOrEmpty(dto.getIdPersonalizacion())) {
            res.setMensajeError(MensajesSistemaEnum.LOGISTICA_INFRAESTRUCTURA_PERSONALIZACION_AREA_ID_PERSONALIZACION_REQ);
        }

        if (ObjectUtils.isNullOrEmpty(dto.getCatAreasSede().getIdAreaSede())) {
            res.setMensajeError(MensajesSistemaEnum.LOGISTICA_INFRAESTRUCTURA_PERSONALIZACION_AREA_ID_AREA_SEDE_REQ);
        }

        if (ObjectUtils.isNull(dto.getFechaActualizacion())) {
            res.setMensajeError(MensajesSistemaEnum.LOGISTICA_INFRAESTRUCTURA_PERSONALIZACION_AREA_FECHA_ACTUALIZACION_REQ);
        }
    }

    @Override
    public void validarPersistencia(PersonalizacionAreaDTO dto, ResultadoDTO<PersonalizacionAreaDTO> res) {
        if (ObjectUtils.isNullOrEmpty(dto.getCatAreasSede().getIdAreaSede())) {
            res.setMensajeError(MensajesSistemaEnum.LOGISTICA_INFRAESTRUCTURA_PERSONALIZACION_AREA_ID_AREA_SEDE_REQ);
        }

        if (ObjectUtils.isNullOrEmpty(dto.getFechaRegistro())) {
            res.setMensajeError(MensajesSistemaEnum.LOGISTICA_INFRAESTRUCTURA_PERSONALIZACION_AREA_FECHA_REGISTRO_REQ);
        }
    }

    public PersonalizacionAreaRepo getPersonalizacionAreaRepo() {
        return personalizacionAreaRepo;
    }

    public void setPersonalizacionAreaRepo(PersonalizacionAreaRepo personalizacionAreaRepo) {
        this.personalizacionAreaRepo = personalizacionAreaRepo;
    }

    public ModelMapper getMaper() {
        return mapper;
    }

    public void setMaper(ModelMapper maper) {
        this.mapper = maper;
    }

    @Override
    public void validarEliminacion(PersonalizacionAreaDTO dto, ResultadoDTO<PersonalizacionAreaDTO> resultado) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
