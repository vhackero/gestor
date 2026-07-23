package mx.gob.sedesol.basegestor.service.impl.gestionescolar.v2;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.CasoAcademicoOperativoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.CasoSimilarDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.ContextoAsistenteCurricularV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.DiagnosticoAcademicoOperativoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.DictamenCasoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.ExpedienteMinimoCasoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.FichaIntegralCasoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.ClasificacionCasoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.MensajeInstitucionalContextualDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.PatronConocimientoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.ViabilidadTecnicaDTO;
import mx.gob.sedesol.basegestor.commons.utils.InscripcionException;
import mx.gob.sedesol.basegestor.model.repositories.gestionescolar.v2.CatViabilidadTecnicaV2Repo;
import mx.gob.sedesol.basegestor.service.gestionescolar.v2.AsistenteCurricularV2Facade;
import mx.gob.sedesol.basegestor.service.gestionescolar.v2.BaseConocimientoAcademicoService;
import mx.gob.sedesol.basegestor.service.gestionescolar.v2.BitacoraCasoService;
import mx.gob.sedesol.basegestor.service.gestionescolar.v2.CasoAcademicoOperativoService;
import mx.gob.sedesol.basegestor.service.gestionescolar.v2.CasosSimilaresService;
import mx.gob.sedesol.basegestor.service.gestionescolar.v2.ClasificadorCasoService;
import mx.gob.sedesol.basegestor.service.gestionescolar.v2.DiagnosticoAcademicoOperativoService;
import mx.gob.sedesol.basegestor.service.gestionescolar.v2.RecomendadorResolucionOperativaService;
import mx.gob.sedesol.basegestor.service.gestionescolar.v2.ValidadorExpedienteCasoService;

@Service("asistenteCurricularV2Facade")
public class AsistenteCurricularV2FacadeImpl implements AsistenteCurricularV2Facade {

    @Autowired
    private CasoAcademicoOperativoService casoService;

    @Autowired
    private ValidadorExpedienteCasoService expedienteService;

    @Autowired
    private DiagnosticoAcademicoOperativoService diagnosticoService;

    @Autowired
    private RecomendadorResolucionOperativaService recomendadorService;

    @Autowired
    private ClasificadorCasoService clasificadorCasoService;

    @Autowired
    private BitacoraCasoService bitacoraCasoService;

    @Autowired
    private BaseConocimientoAcademicoService baseConocimientoAcademicoService;

    @Autowired
    private CasosSimilaresService casosSimilaresService;

    @Autowired
    private CatViabilidadTecnicaV2Repo viabilidadRepo;

