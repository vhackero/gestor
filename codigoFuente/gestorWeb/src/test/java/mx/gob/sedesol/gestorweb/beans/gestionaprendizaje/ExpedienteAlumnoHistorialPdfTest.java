package mx.gob.sedesol.gestorweb.beans.gestionaprendizaje;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import java.io.*;
import java.lang.reflect.Field;
import java.math.*;
import java.nio.file.*;
import java.util.*;
import javax.faces.context.*;
import org.junit.Test;
import org.primefaces.context.RequestContext;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.parser.PdfTextExtractor;
import mx.gob.sedesol.basegestor.commons.dto.gestion.aprendizaje.EventoConstanciaDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.HistorialAcademicoDTO;
import mx.gob.sedesol.basegestor.commons.utils.ColumnasHistorial;
import mx.gob.sedesol.basegestor.service.ParametroSistemaService;
import mx.gob.sedesol.gestorweb.beans.administracion.BitacoraBean;
import mx.gob.sedesol.gestorweb.beans.acceso.BaseBean;
import mx.gob.sedesol.gestorweb.beans.gestionaprendizaje.alumnoview.ConstanciasBean;
import mx.gob.sedesol.gestorweb.sistema.SistemaBean;

/** Recorre la accion real del boton del gestor hasta el contenido del PDF. */
public class ExpedienteAlumnoHistorialPdfTest {
    private abstract static class Contexto extends FacesContext {
        static void activar(FacesContext contexto) { setCurrentInstance(contexto); }
    }

    @Test public void imprimeSeleccionActualConDatosDelEstudianteConsultado() throws Exception { probar(true); }

    @Test public void estudianteTambienImprimeTextosActualizados() throws Exception { probar(false); }

    private void probar(boolean gestor) throws Exception {
        FacesContext faces = mock(FacesContext.class);
        ExternalContext external = mock(ExternalContext.class);
        when(faces.getExternalContext()).thenReturn(external);
        when(faces.getAttributes()).thenReturn(new HashMap<Object, Object>());
        when(external.getResourceAsStream(anyString())).thenAnswer(invocation -> {
            Path archivo = Paths.get("WebContent" + (String) invocation.getArguments()[0]);
            return Files.exists(archivo) ? Files.newInputStream(archivo) : null;
        });
        Contexto.activar(faces);
        RequestContext request = mock(RequestContext.class);
        RequestContext.setCurrentInstance(request, faces);
        try {
            BaseBean bean = gestor ? spy(new ExpedienteAlumnoBean()) : spy(new ConstanciasBean());
            SistemaBean sistema = new SistemaBean();
            sistema.establecerTexto(ColumnasHistorial.ASIGNATURA.getClaveTexto(), "Unidad \"Área\" & <B>");
            inyectar(bean, "sistema", sistema);
            doReturn(1L).when(bean).idPersonaEnSesion();
            ParametroSistemaService parametros = mock(ParametroSistemaService.class);
            inyectar(bean, "parametrosSistemaService", parametros);
            inyectar(bean, "bitacoraBean", mock(BitacoraBean.class));
            HistorialAcademicoDTO historial = new HistorialAcademicoDTO();
            historial.setNombre("ESTUDIANTE_CONSULTADO");
            historial.setMatricula("ES241100064");
            historial.setPromedio(BigDecimal.ZERO);
            historial.setCreditos(BigDecimal.ZERO);
            historial.setAprobadas(BigInteger.ZERO);
            historial.setReprobadas(BigInteger.ZERO);
            historial.setNopresentadas(BigInteger.ZERO);
            historial.setTotal(BigInteger.ONE);
            if (gestor) ((ExpedienteAlumnoBean) bean).setHistorial(historial);
            else ((ConstanciasBean) bean).setHistorialAcademico(historial);
            EventoConstanciaDTO fila = new EventoConstanciaDTO();
            fila.setNombreEc("ASIGNATURA_CONSULTADA");
            fila.setNombreGrupo("GRUPO_CONSULTADO");
            fila.setCalificacionFinal(92.5);
            if (gestor) ((ExpedienteAlumnoBean) bean).setListaEventos(Arrays.asList(fila));
            else inyectar(bean, "eventos", Arrays.asList(fila));

            when(parametros.obtenerParametro(ColumnasHistorial.PARAMETRO)).thenReturn("GRUPO,ASIGNATURA");
            generar(bean);
            assertNotNull("Debe generar el PDF", contenido(bean));
            String primero = leerPdf(bean, "gestor-sin-calificacion.pdf");
            assertTrue(primero.contains("ESTUDIANTE_CONSULTADO"));
            assertTrue(primero.contains(sistema.obtenerCabeceraHistorial("ASIGNATURA")));
            assertTrue(primero.contains("ASIGNATURA_CONSULTADA"));
            assertTrue("Falta la columna seleccionada de grupo", primero.contains("GRUPO_CONSULTADO"));
            assertFalse("Se imprimio una calificacion oculta", primero.contains("92.5"));
            assertTrue("El PDF debe conservar Grupo antes de Asignatura",
                    primero.indexOf("GRUPO_CONSULTADO") < primero.indexOf("ASIGNATURA_CONSULTADA"));

            sistema.establecerTexto(ColumnasHistorial.ASIGNATURA.getClaveTexto(), "Materia ACTUALIZADA");
            when(parametros.obtenerParametro(ColumnasHistorial.PARAMETRO)).thenReturn("CALIFICACION,ASIGNATURA");
            generar(bean);
            String segundo = leerPdf(bean, "gestor-con-calificacion.pdf");
            assertTrue(segundo.contains("92.5"));
            assertTrue("El PDF debe conservar Calificacion antes de Asignatura",
                    segundo.indexOf("92.5") < segundo.indexOf("ASIGNATURA_CONSULTADA"));
            assertTrue(segundo.contains(sistema.obtenerCabeceraHistorial("ASIGNATURA")));
            assertFalse(segundo.contains("Unidad"));
            assertFalse(segundo.contains("GRUPO_CONSULTADO"));
            verify(parametros, times(2)).obtenerParametro(ColumnasHistorial.PARAMETRO);
            verify(external, never()).getResourceAsStream(
                    "/resources/jasperReport/gestionAprendizaje/expediente_academico.jasper");
        } finally {
            RequestContext.setCurrentInstance(null, faces);
            Contexto.activar(null);
        }
    }

