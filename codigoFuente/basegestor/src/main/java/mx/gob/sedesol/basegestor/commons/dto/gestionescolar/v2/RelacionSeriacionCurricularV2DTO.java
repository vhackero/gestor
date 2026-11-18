package mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/** Relación curricular de sólo lectura, transportada desde la estructura del PE. */
public class RelacionSeriacionCurricularV2DTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long idAntecedente;
    private Long idConsecuente;
    private Integer semestreAntecedente;
    private Integer semestreConsecuente;
    private String bloqueAntecedente;
    private String bloqueConsecuente;
    private TipoSeriacionV2 tipoSeriacion = TipoSeriacionV2.NO_DETERMINADA;
    private TipoRelacionSeriacionV2 tipoRelacion = TipoRelacionSeriacionV2.NO_DETERMINADA;
    private EstadoUnidadCurricularV2 estadoAntecedente = EstadoUnidadCurricularV2.NO_DETERMINADO;
    private EstadoCumplimientoSeriacionV2 decision = EstadoCumplimientoSeriacionV2.NO_APLICA;
    private String motivo;
    /** Estado de consulta RN-8.1; el motor no ejecuta altas, cierres ni bajas. */
    private String estadoOperativoInterbloque;
    private String evidencia;
    private Integer profundidadCadena;
    private List<Long> caminoCurricular = new ArrayList<Long>();
    private Boolean antecedenteObligatorio = Boolean.TRUE;
    private Boolean cicloDetectado = Boolean.FALSE;

    public Long getIdAntecedente() { return idAntecedente; }
    public void setIdAntecedente(Long valor) { idAntecedente = valor; }
    public Long getIdConsecuente() { return idConsecuente; }
    public void setIdConsecuente(Long valor) { idConsecuente = valor; }
    public Integer getSemestreAntecedente() { return semestreAntecedente; }
    public void setSemestreAntecedente(Integer valor) { semestreAntecedente = valor; }
    public Integer getSemestreConsecuente() { return semestreConsecuente; }
    public void setSemestreConsecuente(Integer valor) { semestreConsecuente = valor; }
    public String getBloqueAntecedente() { return bloqueAntecedente; }
    public void setBloqueAntecedente(String valor) { bloqueAntecedente = valor; }
    public String getBloqueConsecuente() { return bloqueConsecuente; }
    public void setBloqueConsecuente(String valor) { bloqueConsecuente = valor; }
    public TipoSeriacionV2 getTipoSeriacion() { return tipoSeriacion; }
    public void setTipoSeriacion(TipoSeriacionV2 valor) { tipoSeriacion = valor; }
    public TipoRelacionSeriacionV2 getTipoRelacion() { return tipoRelacion; }
    public void setTipoRelacion(TipoRelacionSeriacionV2 valor) { tipoRelacion = valor; }
    public EstadoUnidadCurricularV2 getEstadoAntecedente() { return estadoAntecedente; }
    public void setEstadoAntecedente(EstadoUnidadCurricularV2 valor) { estadoAntecedente = valor; }
    public EstadoCumplimientoSeriacionV2 getDecision() { return decision; }
    public void setDecision(EstadoCumplimientoSeriacionV2 valor) { decision = valor; }
    public String getMotivo() { return motivo; }
    public void setMotivo(String valor) { motivo = valor; }
    public String getEstadoOperativoInterbloque() { return estadoOperativoInterbloque; }
    public void setEstadoOperativoInterbloque(String valor) { estadoOperativoInterbloque = valor; }
    public String getEvidencia() { return evidencia; }
    public void setEvidencia(String valor) { evidencia = valor; }
    public Integer getProfundidadCadena() { return profundidadCadena; }
    public void setProfundidadCadena(Integer valor) { profundidadCadena = valor; }
    public List<Long> getCaminoCurricular() { return caminoCurricular; }
    public void setCaminoCurricular(List<Long> valor) { caminoCurricular = valor == null ? new ArrayList<Long>() : valor; }
    public Boolean getAntecedenteObligatorio() { return antecedenteObligatorio; }
    public void setAntecedenteObligatorio(Boolean valor) { antecedenteObligatorio = valor; }
    public Boolean getCicloDetectado() { return cicloDetectado; }
    public void setCicloDetectado(Boolean valor) { cicloDetectado = valor; }
}
