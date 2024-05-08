package mx.gob.sedesol.gestorweb.beans.planesprogramas;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import mx.gob.sedesol.basegestor.commons.constantes.ConstantesBitacora;

import org.apache.log4j.Logger;
import org.primefaces.component.panel.Panel;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import mx.gob.sedesol.basegestor.commons.dto.NodoDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.CatalogoComunDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.EstructuraTematicaDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.FichaDescProgramaDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.MallaCurricularDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.PlanDTO;
import mx.gob.sedesol.basegestor.commons.utils.MensajesSistemaEnum;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.ObjetoCurricularEnum;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.CatObjetoCurricular;
import mx.gob.sedesol.basegestor.service.impl.planesyprogramas.FECServiceFacade;
import mx.gob.sedesol.gestorweb.beans.acceso.BaseBean;
import mx.gob.sedesol.gestorweb.commons.constantes.ConstantesGestorWeb;
import mx.gob.sedesol.gestorweb.commons.utils.BitacoraUtil;
import mx.gob.sedesol.gestorweb.sistema.SistemaBean;

@ViewScoped
@ManagedBean
public class PlanProgramaBean extends BaseBean {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(PlanProgramaBean.class);

    private List<MallaCurricularDTO> estructuraPlanes;

    @ManagedProperty("#{sistema}")
    private SistemaBean textosSistema;

    @ManagedProperty(value = "#{fecServiceFacade}")
    private FECServiceFacade fecServiceFacade;

    private TreeNode objCurrSelec;
    private boolean objCurrPrograma;
    private boolean muestraComboObjsCurr;

    private MallaCurricularDTO objetoCurrNuevo;
    private CatalogoComunDTO tipoObjCurrSel;
    private List<CatalogoComunDTO> tiposObjCurr;

    private List<TreeNode> programasPorNodoSel;

    private MallaCurricularDTO nuevoPlan;
    private List<TreeNode> lstArbolesPlanes;
    private boolean muestraProgsXNodo;

    private Panel panelMallaCurr;

    //Estructura Tematica
    private TreeNode objTemarioProgSel;
    private TreeNode temarioXProg;
    private EstructuraTematicaDTO nuevoTemarioXProg;
    private FichaDescProgramaDTO fichaProgramaSelec;

    private boolean muestraArbolTemasXProg;
    private boolean muestraPnlAddTemario;
    private boolean muestraPanelAgregar;
    private boolean muestraPnlAgregaTemas;
    private EstructuraTematicaDTO subTema;
    private boolean orientHorizontalDef;
    private String tipoOrientacionPFTree;

    //PLANES
    private PlanDTO planSelec;

    public PlanProgramaBean() {

        objetoCurrNuevo = new MallaCurricularDTO();
        tipoObjCurrSel = new CatalogoComunDTO();
        nuevoPlan = new MallaCurricularDTO();
        tipoOrientacionPFTree = ConstantesGestorWeb.PF_CMP_TREE_HORIZONTAL;
        estructuraPlanes = new ArrayList<>();

        planSelec = (PlanDTO) getSession().getAttribute(ConstantesGestorWeb.OBJ_PLAN_SELEC);
        if (ObjectUtils.isNotNull(planSelec)) {
            getSession().removeAttribute(ConstantesGestorWeb.OBJ_PLAN_SELEC);

        }
    }

    @PostConstruct
    public void generaEstructuraPlan() {

        MallaCurricularDTO mallaPlanSel = fecServiceFacade.getMallaCurricularService().obtenerMallaCurricularPorIdPlan(planSelec.getIdPlan());
        
        estructuraPlanes.add(mallaPlanSel);
        //estructuraPlanes = getFecServiceFacade().getMallaCurricularService().obtieneMallasCurricularesDisponibles();
        //Crea la estructura de planes
        if (!ObjectUtils.isNullOrEmpty(estructuraPlanes)) {
        	
        	if(estructuraPlanes.get(0) != null) {        		
        	
	            lstArbolesPlanes = new ArrayList<TreeNode>();
	
	            for (MallaCurricularDTO plan : estructuraPlanes) {
	                TreeNode arbolPlanesProgramas = new DefaultTreeNode(new NodoDTO(plan.getId(), plan.getNombre(), plan.getObjetoCurricular().getNombre(), planSelec.getIdCategoriaMdl()), null);
	                arbolPlanesProgramas.setExpanded(true);
	                generaEstructuraInternaPlan(arbolPlanesProgramas, plan.getLstHijosMallaCurr());
	                lstArbolesPlanes.add(arbolPlanesProgramas);
	            }
	            
        	}
        }

    }

