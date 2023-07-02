package mx.gob.sedesol.basegestor.service.impl.gestionescolar;

import java.lang.reflect.Type;
import java.util.List;

import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.RelEvaluacionCalificacionDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.RelGrupoEvaluacionDTO;
import mx.gob.sedesol.basegestor.commons.utils.MensajesSistemaEnum;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.TipoAccion;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.RelGrupoEvaluacion;
import mx.gob.sedesol.basegestor.model.repositories.gestionescolar.RelGpoEvaluacionRepo;
import mx.gob.sedesol.basegestor.service.admin.ComunValidacionService;
import mx.gob.sedesol.basegestor.service.gestionescolar.RelGpoEvaluacionService;

@Service("relGpoEvaluacionService")
public class RelGpoEvaluacionServiceImpl extends ComunValidacionService<RelGrupoEvaluacionDTO> implements RelGpoEvaluacionService {

    private static final Logger logger = Logger.getLogger(RelGpoEvaluacionServiceImpl.class);

    @Autowired
    private RelGpoEvaluacionRepo relGpoEvaluacionRepo;

    private ModelMapper mapperGpoEval = new ModelMapper();

    @Override
    public List<RelGrupoEvaluacionDTO> findAll() {
        Type lstAux = new TypeToken<List<RelGrupoEvaluacionDTO>>() {
        }.getType();
        List<RelGrupoEvaluacion> entidades = relGpoEvaluacionRepo.findAll();
        return mapperGpoEval.map(entidades, lstAux);

    }

    @Override
    public RelGrupoEvaluacionDTO buscarPorId(Integer id) {

        return mapperGpoEval.map(relGpoEvaluacionRepo.findOne(id), RelGrupoEvaluacionDTO.class);
    }

    @Override
    public ResultadoDTO<RelGrupoEvaluacionDTO> guardar(RelGrupoEvaluacionDTO dto) {

        ResultadoDTO<RelGrupoEvaluacionDTO> res = sonDatosRequeridosValidos(TipoAccion.PERSISTENCIA, dto);
        if (ObjectUtils.isNotNull(res) && res.getResultado().getValor()) {

            try {

                RelGrupoEvaluacion entidad = mapperGpoEval.map(dto, RelGrupoEvaluacion.class);
                entidad = relGpoEvaluacionRepo.save(entidad);
                res.setDto(mapperGpoEval.map(entidad, RelGrupoEvaluacionDTO.class));
        //GUSTAVO --guardarBitacora(dto.getBitacoraDTO(), String.valueOf(dto.getIdGpoEvaluacion()));

            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                res.setMensajeError(MensajesSistemaEnum.ADMIN_MSG_GUARDADO_FALLIDO);
            }
        }

        return res;
    }

    @Override
    public ResultadoDTO<RelGrupoEvaluacionDTO> actualizar(RelGrupoEvaluacionDTO dto) {

        ResultadoDTO<RelGrupoEvaluacionDTO> res = sonDatosRequeridosValidos(TipoAccion.ACTUALIZACION, dto);
        if (ObjectUtils.isNotNull(res) && res.getResultado().getValor()) {

            try {

//				PropertyMap<RelGrupoEvaluacionDTO, RelGrupoEvaluacion> relGpoEval = new PropertyMap<RelGrupoEvaluacionDTO, RelGrupoEvaluacion>() {
//					protected void configure() {
//						skip().getTblGrupo().setEvento(null);
//					}
//				};
//				
//				mapperGpoEval.addMappings(relGpoEval);
                //RelGrupoEvaluacion entidad = new RelGrupoEvaluacion();
                RelGrupoEvaluacion entidad = mapperGpoEval.map(dto, RelGrupoEvaluacion.class);
                //mapperGpoEval.map(dto, entidad);
                entidad = relGpoEvaluacionRepo.saveAndFlush(entidad);
                res.setDto(mapperGpoEval.map(entidad, RelGrupoEvaluacionDTO.class));

        //GUSTAVO --guardarBitacora(dto.getBitacoraDTO(), String.valueOf(dto.getIdGpoEvaluacion()));

            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                res.setMensajeError(MensajesSistemaEnum.ADMIN_MSG_ACTUALIZACION_FALLIDA);
            }
        }

        return res;
    }

