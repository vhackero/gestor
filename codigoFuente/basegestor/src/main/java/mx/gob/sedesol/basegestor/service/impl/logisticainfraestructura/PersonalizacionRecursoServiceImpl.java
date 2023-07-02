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
import mx.gob.sedesol.basegestor.commons.dto.logisticainfraestructura.RelPersonalizacionRecursoDTO;
import mx.gob.sedesol.basegestor.commons.utils.MensajesSistemaEnum;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.TipoAccion;
import mx.gob.sedesol.basegestor.model.entities.logisticainfraestructura.RelPersonalizacionRecurso;
import mx.gob.sedesol.basegestor.model.repositories.logisticainfraestructura.PersonalizacionRecursoRepo;
import mx.gob.sedesol.basegestor.service.admin.ComunValidacionService;
import mx.gob.sedesol.basegestor.service.logisticainfraestructura.PersonalizacionRecursoService;

@Service("personalizacionRecursoService")
public class PersonalizacionRecursoServiceImpl extends ComunValidacionService<RelPersonalizacionRecursoDTO> implements PersonalizacionRecursoService {

    private static final long serialVersionUID = -1278870087143650584L;

    private static final Logger logger = Logger.getLogger(PersonalizacionRecursoServiceImpl.class);

    @Autowired
    private PersonalizacionRecursoRepo personalizacionRecursoRepo;

    private ModelMapper mapper = new ModelMapper();

    @Override
    public List<RelPersonalizacionRecursoDTO> findAll() {
        return new ArrayList<>();
    }

    @Override
    public RelPersonalizacionRecursoDTO buscarPorId(Integer id) {
        return new RelPersonalizacionRecursoDTO();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultadoDTO<RelPersonalizacionRecursoDTO> guardar(RelPersonalizacionRecursoDTO dto) {

        ResultadoDTO<RelPersonalizacionRecursoDTO> res = sonDatosRequeridosValidos(TipoAccion.PERSISTENCIA, dto);
        try {
            if (ObjectUtils.isNotNull(res) && res.getResultado().getValor()) {

                RelPersonalizacionRecurso entity = mapper.map(dto, RelPersonalizacionRecurso.class);
                entity = personalizacionRecursoRepo.save(entity);
                res.setDto(mapper.map(entity, RelPersonalizacionRecursoDTO.class));

            //GUSTAVO --guardarBitacoraPersonalizacion(dto, String.valueOf(entity.getIdPersonalizacionArea()));

                res.agregaMensaje(MensajesSistemaEnum.ADMIN_MSG_GUARDADO_EXITOSO.getId());
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            res.setMensajeError(MensajesSistemaEnum.ADMIN_MSG_GUARDADO_FALLIDO);
        }

        return res;
    }

    @Override
    public ResultadoDTO<RelPersonalizacionRecursoDTO> actualizar(RelPersonalizacionRecursoDTO dto) {
        return new ResultadoDTO<>();
    }

    @Override
    public ResultadoDTO<RelPersonalizacionRecursoDTO> eliminar(RelPersonalizacionRecursoDTO dto) {
        return new ResultadoDTO<>();
    }

    @Override
    public ResultadoDTO<RelPersonalizacionRecursoDTO> sonDatosRequeridosValidos(TipoAccion accion,
            RelPersonalizacionRecursoDTO dto) {

        ResultadoDTO<RelPersonalizacionRecursoDTO> res = null;
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
     *
     */
    @Override
    public List<RelPersonalizacionRecursoDTO> consultaRelPersonalizacionArea(Integer idPersonalizacion) {
        List<RelPersonalizacionRecurso> resultado = personalizacionRecursoRepo.consultaRelPersonalizacionArea(idPersonalizacion);
        Type listAux = new TypeToken<List<RelPersonalizacionRecursoDTO>>() {
        }.getType();
        return mapper.map(resultado, listAux);
    }

    public ModelMapper getMapper() {
        return mapper;
    }

    public void setMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public void validarPersistencia(RelPersonalizacionRecursoDTO dto,
            ResultadoDTO<RelPersonalizacionRecursoDTO> resultado) {
        if (ObjectUtils.isNullOrEmpty(dto.getIdPersonalizacionArea())) {
            resultado.setMensajeError(MensajesSistemaEnum.LOGISTICA_INFRAESTRUCTURA_PERSONALIZACION_RECURSO_ID_PERSONALIZACION_AREA_REQ);
        }

        if (ObjectUtils.isNullOrEmpty(dto.getIdRecurso())) {
            resultado.setMensajeError(MensajesSistemaEnum.LOGISTICA_INFRAESTRUCTURA_PERSONALIZACION_RECURSO_ID_RECURSO_REQ);
        }

    }

    @Override
    public void validarActualizacion(RelPersonalizacionRecursoDTO dto,
            ResultadoDTO<RelPersonalizacionRecursoDTO> resultado) {
        // TODO Auto-generated method stub

    }

    @Override
    public void validarEliminacion(RelPersonalizacionRecursoDTO dto,
            ResultadoDTO<RelPersonalizacionRecursoDTO> resultado) {
        // TODO Auto-generated method stub

    }
}
