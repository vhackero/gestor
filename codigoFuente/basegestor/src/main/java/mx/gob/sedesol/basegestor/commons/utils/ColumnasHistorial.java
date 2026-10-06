package mx.gob.sedesol.basegestor.commons.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.List;

/** Catalogo unico de columnas del historial para las vistas y la impresion. */
public enum ColumnasHistorial {
    ASIGNATURA("Nombre de la asignatura", "nombreEc", 140, "col.asignatura"),
    GRUPO("Nombre del grupo", "nombreGrupo", 65, "col.nombreGrupo"),
    ESTRUCTURA("Estructura curricular", "cs", 45, "col.estructura"),
    CLAVE("Clave", "clave", 60, "col.clave"),
    CLAVE_SEP("Clave SEP", "clavesep", 60, "col.claveSep"),
    CREDITOS("Créditos", "creditos", 40, "creditos"),
    PERIODO("Periodo", "periodo", 55, "col.periodo"),
    TIPO_EVALUACION("Tipo de evaluación", "tipoEvaluacion", 50, "col.tipoEvaluacion"),
    ACTA("No. acta", "nActa", 85, "noActa"),
    MODALIDAD("Modalidad", "modalidad", 60, "modalidad"),
    FECHA("Fecha de finalización", "fecha", 60, "fechaFinalizacion"),
    ENCUESTA("Encuesta final", "nombreEstatusEncuesta", 60, "encuestaFinal"),
    PROMEDIO("Promedio de evaluaciones", "calificacionTotal", 60, "promedioEvaluaciones"),
    ASISTENCIA("Porcentaje de asistencias", "porcentajeAsistencia", 60, "porcentajeAsistencias"),
    CALIFICACION("Calificación final", "calificacionFinalTexto", 55, "calificacionFinal");

    public static final String PARAMETRO = "COLUMNAS_HISTORIAL_ACADEMICO";
    public static final String ERROR = "Seleccione al menos una columna válida del historial académico.";
    private final String etiqueta;
    private final String propiedad;
    private final int ancho;
    private final String claveTexto;

    ColumnasHistorial(String etiqueta, String propiedad, int ancho, String claveTexto) {
        this.etiqueta = etiqueta;
        this.propiedad = propiedad;
        this.ancho = ancho;
        this.claveTexto = "gw.alumno.constancias." + claveTexto;
    }

    public String getClave() { return name(); }
    public String getClaveTexto() { return claveTexto; }
    public String getEtiqueta() { return etiqueta; }
    public String getPropiedad() { return propiedad; }
    public String getPropiedadOrden() { return this == CALIFICACION ? "calificacionFinal" : propiedad; }
    public int getAncho() { return ancho; }

    /** Ausencia del parametro conserva todas las columnas; un valor invalido se rechaza. */
    public static List<ColumnasHistorial> leer(String valor) {
        if (valor == null) return Arrays.asList(values());
        // Conserva el orden elegido y elimina duplicados sin mover la primera aparicion.
        Set<ColumnasHistorial> seleccion = new LinkedHashSet<>();
        try {
            for (String clave : valor.split(",", -1)) {
                String normalizada = clave.trim();
                if (!normalizada.isEmpty()) seleccion.add(valueOf(normalizada));
            }
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException(ERROR, ex);
        }
        if (seleccion.isEmpty()) throw new IllegalArgumentException(ERROR);
        return new ArrayList<>(seleccion);
    }

    public static String serializar(List<String> claves) {
        return String.join(",", claves == null ? new ArrayList<String>() : claves);
    }
}
