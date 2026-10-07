package mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/** Resultado concurrente de sólo lectura; no sustituye el dictamen vigente. */
public class ResultadoEvaluacionCurricularV2DTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String situacionAcademica;
    private Integer totalReprobadas;
    private Boolean avanceCondicionado = Boolean.FALSE;
    private Integer prioridadTecnicaDominante;
    private Boolean efectosProductivosAplicados = Boolean.FALSE;
    private List<CondicionCurricularV2> condicionesDetectadas = new ArrayList<CondicionCurricularV2>();
    private List<CodigoReglaCurricularV2> reglasEvaluadas = new ArrayList<CodigoReglaCurricularV2>();
    private List<DecisionUnidadCurricularV2DTO> decisiones = new ArrayList<DecisionUnidadCurricularV2DTO>();
    private List<CodigoMotivoCurricularV2> restricciones = new ArrayList<CodigoMotivoCurricularV2>();
    private List<String> excepciones = new ArrayList<String>();
    private List<ExcepcionCurricularV2DTO> excepcionesEvaluadas = new ArrayList<ExcepcionCurricularV2DTO>();
    private List<String> mensajes = new ArrayList<String>();
    private List<TrazaReglaAplicadaV2DTO> trazas = new ArrayList<TrazaReglaAplicadaV2DTO>();

    public String getSituacionAcademica() { return situacionAcademica; }
    public void setSituacionAcademica(String situacionAcademica) { this.situacionAcademica = situacionAcademica; }
    public Integer getTotalReprobadas() { return totalReprobadas; }
    public void setTotalReprobadas(Integer totalReprobadas) { this.totalReprobadas = totalReprobadas; }
    public Boolean getAvanceCondicionado() { return avanceCondicionado; }
    public void setAvanceCondicionado(Boolean avanceCondicionado) { this.avanceCondicionado = avanceCondicionado; }
    public Integer getPrioridadTecnicaDominante() { return prioridadTecnicaDominante; }
    public void setPrioridadTecnicaDominante(Integer prioridadTecnicaDominante) { this.prioridadTecnicaDominante = prioridadTecnicaDominante; }
    public Boolean getEfectosProductivosAplicados() { return efectosProductivosAplicados; }
    public void setEfectosProductivosAplicados(Boolean efectosProductivosAplicados) { this.efectosProductivosAplicados = efectosProductivosAplicados; }
    public List<CondicionCurricularV2> getCondicionesDetectadas() { return condicionesDetectadas; }
    public List<CodigoReglaCurricularV2> getReglasEvaluadas() { return reglasEvaluadas; }
    public List<DecisionUnidadCurricularV2DTO> getDecisiones() { return decisiones; }
    public List<CodigoMotivoCurricularV2> getRestricciones() { return restricciones; }
    public List<String> getExcepciones() { return excepciones; }
    public List<ExcepcionCurricularV2DTO> getExcepcionesEvaluadas() { return excepcionesEvaluadas; }
    public List<String> getMensajes() { return mensajes; }
    public List<TrazaReglaAplicadaV2DTO> getTrazas() { return trazas; }
}
