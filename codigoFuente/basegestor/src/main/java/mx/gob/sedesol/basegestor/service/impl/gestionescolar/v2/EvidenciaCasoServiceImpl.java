package mx.gob.sedesol.basegestor.service.impl.gestionescolar.v2;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EvidenciaCasoDTO;
import mx.gob.sedesol.basegestor.commons.utils.InscripcionException;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.v2.TblCasoEvidenciaV2;
import mx.gob.sedesol.basegestor.model.repositories.gestionescolar.v2.TblCasoEvidenciaV2Repo;
import mx.gob.sedesol.basegestor.service.gestionescolar.v2.EvidenciaCasoService;

@Service("evidenciaCasoService")
public class EvidenciaCasoServiceImpl implements EvidenciaCasoService {

    @Autowired
    private TblCasoEvidenciaV2Repo evidenciaRepo;

    @Override
    @Transactional
    public EvidenciaCasoDTO registrarEvidencia(Long idCaso, EvidenciaCasoDTO evidencia) throws InscripcionException {
        if (idCaso == null || evidencia == null) {
            throw new InscripcionException("La evidencia requiere idCaso y contenido.");
        }
        TblCasoEvidenciaV2 guardada = evidenciaRepo.save(AsistenteCurricularV2Mapper.toEvidenciaEntity(idCaso, evidencia));
        return AsistenteCurricularV2Mapper.toEvidenciaDto(guardada);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EvidenciaCasoDTO> obtenerEvidencias(Long idCaso) throws InscripcionException {
        List<EvidenciaCasoDTO> resultado = new ArrayList<EvidenciaCasoDTO>();
        evidenciaRepo.findByIdCaso(idCaso)
                .forEach(item -> resultado.add(AsistenteCurricularV2Mapper.toEvidenciaDto(item)));
        return resultado;
    }

    @Override
    @Transactional
    public EvidenciaCasoDTO actualizarEvidencia(Long idCaso, EvidenciaCasoDTO evidencia) throws InscripcionException {
        if (idCaso == null || evidencia == null || evidencia.getId() == null) {
            throw new InscripcionException("La evidencia requiere idCaso e idEvidencia para actualizarse.");
        }
        TblCasoEvidenciaV2 existente = evidenciaRepo.findByIdAndIdCaso(evidencia.getId(), idCaso);
        if (existente == null) {
            throw new InscripcionException("No existe la evidencia indicada para el caso.");
        }
        existente.setTipoEvidencia(evidencia.getTipoEvidencia());
        existente.setDescripcion(evidencia.getDescripcion());
        existente.setRuta(evidencia.getRuta());
        existente.setUsuario(evidencia.getUsuario());
        return AsistenteCurricularV2Mapper.toEvidenciaDto(evidenciaRepo.save(existente));
    }

    @Override
    @Transactional
    public void eliminarEvidencia(Long idCaso, Long idEvidencia) throws InscripcionException {
        if (idCaso == null || idEvidencia == null) {
            throw new InscripcionException("La eliminación de evidencia requiere idCaso e idEvidencia.");
        }
        TblCasoEvidenciaV2 existente = evidenciaRepo.findByIdAndIdCaso(idEvidencia, idCaso);
        if (existente == null) {
            throw new InscripcionException("No existe la evidencia indicada para el caso.");
        }
        evidenciaRepo.delete(existente);
    }
}
