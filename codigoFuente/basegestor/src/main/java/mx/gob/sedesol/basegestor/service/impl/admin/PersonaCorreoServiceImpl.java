package mx.gob.sedesol.basegestor.service.impl.admin;

import java.lang.reflect.Type;
import java.util.List;

import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.gob.sedesol.basegestor.commons.constantes.ConstantesGestor;
import mx.gob.sedesol.basegestor.commons.dto.admin.PersonaCorreoDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.utils.MensajesSistemaEnum;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.TipoAccion;
import mx.gob.sedesol.basegestor.model.entities.admin.RelPersonaCorreo;
import mx.gob.sedesol.basegestor.model.repositories.admin.PersonaCorreoRepo;
import mx.gob.sedesol.basegestor.service.admin.ComunValidacionService;
import mx.gob.sedesol.basegestor.service.admin.PersonaCorreoService;

@Service("personaCorreoService")
public class PersonaCorreoServiceImpl extends ComunValidacionService<PersonaCorreoDTO> implements PersonaCorreoService {

    private static final Logger logger = Logger.getLogger(PersonaCorreoServiceImpl.class);

    @Autowired
    private PersonaCorreoRepo personaCorreoRepo;

    private ModelMapper perCorreoMapper = new ModelMapper();

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultadoDTO<PersonaCorreoDTO> guardar(PersonaCorreoDTO persCorreoDto) {

        ResultadoDTO<PersonaCorreoDTO> res = sonDatosRequeridosValidos(TipoAccion.PERSISTENCIA, persCorreoDto);

        if (res.getResultado().getValor()) {
            try {

                res = new ResultadoDTO<>();
                RelPersonaCorreo entAux = perCorreoMapper.map(persCorreoDto, RelPersonaCorreo.class);
                entAux = personaCorreoRepo.save(entAux);
                res.setDto(perCorreoMapper.map(entAux, PersonaCorreoDTO.class));
                res.agregaMensaje(MensajesSistemaEnum.ADMIN_MSG_GUARDADO_EXITOSO.getId());
            } catch (Exception e) {
                res.setMensajeError(MensajesSistemaEnum.ADMIN_MSG_GUARDADO_FALLIDO);
                logger.error(e.getMessage(), e);
            }
        }

        return res;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultadoDTO<PersonaCorreoDTO> eliminar(PersonaCorreoDTO persCorreoDto) {
        ResultadoDTO<PersonaCorreoDTO> resultado = sonDatosRequeridosValidos(TipoAccion.ELIMINACION, persCorreoDto);
        if (resultado.getResultado().getValor()) {
            try {

                resultado = new ResultadoDTO<>();
                RelPersonaCorreo entidad = perCorreoMapper.map(persCorreoDto, RelPersonaCorreo.class);
                entidad = personaCorreoRepo.saveAndFlush(entidad);
                resultado.setDto(perCorreoMapper.map(entidad, PersonaCorreoDTO.class));
                resultado.agregaMensaje(MensajesSistemaEnum.ADMIN_MSG_ELIMINACION_EXITOSA.getId());
            } catch (Exception e) {
                resultado.setMensajeError(MensajesSistemaEnum.ADMIN_MSG_ELIMINACION_FALLIDA);
                logger.error(e.getMessage(), e);
            }
        }
        return resultado;
    }

    @Override
    public List<PersonaCorreoDTO> findAll() {
        List<RelPersonaCorreo> listaAux = personaCorreoRepo.findAll();
        Type lstPersCorreos = new TypeToken<List<PersonaCorreoDTO>>() {
        }.getType();
        return perCorreoMapper.map(listaAux, lstPersCorreos);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultadoDTO<PersonaCorreoDTO> actualizar(PersonaCorreoDTO persCorreoDto) {
        ResultadoDTO<PersonaCorreoDTO> res = sonDatosRequeridosValidos(TipoAccion.ACTUALIZACION, persCorreoDto);
        if (res.getResultado().getValor()) {

            RelPersonaCorreo personaCorreo = personaCorreoRepo.findOne(persCorreoDto.getIdPersonaCorreo());
            if (ObjectUtils.isNotNull(personaCorreo)) {

                if (!personaCorreo.getPersona().getIdPersona().equals(persCorreoDto.getPersona().getIdPersona())) {
                    res.setMensajeError(MensajesSistemaEnum.MSG_GENERAL_IDENTIFICADORES_DIFERENTES);
                    return res;
                }

                try {
                    res = new ResultadoDTO<>();
                    personaCorreo = perCorreoMapper.map(persCorreoDto, RelPersonaCorreo.class);
                    personaCorreo = personaCorreoRepo.saveAndFlush(personaCorreo);
                    res.setDto(perCorreoMapper.map(personaCorreo, PersonaCorreoDTO.class));
                    res.agregaMensaje(MensajesSistemaEnum.ADMIN_MSG_ACTUALIZACION_EXITOSA.getId());
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                    res.setMensajeError(MensajesSistemaEnum.ADMIN_MSG_ACTUALIZACION_FALLIDA);
                }

            } else {
                res.setMensajeError(MensajesSistemaEnum.ADMIN_MSG_ACTUALIZACION_FALLIDA);
            }
        }
        return res;
    }

    @Override
    public PersonaCorreoDTO buscarPorId(Integer id) {
        PersonaCorreoDTO personaCorreoDTO;
        RelPersonaCorreo entidad = personaCorreoRepo.findOne(id);
        if (ObjectUtils.isNull(entidad)) {
            personaCorreoDTO = null;
        } else {
            personaCorreoDTO = perCorreoMapper.map(entidad, PersonaCorreoDTO.class);
        }
        return personaCorreoDTO;
    }

    @Override
    public PersonaCorreoDTO buscaPersonaCorreoElectronico(String correo) {
        PersonaCorreoDTO resultado = null;
        try {
            RelPersonaCorreo personaCorreo = personaCorreoRepo.obtienePersonaCorreoElectronico(correo);
            if (ObjectUtils.isNotNull(personaCorreo)) {
                resultado = perCorreoMapper.map(personaCorreo, PersonaCorreoDTO.class);
            }

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

        return resultado;
    }

    @Override
    @Transactional(readOnly = true)
    public PersonaCorreoDTO obtenerCorreoInstitucional(Long idPersona) {
        List<RelPersonaCorreo> correos = personaCorreoRepo.obtenerCorreoPersonaPorTipo(idPersona, ConstantesGestor.TIPO_CORREO_INSTITUCIONAL);
        PersonaCorreoDTO correo;
        if (correos.isEmpty()) {
            correo = null;;
        } else {
            correo = perCorreoMapper.map(correos.get(0), PersonaCorreoDTO.class);
        }
        return correo;
    }
    


    @Override
    public void validarPersistencia(PersonaCorreoDTO persCorreoDto, ResultadoDTO<PersonaCorreoDTO> res) {
        if (ObjectUtils.isNull(persCorreoDto.getPersona())
                || ObjectUtils.isNull(persCorreoDto.getPersona().getIdPersona())) {
            res.setMensajeError(MensajesSistemaEnum.CORREO_PERSONA_ID_PERSONA_REQ);
        }
        if (ObjectUtils.isNull(persCorreoDto.getTipoCorreo())
                || ObjectUtils.isNull(persCorreoDto.getTipoCorreo().getIdTipoCorreo())) {
            res.setMensajeError(MensajesSistemaEnum.CORREO_PERSONA_TIPO_REQ);
        }
        if (ObjectUtils.isNullOrEmpty(persCorreoDto.getCorreoElectronico())) {
            res.setMensajeError(MensajesSistemaEnum.CORREO_PERSONA_CORREO_REQ);
        }
        if (ObjectUtils.isNull(persCorreoDto.getFechaRegistro())) {
            res.setMensajeError(MensajesSistemaEnum.MSG_GENERAL_FECHA_REGISTRO_REQ);
        }
        if (ObjectUtils.isNullOrEmpty(persCorreoDto.getUsuarioModifico())) {
            res.setMensajeError(MensajesSistemaEnum.MSG_GENERAL_USUARIO_MODIFICO_REQ);
        }
    }

    @Override
    public void validarActualizacion(PersonaCorreoDTO persCorreoDto, ResultadoDTO<PersonaCorreoDTO> res) {
        if (ObjectUtils.isNull(persCorreoDto.getPersona())
                || ObjectUtils.isNull(persCorreoDto.getPersona().getIdPersona())) {
            res.setMensajeError(MensajesSistemaEnum.CORREO_PERSONA_ID_PERSONA_REQ);
        }
        if (ObjectUtils.isNull(persCorreoDto.getTipoCorreo())
                || ObjectUtils.isNull(persCorreoDto.getTipoCorreo().getIdTipoCorreo())) {
            res.setMensajeError(MensajesSistemaEnum.CORREO_PERSONA_TIPO_REQ);
        }
        if (ObjectUtils.isNullOrEmpty(persCorreoDto.getCorreoElectronico())) {
            res.setMensajeError(MensajesSistemaEnum.CORREO_PERSONA_CORREO_REQ);
        }
        if (ObjectUtils.isNullOrEmpty(persCorreoDto.getFechaActualizacion())) {
            res.setMensajeError(MensajesSistemaEnum.MSG_GENERAL_FECHA_EDICION_REQ);
        }
        if (ObjectUtils.isNullOrEmpty(persCorreoDto.getUsuarioModifico())) {
            res.setMensajeError(MensajesSistemaEnum.MSG_GENERAL_USUARIO_MODIFICO_REQ);
        }
    }

    @Override
    public void validarEliminacion(PersonaCorreoDTO persCorreoDto, ResultadoDTO<PersonaCorreoDTO> res) {
        if (ObjectUtils.isNull(persCorreoDto.getIdPersonaCorreo())) {
            res.setMensajeError(MensajesSistemaEnum.CORREO_PERSONA_ID_REQ);
        }
        if (ObjectUtils.isNull(persCorreoDto.getActivo())) {
            res.setMensajeError(MensajesSistemaEnum.MSG_GENERAL_ESTA_ACTIVO_REQ);
        }
    }

}
