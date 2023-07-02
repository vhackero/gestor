package mx.gob.sedesol.basegestor.service.impl.admin;

import java.lang.reflect.Type;
import java.util.List;

import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.gob.sedesol.basegestor.commons.dto.admin.PersonaTelefonoDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.utils.MensajesSistemaEnum;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.TipoAccion;
import mx.gob.sedesol.basegestor.model.entities.admin.RelPersonaTelefono;
import mx.gob.sedesol.basegestor.model.repositories.admin.PersonaTelefonoRepo;
import mx.gob.sedesol.basegestor.service.admin.ComunValidacionService;
import mx.gob.sedesol.basegestor.service.admin.PersonaTelefonoService;

@Service("personaTelefonoService")
public class PersonaTelefonoServiceImpl extends ComunValidacionService<PersonaTelefonoDTO> implements PersonaTelefonoService {

    private static final Logger logger = Logger.getLogger(PersonaTelefonoServiceImpl.class);
    @Autowired
    private PersonaTelefonoRepo personaTelefonoRepo;

    private ModelMapper perTelMapper = new ModelMapper();

    @Override
    public void validarPersistencia(PersonaTelefonoDTO personaTelDto, ResultadoDTO<PersonaTelefonoDTO> res) {
        if (ObjectUtils.isNull(personaTelDto.getPersona())
                || ObjectUtils.isNull(personaTelDto.getPersona().getIdPersona())) {
            res.setMensajeError(MensajesSistemaEnum.TELEFONO_PERSONA_ID_PERSONA_REQ);
        }
        if (ObjectUtils.isNullOrEmpty(personaTelDto.getTipoTelefono())
                || ObjectUtils.isNullOrEmpty(personaTelDto.getTipoTelefono().getIdTipoTelefono())) {
            res.setMensajeError(MensajesSistemaEnum.TELEFONO_PERSONA_TIPO_REQ);
        }
        if (ObjectUtils.isNullOrEmpty(personaTelDto.getTelefono())) {
            res.setMensajeError(MensajesSistemaEnum.TELEFONO_PERSONA_TELEFONO_REQ);
        }
        if (ObjectUtils.isNull(personaTelDto.getFechaRegistro())) {
            res.setMensajeError(MensajesSistemaEnum.MSG_GENERAL_FECHA_REGISTRO_REQ);
        }
        if (ObjectUtils.isNullOrEmpty(personaTelDto.getUsuarioModifico())) {
            res.setMensajeError(MensajesSistemaEnum.MSG_GENERAL_USUARIO_MODIFICO_REQ);
        }
    }

    @Override
    public void validarActualizacion(PersonaTelefonoDTO personaTelDto, ResultadoDTO<PersonaTelefonoDTO> res) {
        if (ObjectUtils.isNull(personaTelDto.getPersona())
                || ObjectUtils.isNull(personaTelDto.getPersona().getIdPersona())) {
            res.setMensajeError(MensajesSistemaEnum.TELEFONO_PERSONA_ID_PERSONA_REQ);
        }
        if (ObjectUtils.isNullOrEmpty(personaTelDto.getTipoTelefono())
                || ObjectUtils.isNullOrEmpty(personaTelDto.getTipoTelefono().getIdTipoTelefono())) {
            res.setMensajeError(MensajesSistemaEnum.TELEFONO_PERSONA_TIPO_REQ);
        }
        if (ObjectUtils.isNullOrEmpty(personaTelDto.getTelefono())) {
            res.setMensajeError(MensajesSistemaEnum.TELEFONO_PERSONA_TELEFONO_REQ);
        }
        if (ObjectUtils.isNull(personaTelDto.getFechaActualizacion())) {
            res.setMensajeError(MensajesSistemaEnum.MSG_GENERAL_FECHA_EDICION_REQ);
        }
        if (ObjectUtils.isNullOrEmpty(personaTelDto.getUsuarioModifico())) {
            res.setMensajeError(MensajesSistemaEnum.MSG_GENERAL_USUARIO_MODIFICO_REQ);
        }
    }