    /**
     * Metodo recursivo para generar la estructura del Arbol
     *
     * @param nodoPadre
     * @param mallasHijo
     */
    private void generaEstructuraInternaPlan(TreeNode nodoPadre, List<MallaCurricularDTO> mallasHijo) {
        if (!ObjectUtils.isNullOrEmpty(mallasHijo)) {
            for (MallaCurricularDTO hijo : mallasHijo) {
                TreeNode nodoHijo = new DefaultTreeNode(new NodoDTO(hijo.getId(), hijo.getNombre(), hijo.getObjetoCurricular().getNombre()), nodoPadre);
                nodoHijo.setExpanded(true);
                if (!ObjectUtils.isNullOrEmpty(hijo.getLstHijosMallaCurr())) {
                    generaEstructuraInternaPlan(nodoHijo, hijo.getLstHijosMallaCurr());
                }
            }
        }
    }

    /**
     *
     * @param event
     */
    public void onNodeSelect(NodeSelectEvent event) {
        objCurrSelec = event.getTreeNode();
        setMuestraProgsXNodo(Boolean.FALSE);
        setMuestraComboObjsCurr(Boolean.FALSE);
        setMuestraArbolTemasXProg(Boolean.FALSE);

    }

    /**
     * Valida el tipo de objeto curricular para llenar el combo de seleccion
     */
    public void validaTipoObjCurricular() {
        if (ObjectUtils.isNotNull(objCurrSelec)) {

            tiposObjCurr = new ArrayList<>();

            setMuestraPanelAgregar(Boolean.TRUE);
            setMuestraProgsXNodo(Boolean.FALSE);
            setMuestraComboObjsCurr(Boolean.TRUE);
            setMuestraPnlAgregaTemas(Boolean.FALSE);

            NodoDTO datosNodo = (NodoDTO) objCurrSelec.getData();

            if (ObjectUtils.isNotNull(datosNodo)) {
                List<CatalogoComunDTO> objetosCurriculares = getObjsCurricularesSinPrograma();
                //getFecServiceFacade().getObjetoCurricularService().findAll(CatObjetoCurricular.class);

                setObjCurrPrograma(Boolean.FALSE);
                switch (ObjetoCurricularEnum.getEnumPorNombreObjeto(datosNodo.getNombreObjCurricular())) {

                    case PLAN:
                        for (CatalogoComunDTO obj : objetosCurriculares) {
                            if (!obj.getNombre().equals(ObjetoCurricularEnum.PLAN.getNombre())) {
                                tiposObjCurr.add(obj);
                            }
                        }
                        break;

                    case ESTRUCTURA:
                        for (CatalogoComunDTO obj : objetosCurriculares) {
                            if (!obj.getNombre().equals(ObjetoCurricularEnum.ESTRUCTURA.getNombre())
                                    && !obj.getNombre().equals(ObjetoCurricularEnum.PLAN.getNombre())) {
                                tiposObjCurr.add(obj);
                            }
                        }
                        break;

                    case SUB_ESTRUCTURA:
                        for (CatalogoComunDTO obj : objetosCurriculares) {
                            if (!obj.getNombre().equals(ObjetoCurricularEnum.ESTRUCTURA.getNombre())
                                    && !obj.getNombre().equals(ObjetoCurricularEnum.PLAN.getNombre())) {
                                tiposObjCurr.add(obj);
                            }
                        }
                        break;

                    case PROGRAMA:
                        //RN: Se modifico el requerimiento y no se permitiran agregar planes ni programas
                        /*setObjCurrPrograma(Boolean.TRUE);
					setMuestraComboObjsCurr(Boolean.FALSE);
					agregarMsgError("No se puede agregar un subPrograma", null);
                         */
                        break;

                    case TEMA:
                        break;

                }
            }

        }
    }

