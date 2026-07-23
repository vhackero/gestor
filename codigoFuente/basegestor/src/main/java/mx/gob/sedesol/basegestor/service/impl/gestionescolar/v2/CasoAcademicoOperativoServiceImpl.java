package mx.gob.sedesol.basegestor.service.impl.gestionescolar.v2;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.CasoAcademicoOperativoDTO;
import mx.gob.sedesol.basegestor.commons.utils.InscripcionException;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.v2.TblCasoAcademicoOperativoV2;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.v2.TblCasoDiagnosticoV2;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.v2.TblCasoDictamenV2;
import mx.gob.sedesol.basegestor.model.repositories.gestionescolar.v2.CatMotivoRestriccionV2Repo;
import mx.gob.sedesol.basegestor.model.repositories.gestionescolar.v2.CatTipoCasoAcademicoV2Repo;
import mx.gob.sedesol.basegestor.model.repositories.gestionescolar.v2.CatViabilidadTecnicaV2Repo;
import mx.gob.sedesol.basegestor.model.repositories.gestionescolar.v2.TblCasoAcademicoOperativoV2Repo;
import mx.gob.sedesol.basegestor.model.repositories.gestionescolar.v2.TblCasoBitacoraV2Repo;
import mx.gob.sedesol.basegestor.model.repositories.gestionescolar.v2.TblCasoDiagnosticoV2Repo;
import mx.gob.sedesol.basegestor.model.repositories.gestionescolar.v2.TblCasoDictamenV2Repo;
import mx.gob.sedesol.basegestor.service.gestionescolar.v2.CasoAcademicoOperativoService;

@Service("casoAcademicoOperativoService")
public class CasoAcademicoOperativoServiceImpl implements CasoAcademicoOperativoService {

    @Autowired
    private TblCasoAcademicoOperativoV2Repo casoRepo;

    @Autowired
    private TblCasoDiagnosticoV2Repo diagnosticoRepo;

    @Autowired
    private TblCasoDictamenV2Repo dictamenRepo;

    @Autowired
    private TblCasoBitacoraV2Repo bitacoraRepo;

    @Autowired
    private CatTipoCasoAcademicoV2Repo tipoCasoRepo;

    @Autowired
    private CatViabilidadTecnicaV2Repo viabilidadRepo;

    @Autowired
    private CatMotivoRestriccionV2Repo motivoRepo;

