/**
 *
 */
package mx.gob.sedesol.gestorweb.beans.acceso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import mx.gob.sedesol.basegestor.commons.dto.NodoDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.CatalogoComunDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.OrgGubernamentalDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.RolDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.TipoCorreoDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.MallaCurricularDTO;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.gestorweb.commons.constantes.ConstantesGestorWeb;
import mx.gob.sedesol.gestorweb.commons.dto.NodoeHijosDTO;
import mx.gob.sedesol.gestorweb.commons.dto.UsuarioSessionDTO;
import mx.gob.sedesol.gestorweb.sistema.SistemaBean;

/**
 * @author ormg
 *
 */
public abstract class BaseBean implements Serializable {

    protected final HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();

    /**
     *
     */
    private static final long serialVersionUID = -5530415700940770667L;

    public UsuarioSessionDTO getUsuarioEnSession() {
        return (UsuarioSessionDTO) getSession()
                .getAttribute(ConstantesGestorWeb.PARAM_USUARIO_SESSION);
    }

    public Long idPersonaEnSesion(){
    	return getUsuarioEnSession().getIdPersona();
    }
    
    public void agregarMsgInfo(String mensaje, String detalle) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, mensaje, detalle));
    }

    public void agregarMsgInfo(String mensaje, String detalle, SistemaBean sistema) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, sistema.obtenerTexto(mensaje), detalle));
    }

    public void agregarMsgWarn(String mensaje, String detalle) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_WARN, mensaje, detalle));
    }

    public void agregarMsgError(String mensaje, String detalle) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, mensaje, detalle));
    }

    public void agregarMsgError(String mensaje, String detalle, SistemaBean sistema) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, sistema.obtenerTexto(mensaje), detalle));
    }

    public void agregarMsgError(List<String> mensajes, String detalle, SistemaBean sistema) {
        if (ObjectUtils.isNotNull(mensajes)) {
            for (String mensaje : mensajes) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, sistema.obtenerTexto(mensaje), detalle));
            }
        }
    }

    public void agregarMsgFatal(String mensaje, String detalle) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_FATAL, mensaje, detalle));
    }

    public void agregarFlashMessage(String mensaje, String detalle, Severity severity) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        Flash flash = facesContext.getExternalContext().getFlash();
        flash.setKeepMessages(true);
        flash.setRedirect(true);
        facesContext.addMessage(null, new FacesMessage(severity, mensaje, detalle));
    }
    
    public void agregarFlashMessageError(String mensaje){
    	FacesContext facesContext = FacesContext.getCurrentInstance();
        Flash flash = facesContext.getExternalContext().getFlash();
        flash.setKeepMessages(true);
        flash.setRedirect(true);
        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, mensaje, null));
    }
    

    protected HttpServletRequest getRequest() {
        return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
    }
    
    protected HttpServletRequest requestActual(){
    	return getRequest();
    }

    protected HttpSession getSession() {
        return getRequest().getSession();
    }

    protected Map<String, Object> getSessionMap() {
        return FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
    }

    protected ConfigurableNavigationHandler getNavigationHandler() {
        return (ConfigurableNavigationHandler) FacesContext.getCurrentInstance().getApplication()
                .getNavigationHandler();
    }

    public FacesContext getFacesContext() {
        return FacesContext.getCurrentInstance();
    }

    protected ServletContext getServletContext() {
        return (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
    }

    /**
     * Catalogo de TiposCorreo
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<TipoCorreoDTO> getObtieneCatTiposCorreo() {

        return (List<TipoCorreoDTO>) getSession().getServletContext()
                .getAttribute(ConstantesGestorWeb.CAT_TIPOS_CORREO);
    }

    @SuppressWarnings("unchecked")
    public List<RolDTO> getObtieneCatRoles() {
        return (List<RolDTO>) getSession().getServletContext().getAttribute(ConstantesGestorWeb.CAT_ROLES);
    }

    /**
     *
     * @param errores
     * @return
     */
    public String obtenerErroresDeServicio(List<String> errores) {
        StringBuilder cadena = new StringBuilder();
        if (!ObjectUtils.isNullOrEmpty(errores)) {
            for (String error : errores) {
                cadena.append(error).append("\n");
            }
        }
        return cadena.toString();
    }

    /**
     * Generar un token alfanumerico aleatorio
     *
     * @param longitud tamanio del token
     */
    public String getToken(int longitud) {
        StringBuilder token = new StringBuilder();
        long milis = new java.util.GregorianCalendar().getTimeInMillis();
        Random r = new Random(milis);
        int i = 0;
        while (i < longitud) {
            char c = (char) r.nextInt(255);
            if ((c >= '0' && c <= '9') || (c >= 'A' && c <= 'Z')) {
                token.append(c);
                i++;
            }
        }
        return token.toString();
    }

    public Date getFechaActual() {
        return new Date();
    }

    /**
     * Genera los datos del catalogo de organismo gubernamentales en segundo
     * nivel
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<SelectItem> getListItemsOrgGubs() {

        List<SelectItem> items = new ArrayList<>();
        List<OrgGubernamentalDTO> orgGubs = (List<OrgGubernamentalDTO>) getSession().getServletContext()
                .getAttribute(ConstantesGestorWeb.CAT_ORG_GUBERNAMENTALES);
        if (ObjectUtils.isNotNull(orgGubs)) {

            for (OrgGubernamentalDTO orgG : orgGubs) {

                crearArbolOrgGubs(items, orgG);
            }
        }
        return items;
    }

    private void crearArbolOrgGubs(List<SelectItem> items, OrgGubernamentalDTO orgG) {
        SelectItemGroup group = new SelectItemGroup(orgG.getNombre());
        SelectItem[] arrayItems = null;

        if (!orgG.getLstHijosOrgGub().isEmpty()) {

            arrayItems = new SelectItem[orgG.getLstHijosOrgGub().size()];
            int i = 0;

            for (OrgGubernamentalDTO orgGHijo : orgG.getLstHijosOrgGub()) {
                arrayItems[i] = new SelectItem(orgGHijo.getId(), orgGHijo.getNombre());
                i++;
            }
        }
        group.setSelectItems(arrayItems);
        items.add(group);
    }

    /**
     * Genera la estructura organizacional en forma de arbol, permitiendo
     * seleccionar un nodo en especifico
     *
     * @param nodoPadre
     * @param orgsHijos
     */
    public void generaHijosOrgGubAreas(TreeNode nodoPadre, List<OrgGubernamentalDTO> orgsHijos) {
        if (!ObjectUtils.isNullOrEmpty(orgsHijos)) {
            for (OrgGubernamentalDTO og : orgsHijos) {
                if (og.getIdTipoOrganismo().compareTo(8) < 0) {
                    TreeNode ogHijo = new DefaultTreeNode(new NodoDTO(og.getId(), og.getNombre()), nodoPadre);
                    this.generaHijosOrgGubAreas(ogHijo, og.getLstHijosOrgGub());
                }
            }
        }
    }

    /**
     * Metodo que genera el catalogo de Mallas curriculares por nivel de
     * profudidad
     *
     * @param hijos
     * @param nodoGral
     */
    public void generaCatxNivel(List<MallaCurricularDTO> hijos, NodoeHijosDTO nodoGral, int nivel) {

        for (MallaCurricularDTO mint : hijos) {

            NodoeHijosDTO hijo = new NodoeHijosDTO();
            hijo.setIdNodo(mint.getId());
            hijo.setIdPadre(nodoGral.getIdNodo());
            hijo.setIdObjCurr(mint.getObjetoCurricular().getId());
            hijo.setNivel(nivel + 1);
            hijo.setNombre(mint.getNombre());
            nodoGral.getNodosHijos().add(hijo);

            if (!ObjectUtils.isNullOrEmpty(mint.getLstHijosMallaCurr())) {
                this.generaCatxNivel(mint.getLstHijosMallaCurr(), hijo, hijo.getNivel());
            }
            ;
        }
    }

    /**
     * Regrega el elemento de un catalogo por su identificador
     *
     * @param datosCatalogo
     * @param idRequerido
     * @return
     */
    public CatalogoComunDTO getValorDeCatalogo(List<CatalogoComunDTO> datosCatalogo, Integer idRequerido) {

        for (CatalogoComunDTO obj : datosCatalogo) {
            if (obj.getId().equals(idRequerido)) {
                return obj;
            }
        }
        return null;
    }

    public String getNombreElemento(Integer idElemento, List<CatalogoComunDTO> listaElementos) {
        String nombreElemento = null;
        for (CatalogoComunDTO catalogoComunDTO : listaElementos) {
            if (catalogoComunDTO.getId().equals(idElemento)) {
                nombreElemento = catalogoComunDTO.getNombre();
            }
        }
        return nombreElemento;
    }
}
