/**
 *
 */
package mx.gob.sedesol.basegestor.service.impl.encuestas;

import java.lang.reflect.Type;
import java.util.List;

import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.encuestas.PreguntaEncuestaDTO;
import mx.gob.sedesol.basegestor.commons.utils.MensajesErrorEnum;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.ResultadoTransaccionEnum;
import mx.gob.sedesol.basegestor.commons.utils.TipoAccion;
import mx.gob.sedesol.basegestor.model.entities.encuestas.TblPreguntasEncuesta;
import mx.gob.sedesol.basegestor.model.repositories.encuestas.PreguntaEncuestaRepo;
import mx.gob.sedesol.basegestor.service.admin.ComunValidacionService;
import mx.gob.sedesol.basegestor.service.encuestas.PreguntaEncuestaService;

/**
 * @author jhcortes
 *
 */
@Service("preguntaEncuestaService")
public class PreguntaEncuestaServiceImpl extends ComunValidacionService<PreguntaEncuestaDTO> implements PreguntaEncuestaService {

    private static final Logger logger = Logger.getLogger(EncuestaServiceImpl.class);

    @Autowired
    private PreguntaEncuestaRepo preguntaEncuestaRepo;

    private ModelMapper catComunMapper = new ModelMapper();

    /*
	 * (non-Javadoc)
	 * 
	 * @see mx.gob.sedesol.basegestor.service.admin.CommonService#findAll()
     */
    @Override
    public List<PreguntaEncuestaDTO> findAll() {
        List<TblPreguntasEncuesta> preguntas = preguntaEncuestaRepo.findAll();
        Type preguntasDto = new TypeToken<List<PreguntaEncuestaDTO>>() {
        }.getType();
        return catComunMapper.map(preguntas, preguntasDto);
    }

