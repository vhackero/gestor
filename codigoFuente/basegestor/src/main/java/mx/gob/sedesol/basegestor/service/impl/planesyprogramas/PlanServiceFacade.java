package mx.gob.sedesol.basegestor.service.impl.planesyprogramas;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import mx.gob.sedesol.basegestor.commons.constantes.ConstantesBitacora;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import mx.gob.sedesol.basegestor.commons.constantes.ConstantesGestor;
import mx.gob.sedesol.basegestor.commons.dto.admin.CatalogoComunDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.ParametroWSMoodleDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.MallaCurricularDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.PlanDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.RelPlanAptitudDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.RelPlanConocimientoDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.RelPlanHabilidadDTO;
import mx.gob.sedesol.basegestor.commons.utils.MensajesSistemaEnum;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.ObjetoCurricularEnum;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.CatObjetoCurricular;
import mx.gob.sedesol.basegestor.service.ParametroWSMoodleService;
import mx.gob.sedesol.basegestor.service.admin.CatalogoComunService;
import mx.gob.sedesol.basegestor.service.planesyprogramas.MallaCurricularService;
import mx.gob.sedesol.basegestor.service.planesyprogramas.PlanService;
import mx.gob.sedesol.basegestor.ws.moodle.clientes.model.entities.Categoria;
import mx.gob.sedesol.basegestor.ws.moodle.clientes.model.entities.RespuestaCrearCategorias;
import mx.gob.sedesol.basegestor.ws.moodle.clientes.service.client.CrearCategoria;
import mx.gob.sedesol.basegestor.ws.moodle.clientes.service.util.ErrorWS;

@Service("planServiceFacade")
public class PlanServiceFacade {

    private static final Logger logger = Logger.getLogger(PlanServiceFacade.class);

    @Autowired
    private PlanService planService;
    @Autowired
    private MallaCurricularService mallaCurricularService;
    @Autowired
    private CatalogoComunService<CatObjetoCurricular, Integer> catObjCurrService;

    @Autowired
    private ParametroWSMoodleService parametroWSMoodleService;

