package mx.gob.sedesol.gestorweb.beans.gestionescolar;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;

import mx.gob.sedesol.basegestor.commons.dto.admin.CatalogoComunDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.ConsultaBajaDTO;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.service.gestionescolar.ConsultaBajaService;
import mx.gob.sedesol.gestorweb.beans.acceso.BaseBean;

@ManagedBean
@ViewScoped
public class ConsultaBajaUsuariosBean extends BaseBean {

    private static final long serialVersionUID = 1L;

    private static final Logger LOGGER = Logger.getLogger(ConsultaBajaUsuariosBean.class);

    @ManagedProperty(value = "#{consultaBajaService}")
    private transient ConsultaBajaService consultaBajaService;

    private String matricula;
    private String periodoSeleccionado;
    private Integer estatusSeleccionado;
    private List<CatalogoComunDTO> periodos;
    private List<ConsultaBajaDTO> resultados;
    private ConsultaBajaDTO bajaSeleccionada;

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
        bajaSeleccionada = null;
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

    public void prepararEliminacion(ConsultaBajaDTO baja) {
        bajaSeleccionada = baja;
    }

    public void eliminar() {
        try {
            if (bajaSeleccionada == null || bajaSeleccionada.getIdBaja() == null) {
                agregarMsgError("Seleccione un registro válido para eliminar.", null);
                return;
            }

            consultaBajaService.eliminarBaja(bajaSeleccionada);
            agregarMsgInfo("La baja se elimin\u00f3 correctamente.", null);
            buscar();
            bajaSeleccionada = null;
        } catch (Exception e) {
            LOGGER.error("Error al eliminar la baja", e);
            agregarMsgError("Ocurri\u00f3 un error al eliminar la baja.", null);
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

    public ConsultaBajaDTO getBajaSeleccionada() {
        return bajaSeleccionada;
    }

    public void setBajaSeleccionada(ConsultaBajaDTO bajaSeleccionada) {
        this.bajaSeleccionada = bajaSeleccionada;
    }

    public ConsultaBajaService getConsultaBajaService() {
        return consultaBajaService;
    }

    public void setConsultaBajaService(ConsultaBajaService consultaBajaService) {
        this.consultaBajaService = consultaBajaService;
    }
}
