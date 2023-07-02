package mx.gob.sedesol.basegestor.service.impl.gestionescolar;

import mx.gob.sedesol.basegestor.commons.constantes.ConstantesGestor;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.AsistenciaDTO;
import mx.gob.sedesol.basegestor.commons.utils.MensajesErrorEnum;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.ResultadoTransaccionEnum;
import mx.gob.sedesol.basegestor.commons.utils.TipoAccion;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.CatAsistencia;
import mx.gob.sedesol.basegestor.model.repositories.gestionescolar.CatalogoAsistenciaRepo;
import mx.gob.sedesol.basegestor.mongo.service.AdministradorBitacora;
import mx.gob.sedesol.basegestor.service.gestionescolar.CatalogoAsistenciaService;
import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;
import mx.gob.sedesol.basegestor.service.admin.ComunValidacionService;

/**
 * Created by jhcortes on 2/02/17.
 */
@Service("catalogoAsistenciaService")
public class CatalogoAsitenciaServiceImpl extends ComunValidacionService<AsistenciaDTO> implements CatalogoAsistenciaService {

    private static final Logger logger = Logger.getLogger(CatalogoAsitenciaServiceImpl.class);

    @Autowired
    private CatalogoAsistenciaRepo catalogoAsistenciaRepo;

    private ModelMapper catComunMapper = new ModelMapper();

    @Override
    public List<AsistenciaDTO> findAll() {
        List<CatAsistencia> lstAsistencia = catalogoAsistenciaRepo.findAll();
        Type lstAsistenciaDTO = new TypeToken<List<AsistenciaDTO>>() {
        }.getType();
        return catComunMapper.map(lstAsistencia, lstAsistenciaDTO);
    }

    @Override
    public AsistenciaDTO buscarPorId(Integer id) {
        CatAsistencia entidad = catalogoAsistenciaRepo.findOne(id);
        return catComunMapper.map(entidad, AsistenciaDTO.class);
    }

    @Override
    public ResultadoDTO<AsistenciaDTO> guardar(AsistenciaDTO dto) {

        ResultadoDTO<AsistenciaDTO> resultado = sonDatosRequeridosValidos(TipoAccion.PERSISTENCIA, dto);
        if(ObjectUtils.isNotNull(resultado) && resultado.getResultado().getValor()){
            try{

                resultado = new ResultadoDTO<AsistenciaDTO>();

                CatAsistencia entidad = new CatAsistencia();
                catComunMapper.map(dto, entidad);

                catalogoAsistenciaRepo.save(entidad);
                resultado.setDto(catComunMapper.map(entidad, AsistenciaDTO.class));
            //GUSTAVO --guardarBitacoraAsistencia(dto, String.valueOf(entidad.getId()));
            }catch(Exception e){
                resultado.setResultado(ResultadoTransaccionEnum.FALLIDO);
                resultado.setMensajeError(MensajesErrorEnum.ERROR_PERSISTENCIA_DATOS);
                logger.error(e.getMessage(),e);
                throw e;
            }
        }

        return resultado;
    }

    @Override
    public ResultadoDTO<AsistenciaDTO> actualizar(AsistenciaDTO dto) {

        ResultadoDTO<AsistenciaDTO> resultado = sonDatosRequeridosValidos(TipoAccion.ACTUALIZACION, dto);

        if( ObjectUtils.isNotNull(resultado) && resultado.getResultado().getValor()){
            try{

                resultado = new ResultadoDTO<AsistenciaDTO>();
                CatAsistencia entidad = catComunMapper.map(dto, CatAsistencia.class);
                catalogoAsistenciaRepo.saveAndFlush(entidad);
                resultado.setDto(catComunMapper.map(entidad, AsistenciaDTO.class));
            //GUSTAVO --guardarBitacoraAsistencia(dto, String.valueOf(entidad.getId()));
            }catch(Exception e ){
                resultado.setResultado(ResultadoTransaccionEnum.FALLIDO);
                resultado.setMensajeError(MensajesErrorEnum.ERROR_ACTUALIZACION_DATOS);
                logger.error(e.getMessage(),e);
                throw e;
            }
        }
        return resultado;
    }

    @Override
    public ResultadoDTO<AsistenciaDTO> eliminar(AsistenciaDTO dto) {
        ResultadoDTO<AsistenciaDTO> resultado = sonDatosRequeridosValidos(TipoAccion.ELIMINACION, dto);
        if(ObjectUtils.isNotNull(resultado)&& resultado.getResultado().getValor()){
            try{
                dto.setActivo(ConstantesGestor.INACTIVO);
                CatAsistencia entidad = catComunMapper.map(dto, CatAsistencia.class);
                catalogoAsistenciaRepo.delete(entidad);
                resultado.setDto(catComunMapper.map(entidad, AsistenciaDTO.class));
                resultado.setResultado(ResultadoTransaccionEnum.EXITOSO);
            //GUSTAVO --guardarBitacoraAsistencia(dto, String.valueOf(entidad.getId()));
            }catch(Exception e){
                resultado.setResultado(ResultadoTransaccionEnum.FALLIDO);
                resultado.setMensajeError(MensajesErrorEnum.ERROR_ELIMINAR_DATOS);
                throw e;
            }
        }
        return resultado;
    }

    @Override
    public ResultadoDTO<AsistenciaDTO> sonDatosRequeridosValidos(TipoAccion accion, AsistenciaDTO dto) {
        ResultadoDTO<AsistenciaDTO> res = null;

        if(ObjectUtils.isNotNull(dto)) {
            res = new ResultadoDTO();

            switch (accion) {
                case PERSISTENCIA:

                    if (ObjectUtils.isNullOrEmpty(dto.getNombre()))
                        res.setMensajeError(MensajesErrorEnum.ERROR_NOMBRE_REQ);
                    if (ObjectUtils.isNull(dto.getFechaRegistro()))
                        res.setMensajeError(MensajesErrorEnum.ERROR_FECHA_REGISTRO_REQ);
                    if (ObjectUtils.isNullOrEmpty(dto.getUsuarioModifico()))
                        res.setMensajeError(MensajesErrorEnum.ERROR_USUARIO_REQ);

                    break;

                case ACTUALIZACION:

                    if (ObjectUtils.isNullOrEmpty(dto.getId()))
                        res.setMensajeError(MensajesErrorEnum.ERROR_ID_REQ);
                    if (ObjectUtils.isNullOrEmpty(dto.getUsuarioModifico()))
                        res.setMensajeError(MensajesErrorEnum.ERROR_USUARIO_REQ);
                    if (ObjectUtils.isNull(dto.getFechaActualizacion()))
                        res.setMensajeError(MensajesErrorEnum.ERROR_FECHA_UPDATE_REQ);

                    break;

                case ELIMINACION:

                    if (ObjectUtils.isNullOrEmpty(dto.getId()))
                        res.setMensajeError(MensajesErrorEnum.ERROR_ID_REQ);
                    if (ObjectUtils.isNull(dto.getFechaActualizacion()))
                        res.setMensajeError(MensajesErrorEnum.ERROR_FECHA_UPDATE_REQ);
                    break;

            }
        }
        return res;
    }

    @Override
    public void validarPersistencia(AsistenciaDTO dto, ResultadoDTO<AsistenciaDTO> resultado) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void validarActualizacion(AsistenciaDTO dto, ResultadoDTO<AsistenciaDTO> resultado) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void validarEliminacion(AsistenciaDTO dto, ResultadoDTO<AsistenciaDTO> resultado) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
