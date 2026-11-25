package mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2;

import java.io.Serializable;

/** Evidencia no mutable de actividad formal para calcular RN-6.9. */
public class PeriodoTrayectoriaV2DTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private String periodo;
    private Boolean inscripcionFormal;
    private Boolean calificacionFinal;
    private Boolean npBajaParcial;
    private Boolean participacionGrupo;
    private Boolean bajaTemporalCompleta;

    public String getPeriodo() { return periodo; }
    public void setPeriodo(String valor) { periodo = valor; }
    public Boolean getInscripcionFormal() { return inscripcionFormal; }
    public void setInscripcionFormal(Boolean valor) { inscripcionFormal = valor; }
    public Boolean getCalificacionFinal() { return calificacionFinal; }
    public void setCalificacionFinal(Boolean valor) { calificacionFinal = valor; }
    public Boolean getNpBajaParcial() { return npBajaParcial; }
    public void setNpBajaParcial(Boolean valor) { npBajaParcial = valor; }
    public Boolean getParticipacionGrupo() { return participacionGrupo; }
    public void setParticipacionGrupo(Boolean valor) { participacionGrupo = valor; }
    public Boolean getBajaTemporalCompleta() { return bajaTemporalCompleta; }
    public void setBajaTemporalCompleta(Boolean valor) { bajaTemporalCompleta = valor; }
}
