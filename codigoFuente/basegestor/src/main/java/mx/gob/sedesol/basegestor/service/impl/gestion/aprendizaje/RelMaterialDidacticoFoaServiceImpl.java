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
import mx.gob.sedesol.basegestor.commons.dto.gestion.aprendizaje.RelMaterialDidacticoFoaDTO;
import mx.gob.sedesol.basegestor.commons.utils.MensajesErrorEnum;
import mx.gob.sedesol.basegestor.commons.utils.MensajesSistemaEnum;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.ResultadoTransaccionEnum;
import mx.gob.sedesol.basegestor.commons.utils.TipoAccion;
import mx.gob.sedesol.basegestor.model.entities.gestionaprendizaje.RelMaterialDidacticoFoa;
import mx.gob.sedesol.basegestor.model.repositories.gestion.aprendizaje.RelMaterialDidacticoFoaRepo;
import mx.gob.sedesol.basegestor.service.admin.ComunValidacionService;
import mx.gob.sedesol.basegestor.service.gestion.aprendizaje.RelMaterialDidacticoFoaService;

@Service("relMaterialDidacticoService")
public class RelMaterialDidacticoFoaServiceImpl extends ComunValidacionService<RelMaterialDidacticoFoaDTO> implements RelMaterialDidacticoFoaService {

    private static final Logger logger = Logger.getLogger(RelElementosMultimediaFoaServiceImpl.class);

    @Autowired
    private RelMaterialDidacticoFoaRepo relMaterialDidacticoFoaRepo;

    ModelMapper relMaterialDidacticoFoaMapper = new ModelMapper();

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultadoDTO<RelMaterialDidacticoFoaDTO> guardar(RelMaterialDidacticoFoaDTO dto) {
        ResultadoDTO<RelMaterialDidacticoFoaDTO> resultado = sonDatosRequeridosValidos(TipoAccion.PERSISTENCIA, dto);
        if (ObjectUtils.isNotNull(resultado) && resultado.getResultado().getValor()) {
            try {
                RelMaterialDidacticoFoa entidad = new RelMaterialDidacticoFoa();
                relMaterialDidacticoFoaMapper.map(dto, entidad);
                relMaterialDidacticoFoaRepo.save(entidad);
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
    public ResultadoDTO<RelMaterialDidacticoFoaDTO> eliminar(RelMaterialDidacticoFoaDTO dto) {
        ResultadoDTO<RelMaterialDidacticoFoaDTO> resultado = sonDatosRequeridosValidos(TipoAccion.ELIMINACION, dto);
        if (ObjectUtils.isNotNull(resultado) && resultado.getResultado().getValor()) {
            try {
                RelMaterialDidacticoFoa entidad = new RelMaterialDidacticoFoa();
                relMaterialDidacticoFoaMapper.map(dto, entidad);
                relMaterialDidacticoFoaRepo.delete(entidad);
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
    public List<RelMaterialDidacticoFoaDTO> buscarMaterialDidacticoFoa(Integer clFoa) {
        List<RelMaterialDidacticoFoa> materialDidactico = relMaterialDidacticoFoaRepo.findAll();
        Type materialDidacticoDTO = new TypeToken<List<RelMaterialDidacticoFoaDTO>>() {

        }.getType();
        return relMaterialDidacticoFoaMapper.map(materialDidactico, materialDidacticoDTO);
    }

    @Override
    public ResultadoDTO<RelMaterialDidacticoFoaDTO> sonDatosRequeridosValidos(TipoAccion accion,
            RelMaterialDidacticoFoaDTO dto) {
        ResultadoDTO<RelMaterialDidacticoFoaDTO> resultado = null;

        if (ObjectUtils.isNotNull(dto)) {
            resultado = new ResultadoDTO<RelMaterialDidacticoFoaDTO>();

            switch (accion) {
                case PERSISTENCIA:

                    if (ObjectUtils.isNull(dto.getIdFoa())) {
                        resultado.setMensajeError(MensajesSistemaEnum.MSG_GENERAL_CLAVE_REQ);
                    }

                    if (ObjectUtils.isNull(dto.getIdCatRecursoDidactico())) {
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

                    if (ObjectUtils.isNull(dto.getIdCatRecursoDidactico())) {
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
    public ResultadoDTO<RelMaterialDidacticoFoaDTO> actualizar(RelMaterialDidacticoFoaDTO dto) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<RelMaterialDidacticoFoaDTO> findAll() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public RelMaterialDidacticoFoaDTO buscarPorId(Integer id) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @return the relMaterialDidacticoFoaRepo
     */
    public RelMaterialDidacticoFoaRepo getRelMaterialDidacticoFoaRepo() {
        return relMaterialDidacticoFoaRepo;
    }

    /**
     * @param relMaterialDidacticoFoaRepo the relMaterialDidacticoFoaRepo to set
     */
    public void setRelMaterialDidacticoFoaRepo(RelMaterialDidacticoFoaRepo relMaterialDidacticoFoaRepo) {
        this.relMaterialDidacticoFoaRepo = relMaterialDidacticoFoaRepo;
    }

    @Override
    public void validarPersistencia(RelMaterialDidacticoFoaDTO dto, ResultadoDTO<RelMaterialDidacticoFoaDTO> resultado) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void validarActualizacion(RelMaterialDidacticoFoaDTO dto, ResultadoDTO<RelMaterialDidacticoFoaDTO> resultado) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void validarEliminacion(RelMaterialDidacticoFoaDTO dto, ResultadoDTO<RelMaterialDidacticoFoaDTO> resultado) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
