package mx.gob.sedesol.basegestor.service.impl.planesyprogramas;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import mx.gob.sedesol.basegestor.commons.constantes.ConstantesBitacora;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import mx.gob.sedesol.basegestor.commons.constantes.ConstantesGestor;
import mx.gob.sedesol.basegestor.commons.dto.admin.ParametroWSMoodleDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.CmpDinamicoUniDidacticaDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.ControlEstTematicaDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.DetEstUniDidacticaDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.DetEtematicaTemaDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.EstructuraTematicaDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.FichaDescProgramaDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.MallaCurricularDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.PlanDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.RelEstUnidadDidacticaDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.RelProgAutoreDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.RelProgCompEspecificaDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.RelProgDuracionDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.RelProgPersonalExternoDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.RelProgResponsableDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.RelUDidacticaTposCompetenciaDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.RelUniDidacticaMaterialDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.SubtemasUDidacticaDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.ValoresEstTematicaDTO;
import mx.gob.sedesol.basegestor.commons.utils.DateUtils;
import mx.gob.sedesol.basegestor.commons.utils.EstatusProgramaEnum;
import mx.gob.sedesol.basegestor.commons.utils.MensajesSistemaEnum;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.ObjetoCurricularEnum;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.CatCompetenciaEspecifica;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.CatMaterialDidactico;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.CatObjetoCurricular;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.CatStatusPrograma;
import mx.gob.sedesol.basegestor.mongo.service.NotificacionSistemaService;
import mx.gob.sedesol.basegestor.service.ParametroWSMoodleService;
import mx.gob.sedesol.basegestor.service.admin.CatalogoComunService;
import mx.gob.sedesol.basegestor.service.admin.OrgGubernamentalService;
import mx.gob.sedesol.basegestor.service.admin.PersonaService;
import mx.gob.sedesol.basegestor.service.admin.RoleService;
import mx.gob.sedesol.basegestor.service.gestionescolar.PersonaResponsabilidadesService;
import mx.gob.sedesol.basegestor.service.planesyprogramas.CompetenciaEspecificaService;
import mx.gob.sedesol.basegestor.service.planesyprogramas.ETematicaModuloMdlService;
import mx.gob.sedesol.basegestor.service.planesyprogramas.EjeCompetenciaService;
import mx.gob.sedesol.basegestor.service.planesyprogramas.EstPersonalExternoService;
import mx.gob.sedesol.basegestor.service.planesyprogramas.EstUnidadDidacticaService;
import mx.gob.sedesol.basegestor.service.planesyprogramas.EstructuraTematicaService;
import mx.gob.sedesol.basegestor.service.planesyprogramas.FichaDescProgramaService;
import mx.gob.sedesol.basegestor.service.planesyprogramas.MallaCurricularService;
import mx.gob.sedesol.basegestor.service.planesyprogramas.ModuloMoodleService;
import mx.gob.sedesol.basegestor.service.planesyprogramas.PlanService;
import mx.gob.sedesol.basegestor.ws.moodle.clientes.model.entities.Categoria;
import mx.gob.sedesol.basegestor.ws.moodle.clientes.model.entities.RespuestaCrearCategorias;
import mx.gob.sedesol.basegestor.ws.moodle.clientes.service.client.CrearCategoria;
import mx.gob.sedesol.basegestor.ws.moodle.service.CoursesService;

@Service("fecServiceFacade")
public class FECServiceFacade {

    private static final Logger logger = Logger.getLogger(FECServiceFacade.class);

    @Autowired
    private PersonaResponsabilidadesService personaResponsabilidadesService;

    @Autowired
    private MallaCurricularService mallaCurricularService;
    @Autowired
    private FichaDescProgramaService fichaDescProgramaService;
    @Autowired
    private EstructuraTematicaService estructuraTematicaService;
    @Autowired
    private ModuloMoodleService moduloMoodleService;
    @Autowired
    private ETematicaModuloMdlService eTematicaModuloMdlService;
    @Autowired
    private PlanService planService;

    @Autowired
    private CatalogoComunService<CatStatusPrograma, Integer> catStatusProgramaService;
    @Autowired
    private CatalogoComunService<CatObjetoCurricular, Integer> objetoCurricularService;

    @Autowired
    private EstUnidadDidacticaService estUnidadDidacticaService;
    @Autowired
    private CatalogoComunService<CatMaterialDidactico, Integer> catMaterialDidacticoService;
    @Autowired
    private CatalogoComunService<CatCompetenciaEspecifica, Integer> catCompEspecificaService;
    @Autowired
    private OrgGubernamentalService orgGubernamentalService;
    @Autowired
    private EstPersonalExternoService estPersonalExternoService;
    @Autowired
    private CompetenciaEspecificaService competenciaEspecificaService;
    @Autowired
    private EjeCompetenciaService ejeCompetenciaService;
    @Autowired
    private NotificacionSistemaService notificacionSistemaService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PersonaService personaService;
    @Autowired
    private ParametroWSMoodleService parametroWSMoodleService;

    @Value("${ws.moodle.token}")
    private String token;
    @Value("${ws.moodle.urlWS}")
    private String urlWs;
    @Value("${ws.moodle.direcionIp}")
    private String ipWsServer;
    @Value("${ws.moodle.puerto}")
    private String puerto;

