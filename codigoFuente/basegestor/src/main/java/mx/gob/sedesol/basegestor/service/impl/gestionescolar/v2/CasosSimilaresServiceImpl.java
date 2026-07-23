package mx.gob.sedesol.basegestor.service.impl.gestionescolar.v2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.CasoSimilarDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.CasoAcademicoOperativoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.ContextoAsistenteCurricularV2DTO;
import mx.gob.sedesol.basegestor.commons.utils.InscripcionException;
import mx.gob.sedesol.basegestor.model.repositories.gestionescolar.v2.TblCasoAcademicoOperativoV2Repo;
import mx.gob.sedesol.basegestor.service.gestionescolar.v2.CasosSimilaresService;

@Service("casosSimilaresService")
public class CasosSimilaresServiceImpl implements CasosSimilaresService {

    @Autowired
    private TblCasoAcademicoOperativoV2Repo casoRepo;

    @Override
    @Transactional(readOnly = true)
    public List<CasoSimilarDTO> buscarCasosSimilares(ContextoAsistenteCurricularV2DTO contexto)
            throws InscripcionException {
        List<CasoSimilarDTO> resultado = new ArrayList<CasoSimilarDTO>();
        if (contexto == null || contexto.getCasoActual() == null || contexto.getCasoActual().getId() == null) {
            return resultado;
        }
        CasoAcademicoOperativoDTO casoActual = contexto.getCasoActual();
        for (mx.gob.sedesol.basegestor.model.entities.gestionescolar.v2.TblCasoAcademicoOperativoV2 entity : casoRepo.findAll()) {
            if (entity == null || entity.getId() == null || entity.getId().equals(casoActual.getId())) {
                continue;
            }
            CasoSimilarDTO similar = evaluarSimilitud(casoActual, entity);
            if (similar != null) {
                resultado.add(similar);
            }
        }
        Collections.sort(resultado, Comparator.comparing(CasoSimilarDTO::getPuntajeSimilitud).reversed());
        if (resultado.size() > 5) {
            return new ArrayList<CasoSimilarDTO>(resultado.subList(0, 5));
        }
        return resultado;
    }

    private CasoSimilarDTO evaluarSimilitud(CasoAcademicoOperativoDTO casoActual,
            mx.gob.sedesol.basegestor.model.entities.gestionescolar.v2.TblCasoAcademicoOperativoV2 candidato) {
        double puntaje = 0D;
        List<String> razones = new ArrayList<String>();

        if (casoActual.getTipoCaso() != null && casoActual.getTipoCaso().getId() != null
                && casoActual.getTipoCaso().getId().equals(candidato.getIdTipoCaso())) {
            puntaje += 0.45D;
            razones.add("mismo tipo de caso");
        }
        if (casoActual.getMotivoRestriccion() != null && casoActual.getMotivoRestriccion().getId() != null
                && casoActual.getMotivoRestriccion().getId().equals(candidato.getIdMotivoRestriccion())) {
            puntaje += 0.25D;
            razones.add("mismo motivo");
        }
        if (casoActual.getIdPlan() != null && casoActual.getIdPlan().equals(candidato.getIdPlan())) {
            puntaje += 0.15D;
            razones.add("mismo plan");
        }
        if (casoActual.getEstatusCaso() != null && casoActual.getEstatusCaso().equals(candidato.getEstatusCaso())) {
            puntaje += 0.10D;
            razones.add("mismo estatus");
        }
        if (casoActual.getPerfilOrigen() != null && casoActual.getPerfilOrigen().equals(candidato.getPerfilOrigen())) {
            puntaje += 0.05D;
            razones.add("mismo perfil");
        }

        if (puntaje < 0.40D) {
            return null;
        }

        CasoSimilarDTO dto = new CasoSimilarDTO();
        dto.setId(candidato.getId());
        dto.setIdCasoRelacion(candidato.getId());
        dto.setFolioCasoRelacion(candidato.getFolioExterno());
        dto.setPuntajeSimilitud(Double.valueOf(Math.round(puntaje * 100D) / 100D));
        dto.setMotivoRelacion(unirRazones(razones));
        return dto;
    }

    private String unirRazones(List<String> razones) {
        if (razones.isEmpty()) {
            return "similitud general";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < razones.size(); i++) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(razones.get(i));
        }
        return sb.toString();
    }
}
