package mx.gob.sedesol.basegestor.commons.dto.admin;
import java.io.Serializable;
public class FilaAsignacionPlanDTO implements Serializable {
    private final int fila;
    private final String usuario,plan,convocatoria;
    public FilaAsignacionPlanDTO(int fila,String usuario,String plan,String convocatoria){this.fila=fila;this.usuario=usuario;this.plan=plan;this.convocatoria=convocatoria;}
    public int getFila(){return fila;} public String getUsuario(){return usuario;}
    public String getPlan(){return plan;} public String getConvocatoria(){return convocatoria;}
}