    @Override
    public ResultadoDTO<RelGrupoEvaluacionDTO> eliminar(RelGrupoEvaluacionDTO dto) {

        ResultadoDTO<RelGrupoEvaluacionDTO> res = sonDatosRequeridosValidos(TipoAccion.ELIMINACION, dto);
        if (ObjectUtils.isNotNull(res) && res.getResultado().getValor()) {

            try {
                relGpoEvaluacionRepo.delete(dto.getIdGpoEvaluacion());
        //GUSTAVO --guardarBitacora(dto.getBitacoraDTO(), String.valueOf(dto.getIdGpoEvaluacion()));
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                res.setMensajeError(MensajesSistemaEnum.ADMIN_MSG_ELIMINACION_FALLIDA);
            }

        }

        return res;
    }

    public void eliminaEvaluacionesByIdGpoEvaluacion(RelGrupoEvaluacionDTO dto) throws Exception {
        try {
            relGpoEvaluacionRepo.eliminaEvaluacionesByIdGpoEvaluacion(dto.getIdGpoEvaluacion());
    //GUSTAVO --guardarBitacora(dto.getBitacoraDTO(), String.valueOf(dto.getIdGpoEvaluacion()));

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw e;
        }
    }

    /**
     *
     */
    public List<RelGrupoEvaluacionDTO> obtieneEvaluacionesPorIdGrupo(Integer idGpo) {

        Type lstAux = new TypeToken<List<RelGrupoEvaluacionDTO>>() {
        }.getType();

        List<RelGrupoEvaluacion> gposAux = relGpoEvaluacionRepo.obtieneEvaluacionesPorIdGrupo(idGpo);
        if (ObjectUtils.isNotNull(gposAux)) {
            return mapperGpoEval.map(gposAux, lstAux);
        }

        return null;
    }

    /**
     *
     */
    public List<RelEvaluacionCalificacionDTO> obtieneEvaluacionesByIdGpoEval(Integer idGpoEval) {

        Type lstAux = new TypeToken<List<RelGrupoEvaluacionDTO>>() {
        }.getType();
        List<RelEvaluacionCalificacionDTO> res = relGpoEvaluacionRepo.obtieneEvaluacionesByIdGpoEval(idGpoEval);
        if (ObjectUtils.isNotNull(res)) {
            return mapperGpoEval.map(res, lstAux);
        }

        return null;
    }

    @Override
    public void validarPersistencia(RelGrupoEvaluacionDTO dto, ResultadoDTO<RelGrupoEvaluacionDTO> resultado) {

        if (ObjectUtils.isNullOrEmpty(dto.getNombreEvaluacion())) {
            resultado.setMensajeError(MensajesSistemaEnum.MSG_GENERAL_NOMBRE_REQ);
        }
        if (ObjectUtils.isNull(dto.getFechaRegistro())) {
            resultado.setMensajeError(MensajesSistemaEnum.MSG_GENERAL_FECHA_REGISTRO_REQ);
        }
        if (ObjectUtils.isNullOrCero(dto.getUsuarioModifico())) {
            resultado.setMensajeError(MensajesSistemaEnum.MSG_GENERAL_USUARIO_MODIFICO_REQ);
        }

    }

    @Override
    public void validarActualizacion(RelGrupoEvaluacionDTO dto, ResultadoDTO<RelGrupoEvaluacionDTO> resultado) {
        // TODO Auto-generated method stub
        if (ObjectUtils.isNullOrEmpty(dto.getIdGpoEvaluacion())) {
            resultado.setMensajeError(MensajesSistemaEnum.MSG_GENERAL_CLAVE_REQ);
        }
        if (ObjectUtils.isNull(dto.getFechaRegistro())) {
            resultado.setMensajeError(MensajesSistemaEnum.MSG_GENERAL_FECHA_REGISTRO_REQ);
        }
        if (ObjectUtils.isNullOrCero(dto.getUsuarioModifico())) {
            resultado.setMensajeError(MensajesSistemaEnum.MSG_GENERAL_USUARIO_MODIFICO_REQ);
        }
    }

    @Override
    public void validarEliminacion(RelGrupoEvaluacionDTO dto, ResultadoDTO<RelGrupoEvaluacionDTO> resultado) {

        if (ObjectUtils.isNullOrEmpty(dto.getIdGpoEvaluacion())) {
            resultado.setMensajeError(MensajesSistemaEnum.MSG_GENERAL_CLAVE_REQ);
        }

    }

    @Override
    public void eliminaEvaluacionesByIdGpoEvaluacion(Integer idGpoEval) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
