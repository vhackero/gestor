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
@Table(name = "tbl_caso_dictamen")
@NamedQuery(name = "TblCasoDictamenV2.findAll", query = "SELECT t FROM TblCasoDictamenV2 t")
public class TblCasoDictamenV2 implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_caso")
    private Long idCaso;

    @Column(name = "id_accion_operativa")
    private Long idAccionOperativa;

    @Column(name = "id_viabilidad_tecnica")
    private Long idViabilidadTecnica;

    private String dictamen;

    @Column(name = "mensaje_estudiante")
    private String mensajeEstudiante;

    @Column(name = "mensaje_gestor")
    private String mensajeGestor;

    @Column(name = "requiere_escalamiento")
    private Boolean requiereEscalamiento;

    @Column(name = "folio_escalamiento")
    private String folioEscalamiento;

    @Column(name = "usuario_dictamino")
    private Long usuarioDictamino;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_dictamen", insertable = false, updatable = false)
    private Date fechaDictamen;

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

    public Long getIdAccionOperativa() {
        return idAccionOperativa;
    }

    public void setIdAccionOperativa(Long idAccionOperativa) {
        this.idAccionOperativa = idAccionOperativa;
    }

    public Long getIdViabilidadTecnica() {
        return idViabilidadTecnica;
    }

    public void setIdViabilidadTecnica(Long idViabilidadTecnica) {
        this.idViabilidadTecnica = idViabilidadTecnica;
    }

    public String getDictamen() {
        return dictamen;
    }

    public void setDictamen(String dictamen) {
        this.dictamen = dictamen;
    }

    public String getMensajeEstudiante() {
        return mensajeEstudiante;
    }

    public void setMensajeEstudiante(String mensajeEstudiante) {
        this.mensajeEstudiante = mensajeEstudiante;
    }

    public String getMensajeGestor() {
        return mensajeGestor;
    }

    public void setMensajeGestor(String mensajeGestor) {
        this.mensajeGestor = mensajeGestor;
    }

    public Boolean getRequiereEscalamiento() {
        return requiereEscalamiento;
    }

    public void setRequiereEscalamiento(Boolean requiereEscalamiento) {
        this.requiereEscalamiento = requiereEscalamiento;
    }

    public String getFolioEscalamiento() {
        return folioEscalamiento;
    }

    public void setFolioEscalamiento(String folioEscalamiento) {
        this.folioEscalamiento = folioEscalamiento;
    }

    public Long getUsuarioDictamino() {
        return usuarioDictamino;
    }

    public void setUsuarioDictamino(Long usuarioDictamino) {
        this.usuarioDictamino = usuarioDictamino;
    }

    public Date getFechaDictamen() {
        return fechaDictamen;
    }

    public void setFechaDictamen(Date fechaDictamen) {
        this.fechaDictamen = fechaDictamen;
    }
}
