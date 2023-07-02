package mx.gob.sedesol.basegestor.service.impl.gestionescolar;

import java.lang.reflect.Type;
import java.util.List;

import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.ReponsableProduccionEcDTO;
import mx.gob.sedesol.basegestor.commons.utils.MensajesErrorEnum;
import mx.gob.sedesol.basegestor.commons.utils.MensajesSistemaEnum;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.ResultadoTransaccionEnum;
import mx.gob.sedesol.basegestor.commons.utils.TipoAccion;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.RelReponsableProduccionEc;
import mx.gob.sedesol.basegestor.model.repositories.gestion.aprendizaje.RelReponsableProduccionEcRepo;
import mx.gob.sedesol.basegestor.service.admin.ComunValidacionService;
import mx.gob.sedesol.basegestor.service.gestionescolar.RelReponsableProduccionEcService;

@Service("relReponsableProduccionEcService")
public class RelReponsableProduccionEcServiceImpl extends ComunValidacionService<ReponsableProduccionEcDTO> implements RelReponsableProduccionEcService {

    @Autowired
    private RelReponsableProduccionEcRepo relReponsableProduccionEcRepo;

    private ModelMapper mapper = new ModelMapper();

    private static final Logger logger = Logger.getLogger(RelReponsableProduccionEcServiceImpl.class);

    @Override
    public List<ReponsableProduccionEcDTO> getResponsableDelEvento(int idEvento, int idTipoResponsabilidad) {

        List<RelReponsableProduccionEc> lista;
        List<ReponsableProduccionEcDTO> listaDTO;
        lista = relReponsableProduccionEcRepo.getResponsableDelEvento(idEvento, idTipoResponsabilidad);

        Type objetoDTO = new TypeToken<List<ReponsableProduccionEcDTO>>() {
        }.getType();

        for (RelReponsableProduccionEc resProd : lista) {
            resProd.setEventoCapacitacion(null);
            resProd.getPersonaResponsabilidad().setRelReponsableProduccionOas(null);
            resProd.getPersonaResponsabilidad().getTblPersona().setPersonaCorreos(null);
            resProd.getPersonaResponsabilidad().getTblPersona().setRelPersonaRoles(null);
            resProd.getPersonaResponsabilidad().getTblPersona().setRelPersonaTelefonos(null);
            resProd.getPersonaResponsabilidad().getTblPersona().setDomiciliosPersonas(null);

        }

        listaDTO = mapper.map(lista, objetoDTO);

        return listaDTO;
    }