    /**
     *
     * @param clon
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public ResultadoDTO<PlanDTO> clonarPlan(PlanDTO clon) {
        ResultadoDTO<PlanDTO> resultado = new ResultadoDTO<>();
        try {

            List<RelPlanHabilidadDTO> habilidadesClon = clon.getRelPlanHabilidades();
            List<RelPlanAptitudDTO> aptitudesClon = clon.getRelPlanAptitudes();
            List<RelPlanConocimientoDTO> conocimientosClon = clon.getRelPlanConocimientos();

            MallaCurricularDTO mallaCurClon = mallaCurricularService.obtenerMallaCurricularPorIdPlan(clon.getIdPlan());

            clon.setRelPlanAptitudes(null);
            clon.setRelPlanConocimientos(null);
            clon.setRelPlanHabilidades(null);
            clon.setIdPlan(null);
            clon.setFechaRegistro(new Date());
            clon.setVersion(clon.getVersion() + ConstantesGestor.VERSION_UNO);

            ResultadoDTO<PlanDTO> resClon = planService.guardar(clon);

            if (ObjectUtils.isNotNull(resClon) && resClon.getResultado().getValor()) {
                PlanDTO planClonGuardado = resClon.getDto();

                if (!ObjectUtils.isNullOrEmpty(habilidadesClon)) {
                    for (RelPlanHabilidadDTO hab : habilidadesClon) {
                        hab.setTblPlan(planClonGuardado);
                    }
                    planClonGuardado.setRelPlanHabilidades(habilidadesClon);
                }

                if (!ObjectUtils.isNullOrEmpty(aptitudesClon)) {
                    for (RelPlanAptitudDTO apt : aptitudesClon) {
                        apt.setTblPlan(planClonGuardado);
                    }
                    planClonGuardado.setRelPlanAptitudes(aptitudesClon);
                }

                if (!ObjectUtils.isNullOrEmpty(conocimientosClon)) {
                    for (RelPlanConocimientoDTO con : conocimientosClon) {
                        con.setTblPlan(planClonGuardado);
                    }
                    planClonGuardado.setRelPlanConocimientos(conocimientosClon);
                }
                //Se actualiza el plan
                planService.actualizar(planClonGuardado);

                //Se genera la malla Curricular del Plan Clonado
                if (ObjectUtils.isNotNull(mallaCurClon)) {

                    List<MallaCurricularDTO> mallaHijosClon = mallaCurClon.getLstHijosMallaCurr();
                    mallaCurClon.setIdPlan(planClonGuardado.getIdPlan());
                    mallaCurClon.setId(null);
                    mallaCurClon.setLstHijosMallaCurr(null);

                    MallaCurricularDTO mallaClonGuardada = mallaCurricularService.guardar(mallaCurClon).getDto();
                    if (ObjectUtils.isNotNull(mallaClonGuardada)) {
                        this.clonaMallaCurHijos(mallaHijosClon, mallaClonGuardada.getId());
                    }

                } else {

                    //RN: Se genera un registro en la malla curricular como nuevo plan
                    MallaCurricularDTO planMalla = new MallaCurricularDTO();
                    planMalla.setIdPlan(planClonGuardado.getIdPlan());
                    planMalla.setActivo(ConstantesGestor.ACTIVO);
                    planMalla.setFechaRegistro(planClonGuardado.getFechaRegistro());
                    planMalla.setNombre(planClonGuardado.getNombre());
                    planMalla.setMallaCurricularPadre(null);
                    planMalla.setUsuarioModifico(planClonGuardado.getUsuarioModifico());
                    planMalla.setObjetoCurricular(catObjCurrService.buscarRegistroPorNombre(ObjetoCurricularEnum.PLAN.getNombre(), CatObjetoCurricular.class));

                    mallaCurricularService.guardar(planMalla);
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            resultado.setMensajeError(MensajesSistemaEnum.ADMIN_MSG_GUARDADO_FALLIDO);
            throw e;
        }

        return resultado;
    }

    /**
     * Metodo que se encarga de generar los nuevos Ids para la malla curricular
     * clonada
     *
     * @param mallaHijos
     * @param mallaPadre
     */
    private void clonaMallaCurHijos(List<MallaCurricularDTO> mallaHijos, Integer idMallaPadre) {
        if (ObjectUtils.isNotNull(mallaHijos) && ObjectUtils.isNotNull(idMallaPadre)) {

            for (MallaCurricularDTO mcurHijo : mallaHijos) {

                List<MallaCurricularDTO> hijosClon = mcurHijo.getLstHijosMallaCurr();

                mcurHijo.setId(null);
                mcurHijo.setLstHijosMallaCurr(null);
                mcurHijo.setFechaActualizacion(new Date());
                mcurHijo.setMallaCurricularPadre(mallaCurricularService.obtenerMallaCurricularPorId(idMallaPadre));

                //Se persiste el identificador para poder generar los registros subsecuentes
                MallaCurricularDTO mHijoGuard = mallaCurricularService.guardar(mcurHijo).getDto();

                if (!ObjectUtils.isNullOrEmpty(hijosClon) && ObjectUtils.isNotNull(mHijoGuard)) {
                    this.clonaMallaCurHijos(hijosClon, mHijoGuard.getId());
                }
            }
        }
    }

