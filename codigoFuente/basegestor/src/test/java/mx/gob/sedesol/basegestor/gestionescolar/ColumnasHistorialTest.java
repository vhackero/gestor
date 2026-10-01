package mx.gob.sedesol.basegestor.gestionescolar;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import java.lang.reflect.Field;
import java.util.*;
import org.junit.Test;
import mx.gob.sedesol.basegestor.commons.utils.ColumnasHistorial;
import mx.gob.sedesol.basegestor.commons.dto.admin.ParametroSistemaDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestion.aprendizaje.EventoConstanciaDTO;
import mx.gob.sedesol.basegestor.model.repositories.admin.ParametroSistemaRepo;
import mx.gob.sedesol.basegestor.service.impl.admin.ParametroSistemaServiceImpl;

public class ColumnasHistorialTest {
    @Test public void seleccionComunOrdenadaSinDuplicados() {
        assertEquals(15, ColumnasHistorial.leer(null).size());
        assertEquals(Arrays.asList(ColumnasHistorial.ASIGNATURA, ColumnasHistorial.CALIFICACION),
                ColumnasHistorial.leer("CALIFICACION, ASIGNATURA,ASIGNATURA"));
    }

    @Test public void rechazaConfiguracionesVaciasODesconocidasAntesDePersistir() throws Exception {
        ParametroSistemaServiceImpl service = new ParametroSistemaServiceImpl();
        ParametroSistemaRepo repo = mock(ParametroSistemaRepo.class);
        Field f = service.getClass().getDeclaredField("parametroSistemaRepo");
        f.setAccessible(true);
        f.set(service, repo);
        for (String valor : Arrays.asList(null, "", " ", "ASIGNATURA,", "OTRA", "ASIGNATURA,OTRA")) {
            ParametroSistemaDTO dto = new ParametroSistemaDTO(1L);
            dto.setClave(ColumnasHistorial.PARAMETRO);
            dto.setFechaActualizacion(new Date());
            dto.setValor(valor);
            assertFalse(service.guardar(dto).esCorrecto());
            assertFalse(service.actualizar(dto).esCorrecto());
        }
        verifyZeroInteractions(repo);
    }

    @Test public void distingueAusenciaCeroNotaYNoPresentada() {
        EventoConstanciaDTO fila = new EventoConstanciaDTO();
        assertEquals("", fila.getCalificacionFinalTexto());
        fila.setCalificacionFinal(0.0);
        assertEquals("0.0", fila.getCalificacionFinalTexto());
        fila.setCalificacionFinal(92.5);
        assertEquals("92.5", fila.getCalificacionFinalTexto());
        fila.setCalificacionFinal(666.0);
        assertEquals("NP", fila.getCalificacionFinalTexto());
    }
}
