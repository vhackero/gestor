package mx.gob.sedesol.basegestor.service.impl.admin;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import mx.gob.sedesol.basegestor.commons.dto.admin.DomicilioPersonaDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.utils.MensajesSistemaEnum;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.TipoAccion;
import mx.gob.sedesol.basegestor.model.entities.admin.TblDomiciliosPersona;
import mx.gob.sedesol.basegestor.model.entities.admin.TblDomiciliosPersonaReporteUsuario;
import mx.gob.sedesol.basegestor.model.especificaciones.DomiciliosPersonaEspecificacion;
import mx.gob.sedesol.basegestor.model.especificaciones.DomiciliosPersonaReporteUsuarioEspecificacion;
import mx.gob.sedesol.basegestor.model.repositories.admin.DomicilioPersonaRepo;
import mx.gob.sedesol.basegestor.model.repositories.admin.DomicilioPersonaReporteUsuarioRepo;
import mx.gob.sedesol.basegestor.service.admin.ComunValidacionService;
import mx.gob.sedesol.basegestor.service.admin.DomicilioPersonaService;

@Service("domicilioPersonaService")
public class DomicilioPersonaServiceImpl extends ComunValidacionService<DomicilioPersonaDTO>
        implements DomicilioPersonaService {

    private static final Logger logger = Logger.getLogger(DomicilioPersonaServiceImpl.class);

    @Autowired
    private DomicilioPersonaRepo domicilioPersonaRepo;
    
    @Autowired
    private DomicilioPersonaReporteUsuarioRepo domicilioPersonaReporteUsuarioRepo;

    private ModelMapper domPersonaMapper = new ModelMapper();

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultadoDTO<DomicilioPersonaDTO> guardar(DomicilioPersonaDTO domPersonaDto) {

        ResultadoDTO<DomicilioPersonaDTO> res = sonDatosRequeridosValidos(TipoAccion.PERSISTENCIA, domPersonaDto);
        if (res.getResultado().getValor()) {
            try {
                res = new ResultadoDTO<>();
                TblDomiciliosPersona domPersona = domPersonaMapper.map(domPersonaDto, TblDomiciliosPersona.class);
                domicilioPersonaRepo.save(domPersona);
                res.setDto(domPersonaMapper.map(domPersona, DomicilioPersonaDTO.class));

                //GUSTAVO --guardarBitacoraDomicilio(domPersonaDto, String.valueOf(domPersona.getIdDomicilioPersona()));

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
    public ResultadoDTO<DomicilioPersonaDTO> eliminar(DomicilioPersonaDTO domPersonaDto) {

        ResultadoDTO<DomicilioPersonaDTO> res = sonDatosRequeridosValidos(TipoAccion.ELIMINACION, domPersonaDto);
        if (res.getResultado().getValor()) {
            try {
                res = new ResultadoDTO<>();
                TblDomiciliosPersona entidad = domPersonaMapper.map(domPersonaDto, TblDomiciliosPersona.class);
                domicilioPersonaRepo.saveAndFlush(entidad);
                res.setDto(domPersonaMapper.map(entidad, domPersonaDto.getClass()));

                //GUSTAVO --guardarBitacoraDomicilio(domPersonaDto, String.valueOf(entidad.getIdDomicilioPersona()));

                res.agregaMensaje(MensajesSistemaEnum.ADMIN_MSG_ELIMINACION_EXITOSA.getId());

            } catch (Exception e) {
                res.setMensajeError(MensajesSistemaEnum.ADMIN_MSG_ELIMINACION_FALLIDA);
                logger.error(e.getMessage(), e);
            }
        }
        return res;
    }

    @Override
    public List<DomicilioPersonaDTO> findAll() {

        List<TblDomiciliosPersona> lstDomicsPersona = domicilioPersonaRepo.findAll();
        for (TblDomiciliosPersona tblDomicilio : lstDomicsPersona) {
            tblDomicilio.getPersona().setDomiciliosPersonas(null);
            tblDomicilio.getPersona().setPersonaCorreos(null);
            tblDomicilio.getPersona().setRelPersonaTelefonos(null);
            tblDomicilio.getPersona().setRelPersonaRoles(null);
            tblDomicilio.getPersona().setDomiciliosPersonas(null);
        }
        Type lstDomPersona = new TypeToken<List<DomicilioPersonaDTO>>() {
        }.getType();
        return domPersonaMapper.map(lstDomicsPersona, lstDomPersona);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultadoDTO<DomicilioPersonaDTO> actualizar(DomicilioPersonaDTO domPersonaDto) {

        ResultadoDTO<DomicilioPersonaDTO> result = sonDatosRequeridosValidos(TipoAccion.ACTUALIZACION, domPersonaDto);
        if (result.getResultado().getValor()) {
            TblDomiciliosPersona domPersona = domicilioPersonaRepo.findOne(domPersonaDto.getIdDomicilioPersona());
            if (ObjectUtils.isNotNull(domPersona)) {
                if (!domPersona.getPersona().getIdPersona().equals(domPersonaDto.getPersona().getIdPersona())) {
                    result.setMensajeError(MensajesSistemaEnum.MSG_GENERAL_IDENTIFICADORES_DIFERENTES);
                    return result;
                }

                try {
                    result = new ResultadoDTO<>();
                    domPersona = domPersonaMapper.map(domPersonaDto, TblDomiciliosPersona.class);
                    domicilioPersonaRepo.saveAndFlush(domPersona);
                    result.setDto(domPersonaMapper.map(domPersona, DomicilioPersonaDTO.class));

                    //GUSTAVO --guardarBitacoraDomicilio(domPersonaDto, String.valueOf(domPersona.getIdDomicilioPersona()));
                    result.agregaMensaje(MensajesSistemaEnum.ADMIN_MSG_ACTUALIZACION_EXITOSA.getId());
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                    result.setMensajeError(MensajesSistemaEnum.ADMIN_MSG_ACTUALIZACION_FALLIDA);
                }
            } else {
                result.setMensajeError(MensajesSistemaEnum.ADMIN_MSG_ACTUALIZACION_FALLIDA);
            }
        }
        return result;
    }

    @Override
    public DomicilioPersonaDTO buscarPorId(Long id) {
        DomicilioPersonaDTO domicilio = new DomicilioPersonaDTO();
        TblDomiciliosPersona tblDomicilio = domicilioPersonaRepo.findOne(id);
        if (ObjectUtils.isNull(tblDomicilio)) {
            domicilio = null;
        } else {
            tblDomicilio.getPersona().setDomiciliosPersonas(null);
            tblDomicilio.getPersona().setPersonaCorreos(null);
            tblDomicilio.getPersona().setRelPersonaTelefonos(null);
            tblDomicilio.getPersona().setRelPersonaRoles(null);
            tblDomicilio.getPersona().setDomiciliosPersonas(null);
            domPersonaMapper.map(tblDomicilio, domicilio);
        }
        return domicilio;
    }

    @Override
    public void validarPersistencia(DomicilioPersonaDTO domPersonaDto, ResultadoDTO<DomicilioPersonaDTO> res) {
        if (ObjectUtils.isNull(domPersonaDto.getCalle())) {
            res.setMensajeError(MensajesSistemaEnum.DOMICILIO_PERSONA_CALLE_REQ);
        }
        if (ObjectUtils.isNull(domPersonaDto.getNumeroExterior())) {
            res.setMensajeError(MensajesSistemaEnum.DOMICILIO_PERSONA_NUMERO_REQ);
        }
        if (ObjectUtils.isNull(domPersonaDto.getPersona())
                || ObjectUtils.isNull(domPersonaDto.getPersona().getIdPersona())) {
            res.setMensajeError(MensajesSistemaEnum.DOMICILIO_PERSONA_ID_PERSONA_REQ);
        }
        if (ObjectUtils.isNull(domPersonaDto.getAsentamiento())
                || ObjectUtils.isNull(domPersonaDto.getAsentamiento().getIdAsentamiento())) {
            res.setMensajeError(MensajesSistemaEnum.DOMICILIO_PERSONA_LOCALIDAD_REQ);
        }
        if (ObjectUtils.isNull(domPersonaDto.getFechaRegistro())) {
            res.setMensajeError(MensajesSistemaEnum.MSG_GENERAL_FECHA_REGISTRO_REQ);
        }
    }

    @Override
    public void validarActualizacion(DomicilioPersonaDTO domPersonaDto, ResultadoDTO<DomicilioPersonaDTO> res) {
        if (ObjectUtils.isNull(domPersonaDto.getIdDomicilioPersona())) {
            res.setMensajeError(MensajesSistemaEnum.DOMICILIO_PERSONA_ID_REQ);
        }
        if (ObjectUtils.isNull(domPersonaDto.getCalle())) {
            res.setMensajeError(MensajesSistemaEnum.DOMICILIO_PERSONA_CALLE_REQ);
        }
        if (ObjectUtils.isNull(domPersonaDto.getNumeroExterior())) {
            res.setMensajeError(MensajesSistemaEnum.DOMICILIO_PERSONA_NUMERO_REQ);
        }
        if (ObjectUtils.isNull(domPersonaDto.getAsentamiento())
                || ObjectUtils.isNull(domPersonaDto.getAsentamiento().getIdAsentamiento())) {
            res.setMensajeError(MensajesSistemaEnum.DOMICILIO_PERSONA_LOCALIDAD_REQ);
        }
        if (ObjectUtils.isNullOrEmpty(domPersonaDto.getFechaActualizacion())) {
            res.setMensajeError(MensajesSistemaEnum.MSG_GENERAL_FECHA_EDICION_REQ);
        }
        if (ObjectUtils.isNullOrEmpty(domPersonaDto.getUsuarioModifico())) {
            res.setMensajeError(MensajesSistemaEnum.MSG_GENERAL_USUARIO_MODIFICO_REQ);
        }
    }

    @Override
    public void validarEliminacion(DomicilioPersonaDTO domPersonaDto, ResultadoDTO<DomicilioPersonaDTO> res) {
        if (ObjectUtils.isNull(domPersonaDto.getIdDomicilioPersona())) {
            res.setMensajeError(MensajesSistemaEnum.DOMICILIO_PERSONA_ID_REQ);
        }
        if (ObjectUtils.isNull(domPersonaDto.getActivo())) {
            res.setMensajeError(MensajesSistemaEnum.MSG_GENERAL_ESTA_ACTIVO_REQ);
        }
    }

    @Override
    public List<DomicilioPersonaDTO> busquedaPorCriterios(DomicilioPersonaDTO criterios, String tipoDatoFechas) {
        try {
            if (ObjectUtils.isNotNull(criterios)) {
                Type lstDomPersona = new TypeToken<List<DomicilioPersonaDTO>>() {
                }.getType();

                Specification<TblDomiciliosPersona> spec = new DomiciliosPersonaEspecificacion(criterios, tipoDatoFechas);

                List<TblDomiciliosPersona> resultadoCriterios = domicilioPersonaRepo.findAll(spec, sortByFechaActualizacionDesc());

                return domPersonaMapper.map(resultadoCriterios, lstDomPersona);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

        return new ArrayList<>();
    }
    
    @Override
    public List<DomicilioPersonaDTO> busquedaPorCriteriosReporteUsuario(DomicilioPersonaDTO criterios, String tipoDatoFechas) {
        try {
            if (ObjectUtils.isNotNull(criterios)) {
                Type lstDomPersona = new TypeToken<List<DomicilioPersonaDTO>>() {
                }.getType();

                Specification<TblDomiciliosPersonaReporteUsuario> spec = new DomiciliosPersonaReporteUsuarioEspecificacion(criterios, tipoDatoFechas);

                List<TblDomiciliosPersonaReporteUsuario> resultadoCriterios = domicilioPersonaReporteUsuarioRepo.findAll(spec);

                return domPersonaMapper.map(resultadoCriterios, lstDomPersona);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

        return new ArrayList<>();
    }

    private Sort sortByFechaActualizacionDesc() {
        return new Sort(Sort.Direction.DESC, "fechaActualizacion");
    }

}
