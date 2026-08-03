package mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PanelAsistenteVirtualDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String perfil;
    private String escenario;
    private String mensajeOperativo;
    private String etiquetaResumen;
    private String resumenTitulo;
    private String resumenContenido;
    private List<AsistenteVirtualAccionDTO> acciones;

    public PanelAsistenteVirtualDTO() {
        this.acciones = new ArrayList<AsistenteVirtualAccionDTO>();
    }

    public String getPerfil() {
        return perfil;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }

    public String getEscenario() {
        return escenario;
    }

    public void setEscenario(String escenario) {
        this.escenario = escenario;
    }

    public String getMensajeOperativo() {
        return mensajeOperativo;
    }

    public void setMensajeOperativo(String mensajeOperativo) {
        this.mensajeOperativo = mensajeOperativo;
    }

    public String getEtiquetaResumen() {
        return etiquetaResumen;
    }

    public void setEtiquetaResumen(String etiquetaResumen) {
        this.etiquetaResumen = etiquetaResumen;
    }

    public String getResumenTitulo() {
        return resumenTitulo;
    }

    public void setResumenTitulo(String resumenTitulo) {
        this.resumenTitulo = resumenTitulo;
    }

    public String getResumenContenido() {
        return resumenContenido;
    }

    public void setResumenContenido(String resumenContenido) {
        this.resumenContenido = resumenContenido;
    }

    public List<AsistenteVirtualAccionDTO> getAcciones() {
        return acciones;
    }

    public void setAcciones(List<AsistenteVirtualAccionDTO> acciones) {
        this.acciones = acciones;
    }
}
