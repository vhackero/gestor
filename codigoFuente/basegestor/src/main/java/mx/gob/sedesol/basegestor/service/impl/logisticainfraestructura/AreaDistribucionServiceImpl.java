package mx.gob.sedesol.basegestor.service.impl.logisticainfraestructura;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.logisticainfraestructura.RelAreaDistribucionDTO;
import mx.gob.sedesol.basegestor.commons.utils.MensajesSistemaEnum;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.TipoAccion;
import mx.gob.sedesol.basegestor.model.entities.logisticainfraestructura.RelAreaDistribucion;
import mx.gob.sedesol.basegestor.model.repositories.logisticainfraestructura.AreaDistribucionRepo;
import mx.gob.sedesol.basegestor.service.admin.ComunValidacionService;
import mx.gob.sedesol.basegestor.service.logisticainfraestructura.AreaDistribucionService;

@Service("areaDistribucionService")
public class AreaDistribucionServiceImpl extends ComunValidacionService<RelAreaDistribucionDTO> implements AreaDistribucionService {
    
    Logger logger = Logger.getLogger(AreaDistribucionServiceImpl.class);
    
    @Autowired
    private AreaDistribucionRepo areaDistribucionRepo;
    
    private ModelMapper mapper = new ModelMapper();
    
    @Override
    public List<RelAreaDistribucionDTO> findAll() {
        return new ArrayList<>();
    }
    
    @Override
    public RelAreaDistribucionDTO buscarPorId(Integer id) {
        return new RelAreaDistribucionDTO();
    }
    
    @Override
    public ResultadoDTO<RelAreaDistribucionDTO> guardar(RelAreaDistribucionDTO dto) {
        return new ResultadoDTO<>();
    }
    
    @Override
    public ResultadoDTO<RelAreaDistribucionDTO> actualizar(RelAreaDistribucionDTO dto) {
        return new ResultadoDTO<>();
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultadoDTO<RelAreaDistribucionDTO> eliminar(RelAreaDistribucionDTO dto) {
        ResultadoDTO<RelAreaDistribucionDTO> res = sonDatosRequeridosValidos(TipoAccion.ELIMINACION, dto);
        if (ObjectUtils.isNotNull(res) && res.getResultado().getValor()) {
            try {
                RelAreaDistribucion areaDistribucion = mapper.map(dto, RelAreaDistribucion.class);
                areaDistribucionRepo.delete(areaDistribucion);
                res = new ResultadoDTO<>();
                res.setDto(mapper.map(areaDistribucion, RelAreaDistribucionDTO.class));
            //GUSTAVO --guardarBitacoraAreaDistribucion(dto, String.valueOf(areaDistribucion.getIdAreaConfig()));
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                res.setMensajeError(MensajesSistemaEnum.ADMIN_MSG_ELIMINACION_FALLIDA);
            }
        }
        return res;
    }
    
    @Override
    public ResultadoDTO<RelAreaDistribucionDTO> sonDatosRequeridosValidos(TipoAccion accion,
            RelAreaDistribucionDTO dto) {
        ResultadoDTO<RelAreaDistribucionDTO> resultado = null;
        
        if (ObjectUtils.isNotNull(dto)) {
            resultado = new ResultadoDTO<>();
            
            switch (accion) {
                case PERSISTENCIA:
                    break;
                case ACTUALIZACION:
                    break;
                case ELIMINACION:
                    validarEliminacion(dto, resultado);
                    break;
                
                default:
                    resultado.setMensajeError(MensajesSistemaEnum.LOGISTICA_INFRAESTRUCTURA_MSG_OPERACION_REQ);
                    break;
            }
        }
        return resultado;
    }
    
    public void validarEliminacion(RelAreaDistribucionDTO dto, ResultadoDTO<RelAreaDistribucionDTO> resultado) {
        if (ObjectUtils.isNullOrEmpty(dto.getIdAreaConfig())) {
            resultado.setMensajeError(MensajesSistemaEnum.AREA_DISTRIBUCION_ID_AREA_REQ);
        }
        if (ObjectUtils.isNullOrEmpty(dto.getIdDistribucion())) {
            resultado.setMensajeError(MensajesSistemaEnum.AREA_DISTRIBUCION_ID_DISTRIBUCION_REQ);
        }
    }
    
    public AreaDistribucionRepo getAreaDistribucionRepo() {
        return areaDistribucionRepo;
    }
    
    public void setAreaDistribucionRepo(AreaDistribucionRepo areaDistribucionRepo) {
        this.areaDistribucionRepo = areaDistribucionRepo;
    }
    
    public ModelMapper getMapper() {
        return mapper;
    }
    
    public void setMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }
    
    private void guardarBitacoraAreaDistribucion(RelAreaDistribucionDTO dto, String registro) {
        //Usar la clave/id del registro guardado
        //GUSTAVO --dto.getBitacoraDTO()setRegistro(registro);
        //Guarda la bitácora
//GUSTAVO --guardarBitacora(dto.getBitacoraDTO());
    }
    
    @Override
    public void validarPersistencia(RelAreaDistribucionDTO dto, ResultadoDTO<RelAreaDistribucionDTO> resultado) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public void validarActualizacion(RelAreaDistribucionDTO dto, ResultadoDTO<RelAreaDistribucionDTO> resultado) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
