package mx.gob.sedesol.basegestor.commons.dto.gestionescolar;

import java.io.Serializable;

/**
 * DTO para transportar la información capturada en el formulario de nuevas bajas.
 */
public class NuevaBajaDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String matricula;
    private Long idPersona;
    private Long idTipoBaja;
    private Integer idPlan;
    private Integer semestre;
    private Integer bloque;
    private Integer idPrograma;
    private Integer idPeriodo;
    private Integer idEvento;
    private String motivo;
    private String quienAplica;
    private String numeroSolicitud;
    private Integer procesoId;
    private Integer idGrupo;
    private Integer idMotivo;

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public Long getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(Long idPersona) {
        this.idPersona = idPersona;
    }

    public Long getIdTipoBaja() {
        return idTipoBaja;
    }

    public void setIdTipoBaja(Long idTipoBaja) {
        this.idTipoBaja = idTipoBaja;
    }

    public Integer getIdPlan() {
        return idPlan;
    }

    public void setIdPlan(Integer idPlan) {
        this.idPlan = idPlan;
    }

    public Integer getSemestre() {
        return semestre;
    }

    public void setSemestre(Integer semestre) {
        this.semestre = semestre;
    }

    public Integer getBloque() {
        return bloque;
    }

    public void setBloque(Integer bloque) {
        this.bloque = bloque;
    }

    public Integer getIdPrograma() {
        return idPrograma;
    }

    public void setIdPrograma(Integer idPrograma) {
        this.idPrograma = idPrograma;
    }

    public Integer getIdPeriodo() {
        return idPeriodo;
    }

    public void setIdPeriodo(Integer idPeriodo) {
        this.idPeriodo = idPeriodo;
    }

    public Integer getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(Integer idEvento) {
        this.idEvento = idEvento;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getQuienAplica() {
        return quienAplica;
    }

    public void setQuienAplica(String quienAplica) {
        this.quienAplica = quienAplica;
    }

    public String getNumeroSolicitud() {
        return numeroSolicitud;
    }

    public void setNumeroSolicitud(String numeroSolicitud) {
        this.numeroSolicitud = numeroSolicitud;
    }

    public Integer getProcesoId() {
        return procesoId;
    }

    public void setProcesoId(Integer procesoId) {
        this.procesoId = procesoId;
    }

    public Integer getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(Integer idGrupo) {
        this.idGrupo = idGrupo;
    }

    public Integer getIdMotivo() {
        return idMotivo;
    }

    public void setIdMotivo(Integer idMotivo) {
        this.idMotivo = idMotivo;
    }
}