    /**
     * Servicio para persistir un registro en la entidad MallaCurricular DE
     * Igual forma se necesita persistir el identificador del programa en la
     * tabla ficha descriptiva programa
     *
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    public ResultadoDTO<MallaCurricularDTO> guardaMallaCurricular(MallaCurricularDTO dto) throws Exception {

        ResultadoDTO<MallaCurricularDTO> res = new ResultadoDTO<MallaCurricularDTO>();
        try {

            res = mallaCurricularService.guardar(dto);

            if (ObjectUtils.isNotNull(res) && res.getResultado().getValor()) {

                MallaCurricularDTO mallaCurrSvd = res.getDto();

                // Se genera Estructura Moodle
                List<ParametroWSMoodleDTO> plataformas = parametroWSMoodleService.findAll();
                if (!ObjectUtils.isNullOrEmpty(plataformas)) {

                    for (ParametroWSMoodleDTO ptf : plataformas) {

                        CrearCategoria categoriaWS = new CrearCategoria(ptf);
                        Categoria mc = new Categoria();
                        //Se comenta por conflictos con la plataforma moodle
                        //mc.setIdnumber(mallaCurrSvd.getId().toString());
                        mc.setName(mallaCurrSvd.getNombre());
                        mc.setParent(dto.getIdCategoriaMdlPadre());

                        ArrayList<Categoria> categorias = new ArrayList<Categoria>();
                        categorias.add(mc);

                        List<RespuestaCrearCategorias> respuestasWS = categoriaWS.crearCategoria(categorias);
                        if (ObjectUtils.isNotNull(respuestasWS)) {

                            int idCategoriaMdl = respuestasWS.get(ConstantesGestor.PRIMER_ELEMENTO).getId();

                            mallaCurrSvd.setIdCategoriaMdl(idCategoriaMdl);
                            mallaCurrSvd.setFechaActualizacion(new Date());
                            res = mallaCurricularService.actualizar(mallaCurrSvd);

                        }
                    }
                }
            }

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            res.setMensajeError(MensajesSistemaEnum.ADMIN_MSG_GUARDADO_FALLIDO);
            throw e;
        }

        return res;
    }

    /**
     * Elimina estructura tematica y su referencia en la tabla ficha descriptiva
     * programa
     *
     * @param estructuraTematica
     * @return
     */
    /**
     * @Transactional(rollbackFor=Exception.class) public
     * ResultadoDTO<MallaCurricularDTO>
     * eliminaEstructuraTematica(EstructuraTematicaDTO estructuraTematica){
     *
     * ResultadoDTO<MallaCurricularDTO> tx = null; FichaDescProgramaDTO
     * fichaProg = new FichaDescProgramaDTO();
     *
     * try{ //Condicion para eliminar referencia de estructura tematica de la
     * ficha programa
     * if(ObjectUtils.isNotNull(estructuraTematica.getIdFichaDescPrograma())){
     * fichaProg =
     * fichaDescProgramaService.buscarPorId(estructuraTematica.getIdFichaDescPrograma());
     * } estructuraTematicaService.eliminar(estructuraTematica);
     * if(ObjectUtils.isNotNull(fichaProg)){
     * fichaProg.setTblEstructuraTematica(null);
     * fichaDescProgramaService.actualizar(fichaProg); } tx = new
     * ResultadoDTO<MallaCurricularDTO>();      *
     * }catch (Exception e) { tx = new ResultadoDTO<MallaCurricularDTO>();
     * tx.setMensajeError(MensajesSistemaEnum.ADMIN_MSG_GUARDADO_FALLIDO);
     * logger.error(e.getMessage(),e); throw e; }
     *
     * return tx; }
     */
    /**
     * Elimina una Malla curricular, si es programa elimina las relaciones
     *
     * @param mallaCurrDTO
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public ResultadoDTO<MallaCurricularDTO> eliminaMallaCurricular(MallaCurricularDTO mallaCurrDTO) {

        ResultadoDTO<MallaCurricularDTO> res = null;
        try {
            if (ObjectUtils.isNotNull(mallaCurrDTO)) {
                if (mallaCurrDTO.getObjetoCurricular().getNombre()
                        .equalsIgnoreCase(ObjetoCurricularEnum.PROGRAMA.getNombre())) {

                    FichaDescProgramaDTO fichaProgElim = fichaDescProgramaService.buscarPorId(mallaCurrDTO.getId());
                    //Se elimina estructura tematica en caso de que tenga asociado un objeto
                    if (ObjectUtils.isNotNull(fichaProgElim.getTblEstructuraTematica())) {
                        estructuraTematicaService.eliminar(fichaProgElim.getTblEstructuraTematica());
                    }
                    //Se elimina la ficha descriptiva del progama
                    fichaDescProgramaService.eliminar(fichaProgElim);
                }
                //Se elimina el objeto curricular
                mallaCurricularService.eliminar(mallaCurrDTO);
                res = new ResultadoDTO<MallaCurricularDTO>();
            }
        } catch (Exception e) {
            res = new ResultadoDTO<MallaCurricularDTO>();
            res.setMensajeError(MensajesSistemaEnum.ADMIN_MSG_GUARDADO_FALLIDO);
            logger.error(e.getMessage(), e);
            throw e;
        }

        return res;
    }

    @Transactional(rollbackFor = Exception.class)
    public ResultadoDTO<EstructuraTematicaDTO> guardaSubTemaCursoMoodle(EstructuraTematicaDTO tema) throws Exception {
        ResultadoDTO<EstructuraTematicaDTO> res = null;
        try {

            res = estructuraTematicaService.guardar(tema);
            if (ObjectUtils.isNotNull(res) && res.getResultado().getValor()) {

                CoursesService courseService = new CoursesService(
                        ipWsServer,
                        Integer.parseInt(puerto),
                        urlWs,
                        token);

                courseService.setEstructuraTematica(tema);
                courseService.sendCourseCreationRequest();
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw e;
        }

        return res;
    }

    /**
     * Genera la clave del programa formato: AA-BB-CCC AA-BB = Identificador del
     * plan CCC = consecutivo del programa en centenas
     *
     * @return
     */
    public String generaClaveProgramaPlanSedesol(Integer idProgramaBorrador) {
        String claveProg = null;
        PlanDTO planSedesol = planService.buscarPorId(ConstantesGestor.IDENTIFICADOR_PLAN_SEDESOL);
        if (ObjectUtils.isNotNull(planSedesol)) {
            String idPlan = planSedesol.getIdentificador();

            claveProg = idPlan.concat("-").concat(generaFolioPrograma(idProgramaBorrador));
        }
        return claveProg;
    }

    /**
     *
     * @param id
     * @return
     */
    private String generaFolioPrograma(Integer id) {

        String folio = "";
        if (id.compareTo(new Integer(10)) < 0) {
            folio = "00".concat(id.toString());
        } else if (id.compareTo(new Integer(10)) >= 0 && id.compareTo(new Integer(100)) < 0) {
            folio = "0".concat(id.toString());
        } else {
            folio = id.toString();
        }
        return folio;

    }

