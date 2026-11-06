package mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/** Resultado explicable del cálculo único de semestre de referencia RN-6.9. */
public class SemestreReferenciaV2DTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer valor;
    private String estado = "REQUIERE_REVISION";
    private String fuente;
    private String corte;
    private String calidadDato = "NO_DETERMINADA";
    private List<String> periodosContados = new ArrayList<String>();
    private List<String> periodosExcluidos = new ArrayList<String>();

    public Integer getValor() { return valor; }
    public void setValor(Integer valor) { this.valor = valor; }
    public String getEstado() { return estado; }
    public void setEstado(String valor) { estado = valor; }
    public String getFuente() { return fuente; }
    public void setFuente(String valor) { fuente = valor; }
    public String getCorte() { return corte; }
    public void setCorte(String valor) { corte = valor; }
    public String getCalidadDato() { return calidadDato; }
    public void setCalidadDato(String valor) { calidadDato = valor; }
    public List<String> getPeriodosContados() { return periodosContados; }
    public List<String> getPeriodosExcluidos() { return periodosExcluidos; }
}
