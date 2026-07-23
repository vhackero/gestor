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
@Table(name = "rel_patron_mensaje_contextual")
@NamedQuery(name = "RelPatronMensajeContextualV2.findAll", query = "SELECT r FROM RelPatronMensajeContextualV2 r")
public class RelPatronMensajeContextualV2 implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_patron_caso")
    private Long idPatronCaso;

    @Column(name = "id_mensaje_contextual")
    private Long idMensajeContextual;

    private String perfil;

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

    public Long getIdMensajeContextual() {
        return idMensajeContextual;
    }

    public void setIdMensajeContextual(Long idMensajeContextual) {
        this.idMensajeContextual = idMensajeContextual;
    }

    public String getPerfil() {
        return perfil;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
}
