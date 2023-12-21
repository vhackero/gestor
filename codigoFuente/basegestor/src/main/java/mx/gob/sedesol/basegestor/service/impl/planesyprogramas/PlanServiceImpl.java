package mx.gob.sedesol.basegestor.service.impl.planesyprogramas;

import java.lang.reflect.Type;
import java.util.List;

import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.PlanDTO;
import mx.gob.sedesol.basegestor.commons.utils.MensajesSistemaEnum;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.TipoAccion;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.TblPlan;
import mx.gob.sedesol.basegestor.model.especificaciones.PlanEspecificacion;
import mx.gob.sedesol.basegestor.model.repositories.planesyprogramas.PlanRepo;
import mx.gob.sedesol.basegestor.service.admin.ComunValidacionService;
import mx.gob.sedesol.basegestor.service.planesyprogramas.PlanService;

@Service("planService")
public class PlanServiceImpl extends ComunValidacionService<PlanDTO> implements PlanService {

    private static final Logger logger = Logger.getLogger(PlanServiceImpl.class);
    private ModelMapper planMapper = new ModelMapper();

    @Autowired
    private PlanRepo planRepo;

    @Override
    public List<PlanDTO> findAll() {
        Type lstAux = new TypeToken<List<PlanDTO>>() {
        }.getType();
        return planMapper.map(planRepo.findAll(), lstAux);
    }

    @Override
    public PlanDTO buscarPorId(Integer id) {
        TblPlan plantEnt = planRepo.findOne(id);
        return planMapper.map(plantEnt, PlanDTO.class);

    }

    @Transactional(rollbackFor = Exception.class)
    public ResultadoDTO<PlanDTO> guardar(PlanDTO dto) {
        ResultadoDTO<PlanDTO> res = sonDatosRequeridosValidos(TipoAccion.PERSISTENCIA, dto);
        if (ObjectUtils.isNotNull(res) && res.getResultado().getValor()) {

            try {

                TblPlan planEnt = planMapper.map(dto, TblPlan.class);
                planEnt = planRepo.save(planEnt);
                res = new ResultadoDTO<PlanDTO>();
                res.setDto(planMapper.map(planEnt, PlanDTO.class));
        //GUSTAVO --guardarBitacora(dto.getBitacoraDTO(), String.valueOf(planEnt.getIdPlan()));
            } catch (Exception e) {
                res.setMensajeError(MensajesSistemaEnum.ADMIN_MSG_GUARDADO_FALLIDO);
                logger.error(e.getMessage(), e);
                throw e;
            }
        }
        return res;
    }

    @Transactional(rollbackFor = Exception.class)
    public ResultadoDTO<PlanDTO> actualizar(PlanDTO dto) {

        ResultadoDTO<PlanDTO> res = sonDatosRequeridosValidos(TipoAccion.ACTUALIZACION, dto);
        if (ObjectUtils.isNotNull(res) && res.getResultado().getValor()) {

            try {
                logger.error("Este es un error: "+dto.getCatTipoCompetencia().getNombre());
                TblPlan planEnt = planMapper.map(dto, TblPlan.class);
                logger.error("Este es un error2: "+planEnt.getCatTipoCompetencia().getNombre());
                planEnt = planRepo.saveAndFlush(planEnt);
                logger.error("Este es un error3: "+planEnt.getCatTipoCompetencia().getNombre());
                res = new ResultadoDTO<PlanDTO>();
                res.setDto(planMapper.map(planEnt, PlanDTO.class));
        //GUSTAVO --guardarBitacora(dto.getBitacoraDTO(), String.valueOf(planEnt.getIdPlan()));

            } catch (Exception e) {
                res.setMensajeError(MensajesSistemaEnum.ADMIN_MSG_ACTUALIZACION_FALLIDA);
                logger.error(e.getMessage(), e);
                throw e;
            }
        }
        return res;
    }

