/**
 *
 */
package mx.gob.sedesol.basegestor.service.impl.gestion.aprendizaje;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import mx.gob.sedesol.basegestor.commons.dto.admin.CatalogoComunDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestion.aprendizaje.CargaArchivosOaDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestion.aprendizaje.UnidadOaAvaDTO;
import mx.gob.sedesol.basegestor.commons.utils.MensajesErrorEnum;
import mx.gob.sedesol.basegestor.commons.utils.MensajesSistemaEnum;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.ResultadoTransaccionEnum;
import mx.gob.sedesol.basegestor.commons.utils.TipoAccion;
import mx.gob.sedesol.basegestor.model.entities.gestionaprendizaje.CatClasificacionArchivoOa;
import mx.gob.sedesol.basegestor.model.entities.gestionaprendizaje.RelUnidadOaAva;
import mx.gob.sedesol.basegestor.model.entities.gestionaprendizaje.TblCargaArchivosOa;
import mx.gob.sedesol.basegestor.model.repositories.gestion.aprendizaje.CargaArchivosOaRepo;
import mx.gob.sedesol.basegestor.service.admin.ComunValidacionService;
import mx.gob.sedesol.basegestor.service.gestion.aprendizaje.CargaArchivosOaService;

/**
 * @author jhcortes
 *
 */
@Service("cargaArchivosOaService")
public class CargaArchivosOaServiceImpl extends ComunValidacionService<CargaArchivosOaDTO> implements CargaArchivosOaService {
    
    private static final Logger logger = Logger.getLogger(CargaArchivosOaServiceImpl.class);
    
    @Autowired
    private CargaArchivosOaRepo cargaArchivosOaRepo;
    
    private ModelMapper cargaArchivosMapper = new ModelMapper();
    
    @Override
    public List<CargaArchivosOaDTO> findAll() {
        
        List<TblCargaArchivosOa> tblCargaArchivosOas
                = cargaArchivosOaRepo.findAll();
        
        for (TblCargaArchivosOa tblCargaArchivosOa : tblCargaArchivosOas) {
            tblCargaArchivosOa.setUnidadOaAva(null);
            
        }
        
        Type RecursosOaDTO = new TypeToken<List<CargaArchivosOaDTO>>() {
        }.getType();
        
        return cargaArchivosMapper.map(tblCargaArchivosOas, RecursosOaDTO);
        
    }
    
    @Override
    public CargaArchivosOaDTO buscarPorId(Integer id) {
        TblCargaArchivosOa entidad = cargaArchivosOaRepo.findOne(id);
        CargaArchivosOaDTO dto = new CargaArchivosOaDTO();
        
        if (ObjectUtils.isNotNull(entidad)) {
            cargaArchivosMapper.map(entidad, dto);
        }
        return dto;
    }
    
    @Override
    public List<CargaArchivosOaDTO> buscarPorIdUnidadOa(UnidadOaAvaDTO unidadOaAvaDTO, CatalogoComunDTO catalogoComunDTO) {
        
        RelUnidadOaAva relUnidadOaAva = cargaArchivosMapper.map(unidadOaAvaDTO, RelUnidadOaAva.class);
        
        CatClasificacionArchivoOa catClasificacionArchivoOa = cargaArchivosMapper.map(catalogoComunDTO, CatClasificacionArchivoOa.class);;
        
        List<TblCargaArchivosOa> tblCargaArchivosOas
                = cargaArchivosOaRepo.findByUnidadOaAvaAndCatClasificacionArchivo(relUnidadOaAva, catClasificacionArchivoOa);
        
        for (TblCargaArchivosOa tblCargaArchivosOa : tblCargaArchivosOas) {
            tblCargaArchivosOa.setUnidadOaAva(null);
            
        }
        
        Type cargaArchivosOaDTO = new TypeToken<List<CargaArchivosOaDTO>>() {
        }.getType();
        
        return cargaArchivosMapper.map(tblCargaArchivosOas, cargaArchivosOaDTO);
        
    }
    
