package mx.gob.sedesol.basegestor.service.impl.gestion.aprendizaje;

import java.lang.reflect.Type;
import java.util.List;

import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestion.aprendizaje.RelRelacionOtrosObjetosFoaDTO;
import mx.gob.sedesol.basegestor.commons.utils.MensajesErrorEnum;
import mx.gob.sedesol.basegestor.commons.utils.MensajesSistemaEnum;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.ResultadoTransaccionEnum;
import mx.gob.sedesol.basegestor.commons.utils.TipoAccion;
import mx.gob.sedesol.basegestor.model.entities.gestionaprendizaje.RelCatRelacionOtrosObjetosFoa;
import mx.gob.sedesol.basegestor.model.repositories.gestion.aprendizaje.RelRelacionOtrosObjetosFoaRepo;
import mx.gob.sedesol.basegestor.service.admin.ComunValidacionService;
import mx.gob.sedesol.basegestor.service.gestion.aprendizaje.RelRelacionOtrosObjetosService;

@Service("relRelacionOtrosObjetosFoaService")
public class RelRelacionOtrosObjetosFoaServiceImpl extends ComunValidacionService<RelRelacionOtrosObjetosFoaDTO> implements RelRelacionOtrosObjetosService {

    private static final Logger logger = Logger.getLogger(RelElementosMultimediaFoaServiceImpl.class);

    @Autowired
    private RelRelacionOtrosObjetosFoaRepo relRelacionOtrosObjetosFoaRepo;

