package mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Contexto de sólo lectura para comparar el motor vigente con la evaluación V2.
 * No representa una solicitud de inscripción ni conserva una selección del usuario.
 */
public class ContextoEvaluacionCurricularV2DTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long idPersona;
    private Long idPlan;
    private Long idPeriodo;
    private Long idProcesoInscripcion;
    private Integer semestreObjetivo;
    private String bloqueObjetivo;
    private String perfilConsulta;
    private String origenConsulta;
    private String versionReglas;
    private String huellaOferta;
    private String situacionAcademicaOrigen;
    private Integer totalReprobadasOrigen;
    private Integer cargaMinimaOrigen;
    private Integer cargaMaximaOrigen;
    private Boolean ctr01ProcedenciaFutura = Boolean.FALSE;
    private Boolean avanceCondicionado;
    private Date fechaEvaluacion;
    private List<UnidadEvaluacionCurricularV2DTO> unidades = new ArrayList<UnidadEvaluacionCurricularV2DTO>();

    public Long getIdPersona() { return idPersona; }
    public void setIdPersona(Long idPersona) { this.idPersona = idPersona; }
    public Long getIdPlan() { return idPlan; }
    public void setIdPlan(Long idPlan) { this.idPlan = idPlan; }
    public Long getIdPeriodo() { return idPeriodo; }
    public void setIdPeriodo(Long idPeriodo) { this.idPeriodo = idPeriodo; }
    public Long getIdProcesoInscripcion() { return idProcesoInscripcion; }
    public void setIdProcesoInscripcion(Long idProcesoInscripcion) { this.idProcesoInscripcion = idProcesoInscripcion; }
    public Integer getSemestreObjetivo() { return semestreObjetivo; }
    public void setSemestreObjetivo(Integer semestreObjetivo) { this.semestreObjetivo = semestreObjetivo; }
    public String getBloqueObjetivo() { return bloqueObjetivo; }
    public void setBloqueObjetivo(String bloqueObjetivo) { this.bloqueObjetivo = bloqueObjetivo; }
    public String getPerfilConsulta() { return perfilConsulta; }
    public void setPerfilConsulta(String perfilConsulta) { this.perfilConsulta = perfilConsulta; }
    public String getOrigenConsulta() { return origenConsulta; }
    public void setOrigenConsulta(String origenConsulta) { this.origenConsulta = origenConsulta; }
    public String getVersionReglas() { return versionReglas; }
    public void setVersionReglas(String versionReglas) { this.versionReglas = versionReglas; }
    public String getHuellaOferta() { return huellaOferta; }
    public void setHuellaOferta(String huellaOferta) { this.huellaOferta = huellaOferta; }
    public String getSituacionAcademicaOrigen() { return situacionAcademicaOrigen; }
    public void setSituacionAcademicaOrigen(String situacionAcademicaOrigen) { this.situacionAcademicaOrigen = situacionAcademicaOrigen; }
    public Integer getTotalReprobadasOrigen() { return totalReprobadasOrigen; }
    public void setTotalReprobadasOrigen(Integer totalReprobadasOrigen) { this.totalReprobadasOrigen = totalReprobadasOrigen; }
    public Integer getCargaMinimaOrigen() { return cargaMinimaOrigen; }
    public void setCargaMinimaOrigen(Integer cargaMinimaOrigen) { this.cargaMinimaOrigen = cargaMinimaOrigen; }
    public Integer getCargaMaximaOrigen() { return cargaMaximaOrigen; }
    public void setCargaMaximaOrigen(Integer cargaMaximaOrigen) { this.cargaMaximaOrigen = cargaMaximaOrigen; }
    public Boolean getCtr01ProcedenciaFutura() { return ctr01ProcedenciaFutura; }
    public void setCtr01ProcedenciaFutura(Boolean ctr01ProcedenciaFutura) { this.ctr01ProcedenciaFutura = ctr01ProcedenciaFutura; }
    public Boolean getAvanceCondicionado() { return avanceCondicionado; }
    public void setAvanceCondicionado(Boolean avanceCondicionado) { this.avanceCondicionado = avanceCondicionado; }
    public Date getFechaEvaluacion() { return fechaEvaluacion; }
    public void setFechaEvaluacion(Date fechaEvaluacion) { this.fechaEvaluacion = fechaEvaluacion; }
    public List<UnidadEvaluacionCurricularV2DTO> getUnidades() { return unidades; }
    public void setUnidades(List<UnidadEvaluacionCurricularV2DTO> unidades) {
        this.unidades = unidades == null ? new ArrayList<UnidadEvaluacionCurricularV2DTO>() : unidades;
    }
}