    @Transactional(rollbackFor = Exception.class)
    public ResultadoDTO<CargaArchivosOaDTO> guardar(CargaArchivosOaDTO dto) {
        
        ResultadoDTO<CargaArchivosOaDTO> resultado = sonDatosRequeridosValidos(TipoAccion.PERSISTENCIA, dto);
        if (ObjectUtils.isNotNull(resultado) && resultado.getResultado().getValor()) {
            try {
                resultado = new ResultadoDTO<CargaArchivosOaDTO>();
                
                TblCargaArchivosOa entidad = cargaArchivosMapper.map(dto, TblCargaArchivosOa.class);
                cargaArchivosOaRepo.save(entidad);
                resultado.setDto(cargaArchivosMapper.map(entidad, CargaArchivosOaDTO.class));
                
            //GUSTAVO --guardarBitacoraCargaArchivos(dto, entidad.getIdArchivo());
                
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
    
    @Transactional(rollbackFor = Exception.class)
    public ResultadoDTO<CargaArchivosOaDTO> actualizar(CargaArchivosOaDTO dto) {
        ResultadoDTO<CargaArchivosOaDTO> resultado = sonDatosRequeridosValidos(TipoAccion.ACTUALIZACION, dto);
        if (ObjectUtils.isNotNull(resultado) && resultado.getResultado().getValor()) {
            try {
                resultado = new ResultadoDTO<CargaArchivosOaDTO>();
                
                TblCargaArchivosOa entidad = cargaArchivosMapper.map(dto, TblCargaArchivosOa.class);
                cargaArchivosOaRepo.saveAndFlush(entidad);
                resultado.setDto(cargaArchivosMapper.map(entidad, CargaArchivosOaDTO.class));
                
            //GUSTAVO --guardarBitacoraCargaArchivos(dto, entidad.getIdArchivo());
                resultado.agregaMensaje(MensajesSistemaEnum.ADMIN_MSG_ACTUALIZACION_EXITOSA.getId());
                
            } catch (Exception e) {
                resultado.setResultado(ResultadoTransaccionEnum.FALLIDO);
                resultado.setMensajeError(MensajesSistemaEnum.ADMIN_MSG_ACTUALIZACION_FALLIDA);
                logger.error(e.getMessage(), e);
                throw e;
            }
        }
        
        return resultado;
    }
    
    @Transactional(rollbackFor = Exception.class)
    public ResultadoDTO<CargaArchivosOaDTO> eliminar(CargaArchivosOaDTO dto) {
        ResultadoDTO<CargaArchivosOaDTO> resultado = sonDatosRequeridosValidos(TipoAccion.ELIMINACION, dto);
        if (ObjectUtils.isNotNull(resultado) && resultado.getResultado().getValor()) {
            try {
                resultado = new ResultadoDTO<CargaArchivosOaDTO>();
                
                TblCargaArchivosOa entidad = cargaArchivosMapper.map(dto, TblCargaArchivosOa.class);
                cargaArchivosOaRepo.delete(entidad);
                resultado.setDto(cargaArchivosMapper.map(entidad, CargaArchivosOaDTO.class));
                resultado.setResultado(ResultadoTransaccionEnum.EXITOSO);
                
            //GUSTAVO --guardarBitacoraCargaArchivos(dto, entidad.getIdArchivo());
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
    public ResultadoDTO<CargaArchivosOaDTO> sonDatosRequeridosValidos(TipoAccion accion, CargaArchivosOaDTO dto) {
        ResultadoDTO<CargaArchivosOaDTO> res = null;
        
        if (ObjectUtils.isNotNull(dto)) {
            res = new ResultadoDTO<CargaArchivosOaDTO>();
            
            switch (accion) {
                case PERSISTENCIA:
                    
                    if (ObjectUtils.isNullOrEmpty(dto.getDirectorio())) {
                        res.setMensajeError(MensajesErrorEnum.ERROR_VALOR_REQ);
                    }
                    if (ObjectUtils.isNull(dto.getFechaRegistro())) {
                        res.setMensajeError(MensajesErrorEnum.ERROR_FECHA_REGISTRO_REQ);
                    }
                    if (ObjectUtils.isNullOrEmpty(dto.getUsuarioModifico())) {
                        res.setMensajeError(MensajesErrorEnum.ERROR_PERSONA_REQ);
                    }
                    if (ObjectUtils.isNullOrEmpty(dto.getNombreArchivo())) {
                        res.setMensajeError(MensajesErrorEnum.ERROR_VALOR_REQ);
                    }
                    
                    break;
                
                case ACTUALIZACION:
                    
                    if (ObjectUtils.isNullOrEmpty(dto.getId())) {
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
                    
                    if (ObjectUtils.isNullOrEmpty(dto.getId())) {
                        res.setMensajeError(MensajesErrorEnum.ERROR_ID_REQ);
                    }                    
                    break;
                
            }
        }
        return res;
    }
    
    @Override
    public List<CargaArchivosOaDTO> obtenerArchivosPorUnidadOA(Integer idUnidadOA) {
        
        List<CargaArchivosOaDTO> lista = null;
        
        try {
            List<TblCargaArchivosOa> listaEntidad = cargaArchivosOaRepo.obtenerArchivosPorUnidadOA(idUnidadOA);
            lista = new ArrayList<>();
            
            for (TblCargaArchivosOa entidad : listaEntidad) {
                CargaArchivosOaDTO dto = new CargaArchivosOaDTO();
                cargaArchivosMapper.map(entidad, dto);
                lista.add(dto);
            }
            
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw e;
        }
        
        return lista;
    }
    

    
    @Override
    public void validarPersistencia(CargaArchivosOaDTO dto, ResultadoDTO<CargaArchivosOaDTO> resultado) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public void validarActualizacion(CargaArchivosOaDTO dto, ResultadoDTO<CargaArchivosOaDTO> resultado) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public void validarEliminacion(CargaArchivosOaDTO dto, ResultadoDTO<CargaArchivosOaDTO> resultado) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
