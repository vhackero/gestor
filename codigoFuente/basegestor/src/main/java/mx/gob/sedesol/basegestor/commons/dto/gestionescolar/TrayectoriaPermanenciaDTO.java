package mx.gob.sedesol.basegestor.commons.dto.gestionescolar;

import java.io.Serializable;
import java.util.Date;

/**
 * Indicadores informativos de trayectoria. No determina por sí mismo una baja
 * ni modifica la situación académica o la inscripción.
 */
public class TrayectoriaPermanenciaDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String periodoPrimeraInscripcion;
    private Date fechaPrimeraInscripcion;
    private String periodoCorte;
    private Date fechaCorte;
    private Integer duracionNominalSemestres;
    private Integer plazoMaximoSemestres;
    private Integer periodosAcademicosTranscurridos;
    private Integer periodosConInscripcion;
    private Integer plazoRestanteSemestres;
    private String tiempoCalendarioTranscurrido;
    private Boolean determinable = Boolean.FALSE;
    private String mensajeRevision;

    public String getPeriodoPrimeraInscripcion() { return periodoPrimeraInscripcion; }
    public void setPeriodoPrimeraInscripcion(String valor) { periodoPrimeraInscripcion = valor; }
    public Date getFechaPrimeraInscripcion() { return fechaPrimeraInscripcion; }
    public void setFechaPrimeraInscripcion(Date valor) { fechaPrimeraInscripcion = valor; }
    public String getPeriodoCorte() { return periodoCorte; }
    public void setPeriodoCorte(String valor) { periodoCorte = valor; }
    public Date getFechaCorte() { return fechaCorte; }
    public void setFechaCorte(Date valor) { fechaCorte = valor; }
    public Integer getDuracionNominalSemestres() { return duracionNominalSemestres; }
    public void setDuracionNominalSemestres(Integer valor) { duracionNominalSemestres = valor; }
    public Integer getPlazoMaximoSemestres() { return plazoMaximoSemestres; }
    public void setPlazoMaximoSemestres(Integer valor) { plazoMaximoSemestres = valor; }
    public Integer getPeriodosAcademicosTranscurridos() { return periodosAcademicosTranscurridos; }
    public void setPeriodosAcademicosTranscurridos(Integer valor) { periodosAcademicosTranscurridos = valor; }
    public Integer getPeriodosConInscripcion() { return periodosConInscripcion; }
    public void setPeriodosConInscripcion(Integer valor) { periodosConInscripcion = valor; }
    public Integer getPlazoRestanteSemestres() { return plazoRestanteSemestres; }
    public void setPlazoRestanteSemestres(Integer valor) { plazoRestanteSemestres = valor; }
    public String getTiempoCalendarioTranscurrido() { return tiempoCalendarioTranscurrido; }
    public void setTiempoCalendarioTranscurrido(String valor) { tiempoCalendarioTranscurrido = valor; }
    public Boolean getDeterminable() { return determinable; }
    public void setDeterminable(Boolean valor) { determinable = valor; }
    public String getMensajeRevision() { return mensajeRevision; }
    public void setMensajeRevision(String valor) { mensajeRevision = valor; }
}
