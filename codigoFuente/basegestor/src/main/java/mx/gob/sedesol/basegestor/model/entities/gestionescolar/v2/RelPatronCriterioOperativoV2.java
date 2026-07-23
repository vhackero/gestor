package mx.gob.sedesol.basegestor.model.entities.gestionescolar.v2;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "rel_patron_criterio_operativo")
@NamedQuery(name = "RelPatronCriterioOperativoV2.findAll", query = "SELECT r FROM RelPatronCriterioOperativoV2 r")
public class RelPatronCriterioOperativoV2 implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_patron_caso")
    private Long idPatronCaso;

    @Column(name = "id_criterio_operativo")
    private Long idCriterioOperativo;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_registro", insertable = false, updatable = false)
    private Date fechaRegistro;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdPatronCaso() {
        return idPatronCaso;
    }

    public void setIdPatronCaso(Long idPatronCaso) {
        this.idPatronCaso = idPatronCaso;
    }

    public Long getIdCriterioOperativo() {
        return idCriterioOperativo;
    }

    public void setIdCriterioOperativo(Long idCriterioOperativo) {
        this.idCriterioOperativo = idCriterioOperativo;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
}
