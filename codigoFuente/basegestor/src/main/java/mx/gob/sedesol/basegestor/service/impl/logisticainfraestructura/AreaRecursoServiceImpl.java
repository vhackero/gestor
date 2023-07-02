package mx.gob.sedesol.basegestor.service.impl.logisticainfraestructura;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.gob.sedesol.basegestor.commons.constantes.ConstantesBitacora;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.logisticainfraestructura.RelAreaRecursoDTO;
import mx.gob.sedesol.basegestor.commons.utils.MensajesSistemaEnum;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.TipoAccion;
import mx.gob.sedesol.basegestor.model.entities.logisticainfraestructura.RelAreaRecurso;
import mx.gob.sedesol.basegestor.model.repositories.logisticainfraestructura.AreaRecursoRepo;
import mx.gob.sedesol.basegestor.mongo.service.AdministradorBitacora;
import mx.gob.sedesol.basegestor.service.admin.ComunValidacionService;
import mx.gob.sedesol.basegestor.service.logisticainfraestructura.AreaRecursoService;

@Service("areaRecursoService")
public class AreaRecursoServiceImpl extends ComunValidacionService<RelAreaRecursoDTO> implements AreaRecursoService {
    
    private static final Logger logger = Logger.getLogger(AreaRecursoServiceImpl.class);
    
    @Autowired
    private AreaRecursoRepo areaRecursoRepo;
    
    private ModelMapper mapper = new ModelMapper();
    
    @Override
    public List<RelAreaRecursoDTO> obtieneRecursosPorIdArea(Integer idArea) {
        
        Type listAux = new TypeToken<List<RelAreaRecursoDTO>>() {
        }.getType();
        List<RelAreaRecurso> areaRecursos = areaRecursoRepo.obtieneRecursosPorIdArea(idArea);
        return mapper.map(areaRecursos, listAux);
    }
    
    @Override
    public List<RelAreaRecursoDTO> findAll() {
        return new ArrayList<>();
    }
    