    @SuppressWarnings("unchecked")
    private List<CatalogoComunDTO> getObjsCurricularesSinPrograma() {
        List<CatalogoComunDTO> rs = new ArrayList<>();
        List<CatalogoComunDTO> objs = (List<CatalogoComunDTO>) getSession().getServletContext().getAttribute(ConstantesGestorWeb.CAT_OBJ_CURRICULAR);
        for (CatalogoComunDTO o : objs) {
            if (!o.getNombre().equals(ObjetoCurricularEnum.PROGRAMA.getNombre())) {
                rs.add(o);
            }
        }

        return rs;
    }

    /**
     * Elimina un objeto curricular del arbol general y de la BD
     */
    public void eliminaObjCurricular() {

        if (ObjectUtils.isNotNull(objCurrSelec)) {

            setMuestraPanelAgregar(Boolean.FALSE);
            NodoDTO nodoSel = (NodoDTO) objCurrSelec.getData();
            MallaCurricularDTO mallaCurrAElim = getFecServiceFacade().getMallaCurricularService().buscarPorId(nodoSel.getId());

            if (ObjectUtils.isNotNull(mallaCurrAElim.getMallaCurricularPadre())) {
                //GUSTAVO --BitacoraUtil.llenarBitacora(nuevoPlan, getUsuarioEnSession(), ConstantesBitacora.MALLA_CUR_ELIMINAR, request);
                ResultadoDTO<MallaCurricularDTO> resultado = getFecServiceFacade().eliminaMallaCurricular(mallaCurrAElim);
                if (ObjectUtils.isNotNull(resultado) && resultado.getResultado().getValor()) {
                    TreeNode parent = objCurrSelec.getParent();
                    if (ObjectUtils.isNotNull(parent) && !parent.getChildren().isEmpty()) {
                        parent.getChildren().remove(objCurrSelec);
                    }
                }
            } else {
                agregarMsgWarn("No se puede eliminar el Nodo padre", null);
            }
        }
    }

    /**
     * Muestra los programas del nodo seleccionado en una lista
     */
    public void muestraProgramasDelNodo() {
        if (ObjectUtils.isNotNull(objCurrSelec) && ObjectUtils.isNotNull(objCurrSelec.getData())) {

            setMuestraPanelAgregar(Boolean.FALSE);
            NodoDTO nodoSel = (NodoDTO) objCurrSelec.getData();
            if (nodoSel.getNombreObjCurricular().equalsIgnoreCase(ObjetoCurricularEnum.ESTRUCTURA.getNombre())
                    || nodoSel.getNombreObjCurricular().equalsIgnoreCase(ObjetoCurricularEnum.SUB_ESTRUCTURA.getNombre())) {

                setMuestraProgsXNodo(Boolean.TRUE);
                programasPorNodoSel = new ArrayList<TreeNode>();
                for (TreeNode nodoProg : objCurrSelec.getChildren()) {
                    if (((NodoDTO) nodoProg.getData()).getNombreObjCurricular()
                            .equalsIgnoreCase(ObjetoCurricularEnum.PROGRAMA.getNombre())) {
                        programasPorNodoSel.add(nodoProg);
                    }
                }
            }
        }
    }

    /**
     * Metodo que genera la estructura tematica
     */
    public void generaEstructuraTematicaProg() {
        if (ObjectUtils.isNotNull(objCurrSelec) && ObjectUtils.isNotNull(objCurrSelec.getData())) {
            NodoDTO nodoSel = (NodoDTO) objCurrSelec.getData();
            if (nodoSel.getNombreObjCurricular().equalsIgnoreCase(ObjetoCurricularEnum.PROGRAMA.getNombre())) {
                //Se obtiene el identificador del plan

                fichaProgramaSelec = getFecServiceFacade().getFichaDescProgramaService().buscarPorId(nodoSel.getId());
                //lstTemasPrograma = new ArrayList<>();
                if (ObjectUtils.isNotNull(fichaProgramaSelec) && ObjectUtils.isNotNull(fichaProgramaSelec.getTblEstructuraTematica())) {

                    this.generaEstIntTematicaPrograma(fichaProgramaSelec.getTblEstructuraTematica());
                    setMuestraArbolTemasXProg(Boolean.TRUE);
                    setMuestraPanelAgregar(Boolean.FALSE);
                } else {
                    //No existen temas asociados al Programa
                    nuevoTemarioXProg = new EstructuraTematicaDTO();
                    setMuestraPanelAgregar(Boolean.TRUE);
                    setMuestraPnlAddTemario(Boolean.TRUE);
                    setMuestraComboObjsCurr(Boolean.FALSE);
                }
            }
        }
    }

