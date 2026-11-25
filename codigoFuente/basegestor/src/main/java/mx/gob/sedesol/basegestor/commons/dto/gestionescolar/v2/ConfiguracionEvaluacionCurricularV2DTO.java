package mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2;

import java.io.Serializable;

/** Configuración de operación del motor V2, separada de sus reglas académicas. */
public class ConfiguracionEvaluacionCurricularV2DTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String versionReglas;
    private Boolean evaluacionV2Habilitada;
    private Boolean modoSombra;
    private String ctr01PeriodoInicio;
    private Integer ctr01SemestreObjetivo;
    private ParametrosCurricularesV2DTO parametrosCurriculares;

    public String getVersionReglas() { return versionReglas; }
    public void setVersionReglas(String versionReglas) { this.versionReglas = versionReglas; }
    public Boolean getEvaluacionV2Habilitada() { return evaluacionV2Habilitada; }
    public void setEvaluacionV2Habilitada(Boolean evaluacionV2Habilitada) { this.evaluacionV2Habilitada = evaluacionV2Habilitada; }
    public Boolean getModoSombra() { return modoSombra; }
    public void setModoSombra(Boolean modoSombra) { this.modoSombra = modoSombra; }
    public String getCtr01PeriodoInicio() { return ctr01PeriodoInicio; }
    public void setCtr01PeriodoInicio(String ctr01PeriodoInicio) { this.ctr01PeriodoInicio = ctr01PeriodoInicio; }
    public Integer getCtr01SemestreObjetivo() { return ctr01SemestreObjetivo; }
    public void setCtr01SemestreObjetivo(Integer ctr01SemestreObjetivo) { this.ctr01SemestreObjetivo = ctr01SemestreObjetivo; }
    public ParametrosCurricularesV2DTO getParametrosCurriculares() { return parametrosCurriculares; }
    public void setParametrosCurriculares(ParametrosCurricularesV2DTO valor) { parametrosCurriculares = valor; }
}
