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
        private static final String RUTA_BASE = "/views/private/gestionEscolar/altasBajasUsuarios/";

        private String paginaActual;
        private boolean mostrarOpcionesBaja;

        @PostConstruct
        public void init() {
                LOGGER.info("Inicializando módulo de altas y bajas de usuarios");
                paginaActual = RUTA_BASE + "instruccionesAltasBajasUsuarios.xhtml";
                mostrarOpcionesBaja = false;
        }

        public void navegaNuevaAlta() {
                mostrarOpcionesBaja = false;
                paginaActual = RUTA_BASE + "nuevaAltaUsuario.xhtml";
        }

        public void prepararBajas() {
                mostrarOpcionesBaja = true;
                paginaActual = RUTA_BASE + "instruccionesAltasBajasUsuarios.xhtml";
        }

        public void navegaNuevaBaja() {
                mostrarOpcionesBaja = true;
                paginaActual = RUTA_BASE + "nuevaBajaUsuario.xhtml";
        }

        public void navegaConsultaBaja() {
                mostrarOpcionesBaja = true;
                paginaActual = RUTA_BASE + "consultaBajaUsuario.xhtml";
        }

        public void regresarOpcionesPrincipales() {
                mostrarOpcionesBaja = false;
                paginaActual = RUTA_BASE + "instruccionesAltasBajasUsuarios.xhtml";
        }

        public String getPaginaActual() {
                return paginaActual;
        }

        public void setPaginaActual(String paginaActual) {
                this.paginaActual = paginaActual;
        }

        public boolean isMostrarOpcionesBaja() {
                return mostrarOpcionesBaja;
        }

        public void setMostrarOpcionesBaja(boolean mostrarOpcionesBaja) {
                this.mostrarOpcionesBaja = mostrarOpcionesBaja;
        }
}