    /**
     *
     * @param fichaDescProg
     */
    private void generaEstIntTematicaPrograma(EstructuraTematicaDTO temarioPrograma) {

//		temarioXProg = new DefaultTreeNode(new NodoDTO(temarioPrograma.getIdEstructuraTematica(), 
//						temarioPrograma.getNombreTema(), ObjetoCurricularEnum.TEMA.getNombre()),null);
//		temarioXProg.setExpanded(true);
        //generaEstructuraTematicaXPlan(temarioXProg, temarioPrograma.getLstHijosEstTematica());
    }

    /**
     * Metodo recursivo para generar la estructura tematica x plan
     *
     * @param nodoPadre
     * @param mallasHijo
     */
    private void generaEstructuraTematicaXPlan(TreeNode nodoPadre, List<EstructuraTematicaDTO> mallasHijo) {
        if (!ObjectUtils.isNullOrEmpty(mallasHijo)) {
            for (EstructuraTematicaDTO hijo : mallasHijo) {
//				TreeNode nodoHijo = new DefaultTreeNode(new NodoDTO(hijo.getIdEstructuraTematica(), 
//						hijo.getNombreTema(), ObjetoCurricularEnum.TEMA.getNombre()), nodoPadre); 
//				nodoHijo.setExpanded(true);
//				if(!ObjectUtils.isNullOrEmpty(hijo.getLstHijosEstTematica())){
//					generaEstructuraTematicaXPlan(nodoHijo, hijo.getLstHijosEstTematica());
//				}
            }
        }
    }

    /**
     * Agrega un nuevo temario al Programa
     */
    public void agregaTemarioAPrograma() {

        if (ObjectUtils.isNotNull(nuevoTemarioXProg) && ObjectUtils.isNotNull(fichaProgramaSelec)) {
            nuevoTemarioXProg.setFechaRegistro(new Date());
            nuevoTemarioXProg.setUsuarioModifico(getUsuarioEnSession().getIdPersona());
            nuevoTemarioXProg.setActivo(ConstantesGestorWeb.ACTIVO);
            //GUSTAVO --BitacoraUtil.llenarBitacora(nuevoTemarioXProg, getUsuarioEnSession(), ConstantesBitacora.EST_TEMATICA_AGREGAR, request);
            ResultadoDTO<EstructuraTematicaDTO> txRes = getFecServiceFacade().getEstructuraTematicaService().guardar(nuevoTemarioXProg);
            if (ObjectUtils.isNotNull(txRes) && txRes.getResultado().getValor()) {

                fichaProgramaSelec.setTblEstructuraTematica(txRes.getDto());
                fichaProgramaSelec.setFechaActualizacion(new Date());
                getFecServiceFacade().getFichaDescProgramaService().actualizar(fichaProgramaSelec);

                this.generaEstIntTematicaPrograma(fichaProgramaSelec.getTblEstructuraTematica());
                agregarMsgInfo(MensajesSistemaEnum.ADMIN_MSG_GUARDADO_EXITOSO.getId(), null, textosSistema);
                setMuestraArbolTemasXProg(Boolean.TRUE);
                setMuestraPanelAgregar(Boolean.FALSE);
            } else {
                agregarMsgError(MensajesSistemaEnum.ADMIN_MSG_GUARDADO_FALLIDO.getId(), null, textosSistema);
            }
        }
    }