    @Transactional(rollbackFor = Exception.class)
    public ResultadoDTO<PlanDTO> eliminar(PlanDTO dto) {

        ResultadoDTO<PlanDTO> res = sonDatosRequeridosValidos(TipoAccion.ELIMINACION, dto);
        if (ObjectUtils.isNotNull(res) && res.getResultado().getValor()) {

            try {

                TblPlan planEnt = planMapper.map(dto, TblPlan.class);
                planRepo.delete(planEnt.getIdPlan());
                res = new ResultadoDTO<PlanDTO>();
        //GUSTAVO --guardarBitacora(dto.getBitacoraDTO(), String.valueOf(planEnt.getIdPlan()));

            } catch (Exception e) {
                res.setMensajeError(MensajesSistemaEnum.ADMIN_MSG_ELIMINACION_FALLIDA);
                logger.error(e.getMessage(), e);
                throw e;
            }
        }
        return res;

    }

    @Override
    public List<PlanDTO> buscaPlanesPorCriterios(PlanDTO filtro) {

        try {
            Type listAux = new TypeToken<List<PlanDTO>>() {
            }.getType();
            List<TblPlan> planes = planRepo.findAll(new PlanEspecificacion(filtro));

            return planMapper.map(planes, listAux);

        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    public void eliminaHabilidadesPorIdPlan(Integer idPlan) {
        planRepo.eliminaHabilidadesPorIdPlan(idPlan);
    }

    public void eliminaConocimientosPorIdPlan(@Param("idPlan") Integer idPlan) {
        planRepo.eliminaConocimientosPorIdPlan(idPlan);
    }

    public void eliminAptitudesPorIdPlan(@Param("idPlan") Integer idPlan) {
        planRepo.eliminAptitudesPorIdPlan(idPlan);
    }

    @Override
    public ResultadoDTO<PlanDTO> sonDatosRequeridosValidos(TipoAccion accion, PlanDTO dto) {

        ResultadoDTO<PlanDTO> resultado = null;

        if (ObjectUtils.isNotNull(dto)) {
            resultado = new ResultadoDTO<PlanDTO>();

            switch (accion) {
                case PERSISTENCIA:

                    if (ObjectUtils.isNullOrEmpty(dto.getFechaRegistro())) {
                        resultado.setMensajeError(MensajesSistemaEnum.MSG_GENERAL_FECHA_REGISTRO_REQ);
                    }
                    if (ObjectUtils.isNull(dto.getUsuarioModifico())) {
                        resultado.setMensajeError(MensajesSistemaEnum.MSG_GENERAL_USUARIO_MODIFICO_REQ);
                    }
                    break;

                case ELIMINACION:
                    if (ObjectUtils.isNull(dto.getIdPlan())) {
                        resultado.setMensajeError(MensajesSistemaEnum.FICHA_DESC_PROG_ID_REQ);
                    }
                    break;

                case ACTUALIZACION:

                    if (ObjectUtils.isNull(dto.getIdPlan())) {
                        resultado.setMensajeError(MensajesSistemaEnum.PLAN_ID_REQ);
                    }
//				if (ObjectUtils.isNull(dto.getFechaActualizacion())) {
//					resultado.setMensajeError(MensajesSistemaEnum.MSG_GENERAL_FECHA_EDICION_REQ);
//				}
                    if (ObjectUtils.isNull(dto.getUsuarioModifico())) {
                        resultado.setMensajeError(MensajesSistemaEnum.MSG_GENERAL_USUARIO_MODIFICO_REQ);
                    }
                    break;
            }
        }
        return resultado;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Integer obtieneUltimoIdPlanes() {
        return planRepo.obtieneUltimoIdPlanes();
    }

    @Override
    public void validarPersistencia(PlanDTO dto, ResultadoDTO<PlanDTO> resultado) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void validarActualizacion(PlanDTO dto, ResultadoDTO<PlanDTO> resultado) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void validarEliminacion(PlanDTO dto, ResultadoDTO<PlanDTO> resultado) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
