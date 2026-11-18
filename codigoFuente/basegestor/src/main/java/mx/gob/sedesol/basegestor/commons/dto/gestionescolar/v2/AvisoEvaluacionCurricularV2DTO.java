package mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2;

import java.io.Serializable;

/** Mensaje institucional trazable, separado de una decisión académica. */
public class AvisoEvaluacionCurricularV2DTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private CodigoAvisoEvaluacionCurricularV2 codigo;
    private String texto;
    private String referencia;
    private String periodoAplicacion;
    private String vigencia;

    public CodigoAvisoEvaluacionCurricularV2 getCodigo() { return codigo; }
    public void setCodigo(CodigoAvisoEvaluacionCurricularV2 valor) { codigo = valor; }
    public String getTexto() { return texto; }
    public void setTexto(String valor) { texto = valor; }
    public String getReferencia() { return referencia; }
    public void setReferencia(String valor) { referencia = valor; }
    public String getPeriodoAplicacion() { return periodoAplicacion; }
    public void setPeriodoAplicacion(String valor) { periodoAplicacion = valor; }
    public String getVigencia() { return vigencia; }
    public void setVigencia(String valor) { vigencia = valor; }
}