    @Override
    public RelAreaRecursoDTO buscarPorId(Integer id) {
        return new RelAreaRecursoDTO();
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultadoDTO<RelAreaRecursoDTO> guardar(RelAreaRecursoDTO dto) {
        ResultadoDTO<RelAreaRecursoDTO> res = sonDatosRequeridosValidos(TipoAccion.PERSISTENCIA, dto);
        try {
            
            if (ObjectUtils.isNotNull(res) && res.getResultado().getValor()) {
                
                RelAreaRecurso areaRecurso = mapper.map(dto, RelAreaRecurso.class);
                areaRecurso = areaRecursoRepo.save(areaRecurso);
                res.setDto(mapper.map(areaRecurso, RelAreaRecursoDTO.class));
                
            //GUSTAVO --guardarBitacoraAreaRecurso(dto, String.valueOf(areaRecurso.getIdAreaConfig()));
            }
            
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            res.setMensajeError(MensajesSistemaEnum.ADMIN_MSG_GUARDADO_FALLIDO);
        }
        return res;
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultadoDTO<RelAreaRecursoDTO> actualizar(RelAreaRecursoDTO dto) {
        ResultadoDTO<RelAreaRecursoDTO> res = sonDatosRequeridosValidos(TipoAccion.ACTUALIZACION, dto);
        try {
            
            if (ObjectUtils.isNotNull(res) && res.getResultado().getValor()) {
                
                RelAreaRecurso areaRecurso = mapper.map(dto, RelAreaRecurso.class);
                areaRecurso = areaRecursoRepo.save(areaRecurso);
                res.setDto(mapper.map(areaRecurso, RelAreaRecursoDTO.class));
                
            //GUSTAVO --guardarBitacoraAreaRecurso(dto, String.valueOf(areaRecurso.getIdAreaConfig()));
                res.agregaMensaje(MensajesSistemaEnum.ADMIN_MSG_ACTUALIZACION_EXITOSA.getId());
            }
            
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            res.setMensajeError(MensajesSistemaEnum.ADMIN_MSG_ACTUALIZACION_FALLIDA);
        }
        return res;
    }
    
    @Override
    public ResultadoDTO<RelAreaRecursoDTO> eliminar(RelAreaRecursoDTO dto) {
        ResultadoDTO<RelAreaRecursoDTO> res = sonDatosRequeridosValidos(TipoAccion.ELIMINACION, dto);
        try {
            RelAreaRecurso areaRecurso = mapper.map(dto, RelAreaRecurso.class);
            areaRecursoRepo.delete(areaRecurso);
            res = new ResultadoDTO<>();
            res.setDto(mapper.map(areaRecurso, RelAreaRecursoDTO.class));
        //GUSTAVO --guardarBitacoraAreaRecurso(dto, String.valueOf(areaRecurso.getIdAreaConfig()));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            res.setMensajeError(MensajesSistemaEnum.ADMIN_MSG_ELIMINACION_FALLIDA);
        }
        return res;
    }
    
    @Override
    public ResultadoDTO<RelAreaRecursoDTO> sonDatosRequeridosValidos(TipoAccion accion, RelAreaRecursoDTO dto) {
        ResultadoDTO<RelAreaRecursoDTO> resultado = null;
        
        if (ObjectUtils.isNotNull(dto)) {
            resultado = new ResultadoDTO<>();
            
            switch (accion) {
                case PERSISTENCIA:
                    validarPersistencia(dto, resultado);
                    break;
                
                case ELIMINACION:
                    validarEliminacion(dto, resultado);
                    break;
                
                case ACTUALIZACION:
                    validarActualizacion(dto, resultado);
                    break;
                
                default:
                    resultado.setMensajeError(MensajesSistemaEnum.LOGISTICA_INFRAESTRUCTURA_MSG_OPERACION_REQ);
                    break;
            }
        }
        return resultado;
    }
    
    public void validarPersistencia(RelAreaRecursoDTO dto, ResultadoDTO<RelAreaRecursoDTO> resultado) {
        if (ObjectUtils.isNullOrEmpty(dto.getIdAreaConfig())) {
            resultado.setMensajeError(MensajesSistemaEnum.AREA_RECURSOS_ID_AREA_REQ);
        }
        if (ObjectUtils.isNullOrEmpty(dto.getIdRecurso())) {
            resultado.setMensajeError(MensajesSistemaEnum.AREA_RECURSOS_ID_RECURSO_REQ);
        }
        if (ObjectUtils.isNullOrEmpty(dto.getCantidad())) {
            resultado.setMensajeError(MensajesSistemaEnum.AREA_RECURSOS_CANTIDAD_REQ);
        }
    }
    
    public void validarEliminacion(RelAreaRecursoDTO dto, ResultadoDTO<RelAreaRecursoDTO> resultado) {
        if (ObjectUtils.isNullOrEmpty(dto.getIdAreaConfig())) {
            resultado.setMensajeError(MensajesSistemaEnum.AREA_RECURSOS_ID_AREA_REQ);
        }
        if (ObjectUtils.isNullOrEmpty(dto.getIdRecurso())) {
            resultado.setMensajeError(MensajesSistemaEnum.AREA_RECURSOS_ID_RECURSO_REQ);
        }
    }
    
    public void validarActualizacion(RelAreaRecursoDTO dto, ResultadoDTO<RelAreaRecursoDTO> resultado) {
        if (ObjectUtils.isNullOrEmpty(dto.getIdAreaConfig())) {
            resultado.setMensajeError(MensajesSistemaEnum.AREA_RECURSOS_ID_AREA_REQ);
        }
        if (ObjectUtils.isNullOrEmpty(dto.getIdRecurso())) {
            resultado.setMensajeError(MensajesSistemaEnum.AREA_RECURSOS_ID_RECURSO_REQ);
        }
        if (ObjectUtils.isNullOrEmpty(dto.getCantidad())) {
            resultado.setMensajeError(MensajesSistemaEnum.AREA_RECURSOS_CANTIDAD_REQ);
        }
    }
    
    public AreaRecursoRepo getAreaRecursoRepo() {
        return areaRecursoRepo;
    }
    
    public void setAreaRecursoRepo(AreaRecursoRepo areaRecursoRepo) {
        this.areaRecursoRepo = areaRecursoRepo;
    }
    
}
