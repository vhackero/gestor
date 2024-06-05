package mx.gob.sedesol.basegestor.service.impl.gestionescolar;

import java.lang.reflect.Type;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.RelEvaluacionCalificacionDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.RelGrupoEvaluacionDTO;
import mx.gob.sedesol.basegestor.commons.utils.MensajesSistemaEnum;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.TipoAccion;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.CatDictamen;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.CatTipoCalificacionEc;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.RelEvaluacionCalificacion;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.RelEvaluacionCalificacion2;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.RelGrupoEvaluacion;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.RelGrupoEvaluacion2;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.RelGrupoParticipante;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.RelGrupoParticipante2;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.RelPersonaResponsabilidades;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.TblEvento;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.TblGrupo;
import mx.gob.sedesol.basegestor.model.repositories.gestionescolar.RelEvaluacionCalificacion2Repo;
import mx.gob.sedesol.basegestor.model.repositories.gestionescolar.RelGpoEvaluacionRepo;
import mx.gob.sedesol.basegestor.model.repositories.gestionescolar.RelGrupoEvaluacion2Repo;
import mx.gob.sedesol.basegestor.model.repositories.gestionescolar.RelGrupoParticipante2Repo;
import mx.gob.sedesol.basegestor.service.admin.ComunValidacionService;
import mx.gob.sedesol.basegestor.service.gestionescolar.RelGpoEvaluacionService;

@Service("relGpoEvaluacionService")
public class RelGpoEvaluacionServiceImpl extends ComunValidacionService<RelGrupoEvaluacionDTO> implements RelGpoEvaluacionService {

    private static final Logger logger = Logger.getLogger(RelGpoEvaluacionServiceImpl.class);

	private static final RelGrupoEvaluacionDTO RelGrupoEvaluacion2DTO = null;

    @Autowired
    private RelGpoEvaluacionRepo relGpoEvaluacionRepo;
    
    
    
    @Autowired
    private RelGrupoParticipante2Repo relGrupoParticipante2Repo;    
    @Autowired
    private RelGrupoEvaluacion2Repo relGrupoEvaluacion2Repo;    
    @Autowired
    private RelEvaluacionCalificacion2Repo relEvaluacionCalificacion2Repo;
    
    

