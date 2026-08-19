package mx.gob.sedesol.gestorweb.ws.sigie;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import java.math.BigDecimal;
import org.junit.Test;
import mx.gob.sedesol.basegestor.commons.dto.integracion.EstudianteSigieConsultaDTO;

public class EstudianteSigieMapperTest {
    private final EstudianteSigieMapper mapper = new EstudianteSigieMapper();

    @Test public void calculaPorcentajeYPermiteOpcionalesNulos() {
        EstudianteSigieConsultaDTO source = completo();
        source.setSegundoApellido("");
        source.setCorreoInstitucional(null);
        EstudianteSigieResponse response = mapper.mapear(source, "folio", "2026-08-17T12:00:00-06:00");
        assertEquals(new BigDecimal("76.56"), response.getAvance().getPorcentajeCubierto());
        assertNull(response.getSegundoApellido());
        assertNull(response.getCorreoInstitucional());
        assertEquals("PLAN-2024", response.getPrograma().getClave());
        assertEquals("2024", response.getPrograma().getPlanEstudios());
    }

    @Test public void priorizaBajaDefinitivaSobreEgreso() {
        EstudianteSigieConsultaDTO source = completo();
        source.setCreditosCubiertos(new BigDecimal("320"));
        source.setPrioridadBaja(2);
        assertEquals("BAJ_DEF", mapper.mapear(source, "folio", "fecha").getSituacionEscolar().getClave());
    }

    @Test public void clasificaIrregularCuandoConservaAsignaturaReprobada() {
        EstudianteSigieConsultaDTO source = completo();
        source.setIrregular(Boolean.TRUE);
        assertEquals("IRR", mapper.mapear(source, "folio", "fecha").getSituacionEscolar().getClave());
    }

    @Test public void priorizaBajaTemporalSobreIrregularidad() {
        EstudianteSigieConsultaDTO source = completo();
        source.setIrregular(Boolean.TRUE);
        source.setPrioridadBaja(1);
        assertEquals("BAJ_TMP", mapper.mapear(source, "folio", "fecha").getSituacionEscolar().getClave());
    }

    @Test(expected = SigieApiException.class)
    public void rechazaDatosObligatoriosIncompletos() {
        EstudianteSigieConsultaDTO source = completo();
        source.setCurp(null);
        mapper.mapear(source, "folio", "fecha");
    }

    private EstudianteSigieConsultaDTO completo() {
        EstudianteSigieConsultaDTO dto = new EstudianteSigieConsultaDTO();
        dto.setMatricula("ES241100064"); dto.setNombre("Maria"); dto.setPrimerApellido("Lopez");
        dto.setSegundoApellido("Hernandez"); dto.setCurp("LOHM950101MDFPRR03");
        dto.setClavePrograma("PLAN-2024"); dto.setNombrePrograma("Informatica"); dto.setNivel("Licenciatura");
        dto.setIdentificadorPlan("PLAN-2024"); dto.setCreditosTotales(new BigDecimal("320"));
        dto.setCreditosCubiertos(new BigDecimal("245")); dto.setActivo(Boolean.TRUE); dto.setPrioridadBaja(0);
        dto.setIrregular(Boolean.FALSE);
        return dto;
    }
}
