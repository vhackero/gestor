package mx.gob.sedesol.gestorweb.gestionescolar;

import static org.junit.Assert.*;
import java.io.*;
import java.util.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.DispersionPendienteDTO;
import mx.gob.sedesol.gestorweb.commons.utils.DispersionPendientesExcel;

public class DispersionPendientesExcelTest {
    @Test public void excelConservaMatriculaTextoAcentosYTotalSinFormulas() throws Exception {
        List<DispersionPendienteDTO> pendientes = new ArrayList<>();
        for (int n = 0; n < 120; n++) {
            DispersionPendienteDTO alumno = new DispersionPendienteDTO();
            alumno.setMatricula(n == 0 ? "000123" : "ES"+n);
            alumno.setNombre("=Alumno de prueba");
            alumno.setPlan("Administración y Gestión Pública");
            alumno.setPrograma("Interaprendizaje");
            alumno.setSemestre("Semestre 2"); alumno.setBloque("Bloque 2");
            alumno.setProceso("Inscripciones 2026-2");
            pendientes.add(alumno);
        }
        ByteArrayOutputStream salida = new ByteArrayOutputStream();
        DispersionPendientesExcel.escribir(salida, pendientes, "Convocatoria origen");
        try (XSSFWorkbook libro = new XSSFWorkbook(new ByteArrayInputStream(salida.toByteArray()))) {
            assertEquals(120, libro.getSheetAt(0).getLastRowNum());
            assertEquals("Matrícula", libro.getSheetAt(0).getRow(0).getCell(0).getStringCellValue());
            assertEquals("000123", libro.getSheetAt(0).getRow(1).getCell(0).getStringCellValue());
            assertEquals(Cell.CELL_TYPE_STRING, libro.getSheetAt(0).getRow(1).getCell(1).getCellType());
            assertEquals("Administración y Gestión Pública", libro.getSheetAt(0).getRow(1).getCell(3).getStringCellValue());
            assertEquals("", libro.getSheetAt(0).getRow(1).getCell(2).getStringCellValue());
            assertEquals(120, libro.getSheetAt(0).getRow(1).getCell(11).getNumericCellValue(), 0);
            assertEquals("ES119", libro.getSheetAt(0).getRow(120).getCell(0).getStringCellValue());
        }
    }
}
