package mx.gob.sedesol.basegestor.service.impl.logisticainfraestructura;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.logisticainfraestructura.AreaSedeDTO;
import mx.gob.sedesol.basegestor.commons.dto.logisticainfraestructura.ConfiguracionAreaDTO;
import mx.gob.sedesol.basegestor.commons.dto.logisticainfraestructura.CritBusquedaAreasConfigDTO;
import mx.gob.sedesol.basegestor.commons.utils.MensajesErrorEnum;
import mx.gob.sedesol.basegestor.commons.utils.MensajesSistemaEnum;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.TipoAccion;
import mx.gob.sedesol.basegestor.model.entities.logisticainfraestructura.CatAreasSede;
import mx.gob.sedesol.basegestor.model.entities.logisticainfraestructura.TblConfiguracionArea;
import mx.gob.sedesol.basegestor.model.especificaciones.ConfigAreasInfEspecificacion;
import mx.gob.sedesol.basegestor.model.repositories.logisticainfraestructura.AreaSedeRepo;
import mx.gob.sedesol.basegestor.model.repositories.logisticainfraestructura.ConfiguracionAreaRepo;
import mx.gob.sedesol.basegestor.service.admin.ComunValidacionService;
import mx.gob.sedesol.basegestor.service.logisticainfraestructura.ConfiguracionAreaService;

@Service("configuracionAreaService")
public class ConfiguracionAreaServiceImpl extends ComunValidacionService<ConfiguracionAreaDTO> implements ConfiguracionAreaService {

    private static final Logger logger = Logger.getLogger(ConfiguracionAreaServiceImpl.class);

    @Autowired
    private ConfiguracionAreaRepo configuracionAreaRepo;

    @Autowired
    private AreaSedeRepo areaSedeRepo;

    private ModelMapper maperConfigArea = new ModelMapper();

    @Override
    public List<ConfiguracionAreaDTO> findAll() {

        Type lstAux = new TypeToken<List<ConfiguracionAreaDTO>>() {
        }.getType();
        return maperConfigArea.map(configuracionAreaRepo.findAll(sortByFechaActualizacionDesc()), lstAux);
    }

    private Sort sortByFechaActualizacionDesc() {
        return new Sort(Sort.Direction.DESC, "fechaActualizacion");
    }

    @Override
    public ConfiguracionAreaDTO buscarPorId(Integer id) {
        return maperConfigArea.map(configuracionAreaRepo.findOne(id), ConfiguracionAreaDTO.class);
    }

    /**
     *
     * @param dto
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultadoDTO<ConfiguracionAreaDTO> guardar(ConfiguracionAreaDTO dto) {

        ResultadoDTO<ConfiguracionAreaDTO> res = sonDatosRequeridosValidos(TipoAccion.PERSISTENCIA, dto);
        try {
            if (ObjectUtils.isNotNull(res) && res.getResultado().getValor()) {

                TblConfiguracionArea entity = maperConfigArea.map(dto, TblConfiguracionArea.class);
                entity.setFechaActualizacion(new Date());
                entity = configuracionAreaRepo.save(entity);
                res.setDto(maperConfigArea.map(entity, ConfiguracionAreaDTO.class));

                res.agregaMensaje(MensajesSistemaEnum.ADMIN_MSG_GUARDADO_EXITOSO.getId());
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            res.setMensajeError(MensajesSistemaEnum.ADMIN_MSG_GUARDADO_FALLIDO);
        }

        return res;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultadoDTO<ConfiguracionAreaDTO> actualizar(ConfiguracionAreaDTO dto) {

        ResultadoDTO<ConfiguracionAreaDTO> res = sonDatosRequeridosValidos(TipoAccion.ACTUALIZACION, dto);
        try {
            if (ObjectUtils.isNotNull(res) && res.getResultado().getValor()) {

                TblConfiguracionArea entity = maperConfigArea.map(dto, TblConfiguracionArea.class);
                entity = configuracionAreaRepo.saveAndFlush(entity);
                res.setDto(maperConfigArea.map(entity, ConfiguracionAreaDTO.class));

                res.setMensaje(MensajesSistemaEnum.ADMIN_MSG_ACTUALIZACION_EXITOSA.getId());
            }

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            res.setMensajeError(MensajesSistemaEnum.ADMIN_MSG_ACTUALIZACION_FALLIDA);
        }

        return res;
    }

    //TODO: Implementar metodo si se requiere eliminación NO Logica
    @Override
    public ResultadoDTO<ConfiguracionAreaDTO> eliminar(ConfiguracionAreaDTO dto) {
        return new ResultadoDTO<>();
    }

    /**
     *
     * @param criterios
     * @return
     */
    @Override
    public List<ConfiguracionAreaDTO> busquedaAreasConfiguradasCriterios(CritBusquedaAreasConfigDTO criterios) {

        ConfigAreasInfEspecificacion especifConfigArea = new ConfigAreasInfEspecificacion(criterios);
        List<TblConfiguracionArea> resultado = configuracionAreaRepo.findAll(especifConfigArea);
        Type lstAux = new TypeToken<List<ConfiguracionAreaDTO>>() {
        }.getType();

        return maperConfigArea.map(resultado, lstAux);

    }

    /**
     *
     */
    @Override
    public AreaSedeDTO consultaAreaSedePorIdSede(Integer idSede) {

        CatAreasSede areaSede = areaSedeRepo.findOne(idSede);
        ModelMapper mp = new ModelMapper();

        return mp.map(areaSede, AreaSedeDTO.class);
    }

    /**
     *
     * @param accion
     * @param dto
     * @return
     */
    @Override
    public ResultadoDTO<ConfiguracionAreaDTO> sonDatosRequeridosValidos(TipoAccion accion, ConfiguracionAreaDTO dto) {

        ResultadoDTO<ConfiguracionAreaDTO> res = null;
        if (ObjectUtils.isNotNull(dto)) {
            res = new ResultadoDTO<>();

            switch (accion) {
                case PERSISTENCIA:

                    validarPersistencia(dto, res);

                    break;

                case ACTUALIZACION:

                    validarActualizacion(dto, res);

                    break;

                case ELIMINACION:

                    validarEliminacion(dto, res);

                    break;
            }
        }

        return res;
    }

    @Override
    public void validarEliminacion(ConfiguracionAreaDTO dto, ResultadoDTO<ConfiguracionAreaDTO> res) {
        if (ObjectUtils.isNullOrEmpty(dto.getIdConfigArea())) {
            res.setMensajeError(MensajesErrorEnum.ERROR_ID_REQ);
        }
    }

    @Override
    public void validarActualizacion(ConfiguracionAreaDTO dto, ResultadoDTO<ConfiguracionAreaDTO> res) {
        validarEliminacion(dto, res);

        if (ObjectUtils.isNull(dto.getFechaActualizacion())) {
            res.setMensajeError(MensajesErrorEnum.ERROR_FECHA_UPDATE_REQ);
        }
    }

    @Override
    public void validarPersistencia(ConfiguracionAreaDTO dto, ResultadoDTO<ConfiguracionAreaDTO> res) {
        if (ObjectUtils.isNullOrEmpty(dto.getCatAreasSede().getIdAreaSede())) {
            res.setMensajeError(MensajesErrorEnum.ERROR_ID_REQ);
        }
    }

}
