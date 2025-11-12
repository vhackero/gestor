package mx.gob.sedesol.gestorweb.beans.gestionescolar;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;

import mx.gob.sedesol.gestorweb.beans.acceso.BaseBean;

@ManagedBean
@ViewScoped
public class AltasBajasUsuariosBean extends BaseBean {

    private static final long serialVersionUID = 1L;

    private static final Logger LOGGER = Logger.getLogger(AltasBajasUsuariosBean.class);

    private String paginaActual;
    private boolean mostrarOpcionesBajas;

    @PostConstruct
    public void init() {
        LOGGER.info("Inicializando módulo de altas y bajas de usuarios");
        paginaActual = null;
        mostrarOpcionesBajas = false;
    }

    public String irNuevaAlta() {
        LOGGER.info("Navegando a la vista de nueva alta de usuario");
        this.paginaActual = "/views/private/gestionEscolar/altasBajasUsuarios/nuevaAlta.xhtml";
        this.mostrarOpcionesBajas = false;
        return null;
    }

    public String irNuevaBaja() {
        LOGGER.info("Navegando a la vista de nueva baja de usuario");
        this.paginaActual = "/views/private/gestionEscolar/altasBajasUsuarios/nuevaBaja.xhtml";
        this.mostrarOpcionesBajas = false;
        return null;
    }

    public String irConsultarBaja() {
        LOGGER.info("Navegando a la vista de consulta de bajas de usuario");
        this.paginaActual = "/views/private/gestionEscolar/altasBajasUsuarios/consultaBaja.xhtml";
        this.mostrarOpcionesBajas = false;
        return null;
    }

    public String regresarAlModulo() {
        LOGGER.info("Regresando al módulo de altas y bajas de usuarios");
        this.paginaActual = null;
        this.mostrarOpcionesBajas = false;
        return null;
    }

    public void mostrarOpcionesBajas() {
        LOGGER.info("Mostrando opciones disponibles para las bajas de usuario");
        this.paginaActual = null;
        this.mostrarOpcionesBajas = true;
    }

    public String getPaginaActual() {
        return paginaActual;
    }

    public void setPaginaActual(String paginaActual) {
        this.paginaActual = paginaActual;
    }

    public boolean isMostrarOpcionesBajas() {
        return mostrarOpcionesBajas;
    }

    public void setMostrarOpcionesBajas(boolean mostrarOpcionesBajas) {
        this.mostrarOpcionesBajas = mostrarOpcionesBajas;
    }
}
