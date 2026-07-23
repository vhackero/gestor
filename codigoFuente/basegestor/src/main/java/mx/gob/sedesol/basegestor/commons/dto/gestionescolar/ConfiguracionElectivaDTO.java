package mx.gob.sedesol.basegestor.commons.dto.gestionescolar;

import java.io.Serializable;

public class ConfiguracionElectivaDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long idProcesoInscripcion;
    private String nombreProceso;
    private String nombreConvocatoria;
    private Long idPlan;
    private String nombrePlan;
    private Long idPrograma;
    private String nombrePrograma;
    private String clavePrograma;
    private String semestre;
    private Boolean activa;
    private Boolean sinLimite;
    private Integer cupoMaximo;
    private Long cupoOcupado;

    public Long getIdProcesoInscripcion() { return idProcesoInscripcion; }
    public void setIdProcesoInscripcion(Long value) { this.idProcesoInscripcion = value; }
    public String getNombreProceso() { return nombreProceso; }
    public void setNombreProceso(String value) { this.nombreProceso = value; }
    public String getNombreConvocatoria() { return nombreConvocatoria; }
    public void setNombreConvocatoria(String value) { this.nombreConvocatoria = value; }
    public Long getIdPlan() { return idPlan; }
    public void setIdPlan(Long value) { this.idPlan = value; }
    public String getNombrePlan() { return nombrePlan; }
    public void setNombrePlan(String value) { this.nombrePlan = value; }
    public Long getIdPrograma() { return idPrograma; }
    public void setIdPrograma(Long value) { this.idPrograma = value; }
    public String getNombrePrograma() { return nombrePrograma; }
    public void setNombrePrograma(String value) { this.nombrePrograma = value; }
    public String getClavePrograma() { return clavePrograma; }
    public void setClavePrograma(String value) { this.clavePrograma = value; }
    public String getSemestre() { return semestre; }
    public void setSemestre(String value) { this.semestre = value; }
    public Boolean getActiva() { return activa; }
    public void setActiva(Boolean value) { this.activa = value; }
    public Boolean getSinLimite() { return sinLimite; }
    public void setSinLimite(Boolean value) { this.sinLimite = value; }
    public Integer getCupoMaximo() { return cupoMaximo; }
    public void setCupoMaximo(Integer value) { this.cupoMaximo = value; }
    public Long getCupoOcupado() { return cupoOcupado; }
    public void setCupoOcupado(Long value) { this.cupoOcupado = value; }
}
