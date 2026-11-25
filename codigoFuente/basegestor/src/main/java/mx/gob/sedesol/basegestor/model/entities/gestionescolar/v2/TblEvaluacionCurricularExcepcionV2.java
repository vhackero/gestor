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
@Table(name="tbl_evaluacion_curricular_excepcion_v2")
public class TblEvaluacionCurricularExcepcionV2 implements Serializable {
    private static final long serialVersionUID=1L;
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @Column(name="id_evaluacion") private Long idEvaluacion;
    @Column(name="codigo_excepcion") private String codigoExcepcion;
    @Column(name="tipo_excepcion") private String tipoExcepcion;
    @Column(name="estado_excepcion") private String estadoExcepcion;
    @Column(name="codigo_regla_origen") private String codigoReglaOrigen;
    @Column(name="condiciones_evaluadas") private String condicionesEvaluadas;
    @Column(name="condiciones_cumplidas") private String condicionesCumplidas;
    @Column(name="condiciones_no_cumplidas") private String condicionesNoCumplidas;
    private String motivos;
    @Column(name="unidades_relacionadas") private String unidadesRelacionadas;
    private String evidencias;
    @Column(name="explicacion_tecnica") private String explicacionTecnica;
    @Column(name="version_normativa") private String versionNormativa;
    @Temporal(TemporalType.TIMESTAMP) @Column(name="fecha_evaluacion") private Date fechaEvaluacion;
    public Long getId(){return id;} public void setId(Long v){id=v;} public Long getIdEvaluacion(){return idEvaluacion;} public void setIdEvaluacion(Long v){idEvaluacion=v;} public String getCodigoExcepcion(){return codigoExcepcion;} public void setCodigoExcepcion(String v){codigoExcepcion=v;} public String getTipoExcepcion(){return tipoExcepcion;} public void setTipoExcepcion(String v){tipoExcepcion=v;} public String getEstadoExcepcion(){return estadoExcepcion;} public void setEstadoExcepcion(String v){estadoExcepcion=v;} public String getCodigoReglaOrigen(){return codigoReglaOrigen;} public void setCodigoReglaOrigen(String v){codigoReglaOrigen=v;} public String getCondicionesEvaluadas(){return condicionesEvaluadas;} public void setCondicionesEvaluadas(String v){condicionesEvaluadas=v;} public String getCondicionesCumplidas(){return condicionesCumplidas;} public void setCondicionesCumplidas(String v){condicionesCumplidas=v;} public String getCondicionesNoCumplidas(){return condicionesNoCumplidas;} public void setCondicionesNoCumplidas(String v){condicionesNoCumplidas=v;} public String getMotivos(){return motivos;} public void setMotivos(String v){motivos=v;} public String getUnidadesRelacionadas(){return unidadesRelacionadas;} public void setUnidadesRelacionadas(String v){unidadesRelacionadas=v;} public String getEvidencias(){return evidencias;} public void setEvidencias(String v){evidencias=v;} public String getExplicacionTecnica(){return explicacionTecnica;} public void setExplicacionTecnica(String v){explicacionTecnica=v;} public String getVersionNormativa(){return versionNormativa;} public void setVersionNormativa(String v){versionNormativa=v;} public Date getFechaEvaluacion(){return fechaEvaluacion;} public void setFechaEvaluacion(Date v){fechaEvaluacion=v;}
}