    @Override
    @Transactional
    public FichaIntegralCasoDTO obtenerFichaIntegral(ContextoAsistenteCurricularV2DTO contexto) throws InscripcionException {
        FichaIntegralCasoDTO ficha = new FichaIntegralCasoDTO();
        CasoAcademicoOperativoDTO caso = resolverCaso(contexto);
        ficha.setCasoAcademico(caso);

        if (caso == null) {
            ficha.setMensajeGestor("No existe un caso V2 persistido para el contexto actual.");
            ficha.setMensajeEstudiante("Aun no existe un dictamen V2 disponible para tu trayectoria.");
            ficha.setAccionSugerida("Generar orientación académica operativa");
            ficha.setReglaAplicada("Sin regla aplicada");
            ficha.setRequiereSeguimiento(Boolean.FALSE);
            ficha.getAlertas().add("No existe orientación académica operativa asociada.");
            return ficha;
        }

        ExpedienteMinimoCasoDTO expediente = caso.getExpedienteMinimo() != null ? caso.getExpedienteMinimo()
                : expedienteService.validarExpedienteMinimo(contexto);
        caso.setExpedienteMinimo(expediente);

        DiagnosticoAcademicoOperativoDTO diagnostico = caso.getDiagnostico() != null ? caso.getDiagnostico()
                : diagnosticoService.construirDiagnostico(contexto);
        caso.setDiagnostico(diagnostico);

        DictamenCasoDTO dictamen = caso.getDictamen() != null ? caso.getDictamen()
                : recomendadorService.generarDictamen(caso);
        caso.setDictamen(dictamen);
        ficha.setPatronesConocimiento(obtenerPatrones(caso, contexto));
        ficha.setMensajesContextuales(obtenerMensajes(contexto, caso));
        ficha.setCasosSimilares(obtenerCasosSimilares(contexto, caso));
        caso.setCasosSimilares(ficha.getCasosSimilares());

        ficha.setMensajeEstudiante(resolverMensajeEstudiante(caso, dictamen, ficha.getMensajesContextuales()));
        ficha.setMensajeGestor(resolverMensajeGestor(caso, dictamen, ficha.getMensajesContextuales()));
        ficha.setAccionSugerida(resolverAccionSugerida(caso, dictamen, ficha.getPatronesConocimiento()));
        ficha.setReglaAplicada(obtenerReglaAplicada(caso, ficha.getPatronesConocimiento()));
        ficha.setRequiereSeguimiento(Boolean.TRUE.equals(caso.getRequiereIntervencionHumana())
                || Boolean.TRUE.equals(dictamen.getRequiereEscalamiento()));

        if (!Boolean.TRUE.equals(expediente.getCompleto())) {
            ficha.getAlertas().add("El expediente minimo del caso esta incompleto.");
        }
        if (diagnostico == null || diagnostico.getResumenMotor() == null) {
            ficha.getAlertas().add("No existe resumen de diagnostico persistido.");
        }
        if (dictamen == null || dictamen.getDictamen() == null) {
            ficha.getAlertas().add("No existe dictamen persistido.");
        }
        if (ficha.getPatronesConocimiento().isEmpty()) {
            ficha.getAlertas().add("No existen patrones de conocimiento asociados al tipo de caso.");
        }
        if (diagnostico != null && diagnostico.getAlertasDiagnostico() != null) {
            ficha.getAlertas().addAll(diagnostico.getAlertasDiagnostico());
        }
        return ficha;
    }

    private CasoAcademicoOperativoDTO resolverCaso(ContextoAsistenteCurricularV2DTO contexto) throws InscripcionException {
        if (contexto != null && contexto.getCasoActual() != null) {
            if (contexto.getCasoActual().getId() != null) {
                CasoAcademicoOperativoDTO caso = casoService.obtenerCasoPorId(contexto.getCasoActual().getId());
                registrarConsumoAutomatico(caso, contexto);
                return caso;
            }
            return contexto.getCasoActual();
        }
        if (contexto != null && contexto.getIdPersonaObjetivo() != null) {
            CasoAcademicoOperativoDTO casoReciente = casoService
                    .obtenerCasoMasRecientePorPersona(contexto.getIdPersonaObjetivo());
            if (casoReciente != null) {
                return refrescarCasoExistente(contexto, casoReciente);
            }
            return crearCasoInicial(contexto);
        }
        return null;
    }

    private CasoAcademicoOperativoDTO crearCasoInicial(ContextoAsistenteCurricularV2DTO contexto) throws InscripcionException {
        ExpedienteMinimoCasoDTO expediente = expedienteService.validarExpedienteMinimo(contexto);
        ClasificacionCasoDTO clasificacion = clasificadorCasoService.clasificarCaso(contexto);
        DiagnosticoAcademicoOperativoDTO diagnostico = diagnosticoService.construirDiagnostico(contexto);

        CasoAcademicoOperativoDTO casoNuevo = new CasoAcademicoOperativoDTO();
        casoNuevo.setIdPersona(contexto.getIdPersonaObjetivo());
        casoNuevo.setIdPlan(contexto.getIdPlan());
        casoNuevo.setIdPeriodo(contexto.getIdPeriodo());
        casoNuevo.setPerfilOrigen(contexto.getPerfilConsulta());
        casoNuevo.setOrigenCaso(contexto.getPeriodoOperativo());
        casoNuevo.setTipoCaso(clasificacion.getTipoCaso());
        casoNuevo.setMotivoRestriccion(clasificacion.getMotivoPrincipal());
        casoNuevo.setConfianzaClasificacion(clasificacion.getConfianza());
        casoNuevo.setRequiereIntervencionHumana(clasificacion.getRequiereIntervencionHumana());
        casoNuevo.setExpedienteCompleto(expediente.getCompleto());
        casoNuevo.setEstatusCaso(resolverEstatusInicial(expediente, clasificacion));
        casoNuevo.setDescripcionSolicitud(construirDescripcionInicial(contexto));
        casoNuevo.setClasificacion(clasificacion);
        casoNuevo.setExpedienteMinimo(expediente);
        casoNuevo.setDiagnostico(diagnostico);
        casoNuevo.setViabilidadTecnica(resolverViabilidadInicial(contexto, expediente, clasificacion));

        DictamenCasoDTO dictamen = recomendadorService.generarDictamen(casoNuevo);
        casoNuevo.setDictamen(dictamen);

        CasoAcademicoOperativoDTO guardado = casoService.crearCaso(casoNuevo);
        bitacoraCasoService.registrarEvento(guardado.getId(), "CREACION_AUTOMATICA",
                "Se generó automáticamente la orientación V2 desde " + contexto.getOrigenConsulta() + ".",
                contexto.getPerfilConsulta());
        return casoService.obtenerCasoPorId(guardado.getId());
    }

