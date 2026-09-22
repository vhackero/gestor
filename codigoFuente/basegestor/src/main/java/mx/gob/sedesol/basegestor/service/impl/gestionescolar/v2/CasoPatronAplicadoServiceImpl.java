package mx.gob.sedesol.basegestor.service.impl.gestionescolar.v2;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.AccionOperativaDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.CasoAcademicoOperativoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.ContextoAsistenteCurricularV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.CriterioOperativoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.MensajeInstitucionalContextualDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.PatronConocimientoDTO;
import mx.gob.sedesol.basegestor.commons.utils.InscripcionException;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.v2.TblCasoPatronAplicadoV2;
import mx.gob.sedesol.basegestor.model.repositories.gestionescolar.v2.TblCasoPatronAplicadoV2Repo;
import mx.gob.sedesol.basegestor.service.gestionescolar.v2.CasoPatronAplicadoService;

@Service("casoPatronAplicadoService")
public class CasoPatronAplicadoServiceImpl implements CasoPatronAplicadoService {

    @Autowired
    private TblCasoPatronAplicadoV2Repo patronAplicadoRepo;

    @Override
    @Transactional
    public void registrarPatronesAplicados(CasoAcademicoOperativoDTO caso, ContextoAsistenteCurricularV2DTO contexto,
            List<PatronConocimientoDTO> patrones) throws InscripcionException {
        if (caso == null || caso.getId() == null || contexto == null || patrones == null) {
            return;
        }
        String perfil = valor(contexto.getPerfilConsulta(), "SISTEMA");
        String periodo = valor(contexto.getPeriodoOperativo(), "SIN_PERIODO");
        for (PatronConocimientoDTO patron : patrones) {
            if (patron == null || patron.getId() == null) {
                continue;
            }
            String huella = calcularHuella(caso, patron, perfil, periodo);
            TblCasoPatronAplicadoV2 existente = patronAplicadoRepo
                    .findByIdCasoAndIdPatronCasoAndPerfilAndPeriodoOperativoAndHuellaEvaluacion(caso.getId(),
                            patron.getId(), perfil, periodo, huella);
            if (existente != null) {
                continue;
            }
            TblCasoPatronAplicadoV2 registro = new TblCasoPatronAplicadoV2();
            registro.setIdCaso(caso.getId());
            registro.setIdPatronCaso(patron.getId());
            registro.setPuntajeRelevancia(patron.getPuntajeRelevancia());
            registro.setOrdenAplicacion(patron.getOrdenAplicacion());
            registro.setPerfil(perfil);
            registro.setPeriodoOperativo(periodo);
            registro.setHuellaEvaluacion(huella);
            registro.setCriteriosAplicados(unirCriterios(patron.getCriterios()));
            registro.setAccionesAplicadas(unirAcciones(patron.getAcciones()));
            registro.setMensajesAplicados(unirMensajes(patron.getMensajes(), perfil, periodo));
            registro.setIdPersonaConsulta(contexto.getIdPersonaConsulta());
            patronAplicadoRepo.save(registro);
        }
    }

    private String calcularHuella(CasoAcademicoOperativoDTO caso, PatronConocimientoDTO patron, String perfil,
            String periodo) throws InscripcionException {
        StringBuilder contexto = new StringBuilder();
        agregar(contexto, caso.getId());
        agregar(contexto, clave(caso.getTipoCaso()));
        agregar(contexto, clave(caso.getMotivoRestriccion()));
        agregar(contexto, caso.getEstatusCaso());
        agregar(contexto, caso.getDiagnostico() != null ? caso.getDiagnostico().getSituacionAcademica() : null);
        agregar(contexto, caso.getDiagnostico() != null ? caso.getDiagnostico().getRiesgoActual() : null);
        agregar(contexto, caso.getDiagnostico() != null ? caso.getDiagnostico().getRestriccionDominante() : null);
        agregar(contexto, caso.getDiagnostico() != null ? caso.getDiagnostico().getTotalNoAcreditadas() : null);
        agregar(contexto, caso.getDiagnostico() != null ? caso.getDiagnostico().getTotalBloqueadas() : null);
        agregar(contexto, caso.getDiagnostico() != null ? caso.getDiagnostico().getTotalOmisiones() : null);
        agregar(contexto, patron.getId());
        agregar(contexto, patron.getPuntajeRelevancia());
        agregar(contexto, perfil);
        agregar(contexto, periodo);
        try {
            byte[] digest = MessageDigest.getInstance("SHA-256")
                    .digest(contexto.toString().getBytes(StandardCharsets.UTF_8));
            StringBuilder hexadecimal = new StringBuilder();
            for (byte valor : digest) {
                hexadecimal.append(String.format("%02x", Integer.valueOf(valor & 0xff)));
            }
            return hexadecimal.toString();
        } catch (Exception e) {
            throw new InscripcionException("No fue posible generar la huella de auditoría del patrón.", e);
        }
    }

    private String unirCriterios(List<CriterioOperativoDTO> criterios) {
        StringBuilder resultado = new StringBuilder();
        if (criterios != null) {
            for (CriterioOperativoDTO criterio : criterios) {
                agregar(resultado, criterio != null ? criterio.getClave() : null);
            }
        }
        return resultado.toString();
    }

    private String unirAcciones(List<AccionOperativaDTO> acciones) {
        StringBuilder resultado = new StringBuilder();
        if (acciones != null) {
            for (AccionOperativaDTO accion : acciones) {
                agregar(resultado, accion != null ? accion.getClave() : null);
            }
        }
        return resultado.toString();
    }

    private String unirMensajes(List<MensajeInstitucionalContextualDTO> mensajes, String perfil, String periodo) {
        StringBuilder resultado = new StringBuilder();
        if (mensajes != null) {
            for (MensajeInstitucionalContextualDTO mensaje : mensajes) {
                if (mensaje != null && perfil.equalsIgnoreCase(valor(mensaje.getPerfil(), ""))
                        && periodo.equalsIgnoreCase(valor(mensaje.getPeriodoOperativo(), ""))) {
                    agregar(resultado, mensaje.getClave());
                }
            }
        }
        return resultado.toString();
    }

    private void agregar(StringBuilder resultado, Object valor) {
        if (resultado.length() > 0) {
            resultado.append('|');
        }
        resultado.append(valor != null ? valor.toString() : "");
    }

    private String clave(mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.TipoCasoAcademicoDTO tipo) {
        return tipo != null ? tipo.getClave() : null;
    }

    private String clave(mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.MotivoRestriccionDTO motivo) {
        return motivo != null ? motivo.getClave() : null;
    }

    private String valor(String valor, String fallback) {
        return valor != null && !valor.trim().isEmpty() ? valor.trim() : fallback;
    }
}
