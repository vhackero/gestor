package mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2;

import java.io.Serializable;

/** Evidencia externa de una autorización; no sustituye a Inscripción. */
public class AutorizacionIndividualCurricularV2DTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private String referencia;
    private String autoridad;
    private String unidadOSupuesto;
    /** Fechas ISO yyyy-MM-dd verificables por el adaptador que aporta el dato. */
    private String inicioVigencia;
    private String finVigencia;

    public String getReferencia() { return referencia; }
    public void setReferencia(String valor) { referencia = valor; }
    public String getAutoridad() { return autoridad; }
    public void setAutoridad(String valor) { autoridad = valor; }
    public String getUnidadOSupuesto() { return unidadOSupuesto; }
    public void setUnidadOSupuesto(String valor) { unidadOSupuesto = valor; }
    public String getInicioVigencia() { return inicioVigencia; }
    public void setInicioVigencia(String valor) { inicioVigencia = valor; }
    public String getFinVigencia() { return finVigencia; }
    public void setFinVigencia(String valor) { finVigencia = valor; }
}
