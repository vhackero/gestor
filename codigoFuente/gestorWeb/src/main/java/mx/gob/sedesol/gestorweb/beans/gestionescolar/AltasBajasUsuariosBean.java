package mx.gob.sedesol.gestorweb.beans.gestionescolar;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;

import mx.gob.sedesol.gestorweb.beans.acceso.BaseBean;
import mx.gob.sedesol.gestorweb.commons.constantes.ConstantesGestorWeb;

@ManagedBean
@ViewScoped
public class AltasBajasUsuariosBean extends BaseBean {

    private static final long serialVersionUID = 1L;

    private static final Logger LOGGER = Logger.getLogger(AltasBajasUsuariosBean.class);

    @PostConstruct
    public void init() {
        LOGGER.info("Inicializando módulo de altas y bajas de usuarios");
    }

    public String irNuevaAlta() {
        LOGGER.info("Navegando a la vista de nueva alta de usuario");
        return ConstantesGestorWeb.NAVEGA_NUEVA_ALTA_USUARIO;
    }

    public String irNuevaBaja() {
        LOGGER.info("Navegando a la vista de nueva baja de usuario");
        return ConstantesGestorWeb.NAVEGA_NUEVA_BAJA_USUARIO;
    }

    public String irConsultarBaja() {
        LOGGER.info("Navegando a la vista de consulta de bajas de usuario");
        return ConstantesGestorWeb.NAVEGA_CONSULTA_BAJA_USUARIO;
    }

    public String regresarAlModulo() {
        LOGGER.info("Regresando al módulo de altas y bajas de usuarios");
        return ConstantesGestorWeb.NAVEGA_ALTAS_BAJAS_USUARIOS;
    }
}