    private ModelMapper mapperGpoEval = new ModelMapper();
    private ModelMapper mapperDictamen = new ModelMapper();
    private ModelMapper mapperRelGrupoEval = new ModelMapper();
    private ModelMapper mapperRelGrupoPart = new ModelMapper();

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

//    @Override
//    public ResultadoDTO<RelGrupoEvaluacionDTO> guardar(RelGrupoEvaluacionDTO dto) {
//
//        ResultadoDTO<RelGrupoEvaluacionDTO> res = sonDatosRequeridosValidos(TipoAccion.PERSISTENCIA, dto);
//        if (ObjectUtils.isNotNull(res) && res.getResultado().getValor()) {
//
//        	try {
//
//                //RelGrupoEvaluacion entidad = mapperGpoEval.map(dto, RelGrupoEvaluacion.class);
//                
//            	RelGrupoEvaluacion entidad = new RelGrupoEvaluacion();
//            	
//            	//CatTipoCalificacionEc tipo = mapperGpoEval.map(dto.getCatTipoCalificacionEc(), CatTipoCalificacionEc.class);
//            	
//            	CatTipoCalificacionEc tipo = new CatTipoCalificacionEc();
//            	
//            	tipo.setActivo(dto.getCatTipoCalificacionEc().getActivo());
//            	tipo.setDescripcion(dto.getCatTipoCalificacionEc().getDescripcion());
//            	tipo.setFechaActualizacion(dto.getCatTipoCalificacionEc().getFechaActualizacion());
//            	tipo.setFechaRegistro(dto.getCatTipoCalificacionEc().getFechaRegistro());
//            	tipo.setId(dto.getCatTipoCalificacionEc().getId());
//            	tipo.setNombre(dto.getCatTipoCalificacionEc().getNombre());
//            	tipo.setOrden(dto.getCatTipoCalificacionEc().getOrden());
//            	BigInteger id = BigInteger.valueOf(dto.getCatTipoCalificacionEc().getUsuarioModifico());
//            	tipo.setUsuarioModifico(id);
//            	
//            	entidad.setCatTipoCalificacionEc(tipo);
//            	entidad.setFechaRegistro(dto.getFechaRegistro());
//            	entidad.setIdGpoEvaluacion(dto.getIdGpoEvaluacion());
//                entidad.setNombreEvaluacion(dto.getNombreEvaluacion());
//                entidad.setPonderacion(dto.getPonderacion());
//                
//                List<RelEvaluacionCalificacion> lista = new ArrayList<RelEvaluacionCalificacion>();
//                
//                List<RelEvaluacionCalificacionDTO> ls = new ArrayList<>();
//                ls.add(dto.getRelEvaluacionCalificaciones().get(0));
//                
//                for(RelEvaluacionCalificacionDTO calif : ls) {
//                	
//                	RelEvaluacionCalificacion eva = new RelEvaluacionCalificacion();
//                	
//                	eva.setCalificacion(calif.getCalificacion());                	
//                	
//                	CatDictamen dictamen = mapperDictamen.map(calif.getDictamen(), CatDictamen.class);
//                	eva.setDictamen(dictamen);
//                	
//                	eva.setFechaRegistro(calif.getRelGrupoEvaluacion().getFechaRegistro());
//                	
//                	eva.setIdEvalCalificacion(calif.getIdEvalCalificacion());
//                	
//                	
////                	RelGrupoEvaluacion relGrupo = mapperRelGrupoEval.map(calif.getRelGrupoEvaluacion(), RelGrupoEvaluacion.class);
////                	eva.setRelGrupoEvaluacion(relGrupo);
//
////                	RelGrupoEvaluacion relGrupo = new RelGrupoEvaluacion();
////                    relGrupo.setIdGpoEvaluacion(calif.getRelGrupoEvaluacion().getTblGrupo().getIdGrupo());
////
////                    eva.setRelGrupoEvaluacion(relGrupo);
//                   
//                	//SET PARTICIPANTE
//                	RelGrupoParticipante relPart = mapperRelGrupoPart.map(calif.getRelGrupoParticipante(), RelGrupoParticipante.class);
//                	eva.setRelGrupoParticipante(relPart);
//                	
//                	eva.setUsuarioModifico(calif.getUsuarioModifico());
//                	
//                	lista.add(eva);
//                	
//                }           
//           
//                entidad.setRelEvaluacionCalificaciones(lista);
//
//                //Se quito por que no lo realizaba bien
//                //List<RelEvaluacionCalificacion> lis = 
//                //dto.getRelEvaluacionCalificaciones().stream().map(RelEvaluacionCalificacionDTO -> listaM.map(RelEvaluacionCalificacionDTO, RelEvaluacionCalificacion.class)).collect(Collectors.toList());
//
//                //entidad.setRelEvaluacionCalificaciones(dto.getRelEvaluacionCalificaciones());
//                
//                TblGrupo grupo = mapperGpoEval.map(dto.getTblGrupo(), TblGrupo.class);
//                
//                entidad.setTblGrupo(grupo);
//                entidad.setUsuarioModifico(dto.getUsuarioModifico());
//                
//                
//                
//                entidad = relGpoEvaluacionRepo.save(entidad);
//                res.setDto(mapperGpoEval.map(entidad, RelGrupoEvaluacionDTO.class));
//        //GUSTAVO --guardarBitacora(dto.getBitacoraDTO(), String.valueOf(dto.getIdGpoEvaluacion()));
//                logger.info("-" );
//            } catch (Exception e) {
//                logger.error(e.getMessage(), e);
//                res.setMensajeError(MensajesSistemaEnum.ADMIN_MSG_GUARDADO_FALLIDO);
//            }
//        }
//
//        return res;
//    }
    
