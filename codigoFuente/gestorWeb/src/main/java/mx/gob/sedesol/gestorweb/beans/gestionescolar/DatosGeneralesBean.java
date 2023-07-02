package mx.gob.sedesol.gestorweb.beans.gestionescolar;

import mx.gob.sedesol.basegestor.commons.dto.NodoDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.CatalogoComunDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.MallaCurricularDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.RelEjeCompetenciaDTO;
import mx.gob.sedesol.basegestor.commons.utils.CatGestionEscolarEnum;
import mx.gob.sedesol.basegestor.service.impl.planesyprogramas.FECServiceFacade;
import mx.gob.sedesol.basegestor.springinit.GestionEscolarServiceAdapter;
import mx.gob.sedesol.gestorweb.beans.acceso.BaseBean;
import mx.gob.sedesol.gestorweb.commons.constantes.ConstantesGestorWeb;
import mx.gob.sedesol.gestorweb.commons.dto.NodoeHijosDTO;
import org.apache.log4j.Logger;
import org.primefaces.model.CheckboxTreeNode;
import org.primefaces.model.TreeNode;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jhcortes on 9/02/17.
 */
@SessionScoped
@ManagedBean
public class DatosGeneralesBean extends BaseBean {

    private static final long serialVersionUID = 1L;

    private static final Logger logger = Logger.getLogger(NuevoEventoCapacitacionBean.class);

    @ManagedProperty(value = "#{fecServiceFacade}")
    private FECServiceFacade fecServiceFacade;

    @ManagedProperty(value = "#{gestionEscolarServiceAdapter}")
    private GestionEscolarServiceAdapter gestionEscolarServiceAdapter;

    private CatGestionEscolarEnum[] catalogosGestionEscolar;
    private CatGestionEscolarEnum catalogoSeleccionado;
    private List<CatalogoComunDTO> catNivelEnsenanzaProg;
    private List<CatalogoComunDTO> catTpoComp;
    private List<CatalogoComunDTO> catEjeCapacit;
    private NodoeHijosDTO estPlanSedesol;
    private List<CatalogoComunDTO> ejesCapacitacion;
    private TreeNode arbolCompEspecificas;
    private Integer tipoCompetencia;
    private Integer ejeCapacitacion;

    public DatosGeneralesBean() {
    }

    private void generaCatEjesCapacitBusqueda() {
        ejesCapacitacion = new ArrayList<>();

        for (NodoeHijosDTO nh : estPlanSedesol.getNodosHijos()) {
            for (NodoeHijosDTO nint : nh.getNodosHijos()) {
                CatalogoComunDTO cc = new CatalogoComunDTO();
                cc.setId(nint.getIdNodo());
                cc.setNombre(nint.getNombre());
                ejesCapacitacion.add(cc);
            }
        }

        //Genera el arbol de Competencias especificas
        this.generaArbolCompEspecificasXEje();
    }

    @SuppressWarnings("unchecked")
    @PostConstruct
    public void init() {
        catNivelEnsenanzaProg = (List<CatalogoComunDTO>) getSession().getServletContext()
                .getAttribute(ConstantesGestorWeb.CAT_NIVEL_ENSE_PLAN_PROG);
    }

    private void generaArbolCompEspecificasXEje() {

        arbolCompEspecificas = new CheckboxTreeNode(new NodoDTO(1, "Competencias Especificas"), null);
        for (CatalogoComunDTO ejeCap : ejesCapacitacion) {
            TreeNode ejeCapNodo = new CheckboxTreeNode(new NodoDTO(ejeCap.getId(), ejeCap.getNombre()), arbolCompEspecificas);
            this.generaArbolComEspecifXEje(ejeCapNodo, ejeCap.getId());
        }
    }

    /**
     *
     * @param nodoPadre
     * @param idEjeCap
     */
    private void generaArbolComEspecifXEje(TreeNode nodoPadre, Integer idEjeCap) {
        List<RelEjeCompetenciaDTO> relCompEsp = getFecServiceFacade().getEjeCompetenciaService().obtenerCompetenciasEspecificasPorEje(idEjeCap);
        for (RelEjeCompetenciaDTO ejeComp : relCompEsp) {
            TreeNode compEsp = new CheckboxTreeNode(new NodoDTO(ejeComp.getCatCompetenciaEspecifica().getId(), ejeComp.getCatCompetenciaEspecifica().getNombre()), nodoPadre);
            compEsp.setExpanded(Boolean.FALSE);
        }
    }

