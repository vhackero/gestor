package mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/** Resultado por UD de RN-1.4, separado de RN-1.2 y RN-6.4. */
public class EvaluacionIntentosUnidadV2DTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long idPrograma;
    private String clave;
    private Integer intentosIdentificados = Integer.valueOf(0);
    private Integer intentosFallidos = Integer.valueOf(0);
    private Integer intentosAcreditados = Integer.valueOf(0);
    /** Recursamientos = inscripciones computables posteriores a la primera. */
    private Integer recursamientos = Integer.valueOf(0);
    private Integer oportunidadesConsumidas;
    private Integer oportunidadesDisponibles;
    /** RN-1.4: intentos cuya identidad, UD y periodo quedaron verificados. */
    private Integer intentosConfirmados = Integer.valueOf(0);
    /** Intentos que no pueden incluirse ni descartarse sin una fuente adicional. */
    private Integer intentosEnRevision = Integer.valueOf(0);
    private Boolean intentoEnCurso = Boolean.FALSE;
    private Integer oportunidadesDisponiblesMinimas;
    private Integer oportunidadesDisponiblesMaximas;
    private String aviso;
    private EstadoLimiteIntentosV2 estadoLimite = EstadoLimiteIntentosV2.NO_DETERMINADO;
    private Boolean ultimaOportunidad = Boolean.FALSE;
    private Boolean limiteAgotado = Boolean.FALSE;
    private Boolean requiereEscalamiento = Boolean.FALSE;
    private List<String> evidencias = new ArrayList<String>();
    private List<String> duplicadosDescartados = new ArrayList<String>();
    public Long getIdPrograma() { return idPrograma; }
    public void setIdPrograma(Long valor) { idPrograma = valor; }
    public String getClave() { return clave; }
    public void setClave(String valor) { clave = valor; }
    public Integer getIntentosIdentificados() { return intentosIdentificados; }
    public void setIntentosIdentificados(Integer valor) { intentosIdentificados = valor; }
    public Integer getIntentosFallidos() { return intentosFallidos; }
    public void setIntentosFallidos(Integer valor) { intentosFallidos = valor; }
    public Integer getIntentosAcreditados() { return intentosAcreditados; }
    public void setIntentosAcreditados(Integer valor) { intentosAcreditados = valor; }
    public Integer getRecursamientos() { return recursamientos; }
    public void setRecursamientos(Integer valor) { recursamientos = valor; }
    public Integer getOportunidadesConsumidas() { return oportunidadesConsumidas; }
    public void setOportunidadesConsumidas(Integer valor) { oportunidadesConsumidas = valor; }
    public Integer getOportunidadesDisponibles() { return oportunidadesDisponibles; }
    public void setOportunidadesDisponibles(Integer valor) { oportunidadesDisponibles = valor; }
    public Integer getIntentosConfirmados() { return intentosConfirmados; }
    public void setIntentosConfirmados(Integer valor) { intentosConfirmados = valor; }
    public Integer getIntentosEnRevision() { return intentosEnRevision; }
    public void setIntentosEnRevision(Integer valor) { intentosEnRevision = valor; }
    public Boolean getIntentoEnCurso() { return intentoEnCurso; }
    public void setIntentoEnCurso(Boolean valor) { intentoEnCurso = valor; }
    public Integer getOportunidadesDisponiblesMinimas() { return oportunidadesDisponiblesMinimas; }
    public void setOportunidadesDisponiblesMinimas(Integer valor) { oportunidadesDisponiblesMinimas = valor; }
    public Integer getOportunidadesDisponiblesMaximas() { return oportunidadesDisponiblesMaximas; }
    public void setOportunidadesDisponiblesMaximas(Integer valor) { oportunidadesDisponiblesMaximas = valor; }
    public String getAviso() { return aviso; }
    public void setAviso(String valor) { aviso = valor; }
    public EstadoLimiteIntentosV2 getEstadoLimite() { return estadoLimite; }
    public void setEstadoLimite(EstadoLimiteIntentosV2 valor) { estadoLimite = valor; }
    public Boolean getUltimaOportunidad() { return ultimaOportunidad; }
    public void setUltimaOportunidad(Boolean valor) { ultimaOportunidad = valor; }
    public Boolean getLimiteAgotado() { return limiteAgotado; }
    public void setLimiteAgotado(Boolean valor) { limiteAgotado = valor; }
    public Boolean getRequiereEscalamiento() { return requiereEscalamiento; }
    public void setRequiereEscalamiento(Boolean valor) { requiereEscalamiento = valor; }
    public List<String> getEvidencias() { return evidencias; }
    public List<String> getDuplicadosDescartados() { return duplicadosDescartados; }
}
