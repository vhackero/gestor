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
import mx.gob.sedesol.basegestor.commons.dto.gestion.aprendizaje.RelRecursoPredominanteFoaDTO;
import mx.gob.sedesol.basegestor.commons.utils.MensajesErrorEnum;
import mx.gob.sedesol.basegestor.commons.utils.MensajesSistemaEnum;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.ResultadoTransaccionEnum;
import mx.gob.sedesol.basegestor.commons.utils.TipoAccion;
import mx.gob.sedesol.basegestor.model.entities.gestionaprendizaje.RelRecursoPredominanteFoa;
import mx.gob.sedesol.basegestor.model.repositories.gestion.aprendizaje.RelRecursoPredominanteFoaRepo;
import mx.gob.sedesol.basegestor.service.admin.ComunValidacionService;
import mx.gob.sedesol.basegestor.service.gestion.aprendizaje.RelRecursoPredominanteFoaService;

@Service("relRecursoPredominanteFoaService")
public class RelRecursoPredominanteFoaServiceImpl extends ComunValidacionService<RelRecursoPredominanteFoaDTO> implements RelRecursoPredominanteFoaService {

    private static final Logger logger = Logger.getLogger(RelRecursoPredominanteFoaServiceImpl.class);

    @Autowired
    private RelRecursoPredominanteFoaRepo relRecursoPredominanteFoaRepo;

    ModelMapper relRecursoPredominanteFoaMapper = new ModelMapper();

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultadoDTO<RelRecursoPredominanteFoaDTO> guardar(RelRecursoPredominanteFoaDTO dto) {
        ResultadoDTO<RelRecursoPredominanteFoaDTO> resultado = sonDatosRequeridosValidos(TipoAccion.PERSISTENCIA, dto);
        if (ObjectUtils.isNotNull(resultado) && resultado.getResultado().getValor()) {
            try {
                RelRecursoPredominanteFoa entidad = new RelRecursoPredominanteFoa();
                relRecursoPredominanteFoaMapper.map(dto, entidad);
                relRecursoPredominanteFoaRepo.save(entidad);
        //GUSTAVO --guardarBitacora(dto.getBitacoraDTO(), String.valueOf(entidad.getIdFoa()));
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
    public ResultadoDTO<RelRecursoPredominanteFoaDTO> eliminar(RelRecursoPredominanteFoaDTO dto) {
        ResultadoDTO<RelRecursoPredominanteFoaDTO> resultado = sonDatosRequeridosValidos(TipoAccion.ELIMINACION, dto);
        if (ObjectUtils.isNotNull(resultado) && resultado.getResultado().getValor()) {
            try {
                RelRecursoPredominanteFoa entidad = new RelRecursoPredominanteFoa();
                relRecursoPredominanteFoaMapper.map(dto, entidad);
                relRecursoPredominanteFoaRepo.delete(entidad);
        //GUSTAVO --guardarBitacora(dto.getBitacoraDTO(), String.valueOf(entidad.getIdFoa()));
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
    public List<RelRecursoPredominanteFoaDTO> buscarRecursoPredominanteFoa(Integer clFoa) {
        List<RelRecursoPredominanteFoa> recursoPredominante = relRecursoPredominanteFoaRepo.findAll();
        Type recursoPrediminanteDTO = new TypeToken<List<RelRecursoPredominanteFoaDTO>>() {

        }.getType();
        return relRecursoPredominanteFoaMapper.map(recursoPredominante, recursoPrediminanteDTO);
    }

    @Override
    public ResultadoDTO<RelRecursoPredominanteFoaDTO> sonDatosRequeridosValidos(TipoAccion accion,
            RelRecursoPredominanteFoaDTO dto) {
        ResultadoDTO<RelRecursoPredominanteFoaDTO> resultado = null;

        if (ObjectUtils.isNotNull(dto)) {
            resultado = new ResultadoDTO<RelRecursoPredominanteFoaDTO>();

            switch (accion) {
                case PERSISTENCIA:

                    if (ObjectUtils.isNull(dto.getIdFoa())) {
                        resultado.setMensajeError(MensajesSistemaEnum.MSG_GENERAL_CLAVE_REQ);
                    }

                    if (ObjectUtils.isNull(dto.getIdCatRecursoPredominante())) {
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

                    if (ObjectUtils.isNull(dto.getIdCatRecursoPredominante())) {
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
    public List<RelRecursoPredominanteFoaDTO> findAll() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public RelRecursoPredominanteFoaDTO buscarPorId(Integer id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ResultadoDTO<RelRecursoPredominanteFoaDTO> actualizar(RelRecursoPredominanteFoaDTO dto) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @return the relRecursoPredominanteFoaRepo
     */
    public RelRecursoPredominanteFoaRepo getRelRecursoPredominanteFoaRepo() {
        return relRecursoPredominanteFoaRepo;
    }

    /**
     * @param relRecursoPredominanteFoaRepo the relRecursoPredominanteFoaRepo to
     * set
     */
    public void setRelRecursoPredominanteFoaRepo(RelRecursoPredominanteFoaRepo relRecursoPredominanteFoaRepo) {
        this.relRecursoPredominanteFoaRepo = relRecursoPredominanteFoaRepo;
    }

    @Override
    public void validarPersistencia(RelRecursoPredominanteFoaDTO dto, ResultadoDTO<RelRecursoPredominanteFoaDTO> resultado) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void validarActualizacion(RelRecursoPredominanteFoaDTO dto, ResultadoDTO<RelRecursoPredominanteFoaDTO> resultado) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void validarEliminacion(RelRecursoPredominanteFoaDTO dto, ResultadoDTO<RelRecursoPredominanteFoaDTO> resultado) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
