package mx.gob.sedesol.basegestor.commons.dto.gestionescolar;

import java.io.Serializable;

/**
 * Representa la información de matrícula usada para aplicar una baja.
 */
public class BajaDetalleDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long idPersona;
    private Integer idPlan;
    private Integer idPrograma;
    private Integer idEvento;
    private Integer idGrupo;
    private Integer procesoId;

    public Long getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(Long idPersona) {
        this.idPersona = idPersona;
    }

    public Integer getIdPlan() {
        return idPlan;
    }

    public void setIdPlan(Integer idPlan) {
        this.idPlan = idPlan;
    }

    public Integer getIdPrograma() {
        return idPrograma;
    }

    public void setIdPrograma(Integer idPrograma) {
        this.idPrograma = idPrograma;
    }

    public Integer getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(Integer idEvento) {
        this.idEvento = idEvento;
    }

    public Integer getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(Integer idGrupo) {
        this.idGrupo = idGrupo;
    }

    public Integer getProcesoId() {
        return procesoId;
    }

    public void setProcesoId(Integer procesoId) {
        this.procesoId = procesoId;
    }
}
