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
@Table(name = "tbl_caso_patron_aplicado")
@NamedQuery(name = "TblCasoPatronAplicadoV2.findAll", query = "SELECT t FROM TblCasoPatronAplicadoV2 t")
public class TblCasoPatronAplicadoV2 implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "id_caso") private Long idCaso;
    @Column(name = "id_patron_caso") private Long idPatronCaso;
    @Column(name = "puntaje_relevancia") private Double puntajeRelevancia;
    @Column(name = "orden_aplicacion") private Integer ordenAplicacion;
    private String perfil;
    @Column(name = "periodo_operativo") private String periodoOperativo;
    @Column(name = "huella_evaluacion") private String huellaEvaluacion;
    @Column(name = "criterios_aplicados") private String criteriosAplicados;
    @Column(name = "acciones_aplicadas") private String accionesAplicadas;
    @Column(name = "mensajes_aplicados") private String mensajesAplicados;
    @Column(name = "id_persona_consulta") private Long idPersonaConsulta;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_registro", insertable = false, updatable = false) private Date fechaRegistro;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getIdCaso() { return idCaso; }
    public void setIdCaso(Long idCaso) { this.idCaso = idCaso; }
    public Long getIdPatronCaso() { return idPatronCaso; }
    public void setIdPatronCaso(Long idPatronCaso) { this.idPatronCaso = idPatronCaso; }
    public Double getPuntajeRelevancia() { return puntajeRelevancia; }
    public void setPuntajeRelevancia(Double puntajeRelevancia) { this.puntajeRelevancia = puntajeRelevancia; }
    public Integer getOrdenAplicacion() { return ordenAplicacion; }
    public void setOrdenAplicacion(Integer ordenAplicacion) { this.ordenAplicacion = ordenAplicacion; }
    public String getPerfil() { return perfil; }
    public void setPerfil(String perfil) { this.perfil = perfil; }
    public String getPeriodoOperativo() { return periodoOperativo; }
    public void setPeriodoOperativo(String periodoOperativo) { this.periodoOperativo = periodoOperativo; }
    public String getHuellaEvaluacion() { return huellaEvaluacion; }
    public void setHuellaEvaluacion(String huellaEvaluacion) { this.huellaEvaluacion = huellaEvaluacion; }
    public String getCriteriosAplicados() { return criteriosAplicados; }
    public void setCriteriosAplicados(String criteriosAplicados) { this.criteriosAplicados = criteriosAplicados; }
    public String getAccionesAplicadas() { return accionesAplicadas; }
    public void setAccionesAplicadas(String accionesAplicadas) { this.accionesAplicadas = accionesAplicadas; }
    public String getMensajesAplicados() { return mensajesAplicados; }
    public void setMensajesAplicados(String mensajesAplicados) { this.mensajesAplicados = mensajesAplicados; }
    public Long getIdPersonaConsulta() { return idPersonaConsulta; }
    public void setIdPersonaConsulta(Long idPersonaConsulta) { this.idPersonaConsulta = idPersonaConsulta; }
    public Date getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(Date fechaRegistro) { this.fechaRegistro = fechaRegistro; }
}