    /**
     * Metodo persistente para generar un hijo (registro) en el nodo
     * seleccionado del arbol
     */
    public void guardarNodoPlanPrograma() {

        try {
            if (datosValidosNuevoNodo()) {

                NodoDTO datosNodo = (NodoDTO) objCurrSelec.getData();

                tipoObjCurrSel = getFecServiceFacade().getObjetoCurricularService().buscarPorId(tipoObjCurrSel.getId(), CatObjetoCurricular.class);

                objetoCurrNuevo.setObjetoCurricular(tipoObjCurrSel);
                objetoCurrNuevo.setMallaCurricularPadre(
                        getFecServiceFacade().getMallaCurricularService().buscarPorId(datosNodo.getId()));
                objetoCurrNuevo.setFechaRegistro(new Date());
                objetoCurrNuevo.setNombreUsuarioMod(getUsuarioEnSession().getUsuario());
                objetoCurrNuevo.setUsuarioModifico(getUsuarioEnSession().getIdPersona());
                objetoCurrNuevo.setActivo(ConstantesGestorWeb.ACTIVO);
                objetoCurrNuevo.setIdCategoriaMdlPadre(
                        objetoCurrNuevo.getMallaCurricularPadre() != null
                        ? objetoCurrNuevo.getMallaCurricularPadre().getIdCategoriaMdl() : null);
                //objetoCurrNuevo.setId(getFecServiceFacade().getMallaCurricularService().getNuevoIdMallaCurricular());
                //GUSTAVO --BitacoraUtil.llenarBitacora(nuevoPlan, getUsuarioEnSession(), ConstantesBitacora.MALLA_CURR_AGREGAR, request);
                ResultadoDTO<MallaCurricularDTO> res = getFecServiceFacade().guardaMallaCurricular(objetoCurrNuevo);
                if (ObjectUtils.isNotNull(res) && res.getResultado().getValor()) {

                    //Despues de persistir el objeto se agrega al componente Tree
                    objCurrSelec.getChildren().add(new DefaultTreeNode(new NodoDTO(res.getDto().getId(),
                            res.getDto().getNombre(), tipoObjCurrSel.getNombre()), objCurrSelec));
                    setMuestraComboObjsCurr(Boolean.FALSE);
                }

            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

    }

    /**
     * Agrega un nuevo Plan
     */
    public void agregaPlanGeneral() {

        try {
            if (ObjectUtils.isNotNull(nuevoPlan)) {
                nuevoPlan.setActivo(ConstantesGestorWeb.ACTIVO);
                nuevoPlan.setFechaRegistro(new Date());
                nuevoPlan.setNombreUsuarioMod(getUsuarioEnSession().getUsuario());
                nuevoPlan.setObjetoCurricular(getFecServiceFacade().getObjetoCurricularService()
                        .buscarRegistroPorNombre(ObjetoCurricularEnum.PLAN.getNombre(), CatObjetoCurricular.class));
                nuevoPlan.setUsuarioModifico(getUsuarioEnSession().getIdPersona());
                //nuevoPlan.setId(getFecServiceFacade().getMallaCurricularService().getNuevoIdMallaCurricular());
                //GUSTAVO --BitacoraUtil.llenarBitacora(nuevoPlan, getUsuarioEnSession(), ConstantesBitacora.MALLA_CURR_AGREGAR, request);
                ResultadoDTO<MallaCurricularDTO> res = getFecServiceFacade().guardaMallaCurricular(nuevoPlan);
                if (ObjectUtils.isNotNull(res) && res.getResultado().getValor()) {
                    //Vuelve a generar la estructura por consulta
                    this.generaEstructuraPlan();
                }

            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * Valida los datos del Nodo
     *
     * @return
     */
    private boolean datosValidosNuevoNodo() {

        logger.info("validando datos del nodo");

        boolean bnd = Boolean.TRUE;
        if (ObjectUtils.isNull(objCurrSelec) || ObjectUtils.isNull(objCurrSelec.getData())) {
            agregarMsgError("Seleccione el nodo al que desea agregar un hijo", null);
            bnd = Boolean.FALSE;
        }
        if (ObjectUtils.isNull(objetoCurrNuevo) || objetoCurrNuevo.getNombre().isEmpty()) {
            agregarMsgError("Indique el nombre del objeto curricular", null);
            bnd = Boolean.FALSE;
        }
        if (!isObjCurrPrograma() && ObjectUtils.isNull(tipoObjCurrSel)) {
            agregarMsgError("Seleccione un objeto curricular", null);
            bnd = Boolean.FALSE;
        }
        return bnd;
    }

    /**
     *
     * @param event
     */
    public void onTemaSelec(NodeSelectEvent event) {
        objTemarioProgSel = event.getTreeNode();
    }

    /**
     *
     */
    public void muestraPnlSubTemas() {
        subTema = new EstructuraTematicaDTO();
        setMuestraPnlAgregaTemas(Boolean.TRUE);
    }

    /**
     * Metodo para Agregar sub temas al temario seleccionado del programa
     */
    public void agregaSubTemasATemario() {

        try {
            if (ObjectUtils.isNotNull(objTemarioProgSel) && ObjectUtils.isNotNull(subTema)) {
                NodoDTO temaPadre = (NodoDTO) objTemarioProgSel.getData();
                EstructuraTematicaDTO temarioPadre = getFecServiceFacade().getEstructuraTematicaService().buscarPorId(temaPadre.getId());
                if (ObjectUtils.isNotNull(temarioPadre)) {

                    subTema.setActivo(ConstantesGestorWeb.ACTIVO);
                    subTema.setFechaRegistro(new Date());
                    subTema.setUsuarioModifico(getUsuarioEnSession().getIdPersona());
                    //GUSTAVO --BitacoraUtil.llenarBitacora(subTema, getUsuarioEnSession(), ConstantesBitacora.EST_TEMATICA_AGREGAR, request);
                    ResultadoDTO<EstructuraTematicaDTO> resTx = getFecServiceFacade().getEstructuraTematicaService().guardar(subTema);

                    if (ObjectUtils.isNotNull(resTx) && resTx.getResultado().getValor()) {

                        setMuestraPnlAgregaTemas(Boolean.FALSE);
                        agregarMsgInfo(MensajesSistemaEnum.ADMIN_MSG_GUARDADO_EXITOSO.getId(), null, textosSistema);

                    } else {
                        agregarMsgError(MensajesSistemaEnum.ADMIN_MSG_GUARDADO_FALLIDO.getId(), null, textosSistema);
                    }
                }
            }

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * Elimina una estructura tematica
     */
    public void eliminaEstTematica() {

        if (ObjectUtils.isNotNull(objTemarioProgSel)) {

            setMuestraPanelAgregar(Boolean.FALSE);
            NodoDTO nodoSel = (NodoDTO) objTemarioProgSel.getData();
            TreeNode isNodoPadreGral = objTemarioProgSel.getParent();

            EstructuraTematicaDTO estTematicaElim = getFecServiceFacade().getEstructuraTematicaService().buscarPorId(nodoSel.getId());
            if (ObjectUtils.isNull(isNodoPadreGral)) {
                //estTematicaElim.setIdFichaDescPrograma(fichaProgramaSelec.getIdPrograma());
            }

            //ResultadoDTO<MallaCurricularDTO> resultado = getFecServiceFacade().eliminaEstructuraTematica(estTematicaElim);
            ResultadoDTO<MallaCurricularDTO> resultado = null;
            if (ObjectUtils.isNotNull(resultado) && resultado.getResultado().getValor()) {
                TreeNode parent = objTemarioProgSel.getParent();
                if (ObjectUtils.isNotNull(parent) && !parent.getChildren().isEmpty()) {
                    parent.getChildren().remove(objTemarioProgSel);
                }
            }
        }

    }

    /**
     *
     */
    public void onSwitchOrientacion() {

        if (isOrientHorizontalDef()) {
            tipoOrientacionPFTree = ConstantesGestorWeb.PF_CMP_TREE_VERTICAL;
            setOrientHorizontalDef(Boolean.FALSE);
        } else {
            tipoOrientacionPFTree = ConstantesGestorWeb.PF_CMP_TREE_HORIZONTAL;
            setOrientHorizontalDef(Boolean.TRUE);
        }
    }

    /**
     * Navegacion hacia pantalla de recursos moodle
     */
    public String navegaActividadRecursoMoodle() {
        getSession().setAttribute(ConstantesGestorWeb.OBJ_TEMA_PROGRAMA_SELEC, (NodoDTO) objTemarioProgSel.getData());
        return ConstantesGestorWeb.NAVEGA_ACTIVIDADES_RECURSOS_MOODLE;
    }

    public TreeNode getObjTemarioProgSel() {
        return objTemarioProgSel;
    }

    public TreeNode getTemarioXProg() {
        return temarioXProg;
    }

    public void setObjTemarioProgSel(TreeNode objTemarioProgSel) {
        this.objTemarioProgSel = objTemarioProgSel;
    }

    public void setTemarioXProg(TreeNode temarioXProg) {
        this.temarioXProg = temarioXProg;
    }

    public boolean isMuestraPanelAgregar() {
        return muestraPanelAgregar;
    }

    public void setMuestraPanelAgregar(boolean muestraPanelAgregar) {
        this.muestraPanelAgregar = muestraPanelAgregar;
    }

    public FECServiceFacade getFecServiceFacade() {
        if (ObjectUtils.isNull(fecServiceFacade)) {
            fecServiceFacade = new FECServiceFacade();
        }

        return fecServiceFacade;
    }

    public void setFecServiceFacade(FECServiceFacade fecServiceFacade) {
        this.fecServiceFacade = fecServiceFacade;
    }

    /**
     * @return the tipoOrientacionPFTree
     */
    public String getTipoOrientacionPFTree() {
        return tipoOrientacionPFTree;
    }

    /**
     * @param tipoOrientacionPFTree the tipoOrientacionPFTree to set
     */
    public void setTipoOrientacionPFTree(String tipoOrientacionPFTree) {
        this.tipoOrientacionPFTree = tipoOrientacionPFTree;
    }

    /**
     * @return the orientHorizontalDef
     */
    public boolean isOrientHorizontalDef() {
        return orientHorizontalDef;
    }

    /**
     * @param orientHorizontalDef the orientHorizontalDef to set
     */
    public void setOrientHorizontalDef(boolean orientHorizontalDef) {
        this.orientHorizontalDef = orientHorizontalDef;
    }

    public boolean isMuestraPnlAgregaTemas() {
        return muestraPnlAgregaTemas;
    }

    public void setMuestraPnlAgregaTemas(boolean muestraPnlAgregaTemas) {
        this.muestraPnlAgregaTemas = muestraPnlAgregaTemas;
    }

    public boolean isMuestraPnlAddTemario() {
        return muestraPnlAddTemario;
    }

    public void setMuestraPnlAddTemario(boolean muestraPnlAddTemario) {
        this.muestraPnlAddTemario = muestraPnlAddTemario;
    }

    public FichaDescProgramaDTO getFichaProgramaSelec() {
        return fichaProgramaSelec;
    }

    public void setFichaProgramaSelec(FichaDescProgramaDTO fichaProgramaSelec) {
        this.fichaProgramaSelec = fichaProgramaSelec;
    }

    public SistemaBean getTextosSistema() {
        return textosSistema;
    }

    public void setTextosSistema(SistemaBean textosSistema) {
        this.textosSistema = textosSistema;
    }

    public boolean isMuestraArbolTemasXProg() {
        return muestraArbolTemasXProg;
    }

    public void setMuestraArbolTemasXProg(boolean muestraArbolTemasXProg) {
        this.muestraArbolTemasXProg = muestraArbolTemasXProg;
    }

    /**
     * @return the objCurrSelec
     */
    public TreeNode getObjCurrSelec() {
        return objCurrSelec;
    }

    /**
     * @param objCurrSelec the objCurrSelec to set
     */
    public void setObjCurrSelec(TreeNode objCurrSelec) {
        this.objCurrSelec = objCurrSelec;
    }

    /**
     * @return the estructuraPlanes
     */
    public List<MallaCurricularDTO> getEstructuraPlanes() {
        return estructuraPlanes;
    }

    /**
     * @param estructuraPlanes the estructuraPlanes to set
     */
    public void setEstructuraPlanes(List<MallaCurricularDTO> estructuraPlanes) {
        this.estructuraPlanes = estructuraPlanes;
    }

    public EstructuraTematicaDTO getSubTema() {
        return subTema;
    }

    public void setSubTema(EstructuraTematicaDTO subTema) {
        this.subTema = subTema;
    }

    /**
     * @return the objCurrPrograma
     */
    public boolean isObjCurrPrograma() {
        return objCurrPrograma;
    }

    /**
     * @param objCurrPrograma the objCurrPrograma to set
     */
    public void setObjCurrPrograma(boolean objCurrPrograma) {
        this.objCurrPrograma = objCurrPrograma;
    }

    /**
     * @return the objetoCurrNuevo
     */
    public MallaCurricularDTO getObjetoCurrNuevo() {
        return objetoCurrNuevo;
    }

    /**
     * @param objetoCurrNuevo the objetoCurrNuevo to set
     */
    public void setObjetoCurrNuevo(MallaCurricularDTO objetoCurrNuevo) {
        this.objetoCurrNuevo = objetoCurrNuevo;
    }

    /**
     * @return the tipoObjCurrSel
     */
    public CatalogoComunDTO getTipoObjCurrSel() {
        return tipoObjCurrSel;
    }

    /**
     * @param tipoObjCurrSel the tipoObjCurrSel to set
     */
    public void setTipoObjCurrSel(CatalogoComunDTO tipoObjCurrSel) {
        this.tipoObjCurrSel = tipoObjCurrSel;
    }

    /**
     * @return the tiposObjCurr
     */
    public List<CatalogoComunDTO> getTiposObjCurr() {
        return tiposObjCurr;
    }

    /**
     * @param tiposObjCurr the tiposObjCurr to set
     */
    public void setTiposObjCurr(List<CatalogoComunDTO> tiposObjCurr) {
        this.tiposObjCurr = tiposObjCurr;
    }

    /**
     * @return the muestraComboObjsCurr
     */
    public boolean isMuestraComboObjsCurr() {
        return muestraComboObjsCurr;
    }

    /**
     * @param muestraComboObjsCurr the muestraComboObjsCurr to set
     */
    public void setMuestraComboObjsCurr(boolean muestraComboObjsCurr) {
        this.muestraComboObjsCurr = muestraComboObjsCurr;
    }

    /**
     * @return the programasPorNodoSel
     */
    public List<TreeNode> getProgramasPorNodoSel() {
        return programasPorNodoSel;
    }

    /**
     * @param programasPorNodoSel the programasPorNodoSel to set
     */
    public void setProgramasPorNodoSel(List<TreeNode> programasPorNodoSel) {
        this.programasPorNodoSel = programasPorNodoSel;
    }

    /**
     * @return the nuevoPlan
     */
    public MallaCurricularDTO getNuevoPlan() {
        return nuevoPlan;
    }

    /**
     * @param nuevoPlan the nuevoPlan to set
     */
    public void setNuevoPlan(MallaCurricularDTO nuevoPlan) {
        this.nuevoPlan = nuevoPlan;
    }

    /**
     * @return the lstArbolesPlanes
     */
    public List<TreeNode> getLstArbolesPlanes() {
        return lstArbolesPlanes;
    }

    /**
     * @param lstArbolesPlanes the lstArbolesPlanes to set
     */
    public void setLstArbolesPlanes(List<TreeNode> lstArbolesPlanes) {
        this.lstArbolesPlanes = lstArbolesPlanes;
    }

    /**
     * @return the muestraProgsXNodo
     */
    public boolean isMuestraProgsXNodo() {
        return muestraProgsXNodo;
    }

    /**
     * @param muestraProgsXNodo the muestraProgsXNodo to set
     */
    public void setMuestraProgsXNodo(boolean muestraProgsXNodo) {
        this.muestraProgsXNodo = muestraProgsXNodo;
    }

    public Panel getPanelMallaCurr() {
        return panelMallaCurr;
    }

    public void setPanelMallaCurr(Panel panelMallaCurr) {
        this.panelMallaCurr = panelMallaCurr;
    }

    public EstructuraTematicaDTO getNuevoTemarioXProg() {
        return nuevoTemarioXProg;
    }

    public void setNuevoTemarioXProg(EstructuraTematicaDTO nuevoTemarioXProg) {
        this.nuevoTemarioXProg = nuevoTemarioXProg;
    }

    /**
     * @return the planSelec
     */
    public PlanDTO getPlanSelec() {
        return planSelec;
    }

    /**
     * @param planSelec the planSelec to set
     */
    public void setPlanSelec(PlanDTO planSelec) {
        this.planSelec = planSelec;
    }

}
