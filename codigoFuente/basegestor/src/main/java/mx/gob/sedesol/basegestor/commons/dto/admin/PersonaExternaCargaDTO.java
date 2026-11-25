package mx.gob.sedesol.basegestor.commons.dto.admin;

import java.io.Serializable;
import java.util.*;

public class PersonaExternaCargaDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    public static final List<String> COLUMNAS = Collections.unmodifiableList(Arrays.asList(
        "nombre_usuario", "password", "nombre", "apellidop", "apellidom", "id_plan", "division",
        "correo_institucional", "fecha_nacimiento", "curp", "nivel", "id_fuente_externa", "tipo_usuario", "id_convocatoria"));
    private final int fila;
    private final List<String> valores;
    public PersonaExternaCargaDTO(int fila, List<String> valores) {
        if (valores.size() != COLUMNAS.size()) throw new IllegalArgumentException("La fila debe contener 14 columnas.");
        this.fila = fila;
        this.valores = Collections.unmodifiableList(new ArrayList<>(valores));
    }
    public int getFila() { return fila; }
    public String get(String columna) { return valores.get(COLUMNAS.indexOf(columna)); }
}
