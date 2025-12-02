package mx.gob.sedesol.gestorweb.beans.gestionescolar;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;

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
    private Boolean estatusSeleccionado;
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
        if (periodos == null) {
            periodos = new ArrayList<>();
        }
    }

    public void buscar() {
        if (!validarCampos()) {
            return;
        }

        resultados = consultaBajaService.buscarBajas(matricula.trim(), periodoSeleccionado, estatusSeleccionado);

        if (ObjectUtils.isNullOrEmpty(resultados)) {
            agregarMsgInfo("No se encontraron registros", null);
        }
    }

    public void limpiar() {
        matricula = null;
        periodoSeleccionado = null;
        estatusSeleccionado = null;
        resultados = new ArrayList<>();
    }

    public String obtenerNombreEstatus(Boolean estatus) {
        if (Boolean.TRUE.equals(estatus)) {
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

    public Boolean getEstatusSeleccionado() {
        return estatusSeleccionado;
    }

    public void setEstatusSeleccionado(Boolean estatusSeleccionado) {
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
}