    /**
     * Persiste los datos Generales del Programa stream Java8 itera la lista y
     * se setean propiedades necesarias para la persistencia de la entidad
     *
     * @param programa
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public ResultadoDTO<FichaDescProgramaDTO> guardarDatosPrograma(FichaDescProgramaDTO programa) {
        ResultadoDTO<FichaDescProgramaDTO> resultado = new ResultadoDTO<>();
        try {
            Date fechaReg = new Date();

            //Existe el programa, se actualiza el programa
            if (ObjectUtils.isNotNull(programa.getIdPrograma())) {

                programa.setFechaActualizacion(new Date());
                programa.setUsuarioModifico(programa.getUsuarioModifico());

                if (!ObjectUtils.isNullOrEmpty(programa.getRelProgramaResponsables())) {

                    programa.getRelProgramaResponsables().stream().forEach(r -> {
                        r.setFichaDescriptivaPrograma(programa);
                        //r.setFechaRegistro(programa.getFechaRegistro());
                        r.setUsuarioModifico(programa.getUsuarioModifico());
                    });
                }

                if (!ObjectUtils.isNullOrEmpty(programa.getRelProgramaAutores())) {

                    programa.getRelProgramaAutores().stream().forEach(a -> {
                        a.setFichaDescriptivaPrograma(programa);
                        //a.setFechaRegistro(programa.getFechaRegistro());
                        a.setUsuarioModifico(programa.getUsuarioModifico());
                    });
                }

                List<RelProgDuracionDTO> relDuracion = programa.getRelProgramaDuracion();
                if (!ObjectUtils.isNullOrEmpty(relDuracion)) {

                    relDuracion.stream().forEach(c -> {
                        c.setFichaDescriptivaPrograma(programa);
                        //c.setFechaRegistro(programa.getFechaRegistro());
                        c.setUsuarioModifico(programa.getUsuarioModifico());
                    });

                    programa.setRelProgramaDuracion(relDuracion);
                }

                List<RelProgPersonalExternoDTO> relPersExt = programa.getRelProgEstPersonalExterno();

                if (!ObjectUtils.isNullOrEmpty(relPersExt)) {

                    relPersExt.stream().forEach(pe -> {
                        pe.setTblFichaDescriptivaPrograma(programa);
                        pe.setFechaRegistro(programa.getFechaRegistro());
                        pe.setUsuarioModifico(programa.getUsuarioModifico());
                    });
                    programa.setRelProgEstPersonalExterno(relPersExt);
                }
                //}
                //programa.getBitacoraDTO().setFuncion(ConstantesBitacora.FICHA_PROGRAMA_EDITAR);
                resultado = fichaDescProgramaService.actualizar(programa);

            } else {
                //Es nuevo programa

                //List<RelProgAutoreDTO> auxProgAutores = programa.getRelProgramaAutores();
                //List<RelProgResponsableDTO> auxProgResp = programa.getRelProgramaResponsables();
                List<RelProgCompEspecificaDTO> compsEspecificas = programa.getRelProgramaComEspecificas();
                List<RelProgPersonalExternoDTO> personalExt = programa.getRelProgEstPersonalExterno();

                programa.setFechaRegistro(fechaReg);
                programa.setFechaActualizacion(fechaReg);
                programa.setRelProgramaAutores(null);
                //programa.setRelProgramaDuracion(null);
                programa.setRelProgramaResponsables(null);
                programa.setRelProgramaComEspecificas(null);
                programa.setRelProgEstPersonalExterno(null);
                //programa.getBitacoraDTO().setFuncion(ConstantesBitacora.FICHA_PROGRAMA_AGREGAR);
                resultado = fichaDescProgramaService.guardar(programa);
                if (ObjectUtils.isNotNull(resultado) && resultado.getResultado().getValor()) {
                    FichaDescProgramaDTO programaSaved = resultado.getDto();

                    if (ObjectUtils.isNotNull(compsEspecificas)) {
                        compsEspecificas.stream().forEach(c -> {
                            c.setFichaDescProgramaDTO(programaSaved);
                            c.setFechaRegistro(programaSaved.getFechaRegistro());
                            c.setUsuarioModifico(programaSaved.getUsuarioModifico());
                        });
                        programaSaved.setRelProgramaComEspecificas(compsEspecificas);
                    }

                    if (ObjectUtils.isNotNull(personalExt)) {
                        personalExt.stream().forEach(c -> {
                            c.setTblFichaDescriptivaPrograma(programaSaved);
                            c.setFechaRegistro(programaSaved.getFechaRegistro());
                            c.setUsuarioModifico(programaSaved.getUsuarioModifico());
                        });
                        programaSaved.setRelProgEstPersonalExterno(personalExt);
                    }

                    programaSaved.setFechaActualizacion(programaSaved.getFechaRegistro());
                    //programaSaved.getBitacoraDTO().setFuncion(ConstantesBitacora.FICHA_PROGRAMA_EDITAR);
                    resultado = fichaDescProgramaService.actualizar(programaSaved);

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
     * Persiste los datos generales del programa y estandar de competencia
     *
     * @param programa
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public ResultadoDTO<FichaDescProgramaDTO> guardarDatosProgramaYEstCompetencia(FichaDescProgramaDTO programa) {

        ResultadoDTO<FichaDescProgramaDTO> resultado = new ResultadoDTO<>();
        try {
            Date fechaReg = new Date();

            //Existe el programa, se actualiza el programa
            if (ObjectUtils.isNotNull(programa.getIdPrograma())) {

                programa.setFechaActualizacion(new Date());
                //this.setDuracionPrograma(programa, relProgramaDuracionList);
                if (ObjectUtils.isNotNull(programa.getRelProgramaDuracion())) {
                    programa.getRelProgramaDuracion().stream()
                            .forEach(d -> {
                                d.setFechaRegistro(programa.getFechaRegistro());
                                d.setFechaActualizacion(fechaReg);
                                d.setFichaDescriptivaPrograma(programa);
                                d.setUsuarioModifico(programa.getUsuarioModifico());
                            });
                }

                //resultado = fichaDescProgramaService.actualizar(programa);
                resultado = this.guardarDatosPrograma(programa);

            } else {

                List<RelProgDuracionDTO> auxProgDur = programa.getRelProgramaDuracion();
                programa.setRelProgramaDuracion(null);

                //Metodo transaccional 
                resultado = this.guardarDatosPrograma(programa);
                if (ObjectUtils.isNotNull(resultado) && resultado.getResultado().getValor()) {

                    FichaDescProgramaDTO programaSaved = resultado.getDto();

                    if (ObjectUtils.isNotNull(auxProgDur)) {
                        auxProgDur.stream()
                                .forEach(d -> {
                                    d.setFechaRegistro(fechaReg);
                                    d.setFichaDescriptivaPrograma(programaSaved);
                                    d.setUsuarioModifico(programaSaved.getUsuarioModifico());
                                });

                        programaSaved.setRelProgramaDuracion(auxProgDur);
                    }

                    programaSaved.setFechaActualizacion(programaSaved.getFechaRegistro());

                    //resultado = fichaDescProgramaService.actualizar(programaSaved);
                    resultado = this.guardarDatosPrograma(programaSaved);

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
     * Persiste los datos previos del programa y su estructura tematica
     *
     * @param programa
     * @param controlEstTematica
     * @param numUniDidacticas
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public ResultadoDTO<FichaDescProgramaDTO> guardaDatosProgramaYEstTematica(FichaDescProgramaDTO programa, ControlEstTematicaDTO controlEstTematica, Integer numUniDidacticas) throws Exception {

        ResultadoDTO<FichaDescProgramaDTO> resultado = new ResultadoDTO<>();
        try {
            Date fechaReg = new Date();
            programa.setFechaRegistro(fechaReg);

            //Existe el programa, se actualiza el programa
            if (ObjectUtils.isNotNull(programa.getIdPrograma())) {

                EstructuraTematicaDTO estTem = estructuraTematicaService.obtieneEstTematicaTemaPorPrograma(programa.getIdPrograma());
                if (ObjectUtils.isNull(estTem)) {
                    this.guardaEstTematicaRelPrograma(fechaReg, programa, controlEstTematica, numUniDidacticas);
                }

                programa.setFechaActualizacion(fechaReg);

                //resultado = fichaDescProgramaService.actualizar(programa);
                resultado = this.guardarDatosPrograma(programa);

            } else {
                //Es nuevo programa
                resultado = this.guardarDatosProgramaYEstCompetencia(programa);
                if (ObjectUtils.isNotNull(resultado) && resultado.getResultado().getValor()) {

                    FichaDescProgramaDTO programaSaved = resultado.getDto();
                    this.guardaEstTematicaRelPrograma(fechaReg, programaSaved, controlEstTematica, numUniDidacticas);
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
     *
     * @param fechaReg
     * @param programa
     * @param controlEstTematica
     * @param numUniDidacticas
     * @throws Exception
     */
    private void guardaEstTematicaRelPrograma(Date fechaReg, FichaDescProgramaDTO programa,
            ControlEstTematicaDTO controlEstTematica, Integer numUniDidacticas) throws Exception {
        try {

            //Genera una estructura tematica solo en caso de no existir	
            if (ObjectUtils.isNull(programa.getTblEstructuraTematica())
                    || ObjectUtils.isNull(programa.getTblEstructuraTematica().getIdEstructuraTematica())) {

                //Nueva Estructura
                List<DetEtematicaTemaDTO> temasEst = null;
                EstructuraTematicaDTO estTem = new EstructuraTematicaDTO();
                estTem.setActivo(ConstantesGestor.ACTIVO);
                estTem.setFechaRegistro(fechaReg);
                estTem.setNumUnidadesTematicas(numUniDidacticas);
                estTem.setTblFichaDescriptivaPrograma(programa);
                estTem.setUsuarioModifico(programa.getUsuarioModifico());
               // estTem.getBitacoraDTO().setIdUsuario(programa.getBitacoraDTO().getIdUsuario());
               // estTem.getBitacoraDTO().setFechaBitacora(new Date());
               // estTem.getBitacoraDTO().setIp(programa.getBitacoraDTO().getIp());
               // estTem.getBitacoraDTO().setNavegador(programa.getBitacoraDTO().getNavegador());
               // estTem.getBitacoraDTO().setFuncion(ConstantesBitacora.EST_TEMATICA_AGREGAR);
                if (ObjectUtils.isNotNull(controlEstTematica)
                        && !ObjectUtils.isNullOrEmpty(controlEstTematica.getTemas())) {

                    temasEst = new ArrayList<>();
                    for (ValoresEstTematicaDTO itemTema : controlEstTematica.getTemas()) {
                        DetEtematicaTemaDTO tema = new DetEtematicaTemaDTO();
                        tema.setFechaRegistro(fechaReg);
                        tema.setNombreTema(itemTema.getNombreTema());
                        tema.setTblEstructuraTematica(estTem);
                        tema.setUsuarioModifico(estTem.getUsuarioModifico());

                        temasEst.add(tema);

                    }
                }

                estTem.setDetEtematicaTemas(temasEst);
                ResultadoDTO<EstructuraTematicaDTO> resX = estructuraTematicaService.guardar(estTem);
                resX.getResultado();

            }

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw e;
        }
    }

