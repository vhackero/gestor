package mx.gob.sedesol.basegestor.model.entities.gestionescolar.v2;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "tbl_evaluacion_curricular_v2")
public class TblEvaluacionCurricularV2 implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @Column(name = "id_persona") private Long idPersona;
    @Column(name = "id_plan") private Long idPlan;
    @Column(name = "id_periodo") private Long idPeriodo;
    @Column(name = "id_proceso_inscripcion") private Long idProcesoInscripcion;
    @Column(name = "semestre_objetivo") private Integer semestreObjetivo;
    @Column(name = "bloque_objetivo") private String bloqueObjetivo;
    @Column(name = "perfil_consulta") private String perfilConsulta;
    @Column(name = "origen_consulta") private String origenConsulta;
    @Column(name = "version_reglas") private String versionReglas;
    @Column(name = "modo_sombra") private Boolean modoSombra;
    @Column(name = "huella_oferta") private String huellaOferta;
    @Column(name = "hash_contexto") private String hashContexto;
    @Column(name = "situacion_resultante") private String situacionResultante;
    @Column(name = "total_reprobadas") private Integer totalReprobadas;
    @Column(name = "resultado_general") private String resultadoGeneral;
    @Column(name = "estado_evaluacion") private String estadoEvaluacion;
    @Temporal(TemporalType.TIMESTAMP) @Column(name = "fecha_evaluacion") private Date fechaEvaluacion;
    public Long getId(){return id;} public void setId(Long v){id=v;} public Long getIdPersona(){return idPersona;} public void setIdPersona(Long v){idPersona=v;} public Long getIdPlan(){return idPlan;} public void setIdPlan(Long v){idPlan=v;} public Long getIdPeriodo(){return idPeriodo;} public void setIdPeriodo(Long v){idPeriodo=v;} public Long getIdProcesoInscripcion(){return idProcesoInscripcion;} public void setIdProcesoInscripcion(Long v){idProcesoInscripcion=v;} public Integer getSemestreObjetivo(){return semestreObjetivo;} public void setSemestreObjetivo(Integer v){semestreObjetivo=v;} public String getBloqueObjetivo(){return bloqueObjetivo;} public void setBloqueObjetivo(String v){bloqueObjetivo=v;} public String getPerfilConsulta(){return perfilConsulta;} public void setPerfilConsulta(String v){perfilConsulta=v;} public String getOrigenConsulta(){return origenConsulta;} public void setOrigenConsulta(String v){origenConsulta=v;} public String getVersionReglas(){return versionReglas;} public void setVersionReglas(String v){versionReglas=v;} public Boolean getModoSombra(){return modoSombra;} public void setModoSombra(Boolean v){modoSombra=v;} public String getHuellaOferta(){return huellaOferta;} public void setHuellaOferta(String v){huellaOferta=v;} public String getHashContexto(){return hashContexto;} public void setHashContexto(String v){hashContexto=v;} public String getSituacionResultante(){return situacionResultante;} public void setSituacionResultante(String v){situacionResultante=v;} public Integer getTotalReprobadas(){return totalReprobadas;} public void setTotalReprobadas(Integer v){totalReprobadas=v;} public String getResultadoGeneral(){return resultadoGeneral;} public void setResultadoGeneral(String v){resultadoGeneral=v;} public String getEstadoEvaluacion(){return estadoEvaluacion;} public void setEstadoEvaluacion(String v){estadoEvaluacion=v;} public Date getFechaEvaluacion(){return fechaEvaluacion;} public void setFechaEvaluacion(Date v){fechaEvaluacion=v;}
}