    private CasoAcademicoOperativoDTO refrescarCasoExistente(ContextoAsistenteCurricularV2DTO contexto,
            CasoAcademicoOperativoDTO casoActual) throws InscripcionException {
        ExpedienteMinimoCasoDTO expedienteActualizado = expedienteService.validarExpedienteMinimo(contexto);
        ClasificacionCasoDTO clasificacionActualizada = clasificadorCasoService.clasificarCaso(contexto);
        DiagnosticoAcademicoOperativoDTO diagnosticoActualizado = diagnosticoService.construirDiagnostico(contexto);

        casoActual.setExpedienteMinimo(expedienteActualizado);
        casoActual.setClasificacion(clasificacionActualizada);
        casoActual.setDiagnostico(diagnosticoActualizado);
        casoActual.setExpedienteCompleto(expedienteActualizado != null ? expedienteActualizado.getCompleto() : null);
        casoActual.setTipoCaso(clasificacionActualizada != null ? clasificacionActualizada.getTipoCaso() : null);
        casoActual.setMotivoRestriccion(
                clasificacionActualizada != null ? clasificacionActualizada.getMotivoPrincipal() : null);
        casoActual.setConfianzaClasificacion(
                clasificacionActualizada != null ? clasificacionActualizada.getConfianza() : null);
        casoActual.setRequiereIntervencionHumana(
                clasificacionActualizada != null ? clasificacionActualizada.getRequiereIntervencionHumana() : null);
        casoActual.setViabilidadTecnica(resolverViabilidadInicial(contexto, expedienteActualizado, clasificacionActualizada));
        casoActual.setEstatusCaso(resolverEstatusInicial(expedienteActualizado, clasificacionActualizada));

        DictamenCasoDTO dictamenActualizado = recomendadorService.generarDictamen(casoActual);
        casoActual.setDictamen(dictamenActualizado);

        if (!huboCambioRelevante(casoActual, clasificacionActualizada, diagnosticoActualizado, dictamenActualizado,
                expedienteActualizado)) {
            registrarConsumoAutomatico(casoActual, contexto);
            return casoActual;
        }

        CasoAcademicoOperativoDTO persistido = casoService.actualizarCaso(casoActual);
        bitacoraCasoService.registrarEvento(persistido.getId(), "RECALCULO_AUTOMATICO",
                construirDetalleRecalculo(clasificacionActualizada, diagnosticoActualizado, dictamenActualizado,
                        expedienteActualizado),
                contexto.getPerfilConsulta());
        registrarConsumoAutomatico(persistido, contexto);
        return casoService.obtenerCasoPorId(persistido.getId());
    }

    private void registrarConsumoAutomatico(CasoAcademicoOperativoDTO caso, ContextoAsistenteCurricularV2DTO contexto)
            throws InscripcionException {
        if (caso == null || caso.getId() == null || contexto == null) {
            return;
        }
        String origen = contexto.getOrigenConsulta() != null ? contexto.getOrigenConsulta() : "SIN_ORIGEN";
        String perfil = contexto.getPerfilConsulta() != null ? contexto.getPerfilConsulta() : "SIN_PERFIL";
        bitacoraCasoService.registrarEvento(caso.getId(), "CONSUMO_AUTOMATICO",
                "La orientación V2 fue consumida desde " + origen + " por perfil " + perfil + ".", perfil);
    }

