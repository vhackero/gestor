package mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FichaIntegralCasoDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private CasoAcademicoOperativoDTO casoAcademico;
    private String mensajeEstudiante;
    private String mensajeGestor;
    private String accionSugerida;
    private String reglaAplicada;
    private Boolean requiereSeguimiento;
    private List<PatronConocimientoDTO> patronesConocimiento;
    private List<MensajeInstitucionalContextualDTO> mensajesContextuales;
    private List<CasoSimilarDTO> casosSimilares;
    private List<String> alertas;

    public FichaIntegralCasoDTO() {
        this.patronesConocimiento = new ArrayList<PatronConocimientoDTO>();
        this.mensajesContextuales = new ArrayList<MensajeInstitucionalContextualDTO>();
        this.casosSimilares = new ArrayList<CasoSimilarDTO>();
        this.alertas = new ArrayList<String>();
    }

    public CasoAcademicoOperativoDTO getCasoAcademico() {
        return casoAcademico;
    }

    public void setCasoAcademico(CasoAcademicoOperativoDTO casoAcademico) {
        this.casoAcademico = casoAcademico;
    }

    public String getMensajeEstudiante() {
        return mensajeEstudiante;
    }

    public void setMensajeEstudiante(String mensajeEstudiante) {
        this.mensajeEstudiante = mensajeEstudiante;
    }

    public String getMensajeGestor() {
        return mensajeGestor;
    }

    public void setMensajeGestor(String mensajeGestor) {
        this.mensajeGestor = mensajeGestor;
    }

    public String getAccionSugerida() {
        return accionSugerida;
    }

    public void setAccionSugerida(String accionSugerida) {
        this.accionSugerida = accionSugerida;
    }

    public String getReglaAplicada() {
        return reglaAplicada;
    }

    public void setReglaAplicada(String reglaAplicada) {
        this.reglaAplicada = reglaAplicada;
    }

    public Boolean getRequiereSeguimiento() {
        return requiereSeguimiento;
    }

    public void setRequiereSeguimiento(Boolean requiereSeguimiento) {
        this.requiereSeguimiento = requiereSeguimiento;
    }

    public List<PatronConocimientoDTO> getPatronesConocimiento() {
        return patronesConocimiento;
    }

    public void setPatronesConocimiento(List<PatronConocimientoDTO> patronesConocimiento) {
        this.patronesConocimiento = patronesConocimiento;
    }

    public List<MensajeInstitucionalContextualDTO> getMensajesContextuales() {
        return mensajesContextuales;
    }

    public void setMensajesContextuales(List<MensajeInstitucionalContextualDTO> mensajesContextuales) {
        this.mensajesContextuales = mensajesContextuales;
    }

    public List<CasoSimilarDTO> getCasosSimilares() {
        return casosSimilares;
    }

    public void setCasosSimilares(List<CasoSimilarDTO> casosSimilares) {
        this.casosSimilares = casosSimilares;
    }

    public List<String> getAlertas() {
        return alertas;
    }

    public void setAlertas(List<String> alertas) {
        this.alertas = alertas;
    }
}
