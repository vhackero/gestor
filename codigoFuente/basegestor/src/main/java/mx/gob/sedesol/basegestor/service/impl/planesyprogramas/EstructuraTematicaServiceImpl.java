package mx.gob.sedesol.basegestor.service.impl.planesyprogramas;

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
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.DetEtematicaTemaDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.EstructuraTematicaDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.RelEstUnidadDidacticaDTO;
import mx.gob.sedesol.basegestor.commons.utils.MensajesSistemaEnum;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.TipoAccion;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.DetEtematicaTema;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.RelEstructuraUnidadDidactica;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.TblEstructuraTematica;
import mx.gob.sedesol.basegestor.model.repositories.planesyprogramas.DetEtematicaTemaRepo;
import mx.gob.sedesol.basegestor.model.repositories.planesyprogramas.EstructuraTematicaRepo;
import mx.gob.sedesol.basegestor.model.repositories.planesyprogramas.RelEstructuraUDidacticaRepo;
import mx.gob.sedesol.basegestor.mongo.service.AdministradorBitacora;
import mx.gob.sedesol.basegestor.service.admin.ComunValidacionService;
import mx.gob.sedesol.basegestor.service.planesyprogramas.EstructuraTematicaService;

@Service("estructuraTematicaService")
public class EstructuraTematicaServiceImpl extends ComunValidacionService<EstructuraTematicaDTO> implements EstructuraTematicaService {

    private static final Logger logger = Logger.getLogger(EstructuraTematicaServiceImpl.class);

    @Autowired
    private EstructuraTematicaRepo estructuraTematicaRepo;

    private ModelMapper estTematicaMapper = new ModelMapper();

    @Autowired
    private DetEtematicaTemaRepo detEstTematicaTemaRepo;

    @Autowired
    private RelEstructuraUDidacticaRepo relEstUDidacticaRepo;

    @Override
    public List<EstructuraTematicaDTO> findAll() {

        List<TblEstructuraTematica> estTem = estructuraTematicaRepo.findAll();
        List<EstructuraTematicaDTO> lstAux = new ArrayList<>();

        if (!ObjectUtils.isNullOrEmpty(estTem)) {

            for (TblEstructuraTematica et : estTem) {
                et.setTblFichaDescriptivaPrograma(null);
                for (DetEtematicaTema det : et.getDetEtematicaTemas()) {
                    det.setTblEstructuraTematica(null);
                    if (!ObjectUtils.isNullOrEmpty(det.getRelEstructuraUnidadDidacticas())) {
                        for (RelEstructuraUnidadDidactica rel : det.getRelEstructuraUnidadDidacticas()) {
                            rel.setDetEtematicaTema(null);
                            rel.getDetEstUnidadDidactica().setRelEstructuraUnidadDidacticas(null);
                        }
                    }
                }

                EstructuraTematicaDTO dto = new EstructuraTematicaDTO();
                estTematicaMapper.map(et, dto);
                lstAux.add(dto);

            }
        }

        //return estTematicaMapper.map(estTem, lstEstTemAux);
        return lstAux;
    }

    @Override
    public EstructuraTematicaDTO buscarPorId(Integer id) {
        TblEstructuraTematica res = estructuraTematicaRepo.findOne(id);

        if (ObjectUtils.isNotNull(res)) {
            //res.setTblFichaDescriptivaPrograma(null);
            for (DetEtematicaTema et : res.getDetEtematicaTemas()) {
                et.setTblEstructuraTematica(null);
                if (!ObjectUtils.isNullOrEmpty(et.getRelEstructuraUnidadDidacticas())) {
                    for (RelEstructuraUnidadDidactica rel : et.getRelEstructuraUnidadDidacticas()) {
                        rel.setDetEtematicaTema(null);
                        rel.getDetEstUnidadDidactica().setRelEstructuraUnidadDidacticas(null);
                    }
                }
            }
        }

        EstructuraTematicaDTO obj = new EstructuraTematicaDTO();
        estTematicaMapper.map(res, obj);

        return obj;

    }