    private ViabilidadTecnicaDTO resolverViabilidadInicial(ContextoAsistenteCurricularV2DTO contexto,
            ExpedienteMinimoCasoDTO expediente, ClasificacionCasoDTO clasificacion) {
        if (!Boolean.TRUE.equals(expediente.getCompleto())) {
            return AsistenteCurricularV2Mapper.toViabilidadDto(viabilidadRepo.findByClave("PENDIENTE_INFORMACION"));
        }
        if (Boolean.TRUE.equals(clasificacion.getRequiereIntervencionHumana())) {
            return AsistenteCurricularV2Mapper.toViabilidadDto(viabilidadRepo.findByClave("REQUIERE_REVALORACION"));
        }
        if (contexto != null && "INSCRIPCION".equalsIgnoreCase(contexto.getPeriodoOperativo())) {
            return AsistenteCurricularV2Mapper.toViabilidadDto(viabilidadRepo.findByClave("VIABLE_CON_AJUSTE"));
        }
        return AsistenteCurricularV2Mapper.toViabilidadDto(viabilidadRepo.findByClave("VIABLE"));
    }

    private String resolverEstatusInicial(ExpedienteMinimoCasoDTO expediente, ClasificacionCasoDTO clasificacion) {
        if (expediente == null || !Boolean.TRUE.equals(expediente.getCompleto())) {
            return "PENDIENTE_INFORMACIÓN";
        }
        if (clasificacion != null && clasificacion.getEstatusSugerido() != null
                && !clasificacion.getEstatusSugerido().trim().isEmpty()) {
            return clasificacion.getEstatusSugerido();
        }
        if (clasificacion != null && !Boolean.TRUE.equals(clasificacion.getOperable())) {
            return "NO_OPERABLE";
        }
        if (clasificacion != null && Boolean.TRUE.equals(clasificacion.getRequiereIntervencionHumana())) {
            return "EN_REVISION";
        }
        return "EN_ANALISIS";
    }

    private String construirDescripcionInicial(ContextoAsistenteCurricularV2DTO contexto) {
        return "Orientación generada automáticamente para perfil "
                + (contexto.getPerfilConsulta() != null ? contexto.getPerfilConsulta() : "SIN_PERFIL")
                + " en origen "
                + (contexto.getOrigenConsulta() != null ? contexto.getOrigenConsulta() : "SIN_ORIGEN")
                + " durante período "
                + (contexto.getPeriodoOperativo() != null ? contexto.getPeriodoOperativo() : "SIN_PERIODO") + ".";
    }

