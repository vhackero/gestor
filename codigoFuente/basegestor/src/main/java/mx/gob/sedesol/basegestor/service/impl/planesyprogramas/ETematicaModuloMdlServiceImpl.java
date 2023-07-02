package mx.gob.sedesol.basegestor.service.impl.planesyprogramas;

import java.lang.reflect.Type;
import java.util.List;

import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.RelETematicaModuloMdlDTO;
import mx.gob.sedesol.basegestor.commons.utils.MensajesSistemaEnum;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.TipoAccion;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.RelEtematicaModuloMdl;
import mx.gob.sedesol.basegestor.model.repositories.planesyprogramas.ETematicaModuloMdlRepo;
import mx.gob.sedesol.basegestor.service.admin.ComunValidacionService;
import mx.gob.sedesol.basegestor.service.planesyprogramas.ETematicaModuloMdlService;

@Service("eTematicaModuloMdlService")
public class ETematicaModuloMdlServiceImpl extends ComunValidacionService<RelETematicaModuloMdlDTO> implements ETematicaModuloMdlService {
    
    private static final Logger logger = Logger.getLogger(ETematicaModuloMdlService.class);
    
    @Autowired
    private ETematicaModuloMdlRepo eTematicaModuloMdlRepo;
    
    private ModelMapper etematiaMdlMapper = new ModelMapper();
    
    @Override
    public List<RelETematicaModuloMdlDTO> findAll() {
        Type lstAuxMdl = new TypeToken<List<RelETematicaModuloMdlDTO>>() {
        }.getType();
        return etematiaMdlMapper.map(eTematicaModuloMdlRepo.findAll(), lstAuxMdl);
    }
    
    @Override
    public RelETematicaModuloMdlDTO buscarPorId(Integer id) {
        return etematiaMdlMapper.map(eTematicaModuloMdlRepo.getOne(id), RelETematicaModuloMdlDTO.class);
    }
    
    @Transactional(rollbackFor = Exception.class)
    public ResultadoDTO<RelETematicaModuloMdlDTO> guardar(RelETematicaModuloMdlDTO dto) {
        
        logger.debug("Persistiendo relacion estructura tematica modulo mdl ");
        ResultadoDTO<RelETematicaModuloMdlDTO> rs = sonDatosRequeridosValidos(TipoAccion.PERSISTENCIA, dto);
        if (ObjectUtils.isNotNull(rs) && rs.getResultado().getValor()) {
            try {
                
                RelEtematicaModuloMdl relTemaMdl = etematiaMdlMapper.map(dto, RelEtematicaModuloMdl.class);
                relTemaMdl = eTematicaModuloMdlRepo.save(relTemaMdl);
                rs = new ResultadoDTO<RelETematicaModuloMdlDTO>();
            //GUSTAVO --guardarBitacoraActividad(dto, String.valueOf(relTemaMdl.getIdEstructuraTematica()));
                
            } catch (Exception e) {
                rs.setMensajeError(MensajesSistemaEnum.ADMIN_MSG_GUARDADO_FALLIDO);
                logger.error(e.getMessage(), e);
                throw e;
            }
        }
        return rs;
    }
    
    @Override
    public ResultadoDTO<RelETematicaModuloMdlDTO> actualizar(RelETematicaModuloMdlDTO dto) {
        return null;
    }
    
    @Transactional(rollbackFor = Exception.class)
    public ResultadoDTO<RelETematicaModuloMdlDTO> eliminar(RelETematicaModuloMdlDTO dto) {
        logger.debug("Eliminando relacion estructura tematica modulo mdl");
        ResultadoDTO<RelETematicaModuloMdlDTO> rs = sonDatosRequeridosValidos(TipoAccion.ELIMINACION, dto);
        if (ObjectUtils.isNotNull(rs) && rs.getResultado().getValor()) {
            try {
                
                RelEtematicaModuloMdl relTemaMdl = etematiaMdlMapper.map(dto, RelEtematicaModuloMdl.class);
                eTematicaModuloMdlRepo.delete(relTemaMdl);
                rs = new ResultadoDTO<RelETematicaModuloMdlDTO>();
            //GUSTAVO --guardarBitacoraActividad(dto, String.valueOf(relTemaMdl.getIdEstructuraTematica()));
                
            } catch (Exception e) {
                rs.setMensajeError(MensajesSistemaEnum.ADMIN_MSG_ELIMINACION_FALLIDA);
                logger.error(e.getMessage(), e);
                throw e;
            }
        }
        return rs;
    }
    
    @Override
    public ResultadoDTO<RelETematicaModuloMdlDTO> sonDatosRequeridosValidos(TipoAccion accion, RelETematicaModuloMdlDTO dto) {
        
        ResultadoDTO<RelETematicaModuloMdlDTO> resultado = null;
        if (ObjectUtils.isNotNull(dto)) {
            resultado = new ResultadoDTO<RelETematicaModuloMdlDTO>();
            
            switch (accion) {
                case PERSISTENCIA:
                    
                    if (ObjectUtils.isNullOrEmpty(dto.getFechaRegistro())) {
                        resultado.setMensajeError(MensajesSistemaEnum.MSG_GENERAL_FECHA_REGISTRO_REQ);
                    }
                    if (ObjectUtils.isNull(dto.getUsuarioModifico())) {
                        resultado.setMensajeError(MensajesSistemaEnum.MSG_GENERAL_USUARIO_MODIFICO_REQ);
                    }
                    if (ObjectUtils.isNull(dto.getIdEstructuraTematica())) {
                        resultado.setMensajeError(MensajesSistemaEnum.EST_TEMATICA_ID_REQ);
                    }
                    if (ObjectUtils.isNull(dto.getIdModuloMdl())) {
                        resultado.setMensajeError(MensajesSistemaEnum.MDL_MODULOS_ID_REQ);
                    }
                    break;
                
                case ELIMINACION:
                    if (ObjectUtils.isNull(dto.getIdEstructuraTematica())) {
                        resultado.setMensajeError(MensajesSistemaEnum.FICHA_DESC_PROG_ID_REQ);
                    }
                    break;
                
                case ACTUALIZACION:
                    return null;
            }
        }
        return resultado;
    }
    
    @Override
    public void validarPersistencia(RelETematicaModuloMdlDTO dto, ResultadoDTO<RelETematicaModuloMdlDTO> resultado) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public void validarActualizacion(RelETematicaModuloMdlDTO dto, ResultadoDTO<RelETematicaModuloMdlDTO> resultado) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public void validarEliminacion(RelETematicaModuloMdlDTO dto, ResultadoDTO<RelETematicaModuloMdlDTO> resultado) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
