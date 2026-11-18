package mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2;

import java.io.Serializable;

/** Parámetros de lectura del ámbito PE2024/2027; no activa efectos escolares. */
public class ParametrosCurricularesV2DTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer oportunidadesPorUd = Integer.valueOf(3);
    private Integer calificacionMinimaAprobatoria = Integer.valueOf(60);
    private Integer cargaMinima = Integer.valueOf(3);
    private Integer cargaMaxRegular = Integer.valueOf(8);
    private Integer cargaMaxIrregular = Integer.valueOf(6);
    private Integer umbralRezagoSevero = Integer.valueOf(4);
    private Integer maxSemestresBajaTemporal = Integer.valueOf(3);
    private Integer optativasRequeridas = Integer.valueOf(8);
    private String ctr01PeriodoInicio = "2027-1";
    private Integer ctr01Semestre = Integer.valueOf(5);
    private String documentoRespaldo;
    private String vigencia;
    private String ambito;
    /** Tabla 2 RN-7.1; formato CLAVE:SEMESTRE, configurado por ámbito y vigencia. */
    private String catalogoOptativas;
    /** Comunicado institucional de consulta aplicable a un periodo específico. */
    private String periodoComunicadoInstitucional;
    private String textoComunicadoInstitucional;
    private String referenciaComunicadoInstitucional;
    private String vigenciaComunicadoInstitucional;
    public Integer getOportunidadesPorUd() { return oportunidadesPorUd; } public void setOportunidadesPorUd(Integer v) { oportunidadesPorUd = v; }
    public Integer getCalificacionMinimaAprobatoria() { return calificacionMinimaAprobatoria; } public void setCalificacionMinimaAprobatoria(Integer v) { calificacionMinimaAprobatoria = v; }
    public Integer getCargaMinima() { return cargaMinima; } public void setCargaMinima(Integer v) { cargaMinima = v; }
    public Integer getCargaMaxRegular() { return cargaMaxRegular; } public void setCargaMaxRegular(Integer v) { cargaMaxRegular = v; }
    public Integer getCargaMaxIrregular() { return cargaMaxIrregular; } public void setCargaMaxIrregular(Integer v) { cargaMaxIrregular = v; }
    public Integer getUmbralRezagoSevero() { return umbralRezagoSevero; } public void setUmbralRezagoSevero(Integer v) { umbralRezagoSevero = v; }
    public Integer getMaxSemestresBajaTemporal() { return maxSemestresBajaTemporal; } public void setMaxSemestresBajaTemporal(Integer v) { maxSemestresBajaTemporal = v; }
    public Integer getOptativasRequeridas() { return optativasRequeridas; } public void setOptativasRequeridas(Integer v) { optativasRequeridas = v; }
    public String getCtr01PeriodoInicio() { return ctr01PeriodoInicio; } public void setCtr01PeriodoInicio(String v) { ctr01PeriodoInicio = v; }
    public Integer getCtr01Semestre() { return ctr01Semestre; } public void setCtr01Semestre(Integer v) { ctr01Semestre = v; }
    public String getDocumentoRespaldo() { return documentoRespaldo; } public void setDocumentoRespaldo(String v) { documentoRespaldo = v; }
    public String getVigencia() { return vigencia; } public void setVigencia(String v) { vigencia = v; }
    public String getAmbito() { return ambito; } public void setAmbito(String v) { ambito = v; }
    public String getCatalogoOptativas() { return catalogoOptativas; } public void setCatalogoOptativas(String v) { catalogoOptativas = v; }
    public String getPeriodoComunicadoInstitucional() { return periodoComunicadoInstitucional; } public void setPeriodoComunicadoInstitucional(String v) { periodoComunicadoInstitucional = v; }
    public String getTextoComunicadoInstitucional() { return textoComunicadoInstitucional; } public void setTextoComunicadoInstitucional(String v) { textoComunicadoInstitucional = v; }
    public String getReferenciaComunicadoInstitucional() { return referenciaComunicadoInstitucional; } public void setReferenciaComunicadoInstitucional(String v) { referenciaComunicadoInstitucional = v; }
    public String getVigenciaComunicadoInstitucional() { return vigenciaComunicadoInstitucional; } public void setVigenciaComunicadoInstitucional(String v) { vigenciaComunicadoInstitucional = v; }
}
