package mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/** Orquestación de carga V2: referencia resultados previos, no los recalcula. */
public class EvaluacionCargaAcademicaV2DTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String situacionAcademica;
    private Integer cargaMinimaNormativa;
    private Integer cargaMaximaNormativa;
    private Integer cargaEvaluada;
    private Boolean minimoCumplido;
    private Boolean excepcionMinimoAplicable;
    private Boolean maximoCumplido;
    private Boolean requiereRevision = Boolean.FALSE;
    private Boolean rn64AplicableEnFuturaFase = Boolean.FALSE;
    private Boolean ctr01Rn61Procedente;
    private EstadoCargaAcademicaV2 estado = EstadoCargaAcademicaV2.NO_DETERMINADA;
    private List<Long> udsPrioritarias = new ArrayList<Long>();
    private List<Long> udsIncluidasSombra = new ArrayList<Long>();
    private List<String> udsExcluidas = new ArrayList<String>();
    private List<CodigoMotivoCurricularV2> causasExclusion = new ArrayList<CodigoMotivoCurricularV2>();
    private List<CodigoReglaCurricularV2> reglasRelacionadas = new ArrayList<CodigoReglaCurricularV2>();
    private List<String> evidencias = new ArrayList<String>();

    public String getSituacionAcademica() { return situacionAcademica; }
    public void setSituacionAcademica(String valor) { situacionAcademica = valor; }
    public Integer getCargaMinimaNormativa() { return cargaMinimaNormativa; }
    public void setCargaMinimaNormativa(Integer valor) { cargaMinimaNormativa = valor; }
    public Integer getCargaMaximaNormativa() { return cargaMaximaNormativa; }
    public void setCargaMaximaNormativa(Integer valor) { cargaMaximaNormativa = valor; }
    public Integer getCargaEvaluada() { return cargaEvaluada; }
    public void setCargaEvaluada(Integer valor) { cargaEvaluada = valor; }
    public Boolean getMinimoCumplido() { return minimoCumplido; }
    public void setMinimoCumplido(Boolean valor) { minimoCumplido = valor; }
    public Boolean getExcepcionMinimoAplicable() { return excepcionMinimoAplicable; }
    public void setExcepcionMinimoAplicable(Boolean valor) { excepcionMinimoAplicable = valor; }
    public Boolean getMaximoCumplido() { return maximoCumplido; }
    public void setMaximoCumplido(Boolean valor) { maximoCumplido = valor; }
    public Boolean getRequiereRevision() { return requiereRevision; }
    public void setRequiereRevision(Boolean valor) { requiereRevision = valor; }
    public Boolean getRn64AplicableEnFuturaFase() { return rn64AplicableEnFuturaFase; }
    public void setRn64AplicableEnFuturaFase(Boolean valor) { rn64AplicableEnFuturaFase = valor; }
    public Boolean getCtr01Rn61Procedente() { return ctr01Rn61Procedente; }
    public void setCtr01Rn61Procedente(Boolean valor) { ctr01Rn61Procedente = valor; }
    public EstadoCargaAcademicaV2 getEstado() { return estado; }
    public void setEstado(EstadoCargaAcademicaV2 valor) { estado = valor; }
    public List<Long> getUdsPrioritarias() { return udsPrioritarias; }
    public List<Long> getUdsIncluidasSombra() { return udsIncluidasSombra; }
    public List<String> getUdsExcluidas() { return udsExcluidas; }
    public List<CodigoMotivoCurricularV2> getCausasExclusion() { return causasExclusion; }
    public List<CodigoReglaCurricularV2> getReglasRelacionadas() { return reglasRelacionadas; }
    public List<String> getEvidencias() { return evidencias; }
}
