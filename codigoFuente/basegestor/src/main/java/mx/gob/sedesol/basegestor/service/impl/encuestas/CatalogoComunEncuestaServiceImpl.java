/**
 *
 */
package mx.gob.sedesol.basegestor.service.impl.encuestas;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.gob.sedesol.basegestor.commons.constantes.ConstantesGestor;
import mx.gob.sedesol.basegestor.commons.dto.admin.CatalogoComunDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.utils.MensajesErrorEnum;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.ResultadoTransaccionEnum;
import mx.gob.sedesol.basegestor.commons.utils.TipoAccion;
import mx.gob.sedesol.basegestor.model.entities.encuestas.CatEncuestaTipo;
import mx.gob.sedesol.basegestor.model.repositories.admin.CatalogoComunRepo;
import mx.gob.sedesol.basegestor.service.admin.ComunValidacionService;
import mx.gob.sedesol.basegestor.service.encuestas.CatalogoComunEncuestaService;

/**
 * @author jhcortes
 *
 */
@Service("catalogoComunEncuestaService")
public class CatalogoComunEncuestaServiceImpl<A, ID extends Serializable> extends ComunValidacionService<CatalogoComunDTO>
        implements CatalogoComunEncuestaService<A, ID> {
    
    private static final Logger logger = Logger.getLogger(CatalogoComunEncuestaServiceImpl.class);
    
    @Autowired(required = true)
    private CatalogoComunRepo<A, ID> catalogoComunRepo;
    
    private ModelMapper catComunMapper = new ModelMapper();
    
    @Override
    public List<CatalogoComunDTO> findAll(Class<A> classEntidad) {
        catalogoComunRepo.setEntityClass(classEntidad);
        List<A> lstaux = catalogoComunRepo.findAll();
        Type lstEnt = new TypeToken<List<CatalogoComunDTO>>() {
        }.getType();
        return catComunMapper.map(lstaux, lstEnt);
    }
    
    @Override
    public CatalogoComunDTO buscarPorId(ID id, Class<A> classEntidad) {
        catalogoComunRepo.setEntityClass(classEntidad);
        A entidad = catalogoComunRepo.findOne(id);
        return catComunMapper.map(entidad, CatalogoComunDTO.class);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultadoDTO<CatalogoComunDTO> guardar(CatalogoComunDTO catDto, Class<A> classEntidad) throws Exception {
        ResultadoDTO<CatalogoComunDTO> resultado = sonDatosRequeridosValidos(TipoAccion.PERSISTENCIA, catDto);
        if (ObjectUtils.isNotNull(resultado)) {
            try {
                A entidad = catComunMapper.map(catDto, classEntidad);
                resultado = new ResultadoDTO<>();
                entidad = catalogoComunRepo.save(entidad);
                resultado.setDto(catComunMapper.map(entidad, CatalogoComunDTO.class));
            //GUSTAVO --guardarBitacoraComun(catDto, String.valueOf(entidad));
            } catch (Exception e) {
                resultado.setResultado(ResultadoTransaccionEnum.FALLIDO);
                resultado.setMensajeError(MensajesErrorEnum.ERROR_PERSISTENCIA_DATOS);
                throw e;
            }
        }
        return resultado;
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultadoDTO<CatalogoComunDTO> actualizar(CatalogoComunDTO catDto, Class<A> classEntidad) throws Exception {
        
        ResultadoDTO<CatalogoComunDTO> resultado = sonDatosRequeridosValidos(TipoAccion.ACTUALIZACION, catDto);
        if (ObjectUtils.isNotNull(resultado)) {
            try {
                resultado = new ResultadoDTO<>();
                A entidad = catComunMapper.map(catDto, classEntidad);
                entidad = catalogoComunRepo.saveAndFlush(entidad);
                resultado.setDto(catComunMapper.map(entidad, CatalogoComunDTO.class));
            //GUSTAVO --guardarBitacoraComun(catDto, String.valueOf(entidad));
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
    public ResultadoDTO<CatalogoComunDTO> eliminaLogicamente(CatalogoComunDTO catDto, Class<A> classEntidad) throws Exception {
        
        ResultadoDTO<CatalogoComunDTO> resultado = sonDatosRequeridosValidos(TipoAccion.ELIMINACION, catDto);
        if (ObjectUtils.isNotNull(resultado)) {
            try {
                catDto.setActivo(ConstantesGestor.INACTIVO);
                A entidad = catComunMapper.map(catDto, classEntidad);
                catalogoComunRepo.saveAndFlush(entidad);
                resultado.setDto(catComunMapper.map(entidad, CatalogoComunDTO.class));
                resultado = new ResultadoDTO<>();
            //GUSTAVO --guardarBitacoraComun(catDto, String.valueOf(entidad));
            } catch (Exception e) {
                resultado.setResultado(ResultadoTransaccionEnum.FALLIDO);
                resultado.setMensajeError(MensajesErrorEnum.ERROR_ELIMINAR_DATOS);
                logger.error(e.getMessage(), e);
                throw e;
            }
        }
        return resultado;
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultadoDTO<CatalogoComunDTO> eliminar(CatalogoComunDTO catDto, Class<A> classEntidad) throws Exception {
        
        ResultadoDTO<CatalogoComunDTO> resultado = sonDatosRequeridosValidos(TipoAccion.ELIMINACION, catDto);
        if (ObjectUtils.isNotNull(resultado)) {
            try {
                
                A entidad = catComunMapper.map(catDto, classEntidad);
                catalogoComunRepo.delete(entidad);
                
                resultado.setDto(catComunMapper.map(entidad, CatalogoComunDTO.class));
                resultado = new ResultadoDTO<>();
                resultado.setResultado(ResultadoTransaccionEnum.EXITOSO);
            //GUSTAVO --guardarBitacoraComun(catDto, String.valueOf(entidad));
            } catch (Exception e) {
                resultado.setResultado(ResultadoTransaccionEnum.FALLIDO);
                resultado.setMensajeError(MensajesErrorEnum.ERROR_ELIMINAR_DATOS);
                logger.error(e.getMessage(), e);
                throw e;
            }
        }
        
        return resultado;
    }
    
    @Override
    public ResultadoDTO<CatalogoComunDTO> sonDatosRequeridosValidos(TipoAccion accion, CatalogoComunDTO catDto) {
        
        ResultadoDTO<CatalogoComunDTO> res = new ResultadoDTO<>();
        
        if (ObjectUtils.isNotNull(catDto)) {
            
            switch (accion) {
                case PERSISTENCIA:
                    res = validaPersistencia(catDto);
                    break;
                
                case ACTUALIZACION:
                    res = validaActualizacion(catDto);
                    break;
                
                case ELIMINACION:
                    res = validaEliminacion(catDto);
                    break;
            }
        }
        
        return res;
    }

    /**
     *
     * @param catDto
     * @return
     */
    private ResultadoDTO<CatalogoComunDTO> validaEliminacion(CatalogoComunDTO catDto) {
        ResultadoDTO<CatalogoComunDTO> res = new ResultadoDTO<>();
        if (ObjectUtils.isNullOrEmpty(catDto.getId())) {
            res.setMensajeError(MensajesErrorEnum.ERROR_ID_REQ);
        }
        if (ObjectUtils.isNull(catDto.getFechaActualizacion())) {
            res.setMensajeError(MensajesErrorEnum.ERROR_FECHA_UPDATE_REQ);
        }
        return res;
    }

    /**
     *
     * @param catDto
     * @return
     */
    private ResultadoDTO<CatalogoComunDTO> validaActualizacion(CatalogoComunDTO catDto) {
        ResultadoDTO<CatalogoComunDTO> res = new ResultadoDTO<>();
        if (ObjectUtils.isNullOrEmpty(catDto.getId())) {
            res.setMensajeError(MensajesErrorEnum.ERROR_ID_REQ);
        }
        if (ObjectUtils.isNullOrEmpty(catDto.getUsuarioModifico())) {
            res.setMensajeError(MensajesErrorEnum.ERROR_USUARIO_REQ);
        }
        if (ObjectUtils.isNull(catDto.getFechaActualizacion())) {
            res.setMensajeError(MensajesErrorEnum.ERROR_FECHA_UPDATE_REQ);
        }
        return res;
    }

    /**
     *
     * @param catDto
     * @return
     */
    private ResultadoDTO<CatalogoComunDTO> validaPersistencia(CatalogoComunDTO catDto) {
        ResultadoDTO<CatalogoComunDTO> res = new ResultadoDTO<>();
        
        if (ObjectUtils.isNullOrEmpty(catDto.getNombre())) {
            res.setMensajeError(MensajesErrorEnum.ERROR_NOMBRE_REQ);
        }
        if (ObjectUtils.isNull(catDto.getFechaRegistro())) {
            res.setMensajeError(MensajesErrorEnum.ERROR_FECHA_REGISTRO_REQ);
        }
        if (ObjectUtils.isNullOrEmpty(catDto.getUsuarioModifico())) {
            res.setMensajeError(MensajesErrorEnum.ERROR_USUARIO_REQ);
        }
        return res;
    }
    
    @Override
    public CatalogoComunDTO buscarRegistroPorNombre(String nombre, Class<A> classEntidad) {
        catalogoComunRepo.setEntityClass(classEntidad);
        A entidad = catalogoComunRepo.buscarRegistroPorNombre(nombre);
        return catComunMapper.map(entidad, CatalogoComunDTO.class);
    }
    
    @Override
    public List<CatalogoComunDTO> buscarPorContexto(Long idContexto, Class<A> classEntidad) {
        catalogoComunRepo.setEntityClass(classEntidad);
        List<A> lstaux = catalogoComunRepo.buscarPorContexto(idContexto);
        Type lstEnt = new TypeToken<List<CatalogoComunDTO>>() {
        }.getType();
        return catComunMapper.map(lstaux, lstEnt);
    }
    
    @Override
    public List<CatalogoComunDTO> buscarTipoEncuesta(Class<A> classEntidad) {
        catalogoComunRepo.setEntityClass(classEntidad);
        List<A> lstaux = catalogoComunRepo.buscarCatTipoEncuesta();
        List<CatalogoComunDTO> lstEnt = new ArrayList<>();
        
        if (lstaux != null) {
            for (int x = 0; x < lstaux.size(); x++) {
                CatEncuestaTipo tipo = (CatEncuestaTipo) lstaux.get(x);
                lstEnt.add(catComunMapper.map(tipo, CatalogoComunDTO.class));
            }
        }
        return lstEnt;
    }
    
    
    @Override
    public void validarPersistencia(CatalogoComunDTO dto, ResultadoDTO<CatalogoComunDTO> resultado) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public void validarActualizacion(CatalogoComunDTO dto, ResultadoDTO<CatalogoComunDTO> resultado) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public void validarEliminacion(CatalogoComunDTO dto, ResultadoDTO<CatalogoComunDTO> resultado) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
