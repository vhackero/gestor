package mx.gob.sedesol.basegestor.commons.dto.admin;

import java.io.Serializable;

public class ImportacionFuenteEstadoDTO implements Serializable {
    private static final long serialVersionUID=1L;
    private final String id;
    private final Long usuario;
    private volatile int total;
    private volatile int importados;
    private volatile boolean terminado;
    private volatile String mensaje="Importación en espera.";
    private volatile long fin;
    public ImportacionFuenteEstadoDTO(String id,Long usuario,int total) {this.id=id;this.usuario=usuario;this.total=total;}
    public String getId() {return id;}
    public Long getUsuario() {return usuario;}
    public int getTotal() {return total;}
    public int getImportados() {return importados;}
    public boolean isTerminado() {return terminado;}
    public String getMensaje() {return mensaje;}
    public long getFin() {return fin;}
    public int getPorcentaje() {return total==0 ? 0 : Math.min(100,(int)((long)importados*100/total));}
    public void avanzar(int cantidad) {importados+=cantidad;mensaje="Importados "+importados+" de "+total+" usuarios detectados.";}
    public void finalizar(String texto) {mensaje=texto;fin=System.currentTimeMillis();terminado=true;}
}