    @Override
    public PreguntaEncuestaDTO buscarPorId(Long id) {
        TblPreguntasEncuesta entidad = preguntaEncuestaRepo.findOne(id);
        return catComunMapper.map(entidad, PreguntaEncuestaDTO.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultadoDTO<PreguntaEncuestaDTO> guardar(PreguntaEncuestaDTO dto) {
        ResultadoDTO<PreguntaEncuestaDTO> resultado = sonDatosRequeridosValidos(TipoAccion.PERSISTENCIA, dto);
        if (ObjectUtils.isNotNull(resultado)) {
            try {
                resultado = new ResultadoDTO<>();
                TblPreguntasEncuesta entidad = catComunMapper.map(dto, TblPreguntasEncuesta.class);
                entidad = preguntaEncuestaRepo.save(entidad);
                resultado.setDto(catComunMapper.map(entidad, PreguntaEncuestaDTO.class));
        //GUSTAVO --guardarBitacora(dto.getBitacoraDTO(), String.valueOf(entidad.getId()));
            } catch (Exception e) {
                resultado.setResultado(ResultadoTransaccionEnum.FALLIDO);
                resultado.setMensajeError(MensajesErrorEnum.ERROR_PERSISTENCIA_DATOS);
                logger.error(e.getMessage(), e);
                throw e;
            }
        }

        return resultado;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultadoDTO<PreguntaEncuestaDTO> actualizar(PreguntaEncuestaDTO dto) {
        ResultadoDTO<PreguntaEncuestaDTO> resultado = sonDatosRequeridosValidos(TipoAccion.ACTUALIZACION, dto);
        if (ObjectUtils.isNotNull(resultado)) {
            try {
                resultado = new ResultadoDTO<>();
                TblPreguntasEncuesta entidad = catComunMapper.map(dto, TblPreguntasEncuesta.class);
                entidad = preguntaEncuestaRepo.saveAndFlush(entidad);
                resultado.setDto(catComunMapper.map(entidad, PreguntaEncuestaDTO.class));
        //GUSTAVO --guardarBitacora(dto.getBitacoraDTO(), String.valueOf(entidad.getId()));
            } catch (Exception e) {
                resultado.setResultado(ResultadoTransaccionEnum.FALLIDO);
                resultado.setMensajeError(MensajesErrorEnum.ERROR_ACTUALIZACION_DATOS);
                logger.error(e.getMessage(), e);
                throw e;
            }
        }
        return resultado;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultadoDTO<PreguntaEncuestaDTO> eliminar(PreguntaEncuestaDTO dto) {

        ResultadoDTO<PreguntaEncuestaDTO> resultado = new ResultadoDTO<>();
        try {
            TblPreguntasEncuesta entidad = catComunMapper.map(dto, TblPreguntasEncuesta.class);

            preguntaEncuestaRepo.delete(entidad);

            resultado.setResultado(ResultadoTransaccionEnum.EXITOSO);
    //GUSTAVO --guardarBitacora(dto.getBitacoraDTO(), String.valueOf(entidad.getId()));
        } catch (Exception e) {

            resultado.setResultado(ResultadoTransaccionEnum.FALLIDO);
            resultado.setMensajeError(MensajesErrorEnum.ERROR_ELIMINAR_DATOS);
            logger.error(e.getMessage(), e);
            throw e;
        }
        return resultado;
    }

    @Override
    public ResultadoDTO<PreguntaEncuestaDTO> sonDatosRequeridosValidos(TipoAccion accion, PreguntaEncuestaDTO dto) {
        ResultadoDTO<PreguntaEncuestaDTO> res = new ResultadoDTO<>();

        if (ObjectUtils.isNotNull(dto)) {
            switch (accion) {
                case PERSISTENCIA:
                    res = validaPersistencia(dto);
                    break;
                case ACTUALIZACION:
                    res = validaActualizacion(dto);
                    break;
                case ELIMINACION:
                    res = validaEliminacion(dto);
                    break;
            }
        }

        return res;
    }

    /**
     *
     * @param dto
     * @return
     */
    private ResultadoDTO<PreguntaEncuestaDTO> validaEliminacion(PreguntaEncuestaDTO dto) {
        ResultadoDTO<PreguntaEncuestaDTO> res = new ResultadoDTO<>();
        if (ObjectUtils.isNullOrEmpty(dto.getId())) {
            res.setMensajeError(MensajesErrorEnum.ERROR_ID_REQ);
        }
        if (ObjectUtils.isNull(dto.getFechaActualizacion())) {
            res.setMensajeError(MensajesErrorEnum.ERROR_FECHA_UPDATE_REQ);
        }
        return res;
    }

    /**
     *
     * @param dto
     * @return
     */
    private ResultadoDTO<PreguntaEncuestaDTO> validaActualizacion(PreguntaEncuestaDTO dto) {
        ResultadoDTO<PreguntaEncuestaDTO> res = new ResultadoDTO<>();
        if (ObjectUtils.isNullOrEmpty(dto.getId())) {
            res.setMensajeError(MensajesErrorEnum.ERROR_ID_REQ);
        }
        if (ObjectUtils.isNullOrEmpty(dto.getUsuarioModifico())) {
            res.setMensajeError(MensajesErrorEnum.ERROR_USUARIO_REQ);
        }
        if (ObjectUtils.isNull(dto.getFechaActualizacion())) {
            res.setMensajeError(MensajesErrorEnum.ERROR_FECHA_UPDATE_REQ);
        }
        return res;
    }

    /**
     *
     * @param dto
     * @return
     */
    private ResultadoDTO<PreguntaEncuestaDTO> validaPersistencia(PreguntaEncuestaDTO dto) {
        ResultadoDTO<PreguntaEncuestaDTO> res = new ResultadoDTO<>();
        if (ObjectUtils.isNullOrEmpty(dto.getNombre())) {
            res.setMensajeError(MensajesErrorEnum.ERROR_NOMBRE_REQ);
        }
        if (ObjectUtils.isNull(dto.getFechaRegistro())) {
            res.setMensajeError(MensajesErrorEnum.ERROR_FECHA_REGISTRO_REQ);
        }
        if (ObjectUtils.isNullOrEmpty(dto.getUsuarioModifico())) {
            res.setMensajeError(MensajesErrorEnum.ERROR_USUARIO_REQ);
        }
        return res;
    }

    @Override
    public List<TblPreguntasEncuesta> buscarPorIdEncuesta(Long idEncuesta) {
        return null;
    }

    @Override
    public void validarPersistencia(PreguntaEncuestaDTO dto, ResultadoDTO<PreguntaEncuestaDTO> resultado) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void validarActualizacion(PreguntaEncuestaDTO dto, ResultadoDTO<PreguntaEncuestaDTO> resultado) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void validarEliminacion(PreguntaEncuestaDTO dto, ResultadoDTO<PreguntaEncuestaDTO> resultado) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
