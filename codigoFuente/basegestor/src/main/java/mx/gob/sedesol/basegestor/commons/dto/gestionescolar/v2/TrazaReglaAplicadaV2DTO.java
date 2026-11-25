package mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2;

import java.io.Serializable;
import java.util.Date;

public class TrazaReglaAplicadaV2DTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private CodigoReglaCurricularV2 codigoRegla;
    private CodigoMotivoCurricularV2 codigoMotivo;
    private String versionReglas;
    private Integer prioridad;
    private Boolean aplicada;
    private String resultado;
    private String detalle;
    private Date fechaEvaluacion;
    public CodigoReglaCurricularV2 getCodigoRegla() { return codigoRegla; }
    public void setCodigoRegla(CodigoReglaCurricularV2 v) { codigoRegla = v; }
    public CodigoMotivoCurricularV2 getCodigoMotivo() { return codigoMotivo; }
    public void setCodigoMotivo(CodigoMotivoCurricularV2 v) { codigoMotivo = v; }
    public String getVersionReglas() { return versionReglas; }
    public void setVersionReglas(String v) { versionReglas = v; }
    public Integer getPrioridad() { return prioridad; }
    public void setPrioridad(Integer v) { prioridad = v; }
    public Boolean getAplicada() { return aplicada; }
    public void setAplicada(Boolean v) { aplicada = v; }
    public String getResultado() { return resultado; }
    public void setResultado(String v) { resultado = v; }
    public String getDetalle() { return detalle; }
    public void setDetalle(String v) { detalle = v; }
    public Date getFechaEvaluacion() { return fechaEvaluacion; }
    public void setFechaEvaluacion(Date v) { fechaEvaluacion = v; }
}
