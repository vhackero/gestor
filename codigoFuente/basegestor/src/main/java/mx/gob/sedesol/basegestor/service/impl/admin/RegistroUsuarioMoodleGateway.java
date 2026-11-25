package mx.gob.sedesol.basegestor.service.impl.admin;
import org.springframework.stereotype.Component;
import mx.gob.sedesol.basegestor.commons.dto.admin.ParametroWSMoodleDTO;
import mx.gob.sedesol.basegestor.ws.moodle.clientes.service.client.UsuarioWSClient;
@Component
public class RegistroUsuarioMoodleGateway {
    public UsuarioWSClient cliente(ParametroWSMoodleDTO plataforma){return new UsuarioWSClient(plataforma);}
}
