package mx.gob.sedesol.basegestor.service.impl.gestionescolar.v2;

import java.util.UUID;

import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.AccionOperativaDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.BitacoraCasoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.CampoExpedienteCasoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.CasoAcademicoOperativoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.CasoSimilarDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.CasoUdRelacionadaDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.ClasificacionCasoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.ContextoAsistenteCurricularV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.CriterioOperativoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.DiagnosticoAcademicoOperativoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.DictamenCasoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EvidenciaCasoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.ExpedienteMinimoCasoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.MensajeInstitucionalContextualDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.MotivoRestriccionDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.PatronConocimientoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.TipoCasoAcademicoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.ViabilidadTecnicaDTO;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.v2.CatAccionOperativaV2;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.v2.CatCriterioOperativoV2;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.v2.CatMensajeInstitucionalContextualV2;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.v2.CatMotivoRestriccionV2;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.v2.CatTipoCasoAcademicoV2;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.v2.CatViabilidadTecnicaV2;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.v2.RelPatronCasoAcademicoV2;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.v2.TblCasoAcademicoOperativoV2;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.v2.TblCasoBitacoraV2;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.v2.TblCasoDiagnosticoV2;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.v2.TblCasoDictamenV2;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.v2.TblCasoEvidenciaV2;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.v2.TblCasoSimilarV2;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.v2.TblCasoUdRelacionadaV2;

final class AsistenteCurricularV2Mapper {

    private AsistenteCurricularV2Mapper() {
    }

    static TipoCasoAcademicoDTO toTipoCasoDto(CatTipoCasoAcademicoV2 entity) {
        if (entity == null) {
            return null;
        }
        TipoCasoAcademicoDTO dto = new TipoCasoAcademicoDTO();
        dto.setId(entity.getId());
        dto.setClave(entity.getClave());
        dto.setNombre(entity.getNombre());
        dto.setDescripcion(entity.getDescripcion());
        dto.setActivo(entity.getActivo());
        return dto;
    }

    static MotivoRestriccionDTO toMotivoDto(CatMotivoRestriccionV2 entity) {
        if (entity == null) {
            return null;
        }
        MotivoRestriccionDTO dto = new MotivoRestriccionDTO();
        dto.setId(entity.getId());
        dto.setClave(entity.getClave());
        dto.setNombre(entity.getNombre());
        dto.setDescripcion(entity.getDescripcion());
        dto.setActivo(entity.getActivo());
        return dto;
    }

    static CriterioOperativoDTO toCriterioDto(CatCriterioOperativoV2 entity) {
        if (entity == null) {
            return null;
        }
        CriterioOperativoDTO dto = new CriterioOperativoDTO();
        dto.setId(entity.getId());
        dto.setClave(entity.getClave());
        dto.setNombre(entity.getNombre());
        dto.setDescripcion(entity.getDescripcion());
        dto.setPrioridad(entity.getPrioridad());
        dto.setActivo(entity.getActivo());
        return dto;
    }

    static AccionOperativaDTO toAccionDto(CatAccionOperativaV2 entity) {
        if (entity == null) {
            return null;
        }
        AccionOperativaDTO dto = new AccionOperativaDTO();
        dto.setId(entity.getId());
        dto.setClave(entity.getClave());
        dto.setNombre(entity.getNombre());
        dto.setDescripcion(entity.getDescripcion());
        dto.setActivo(entity.getActivo());
        return dto;
    }

    static ViabilidadTecnicaDTO toViabilidadDto(CatViabilidadTecnicaV2 entity) {
        if (entity == null) {
            return null;
        }
        ViabilidadTecnicaDTO dto = new ViabilidadTecnicaDTO();
        dto.setId(entity.getId());
        dto.setClave(entity.getClave());
        dto.setNombre(entity.getNombre());
        dto.setDescripcion(entity.getDescripcion());
        dto.setActivo(entity.getActivo());
        return dto;
    }

    static MensajeInstitucionalContextualDTO toMensajeDto(CatMensajeInstitucionalContextualV2 entity) {
        if (entity == null) {
            return null;
        }
        MensajeInstitucionalContextualDTO dto = new MensajeInstitucionalContextualDTO();
        dto.setId(entity.getId());
        dto.setClave(entity.getClave());
        dto.setPerfil(entity.getPerfil());
        dto.setPeriodoOperativo(entity.getPeriodoOperativo());
        dto.setTitulo(entity.getTitulo());
        dto.setMensaje(entity.getMensaje());
        dto.setTipo(entity.getTipo());
        dto.setActivo(entity.getActivo());
        return dto;
    }

