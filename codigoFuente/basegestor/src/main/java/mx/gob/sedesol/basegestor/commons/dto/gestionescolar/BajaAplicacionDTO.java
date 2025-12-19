package mx.gob.sedesol.basegestor.commons.dto.gestionescolar;

import java.io.Serializable;

public class BajaAplicacionDTO implements Serializable {

    private static final long serialVersionUID = 6945800643331291351L;

    private Long idPersona;
    private Long motivoBajaId;
    private Long procesoId;
    private Long idPlan;
    private Long idPrograma;
    private Long idEvento;
    private Long idGrupo;
    private Integer idUserEnrolmentsLms;
    private String quienAplicaBaja;
    private Integer contabilizar;
    private String numeroSolicitud;

    public BajaAplicacionDTO() {
    }

    public BajaAplicacionDTO(Long idPersona, Long motivoBajaId, Long procesoId, Long idPlan, Long idPrograma, Long idEvento,
                              Long idGrupo, Integer idUserEnrolmentsLms, String quienAplicaBaja, Integer contabilizar,
                              String numeroSolicitud) {
        this.idPersona = idPersona;
        this.motivoBajaId = motivoBajaId;
        this.procesoId = procesoId;
        this.idPlan = idPlan;
        this.idPrograma = idPrograma;
        this.idEvento = idEvento;
        this.idGrupo = idGrupo;
        this.idUserEnrolmentsLms = idUserEnrolmentsLms;
        this.quienAplicaBaja = quienAplicaBaja;
        this.contabilizar = contabilizar;
        this.numeroSolicitud = numeroSolicitud;
    }

    public Long getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(Long idPersona) {
        this.idPersona = idPersona;
    }

    public Long getMotivoBajaId() {
        return motivoBajaId;
    }

    public void setMotivoBajaId(Long motivoBajaId) {
        this.motivoBajaId = motivoBajaId;
    }

    public Long getProcesoId() {
        return procesoId;
    }

    public void setProcesoId(Long procesoId) {
        this.procesoId = procesoId;
    }

    public Long getIdPlan() {
        return idPlan;
    }

    public void setIdPlan(Long idPlan) {
        this.idPlan = idPlan;
    }

    public Long getIdPrograma() {
        return idPrograma;
    }

    public void setIdPrograma(Long idPrograma) {
        this.idPrograma = idPrograma;
    }

    public Long getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(Long idEvento) {
        this.idEvento = idEvento;
    }

    public Long getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(Long idGrupo) {
        this.idGrupo = idGrupo;
    }

    public Integer getIdUserEnrolmentsLms() {
        return idUserEnrolmentsLms;
    }

    public void setIdUserEnrolmentsLms(Integer idUserEnrolmentsLms) {
        this.idUserEnrolmentsLms = idUserEnrolmentsLms;
    }

    public String getQuienAplicaBaja() {
        return quienAplicaBaja;
    }

    public void setQuienAplicaBaja(String quienAplicaBaja) {
        this.quienAplicaBaja = quienAplicaBaja;
    }

    public Integer getContabilizar() {
        return contabilizar;
    }

    public void setContabilizar(Integer contabilizar) {
        this.contabilizar = contabilizar;
    }

    public String getNumeroSolicitud() {
        return numeroSolicitud;
    }

    public void setNumeroSolicitud(String numeroSolicitud) {
        this.numeroSolicitud = numeroSolicitud;
    }
}