    private void generar(BaseBean bean) {
        if (bean instanceof ExpedienteAlumnoBean) ((ExpedienteAlumnoBean) bean).generarReporte();
        else ((ConstanciasBean) bean).generarReporte();
    }

    private org.primefaces.model.StreamedContent contenido(BaseBean bean) {
        return bean instanceof ExpedienteAlumnoBean ? ((ExpedienteAlumnoBean) bean).getReportePDF()
                : ((ConstanciasBean) bean).getReportePDF();
    }

    private void inyectar(Object bean, String nombre, Object valor) throws Exception {
        Class<?> tipo = bean instanceof ExpedienteAlumnoBean ? ExpedienteAlumnoBean.class : ConstanciasBean.class;
        Field f = tipo.getDeclaredField(nombre);
        f.setAccessible(true);
        f.set(bean, valor);
    }

    private String leerPdf(BaseBean bean, String nombre) throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try (InputStream in = contenido(bean).getStream()) {
            byte[] buffer = new byte[4096];
            int leidos;
            while ((leidos = in.read(buffer)) != -1) out.write(buffer, 0, leidos);
        }
        Path carpeta = Paths.get("target/historial-gestor-validation");
        Files.createDirectories(carpeta);
        Files.write(carpeta.resolve(bean instanceof ExpedienteAlumnoBean ? nombre : "estudiante-" + nombre), out.toByteArray());
        PdfReader reader = new PdfReader(out.toByteArray());
        try {
            PdfTextExtractor extractor = new PdfTextExtractor(reader);
            StringBuilder texto = new StringBuilder();
            for (int i = 1; i <= reader.getNumberOfPages(); i++) texto.append(extractor.getTextFromPage(i));
            return texto.toString();
        } finally {
            reader.close();
        }
    }
}
