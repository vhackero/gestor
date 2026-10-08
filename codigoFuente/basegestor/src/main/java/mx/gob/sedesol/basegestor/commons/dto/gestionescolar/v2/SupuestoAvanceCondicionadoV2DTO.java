package mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2;
import java.io.Serializable; import java.util.ArrayList; import java.util.List;
/** Supuesto tipificado identificado en sombra; no representa autorización. */
public class SupuestoAvanceCondicionadoV2DTO implements Serializable { private static final long serialVersionUID=1L;
 private CodigoSupuestoAvanceCondicionadoV2 codigo; private List<Long> unidadesRelacionadas=new ArrayList<Long>(); private String causa; private String evidencia;
 public CodigoSupuestoAvanceCondicionadoV2 getCodigo(){return codigo;} public void setCodigo(CodigoSupuestoAvanceCondicionadoV2 v){codigo=v;} public List<Long> getUnidadesRelacionadas(){return unidadesRelacionadas;} public String getCausa(){return causa;} public void setCausa(String v){causa=v;} public String getEvidencia(){return evidencia;} public void setEvidencia(String v){evidencia=v;}
}