    static PatronConocimientoDTO toPatronDto(RelPatronCasoAcademicoV2 entity) {
        if (entity == null) {
            return null;
        }
        PatronConocimientoDTO dto = new PatronConocimientoDTO();
        dto.setId(entity.getId());
        dto.setNombrePatron(entity.getNombrePatron());
        dto.setDescripcion(entity.getDescripcion());
        dto.setConfianzaBase(entity.getConfianzaBase());
        dto.setActivo(entity.getActivo());
        return dto;
    }

    static TblCasoAcademicoOperativoV2 toCasoEntity(CasoAcademicoOperativoDTO dto) {
        TblCasoAcademicoOperativoV2 entity = new TblCasoAcademicoOperativoV2();
        entity.setId(dto.getId());
        entity.setFolioExterno(dto.getFolioExterno() != null ? dto.getFolioExterno() : generarFolio());
        entity.setIdPersona(dto.getIdPersona());
        entity.setIdPlan(dto.getIdPlan());
        entity.setIdPeriodo(dto.getIdPeriodo());
        entity.setPerfilOrigen(dto.getPerfilOrigen() != null ? dto.getPerfilOrigen() : "SISTEMA");
        entity.setOrigenCaso(dto.getOrigenCaso() != null ? dto.getOrigenCaso() : "ASISTENTE");
        entity.setIdTipoCaso(dto.getTipoCaso() != null ? dto.getTipoCaso().getId() : null);
        entity.setIdViabilidadTecnica(dto.getViabilidadTecnica() != null ? dto.getViabilidadTecnica().getId() : null);
        entity.setIdMotivoRestriccion(dto.getMotivoRestriccion() != null ? dto.getMotivoRestriccion().getId() : null);
        entity.setDescripcionSolicitud(dto.getDescripcionSolicitud());
        entity.setConfianzaClasificacion(dto.getConfianzaClasificacion() != null ? dto.getConfianzaClasificacion() : 0D);
        entity.setRequiereIntervencionHumana(dto.getRequiereIntervencionHumana() != null ? dto.getRequiereIntervencionHumana() : Boolean.FALSE);
        entity.setExpedienteCompleto(dto.getExpedienteCompleto() != null ? dto.getExpedienteCompleto() : Boolean.FALSE);
        entity.setEstatusCaso(dto.getEstatusCaso() != null ? dto.getEstatusCaso() : "NUEVO");
        entity.setUsuarioModifico(dto.getUsuarioModifico());
        return entity;
    }

    static CasoAcademicoOperativoDTO toCasoDto(TblCasoAcademicoOperativoV2 entity) {
        if (entity == null) {
            return null;
        }
        CasoAcademicoOperativoDTO dto = new CasoAcademicoOperativoDTO();
        dto.setId(entity.getId());
        dto.setFolioExterno(entity.getFolioExterno());
        dto.setIdPersona(entity.getIdPersona());
        dto.setIdPlan(entity.getIdPlan());
        dto.setIdPeriodo(entity.getIdPeriodo());
        dto.setPerfilOrigen(entity.getPerfilOrigen());
        dto.setOrigenCaso(entity.getOrigenCaso());
        dto.setConfianzaClasificacion(entity.getConfianzaClasificacion());
        dto.setRequiereIntervencionHumana(entity.getRequiereIntervencionHumana());
        dto.setExpedienteCompleto(entity.getExpedienteCompleto());
        dto.setEstatusCaso(entity.getEstatusCaso());
        dto.setFechaRegistro(entity.getFechaRegistro());
        dto.setFechaActualizacion(entity.getFechaActualizacion());
        dto.setUsuarioModifico(entity.getUsuarioModifico());
        return dto;
    }

    static TblCasoDiagnosticoV2 toDiagnosticoEntity(Long idCaso, DiagnosticoAcademicoOperativoDTO dto) {
        TblCasoDiagnosticoV2 entity = new TblCasoDiagnosticoV2();
        entity.setId(dto.getId());
        entity.setIdCaso(idCaso);
        entity.setSituacionAcademica(truncar(dto.getSituacionAcademica(), 50));
        entity.setRiesgoActual(truncar(dto.getRiesgoActual(), 50));
        entity.setRiesgoSiguientePeriodo(truncar(dto.getRiesgoSiguientePeriodo(), 50));
        entity.setCierreAnual(truncar(dto.getCierreAnual(), 50));
        entity.setCargaViable(dto.getCargaViable());
        entity.setSeriacionActiva(dto.getSeriacionActiva());
        entity.setOfertaVigente(dto.getOfertaVigente());
        entity.setDictamenPreliminar(truncar(dto.getDictamenPreliminar(), 100));
        entity.setResumenMotor(dto.getResumenMotor());
        return entity;
    }

