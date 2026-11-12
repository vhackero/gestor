package mx.gob.sedesol.basegestor.commons.dto.gestionescolar;

/** Deuda única de optativa adicional, independiente de sus cursamientos. */
public class AdeudoOptativaDTO extends InscripcionMateriasReprobadasDTO {
    private Long idOrigen;
    private Long idInscripcionActual;
    private int sustituciones;
    private boolean enCurso;
    private boolean limiteOportunidadesActivo = true;
    private int limiteAcumulado = 3;
    private int limiteSustituciones = 2;
    private boolean limiteSustitucionesActivo = true;
    public int getLimiteAcumulado() { return limiteAcumulado; }
    public void setLimiteAcumulado(int v) { limiteAcumulado=v; }
    public int getLimiteSustituciones() { return limiteSustituciones; }
    public void setLimiteSustituciones(int v) { limiteSustituciones=v; }
    public boolean isLimiteSustitucionesActivo() { return limiteSustitucionesActivo; }
    public void setLimiteSustitucionesActivo(boolean v) { limiteSustitucionesActivo=v; }
    public boolean isAdmiteSustitucion() { return !limiteSustitucionesActivo || sustituciones<limiteSustituciones; }
    public int getSustitucionesDisponibles() { return Math.max(0,limiteSustituciones-sustituciones); }
    public boolean isLimiteOportunidadesActivo() { return limiteOportunidadesActivo; }
    public void setLimiteOportunidadesActivo(boolean v) { limiteOportunidadesActivo=v; }
    public boolean isAdmiteOtraOportunidad() { return !limiteOportunidadesActivo || getOportunidadesDisponibles()>0; }
    public Long getIdOrigen() { return idOrigen; }
    public void setIdOrigen(Long v) { idOrigen=v; }
    public Long getIdInscripcionActual() { return idInscripcionActual; }
    public void setIdInscripcionActual(Long v) { idInscripcionActual=v; }
    public int getSustituciones() { return sustituciones; }
    public void setSustituciones(int v) { sustituciones=v; }
    public boolean isEnCurso() { return enCurso; }
    public void setEnCurso(boolean v) { enCurso=v; }
    public long getOportunidadesDisponibles() { return Math.max(0L,limiteAcumulado-getIntentosReprobados()); }
    public String getDescripcion() { return getNombrePrograma()+" ("+getIntentosReprobados()+" oportunidades acumuladas; "+(limiteOportunidadesActivo?getOportunidadesDisponibles()+" disponibles":"límite de oportunidades desactivado")+")"; }
}