    @Override
    @Transactional
    public CasoAcademicoOperativoDTO crearCaso(CasoAcademicoOperativoDTO caso) throws InscripcionException {
        if (caso == null || caso.getIdPersona() == null) {
            throw new InscripcionException("El caso academico requiere idPersona.");
        }

        TblCasoAcademicoOperativoV2 entity = casoRepo.save(AsistenteCurricularV2Mapper.toCasoEntity(caso));

        if (caso.getDiagnostico() != null) {
            TblCasoDiagnosticoV2 diagnostico = diagnosticoRepo.findByIdCaso(entity.getId());
            if (diagnostico != null) {
                diagnosticoRepo.delete(diagnostico);
            }
            diagnosticoRepo.save(AsistenteCurricularV2Mapper.toDiagnosticoEntity(entity.getId(), caso.getDiagnostico()));
        }

        if (caso.getDictamen() != null) {
            TblCasoDictamenV2 dictamen = dictamenRepo.findByIdCaso(entity.getId());
            if (dictamen != null) {
                dictamenRepo.delete(dictamen);
            }
            dictamenRepo.save(AsistenteCurricularV2Mapper.toDictamenEntity(entity.getId(), caso.getDictamen()));
        }

        return obtenerCasoPorId(entity.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public CasoAcademicoOperativoDTO obtenerCasoPorId(Long idCaso) throws InscripcionException {
        TblCasoAcademicoOperativoV2 entity = casoRepo.findOne(idCaso);
        if (entity == null) {
            throw new InscripcionException("No existe el caso academico solicitado.");
        }
        return construirCaso(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public CasoAcademicoOperativoDTO obtenerCasoPorFolio(String folioExterno) throws InscripcionException {
        TblCasoAcademicoOperativoV2 entity = casoRepo.findByFolioExterno(folioExterno);
        if (entity == null) {
            throw new InscripcionException("No existe el caso academico para el folio indicado.");
        }
        return construirCaso(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CasoAcademicoOperativoDTO> obtenerCasosPorPersona(Long idPersona) throws InscripcionException {
        List<CasoAcademicoOperativoDTO> resultado = new ArrayList<CasoAcademicoOperativoDTO>();
        if (idPersona == null) {
            return resultado;
        }
        for (TblCasoAcademicoOperativoV2 entity : casoRepo.findByIdPersona(idPersona)) {
            resultado.add(construirCaso(entity));
        }
        return resultado;
    }

    @Override
    @Transactional(readOnly = true)
    public CasoAcademicoOperativoDTO obtenerCasoMasRecientePorPersona(Long idPersona) throws InscripcionException {
        if (idPersona == null) {
            return null;
        }
        TblCasoAcademicoOperativoV2 entity = casoRepo.findTopByIdPersonaOrderByIdDesc(idPersona);
        return entity != null ? construirCaso(entity) : null;
    }

    @Override
    @Transactional
    public CasoAcademicoOperativoDTO actualizarEstatus(Long idCaso, String estatusCaso, Long usuarioModifico)
            throws InscripcionException {
        TblCasoAcademicoOperativoV2 entity = casoRepo.findOne(idCaso);
        if (entity == null) {
            throw new InscripcionException("No existe el caso academico para actualizar.");
        }
        entity.setEstatusCaso(estatusCaso);
        entity.setUsuarioModifico(usuarioModifico);
        casoRepo.save(entity);
        return construirCaso(entity);
    }

    @Override
    @Transactional
    public CasoAcademicoOperativoDTO actualizarCaso(CasoAcademicoOperativoDTO caso) throws InscripcionException {
        if (caso == null || caso.getId() == null) {
            throw new InscripcionException("El caso academico requiere identificador para actualizar.");
        }
        TblCasoAcademicoOperativoV2 actual = casoRepo.findOne(caso.getId());
        if (actual == null) {
            throw new InscripcionException("No existe el caso academico para actualizacion integral.");
        }

        TblCasoAcademicoOperativoV2 actualizado = AsistenteCurricularV2Mapper.toCasoEntity(caso);
        actualizado.setFolioExterno(actual.getFolioExterno());
        if (actualizado.getUsuarioModifico() == null) {
            actualizado.setUsuarioModifico(actual.getUsuarioModifico());
        }
        casoRepo.save(actualizado);

        if (caso.getDiagnostico() != null) {
            TblCasoDiagnosticoV2 diagnostico = diagnosticoRepo.findByIdCaso(actual.getId());
            if (diagnostico != null) {
                diagnosticoRepo.delete(diagnostico);
            }
            diagnosticoRepo.save(AsistenteCurricularV2Mapper.toDiagnosticoEntity(actual.getId(), caso.getDiagnostico()));
        }

        if (caso.getDictamen() != null) {
            TblCasoDictamenV2 dictamen = dictamenRepo.findByIdCaso(actual.getId());
            if (dictamen != null) {
                dictamenRepo.delete(dictamen);
            }
            dictamenRepo.save(AsistenteCurricularV2Mapper.toDictamenEntity(actual.getId(), caso.getDictamen()));
        }

        return obtenerCasoPorId(actual.getId());
    }

    private CasoAcademicoOperativoDTO construirCaso(TblCasoAcademicoOperativoV2 entity) {
        CasoAcademicoOperativoDTO dto = AsistenteCurricularV2Mapper.toCasoDto(entity);
        dto.setTipoCaso(AsistenteCurricularV2Mapper.toTipoCasoDto(tipoCasoRepo.findOne(entity.getIdTipoCaso())));
        dto.setViabilidadTecnica(AsistenteCurricularV2Mapper.toViabilidadDto(viabilidadRepo.findOne(entity.getIdViabilidadTecnica())));
        dto.setMotivoRestriccion(AsistenteCurricularV2Mapper.toMotivoDto(motivoRepo.findOne(entity.getIdMotivoRestriccion())));
        dto.setDiagnostico(AsistenteCurricularV2Mapper.toDiagnosticoDto(diagnosticoRepo.findByIdCaso(entity.getId())));
        dto.setDictamen(AsistenteCurricularV2Mapper.toDictamenDto(dictamenRepo.findByIdCaso(entity.getId())));

        bitacoraRepo.findByIdCasoOrderByFechaAsc(entity.getId()).forEach(item -> dto.getBitacora()
                .add(AsistenteCurricularV2Mapper.toBitacoraDto(item)));
        return dto;
    }
}