    @Override
    public void validarEliminacion(PersonaTelefonoDTO personaTelDto, ResultadoDTO<PersonaTelefonoDTO> res) {
        if (ObjectUtils.isNull(personaTelDto.getIdPersonaTelefono())) {
            res.setMensajeError(MensajesSistemaEnum.TELEFONO_PERSONA_ID_REQ);
        }
        if (ObjectUtils.isNull(personaTelDto.getActivo())) {
            res.setMensajeError(MensajesSistemaEnum.MSG_GENERAL_ESTA_ACTIVO_REQ);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultadoDTO<PersonaTelefonoDTO> guardar(PersonaTelefonoDTO personaTelDto) {

        ResultadoDTO<PersonaTelefonoDTO> res = sonDatosRequeridosValidos(TipoAccion.PERSISTENCIA, personaTelDto);
        if (res.getResultado().getValor()) {

            try {
                res = new ResultadoDTO<>();
                RelPersonaTelefono entidadAux = perTelMapper.map(personaTelDto, RelPersonaTelefono.class);
                entidadAux = personaTelefonoRepo.save(entidadAux);
                res.setDto(perTelMapper.map(entidadAux, PersonaTelefonoDTO.class));

        //GUSTAVO --guardarBitacora(personaTelDto.getBitacoraDTO(), String.valueOf(entidadAux.getIdPersonaTelefono()));
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
    public ResultadoDTO<PersonaTelefonoDTO> eliminar(PersonaTelefonoDTO personaTelDto) {

        ResultadoDTO<PersonaTelefonoDTO> res = sonDatosRequeridosValidos(TipoAccion.ELIMINACION, personaTelDto);
        if (res.getResultado().getValor()) {

            try {
                res = new ResultadoDTO<>();
                RelPersonaTelefono entidadAux = perTelMapper.map(personaTelDto, RelPersonaTelefono.class);
                entidadAux = personaTelefonoRepo.saveAndFlush(entidadAux);
                res.setDto(perTelMapper.map(entidadAux, PersonaTelefonoDTO.class));

        //GUSTAVO --guardarBitacora(personaTelDto.getBitacoraDTO(), String.valueOf(entidadAux.getIdPersonaTelefono()));
                res.agregaMensaje(MensajesSistemaEnum.ADMIN_MSG_ELIMINACION_EXITOSA.getId());
            } catch (Exception e) {
                res.setMensajeError(MensajesSistemaEnum.ADMIN_MSG_ELIMINACION_FALLIDA);
                logger.error(e.getMessage(), e);
            }
        }

        return res;
    }

    @Override
    public List<PersonaTelefonoDTO> findAll() {
        List<RelPersonaTelefono> listaAux = personaTelefonoRepo.findAll();
        Type lstPerTelefono = new TypeToken<List<PersonaTelefonoDTO>>() {
        }.getType();
        return perTelMapper.map(listaAux, lstPerTelefono);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultadoDTO<PersonaTelefonoDTO> actualizar(PersonaTelefonoDTO personaTelDto) {

        ResultadoDTO<PersonaTelefonoDTO> res = sonDatosRequeridosValidos(TipoAccion.ACTUALIZACION, personaTelDto);
        if (res.getResultado().getValor()) {

            RelPersonaTelefono entAux = personaTelefonoRepo.findOne(personaTelDto.getIdPersonaTelefono());
            if (ObjectUtils.isNotNull(entAux)) {

                if (!personaTelDto.getPersona().getIdPersona().equals(entAux.getPersona().getIdPersona())) {
                    res.setMensajeError(MensajesSistemaEnum.MSG_GENERAL_IDENTIFICADORES_DIFERENTES);
                    return res;
                }

                try {
                    res = new ResultadoDTO<>();
                    entAux = perTelMapper.map(personaTelDto, RelPersonaTelefono.class);
                    entAux = personaTelefonoRepo.saveAndFlush(entAux);
                    res.setDto(perTelMapper.map(entAux, PersonaTelefonoDTO.class));

            //GUSTAVO --guardarBitacora(personaTelDto.getBitacoraDTO(), String.valueOf(entAux.getIdPersonaTelefono()));
                    res.agregaMensaje(MensajesSistemaEnum.ADMIN_MSG_ACTUALIZACION_EXITOSA.getId());
                } catch (Exception e) {
                    res.setMensajeError(MensajesSistemaEnum.ADMIN_MSG_ACTUALIZACION_FALLIDA);
                    logger.error(e.getMessage(), e);
                }

            } else {
                res.setMensajeError(MensajesSistemaEnum.ADMIN_MSG_ACTUALIZACION_FALLIDA);
            }
        }

        return res;
    }

    @Override
    public PersonaTelefonoDTO buscarPorId(Integer id) {
        PersonaTelefonoDTO personaTelefonoDTO;
        RelPersonaTelefono entidad = personaTelefonoRepo.findOne(id);
        if (ObjectUtils.isNull(entidad)) {
            personaTelefonoDTO = null;
        } else {
            personaTelefonoDTO = perTelMapper.map(entidad, PersonaTelefonoDTO.class);
        }
        return personaTelefonoDTO;
    }

    @Override
    public PersonaTelefonoDTO obtenerTelefonosPersonaPorTipo(Long idPersona, Integer idTipoTelefono) {
        List<RelPersonaTelefono> telefonos = personaTelefonoRepo.obtenerTelefonosPersonaPorTipo(idPersona, idTipoTelefono);
        PersonaTelefonoDTO telefono;
        if (telefonos.isEmpty()) {
            telefono = null;
        } else {
            telefono = perTelMapper.map(telefonos.get(0), PersonaTelefonoDTO.class);
        }
        return telefono;
    }
}
