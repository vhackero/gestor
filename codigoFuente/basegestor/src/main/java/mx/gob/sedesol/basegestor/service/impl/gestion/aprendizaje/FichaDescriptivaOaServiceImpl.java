package mx.gob.sedesol.basegestor.service.impl.gestion.aprendizaje;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestion.aprendizaje.FichaDescripcionOaDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestion.aprendizaje.FichaDescriptivaOaDTO;
import mx.gob.sedesol.basegestor.commons.utils.MensajesErrorEnum;
import mx.gob.sedesol.basegestor.commons.utils.MensajesSistemaEnum;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.ResultadoTransaccionEnum;
import mx.gob.sedesol.basegestor.commons.utils.TipoAccion;
import mx.gob.sedesol.basegestor.model.entities.gestionaprendizaje.TblFichaDescripcionObjetoAprendizaje;
import mx.gob.sedesol.basegestor.model.entities.gestionaprendizaje.TblFichaDescriptivaObjetoAprendizaje;
import mx.gob.sedesol.basegestor.model.repositories.gestion.aprendizaje.FichaDescriptivaOaRepo;
import mx.gob.sedesol.basegestor.service.admin.ComunValidacionService;
import mx.gob.sedesol.basegestor.service.gestion.aprendizaje.FichaDescriptivaOaService;

@Service("fichaDescriptivaOaService")
public class FichaDescriptivaOaServiceImpl extends ComunValidacionService<FichaDescriptivaOaDTO> implements FichaDescriptivaOaService {

    private static final Logger logger = Logger.getLogger(FichaDescriptivaOaServiceImpl.class);

    @Autowired
    private FichaDescriptivaOaRepo fichaDescriptivaObjetoAprendizajeRepo;

    private ModelMapper fichaDecApeMapper = new ModelMapper();

    @PostConstruct
    public void init() {

        PropertyMap<FichaDescriptivaOaDTO, TblFichaDescriptivaObjetoAprendizaje> foaDescriptivaTblMap = new PropertyMap<FichaDescriptivaOaDTO, TblFichaDescriptivaObjetoAprendizaje>() {
            protected void configure() {
                skip().setFichaDescripcionOa(null);
            }
        };

        PropertyMap<FichaDescripcionOaDTO, TblFichaDescripcionObjetoAprendizaje> foaDescripcionTblMap = new PropertyMap<FichaDescripcionOaDTO, TblFichaDescripcionObjetoAprendizaje>() {
            protected void configure() {
                skip().setFichaDescriptivaOa(null);
            }
        };
        fichaDecApeMapper.addMappings(foaDescriptivaTblMap);
        fichaDecApeMapper.addMappings(foaDescripcionTblMap);
    }

    @Override
    public List<FichaDescriptivaOaDTO> findAll() {
        List<TblFichaDescriptivaObjetoAprendizaje> fichas = new ArrayList<TblFichaDescriptivaObjetoAprendizaje>();
        Type fichaDescriptivaObjetoAprendizajeDTO = null;
        fichaDescriptivaObjetoAprendizajeDTO = new TypeToken<List<FichaDescriptivaOaDTO>>() {
        }.getType();

        try {
            fichas = getFichaDescriptivaObjetoAprendizajeRepo().findAll();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("FichaDescriptivaOaServiceImpl:findAll:err: " + e);
        }
        return fichaDecApeMapper.map(fichas, fichaDescriptivaObjetoAprendizajeDTO);

    }

