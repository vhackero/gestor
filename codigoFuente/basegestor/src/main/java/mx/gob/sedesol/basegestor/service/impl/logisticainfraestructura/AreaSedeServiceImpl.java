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
import mx.gob.sedesol.basegestor.commons.dto.logisticainfraestructura.AreaSedeDTO;
import mx.gob.sedesol.basegestor.commons.utils.MensajesErrorEnum;
import mx.gob.sedesol.basegestor.commons.utils.MensajesSistemaEnum;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.TipoAccion;
import mx.gob.sedesol.basegestor.model.entities.logisticainfraestructura.CatAreasSede;
import mx.gob.sedesol.basegestor.model.repositories.logisticainfraestructura.AreaSedeRepo;
import mx.gob.sedesol.basegestor.mongo.service.AdministradorBitacora;
import mx.gob.sedesol.basegestor.service.admin.ComunValidacionService;
import mx.gob.sedesol.basegestor.service.logisticainfraestructura.AreaSedeService;

@Service("areaSedeService")
public class AreaSedeServiceImpl extends ComunValidacionService<AreaSedeDTO> implements AreaSedeService {

    private Logger logger = Logger.getLogger(AreaSedeServiceImpl.class);

    @Autowired
    private AreaSedeRepo areaSedeRepo;

    private ModelMapper mapperAreaSede = new ModelMapper();

    /**
     *
     * @return
     */
    @Override
    public List<AreaSedeDTO> findAll() {
        Type listAux = new TypeToken<List<AreaSedeDTO>>() {
        }.getType();
        return mapperAreaSede.map(areaSedeRepo.findAll(), listAux);
    }

    /**
     *
     * @param id
     * @return
     */
    @Override
    public AreaSedeDTO buscarPorId(Integer id) {
        return mapperAreaSede.map(areaSedeRepo.findOne(id), AreaSedeDTO.class);
    }

    /**
     *
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultadoDTO<AreaSedeDTO> guardar(AreaSedeDTO dto) {

        ResultadoDTO<AreaSedeDTO> res = sonDatosRequeridosValidos(TipoAccion.PERSISTENCIA, dto);
        if (ObjectUtils.isNotNull(res) && res.getResultado().getValor()) {

            try {

                CatAreasSede as = mapperAreaSede.map(dto, CatAreasSede.class);
                as = areaSedeRepo.save(as);
                res.setDto(mapperAreaSede.map(as, AreaSedeDTO.class));

            //GUSTAVO --guardarBitacoraAreaSede(dto, String.valueOf(as.getIdAreaSede()));

            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                res.setMensajeError(MensajesSistemaEnum.ADMIN_MSG_GUARDADO_FALLIDO);
                throw e;
            }

        }
        return res;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultadoDTO<AreaSedeDTO> actualizar(AreaSedeDTO dto) {

        ResultadoDTO<AreaSedeDTO> res = sonDatosRequeridosValidos(TipoAccion.ACTUALIZACION, dto);
        if (ObjectUtils.isNotNull(res) && res.getResultado().getValor()) {

            try {

                CatAreasSede as = mapperAreaSede.map(dto, CatAreasSede.class);
                as = areaSedeRepo.saveAndFlush(as);
                res.setDto(mapperAreaSede.map(as, AreaSedeDTO.class));

            //GUSTAVO --guardarBitacoraAreaSede(dto, String.valueOf(as.getIdAreaSede()));

            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                res.setMensajeError(MensajesSistemaEnum.ADMIN_MSG_GUARDADO_FALLIDO);
                throw e;
            }

        }
        return res;
    }

    @Override
    public ResultadoDTO<AreaSedeDTO> eliminar(AreaSedeDTO dto) {
        return new ResultadoDTO<>();
    }

    @Override
    public void eliminaAreasSedePorIdSede(Integer idSede) {
        areaSedeRepo.eliminaAreasSedePorIdSede(idSede);
    }

    /**
     *
     */
    @Override
    public List<AreaSedeDTO> consultaAreasSedePorIdSede(Integer idSede) {

        List<CatAreasSede> res = areaSedeRepo.consultaAreasSedePorIdSede(idSede);
        if (!ObjectUtils.isNullOrEmpty(res)) {

            Type listAux = new TypeToken<List<AreaSedeDTO>>() {
            }.getType();
            return mapperAreaSede.map(res, listAux);
        }

        return new ArrayList<>();
    }

    /**
     *
     */
    @Override
    public List<AreaSedeDTO> consultaAreasSedePorIdArea(Integer idArea) {

        List<CatAreasSede> res = areaSedeRepo.consultaAreasSedePorIdArea(idArea);
        if (!ObjectUtils.isNullOrEmpty(res)) {
            Type listAux = new TypeToken<List<AreaSedeDTO>>() {
            }.getType();
            return mapperAreaSede.map(res, listAux);
        }

        return new ArrayList<>();
    }

    /**
     *
     */
    @Override
    public AreaSedeDTO consultaAreaSedePorIdSedeyIdArea(Integer idSede, Integer idArea) {

        CatAreasSede res = areaSedeRepo.consultaAreaSedePorIdSedeyIdArea(idSede, idArea);
        if (ObjectUtils.isNotNull(res)) {
            return mapperAreaSede.map(res, AreaSedeDTO.class);
        }

        return null;
    }

    /**
     *
     */
    @Override
    public ResultadoDTO<AreaSedeDTO> sonDatosRequeridosValidos(TipoAccion accion, AreaSedeDTO dto) {

        ResultadoDTO<AreaSedeDTO> res = null;
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

                    if (ObjectUtils.isNullOrEmpty(dto.getIdAreaSede())) {
                        res.setMensajeError(MensajesErrorEnum.ERROR_ID_REQ);
                    }

                    break;
            }
        }

        return res;
    }

    @Override
    public void validarActualizacion(AreaSedeDTO dto, ResultadoDTO<AreaSedeDTO> res) {
        if (ObjectUtils.isNullOrEmpty(dto.getIdAreaSede())) {
            res.setMensajeError(MensajesErrorEnum.ERROR_ID_REQ);
        }

        if (ObjectUtils.isNull(dto.getFechaActualizacion())) {
            res.setMensajeError(MensajesErrorEnum.ERROR_FECHA_UPDATE_REQ);
        }
    }

    @Override
    public void validarPersistencia(AreaSedeDTO dto, ResultadoDTO<AreaSedeDTO> res) {
        if (ObjectUtils.isNullOrEmpty(dto.getCatArea().getId())) {
            res.setMensajeError(MensajesErrorEnum.ERROR_ID_REQ);
        }

        if (ObjectUtils.isNull(dto.getCatSede().getIdSede())) {
            res.setMensajeError(MensajesErrorEnum.ERROR_ID_REQ);
        }

        if (ObjectUtils.isNull(dto.getCatSede().getFechaRegistro())) {
            res.setMensajeError(MensajesErrorEnum.ERROR_ID_REQ);
        }
    }


    @Override
    public void validarEliminacion(AreaSedeDTO dto, ResultadoDTO<AreaSedeDTO> resultado) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