    /**
     *
     */
    @Transactional(rollbackFor = Exception.class)
    public ResultadoDTO<EstructuraTematicaDTO> guardar(EstructuraTematicaDTO dto) {

        ResultadoDTO<EstructuraTematicaDTO> r = sonDatosRequeridosValidos(TipoAccion.PERSISTENCIA, dto);
        if (ObjectUtils.isNotNull(r) && r.getResultado().getValor()) {

            try {
                TblEstructuraTematica estTem = estTematicaMapper.map(dto, TblEstructuraTematica.class);
                estTem = estructuraTematicaRepo.save(estTem);
                r = new ResultadoDTO<EstructuraTematicaDTO>();
                r.setDto(estTematicaMapper.map(estTem, EstructuraTematicaDTO.class));

            //GUSTAVO --guardarBitacoraEstructuraTematica(dto, String.valueOf(estTem.getIdEstructuraTematica()));

            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                r.setMensajeError(MensajesSistemaEnum.ADMIN_MSG_GUARDADO_FALLIDO);
                throw e;
            }
        }
        return r;
    }

    /**
     *
     */
    @Transactional(rollbackFor = Exception.class)
    public ResultadoDTO<EstructuraTematicaDTO> actualizar(EstructuraTematicaDTO dto) {
        ResultadoDTO<EstructuraTematicaDTO> r = sonDatosRequeridosValidos(TipoAccion.ACTUALIZACION, dto);
        if (ObjectUtils.isNotNull(r) && r.getResultado().getValor()) {

            try {
                TblEstructuraTematica estTem = estTematicaMapper.map(dto, TblEstructuraTematica.class);
                estTem = estructuraTematicaRepo.saveAndFlush(estTem);
                r = new ResultadoDTO<EstructuraTematicaDTO>();
                r.setDto(estTematicaMapper.map(estTem, EstructuraTematicaDTO.class));

            //GUSTAVO --guardarBitacoraEstructuraTematica(dto, String.valueOf(estTem.getIdEstructuraTematica()));

            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                r.setMensajeError(MensajesSistemaEnum.ADMIN_MSG_ACTUALIZACION_FALLIDA);
                throw e;
            }
        }
        return r;
    }

    /**
     *
     */
    public ResultadoDTO<EstructuraTematicaDTO> eliminar(EstructuraTematicaDTO dto) {
        ResultadoDTO<EstructuraTematicaDTO> r = sonDatosRequeridosValidos(TipoAccion.ELIMINACION, dto);
        if (ObjectUtils.isNotNull(r) && r.getResultado().getValor()) {

            try {
                TblEstructuraTematica estTem = estTematicaMapper.map(dto, TblEstructuraTematica.class);
                estructuraTematicaRepo.delete(estTem);
                r = new ResultadoDTO<EstructuraTematicaDTO>();
            //GUSTAVO --guardarBitacoraEstructuraTematica(dto, String.valueOf(estTem.getIdEstructuraTematica()));

            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                r.setMensajeError(MensajesSistemaEnum.ADMIN_MSG_ELIMINACION_FALLIDA);
                throw e;
            }
        }
        return r;
    }