    /**
     * Guarda un nuevo plan con sus relaciones
     *
     * @param plan
     * @param habilidadesPlan
     * @param aptitudesPlan
     * @param conocimientosPlan
     * @return
     */
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.READ_UNCOMMITTED)
    public ResultadoDTO<PlanDTO> guardaNuevoPlan(PlanDTO plan, List<CatalogoComunDTO> habilidadesPlan, List<CatalogoComunDTO> aptitudesPlan, List<CatalogoComunDTO> conocimientosPlan) {

        ResultadoDTO<PlanDTO> resultado = new ResultadoDTO<>();
        List<RelPlanHabilidadDTO> relHabilidades;
        List<RelPlanConocimientoDTO> relConocimientos;
        List<RelPlanAptitudDTO> relAptitudes;

        if (ObjectUtils.isNull(plan)) {
            return null;
        }

        try {

            //Se persiste el plan para generar Id y se procede a generar las relaciones multiples
            logger.debug("Persistiendo el nuevo plan");
            plan.setFechaRegistro(new Date());
            plan.setVersion(ConstantesGestor.VERSION_UNO);

            resultado = planService.guardar(plan);

            if (ObjectUtils.isNotNull(resultado) && resultado.getResultado().getValor()) {
                PlanDTO nuevoPlan = resultado.getDto();

                //RN: Se genera un registro en la malla curricular como nuevo plan
                MallaCurricularDTO planMalla = new MallaCurricularDTO();
                planMalla.setIdPlan(nuevoPlan.getIdPlan());
                planMalla.setActivo(ConstantesGestor.ACTIVO);
                planMalla.setFechaRegistro(nuevoPlan.getFechaRegistro());
                planMalla.setNombre(nuevoPlan.getNombre());
                planMalla.setMallaCurricularPadre(null);
                planMalla.setUsuarioModifico(nuevoPlan.getUsuarioModifico());
                planMalla.setObjetoCurricular(catObjCurrService.buscarRegistroPorNombre(ObjetoCurricularEnum.PLAN.getNombre(), CatObjetoCurricular.class));
                //planMalla.getBitacoraDTO().setIdUsuario(plan.getBitacoraDTO().getIdUsuario());
                //planMalla.getBitacoraDTO().setFechaBitacora(new Date());
                //planMalla.getBitacoraDTO().setIp(plan.getBitacoraDTO().getIp());
                //planMalla.getBitacoraDTO().setNavegador(plan.getBitacoraDTO().getNavegador());
                //planMalla.getBitacoraDTO().setFuncion(ConstantesBitacora.MALLA_CURR_AGREGAR);
                mallaCurricularService.guardar(planMalla);

                //Se generan las Relaciones de acuerdo a las listas de seleccion multiple
                if (!ObjectUtils.isNullOrEmpty(habilidadesPlan)) {
                    relHabilidades = new ArrayList<>();
                    for (CatalogoComunDTO habilidad : habilidadesPlan) {

                        RelPlanHabilidadDTO relHabil = new RelPlanHabilidadDTO();
                        relHabil.setFechaRegistro(nuevoPlan.getFechaRegistro());
                        relHabil.setActivo(ConstantesGestor.ACTIVO);
                        relHabil.setUsuarioModifico(plan.getUsuarioModifico());
                        relHabil.setTblPlan(nuevoPlan);
                        relHabil.setCatHabilidadesPlan(habilidad);

                        relHabilidades.add(relHabil);
                    }
                    nuevoPlan.setRelPlanHabilidades(relHabilidades);
                }

                if (!ObjectUtils.isNullOrEmpty(aptitudesPlan)) {
                    relAptitudes = new ArrayList<>();
                    for (CatalogoComunDTO aptitud : aptitudesPlan) {
                        RelPlanAptitudDTO relApt = new RelPlanAptitudDTO();
                        relApt.setFechaRegistro(nuevoPlan.getFechaRegistro());
                        relApt.setActivo(ConstantesGestor.ACTIVO);
                        relApt.setUsuarioModifico(plan.getUsuarioModifico());
                        relApt.setTblPlan(nuevoPlan);
                        relApt.setCatAptitudesPlan(aptitud);

                        relAptitudes.add(relApt);
                    }
                    nuevoPlan.setRelPlanAptitudes(relAptitudes);
                }

                if (!ObjectUtils.isNullOrEmpty(conocimientosPlan)) {
                    relConocimientos = new ArrayList<>();
                    for (CatalogoComunDTO conoc : conocimientosPlan) {
                        RelPlanConocimientoDTO relConoc = new RelPlanConocimientoDTO();
                        relConoc.setFechaRegistro(nuevoPlan.getFechaRegistro());
                        relConoc.setActivo(ConstantesGestor.ACTIVO);
                        relConoc.setUsuarioModifico(plan.getUsuarioModifico());
                        relConoc.setTblPlan(nuevoPlan);
                        relConoc.setCatAreasConocimiento(conoc);

                        relConocimientos.add(relConoc);
                    }
                    nuevoPlan.setRelPlanConocimientos(relConocimientos);
                }

                //Se Guardan plan con relaciones
                resultado = planService.actualizar(nuevoPlan);

                //Se genera Estructura  Moodle
                if (ObjectUtils.isNotNull(resultado) && resultado.getResultado().getValor()) {
                    PlanDTO planSaved = resultado.getDto();
                    try {
                        List<ParametroWSMoodleDTO> plataformas = parametroWSMoodleService.findAll();
                        if (!ObjectUtils.isNullOrEmpty(plataformas)) {

                            for (ParametroWSMoodleDTO ptf : plataformas) {

                                CrearCategoria categoriaWS = new CrearCategoria(ptf);
                                Categoria planCategoria = new Categoria();
                                planCategoria.setIdnumber(planSaved.getIdPlan().toString());
                                planCategoria.setName(planSaved.getNombre());

                                ArrayList<Categoria> categorias = new ArrayList<Categoria>();
                                categorias.add(planCategoria);

                                List<RespuestaCrearCategorias> respuestasWS = categoriaWS.crearCategoria(categorias);
                                if (ObjectUtils.isNotNull(respuestasWS)) {

                                    int idCategoriaMdl = respuestasWS.get(ConstantesGestor.PRIMER_ELEMENTO).getId();

                                    planSaved.setIdCategoriaMdl(idCategoriaMdl);
                                    resultado = getPlanService().actualizar(planSaved);

                                    MallaCurricularDTO mallaCurr = mallaCurricularService.obtenerMallaCurricularPorIdPlan(planSaved.getIdPlan());
                                    mallaCurr.setIdCategoriaMdl(idCategoriaMdl);
                                    mallaCurr.setFechaActualizacion(new Date());
                                   // mallaCurr.getBitacoraDTO().setIdUsuario(plan.getBitacoraDTO().getIdUsuario());
                                   // mallaCurr.getBitacoraDTO().setFechaBitacora(new Date());
                                   // mallaCurr.getBitacoraDTO().setIp(plan.getBitacoraDTO().getIp());
                                   // mallaCurr.getBitacoraDTO().setNavegador(plan.getBitacoraDTO().getNavegador());
                                   // mallaCurr.getBitacoraDTO().setFuncion(ConstantesBitacora.MALLA_CUR_EDITAR);
                                    mallaCurricularService.actualizar(mallaCurr);

                                }
                            }
                        }

                    } catch (ErrorWS e) {
                        logger.error(e.getMessage(), e);
                        resultado.setMensajeError(MensajesSistemaEnum.ADMIN_MSG_GUARDADO_FALLIDO);
                    }
                }
                logger.debug("finaliza persistencia de plan");
                return resultado;
            }

        } catch (Exception e) {
            logger.error(e.getMessage());
            resultado.setMensajeError(MensajesSistemaEnum.ADMIN_MSG_GUARDADO_FALLIDO);
            throw e;
        }

        return null;
    }

    @Transactional(rollbackFor = Exception.class)
    public ResultadoDTO<PlanDTO> editarPlan(PlanDTO plan, List<CatalogoComunDTO> habilidadesPlan, List<CatalogoComunDTO> aptitudesPlan, List<CatalogoComunDTO> conocimientosPlan) {

        ResultadoDTO<PlanDTO> resultado = null;
        List<RelPlanHabilidadDTO> relHabilidades;
        List<RelPlanConocimientoDTO> relConocimientos;
        List<RelPlanAptitudDTO> relAptitudes;
        Date fechaAct = new Date();

        try {

            plan.setRelPlanAptitudes(null);
            plan.setRelPlanConocimientos(null);
            plan.setRelPlanHabilidades(null);

            planService.eliminaConocimientosPorIdPlan(plan.getIdPlan());
            planService.eliminAptitudesPorIdPlan(plan.getIdPlan());
            planService.eliminaHabilidadesPorIdPlan(plan.getIdPlan());

            logger.debug("Editando el nuevo plan");
            plan.setFechaActualizacion(fechaAct);

            //RN: Actualizacion de Nombre de malla curricular
            MallaCurricularDTO mallaCurr = mallaCurricularService.obtenerMallaCurricularPorIdPlan(plan.getIdPlan());
            mallaCurr.setNombre(plan.getNombre());
            mallaCurr.setFechaActualizacion(fechaAct);
            mallaCurr.setUsuarioModifico(plan.getUsuarioModifico());
           // mallaCurr.getBitacoraDTO().setIdUsuario(plan.getBitacoraDTO().getIdUsuario());
           // mallaCurr.getBitacoraDTO().setFechaBitacora(new Date());
           // mallaCurr.getBitacoraDTO().setIp(plan.getBitacoraDTO().getIp());
           // mallaCurr.getBitacoraDTO().setNavegador(plan.getBitacoraDTO().getNavegador());
           // mallaCurr.getBitacoraDTO().setFuncion(ConstantesBitacora.MALLA_CUR_EDITAR);
            mallaCurricularService.actualizar(mallaCurr);

            //Se generan las Relaciones de acuerdo a las listas de seleccion multiple
            if (!ObjectUtils.isNullOrEmpty(habilidadesPlan)) {
                relHabilidades = new ArrayList<>();
                for (CatalogoComunDTO habilidad : habilidadesPlan) {

                    RelPlanHabilidadDTO relHabil = new RelPlanHabilidadDTO();
                    relHabil.setFechaRegistro(plan.getFechaActualizacion());
                    relHabil.setActivo(ConstantesGestor.ACTIVO);
                    relHabil.setUsuarioModifico(plan.getUsuarioModifico());
                    relHabil.setTblPlan(plan);
                    relHabil.setCatHabilidadesPlan(habilidad);

                    relHabilidades.add(relHabil);
                }
                plan.setRelPlanHabilidades(relHabilidades);
            }

            if (!ObjectUtils.isNullOrEmpty(aptitudesPlan)) {
                relAptitudes = new ArrayList<>();
                for (CatalogoComunDTO aptitud : aptitudesPlan) {
                    RelPlanAptitudDTO relApt = new RelPlanAptitudDTO();
                    relApt.setFechaRegistro(plan.getFechaActualizacion());
                    relApt.setActivo(ConstantesGestor.ACTIVO);
                    relApt.setUsuarioModifico(plan.getUsuarioModifico());
                    relApt.setTblPlan(plan);
                    relApt.setCatAptitudesPlan(aptitud);

                    relAptitudes.add(relApt);
                }
                plan.setRelPlanAptitudes(relAptitudes);
            }

            if (!ObjectUtils.isNullOrEmpty(conocimientosPlan)) {
                relConocimientos = new ArrayList<>();
                for (CatalogoComunDTO conoc : conocimientosPlan) {
                    RelPlanConocimientoDTO relConoc = new RelPlanConocimientoDTO();
                    relConoc.setFechaRegistro(plan.getFechaRegistro());
                    relConoc.setActivo(ConstantesGestor.ACTIVO);
                    relConoc.setUsuarioModifico(plan.getUsuarioModifico());
                    relConoc.setTblPlan(plan);
                    relConoc.setCatAreasConocimiento(conoc);

                    relConocimientos.add(relConoc);
                }
                plan.setRelPlanConocimientos(relConocimientos);
            }

            resultado = planService.actualizar(plan);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            resultado.setMensajeError(MensajesSistemaEnum.ADMIN_MSG_ACTUALIZACION_FALLIDA);
            throw e;
        }

        return resultado;
    }

    public String generaIdentificadorPlan() {
        String identif = "";
        String idPlan = "";

        Integer maxId = planService.obtieneUltimoIdPlanes();
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        String anio = Integer.toString(cal.get(Calendar.YEAR)).substring(2, 4);

        if (ObjectUtils.isNotNull(maxId)) {
            maxId = maxId + 1;
            if (maxId < 10) {
                idPlan = "0".concat(maxId.toString());
            } else {
                idPlan = maxId.toString();
            }

            identif = idPlan.concat("-").concat(anio);
        } else {
            identif = ("01-").concat(anio);
        }

        return identif;
    }

    /**
     * @return the planService
     */
    public PlanService getPlanService() {
        return planService;
    }

    /**
     * @param planService the planService to set
     */
    public void setPlanService(PlanService planService) {
        this.planService = planService;
    }

    /**
     * @return the parametroWSMoodleService
     */
    public ParametroWSMoodleService getParametroWSMoodleService() {
        return parametroWSMoodleService;
    }

    /**
     * @param parametroWSMoodleService the parametroWSMoodleService to set
     */
    public void setParametroWSMoodleService(ParametroWSMoodleService parametroWSMoodleService) {
        this.parametroWSMoodleService = parametroWSMoodleService;
    }

}
