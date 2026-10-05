package mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
public class UnidadEvaluacionCurricularV2DTO implements Serializable {
 private static final long serialVersionUID=1L;
 private Long idPrograma; private String clave; private String nombre; private String tipoUd; private Integer semestre; private String bloque;
 private EstadoUnidadCurricularV2 estado; private CausaPendienteUnidadV2 causa; private Boolean ofertada; private Boolean seriacionConocida;
 private Integer intentos; private List<String> referenciasHistoricas=new ArrayList<String>();
 public Long getIdPrograma(){return idPrograma;} public void setIdPrograma(Long v){idPrograma=v;} public String getClave(){return clave;} public void setClave(String v){clave=v;} public String getNombre(){return nombre;} public void setNombre(String v){nombre=v;} public String getTipoUd(){return tipoUd;} public void setTipoUd(String v){tipoUd=v;} public Integer getSemestre(){return semestre;} public void setSemestre(Integer v){semestre=v;} public String getBloque(){return bloque;} public void setBloque(String v){bloque=v;} public EstadoUnidadCurricularV2 getEstado(){return estado;} public void setEstado(EstadoUnidadCurricularV2 v){estado=v;} public CausaPendienteUnidadV2 getCausa(){return causa;} public void setCausa(CausaPendienteUnidadV2 v){causa=v;} public Boolean getOfertada(){return ofertada;} public void setOfertada(Boolean v){ofertada=v;} public Boolean getSeriacionConocida(){return seriacionConocida;} public void setSeriacionConocida(Boolean v){seriacionConocida=v;} public Integer getIntentos(){return intentos;} public void setIntentos(Integer v){intentos=v;} public List<String> getReferenciasHistoricas(){return referenciasHistoricas;} public void setReferenciasHistoricas(List<String> v){referenciasHistoricas=v==null?new ArrayList<String>():v;}
}
