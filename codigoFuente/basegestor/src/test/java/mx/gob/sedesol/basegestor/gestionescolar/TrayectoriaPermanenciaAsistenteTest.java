package mx.gob.sedesol.basegestor.service.impl.gestionescolar;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

import java.util.Calendar;
import java.util.Date;

import org.testng.annotations.Test;

import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.TrayectoriaPermanenciaDTO;

public class TrayectoriaPermanenciaAsistenteTest {

    @Test
    public void muestraPlazoRestanteSoloConIngresoYCalendarioVerificables() {
        TrayectoriaPermanenciaDTO trayectoria = trayectoriaCompleta(5);

        TrayectoriaPermanenciaDTO resultado = AsistenteInscripcionServiceImpl.completarIndicadoresTrayectoria(trayectoria);

        assertTrue(resultado.getDeterminable());
        assertEquals(resultado.getDuracionNominalSemestres(), Integer.valueOf(8));
        assertEquals(resultado.getPlazoMaximoSemestres(), Integer.valueOf(16));
        assertEquals(resultado.getPlazoRestanteSemestres(), Integer.valueOf(11));
        assertEquals(resultado.getPeriodosConInscripcion(), Integer.valueOf(3));
        assertNull(resultado.getMensajeRevision());
    }

    @Test
    public void noInfierePlazoCuandoFaltaPeriodoInicialValidado() {
        TrayectoriaPermanenciaDTO trayectoria = trayectoriaCompleta(5);
        trayectoria.setPeriodoPrimeraInscripcion(null);

        TrayectoriaPermanenciaDTO resultado = AsistenteInscripcionServiceImpl.completarIndicadoresTrayectoria(trayectoria);

        assertFalse(resultado.getDeterminable());
        assertNull(resultado.getPlazoRestanteSemestres());
        assertTrue(resultado.getMensajeRevision().contains("Mesa de Servicio"));
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
