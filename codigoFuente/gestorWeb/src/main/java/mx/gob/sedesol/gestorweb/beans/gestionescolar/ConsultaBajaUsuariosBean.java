package mx.gob.sedesol.gestorweb.beans.gestionescolar;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;

import mx.gob.sedesol.basegestor.commons.dto.admin.CatalogoComunDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.ConsultaBajaDTO;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.service.gestionescolar.ConsultaBajaService;
import mx.gob.sedesol.basegestor.service.gestionescolar.NuevaBajaService;
import mx.gob.sedesol.gestorweb.beans.acceso.BaseBean;
import mx.gob.sedesol.gestorweb.beans.gestionescolar.AltasBajasUsuariosBean;
import mx.gob.sedesol.gestorweb.beans.gestionescolar.NuevaBajaUsuarioBean;

@ManagedBean
@ViewScoped
public class ConsultaBajaUsuariosBean extends BaseBean {

    private static final long serialVersionUID = 1L;

    private static final Logger LOGGER = Logger.getLogger(ConsultaBajaUsuariosBean.class);

    @ManagedProperty(value = "#{consultaBajaService}")
    private transient ConsultaBajaService consultaBajaService;

    @ManagedProperty(value = "#{nuevaBajaService}")
    private transient NuevaBajaService nuevaBajaService;

    @ManagedProperty(value = "#{nuevaBajaUsuarioBean}")
    private NuevaBajaUsuarioBean nuevaBajaUsuarioBean;

    @ManagedProperty(value = "#{altasBajasUsuariosBean}")
    private AltasBajasUsuariosBean altasBajasUsuariosBean;

    private String matricula;
    private String periodoSeleccionado;
    private Integer estatusSeleccionado;
    private List<CatalogoComunDTO> periodos;
    private List<ConsultaBajaDTO> resultados;

    @PostConstruct
    public void init() {
        LOGGER.info("Inicializando consulta de bajas de usuarios");
        resultados = new ArrayList<>();
        cargarPeriodos();
    }

    private void cargarPeriodos() {
        periodos = consultaBajaService.obtenerPeriodos();
    }

    public void buscar() {
        if (!validarCampos()) {
            RequestContext.getCurrentInstance().addCallbackParam("sinResultados", false);
            return;
        }

        resultados = consultaBajaService.buscarBajas(matricula.trim(), periodoSeleccionado, estatusSeleccionado);

        boolean sinResultados = ObjectUtils.isNullOrEmpty(resultados);

        if (sinResultados) {
            agregarMsgInfo("No se encontraron registros", null);
        }

        RequestContext.getCurrentInstance().addCallbackParam("sinResultados", sinResultados);
    }

    public void limpiar() {
        matricula = null;
        periodoSeleccionado = null;
        estatusSeleccionado = null;
        resultados = new ArrayList<>();
        limpiarComponentes("frmAltasBajas:frmConsultaBaja");
        RequestContext.getCurrentInstance().reset(":frmAltasBajas:frmResultadosBaja");
    }

