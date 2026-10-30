package mx.gob.sedesol.basegestor.commons.dto.gestionescolar;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/** Metadatos comunes de una consulta; no representa una decisión académica. */
public class MetadatosConsultaAsistenteDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Date fechaCorte;
    private String periodoActual;
    private String versionReglas;
    private EstadoFuenteConsultaAsistente estadoExpediente = EstadoFuenteConsultaAsistente.PARCIAL;
    private EstadoFuenteConsultaAsistente estadoOferta = EstadoFuenteConsultaAsistente.PARCIAL;
    private EstadoFuenteConsultaAsistente estadoTrayectoria = EstadoFuenteConsultaAsistente.PARCIAL;
    private EstadoFuenteConsultaAsistente estadoEvaluacionV2 = EstadoFuenteConsultaAsistente.PARCIAL;
    private List<String> camposPendientes = new ArrayList<String>();

    public Date getFechaCorte() { return fechaCorte; }
    public void setFechaCorte(Date valor) { fechaCorte = valor; }
    public String getPeriodoActual() { return periodoActual; }
    public void setPeriodoActual(String valor) { periodoActual = valor; }
    public String getVersionReglas() { return versionReglas; }
    public void setVersionReglas(String valor) { versionReglas = valor; }
    public EstadoFuenteConsultaAsistente getEstadoExpediente() { return estadoExpediente; }
    public void setEstadoExpediente(EstadoFuenteConsultaAsistente valor) { estadoExpediente = valor; }
    public EstadoFuenteConsultaAsistente getEstadoOferta() { return estadoOferta; }
    public void setEstadoOferta(EstadoFuenteConsultaAsistente valor) { estadoOferta = valor; }
    public EstadoFuenteConsultaAsistente getEstadoTrayectoria() { return estadoTrayectoria; }
    public void setEstadoTrayectoria(EstadoFuenteConsultaAsistente valor) { estadoTrayectoria = valor; }
    public EstadoFuenteConsultaAsistente getEstadoEvaluacionV2() { return estadoEvaluacionV2; }
    public void setEstadoEvaluacionV2(EstadoFuenteConsultaAsistente valor) { estadoEvaluacionV2 = valor; }
    public List<String> getCamposPendientes() { return camposPendientes; }
    public void setCamposPendientes(List<String> valor) {
        camposPendientes = valor == null ? new ArrayList<String>() : valor;
    }
}
