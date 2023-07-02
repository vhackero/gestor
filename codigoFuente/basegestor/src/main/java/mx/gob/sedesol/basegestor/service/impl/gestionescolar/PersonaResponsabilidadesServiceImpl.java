package mx.gob.sedesol.basegestor.service.impl.gestionescolar;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.PersonaResponsabilidadesDTO;
import mx.gob.sedesol.basegestor.commons.utils.MensajesSistemaEnum;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.TipoAccion;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.RelPersonaResponsabilidades;
import mx.gob.sedesol.basegestor.model.repositories.gestionescolar.PersonaResponsabilidadesRepo;
import mx.gob.sedesol.basegestor.service.admin.ComunValidacionService;
import mx.gob.sedesol.basegestor.service.gestionescolar.PersonaResponsabilidadesService;

@Service("personaResponsabilidadesService")
public class PersonaResponsabilidadesServiceImpl extends ComunValidacionService<PersonaResponsabilidadesDTO> implements PersonaResponsabilidadesService {

    private static final Logger logger = Logger.getLogger(PersonaResponsabilidadesServiceImpl.class);

    @Autowired
    private PersonaResponsabilidadesRepo personaResponsabilidadesRepo;

    private ModelMapper mapper = new ModelMapper();

    @Override
    public List<PersonaResponsabilidadesDTO> obtienePersonasPorResponsabilidad(Integer idResponsabilidad) {
        try {
            List<RelPersonaResponsabilidades> personas = personaResponsabilidadesRepo
                    .obtienePersonasPorResponsabilidad(idResponsabilidad);
            for (RelPersonaResponsabilidades obj : personas) {
                obj.setRelReponsableProduccionOas(null);
                obj.getTblPersona().setPersonaCorreos(null);
                obj.getTblPersona().setRelPersonaRoles(null);
                obj.getTblPersona().setRelPersonaTelefonos(null);
                obj.getTblPersona().setDomiciliosPersonas(null);
            }
            List<PersonaResponsabilidadesDTO> listR = new ArrayList<>();
            for (RelPersonaResponsabilidades pr : personas) {
                PersonaResponsabilidadesDTO prDTO = new PersonaResponsabilidadesDTO();
                mapper.map(pr, prDTO);
                listR.add(prDTO);
            }
            return listR;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return new ArrayList<>();
    }

    @Override
    public List<PersonaResponsabilidadesDTO> obtienePersonasPorResponsabilidadList(List<Integer> idResponsabilidades) {
        try {
            List<RelPersonaResponsabilidades> personas = personaResponsabilidadesRepo
                    .obtienePersonasPorResponsabilidadList(idResponsabilidades);

            for (RelPersonaResponsabilidades obj : personas) {
                obj.setRelReponsableProduccionOas(null);
                obj.getTblPersona().setPersonaCorreos(null);
                obj.getTblPersona().setRelPersonaRoles(null);
                obj.getTblPersona().setRelPersonaTelefonos(null);
                obj.getTblPersona().setDomiciliosPersonas(null);
            }
            List<PersonaResponsabilidadesDTO> listR = new ArrayList<>();
            for (RelPersonaResponsabilidades pr : personas) {
                PersonaResponsabilidadesDTO prDTO = new PersonaResponsabilidadesDTO();
                mapper.map(pr, prDTO);
                listR.add(prDTO);
            }
            return listR;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return new ArrayList<>();
    }

    @Override
    public List<PersonaResponsabilidadesDTO> findAll() {
        return new ArrayList<>();
    }

    @Override
    public PersonaResponsabilidadesDTO buscarPorId(Integer id) {
        return new PersonaResponsabilidadesDTO();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultadoDTO<PersonaResponsabilidadesDTO> guardar(PersonaResponsabilidadesDTO dto) {
        ResultadoDTO<PersonaResponsabilidadesDTO> res = sonDatosRequeridosValidos(TipoAccion.PERSISTENCIA, dto);

        try {
            if (ObjectUtils.isNotNull(res) && res.getResultado().getValor()) {

                RelPersonaResponsabilidades perResp = mapper.map(dto, RelPersonaResponsabilidades.class);
                perResp = personaResponsabilidadesRepo.save(perResp);

                res = new ResultadoDTO<>();
                res.setDto(mapper.map(perResp, PersonaResponsabilidadesDTO.class));

            //GUSTAVO --guardarBitacoraPersonaResponsabilidad(dto, String.valueOf(perResp.getId()));
            }

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            res.setMensajeError(MensajesSistemaEnum.ADMIN_MSG_GUARDADO_FALLIDO);
            throw e;
        }
        return res;
    }

    @Override
    public ResultadoDTO<PersonaResponsabilidadesDTO> actualizar(PersonaResponsabilidadesDTO dto) {
        return new ResultadoDTO<>();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultadoDTO<PersonaResponsabilidadesDTO> eliminar(PersonaResponsabilidadesDTO dto) {

        ResultadoDTO<PersonaResponsabilidadesDTO> res = sonDatosRequeridosValidos(TipoAccion.ELIMINACION, dto);
        try {
            RelPersonaResponsabilidades perResp = mapper.map(dto, RelPersonaResponsabilidades.class);
            logger.info("linea 132");
            personaResponsabilidadesRepo.delete(perResp);
            logger.info("linea 134");
            res = new ResultadoDTO<>();
            res.setDto(mapper.map(perResp, PersonaResponsabilidadesDTO.class));
        } catch (Exception e) {
        	logger.info("entro en el servicio");
            logger.error(e.getMessage(), e);
            res.setMensajeError(MensajesSistemaEnum.ADMIN_MSG_ELIMINACION_FALLIDA);
        } 
        return res;
    }

    @Override
    public ResultadoDTO<PersonaResponsabilidadesDTO> sonDatosRequeridosValidos(TipoAccion accion,
            PersonaResponsabilidadesDTO dto) {
        ResultadoDTO<PersonaResponsabilidadesDTO> resultado = null;

        if (ObjectUtils.isNotNull(dto)) {
            resultado = new ResultadoDTO<>();

            switch (accion) {
                case PERSISTENCIA:
                    validarPersistencia(dto, resultado);
                    break;

                case ELIMINACION:
                    validarEliminacion(dto, resultado);
                    break;

                default:
                    resultado.setMensajeError(MensajesSistemaEnum.PLNT_MSG_OPERACION_REQ);
                    break;
            }
        }
        return resultado;
    }

    public void validarPersistencia(PersonaResponsabilidadesDTO dto,
            ResultadoDTO<PersonaResponsabilidadesDTO> resultado) {
        if (ObjectUtils.isNullOrEmpty(dto.getTblPersona())) {
            resultado.setMensajeError(MensajesSistemaEnum.PERSONA_RESPONSABILIDAD_ID_PERSONA_REQ);
        }
        if (ObjectUtils.isNullOrEmpty(dto.getCatTipoResponsabilidadEc())) {
            resultado.setMensajeError(MensajesSistemaEnum.PERSONA_RESPONSABILIDAD_ID_RESPONSABILIDAD_REQ);
        }
    }

    public void validarEliminacion(PersonaResponsabilidadesDTO dto,
            ResultadoDTO<PersonaResponsabilidadesDTO> resultado) {
        if (ObjectUtils.isNullOrEmpty(dto.getTblPersona())) {
            resultado.setMensajeError(MensajesSistemaEnum.PERSONA_RESPONSABILIDAD_ID_PERSONA_REQ);
        }
        if (ObjectUtils.isNullOrEmpty(dto.getCatTipoResponsabilidadEc())) {
            resultado.setMensajeError(MensajesSistemaEnum.PERSONA_RESPONSABILIDAD_ID_RESPONSABILIDAD_REQ);
        }
        if (ObjectUtils.isNullOrEmpty(dto.getId())) {
            resultado.setMensajeError(MensajesSistemaEnum.PERSONA_RESPONSABILIDAD_ID_REQ);
        }
    }


    @Override
    public void validarActualizacion(PersonaResponsabilidadesDTO dto, ResultadoDTO<PersonaResponsabilidadesDTO> resultado) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
