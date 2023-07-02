package mx.gob.sedesol.basegestor.service.impl.gestion.aprendizaje;

import java.lang.reflect.Type;
import java.util.List;
import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestion.aprendizaje.AmbienteVirtualAprendizajeDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestion.aprendizaje.UnidadOaAvaDTO;
import mx.gob.sedesol.basegestor.commons.utils.MensajesSistemaEnum;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.ResultadoTransaccionEnum;
import mx.gob.sedesol.basegestor.commons.utils.TipoAccion;
import mx.gob.sedesol.basegestor.model.entities.gestionaprendizaje.RelReponsableProduccionOa;
import mx.gob.sedesol.basegestor.model.entities.gestionaprendizaje.RelUnidadOaAva;
import mx.gob.sedesol.basegestor.model.entities.gestionaprendizaje.TblCargaArchivosOa;
import mx.gob.sedesol.basegestor.model.repositories.gestion.aprendizaje.RelUnidadOaAvaRepo;
import mx.gob.sedesol.basegestor.service.admin.ComunValidacionService;
import mx.gob.sedesol.basegestor.service.gestion.aprendizaje.AmbienteVirtualApService;
import mx.gob.sedesol.basegestor.service.gestion.aprendizaje.RelUnidadOaAvaService;
import mx.gob.sedesol.basegestor.service.impl.planesyprogramas.EstructuraTematicaServiceImpl;

@Service("relUnidadOaAvaService")
public class RelUnidadOaAvaServiceImpl extends ComunValidacionService<UnidadOaAvaDTO> implements RelUnidadOaAvaService {

    private static final Logger logger = Logger.getLogger(EstructuraTematicaServiceImpl.class);

    /**
     * Inyeccion de rel unidad oa ava repo
     */
    @Autowired
    private RelUnidadOaAvaRepo relUnidadOaAvaRepo;

    /**
     * Inyeccion de ambiente virtual Repo
     */
    @Autowired
    private AmbienteVirtualApService ambienteVirtualApService;

    private ModelMapper relUnidadOaAvaMapper = new ModelMapper();

    @Override
    public List<UnidadOaAvaDTO> findAll() {

        Type listAux = new TypeToken<List<UnidadOaAvaDTO>>() {
        }.getType();

        List<RelUnidadOaAva> relUnidadOaAvas
                = relUnidadOaAvaRepo.findAll();

        limpiarRelacionesEntidades(relUnidadOaAvas);

        return relUnidadOaAvaMapper.map(relUnidadOaAvas, listAux);

    }

    @Override
    public List<UnidadOaAvaDTO> findByIdAva(Integer idAva) {

        Type listAux = new TypeToken<List<UnidadOaAvaDTO>>() {
        }.getType();

        List<RelUnidadOaAva> relUnidadOaAvas
                = relUnidadOaAvaRepo.findByIdAva(idAva);

        limpiarRelacionesEntidades(relUnidadOaAvas);

        return relUnidadOaAvaMapper.map(relUnidadOaAvas, listAux);
    }

    private void limpiarRelacionesEntidades(List<RelUnidadOaAva> relUnidadOaAvas) {

        for (RelUnidadOaAva relUnidadOaAva : relUnidadOaAvas) {
            for (TblCargaArchivosOa tblCargaArchivosOa : relUnidadOaAva.getCargaArchivosOas()) {
                tblCargaArchivosOa.setUnidadOaAva(null);
            }
            relUnidadOaAva.setRecursosOas(null);

            if (ObjectUtils.isNotNull(relUnidadOaAva.getDetEstUnidadDidactica())) {
                relUnidadOaAva.getDetEstUnidadDidactica().setRelEstructuraUnidadDidacticas(null);
                relUnidadOaAva.getDetEstUnidadDidactica().setRelUDidacticaTposCompetencia(null);
                relUnidadOaAva.getDetEstUnidadDidactica().setRelUnidadOaAvas(null);
                relUnidadOaAva.getDetEstUnidadDidactica().setRelUniDidacticaMaterial(null);
                //	relUnidadOaAva.getDetEstUnidadDidactica().setSubtemasUdidactica(null);
            }
            if (ObjectUtils.isNotNull(relUnidadOaAva.getReponsableProduccionOas())) {

                for (RelReponsableProduccionOa relReponsableProduccionOa : relUnidadOaAva.getReponsableProduccionOas()) {
                    relReponsableProduccionOa.setUnidadOaAva(null);
                    logger.info(relReponsableProduccionOa.getPersonaResponsabilidade().getCatTipoResponsabilidadEc());
                    relReponsableProduccionOa.getPersonaResponsabilidade().setRelReponsableProduccionOas(null);
                    relReponsableProduccionOa.getPersonaResponsabilidade().setRelReponsableProduccionEcs(null);

                }
            }
            if (ObjectUtils.isNotNull(relUnidadOaAva.getAmbienteVirtualAprendizaje())) {
                relUnidadOaAva.getAmbienteVirtualAprendizaje().setRelUnidadOaAvas(null);
            }
        }
    }

