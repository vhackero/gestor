package mx.gob.sedesol.basegestor.service.impl.gestionescolar;

import static org.testng.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;

import org.testng.annotations.Test;

import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.IntentosAsignaturasDTO;

public class AsistenteInscripcionPrioridadesTest {

    @Test
    public void cuentaIntentosPorIdentidadDeUdSinDuplicarPersonasNiUnidades() {
        IntentosAsignaturasDTO primera = intento(100L, 2L);
        IntentosAsignaturasDTO segunda = intento(200L, 1L);
        IntentosAsignaturasDTO actualizacionMismaUd = intento(100L, 2L);

        Map<Long, Long> resultado = AsistenteInscripcionServiceImpl
                .mapearIntentosReprobadosPorUd(Arrays.asList(primera, segunda, actualizacionMismaUd));

        assertEquals(resultado.size(), 2);
        assertEquals(resultado.get(100L), Long.valueOf(2L));
        assertEquals(resultado.get(200L), Long.valueOf(1L));
    }

    @Test
    public void noRelacionaRecursamientoSoloPorNombreCoincidente() {
        assertEquals(AsistenteInscripcionServiceImpl.coincideUnidadReinscrita(Long.valueOf(200L), "UD-200",
                Collections.singleton(Long.valueOf(100L)), new HashSet<String>()), false);
        assertEquals(AsistenteInscripcionServiceImpl.coincideUnidadReinscrita(null, "UD-200",
                Collections.<Long>emptySet(), Collections.<String>emptySet()), false);
    }

    @Test
    public void relacionaRecursamientoPorIdOClaveCurricularVerificable() {
        assertEquals(AsistenteInscripcionServiceImpl.coincideUnidadReinscrita(Long.valueOf(100L), "OTRA",
                Collections.singleton(Long.valueOf(100L)), Collections.<String>emptySet()), true);
        assertEquals(AsistenteInscripcionServiceImpl.coincideUnidadReinscrita(Long.valueOf(200L), "UD-100",
                Collections.<Long>emptySet(), Collections.singleton("UD-100")), true);
    }

    private IntentosAsignaturasDTO intento(Long idPrograma, Long cantidad) {
        IntentosAsignaturasDTO dto = new IntentosAsignaturasDTO();
        dto.setIdPrograma(idPrograma);
        dto.setIntentosReprobados(cantidad);
        return dto;
    }
}