    /**
     *
     */
    private void generaEstructuraCatTpoCompetenciaPlan() {

        List<NodoeHijosDTO> planes = new ArrayList<>();
        List<MallaCurricularDTO> mallas = new ArrayList<>();

        // getFecServiceFacade().getMallaCurricularService().obtieneMallasCurricularesDisponibles();
        // RN: Solo se presentara el plan de sedesol por el momento
        MallaCurricularDTO mallaSedesol = getFecServiceFacade().getMallaCurricularService()
                .obtenerMallaCurricularPorId(1);
        mallas.add(mallaSedesol);

        for (MallaCurricularDTO m : mallas) {
            NodoeHijosDTO nodog = new NodoeHijosDTO();
            nodog.setNombre(m.getNombre());
            nodog.setIdNodo(m.getId());
            nodog.setIdPadre(m.getMallaCurricularPadre() != null ? m.getMallaCurricularPadre().getId() : null);
            nodog.setIdObjCurr(m.getObjetoCurricular().getId());
            nodog.setNivel(0);

            if (!m.getLstHijosMallaCurr().isEmpty()) {
                this.generaCatxNivel(m.getLstHijosMallaCurr(), nodog, nodog.getNivel());
            }

            planes.add(nodog);
        }

        catTpoComp = new ArrayList<>();

        // Genera el Catalogo Tipo de Competencia
        estPlanSedesol = planes.get(ConstantesGestorWeb.INDICE_INICIAL);
        for (NodoeHijosDTO nh : estPlanSedesol.getNodosHijos()) {
            CatalogoComunDTO cc = new CatalogoComunDTO();
            cc.setId(nh.getIdNodo());
            cc.setNombre(nh.getNombre());
            catTpoComp.add(cc);
        }
    }

    /**
     *
     * @param hijos
     * @param nodoGral
     */
//    private void generaCatxNivel(List<MallaCurricularDTO> hijos, NodoeHijosDTO nodoGral, int nivel) {
//
//        for (MallaCurricularDTO mint : hijos) {
//
//            NodoeHijosDTO hijo = new NodoeHijosDTO();
//            hijo.setIdNodo(mint.getId());
//            hijo.setIdPadre(nodoGral.getIdNodo());
//            hijo.setIdObjCurr(mint.getObjetoCurricular().getId());
//            hijo.setNivel(nivel + 1);
//            hijo.setNombre(mint.getNombre());
//            nodoGral.getNodosHijos().add(hijo);
//
//            if (!ObjectUtils.isNullOrEmpty(mint.getLstHijosMallaCurr()))
//                this.generaCatxNivel(mint.getLstHijosMallaCurr(), hijo, hijo.getNivel());
//            ;
//        }
//    }
    public FECServiceFacade getFecServiceFacade() {
        return fecServiceFacade;
    }

    public void setFecServiceFacade(FECServiceFacade fecServiceFacade) {
        this.fecServiceFacade = fecServiceFacade;
    }

    public GestionEscolarServiceAdapter getGestionEscolarServiceAdapter() {
        return gestionEscolarServiceAdapter;
    }

    public void setGestionEscolarServiceAdapter(GestionEscolarServiceAdapter gestionEscolarServiceAdapter) {
        this.gestionEscolarServiceAdapter = gestionEscolarServiceAdapter;
    }

    public List<CatalogoComunDTO> getCatTpoComp() {
        return catTpoComp;
    }

    public void setCatTpoComp(List<CatalogoComunDTO> catTpoComp) {
        this.catTpoComp = catTpoComp;
    }

    public List<CatalogoComunDTO> getCatEjeCapacit() {
        return catEjeCapacit;
    }

    public void setCatEjeCapacit(List<CatalogoComunDTO> catEjeCapacit) {
        this.catEjeCapacit = catEjeCapacit;
    }

    public NodoeHijosDTO getEstPlanSedesol() {
        return estPlanSedesol;
    }

    public void setEstPlanSedesol(NodoeHijosDTO estPlanSedesol) {
        this.estPlanSedesol = estPlanSedesol;
    }

    public List<CatalogoComunDTO> getEjesCapacitacion() {
        return ejesCapacitacion;
    }

    public void setEjesCapacitacion(List<CatalogoComunDTO> ejesCapacitacion) {
        this.ejesCapacitacion = ejesCapacitacion;
    }

    public Integer getTipoCompetencia() {
        return tipoCompetencia;
    }

    public void setTipoCompetencia(Integer tipoCompetencia) {
        this.tipoCompetencia = tipoCompetencia;
    }

    public Integer getEjeCapacitacion() {
        return ejeCapacitacion;
    }

    public void setEjeCapacitacion(Integer ejeCapacitacion) {
        this.ejeCapacitacion = ejeCapacitacion;
    }

    public CatGestionEscolarEnum[] getCatalogosGestionEscolar() {
        return catalogosGestionEscolar;
    }

    public void setCatalogosGestionEscolar(CatGestionEscolarEnum[] catalogosGestionEscolar) {
        this.catalogosGestionEscolar = catalogosGestionEscolar;
    }

    public CatGestionEscolarEnum getCatalogoSeleccionado() {
        return catalogoSeleccionado;
    }

    public void setCatalogoSeleccionado(CatGestionEscolarEnum catalogoSeleccionado) {
        this.catalogoSeleccionado = catalogoSeleccionado;
    }

    public TreeNode getArbolCompEspecificas() {
        return arbolCompEspecificas;
    }

    public void setArbolCompEspecificas(TreeNode arbolCompEspecificas) {
        this.arbolCompEspecificas = arbolCompEspecificas;
    }

    public List<CatalogoComunDTO> getCatNivelEnsenanzaProg() {
        return catNivelEnsenanzaProg;
    }

    public void setCatNivelEnsenanzaProg(List<CatalogoComunDTO> catNivelEnsenanzaProg) {
        this.catNivelEnsenanzaProg = catNivelEnsenanzaProg;
    }
}