    /**
     *
     */
    public DetEtematicaTemaDTO obtieneDetEtematicaTema(Integer idEstTematica, String nombreTema) {

        if (nombreTema.isEmpty()) {
            return null;
        }
        try {
            DetEtematicaTema entity = detEstTematicaTemaRepo.buscarTemaPorNombreEIdEstTematica(idEstTematica, nombreTema.trim());
            if (ObjectUtils.isNotNull(entity)) {
                entity.getTblEstructuraTematica();
                if (ObjectUtils.isNullOrEmpty(entity.getRelEstructuraUnidadDidacticas())) {
                    entity.setRelEstructuraUnidadDidacticas(null);
                }

                return estTematicaMapper.map(entity, DetEtematicaTemaDTO.class);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     *
     */
    public List<RelEstUnidadDidacticaDTO> obtieneRelacionesPorEstTematica(Integer idTema) {
        //Type lstAux = new TypeToken<List<RelEstUnidadDidacticaDTO>>(){}.getType();
        List<RelEstUnidadDidacticaDTO> res = null;
        List<RelEstructuraUnidadDidactica> lstEntities = relEstUDidacticaRepo.obtieneRelacionesPorEstTematica(idTema);
        if (!ObjectUtils.isNullOrEmpty(lstEntities)) {
            res = new ArrayList<>();
            for (RelEstructuraUnidadDidactica entity : lstEntities) {
                RelEstUnidadDidacticaDTO dto = new RelEstUnidadDidacticaDTO();
                estTematicaMapper.map(entity, dto);
                res.add(dto);
            }
        }

        //return estTematicaMapper.map(lstEntities, lstAux);
        return res;
    }

    /**
     *
     */
    public EstructuraTematicaDTO obtieneEstTematicaTemaPorPrograma(Integer idPrograma) {
        TblEstructuraTematica entity = estructuraTematicaRepo.buscarEstTematicaPorIdPrograma(idPrograma);
        if (ObjectUtils.isNotNull(entity)) {
            return estTematicaMapper.map(entity, EstructuraTematicaDTO.class);
        }

        return null;
    }

    /**
     *
     */
    public List<RelEstUnidadDidacticaDTO> obtieneRelEstUnidadDidPorPrograma(Integer idPrograma) {
        Type lstAux = new TypeToken<List<RelEstUnidadDidacticaDTO>>() {
        }.getType();
        List<RelEstructuraUnidadDidactica> relEstUD = relEstUDidacticaRepo.obtieneRelEstUnidadDidPorPrograma(idPrograma);

        return estTematicaMapper.map(relEstUD, lstAux);
    }

    @Override
    public List<RelEstUnidadDidacticaDTO> obtieneEstUnidadesPorIdPrograma(Integer idPrograma) {
        Type lstAux = new TypeToken<List<RelEstUnidadDidacticaDTO>>() {
        }.getType();
        List<RelEstructuraUnidadDidactica> relEstUD = relEstUDidacticaRepo.obtieneRelEstUnidadDidPorProgramaJoin(idPrograma);

        for (RelEstructuraUnidadDidactica relEstructuraUnidadDidactica : relEstUD) {

            relEstructuraUnidadDidactica.setDetEtematicaTema(null);

            if (ObjectUtils.isNotNull(relEstructuraUnidadDidactica.getDetEstUnidadDidactica())) {
                relEstructuraUnidadDidactica.getDetEstUnidadDidactica().setRelEstructuraUnidadDidacticas(null);
                relEstructuraUnidadDidactica.getDetEstUnidadDidactica().setRelEstructuraUnidadDidacticas(null);
                relEstructuraUnidadDidactica.getDetEstUnidadDidactica().setRelUnidadOaAvas(null);
                relEstructuraUnidadDidactica.getDetEstUnidadDidactica().setRelUniDidacticaMaterial(null);
                relEstructuraUnidadDidactica.getDetEstUnidadDidactica().setSubtemasUdidactica(null);
            }

        }

        return estTematicaMapper.map(relEstUD, lstAux);
    }

    @Override
    public ResultadoDTO<EstructuraTematicaDTO> sonDatosRequeridosValidos(TipoAccion accion, EstructuraTematicaDTO dto) {

        ResultadoDTO<EstructuraTematicaDTO> resultado = null;

        if (ObjectUtils.isNotNull(dto)) {
            resultado = new ResultadoDTO<EstructuraTematicaDTO>();

            switch (accion) {
                case PERSISTENCIA:

                    if (ObjectUtils.isNull(dto.getFechaRegistro())) {
                        resultado.setMensajeError(MensajesSistemaEnum.MSG_GENERAL_FECHA_REGISTRO_REQ);
                    }
                    break;

                case ELIMINACION:
                    if (ObjectUtils.isNull(dto.getIdEstructuraTematica())) {
                        resultado.setMensajeError(MensajesSistemaEnum.EST_TEMATICA_ID_REQ);
                    }
                    break;

                case ACTUALIZACION:

                    if (ObjectUtils.isNull(dto.getIdEstructuraTematica())) {
                        resultado.setMensajeError(MensajesSistemaEnum.EST_TEMATICA_ID_REQ);
                    }
                    if (ObjectUtils.isNull(dto.getFechaActualizacion())) {
                        resultado.setMensajeError(MensajesSistemaEnum.MSG_GENERAL_FECHA_EDICION_REQ);
                    }
                    if (ObjectUtils.isNull(dto.getUsuarioModifico())) {
                        resultado.setMensajeError(MensajesSistemaEnum.MSG_GENERAL_USUARIO_MODIFICO_REQ);
                    }
                    break;
            }
        }
        return resultado;
    }

    @Override
    public EstructuraTematicaDTO obtenerEstructuraTematicaPorId(Integer id_est_tematica) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void validarPersistencia(EstructuraTematicaDTO dto, ResultadoDTO<EstructuraTematicaDTO> resultado) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void validarActualizacion(EstructuraTematicaDTO dto, ResultadoDTO<EstructuraTematicaDTO> resultado) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void validarEliminacion(EstructuraTematicaDTO dto, ResultadoDTO<EstructuraTematicaDTO> resultado) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
