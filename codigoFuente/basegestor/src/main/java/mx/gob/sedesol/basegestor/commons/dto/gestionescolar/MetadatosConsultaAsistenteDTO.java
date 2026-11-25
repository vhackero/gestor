package mx.gob.sedesol.basegestor.commons.dto.gestionescolar;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/** Metadatos comunes de una consulta; no representa una decisión académica. */
public class MetadatosConsultaAsistenteDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Compatibilidad con consumidores previos. Equivale al corte de trayectoria
     * cuando existe; no representa un corte unificado de todas las fuentes.
     */
    private Date fechaCorte;
    private Date fechaCorteExpediente;
    private Date fechaCorteOferta;
    private Date fechaCorteTrayectoria;
    private Date fechaEvaluacionV2;
    private String periodoActual;
    private String versionReglas;
    private EstadoFuenteConsultaAsistente estadoExpediente = EstadoFuenteConsultaAsistente.PARCIAL;
    private EstadoFuenteConsultaAsistente estadoOferta = EstadoFuenteConsultaAsistente.PARCIAL;
    private EstadoFuenteConsultaAsistente estadoTrayectoria = EstadoFuenteConsultaAsistente.PARCIAL;
    private EstadoFuenteConsultaAsistente estadoEvaluacionV2 = EstadoFuenteConsultaAsistente.PARCIAL;
    private List<String> camposPendientes = new ArrayList<String>();

    public Date getFechaCorte() { return fechaCorte; }
    public void setFechaCorte(Date valor) { fechaCorte = valor; }
    public Date getFechaCorteExpediente() { return fechaCorteExpediente; }
    public void setFechaCorteExpediente(Date valor) { fechaCorteExpediente = valor; }
    public Date getFechaCorteOferta() { return fechaCorteOferta; }
    public void setFechaCorteOferta(Date valor) { fechaCorteOferta = valor; }
    public Date getFechaCorteTrayectoria() { return fechaCorteTrayectoria; }
    public void setFechaCorteTrayectoria(Date valor) {
        fechaCorteTrayectoria = valor;
        fechaCorte = valor;
    }
    public Date getFechaEvaluacionV2() { return fechaEvaluacionV2; }
    public void setFechaEvaluacionV2(Date valor) { fechaEvaluacionV2 = valor; }
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
