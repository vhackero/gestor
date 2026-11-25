package mx.gob.sedesol.basegestor.commons.dto.gestionescolar;

/** Deuda única de optativa adicional, independiente de sus cursamientos. */
public class AdeudoOptativaDTO extends InscripcionMateriasReprobadasDTO {
    private Long idOrigen;
    private Long idInscripcionActual;
    private int sustituciones;
    private boolean enCurso;
    private Boolean limiteOportunidadesActivo;
    private Integer limiteAcumulado;
    private Integer limiteSustituciones;
    private Boolean limiteSustitucionesActivo;
    public int getLimiteAcumulado() { if(limiteAcumulado==null)throw new IllegalStateException("Falta el límite acumulado configurado.");return limiteAcumulado; }
    public void setLimiteAcumulado(int v) { limiteAcumulado=v; }
    public int getLimiteSustituciones() { if(limiteSustituciones==null)throw new IllegalStateException("Falta el límite de sustituciones configurado.");return limiteSustituciones; }
    public void setLimiteSustituciones(int v) { limiteSustituciones=v; }
    public boolean isLimiteSustitucionesActivo() { if(limiteSustitucionesActivo==null)throw new IllegalStateException("Falta el interruptor de sustituciones configurado.");return limiteSustitucionesActivo; }
    public void setLimiteSustitucionesActivo(boolean v) { limiteSustitucionesActivo=v; }
    public boolean isAdmiteSustitucion() { return !isLimiteSustitucionesActivo() || sustituciones<getLimiteSustituciones(); }
    public int getSustitucionesDisponibles() { return Math.max(0,getLimiteSustituciones()-sustituciones); }
    public boolean isLimiteOportunidadesActivo() { if(limiteOportunidadesActivo==null)throw new IllegalStateException("Falta el interruptor de oportunidades configurado.");return limiteOportunidadesActivo; }
    public void setLimiteOportunidadesActivo(boolean v) { limiteOportunidadesActivo=v; }
    public boolean isAdmiteOtraOportunidad() { return !isLimiteOportunidadesActivo() || getOportunidadesDisponibles()>0; }
    public Long getIdOrigen() { return idOrigen; }
    public void setIdOrigen(Long v) { idOrigen=v; }
    public Long getIdInscripcionActual() { return idInscripcionActual; }
    public void setIdInscripcionActual(Long v) { idInscripcionActual=v; }
    public int getSustituciones() { return sustituciones; }
    public void setSustituciones(int v) { sustituciones=v; }
    public boolean isEnCurso() { return enCurso; }
    public void setEnCurso(boolean v) { enCurso=v; }
    public long getOportunidadesDisponibles() { return Math.max(0L,getLimiteAcumulado()-getIntentosReprobados()); }
    public String getDescripcion() { return getNombrePrograma()+" ("+getIntentosReprobados()+" oportunidades acumuladas; "+(isLimiteOportunidadesActivo()?getOportunidadesDisponibles()+" disponibles":"límite de oportunidades desactivado")+")"; }
}
