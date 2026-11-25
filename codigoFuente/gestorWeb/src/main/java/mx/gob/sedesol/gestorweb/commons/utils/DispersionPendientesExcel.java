package mx.gob.sedesol.gestorweb.commons.utils;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.List;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.DispersionPendienteDTO;

public final class DispersionPendientesExcel {
    private DispersionPendientesExcel() { }

    public static void escribir(OutputStream salida, List<DispersionPendienteDTO> pendientes,
            String procesoOrigen) throws IOException {
        SXSSFWorkbook libro = new SXSSFWorkbook(100);
        try {
            Sheet hoja = libro.createSheet("Pendientes de matricular");
            String[] encabezados = {"Matrícula", "Nombre", "Correo institucional", "Plan", "Programa", "Clave",
                "Semestre", "Bloque", "Proceso con dispersión", "Proceso a matricular", "Fecha de inscripción",
                "Total de alumnos pendientes"};
            Row encabezado = hoja.createRow(0);
            for (int c = 0; c < encabezados.length; c++) {
                encabezado.createCell(c).setCellValue(encabezados[c]);
                hoja.setColumnWidth(c, (c == 1 || c == 3 || c == 4 || c == 8 || c == 9 ? 45 : 27) * 256);
            }
            SimpleDateFormat fecha = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            int numeroFila = 1;
            for (DispersionPendienteDTO alumno : pendientes) {
                String[] valores = {alumno.getMatricula(), alumno.getNombre(), alumno.getCorreo(), alumno.getPlan(),
                    alumno.getPrograma(), alumno.getClave(), alumno.getSemestre(), alumno.getBloque(), procesoOrigen,
                    alumno.getProceso(), alumno.getFechaInscripcion() == null ? "" : fecha.format(alumno.getFechaInscripcion())};
                Row fila = hoja.createRow(numeroFila++);
                for (int c = 0; c < valores.length; c++) {
                    // Siempre texto: preserva ceros y evita interpretar formulas en datos del alumno.
                    fila.createCell(c).setCellValue(valores[c] == null ? "" : valores[c]);
                }
                fila.createCell(11).setCellValue(pendientes.size());
            }
            hoja.createFreezePane(0, 1);
            hoja.setAutoFilter(new org.apache.poi.ss.util.CellRangeAddress(0, numeroFila - 1, 0, encabezados.length - 1));
            libro.write(salida);
        } finally {
            try { libro.close(); } finally { libro.dispose(); }
        }
    }
}
