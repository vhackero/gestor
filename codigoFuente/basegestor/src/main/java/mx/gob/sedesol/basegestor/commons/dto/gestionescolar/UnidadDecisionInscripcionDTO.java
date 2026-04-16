package mx.gob.sedesol.basegestor.commons.dto.gestionescolar;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UnidadDecisionInscripcionDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long udId;
    private String clave;
    private String nombre;
    private String tipoUd;

    private Integer semestre;
    private Integer bloque;
    private Integer creditos;

    private String estatusHistorico;
    private String estatusPeriodo;
    private String prioridad;

    private Boolean seleccionable;
    private Boolean ofertada;
    private Boolean recomendada;
    private Boolean prioritaria;
    private Boolean condicionada;
    private Boolean bloqueada;

    private String mensajeCorto;
    private String mensajeDetallado;
    private String accionSugerida;
    private String riesgoSiNoSeInscribe;

    private List<MotivoDecisionDTO> motivos;
    private List<RelacionSeriacionDTO> requiere;
    private List<RelacionSeriacionDTO> desbloquea;

    public UnidadDecisionInscripcionDTO() {
        this.motivos = new ArrayList<MotivoDecisionDTO>();
        this.requiere = new ArrayList<RelacionSeriacionDTO>();
        this.desbloquea = new ArrayList<RelacionSeriacionDTO>();
    }

    public Long getUdId() {
        return udId;
    }

    public void setUdId(Long udId) {
        this.udId = udId;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipoUd() {
        return tipoUd;
    }

    public void setTipoUd(String tipoUd) {
        this.tipoUd = tipoUd;
    }

    public Integer getSemestre() {
        return semestre;
    }

    public void setSemestre(Integer semestre) {
        this.semestre = semestre;
    }

    public Integer getBloque() {
        return bloque;
    }

    public void setBloque(Integer bloque) {
        this.bloque = bloque;
    }

    public Integer getCreditos() {
        return creditos;
    }

    public void setCreditos(Integer creditos) {
        this.creditos = creditos;
    }

    public String getEstatusHistorico() {
        return estatusHistorico;
    }

    public void setEstatusHistorico(String estatusHistorico) {
        this.estatusHistorico = estatusHistorico;
    }

    public String getEstatusPeriodo() {
        return estatusPeriodo;
    }

    public void setEstatusPeriodo(String estatusPeriodo) {
        this.estatusPeriodo = estatusPeriodo;
    }

    public String getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(String prioridad) {
        this.prioridad = prioridad;
    }

    public Boolean getSeleccionable() {
        return seleccionable;
    }

    public void setSeleccionable(Boolean seleccionable) {
        this.seleccionable = seleccionable;
    }

    public Boolean getOfertada() {
        return ofertada;
    }

    public void setOfertada(Boolean ofertada) {
        this.ofertada = ofertada;
    }

    public Boolean getRecomendada() {
        return recomendada;
    }

    public void setRecomendada(Boolean recomendada) {
        this.recomendada = recomendada;
    }

    public Boolean getPrioritaria() {
        return prioritaria;
    }

    public void setPrioritaria(Boolean prioritaria) {
        this.prioritaria = prioritaria;
    }

    public Boolean getCondicionada() {
        return condicionada;
    }

    public void setCondicionada(Boolean condicionada) {
        this.condicionada = condicionada;
    }

    public Boolean getBloqueada() {
        return bloqueada;
    }

    public void setBloqueada(Boolean bloqueada) {
        this.bloqueada = bloqueada;
    }

    public String getMensajeCorto() {
        return mensajeCorto;
    }

    public void setMensajeCorto(String mensajeCorto) {
        this.mensajeCorto = mensajeCorto;
    }

    public String getMensajeDetallado() {
        return mensajeDetallado;
    }

    public void setMensajeDetallado(String mensajeDetallado) {
        this.mensajeDetallado = mensajeDetallado;
    }

    public String getAccionSugerida() {
        return accionSugerida;
    }

    public void setAccionSugerida(String accionSugerida) {
        this.accionSugerida = accionSugerida;
    }

    public String getRiesgoSiNoSeInscribe() {
        return riesgoSiNoSeInscribe;
    }

    public void setRiesgoSiNoSeInscribe(String riesgoSiNoSeInscribe) {
        this.riesgoSiNoSeInscribe = riesgoSiNoSeInscribe;
    }

    public List<MotivoDecisionDTO> getMotivos() {
        return motivos;
    }

    public void setMotivos(List<MotivoDecisionDTO> motivos) {
        this.motivos = motivos;
    }

    public List<RelacionSeriacionDTO> getRequiere() {
        return requiere;
    }

    public void setRequiere(List<RelacionSeriacionDTO> requiere) {
        this.requiere = requiere;
    }

    public List<RelacionSeriacionDTO> getDesbloquea() {
        return desbloquea;
    }

    public void setDesbloquea(List<RelacionSeriacionDTO> desbloquea) {
        this.desbloquea = desbloquea;
    }
}