    @Override
    public List<ReponsableProduccionEcDTO> getResponsableDelEvento(int idEvento) {

        List<RelReponsableProduccionEc> lista;
        List<ReponsableProduccionEcDTO> listaDTO;
        lista = relReponsableProduccionEcRepo.getResponsableDelEvento(idEvento);

        

        Type objetoDTO = new TypeToken<List<ReponsableProduccionEcDTO>>() {
        }.getType();

        for (RelReponsableProduccionEc resProd : lista) {

            resProd.getPersonaResponsabilidad().setRelReponsableProduccionOas(null);
            resProd.getPersonaResponsabilidad().getTblPersona().setPersonaCorreos(null);
            resProd.getPersonaResponsabilidad().getTblPersona().setRelPersonaRoles(null);
            resProd.getPersonaResponsabilidad().getTblPersona().setRelPersonaTelefonos(null);
            resProd.getPersonaResponsabilidad().getTblPersona().setDomiciliosPersonas(null);

        }

        listaDTO = mapper.map(lista, objetoDTO);

        return listaDTO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultadoDTO<ReponsableProduccionEcDTO> guardar(ReponsableProduccionEcDTO dto) {

        ResultadoDTO<ReponsableProduccionEcDTO> resultado
                = sonDatosRequeridosValidos(TipoAccion.PERSISTENCIA, dto);
        if (ObjectUtils.isNotNull(resultado) && resultado.getResultado().getValor()) {
            try {
                resultado = new ResultadoDTO<ReponsableProduccionEcDTO>();
                RelReponsableProduccionEc entidad = new RelReponsableProduccionEc();

                mapper.map(dto, entidad);

                relReponsableProduccionEcRepo.save(entidad);
                resultado.setDto(mapper.map(entidad, ReponsableProduccionEcDTO.class));

        //GUSTAVO --guardarBitacora(dto.getBitacoraDTO(), String.valueOf(entidad.getIdReponsableProduccion()));

                resultado.agregaMensaje(MensajesSistemaEnum.ADMIN_MSG_GUARDADO_EXITOSO.getId());

            } catch (Exception e) {
                e.printStackTrace();
                resultado.setResultado(ResultadoTransaccionEnum.FALLIDO);
                resultado.setMensajeError(MensajesSistemaEnum.ADMIN_MSG_GUARDADO_FALLIDO);
                logger.error("RelRespomnsableProduccionEC:guardar:err :" + e);

            }
        }

        return resultado;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultadoDTO<ReponsableProduccionEcDTO> actualizar(ReponsableProduccionEcDTO dto) {
        ResultadoDTO<ReponsableProduccionEcDTO> resultado
                = sonDatosRequeridosValidos(TipoAccion.ACTUALIZACION, dto);
        if (ObjectUtils.isNotNull(resultado) && resultado.getResultado().getValor()) {
            try {
                resultado = new ResultadoDTO<ReponsableProduccionEcDTO>();
                RelReponsableProduccionEc entidad = new RelReponsableProduccionEc();

                mapper.map(dto, entidad);
                relReponsableProduccionEcRepo.saveAndFlush(entidad);
                resultado.setDto(mapper.map(entidad, ReponsableProduccionEcDTO.class));

        //GUSTAVO --guardarBitacora(dto.getBitacoraDTO(), String.valueOf(entidad.getIdReponsableProduccion()));

                resultado.agregaMensaje(MensajesSistemaEnum.ADMIN_MSG_ACTUALIZACION_EXITOSA.getId());

            } catch (Exception e) {
                e.printStackTrace();
                resultado.setResultado(ResultadoTransaccionEnum.FALLIDO);
                resultado.setMensajeError(MensajesSistemaEnum.ADMIN_MSG_ACTUALIZACION_FALLIDA);
                logger.error(e.getMessage(), e);

            }
        }

        return resultado;
    }

    @Transactional(rollbackFor = Exception.class)
    public ResultadoDTO<ReponsableProduccionEcDTO> eliminar(ReponsableProduccionEcDTO dto) {
        ResultadoDTO<ReponsableProduccionEcDTO> resultado = sonDatosRequeridosValidos(TipoAccion.ELIMINACION, dto);
        if (ObjectUtils.isNotNull(resultado) && resultado.getResultado().getValor()) {
            try {
                resultado = new ResultadoDTO<ReponsableProduccionEcDTO>();

                RelReponsableProduccionEc entidad = mapper.map(dto, RelReponsableProduccionEc.class);
                relReponsableProduccionEcRepo.delete(entidad);
                resultado.setDto(mapper.map(entidad, ReponsableProduccionEcDTO.class));
                resultado.setResultado(ResultadoTransaccionEnum.EXITOSO);

        //GUSTAVO --guardarBitacora(dto.getBitacoraDTO(), String.valueOf(entidad.getIdReponsableProduccion()));
                resultado.agregaMensaje(MensajesSistemaEnum.ADMIN_MSG_ELIMINACION_EXITOSA.getId());

            } catch (Exception e) {
                resultado.setResultado(ResultadoTransaccionEnum.FALLIDO);
                resultado.setMensajeError(MensajesSistemaEnum.ADMIN_MSG_ELIMINACION_FALLIDA);
                logger.error(e.getMessage(), e);
                throw e;
            }
        }

        return resultado;
    }

    @Override
    public ResultadoDTO<ReponsableProduccionEcDTO> sonDatosRequeridosValidos(TipoAccion accion,
            ReponsableProduccionEcDTO dto) {
        ResultadoDTO<ReponsableProduccionEcDTO> res = null;

        if (ObjectUtils.isNotNull(dto)) {
            res = new ResultadoDTO<ReponsableProduccionEcDTO>();

            switch (accion) {
                case PERSISTENCIA:

                    if (ObjectUtils.isNull(dto.getFechaRegistro())) {
                        res.setMensajeError(MensajesErrorEnum.ERROR_FECHA_REGISTRO_REQ);
                    }
                    if (ObjectUtils.isNullOrEmpty(dto.getUsuarioModifico())) {
                        res.setMensajeError(MensajesErrorEnum.ERROR_PERSONA_REQ);
                    }
                    break;

                case ACTUALIZACION:

                    if (ObjectUtils.isNullOrEmpty(dto.getIdEvtCapacitacion())) {
                        res.setMensajeError(MensajesErrorEnum.ERROR_ID_REQ);
                    }
                    if (ObjectUtils.isNullOrEmpty(dto.getIdReponsableProduccion())) {
                        res.setMensajeError(MensajesErrorEnum.ERROR_ID_REQ);
                    }
                    if (ObjectUtils.isNullOrEmpty(dto.getUsuarioModifico())) {
                        res.setMensajeError(MensajesErrorEnum.ERROR_PERSONA_REQ);
                    }
                    if (ObjectUtils.isNull(dto.getFechaActualizacion())) {
                        res.setMensajeError(MensajesErrorEnum.ERROR_FECHA_UPDATE_REQ);
                    }
                    break;

                case ELIMINACION:

                    if (ObjectUtils.isNullOrEmpty(dto.getIdEvtCapacitacion())) {
                        res.setMensajeError(MensajesErrorEnum.ERROR_ID_REQ);
                    }
                    if (ObjectUtils.isNullOrEmpty(dto.getIdReponsableProduccion())) {
                        res.setMensajeError(MensajesErrorEnum.ERROR_ID_REQ);
                    }
                    break;
            }
        }
        return res;
    }

    @Override
    public void validarPersistencia(ReponsableProduccionEcDTO dto, ResultadoDTO<ReponsableProduccionEcDTO> resultado) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void validarActualizacion(ReponsableProduccionEcDTO dto, ResultadoDTO<ReponsableProduccionEcDTO> resultado) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void validarEliminacion(ReponsableProduccionEcDTO dto, ResultadoDTO<ReponsableProduccionEcDTO> resultado) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
