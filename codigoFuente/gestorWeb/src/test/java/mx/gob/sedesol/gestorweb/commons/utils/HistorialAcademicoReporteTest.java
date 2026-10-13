package mx.gob.sedesol.gestorweb.commons.utils;

import static org.junit.Assert.*;
import java.io.*;
import java.nio.file.*;
import java.util.*;
import org.junit.Test;
import mx.gob.sedesol.basegestor.commons.dto.gestion.aprendizaje.EventoConstanciaDTO;
import mx.gob.sedesol.basegestor.commons.utils.ColumnasHistorial;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;

public class HistorialAcademicoReporteTest {
    @Test public void imprimeTodasLasColumnasYNotasSinTextoNull() throws Exception {
        JasperPrint print = imprimir(ColumnasHistorial.leer(null), "todas");
        String texto = texto(print);
        assertTrue(texto.contains("92.5"));
        assertTrue(texto.contains("NP"));
        assertFalse(texto.contains("null"));
        assertTrue(texto.contains("GRUPO_PRUEBA"));
        assertTrue(texto.contains("ASIGNATURA_PRUEBA"));
        assertEquals(842, print.getPageWidth());
    }

    @Test public void ocultaColumnasTambienEnPdfYConservaNota() throws Exception {
        JasperPrint print = imprimir(ColumnasHistorial.leer("ASIGNATURA,CALIFICACION"), "reducidas");
        String texto = texto(print);
        assertTrue(texto.contains("ASIGNATURA_PRUEBA"));
        assertTrue(texto.contains("92.5"));
        assertFalse(texto.contains("GRUPO_PRUEBA"));
        assertFalse(texto.contains("Nombre del grupo"));
        assertFalse(texto.contains("null"));
        assertEquals(595, print.getPageWidth());
    }

    @Test public void permiteOcultarCalificacionYSoloUnaColumna() throws Exception {
        String texto = texto(imprimir(ColumnasHistorial.leer("GRUPO"), "una"));
        assertTrue(texto.contains("GRUPO_PRUEBA"));
        assertFalse(texto.contains("ASIGNATURA_PRUEBA"));
        assertFalse(texto.contains("92.5"));
        assertFalse(texto.contains("Calificación final"));
    }

    private JasperPrint imprimir(List<ColumnasHistorial> columnas, String nombre) throws Exception {
        JasperReport report;
        try (InputStream in = Files.newInputStream(Paths.get(
                "WebContent/resources/jasperReport/gestionAprendizaje/expediente_academico.jrxml"))) {
            report = HistorialAcademicoReporte.compilar(in, columnas);
        }
        List<EventoConstanciaDTO> filas = new ArrayList<>();
        for (Double nota : Arrays.asList(92.5, 666.0, null)) {
            EventoConstanciaDTO fila = new EventoConstanciaDTO();
            fila.setNombreEc("ASIGNATURA_PRUEBA");
            fila.setNombreGrupo("GRUPO_PRUEBA");
            fila.setCalificacionFinal(nota);
            fila.setModalidad("En línea");
            fila.setFecha(new Date(0));
            filas.add(fila);
        }
        Map<String, Object> p = new HashMap<>();
        for (JRParameter param : report.getParameters()) {
            if (!param.isSystemDefined() && param.getValueClass() == String.class) p.put(param.getName(), "");
            if (!param.isSystemDefined() && param.getValueClass() == Integer.class) p.put(param.getName(), 0);
        }
        p.put("NOMBRE_ESTRUCTURA", "Semestre");
        p.put("dsHistorial", new JRBeanArrayDataSource(filas.toArray()));
        JasperPrint print = JasperFillManager.fillReport(report, p, new JREmptyDataSource(1));
        byte[] pdf = JasperExportManager.exportReportToPdf(print);
        assertTrue(pdf.length > 1000);
        assertEquals("%PDF", new String(pdf, 0, 4, "US-ASCII"));
        Path carpeta = Paths.get("target/historial-validation");
        Files.createDirectories(carpeta);
        Files.write(carpeta.resolve("historial-" + nombre + ".pdf"), pdf);
        javax.imageio.ImageIO.write((java.awt.image.RenderedImage) JasperPrintManager.printPageToImage(print, 0, 1.4f),
                "png", carpeta.resolve("historial-" + nombre + ".png").toFile());
        return print;
    }

    private String texto(JasperPrint print) {
        StringBuilder texto = new StringBuilder();
        for (JRPrintPage pagina : print.getPages()) agregar(pagina.getElements(), texto);
        return texto.toString();
    }

    private void agregar(List<JRPrintElement> elementos, StringBuilder texto) {
        for (JRPrintElement elemento : elementos) {
            if (elemento instanceof JRPrintText) texto.append(((JRPrintText) elemento).getFullText()).append("\n");
            if (elemento instanceof JRPrintFrame) agregar(((JRPrintFrame) elemento).getElements(), texto);
        }
    }
}