    private boolean huboCambioRelevante(CasoAcademicoOperativoDTO casoActual,
            ClasificacionCasoDTO clasificacionActualizada, DiagnosticoAcademicoOperativoDTO diagnosticoActualizado,
            DictamenCasoDTO dictamenActualizado, ExpedienteMinimoCasoDTO expedienteActualizado) {
        if (!iguales(casoActual.getEstatusCaso(), resolverEstatusInicial(expedienteActualizado, clasificacionActualizada))) {
            return true;
        }
        if (!iguales(clave(casoActual.getTipoCaso()), clave(clasificacionActualizada != null ? clasificacionActualizada.getTipoCaso() : null))) {
            return true;
        }
        if (!iguales(clave(casoActual.getMotivoRestriccion()),
                clave(clasificacionActualizada != null ? clasificacionActualizada.getMotivoPrincipal() : null))) {
            return true;
        }
        if (!iguales(casoActual.getConfianzaClasificacion(),
                clasificacionActualizada != null ? clasificacionActualizada.getConfianza() : null)) {
            return true;
        }
        if (!iguales(casoActual.getRequiereIntervencionHumana(),
                clasificacionActualizada != null ? clasificacionActualizada.getRequiereIntervencionHumana() : null)) {
            return true;
        }
        if (!iguales(casoActual.getExpedienteCompleto(),
                expedienteActualizado != null ? expedienteActualizado.getCompleto() : null)) {
            return true;
        }
        if (!iguales(casoActual.getDiagnostico() != null ? casoActual.getDiagnostico().getRestriccionDominante() : null,
                diagnosticoActualizado != null ? diagnosticoActualizado.getRestriccionDominante() : null)) {
            return true;
        }
        if (!iguales(casoActual.getDiagnostico() != null ? casoActual.getDiagnostico().getMotivoBloqueoPrincipal() : null,
                diagnosticoActualizado != null ? diagnosticoActualizado.getMotivoBloqueoPrincipal() : null)) {
            return true;
        }
        if (!iguales(casoActual.getDiagnostico() != null ? casoActual.getDiagnostico().getTotalNoAcreditadas() : null,
                diagnosticoActualizado != null ? diagnosticoActualizado.getTotalNoAcreditadas() : null)) {
            return true;
        }
        if (!iguales(casoActual.getDiagnostico() != null ? casoActual.getDiagnostico().getTotalBloqueadas() : null,
                diagnosticoActualizado != null ? diagnosticoActualizado.getTotalBloqueadas() : null)) {
            return true;
        }
        if (!iguales(casoActual.getDictamen() != null ? casoActual.getDictamen().getDictamen() : null,
                dictamenActualizado != null ? dictamenActualizado.getDictamen() : null)) {
            return true;
        }
        return !iguales(casoActual.getDictamen() != null ? casoActual.getDictamen().getMensajeEstudiante() : null,
                dictamenActualizado != null ? dictamenActualizado.getMensajeEstudiante() : null);
    }

    private String construirDetalleRecalculo(ClasificacionCasoDTO clasificacion,
            DiagnosticoAcademicoOperativoDTO diagnostico, DictamenCasoDTO dictamen,
            ExpedienteMinimoCasoDTO expediente) {
        StringBuilder detalle = new StringBuilder();
        detalle.append("Se recalculó la orientación V2 con estatus ")
                .append(clasificacion != null ? clasificacion.getEstatusSugerido() : "SIN_ESTATUS");
        detalle.append(", tipo ").append(clave(clasificacion != null ? clasificacion.getTipoCaso() : null));
        detalle.append(", motivo ").append(clave(clasificacion != null ? clasificacion.getMotivoPrincipal() : null));
        detalle.append(", restricción ").append(diagnostico != null ? diagnostico.getRestriccionDominante() : "SIN_RESTRICCIÓN");
        detalle.append(", dictamen ").append(dictamen != null ? dictamen.getDictamen() : "SIN_DICTAMEN");
        detalle.append(", expediente ")
                .append(expediente != null && Boolean.TRUE.equals(expediente.getCompleto()) ? "COMPLETO" : "INCOMPLETO")
                .append(".");
        return detalle.toString();
    }

    private List<PatronConocimientoDTO> obtenerPatrones(CasoAcademicoOperativoDTO caso,
            ContextoAsistenteCurricularV2DTO contexto) throws InscripcionException {
        if (caso == null || caso.getTipoCaso() == null) {
            return java.util.Collections.<PatronConocimientoDTO>emptyList();
        }
        return baseConocimientoAcademicoService.obtenerPatronesRelevantes(caso,
                contexto != null ? contexto.getPerfilConsulta() : null,
                contexto != null ? contexto.getPeriodoOperativo() : null);
    }

    private List<MensajeInstitucionalContextualDTO> obtenerMensajes(ContextoAsistenteCurricularV2DTO contexto,
            CasoAcademicoOperativoDTO caso)
            throws InscripcionException {
        if (contexto == null || contexto.getPerfilConsulta() == null || contexto.getPeriodoOperativo() == null) {
            return java.util.Collections.<MensajeInstitucionalContextualDTO>emptyList();
        }
        return baseConocimientoAcademicoService.obtenerMensajesContextuales(caso, contexto.getPerfilConsulta(),
                contexto.getPeriodoOperativo());
    }

