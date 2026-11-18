package mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/** Decisión descriptiva V2: conserva el origen y no modifica la inscripción vigente. */
public class DecisionUnidadCurricularV2DTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long idPrograma;
    private Boolean habilitada;
    private Boolean prioritaria;
    private Boolean ofertaOrigen;
    private Boolean ofertaEvaluadaV2;
    private Boolean seleccionableOrigen;
    private Boolean bloqueadaOrigen;
    /** Estado consultable de RN-8.1: no ejecuta cierres ni movimientos escolares. */
    private String estadoOperativoInterbloque;
    private String explicacionTecnica;
    private List<EstadoDecisionUnidadCurricularV2> estadosSombra = new ArrayList<EstadoDecisionUnidadCurricularV2>();
    private List<CodigoMotivoCurricularV2> motivos = new ArrayList<CodigoMotivoCurricularV2>();
    private List<CodigoExcepcionCurricularV2> excepcionesRelacionadas = new ArrayList<CodigoExcepcionCurricularV2>();
    private List<String> referenciasFuente = new ArrayList<String>();
    private List<TrazaReglaAplicadaV2DTO> trazas = new ArrayList<TrazaReglaAplicadaV2DTO>();

    public Long getIdPrograma() { return idPrograma; }
    public void setIdPrograma(Long idPrograma) { this.idPrograma = idPrograma; }
    public Boolean getHabilitada() { return habilitada; }
    public void setHabilitada(Boolean habilitada) { this.habilitada = habilitada; }
    public Boolean getPrioritaria() { return prioritaria; }
    public void setPrioritaria(Boolean prioritaria) { this.prioritaria = prioritaria; }
    public Boolean getOfertaOrigen() { return ofertaOrigen; }
    public void setOfertaOrigen(Boolean ofertaOrigen) { this.ofertaOrigen = ofertaOrigen; }
    public Boolean getOfertaEvaluadaV2() { return ofertaEvaluadaV2; }
    public void setOfertaEvaluadaV2(Boolean ofertaEvaluadaV2) { this.ofertaEvaluadaV2 = ofertaEvaluadaV2; }
    public Boolean getSeleccionableOrigen() { return seleccionableOrigen; }
    public void setSeleccionableOrigen(Boolean seleccionableOrigen) { this.seleccionableOrigen = seleccionableOrigen; }
    public Boolean getBloqueadaOrigen() { return bloqueadaOrigen; }
    public void setBloqueadaOrigen(Boolean bloqueadaOrigen) { this.bloqueadaOrigen = bloqueadaOrigen; }
    public String getEstadoOperativoInterbloque() { return estadoOperativoInterbloque; }
    public void setEstadoOperativoInterbloque(String estadoOperativoInterbloque) { this.estadoOperativoInterbloque = estadoOperativoInterbloque; }
    public String getExplicacionTecnica() { return explicacionTecnica; }
    public void setExplicacionTecnica(String explicacionTecnica) { this.explicacionTecnica = explicacionTecnica; }
    public List<EstadoDecisionUnidadCurricularV2> getEstadosSombra() { return estadosSombra; }
    public List<CodigoMotivoCurricularV2> getMotivos() { return motivos; }
    public List<CodigoExcepcionCurricularV2> getExcepcionesRelacionadas() { return excepcionesRelacionadas; }
    public List<String> getReferenciasFuente() { return referenciasFuente; }
    public List<TrazaReglaAplicadaV2DTO> getTrazas() { return trazas; }
}
