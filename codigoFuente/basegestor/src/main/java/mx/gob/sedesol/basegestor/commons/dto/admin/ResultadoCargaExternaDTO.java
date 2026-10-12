package mx.gob.sedesol.basegestor.commons.dto.admin;

import java.io.Serializable;
import java.util.*;

public class ResultadoCargaExternaDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private final List<String> errores = new ArrayList<>();
    private int insertados;
    public List<String> getErrores() { return Collections.unmodifiableList(errores); }
    public void agregarError(String error) { if (errores.size() < 100) errores.add(error); }
    public int getInsertados() { return insertados; }
    public void setInsertados(int valor) { insertados = valor; }
}
