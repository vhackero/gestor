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
@Table(name = "tbl_caso_similar")
@NamedQuery(name = "TblCasoSimilarV2.findAll", query = "SELECT t FROM TblCasoSimilarV2 t")
public class TblCasoSimilarV2 implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_caso_origen")
    private Long idCasoOrigen;

    @Column(name = "id_caso_relacionado")
    private Long idCasoRelacionado;

    @Column(name = "puntaje_similitud")
    private Double puntajeSimilitud;

    @Column(name = "motivo_relacion")
    private String motivoRelacion;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_registro", insertable = false, updatable = false)
    private Date fechaRegistro;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdCasoOrigen() {
        return idCasoOrigen;
    }

    public void setIdCasoOrigen(Long idCasoOrigen) {
        this.idCasoOrigen = idCasoOrigen;
    }

    public Long getIdCasoRelacionado() {
        return idCasoRelacionado;
    }

    public void setIdCasoRelacionado(Long idCasoRelacionado) {
        this.idCasoRelacionado = idCasoRelacionado;
    }

    public Double getPuntajeSimilitud() {
        return puntajeSimilitud;
    }

    public void setPuntajeSimilitud(Double puntajeSimilitud) {
        this.puntajeSimilitud = puntajeSimilitud;
    }

    public String getMotivoRelacion() {
        return motivoRelacion;
    }

    public void setMotivoRelacion(String motivoRelacion) {
        this.motivoRelacion = motivoRelacion;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
}
