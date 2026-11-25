package mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
/** Evidencia estructurada de una condición A..G de RN-6.1.2. */
public class CondicionComunAvanceV2DTO implements Serializable {
 private static final long serialVersionUID=1L; private String codigo; private EstadoCondicionAvanceV2 estado; private String detalle;
 private List<Long> unidadesRelacionadas=new ArrayList<Long>(); private List<String> evidencias=new ArrayList<String>();
 public String getCodigo(){return codigo;} public void setCodigo(String v){codigo=v;} public EstadoCondicionAvanceV2 getEstado(){return estado;} public void setEstado(EstadoCondicionAvanceV2 v){estado=v;} public String getDetalle(){return detalle;} public void setDetalle(String v){detalle=v;} public List<Long> getUnidadesRelacionadas(){return unidadesRelacionadas;} public List<String> getEvidencias(){return evidencias;}
}
