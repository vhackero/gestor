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
import mx.gob.sedesol.basegestor.commons.dto.gestion.aprendizaje.ElementosMultimediaFoaDTO;
import mx.gob.sedesol.basegestor.commons.utils.MensajesErrorEnum;
import mx.gob.sedesol.basegestor.commons.utils.MensajesSistemaEnum;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.ResultadoTransaccionEnum;
import mx.gob.sedesol.basegestor.commons.utils.TipoAccion;
import mx.gob.sedesol.basegestor.model.entities.gestionaprendizaje.RelElementosMultimediaFoa;
import mx.gob.sedesol.basegestor.model.repositories.gestion.aprendizaje.RelElementosMultimediaFoaRepo;
import mx.gob.sedesol.basegestor.service.admin.ComunValidacionService;
import mx.gob.sedesol.basegestor.service.gestion.aprendizaje.RelElementosMultimediaFoaService;

@Service("relElementosMultimediaFoaService")

public class RelElementosMultimediaFoaServiceImpl extends ComunValidacionService<ElementosMultimediaFoaDTO> implements RelElementosMultimediaFoaService {

    private static final Logger logger = Logger.getLogger(RelElementosMultimediaFoaServiceImpl.class);

    @Autowired
    private RelElementosMultimediaFoaRepo relElementosMultimediaFoaRepo;

    private ModelMapper relElementosMultimediaFoaMapper = new ModelMapper();

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultadoDTO<ElementosMultimediaFoaDTO> guardar(ElementosMultimediaFoaDTO dto) {

        ResultadoDTO<ElementosMultimediaFoaDTO> resultado = sonDatosRequeridosValidos(TipoAccion.PERSISTENCIA, dto);
        if (ObjectUtils.isNotNull(resultado) && resultado.getResultado().getValor()) {
            try {
                RelElementosMultimediaFoa entidad = new RelElementosMultimediaFoa();
                relElementosMultimediaFoaMapper.map(dto, entidad);
                relElementosMultimediaFoaRepo.save(entidad);
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
    public ResultadoDTO<ElementosMultimediaFoaDTO> eliminar(ElementosMultimediaFoaDTO dto) {
        ResultadoDTO<ElementosMultimediaFoaDTO> resultado = sonDatosRequeridosValidos(TipoAccion.ELIMINACION, dto);
        if (ObjectUtils.isNotNull(resultado) && resultado.getResultado().getValor()) {
            try {
                RelElementosMultimediaFoa entidad = new RelElementosMultimediaFoa();
                relElementosMultimediaFoaMapper.map(dto, entidad);
                relElementosMultimediaFoaRepo.delete(entidad);
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
    public ResultadoDTO<ElementosMultimediaFoaDTO> sonDatosRequeridosValidos(TipoAccion accion, ElementosMultimediaFoaDTO dto) {

        ResultadoDTO<ElementosMultimediaFoaDTO> resultado = null;

        if (ObjectUtils.isNotNull(dto)) {
            resultado = new ResultadoDTO<ElementosMultimediaFoaDTO>();

            switch (accion) {
                case PERSISTENCIA:

                    if (ObjectUtils.isNull(dto.getIdFoa())) {
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
                    if (ObjectUtils.isNull(dto.getIdElementoMultimediaFoa())) {
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
    public List<ElementosMultimediaFoaDTO> findAll() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<ElementosMultimediaFoaDTO> buscarElementoMultimediaFoa(Integer clFoa) {
        List<RelElementosMultimediaFoa> elementosMultimedia = relElementosMultimediaFoaRepo.findAll();
        Type elementoMultimediaDTO = new TypeToken<List<ElementosMultimediaFoaDTO>>() {

        }.getType();
        return relElementosMultimediaFoaMapper.map(elementosMultimedia, elementoMultimediaDTO);
    }

    @Override
    public ElementosMultimediaFoaDTO buscarPorId(Integer id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ResultadoDTO<ElementosMultimediaFoaDTO> actualizar(ElementosMultimediaFoaDTO dto) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @return the relElementosMultimediaFoaRepo
     */
    public RelElementosMultimediaFoaRepo getRelElementosMultimediaFoaRepo() {
        return relElementosMultimediaFoaRepo;
    }

    /**
     * @param relElementosMultimediaFoaRepo the relElementosMultimediaFoaRepo to
     * set
     */
    public void setRelElementosMultimediaFoaRepo(RelElementosMultimediaFoaRepo relElementosMultimediaFoaRepo) {
        this.relElementosMultimediaFoaRepo = relElementosMultimediaFoaRepo;
    }

    @Override
    public void validarPersistencia(ElementosMultimediaFoaDTO dto, ResultadoDTO<ElementosMultimediaFoaDTO> resultado) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void validarActualizacion(ElementosMultimediaFoaDTO dto, ResultadoDTO<ElementosMultimediaFoaDTO> resultado) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void validarEliminacion(ElementosMultimediaFoaDTO dto, ResultadoDTO<ElementosMultimediaFoaDTO> resultado) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