    /**
     *
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResultadoDTO<UnidadOaAvaDTO> guardar(UnidadOaAvaDTO dto) {

        ResultadoDTO<UnidadOaAvaDTO> resultado = sonDatosRequeridosValidos(TipoAccion.PERSISTENCIA, dto);
        if (ObjectUtils.isNotNull(resultado) && resultado.getResultado().getValor()) {

            try {
                RelUnidadOaAva relUnidadOaAva = relUnidadOaAvaMapper.map(dto, RelUnidadOaAva.class);
                relUnidadOaAva = relUnidadOaAvaRepo.save(relUnidadOaAva);
                resultado = new ResultadoDTO<UnidadOaAvaDTO>();
                resultado.setDto(relUnidadOaAvaMapper.map(relUnidadOaAva, UnidadOaAvaDTO.class));

        //GUSTAVO --guardarBitacora(dto.getBitacoraDTO(), String.valueOf(relUnidadOaAva.getId()));

            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                resultado.setMensajeError(MensajesSistemaEnum.ADMIN_MSG_GUARDADO_FALLIDO);
                throw e;
            }
        }
        return resultado;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultadoDTO<UnidadOaAvaDTO> actualizar(UnidadOaAvaDTO dto) {
        ResultadoDTO<UnidadOaAvaDTO> rs = sonDatosRequeridosValidos(TipoAccion.ACTUALIZACION, dto);
        if (ObjectUtils.isNotNull(rs) && rs.getResultado().getValor()) {
            try {

                RelUnidadOaAva relUnidadOaAva = new RelUnidadOaAva();

                relUnidadOaAvaMapper.map(dto, relUnidadOaAva);

                relUnidadOaAva = relUnidadOaAvaRepo.saveAndFlush(relUnidadOaAva);
                rs = new ResultadoDTO<UnidadOaAvaDTO>();

                UnidadOaAvaDTO unidadOaAvaDTO = new UnidadOaAvaDTO();

                relUnidadOaAvaMapper.map(relUnidadOaAva, unidadOaAvaDTO);
                rs.setDto(unidadOaAvaDTO);

        //GUSTAVO --guardarBitacora(dto.getBitacoraDTO(), String.valueOf(relUnidadOaAva.getId()));

            } catch (Exception e) {
                rs.setMensajeError(MensajesSistemaEnum.ADMIN_MSG_ACTUALIZACION_FALLIDA);
                logger.error(e.getMessage(), e);
            }
        }
        return rs;
    }

    @Transactional(rollbackFor = Exception.class)
    public ResultadoDTO<UnidadOaAvaDTO> actualizarUnidadYAmbientevirtualAprendizaje(UnidadOaAvaDTO unidadOaAvaDTO,
            AmbienteVirtualAprendizajeDTO ambienteVirtualAprendizajeDTO) {
        ResultadoDTO<UnidadOaAvaDTO> resultadoActualizarUnidadOa = null;

        logger.info("Se va a actualiza la unidad OA y el Ambiente virtual de aprendizaje");

        resultadoActualizarUnidadOa
                = this.actualizar(unidadOaAvaDTO);

        ResultadoDTO<AmbienteVirtualAprendizajeDTO> resultadoActualizarAVA
                = ambienteVirtualApService.actualizar(ambienteVirtualAprendizajeDTO);

        if (!resultadoActualizarUnidadOa.esCorrecto() || !resultadoActualizarAVA.esCorrecto()) {
            resultadoActualizarUnidadOa.setResultado(ResultadoTransaccionEnum.FALLIDO);
            resultadoActualizarUnidadOa.setMensajeError(MensajesSistemaEnum.ADMIN_MSG_ACTUALIZACION_FALLIDA);
        }

        logger.info("El resultado de la transaccion de la actualizacion fue " + resultadoActualizarUnidadOa.getResultado());
        return resultadoActualizarUnidadOa;
    }

    @Override
    public ResultadoDTO<UnidadOaAvaDTO> sonDatosRequeridosValidos(TipoAccion accion, UnidadOaAvaDTO dto) {

        ResultadoDTO<UnidadOaAvaDTO> resultado = null;

        if (ObjectUtils.isNotNull(dto)) {
            resultado = new ResultadoDTO<UnidadOaAvaDTO>();

            switch (accion) {
                case PERSISTENCIA:

                    if (ObjectUtils.isNull(dto.getFechaRegistro())) {
                        resultado.setMensajeError(MensajesSistemaEnum.MSG_GENERAL_FECHA_REGISTRO_REQ);
                    }
                    if (ObjectUtils.isNull(dto.getUsuarioModifico())) {
                        resultado.setMensajeError(MensajesSistemaEnum.MSG_GENERAL_USUARIO_MODIFICO_REQ);
                    }

                    break;

                case ELIMINACION:
                    if (ObjectUtils.isNull(dto.getId())) {
                        resultado.setMensajeError(MensajesSistemaEnum.MSG_GENERAL_ID_REQ);
                    }
                    break;

                case ACTUALIZACION:

                    if (ObjectUtils.isNull(dto.getId())) {
                        resultado.setMensajeError(MensajesSistemaEnum.MSG_GENERAL_ID_REQ);
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
    public void validarPersistencia(UnidadOaAvaDTO dto, ResultadoDTO<UnidadOaAvaDTO> resultado) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void validarActualizacion(UnidadOaAvaDTO dto, ResultadoDTO<UnidadOaAvaDTO> resultado) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void validarEliminacion(UnidadOaAvaDTO dto, ResultadoDTO<UnidadOaAvaDTO> resultado) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
