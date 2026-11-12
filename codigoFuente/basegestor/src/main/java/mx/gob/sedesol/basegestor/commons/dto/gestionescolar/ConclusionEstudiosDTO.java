package mx.gob.sedesol.basegestor.commons.dto.gestionescolar;

import java.util.ArrayList;
import java.util.List;

/** Dos criterios alternativos de conclusión; no modifica la regularidad ni el historial. */
public class ConclusionEstudiosDTO {
    private boolean reglaActiva;
    private long creditosAprobados;
    private Long creditosRequeridos;
    private int obligatoriasRequeridas;
    private int obligatoriasAcreditadas;
    private int bloquesRequeridos;
    private int bloquesCubiertos;
    private boolean mapaComprobado;
    private List<Long> unidadesAprobadas = new ArrayList<>();
    private List<Long> obligatoriasPendientes = new ArrayList<>();
    private List<Long> bloquesPendientes = new ArrayList<>();
    private java.util.Map<Long,Long> optativaPorBloque = new java.util.LinkedHashMap<>();
    public java.util.Map<Long,Long> getOptativaPorBloque() { return optativaPorBloque; }
    public boolean isReglaActiva() { return reglaActiva; }
    public void setReglaActiva(boolean v) { reglaActiva=v; }
    public long getCreditosAprobados() { return creditosAprobados; }
    public void setCreditosAprobados(long v) { creditosAprobados=v; }
    public Long getCreditosRequeridos() { return creditosRequeridos; }
    public void setCreditosRequeridos(Long v) { creditosRequeridos=v; }
    public int getObligatoriasRequeridas() { return obligatoriasRequeridas; }
    public void setObligatoriasRequeridas(int v) { obligatoriasRequeridas=v; }
    public int getObligatoriasAcreditadas() { return obligatoriasAcreditadas; }
    public void setObligatoriasAcreditadas(int v) { obligatoriasAcreditadas=v; }
    public int getBloquesRequeridos() { return bloquesRequeridos; }
    public void setBloquesRequeridos(int v) { bloquesRequeridos=v; }
    public int getBloquesCubiertos() { return bloquesCubiertos; }
    public void setBloquesCubiertos(int v) { bloquesCubiertos=v; }
    public boolean isMapaComprobado() { return mapaComprobado; }
    public void setMapaComprobado(boolean v) { mapaComprobado=v; }
    public List<Long> getUnidadesAprobadas() { return unidadesAprobadas; }
    public List<Long> getObligatoriasPendientes() { return obligatoriasPendientes; }
    public List<Long> getBloquesPendientes() { return bloquesPendientes; }
    public boolean isPorCreditos() { return creditosRequeridos!=null && creditosRequeridos>0 && creditosAprobados>=creditosRequeridos; }
    public boolean isPorCobertura() { return mapaComprobado && obligatoriasRequeridas>0 && obligatoriasPendientes.isEmpty() && bloquesPendientes.isEmpty(); }
    public boolean isConcluido() { return isPorCreditos() || isPorCobertura(); }
    public boolean isBloqueaInscripcion() { return reglaActiva && isConcluido(); }
    public String getMensaje() {
        if(!isConcluido()) return "";
        String criterio=isPorCreditos()?"créditos aprobados iguales o superiores al total de tu plan":"todas las obligatorias y al menos una optativa requerida por cada bloque de tu plan";
        return "Cumples el criterio de conclusión de estudios por "+criterio+". "
            +(reglaActiva?"No se permiten nuevas inscripciones por esta regla.":"La validación de conclusión está desactivada; se mantienen las demás reglas de inscripción.");
    }
}
