package mx.gob.sedesol.basegestor.commons.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;

/** Catalogo unico de columnas del historial para las vistas y la impresion. */
public enum ColumnasHistorial {
    ASIGNATURA("Nombre de la asignatura", "nombreEc", 140),
    GRUPO("Nombre del grupo", "nombreGrupo", 65),
    ESTRUCTURA("Estructura curricular", "cs", 45),
    CLAVE("Clave", "clave", 60),
    CLAVE_SEP("Clave SEP", "clavesep", 60),
    CREDITOS("Créditos", "creditos", 40),
    PERIODO("Periodo", "periodo", 55),
    TIPO_EVALUACION("Tipo de evaluación", "tipoEvaluacion", 50),
    ACTA("No. acta", "nActa", 85),
    MODALIDAD("Modalidad", "modalidad", 60),
    FECHA("Fecha de finalización", "fecha", 60),
    ENCUESTA("Encuesta final", "nombreEstatusEncuesta", 60),
    PROMEDIO("Promedio de evaluaciones", "calificacionTotal", 60),
    ASISTENCIA("Porcentaje de asistencias", "porcentajeAsistencia", 60),
    CALIFICACION("Calificación final", "calificacionFinalTexto", 55);

    public static final String PARAMETRO = "COLUMNAS_HISTORIAL_ACADEMICO";
    public static final String ERROR = "Seleccione al menos una columna válida del historial académico.";
    private final String etiqueta;
    private final String propiedad;
    private final int ancho;

    ColumnasHistorial(String etiqueta, String propiedad, int ancho) {
        this.etiqueta = etiqueta;
        this.propiedad = propiedad;
        this.ancho = ancho;
    }

    public String getClave() { return name(); }
    public String getEtiqueta() { return etiqueta; }
    public String getPropiedad() { return propiedad; }
    public int getAncho() { return ancho; }

    /** Ausencia del parametro conserva todas las columnas; un valor invalido se rechaza. */
    public static List<ColumnasHistorial> leer(String valor) {
        if (valor == null) return Arrays.asList(values());
        EnumSet<ColumnasHistorial> seleccion = EnumSet.noneOf(ColumnasHistorial.class);
        try {
            for (String clave : valor.split(",", -1)) seleccion.add(valueOf(clave.trim()));
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
