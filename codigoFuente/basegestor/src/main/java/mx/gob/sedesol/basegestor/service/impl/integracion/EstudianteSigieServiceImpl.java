package mx.gob.sedesol.basegestor.service.impl.integracion;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import mx.gob.sedesol.basegestor.commons.dto.integracion.EstudianteSigieConsultaDTO;
import mx.gob.sedesol.basegestor.model.repositories.integracion.EstudianteSigieRepository;
import mx.gob.sedesol.basegestor.service.integracion.EstudianteSigieService;

@Service
@Transactional(readOnly = true)
public class EstudianteSigieServiceImpl implements EstudianteSigieService {
    @Autowired
    private EstudianteSigieRepository repository;

    @Override
    public Optional<EstudianteSigieConsultaDTO> consultarPorMatricula(String matricula) {
        return repository.consultarPorMatricula(matricula);
    }
}