    private List<CasoSimilarDTO> obtenerCasosSimilares(ContextoAsistenteCurricularV2DTO contexto,
            CasoAcademicoOperativoDTO caso) throws InscripcionException {
        if (contexto == null || caso == null || caso.getId() == null) {
            return java.util.Collections.<CasoSimilarDTO>emptyList();
        }
        ContextoAsistenteCurricularV2DTO contextoConsulta = contexto;
        contextoConsulta.setCasoActual(caso);
        return casosSimilaresService.buscarCasosSimilares(contextoConsulta);
    }

    private String obtenerReglaAplicada(CasoAcademicoOperativoDTO caso, List<PatronConocimientoDTO> patrones)
            throws InscripcionException {
        if (patrones != null && !patrones.isEmpty() && patrones.get(0).getNombrePatron() != null
                && !patrones.get(0).getNombrePatron().trim().isEmpty()) {
            return patrones.get(0).getNombrePatron();
        }
        if (caso.getTipoCaso() == null) {
            return "Sin regla aplicada";
        }
        if (caso.getTipoCaso().getNombre() != null && !caso.getTipoCaso().getNombre().trim().isEmpty()) {
            return caso.getTipoCaso().getNombre();
        }
        if (caso.getTipoCaso().getClave() != null && !caso.getTipoCaso().getClave().trim().isEmpty()) {
            return caso.getTipoCaso().getClave();
        }
        return "Regla general de clasificacion inicial";
    }

    private String resolverMensajeEstudiante(CasoAcademicoOperativoDTO caso, DictamenCasoDTO dictamen,
            List<MensajeInstitucionalContextualDTO> mensajesContextuales) {
        String sintetico = sintetizarMensajeEstudiante(caso);
        String mensajeDictamen = dictamen != null ? dictamen.getMensajeEstudiante() : null;
        String mensajeContextual = buscarPrimerMensaje(mensajesContextuales, "ESTUDIANTE");
        return combinarMensajes(sintetico, mensajeContextual, mensajeDictamen,
                "No se encontró una orientación contextual disponible para el estudiante.");
    }

    private String resolverMensajeGestor(CasoAcademicoOperativoDTO caso, DictamenCasoDTO dictamen,
            List<MensajeInstitucionalContextualDTO> mensajesContextuales) {
        String sintetico = sintetizarMensajeGestor(caso);
        String mensajeDictamen = dictamen != null ? dictamen.getMensajeGestor() : null;
        String mensajeContextual = buscarPrimerMensaje(mensajesContextuales, "GESTOR");
        return combinarMensajes(sintetico, mensajeContextual, mensajeDictamen,
                "No se encontró una orientación contextual disponible para el gestor.");
    }

    private String resolverAccionSugerida(CasoAcademicoOperativoDTO caso, DictamenCasoDTO dictamen,
            List<PatronConocimientoDTO> patrones) {
        String accionSintetica = sintetizarAccionSugerida(caso);
        if (accionSintetica != null) {
            return accionSintetica;
        }
        if (dictamen != null && dictamen.getAccionOperativa() != null
                && !esAccionGenerica(dictamen.getAccionOperativa().getNombre())) {
            return dictamen.getAccionOperativa().getNombre();
        }
        if (patrones != null) {
            for (PatronConocimientoDTO patron : patrones) {
                if (patron != null && patron.getAcciones() != null && !patron.getAcciones().isEmpty()
                        && patron.getAcciones().get(0) != null
                        && patron.getAcciones().get(0).getNombre() != null
                        && !patron.getAcciones().get(0).getNombre().trim().isEmpty()) {
                    return patron.getAcciones().get(0).getNombre();
                }
            }
        }
        return dictamen != null && dictamen.getAccionOperativa() != null ? dictamen.getAccionOperativa().getNombre()
                : "Dar seguimiento";
    }

