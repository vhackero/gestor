package mx.gob.sedesol.gestorweb.ws.sigie;

import static org.junit.Assert.assertTrue;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.Test;

public class OpenApiContractTest {
    @Test public void contratoContieneRutaCamposYCodigosEstables() throws Exception {
        String yaml = new String(Files.readAllBytes(Paths.get("docs/openapi.yaml")), StandardCharsets.UTF_8);
        String[] required = { "openapi: 3.1.0", "/SIGIE/v1/estudiantes/{matricula}", "matricula:",
                "primer_apellido:", "creditos_totales:", "creditos_cubiertos:", "situacion_escolar:",
                "folio_consulta:", "'200':", "'400':", "'401':", "'403':", "'404':", "'409':",
                "'429':", "'500':", "'503':" };
        for (String token : required) { assertTrue("Falta en OpenAPI: " + token, yaml.contains(token)); }
    }
}
