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
@Table(name="tbl_evaluacion_curricular_regla_v2")
public class TblEvaluacionCurricularReglaV2 implements Serializable {
    private static final long serialVersionUID=1L;
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @Column(name="id_evaluacion") private Long idEvaluacion;
    @Column(name="id_evaluacion_ud") private Long idEvaluacionUd;
    @Column(name="codigo_regla") private String codigoRegla;
    @Column(name="codigo_motivo") private String codigoMotivo;
    private Integer prioridad; private Boolean evaluada; private Boolean aplicada; private String resultado; private String detalle;
    @Column(name="variables_relevantes") private String variablesRelevantes;
    @Temporal(TemporalType.TIMESTAMP) @Column(name="fecha_evaluacion") private Date fechaEvaluacion;
    public Long getId(){return id;} public void setId(Long v){id=v;} public Long getIdEvaluacion(){return idEvaluacion;} public void setIdEvaluacion(Long v){idEvaluacion=v;} public Long getIdEvaluacionUd(){return idEvaluacionUd;} public void setIdEvaluacionUd(Long v){idEvaluacionUd=v;} public String getCodigoRegla(){return codigoRegla;} public void setCodigoRegla(String v){codigoRegla=v;} public String getCodigoMotivo(){return codigoMotivo;} public void setCodigoMotivo(String v){codigoMotivo=v;} public Integer getPrioridad(){return prioridad;} public void setPrioridad(Integer v){prioridad=v;} public Boolean getEvaluada(){return evaluada;} public void setEvaluada(Boolean v){evaluada=v;} public Boolean getAplicada(){return aplicada;} public void setAplicada(Boolean v){aplicada=v;} public String getResultado(){return resultado;} public void setResultado(String v){resultado=v;} public String getDetalle(){return detalle;} public void setDetalle(String v){detalle=v;} public String getVariablesRelevantes(){return variablesRelevantes;} public void setVariablesRelevantes(String v){variablesRelevantes=v;} public Date getFechaEvaluacion(){return fechaEvaluacion;} public void setFechaEvaluacion(Date v){fechaEvaluacion=v;}
}