    private String sintetizarMensajeEstudiante(CasoAcademicoOperativoDTO caso) {
        if (caso == null || caso.getDiagnostico() == null) {
            return null;
        }
        DiagnosticoAcademicoOperativoDTO diagnostico = caso.getDiagnostico();
        List<String> partes = new ArrayList<String>();
        int noAcreditadas = valor(diagnostico.getTotalNoAcreditadas());
        int bloqueadas = valor(diagnostico.getTotalBloqueadas());
        int omisiones = valor(diagnostico.getTotalOmisiones());
        String riesgo = limpio(diagnostico.getRiesgoActual());

        if (noAcreditadas >= 5) {
            partes.add("Tu trayectoria presenta un riesgo alto por acumulación severa de UD no acreditadas.");
        } else if (noAcreditadas >= 3) {
            partes.add("Tu trayectoria presenta un rezago importante por varias UD no acreditadas.");
        } else if (contiene(riesgo, "ALTO")) {
            partes.add("Tu trayectoria presenta un riesgo académico alto que requiere atención prioritaria.");
        }

        if (noAcreditadas > 0) {
            partes.add("La prioridad inmediata es regularizar primero las " + noAcreditadas
                    + " UD no acreditadas que hoy condicionan tu avance.");
        } else if (omisiones > 0) {
            partes.add("Debes aclarar y atender las UD omitidas antes de intentar ampliar tu carga.");
        }

        if (bloqueadas > 0) {
            partes.add("Además, hay " + bloqueadas
                    + " UD con restricción de seriación que sólo se liberan acreditando la antecedente.");
        }

        if (caso.getOrigenCaso() != null && caso.getOrigenCaso().equalsIgnoreCase("INSCRIPCION")) {
            partes.add("En este periodo conviene elegir sólo la carga compatible con tu regularización y con las reglas activas del sistema.");
        } else {
            partes.add("Durante cursamiento el foco debe estar en cerrar tus UD activas y preparar mejor el siguiente período.");
        }
        return unirPartes(partes);
    }

    private String sintetizarMensajeGestor(CasoAcademicoOperativoDTO caso) {
        if (caso == null || caso.getDiagnostico() == null) {
            return null;
        }
        DiagnosticoAcademicoOperativoDTO diagnostico = caso.getDiagnostico();
        List<String> partes = new ArrayList<String>();
        int noAcreditadas = valor(diagnostico.getTotalNoAcreditadas());
        int bloqueadas = valor(diagnostico.getTotalBloqueadas());
        String restriccion = limpio(diagnostico.getRestriccionDominante());

        if (noAcreditadas >= 5) {
            partes.add("El caso debe tratarse como rezago severo con regularización intensiva.");
        } else if (noAcreditadas >= 3) {
            partes.add("El caso combina rezago relevante y necesidad de regularización prioritaria.");
        }
        if (bloqueadas > 0) {
            partes.add("Explicar con claridad la seriación activa y evitar prometer desbloqueos manuales.");
        }
        if (contiene(restriccion, "AVANCE_ANUAL")) {
            partes.add("La orientación debe centrarse en restricción de avance anual, carga compatible y continuidad real.");
        }
        if (noAcreditadas > 0) {
            partes.add("Documentar cuántas UD no acreditadas sostienen la recomendación y comunicar un siguiente paso concreto al estudiante.");
        }
        return unirPartes(partes);
    }

    private String sintetizarAccionSugerida(CasoAcademicoOperativoDTO caso) {
        if (caso == null || caso.getDiagnostico() == null) {
            return null;
        }
        DiagnosticoAcademicoOperativoDTO diagnostico = caso.getDiagnostico();
        int noAcreditadas = valor(diagnostico.getTotalNoAcreditadas());
        int bloqueadas = valor(diagnostico.getTotalBloqueadas());

        if (noAcreditadas >= 5) {
            return "Priorizar regularización intensiva y limitar la carga a UD compatibles con tu recuperación académica";
        }
        if (noAcreditadas >= 3) {
            return "Priorizar regularización de UD no acreditadas antes de ampliar carga";
        }
        if (bloqueadas > 0) {
            return "Atender primero la UD antecedente que mantiene la seriación activa";
        }
        return null;
    }

    private String combinarMensajes(String principal, String contextual, String dictamen, String fallback) {
        List<String> partes = new ArrayList<String>();
        agregarParte(partes, principal);
        agregarParte(partes, contextual);
        if (!esMensajeGenerico(dictamen)) {
            agregarParte(partes, dictamen);
        }
        if (partes.isEmpty()) {
            agregarParte(partes, dictamen);
        }
        String texto = unirPartes(partes);
        return texto != null ? texto : fallback;
    }