    ModelMapper relRelacionOtrosObjetosFoaMapper = new ModelMapper();

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultadoDTO<RelRelacionOtrosObjetosFoaDTO> guardar(RelRelacionOtrosObjetosFoaDTO dto) {
        ResultadoDTO<RelRelacionOtrosObjetosFoaDTO> resultado = sonDatosRequeridosValidos(TipoAccion.PERSISTENCIA, dto);
        if (ObjectUtils.isNotNull(resultado) && resultado.getResultado().getValor()) {
            try {
                RelCatRelacionOtrosObjetosFoa entidad = new RelCatRelacionOtrosObjetosFoa();
                relRelacionOtrosObjetosFoaMapper.map(dto, entidad);
                relRelacionOtrosObjetosFoaRepo.save(entidad);
        //GUSTAVO --guardarBitacora(dto.getBitacoraDTO(), String.valueOf(entidad.getIdCatRelacionOtrosObjetos()));
                resultado.agregaMensaje(MensajesSistemaEnum.ADMIN_MSG_GUARDADO_EXITOSO.getId());
            } catch (Exception e) {
                resultado.setResultado(ResultadoTransaccionEnum.FALLIDO);
                resultado.setMensajeError(MensajesSistemaEnum.ADMIN_MSG_GUARDADO_FALLIDO);
                logger.error(e.getMessage(), e);
                throw e;
            }
        }
        return resultado;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultadoDTO<RelRelacionOtrosObjetosFoaDTO> eliminar(RelRelacionOtrosObjetosFoaDTO dto) {
        ResultadoDTO<RelRelacionOtrosObjetosFoaDTO> resultado = sonDatosRequeridosValidos(TipoAccion.ELIMINACION, dto);
        if (ObjectUtils.isNotNull(resultado) && resultado.getResultado().getValor()) {
            try {
                RelCatRelacionOtrosObjetosFoa entidad = new RelCatRelacionOtrosObjetosFoa();
                relRelacionOtrosObjetosFoaMapper.map(dto, entidad);
                relRelacionOtrosObjetosFoaRepo.delete(entidad);
        //GUSTAVO --guardarBitacora(dto.getBitacoraDTO(), String.valueOf(entidad.getIdCatRelacionOtrosObjetos()));
                resultado.agregaMensaje(MensajesSistemaEnum.ADMIN_MSG_ELIMINACION_EXITOSA.getId());
            } catch (Exception e) {
                resultado.setResultado(ResultadoTransaccionEnum.FALLIDO);
                resultado.setMensajeError(MensajesErrorEnum.ERROR_ELIMINAR_DATOS);
                throw e;
            }
        }
        return resultado;
    }

    @Override
    public List<RelRelacionOtrosObjetosFoaDTO> buscarRelacionOtrosObjetosFoa(Integer clFoa) {
        List<RelCatRelacionOtrosObjetosFoa> relacionOtroObjeto = relRelacionOtrosObjetosFoaRepo.findAll();
        Type relacionOtroObjetoDTO = new TypeToken<List<RelRelacionOtrosObjetosFoaDTO>>() {

        }.getType();
        return relRelacionOtrosObjetosFoaMapper.map(relacionOtroObjeto, relacionOtroObjetoDTO);
    }

    @Override
    public ResultadoDTO<RelRelacionOtrosObjetosFoaDTO> sonDatosRequeridosValidos(TipoAccion accion,
            RelRelacionOtrosObjetosFoaDTO dto) {
        ResultadoDTO<RelRelacionOtrosObjetosFoaDTO> resultado = null;

        if (ObjectUtils.isNotNull(dto)) {
            resultado = new ResultadoDTO<RelRelacionOtrosObjetosFoaDTO>();

            switch (accion) {
                case PERSISTENCIA:

                    if (ObjectUtils.isNull(dto.getIdFoa())) {
                        resultado.setMensajeError(MensajesSistemaEnum.MSG_GENERAL_CLAVE_REQ);
                    }

                    if (ObjectUtils.isNull(dto.getIdCatRelacionOtrosObjetos())) {
                        resultado.setMensajeError(MensajesSistemaEnum.MSG_GENERAL_CLAVE_REQ);
                    }

                    if (ObjectUtils.isNull(dto.getFechaRegistro())) {
                        resultado.setMensajeError(MensajesSistemaEnum.MSG_GENERAL_CLAVE_REQ);
                    }

                    if (ObjectUtils.isNull(dto.getUsuarioModifico())) {
                        resultado.setMensajeError(MensajesSistemaEnum.MSG_GENERAL_CLAVE_REQ);
                    }

                    break;

                case ELIMINACION:
                    if (ObjectUtils.isNull(dto.getIdFoa())) {
                        resultado.setMensajeError(MensajesSistemaEnum.MSG_GENERAL_ID_REQ);
                    }

                    if (ObjectUtils.isNull(dto.getIdCatRelacionOtrosObjetos())) {
                        resultado.setMensajeError(MensajesSistemaEnum.MSG_GENERAL_ID_REQ);
                    }
                    break;
                default:
                    break;
            }
        }
        return resultado;
    }

    @Override
    public List<RelRelacionOtrosObjetosFoaDTO> findAll() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public RelRelacionOtrosObjetosFoaDTO buscarPorId(Integer id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ResultadoDTO<RelRelacionOtrosObjetosFoaDTO> actualizar(RelRelacionOtrosObjetosFoaDTO dto) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @return the relRelacionOtrosObjetosFoaRepo
     */
    public RelRelacionOtrosObjetosFoaRepo getRelRelacionOtrosObjetosFoaRepo() {
        return relRelacionOtrosObjetosFoaRepo;
    }

    /**
     * @param relRelacionOtrosObjetosFoaRepo the relRelacionOtrosObjetosFoaRepo
     * to set
     */
    public void setRelRelacionOtrosObjetosFoaRepo(RelRelacionOtrosObjetosFoaRepo relRelacionOtrosObjetosFoaRepo) {
        this.relRelacionOtrosObjetosFoaRepo = relRelacionOtrosObjetosFoaRepo;
    }

    @Override
    public void validarPersistencia(RelRelacionOtrosObjetosFoaDTO dto, ResultadoDTO<RelRelacionOtrosObjetosFoaDTO> resultado) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void validarActualizacion(RelRelacionOtrosObjetosFoaDTO dto, ResultadoDTO<RelRelacionOtrosObjetosFoaDTO> resultado) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void validarEliminacion(RelRelacionOtrosObjetosFoaDTO dto, ResultadoDTO<RelRelacionOtrosObjetosFoaDTO> resultado) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
