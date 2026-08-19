package mx.gob.sedesol.gestorweb.ws.sigie;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import mx.gob.sedesol.basegestor.commons.dto.integracion.EstudianteSigieConsultaDTO;
import mx.gob.sedesol.basegestor.service.integracion.EstudianteSigieService;

@RestController
@RequestMapping("/v1/estudiantes")
public class EstudianteSigieController {
    @Autowired private MatriculaValidator validator;
    @Autowired private EstudianteSigieService service;
    @Autowired private EstudianteSigieMapper mapper;

    @RequestMapping(value = "/{matricula}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EstudianteSigieResponse> consultar(@PathVariable("matricula") String matricula,
            HttpServletRequest request) {
        if (!validator.esValida(matricula)) {
            throw new SigieApiException(HttpStatus.BAD_REQUEST, "MATRICULA_INVALIDA",
                    "La matrícula proporcionada no tiene un formato válido.");
        }
        String matriculaNormalizada = validator.normalizar(matricula);
        Optional<EstudianteSigieConsultaDTO> estudiante = service.consultarPorMatricula(matriculaNormalizada);
        if (!estudiante.isPresent()) {
            throw new SigieApiException(HttpStatus.NOT_FOUND, "MATRICULA_NO_ENCONTRADA",
                    "No existe registro para la matrícula proporcionada.");
        }
        String folio = request.getAttribute(SigieRequestContext.FOLIO).toString();
        String fecha = ZonedDateTime.now().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        return ResponseEntity.ok(mapper.mapear(estudiante.get(), folio, fecha));
    }
}
