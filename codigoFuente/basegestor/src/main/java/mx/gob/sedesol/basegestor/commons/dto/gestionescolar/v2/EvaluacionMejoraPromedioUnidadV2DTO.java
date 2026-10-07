package mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/** Resultado explicable por UD de RN-1.5, sin reservar oferta ni cupo. */
public class EvaluacionMejoraPromedioUnidadV2DTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long idPrograma;
    private String clave;
    private EstadoMejoraPromedioV2 estado = EstadoMejoraPromedioV2.NO_DETERMINADO;
    private Integer oportunidadesMejoraUtilizadas;
    private Integer oportunidadesMejoraDisponibles;
    private Double calificacionFavorable;
    private Boolean ofertada;
    private Boolean cupoDisponible;
    private String prioridad = "INFERIOR_A_PRIMERA_INSCRIPCION_Y_REPROBACION";
    private List<String> evidencias = new ArrayList<String>();
    private List<String> motivos = new ArrayList<String>();
    public Long getIdPrograma() { return idPrograma; }
    public void setIdPrograma(Long valor) { idPrograma = valor; }
    public String getClave() { return clave; }
    public void setClave(String valor) { clave = valor; }
    public EstadoMejoraPromedioV2 getEstado() { return estado; }
    public void setEstado(EstadoMejoraPromedioV2 valor) { estado = valor; }
    public Integer getOportunidadesMejoraUtilizadas() { return oportunidadesMejoraUtilizadas; }
    public void setOportunidadesMejoraUtilizadas(Integer valor) { oportunidadesMejoraUtilizadas = valor; }
    public Integer getOportunidadesMejoraDisponibles() { return oportunidadesMejoraDisponibles; }
    public void setOportunidadesMejoraDisponibles(Integer valor) { oportunidadesMejoraDisponibles = valor; }
    public Double getCalificacionFavorable() { return calificacionFavorable; }
    public void setCalificacionFavorable(Double valor) { calificacionFavorable = valor; }
    public Boolean getOfertada() { return ofertada; }
    public void setOfertada(Boolean valor) { ofertada = valor; }
    public Boolean getCupoDisponible() { return cupoDisponible; }
    public void setCupoDisponible(Boolean valor) { cupoDisponible = valor; }
    public String getPrioridad() { return prioridad; }
    public void setPrioridad(String valor) { prioridad = valor; }
    public List<String> getEvidencias() { return evidencias; }
    public List<String> getMotivos() { return motivos; }
}