    @Override
    public FichaDescriptivaOaDTO buscarPorId(Integer id) {
        TblFichaDescriptivaObjetoAprendizaje entidad = new TblFichaDescriptivaObjetoAprendizaje();
        FichaDescriptivaOaDTO dto = new FichaDescriptivaOaDTO();
        entidad = fichaDescriptivaObjetoAprendizajeRepo.findOne(id);

        if (ObjectUtils.isNotNull(entidad)) {
            fichaDecApeMapper.map(entidad, dto);
        }
        return dto;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultadoDTO<FichaDescriptivaOaDTO> guardar(FichaDescriptivaOaDTO dto) {

        ResultadoDTO<FichaDescriptivaOaDTO> resultado
                = sonDatosRequeridosValidos(TipoAccion.PERSISTENCIA, dto);
        if (ObjectUtils.isNotNull(resultado) && resultado.getResultado().getValor()) {
            try {
                resultado = new ResultadoDTO<FichaDescriptivaOaDTO>();
                TblFichaDescriptivaObjetoAprendizaje entidad = new TblFichaDescriptivaObjetoAprendizaje();

                fichaDecApeMapper.map(dto, entidad);
                entidad.setFichaDescripcionOa(new TblFichaDescripcionObjetoAprendizaje());
                fichaDecApeMapper.map(dto.getFichaDescripcionOa(), entidad.getFichaDescripcionOa());
                entidad.getFichaDescripcionOa().setFichaDescriptivaOa(entidad);

                getFichaDescriptivaObjetoAprendizajeRepo().save(entidad);
                resultado.setDto(fichaDecApeMapper.map(entidad, FichaDescriptivaOaDTO.class));

            //GUSTAVO --guardarBitacoraFicha(dto, String.valueOf(entidad.getIdFoa()));

                resultado.agregaMensaje(MensajesSistemaEnum.ADMIN_MSG_GUARDADO_EXITOSO.getId());

            } catch (Exception e) {
                resultado.setResultado(ResultadoTransaccionEnum.FALLIDO);
                resultado.setMensajeError(MensajesSistemaEnum.ADMIN_MSG_GUARDADO_FALLIDO);
                logger.error("FichadescriptivaOaService:guardar:err :" + e);

            }
        }

        return resultado;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultadoDTO<FichaDescriptivaOaDTO> actualizar(FichaDescriptivaOaDTO dto) {
        ResultadoDTO<FichaDescriptivaOaDTO> resultado
                = sonDatosRequeridosValidos(TipoAccion.ACTUALIZACION, dto);
        if (ObjectUtils.isNotNull(resultado) && resultado.getResultado().getValor()) {
            try {
                resultado = new ResultadoDTO<FichaDescriptivaOaDTO>();
                TblFichaDescriptivaObjetoAprendizaje entidad = new TblFichaDescriptivaObjetoAprendizaje();

                fichaDecApeMapper.map(dto, entidad);
                entidad.setFichaDescripcionOa(new TblFichaDescripcionObjetoAprendizaje());
                fichaDecApeMapper.map(dto.getFichaDescripcionOa(), entidad.getFichaDescripcionOa());
                entidad.getFichaDescripcionOa().setFichaDescriptivaOa(entidad);

                getFichaDescriptivaObjetoAprendizajeRepo().saveAndFlush(entidad);
                resultado.setDto(fichaDecApeMapper.map(entidad, FichaDescriptivaOaDTO.class));

            //GUSTAVO --guardarBitacoraFicha(dto, String.valueOf(entidad.getIdFoa()));

                resultado.agregaMensaje(MensajesSistemaEnum.ADMIN_MSG_ACTUALIZACION_EXITOSA.getId());

            } catch (Exception e) {
                resultado.setResultado(ResultadoTransaccionEnum.FALLIDO);
                resultado.setMensajeError(MensajesSistemaEnum.ADMIN_MSG_ACTUALIZACION_FALLIDA);
                logger.error(e.getMessage(), e);

            }
        }

        return resultado;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultadoDTO<FichaDescriptivaOaDTO> eliminar(FichaDescriptivaOaDTO dto) {
        ResultadoDTO<FichaDescriptivaOaDTO> resultado
                = sonDatosRequeridosValidos(TipoAccion.ELIMINACION, dto);
        if (ObjectUtils.isNotNull(resultado) && resultado.getResultado().getValor()) {
            try {
                resultado = new ResultadoDTO<FichaDescriptivaOaDTO>();

                TblFichaDescriptivaObjetoAprendizaje entidad
                        = fichaDecApeMapper.map(dto, TblFichaDescriptivaObjetoAprendizaje.class);
                getFichaDescriptivaObjetoAprendizajeRepo().delete(entidad);
                resultado.setDto(fichaDecApeMapper.map(entidad, FichaDescriptivaOaDTO.class));
                resultado.setResultado(ResultadoTransaccionEnum.EXITOSO);

            //GUSTAVO --guardarBitacoraFicha(dto, String.valueOf(entidad.getIdFoa()));

                resultado.agregaMensaje(MensajesSistemaEnum.ADMIN_MSG_ELIMINACION_EXITOSA.getId());

            } catch (Exception e) {
                resultado.setResultado(ResultadoTransaccionEnum.FALLIDO);
                resultado.setMensajeError(MensajesSistemaEnum.ADMIN_MSG_ELIMINACION_FALLIDA);
                logger.error(e.getMessage(), e);
            }
        }

        return resultado;
    }

    @Override
    public ResultadoDTO<FichaDescriptivaOaDTO> sonDatosRequeridosValidos(TipoAccion accion,
            FichaDescriptivaOaDTO dto) {
        ResultadoDTO<FichaDescriptivaOaDTO> res = null;

        if (ObjectUtils.isNotNull(dto)) {
            res = new ResultadoDTO<FichaDescriptivaOaDTO>();

            switch (accion) {
                case PERSISTENCIA:

                    if (ObjectUtils.isNull(dto.getFechaRegistro())) {
                        res.setMensajeError(MensajesErrorEnum.ERROR_FECHA_REGISTRO_REQ);
                    }
                    if (ObjectUtils.isNullOrEmpty(dto.getUsuarioModifico())) {
                        res.setMensajeError(MensajesErrorEnum.ERROR_PERSONA_REQ);
                    }
                    break;

                case ACTUALIZACION:

                    if (ObjectUtils.isNullOrEmpty(dto.getIdFoa())) {
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

                    if (ObjectUtils.isNullOrEmpty(dto.getIdFoa())) {
                        res.setMensajeError(MensajesErrorEnum.ERROR_ID_REQ);
                    }
                    break;
            }
        }
        return res;
    }

    /**
     * @return the fichaDescriptivaObjetoAprendizajeRepo
     */
    public FichaDescriptivaOaRepo getFichaDescriptivaObjetoAprendizajeRepo() {
        return fichaDescriptivaObjetoAprendizajeRepo;
    }

    /**
     * @param fichaDescriptivaObjetoAprendizajeRepo the
     * fichaDescriptivaObjetoAprendizajeRepo to set
     */
    public void setFichaDescriptivaObjetoAprendizajeRepo(FichaDescriptivaOaRepo fichaDescriptivaObjetoAprendizajeRepo) {
        this.fichaDescriptivaObjetoAprendizajeRepo = fichaDescriptivaObjetoAprendizajeRepo;
    }


    @Override
    public void validarPersistencia(FichaDescriptivaOaDTO dto, ResultadoDTO<FichaDescriptivaOaDTO> resultado) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void validarActualizacion(FichaDescriptivaOaDTO dto, ResultadoDTO<FichaDescriptivaOaDTO> resultado) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void validarEliminacion(FichaDescriptivaOaDTO dto, ResultadoDTO<FichaDescriptivaOaDTO> resultado) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
