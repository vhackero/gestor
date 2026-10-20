package mx.gob.sedesol.basegestor.commons.dto.admin;
import java.io.Serializable;
import java.util.*;
public class EstadoRegistroMoodleDTO implements Serializable {
    private final String id;private final Long usuario;private final int total;
    private volatile int registrados,reutilizados,omitidos,fallidos,procesados;
    private volatile boolean terminado;private volatile long fin;private volatile String error;
    private final List<String[]> errores=new ArrayList<>();
    public EstadoRegistroMoodleDTO(String id,Long usuario,long total){this.id=id;this.usuario=usuario;this.total=(int)Math.min(Integer.MAX_VALUE,total);}
    public String getId(){return id;}public Long getUsuario(){return usuario;}public int getTotal(){return total;}
    public int getRegistrados(){return registrados;}public int getReutilizados(){return reutilizados;}public int getOmitidos(){return omitidos;}public int getFallidos(){return fallidos;}public int getProcesados(){return procesados;}
    public boolean isTerminado(){return terminado;}public long getFin(){return fin;}public String getError(){return error;}
    public int getPorcentaje(){return total==0?0:Math.min(100,(int)((long)procesados*100/total));}
    public void avanzar(int resultado){if(resultado==1)registrados++;else if(resultado==2)reutilizados++;else omitidos++;procesados++;}
    public synchronized void fallar(Long persona,String clave){fallidos++;procesados++;if(errores.size()<100)errores.add(new String[]{persona.toString(),clave});}
    public synchronized List<String[]> getErrores(){return new ArrayList<>(errores);}
    public void finalizar(String clave){error=clave;fin=System.currentTimeMillis();terminado=true;}
}