    static DiagnosticoAcademicoOperativoDTO toDiagnosticoDto(TblCasoDiagnosticoV2 entity) {
        if (entity == null) {
            return null;
        }
        DiagnosticoAcademicoOperativoDTO dto = new DiagnosticoAcademicoOperativoDTO();
        dto.setId(entity.getId());
        dto.setSituacionAcademica(entity.getSituacionAcademica());
        dto.setRiesgoActual(entity.getRiesgoActual());
        dto.setRiesgoSiguientePeriodo(entity.getRiesgoSiguientePeriodo());
        dto.setCierreAnual(entity.getCierreAnual());
        dto.setCargaViable(entity.getCargaViable());
        dto.setSeriacionActiva(entity.getSeriacionActiva());
        dto.setOfertaVigente(entity.getOfertaVigente());
        dto.setDictamenPreliminar(entity.getDictamenPreliminar());
        dto.setResumenMotor(entity.getResumenMotor());
        return dto;
    }

    static TblCasoDictamenV2 toDictamenEntity(Long idCaso, DictamenCasoDTO dto) {
        TblCasoDictamenV2 entity = new TblCasoDictamenV2();
        entity.setId(dto.getId());
        entity.setIdCaso(idCaso);
        entity.setIdAccionOperativa(dto.getAccionOperativa() != null ? dto.getAccionOperativa().getId() : null);
        entity.setIdViabilidadTecnica(dto.getViabilidadTecnica() != null ? dto.getViabilidadTecnica().getId() : null);
        entity.setDictamen(dto.getDictamen());
        entity.setMensajeEstudiante(dto.getMensajeEstudiante());
        entity.setMensajeGestor(dto.getMensajeGestor());
        entity.setRequiereEscalamiento(dto.getRequiereEscalamiento());
        entity.setFolioEscalamiento(dto.getFolioEscalamiento());
        entity.setUsuarioDictamino(dto.getUsuarioDictamino());
        return entity;
    }

    static DictamenCasoDTO toDictamenDto(TblCasoDictamenV2 entity) {
        if (entity == null) {
            return null;
        }
        DictamenCasoDTO dto = new DictamenCasoDTO();
        dto.setId(entity.getId());
        dto.setDictamen(entity.getDictamen());
        dto.setMensajeEstudiante(entity.getMensajeEstudiante());
        dto.setMensajeGestor(entity.getMensajeGestor());
        dto.setRequiereEscalamiento(entity.getRequiereEscalamiento());
        dto.setFolioEscalamiento(entity.getFolioEscalamiento());
        dto.setUsuarioDictamino(entity.getUsuarioDictamino());
        dto.setFechaDictamen(entity.getFechaDictamen());
        return dto;
    }

    static TblCasoUdRelacionadaV2 toUdEntity(Long idCaso, CasoUdRelacionadaDTO dto) {
        TblCasoUdRelacionadaV2 entity = new TblCasoUdRelacionadaV2();
        entity.setId(dto.getId());
        entity.setIdCaso(idCaso);
        entity.setIdPrograma(dto.getIdPrograma());
        entity.setClaveUd(dto.getClaveUd());
        entity.setNombreUd(dto.getNombreUd());
        entity.setTipoUd(dto.getTipoUd());
        entity.setBloque(dto.getBloque());
        entity.setSemestre(dto.getSemestre());
        entity.setEstatusDetectado(dto.getEstatusDetectado());
        entity.setEsCritica(dto.getCritica());
        return entity;
    }

    static CasoUdRelacionadaDTO toUdDto(TblCasoUdRelacionadaV2 entity) {
        if (entity == null) {
            return null;
        }
        CasoUdRelacionadaDTO dto = new CasoUdRelacionadaDTO();
        dto.setId(entity.getId());
        dto.setIdPrograma(entity.getIdPrograma());
        dto.setClaveUd(entity.getClaveUd());
        dto.setNombreUd(entity.getNombreUd());
        dto.setTipoUd(entity.getTipoUd());
        dto.setBloque(entity.getBloque());
        dto.setSemestre(entity.getSemestre());
        dto.setEstatusDetectado(entity.getEstatusDetectado());
        dto.setCritica(entity.getEsCritica());
        return dto;
    }

    static TblCasoBitacoraV2 toBitacoraEntity(Long idCaso, BitacoraCasoDTO dto) {
        TblCasoBitacoraV2 entity = new TblCasoBitacoraV2();
        entity.setId(dto.getId());
        entity.setIdCaso(idCaso);
        entity.setEvento(dto.getEvento());
        entity.setDetalle(dto.getDetalle());
        entity.setUsuario(dto.getUsuario());
        return entity;
    }

