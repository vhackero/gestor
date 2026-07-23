package mx.gob.sedesol.basegestor.service.impl.gestionescolar.v2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.AsistenteInscripcionContextoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.DiagnosticoAcademicoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.CampoExpedienteCasoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.ContextoAsistenteCurricularV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.ExpedienteMinimoCasoDTO;
import mx.gob.sedesol.basegestor.commons.utils.InscripcionException;
import mx.gob.sedesol.basegestor.service.gestionescolar.AsistenteInscripcionService;
import mx.gob.sedesol.basegestor.service.gestionescolar.v2.ValidadorExpedienteCasoService;

@Service("validadorExpedienteCasoService")
public class ValidadorExpedienteCasoServiceImpl implements ValidadorExpedienteCasoService {

    @Autowired
    private AsistenteInscripcionService asistenteInscripcionService;

    @Override
    public ExpedienteMinimoCasoDTO validarExpedienteMinimo(ContextoAsistenteCurricularV2DTO contexto) {
        ExpedienteMinimoCasoDTO expediente = AsistenteCurricularV2Mapper.crearExpedienteBase(contexto);
        if (contexto == null) {
            expediente.setCompleto(Boolean.FALSE);
            expediente.setEstatusExpediente("INCOMPLETO");
            expediente.getFaltantes().add("No existe contexto operativo para validar el caso.");
            return expediente;
        }

        boolean contextoAcademicoSuficiente = tieneContextoAcademicoSuficiente(contexto);

        validarCampo(expediente, "personaObjetivo", "Persona objetivo", contexto.getIdPersonaObjetivo() != null,
                "No se identificó la persona objetivo del caso.");
        validarCampo(expediente, "plan", "Plan de estudios", contexto.getIdPlan() != null || contextoAcademicoSuficiente,
                "No se identificó el plan de estudios del caso.");
        validarCampo(expediente, "perfilConsulta", "Perfil de consulta",
                tieneTexto(contexto.getPerfilConsulta()),
                "No se identificó el perfil que consulta el caso.");
        validarCampo(expediente, "periodoOperativo", "Periodo operativo",
                tieneTexto(contexto.getPeriodoOperativo()),
                "No se identificó si el caso pertenece a inscripción o cursamiento.");
        validarCampo(expediente, "origenConsulta", "Origen de consulta",
                tieneTexto(contexto.getOrigenConsulta()),
                "No se identificó la pantalla u origen que abrió el caso.");

        if (Boolean.TRUE.equals(contexto.getVistaGestor())) {
            validarCampo(expediente, "personaConsulta", "Persona consulta",
                    contexto.getIdPersonaConsulta() != null,
                    "La consulta en modo gestor requiere identificar al gestor que abrió el caso.");
        }

        if (contexto.getIdPeriodo() == null) {
            expediente.getFaltantes().add("No se identificó el periodo/proceso específico del caso.");
        }

        boolean completo = expediente.getCampos().stream()
                .allMatch(campo -> !Boolean.TRUE.equals(campo.getObligatorio()) || Boolean.TRUE.equals(campo.getPresente()));
        expediente.setCompleto(Boolean.valueOf(completo));
        expediente.setEstatusExpediente(completo ? "COMPLETO" : "INCOMPLETO");
        return expediente;
    }

    private boolean tieneContextoAcademicoSuficiente(ContextoAsistenteCurricularV2DTO contexto) {
        if (contexto == null || contexto.getIdPersonaObjetivo() == null || asistenteInscripcionService == null) {
            return false;
        }
        try {
            AsistenteInscripcionContextoDTO contextoAsistido = asistenteInscripcionService
                    .obtenerContextoAsistido(contexto.getIdPersonaObjetivo());
            if (contextoAsistido == null) {
                return false;
            }
            DiagnosticoAcademicoDTO diagnostico = contextoAsistido.getDiagnosticoActual();
            if (diagnostico != null) {
                if (valor(diagnostico.getMateriasReprobadasActivas()) > 0
                        || valor(diagnostico.getMateriasPendientesNoInscritas()) > 0
                        || valor(diagnostico.getMateriasBloqueadasPorSeriacion()) > 0) {
                    return true;
                }
            }
            return contextoAsistido.getUnidades() != null && !contextoAsistido.getUnidades().isEmpty();
        } catch (InscripcionException e) {
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    private void validarCampo(ExpedienteMinimoCasoDTO expediente, String clave, String nombre, boolean disponible,
            String mensajeFaltante) {
        CampoExpedienteCasoDTO campo = buscarCampo(expediente, clave);
        if (campo == null) {
            campo = new CampoExpedienteCasoDTO();
            campo.setClaveCampo(clave);
            campo.setNombreCampo(nombre);
            campo.setObligatorio(Boolean.TRUE);
            expediente.getCampos().add(campo);
        }
        campo.setPresente(Boolean.valueOf(disponible));
        campo.setObservacion(disponible ? "Dato disponible." : mensajeFaltante);
        if (!disponible) {
            expediente.getFaltantes().add(mensajeFaltante);
        }
    }

    private CampoExpedienteCasoDTO buscarCampo(ExpedienteMinimoCasoDTO expediente, String clave) {
        for (CampoExpedienteCasoDTO campo : expediente.getCampos()) {
            if (campo != null && clave.equals(campo.getClaveCampo())) {
                return campo;
            }
        }
        return null;
    }

    private boolean tieneTexto(String valor) {
        return valor != null && !valor.trim().isEmpty();
    }

    private int valor(Integer numero) {
        return numero != null ? numero.intValue() : 0;
    }
}
