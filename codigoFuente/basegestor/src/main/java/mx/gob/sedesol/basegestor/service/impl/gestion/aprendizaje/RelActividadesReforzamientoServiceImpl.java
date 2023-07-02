package mx.gob.sedesol.basegestor.service.impl.gestion.aprendizaje;

import java.lang.reflect.Type;
import java.util.List;

import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.gob.sedesol.basegestor.commons.constantes.ConstantesBitacora;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestion.aprendizaje.RelActividadesReforzamientoFoaDTO;
import mx.gob.sedesol.basegestor.commons.utils.MensajesErrorEnum;
import mx.gob.sedesol.basegestor.commons.utils.MensajesSistemaEnum;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.ResultadoTransaccionEnum;
import mx.gob.sedesol.basegestor.commons.utils.TipoAccion;
import mx.gob.sedesol.basegestor.model.entities.gestionaprendizaje.RelFoaActividadReforzamiento;
import mx.gob.sedesol.basegestor.model.repositories.gestion.aprendizaje.RelActividadesReforzamientoFoaRepo;
import mx.gob.sedesol.basegestor.service.admin.ComunValidacionService;
import mx.gob.sedesol.basegestor.service.gestion.aprendizaje.RelActividadesReforzamientoFoaService;

@Service("relActividadesReforzamientoService")
public class RelActividadesReforzamientoServiceImpl extends ComunValidacionService<RelActividadesReforzamientoFoaDTO> implements RelActividadesReforzamientoFoaService {

    private static final Logger logger = Logger.getLogger(RelActividadesReforzamientoServiceImpl.class);

    @Autowired
    private RelActividadesReforzamientoFoaRepo relActividadesReforzamientoFoaRepo;

    private ModelMapper relActividadesReforzamientoFoaMapper = new ModelMapper();

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultadoDTO<RelActividadesReforzamientoFoaDTO> guardar(RelActividadesReforzamientoFoaDTO dto) {
        ResultadoDTO<RelActividadesReforzamientoFoaDTO> resultado = sonDatosRequeridosValidos(TipoAccion.PERSISTENCIA, dto);
        if (ObjectUtils.isNotNull(resultado) && resultado.getResultado().getValor()) {
            try {
                RelFoaActividadReforzamiento entidad = new RelFoaActividadReforzamiento();
                relActividadesReforzamientoFoaMapper.map(dto, entidad);
                relActividadesReforzamientoFoaRepo.save(entidad);
        //GUSTAVO --guardarBitacora(dto.getBitacoraDTO(), String.valueOf(entidad.getIdActividadReforzamiento()));
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
    public ResultadoDTO<RelActividadesReforzamientoFoaDTO> eliminar(RelActividadesReforzamientoFoaDTO dto) {
        ResultadoDTO<RelActividadesReforzamientoFoaDTO> resultado = sonDatosRequeridosValidos(TipoAccion.ELIMINACION, dto);
        if (ObjectUtils.isNotNull(resultado) && resultado.getResultado().getValor()) {
            try {
                RelFoaActividadReforzamiento entidad = new RelFoaActividadReforzamiento();
                relActividadesReforzamientoFoaMapper.map(dto, entidad);
                relActividadesReforzamientoFoaRepo.delete(entidad);
        //GUSTAVO --guardarBitacora(dto.getBitacoraDTO(), String.valueOf(entidad.getIdActividadReforzamiento()));
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
    public List<RelActividadesReforzamientoFoaDTO> buscarActividadReforzamientoFoa(Integer clFoa) {
        List<RelFoaActividadReforzamiento> actividadReforzamiento = relActividadesReforzamientoFoaRepo.findAll();
        Type actividadReforzamientoDTO = new TypeToken<List<RelActividadesReforzamientoFoaDTO>>() {

        }.getType();
        return relActividadesReforzamientoFoaMapper.map(actividadReforzamiento, actividadReforzamientoDTO);
    }

    @Override
    public ResultadoDTO<RelActividadesReforzamientoFoaDTO> sonDatosRequeridosValidos(TipoAccion accion,
            RelActividadesReforzamientoFoaDTO dto) {
        ResultadoDTO<RelActividadesReforzamientoFoaDTO> resultado = null;

        if (ObjectUtils.isNotNull(dto)) {
            resultado = new ResultadoDTO<RelActividadesReforzamientoFoaDTO>();

            switch (accion) {
                case PERSISTENCIA:

                    if (ObjectUtils.isNull(dto.getIdFoa())) {
                        resultado.setMensajeError(MensajesSistemaEnum.MSG_GENERAL_CLAVE_REQ);
                    }

                    if (ObjectUtils.isNull(dto.getIdActividadReforzamiento())) {
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

                    if (ObjectUtils.isNull(dto.getIdActividadReforzamiento())) {
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
    public List<RelActividadesReforzamientoFoaDTO> findAll() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public RelActividadesReforzamientoFoaDTO buscarPorId(Integer id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ResultadoDTO<RelActividadesReforzamientoFoaDTO> actualizar(RelActividadesReforzamientoFoaDTO dto) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @return the relActividadesReforzamientoFoaRepo
     */
    public RelActividadesReforzamientoFoaRepo getRelActividadesReforzamientoFoaRepo() {
        return relActividadesReforzamientoFoaRepo;
    }

    /**
     * @param relActividadesReforzamientoFoaRepo the
     * relActividadesReforzamientoFoaRepo to set
     */
    public void setRelActividadesReforzamientoFoaRepo(RelActividadesReforzamientoFoaRepo relActividadesReforzamientoFoaRepo) {
        this.relActividadesReforzamientoFoaRepo = relActividadesReforzamientoFoaRepo;
    }

    @Override
    public void validarPersistencia(RelActividadesReforzamientoFoaDTO dto, ResultadoDTO<RelActividadesReforzamientoFoaDTO> resultado) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void validarActualizacion(RelActividadesReforzamientoFoaDTO dto, ResultadoDTO<RelActividadesReforzamientoFoaDTO> resultado) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void validarEliminacion(RelActividadesReforzamientoFoaDTO dto, ResultadoDTO<RelActividadesReforzamientoFoaDTO> resultado) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
