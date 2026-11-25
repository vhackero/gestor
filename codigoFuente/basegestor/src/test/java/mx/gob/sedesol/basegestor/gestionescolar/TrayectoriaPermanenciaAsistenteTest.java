package mx.gob.sedesol.basegestor.service.impl.gestionescolar;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

import java.util.Calendar;
import java.util.Date;

import org.testng.annotations.Test;

import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.TrayectoriaPermanenciaDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.EstadoFuenteConsultaAsistente;

public class TrayectoriaPermanenciaAsistenteTest {

    @Test
    public void distingueConsultaDeOfertaVaciaDeFuenteParcial() {
        assertEquals(AsistenteInscripcionServiceImpl.estadoOferta(Boolean.TRUE), EstadoFuenteConsultaAsistente.DISPONIBLE);
        assertEquals(AsistenteInscripcionServiceImpl.estadoOferta(Boolean.FALSE), EstadoFuenteConsultaAsistente.VACIO);
        assertEquals(AsistenteInscripcionServiceImpl.estadoOferta(null), EstadoFuenteConsultaAsistente.PARCIAL);
        assertEquals(AsistenteInscripcionServiceImpl.estadoExpediente(false), EstadoFuenteConsultaAsistente.PARCIAL);
    }

    @Test
    public void calculaPlazoEnTiempoCalendarioYNoPorSemestresInscritos() {
        TrayectoriaPermanenciaDTO trayectoria = trayectoriaCompleta(5);

        TrayectoriaPermanenciaDTO resultado = AsistenteInscripcionServiceImpl.completarIndicadoresTrayectoria(trayectoria);

        assertTrue(resultado.getDeterminable());
        assertEquals(resultado.getDuracionNominalSemestres(), Integer.valueOf(8));
        assertEquals(resultado.getDuracionNominalAnios(), Integer.valueOf(4));
        assertEquals(resultado.getPlazoMaximoAnios(), Integer.valueOf(8));
        assertEquals(resultado.getPlazoMaximoSemestres(), Integer.valueOf(16));
        assertTrue(resultado.getPlazoRestanteCalendario().contains("año"));
        assertEquals(resultado.getSemestresConcluidos(), Integer.valueOf(4));
        assertTrue(resultado.getSemestreEnCurso());
        assertEquals(resultado.getPeriodosConInscripcion(), Integer.valueOf(3));
        assertNull(resultado.getMensajeRevision());
    }

    @Test
    public void conservaTiempoCalendarioPeroSolicitaRevisionSinCalendarioAplicable() {
        TrayectoriaPermanenciaDTO trayectoria = trayectoriaCompleta(5);
        trayectoria.setPeriodoPrimeraInscripcion(null);

        TrayectoriaPermanenciaDTO resultado = AsistenteInscripcionServiceImpl.completarIndicadoresTrayectoria(trayectoria);

        assertTrue(resultado.getDeterminable());
        assertNotNull(resultado.getPlazoRestanteCalendario());
        assertTrue(resultado.getMensajeRevision().contains("calendario académico"));
    }

    @Test
    public void admisionSinPrimeraInscripcionNoIniciaElCalculo() {
        TrayectoriaPermanenciaDTO resultado = AsistenteInscripcionServiceImpl.completarIndicadoresTrayectoria(null);

        assertFalse(resultado.getDeterminable());
        assertNull(resultado.getPlazoRestanteCalendario());
        assertTrue(resultado.getMensajeRevision().contains("Mesa de Servicio"));
    }

    @Test
    public void cuatroAniosNoAgotanElPlazoMaximoDeOcho() {
        TrayectoriaPermanenciaDTO trayectoria = trayectoriaCompleta(8);
        trayectoria.setFechaPrimeraInscripcion(fecha(2022, Calendar.JANUARY, 20));
        trayectoria.setFechaCorte(fecha(2026, Calendar.JANUARY, 20));

        TrayectoriaPermanenciaDTO resultado = AsistenteInscripcionServiceImpl.completarIndicadoresTrayectoria(trayectoria);

        assertFalse(resultado.getPlazoAgotado());
        assertTrue(resultado.getPlazoRestanteCalendario().contains("4 años"));
    }

    @Test
    public void plazoMaximoAgotadoNoGeneraProrrogaAutomatica() {
        TrayectoriaPermanenciaDTO trayectoria = trayectoriaCompleta(16);
        trayectoria.setFechaPrimeraInscripcion(fecha(2018, Calendar.JANUARY, 20));
        trayectoria.setFechaCorte(fecha(2026, Calendar.JANUARY, 20));

        TrayectoriaPermanenciaDTO resultado = AsistenteInscripcionServiceImpl.completarIndicadoresTrayectoria(trayectoria);

        assertTrue(resultado.getPlazoAgotado());
        assertEquals(resultado.getPlazoRestanteCalendario(), "Plazo máximo agotado");
    }

    @Test
    public void bloquesB1YB2PertenecenAlMismoSemestreAcademico() {
        TrayectoriaPermanenciaDTO trayectoria = trayectoriaCompleta(1);
        trayectoria.setPeriodoPrimeraInscripcion("2024-1-B1");
        trayectoria.setPeriodoCorte("2024-1-B2");

        TrayectoriaPermanenciaDTO resultado = AsistenteInscripcionServiceImpl.completarIndicadoresTrayectoria(trayectoria);

        assertEquals(resultado.getPeriodosAcademicosTranscurridos(), Integer.valueOf(1));
        assertEquals(resultado.getSemestresConcluidos(), Integer.valueOf(0));
        assertTrue(resultado.getSemestreEnCurso());
    }

    @Test
    public void bajaTemporalNoExtiendeElPlazoCalendario() {
        TrayectoriaPermanenciaDTO trayectoria = trayectoriaCompleta(6);
        trayectoria.setFechaPrimeraInscripcion(fecha(2018, Calendar.JANUARY, 20));
        trayectoria.setFechaCorte(fecha(2026, Calendar.JANUARY, 20));
        trayectoria.setPeriodosConInscripcion(Integer.valueOf(4));

        TrayectoriaPermanenciaDTO resultado = AsistenteInscripcionServiceImpl.completarIndicadoresTrayectoria(trayectoria);

        assertEquals(resultado.getPeriodosConInscripcion(), Integer.valueOf(4));
        assertTrue(resultado.getPlazoAgotado());
        assertEquals(resultado.getPlazoRestanteCalendario(), "Plazo máximo agotado");
    }

    private TrayectoriaPermanenciaDTO trayectoriaCompleta(int periodosTranscurridos) {
        TrayectoriaPermanenciaDTO trayectoria = new TrayectoriaPermanenciaDTO();
        trayectoria.setFechaPrimeraInscripcion(fecha(2024, Calendar.JANUARY, 20));
        trayectoria.setPeriodoPrimeraInscripcion("2024-1");
        trayectoria.setFechaCorte(fecha(2026, Calendar.OCTOBER, 21));
        trayectoria.setPeriodoCorte("2026-2");
        trayectoria.setPeriodosAcademicosTranscurridos(Integer.valueOf(periodosTranscurridos));
        trayectoria.setPeriodosConInscripcion(Integer.valueOf(3));
        return trayectoria;
    }

    private Date fecha(int anio, int mes, int dia) {
        Calendar calendario = Calendar.getInstance();
        calendario.clear();
        calendario.set(anio, mes, dia);
        return calendario.getTime();
    }
}