    private void limpiarComponentes(String clientId) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        if (facesContext == null) {
            return;
        }
        UIComponent componente = facesContext.getViewRoot().findComponent(clientId);
        if (componente != null) {
            limpiarValores(componente);
        }
    }

    private void limpiarValores(UIComponent componente) {
        if (componente instanceof UIInput) {
            UIInput input = (UIInput) componente;
            input.setSubmittedValue(null);
            input.setValue(null);
            input.setLocalValueSet(false);
            input.setValid(true);
        }
        for (UIComponent hijo : componente.getChildren()) {
            limpiarValores(hijo);
        }
    }

    public String obtenerNombreEstatus(Integer estatus) {
        if (estatus != null && estatus == 1) {
            return "Aplicada";
        }
        return "Pendiente";
    }

    private boolean validarCampos() {
        boolean valido = true;

        if (ObjectUtils.isNullOrEmpty(matricula) || matricula.trim().isEmpty()) {
            agregarMsgError("La matrícula o usuario es obligatorio.", null);
            valido = false;
        } else {
            matricula = matricula.trim();
        }

        if (ObjectUtils.isNullOrEmpty(periodoSeleccionado)) {
            agregarMsgError("El periodo es obligatorio.", null);
            valido = false;
        }

        if (ObjectUtils.isNullOrEmpty(estatusSeleccionado)) {
            agregarMsgError("El estatus es obligatorio.", null);
            valido = false;
        }

        return valido;
    }

    public void editar(ConsultaBajaDTO bajaSeleccionada) {
        try {
            nuevaBajaUsuarioBean.prepararEdicionDesdeConsulta(bajaSeleccionada);
            altasBajasUsuariosBean.setPaginaActual("/views/private/gestionEscolar/altasBajasUsuarios/nuevaBaja.xhtml");
            altasBajasUsuariosBean.setMostrarOpcionesBajas(true);
            RequestContext.getCurrentInstance().update(":frmAltasBajas:panelBotones");
            RequestContext.getCurrentInstance().update(":frmAltasBajas:panelContenido");
        } catch (Exception ex) {
            LOGGER.error("Error al preparar edición de baja", ex);
            agregarMsgError("No fue posible preparar la edición de la baja seleccionada.", null);
        }
    }

    public void eliminar(ConsultaBajaDTO bajaSeleccionada) {
        try {
            nuevaBajaService.eliminarBaja(bajaSeleccionada);
            agregarMsgInfo("Baja eliminada correctamente", null);
            buscar();
        } catch (IllegalArgumentException ex) {
            agregarMsgError(ex.getMessage(), null);
        } catch (Exception ex) {
            LOGGER.error("Error al eliminar la baja", ex);
            agregarMsgError("Ocurrió un error al eliminar la baja seleccionada.", null);
        }
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getPeriodoSeleccionado() {
        return periodoSeleccionado;
    }

    public void setPeriodoSeleccionado(String periodoSeleccionado) {
        this.periodoSeleccionado = periodoSeleccionado;
    }

    public Integer getEstatusSeleccionado() {
        return estatusSeleccionado;
    }

    public void setEstatusSeleccionado(Integer estatusSeleccionado) {
        this.estatusSeleccionado = estatusSeleccionado;
    }

    public List<CatalogoComunDTO> getPeriodos() {
        return periodos;
    }

    public void setPeriodos(List<CatalogoComunDTO> periodos) {
        this.periodos = periodos;
    }

    public List<ConsultaBajaDTO> getResultados() {
        return resultados;
    }

    public void setResultados(List<ConsultaBajaDTO> resultados) {
        this.resultados = resultados;
    }

    public ConsultaBajaService getConsultaBajaService() {
        return consultaBajaService;
    }

    public void setConsultaBajaService(ConsultaBajaService consultaBajaService) {
        this.consultaBajaService = consultaBajaService;
    }

    public NuevaBajaService getNuevaBajaService() {
        return nuevaBajaService;
    }

    public void setNuevaBajaService(NuevaBajaService nuevaBajaService) {
        this.nuevaBajaService = nuevaBajaService;
    }

    public NuevaBajaUsuarioBean getNuevaBajaUsuarioBean() {
        return nuevaBajaUsuarioBean;
    }

    public void setNuevaBajaUsuarioBean(NuevaBajaUsuarioBean nuevaBajaUsuarioBean) {
        this.nuevaBajaUsuarioBean = nuevaBajaUsuarioBean;
    }

    public AltasBajasUsuariosBean getAltasBajasUsuariosBean() {
        return altasBajasUsuariosBean;
    }

    public void setAltasBajasUsuariosBean(AltasBajasUsuariosBean altasBajasUsuariosBean) {
        this.altasBajasUsuariosBean = altasBajasUsuariosBean;
    }
}
