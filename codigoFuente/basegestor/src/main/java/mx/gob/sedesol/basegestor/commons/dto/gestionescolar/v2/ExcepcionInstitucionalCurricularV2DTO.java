package mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2;

import java.io.Serializable;

/**
 * Evidencia de una excepción institucional parametrizable. La evaluación sólo
 * la lee: no crea, autoriza ni altera inscripciones, adeudos u oportunidades.
 */
public class ExcepcionInstitucionalCurricularV2DTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private String referencia;
    private String autoridad;
    private String reglasComprendidas;
    private String criterioBeneficiarios;
    private String proceso;
    private String periodoAplicacion;
    private String inicioVigencia;
    private String finVigencia;
    private String configuradoPor;
    private String fechaConfiguracion;
    private String alcance;
    private Boolean aplicable = Boolean.FALSE;
    private Boolean revocada = Boolean.FALSE;
    public String getReferencia() { return referencia; } public void setReferencia(String v) { referencia = v; }
    public String getAutoridad() { return autoridad; } public void setAutoridad(String v) { autoridad = v; }
    public String getReglasComprendidas() { return reglasComprendidas; } public void setReglasComprendidas(String v) { reglasComprendidas = v; }
    public String getCriterioBeneficiarios() { return criterioBeneficiarios; } public void setCriterioBeneficiarios(String v) { criterioBeneficiarios = v; }
    public String getProceso() { return proceso; } public void setProceso(String v) { proceso = v; }
    public String getPeriodoAplicacion() { return periodoAplicacion; } public void setPeriodoAplicacion(String v) { periodoAplicacion = v; }
    public String getInicioVigencia() { return inicioVigencia; } public void setInicioVigencia(String v) { inicioVigencia = v; }
    public String getFinVigencia() { return finVigencia; } public void setFinVigencia(String v) { finVigencia = v; }
    public String getConfiguradoPor() { return configuradoPor; } public void setConfiguradoPor(String v) { configuradoPor = v; }
    public String getFechaConfiguracion() { return fechaConfiguracion; } public void setFechaConfiguracion(String v) { fechaConfiguracion = v; }
    public String getAlcance() { return alcance; } public void setAlcance(String v) { alcance = v; }
    public Boolean getAplicable() { return aplicable; } public void setAplicable(Boolean v) { aplicable = v; }
    public Boolean getRevocada() { return revocada; } public void setRevocada(Boolean v) { revocada = v; }
}
