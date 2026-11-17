package mx.gob.sedesol.basegestor.commons.dto.gestionescolar;

import java.util.*;
public class OportunidadesUnidadDTO {
    private Long idPrograma;
    private String nombre;
    private int consumidas;
    private int limite;
    private Boolean limiteActivo;
    public boolean isLimiteActivo() { if(limiteActivo==null)throw new IllegalStateException("Falta el interruptor de oportunidades configurado.");return limiteActivo; }
    public void setLimiteActivo(boolean v) { limiteActivo=v; }
    private boolean acreditada;
    public boolean isAcreditada() { return acreditada; }
    public void setAcreditada(boolean v) { acreditada=v; }
    private List<Long> inscripciones = new ArrayList<>();
    private List<Long> excluidasPorBaja = new ArrayList<>();
    public Long getIdPrograma() { return idPrograma; }
    public void setIdPrograma(Long v) { idPrograma=v; }
    public String getNombre() { return nombre; }
    public void setNombre(String v) { nombre=v; }
    public int getConsumidas() { return consumidas; }
    public void setConsumidas(int v) { consumidas=v; }
    public int getLimite() { return limite; }
    public void setLimite(int v) { limite=v; }
    public int getDisponibles() { return Math.max(0,limite-consumidas); }
    public boolean isAgotada() { return isLimiteActivo() && !acreditada && consumidas>=limite; }
    public List<Long> getInscripciones() { return inscripciones; }
    public List<Long> getExcluidasPorBaja() { return excluidasPorBaja; }
}
