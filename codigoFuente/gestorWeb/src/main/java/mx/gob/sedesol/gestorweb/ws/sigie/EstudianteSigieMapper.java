package mx.gob.sedesol.gestorweb.ws.sigie;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import mx.gob.sedesol.basegestor.commons.dto.integracion.EstudianteSigieConsultaDTO;

@Component
public class EstudianteSigieMapper {
    private static final Pattern ANIO = Pattern.compile("(?:19|20)[0-9]{2}");

    public EstudianteSigieResponse mapear(EstudianteSigieConsultaDTO source, String folio, String fecha) {
        validarRequeridos(source);
        EstudianteSigieResponse response = new EstudianteSigieResponse();
        response.setMatricula(source.getMatricula());
        response.setNombre(source.getNombre());
        response.setPrimerApellido(source.getPrimerApellido());
        response.setSegundoApellido(vacioANull(source.getSegundoApellido()));
        response.setCurp(source.getCurp());

        EstudianteSigieResponse.Programa programa = new EstudianteSigieResponse.Programa();
        programa.setClave(source.getClavePrograma());
        programa.setNombre(vacioANull(source.getNombrePrograma()));
        programa.setNivel(vacioANull(source.getNivel()));
        programa.setPlanEstudios(extraerAnio(source.getIdentificadorPlan()));
        response.setPrograma(programa);

        EstudianteSigieResponse.Avance avance = new EstudianteSigieResponse.Avance();
        avance.setCreditosTotales(source.getCreditosTotales());
        avance.setCreditosCubiertos(source.getCreditosCubiertos());
        if (source.getCreditosTotales().compareTo(BigDecimal.ZERO) > 0) {
            avance.setPorcentajeCubierto(source.getCreditosCubiertos().multiply(new BigDecimal("100"))
                    .divide(source.getCreditosTotales(), 2, RoundingMode.HALF_UP));
        }
        response.setAvance(avance);
        response.setSituacionEscolar(resolverSituacion(source));
        response.setEstatus(Boolean.TRUE.equals(source.getActivo())
                ? new EstudianteSigieResponse.Catalogo("ACT", "Activo")
                : new EstudianteSigieResponse.Catalogo("INA", "Inactivo"));
        response.setCorreoInstitucional(vacioANull(source.getCorreoInstitucional()));
        response.setFechaHoraConsulta(fecha);
        response.setFolioConsulta(folio);
        return response;
    }

    private EstudianteSigieResponse.Catalogo resolverSituacion(EstudianteSigieConsultaDTO source) {
        if (Integer.valueOf(2).equals(source.getPrioridadBaja())) {
            return new EstudianteSigieResponse.Catalogo("BAJ_DEF", "Baja definitiva");
        }
        if (Integer.valueOf(1).equals(source.getPrioridadBaja())) {
            return new EstudianteSigieResponse.Catalogo("BAJ_TMP", "Baja temporal");
        }
        if (source.getCreditosCubiertos().compareTo(source.getCreditosTotales()) >= 0) {
            return new EstudianteSigieResponse.Catalogo("EGR", "Egresado");
        }
        if (Boolean.TRUE.equals(source.getIrregular())) {
            return new EstudianteSigieResponse.Catalogo("IRR", "Irregular");
        }
        return new EstudianteSigieResponse.Catalogo("REG", "Regular");
    }

    private void validarRequeridos(EstudianteSigieConsultaDTO value) {
        if (vacio(value.getMatricula()) || vacio(value.getNombre()) || vacio(value.getPrimerApellido())
                || vacio(value.getCurp()) || vacio(value.getClavePrograma())
                || value.getCreditosTotales() == null || value.getCreditosCubiertos() == null
                || value.getActivo() == null) {
            throw new SigieApiException(HttpStatus.CONFLICT, "DATOS_INCOMPLETOS",
                    "La información académica requerida del estudiante está incompleta.");
        }
    }

    private String extraerAnio(String identificador) {
        if (identificador == null) { return null; }
        Matcher matcher = ANIO.matcher(identificador);
        return matcher.find() ? matcher.group() : null;
    }
    private boolean vacio(String value) { return value == null || value.trim().isEmpty(); }
    private String vacioANull(String value) { return vacio(value) ? null : value; }
}
