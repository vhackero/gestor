package mx.gob.sedesol.basegestor.commons.dto.admin;

import java.io.Serializable;

/** Relación identificada por su PK: no confundir asignación con historial académico. */
public class AsignacionPlanUsuarioDTO implements Serializable {
    private static final long serialVersionUID=1L;
    private Long idAsignacion,idPersona;
    private Integer idPlan,idConvocatoria;
    private String usuario,nombre,plan,convocatoria;
    public Long getIdAsignacion(){return idAsignacion;}
    public void setIdAsignacion(Long valor){idAsignacion=valor;}
    public Long getIdPersona(){return idPersona;}
    public void setIdPersona(Long valor){idPersona=valor;}
    public Integer getIdPlan(){return idPlan;}
    public void setIdPlan(Integer valor){idPlan=valor;}
    public Integer getIdConvocatoria(){return idConvocatoria;}
    public void setIdConvocatoria(Integer valor){idConvocatoria=valor;}
    public String getUsuario(){return usuario;}
    public void setUsuario(String valor){usuario=valor;}
    public String getNombre(){return nombre;}
    public void setNombre(String valor){nombre=valor;}
    public String getPlan(){return plan;}
    public void setPlan(String valor){plan=valor;}
    public String getConvocatoria(){return convocatoria;}
    public void setConvocatoria(String valor){convocatoria=valor;}
}
