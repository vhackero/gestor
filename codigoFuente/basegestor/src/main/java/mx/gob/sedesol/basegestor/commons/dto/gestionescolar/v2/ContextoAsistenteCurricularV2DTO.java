package mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2;

import java.io.Serializable;

public class ContextoAsistenteCurricularV2DTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long idPersonaObjetivo;
    private Long idPersonaConsulta;
    private String perfilConsulta;
    private String periodoOperativo;
    private String origenConsulta;
    private Long idPlan;
    private Long idPeriodo;
    private Boolean vistaGestor;
    private CasoAcademicoOperativoDTO casoActual;

    public Long getIdPersonaObjetivo() {
        return idPersonaObjetivo;
    }

    public void setIdPersonaObjetivo(Long idPersonaObjetivo) {
        this.idPersonaObjetivo = idPersonaObjetivo;
    }

    public Long getIdPersonaConsulta() {
        return idPersonaConsulta;
    }

    public void setIdPersonaConsulta(Long idPersonaConsulta) {
        this.idPersonaConsulta = idPersonaConsulta;
    }

    public String getPerfilConsulta() {
        return perfilConsulta;
    }

    public void setPerfilConsulta(String perfilConsulta) {
        this.perfilConsulta = perfilConsulta;
    }

    public String getPeriodoOperativo() {
        return periodoOperativo;
    }

    public void setPeriodoOperativo(String periodoOperativo) {
        this.periodoOperativo = periodoOperativo;
    }

    public String getOrigenConsulta() {
        return origenConsulta;
    }

    public void setOrigenConsulta(String origenConsulta) {
        this.origenConsulta = origenConsulta;
    }

    public Long getIdPlan() {
        return idPlan;
    }

    public void setIdPlan(Long idPlan) {
        this.idPlan = idPlan;
    }

    public Long getIdPeriodo() {
        return idPeriodo;
    }

    public void setIdPeriodo(Long idPeriodo) {
        this.idPeriodo = idPeriodo;
    }

    public Boolean getVistaGestor() {
        return vistaGestor;
    }

    public void setVistaGestor(Boolean vistaGestor) {
        this.vistaGestor = vistaGestor;
    }

    public CasoAcademicoOperativoDTO getCasoActual() {
        return casoActual;
    }

    public void setCasoActual(CasoAcademicoOperativoDTO casoActual) {
        this.casoActual = casoActual;
    }
}
