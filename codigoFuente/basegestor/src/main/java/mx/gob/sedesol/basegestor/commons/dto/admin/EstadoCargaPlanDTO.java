package mx.gob.sedesol.basegestor.commons.dto.admin;
import java.io.Serializable;
public class EstadoCargaPlanDTO implements Serializable {
    private final String id; private final Long usuario; private final int total;
    private volatile int procesados,asignados,omitidos;
    private volatile boolean terminado; private volatile long fin;
    private volatile String error; private volatile Object[] argumentos=new Object[0];
    public EstadoCargaPlanDTO(String id,Long usuario,int total){this.id=id;this.usuario=usuario;this.total=total;}
    public String getId(){return id;} public Long getUsuario(){return usuario;}
    public int getTotal(){return total;} public int getAsignados(){return asignados;} public int getOmitidos(){return omitidos;}
    public int getProcesados(){return procesados;} public boolean isTerminado(){return terminado;} public long getFin(){return fin;}
    public int getPorcentaje(){return total==0?0:(int)((long)procesados*100/total);}
    public String getError(){return error;} public Object[] getArgumentos(){return argumentos;}
    public void avanzar(int asignados,int omitidos){this.asignados+=asignados;this.omitidos+=omitidos;procesados+=asignados+omitidos;}
    public void finalizar(String error,Object... argumentos){this.error=error;this.argumentos=argumentos;fin=System.currentTimeMillis();terminado=true;}
}
