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
@Table(name = "tbl_caso_ud_relacionada")
@NamedQuery(name = "TblCasoUdRelacionadaV2.findAll", query = "SELECT t FROM TblCasoUdRelacionadaV2 t")
public class TblCasoUdRelacionadaV2 implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_caso")
    private Long idCaso;

    @Column(name = "id_programa")
    private Long idPrograma;

    @Column(name = "clave_ud")
    private String claveUd;

    @Column(name = "nombre_ud")
    private String nombreUd;

    @Column(name = "tipo_ud")
    private String tipoUd;

    private String bloque;
    private String semestre;

    @Column(name = "estatus_detectado")
    private String estatusDetectado;

    @Column(name = "es_critica")
    private Boolean esCritica;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_registro", insertable = false, updatable = false)
    private Date fechaRegistro;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdCaso() {
        return idCaso;
    }

    public void setIdCaso(Long idCaso) {
        this.idCaso = idCaso;
    }

    public Long getIdPrograma() {
        return idPrograma;
    }

    public void setIdPrograma(Long idPrograma) {
        this.idPrograma = idPrograma;
    }

    public String getClaveUd() {
        return claveUd;
    }

    public void setClaveUd(String claveUd) {
        this.claveUd = claveUd;
    }

    public String getNombreUd() {
        return nombreUd;
    }

    public void setNombreUd(String nombreUd) {
        this.nombreUd = nombreUd;
    }

    public String getTipoUd() {
        return tipoUd;
    }

    public void setTipoUd(String tipoUd) {
        this.tipoUd = tipoUd;
    }

    public String getBloque() {
        return bloque;
    }

    public void setBloque(String bloque) {
        this.bloque = bloque;
    }

    public String getSemestre() {
        return semestre;
    }

    public void setSemestre(String semestre) {
        this.semestre = semestre;
    }

    public String getEstatusDetectado() {
        return estatusDetectado;
    }

    public void setEstatusDetectado(String estatusDetectado) {
        this.estatusDetectado = estatusDetectado;
    }

    public Boolean getEsCritica() {
        return esCritica;
    }

    public void setEsCritica(Boolean esCritica) {
        this.esCritica = esCritica;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
}