    private void agregarParte(List<String> partes, String texto) {
        String limpio = limpio(texto);
        if (limpio == null) {
            return;
        }
        String normalizado = normalizarComparacion(limpio);
        for (String actual : partes) {
            String actualNormalizado = normalizarComparacion(actual);
            if (actualNormalizado.equals(normalizado)
                    || actualNormalizado.contains(normalizado)
                    || normalizado.contains(actualNormalizado)) {
                return;
            }
        }
        partes.add(limpio);
    }

    private String unirPartes(List<String> partes) {
        if (partes == null || partes.isEmpty()) {
            return null;
        }
        StringBuilder texto = new StringBuilder();
        for (String parte : partes) {
            String limpia = limpio(parte);
            if (limpia == null) {
                continue;
            }
            if (texto.length() > 0) {
                texto.append(" ");
            }
            texto.append(asegurarPuntoFinal(limpia));
        }
        return texto.length() > 0 ? texto.toString().trim() : null;
    }

    private String buscarPrimerMensaje(List<MensajeInstitucionalContextualDTO> mensajes, String perfil) {
        if (mensajes == null) {
            return null;
        }
        for (MensajeInstitucionalContextualDTO mensaje : mensajes) {
            if (mensaje != null && mensaje.getMensaje() != null && !mensaje.getMensaje().trim().isEmpty()
                    && perfil.equalsIgnoreCase(mensaje.getPerfil())) {
                return mensaje.getMensaje().trim();
            }
        }
        for (MensajeInstitucionalContextualDTO mensaje : mensajes) {
            if (mensaje != null && mensaje.getMensaje() != null && !mensaje.getMensaje().trim().isEmpty()) {
                return mensaje.getMensaje().trim();
            }
        }
        return null;
    }

    private boolean esMensajeGenerico(String mensaje) {
        if (mensaje == null || mensaje.trim().isEmpty()) {
            return true;
        }
        String texto = mensaje.trim().toUpperCase();
        List<String> genericos = new ArrayList<String>();
        genericos.add("SE GENERO UNA ORIENTACION INICIAL");
        genericos.add("SE GENERO UNA VALIDACION INICIAL");
        genericos.add("TU CASO SERA REVISADO");
        genericos.add("TU CASO REQUIERE INFORMACION ADICIONAL");
        for (String generico : genericos) {
            if (texto.contains(generico)) {
                return true;
            }
        }
        return false;
    }

    private boolean esAccionGenerica(String accion) {
        if (accion == null || accion.trim().isEmpty()) {
            return true;
        }
        String texto = accion.trim().toUpperCase();
        return "DAR SEGUIMIENTO".equals(texto) || "VALIDAR SELECCION".equals(texto)
                || "ESCALAR REVISION".equals(texto) || "SOLICITAR INFORMACION".equals(texto);
    }

    private boolean iguales(Object a, Object b) {
        return a == null ? b == null : a.equals(b);
    }

    private String limpio(String valor) {
        return valor != null && !valor.trim().isEmpty() ? valor.trim() : null;
    }

    private String asegurarPuntoFinal(String texto) {
        if (texto == null || texto.isEmpty()) {
            return texto;
        }
        char ultimo = texto.charAt(texto.length() - 1);
        if (ultimo == '.' || ultimo == ':' || ultimo == ';') {
            return texto;
        }
        return texto + ".";
    }

    private String normalizarComparacion(String texto) {
        if (texto == null) {
            return "";
        }
        return texto.toUpperCase().replaceAll("[^A-Z0-9 ]", " ").replaceAll("\\s+", " ").trim();
    }

    private int valor(Integer numero) {
        return numero != null ? numero.intValue() : 0;
    }

    private boolean contiene(String valor, String patron) {
        return valor != null && patron != null && valor.toUpperCase().contains(patron.toUpperCase());
    }

    private String clave(mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.TipoCasoAcademicoDTO tipoCaso) {
        return tipoCaso != null ? tipoCaso.getClave() : null;
    }

    private String clave(mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.MotivoRestriccionDTO motivo) {
        return motivo != null ? motivo.getClave() : null;
    }
}