    @Override
    public ResultadoDTO<RelGrupoEvaluacionDTO> guardar(RelGrupoEvaluacionDTO dto) {

        ResultadoDTO<RelGrupoEvaluacionDTO> res = sonDatosRequeridosValidos(TipoAccion.PERSISTENCIA, dto);
           
        if (ObjectUtils.isNotNull(res) && res.getResultado().getValor()) {

        	try {

                //RelGrupoEvaluacion entidad = mapperGpoEval.map(dto, RelGrupoEvaluacion.class);
                
            	RelGrupoEvaluacion entidad = new RelGrupoEvaluacion();
            	
            	//CatTipoCalificacionEc tipo = mapperGpoEval.map(dto.getCatTipoCalificacionEc(), CatTipoCalificacionEc.class);
            	
            	CatTipoCalificacionEc tipo = new CatTipoCalificacionEc();
            	
            	tipo.setActivo(dto.getCatTipoCalificacionEc().getActivo());
            	tipo.setDescripcion(dto.getCatTipoCalificacionEc().getDescripcion());
            	tipo.setFechaActualizacion(dto.getCatTipoCalificacionEc().getFechaActualizacion());
            	tipo.setFechaRegistro(dto.getCatTipoCalificacionEc().getFechaRegistro());
            	tipo.setId(dto.getCatTipoCalificacionEc().getId());
            	tipo.setNombre(dto.getCatTipoCalificacionEc().getNombre());
            	tipo.setOrden(dto.getCatTipoCalificacionEc().getOrden());
            	BigInteger id = BigInteger.valueOf(dto.getCatTipoCalificacionEc().getUsuarioModifico());
            	tipo.setUsuarioModifico(id);
            	
            	entidad.setCatTipoCalificacionEc(tipo);
            	entidad.setFechaRegistro(dto.getFechaRegistro());
            	entidad.setIdGpoEvaluacion(dto.getIdGpoEvaluacion());
                entidad.setNombreEvaluacion(dto.getNombreEvaluacion());
                entidad.setPonderacion(dto.getPonderacion());
                
                List<RelEvaluacionCalificacion> lista = new ArrayList<RelEvaluacionCalificacion>();
                
                List<RelEvaluacionCalificacionDTO> ls = new ArrayList<>();
                ls.add(dto.getRelEvaluacionCalificaciones().get(0));
                
                for(RelEvaluacionCalificacionDTO calif : ls) {
                	
                	RelEvaluacionCalificacion eva = new RelEvaluacionCalificacion();
                	
                	eva.setCalificacion(calif.getCalificacion());                	
                	
                	CatDictamen dictamen = mapperDictamen.map(calif.getDictamen(), CatDictamen.class);
                	eva.setDictamen(dictamen);
                	
                	eva.setFechaRegistro(calif.getRelGrupoEvaluacion().getFechaRegistro());
                	
                	eva.setIdEvalCalificacion(calif.getIdEvalCalificacion());
                	
                	
//                	RelGrupoEvaluacion relGrupo = mapperRelGrupoEval.map(calif.getRelGrupoEvaluacion(), RelGrupoEvaluacion.class);
//                	eva.setRelGrupoEvaluacion(relGrupo);

//                	RelGrupoEvaluacion relGrupo = new RelGrupoEvaluacion();
//                    relGrupo.setIdGpoEvaluacion(calif.getRelGrupoEvaluacion().getTblGrupo().getIdGrupo());
//
//                    eva.setRelGrupoEvaluacion(relGrupo);
                   
                	//SET PARTICIPANTE
                	RelGrupoParticipante relPart = mapperRelGrupoPart.map(calif.getRelGrupoParticipante(), RelGrupoParticipante.class);
                	eva.setRelGrupoParticipante(relPart);
                	
                	eva.setUsuarioModifico(calif.getUsuarioModifico());
                	
                	lista.add(eva);
                	
                }           
           
                entidad.setRelEvaluacionCalificaciones(lista);

                //Se quito por que no lo realizaba bien
                //List<RelEvaluacionCalificacion> lis = 
                //dto.getRelEvaluacionCalificaciones().stream().map(RelEvaluacionCalificacionDTO -> listaM.map(RelEvaluacionCalificacionDTO, RelEvaluacionCalificacion.class)).collect(Collectors.toList());

                //entidad.setRelEvaluacionCalificaciones(dto.getRelEvaluacionCalificaciones());
                
                TblGrupo grupo = mapperGpoEval.map(dto.getTblGrupo(), TblGrupo.class);
                
                entidad.setTblGrupo(grupo);
                entidad.setUsuarioModifico(dto.getUsuarioModifico());
                
                
                
                entidad = relGpoEvaluacionRepo.save(entidad);
                res.setDto(mapperGpoEval.map(entidad, RelGrupoEvaluacionDTO.class));
        //GUSTAVO --guardarBitacora(dto.getBitacoraDTO(), String.valueOf(dto.getIdGpoEvaluacion()));
                
                
                
                
                
              //Rel grupo evaluacion
                
                RelGrupoEvaluacion2 relGrupoEvaluacion2 = new RelGrupoEvaluacion2();
                
                relGrupoEvaluacion2Repo.saveAndFlush(relGrupoEvaluacion2);
                
              //Rel grupo participante
                
                RelGrupoParticipante2 relGrupoParticipante2 = new RelGrupoParticipante2();
                
                relGrupoParticipante2Repo.saveAndFlush(relGrupoParticipante2);
                
              //Rel evaluaqcion calificacion
                
                RelEvaluacionCalificacion2 relEvaluacionCalificacion2 = new RelEvaluacionCalificacion2();
                
                
                
                relEvaluacionCalificacion2Repo.saveAndFlush(relEvaluacionCalificacion2);
                
  
                
                logger.info("-" );
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
    
    @Transactional
   	@Override
    public void eliminaEvaluacionesByIdGpoEvaluacion(Integer idGpoEval) throws Exception {
        try {
            relGpoEvaluacionRepo.eliminaEvaluacionesByIdGpoEvaluacion(idGpoEval);
    //GUSTAVO --guardarBitacora(dto.getBitacoraDTO(), String.valueOf(dto.getIdGpoEvaluacion()));

        } catch (Exception e) {
            logger.error(e.getMessage(), e); 
            throw e;
        }
    }
    
    @Transactional
	@Override
    public void eliminaGrupoEvaluacionByIdGrupo(Integer idGrupo) throws Exception {
        try {
            relGpoEvaluacionRepo.eliminaGrupoEvaluacionByIdGrupo(idGrupo);
        } catch (Exception e) {
        	logger.error(" ERRROR eliminaGrupoEvaluacionByIdGrupo>> "+e.getMessage(), e); 
            throw e;
        }
    }

    @Transactional
	@Override
    public void eliminaEvaluacionesByIdGrupo(Integer idGrupo) throws Exception {
        try {
            relGpoEvaluacionRepo.eliminaEvaluacionesByIdGrupo(idGrupo);
        } catch (Exception e) {
            logger.error(" ERRROR eliminaEvaluacionesByIdGrupo>> "+e.getMessage(), e); 
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

//    @Override
//    public void eliminaEvaluacionesByIdGpoEvaluacion(Integer idGpoEval) throws Exception {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }

}