    static BitacoraCasoDTO toBitacoraDto(TblCasoBitacoraV2 entity) {
        if (entity == null) {
            return null;
        }
        BitacoraCasoDTO dto = new BitacoraCasoDTO();
        dto.setId(entity.getId());
        dto.setEvento(entity.getEvento());
        dto.setDetalle(entity.getDetalle());
        dto.setUsuario(entity.getUsuario());
        dto.setFecha(entity.getFecha());
        return dto;
    }

    static TblCasoEvidenciaV2 toEvidenciaEntity(Long idCaso, EvidenciaCasoDTO dto) {
        TblCasoEvidenciaV2 entity = new TblCasoEvidenciaV2();
        entity.setId(dto.getId());
        entity.setIdCaso(idCaso);
        entity.setTipoEvidencia(dto.getTipoEvidencia());
        entity.setRuta(dto.getRuta());
        entity.setDescripcion(dto.getDescripcion());
        entity.setUsuario(dto.getUsuario());
        return entity;
    }

    static EvidenciaCasoDTO toEvidenciaDto(TblCasoEvidenciaV2 entity) {
        if (entity == null) {
            return null;
        }
        EvidenciaCasoDTO dto = new EvidenciaCasoDTO();
        dto.setId(entity.getId());
        dto.setTipoEvidencia(entity.getTipoEvidencia());
        dto.setRuta(entity.getRuta());
        dto.setDescripcion(entity.getDescripcion());
        dto.setUsuario(entity.getUsuario());
        dto.setFecha(entity.getFecha());
        return dto;
    }

    static CasoSimilarDTO toCasoSimilarDto(TblCasoSimilarV2 entity) {
        if (entity == null) {
            return null;
        }
        CasoSimilarDTO dto = new CasoSimilarDTO();
        dto.setId(entity.getId());
        dto.setIdCasoRelacion(entity.getIdCasoRelacionado());
        dto.setPuntajeSimilitud(entity.getPuntajeSimilitud());
        dto.setMotivoRelacion(entity.getMotivoRelacion());
        return dto;
    }

    static ClasificacionCasoDTO crearClasificacionVacia(ContextoAsistenteCurricularV2DTO contexto) {
        ClasificacionCasoDTO dto = new ClasificacionCasoDTO();
        dto.setConfianza(0D);
        dto.setOperable(contexto != null && contexto.getIdPersonaObjetivo() != null);
        dto.setRequiereIntervencionHumana(Boolean.FALSE);
        return dto;
    }

    static ExpedienteMinimoCasoDTO crearExpedienteBase(ContextoAsistenteCurricularV2DTO contexto) {
        ExpedienteMinimoCasoDTO dto = new ExpedienteMinimoCasoDTO();

        CampoExpedienteCasoDTO persona = new CampoExpedienteCasoDTO();
        persona.setClaveCampo("ID_PERSONA");
        persona.setNombreCampo("Persona objetivo");
        persona.setObligatorio(Boolean.TRUE);
        persona.setPresente(contexto != null && contexto.getIdPersonaObjetivo() != null);
        persona.setObservacion(persona.getPresente() ? "Identificador disponible." : "Falta persona objetivo.");
        dto.getCampos().add(persona);

        CampoExpedienteCasoDTO plan = new CampoExpedienteCasoDTO();
        plan.setClaveCampo("ID_PLAN");
        plan.setNombreCampo("Plan academico");
        plan.setObligatorio(Boolean.TRUE);
        plan.setPresente(contexto != null && contexto.getIdPlan() != null);
        plan.setObservacion(plan.getPresente() ? "Plan disponible." : "Falta plan academico.");
        dto.getCampos().add(plan);

        CampoExpedienteCasoDTO periodo = new CampoExpedienteCasoDTO();
        periodo.setClaveCampo("ID_PERIODO");
        periodo.setNombreCampo("Periodo operativo");
        periodo.setObligatorio(Boolean.TRUE);
        periodo.setPresente(contexto != null && contexto.getIdPeriodo() != null);
        periodo.setObservacion(periodo.getPresente() ? "Periodo disponible." : "Falta periodo operativo.");
        dto.getCampos().add(periodo);

        if (!persona.getPresente()) {
            dto.getFaltantes().add("Persona objetivo");
        }
        if (!plan.getPresente()) {
            dto.getFaltantes().add("Plan academico");
        }
        if (!periodo.getPresente()) {
            dto.getFaltantes().add("Periodo operativo");
        }
        dto.setCompleto(dto.getFaltantes().isEmpty());
        return dto;
    }

    static String generarFolio() {
        return "CASO-V2-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    private static String truncar(String texto, int longitudMaxima) {
        if (texto == null) {
            return null;
        }
        String limpio = texto.trim().replaceAll("\\s+", " ");
        if (limpio.length() <= longitudMaxima) {
            return limpio;
        }
        return limpio.substring(0, Math.max(0, longitudMaxima - 3)) + "...";
    }
}
