package mx.gob.sedesol.basegestor.model.entities.gestionescolar.v2;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_evaluacion_curricular_ud_v2")
public class TblEvaluacionCurricularUdV2 implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @Column(name="id_evaluacion") private Long idEvaluacion;
    @Column(name="id_programa") private Long idPrograma;
    @Column(name="clave_ud") private String claveUd;
    @Column(name="tipo_ud") private String tipoUd;
    private Integer semestre; private String bloque;
    @Column(name="estado_normalizado") private String estadoNormalizado;
    @Column(name="causa_pendiente") private String causaPendiente;
    private Boolean ofertada;
    @Column(name="seleccionable_origen") private Boolean seleccionableOrigen;
    @Column(name="ofertada_evaluada_v2") private Boolean ofertadaEvaluadaV2;
    @Column(name="seriacion_conocida") private Boolean seriacionConocida;
    @Column(name="seriacion_directa") private Boolean seriacionDirecta;
    @Column(name="seriacion_indirecta") private Boolean seriacionIndirecta;
    @Column(name="habilitada_sombra") private Boolean habilitadaSombra;
    @Column(name="prioritaria_sombra") private Boolean prioritariaSombra;
    @Column(name="decision_v2") private String decisionV2;
    @Column(name="motivos_v2") private String motivosV2;
    @Column(name="fuente_estado") private String fuenteEstado;
    @Column(name="referencias_historicas") private String referenciasHistoricas;
    public Long getId(){return id;} public void setId(Long v){id=v;} public Long getIdEvaluacion(){return idEvaluacion;} public void setIdEvaluacion(Long v){idEvaluacion=v;} public Long getIdPrograma(){return idPrograma;} public void setIdPrograma(Long v){idPrograma=v;} public String getClaveUd(){return claveUd;} public void setClaveUd(String v){claveUd=v;} public String getTipoUd(){return tipoUd;} public void setTipoUd(String v){tipoUd=v;} public Integer getSemestre(){return semestre;} public void setSemestre(Integer v){semestre=v;} public String getBloque(){return bloque;} public void setBloque(String v){bloque=v;} public String getEstadoNormalizado(){return estadoNormalizado;} public void setEstadoNormalizado(String v){estadoNormalizado=v;} public String getCausaPendiente(){return causaPendiente;} public void setCausaPendiente(String v){causaPendiente=v;} public Boolean getOfertada(){return ofertada;} public void setOfertada(Boolean v){ofertada=v;} public Boolean getSeleccionableOrigen(){return seleccionableOrigen;} public void setSeleccionableOrigen(Boolean v){seleccionableOrigen=v;} public Boolean getOfertadaEvaluadaV2(){return ofertadaEvaluadaV2;} public void setOfertadaEvaluadaV2(Boolean v){ofertadaEvaluadaV2=v;} public Boolean getSeriacionConocida(){return seriacionConocida;} public void setSeriacionConocida(Boolean v){seriacionConocida=v;} public Boolean getSeriacionDirecta(){return seriacionDirecta;} public void setSeriacionDirecta(Boolean v){seriacionDirecta=v;} public Boolean getSeriacionIndirecta(){return seriacionIndirecta;} public void setSeriacionIndirecta(Boolean v){seriacionIndirecta=v;} public Boolean getHabilitadaSombra(){return habilitadaSombra;} public void setHabilitadaSombra(Boolean v){habilitadaSombra=v;} public Boolean getPrioritariaSombra(){return prioritariaSombra;} public void setPrioritariaSombra(Boolean v){prioritariaSombra=v;} public String getDecisionV2(){return decisionV2;} public void setDecisionV2(String v){decisionV2=v;} public String getMotivosV2(){return motivosV2;} public void setMotivosV2(String v){motivosV2=v;} public String getFuenteEstado(){return fuenteEstado;} public void setFuenteEstado(String v){fuenteEstado=v;} public String getReferenciasHistoricas(){return referenciasHistoricas;} public void setReferenciasHistoricas(String v){referenciasHistoricas=v;}
}