    /**
     *
     * @param coleccion
     * @param findingId
     * @return
     */
    public boolean contieneMatDidacticoID(List<RelUniDidacticaMaterialDTO> coleccion, String findingId) {
        if (!ObjectUtils.isNullOrEmpty(coleccion)) {
            for (RelUniDidacticaMaterialDTO valP : coleccion) {
                if (valP.getCatMaterialDidactico().getId().equals(Integer.parseInt(findingId))) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     *
     * @param coleccion
     * @param findingId
     * @return
     */
    public boolean contieneTpoCompetenciaID(List<RelUDidacticaTposCompetenciaDTO> coleccion, String findingId) {
        if (!ObjectUtils.isNullOrEmpty(coleccion)) {
            for (RelUDidacticaTposCompetenciaDTO valP : coleccion) {
                if (valP.getCatCompetenciaEspecifica().getId().equals(Integer.parseInt(findingId))) {
                    return true;
                }
            }
        }
        return false;
    }

    @Transactional(rollbackFor = Exception.class)
    public ResultadoDTO<DetEstUniDidacticaDTO> eliminaRelacionesUnidadDidactica(FichaDescProgramaDTO dto) {
        List<RelEstUnidadDidacticaDTO> estTemUnidad = estructuraTematicaService.obtieneRelEstUnidadDidPorPrograma(dto.getIdPrograma());
        ResultadoDTO<DetEstUniDidacticaDTO> resTx = new ResultadoDTO<>();
        try {
            if (!ObjectUtils.isNullOrEmpty(estTemUnidad)) {
                for (RelEstUnidadDidacticaDTO rel : estTemUnidad) {
                    //rel.getBitacoraDTO().setIdUsuario(dto.getBitacoraDTO().getIdUsuario());
                    //rel.getBitacoraDTO().setFuncion(ConstantesBitacora.EST_UDIDACTICA_ELIMINAR);
                    //rel.getBitacoraDTO().setIp(dto.getBitacoraDTO().getIp());
                    //rel.getBitacoraDTO().setNavegador(dto.getBitacoraDTO().getNavegador());
                    //rel.getBitacoraDTO().setFechaBitacora(new Date());
                    estUnidadDidacticaService.eliminaRelUnidDidMaterialByIdUnidDid(rel);
                    estUnidadDidacticaService.eliminaRelUnidDidTpoCompByIdUnidDid(rel);
                }
            }

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            resTx.setMensajeError(MensajesSistemaEnum.ADMIN_MSG_ELIMINACION_FALLIDA);
            throw e;
        }
        return resTx;
    }

    /**
     * Guarda datos previos del programa si no se han persistido y guarda las
     * unidades didacticas asociadas
     *
     * @param programa
     * @param unidadesDidacticas
     * @return
     */
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.READ_UNCOMMITTED)
    public ResultadoDTO<FichaDescProgramaDTO> guardaDatosProgramaYUnidadesDidacticas(FichaDescProgramaDTO programa, List<CmpDinamicoUniDidacticaDTO> unidadesDidacticas, ControlEstTematicaDTO controlEstTematica, Integer numUniDidacticas)
            throws Exception {

        Date fechaReg = new Date();
        ResultadoDTO<FichaDescProgramaDTO> resultado = new ResultadoDTO<>();
        try {

            EstructuraTematicaDTO estTem = estructuraTematicaService.obtieneEstTematicaTemaPorPrograma(programa.getIdPrograma());

            if (ObjectUtils.isNull(estTem)) {
                ResultadoDTO<FichaDescProgramaDTO> resBorrador = this.guardaDatosProgramaYEstTematica(programa, controlEstTematica, numUniDidacticas);
                if (ObjectUtils.isNotNull(resBorrador) && resBorrador.getResultado().getValor()) {
                    programa = resBorrador.getDto();
                    resultado.setDto(programa);
                }
            }

            //Se consultan los datos con los nuevos cambios y se comienza con la edicion 
            List<RelEstUnidadDidacticaDTO> estTemUnidad = estructuraTematicaService.obtieneRelEstUnidadDidPorPrograma(programa.getIdPrograma());
            if (!ObjectUtils.isNullOrEmpty(estTemUnidad)) {

                for (RelEstUnidadDidacticaDTO rel : estTemUnidad) {

                    for (CmpDinamicoUniDidacticaDTO udBorrador : unidadesDidacticas) {

                        //Se modifican las unidades didacticas
                        if (rel.getDetEstUnidadDidactica().getIdUnidadDidactica().equals(udBorrador.getDetUnidadDidactica().getIdUnidadDidactica())) {

                            rel.getDetEstUnidadDidactica().setActividadesAprendizaje(udBorrador.getDetUnidadDidactica().getActividadesAprendizaje());
                            rel.getDetEstUnidadDidactica().setActivo(ConstantesGestor.ACTIVO);
                            rel.getDetEstUnidadDidactica().setEvaluacion(udBorrador.getDetUnidadDidactica().getEvaluacion());
                            rel.getDetEstUnidadDidactica().setFechaActualizacion(new Date());
                            rel.getDetEstUnidadDidactica().setFechaRegistro(udBorrador.getDetUnidadDidactica().getFechaRegistro());
                            rel.getDetEstUnidadDidactica().setFuentesInformacion(udBorrador.getDetUnidadDidactica().getFuentesInformacion());
                            rel.getDetEstUnidadDidactica().setMediosRecursos(udBorrador.getDetUnidadDidactica().getMediosRecursos());
                            rel.getDetEstUnidadDidactica().setObjetivosEspecificos(udBorrador.getDetUnidadDidactica().getObjetivosEspecificos());
                            rel.getDetEstUnidadDidactica().setNombreUnidad(udBorrador.getDetUnidadDidactica().getNombreUnidad());

                            rel.getDetEstUnidadDidactica().setRelUDidacticaTposCompetencia(udBorrador.getDetUnidadDidactica().getRelUDidacticaTposCompetencia());
                            rel.getDetEstUnidadDidactica().setRelUniDidacticaMaterial(udBorrador.getDetUnidadDidactica().getRelUniDidacticaMaterial());

                            if (!ObjectUtils.isNullOrEmpty(udBorrador.getDetUnidadDidactica().getSubtemasUdidactica())) {

                                udBorrador.getDetUnidadDidactica().getSubtemasUdidactica().forEach(sb -> {
                                    sb.setActivo(ConstantesGestor.ACTIVO);
                                    sb.setDetEstUnidadDidactica(rel.getDetEstUnidadDidactica());
                                    sb.setFechaActualizacion(fechaReg);
                                    sb.setUsuarioModifico(rel.getDetEstUnidadDidactica().getUsuarioModifico());
                                });

                                rel.getDetEstUnidadDidactica().setSubtemasUdidactica(udBorrador.getDetUnidadDidactica().getSubtemasUdidactica());
                            }

                            // Se genera relacion para persistir el material didactico seleccionado
                            if (!ObjectUtils.isNullOrEmpty(udBorrador.getMaterialDidApoyoSel())) {

                                List<RelUniDidacticaMaterialDTO> auxMatDid = new ArrayList<>();
                                for (String mdid : udBorrador.getMaterialDidApoyoSel()) {
                                    //if(!this.contieneMatDidacticoID(rel.getDetEstUnidadDidactica().getRelUniDidacticaMaterial(), mdid)){
                                    RelUniDidacticaMaterialDTO relMat = new RelUniDidacticaMaterialDTO();
                                    relMat.setFechaRegistro(fechaReg);
                                    relMat.setCatMaterialDidactico(catMaterialDidacticoService.buscarPorId(Integer.parseInt(mdid), CatMaterialDidactico.class));
                                    relMat.setDetEstUnidadDidactica(rel.getDetEstUnidadDidactica());
                                    relMat.setUsuarioModifico(rel.getDetEstUnidadDidactica().getUsuarioModifico());

                                    auxMatDid.add(relMat);
                                    //}
                                }
                                rel.getDetEstUnidadDidactica().setRelUniDidacticaMaterial(auxMatDid);
                            }

                            //Competencias especificas seleccionadas
                            if (!ObjectUtils.isNullOrEmpty(udBorrador.getTiposCompetenciaXEje())) {

                                List<RelUDidacticaTposCompetenciaDTO> auxTposComp = new ArrayList<>();
                                for (String mdid : udBorrador.getTiposCompetenciaXEje()) {
                                    //if(!this.contieneTpoCompetenciaID(rel.getDetEstUnidadDidactica().getRelUDidacticaTposCompetencia(), mdid)){

                                    RelUDidacticaTposCompetenciaDTO cmpEsp = new RelUDidacticaTposCompetenciaDTO();
                                    cmpEsp.setFechaRegistro(fechaReg);
                                    cmpEsp.setCatCompetenciaEspecifica(catCompEspecificaService.buscarPorId(Integer.parseInt(mdid), CatCompetenciaEspecifica.class));
                                    cmpEsp.setDetEstUnidadDidactica(rel.getDetEstUnidadDidactica());
                                    cmpEsp.setUsuarioModifico(rel.getDetEstUnidadDidactica().getUsuarioModifico());

                                    //rel.getDetEstUnidadDidactica().getRelUDidacticaTposCompetencia().add(cmpEsp);
                                    auxTposComp.add(cmpEsp);
                                    //}
                                }

                                rel.getDetEstUnidadDidactica().setRelUDidacticaTposCompetencia(auxTposComp);
                            }

                            rel.getDetEstUnidadDidactica().setTituloUa(rel.getDetEstUnidadDidactica().getTituloUa());
                            rel.getDetEstUnidadDidactica().setUsuarioModifico(programa.getUsuarioModifico());

                        }
                    }

                    estUnidadDidacticaService.actualizar(rel.getDetEstUnidadDidactica());
                }

                resultado = this.guardarDatosPrograma(programa);
            } else {

                //Se Insertan las unidades didacticas
                for (CmpDinamicoUniDidacticaDTO unidad : unidadesDidacticas) {
                    if (ObjectUtils.isNotNull(unidad.getDetUnidadDidactica())) {

                        DetEstUniDidacticaDTO estUnidDid = new DetEstUniDidacticaDTO();

                        estUnidDid.setActividadesAprendizaje(unidad.getDetUnidadDidactica().getActividadesAprendizaje());
                        estUnidDid.setActivo(ConstantesGestor.ACTIVO);
                        estUnidDid.setEvaluacion(unidad.getDetUnidadDidactica().getEvaluacion());
                        estUnidDid.setFechaRegistro(fechaReg);
                        estUnidDid.setFuentesInformacion(unidad.getDetUnidadDidactica().getFuentesInformacion());
                        estUnidDid.setMediosRecursos(unidad.getDetUnidadDidactica().getMediosRecursos());
                        estUnidDid.setNumSubtemas(unidad.getDetUnidadDidactica().getSubtemasUdidactica() != null
                                ? unidad.getDetUnidadDidactica().getSubtemasUdidactica().size() : null);
                        estUnidDid.setObjetivosEspecificos(unidad.getDetUnidadDidactica().getObjetivosEspecificos());

                        estUnidDid.setTituloUa(unidad.getDetUnidadDidactica().getTituloUa());
                        estUnidDid.setNombreUnidad(unidad.getDetUnidadDidactica().getNombreUnidad());
                        estUnidDid.setUsuarioModifico(programa.getUsuarioModifico());
                        estUnidDid.setSubtemasUdidactica(obtieneSubtemasUnidDidactica(estUnidDid, unidad.getDetUnidadDidactica().getSubtemasUdidactica()));

                        ResultadoDTO<DetEstUniDidacticaDTO> resUnidDid = estUnidadDidacticaService.guardar(estUnidDid);

                        if (ObjectUtils.isNotNull(resUnidDid) && resUnidDid.getResultado().getValor()) {

                            DetEstUniDidacticaDTO unidadDidSved = resUnidDid.getDto();

                            // Se genera relacion para persistir el material didactico seleccionado
                            if (!ObjectUtils.isNullOrEmpty(unidad.getMaterialDidApoyoSel())) {
                                unidadDidSved.setRelUniDidacticaMaterial(new ArrayList<>());
                                for (String matDid : unidad.getMaterialDidApoyoSel()) {

                                    RelUniDidacticaMaterialDTO relMat = new RelUniDidacticaMaterialDTO();
                                    relMat.setFechaRegistro(fechaReg);
                                    relMat.setCatMaterialDidactico(catMaterialDidacticoService.buscarPorId(Integer.parseInt(matDid), CatMaterialDidactico.class));
                                    relMat.setDetEstUnidadDidactica(unidadDidSved);
                                    relMat.setUsuarioModifico(unidadDidSved.getUsuarioModifico());

                                    unidadDidSved.getRelUniDidacticaMaterial().add(relMat);
                                }
                            }

                            // Se genera relacion para persistir competencias especificas
                            if (!ObjectUtils.isNullOrEmpty(unidad.getTiposCompetenciaXEje())) {
                                unidadDidSved.setRelUDidacticaTposCompetencia(new ArrayList<>());
                                for (String cmpEsp : unidad.getTiposCompetenciaXEje()) {

                                    RelUDidacticaTposCompetenciaDTO rel = new RelUDidacticaTposCompetenciaDTO();
                                    rel.setCatCompetenciaEspecifica(catCompEspecificaService.buscarPorId(Integer.parseInt(cmpEsp), CatCompetenciaEspecifica.class));
                                    rel.setDetEstUnidadDidactica(unidadDidSved);
                                    rel.setFechaRegistro(fechaReg);
                                    rel.setUsuarioModifico(unidadDidSved.getUsuarioModifico());

                                    unidadDidSved.getRelUDidacticaTposCompetencia().add(rel);
                                }
                            }

                            //Se guarda la relacion con tema 
                            EstructuraTematicaDTO estTematica = estructuraTematicaService.obtieneEstTematicaTemaPorPrograma(programa.getIdPrograma());
                            if (ObjectUtils.isNotNull(estTematica)) {

                                String nombreUnidad = "";
                                if (unidad.getNombreUnidad().contains(",")) {
                                    String[] splitNombresUnidad = unidad.getNombreUnidad().split("\\,");

                                    if (!ObjectUtils.isNullOrEmpty(splitNombresUnidad)) {
                                        for (String temasRel : splitNombresUnidad) {
                                            unidadDidSved.getRelEstructuraUnidadDidacticas().add(
                                                    this.generaRelEstUnidDidTema(estTematica.getIdEstructuraTematica(), temasRel, fechaReg, unidadDidSved));
                                        }
                                    }
                                } else {
                                    nombreUnidad = unidad.getNombreUnidad();
                                    unidadDidSved.getRelEstructuraUnidadDidacticas().add(
                                            this.generaRelEstUnidDidTema(estTematica.getIdEstructuraTematica(), nombreUnidad, fechaReg, unidadDidSved));
                                }

                            }

                            //Se Actualiza La unidad didactica para generar las relaciones
                            unidadDidSved.setFechaActualizacion(fechaReg);
                            estUnidadDidacticaService.actualizar(unidadDidSved);
                        }
                    }
                }

                resultado = this.guardarDatosPrograma(programa);
            } // Fin else

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            resultado.setMensajeError(MensajesSistemaEnum.ADMIN_MSG_GUARDADO_FALLIDO);
            throw e;
        }

        return resultado;
    }

    /**
     *
     * @param tema
     * @param fechaReg
     * @param unidadDidactica
     * @return
     */
    private RelEstUnidadDidacticaDTO generaRelEstUnidDidTema(Integer idEstTematica, String tema, Date fechaReg, DetEstUniDidacticaDTO unidadDidactica) {

        DetEtematicaTemaDTO temaGuardado = estructuraTematicaService.obtieneDetEtematicaTema(idEstTematica, tema);
        if (ObjectUtils.isNotNull(temaGuardado)) {
            RelEstUnidadDidacticaDTO relEstUnidDid = new RelEstUnidadDidacticaDTO();
            relEstUnidDid.setDetEtematicaTema(temaGuardado);
            relEstUnidDid.setFechaRegistro(fechaReg);
            relEstUnidDid.setDetEstUnidadDidactica(unidadDidactica);
            relEstUnidDid.setUsuarioModifico(unidadDidactica.getUsuarioModifico());

            return relEstUnidDid;
        }
        return null;

    }

    /**
     *
     * @param usuarioMod
     * @param fechaReg
     * @param subtemas
     * @return
     */
    private List<SubtemasUDidacticaDTO> obtieneSubtemasUnidDidactica(DetEstUniDidacticaDTO estUnidDid, List<SubtemasUDidacticaDTO> subtemas) {

        if (ObjectUtils.isNotNull(subtemas)) {
            for (SubtemasUDidacticaDTO stema : subtemas) {

                stema.setFechaRegistro(estUnidDid.getFechaRegistro());
                stema.setActivo(ConstantesGestor.ACTIVO);
                stema.setUsuarioModifico(estUnidDid.getUsuarioModifico());
                stema.setDetEstUnidadDidactica(estUnidDid);
            }
        }

        return subtemas;
    }

    /**
     *
     * @param idTema
     * @return
     */
    private List<Integer> listIdTemasPorUnidadDidactica(Integer estTem, Integer idTema) {
        List<Integer> aux = null;
        List<RelEstUnidadDidacticaDTO> rel = estructuraTematicaService.obtieneRelacionesPorEstTematica(idTema);
        if (ObjectUtils.isNotNull(rel)) {
            aux = new ArrayList<>();
            for (RelEstUnidadDidacticaDTO ud : rel) {
                String[] nombreTemas = ud.getDetEstUnidadDidactica().getTituloUa().split("\\,");
                if (ObjectUtils.isNotNull(nombreTemas) && nombreTemas.length > 1) {
                    for (String ntema : nombreTemas) {
                        aux.add(estructuraTematicaService.obtieneDetEtematicaTema(estTem, ntema).getIdDetTema());
                    }
                }
            }
        }

        return aux;
    }

    /**
     *
     * @param unidadesDidacticas
     */
    private List<Integer> depuraTemasCompuestos(Integer estTem, List<Integer> temasPorEstTematica) {

        List<Integer> auxDep = null;
        if (ObjectUtils.isNotNull(temasPorEstTematica)) {
            auxDep = new ArrayList<>();
            for (Integer idT : temasPorEstTematica) {
                List<Integer> temasCompuestos = this.listIdTemasPorUnidadDidactica(estTem, idT);
                if (!ObjectUtils.isNullOrEmpty(temasCompuestos)) {
                    if (!auxDep.contains(temasCompuestos.get(0))) {
                        auxDep.add(temasCompuestos.get(0));
                    }
                } else {
                    auxDep.add(idT);
                }

            }
        }

        return auxDep;

    }

    /**
     * Genera la clonacion de un programa con base al criterio del usuario si
     * desea clonar el programa con o sin estructura tematica y unidades
     * didacticas
     *
     * @param progClon
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public ResultadoDTO<FichaDescProgramaDTO> clonaProgramaCapacitacion(FichaDescProgramaDTO progClon, boolean clonaEstyUnidDid) {
        ResultadoDTO<FichaDescProgramaDTO> resClonacion = new ResultadoDTO<>();

        List<RelProgDuracionDTO> duracionProg = progClon.getRelProgramaDuracion();
        List<RelProgAutoreDTO> autoresProg = progClon.getRelProgramaAutores();
        List<RelProgResponsableDTO> responsablesProg = progClon.getRelProgramaResponsables();
        List<RelProgCompEspecificaDTO> comEspecificasProg = progClon.getRelProgramaComEspecificas();

        //EstructuraTematicaDTO estructuraTemClon = null;
        Map<Integer, String> mapaTemas = null;
        List<DetEtematicaTemaDTO> detTemasClon = null;
        HashMap<Integer, List<DetEstUniDidacticaDTO>> temaUDs = null;

        EstructuraTematicaDTO estructuraTemClon = estructuraTematicaService.obtieneEstTematicaTemaPorPrograma(progClon.getIdPrograma());

        if (ObjectUtils.isNotNull(estructuraTemClon)) {
            detTemasClon = estructuraTemClon.getDetEtematicaTemas();
        }

        if (clonaEstyUnidDid && ObjectUtils.isNotNull(detTemasClon)) {

            List<Integer> idTemas = detTemasClon.stream().map(t -> t.getIdDetTema()).collect(Collectors.toList());
            mapaTemas = detTemasClon.stream().collect(Collectors.toMap(DetEtematicaTemaDTO::getIdDetTema, DetEtematicaTemaDTO::getNombreTema));
            //Depurar la lista de temas

            idTemas = depuraTemasCompuestos(estructuraTemClon.getIdEstructuraTematica(), idTemas);
            //Se obtiene la relacion de tema y unidades didacticas
            temaUDs = new HashMap<>();
            for (Integer idTema : idTemas) {
                List<RelEstUnidadDidacticaDTO> rel = estructuraTematicaService.obtieneRelacionesPorEstTematica(idTema);
                List<DetEstUniDidacticaDTO> unidades = rel.stream().map(r -> r.getDetEstUnidadDidactica()).collect(Collectors.toList());

                if (temaUDs.containsKey(idTema)) {
                    temaUDs.get(idTema).addAll(unidades);
                } else {
                    temaUDs.put(idTema, unidades);
                }
            }
        }

        //Se setean a nulas las relaciones del programa
        progClon.setRelProgramaAutores(null);
        progClon.setRelProgramaComEspecificas(null);
        progClon.setRelProgramaDuracion(null);
        progClon.setRelProgramaResponsables(null);
        progClon.setIdPrograma(null);
        progClon.setIdentificadorFinal(null);
        progClon.setCatStatusPrograma(catStatusProgramaService.buscarPorId(EstatusProgramaEnum.BORRADOR.getId(), CatStatusPrograma.class));

        Date fechaActual = new Date();
        progClon.setFechaRegistro(fechaActual);
        progClon.setFechaActualizacion(fechaActual);
        progClon.setFechaSolicitud(DateUtils.agregaDiaHabilAFecha(progClon.getFechaVigInicial()));
        progClon.setFechaProduccion(DateUtils.agregaDiaHabilAFecha(progClon.getFechaSolicitud()));
        progClon.setFechaArranque(DateUtils.agregaDiaHabilAFecha(progClon.getFechaProduccion()));

        try {
            //Se guarda el programa para obtener el nuevo identificador
            resClonacion = fichaDescProgramaService.guardar(progClon);

            if (ObjectUtils.isNotNull(resClonacion) && resClonacion.getResultado().getValor()) {
                final FichaDescProgramaDTO progSaved = resClonacion.getDto();

                //Se setea el nuevo id del programa a las relaciones
                duracionProg.stream().forEach(d -> {
                    d.setFichaDescriptivaPrograma(progSaved);
                    d.setFechaRegistro(progSaved.getFechaRegistro());
                });

                autoresProg.stream().forEach(a -> {
                    a.setFichaDescriptivaPrograma(progSaved);
                    a.setFechaRegistro(progSaved.getFechaRegistro());
                });

                responsablesProg.stream().forEach(r -> {
                    r.setFichaDescriptivaPrograma(progSaved);
                    r.setFechaRegistro(progSaved.getFechaRegistro());
                });

                comEspecificasProg.stream().forEach(c -> {
                    c.setFichaDescProgramaDTO(progSaved);
                    c.setFechaRegistro(progSaved.getFechaRegistro());
                });

                progSaved.setRelProgramaDuracion(duracionProg);
                progSaved.setRelProgramaComEspecificas(comEspecificasProg);
                progSaved.setRelProgramaAutores(autoresProg);
                progSaved.setRelProgramaResponsables(responsablesProg);

                ResultadoDTO<FichaDescProgramaDTO> resTX = fichaDescProgramaService.actualizar(progSaved);

                if (ObjectUtils.isNotNull(resTX) && resTX.getResultado().getValor()) {

                    if (clonaEstyUnidDid && ObjectUtils.isNotNull(estructuraTemClon)) {

                        //Persistencia de una nueva estructura tematica
                        estructuraTemClon.setIdEstructuraTematica(null);
                        estructuraTemClon.setTblFichaDescriptivaPrograma(progSaved);
                        estructuraTemClon.setFechaRegistro(progSaved.getFechaRegistro());
                        estructuraTemClon.setUsuarioModifico(progSaved.getUsuarioModifico());

                        detTemasClon.stream().forEach(t -> {
                            t.setIdDetTema(null);
                            t.setTblEstructuraTematica(estructuraTemClon);
                        });

                        estructuraTemClon.setDetEtematicaTemas(detTemasClon);

                        //Se guarda la copia de la estructura tematica y sus temas
                        ResultadoDTO<EstructuraTematicaDTO> resTxEstTematica = estructuraTematicaService.guardar(estructuraTemClon);

                        if (ObjectUtils.isNotNull(resTxEstTematica) && resTxEstTematica.getResultado().getValor()) {

                            EstructuraTematicaDTO estructuraClonSaved = resTxEstTematica.getDto();

                            // ******* Procesamiento de Unidades Didacticas ****************
                            if (clonaEstyUnidDid && !ObjectUtils.isNullOrEmpty(temaUDs)) {

                                for (Entry<Integer, List<DetEstUniDidacticaDTO>> entryTemas : temaUDs.entrySet()) {

                                    for (DetEstUniDidacticaDTO uxt : entryTemas.getValue()) {

                                        List<RelUniDidacticaMaterialDTO> materialDid = uxt.getRelUniDidacticaMaterial();
                                        List<RelUDidacticaTposCompetenciaDTO> cmpsEspecif = uxt.getRelUDidacticaTposCompetencia();
                                        List<SubtemasUDidacticaDTO> subtemasUD = uxt.getSubtemasUdidactica();

                                        uxt.setIdUnidadDidactica(null);
                                        uxt.setRelUniDidacticaMaterial(null);
                                        uxt.setRelUDidacticaTposCompetencia(null);
                                        uxt.setSubtemasUdidactica(null);
                                        uxt.setRelEstructuraUnidadDidacticas(null);

                                        List<DetEtematicaTemaDTO> temasPersistidos = estructuraClonSaved.getDetEtematicaTemas();

                                        ResultadoDTO<DetEstUniDidacticaDTO> resUD = estUnidadDidacticaService.guardar(uxt);

                                        if (ObjectUtils.isNotNull(resUD) && resUD.getResultado().getValor()) {

                                            DetEstUniDidacticaDTO uniDidSaved = resUD.getDto();

                                            materialDid.stream().forEach(md -> {
                                                md.setDetEstUnidadDidactica(uniDidSaved);
                                                md.setFechaRegistro(uniDidSaved.getFechaRegistro());
                                                md.setUsuarioModifico(uniDidSaved.getUsuarioModifico());
                                            });

                                            cmpsEspecif.stream().forEach(ce -> {
                                                ce.setDetEstUnidadDidactica(uniDidSaved);
                                                ce.setFechaRegistro(uniDidSaved.getFechaRegistro());
                                                ce.setUsuarioModifico(uniDidSaved.getUsuarioModifico());
                                            });

                                            subtemasUD.stream().forEach(st -> {
                                                st.setIdSubtemaUdidactica(null);
                                                st.setDetEstUnidadDidactica(uniDidSaved);
                                                st.setFechaRegistro(uniDidSaved.getFechaRegistro());
                                                st.setUsuarioModifico(uniDidSaved.getUsuarioModifico());
                                            });

                                            uniDidSaved.setRelUDidacticaTposCompetencia(cmpsEspecif);
                                            uniDidSaved.setRelUniDidacticaMaterial(materialDid);
                                            uniDidSaved.setSubtemasUdidactica(subtemasUD);

                                            RelEstUnidadDidacticaDTO relEstUniDid = new RelEstUnidadDidacticaDTO();
                                            relEstUniDid.setDetEstUnidadDidactica(uniDidSaved);

                                            relEstUniDid.setDetEtematicaTema(
                                                    obtieneTemaEstructuraByNombre(temasPersistidos, mapaTemas.get(entryTemas.getKey())));

                                            relEstUniDid.setFechaRegistro(uniDidSaved.getFechaRegistro());
                                            relEstUniDid.setUsuarioModifico(uniDidSaved.getUsuarioModifico());
                                            uniDidSaved.getRelEstructuraUnidadDidacticas().add(relEstUniDid);

                                            estUnidadDidacticaService.actualizar(uniDidSaved);

                                        }

                                    }

                                }
                            } // Fin unidades Didacticas
                        }

                    }

                }

            }

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            resClonacion.setMensajeError(MensajesSistemaEnum.ADMIN_MSG_GUARDADO_FALLIDO);
            throw e;
        }

        return resClonacion;
    }

    /**
     *
     * @param list
     * @param nombreTema
     * @return
     */
    private DetEtematicaTemaDTO obtieneTemaEstructuraByNombre(List<DetEtematicaTemaDTO> list, String nombreTema) {

        if (ObjectUtils.isNotNull(list)) {
            for (DetEtematicaTemaDTO temaAux : list) {
                if (temaAux.getNombreTema().equalsIgnoreCase(nombreTema)) {
                    return temaAux;
                }
            }
        }
        return null;
    }

    /**
     * @return the mallaCurricularService
     */
    public MallaCurricularService getMallaCurricularService() {
        return mallaCurricularService;
    }

    /**
     * @return the fichaDescProgramaService
     */
    public FichaDescProgramaService getFichaDescProgramaService() {
        return fichaDescProgramaService;
    }

    /**
     * @return the estructuraTematicaService
     */
    public EstructuraTematicaService getEstructuraTematicaService() {
        return estructuraTematicaService;
    }

    /**
     * @return the moduloMoodleService
     */
    public ModuloMoodleService getModuloMoodleService() {
        return moduloMoodleService;
    }

    /**
     * @return the eTematicaModuloMdlService
     */
    public ETematicaModuloMdlService geteTematicaModuloMdlService() {
        return eTematicaModuloMdlService;
    }

    /**
     * @return the objetoCurricularService
     */
    public CatalogoComunService<CatObjetoCurricular, Integer> getObjetoCurricularService() {
        return objetoCurricularService;
    }

    /**
     * @return the planService
     */
    public PlanService getPlanService() {
        return planService;
    }

    /**
     * @return the catStatusProgramaService
     */
    public CatalogoComunService<CatStatusPrograma, Integer> getCatStatusProgramaService() {
        return catStatusProgramaService;
    }

    /**
     * @return the ejeCompetenciaService
     */
    public EjeCompetenciaService getEjeCompetenciaService() {
        return ejeCompetenciaService;
    }

    /**
     * @return the estUnidadDidacticaService
     */
    public EstUnidadDidacticaService getEstUnidadDidacticaService() {
        return estUnidadDidacticaService;
    }

    /**
     * @return the orgGubernamentalService
     */
    public OrgGubernamentalService getOrgGubernamentalService() {
        return orgGubernamentalService;
    }

    /**
     * @return the estPersonalExternoService
     */
    public EstPersonalExternoService getEstPersonalExternoService() {
        return estPersonalExternoService;
    }

    /**
     * @return the competenciaEspecificaService
     */
    public CompetenciaEspecificaService getCompetenciaEspecificaService() {
        return competenciaEspecificaService;
    }

    public PersonaResponsabilidadesService getPersonaResponsabilidadesService() {
        return personaResponsabilidadesService;
    }

    public void setPersonaResponsabilidadesService(PersonaResponsabilidadesService personaResponsabilidadesService) {
        this.personaResponsabilidadesService = personaResponsabilidadesService;
    }

    /**
     * @return the notificacionSistemaService
     */
    public NotificacionSistemaService getNotificacionSistemaService() {
        return notificacionSistemaService;
    }

    /**
     * @param notificacionSistemaService the notificacionSistemaService to set
     */
    public void setNotificacionSistemaService(NotificacionSistemaService notificacionSistemaService) {
        this.notificacionSistemaService = notificacionSistemaService;
    }

    /**
     * @return the roleService
     */
    public RoleService getRoleService() {
        return roleService;
    }

    /**
     * @param roleService the roleService to set
     */
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    /**
     * @return the personaService
     */
    public PersonaService getPersonaService() {
        return personaService;
    }

    /**
     * @param personaService the personaService to set
     */
    public void setPersonaService(PersonaService personaService) {
        this.personaService = personaService;
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
